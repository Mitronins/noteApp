package com.controller;

import com.model.DbHandler;
import com.model.objects.MyNote;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;


public class EditController {
    @FXML
    public TextArea textArea;

    @FXML
    public TextField textDate;


    public void addToDB(ActionEvent actionEvent) throws SQLException {
        DbHandler dbHandler = DbHandler.getInstance();
        MyNote myNote = new MyNote(textDate.getText(), textArea.getText());
        new Thread(() -> dbHandler.addNote(myNote)).start();

        actionClose(actionEvent);
    }

    public void setDate() {
        Date date = new Date();
        Format formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        String s = formatter.format(date);
        textDate.setText(s);
    }

    public void actionClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }

    @FXML
    private void initialize() {
        setDate();
        addTextLimiter(textArea, 100);
    }

    //ограничение на ввод в textArea
    public void addTextLimiter(final TextArea textArea, final int maxLength) {
        textArea.textProperty().addListener((ov, oldValue, newValue) -> {
            if (textArea.getText().length() > maxLength) {
                String s = textArea.getText().substring(0, maxLength);
                textArea.setText(s);
            }
        });
    }
}
