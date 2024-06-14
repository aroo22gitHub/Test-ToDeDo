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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserActivity extends AppCompatActivity {

    private TextView usernameTextView;
    private TextView emailTextView;
    private SharedPreferencesHelper sharedPreferencesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        usernameTextView = findViewById(R.id.username);
        emailTextView = findViewById(R.id.email);
        sharedPreferencesHelper = new SharedPreferencesHelper(this);

        NewUser user = sharedPreferencesHelper.getUser();
        if (user != null) {
            usernameTextView.setText(user.getFullName());
            emailTextView.setText(user.getEmail());
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_tasks:
                        startActivity(new Intent(UserActivity.this, MainActivity.class));
                        return true;
                    case R.id.nav_settings:
                        // Already on user activity
                        return true;
                    case R.id.nav_info:
                        startActivity(new Intent(UserActivity.this, DeveloperActivity.class));
                        return true;
                }
                return false;
            }
        });

        // Set the selected item to Settings
        bottomNavigationView.setSelectedItemId(R.id.nav_settings);

        // Edit Button Click Listener
        Button editButton = findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog();
            }
        });

        // Sign Out Button Click Listener
        Button signOutButton = findViewById(R.id.signOutButton);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSignOutDialog();
            }
        });
    }

    private void showEditDialog() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.edit_dialogbox);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = android.view.Gravity.CENTER;

        dialog.getWindow().setAttributes(layoutParams);

        EditText editUsername = dialog.findViewById(R.id.editUsername);
        EditText editEmail = dialog.findViewById(R.id.editEmail);
        Button cancelButton = dialog.findViewById(R.id.cancelButton);
        Button okButton = dialog.findViewById(R.id.okButton);

        // Set current values
        editUsername.setText(usernameTextView.getText().toString());
        editEmail.setText(emailTextView.getText().toString());

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle OK button click event
                String newUsername = editUsername.getText().toString().trim();
                String newEmail = editEmail.getText().toString().trim();
                if (!newUsername.isEmpty() && !newEmail.isEmpty()) {
                    usernameTextView.setText(newUsername);
                    emailTextView.setText(newEmail);

                    NewUser updatedUser = new NewUser(newUsername, newEmail, sharedPreferencesHelper.getUser().getPassword());
                    sharedPreferencesHelper.saveUser(updatedUser);

                    dialog.dismiss();
                } else {
                    Toast.makeText(UserActivity.this, "Please enter both username and email", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();
    }

    private void showSignOutDialog() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.signout_dialogbox);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = android.view.Gravity.CENTER;

        dialog.getWindow().setAttributes(layoutParams);

        Button cancelSignOutButton = dialog.findViewById(R.id.cancelButton);
        Button okSignOutButton = dialog.findViewById(R.id.okButton);

        cancelSignOutButton.setOnClickListener(v -> dialog.dismiss());

        okSignOutButton.setOnClickListener(v -> {
            sharedPreferencesHelper.clearUserData();
            dialog.dismiss();
            Intent intent = new Intent(UserActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        dialog.show();
    }
}




