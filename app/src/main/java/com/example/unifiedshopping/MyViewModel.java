package com.example.unifiedshopping;

import android.media.MediaPlayer;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Collections;

public class MyViewModel extends ViewModel {


    private ArrayList<Order> mainList;
    private ArrayList<Order> hiddenList;
    private ArrayList<Order> dismissedList;
    private ArrayList<Order> deliveredList;


    //Constructor
    public MyViewModel()
    {
        mainList = new ArrayList<Order>();
        hiddenList = new ArrayList<Order>();
        dismissedList = new ArrayList<Order>();
        deliveredList = new ArrayList<Order>();
    }

    public void init(ArrayList<Order> data)
    {
        for (int i=0 ; i< data.size() ; i++)
        {
            Order t = data.get(i);
            if (t.getCurrStatus().contains("Delivered ")==true)
            {
                deliveredList.add(t);
            }
            else if (t.getVisibility()==0)
            {
                hiddenList.add(t);
            }
            else if (t.getVisibility()==-1)
            {
                dismissedList.add(t);
            }
            else
            {
                mainList.add(t);
            }

        }
    }

    // Changing statuses
    public void hideOrder(int pos){
        Order o = mainList.get(pos);
        mainList.remove(pos);
        hiddenList.add(o);
        Collections.sort(hiddenList);
        Collections.sort(mainList);
    }

    public void unHideOrder(int pos){
        Order o = hiddenList.get(pos);
        hiddenList.remove(pos);
        mainList.add(o);
        Collections.sort(mainList);
        Collections.sort(hiddenList);
    }

    public void stopTrackingOrder(int pos){
        Order o = mainList.get(pos);
        mainList.remove(pos);
        dismissedList.add(o);
        Collections.sort(dismissedList);
        Collections.sort(mainList);
    }

    public void trackOrder(int pos) {
        Order o = dismissedList.get(pos);
        dismissedList.remove(pos);
        mainList.add(o);
        Collections.sort(mainList);
        Collections.sort(dismissedList);
    }

    public void stopTrackingHidden(int pos) {
        Order o = hiddenList.get(pos);
        hiddenList.remove(pos);
        dismissedList.add(o);
        Collections.sort(mainList);
        Collections.sort(dismissedList);
    }

    public void sortMain()    {
        Collections.sort(mainList);
    }

    // Getters and Setters
    public ArrayList<Order> getMainList() {
        return mainList;
    }

    public ArrayList<Order> getHiddenList() {
        return hiddenList;
    }

    public ArrayList<Order> getDismissedList() {
        return dismissedList;
    }

    public ArrayList<Order> getDeliveredList() {
        return deliveredList;
    }



}
