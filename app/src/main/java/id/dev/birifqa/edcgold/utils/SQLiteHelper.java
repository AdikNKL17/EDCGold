package id.dev.birifqa.edcgold.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;

    static final String DATABASE_NAME = "apotekku.db";

    public static final String TABLE_MEDICINE = "medicine";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_ID_MEDICINE = "id_medicine";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DATE_EXPIRED = "date_expired";
    public static final String COLUMN_BARCODE = "barcode";
    public static final String COLUMNT_QTY = "qty";
    public static final String COLUMN_ADDRESS = "address";

    public static final String TABLE_CART = "cart";
    public static final String COLUMN_ID_CART = "id_cart";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE_MEDICINE = "CREATE TABLE " + TABLE_MEDICINE + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY autoincrement, " +
                COLUMN_ID_MEDICINE + " TEXT NOT NULL, " +
                COLUMN_NAME + " TEXT NOT NULL, " +
                COLUMN_DATE_EXPIRED + " TEXT NOT NULL, " +
                COLUMN_BARCODE + " TEXT NOT NULL, " +
                COLUMNT_QTY + " TEXT NOT NULL" +
                " )";

        db.execSQL(CREATE_TABLE_MEDICINE);

        final String CREATE_TABLE_CART = "CREATE TABLE " + TABLE_CART + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY autoincrement, " +
                COLUMN_ID_CART + " TEXT NOT NULL, " +
                COLUMN_ID_MEDICINE + " TEXT NOT NULL, " +
                COLUMNT_QTY + " TEXT NOT NULL" +
                " )";

        db.execSQL(CREATE_TABLE_CART);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEDICINE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        onCreate(db);
    }

    public ArrayList<HashMap<String, String>> getAllData() {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT * FROM " + TABLE_MEDICINE;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(COLUMN_ID, cursor.getString(0));
                map.put(COLUMN_ID_MEDICINE, cursor.getString(1));
                map.put(COLUMN_NAME, cursor.getString(2));
                map.put(COLUMN_DATE_EXPIRED, cursor.getString(3));
                map.put(COLUMN_BARCODE, cursor.getString(4));
                map.put(COLUMNT_QTY, cursor.getString(5));
                wordList.add(map);
            } while (cursor.moveToNext());
        }

        Log.e("select sqlite ", "" + wordList);

        database.close();
        return wordList;
    }

    public void insertMedicine(String id_medicine, String name, String date_expired, String barcode, String qty) {
        SQLiteDatabase database = this.getWritableDatabase();
        String queryValues = "INSERT INTO " + TABLE_MEDICINE + " (id_medicine, name, date_expired, barcode, qty) " +
                "VALUES ('" + id_medicine + "', '" + name + "', '" + date_expired + "', '" + barcode + "', '" + qty + "')";

        Log.e("insert sqlite ", "" + queryValues);
        database.execSQL(queryValues);
        database.close();
    }

    public void insertToCart(String id_cart, String id_medicine, String qty) {
        SQLiteDatabase database = this.getWritableDatabase();
        String queryValues = "INSERT INTO " + TABLE_MEDICINE + " (id_cart, id_medicine, qty) " +
                "VALUES ('" + id_cart + "', '" + id_medicine+ "', '" + qty+ "')";

        Log.e("insert sqlite ", "" + queryValues);
        database.execSQL(queryValues);
        database.close();
    }

    public void updateCart(int id, String id_cart, String id_medicine, String qty) {
        SQLiteDatabase database = this.getWritableDatabase();

        String updateQuery = "UPDATE " + TABLE_MEDICINE + " SET "
                + COLUMN_ID_CART + "='" + id_cart + "', "
                + COLUMN_ID_MEDICINE + "='" + id_medicine + "', "
                + COLUMNT_QTY + "='" + qty + "'"
                + " WHERE " + COLUMN_ID + "=" + "'" + id + "'";
        Log.e("update sqlite ", updateQuery);
        database.execSQL(updateQuery);
        database.close();
    }

    public void deleteFromCart(int id) {
        SQLiteDatabase database = this.getWritableDatabase();

        String updateQuery = "DELETE FROM " + TABLE_CART + " WHERE " + COLUMN_ID + "=" + "'" + id + "'";
        Log.e("update sqlite ", updateQuery);
        database.execSQL(updateQuery);
        database.close();
    }
}
