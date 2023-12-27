module com.charan.attendance_fetcher {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;

    opens com.charan.attendance_fetcher to javafx.fxml;
    exports com.charan.attendance_fetcher;
}