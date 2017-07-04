package org.wikipedia.imagesearch.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import org.wikipedia.imagesearch.R;
import java.util.List;

public class LoadImage extends ArrayAdapter<String> {
    private final Activity context;
    private final List<String> images;

    public LoadImage(Activity context, List<String> images) {
        super(context, R.layout.image_row, images);
        this.context = context;
        this.images = images;
    }

    @NonNull
    @Override
    public View getView(int position, View view, @NonNull ViewGroup parent) {
        if (view == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.image_row, parent, false);
        }
        ImageView imageView = (ImageView) view.findViewById(R.id.img);

//        new DownloadImageTask(imageView, images.get(position)).execute();

        Picasso.with(context).load(images.get(position)).placeholder(ContextCompat.getDrawable(context, R.drawable.ic_image_black_48dp)).into(imageView);
        return view;
    }
}

