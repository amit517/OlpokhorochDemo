package com.team.olpokhorochdemo.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.team.olpokhorochdemo.R;
import com.team.olpokhorochdemo.databinding.ItemPersonBinding;
import com.team.olpokhorochdemo.model.Person;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amit on 03,December,2020
 */
public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {

    private List<Person> personList;
    private Context mContext;
    private final RvLongClickListener listener;

    public PersonAdapter(Context mContext, RvLongClickListener listener) {
        this.mContext = mContext;
        this.listener = listener;
        personList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPersonBinding itemPersonBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_person, parent, false);
        return new ViewHolder(itemPersonBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemPersonBinding.setPerson(personList.get(position));
    }

    @Override
    public int getItemCount() {
        if (personList != null) {
            return personList.size();
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

        private ItemPersonBinding itemPersonBinding;
        private WeakReference<RvLongClickListener> listenerRef;

        public ViewHolder(@NonNull ItemPersonBinding itemPersonBinding) {
            super(itemPersonBinding.getRoot());
            this.itemPersonBinding = itemPersonBinding;
            listenerRef = new WeakReference<>(listener);
            itemPersonBinding.ageTV.setOnLongClickListener(this);
            itemPersonBinding.nameTV.setOnLongClickListener(this);
            itemPersonBinding.linearLayout.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            if (view.getId() == R.id.nameTV || view.getId() == R.id.ageTV || view.getId() == R.id.linearLayout){
                listenerRef.get().position(getAdapterPosition());
            }
            return false;
        }
    }

    public void setPersonList (List<Person> mPersonList){
        if (personList != null) {
            personList.clear();
        }
        personList.addAll(mPersonList);
        notifyDataSetChanged();
    }
    public interface RvLongClickListener {
        void position(int position);
    }
}
