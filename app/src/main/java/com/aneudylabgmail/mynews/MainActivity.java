package com.aneudylabgmail.mynews;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.aneudylabgmail.mynews.model.RssFeed;
import com.aneudylabgmail.mynews.storages.RssFeedStorage;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView buttons = (ListView) findViewById(R.id.buttons);

        RssFeedStorage rssFeedStorage = new RssFeedStorage();
        buttons.setAdapter(new ButtonsAdapter(rssFeedStorage.get()));
    }

    private class ButtonsAdapter extends ArrayAdapter<RssFeed> {
        ButtonsAdapter(@NonNull List<RssFeed> objects) {
            this(MainActivity.this, 0, objects);
        }

        ButtonsAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<RssFeed> objects) {
            super(context, resource, objects);
        }

        private void setUpView(View view, final RssFeed rssFeed){
            Button button = (Button) view.findViewById(R.id.rss_feed_button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, RssFeedActivity.class);
                    intent.putExtra(RssFeedActivity.NEWS_URL, rssFeed.getUrl());
                    startActivity(intent);
                }
            });

            button.setText(rssFeed.getName());
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            final RssFeed rssFeed = getItem(position);
            if (rssFeed == null) {
                return super.getView(position, convertView, parent);
            }

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.rss_button_layout, parent, false);
            }

            setUpView(convertView, rssFeed);

            return convertView;
        }

    }
}
