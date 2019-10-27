package com.example.palmstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import com.example.palmstore.SQLiteModels.SQLiteConnection;

public class ConfirmationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("Name");

        SQLiteConnection sqLiteConnection = new SQLiteConnection(this);

        int sellerId = sqLiteConnection.getSellerId(name);
        int farmerId = sqLiteConnection.getFarmerId(name);

        if (sellerId != 0){
            sqLiteConnection.UpdateSellerPrices(sellerId);
        }else if (farmerId != 0) {
            sqLiteConnection.UpdateFarmerPrices(farmerId);
        }
    }

    public void onClickBtnGoBackHome(View view) {
        Intent intent = new Intent(this , DrawerActivity.class);
        startActivity(intent);
    }
}
