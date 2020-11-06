package com.reminderapp.ui.ongoing;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class OngoingViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public OngoingViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is ongoing fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}