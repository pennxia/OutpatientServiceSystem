package com.nobitastudio.materialdesign.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DoctorNote implements Serializable {

    @SerializedName("DoctorNoteId")
    private String DoctorNoteId;

    @SerializedName("note")
    private String note;

    public DoctorNote() {
    }

    public String getDoctorNoteId() {
        return DoctorNoteId;
    }

    public void setDoctorNoteId(String doctorNoteId) {
        DoctorNoteId = doctorNoteId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
