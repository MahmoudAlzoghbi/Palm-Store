package com.example.palmstore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.palmstore.ListViewAdapter.PalmRow;
import com.example.palmstore.ListViewAdapter.SellerListAdapter;
import com.example.palmstore.ListViewAdapter.SellerRow;
import com.example.palmstore.SQLiteModels.SQLiteConnection;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class SellerBillActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_bill);

        SQLiteConnection sqLiteConnection = new SQLiteConnection(this);

        ListView listView = findViewById(R.id.List_Sellers_Bills);

        List<SellerRow> sellerRows;
        sellerRows = sqLiteConnection.getAllSellerPrices();
        ArrayAdapter<SellerRow> adapter = new SellerListAdapter(SellerBillActivity.this , sellerRows , R.layout.list_seller_view);
        listView.setAdapter(adapter);
    }
}
