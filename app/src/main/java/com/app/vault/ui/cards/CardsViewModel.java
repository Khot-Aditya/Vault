package com.app.vault.ui.cards;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CardsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CardsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Cards fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
