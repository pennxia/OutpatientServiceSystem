package com.nobitastudio.materialdesign.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nobitastudio.materialdesign.R;
import com.nobitastudio.materialdesign.activity.DoctorDetailsActivity;
import com.nobitastudio.materialdesign.activity.RegisterActivity;
import com.nobitastudio.materialdesign.bean.Visit;
import com.nobitastudio.materialdesign.util.AppDataUtil;
import com.nobitastudio.materialdesign.util.Utility;

import java.util.List;

public class DoctorDetailsActivityRecycleViewAdapter extends RecyclerView.Adapter<DoctorDetailsActivityRecycleViewAdapter.ViewHolder> {

    Context mContext;
    DoctorDetailsActivity activity;
    List<Visit> visitsDisplayed;
    String subMajor;

    public DoctorDetailsActivityRecycleViewAdapter(DoctorDetailsActivity activity, List<Visit> visitsDisplayed) {
        this.activity = activity;
        this.visitsDisplayed = visitsDisplayed;
        mContext = activity.getApplicationContext();
        subMajor = AppDataUtil.getSelectedDoctor().getSubmajor();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView dateTextView;
        TextView weekTextView;
        TextView timeTextView;
        TextView hospitalDistrictTextView;
        TextView subMajorTextView;
        FrameLayout framelayout;
        TextView canRegisterTextview;
        LinearLayout registerLinearlayout;

        public ViewHolder(View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.recycleview_item_activity_doctordetails_date);
            weekTextView = itemView.findViewById(R.id.recycleview_item_activity_doctordetails_week);
            timeTextView = itemView.findViewById(R.id.recycleview_item_activity_doctordetails_time);
            hospitalDistrictTextView = itemView.findViewById(R.id.recycleview_item_activity_doctordetails_hospitaldistrict);
            subMajorTextView = itemView.findViewById(R.id.recycleview_item_activity_doctordetails_submajor);
            framelayout = itemView.findViewById(R.id.recycleview_item_activity_doctordetails_framelayout);
            canRegisterTextview = itemView.findViewById(R.id.recycleview_item_activity_doctordetails_canregister_textview);
            registerLinearlayout = itemView.findViewById(R.id.recycleview_item_activity_doctordetails_linearlayout);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycleview_item_activity_doctordetails, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                enterLoginActivityOrStartRegisterActivity(position);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (visitsDisplayed.size() > 0) {
            Visit visit = visitsDisplayed.get(position);
            // check the visit whether can register
            if (visit.getLeftAmount().intValue() > 0) {
                // the visit can register
                holder.framelayout.setBackgroundResource(R.drawable.ic_circle_green);
                holder.canRegisterTextview.setText("预约");
                holder.canRegisterTextview.setTextColor(activity.getResources().getColor(R.color.colorGreen2));
            } else {
                //the visit can't register
                holder.framelayout.setBackgroundResource(R.drawable.ic_circle_red);
                holder.canRegisterTextview.setText("号满");
                holder.canRegisterTextview.setTextColor(activity.getResources().getColor(R.color.colorRed2));
            }
            holder.dateTextView.setText(Utility.handleYear(visit.getYears()));
            holder.weekTextView.setText(visit.getWeek());
            holder.timeTextView.setText(visit.getTimeSlot());
            holder.hospitalDistrictTextView.setText("石河子大学附属医院");
            holder.subMajorTextView.setText(subMajor);
        }
    }

    @Override
    public int getItemCount() {
        return visitsDisplayed.size();
    }

    private void enterLoginActivityOrStartRegisterActivity(int position) {
        // judge the user whether havs login if not enter LoginActivity,otherwise enter RegisterActivity
        Visit selectedVisit = visitsDisplayed.get(position);
        if (selectedVisit.getLeftAmount() > 0) {
            //user can register enter RegisterActivity
            AppDataUtil.setSelectedVisit(selectedVisit);
            Intent intent = new Intent(activity, RegisterActivity.class);
            activity.startActivity(intent);
        } else {
            //display user can't register the visit,choose other visit
            Utility.showToastShort(activity, "您选择的出诊安排已满，请另行选择~");
        }
    }


}
