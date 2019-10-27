package com.example.palmstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.palmstore.SQLiteModels.SQLiteConnection;

public class OwnMoneyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_own_money);

        SQLiteConnection sqLiteConnection = new SQLiteConnection(this);
        double price = sqLiteConnection.getOwnPrice();

        TextView textView = findViewById(R.id.set_own_price);

        textView.setText(String.valueOf(price));

    }

    public void onClickBtnGoBackHomePage(View view) {
        Intent intent = new Intent(this , DrawerActivity.class);
        startActivity(intent);
    }
}
