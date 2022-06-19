package com.drodriguez.es.alumnosdam.services;

import com.drodriguez.es.alumnosdam.managers.DataBaseManager;
import com.drodriguez.es.alumnosdam.models.Alumno;
import com.drodriguez.es.alumnosdam.models.PROMOCION;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class CSVStorage implements ICSVStorage {
    DataBaseManager db;

    @Override
    public boolean save(Path path, List<Alumno> item) {
        var encabezado = "id, dni, nombreApellidos, nota, fechaNacimiento, promocion";
        var csv =new StringBuilder(encabezado);
        var csvLista = item.stream().map(this::toCsv).toList();
        for (String st : csvLista) {
            csv.append("\n");
            csv.append(st);
        }
        try (var fileWriter = new FileWriter(path.toFile())){
            fileWriter.write(csv.toString());
            return true;
        } catch (Exception e) {
            System.out.println("Error escribiendo en la base de datos");
        }
        return false;
    }

    @Override
    public List<Alumno> load(Path Path) throws IOException {
        try {
            return Files.lines(Path).skip(1).map(this::parse).toList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    private Alumno parse(String linea){
        var filds = linea.split(String.valueOf(','));
        var id = Integer.parseInt(filds[0]);
        var dni = filds[1];
        var nombreApellidos = filds[2];
        var nota = Double.parseDouble(filds[3]);
        var fechaNacimiento = LocalDate.parse(filds[4]);
        var promocion =PROMOCION.valueOf(filds[5]);
        return new Alumno(id, dni, nombreApellidos, nota, fechaNacimiento, promocion);
    }

    private String toCsv(Alumno dto){
        return dto.getID() + ";" +
                dto.getDni() + ";" +
                dto.getNombreApellidos() + ";" +
                dto.getNota() + ";" +
                dto.getFechaNacimiento()
                + ";" + dto.getPromociona();
    }
}
