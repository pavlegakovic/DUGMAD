package com.example.demo2;

import javafx.fxml.FXML;

public class HelloController {
    @FXML
    public javafx.scene.control.Label defaultLabel;
    @FXML
    public javafx.scene.control.Button restartBtn;

    @FXML
    protected void onRestartButtonClick() {
        defaultLabel.setText("");
    }

    public void writeToLabel(String labelMessage)
    {
        defaultLabel.setText(labelMessage);

    }
}