package com.kapoocino.camera.editimage.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.kapoocino.camera.R;
import com.kapoocino.camera.editimage.EditImageActivity;
import com.kapoocino.camera.editimage.view.imagezoom.ImageViewTouchBase;


/**
 * 工具栏主菜单
 *
 * @author panyi
 */
public class MainMenuFragment extends Fragment {
    public static final int INDEX = 0;

    public static final String TAG = MainMenuFragment.class.getName();
    private View mainView;
    private EditImageActivity activity;

    private View stickerBtn;// 贴图按钮
    private View filterBtn;// 滤镜按钮

    public static MainMenuFragment newInstance(EditImageActivity activity) {
        MainMenuFragment fragment = new MainMenuFragment();
        fragment.activity = activity;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_edit_image_main_menu,
                null);
        stickerBtn = mainView.findViewById(R.id.btn_stickers);
        filterBtn = mainView.findViewById(R.id.btn_filter);
        return mainView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        stickerBtn.setOnClickListener(new StickerClick());
        filterBtn.setOnClickListener(new FliterClick());
    }

    /**
     * 贴图模式
     *
     * @author panyi
     */
    private final class StickerClick implements OnClickListener {
        @Override
        public void onClick(View v) {
            activity.mode = EditImageActivity.MODE_STICKERS;
            activity.mStickerFragment.getmStickerView().setVisibility(
                    View.VISIBLE);
            activity.bottomGallery.setCurrentItem(StickerFragment.INDEX);
            activity.bannerFlipper.showNext();
        }
    }// end inner class

    /**
     * 滤镜模式
     *
     * @author panyi
     */
    private final class FliterClick implements OnClickListener {
        @Override
        public void onClick(View v) {
            activity.mode = EditImageActivity.MODE_FILTER;
            activity.mFliterListFragment.setCurrentBitmap(activity.mainBitmap);
            activity.mainImage.setImageBitmap(activity.mainBitmap);
            activity.mainImage.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);
            activity.mainImage.setScaleEnabled(false);
            activity.bottomGallery.setCurrentItem(FliterListFragment.INDEX);
            activity.bannerFlipper.showNext();
        }
    }// end inner class

}// end class
