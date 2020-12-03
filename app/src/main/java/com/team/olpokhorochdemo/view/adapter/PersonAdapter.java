package com.team.olpokhorochdemo.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team.olpokhorochdemo.R;
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

    public PersonAdapter(List<Person> personList, Context mContext, RvLongClickListener listener) {
        this.personList = personList;
        this.mContext = mContext;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_person, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nameTV.setText("Name : "+personList.get(position).getName());
        holder.ageTV.setText("Age : "+String.valueOf(personList.get(position).getAge()));
    }

    @Override
    public int getItemCount() {
        return personList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        TextView nameTV, ageTV;
        private WeakReference<RvLongClickListener> listenerRef;
        private LinearLayout linearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTV = itemView.findViewById(R.id.nameTV);
            ageTV = itemView.findViewById(R.id.ageTV);
            linearLayout = itemView.findViewById(R.id.linearLayout);
            listenerRef = new WeakReference<>(listener);
            nameTV.setOnLongClickListener(this);
            ageTV.setOnLongClickListener(this);
            linearLayout.setOnLongClickListener(this);


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
        personList.clear();
        personList.addAll(mPersonList);
        notifyDataSetChanged();
    }
    public interface RvLongClickListener {
        void position(int position);
    }
}
