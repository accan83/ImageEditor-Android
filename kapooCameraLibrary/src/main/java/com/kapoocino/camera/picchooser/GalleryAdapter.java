package com.kapoocino.camera.picchooser;

import java.util.List;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kapoocino.camera.R;


class GalleryAdapter extends BaseAdapter {

    private final Context context;
    private final List<GridItem> items;
    private final LayoutInflater mInflater;

    public GalleryAdapter(final Context context, final List<GridItem> buckets) {
        this.items = buckets;
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        if (items.get(0) instanceof BucketItem) { // show buckets
            ViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.bucketitem, null);
                holder = new ViewHolder();
                holder.icon = (ImageView) convertView.findViewById(R.id.icon);
                holder.text = (TextView) convertView.findViewById(R.id.text);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            BucketItem bi = (BucketItem) items.get(position);

            String name = bi.name;
            if (name.length() > 12){
                name = name.substring(0, 12) + "...";
            }

            holder.text.setText(bi.images > 1 ?
                    name + "\n" + context.getString(R.string.images, bi.images) :
                    name);
            Glide.with(context)
                    .load("file://" + bi.path)
                    .into(holder.icon);
//            ImageLoader.getInstance().displayImage("file://" + bi.path, holder.icon);

            return convertView;
        } else { // show images in a bucket
            ImageView imageView;
            if (convertView == null) {  // if it's not recycled, initialize some attributes
                imageView = (ImageView) mInflater.inflate(R.layout.imageitem, null);
            } else {
                imageView = (ImageView) convertView;
            }
            Glide.with(context)
                    .load("file://" + items.get(position).path)
                    .into(imageView);
            Log.d("---PATH---", "file://" + items.get(position).path);
//            ImageLoader.getInstance().displayImage("file://" + items.get(position).path, imageView);
            return imageView;
        }
    }

    private static class ViewHolder {
        private ImageView icon;
        private TextView text;
    }

}
