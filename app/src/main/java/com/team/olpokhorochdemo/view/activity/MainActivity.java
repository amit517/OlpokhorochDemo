package com.team.olpokhorochdemo.view.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.team.olpokhorochdemo.view.fragment.AddPersonDialogFragment;
import com.team.olpokhorochdemo.viewmodel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AddPersonDialogFragment.ItemClickListene, PersonAdapter.RvLongClickListener {

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
        initRecyclearView();

        mMainActivityViewModel.getAllPersons().observe(this, new Observer<List<Person>>() {
            @Override
            public void onChanged(List<Person> people) {
                personArrayList.clear();
                personArrayList.addAll(people);
                adapter.setPersonList(people);
                Log.d(TAG, "onChanged: "+people.size());
            }
        });

        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddPersonDialogFragment addPhotoBottomDialogFragment = new AddPersonDialogFragment();
                addPhotoBottomDialogFragment.show(getSupportFragmentManager(),
                        AddPersonDialogFragment.TAG);
            }
        });
    }

    private void initRecyclearView() {
        binding.personRV.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PersonAdapter(this,this);
        binding.personRV.setAdapter(adapter);
    }

    private void init() {
        personArrayList = new ArrayList<>();
        mMainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
    }

    @Override
    public void successfullyAdded() {
        finish();
        startActivity(getIntent());
    }

    @Override
    public void position(int position) {
        new AlertDialog.Builder(this)
                .setTitle("Delete")
                .setMessage("You are about to delete "+personArrayList.get(position).getName()+".\nAre you sure you want to delete?")
                .setIcon(R.drawable.ic_baseline_delete_forever_24)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
}