package com.example.gmail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.gmail.adapters.EmailBaseAdapter;
import com.example.gmail.models.Email;

import java.util.ArrayList;
import java.util.List;

import io.bloco.faker.Faker;

public class MainActivity extends AppCompatActivity {

    List<Email> items;
    SearchView txtSearchValue;
    EmailBaseAdapter adapter;
    boolean filterOnOFF = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Faker faker = new Faker();
        items = new ArrayList<>();
        for (int i = 0; i < 30; i++)
            items.add(new Email(i, faker.name.firstName(), faker.company.name(), faker.lorem.paragraph(30), i + ":00", null, faker.bool.bool(), faker.bool.bool(), getRandomMaterialColor("400")));

        items =  new ArrayList<>(items);

        adapter = new EmailBaseAdapter(this, items);

        ListView listView = findViewById(R.id.list_mail);
        listView.setAdapter(adapter);

        registerForContextMenu(listView);
        listView.setLongClickable(true);
    }

    private int getRandomMaterialColor(String typeColor) {
        int returnColor = Color.GRAY;
        int arrayId = getResources().getIdentifier("mdcolor_" + typeColor, "array", getPackageName());

        if (arrayId != 0) {
            TypedArray colors = getResources().obtainTypedArray(arrayId);
            int index = (int) (Math.random() * colors.length());
            returnColor = colors.getColor(index, Color.GRAY);
            colors.recycle();
        }
        return returnColor;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.setFilerType(EmailBaseAdapter.FILTER_TYPE_SEARCH);
                adapter.getFilter().filter(newText);

                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_favorite) {
            adapter.setFilerType(EmailBaseAdapter.FILTER_TYPE_IMPORTANT);

            if (filterOnOFF) {
                adapter.getFilter().filter(null);
            } else {
                adapter.getFilter().filter("1");
            }

            filterOnOFF = !filterOnOFF;

            return true;
        } else if (id == R.id.action_search) {
            return true;
        }

        return false;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, 101, 0, "Reply");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Email selectedItem = items.get(info.position);

        int id = item.getItemId();
        if (id == 101) {
            Intent intent = new Intent(MainActivity.this, ReplyActivity.class);

            Bundle dataBundle = new Bundle();

            dataBundle.putString("email", selectedItem.getFrom());
            dataBundle.putString("subject", selectedItem.getSubject());

            intent.putExtras(dataBundle);

            startActivity(intent);
        }

        return super.onContextItemSelected(item);
    }
}