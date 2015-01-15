package com.huami.madroid.swipelistview;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class SwipeDelListViewActivity extends ActionBarActivity {

    private SwipeDelListView mListView;

    private ArrayList<String> mData = new ArrayList<String>() {
        {
            for(int i=0;i<50;i++) {
                add("左滑删除 " + i);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_listview_del);

        mListView = (SwipeDelListView) findViewById(R.id.swipe_listview);

        mListView.setAdapter(new MyAdapter());


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if(mListView.canClick()) {
                    Toast.makeText(SwipeDelListViewActivity.this, mData.get(position), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(null == convertView) {
                convertView = View.inflate(SwipeDelListViewActivity.this, R.layout.swipe_listview_item, null);
            }
            TextView tv = (TextView) convertView.findViewById(R.id.tv_text);
            TextView delete = (TextView) convertView.findViewById(R.id.tv_del);

            tv.setText(mData.get(position));

            final int pos = position;
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mData.remove(pos);
                    notifyDataSetChanged();
                    mListView.turnToNormal();
                }
            });

            return convertView;
        }
    }
}
