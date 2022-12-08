package com.example.pf_hpa4.constants;

public class ApiConstants {
    public static final String BaseUrl = "https://asistencia-upn43.ondigitalocean.app/api/";

    public static class AttendeeStatus {
        public static int presentId = 1;

        public static String presentName = "Presente";
        public static int lateId = 2;
        public static String lateName = "Tardanza";
        public static int absentId = 3;
        public static String absentName = "Ausencia";
        public static int excusedAbsenceId = 4;
        public static String excusedAbsenceName = "Ausencia con excusa";
    }
    public static class Roles {
        public static Integer admin = 1;
        public static Integer teacher = 2;
        public static Integer student = 3;
    }
}
