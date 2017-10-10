package main.java.module.thrifttest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.list_view);

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
                    mListView.setAdapter(new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,urls));
                }
            }
        });
    }
}
