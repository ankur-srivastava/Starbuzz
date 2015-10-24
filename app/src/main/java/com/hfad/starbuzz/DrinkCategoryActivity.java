package com.hfad.starbuzz;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class DrinkCategoryActivity extends ListActivity {

    SQLiteDatabase db;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getData();
        getDataFromDB();
    }

    public void getData(){
        ArrayAdapter<Drink> listAdapter = new ArrayAdapter<Drink>(this, android.R.layout.simple_list_item_1, Drink.drinks);
        ListView listView = getListView();
        listView.setAdapter(listAdapter);

    }

    public void getDataFromDB(){
        SQLiteOpenHelper helper = new StarbuzzDatabaseHelper(this);
        db = helper.getReadableDatabase();
        cursor = db.query("DRINK",
                new String[]{"_id", "NAME"},
                null, null, null, null, null);

        CursorAdapter cA = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1,
                cursor, new String[]{"NAME"}, new int[]{android.R.id.text1}, 0);
        ListView listView = getListView();
        listView.setAdapter(cA);

    }

    public void onListItemClick(ListView listView, View view, int position, long id){
        Intent intent = new Intent(DrinkCategoryActivity.this, DrinkActivity.class);
        intent.putExtra("drink_no", (int) id);
        startActivity(intent);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        cursor.close();
        db.close();
    }
}
