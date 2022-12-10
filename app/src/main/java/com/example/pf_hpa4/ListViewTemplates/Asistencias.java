package com.example.pf_hpa4.ListViewTemplates;

public class Asistencias {

    private int id;
    private String fecha;
    private String hora;
    private int estudiante_id;
    private int grupo_asignatura_id;
    private int estado_asistencia_id;


    public Asistencias(int id_ , String fecha_, String hora_, int estudiante_id_, int grupo_asignatura_id_, int estado_asistencia_id_){
        id = id_;
        fecha = fecha_;
        hora = hora_;
        estudiante_id = estudiante_id_;
        grupo_asignatura_id = grupo_asignatura_id_;
        estado_asistencia_id = estado_asistencia_id_;
    }

    public int getId(){
        return id;
    }

    public String getFecha(){
        return fecha;
    }

    public String getHora() {
        return hora;
    }

    public int getEstudiante_id() {
        return estudiante_id;
    }

    public int getGrupo_id() {return grupo_asignatura_id;}

    public int getEstado_Asistencia() {
        return estado_asistencia_id;
    }

}
