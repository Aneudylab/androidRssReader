package com.aneudylabgmail.mynews.model;

/**
 * Created by AneudyEnrique on 22/7/17.
 *
 */

public class RssFeed {
    private String url;
    private String Name;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public RssFeed(String url, String name) {
        this.url = url;
        Name = name;
    }
}
