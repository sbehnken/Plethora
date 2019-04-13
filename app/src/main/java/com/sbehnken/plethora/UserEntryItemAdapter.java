package com.sbehnken.plethora;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sbehnken.plethora.model.UserEntry;

import java.util.ArrayList;

public class UserEntryItemAdapter extends RecyclerView.Adapter<UserItemViewHolder> {
    Context context;
    ArrayList<UserEntry> userEntryList = new ArrayList();

    public UserEntryItemAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public UserItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.user_entry_item, viewGroup, false);

        UserItemViewHolder viewHolder = new UserItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserItemViewHolder userItemViewHolder, int i) {
        userItemViewHolder.bindUserEntryItem(userEntryList.get(i).getWord(), userEntryList.get(i).getPoints().toString());

    }

    @Override
    public int getItemCount() {
        return userEntryList.size();
    }

    public void addWord(UserEntry userEntry) {
        userEntryList.add(userEntry);
        notifyDataSetChanged();
    }



    public ArrayList<UserEntry> getUserEntryList() {
        return userEntryList;
    }
}

