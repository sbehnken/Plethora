
package com.example.bogglegame.bogglegame.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VariantForm {

    @SerializedName("text")
    @Expose
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
