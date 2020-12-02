package com.team.olpokhorochdemo.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.team.olpokhorochdemo.model.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amit on 03,December,2020
 */
public class PersonRepository {

    private static final String TAG = "PersonRepository";
    private static PersonRepository instance;
    private ArrayList<Person> personArrayList = new ArrayList<>();

    public static PersonRepository getInstance(){
        if(instance == null){
            instance = new PersonRepository();
        }
        return instance;
    }

    public MutableLiveData<List<Person>> getPersons(){
        fetchData();
        MutableLiveData<List<Person>> data = new MutableLiveData<>();
        data.setValue(personArrayList);
        return data;
    }
    public void fetchData() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Person");
        query.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> postList, ParseException e) {
                if (e == null) {
                    // If there are results, update the list of posts
                    // and notify the adapter
                    personArrayList.clear();
                    for (ParseObject person : postList) {
                        personArrayList.add(new Person(person.getString("name"), person.getString("age")));
                    }
                } else {
                    Log.d(getClass().getSimpleName(), "Error: " + e.getMessage());
                }
            }
        });
    }

}
