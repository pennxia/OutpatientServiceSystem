package com.nobitastudio.materialdesign.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllPayment implements Serializable {

    @SerializedName("clientRegistrationCosts")
    List<ClientRegistrationCost> clientRegistrationCosts;

    @SerializedName("clientCheckInspectionCosts")
    List<ClientCheckInspectionCost> clientCheckInspectionCosts;

    @SerializedName("clientOperationCosts")
    List<ClientOperationCost> clientOperationCosts;

    @SerializedName("clientDrugCosts")
    List<ClientDrugCost> clientDrugCosts;

    public AllPayment() {
    }

    public List<ClientRegistrationCost> getClientRegistrationCosts() {
        return clientRegistrationCosts;
    }

    public void setClientRegistrationCosts(List<ClientRegistrationCost> clientRegistrationCosts) {
        this.clientRegistrationCosts = clientRegistrationCosts;
    }

    public List<ClientCheckInspectionCost> getClientCheckInspectionCosts() {
        return clientCheckInspectionCosts;
    }

    public void setClientCheckInspectionCosts(List<ClientCheckInspectionCost> clientCheckInspectionCosts) {
        this.clientCheckInspectionCosts = clientCheckInspectionCosts;
    }

    public List<ClientOperationCost> getClientOperationCosts() {
        return clientOperationCosts;
    }

    public void setClientOperationCosts(List<ClientOperationCost> clientOperationCosts) {
        this.clientOperationCosts = clientOperationCosts;
    }

    public List<ClientDrugCost> getClientDrugCosts() {
        return clientDrugCosts;
    }

    public void setClientDrugCosts(List<ClientDrugCost> clientDrugCosts) {
        this.clientDrugCosts = clientDrugCosts;
    }

}
