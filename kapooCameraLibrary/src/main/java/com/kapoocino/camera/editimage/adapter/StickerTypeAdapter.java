package com.kapoocino.camera.editimage.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kapoocino.camera.R;
import com.kapoocino.camera.editimage.fragment.StickerFragment;


/**
 * 贴图分类列表Adapter
 *
 * @author panyi
 */
public class StickerTypeAdapter extends RecyclerView.Adapter<ViewHolder> {
    public static final int[] typeIcon = {R.drawable.kaps_thumb, R.drawable.poc_thumb, R.drawable.ino_thumb};
    public static final String[] stickerPath = {"stickers/kaps", "stickers/poc", "stickers/ino"};
    public static final String[] stickerPathName = {"Kaps", "Poc", "Ino"};
    private StickerFragment mStickerFragment;
    private ImageClick mImageClick = new ImageClick();

    public StickerTypeAdapter(StickerFragment fragment) {
        super();
        this.mStickerFragment = fragment;
    }

    public class ImageHolder extends ViewHolder {
        public ImageView icon;

        public ImageHolder(View itemView) {
            super(itemView);
            this.icon = (ImageView) itemView.findViewById(R.id.icon);
        }
    }// end inner class

    @Override
    public int getItemCount() {
        return stickerPathName.length;
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewtype) {
        View v = null;
        v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.view_sticker_type_item, null);
        ImageHolder holder = new ImageHolder(v);
        return holder;
    }

    /**
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ImageHolder imageHolder = (ImageHolder) holder;
        imageHolder.icon.setImageResource(typeIcon[position]);
        imageHolder.icon.setTag(stickerPath[position]);
        imageHolder.icon.setOnClickListener(mImageClick);
    }

    /**
     * 选择贴图类型
     *
     * @author panyi
     */
    private final class ImageClick implements OnClickListener {
        @Override
        public void onClick(View v) {
            String data = (String) v.getTag();
            // System.out.println("data---->" + data);
            mStickerFragment.swipToStickerDetails(data);
        }
    }// end inner class
}// end class
