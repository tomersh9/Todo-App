package com.example.todoroomapp.tasks;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoroomapp.R;
import com.example.todoroomapp.SoundManager;
import com.example.todoroomapp.model.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;

public class TasksFragment extends Fragment implements TaskAdapter.MyTaskAdapterListener {

    private TaskViewModel viewModel;

    //todo list
    private List<Task> taskList = new ArrayList<>();
    private RecyclerView tasksRecycler;
    private TaskAdapter taskAdapter;

    private SoundManager soundManager;

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

        soundManager = new SoundManager(getActivity());

        //find recycler view
        tasksRecycler = rootView.findViewById(R.id.tasks_recycler);

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
    public void onCheckClicked(int pos) {
        final Task task = taskList.get(pos);
        task.setComplete(!task.getComplete());
        int lastIndex = taskList.size() - 1;
        if (task.getComplete()) {
            taskAdapter.notifyItemMoved(pos, lastIndex);
            Collections.swap(taskList, pos, lastIndex);
            soundManager.playFinishSfx();
        } else {
            soundManager.playUndoneSfx();
        }
        viewModel.updateTask(task);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        soundManager.stopSoundPool();
    }

    private void showList(List<Task> tasks) {

        //clean and re-add all tasks when changed
        taskList.clear();
        Collections.reverse(tasks);
        taskList.addAll(tasks);

        //recyclers
        tasksRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        tasksRecycler.setHasFixedSize(true);
        tasksRecycler.setNestedScrollingEnabled(false);

        //adapter
        taskAdapter = new TaskAdapter(taskList, getActivity());
        taskAdapter.setListener(this);
        tasksRecycler.setAdapter(taskAdapter);
    }
}
