package com.hxl.scdjc_kotlin.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.hxl.scdjc_kotlin.R;
import com.hxl.scdjc_kotlin.util.ToastUtil;
import com.hxl.scdjc_kotlin.view.photoview.PhotoView;
import com.hxl.scdjc_kotlin.view.photoview.PhotoViewAttacher;

import java.util.concurrent.ExecutionException;

/**
 * 查看图片的dialog
 */
public class PreviewImageDialog extends Dialog {
    private Activity mRootActivity = null;
    private String imgUrl;
    private Point mScreenPoint = new Point();
    private PhotoViewAttacher mAttacher;
    private PhotoView ivPhoto;

    public PreviewImageDialog(Activity arg, String url) {
        super(arg, R.style.MyDialog);
        mRootActivity = arg;
        imgUrl = url;
    }


    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new BitmapDrawable());
        this.setContentView(R.layout.dialog_view_image);
        ivPhoto = findViewById(R.id.photo_view);
        initDialogWindow();
        initView();
    }

    private void initView() {
        if (imgUrl != null) {

            try {
                Bitmap bitmap = Glide.with(mRootActivity)
                        .load(imgUrl)
                        .asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.RESULT)
                        .listener(new RequestListener<String, Bitmap>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                                ToastUtil.Companion.show(mRootActivity, "图片加载失败");
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                mAttacher = new PhotoViewAttacher(ivPhoto);
                                mAttacher.setOnPhotoTapListener((view, x, y) -> {
                                    dismiss();
                                });//单击监听
                                mAttacher.setOnClickListener(v -> dismiss());
                                return false;
                            }
                        })
                        .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .get();

                ivPhoto.setImageBitmap(bitmap);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

    }


    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    private void initDialogWindow() {
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        WindowManager m = mRootActivity.getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        try {
            d.getSize(mScreenPoint);
        } catch (NoSuchMethodError ignore) { // Older device
            mScreenPoint.x = d.getWidth();
            mScreenPoint.y = d.getHeight();
        }

        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
//        p.width = DensityUtil.dip2px(getContext(), 160);
        p.width = (int) (mScreenPoint.x);  // 宽度设置为屏幕的0.75
//        p.height = (int) (mScreenPoint.y);  // 高度设置为屏幕的0.35
        dialogWindow.setAttributes(p);
    }
}
