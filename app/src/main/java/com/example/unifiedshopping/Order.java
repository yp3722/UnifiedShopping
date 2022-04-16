package com.example.unifiedshopping;

public class Order implements Comparable<Order>{


    private int priorityFlag = 0;   // -1 = low/muted , 0 = default , 1 = high
    private int visibility = 1;   //1 = visible, 0 = hidden , -1 = dismissed
    //private String orderID;
    private String orderName;
    private String currStatus;
    private String addressName;
    private String datePlaced;
    private String orderImg;

    private String vendorName;

    //Constructor
    public Order( String orderName, String currStatus, String addressName, String datePlaced, String orderImg , String vendorName) {
        //this.orderID = orderID;
        this.orderName = orderName;
        this.currStatus = currStatus;
        this.addressName = addressName;
        this.datePlaced = datePlaced;
        this.orderImg = orderImg;
        this.vendorName = vendorName;
    }
    //Compare
    @Override
    public int compareTo(Order o) {
        if (this.priorityFlag == o.getPriorityFlag())
            return o.getCurrStatus().compareTo(this.currStatus);
        else if (this.priorityFlag > o.getPriorityFlag())
            return -1;
        else
            return 1;

    }

    //Getters and Setters

    public int getPriorityFlag() {
        return priorityFlag;
    }

    public void setPriorityFlag(int priorityFlag) {
        this.priorityFlag = priorityFlag;
    }

    /*public String getOrderID() {
       return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }*/

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getCurrStatus() {
        return currStatus;
    }

    public void setCurrStatus(String currStatus) {
        this.currStatus = currStatus;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getDatePlaced() {
        return datePlaced;
    }

    public void setDatePlaced(String datePlaced) {
        this.datePlaced = datePlaced;
    }

    public String getOrderImg() {
        return orderImg;
    }

    public void setOrderImg(String orderImg) {
        this.orderImg = orderImg;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }


    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }
}
