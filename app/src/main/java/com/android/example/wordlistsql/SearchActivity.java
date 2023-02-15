package com.android.example.wordlistsql;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import java.util.Locale;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = EditWordActivity.class.getSimpleName();

    private TextView mTextView;
    private WordListOpenHelper mDB;
    private String word;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mTextView = ((TextView) findViewById(R.id.search_result));
        mDB = new WordListOpenHelper(this);
        word = getIntent().getExtras().getString("word");
        showResult();

    }

    public void showResult(){
        Cursor cursor = mDB.search(word);

        if (cursor != null & cursor.getCount() > 0 & word.length()>0) {
            mTextView.setText("Result for " + word + ":\n\n");
            cursor.moveToFirst();
            int index;
            String result;
            do {
                index = cursor.getColumnIndex(WordListOpenHelper.KEY_WORD);
                result = cursor.getString(index);
                mTextView.append(result + "\n");
            } while (cursor.moveToNext());
            cursor.close();
        }
        else{
            mTextView.setText("No results for " + word);
            AlertDialog.Builder builder = new AlertDialog.Builder(SearchActivity.this);
            builder.setTitle("Alert");
            builder.setMessage("NO RESULTS FOR " + word.toUpperCase(Locale.ROOT));
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.gohome:
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}