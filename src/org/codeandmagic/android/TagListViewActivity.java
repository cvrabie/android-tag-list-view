package org.codeandmagic.android;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.Arrays;

public class TagListViewActivity extends Activity
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

        Log.d("tagview", "started");
    }
}
