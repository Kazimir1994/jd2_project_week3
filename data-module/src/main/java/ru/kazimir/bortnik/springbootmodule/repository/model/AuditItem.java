package ru.kazimir.bortnik.springbootmodule.repository.model;

import java.sql.Timestamp;

public class AuditItem {

    private String action;
    private Long itemID;
    private Timestamp date;
    private String updateStatus;

    @Override
    public String toString() {
        return "AuditItem{" +
                "action='" + action + '\'' +
                ", itemID=" + itemID +
                ", date=" + date +
                ", updateStatus='" + updateStatus + '\'' +
                '}';
    }


    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Long getItemID() {
        return itemID;
    }

    public void setItemID(Long itemID) {
        this.itemID = itemID;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(String updateStatus) {
        this.updateStatus = updateStatus;
    }
}
