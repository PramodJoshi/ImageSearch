package org.wikipedia.imagesearch.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.wikipedia.imagesearch.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;

public class Utils {

    /**
     * Ensure the class cannot be instantiated
     */
    private Utils() {

    }

    /**
     * Closes a BufferedReader
     *
     * @param context application context
     * @param imageData json feed
     */
    public static HashMap<String, String> getImages(Context context, String imageData) {
        HashMap<String, String> images = new HashMap<>();
        try {
            //Whole JSON object
            JSONObject jsonObject = new JSONObject(imageData);
            if (jsonObject.length() > 0) {
                //Query JSON object
                JSONObject queryObject = null;
                if(jsonObject.has(context.getString(R.string.query))) {
                    queryObject = jsonObject.getJSONObject(context.getString(R.string.query));
                }

                if (queryObject != null && queryObject.length() > 0) {
                    //Pages JSON object
                    JSONObject pagesObject = null;
                    if(queryObject.has(context.getString(R.string.pages))) {
                        pagesObject = queryObject.getJSONObject(context.getString(R.string.pages));
                    }

                    if (pagesObject != null && pagesObject.length() > 0) {
                        JSONArray pagesArray = pagesObject.names();
                        for (int i = 0; i < pagesArray.length(); i++) {
                            //Single image page JSON object
                            JSONObject pageObject = null;
                            if(pagesObject.has(pagesArray.getString(i))) {
                                pageObject = pagesObject.getJSONObject(pagesArray.getString(i));
                            }
                            String imageTitle = null;
                            String imageSource = null;
                            if (pageObject != null && pageObject.length() > 0) {
                                if(pageObject.has(context.getString(R.string.title))) {
                                    imageTitle = pageObject.getString(context.getString(R.string.title));
                                }

                                //Thumbnail JSON object
                                JSONObject thumbnailObject = null;
                                if(pageObject.has(context.getString(R.string.thumbnail))) {
                                    thumbnailObject = pageObject.getJSONObject(context.getString(R.string.thumbnail));
                                }
                                if (thumbnailObject != null && thumbnailObject.length() > 0) {
                                    if(thumbnailObject.has(context.getString(R.string.imgSource))) {
                                        imageSource = thumbnailObject.getString(context.getString(R.string.imgSource));
                                    }
                                }
                            }
                            images.put(imageTitle, imageSource);
                        }
                    }
                }
            }
        } catch (JSONException e) {
            images = new HashMap<>();
            e.printStackTrace();
        }

        return images;
    }

    /**
     * Closes a BufferedReader
     *
     * @param bufferedReader A BufferedReader to close
     */
    public static void closeBufferedReader(BufferedReader bufferedReader) {
        if (bufferedReader == null) {
            return;
        }

        try {
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes a HttpURLConnection
     *
     * @param conn A HttpURLConnection to close
     */
    public static void closeHttpConnection(HttpURLConnection conn) {
        if (conn == null) {
            return;
        }

        conn.disconnect();
    }

    /**
     * Checks network connection
     *
     * @param context application context
     */
    public static boolean networkConnection(Context context) {
        ConnectivityManager cManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cManager.getActiveNetworkInfo();
        return !(nInfo == null || (nInfo.getState() != NetworkInfo.State.CONNECTED));
    }

    /**
     * Displays network connection error dialog
     *
     * @param context application context
     */
    public static void networkConnectionError(final Context context) {
        AlertDialog.Builder ad = new AlertDialog.Builder(context);
        ad.setTitle(context.getResources().getString(R.string.network_error_title));
        ad.setMessage(context.getResources().getString(R.string.network_error_message));
        ad.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alert = ad.create();
        if(!alert.isShowing()) {
            alert.show();
        }
    }

    /**
     * Displays custom alert message
     *
     * @param context application context
     */
    public static void displayAlert(Context context, String alertMsg) {
        AlertDialog.Builder ad = new AlertDialog.Builder(context);
        ad.setTitle(context.getResources().getString(R.string.app_name));
        ad.setMessage(alertMsg);
        ad.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog alert = ad.create();
        if(!alert.isShowing()) {
            alert.show();
        }
        ((TextView)alert.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());
    }
}
