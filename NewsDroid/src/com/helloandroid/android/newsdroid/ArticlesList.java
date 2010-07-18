package com.helloandroid.android.newsdroid;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.net.ContentURI;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ArticlesList extends ListActivity {

	private List<Article> articles;
	private Feed feed;
	private NewsDroidDB droidDB;
	
	@Override
	protected void onCreate(Bundle icicle) {
		try {
			super.onCreate(icicle);
			droidDB = new NewsDroidDB(this);
			setContentView(R.layout.articles_list);
			
			feed = new Feed();
			
			if (icicle != null) {
				feed.feedId = icicle.getLong("feed_id");
				feed.title = icicle.getString("title");
				feed.url = new URL(icicle.getString("url"));
			} else {
				Bundle extras = getIntent().getExtras();
				feed.feedId = extras.getLong("feed_id");
				feed.title = extras.getString("title");
				feed.url = new URL(extras.getString("url"));

				droidDB.deleteAricles(feed.feedId);
				RSSHandler rh = new RSSHandler();
		        rh.updateArticles(this, feed);
			}
	        
			fillData();

		} catch (MalformedURLException e) {
			Log.e("NewsDroid",e.toString());
		}
		
	}
	
	@Override
    protected void onFreeze(Bundle outState) {
        super.onFreeze(outState);
        outState.putLong("feed_id", feed.feedId);
        outState.putString("title", feed.title);
        outState.putString("url", feed.url.toString());
    }
	 
	
	@Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent myIntent = new Intent(Intent.VIEW_ACTION, ContentURI.create(articles.get(position).url.toString()));
        startActivity(myIntent);
    }
	
	private void fillData() {

        List<String> items = new ArrayList<String>();
        articles = droidDB.getArticles(feed.feedId);
        for (Article article : articles) {
            items.add(article.title);
        }
        
        ArrayAdapter<String> notes = 
            new ArrayAdapter<String>(this, R.layout.article_row, items);
        setListAdapter(notes);
	}

}
