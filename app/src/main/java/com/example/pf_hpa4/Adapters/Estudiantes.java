package com.example.pf_hpa4.Adapters;

public class Estudiantes {
    private int id;
    private String nombre;
    private String apellido;
    private String cedula;
    private String correo;
    private String foto_url;


    public Estudiantes(int id_ , String nombre_, String apellido_, String cedula_, String correo_, String foto_url_){
        id = id_;
        nombre = nombre_;
        apellido = apellido_;
        cedula = cedula_;
        correo = correo_;
        foto_url = foto_url_;
    }

    public int getId(){
        return id;
    }

    public String getNombre(){ return nombre; }

    public String getApellido() {
        return apellido;
    }

    public String getCedula() {
        return cedula;
    }

    public String getCorreo() {
        return correo;
    }

    public String getFoto() {
        return foto_url;
    }
}
