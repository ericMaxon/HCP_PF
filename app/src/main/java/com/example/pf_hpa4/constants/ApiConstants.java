package com.example.pf_hpa4.constants;

public class ApiConstants {
    public static final String BaseUrl = "https://asistencia-upn43.ondigitalocean.app/api/";

    public static class EAttendeeStatus {
        public static final int presentId = 1;
        public static final int lateId = 2;
        public static final int absentId = 3;
        public static final int excusedAbsenceId = 4;
    }
    public static class AttendeeStatus {
        public static String getStatus(int statusId){
            switch(statusId){
                case EAttendeeStatus.presentId:
                    return "Prensente";
                case EAttendeeStatus.lateId:
                    return "Tardanza";
                case EAttendeeStatus.absentId:
                    return "Ausencia";
                case EAttendeeStatus.excusedAbsenceId:
                    return "Ausencia con excusa";
                default:
                    return "No se ha definido el estado de la asistencia";
            }
        }
    }
    public static class Role {
        public static Integer admin = 1;
        public static Integer teacher = 2;
        public static Integer student = 3;
    }
}
