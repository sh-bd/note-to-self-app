package com.shamim.note_to_self;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private Switch switch1;
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "NoteToSelfPrefs";
    private static final String SHOW_DIVIDER = "show_divider";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        switch1 = findViewById(R.id.switch1);

        boolean showDivider = sharedPreferences.getBoolean(SHOW_DIVIDER, true);
        switch1.setChecked(showDivider);

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(SHOW_DIVIDER, isChecked);
                editor.apply();
            }
        });
    }
}
