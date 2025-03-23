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

    public String callSubscriberProcedure(Float actionType, String customerExternalId, String subscriberExternalId,
                                          Float productId, Float mainBalance, String bankCode, String branchCode,
                                          String accountNumber, Float status) {
        return jdbcTemplate.execute((Connection connection) -> {
            try (CallableStatement cs = connection.prepareCall("{call service_pkg.subscriber(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}")) {

                // Set IN parameters
                cs.setString(1, String.valueOf(actionType));
                cs.setString(2, customerExternalId);
                cs.setString(3, subscriberExternalId);
                cs.setString(4, String.valueOf(productId));
                cs.setString(5, String.valueOf(mainBalance));
                cs.setString(6, bankCode);
                cs.setString(7, branchCode);
                cs.setString(8, accountNumber);
                cs.setString(9, String.valueOf(status));

                // Register OUT parameters
                cs.registerOutParameter(10, Types.INTEGER);  // v_response_code
                cs.registerOutParameter(11, Types.VARCHAR); // v_response_message

                // Execute the procedure
                cs.execute();

                // Retrieve OUT parameters
                int responseCode = cs.getInt(10);
                String responseMessage = cs.getString(11);

                return "Response Code: " + responseCode + ", Message: " + responseMessage;

            } catch (Exception e) {
                return "Error: " + e.getMessage();
            }
        });
    }

    public String addTransaction(String subscriberExternalId, Float transactionAmount, Float status,
                                 String fromBankCode, String toBankCode, String referenceTransaction,
                                 String rawMessage, Float transactionType) {
        return jdbcTemplate.execute((Connection connection) -> {
            try (CallableStatement cs = connection.prepareCall("{call transaction_pkg.add_transaction(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}")) {

                // Set IN parameters
                cs.setString(1, subscriberExternalId);
                cs.setString(2, String.valueOf(transactionAmount));
                cs.setString(3, String.valueOf(status));
                cs.setString(4, fromBankCode);
                cs.setString(5, toBankCode);
                cs.setString(6, referenceTransaction);
                cs.setString(7, rawMessage);
                cs.setString(8, String.valueOf(transactionType));

                // Register OUT parameters
                cs.registerOutParameter(9, Types.INTEGER);  // v_response_code
                cs.registerOutParameter(10, Types.VARCHAR); // v_response_message
                cs.registerOutParameter(11, Types.VARCHAR); // v_reference79transaction

                // Execute the procedure
                cs.execute();

                // Retrieve OUT parameters
                int responseCode = cs.getInt(9);
                String responseMessage = cs.getString(10);
                String reference79Transaction = cs.getString(11);

                return "Response Code: " + responseCode + ", Message: " + responseMessage + ", Reference: " + reference79Transaction;

            } catch (Exception e) {
                return "Error: " + e.getMessage();
            }
        });
    }

    public String getBalance(String subscriberExternalId, Float productId) {
        return jdbcTemplate.execute((Connection connection) -> {
            try (CallableStatement cs = connection.prepareCall("{call transaction_pkg.get_balance(?, ?, ?, ?, ?)}")) {

                // Set IN parameters
                cs.setString(1, subscriberExternalId);
                cs.setString(2, String.valueOf(productId));

                // Register OUT parameters
                cs.registerOutParameter(3, Types.INTEGER);  // v_response_code
                cs.registerOutParameter(4, Types.VARCHAR); // v_response_message
                cs.registerOutParameter(5, Types.FLOAT);   // v_balance

                // Execute the procedure
                cs.execute();

                // Retrieve OUT parameters
                int responseCode = cs.getInt(3);
                String responseMessage = cs.getString(4);
                Float balance = cs.getFloat(5);

                return "Response Code: " + responseCode + ", Message: " + responseMessage + ", Balance: " + balance;

            } catch (Exception e) {
                return "Error: " + e.getMessage();
            }
        });
    }


}
