

package com.example.lianxishop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.lianxishop.adapter.ShopAdapter;
import com.example.lianxishop.bean.ShopBean;
import com.example.lianxishop.persenter.IPersenterImpl;
import com.example.lianxishop.view.IView;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IntentActivity extends AppCompatActivity implements IView,View.OnClickListener {

    private String path = "http://www.zhaoapi.cn/product/getCarts";
    private IPersenterImpl iPersenter;
    private RecyclerView recyclerView;
    private ShopAdapter adapter;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent);
        init();
    }

    private void init() {
        Intent intent = getIntent();
        String uid = intent.getStringExtra("uid");
        iPersenter = new IPersenterImpl(this);
        recyclerView = findViewById(R.id.shop_recicle);
        adapter = new ShopAdapter(this);
        textView = findViewById(R.id.zprice);


        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);


        adapter.setShopCallBackListener(new ShopAdapter.ShopCallBackListener() {
            @Override
            public void callBack(List<ShopBean.DataBean> list) {
                int totlenum = 0;
                double price = 0;
                for (int i = 0; i < list.size(); i++) {
                    ShopBean.DataBean dataBean = list.get(i);
                    for (int j = 0; j < dataBean.getList().size(); j++) {
                        price = price + (dataBean.getList().get(j).getNum() * dataBean.getList().get(j).getPrice());

                    }
                }

                textView.setText(price+"");
            }
        });

        Map<String,String> map = new HashMap<>();
        map.put("uid",uid);
        iPersenter.showRequestData(path,map,ShopBean.class);
    }

    private void getDatas() {
        double price = 0;
        for (int i = 0; i < beanList.size(); i++) {
            ShopBean.DataBean dataBean = beanList.get(i);
            for (int j = 0; j < dataBean.getList().size(); j++) {
                price = price + (dataBean.getList().get(j).getNum() * dataBean.getList().get(j).getPrice());

            }
        }

        textView.setText(price+"");
    }

    @Override
    public void onClick(View v) {

    }
    List<ShopBean.DataBean> beanList;
    @Override
    public void startRequestData(Object data) {
        if (data instanceof ShopBean){
            ShopBean bean = (ShopBean) data;
            beanList = bean.getData();
            adapter.setData(beanList);

            getDatas();
        }
    }
}
