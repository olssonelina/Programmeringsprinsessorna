package com.example.nightingale.qwalk;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by Elina Olsson on 2017-04-27.
 */

public class addListenerOnTextChange implements TextWatcher {
    private Context mContext;
    EditText mOptionField;
    CreateQuestionActivity createQuestionActivity;

    public addListenerOnTextChange(Context context, EditText optionField) {
        super();
        this.mContext = context;
        this.mOptionField = optionField;
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mOptionField.setTextColor(Color.BLUE);
    }
}