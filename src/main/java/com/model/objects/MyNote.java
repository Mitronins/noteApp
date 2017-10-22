package com.model.objects;

import javafx.beans.property.SimpleStringProperty;

public class MyNote {

    private SimpleStringProperty dateNote = new SimpleStringProperty("");
    private SimpleStringProperty textNote = new SimpleStringProperty("");

    public MyNote(String dateNote, String textNote) {
        this.dateNote = new SimpleStringProperty(dateNote);
        this.textNote = new SimpleStringProperty(textNote);
    }

    public MyNote() {
    }

    public String getDateNote() {
        return dateNote.get();
    }

    public void setDateNote(String textNote) {
        this.dateNote.set(textNote);
    }
    public String getTextNote() {
        return textNote.get();
    }

    public void setTextNote(String textNote) {
        this.textNote.set(textNote);
    }

    public SimpleStringProperty dateNoteProperty() {
        return dateNote;
    }

    public SimpleStringProperty textNoteProperty() {
        return textNote;
    }

    @Override
    public String toString() {
        return "MyNote{" +
                "dateNote=" + dateNote +
                ", textNote=" + textNote +
                '}';
    }
}
