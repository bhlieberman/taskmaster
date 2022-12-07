package com.example.taskmaster.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmaster.R;
import com.example.taskmaster.activities.MainActivity;
import com.example.taskmaster.models.Task;

import java.util.List;

public class TaskRecyclerViewViewAdapter extends RecyclerView.Adapter<TaskRecyclerViewViewAdapter.TaskViewHolder> {

    List<Task> tasks;
    Context callingActivity;

    public TaskRecyclerViewViewAdapter(List<Task> tasks, Context callingActivity) {
        this.tasks = tasks;
        this.callingActivity = callingActivity;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View taskFragment = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_task, parent, false);
        return new TaskViewHolder(taskFragment);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        TextView superPetFragmentTextViewName = holder.itemView.findViewById(R.id.TaskFragTVTitle);
        String taskName= tasks.get(position).getTitle();
        superPetFragmentTextViewName.setText(position + ". " + taskName);
        View superPetItemView = holder.itemView;
        superPetItemView.setOnClickListener(v -> {
            Intent goToAllTasksView = new Intent(callingActivity, MainActivity.class);
            goToAllTasksView.putExtra(MainActivity.TASK_NAME_TAG, taskName);
            callingActivity.startActivity(goToAllTasksView);
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }


    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        public TaskViewHolder(@NonNull View viewHolder) {
            super(viewHolder);
        }
    }
}
