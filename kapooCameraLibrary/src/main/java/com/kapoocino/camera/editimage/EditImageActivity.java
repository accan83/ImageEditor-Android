package com.kapoocino.camera.editimage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ViewFlipper;

import com.kapoocino.camera.BaseActivity;
import com.kapoocino.camera.KapooCamera;
import com.kapoocino.camera.R;
import com.kapoocino.camera.editimage.fragment.FliterListFragment;
import com.kapoocino.camera.editimage.fragment.MainMenuFragment;
import com.kapoocino.camera.editimage.fragment.StickerFragment;
import com.kapoocino.camera.editimage.utils.BitmapUtils;
import com.kapoocino.camera.editimage.view.CustomViewPager;
import com.kapoocino.camera.editimage.view.StickerView;
import com.kapoocino.camera.editimage.view.imagezoom.ImageViewTouch;
import com.kapoocino.camera.editimage.view.imagezoom.ImageViewTouchBase;

import java.util.Date;

/**
 * 图片编辑 主页面
 * 
 * @author panyi
 * 
 *         包含 1.贴图 2.滤镜 3.剪裁 4.底图旋转 功能
 * 
 */
public class EditImageActivity extends BaseActivity {
	public static final String FILE_PATH = "file_path";
	public static final String EXTRA_OUTPUT = "extra_output";

	public static final int MODE_NONE = 0;
	public static final int MODE_STICKERS = 1;// 贴图模式
	public static final int MODE_FILTER = 2;// 滤镜模式

	public String filePath;// 需要编辑图片路径
	public String saveFilePath;// 生成的新图片路径
	private int imageWidth, imageHeight;// 展示图片控件 宽 高
	private LoadImageTask mLoadImageTask;

	public int mode = MODE_NONE;// 当前操作模式
	private EditImageActivity mContext;
	public Bitmap mainBitmap;// 底层显示Bitmap
	public ImageViewTouch mainImage;
	private View backBtn;

	public ViewFlipper bannerFlipper;
	private View applyBtn;// 应用按钮
	private View saveBtn;// 保存按钮

	public StickerView mStickerView;// 贴图层View

