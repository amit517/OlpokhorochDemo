package com.team.olpokhorochdemo.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.team.olpokhorochdemo.R;
import com.team.olpokhorochdemo.model.Person;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ArrayList<Person> personArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();

        setContentView(R.layout.activity_main);
/*        createObject("Amit Kundu",25);
        createObject("Mostain Ahamed",30);
        createObject("Abdullah Al Noman",25);
        createObject("Soumik Kundu",25);*/

        readObject();


    }

    private void init() {
        personArrayList = new ArrayList<>();
    }

    public void createObject(String name, int age) {
        ParseObject entity = new ParseObject("Person");

        entity.put("name", name);
        entity.put("age", age);

        // Saves the new object.
        // Notice that the SaveCallback is totally optional!
        entity.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                // Here you can handle errors, if thrown. Otherwise, "e" should be null
                if (e==null){
                    Log.d(TAG, "done: "+"added");
                }else {
                    Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void readObject() {
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
                    for (Person post : personArrayList) {
                        Log.d(TAG, "done: "+post.getName());
                    }
                } else {
                    Log.d(getClass().getSimpleName(), "Error: " + e.getMessage());
                }
            }
        });
    }
}