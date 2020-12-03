package com.team.olpokhorochdemo.repository;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.team.olpokhorochdemo.model.Person;
import com.team.olpokhorochdemo.view.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amit on 03,December,2020
 */
public class PersonRepository {

    private static final String TAG = "PersonRepository";
    private ArrayList<Person> personArrayList = new ArrayList<>();
    private MutableLiveData<Boolean> mIsUpdating = new MutableLiveData<>();
    private Application application;

    public PersonRepository(Application application) {
        this.application = application;
    }

    public MutableLiveData<List<Person>> getPersons(){

        MutableLiveData<List<Person>> data = new MutableLiveData<>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Person");

        query.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> postList, ParseException e) {
                if (e == null) {
                    personArrayList.clear();
                    for (ParseObject person : postList) {
                        personArrayList.add(new Person(person.getString("name"), person.getInt("age")));
                    }
                    data.postValue(personArrayList);
                } else {
                    Log.d(getClass().getSimpleName(), "Error: " + e.getMessage());
                }
            }
        });

        return data;
    }

    public MutableLiveData<String> addPerson(Person person){
        mIsUpdating.setValue(true);
        final MutableLiveData<String> liveData = new MutableLiveData<>();
        ParseObject entity = new ParseObject("Person");

        entity.put("name", person.getName());
        entity.put("age", person.getAge());

        // Saves the new object.
        // Notice that the SaveCallback is totally optional!
        entity.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                // Here you can handle errors, if thrown. Otherwise, "e" should be null
                if (e == null) {
                    mIsUpdating.postValue(false);
                    liveData.postValue("200");
                } else {
                    mIsUpdating.postValue(false);
                    liveData.postValue("500");
                    Toast.makeText(application, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return liveData;
    }

    public LiveData<Boolean> getIsUpdating(){
        return mIsUpdating;
    }
}
