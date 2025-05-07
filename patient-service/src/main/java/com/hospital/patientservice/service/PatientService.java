package com.hospital.patientservice.service;

import com.hospital.patientservice.dto.PatientRequestDTO;
import com.hospital.patientservice.dto.PatientResponseDTO;
import com.hospital.patientservice.exception.EmailAlreadyExistException;
import com.hospital.patientservice.exception.PatientNotFoundException;
import com.hospital.patientservice.grpc.BillingServiceClientGrpc;
import com.hospital.patientservice.kafka.KafkaProducer;
import com.hospital.patientservice.mapper.PatientMapper;
import com.hospital.patientservice.model.Patient;
import com.hospital.patientservice.repo.PatientRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PatientService {
    private final PatientRepo patientRepo;
    private final BillingServiceClientGrpc billingServiceClient;
    private final KafkaProducer kafkaProducer;

    public PatientService(PatientRepo patientRepo, BillingServiceClientGrpc billingServiceClient, KafkaProducer kafkaProducer) {
        this.patientRepo = patientRepo;
        this.billingServiceClient = billingServiceClient;
        this.kafkaProducer = kafkaProducer;
    }
    public List<PatientResponseDTO> getPatients(){
        List<Patient> patients = patientRepo.findAll();
        List<PatientResponseDTO> patientResponseDTOs = patients.stream()
                .map(PatientMapper::patientToDto).toList();
        return patientResponseDTOs;
    }

    public PatientResponseDTO  createPatient(PatientRequestDTO patientRequestDTO) {
        if(patientRepo.existsByEmail(patientRequestDTO.getEmail())) {
            throw new EmailAlreadyExistException("A patient with the email: "+ patientRequestDTO.getEmail()+ "already exists");
        }
        Patient patient = patientRepo.save(PatientMapper.dtoToPatient(patientRequestDTO));

        //create a patient billing account
        billingServiceClient.createBillingAccount(patient.getId().toString(), patient.getName(),patient.getEmail());

        //send a kafka event to consumers
        kafkaProducer.sendEvent(patient);

        return PatientMapper.patientToDto(patient);
    }
    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO) {
        Patient patient = patientRepo.findById(id).orElseThrow(()-> {
            return new PatientNotFoundException("Patient with Id: " + id + "not found");
        });

        if(patientRepo.existsByEmailAndIdNot(patientRequestDTO.getEmail(), id)) {
            throw new EmailAlreadyExistException("A patient with the email: "
                    + patientRequestDTO.getEmail()+ "already exists");
        }
        patient.setName(patientRequestDTO.getName());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));

        return PatientMapper.patientToDto(patientRepo.save(patient));

    }

    public void deletePatient(UUID id) {
        patientRepo.deleteById(id);
    }
}
