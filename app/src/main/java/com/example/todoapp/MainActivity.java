package com.example.todoapp;

import android.content.Intent;
import android.os.Bundle;


import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import adapters.RecyclerItemTouchHelper;
import adapters.TodoAdapter;
import comparators.ToDoDateComparator;
import comparators.ToDoNameComparator;
import comparators.ToDoUrgencyComparator;
import model.DBModel;
import model.IModel;
import model.ToDoItem;

public class MainActivity extends AppCompatActivity {

    public static final int RQC_NEW = 1;
    public static final int RQC_EDIT = 2;
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private List<ToDoItem> items;
    private RecyclerView rvItems;
    private TodoAdapter adapter;
    private IModel model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             /*  Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                ujElem();
            }
        });
        model = new DBModel(this);
        items=model.getAllToDoItem();
                rvItems = findViewById(R.id.rvItems);
      /*
        items = new ArrayList<>();
        items.add(new ToDoItem(1, "A", 1991, 11, 11, 1, "1"));
        items.add(new ToDoItem(2, "B", 1992, 12, 11, 0, "0"));
        items.add(new ToDoItem(3, "C", 1990, 1, 11, 1, "2"));*/
        adapter = new TodoAdapter(items, R.layout.list_item, this, model);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerItemTouchHelper(adapter));
        itemTouchHelper.attachToRecyclerView(rvItems);

        rvItems.setAdapter(adapter);
        rvItems.setLayoutManager(new LinearLayoutManager(this));

    }

    public void ujElem() {
        Intent intent = new Intent(this, ToDoActivity.class);
        startActivityForResult(intent, RQC_NEW);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            ToDoItem tdi = (ToDoItem) data.getSerializableExtra("tdi");
            if (requestCode == RQC_NEW) {
                items.add(tdi);
                model.saveOrUdpate(tdi);
                adapter.notifyItemInserted(items.size() - 1);
            } else if (requestCode == RQC_EDIT) {
                int position = data.getIntExtra("index", -1);
                items.set(position, tdi);
                model.saveOrUdpate(tdi);
                adapter.notifyItemChanged(position);
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.miMegnevezes) {
            items.sort(new ToDoNameComparator());
            adapter.notifyDataSetChanged();
        } else if (id == R.id.miFontossag) {
            items.sort(new ToDoUrgencyComparator());
            adapter.notifyDataSetChanged();
        } else if (id == R.id.miHatarido) {
            items.sort(new ToDoDateComparator().reversed());
            adapter.notifyDataSetChanged();
        }


        return super.onOptionsItemSelected(item);
    }

}