package com.example.gmail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.gmail.adapters.EmailBaseAdapter;
import com.example.gmail.models.EmailItemModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<EmailItemModel> items;

    String[] logos = {
            "A", "B", "C", "D", "E", "F", "G", "H", "K", "L"
    };

    String[] names = {
            "AGDFG", "BSDFGDGS", "CSDGDFG", "DDFGDFG", "EGDFGD", "FFDGDG", "GFDGDF", "HEWRWER", "KSDTRT", "LDFGDGDF"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        items = new ArrayList<>();
        for (int i = 0; i < 10; i++)
            items.add(new EmailItemModel(logos[i], names[i], "10:3" + i +" AM", "i write this letter to confirm something about...", true));

        EmailBaseAdapter adapter = new EmailBaseAdapter(this, items);

        ListView listView = findViewById(R.id.list_mail);
        listView.setAdapter(adapter);
    }
}