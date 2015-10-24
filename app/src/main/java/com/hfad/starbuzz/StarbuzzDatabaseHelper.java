package com.hfad.starbuzz;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ankursrivastava on 8/8/15.
 */
public class StarbuzzDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "starbuzz";
    //private static final int DB_VESRION = 1;
    private static final int DB_VESRION = 2;


    public StarbuzzDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VESRION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        updateMyDatabase(db,0, DB_VESRION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        updateMyDatabase(db, oldVersion, newVersion);
    }

    public static void insertDrink(SQLiteDatabase db, String name, String description, int resourceId){
        ContentValues cv = new ContentValues();
        cv.put("NAME", name);
        cv.put("DESCRIPTION", description);
        cv.put("IMAGE_RESOURCE_ID", resourceId);
        db.insert("DRINK", null, cv);
    }

    public void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion){
        if(oldVersion < 1){
            db.execSQL("CREATE TABLE DRINK(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    +"NAME TEXT, "+"DESCRIPTION TEXT, "+"IMAGE_RESOURCE_ID INTEGER);");
            insertDrink(db, "Latte", "Expresso and Steamed Milk", R.drawable.latte);
            insertDrink(db, "Cappuccino", "Expresso, Hot Milk", R.drawable.cappuccino);
            insertDrink(db, "Filter", "Our best drip coffee", R.drawable.filter);
        }
        if(oldVersion < 2){
            db.execSQL("ALTER TABLE DRINK ADD COLUMN FAVOURITE NUMERIC");
        }
    }
}
