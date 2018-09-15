package com.nobitastudio.materialdesign.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nobitastudio.materialdesign.R;
import com.nobitastudio.materialdesign.activity.DoctorDetailsActivity;
import com.nobitastudio.materialdesign.activity.MyDoctorActivity;
import com.nobitastudio.materialdesign.bean.ClientDoctor;
import com.nobitastudio.materialdesign.bean.Doctor;
import com.nobitastudio.materialdesign.bean.OutpatientDepartment;
import com.nobitastudio.materialdesign.util.AppDataUtil;
import com.nobitastudio.materialdesign.util.HttpUtil;
import com.nobitastudio.materialdesign.util.Utility;

import java.util.List;

public class MyDoctorActivityRecycleViewAdapter extends RecyclerView.Adapter<MyDoctorActivityRecycleViewAdapter.ViewHolder> {

    MyDoctorActivity activity;
    Context mContext;
    List<ClientDoctor> clientDoctors;

    public MyDoctorActivityRecycleViewAdapter(MyDoctorActivity activity, List<ClientDoctor> clientDoctors) {
        mContext = activity.getApplicationContext();
        this.clientDoctors = clientDoctors;
        this.activity = activity;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView doctorNameTextView;
        ImageView doctorCircleImageView;
        TextView hospitalNameTextView;
        TextView departmentNameTextView;
        TextView doctorLevelTextView;
        FrameLayout heartFrameLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            doctorNameTextView = itemView.findViewById(R.id.activity_my_doctor_recycleview_item_doctorname_textview);
            doctorCircleImageView = itemView.findViewById(R.id.activity_my_doctor_recycleview_item_doctor_imageview);
            hospitalNameTextView = itemView.findViewById(R.id.activity_my_doctor_recycleview_item_doctorhospital_textview);
            departmentNameTextView = itemView.findViewById(R.id.activity_my_doctor_recycleview_item_doctordepartment_textview);
            doctorLevelTextView = itemView.findViewById(R.id.activity_my_doctor_recycleview_item_doctorlevel_textview);
            heartFrameLayout = itemView.findViewById(R.id.activity_my_doctor_recycleview_item_heart_framelayout);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycleview_item_activity_mydoctor, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //enter registration details activity.
                int selectedDoctorPosition = viewHolder.getAdapterPosition();
                enterDocterDetailsActivity(selectedDoctorPosition);
            }

        });
        viewHolder.heartFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ask the user whether to cancel collect the doctor.
                int selectedDoctorPosition = viewHolder.getAdapterPosition();
                activity.showSnackbarLong(parent, "确定取消收藏该医生吗？", "确定", selectedDoctorPosition);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyDoctorActivityRecycleViewAdapter.ViewHolder holder, int position) {
        if (clientDoctors.size() > 0) {
            Doctor doctor = clientDoctors.get(position).getDoctor();
            OutpatientDepartment outpatientDepartment = clientDoctors.get(position).getOutpatientDepartment();
            Glide.with(mContext).load(Utility.getDoctorImageRequestAddress(doctor.getDoctorId())).into(holder.doctorCircleImageView);
            holder.doctorNameTextView.setText(doctor.getDoctorName());
            holder.hospitalNameTextView.setText(clientDoctors.get(position).getHospitalName());
            holder.departmentNameTextView.setText(outpatientDepartment.getOutpatientDepartmentName());
            holder.doctorLevelTextView.setText(doctor.getDoctorLevel());
        }
    }

    @Override
    public int getItemCount() {
        return clientDoctors.size();
    }

    private void enterDocterDetailsActivity(int selectedDoctorPosition) {
        Intent intent = new Intent(activity, DoctorDetailsActivity.class);
        Doctor selectedDoctor = clientDoctors.get(selectedDoctorPosition).getDoctor();
        OutpatientDepartment selectedOutpatientDepartment = clientDoctors.get(selectedDoctorPosition).getOutpatientDepartment();
        AppDataUtil.setSelectedDoctor(selectedDoctor);
        AppDataUtil.setSelectedOutpatientDepartment(selectedOutpatientDepartment);
        activity.startActivity(intent);
    }

    /**
     * cancel the user's doctor collection,
     *
     * @param selectedPosition
     */
    public void removeDoctorFromMydoctor(int selectedPosition) {
        String account = AppDataUtil.getUser().getUserAccount();
        Integer doctorId = clientDoctors.get(selectedPosition).getDoctor().getDoctorId();
        String requestAction = "removeDoctorFromMyDoctor";
        String parameter = "&account=" + account + "&doctorId=" + doctorId;
        HttpUtil.onlySendHttpRequest(requestAction, parameter);
    }

}
