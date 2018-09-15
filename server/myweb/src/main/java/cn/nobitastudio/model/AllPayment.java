package cn.nobitastudio.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllPayment implements Serializable {

    List<ClientRegistrationCost> clientRegistrationCosts;
    List<ClientCheckInspectionCost> clientCheckInspectionCosts;
    List<ClientOperationCost> clientOperationCosts;
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
