package com.example.testtodedo;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testtodedo.Adapter.ToDeDoAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.testtodedo.R; // Ensure this import is present

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ToDeDoAdapter toDeDoAdapter;
    private List<String> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.tasksRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        taskList = new ArrayList<>();
        // Add initial tasks to the list
        taskList.add("Task 1");
        taskList.add("Task 2");
        taskList.add("Task 3");

        toDeDoAdapter = new ToDeDoAdapter(taskList);
        recyclerView.setAdapter(toDeDoAdapter);

        Button addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddTaskDialog();
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_tasks:
                        // Already on tasks activity
                        return true;
                    case R.id.nav_settings:
                        startActivity(new Intent(MainActivity.this, UserActivity.class));
                        return true;
                    case R.id.nav_info:
                        startActivity(new Intent(MainActivity.this, DeveloperActivity.class));
                        return true;
                }
                return false;
            }
        });

        // Set the selected item to Tasks
        bottomNavigationView.setSelectedItemId(R.id.nav_tasks);
    }

    private void showAddTaskDialog() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_task_dialog_box);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = android.view.Gravity.CENTER;

        dialog.getWindow().setAttributes(layoutParams);

        EditText taskEditText = dialog.findViewById(R.id.taskInput);
        Button cancelButton = dialog.findViewById(R.id.cancelButton);
        Button okButton = dialog.findViewById(R.id.okButton);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String task = taskEditText.getText().toString().trim();
                if (!task.isEmpty()) {
                    taskList.add(task);
                    toDeDoAdapter.notifyItemInserted(taskList.size() - 1);
                    dialog.dismiss();
                } else {
                    Toast.makeText(MainActivity.this, "Please enter a task", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();
    }
}









