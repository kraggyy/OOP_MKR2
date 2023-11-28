module com.example.module {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.knowm.xchart;


    opens com.example.module to javafx.fxml;
    exports com.example.module;
}