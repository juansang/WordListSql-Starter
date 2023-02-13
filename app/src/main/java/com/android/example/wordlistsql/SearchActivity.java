package com.android.example.wordlistsql;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = EditWordActivity.class.getSimpleName();

    private TextView mTextView;
    private WordListOpenHelper mDB;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mTextView = ((TextView) findViewById(R.id.search_result));
        mDB = new WordListOpenHelper(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem searchWord = menu.findItem(R.id.action_search2);
        SearchView sv = (SearchView) searchWord.getActionView();

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String word = query;
                Cursor cursor = mDB.search(word);

                if (cursor != null & cursor.getCount() > 0) {
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(SearchActivity.this);
                    builder.setTitle("Alert");
                    builder.setMessage("NO RESULTS FOR " + word);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });
        return true;
    }


}