package com.example.designer2.smssmsapp;

import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class Records extends AppCompatActivity  {

    DatabaseHelper helper;
    List<DBModel> dblist;
    RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerviewAdapter adapters;
    SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        helper = new DatabaseHelper(this);
        dblist = new ArrayList<DBModel>();
        dblist = helper.getDataFromDB();
        recyclerView = (RecyclerView) findViewById(R.id.recview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerviewAdapter(this, helper.getDataFromDB(),recyclerView);
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));


        recyclerView.setAdapter(adapter);








        // action bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Sms Records");


    }



private void showActionsDialog(final int position) {
    //CharSequence colors[] = new CharSequence[]{"Edit", "Delete"};

    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle("Choose option");
    builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
            //databaseHelper.deleteARow(dblist.get(position),position);

        }
    });


    builder.show();
}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search, menu);

        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);
        return true;
    }

    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (adapters!=null)
                    adapters.getFilter().filter(newText);
                return true;
            }
        });
    }


}

