module com.drodriguez.es.alumnosdam {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires java.sql;


    opens com.drodriguez.es.alumnosdam to javafx.fxml;
    exports com.drodriguez.es.alumnosdam;

    opens com.drodriguez.es.alumnosdam.controllers to javafx.fxml;
    exports com.drodriguez.es.alumnosdam.controllers;
}