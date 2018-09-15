package com.nobitastudio.materialdesign.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nobitastudio.materialdesign.R;
import com.nobitastudio.materialdesign.activity.MedicalCardDetailsActivity;
import com.nobitastudio.materialdesign.activity.MyMedicalCardsActivity;
import com.nobitastudio.materialdesign.bean.MedicalCard;
import com.nobitastudio.materialdesign.util.AppDataUtil;

import java.util.List;

public class MyMedicalCardsActivityRecycleViewAdapter extends RecyclerView.Adapter<MyMedicalCardsActivityRecycleViewAdapter.ViewHolder> {

    Context mContext;
    List<MedicalCard> medicalCards;
    MyMedicalCardsActivity activity;

    public MyMedicalCardsActivityRecycleViewAdapter(MyMedicalCardsActivity activity, List<MedicalCard> medicalCards) {
        this.medicalCards = medicalCards;
        this.activity = activity;
        mContext = activity.getApplicationContext();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView medicalCardOwnerName;
        TextView medicalCardNo;

        public ViewHolder(View itemView) {
            super(itemView);
            medicalCardOwnerName = itemView.findViewById(R.id.recycleview_item_activity_mymedicalcards_ownername_textview);
            medicalCardNo = itemView.findViewById(R.id.recycleview_item_activity_mymedicalcards_medicalcardno_textview);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycleview_item_activity_mymedicalcards, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedMedicalCardPosition = viewHolder.getAdapterPosition();
                if (AppDataUtil.entranceActivityIsMainActivity() || AppDataUtil.entranceActivityIsPersonalCenterActivity()) {
                    //click a medicalcard,enter medicalCardDetaislActivity(),contain unbind medicalcard.
                    enterMedicalCardDetailsActivity(selectedMedicalCardPosition);
                } else if (AppDataUtil.entranceActivityIsRegisterActivity()) {
                    //return selectedMedicalCard
                    returnRegisterActivity(selectedMedicalCardPosition);
                }
            }
        });
        return viewHolder;
    }

    private void enterMedicalCardDetailsActivity(int selectedMedicalCardPosition) {
        MedicalCard selectedMedicalCard = medicalCards.get(selectedMedicalCardPosition);
        AppDataUtil.setSelectedMedicalCard(selectedMedicalCard);
        Intent intent = new Intent(activity, MedicalCardDetailsActivity.class);
        activity.startActivity(intent);
    }

    private void returnRegisterActivity(int selectedMedicalCardPosition) {
        MedicalCard selectedMedicalCard = medicalCards.get(selectedMedicalCardPosition);
        AppDataUtil.setSelectedMedicalCard(selectedMedicalCard);
        activity.setResult(activity.RESULT_OK);
        activity.finish();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (medicalCards.size() > 0) {
            MedicalCard medicalCard = medicalCards.get(position);
            holder.medicalCardOwnerName.setText(medicalCard.getOwnerName());
            holder.medicalCardNo.setText(medicalCard.getMedicalCardNo().toString());
        }
    }

    @Override
    public int getItemCount() {
        return medicalCards.size();
    }


}
