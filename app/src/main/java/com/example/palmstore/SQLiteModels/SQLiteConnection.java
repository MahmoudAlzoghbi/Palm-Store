package com.example.palmstore.SQLiteModels;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.palmstore.ListViewAdapter.PalmRow;
import com.example.palmstore.ListViewAdapter.SellerRow;

import java.util.ArrayList;
import java.util.List;

public class SQLiteConnection extends SQLiteOpenHelper {

    public static final String DBName = "4thDBPalm.db";
    public static final int version = 1;

    public SQLiteConnection(Context context) {
        super(context, DBName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE \"Farmer\" (\n" +
                "\t\"farmerId\"\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                "\t\"name\"\tTEXT NOT NULL UNIQUE\n" +
                ");");

        sqLiteDatabase.execSQL("CREATE TABLE \"Seller\" (\n" +
                "\t\"sellerId\"\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                "\t\"name\"\tTEXT NOT NULL UNIQUE\n" +
                ");");

        sqLiteDatabase.execSQL("CREATE TABLE \"Price\" (\n" +
                "\t\"id\"\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                "\t\"price\"\tREAL NOT NULL,\n" +
                "\t\"FarmerId\"\tINTEGER,\n" +
                "\t\"SellerId\"\tINTEGER,\n" +
                "\t\"created_at\"\tTEXT DEFAULT CURRENT_TIMESTAMP,\n" +
                "\tFOREIGN KEY(\"FarmerId\") REFERENCES \"Farmer\"(\"farmerId\"),\n" +
                "\tFOREIGN KEY(\"SellerId\") REFERENCES \"Seller\"(\"sellerId\")\n" +
                ");");

        sqLiteDatabase.execSQL("CREATE TABLE \"SellerTotalPrice\" (\n" +
                "\t\"id\"\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                "\t\"price\"\tREAL NOT NULL,\n" +
                "\t\"sellerid\"\tINTEGER,\n" +
                "\t\"finish\"\tTEXT,\n" +
                "\tFOREIGN KEY(\"sellerid\") REFERENCES \"Seller\"(\"sellerId\")\n" +
                ");");

        sqLiteDatabase.execSQL("CREATE TABLE \"FTotalPrice\" (\n" +
                "\t\"id\"\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                "\t\"price\"\tREAL NOT NULL,\n" +
                "\t\"farmerID\"\tINTEGER,\n" +
                "\t\"finish\"\tTEXT,\n" +
                "\tFOREIGN KEY(\"farmerID\") REFERENCES \"Farmer\"(\"farmerId\")\n" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            onCreate(sqLiteDatabase);
    }

    public boolean insertData(double price , String farmer , String seller){

        SQLiteDatabase dbWrite = this.getWritableDatabase();

        ContentValues FarmerContentValues = new ContentValues();
        ContentValues SellerContentValues = new ContentValues();

        // kda ana 3malt save L El Farmer name W seller Name
        if (FoundFarmer(farmer).equals("FarmerFound") && FoundSeller(seller).equals("SellerFound")){
            int farmerId = getFarmerId(farmer);
            int sellerId = getSellerId(seller);

            insertPrice(farmerId , sellerId , price);

        }else if (FoundFarmer(farmer).equals("FarmerFound")){
            int farmerId = getFarmerId(farmer);

            SellerContentValues.put("name" , seller);
            dbWrite.insert("Seller" , null , SellerContentValues);
            int sellerId = getSellerId(seller);

            insertPrice(farmerId , sellerId , price);

        }else if (FoundSeller(seller).equals("SellerFound")){
            int sellerId = getSellerId(seller);

            FarmerContentValues.put("name" , farmer);
            dbWrite.insert("Farmer" , null , FarmerContentValues);
            int farmerId = getFarmerId(farmer);

            insertPrice(farmerId , sellerId , price);

        }else {
            FarmerContentValues.put("name" , farmer);
            SellerContentValues.put("name" , seller);

            dbWrite.insert("Farmer" , null , FarmerContentValues);
            dbWrite.insert("Seller" , null , SellerContentValues);

            int farmerId = getFarmerId(farmer);
            int sellerId = getSellerId(seller);

            insertPrice(farmerId , sellerId , price);
        }
        return true;
    }

    public int getFarmerId(String name){
        int farmerId = 0;
        SQLiteDatabase dbRead = this.getReadableDatabase();
        Cursor FarmerCursor = dbRead.rawQuery("select * from Farmer" , null);
        FarmerCursor.moveToFirst();
        for (int i = 0 ; i < FarmerCursor.getCount() ; i++ ){
            if (FarmerCursor.getString(1).equals(name)){
                farmerId = FarmerCursor.getInt(0);
            }
            FarmerCursor.moveToNext();
        }
        return farmerId;
    }

    public int getSellerId(String name){
        int SellerId = 0 ;
        SQLiteDatabase dbRead = this.getWritableDatabase();
        Cursor SellerCursor = dbRead.rawQuery("SELECT * from Seller" , null);
        SellerCursor.moveToFirst();
        for (int i = 0 ; i < SellerCursor.getCount() ; i++){
            if (SellerCursor.getString(1).equals(name)){
                SellerId = SellerCursor.getInt(0);
            }
            SellerCursor.moveToNext();
        }
        return SellerId;
    }

    public String FoundFarmer(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Farmer" , null);
        cursor.moveToFirst();
        for (int i = 0 ; i < cursor.getCount() ; i++ ){
            if (cursor.getString(1).equals(name)){
                return "FarmerFound";
            }
            cursor.moveToNext();
        }
        return "FarmerNotFound";
    }
    public String FoundSeller(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Seller" , null);
        cursor.moveToFirst();
        for (int i = 0 ; i < cursor.getCount() ; i++ ){
            if (cursor.getString(1).equals(name)){
                return "SellerFound";
            }
            cursor.moveToNext();
        }
        return "SellerNotFound";
    }

    public List<PalmRow> getAllDayWork(){
        List<PalmRow> palmRows = new ArrayList<>();
        List<Integer> farmersIds = new ArrayList<>();
        List<String> farmersNames = new ArrayList<>();
        List<Integer> sellersIds = new ArrayList<>();
        List<String> sellerNames = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor farmerCursor = db.rawQuery("select * from Farmer" , null);
        farmerCursor.moveToFirst();
        for (int i = 0 ; i < farmerCursor.getCount() ; i++ ){
            farmersIds.add(farmerCursor.getInt(0));
            farmersNames.add(farmerCursor.getString(1));
            farmerCursor.moveToNext();
        }

        Cursor sellerCursor = db.rawQuery("select * from Seller" , null);
        sellerCursor.moveToFirst();
        for (int i = 0 ; i < sellerCursor.getCount() ; i++ ){
            sellersIds.add(sellerCursor.getInt(0));
            sellerNames.add(sellerCursor.getString(1));
            sellerCursor.moveToNext();
        }

        Cursor priceCursor = db.rawQuery("select * from Price" , null);
        priceCursor.moveToFirst();
        for (int i = 0 ; i < priceCursor.getCount() ; i++ ){
            int price = priceCursor.getInt(1);
            String data = priceCursor.getString(4);
            data = data.substring(0 , data.indexOf(" "));
            //System.out.println(price);
            int indexOfFarmerName = farmersIds.indexOf(priceCursor.getInt(2));
            String nameFarmer = farmersNames.get(indexOfFarmerName);

            int indexOfSellerName = sellersIds.indexOf(priceCursor.getInt(3));
            String nameSeller = sellerNames.get(indexOfSellerName);

            palmRows.add(new PalmRow(price , nameFarmer , nameSeller , data));
            //System.out.println();
            priceCursor.moveToNext();
        }
        return palmRows;
    }

    public void insertPrice(int FarmerId , int SellerId , double price){
        SQLiteDatabase dbWrite = this.getWritableDatabase();

        ContentValues PricesContentValues = new ContentValues();
        ContentValues FTotalContentValues = new ContentValues();
        ContentValues STotalContentValues = new ContentValues();

        PricesContentValues.put("price" , price);
        PricesContentValues.put("SellerId" , SellerId);
        PricesContentValues.put("FarmerId" , FarmerId);

        FTotalContentValues.put("price" , price);
        FTotalContentValues.put("farmerID", FarmerId);
        FTotalContentValues.put("finish" , "notFinished");

        STotalContentValues.put("price" , price + (price/10) );
        STotalContentValues.put("sellerid" , SellerId);
        STotalContentValues.put("finish" , "notFinished");

        dbWrite.insert("Price" , null , PricesContentValues);
        dbWrite.insert("SellerTotalPrice" , null , STotalContentValues);
        dbWrite.insert("FTotalPrice" , null , FTotalContentValues);
    }

    public void UpdateSellerPrices(int id){
        ContentValues contentValues = new ContentValues();

        contentValues.put("finish" , "finished");

        SQLiteDatabase dbWrite = this.getWritableDatabase();

        dbWrite.update("SellerTotalPrice" , contentValues , "sellerid = " + id , null);
    }
    public void UpdateFarmerPrices(int id){
        ContentValues contentValues = new ContentValues();

        contentValues.put("finish" , "finished");

        SQLiteDatabase dbWrite = this.getWritableDatabase();

        dbWrite.update("FTotalPrice" , contentValues , "farmerID = " + id , null);
    }

    public List<SellerRow> getAllSellerPrices(){
        SQLiteDatabase db = this.getReadableDatabase();

        List<Integer> sellersIds = new ArrayList<>();
        List<String> sellerNames = new ArrayList<>();

        List<SellerRow> sellerRows = new ArrayList<>();

        Cursor sellerCursor = db.rawQuery("select * from Seller" , null);
        sellerCursor.moveToFirst();
        for (int i = 0 ; i < sellerCursor.getCount() ; i++ ){
            sellersIds.add(sellerCursor.getInt(0));
            sellerNames.add(sellerCursor.getString(1));
            sellerCursor.moveToNext();
        }
        Cursor cursorPrice = db.rawQuery("select * from SellerTotalPrice" , null);

        cursorPrice.moveToFirst();
        for (int i = 0 ; i < cursorPrice.getCount() ; i++ ){
            if (!cursorPrice.getString(3).equals("finished")){
                String name = sellerNames.get(sellersIds.indexOf(cursorPrice.getInt(2)));
                sellerRows.add(new SellerRow(cursorPrice.getDouble(1) , name));
            }
            cursorPrice.moveToNext();
        }
        return getUserPrice(sellerRows);
    }

    public List<SellerRow> getAllFarmerPrices(){
        SQLiteDatabase db = this.getReadableDatabase();

        List<Integer> sellersIds = new ArrayList<>();
        List<String> sellerNames = new ArrayList<>();

        List<SellerRow> sellerRows = new ArrayList<>();

        Cursor sellerCursor = db.rawQuery("select * from Farmer" , null);
        sellerCursor.moveToFirst();
        for (int i = 0 ; i < sellerCursor.getCount() ; i++ ){
            sellersIds.add(sellerCursor.getInt(0));
            sellerNames.add(sellerCursor.getString(1));
            sellerCursor.moveToNext();
        }
        Cursor cursorPrice = db.rawQuery("select * from FTotalPrice" , null);

        cursorPrice.moveToFirst();
        for (int i = 0 ; i < cursorPrice.getCount() ; i++ ){
            if (!cursorPrice.getString(3).equals("finished")){
                String name = sellerNames.get(sellersIds.indexOf(cursorPrice.getInt(2)));
                sellerRows.add(new SellerRow(cursorPrice.getDouble(1) , name));
            }
            cursorPrice.moveToNext();
        }
        return getUserPrice(sellerRows);
    }

    public List<SellerRow> getUserPrice(List<SellerRow> sellerRows){
        List<String> UniqueSeller = new ArrayList<>();
        for (int i = 0 ; i < sellerRows.size() ; i++ ){
            if ( i == 0 ){
                UniqueSeller.add(sellerRows.get(i).getName());
            } else if (!UniqueSeller.contains(sellerRows.get(i).getName())){
                UniqueSeller.add(sellerRows.get(i).getName());
            }
        }
        List<Double> sellerPrice = new ArrayList<>();
        for (int i = 0 ; i < UniqueSeller.size() ; i++ ){
            double price = 0;
            for (int j = 0 ; j < sellerRows.size() ; j++ ){
                if (UniqueSeller.get(i).equals(sellerRows.get(j).getName())){
                    price += sellerRows.get(j).getPrice();
                }
            }
            sellerPrice.add(price);
        }
        sellerRows.clear();
        for (int i = 0 ; i < UniqueSeller.size() ; i++){
            sellerRows.add(new SellerRow(sellerPrice.get(i) , UniqueSeller.get(i)));
        }
        return sellerRows;
    }
    public double getOwnPrice(){
        double price = 0;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Price" , null);

        cursor.moveToFirst();
        for (int i = 0 ; i < cursor.getCount() ; i++ ){
            price += cursor.getDouble(1);
            cursor.moveToNext();
        }
        price = price /10;
        return price;
    }
}
