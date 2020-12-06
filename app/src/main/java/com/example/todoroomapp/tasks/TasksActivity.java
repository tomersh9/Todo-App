package com.example.todoroomapp.tasks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.todoroomapp.R;
import com.example.todoroomapp.model.Task;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TasksActivity extends AppCompatActivity {

    private AddTaskViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //views
        Toolbar toolbar = findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar_layout);
        CoordinatorLayout coordinatorLayout = findViewById(R.id.coordinator_layout);
        final FloatingActionButton fab = findViewById(R.id.add_fab);

        //show fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new TasksFragment()).commit();

        //assign view model
        viewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(AddTaskViewModel.class);

        //listeners
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(TasksActivity.this, R.style.BottomSheetDialogTheme);
                View bottomSheetView = LayoutInflater.from(TasksActivity.this).inflate(R.layout.bottom_sheet_add_task, null);

                final EditText editText = bottomSheetView.findViewById(R.id.task_et);
                ImageButton uploadBtn = bottomSheetView.findViewById(R.id.upload_btn);

                uploadBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!editText.getText().toString().trim().isEmpty()) {
                            viewModel.addTask(new Task(editText.getText().toString().trim(),false));
                            bottomSheetDialog.dismiss();
                        }
                    }
                });

                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });
    }
}
