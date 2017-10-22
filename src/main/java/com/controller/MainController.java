package com.controller;

import com.model.DbHandler;
import com.model.objects.MyNote;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class MainController {

    private static ObservableList<MyNote> list;
    @FXML
    public TableView tableView;
    @FXML
    public VBox vbox;
    @FXML
    private TableColumn<MyNote, String> columnText;
    @FXML
    private TableColumn<MyNote, String> columnDate;

    private Parent root;


    public void showDialog(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            root = FXMLLoader.load(getClass().getResource("/fxml/editDialog.fxml"));

            stage.setTitle("Add note");
            stage.setMinWidth(300);
            stage.setMinHeight(300);
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void initialize(){
        try {
            displayTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void refreshNotes(ActionEvent actionEvent) throws SQLException {
        displayTable();
    }

    public void displayTable() throws SQLException {
        columnDate.setCellValueFactory(new PropertyValueFactory<>("dateNote"));
        columnText.setCellValueFactory(new PropertyValueFactory<>("textNote"));
        DbHandler dbHandler = DbHandler.getInstance();
        list = FXCollections.observableArrayList();
        Thread thread = new Thread(() -> list = dbHandler.getAllNotes());
        try {
            thread.start();
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        tableView.setItems(list);
    }
}
