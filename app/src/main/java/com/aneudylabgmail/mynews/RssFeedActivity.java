package com.aneudylabgmail.mynews;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.prof.rssparser.Article;
import com.prof.rssparser.Parser;

import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.List;

public class RssFeedActivity extends AppCompatActivity implements Parser.OnTaskCompleted {
    public static final String NEWS_URL = "NEWS_URL";

    ProgressDialog progress;
    ListView rssItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rss_feed);

        rssItems = (ListView) findViewById(R.id.rss_items);

        Parser rssParser = new Parser();
        rssParser.onFinish(this);
        progress = ProgressDialog.show(this, null, null, true, false);
        progress.setContentView(R.layout.progress_layout);

        String rssUrl = getIntent().getStringExtra(NEWS_URL);
        rssParser.execute(rssUrl);
    }

    @Override
    public void onTaskCompleted(final ArrayList<Article> list) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                rssItems.setAdapter(new FeedAdapter(list));
                progress.dismiss();
            }
        });
    }

    @Override
    public void onError() {
        Toast.makeText(RssFeedActivity.this, "Error...", Toast.LENGTH_SHORT).show();
    }

    private class FeedAdapter extends ArrayAdapter<Article> {

        FeedAdapter(@NonNull List<Article> objects) {
            this(RssFeedActivity.this, 0, objects);
        }

        FeedAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Article> objects) {
            super(context, resource, objects);
        }

        private void setUpView(@NonNull View view, final Article article){
            setText(view, R.id.title, article.getTitle());
            setText(view, R.id.description, Jsoup.parse(article.getDescription()).text());

            Button read = (Button) view.findViewById(R.id.read_new);
            read.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(article.getLink()));
                    startActivity(intent);
                }
            });
        }
        private void setText(@NonNull View view, @IdRes int resource, String text) {
            TextView textView = (TextView) view.findViewById(resource);
            textView.setText(text);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            final Article article = getItem(position);

            if(article == null) {
                return super.getView(position, convertView, parent);
            }

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.feed_item_layout, parent, false);
            }
            setUpView(convertView, article);
            return convertView;
        }
    }
}
