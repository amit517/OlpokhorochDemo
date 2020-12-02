package com.team.olpokhorochdemo.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.team.olpokhorochdemo.model.Person;
import com.team.olpokhorochdemo.repository.PersonRepository;

import java.util.List;

/**
 * Created by Amit on 03,December,2020
 */
public class MainActivityViewModel extends ViewModel {

    private MutableLiveData<List<Person>> mPersonList;
    private PersonRepository mRepo;

    public void init(){
        if (mPersonList !=null){
            return;
        }
        mRepo = PersonRepository.getInstance();
        mPersonList = mRepo.getPersons();
    }

    public LiveData<List<Person>> getAllPersons(){
        return mPersonList;
    }

}
