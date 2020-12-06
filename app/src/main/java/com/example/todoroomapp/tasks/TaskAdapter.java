package com.example.todoroomapp.tasks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoroomapp.R;
import com.example.todoroomapp.model.Task;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> tasks;

    public interface MyTaskAdapterListener {
        void onCheckClicked(int pos, boolean type);
    }

    private MyTaskAdapterListener listener;

    public void setListener(MyTaskAdapterListener listener) {
        this.listener = listener;
    }

    public TaskAdapter(List<Task> tasks) {
        this.tasks = tasks;
    }

    //inner class
    public class TaskViewHolder extends RecyclerView.ViewHolder {

        CheckBox isCompleteCb;
        TextView contentTv;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            isCompleteCb = itemView.findViewById(R.id.cell_checkbox);
            contentTv = itemView.findViewById(R.id.cell_task_content_tv);

            isCompleteCb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onCheckClicked(getAdapterPosition(), false);
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_cell, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.contentTv.setText(task.getContent());
        holder.isCompleteCb.setChecked(task.getComplete());
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }
}
