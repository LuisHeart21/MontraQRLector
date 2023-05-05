package com.example.montraqrlector.models;

public class People {
    String lectura;
    String id;
    String qrscan;
    String name;
    String empresa;
    String tel1;
    String tel2;
    String correo1;
    String correo2;
    String informacion;
    String comentarios;
    String agrx;

    public People(String id, String lectura, String qrscan, String name, String empresa, String tel1, String tel2, String correo1,
    String correo2, String informacion, String comentarios, String agrx) {
        this.lectura = lectura;
        this.id = id;
        this.qrscan = qrscan;
        this.name = name;
        this.empresa = empresa;
        this.tel1 = tel1;
        this.tel2 = tel2;
        this.correo1 = correo1;
        this.correo2 = correo2;
        this.informacion= informacion;
        this.comentarios = comentarios;
        this.agrx = agrx;
    }
    public String getLectura() {
        return lectura;
    }

    public String getId() {
        return id;
    }
    public String getQrscan() { return qrscan;}
    public String getName() {
        return name;
    }

    public String getEmpresa() {
        return empresa;
    }

    public String getTel1() {
        return tel1;
    }
    public String getTel2() {
        return tel2;
    }
    public String getCorreo1() {
        return correo1;
    }
    public String getCorreo2() {
        return correo2;
    }
    public String getInformacion() {
        return informacion;
    }
    public String getComentarios() {
        return comentarios;
    }
    public String getAgrx() {
        return agrx;
    }
}