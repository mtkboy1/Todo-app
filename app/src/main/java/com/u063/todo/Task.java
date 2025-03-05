package com.u063.todo;

import static android.view.View.GONE;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;

public class Task {
    private String name;
    private static LinearLayout linearLayout;
    private Context c;
    private EditText etx;

    public Task(String name, Context c){
        this.name = name;
        this.c = c;
        etx = new EditText(c);
        etx.setText(name);
        etx.addTextChangedListener(new TextWatcher() {
            String before;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                setOnChange();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(etx.getText().toString().isEmpty()){
                    etx.setVisibility(GONE);
                }
            }
        });
        linearLayout.addView(etx);
    }
    public static void setConstraintLayout(LinearLayout cl){
        linearLayout = cl;
    }
    public String getName(){
        return name;
    }
    private void setOnChange(){
        name = etx.getText().toString();
        Log.e("name",name);
    }
}
