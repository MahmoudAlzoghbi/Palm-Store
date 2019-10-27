package com.example.palmstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.palmstore.CurrentDate.CurrentDate;
import com.example.palmstore.ListViewAdapter.PalmRow;
import com.example.palmstore.ListViewAdapter.ViewAdapter;
import com.example.palmstore.SQLiteModels.SQLiteConnection;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        CurrentDate currentDate = new CurrentDate();
        String date = currentDate.getCurrentDate();
        System.out.println(date);

        SQLiteConnection sqLiteConnection = new SQLiteConnection(this);
        ListView listView = findViewById(R.id.ListView1);

        ArrayAdapter<PalmRow> adapter = new ViewAdapter(R.layout.item_list_view , MainActivity.this , sqLiteConnection.getAllDayWork());
        listView.setAdapter(adapter);
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
                Toast.makeText(this ,"تم اضافه البيعه بنجاح" , Toast.LENGTH_LONG).show();
                ListView listView = findViewById(R.id.ListView1);

                ArrayAdapter<PalmRow> adapter = new ViewAdapter(R.layout.item_list_view , MainActivity.this , sqLiteConnection.getAllDayWork());
                listView.setAdapter(adapter);
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
    public void onClickBtnShowMy(View view) {
        Intent intent = new Intent(this , OwnMoneyActivity.class);
        startActivity(intent);
    }
    ////////////////////////////
    public void onClickBtnShowFarmersBill(View view) {
        Intent intent = new Intent(MainActivity.this , FarmerBillActivity.class);
        startActivity(intent);
    }
    ///////////////////////
    public void onClickBtnShowSellersBill(View view) {
        Intent intent = new Intent(MainActivity.this , SellerBillActivity.class);
        startActivity(intent);
    }
}
