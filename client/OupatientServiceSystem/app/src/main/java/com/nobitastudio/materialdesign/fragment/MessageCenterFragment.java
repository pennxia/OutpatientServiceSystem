package com.nobitastudio.materialdesign.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nobitastudio.materialdesign.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageCenterFragment extends Fragment implements View.OnClickListener {

    LinearLayout diagnosisMessageLinearLayout;
    LinearLayout appointmentMessageLinearLayout;
    LinearLayout checkInspectionMessageLinearLayout;
    LinearLayout paymentMessageLinearLayout;
    LinearLayout serviceMessageLinearLayout;
    LinearLayout announcementMessageLinearLayout;
    ImageView diagnosisDotImageView;
    ImageView appointmentDotImageView;
    ImageView checkInspectionDotImageView;
    ImageView paymentDotImageView;
    ImageView serviceDotImageView;
    ImageView announcementDotImageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_messagecenter, container, false);

        //initBaseData
        initBaseData();

        //init widget
        diagnosisMessageLinearLayout = view.findViewById(R.id.fragment_personalcenter_diagnosis_linearlayout);
        appointmentMessageLinearLayout = view.findViewById(R.id.fragment_personalcenter_appointment_linearlayout);
        checkInspectionMessageLinearLayout = view.findViewById(R.id.fragment_personalcenter_checkinspection_linearlayout);
        paymentMessageLinearLayout = view.findViewById(R.id.fragment_personalcenter_payment_linearlayout);
        serviceMessageLinearLayout = view.findViewById(R.id.fragment_personalcenter_service_linearlayout);
        announcementMessageLinearLayout = view.findViewById(R.id.fragment_personalcenter_announcement_linearlayout);
        diagnosisDotImageView = view.findViewById(R.id.fragment_personalcenter_diagnosisdot_imageview);
        appointmentDotImageView = view.findViewById(R.id.fragment_personalcenter_appointmentdot_imageview);
        checkInspectionDotImageView = view.findViewById(R.id.fragment_personalcenter_checkinspectiondot_imageview);
        paymentDotImageView = view.findViewById(R.id.fragment_personalcenter_paymentdot_imageview);
        serviceDotImageView = view.findViewById(R.id.fragment_personalcenter_servicedot_imageview);
        announcementDotImageView = view.findViewById(R.id.fragment_personalcenter_announcementdot_imageview);

        //init listener
        diagnosisMessageLinearLayout.setOnClickListener(this);
        appointmentMessageLinearLayout.setOnClickListener(this);
        checkInspectionMessageLinearLayout.setOnClickListener(this);
        paymentMessageLinearLayout.setOnClickListener(this);
        serviceMessageLinearLayout.setOnClickListener(this);
        announcementMessageLinearLayout.setOnClickListener(this);

        return view;
    }

    private void initBaseData() {

    }


    @Override
    public void onClick(View v) {

        int viewId = v.getId();
        if (viewId == R.id.fragment_personalcenter_diagnosis_linearlayout) {
            //enter diagnosisDetailsActivity

        } else if (viewId == R.id.fragment_personalcenter_appointment_linearlayout) {
            //enter appointmentDetailsActivity

        } else if (viewId == R.id.fragment_personalcenter_checkinspection_linearlayout) {
            //enter checkinSpectionDetailsActivity

        } else if (viewId == R.id.fragment_personalcenter_payment_linearlayout) {
            //enter paymentDetailsActivity

        } else if (viewId == R.id.fragment_personalcenter_service_linearlayout) {
            //serviceDetailsActivity

        } else if (viewId == R.id.fragment_personalcenter_announcement_linearlayout) {
            //announcementDetailsActivity

        }

    }


}
