package com.example.pf_hpa4.ListViewTemplates;

public class Grupos {

    private int id;
    private String asignatura;
    private String codigo_asignatura;
    private int docente_id;
    private String grupo;
    private String periodo;

    public Grupos(int id_ , String asignatura_, String codigo_asignatura_, int docente_id_, String grupo_, String periodo_){
        id = id_;
        asignatura = asignatura_;
        codigo_asignatura = codigo_asignatura_;
        docente_id = docente_id_;
        grupo = grupo_;
        periodo = periodo_;
    }

    public int getId(){
        return id;
    }

    public String getAsignatura(){
        return asignatura;
    }

    public String getCodigoAsignatura() {
        return codigo_asignatura;
    }

    public int getDocenteId() {
        return docente_id;
    }

    public String getGrupo() {
        return grupo;
    }

    public String getPeriodo() {
        return periodo;
    }

}