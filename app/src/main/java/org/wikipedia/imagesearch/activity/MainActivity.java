package org.wikipedia.imagesearch.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import org.wikipedia.imagesearch.R;
import org.wikipedia.imagesearch.adapter.LoadImage;
import org.wikipedia.imagesearch.utils.ImageTask;
import org.wikipedia.imagesearch.utils.LicenseDialog;
import org.wikipedia.imagesearch.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private LoadImage listAdapter;
    private String imageJSON = null;
    private HashMap<String, String> images = new HashMap<>();
    private List<String> imageUrls;
    ListView imageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageList = (ListView) findViewById(R.id.listView);
        imageList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (Utils.networkConnection(MainActivity.this)) {
                    Intent intent = new Intent(MainActivity.this.getApplicationContext(), ImageActivity.class);
                    intent.putExtra(getString(R.string.intent_key), imageUrls.get(position));
                    startActivity(intent);
                } else {
                    Utils.networkConnectionError(MainActivity.this);
                }
            }
        });

        EditText searchText = (EditText) findViewById(R.id.editText);
        searchText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                try {
                    if (Utils.networkConnection(MainActivity.this)) {
                        imageJSON = new ImageTask(MainActivity.this.getApplicationContext(), s.toString()).execute().get();
                    } else {
                        Utils.networkConnectionError(MainActivity.this);
                    }
                    if (imageJSON != null) {
                        images = Utils.getImages(MainActivity.this.getApplicationContext(), imageJSON);
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }

                imageUrls = new ArrayList<>();
                if (images.size() > 0) {
                    for (String key : images.keySet()) {
                        if (key != null && key.toLowerCase().startsWith(s.toString().toLowerCase())) {
                            imageUrls.add(images.get(key));
                        }
                    }
                }

                listAdapter = new LoadImage(MainActivity.this, imageUrls);
                imageList.setAdapter(listAdapter);
                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate menu
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_about){
            String dialogTitle = getString(R.string.licenses);
            String dialogText = getString(R.string.license_text);
            Fragment fragment = LicenseDialog.newInstance(dialogTitle, dialogText);
            Utils.showDialog(MainActivity.this, fragment, "LICENSE_DIALOG");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}