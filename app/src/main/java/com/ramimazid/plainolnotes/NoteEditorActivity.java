package com.ramimazid.plainolnotes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

import com.ramimazid.plainolnotes.data.NoteItem;

/**
 * Created by Rami on 5/13/2015.
 */
public class NoteEditorActivity extends Activity {
    private NoteItem note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);
        //Function that causes the home button to act as an "up one level" button
        getActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = this.getIntent();
        note = new NoteItem();
        note.setText(intent.getStringExtra("text"));
        note.setKey(intent.getStringExtra("key"));

        //
        EditText et = (EditText) findViewById(R.id.noteText);
        et.setText(note.getText());
        et.setSelection(note.getText().length());
    }

    //Function that saves the text in the current activity
    private void saveAndFinish() {
        EditText et = (EditText) findViewById(R.id.noteText);
        String noteText = et.getText().toString();

        Intent intent = new Intent();
        intent.putExtra("key", note.getKey());
        intent.putExtra("text", noteText);
        setResult(RESULT_OK, intent);
        finish();
    }

    //Call saveAndFinish() when pressing the action bar back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            saveAndFinish();
        }
        return false;
    }

    //Call saveAndFinish() when pressing the hardware back button
    @Override
    public void onBackPressed() {
        saveAndFinish();
    }
}
