package com.carsonskjerdal.app.thrillseekers.adapters;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.carsonskjerdal.app.thrillseekers.R;
import com.carsonskjerdal.app.thrillseekers.activity.MainActivity;
import com.carsonskjerdal.app.thrillseekers.activity.MapActivity;
import com.carsonskjerdal.app.thrillseekers.items.ThrillItem;

import java.util.List;

/**
 * Created by Carson on 4/13/2018.
 * <p>
 * Feel free to use code just give credit please :)
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ItemHolder> implements View.OnClickListener {

    private List itemList;
    private String itemName;
    private int itemPosition;
    private ItemHolder itemHolder;

    public CustomAdapter(List list) {
        itemList = list;
    }

    @Override
    public void onClick(View v) {
    Log.e("test", "test2");
    }

    /* ViewHolder for each item */
    public class ItemHolder extends RecyclerView.ViewHolder {


        TextView name;
        ImageView image;
        String itemName;


        ItemHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            image = itemView.findViewById(R.id.image);
            int listSize = itemList.size();

            int position = getAdapterPosition();
            //Log.e("Adapter", "id" + position);

        }
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);


        /*int height = parent.getMeasuredHeight() / 4;
        int width = parent.getMeasuredWidth();

        itemView.setLayoutParams(new RecyclerView.LayoutParams(width, height));*/

        return new ItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        //holder.setIsRecyclable(false);
        ThrillItem playerItem = (ThrillItem) itemList.get(position);

        itemName = playerItem.getName();
        itemPosition = holder.getAdapterPosition();
        itemHolder = holder;

        //Sets Text
        holder.name.setText(itemName);
        //Log.e("View Holder",playerItem.getName() + " is " + playerItem.getImage());
        //holder.playerName.setTag(R.string.listSize, playerList.size());
        holder.name.setTag(position);

        //holder.image.setImageAlpha(playerItem.getImage());
        holder.image.setImageResource(playerItem.getImage());
        holder.image.setTag(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //goes to new activity passing the item name
                Intent intent = new Intent(itemHolder.itemView.getContext(), MapActivity.class);
                Bundle b = new Bundle();

                //get text for current item
                String textGet = itemName;
                //put text into a bundle and add to intent
                intent.putExtra("text", textGet);

                //get position to carry integer
                intent.putExtra("position", itemPosition);

                intent.putExtras(b);

                //begin activity
                itemHolder.itemView.getContext().startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }


    public List<ThrillItem> getList() {
        //Log.e("Edit Text", "List: " + itemList.size() + " is the size. It cointains at position 0: " + itemList.get(0).getName());
        return itemList;
    }


}
