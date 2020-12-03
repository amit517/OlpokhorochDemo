package com.team.olpokhorochdemo.view.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.snackbar.Snackbar;
import com.team.olpokhorochdemo.R;
import com.team.olpokhorochdemo.model.Person;
import com.team.olpokhorochdemo.viewmodel.MainActivityViewModel;

import java.util.Objects;

public class AddPersonDialogFragment extends BottomSheetDialogFragment implements  View.OnClickListener {

    public static final String TAG = "AddPersonDialogFragment";
    private Context context;
    private ItemClickListene mListener;
    private EditText nameET,ageET;
    private Button addBtn,cancelBtn;
    private ProgressBar mProgressBar;
    private MainActivityViewModel mMainActivityViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.bottom_sheet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        addBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
        nameET.requestFocus();

        mMainActivityViewModel.getIsUpdating().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean){
                    showProgressBar();
                }
                else{
                    hideProgressBar();
                }
            }
        });
    }

    private void showProgressBar(){
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar(){
        mProgressBar.setVisibility(View.GONE);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialogInterface;
                setupFullHeight(bottomSheetDialog);
            }
        });
        return dialog;
    }

    private void setupFullHeight(BottomSheetDialog bottomSheetDialog) {
        FrameLayout bottomSheet = (FrameLayout) bottomSheetDialog.findViewById(R.id.design_bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();

        int windowHeight = getWindowHeight();
        if (layoutParams != null) {
            layoutParams.height = windowHeight - (windowHeight / 5);
        }
        bottomSheet.setLayoutParams(layoutParams);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    private int getWindowHeight() {
        // Calculate window height for fullscreen use
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    private void init(View view) {
        nameET = view.findViewById(R.id.nameET);
        ageET = view.findViewById(R.id.ageET);
        addBtn = view.findViewById(R.id.addBtn);
        cancelBtn = view.findViewById(R.id.cancelBtn);
        mProgressBar = view.findViewById(R.id.progress_bar);
        mMainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        mListener = (ItemClickListene) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addBtn:
                if (!ageET.getText().toString().isEmpty() && !nameET.getText().toString().isEmpty()){
                    addPerson(new Person(nameET.getText().toString(),Integer.parseInt(ageET.getText().toString())));
                }else {
                    Toast.makeText(context, "All fields are required", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.cancelBtn:
                dismiss();
                break;
        }
    }

    private void addPerson(Person person) {
        mMainActivityViewModel.addPerson(person).observe(this, new Observer<String>() {
            @Override
            public void onChanged(String responseCode) {
                if (responseCode.equals("200")){
                    Snackbar.make(Objects.requireNonNull(getView()), "Successfully deleted", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    mListener.successfullyAdded();
                    dismiss();
                }else {
                    Snackbar.make(Objects.requireNonNull(getView()), "Something went wrong", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
    }

    public interface ItemClickListene {
        void successfullyAdded();
    }
}
