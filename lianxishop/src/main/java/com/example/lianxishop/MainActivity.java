package com.example.lianxishop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lianxishop.adapter.BeanAdapter;
import com.example.lianxishop.bean.Bean;
import com.example.lianxishop.bean.ShoppingBean;
import com.example.lianxishop.persenter.IPersenterImpl;
import com.example.lianxishop.view.IView;
import com.example.lianxishop.view.TitleView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements IView,View.OnClickListener {

    private String path = "http://www.zhaoapi.cn/product/searchProducts";
    private EditText ed_serch;
    private IPersenterImpl iPersenter;
    private XRecyclerView recyclerView;
    private int page;
    private int sort;
    private TitleView title;
    private BeanAdapter adapter;
    private int pid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    //获取资源id
    private void init() {
        page = 1;
        sort = 0;
        ed_serch = findViewById(R.id.ed_serch);
        findViewById(R.id.radio1).setOnClickListener(this);
        findViewById(R.id.radio2).setOnClickListener(this);
        findViewById(R.id.radio3).setOnClickListener(this);
        title = findViewById(R.id.title);
        recyclerView = findViewById(R.id.reciclr);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        //设置刷新加载
        recyclerView.setLoadingMoreEnabled(true);
        recyclerView.setPullRefreshEnabled(true);

        iPersenter = new IPersenterImpl(this);
        //适配器
        adapter = new BeanAdapter(this);
        recyclerView.setAdapter(adapter);


        title.setGetData(new TitleView.GetData() {
            @Override
            public void data(String str) {
                page = 1;
                Map<String,String> mapa = new HashMap<>();
                mapa.put("keywords",str);
                mapa.put("page",page+"");
                mapa.put("sort",sort+"");
                iPersenter.showRequestData(path,mapa,Bean.class);
            }
        });
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getIpersenter(ed_serch.getText().toString(),page,sort);
            }

            @Override
            public void onLoadMore() {
                getIpersenter(ed_serch.getText().toString(),page,sort);
            }
        });
        getIpersenter(ed_serch.getText().toString(),page,sort);

        //点击加入购物车添加进购物车
        adapter.setOnClick(new BeanAdapter.OnClick() {
            @Override
            public void Click(int pos) {
                pid = adapter.getPid(pos);
                Map<String,String> map = new HashMap<>();
                map.put("uid",10196+"");
                map.put("pid", pid +"");
               iPersenter.showRequestData("http://www.zhaoapi.cn/product/addCart",map,ShoppingBean.class);
            }
        });


        //点击条目进行跳转到购物车页面
        adapter.setItemOnClick(new BeanAdapter.ItemOnClick() {
            @Override
            public void Click() {
                Intent intent = new Intent(MainActivity.this,IntentActivity.class);
                intent.putExtra("uid",10196+"");
                startActivity(intent);
            }
        });
    }

    private void getIpersenter(String name,int page,int sort) {
        Map<String,String> map = new HashMap<>();
        map.put("keywords",name);
        map.put("page",page+"");
        map.put("sort",sort+"");
        iPersenter.showRequestData(path,map,Bean.class);
    }

    @Override
    public void onClick(View v) {
        page = 1;
        switch (v.getId()){
            case R.id.radio1:
                sort =0;
                Map<String,String> map = new HashMap<>();
                map.put("keywords",ed_serch.getText().toString());
                map.put("page",page+"");
                map.put("sort",sort+"");
                iPersenter.showRequestData(path,map,Bean.class);
                break;
            case R.id.radio2:
                sort =1;
                Map<String,String> maps = new HashMap<>();
                maps.put("keywords",ed_serch.getText().toString());
                maps.put("page",page+"");
                maps.put("sort",sort+"");
                iPersenter.showRequestData(path,maps,Bean.class);
                break;
            case R.id.radio3:
                sort =2;
                Map<String,String> map3 = new HashMap<>();
                map3.put("keywords",ed_serch.getText().toString());
                map3.put("page",page+"");
                map3.put("sort",sort+"");
                iPersenter.showRequestData(path,map3,Bean.class);
                break;
                default:
                    break;
        }
    }

    @Override
    public void startRequestData(Object data) {
        if (data instanceof Bean){
            Bean bean = (Bean) data;
            if (page == 1){
                adapter.setData(bean.getData());
            }else{
                adapter.addData(bean.getData());
            }
            page++;
            recyclerView.refreshComplete();
            recyclerView.loadMoreComplete();
        }
        if (data instanceof ShoppingBean){
            ShoppingBean bean = (ShoppingBean) data;
            if (bean.getCode().equals("0")){
                Toast.makeText(this,"添加成功",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,bean.getMsg().toString(),Toast.LENGTH_SHORT).show();
            }
        }

    }
}
