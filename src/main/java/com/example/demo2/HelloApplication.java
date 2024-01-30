package com.example.demo2;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import javafx.application.Application;
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
            scene= new Scene(fxmlLoader.load(), 600, 600);
        }

        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        stage.setScene(scene);
        stage.show();

        SerialPort serialPort = SerialPort.getCommPort("COM11");
        serialPort.setComPortParameters(9600,Byte.SIZE,SerialPort.ONE_STOP_BIT,SerialPort.NO_PARITY);
        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING,0,0);

        try {
            serialPort.openPort();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        serialPort.addDataListener(new SerialPortDataListener() {
            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
            }

            @Override
            public void serialEvent(SerialPortEvent serialPortEvent) {
                if(serialPortEvent.getEventType()==SerialPort.LISTENING_EVENT_DATA_RECEIVED)
                {
                    String data = new String(serialPortEvent.getReceivedData());
                    ((HelloController)fxmlLoader.getController()).writeToLabel(data);
                }
            }
        });

    }

    public static void main(String[] args) {
        launch();
    }
}