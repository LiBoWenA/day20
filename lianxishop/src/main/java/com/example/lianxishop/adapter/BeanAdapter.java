package com.example.lianxishop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lianxishop.R;
import com.example.lianxishop.bean.Bean;

import java.util.ArrayList;
import java.util.List;


public class BeanAdapter extends RecyclerView.Adapter<BeanAdapter.ViewHolder> {
    private Context context;
    private List<Bean.DataBean> list;

    public BeanAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void setData(List<Bean.DataBean> lists){
        list.clear();
        list.addAll(lists);
        notifyDataSetChanged();
    }

    public void addData(List<Bean.DataBean> lists){
        list.addAll(lists);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BeanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.shop_adapter,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BeanAdapter.ViewHolder viewHolder, final int i) {
        String images = list.get(i).getImages();
        String[] split = images.split("\\|");
        Glide.with(context).load(split[0]).into(viewHolder.img);
        viewHolder.tName.setText(list.get(i).getTitle());
        viewHolder.tPrice.setText(list.get(i).getPrice()+"");
        viewHolder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClick != null){
                    onClick.Click(i);

                }
            }
        });
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemOnClick != null){
                    itemOnClick.Click();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView tName,tPrice;
        private Button btn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            tName = itemView.findViewById(R.id.name);
            tPrice = itemView.findViewById(R.id.price);
            btn = itemView.findViewById(R.id.btn_add);
        }
    }

    public int getPid(int position){
        int pid = list.get(position).getPid();
        return pid;
    }
    OnClick onClick;

    public void setOnClick(OnClick click){
        onClick = click;
    }

    public interface OnClick{
        void Click(int pos);
    }

    ItemOnClick itemOnClick;

    public void setItemOnClick(ItemOnClick item){
        itemOnClick = item;
    }

    public interface ItemOnClick{
        void Click();
    }
}
