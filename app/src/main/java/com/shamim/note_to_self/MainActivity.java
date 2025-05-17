package com.shamim.note_to_self;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;


import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    private List<Note> noteList = new ArrayList<>();
    private RecyclerView recyclerView;
    private NoteAdapter mAdapter;
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "NoteToSelfPrefs";
    private static final String SHOW_DIVIDER = "show_divider";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadNotes();


        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogNewNote dialog = new DialogNewNote();
                dialog.show(getSupportFragmentManager(), "");
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        mAdapter = new NoteAdapter(this, noteList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        boolean showDivider = sharedPreferences.getBoolean(SHOW_DIVIDER, true);
        if (showDivider) {
            recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        }

        recyclerView.setAdapter(mAdapter);
    }

    public void createNewNote(Note n) {
        noteList.add(n);
        mAdapter.notifyDataSetChanged();
        saveNotes();
    }

    public void showNote(int noteToShow) {
        DialogShowNote dialog = new DialogShowNote();
        dialog.sendNoteSelected(noteList.get(noteToShow));
        dialog.show(getSupportFragmentManager(), "");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private static final String NOTES_FILE = "notes.json";

    /** Serializes the current noteList to a JSON array and writes it to internal storage */
    private void saveNotes() {
        JSONArray array = new JSONArray();
        try {
            for (Note note : noteList) {
                JSONObject obj = new JSONObject();
                obj.put("title",       note.getTitle());
                obj.put("description", note.getDescription());
                obj.put("idea",        note.isIdea());
                obj.put("todo",        note.isTodo());
                obj.put("important",   note.isImportant());
                array.put(obj);
            }
            String data = array.toString();
            OutputStream os = openFileOutput(NOTES_FILE, MODE_PRIVATE);
            os.write(data.getBytes());
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** Reads the JSON file from internal storage and repopulates noteList */
    private void loadNotes() {
        noteList.clear();
        try {
            InputStream is = openFileInput(NOTES_FILE);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String data = new String(buffer);
            JSONArray array = new JSONArray(data);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                Note note = new Note();
                note.setTitle(      obj.getString("title"));
                note.setDescription(obj.getString("description"));
                note.setIdea(       obj.getBoolean("idea"));
                note.setTodo(       obj.getBoolean("todo"));
                note.setImportant(  obj.getBoolean("important"));
                noteList.add(note);
            }
        } catch (IOException e) {
            // Happens first time when file doesn't exist—ignore
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
