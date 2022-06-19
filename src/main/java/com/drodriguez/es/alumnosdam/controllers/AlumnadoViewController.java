package com.drodriguez.es.alumnosdam.controllers;

import com.drodriguez.es.alumnosdam.managers.DataBaseManager;
import com.drodriguez.es.alumnosdam.models.Alumno;
import com.drodriguez.es.alumnosdam.models.PROMOCION;
import com.drodriguez.es.alumnosdam.repositories.AlumnosRepository;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

public class AlumnadoViewController {

    AlumnosRepository alumnosRepository = new AlumnosRepository(DataBaseManager.getInstance());
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
        loadData();
    }catch (Exception e){
        e.printStackTrace();
    }
        dniData.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDni()));
        nombreApellidosData.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombreApellidos()));
        notaData.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getNota()).asObject());
        promocionData.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPromociona()));
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
        alert.setTitle("¿Está seguro que desea cerrar ALumnosDAM?");
        alert.setContentText("¿Está seguro que desea cerrar ALumnosDAM?");
        Optional<ButtonType> resultado = alert.showAndWait();
        if (resultado.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    public void onImportarAction(ActionEvent actionEvent) {
    }

    public void onExportarAction(ActionEvent actionEvent) {
    }

    public void onPrintInformeAction(ActionEvent actionEvent) {
    }

    public void onRegistrarAlumnoAction(ActionEvent actionEvent) {
    }

    public void onEliminarAlumnoAction(ActionEvent actionEvent) {
    }

    public void onActualizarAlumnoAction(ActionEvent actionEvent) {
    }

    public void onLimpiarCamposAction(ActionEvent actionEvent) {
    }
}
