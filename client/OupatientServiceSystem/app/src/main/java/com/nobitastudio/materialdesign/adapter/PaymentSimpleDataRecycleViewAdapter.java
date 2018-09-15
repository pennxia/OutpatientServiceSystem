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
import com.nobitastudio.materialdesign.activity.CheckInspectionDetailsActivity;
import com.nobitastudio.materialdesign.activity.DrugDetailsActivity;
import com.nobitastudio.materialdesign.activity.OperationDetailsActivity;
import com.nobitastudio.materialdesign.activity.RegisterDetailsActivity;
import com.nobitastudio.materialdesign.bean.PaymentSimpleData;
import com.nobitastudio.materialdesign.util.AppDataUtil;
import com.nobitastudio.materialdesign.util.Utility;

import java.util.List;

public class PaymentSimpleDataRecycleViewAdapter extends RecyclerView.Adapter<PaymentSimpleDataRecycleViewAdapter.ViewHolder> {

    AppCompatActivity activity;
    Context mContext;
    List<PaymentSimpleData> paymentSimpleDataList;

    public PaymentSimpleDataRecycleViewAdapter(AppCompatActivity activity, List<PaymentSimpleData> paymentSimpleDataList) {
        mContext = activity.getApplicationContext();
        this.paymentSimpleDataList = paymentSimpleDataList;
        this.activity = activity;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderDetailsTextView;
        TextView orderTypeTextView;
        TextView itemCostIdTextView;
        TextView payStateTextView;
        TextView costTextView;
        TextView cancelOrPayTimeTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            orderDetailsTextView = itemView.findViewById(R.id.recycleview_item_activity_outpatientpayment_orderdetails_textview);
            orderTypeTextView = itemView.findViewById(R.id.recycleview_item_activity_outpatientpayment_ordertype_textview);
            itemCostIdTextView = itemView.findViewById(R.id.recycleview_item_activity_outpatientpayment_itemcostid_textview);
            payStateTextView = itemView.findViewById(R.id.recycleview_item_activity_outpatientpayment_paystate_textview);
            costTextView = itemView.findViewById(R.id.recycleview_item_activity_outpatientpayment_cost_textview);
            cancelOrPayTimeTextView = itemView.findViewById(R.id.recycleview_item_activity_outpatientpayment_cancelorpaytime_textview);
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycleview_item_activity_allpayment, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedDoctorPosition = viewHolder.getAdapterPosition();
                enterPaymentDetailsActivity(selectedDoctorPosition);
            }

        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (paymentSimpleDataList.size() > 0) {
            PaymentSimpleData selectedPaymentSimpleData = paymentSimpleDataList.get(position);
            holder.orderDetailsTextView.setText(getPaymenItemNameByItemType(selectedPaymentSimpleData.getPaymentItemType()));
            holder.orderTypeTextView.setText(geOrderTypeByItemType(selectedPaymentSimpleData.getPaymentItemType()));
            holder.itemCostIdTextView.setText(selectedPaymentSimpleData.getItemCostId());
            holder.payStateTextView.setText(getPaymenItemPayStateByItemPayState(selectedPaymentSimpleData.getPayState()));
            holder.costTextView.setText(selectedPaymentSimpleData.getCost() + "");
            holder.cancelOrPayTimeTextView.setText(Utility.handleStrDate(selectedPaymentSimpleData.getPayOrCancelTime()));
        }
    }

    @Override
    public int getItemCount() {
        return paymentSimpleDataList.size();
    }

    private void enterPaymentDetailsActivity(int selectedItemPosition) {
        PaymentSimpleData selectedPaymentSimpleData = paymentSimpleDataList.get(selectedItemPosition);
        // 1 references drugCost, 2 references operationCost  3 references checkInspectionCost. 4 references registerCost
        Integer paymentItemType = selectedPaymentSimpleData.getPaymentItemType();
        AppDataUtil.setItemCostId(selectedPaymentSimpleData.getItemCostId());
        if (paymentItemType.equals(Utility.CHARGE_ITEM_TYPE_REGISTER)) {
            AppDataUtil.setRegistrationNo(selectedPaymentSimpleData.getItemCostId()); // the itemCostId is registrationNo;
            AppDataUtil.setOrderState(selectedPaymentSimpleData.getPayState());
            Intent intent = new Intent(activity,RegisterDetailsActivity.class);
            activity.startActivity(intent);
        } else if (paymentItemType.equals(Utility.CHARGE_ITEM_TYPE_DRUGCOST)) {
            //enter DrugDetailsActivity
            Intent intent = new Intent(activity, DrugDetailsActivity.class);
            activity.startActivity(intent);
        } else if (paymentItemType.equals(Utility.CHARGE_ITEM_TYPE_OPERATIONCOST)) {
            //enter OperationDetailsActivity
            Intent intent = new Intent(activity, OperationDetailsActivity.class);
            activity.startActivity(intent);
        } else if (paymentItemType.equals(Utility.CHARGE_ITEM_TYPE_CHECKINSPECTIONCOST)) {
            //enter CheckInspectionDetailsActivity
            Intent intent = new Intent(activity, CheckInspectionDetailsActivity.class);
            activity.startActivity(intent);
        } else if (paymentItemType.equals(Utility.CHARGE_ITEM_TYPE_REGISTER)) {
            //enter RegisterDetailsActivity
            Intent intent = new Intent(activity, RegisterDetailsActivity.class);
            activity.startActivity(intent);
        }

    }

    private String getPaymenItemNameByItemType(Integer paymentItemType) {
        // 1 references drugCost, 2 references operationCost  3 references checkInspectionCost.
        String paymentItemTypeStr = "其他订单";
        if (paymentItemType.equals(Utility.CHARGE_ITEM_TYPE_REGISTER)) {
            paymentItemTypeStr = "挂号单";
        } else if (paymentItemType.equals(Utility.CHARGE_ITEM_TYPE_DRUGCOST)) {
            paymentItemTypeStr = "药品单";
        } else if (paymentItemType.equals(Utility.CHARGE_ITEM_TYPE_OPERATIONCOST)) {
            paymentItemTypeStr = "手术单";
        } else if (paymentItemType.equals(Utility.CHARGE_ITEM_TYPE_CHECKINSPECTIONCOST)) {
            paymentItemTypeStr = "检查检验单";
        }
        return paymentItemTypeStr;
    }

    private String geOrderTypeByItemType(Integer paymentItemType) {
        String orderTypeStr = "未知单号";
        if (paymentItemType.equals(Utility.CHARGE_ITEM_TYPE_REGISTER)) {
            orderTypeStr = "挂号单单号";
        } else if (paymentItemType.equals(Utility.CHARGE_ITEM_TYPE_DRUGCOST)) {
            orderTypeStr = "药品单单号";
        } else if (paymentItemType.equals(Utility.CHARGE_ITEM_TYPE_OPERATIONCOST)) {
            orderTypeStr = "手术单单号";
        } else if (paymentItemType.equals(Utility.CHARGE_ITEM_TYPE_CHECKINSPECTIONCOST)) {
            orderTypeStr = "检查检验单单号";
        }
        return orderTypeStr;
    }

    private String getPaymenItemPayStateByItemPayState(Integer paystate) {
        // 1 references drugCost, 2 references operationCost  3 references checkInspectionCost.
        String paymentItemTypeStr = "异常状态";
        if (paystate.equals(0)) {
            paymentItemTypeStr = "待支付";
        } else if (paystate.equals(1)) {
            paymentItemTypeStr = "已支付";
        } else if (paystate.equals(2)) {
            paymentItemTypeStr = "已取消";
        }
        return paymentItemTypeStr;
    }

}
