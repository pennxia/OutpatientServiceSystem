package com.nobitastudio.materialdesign.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nobitastudio.materialdesign.R;
import com.nobitastudio.materialdesign.bean.ClientDrugList;
import com.nobitastudio.materialdesign.bean.ClientDrugListData;

import java.util.List;

public class DrugDetailsRecycleViewAdapter extends RecyclerView.Adapter<DrugDetailsRecycleViewAdapter.ViewHolder>{

    Context mContext;
    AppCompatActivity activity;
    List<ClientDrugList> clientDrugLists;


    public DrugDetailsRecycleViewAdapter( List<ClientDrugList> clientDrugLists) {
        this.clientDrugLists = clientDrugLists;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView drugNameTextView;
        TextView purchaseNumberTextView;
        TextView priceTextView;
        TextView allPriceTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            drugNameTextView = itemView.findViewById(R.id.recycleview_item_activity_drugdetails_drugname_textview);
            purchaseNumberTextView = itemView.findViewById(R.id.recycleview_item_activity_drugdetails_purchasenumber_textview);
            priceTextView = itemView.findViewById(R.id.recycleview_item_activity_drugdetails_price_textview);
            allPriceTextView = itemView.findViewById(R.id.recycleview_item_activity_drugdetails_allprice_textview);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }

        View view = LayoutInflater.from(mContext).inflate(R.layout.recycleview_item_activity_drugdetails, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (clientDrugLists.size() > 0){
            ClientDrugList clientDrugList = clientDrugLists.get(position);
            holder.drugNameTextView.setText(clientDrugList.getDrugName());
            holder.purchaseNumberTextView.setText(String.valueOf(clientDrugList.getPurchaseNumber()));
            holder.priceTextView.setText(String.valueOf(clientDrugList.getPrice()));
            holder.allPriceTextView.setText(String.valueOf(clientDrugList.getPrice() * clientDrugList.getPurchaseNumber()));
        }
    }

    @Override
    public int getItemCount() {
        return clientDrugLists.size();
    }


}
