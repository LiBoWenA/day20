package com.example.lianxishop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lianxishop.R;
import com.example.lianxishop.bean.ShopBean;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {
    private Context context;
    private List<ShopBean.DataBean> list;

    public ShopAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void setData(List<ShopBean.DataBean> lists){
        list = lists;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ShopAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.shopadapter,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopAdapter.ViewHolder viewHolder, int i) {
        viewHolder.tTitle.setText(list.get(i).getSellerName());
        //添加布局管理者
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        viewHolder.recyclerView.setLayoutManager(layoutManager);

        ChildAdapter adapter = new ChildAdapter(context,list.get(i).getList());
        viewHolder.recyclerView.setAdapter(adapter);
        adapter.setChildCallBackLitener(new ChildAdapter.ChildCallBackLitener() {
            @Override
            public void callBack() {
                if (shopCallBackListener != null){
                    shopCallBackListener.callBack(list);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tTitle;
        RecyclerView recyclerView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tTitle = itemView.findViewById(R.id.s_title);
            recyclerView = itemView.findViewById(R.id.itemrecler);
        }
    }

    ShopCallBackListener shopCallBackListener;

    public void setShopCallBackListener(ShopCallBackListener listener){
        shopCallBackListener = listener;
    }

    public interface ShopCallBackListener{
        void callBack(List<ShopBean.DataBean> list);
    }
}
