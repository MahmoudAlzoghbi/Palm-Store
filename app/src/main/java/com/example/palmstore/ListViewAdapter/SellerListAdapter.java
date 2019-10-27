package com.example.palmstore.ListViewAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.palmstore.ConfirmationActivity;
import com.example.palmstore.R;
import com.example.palmstore.SellerBillActivity;

import java.util.ArrayList;
import java.util.List;

public class SellerListAdapter extends ArrayAdapter<SellerRow> {


    private AppCompatActivity context;
    private List<SellerRow> sellerRows;

    public SellerListAdapter (AppCompatActivity context , List<SellerRow> sellerRows , int resource){
        super(context , resource, sellerRows);

        this.context = context;
        this.sellerRows = sellerRows;
    }

    @Override
    public SellerRow getItem (int position){
        return sellerRows.get(position);
    }

    @Override
    public View getView(final int position , View convertView , ViewGroup parent){
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null){
            convertView = inflater.inflate(R.layout.list_seller_view , parent , false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        /*double price = getItem(position).getPrice() + (getItem(position).getPrice()/10);*/
        holder.sellername.setText(getItem(position).getName());
        holder.sellerPrice.setText(String.valueOf(getItem(position).getPrice()));

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = getItem(position).getName();
                Intent intent = new Intent(getContext() , ConfirmationActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("Name" , name);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    private static class ViewHolder{
        private TextView sellername;
        private TextView sellerPrice;
        private CheckBox checkBox;

        public ViewHolder(View view){
            sellername = view.findViewById(R.id.sellerName);
            sellerPrice = view.findViewById(R.id.sellerBillPrice);
            checkBox = view.findViewById(R.id.finishing);
        }
    }
}
