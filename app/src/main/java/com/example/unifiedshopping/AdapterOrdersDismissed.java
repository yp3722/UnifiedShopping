package com.example.unifiedshopping;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class AdapterOrdersDismissed extends RecyclerView.Adapter<AdapterOrdersDismissed.CustomViewHolder> {
    private ArrayList<Order> OrderList;
    private LayoutInflater Linflater;
    private int position;
    private ItemClickListner gitemClickListner;

    public AdapterOrdersDismissed(ArrayList<Order> O, Context context, ItemClickListner itmClkListner)
    {
        OrderList = O;
        Linflater=LayoutInflater.from(context);
        gitemClickListner = itmClkListner;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener {
        private ImageView prodImg;
        private TextView prodName;
        private TextView deliveryName;
        private TextView prodStatus;
        private TextView vendorName;
        private ImageView progressBar;
        private ImageView icon1;
        private TextView inTransit;

        public ItemClickListner itemClickListner;
        public ConstraintLayout elemLayout;

        public CustomViewHolder(final View itemView, ItemClickListner itemClickListner) {
            super(itemView);
            prodImg = itemView.findViewById(R.id.orderImg);
            prodStatus = itemView.findViewById(R.id.orderStatus);
            deliveryName = itemView.findViewById(R.id.addressName);
            prodName = itemView.findViewById(R.id.itemName);
            vendorName = itemView.findViewById(R.id.vendorName);
            icon1 = itemView.findViewById(R.id.icon1);
            progressBar = itemView.findViewById(R.id.progressBar);
            elemLayout = itemView.findViewById(R.id.elem);
            inTransit = itemView.findViewById(R.id.transit);

            this.itemClickListner = itemClickListner;
            itemView.setOnClickListener(this);

            elemLayout.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClickListner.onItemClick(getAdapterPosition());
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.add(this.getAdapterPosition(),866,1,"Start Tracking");
        }
    }

    public interface  ItemClickListner
    {
        void onItemClick(int pos);
    }

    @NonNull
    @Override
    public AdapterOrdersDismissed.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = Linflater.inflate(R.layout.orderitem , parent , false);
        return new CustomViewHolder(item,gitemClickListner);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterOrdersDismissed.CustomViewHolder holder, int position) {

        //holder.prodImg.setImageBitmap(getImageBitmap(OrderList.get(position).getOrderImg()));
        holder.prodImg.setImageResource(R.drawable.dummy_order_image);
        String name = OrderList.get(position).getOrderName();
        if (name.length()>50)
            name = name.substring(0,45)+"...";
        holder.prodName.setText(name);
        holder.deliveryName.setText("");
        holder.prodStatus.setText("Not Being Tracked");//OrderList.get(position).getCurrStatus()
        holder.vendorName.setText("");
        holder.progressBar.setImageResource(R.drawable.not_tracked);
        //Log.i("Yash", "inadapter: "+OrderList.get(position).getCurrStatus()+Integer.toString(OrderList.get(position).getPriorityFlag()));
        holder.icon1.setImageResource(R.drawable.deleted);
        holder.inTransit.setText("");




        Log.i("Yash", "inside onBind" );
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setPosition(holder.getLayoutPosition());
                return false;
            }
        });

    }

    @Override
    public void onViewRecycled(CustomViewHolder holder) {
        holder.itemView.setOnLongClickListener(null);
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemCount() {
        return OrderList.size();
    }

    public void setPosition(int position)
    {
        this.position = position;
    }

    //Function Parses url to bitmap
    private Bitmap getImageBitmap(String url) {
        Bitmap bm = null;
        try {
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
        } catch (IOException e) {
            Log.i("yash Adapter Bitmap", "Error getting bitmap", e);
        }
        return bm;
    }
}
