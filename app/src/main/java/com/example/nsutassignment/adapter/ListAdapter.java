package com.example.nsutassignment.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nsutassignment.ListActivity;
import com.example.nsutassignment.R;
import com.example.nsutassignment.clickListeners.SetClickListeners;
import com.example.nsutassignment.items.Item;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.viewHolder> {
    ArrayList<Item> list;
    SetClickListeners setClickListeners;
    public ListAdapter(ArrayList<Item> list, SetClickListeners setClickListeners){
        this.list = list;
        this.setClickListeners = setClickListeners;
    }
    @Override
    public ListAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.viewHolder holder, int position) {
        Item item = list.get(position);
        String name = item.getName();
        String price = item.getPrice();
        String date = item.getDate();
        holder.textViewName.setText(name);
        holder.textViewPrice.setText("\u20B9" + price);
        holder.textViewDate.setText(date);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setClickListeners.onClickToDelete(holder.getAdapterPosition());
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewDate, textViewPrice;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textviewName);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
        }
    }

    public void deleteItem(int position){

        Log.v("Aneesh", Integer.toString(position));

        if(position >= 0 && position < list.size()){
            list.remove(position);
            notifyItemRemoved(position);
        }
    }
}
