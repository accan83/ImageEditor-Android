package com.kapoocino.camera.editimage.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.kapoocino.camera.R;
import com.kapoocino.camera.editimage.fragment.StickerFragment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * 贴图分类列表Adapter
 * 
 * @author panyi
 * 
 */
public class StickerAdapter extends RecyclerView.Adapter<ViewHolder> {

	private StickerFragment mStickerFragment;
	private ImageClick mImageClick;
	private List<String> pathList = new ArrayList<String>();// 图片路径列表

	public StickerAdapter(StickerFragment fragment) {
		super();
		this.mStickerFragment = fragment;
	}

	public class ImageHolder extends ViewHolder {
		public ImageView image;

		public ImageHolder(View itemView) {
			super(itemView);
			this.image = (ImageView) itemView.findViewById(R.id.img);
		}
	}// end inner class

	@Override
	public int getItemCount() {
		return pathList.size();
	}

	@Override
	public int getItemViewType(int position) {
		return 1;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewtype) {
		View v = null;
		v = LayoutInflater.from(parent.getContext()).inflate(
				R.layout.view_sticker_item, null);
		ImageHolder holer = new ImageHolder(v);
		return holer;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		ImageHolder imageHoler = (ImageHolder) holder;
		String path = pathList.get(position);
		 System.out.println(path);
		Glide.with(mStickerFragment)
				.load("file:///android_asset/" + path)
				.into(imageHoler.image);
//		ImageLoader.getInstance().displayImage("assets://" + path,
//				imageHoler.image, imageOption);
//		imageHoler.image.setTag(path);
		mImageClick = new ImageClick(path);
		imageHoler.image.setOnClickListener(mImageClick);
	}

	public void addStickerImages(String folderPath) {
		pathList.clear();
		try {
			String[] files = mStickerFragment.getActivity().getAssets()
					.list(folderPath);
			for (String name : files) {
				pathList.add(folderPath + File.separator + name);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.notifyDataSetChanged();
	}

	/**
	 * 选择贴图
	 * 
	 * @author panyi
	 * 
	 */
	private final class ImageClick implements OnClickListener {
		String path;

		public ImageClick(String path) {
			this.path = path;
		}
		@Override
		public void onClick(View v) {
			mStickerFragment.selectedStickerItem(path);
		}
	}// end inner class

}// end class
