package com.hospital.patientservice.mapper;

import com.hospital.patientservice.dto.PatientRequestDTO;
import com.hospital.patientservice.dto.PatientResponseDTO;
import com.hospital.patientservice.model.Patient;

import java.time.LocalDate;

public class PatientMapper {
    public static PatientResponseDTO patientToDto(Patient patient) {

        PatientResponseDTO patientResponseDTO = new PatientResponseDTO();

        patientResponseDTO.setId(patient.getId().toString());
        patientResponseDTO.setEmail(patient.getEmail());
        patientResponseDTO.setAddress(patient.getAddress());
        patientResponseDTO.setDateOfBirth(patient.getDateOfBirth().toString());
        patientResponseDTO.setName(patient.getName());

        return patientResponseDTO;
    }

    public static Patient dtoToPatient(PatientRequestDTO patientReqDTO) {
        Patient patient = new Patient();
        patient.setName(patientReqDTO.getName());
        patient.setEmail(patientReqDTO.getEmail());
        patient.setAddress(patientReqDTO.getAddress());
        patient.setDateOfBirth(LocalDate.parse(patientReqDTO.getDateOfBirth()));
        patient.setRegisteredDate(LocalDate.parse(patientReqDTO.getRegisteredDate()));

        return patient;
    }
}
