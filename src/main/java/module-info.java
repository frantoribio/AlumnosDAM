module com.drodriguez.es.alumnosdam {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.drodriguez.es.alumnosdam to javafx.fxml;
    exports com.drodriguez.es.alumnosdam;
}