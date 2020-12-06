package com.example.todoroomapp.tasks;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
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
    private Context context;

    public interface MyTaskAdapterListener {
        void onCheckClicked(int pos);
    }

    private MyTaskAdapterListener listener;

    public void setListener(MyTaskAdapterListener listener) {
        this.listener = listener;
    }

    public TaskAdapter(List<Task> tasks, Context context) {
        this.tasks = tasks;
        this.context = context;
    }

    //inner class
    public class TaskViewHolder extends RecyclerView.ViewHolder {

        CheckBox isCompleteCb;
        TextView contentTv;
        int markColor;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            isCompleteCb = itemView.findViewById(R.id.cell_checkbox);
            contentTv = itemView.findViewById(R.id.cell_task_content_tv);

            markColor = context.getResources().getColor(R.color.marked);

            isCompleteCb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onCheckClicked(getAdapterPosition());
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

        if(task.getComplete()) {
            holder.contentTv.setPaintFlags(holder.contentTv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.contentTv.setTextColor(holder.markColor);
        }

    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }
}
