package org.wikipedia.imagesearch.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.wikipedia.imagesearch.R;

public class ImageActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra(getString(R.string.intent_key));

        ImageView imageView = (ImageView) findViewById(R.id.img);
        Picasso.with(this).load(imageUrl).placeholder(ContextCompat.getDrawable(this, R.drawable.ic_image_black_48dp)).into(imageView);
    }
}
