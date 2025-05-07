package com.hospital.billingservice.grpc;

import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc.BillingServiceImplBase;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
public class BillingGrpcService extends BillingServiceImplBase {
    private static final Logger log = LoggerFactory.getLogger(BillingGrpcService.class);

    @Override
    public void createBillingAccount(BillingRequest billingRequest, StreamObserver<BillingResponse> responseObserver) {
        log.info("CreateBillingAccount request received {}", billingRequest.toString());
        //business logic to save transactions to db, calculations...

        BillingResponse response = BillingResponse.newBuilder().setAccountId("12345").setStatus("Active").build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }
}
