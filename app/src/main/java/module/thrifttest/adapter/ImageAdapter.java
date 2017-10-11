package main.java.module.thrifttest.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.proto.protocol.Results;

import java.util.List;

import main.java.module.thrifttest.util.GlideMgr;
import module.thrifttest.R;


public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageHolder> {

    private Context mContext;
    private List<Results> mData;

    public ImageAdapter(Context context, List<Results> data) {
        mContext = context;
        mData = data;
    }

    @Override
    public ImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_image, parent, false);
        return new ImageHolder(view);
    }

    @Override
    public void onBindViewHolder(final ImageHolder holder, int position) {
        holder.mTvWho.setText(mData.get(position).getWho());
        holder.mTvTime.setText(mData.get(position).getCreatedAt());
        GlideMgr.shared().loadImage(mContext, mData.get(position).getUrl().replace("https","http"), new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                holder.mGirlImage.setImageDrawable(resource);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ImageHolder extends RecyclerView.ViewHolder {

        private final TextView mTvWho;
        private final TextView mTvTime;
        private final ImageView mGirlImage;

        public ImageHolder(View view) {
            super(view);
            mTvWho = (TextView) view.findViewById(R.id.tv_who);
            mTvTime = (TextView) view.findViewById(R.id.tv_time);
            mGirlImage = (ImageView) view.findViewById(R.id.girl_pic);
        }
    }
}
