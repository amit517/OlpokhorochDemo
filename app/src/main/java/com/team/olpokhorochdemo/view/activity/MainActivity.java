package com.team.olpokhorochdemo.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.team.olpokhorochdemo.R;
import com.team.olpokhorochdemo.databinding.ActivityMainBinding;
import com.team.olpokhorochdemo.model.Person;
import com.team.olpokhorochdemo.view.adapter.PersonAdapter;
import com.team.olpokhorochdemo.viewmodel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ArrayList<Person> personArrayList;
    private MainActivityViewModel mMainActivityViewModel;
    private ActivityMainBinding binding;
    private PersonAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        init();
        mMainActivityViewModel.init();
        mMainActivityViewModel.getAllPersons().observe(this, new Observer<List<Person>>() {
            @Override
            public void onChanged(List<Person> people) {
                adapter.notifyDataSetChanged();
            }
        });
        initRecyclearView();
    }

    private void initRecyclearView() {
        binding.personRV.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PersonAdapter(this, mMainActivityViewModel.getAllPersons().getValue());
        binding.personRV.setAdapter(adapter);
    }

    private void init() {
        personArrayList = new ArrayList<>();
        mMainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

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


}