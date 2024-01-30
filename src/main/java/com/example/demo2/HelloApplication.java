package com.example.demo2;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader= new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = null;

        try {
            scene= new Scene(fxmlLoader.load(), 1800, 900);
        }

        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        stage.setScene(scene);
        stage.show();

        HelloController helloController = fxmlLoader.getController();
        helloController.initializeSerial();

    }

    public static void main(String[] args) {
        launch();
    }
}