package org.wikipedia.imagesearch.utils;

import org.wikipedia.imagesearch.R;

import android.content.Context;
import android.os.AsyncTask;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageTask extends AsyncTask<Void, Void, String> {
    private Context mContext;
    private String searchString;

    public ImageTask(Context context, String searchString) {
        mContext = context;
        this.searchString = searchString;
    }

	@Override
	protected String doInBackground(Void... params) {

		URL url = null;
        String response = null;
		try {
			url = new URL(mContext.getString(R.string.wiki_api, searchString));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		try {
			response = HTTPUtils.performRESTRequest(url);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return response;
	}
}