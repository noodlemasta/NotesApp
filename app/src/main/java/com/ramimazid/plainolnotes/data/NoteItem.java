package com.ramimazid.plainolnotes.data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Rami on 5/13/2015.
 */
//Class called NoteItem which stores the data for a single note using a key to identify the note and text to hold the contents of the note
public class NoteItem {
    //Private data of key and text
    private String key;
    private String text;
    //Getters
    public String getKey() {
        return key;
    }
    public String getText() {
        return text;
    }
    //Setters
    public void setKey(String key) {
        this.key = key;
    }
    public void setText(String text) {
        this.text = text;
    }
    //Method that creates a unique key for a note based on the date and time
    public static NoteItem getNew() {
        //Locale sets the date to US format?
        Locale locale = new Locale("en_US");
        Locale.setDefault(locale);

        //SimpleDateFormat is a class that has the date and time values
        String pattern = "yyyy-MM-dd HH:mm:ss Z";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        //Date and time values stored as a string
        String key = formatter.format(new Date());

        //Create new NoteItem with newly created key and set default text to no character
        //Return this newly created note
        NoteItem note = new NoteItem();
        note.setKey(key);
        note.setText("");
        return note;
    }

    @Override
    public String toString() {
        return this.getText();
    }
}
