package com.example.todoroomapp.tasks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoroomapp.R;
import com.example.todoroomapp.model.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TasksFragment extends Fragment implements TaskAdapter.MyTaskAdapterListener, DoneAdapter.MyDoneAdapterListener {

    private TaskViewModel viewModel;

    //todo list
    private List<Task> taskList = new ArrayList<>();
    private List<Task> doneList = new ArrayList<>();
    private RecyclerView tasksRecycler;
    private RecyclerView doneRecycler;
    private TaskAdapter taskAdapter;
    private DoneAdapter doneAdapter;
    private TextView doneTv;
    private boolean isShown = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getActivity() != null) {
            viewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(TaskViewModel.class);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_fragment, container, false);

        //find recycler view
        tasksRecycler = rootView.findViewById(R.id.tasks_recycler);
        doneRecycler = rootView.findViewById(R.id.done_recycler);
        doneTv = rootView.findViewById(R.id.done_tv);

        doneTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isShown = !isShown;
                if(!isShown) {
                    doneRecycler.setVisibility(View.GONE);
                } else {
                    doneRecycler.setVisibility(View.VISIBLE);
                }
            }
        });

        //Observe changes on the tasks list
        viewModel.getTasks().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                if (!tasks.isEmpty()) {
                    showList(tasks);
                }
            }
        });

        return rootView;
    }

    @Override
    public void onCheckClicked(int pos, boolean type) {

        Task task;
        if (!type) { //transform to DONE
            task = taskList.get(pos);
        } else { //transform to UNDONE
            task = doneList.get(pos);
        }

        taskAdapter.notifyItemMoved(pos,taskList.size() - 1);
        //swap in real list

        /*if (task.getComplete()) { //make it NOT DONE
            task.setComplete(false);
            doneList.add(0,task);
            taskList.remove(task);
            taskAdapter.notifyItemRemoved(pos);
            doneAdapter.notifyItemInserted(0);
            viewModel.updateTask(task);
        } else { //make it DONE
            task.setComplete(true);
            taskList.add(0,task);
            doneList.remove(task);
            taskAdapter.notifyItemInserted(0);
            doneAdapter.notifyItemRemoved(pos);
            viewModel.updateTask(task);
        }*/
    }

    private void showList(List<Task> tasks) {

        //separate done and undone
        Collections.reverse(tasks);
        taskList.clear();
        doneList.clear();

        for (Task task : tasks) {
            if (!task.getComplete()) {
                taskList.add(task);
            } else {
                doneList.add(task);
            }
        }

        //recyclers
        tasksRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        tasksRecycler.setHasFixedSize(true);
        tasksRecycler.setNestedScrollingEnabled(false);
        doneRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        doneRecycler.setHasFixedSize(true);
        doneRecycler.setNestedScrollingEnabled(false);

        //adapter
        taskAdapter = new TaskAdapter(taskList);
        taskAdapter.setListener(this);
        doneAdapter = new DoneAdapter(doneList);
        doneAdapter.setListener(this);
        tasksRecycler.setAdapter(taskAdapter);
        doneRecycler.setAdapter(doneAdapter);
    }
}
