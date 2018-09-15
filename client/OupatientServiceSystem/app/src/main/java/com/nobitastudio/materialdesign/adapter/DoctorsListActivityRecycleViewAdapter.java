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
import com.nobitastudio.materialdesign.activity.DoctorDetailsActivity;
import com.nobitastudio.materialdesign.bean.Doctor;
import com.nobitastudio.materialdesign.R;
import com.nobitastudio.materialdesign.activity.DoctorsListActivity;
import com.nobitastudio.materialdesign.bean.DoctorWhetherHasNumber;
import com.nobitastudio.materialdesign.util.AppDataUtil;
import com.nobitastudio.materialdesign.util.Utility;

import java.util.List;


public class DoctorsListActivityRecycleViewAdapter extends RecyclerView.Adapter<DoctorsListActivityRecycleViewAdapter.ViewHolder> {

    Context mContext;
    DoctorsListActivity activity;
    List<Doctor> doctors;
    List<DoctorWhetherHasNumber> doctorWhetherHasNumbers;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView doctorCircleImageView;
        TextView doctorNameTextView;
        TextView doctorLevelTextView;
        TextView doctorOutpatientDepartTextView;
        TextView subMajorTextView;
        TextView specialityTextView;
        FrameLayout doctorRegisterFramelayout;
        TextView doctorRegisterTextview;

        public ViewHolder(View itemView) {
            super(itemView);
            doctorCircleImageView = itemView.findViewById(R.id.recycleview_item_activity_doctors_list_doctor_imageview);
            doctorNameTextView = itemView.findViewById(R.id.recycleview_item_activity_doctors_list_doctorname_textview);
            doctorLevelTextView = itemView.findViewById(R.id.recycleview_item_activity_doctors_list_doctorlevel_textview);
            doctorOutpatientDepartTextView = itemView.findViewById(R.id.recycleview_item_activity_doctors_list_department_textview);
            subMajorTextView = itemView.findViewById(R.id.recycleview_item_activity_doctors_list_doctorsubmajor_textview);
            specialityTextView = itemView.findViewById(R.id.recycleview_item_activity_doctors_list_doctorspeciality_textview);
            doctorRegisterFramelayout = itemView.findViewById(R.id.recycleview_item_activity_doctors_list_canregister_framelayout);
            doctorRegisterTextview = itemView.findViewById(R.id.recycleview_item_activity_doctors_list_canregister_textview);
        }
    }

    public DoctorsListActivityRecycleViewAdapter(List<Doctor> doctors, List<DoctorWhetherHasNumber> doctorWhetherHasNumbers, DoctorsListActivity activity) {
        this.doctors = doctors;
        this.activity = activity;
        this.doctorWhetherHasNumbers = doctorWhetherHasNumbers;
        this.mContext = activity.getApplicationContext();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycleview_item_activity_doctorslist, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedDoctorPosition = viewHolder.getAdapterPosition();
                //click a doctor,then jump the details of the doctor
                enterDoctorDetailsActivity(selectedDoctorPosition);
            }

        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        //there need to judge the doctor whether can register
        if (doctors.size() > 0 && doctorWhetherHasNumbers.size() > 0) {
            final Doctor selectDoctor = doctors.get(position);
            //by network load doctor iamge,now use bingpic to test
            Glide.with(mContext).load(Utility.getDoctorImageRequestAddress(selectDoctor.getDoctorId())).into(holder.doctorCircleImageView);
            holder.doctorNameTextView.setText(selectDoctor.getDoctorName());
            holder.doctorLevelTextView.setText(selectDoctor.getDoctorLevel());
            holder.doctorOutpatientDepartTextView.setText(AppDataUtil.getSelectedOutpatientDepartment().getOutpatientDepartmentName());
            holder.subMajorTextView.setText(selectDoctor.getSubmajor());
            holder.specialityTextView.setText(selectDoctor.getSpecialty());

            for (int i = 0; i < doctorWhetherHasNumbers.size(); i++) {
                DoctorWhetherHasNumber doctorWhetherHasNumber = doctorWhetherHasNumbers.get(i);
                if (doctorWhetherHasNumber.getDoctorId() != null){
                    if (doctorWhetherHasNumber.getDoctorId().equals(selectDoctor.getDoctorId())) {
                        if (doctorWhetherHasNumber.isDoctorhasNumber()) {
                            holder.doctorRegisterFramelayout.setBackgroundResource(R.drawable.ic_circle_green);
                            holder.doctorRegisterTextview.setText("有号");
                            holder.doctorRegisterTextview.setTextColor(mContext.getResources().getColor(R.color.colorGreen2));
                        }
                        break;
                    }
                }
            }

        }

    }

    @Override
    public int getItemCount() {
        return doctors.size();
    }

    private void enterDoctorDetailsActivity(int position) {
        Intent intent = new Intent(activity, DoctorDetailsActivity.class);
        Doctor selectedDoctor = doctors.get(position);
        AppDataUtil.setSelectedDoctor(selectedDoctor);
        activity.startActivity(intent);
    }

}
