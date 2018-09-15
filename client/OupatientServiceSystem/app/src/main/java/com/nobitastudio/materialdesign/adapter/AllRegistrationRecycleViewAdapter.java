package com.nobitastudio.materialdesign.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nobitastudio.materialdesign.R;
import com.nobitastudio.materialdesign.activity.RegisterDetailsActivity;
import com.nobitastudio.materialdesign.bean.ClientRegistration;
import com.nobitastudio.materialdesign.util.AppDataUtil;
import com.nobitastudio.materialdesign.util.Utility;

import java.util.List;

public class AllRegistrationRecycleViewAdapter extends RecyclerView.Adapter<AllRegistrationRecycleViewAdapter.ViewHolder> {

    AppCompatActivity activity;
    Context mContext;
    List<ClientRegistration> clientRegistrations;

    public AllRegistrationRecycleViewAdapter(AppCompatActivity activity) {
        mContext = activity.getApplicationContext();
        this.clientRegistrations = AppDataUtil.getClientRegistrations();
        this.activity = activity;

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView registrationNoTextView;
        TextView orderStateTextView;
        TextView costTextView;
        TextView cancelOrPayTimeTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            registrationNoTextView = itemView.findViewById(R.id.recycleview_item_fragment_myapponitment_registrationno_textview);
            orderStateTextView = itemView.findViewById(R.id.recycleview_item_fragment_myapponitment_paystate_textview);
            costTextView = itemView.findViewById(R.id.recycleview_item_fragment_myapponitment_cost_textview);
            cancelOrPayTimeTextView = itemView.findViewById(R.id.recycleview_item_fragment_myapponitment_cancelorpaytime_textview);
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycleview_item_fragment_myapponitment, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedDoctorPosition = viewHolder.getAdapterPosition();
                //enter registration details activity.
                enterRegisterDetailsActivity(selectedDoctorPosition);

            }

        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (clientRegistrations.size() > 0) {
            ClientRegistration selectedClientRegistration = clientRegistrations.get(position);
            holder.registrationNoTextView.setText(selectedClientRegistration.getRegistrationNo());
            holder.orderStateTextView.setText(Utility.handelIntegerOrderStateToStr(selectedClientRegistration.getOrderState()));
            holder.costTextView.setText(String.valueOf(selectedClientRegistration.getCost()) + " 元");
            holder.cancelOrPayTimeTextView.setText(Utility.handleStrDate(selectedClientRegistration.getPayOrCancelTime()));
        }
    }

    @Override
    public int getItemCount() {
        return clientRegistrations.size();
    }

    private void enterRegisterDetailsActivity(int selectedDoctorPosition) {
        Intent intent = new Intent(activity, RegisterDetailsActivity.class);
        AppDataUtil.setRegistrationNo(clientRegistrations.get(selectedDoctorPosition).getRegistrationNo());
        AppDataUtil.setOrderState(clientRegistrations.get(selectedDoctorPosition).getOrderState());
        activity.startActivity(intent);
    }


}
