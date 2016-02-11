package com.ramimazid.plainolnotes;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ramimazid.plainolnotes.data.NoteItem;
import com.ramimazid.plainolnotes.data.NotesDataSource;

import java.util.List;


public class MainActivity extends ListActivity {
    private static final int EDITOR_ACTIVITY_REQUEST = 1001;
    private static final int MENU_DELETE_ID = 1002;
    private int currentNoteId;
    //Declare private data of datasource
    private NotesDataSource datasource;
    List<NoteItem> notessList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerForContextMenu(getListView());

        //Instantiate datasource as a new NotesDataSource class
        datasource = new NotesDataSource(this);

        refreshDisplay();

//        For Testing Purposes only
//        //Create a local list of NoteItems to hold all the notes within datasource on startup
//        List<NoteItem> notes = datasource.findAll();
//        NoteItem note = notes.get(0);
//        note.setText("Updated");
//        datasource.update(note);
//
//        notes = datasource.findAll();
//        note = notes.get(0);
//
//        //Android log utility for debugging
//        Log.i("NOTES", note.getKey() + ": " + note.getText());
    }

    private void refreshDisplay() {
        notessList = datasource.findAll();
        ArrayAdapter<NoteItem> adapter = new ArrayAdapter<NoteItem>(this, R.layout.list_item_layout, notessList);
        setListAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_main,menu);
//        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_create) {
            createNote();

//            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void createNote() {
        NoteItem note = NoteItem.getNew();
        Intent intent = new Intent(this, NoteEditorActivity.class);
        intent.putExtra("key", note.getKey());
        intent.putExtra("text", note.getText());
        startActivityForResult(intent, EDITOR_ACTIVITY_REQUEST);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
//        super.onListItemClick(l, v, position, id);
        NoteItem note = notessList.get(position);
        Intent intent = new Intent(this, NoteEditorActivity.class);
        intent.putExtra("key", note.getKey());
        intent.putExtra("text", note.getText());
        startActivityForResult(intent, EDITOR_ACTIVITY_REQUEST);
    }

    //Update note after returning to the main activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EDITOR_ACTIVITY_REQUEST && resultCode == RESULT_OK) {
            NoteItem note = new NoteItem();
            note.setKey(data.getStringExtra("key"));
            note.setText(data.getStringExtra("text"));
            datasource.update(note);
            refreshDisplay();
        }
    }

    //Bring up context menu to delete a note
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        currentNoteId = (int) info.id;
        menu.add(0,MENU_DELETE_ID,0, "Delete");
    }

    //Delete function that comes up from context menu
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == MENU_DELETE_ID) {
            NoteItem note = notessList.get(currentNoteId);
            datasource.remove(note);
            refreshDisplay();
        }
        return super.onContextItemSelected(item);
    }
}
