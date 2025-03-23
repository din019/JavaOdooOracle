package com.example.demo.controller;

import com.example.demo.service.OracleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CustomerController {

    private final OracleService oracleService;

    public CustomerController(OracleService oracleService) {
        this.oracleService = oracleService;
    }

    @PostMapping("/customer/create")
    public ResponseEntity<String> createCustomer(
            @RequestParam Float actionType,
            @RequestParam String customerExternalId,
            @RequestParam String subscriberExternalId,
            @RequestParam Float customerType,
            @RequestParam Float customerStatus,
            @RequestParam String firstName,
            @RequestParam String secondName,
            @RequestParam String email,
            @RequestParam Float contactNo,
            @RequestParam String address,
            @RequestParam String city,
            @RequestParam Float idType,
            @RequestParam String nationalId,
            @RequestParam String bankCode,
            @RequestParam String branchCode,
            @RequestParam String accountNumber,
            @RequestParam Float productId,
            @RequestParam Float mainBalance
    ) {
        // Calling the OracleService's method with all parameters
        String result = oracleService.callStoredProcedure(
                actionType,
                customerExternalId,
                subscriberExternalId,
                customerType,
                customerStatus,
                firstName,
                secondName,
                email,
                contactNo,
                address,
                city,
                idType,
                nationalId,
                bankCode,
                branchCode,
                accountNumber,
                productId,
                mainBalance
        );

        // Return the response from the stored procedure
        return ResponseEntity.ok(result);
    }

    @PostMapping("/subscriber/update")
    public ResponseEntity<String> updateSubscriber(
            @RequestParam Float actionType,
            @RequestParam String customerExternalId,
            @RequestParam String subscriberExternalId,
            @RequestParam Float productId,
            @RequestParam Float mainBalance,
            @RequestParam String bankCode,
            @RequestParam String branchCode,
            @RequestParam String accountNumber,
            @RequestParam Float status) {

        // Calling the OracleService's method for subscriber procedure
        String result = oracleService.callSubscriberProcedure(
                actionType,
                customerExternalId,
                subscriberExternalId,
                productId,
                mainBalance,
                bankCode,
                branchCode,
                accountNumber,
                status
        );

        // Return the response from the stored procedure
        return ResponseEntity.ok(result);
    }

    @PostMapping("/transaction/add")
    public ResponseEntity<String> addTransaction(
            @RequestParam String subscriberExternalId,
            @RequestParam Float transactionAmount,
            @RequestParam Float status,
            @RequestParam String fromBankCode,
            @RequestParam String toBankCode,
            @RequestParam String referenceTransaction,
            @RequestParam String rawMessage,
            @RequestParam Float transactionType) {

        // Calling the OracleService's method for add_transaction procedure
        String result = oracleService.addTransaction(
                subscriberExternalId,
                transactionAmount,
                status,
                fromBankCode,
                toBankCode,
                referenceTransaction,
                rawMessage,
                transactionType
        );

        // Return the response from the stored procedure
        return ResponseEntity.ok(result);
    }

    @PostMapping("/transaction/balance")
    public ResponseEntity<String> getBalance(
            @RequestParam String subscriberExternalId,
            @RequestParam Float productId) {

        // Calling the OracleService's method for get_balance procedure
        String result = oracleService.getBalance(subscriberExternalId, productId);

        // Return the response from the stored procedure
        return ResponseEntity.ok(result);
    }

}
