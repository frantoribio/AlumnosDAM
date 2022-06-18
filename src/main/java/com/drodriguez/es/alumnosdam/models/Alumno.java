package com.drodriguez.es.alumnosdam.models;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Alumno {
    private IntegerProperty ID;
    private StringProperty dni;
    private StringProperty nombreApellidos;
    private DoubleProperty nota;
    private ObjectProperty<LocalDate> fechaNacimiento;

    public Alumno(int ID, String dni, String nombreApellidos, double nota, LocalDate fechaNacimiento){
        this.ID = new SimpleIntegerProperty(ID);
        this.dni = new SimpleStringProperty(dni);
        this.nombreApellidos = new SimpleStringProperty(nombreApellidos);
        this.nota = new SimpleDoubleProperty(nota);
        this.fechaNacimiento = new SimpleObjectProperty<>(fechaNacimiento);
    }

    //Getter and Setter
    public int getID() {
        return ID.get();
    }

    public IntegerProperty IDProperty() {
        return ID;
    }

    public void setID(int ID) {
        this.ID.set(ID);
    }

    public String getDni() {
        return dni.get();
    }

    public StringProperty dniProperty() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni.set(dni);
    }

    public String getNombreApellidos() {
        return nombreApellidos.get();
    }

    public StringProperty nombreApellidosProperty() {
        return nombreApellidos;
    }

    public void setNombreApellidos(String nombreApellidos) {
        this.nombreApellidos.set(nombreApellidos);
    }

    public double getNota() {
        return nota.get();
    }

    public DoubleProperty notaProperty() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota.set(nota);
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento.get();
    }

    public ObjectProperty<LocalDate> fechaNacimientoProperty() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento.set(fechaNacimiento);
    }
}
