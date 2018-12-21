package com.example.lianxishop.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.lianxishop.R;

public class TitleView extends LinearLayout {
    Context context;

    public TitleView(Context context) {
        super(context);
        init(context);
    }

    public TitleView(Context context,  AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TitleView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.head,null);
        final EditText editText = view.findViewById(R.id.ed_serch);
        Button btn = view.findViewById(R.id.serch);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getData != null){
                    getData.data(editText.getText().toString());
                }
            }
        });
        addView(view);
    }
    GetData getData;

    public void setGetData(GetData data){
        getData = data;
    }

    public interface GetData{
        void data(String str);
    }
}
