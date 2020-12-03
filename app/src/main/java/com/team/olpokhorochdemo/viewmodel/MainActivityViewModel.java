package com.team.olpokhorochdemo.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.team.olpokhorochdemo.model.Person;
import com.team.olpokhorochdemo.repository.PersonRepository;

import java.util.List;

/**
 * Created by Amit on 03,December,2020
 */
public class MainActivityViewModel extends AndroidViewModel {
    private PersonRepository mRepo;

    public MainActivityViewModel (@NonNull Application application){
        super(application);
        mRepo = new PersonRepository(application);
    }

    public MutableLiveData<List<Person>> getAllPersons(){
        return mRepo.getPersons();
    }

    public MutableLiveData<String> addPerson(Person person){
        return mRepo.addPerson(person);
    }

    public MutableLiveData<String> deletePerson(String  objectId){
        return mRepo.deleteObject(objectId);
    }

    public LiveData<Boolean> getIsUpdating(){
        return mRepo.getIsUpdating();
    }

}
