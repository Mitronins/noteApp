package com.main;

import com.model.DbHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
        primaryStage.setTitle("Notes");
        primaryStage.setMinHeight(500);
        primaryStage.setMinWidth(500);
        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.show();
    }


    public static void main(String[] args) throws SQLException, IOException {
        createDB();
        DbHandler dbHandler = DbHandler.getInstance();
        launch(args);
    }

    public static void createDB() {
        File folder = new File("resources");
        if (!folder.exists()) {
            folder.mkdir();
        }
        File file = new File("."+ File.separator+"resources" + File.separator + "notedb.sqlite");
        try {
            if(file.createNewFile()){
                System.out.println("file db has been created");
                try {
                    DbHandler dbHandler = DbHandler.getInstance();
                    dbHandler.createDB();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }else System.out.println("db already exist");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
