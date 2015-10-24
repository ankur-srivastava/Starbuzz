package com.hfad.starbuzz;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class DrinkActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        int id = (Integer)getIntent().getExtras().get("drink_no");

        Drink drink = null;

        try {
            SQLiteOpenHelper helper = new StarbuzzDatabaseHelper(this);
            SQLiteDatabase db = helper.getReadableDatabase();

            if (db != null) {
                Cursor c = db.query("DRINK", new String[]{"NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID", "FAVOURITE"},
                        "_id=?",
                        new String[]{Integer.toString(id)},
                        null, null, null);
                if (c.moveToFirst()) {
                    String name = c.getString(0);
                    String description = c.getString(1);
                    int photoId = c.getInt(2);
                    int favourite = c.getInt(3);

                    TextView drinkText = (TextView) findViewById(R.id.drinkText);
                    drinkText.setText(name);

                    ImageView image = (ImageView) findViewById(R.id.detailImage);
                    image.setImageResource(photoId);

                    CheckBox favouriteBox = (CheckBox) findViewById(R.id.favourite);
                    if(favourite == 1) {
                        favouriteBox.setChecked(true);
                    }
                }

                c.close();
                db.close();
            }
        }catch(SQLiteException ex){
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateFavourite(View view){
        int drinkNo = (Integer)getIntent().getExtras().get("drink_no");
        new DrinkAsyncHelper().execute(drinkNo);
    }

    private class DrinkAsyncHelper extends AsyncTask<Integer, Void, Boolean> {

        ContentValues cv;

        protected void onPreExecute(){
            CheckBox favouriteBox = (CheckBox) findViewById(R.id.favourite);
            cv = new ContentValues();
            cv.put("FAVOURITE", favouriteBox.isChecked());
        }

        protected Boolean doInBackground(Integer... drinks){
            int drinkNo = drinks[0];
            try {
                SQLiteOpenHelper helper = new StarbuzzDatabaseHelper(DrinkActivity.this);
                SQLiteDatabase db = helper.getWritableDatabase();

                db.update("DRINK", cv, "_id=?", new String[]{Integer.toString(drinkNo)});
                db.close();
                return true;
            }catch(SQLiteException ex){
                Toast.makeText(getApplicationContext(), "Update issues", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        protected void onPostExecute(Boolean result){
            if(!result){
                Toast.makeText(getApplicationContext(), "Error while updating", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(), "Preference saved", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
