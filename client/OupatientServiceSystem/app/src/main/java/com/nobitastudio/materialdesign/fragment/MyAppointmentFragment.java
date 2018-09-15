package com.nobitastudio.materialdesign.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nobitastudio.materialdesign.R;
import com.nobitastudio.materialdesign.activity.MainActivity;
import com.nobitastudio.materialdesign.adapter.AllRegistrationRecycleViewAdapter;

public class MyAppointmentFragment extends Fragment {

    RecyclerView registerationRecyclerView;
    AllRegistrationRecycleViewAdapter adapter;
    MainActivity mainActivity;
    Context mContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myappointment,container,false);
        registerationRecyclerView = view.findViewById(R.id.fragment_myappointment_recyclerview);

        //init data
        mainActivity = (MainActivity) getActivity();
        mContext = mainActivity.getApplicationContext();

        adapter = new AllRegistrationRecycleViewAdapter(mainActivity);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext,1);
        registerationRecyclerView.setLayoutManager(gridLayoutManager);
        registerationRecyclerView.setAdapter(adapter);
        return view;
    }

    public AllRegistrationRecycleViewAdapter getAdapter() {
        return adapter;
    }
}
