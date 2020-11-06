package com.reminderapp.ui.finished;

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

import com.reminderapp.R;

public class FinishedFragment extends Fragment {

    private FinishedViewModel FinishedViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FinishedViewModel =
                new ViewModelProvider(this).get(FinishedViewModel.class);
        View root = inflater.inflate(R.layout.fragment_finished, container, false);
        final TextView textView = root.findViewById(R.id.text_finished);
        FinishedViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}