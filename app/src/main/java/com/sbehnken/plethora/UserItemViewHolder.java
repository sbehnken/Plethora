package com.sbehnken.plethora;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class UserItemViewHolder extends RecyclerView.ViewHolder {
    private TextView enteredWord;
    private TextView pointsText;

    public UserItemViewHolder(@NonNull View itemView) {
        super(itemView);

        enteredWord = itemView.findViewById(R.id.entered_word);
        pointsText = itemView.findViewById(R.id.point_amount);
    }
    public void bindUserEntryItem(String word, String points) {
        enteredWord.setText(word);
        pointsText.setText(points);
    }
}
