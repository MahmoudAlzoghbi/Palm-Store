package com.example.palmstore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.palmstore.ListViewAdapter.PalmRow;
import com.example.palmstore.ListViewAdapter.SellerListAdapter;
import com.example.palmstore.ListViewAdapter.SellerRow;
import com.example.palmstore.SQLiteModels.SQLiteConnection;

import java.util.ArrayList;
import java.util.List;

public class FarmerBillActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_bill);

        SQLiteConnection sqLiteConnection = new SQLiteConnection(this);

        ListView listView = findViewById(R.id.List_Farmers_Bills);

        List<SellerRow> sellerRows;
        sellerRows = sqLiteConnection.getAllFarmerPrices();
        ArrayAdapter<SellerRow> adapter = new SellerListAdapter(FarmerBillActivity.this , sellerRows , R.layout.list_seller_view);
        listView.setAdapter(adapter);}
}
