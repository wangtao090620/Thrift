package main.java.module.thrifttest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.proto.protocol.ImageRequest;
import com.proto.protocol.ImageResponse;
import com.proto.protocol.Results;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import main.java.module.thrifttest.adapter.ImageAdapter;
import main.java.module.thrifttest.proto.ImeServiceApi;
import module.thrifttest.R;


public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ImeServiceApi.shared().loadImage(new ImageRequest(), new ImeServiceApi.Callback<ImageResponse>() {
            @Override
            public void onSuccess(ImageResponse res, Map<String, List<String>> headers) {

                if (res == null || res.getResults() == null)
                    return;

                List<Results> resultses = new ArrayList<Results>();

                for (Object result : res.getResults()) {
                    if (result instanceof Results) {
                        Results result1 = ((Results) result);
                        resultses.add(result1);
                    }
                }

                if (resultses.size() > 0) {
                    mRecyclerView.setAdapter(new ImageAdapter(MainActivity.this, resultses));
                }
            }

            @Override
            public void onError(int code, String msg, Map<String, List<String>> headers, Throwable t) {
                super.onError(code, msg, headers, t);
            }
        });

//        SearchRequest searchRequest = new SearchRequest("hello", BuildConfig.GIPHY_KEY);
//
//        GiphyServiceApi.shared().search(searchRequest, new GiphyServiceApi.Callback<SearchResponse>() {
//            @Override
//            public void onSuccess(SearchResponse res, Map<String, List<String>> headers) {
//                if (res == null || res.getData() == null)
//                    return;
//
//                List<String> urls = new ArrayList<String>();
//                for (Object d : res.getData()) {
//                    if (d instanceof Data) {
//                        String url = ((Data) d).getImages().getFixed_width().getUrl();
//                        urls.add(url);
//                    }
//                }
//
//                System.out.println("haha success");
//            }
//
//            @Override
//            public void onError(int code, String msg, Map<String, List<String>> headers, Throwable t) {
//                super.onError(code, msg, headers, t);
//
//                System.out.println("haha error");
//            }
//        });

    }
}
