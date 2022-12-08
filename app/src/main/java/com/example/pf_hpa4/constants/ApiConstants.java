package com.example.pf_hpa4.constants;

public class ApiConstants {
    public static final String BaseUrl = "https://asistencia-upn43.ondigitalocean.app/api/";

    public static class AttendeeStatus {
        public String present = "Presente";
        public String late = "Tardanza";
        public String absent = "Ausencia";
        public String excusedAbsence = "Ausencia con excusa";
    }

    public static class Roles {
        public static Integer admin = 1;
        public static Integer teacher = 2;
        public static Integer student = 3;
    }
}
