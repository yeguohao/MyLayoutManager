package com.ygh.mediaplayerdemo;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {

    private static final String TAG = "MyAdapter";
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_demo, parent, false);
        Log.e(TAG, "onCreateViewHolder: " );
        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.textView.setText(position + " item");
    }

    @Override
    public int getItemCount() {
        return 50;
    }

    class MyHolder extends RecyclerView.ViewHolder {

        TextView textView;

        MyHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text);
        }
    }
}
