package com.example.lianxishop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lianxishop.R;
import com.example.lianxishop.bean.ShopBean;
import com.example.lianxishop.view.ItemPriceView;

import java.util.List;

public class ChildAdapter extends RecyclerView.Adapter<ChildAdapter.ViewHolder> {

    private Context context;
    private List<ShopBean.DataBean.ListBean> list;

    public ChildAdapter(Context context, List<ShopBean.DataBean.ListBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ChildAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.child_adapter,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildAdapter.ViewHolder viewHolder, int i) {
        String images = list.get(i).getImages();
        String[] split = images.split("\\|");
        Glide.with(context).load(split[0]).into(viewHolder.imageView);
        viewHolder.checkBox.setChecked(list.get(i).isCheck());
        viewHolder.tTitle.setText(list.get(i).getTitle());
        viewHolder.tPrice.setText("合计：¥"+list.get(i).getPrice());
        viewHolder.recyclerView.setData(this,list,i);


        viewHolder.recyclerView.setOnCallBack(new ItemPriceView.CallBackListener() {
            @Override
            public void callBack() {
                if (callBackLitener != null){
                    callBackLitener.callBack();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        ImageView imageView;
        TextView tTitle,tPrice;
        ItemPriceView recyclerView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.s_ck);
            imageView = itemView.findViewById(R.id.img);
            tTitle = itemView.findViewById(R.id.title);
            tPrice = itemView.findViewById(R.id.price);
            recyclerView = itemView.findViewById(R.id.num);
        }
    }

    ChildCallBackLitener callBackLitener;

    public void setChildCallBackLitener(ChildCallBackLitener shopCallBackLitener){
        callBackLitener = shopCallBackLitener;
    }

    public interface ChildCallBackLitener{
        void callBack();
    }
}
