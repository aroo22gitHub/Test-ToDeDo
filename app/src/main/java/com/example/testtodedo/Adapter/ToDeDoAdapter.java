package com.example.testtodedo.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testtodedo.R;

import java.util.List;

public class ToDeDoAdapter extends RecyclerView.Adapter<ToDeDoAdapter.TaskViewHolder> {

    private List<String> taskList;
    private Context context;

    public ToDeDoAdapter(List<String> taskList) {
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        String task = taskList.get(position);
        holder.taskName.setText(task);

        holder.checkBox.setOnClickListener(v -> holder.checkBox.setChecked(!holder.checkBox.isChecked()));

        holder.editButton.setOnClickListener(v -> showEditTaskDialog(holder.getAdapterPosition()));

        holder.deleteButton.setOnClickListener(v -> {
            taskList.remove(holder.getAdapterPosition());
            notifyItemRemoved(holder.getAdapterPosition());
            notifyItemRangeChanged(holder.getAdapterPosition(), taskList.size());
        });
    }

    private void showEditTaskDialog(int position) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_task_dialog_box);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = android.view.Gravity.CENTER;

        dialog.getWindow().setAttributes(layoutParams);

        EditText taskEditText = dialog.findViewById(R.id.taskInput);
        taskEditText.setText(taskList.get(position));
        Button cancelButton = dialog.findViewById(R.id.cancelButton);
        Button okButton = dialog.findViewById(R.id.okButton);

        cancelButton.setOnClickListener(v -> dialog.dismiss());

        okButton.setOnClickListener(v -> {
            String editedTask = taskEditText.getText().toString().trim();
            if (!editedTask.isEmpty()) {
                taskList.set(position, editedTask);
                notifyItemChanged(position);
                dialog.dismiss();
            } else {
                Toast.makeText(context, "Please enter a task", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {

        public TextView taskName;
        public CheckBox checkBox;
        public ImageButton editButton, deleteButton;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            taskName = itemView.findViewById(R.id.item_text);
            checkBox = itemView.findViewById(R.id.item_checkbox);
            editButton = itemView.findViewById(R.id.item_edit);
            deleteButton = itemView.findViewById(R.id.item_delete);
        }
    }
}



