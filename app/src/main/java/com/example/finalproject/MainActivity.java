package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.finalproject.adapter.SlidingMenuAdapter;
import com.example.finalproject.data.DatabaseHandler;
import com.example.finalproject.fragment.Detail;
import com.example.finalproject.fragment.CreateWorkout;
import com.example.finalproject.fragment.TrainingPlan;
import com.example.finalproject.model.ItemSlideMenu;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<ItemSlideMenu> listSliding;
    private SlidingMenuAdapter adapter;
    private ListView listViewSliding;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //----------Sliding Side thingy-------------------

        listViewSliding = findViewById(R.id.lv_sliding_menu);
        drawerLayout = findViewById(R.id.drawer_layout);
        listSliding = new ArrayList<>();
        //Adding items to my list of fragments (limited to 3 for now since I only have 3 frag).
        listSliding.add(new ItemSlideMenu(R.drawable.list, "Training Plan"));
        listSliding.add(new ItemSlideMenu(R.drawable.add, "Create Workout"));
        listSliding.add(new ItemSlideMenu(R.drawable.search_img, "Search Detail"));
        adapter = new SlidingMenuAdapter(this, listSliding);
        listViewSliding.setAdapter(adapter);

        //Display icon open/close sliding list
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Set title
        //setTitle(listSliding.get(0).getTitle());

        //item selected
        listViewSliding.setItemChecked(0, true);

        //Close menu
        drawerLayout.closeDrawer(listViewSliding);

        //Display fragment 1 when start
        replaceFragment(0);

        //Handle on item click
        listViewSliding.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //set title
                //setTitle(listSliding.get(position).getTitle());

                //item selected
                listViewSliding.setItemChecked(position, true);

                //Replace fragment
                replaceFragment(position);

                //Close menu
                drawerLayout.closeDrawer(listViewSliding);

            }
        });

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_opened, R.string.drawer_closed) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };

        drawerLayout.setDrawerListener(actionBarDrawerToggle);

    }

    //Menu item Upper Right
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);



        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.menu_item_1:
                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                db.deleteAll();
                db.close();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(Color.WHITE);
        actionBarDrawerToggle.syncState();
    }


    //Create method to replace fragment
    private void replaceFragment(int pos) {
        Fragment fragment = null;
        switch (pos) {
            case 0:
                fragment = new TrainingPlan();
                break;
            case 1:
                fragment = new CreateWorkout();
                break;
            case 2:
                fragment = new Detail();
                break;
            default:
                Toast.makeText(getApplicationContext(), "An error occurred, Please try again !", Toast.LENGTH_SHORT).show();
                break;
        }

        if (null != fragment) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_content, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}
