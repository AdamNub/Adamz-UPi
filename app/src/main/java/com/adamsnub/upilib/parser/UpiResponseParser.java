package com.adamsnub.upilib.parser;

import com.adamsnub.upilib.models.TransactionResponse;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class UpiResponseParser {

    public static TransactionResponse parse(String response) {
        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setRawResponse(response);

        if (response == null || response.isEmpty()) {
            transactionResponse.setStatus(TransactionResponse.STATUS_CANCELLED);
            return transactionResponse;
        }

        // Parse the response string into a map of key-value pairs
        Map<String, String> responseMap = parseResponseString(response);

        // Extract common fields
        transactionResponse.setTransactionId(responseMap.get("txnid"));
        transactionResponse.setResponseCode(responseMap.get("responsecode"));
        transactionResponse.setApprovalRefNo(responseMap.get("approvalrefno"));
        transactionResponse.setStatus(determineStatus(responseMap));

        return transactionResponse;
    }

    private static Map<String, String> parseResponseString(String response) {
        Map<String, String> map = new HashMap<>();
        String[] pairs = response.split("&");
        
        for (String pair : pairs) {
            String[] keyValue = pair.split("=", 2);
            if (keyValue.length == 2) {
                // Normalize key to lowercase to avoid case-sensitivity bugs
                map.put(keyValue[0].toLowerCase(), keyValue[1]);
            }
        }

        // Log the parsed map to help with debugging
        Log.d("UPI Response Map", map.toString());
        
        return map;
    }

    private static String determineStatus(Map<String, String> responseMap) {
        // Check if response map is empty or null
        if (responseMap == null || responseMap.isEmpty()) {
            return TransactionResponse.STATUS_FAILURE;  // Treat empty response as failure
        }

        String status = responseMap.get("status");

        if (status != null) {
            if ("success".equalsIgnoreCase(status)) {
                return TransactionResponse.STATUS_SUCCESS;
            } else if ("failure".equalsIgnoreCase(status) || "failed".equalsIgnoreCase(status)) {
                return TransactionResponse.STATUS_FAILURE;
            } else if ("submitted".equalsIgnoreCase(status)) {
                return TransactionResponse.STATUS_SUBMITTED;  // Treat "submitted" as pending
            }
        }

        // Check response code for standard UPI codes
        String responseCode = responseMap.get("responsecode");
        if ("00".equals(responseCode)) {
            return TransactionResponse.STATUS_SUCCESS;
        } else if ("01".equals(responseCode) || "02".equals(responseCode)) {
            return TransactionResponse.STATUS_FAILURE;  // Standard UPI failure codes
        }

        return TransactionResponse.STATUS_FAILURE;  // Default to failure if no other condition is met
    }
}