package com.example.borrar.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PageViewModel extends ViewModel {

    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();


    public void setIndex(int index) {
        mIndex.setValue(index);
    }


}