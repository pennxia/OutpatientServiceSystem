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
import com.nobitastudio.materialdesign.activity.DoctorsListActivity;
import com.nobitastudio.materialdesign.activity.OutpatientIntroductionActivity;
import com.nobitastudio.materialdesign.bean.OutpatientDepartment;
import com.nobitastudio.materialdesign.util.AppDataUtil;
import com.nobitastudio.materialdesign.util.Utility;

import java.util.List;


public class OutpatientRecycleViewAdapter extends RecyclerView.Adapter<OutpatientRecycleViewAdapter.ViewHolder> {

    Context mContext;
    AppCompatActivity activity;
    List<OutpatientDepartment> outpatientDepartments;

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.recycleview_item_activity_outpatientdepartment_textview);
        }
    }

    public OutpatientRecycleViewAdapter(AppCompatActivity activity, List<OutpatientDepartment> outpatientDepartments) {
        this.activity = activity;
        this.outpatientDepartments = outpatientDepartments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }

        View view = LayoutInflater.from(mContext).inflate(R.layout.recycleview_item_activity_outpatientdepartment, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        if (AppDataUtil.entranceActivityIsMainActivity()) {
            // this activity is outpatientDepartmentActivity.
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = viewHolder.getAdapterPosition();
                    enterDoctorListActivity(activity, position);

                }

            });
        } else if (AppDataUtil.entranceActivityIsHospitalHomeFragment()){
            //  this activity is navigationHospitalGuideActivity.
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = viewHolder.getAdapterPosition();
                    AppDataUtil.setSelectedOutpatientDepartment(outpatientDepartments.get(position));
                    Intent intent = new Intent(activity, OutpatientIntroductionActivity.class);
                    activity.startActivity(intent);
                }
            });
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(outpatientDepartments.get(position).getOutpatientDepartmentName());
    }

    @Override
    public int getItemCount() {
        return outpatientDepartments.size();
    }

    private void enterDoctorListActivity(AppCompatActivity activity, int position) {
        Intent intent = new Intent(activity, DoctorsListActivity.class);
        AppDataUtil.setSelectedOutpatientDepartment(outpatientDepartments.get(position));
        activity.startActivity(intent);
    }

}
