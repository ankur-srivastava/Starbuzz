package com.hfad.starbuzz;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


public class TopLevelActivity extends Activity {

    SQLiteDatabase db;
    Cursor cursor;

    static String className = TopLevelActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_level);
        setListViewListener();
        populateFavouriteListView();
    }

    public void setListViewListener(){
        //An event listener on click of Drink
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> listView, View view, int position, long id){
                if(position == 0){
                    Intent intent = new Intent(TopLevelActivity.this, DrinkCategoryActivity.class);
                    startActivity(intent);
                }
            }
        };

        ListView listView = (ListView) findViewById(R.id.list_options);
        listView.setOnItemClickListener(itemClickListener);
    }

    public void onRestart(){
        super.onRestart();
        Log.v(className, "In Restart");
        Cursor newCursor = null;
        try {
            SQLiteOpenHelper helper = new StarbuzzDatabaseHelper(this);
            db = helper.getReadableDatabase();
            newCursor = db.query("DRINK", new String[]{"_id", "NAME"}, "FAVOURITE=1", null, null, null, null);
        }catch (SQLiteException ex){
            ex.printStackTrace();
        }
        if(newCursor != null){
            ListView favListView = (ListView) findViewById(R.id.favouriteList);
            CursorAdapter adapter = (CursorAdapter) favListView.getAdapter();
            adapter.changeCursor(newCursor);
            cursor = newCursor;
        }
    }

    public void populateFavouriteListView(){
        ListView favList = (ListView) findViewById(R.id.favouriteList);
        try{
            SQLiteOpenHelper helper = new StarbuzzDatabaseHelper(this);
            db = helper.getReadableDatabase();
            cursor = db.query("DRINK", new String[]{"_id", "NAME"},
                    "FAVOURITE=1", null, null, null, null);
            CursorAdapter adapter = new SimpleCursorAdapter(TopLevelActivity.this, android.R.layout.simple_list_item_1, cursor,
                    new String[]{"NAME"}, new int[]{android.R.id.text1}, 0);
            favList.setAdapter(adapter);
        }catch(SQLiteException ex){
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
        }
        attachListenerToFavouriteListView(favList);
    }

    public void attachListenerToFavouriteListView(ListView favList){
        favList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int positionId, long id){
                Intent intent = new Intent(TopLevelActivity.this, DrinkActivity.class);
                intent.putExtra("drink_no", (int)id);
                startActivity(intent);
            }
        });
    }
}
