package com.drodriguez.es.alumnosdam.repositories;

import com.drodriguez.es.alumnosdam.managers.DataBaseManager;
import com.drodriguez.es.alumnosdam.models.Alumno;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

public class AlumnosRepository implements IRepositoryAlumnos {
    public static AlumnosRepository instance;
    public final ObservableList<Alumno> repository = FXCollections.observableArrayList();
    DataBaseManager db;
    public AlumnosRepository(DataBaseManager db) {
        this.db = db;
    }
    public static AlumnosRepository getInstance(DataBaseManager db) {
        if (instance == null) {
            instance = new AlumnosRepository(db);
        }
        return instance;
    }
    @Override
    public ObservableList<Alumno> findAll() throws SQLException {
        String sql = "SELECT * FROM ALUMNOS";
        db.open();
        ResultSet resultado = db.select(sql).orElseThrow(() -> new SQLException("Error mostrando todos los alumnos"));
        repository.clear();
        while(resultado.next()) {
            repository.add(
                    new Alumno(
                            resultado.getInt("id"),
                            resultado.getString("dni"),
                            resultado.getString("nombreApellidos"),
                            resultado.getDouble("nota"),
                            LocalDate.parse(resultado.getString("fecha"))
                            ));
        }
        return repository;
    }

    @Override
    public Optional<Alumno> findById(Integer id) throws SQLException {
        String sql = "SELECT * FROM ALUMNOS WHERE id = ?";
        db.open();
        ResultSet resultado = db.select(sql, id).orElseThrow(SQLException::new);
        if(resultado.first()){
            Alumno alumno = new Alumno(
                    resultado.getInt("id"),
                    resultado.getString("dni"),
                    resultado.getString("nombreApellidos"),
                    resultado.getDouble("nota"),
                    LocalDate.parse(resultado.getString("fecha"))
            );
            db.close();
            return Optional.of(alumno);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Alumno> save(Alumno alumno) throws SQLException {
        String sql = "INSERT INTO ALUMNOS (id, dni, nombreApellidos, nota, fechaNacimiento) VALUES (null, ?, ?, ?, ?)";
        db.open();
        db.insert(sql, alumno.getDni(), alumno.getNombreApellidos(), alumno.getNota(), alumno.getFechaNacimiento());
        db.close();
        return Optional.of(alumno);
    }

    @Override
    public Optional<Alumno> update(Integer integer, Alumno entity) throws SQLException {
        return Optional.empty();
    }

    @Override
    public void delete(Alumno alumno) throws SQLException {
        String sql = "DELETE FROM ALUMNOS WHERE id=?";
        db.open();
        db.delete(sql, alumno.getID());
        db.close();
        repository.remove(alumno);
    }
}
