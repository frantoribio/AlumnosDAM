package com.drodriguez.es.alumnosdam.controllers;

import com.drodriguez.es.alumnosdam.managers.DataBaseManager;
import com.drodriguez.es.alumnosdam.models.Alumno;
import com.drodriguez.es.alumnosdam.models.PROMOCION;
import com.drodriguez.es.alumnosdam.repositories.AlumnosRepository;
import com.drodriguez.es.alumnosdam.services.CSVStorage;
import com.drodriguez.es.alumnosdam.services.ICSVStorage;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Array;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

public class AlumnadoViewController {

    AlumnosRepository alumnosRepository = new AlumnosRepository(DataBaseManager.getInstance());
    private ObservableList<Alumno>alumnosList;
    private ICSVStorage csvStorage = new CSVStorage();
@FXML
private Label idData;
@FXML
    TableView<Alumno> alumnoTable;
@FXML
    TableColumn<Alumno, String> dniData;
@FXML
    TableColumn<Alumno, String>nombreApellidosData;
@FXML
    TableColumn<Alumno, Double> notaData;
@FXML
    TableColumn<Alumno, PROMOCION> promocionData;
@FXML
    TextField dniLabelData;
@FXML
    TextField nombreApellidosLabelData;
@FXML
    TextField notaLabelData;
@FXML
    DatePicker fechaNacimientoLabelData;
@FXML
    ComboBox<PROMOCION> promocionLabelData;


@FXML
    private void initialize() {
    try {
        System.out.println("Cargando datos");
        loadComboBox();
        alumnoTable.setItems(alumnosRepository.findAll());
    }catch (Exception e){
        e.printStackTrace();
    }
        dniData.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDni()));
        nombreApellidosData.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombreApellidos()));
        notaData.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getNota()).asObject());
        promocionData.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPromociona()));
        alumnoTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> alumnoSeleccionado(newValue));
        vaciarCampos();
}

    private void loadComboBox() {
        promocionLabelData.setItems(FXCollections.observableArrayList(PROMOCION.PROMOCIONA, PROMOCION.NO_PROMOCIONA));
    }

    private void loadData() throws SQLException {
        System.out.println("Obteniendo datos de la base de datos");
        alumnoTable.setItems(alumnosRepository.findAll());
    }

    public void onCerrarAplicacion(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("¿Está seguro que desea cerrar AlumnosDAM?");
        alert.setContentText("¿Está seguro que desea cerrar AlumnosDAM?");
        Optional<ButtonType> resultado = alert.showAndWait();
        if (resultado.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    public void onImportarAction(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Importar alumnos");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV", "*.csv"));
        Path path = fileChooser.showOpenDialog(idData.getScene().getWindow()).toPath();
            try {
                alumnosRepository.loadFromCSV(path);
                alumnoTable.setItems(alumnosRepository.findAll());
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }


    public void onExportarAction(ActionEvent actionEvent) {
    }

    public void onPrintInformeAction(ActionEvent actionEvent) {
    }

    public void onRegistrarAlumnoAction(ActionEvent actionEvent) throws SQLException {
        try {
            alumnosRepository.save(
                    new Alumno(
                            dniLabelData.getText(),
                            nombreApellidosLabelData.getText(),
                            LocalDate.parse(fechaNacimientoLabelData.getValue().toString()),
                            Double.parseDouble(notaLabelData.getText()),
                            promocionLabelData.getValue()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        alumnoTable.setItems(alumnosRepository.findAll());
    }

    public void onEliminarAlumnoAction(ActionEvent actionEvent) throws SQLException {
    Alumno alumno = alumnoTable.getSelectionModel().getSelectedItem();
        try {
            alumnosRepository.delete(alumno);
        } catch (Exception e) {
            e.printStackTrace();
        }
        alumnoTable.setItems(alumnosRepository.findAll());

    }

    public void onActualizarAlumnoAction(ActionEvent actionEvent) throws SQLException {
        Alumno alumno = alumnoTable.getSelectionModel().getSelectedItem();
        alumno.setDni(dniLabelData.getText());
        alumno.setNombreApellidos(nombreApellidosLabelData.getText());
        alumno.setFechaNacimiento(LocalDate.parse(fechaNacimientoLabelData.getValue().toString()));
        alumno.setNota(Double.parseDouble(notaLabelData.getText()));
        alumno.setPromociona(promocionLabelData.getValue());
        try {
            alumnosRepository.update(alumno.getID(), alumno);
        } catch (Exception e) {
            e.printStackTrace();
        }
        alumnoTable.setItems(alumnosRepository.findAll());
    }


    public void onLimpiarCamposAction(ActionEvent actionEvent) {
        vaciarCampos();

    }

    private void vaciarCampos(){
        dniLabelData.setText("");
        nombreApellidosLabelData.setText("");
        notaLabelData.setText("");
        fechaNacimientoLabelData.setValue(null);
        promocionLabelData.setValue(null);
    }

    private void setData(Alumno alumno){
        dniLabelData.setText(alumno.getDni());
        nombreApellidosLabelData.setText(alumno.getNombreApellidos());
        notaLabelData.setText(String.valueOf(alumno.getNota()));
        fechaNacimientoLabelData.setValue(alumno.getFechaNacimiento());
        promocionLabelData.setValue(alumno.getPromociona());
    }

    private void alumnoSeleccionado(Alumno alumno){
    if(alumno != null){
        setData(alumno);
    }else {
        vaciarCampos();
        }
    }
}
