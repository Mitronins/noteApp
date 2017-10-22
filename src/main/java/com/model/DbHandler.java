package com.model;

import com.model.objects.MyNote;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.sqlite.JDBC;

import java.sql.*;

public class DbHandler {

    // Константа, в которой хранится адрес подключения

    private static final String CON_STR = "jdbc:sqlite:./resources/notedb.sqlite";

    // Объект, в котором будет храниться соединение с БД
    private Connection connection;

    // Используем singleton
    private static DbHandler instance = null;

    public static synchronized DbHandler getInstance() throws SQLException {
        if (instance == null)
            instance = new DbHandler();
        return instance;
    }


    private DbHandler() throws SQLException {
        DriverManager.registerDriver(new JDBC());
        this.connection = DriverManager.getConnection(CON_STR);
    }

    public ObservableList<MyNote> getAllNotes() {
        try (Statement statement = this.connection.createStatement()) {
            ObservableList<MyNote> listNotes = FXCollections.observableArrayList();
            ResultSet resultSet = statement.executeQuery("SELECT date, text_note FROM notes");
            while (resultSet.next()) {
                MyNote myNote = new MyNote(resultSet.getString("date"),resultSet.getString("text_note"));
                listNotes.add(myNote);
            }
            return listNotes;

        } catch (SQLException e) {
            e.printStackTrace();
            // Если произошла ошибка - возвращаем пустую коллекцию
            return FXCollections.emptyObservableList();
        }
    }

    public void addNote(MyNote note) {
        try (PreparedStatement statement = this.connection.prepareStatement(
                "INSERT INTO notes('date', 'text_note') " +
                        "VALUES(?, ?)")) {
            statement.setObject(1, note.getDateNote());
            statement.setObject(2, note.getTextNote());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createDB() throws ClassNotFoundException, SQLException
    {
        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE if not exists 'notes' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'date' text, 'text_note' text);");
        System.out.println("Table has been created or already exist.");
    }
}