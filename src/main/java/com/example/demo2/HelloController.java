package com.example.demo2;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import javafx.application.Platform;
import javafx.fxml.FXML;

public class HelloController {

    //komentar

    String message;

    @FXML
    public javafx.scene.control.Label defaultLabel;
    @FXML
    public javafx.scene.control.Button restartBtn;

    @FXML
    protected void onRestartButtonClick() {
        defaultLabel.setText("");
        message = "";
    }

    public void initializeSerial()
    {
        message = "";
        SerialPort serialPort = SerialPort.getCommPort("COM6");
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
                    if(message.isEmpty())
                    {
                        if(!data.contains("Start"))
                        {
                            message=data;
                            try {
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (data.contains("BTN0"))
                                            HelloController.this.writeToLabel("Tim 1");
                                        else if (data.contains("BTN1"))
                                            HelloController.this.writeToLabel("Tim 2");
                                        else if (data.contains("BTN2"))
                                            HelloController.this.writeToLabel("Tim 3");
                                        else if (data.contains("BTN3"))
                                            HelloController.this.writeToLabel("Tim 4");
                                        else if (data.contains("BTN4"))
                                            HelloController.this.writeToLabel("Tim 5");
                                    }
                                });
                            }
                            catch (Exception e)
                            {
                                System.out.println(e.getMessage());
                            }
                        }
                        else
                        {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    HelloController.this.writeToLabel(data);
                                }
                            });
                        }
                    }
                }
            }
        });
    }

    public void writeToLabel(String labelMessage)
    {
        defaultLabel.setText(labelMessage);

    }
}