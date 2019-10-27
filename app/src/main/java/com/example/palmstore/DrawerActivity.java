package com.example.palmstore;

import android.content.Intent;
import android.os.Bundle;

import com.example.palmstore.CurrentDate.CurrentDate;
import com.example.palmstore.ListViewAdapter.PalmRow;
import com.example.palmstore.ListViewAdapter.ViewAdapter;
import com.example.palmstore.SQLiteModels.SQLiteConnection;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        /////////////////////////////////////////////////
        CurrentDate currentDate = new CurrentDate();
        String date = currentDate.getCurrentDate();
        System.out.println(date);

        SQLiteConnection sqLiteConnection = new SQLiteConnection(this);
        ListView listView = findViewById(R.id.ListView);

        ArrayAdapter<PalmRow> adapter = new ViewAdapter(R.layout.item_list_view , DrawerActivity.this , sqLiteConnection.getAllDayWork());
        listView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent intent = new Intent(this , OwnMoneyActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(DrawerActivity.this , SellerBillActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_slideshow) {
            Intent intent = new Intent(DrawerActivity.this , FarmerBillActivity.class);
            startActivity(intent);

        }  else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public String getFarmerName(){
        EditText FarmerEditText = findViewById(R.id.FarmerName);
        String name = FarmerEditText.getText().toString().trim();
        return name;
    }

    public String getSellerName(){
        EditText SellerEditText = findViewById(R.id.CustomerName);
        String name = SellerEditText.getText().toString().trim();
        return name;
    }

    public double getPrice(){
        EditText PriceEditText = findViewById(R.id.Price);
        double price = Double.parseDouble(PriceEditText.getText().toString().trim());
        return price;
    }

    public void onClickBtnSave(View view) {
        SQLiteConnection sqLiteConnection = new SQLiteConnection(this);

        if (Validate()){
            String farmerName = getFarmerName();
            String sellerName = getSellerName();
            double price = getPrice();

            boolean valid = sqLiteConnection.insertData(price , farmerName , sellerName);

            if (valid){
                ListView listView = findViewById(R.id.ListView);

                ArrayAdapter<PalmRow> adapter = new ViewAdapter(R.layout.item_list_view , DrawerActivity.this , sqLiteConnection.getAllDayWork());
                listView.setAdapter(adapter);

                Toast.makeText(this ,"تم اضافه البيعه بنجاح" , Toast.LENGTH_LONG).show();
            }
        }

    }

    public boolean Validate(){
        EditText PriceEditText = findViewById(R.id.Price);

        if (getFarmerName().isEmpty()){
            EditText FarmerEditText = findViewById(R.id.FarmerName);
            FarmerEditText.setError("من فضلك ادخل اسم الفلاح");
            return false;
        }if (getSellerName().isEmpty()){
            EditText SellerEditText = findViewById(R.id.CustomerName);
            SellerEditText.setError("من فضلك ادخل اسم التاجر");
            return false;
        }if (PriceEditText.getText().toString().isEmpty()){
            PriceEditText.setError("من فضلك ادخل سعر البلح");
            return false;
        }
        return true;
    }
    ////////////////////////////////////
    /*public void onClickBtnShowMy(View view) {
        Intent intent = new Intent(this , OwnMoneyActivity.class);
        startActivity(intent);
    }*/
    ////////////////////////////
    /*public void onClickBtnShowFarmersBill(View view) {
        Intent intent = new Intent(DrawerActivity.this , FarmerBillActivity.class);
        startActivity(intent);
    }
    ///////////////////////
    public void onClickBtnShowSellersBill(View view) {
        Intent intent = new Intent(DrawerActivity.this , SellerBillActivity.class);
        startActivity(intent);
    }*/
}
