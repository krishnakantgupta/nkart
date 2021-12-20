package com.kk.nkart.ui.common;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

import com.kk.nkart.R;

public class CustomTextBox extends EditText {
    public CustomTextBox(Context context) {
        super(context, null, 0, 0);
    }

    public CustomTextBox(Context context, AttributeSet attrs) {
        this(context, attrs, 0, 0);
    }

    public CustomTextBox(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public CustomTextBox(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, R.style.customTextBox);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        setBackground(context.getDrawable(R.drawable.bg_textbox));
//        setPadding(10,0,0,0);

    }

    public String getValue() {
        return this.getText().toString();
    }
}
