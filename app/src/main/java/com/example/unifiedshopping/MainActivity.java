package com.example.unifiedshopping;

import static java.util.Objects.isNull;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements AdapterOrders.ItemClickListner,AdapterOrdersHidden.ItemClickListner,AdapterOrdersDismissed.ItemClickListner{

    private ArrayList<Order> test;
    private RecyclerView mainView;
    public RecyclerView.Adapter myAdapter;
    public LinearLayoutManager listlayoutManager;
    public MyViewModel dataholder;
    public TextView Header;
    public ImageButton homeBtn;
    public ImageButton completedBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataholder = new ViewModelProvider(this).get(MyViewModel.class);
        setContentView(R.layout.activity_main);
        mainView = findViewById(R.id.recyclerViewMain);
        Header = findViewById(R.id.textView);
        registerForContextMenu(mainView);
        setTop10();
        dataholder.init(test);
        setAdapter(dataholder.getMainList(),1);
        Header.setText("  Your Orders "+"("+Integer.toString(myAdapter.getItemCount())+")");
        homeBtn = findViewById(R.id.homeBtn);
        homeBtn.setImageResource(R.drawable.home);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAdapter(dataholder.getMainList(),1);
                Header.setText("  Your Orders "+"("+Integer.toString(myAdapter.getItemCount())+")");
            }
        });
        completedBtn = findViewById(R.id.historyBtn);
        completedBtn.setImageResource(R.drawable.history);
        completedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAdapter(dataholder.getDeliveredList(),4);
                Header.setText("  Completed Orders "+"("+Integer.toString(myAdapter.getItemCount())+")");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {

            case R.id.show_hidden:
            {
                setAdapter(dataholder.getHiddenList(),0);
                Header.setText("  Hidden Orders "+"("+Integer.toString(myAdapter.getItemCount())+")");
            }
            return true;

            case R.id.show_dismissed:
            {
                setAdapter(dataholder.getDismissedList(), -1);
                Header.setText("  Dismissed Orders "+"("+Integer.toString(myAdapter.getItemCount())+")");
            }
            return true;
            default:return super.onOptionsItemSelected(item);
        }
    }

    public void setAdapter(ArrayList<Order> X, int view_code){
        listlayoutManager = new LinearLayoutManager(this);
        mainView.setLayoutManager(listlayoutManager);
        mainView.setItemAnimator(new DefaultItemAnimator());
        Log.i("yash inside set adapter", Integer.toString(test.size()));
        if (view_code == 1)
            myAdapter = new AdapterOrders(X,this,this);
        else if (view_code == 0)
            myAdapter = new AdapterOrdersHidden(X,this,this);
        else if (view_code == -1)
            myAdapter = new AdapterOrdersDismissed(X,this,this);
        else if (view_code == 4)
            myAdapter = new AdapterOrdersCompleted(X,this);

        mainView.setAdapter(myAdapter);

        DividerItemDecoration divider = new DividerItemDecoration(mainView.getContext(),listlayoutManager.getOrientation());
        mainView.addItemDecoration(divider);

    }

    //Launch Order Detail activity
    @Override
    public void onItemClick(int pos) {

    }

    public void setTop10()
    {
        test = new ArrayList<Order>();
        Order o1 = new Order("Bnesi Personalized Photo Color Film Customization Personalized Gift Keychain, Customized UniqueMeaning Gift Customization For Family, Friends", "Delivered on March 4", "Akshay", "String datePlaced", "https://m.media-amazon.com/images/I/51itQaiJvpL._SS142_.jpg" , "Amazon");
        Order o2 = new Order("Odr 123", "Placed on April 8", "addr1", "String datePlaced1", "https://m.media-amazon.com/images/I/51itQaiJvpL._SS142_.jpg" , "Amazon");
        Order o3 = new Order("Abcdefg dummy order", "Placed on April 10", "addr1", "String datePlaced", "https://m.media-amazon.com/images/I/51itQaiJvpL._SS142_.jpg" , "Amazon");
        Order o4 = new Order("Cheese, Ramen and more", "Placed on April 8", "addr1", "String datePlaced1", "https://m.media-amazon.com/images/I/51itQaiJvpL._SS142_.jpg" , "Walmart");
        Order o5 = new Order("Green Tea", "Placed on April 12", "addr2", "String datePlaced1", "https://m.media-amazon.com/images/I/51itQaiJvpL._SS142_.jpg" , "Walmart");
        Order o6 = new Order("Light Saber", "Shipped on March 26", "addr1", "String datePlaced", "https://m.media-amazon.com/images/I/51itQaiJvpL._SS142_.jpg" , "Amazon");
        Order o7 = new Order("Salt and Pepper", "Shipped on April 1", "addr2", "String datePlaced1", "https://m.media-amazon.com/images/I/51itQaiJvpL._SS142_.jpg" , "Walmart");
        test.add(o1);
        test.add(o2);
        test.add(o3);
        test.add(o5);
        test.add(o6);
        test.add(o7);
        test.add(o4);
        /*for (int i=0 ; i<test.size() ; i++)
        {
            Log.i("Yash", test.get(i).getOrderName()+" "+test.get(i).getPriorityFlag() );
        }*/
        Collections.sort(test);
        /*for (int i=0 ; i<test.size() ; i++)
        {
            Log.i("Yash", test.get(i).getOrderName()+" "+test.get(i).getPriorityFlag() );
        }*/

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        int clkpos = item.getGroupId();
        Order t;// = dataholder.getMainList().get(clkpos);
        //Log.i("Yash", "clkpos: "+Integer.toString(clkpos)+" "+Integer.toString(item.getItemId()));
        switch(item.getItemId())
        {

            case 666:
                t = dataholder.getMainList().get(clkpos);
                if (t.getPriorityFlag()==1)
                {
                    Toast.makeText(this,"Notice: Order Priority is already set to High",Toast.LENGTH_LONG).show();
                    return true;
                }
                else {
                    //Log.i("Yash", "onContextItemSelected: "+Integer.toString(dataholder.getMainList().get(clkpos).getPriorityFlag()));
                    t.setPriorityFlag(1);
                    //Log.i("Yash", "onContextItemSelected: "+Integer.toString(dataholder.getMainList().get(clkpos).getPriorityFlag()));
                    Header.setText("  Your Orders "+"("+Integer.toString(myAdapter.getItemCount())+")");
                    Toast.makeText(this,"Priority set to High!",Toast.LENGTH_LONG).show();
                    dataholder.sortMain();
                    //listlayoutManager.scrollToPositionWithOffset(0,0);
                    mainView.smoothScrollToPosition(0);
                    //listlayoutManager.smoothScrollToPosition(mainView, new RecyclerView.State(), 0);
                }
                break;
            case 667:
                t = dataholder.getMainList().get(clkpos);
                if (t.getPriorityFlag()==0)
                {
                    Toast.makeText(this,"Notice: Order Priority is already set to Default",Toast.LENGTH_LONG).show();
                    return true;
                }
                else {
                    t.setPriorityFlag(0);
                    Toast.makeText(this,"Priority set to Default!",Toast.LENGTH_LONG).show();
                    Header.setText("  Your Orders "+"("+Integer.toString(myAdapter.getItemCount())+")");
                    dataholder.sortMain();
                    mainView.smoothScrollToPosition(dataholder.getMainList().indexOf(t));
                }
                break;
            case 668:
                t = dataholder.getMainList().get(clkpos);
                if (t.getPriorityFlag()==-1)
                {
                    Toast.makeText(this,"Notice: Order Priority is already set to Low",Toast.LENGTH_LONG).show();
                    return true;
                }
                else {
                    t.setPriorityFlag(-1);
                    Toast.makeText(this,"Priority set to Low!",Toast.LENGTH_LONG).show();
                    Header.setText("  Your Orders "+"("+Integer.toString(myAdapter.getItemCount())+")");
                    dataholder.sortMain();
                    mainView.smoothScrollToPosition(dataholder.getMainList().indexOf(t));
                }
                break;
            case 669:
                t = dataholder.getMainList().get(clkpos);
                t.setVisibility(0);
                dataholder.hideOrder(clkpos);
                Toast.makeText(this,"Order is now hidden!",Toast.LENGTH_LONG).show();
                Header.setText("  Your Orders "+"("+Integer.toString(myAdapter.getItemCount())+")");
                break;
            case 670:
                t = dataholder.getMainList().get(clkpos);
                t.setVisibility(-1);
                dataholder.stopTrackingOrder(clkpos);
                Toast.makeText(this,"Order dismissed and will not be tracked anymore!",Toast.LENGTH_LONG).show();
                Header.setText("  Your Orders "+"("+Integer.toString(myAdapter.getItemCount())+")");
                break;
            case 766:
                t = dataholder.getHiddenList().get(clkpos);
                t.setVisibility(1);
                dataholder.unHideOrder(clkpos);
                Toast.makeText(this,"Order now visible on homepage!",Toast.LENGTH_LONG).show();
                Header.setText("  Hidden Orders "+"("+Integer.toString(myAdapter.getItemCount())+")");
                break;
            case 870:
                t = dataholder.getHiddenList().get(clkpos);
                t.setVisibility(1);
                dataholder.stopTrackingHidden(clkpos);
                Toast.makeText(this,"Order dismissed and will not be tracked anymore",Toast.LENGTH_LONG).show();
                Header.setText("  Hidden Orders "+"("+Integer.toString(myAdapter.getItemCount())+")");
                break;
            case 866:
                t = dataholder.getDismissedList().get(clkpos);
                t.setVisibility(1);
                dataholder.trackOrder(clkpos);
                Toast.makeText(this,"Order is now being tracked!",Toast.LENGTH_LONG).show();
                Header.setText("  Dismissed Orders "+"("+Integer.toString(myAdapter.getItemCount())+")");
                break;


            default:  return super.onContextItemSelected(item);
        }
        myAdapter.notifyDataSetChanged();
        return true;
    }


}