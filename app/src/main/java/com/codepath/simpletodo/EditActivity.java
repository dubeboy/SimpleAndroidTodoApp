package com.codepath.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {

    private int position;
    private String itemText;
    private EditText etEditItem;
    public static final int RESULT_OK = 1;
    private String TAG = EditActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        etEditItem = (EditText) findViewById(R.id.etEditItem);
        itemText = getIntent().getStringExtra(MainActivity.INTENT_EXTRA_ITEM_TEXT);
        position = getIntent().getIntExtra(MainActivity.INTENT_EXTRA_ITEM_POSITION, -1);

        Log.d(TAG, "onCreate: " + itemText);
        etEditItem.setText(itemText);
    }

    public void onSave(View view) {
        itemText += etEditItem.getText().toString();
        sendBackResult(position, itemText);
    }

    //finishes the current activity and sends back the result to the caller
    private void sendBackResult(int position, String itemText) {
        Intent data = new Intent();
        data.putExtra(MainActivity.INTENT_EXTRA_ITEM_TEXT, itemText);
        data.putExtra(MainActivity.INTENT_EXTRA_ITEM_POSITION, position);
        setResult(RESULT_OK, data);
        finish();
    }
    // TODO also look into when a person presses back save the stuff
}
