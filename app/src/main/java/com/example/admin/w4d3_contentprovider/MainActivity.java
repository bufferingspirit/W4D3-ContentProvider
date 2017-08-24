package com.example.admin.w4d3_contentprovider;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    ArrayList<foodEntry> foodList = new ArrayList<>();
    ArrayList<foodEntry> outList = new ArrayList<>();

    RecyclerView recyclerView;
    RecyclerView.LayoutManager gridLayout;
    RecyclerView.ItemAnimator itemAnimator;
    FoodAdapter foodAdapter;

    Button btnCake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnCake = (Button) findViewById(R.id.btnCake);

        initFoodList();
        initRecyclerView();
        for (foodEntry entry : foodList) {
            ContentValues values = new ContentValues();
            values.put(FoodProvider.FOOD_NAME, entry.getName());
            values.put(FoodProvider.CALORIES, entry.getCalories());
            values.put(FoodProvider.FAT, entry.getFat());
            values.put(FoodProvider.PROTEIN, entry.getProtein());
            values.put(FoodProvider.SODIUM, entry.getSodium());
            Uri uri = getContentResolver().insert(
                    FoodProvider.CONTENT_URI, values);
            Log.d(TAG, "onCreate: " + uri.toString());

        }

        displayFoods();
    }

    public void initRecyclerView(){

        recyclerView = (RecyclerView) findViewById(R.id.rView);

        gridLayout = new GridLayoutManager(this, 5);
        itemAnimator = new DefaultItemAnimator();

        recyclerView.setLayoutManager(gridLayout);
        recyclerView.setItemAnimator(itemAnimator);
        foodAdapter = new FoodAdapter(outList);
        recyclerView.setAdapter(foodAdapter);
    }

    public void initFoodList(){
        foodList.add(new foodEntry("Burger", "460", "16g", "17g", "160mg"));
    }

    public void displayFoods() {
        // Retrieve student records
        String URL = "content://com.example.admin.w4d3_contentprovider.FoodProvider";

        Uri foods = Uri.parse(URL);
        //TODO change query
        Cursor c = getContentResolver().query(foods, null, null, null, "_id");

        if (c.moveToFirst()) {
            do{

                outList.add(new foodEntry(c.getString(c.getColumnIndex(FoodProvider.FOOD_NAME)),
                        c.getString(c.getColumnIndex(FoodProvider.CALORIES)),
                        c.getString(c.getColumnIndex(FoodProvider.FAT)),
                        c.getString(c.getColumnIndex(FoodProvider.PROTEIN)),
                        c.getString(c.getColumnIndex(FoodProvider.SODIUM))));
                Log.d(TAG, "displayFoods: " + c.getString(c.getColumnIndex(FoodProvider._ID)) +
                        ", " + c.getString(c.getColumnIndex(FoodProvider.FOOD_NAME)));
            } while (c.moveToNext());
        }
    }
}