	public CustomViewPager bottomGallery;// 底部gallery
	private BottomGalleryAdapter mBottomGalleryAdapter;// 底部gallery
	private MainMenuFragment mMainMenuFragment;// Menu
	public StickerFragment mStickerFragment;// 贴图Fragment
	public FliterListFragment mFliterListFragment;// 滤镜FliterListFragment

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		checkInitImageLoader();
		KapooCamera.clearHistory();
		setContentView(R.layout.activity_image_edit);
		initView();
		getData();
	}

	private void getData() {
//		filePath = getIntent().getStringExtra(FILE_PATH);
		saveFilePath = getIntent().getStringExtra(EXTRA_OUTPUT);// 保存图片路径
//		loadImage(filePath);
		loadImage();
	}

	private void initView() {
		mContext = this;
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		imageWidth = (int) ((float) metrics.widthPixels / 1.5);
		imageHeight = (int) ((float) metrics.heightPixels / 1.5);

		bannerFlipper = (ViewFlipper) findViewById(R.id.banner_flipper);
		bannerFlipper.setInAnimation(this, R.anim.in_bottom_to_top);
		bannerFlipper.setOutAnimation(this, R.anim.out_bottom_to_top);
		applyBtn = findViewById(R.id.apply);
		applyBtn.setOnClickListener(new ApplyBtnClick());
		saveBtn = findViewById(R.id.save_btn);
		saveBtn.setOnClickListener(new SaveBtnClick());

		mainImage = (ImageViewTouch) findViewById(R.id.main_image);
		backBtn = findViewById(R.id.back_btn);// 退出按钮
		backBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				forceReturnBack();
			}
		});

		mStickerView = (StickerView) findViewById(R.id.sticker_panel);

		// 底部gallery
		bottomGallery = (CustomViewPager) findViewById(R.id.bottom_gallery);
		//bottomGallery.setOffscreenPageLimit(5);
		mMainMenuFragment = MainMenuFragment.newInstance(this);
		mBottomGalleryAdapter = new BottomGalleryAdapter(
				this.getSupportFragmentManager());
		mStickerFragment = StickerFragment.newInstance(this);
		mFliterListFragment = FliterListFragment.newInstance(this);
		bottomGallery.setAdapter(mBottomGalleryAdapter);
	}

	/**
	 * 
	 * @author panyi
	 * 
	 */
	private final class BottomGalleryAdapter extends FragmentPagerAdapter {
		public BottomGalleryAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int index) {
			// System.out.println("createFragment-->"+index);
            switch (index){
                case MainMenuFragment.INDEX:// 主菜单
                    return mMainMenuFragment;
                case StickerFragment.INDEX:// 贴图
                    return mStickerFragment;
                case FliterListFragment.INDEX:// 滤镜
                    return mFliterListFragment;
            }//end switch
            return MainMenuFragment.newInstance(mContext);
		}

		@Override
		public int getCount() {
			return 6;
		}
	}// end inner class

	/**
	 * 异步载入编辑图片
	 * 
	 * @param
	 */
	public void loadImage() {
		mainBitmap = KapooCamera.bitmap.copy(KapooCamera.bitmap.getConfig(), KapooCamera.bitmap.isMutable());
		KapooCamera.addHistory(mainBitmap);
		mainImage.setImageBitmap(mainBitmap);
		mainImage.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);
	}

	private final class LoadImageTask extends AsyncTask<String, Void, Bitmap> {
		@Override
		protected Bitmap doInBackground(String... params) {
			return BitmapUtils.loadImageByPath(params[0], imageWidth,
					imageHeight);
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			if (mainBitmap != null) {
				mainBitmap.recycle();
				mainBitmap = null;
				System.gc();
			}
			mainBitmap = result;
			mainImage.setImageBitmap(result);
			mainImage.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);
			// mainImage.setDisplayType(DisplayType.FIT_TO_SCREEN);
		}
	}// end inner class

	/**
	 * 按下返回键
	 * 
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			switch (mode) {
			case MODE_STICKERS:
				mStickerFragment.backToMain();
				return true;
			case MODE_FILTER:// 滤镜编辑状态
				mFliterListFragment.backToMain();// 保存滤镜贴图
				return true;
			}// end switch

			forceReturnBack();
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 强制推出
	 */
	private void forceReturnBack() {
		if (mode == MODE_NONE) {
			setResult(RESULT_CANCELED);
			this.finish();
		}
		else {
			changeMainBitmap(KapooCamera.getLastHistory());
		}
	}

	/**
	 * 保存按钮点击
	 * 
	 * @author panyi
	 * 
	 */
	private final class ApplyBtnClick implements OnClickListener {
		@Override
		public void onClick(View v) {
			switch (mode) {
			case MODE_STICKERS:
				mStickerFragment.saveStickers();// 保存贴图
				break;
			case MODE_FILTER:// 滤镜编辑状态
				mFliterListFragment.saveFilterImage();// 保存滤镜贴图
				break;
			default:
				break;
			}// end switch
		}
	}// end inner class

	/**
	 * 保存按钮 点击退出
	 * 
	 * @author panyi
	 * 
	 */
	private final class SaveBtnClick implements OnClickListener {
		@Override
		public void onClick(View v) {
			KapooCamera.bitmap = mainBitmap.copy(mainBitmap.getConfig(), mainBitmap.isMutable());
			String path = KapooCamera.saveBitmap(mainBitmap, saveFilePath);
			Intent returnIntent = new Intent();
			returnIntent.putExtra("filepath", path);
			mContext.setResult(RESULT_OK, returnIntent);
			mContext.finish();
		}
	}// end inner class

	/**
	 * 切换底图Bitmap
	 * 
	 * @param newBit
	 */
	public void changeMainBitmap(Bitmap newBit) {
		if (mainBitmap != null) {
			if (!mainBitmap.isRecycled()) {// 回收
				mainBitmap.recycle();
			}
			mainBitmap = newBit;
		} else {
			mainBitmap = newBit;
		}// end if
		mainImage.setImageBitmap(mainBitmap);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mLoadImageTask != null) {
			mLoadImageTask.cancel(true);
		}
	}

}// end class
