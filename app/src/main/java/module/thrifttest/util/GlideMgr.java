package main.java.module.thrifttest.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;

import main.java.module.thrifttest.MainApp;

/**
 * Created by dingchao on 2017/3/6.
 */
public class GlideMgr implements Initializable {

    private static final Logger mLog = LoggerFactory.getLogger("GlideMgr");

    private RequestManager mRequestManager;

    public static GlideMgr shared() {
        return MainApp.shared().get(GlideMgr.class);
    }

    @Override
    public void init(Context ctx) {

    }

    @Override
    public String getName() {
        return "GlideMgr";
    }

    public void loadImage(Context ctx, String url, final View view) {
        if (view instanceof ImageView) {
            loadImage(ctx, url, (ImageView) view);
        } else {
            loadImage(ctx, url, new SimpleTarget<GlideDrawable>() {
                @Override
                public void onResourceReady(GlideDrawable resource, GlideAnimation glideAnimation) {
                    view.setBackgroundDrawable(resource);
                }
            });
        }
    }

    public void loadImage(Fragment fragment, String url, final View view) {
        if (view instanceof ImageView) {
            loadImage(fragment, url, (ImageView) view);
        } else {
            loadImage(fragment, url, new SimpleTarget<GlideDrawable>() {
                @Override
                public void onResourceReady(GlideDrawable resource, GlideAnimation glideAnimation) {
                    view.setBackgroundDrawable(resource);
                }
            });
        }
    }

    public void loadImage(Context ctx, String url, int defDrawable, final View view) {
        if (view instanceof ImageView) {
            loadImage(ctx, url, defDrawable, (ImageView) view);
        } else {
            loadImage(ctx, url, defDrawable, new SimpleTarget<GlideDrawable>() {
                @Override
                public void onResourceReady(GlideDrawable resource, GlideAnimation glideAnimation) {
                    view.setBackgroundDrawable(resource);
                }
            });
        }
    }

    public void loadImage(Fragment fragment, String url, int defDrawable, final View view) {
        if (view instanceof ImageView) {
            loadImage(fragment, url, defDrawable, (ImageView) view);
        } else {
            loadImage(fragment, url, defDrawable, new SimpleTarget<GlideDrawable>() {
                @Override
                public void onResourceReady(GlideDrawable resource, GlideAnimation glideAnimation) {
                    view.setBackgroundDrawable(resource);
                }
            });
        }
    }

    public void loadImage(Context ctx, String url, int defDrawable, SimpleTarget<GlideDrawable> target) {
        mRequestManager = Glide.with(ctx);
        mRequestManager.load(url)
                .centerCrop()
                .dontAnimate()
//                .placeholder(defDrawable)
                .into(target);
    }

    public void loadImage(Fragment fragment, String url, int defDrawable, SimpleTarget<GlideDrawable> target) {
        mRequestManager = Glide.with(fragment);
        mRequestManager.load(url)
                .centerCrop()
                .dontAnimate()
//                .placeholder(defDrawable)
                .into(target);
    }

    private void loadImage(Context ctx, String url, int defDrawable, ImageView view) {

        mRequestManager = Glide.with(ctx);
        mRequestManager.load(url)
                .centerCrop()
                .dontAnimate()
//                .placeholder(defDrawable)
                .into(view);
    }

    private void loadImage(Fragment fragment, String url, int defDrawable, ImageView view) {

        mRequestManager = Glide.with(fragment);
        mRequestManager.load(url)
                .centerCrop()
                .dontAnimate()
//                .placeholder(defDrawable)
                .into(view);
    }

    private void loadImage(Context ctx, String url, ImageView view) {

        mRequestManager = Glide.with(ctx);
        mRequestManager.load(url)
                .centerCrop()
                .dontAnimate()
                .into(view);
    }

    private void loadImage(Fragment fragment, String url, ImageView view) {

        mRequestManager = Glide.with(fragment);
        mRequestManager.load(url)
                .centerCrop()
                .dontAnimate()
                .into(view);
    }

    public void loadImage(Context ctx, String url, SimpleTarget<GlideDrawable> target) {
        mRequestManager = Glide.with(ctx);
        mRequestManager.load(url)
                .centerCrop()
                .dontAnimate()
                .into(target);
    }

    public void loadBitmap(Context ctx, String url, SimpleTarget<Bitmap> target) {
        mRequestManager = Glide.with(ctx);
        mRequestManager.load(url)
                .asBitmap()
                .centerCrop()
                .dontAnimate()
                .into(target);
    }


    public Bitmap loadBitmap(Context ctx, String url, int iconWidth, int iconHeight) {
        mRequestManager = Glide.with(ctx);
        try {
            return mRequestManager.load(url)
                    .asBitmap()
                    .centerCrop()
                    .into(iconWidth, iconHeight)
                    .get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void loadImage(Fragment fragment, String url, SimpleTarget<GlideDrawable> target) {
        mRequestManager = Glide.with(fragment);
        mRequestManager.load(url)
                .centerCrop()
                .dontAnimate()
                .into(target);
    }

    public void loadBitmap(Fragment fragment, String url, SimpleTarget<Bitmap> target) {
        mRequestManager = Glide.with(fragment);
        mRequestManager.load(url)
                .asBitmap()
                .centerCrop()
                .dontAnimate()
                .into(target);
    }

    public void loadImage(Context ctx, String path, int defDrawable, ImageView view, RequestListener<String, GlideDrawable> requstlistener) {
        mRequestManager = Glide.with(ctx);
        mRequestManager.load(path)
                .centerCrop()
                .dontAnimate()
                .placeholder(defDrawable)
                .listener(requstlistener)
                .into(view);
    }

    public void loadImage(Context ctx, String path, int defDrawable, SimpleTarget<GlideDrawable> target, RequestListener<String, GlideDrawable> requstlistener) {
        mRequestManager = Glide.with(ctx);
        mRequestManager.load(path)
                .centerCrop()
                .dontAnimate()
                .placeholder(defDrawable)
                .listener(requstlistener)
                .into(target);
    }

    public void loadImage(Context ctx, int res, final ImageView target) {
        mRequestManager = Glide.with(ctx);
        mRequestManager.load(res)
                .asGif()
                .centerCrop()
                .dontAnimate()
                .into(target);
    }

    public void scrollNotRequest(AbsListView view) {
        if (view != null && mRequestManager != null) {
            view.setOnScrollListener(new AbsListView.OnScrollListener() {

                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {

                    switch (scrollState) {
                        case SCROLL_STATE_IDLE:
                        case SCROLL_STATE_TOUCH_SCROLL:
                            if (mRequestManager != null)
                                mRequestManager.onStart();
                            break;
                        case SCROLL_STATE_FLING:
                            if (mRequestManager != null)
                                mRequestManager.onStop();
                            break;
                    }
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                }
            });
        }
    }

    public void resumeRequest() {
        if (mRequestManager != null) {
            mRequestManager.onStart();
        }
    }

    public void pauseRequest() {
        if (mRequestManager != null) {
            mRequestManager.onStop();
        }
    }
}
