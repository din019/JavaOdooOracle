package com.example.demo.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

@Service
public class OracleService {

    private final JdbcTemplate jdbcTemplate;

    // Constructor injection of JdbcTemplate
    public OracleService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String callStoredProcedure(Float actionType, String customerExternalId, String subscriberExternalId,
                                      Float customerType, Float customerStatus, String firstName,
                                      String secondName, String email, Float contactNo, String address,
                                      String city, Float idType, String nationalId, String bankCode,
                                      String branchCode, String accountNumber, Float productId, Float mainBalance) {
        // Using jdbcTemplate to get a connection
        return jdbcTemplate.execute((Connection connection) -> {
            try (CallableStatement cs = connection.prepareCall("{call service_pkg.customer(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}")) {

                // Set the IN parameters, converting Floats to Strings
                cs.setString(1, String.valueOf(actionType));
                cs.setString(2, customerExternalId);
                cs.setString(3, subscriberExternalId);
                cs.setString(4, String.valueOf(customerType));
                cs.setString(5, String.valueOf(customerStatus));
                cs.setString(6, firstName);
                cs.setString(7, secondName);
                cs.setString(8, email);
                cs.setString(9, String.valueOf(contactNo));
                cs.setString(10, address);
                cs.setString(11, city);
                cs.setString(12, String.valueOf(idType));
                cs.setString(13, nationalId);
                cs.setString(14, bankCode);
                cs.setString(15, branchCode);
                cs.setString(16, accountNumber);
                cs.setString(17, String.valueOf(productId));
                cs.setString(18, String.valueOf(mainBalance));

                // Register OUT parameters
                cs.registerOutParameter(19, Types.INTEGER);  // v_response_code
                cs.registerOutParameter(20, Types.VARCHAR); // v_response_message

                // Execute the procedure
                cs.execute();

                // Retrieve OUT parameters
                int responseCode = cs.getInt(19);
                String responseMessage = cs.getString(20);

                // Return the response message or code
                return "Response Code: " + responseCode + ", Message: " + responseMessage;

            } catch (Exception e) {
                return "Error: " + e.getMessage();
            }
        });
    }
}
