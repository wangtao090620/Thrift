package main.java.module.thrifttest;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.giphy.protocol.Data;
import com.giphy.protocol.SearchRequest;
import com.giphy.protocol.SearchResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import main.java.module.thrifttest.giphy.GiphyServiceApi;
import module.thrifttest.BuildConfig;
import module.thrifttest.R;


public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        SearchRequest searchRequest = new SearchRequest("hello", BuildConfig.GIPHY_KEY);

        GiphyServiceApi.shared().search(searchRequest, new GiphyServiceApi.Callback<SearchResponse>() {
            @Override
            public void onSuccess(SearchResponse res, Map<String, List<String>> headers) {
                if (res == null || res.getData() == null)
                    return;

                List<String> urls = new ArrayList<String>();
                for (Object d : res.getData()) {
                    if (d instanceof Data) {
                        String url = ((Data) d).getImages().getFixed_width().getUrl();
                        urls.add(url);
                    }
                }

                if (urls.size() > 0) {
                    mRecyclerView.setAdapter(new GiphyAdapter(MainActivity.this, urls));

                }
            }
        });
    }

    class GiphyAdapter extends RecyclerView.Adapter<GiphyAdapter.GiphyHolder> {

        private Context mContext;
        private List<String> mData;

        public GiphyAdapter(Context context, List<String> data) {
            mContext = context;
            mData = data;
        }

        @Override
        public GiphyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            ImageView view = (ImageView) LayoutInflater.from(mContext).inflate(R.layout.item_giphy, parent, false);
            return new GiphyHolder(view);
        }

        @Override
        public void onBindViewHolder(GiphyHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return mData != null ? mData.size() : 0;
        }


        class GiphyHolder extends RecyclerView.ViewHolder {


            private ImageView mView;

            public GiphyHolder(ImageView view) {
                super(view);
                mView = view;
            }
        }
    }
}
