package com.reminderapp.ui.ongoing;

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

public class OngoingFragment extends Fragment {

    private OngoingViewModel ongoingViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ongoingViewModel =
                new ViewModelProvider(this).get(OngoingViewModel.class);
        View root = inflater.inflate(R.layout.fragment_ongoing, container, false);
        final TextView textView = root.findViewById(R.id.text_ongoing);
        ongoingViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}