package com.tscode.common.constant;

public final class MessageConstants {

    private MessageConstants() {
        // Private constructor to prevent instantiation
    }

    // General Messages
    public static final String ASYNC_PROCESSING = "The request is being processed asynchronously.";

    // Employee Fetch Messages
    public static final String EMPLOYEE_FETCH_SUCCESS = "Employee with ID %d fetched and saved successfully.";
    public static final String EMPLOYEE_FETCH_FAILED = "Failed to fetch and save employee with ID %d.";
    public static final String INVALID_EMPLOYEE_ID = "Invalid employee ID: %d.";

    // Employee List Messages
    public static final String EMPLOYEE_LIST_FETCH_SUCCESS = "All employees from the list have been saved successfully.";
    public static final String EMPLOYEE_LIST_FETCH_FAILED = "Failed to fetch and save employee list.";
    public static final String EMPLOYEE_LIST_EMPTY = "The employee list is empty or not available.";

    // Error Codes
    public static final String BUSINESS_ERROR_CODE = "BUSINESS_ERROR";
    public static final String INTERNAL_SERVER_ERROR_CODE = "INTERNAL_SERVER_ERROR";

    //Message Error
    public static final String GLOBAL_EXCEPTION_ERROR = "An unexpected error occurred";
}