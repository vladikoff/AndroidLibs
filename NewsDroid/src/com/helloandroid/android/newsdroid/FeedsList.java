package com.helloandroid.android.newsdroid;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Menu.Item;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class FeedsList extends ListActivity {

	private static final int ACTIVITY_DELETE=1;
	private static final int ACTIVITY_INSERT=2;
	private static final int ACTIVITY_VIEW=3;
	
	private NewsDroidDB droidDB;
	private List<Feed> feeds;
	
	@Override
	protected void onCreate(Bundle icicle) {
		try {
	        super.onCreate(icicle);
	        setContentView(R.layout.feeds_list);
	        droidDB = new NewsDroidDB(this);
	        fillData();
		} catch (Throwable e) {
			Log.e("NewsDroid",e.toString());
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, ACTIVITY_INSERT, R.string.menu_insert);
        menu.add(0, ACTIVITY_DELETE, R.string.menu_delete);
        return true;
    }
	
	@Override
    public boolean onMenuItemSelected(int featureId, Item item) {
        super.onMenuItemSelected(featureId, item);
        switch(item.getId()) {
        case ACTIVITY_INSERT:
            createFeed();
            break;
        case ACTIVITY_DELETE:
            droidDB.deleteFeed(feeds.get(getSelection()).feedId);
            fillData();
            break;
        }
        
        return true;
    }
	
	private void createFeed() {
		Intent i = new Intent(this, URLEditor.class);
        startSubActivity(i, ACTIVITY_INSERT);
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			String data, Bundle extras) {
		super.onActivityResult(requestCode, resultCode, data, extras);
		fillData();
	}

	@Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Intent i = new Intent(this, ArticlesList.class);
        i.putExtra("feed_id", feeds.get(position).feedId);
        i.putExtra("title", feeds.get(position).title);
        i.putExtra("url", feeds.get(position).url.toString());        
        startSubActivity(i, ACTIVITY_VIEW);
    }
	
	private void fillData() {

        List<String> items = new ArrayList<String>();

        feeds = droidDB.getFeeds();
        for (Feed feed : feeds) {
            items.add(feed.title);
        }
        
        ArrayAdapter<String> notes = 
            new ArrayAdapter<String>(this, R.layout.feeds_row, items);
        setListAdapter(notes);
	}

	
	
}
