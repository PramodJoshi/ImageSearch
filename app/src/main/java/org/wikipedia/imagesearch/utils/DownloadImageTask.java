package org.wikipedia.imagesearch.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;

public class DownloadImageTask extends AsyncTask<Void, Void, Bitmap> {
    private ImageView imageView;
	private String url;

    public DownloadImageTask(ImageView imageView, String url) {
        this.imageView = imageView;
		this.url = url;
    }

    @Override
	protected Bitmap doInBackground(Void... params) {
		Bitmap image = null;
		try {
			InputStream in = new java.net.URL(url).openStream();
			image = BitmapFactory.decodeStream(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return image;
	}

	protected void onPostExecute(Bitmap result) {
		imageView.setImageBitmap(result);
	}
}