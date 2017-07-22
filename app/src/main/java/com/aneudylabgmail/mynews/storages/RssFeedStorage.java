package com.aneudylabgmail.mynews.storages;

import com.aneudylabgmail.mynews.model.RssFeed;

import java.util.ArrayList;

/**
 * Created by AneudyEnrique on 22/7/17.
 *
 */

public class RssFeedStorage {

    private static ArrayList<RssFeed> rssFeeds;
    private ArrayList<RssFeed> getDefaultRssFeeds(){
        ArrayList<RssFeed> defaultFeeds = new ArrayList<>();

        defaultFeeds.add(new RssFeed("https://www.diariolibre.com/rss/portada.xml", "Diario Libre"));
        defaultFeeds.add(new RssFeed("http://www.listindiario.com/rss/portada/", "Listin Diario"));
        defaultFeeds.add(new RssFeed("http://elnacional.com.do/feed/", "El Nacional"));

        return defaultFeeds;
    }

    public ArrayList<RssFeed> get(){
        if(rssFeeds == null){
            rssFeeds = getDefaultRssFeeds();
        }

        return rssFeeds;
    }
}
