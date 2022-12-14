package com.example.pf_hpa4.constants;

import android.widget.Toast;

import com.example.pf_hpa4.Activities.PassListActivity;

import java.time.LocalDateTime;

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
                    return "Presente";
                case EAttendeeStatus.lateId:
                    return "Tardanza";
                case EAttendeeStatus.absentId:
                    return "Ausencia";
                case EAttendeeStatus.excusedAbsenceId:
                    return "Ausencia justificada";
                default:
                    return "No se ha definido el estado de la asistencia";
            }
        }
        public static Integer getStatusId(LocalDateTime time, LocalDateTime Starttime){

            int minutes = time.getMinute() - Starttime.getMinute();
            int hours = time.getHour() - Starttime.getHour();

            if ((hours < 1) && (minutes < PasarAsistencia.timeLate) ) {
                return EAttendeeStatus.presentId;
            } else {
                return EAttendeeStatus.lateId;
            }

        }
    }
    public static class Role {
        public static Integer admin = 1;
        public static Integer teacher = 2;
        public static Integer student = 3;
    }

    public static class PasarAsistencia{
        public static Integer timeLate = 3; //Tiempo despues de iniciar un registro de asistencia para marcar al estudiante como tardanza
    }
}
