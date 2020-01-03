package com.aakriti.taskmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aakriti.taskmanager.api.TaskAPI;
import com.aakriti.taskmanager.url.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTaskActivity extends AppCompatActivity {
    Button btnAddNotes;
    EditText etNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        btnAddNotes = findViewById(R.id.btnAddNote);
        etNote = findViewById(R.id.etNote);

        btnAddNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNotes();
            }
        });
    }

    private void addNotes() {
        //Task notes = new Task(etNote.getText().toString());

        TaskAPI noteAPI = URL.getInstance().create(TaskAPI.class);

        Call<Void> voidCall = noteAPI.addTask(URL.token,etNote.getText().toString());

        voidCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(AddTaskActivity.this, "Code : " + response.code() + ", Message : " + response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(AddTaskActivity.this, "Added", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(AddTaskActivity.this, "Error " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}
