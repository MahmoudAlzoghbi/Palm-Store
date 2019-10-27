package com.example.palmstore.ListViewAdapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.palmstore.R;

import java.util.List;

public class ViewAdapter extends ArrayAdapter<PalmRow> {

        private AppCompatActivity context;
        private List<PalmRow> palmRows ;

    public ViewAdapter( int resource, AppCompatActivity context, List<PalmRow> palmRows) {
        super( context , resource , palmRows);
        this.context = context;
        this.palmRows = palmRows;
    }

    @Override
    public PalmRow getItem(int position){
        return palmRows.get(position);
    }

    @Override
    public View getView(int position , View convertView , ViewGroup parent){
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_list_view , parent , false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.price.setText(String.valueOf(getItem(position).getPrice()));
        holder.farmer.setText(getItem(position).getFarmer());
        holder.seller.setText(getItem(position).getSeller());

        return convertView;
    }
    private static class ViewHolder{
        private TextView price;
        private TextView farmer;
        private TextView seller;

        public ViewHolder(View view){
            price = view.findViewById(R.id.Price1);
            farmer = view.findViewById(R.id.FarmerName1);
            seller = view.findViewById(R.id.CustomerName1);
        }
    }
}
