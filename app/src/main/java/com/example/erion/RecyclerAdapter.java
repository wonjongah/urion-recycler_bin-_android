package com.example.erion;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder> {

    // adapter에 들어갈 list 입니다.
    private ArrayList<Data> listData = new ArrayList<>();

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ItemViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
        holder.onBind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수 입니다.
        return listData.size();
    }

    void addItem(Data data) {
        // 외부에서 item을 추가시킬 함수입니다.
        listData.add(data);
    }

    // RecyclerView의 핵심인 ViewHolder 입니다.
    // 여기서 subView를 setting 해줍니다.
    class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_bottle_kind;
        private TextView tv_bottle_point;
        private ImageView iv_bottle;
        private TextView tv_date;

        ItemViewHolder(View itemView) {
            super(itemView);

            tv_bottle_kind= itemView.findViewById(R.id.tv_bottle_kind);
            tv_bottle_point = itemView.findViewById(R.id.tv_bottle_point);
            iv_bottle = itemView.findViewById(R.id.iv_bottle);
            tv_date = itemView.findViewById(R.id.tv_date);
        }

        void onBind(Data data) {
            tv_bottle_kind.setText(data.getBottleKind());
            tv_bottle_point.setText(String.valueOf(data.getBottlePoint()));
            tv_date.setText(String.valueOf(data.getDate()));
            Glide.with(itemView).load(data.getResId()).into(iv_bottle);
//            iv_bottle.setImageResource(data.getResId());
        }
    }
}
