package com.ramimazid.plainolnotes.data;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by Rami on 5/13/2015.
 */
public class NotesDataSource {

    private static final String PREFKEY = "notes";
    private SharedPreferences notePrefs;

    public NotesDataSource(Context context) {
        notePrefs = context.getSharedPreferences(PREFKEY, Context.MODE_PRIVATE);
    }

    //Class that returns a list of all the notes
    public List<NoteItem> findAll() {

        Map<String, ?> notesMap = notePrefs.getAll();

        //keySet() returns a listing of all the keys with no order
        //TreeSet sorts the keys, and this is stored in SortedSet
        SortedSet<String> keys = new TreeSet<String>(notesMap.keySet());

        //Create a list of notes
        //Iterate through the SortedSet of keys and add the key:text combo into noteList
        List<NoteItem> noteList = new ArrayList<NoteItem>();
        for (String key : keys) {
            NoteItem note = new NoteItem();
            note.setKey(key);
            note.setText((String) notesMap.get(key));
            noteList.add(note);
        }

//        NoteItem note = NoteItem.getNew();
//        noteList.add(note);
        return noteList;
    }

    //Updates SharedPerferences by changing notePrefs when we create a new note or update a pre-existing note
    public boolean update(NoteItem note) {

        SharedPreferences.Editor editor = notePrefs.edit();
        editor.putString(note.getKey(), note.getText());
        editor.commit();
        return true;
    }

    //Removes a note if that note exists
    public boolean remove(NoteItem note) {

        if (notePrefs.contains(note.getKey())) {
            SharedPreferences.Editor editor = notePrefs.edit();
            editor.remove(note.getKey());
            editor.commit();
        }

        return true;
    }
}
