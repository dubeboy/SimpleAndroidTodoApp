package com.codepath.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> items;
    private  ArrayAdapter<String> itemsAdapter;
    private ListView lvItems;
    private String TAG = MainActivity.class.getSimpleName();
    static final String INTENT_EXTRA_ITEM_POSITION = "item_position";
    static final String INTENT_EXTRA_ITEM_TEXT = "item_text";
    private static final int EDIT_REQUEST_CODE = 20;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView)  findViewById(R.id.lvItems);
        items = new ArrayList<>();
        readItems(); // instantiates a new items array from a file
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);

        items.add("First Item");
        items.add("Second Item");
        setupListViewListener();
        setupItemClickListener();
    }

    private void setupItemClickListener() {
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent   = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra(INTENT_EXTRA_ITEM_POSITION, position);
                intent.putExtra(INTENT_EXTRA_ITEM_TEXT, items.get(position));
                startActivityForResult(intent, EDIT_REQUEST_CODE);
            }
        });
    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    public void onAddItem(View view) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");
        writeItems();
    }

    private void readItems() {
        File filesDir = getFilesDir();
        Log.d(TAG, "readItems: " + filesDir.getPath());
        File todoFile = new File(filesDir, "todo.txt");
//        items = new ArrayList<>(); // some times the app crashes ???? TODO  Throw Java.io.FileNotFound
        try {
            items = new ArrayList<>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            Log.d(TAG, "readItems: Oops!! IOException");
            e.printStackTrace();
        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == EditActivity.RESULT_OK && requestCode == EDIT_REQUEST_CODE) {
            int position = data.getIntExtra(MainActivity.INTENT_EXTRA_ITEM_POSITION, 0);
            String text = data.getStringExtra(MainActivity.INTENT_EXTRA_ITEM_TEXT);
            items.set(position, text);
            itemsAdapter.notifyDataSetChanged();
            writeItems();
        }
    }
}
