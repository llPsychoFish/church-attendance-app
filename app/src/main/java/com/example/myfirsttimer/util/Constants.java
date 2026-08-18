package com.example.myfirsttimer.util;

public final class Constants {
    private Constants() {
    }

    // Service types (matches docs/data-field-spec.md §3)
    public static final String SERVICE_SUN = "SUN";
    public static final String SERVICE_WED = "WED";
    public static final String SERVICE_FRI = "FRI";
    public static final String SERVICE_CELL = "CELL";

    public static final String[] SERVICE_TYPES = {
            SERVICE_SUN, SERVICE_WED, SERVICE_FRI, SERVICE_CELL
    };

    // Follow-up departments (matches First Timer Card checkboxes)
    public static final String[] DEPARTMENTS = {
            "Ushering",
            "Choir",
            "Technical",
            "Creative Art",
            "Media & New Media",
            "Innovations"
    };

    // Follow-up status values
    public static final String FOLLOW_UP_NEW = "New";
    public static final String FOLLOW_UP_CONTACTED = "Contacted";
    public static final String FOLLOW_UP_ATTENDED_AGAIN = "Attended again";
    public static final String FOLLOW_UP_INTEGRATED = "Integrated";
}
