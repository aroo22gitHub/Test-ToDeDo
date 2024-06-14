package com.example.testtodedo;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.testtodedo.Model.ToDeDoModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SharedPreferencesHelper {

    private static final String PREF_NAME = "ToDoAppPrefs";
    private static final String TASKS_KEY = "tasks";
    private static final String USER_KEY = "user";

    private SharedPreferences sharedPreferences;
    private Gson gson;

    public SharedPreferencesHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public void saveTasks(List<ToDeDoModel> taskList) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = gson.toJson(taskList);
        editor.putString(TASKS_KEY, json);
        editor.apply();
    }

    public List<ToDeDoModel> getTasks() {
        String json = sharedPreferences.getString(TASKS_KEY, null);
        Type type = new TypeToken<ArrayList<ToDeDoModel>>() {}.getType();
        List<ToDeDoModel> taskList = gson.fromJson(json, type);
        return taskList != null ? taskList : new ArrayList<ToDeDoModel>();
    }

    public void saveUser(NewUser user) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = gson.toJson(user);
        editor.putString(USER_KEY, json);
        editor.apply();
    }

    public NewUser getUser() {
        String json = sharedPreferences.getString(USER_KEY, null);
        Type type = new TypeToken<NewUser>() {}.getType();
        NewUser user = gson.fromJson(json, type);
        return user != null ? user : new NewUser();
    }

    public void clearUserData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(USER_KEY);
        editor.remove(TASKS_KEY);
        editor.apply();
    }
}


