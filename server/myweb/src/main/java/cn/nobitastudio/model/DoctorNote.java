package cn.nobitastudio.model;

import java.io.Serializable;

public class DoctorNote implements Serializable {

    private String DoctorNoteId;

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
