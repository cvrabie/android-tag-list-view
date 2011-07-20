package org.codeandmagic.android;

import org.codeandmagic.android.TagListView.TagListener;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class TagListViewActivity extends Activity implements TagListener
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        TagListView tagListView = (TagListView) findViewById(R.id.tagview);
        tagListView.addTag("work");
        tagListView.addTag("servers");
        tagListView.addTag("important");
        tagListView.addTag("evelyne");
        tagListView.addTag("cristian");
        tagListView.addTag("hot");
        tagListView.addTag("summer");
        tagListView.addTag("lorem ipsum dolor");

		tagListView.addTagListener(this);
	}

	@Override
	public void onAddedTag(String tag) {
		Log.d("tagview", "added tag " + tag);
	}

	@Override
	public void onRemovedTag(String tag) {
		Log.d("tagview", "removed tag " + tag);
	}
}
