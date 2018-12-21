package com.example.lianxishop.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.lianxishop.R;
import com.example.lianxishop.adapter.ChildAdapter;
import com.example.lianxishop.bean.Bean;
import com.example.lianxishop.bean.ShopBean;

import java.util.List;

public class ItemPriceView extends LinearLayout implements View.OnClickListener {

    private EditText ed_num;
    private Context context;
    private List<ShopBean.DataBean.ListBean> list;
    int position;
    private ChildAdapter adapter;


    public ItemPriceView(Context context) {
        super(context);
        init(context);
    }

    public ItemPriceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ItemPriceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    //获取资源ID
    private void init(Context context) {
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.item_price,null);
        ed_num = view.findViewById(R.id.ed_num);
        view.findViewById(R.id.btn_add).setOnClickListener(this);
        view.findViewById(R.id.btn_jian).setOnClickListener(this);
        addView(view);

        ed_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    num = Integer.parseInt(String.valueOf(s));
                    list.get(position).setNum(num);
                }catch (Exception e){
                    list.get(position).setNum(1);
                }
                if (backListener != null){
                    backListener.callBack();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private int num;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add:
                num++;
                ed_num.setText(num+"");
                break;
            case R.id.btn_jian:
                if (num>1){
                    num--;
                }else{
                    Toast.makeText(context,"数量不能小于1",Toast.LENGTH_SHORT).show();
                }
                ed_num.setText(num+"");
                break;
                default:
                    break;

        }
    }
    public void setData(ChildAdapter adapter, List<ShopBean.DataBean.ListBean> list, int i){
        this.list = list;
        position = i;
        this.adapter = adapter;
        num = list.get(position).getNum();
        ed_num.setText(num+"");
    }


    //向上通知
    CallBackListener backListener;

    public void setOnCallBack(CallBackListener onCallBack){
        backListener = onCallBack;
    }

    public interface CallBackListener{
        void callBack();
    }
}
