package com.huami.madroid.swipelistview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    private ListView listView ;
    private ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }


    private void init(){
        listView = (ListView)findViewById(R.id.listview_main) ;

        String[] dataSourceList = {"SlideCutListView Demo1" ,"SwipeDelListView Demo2"} ;

        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,dataSourceList) ;

        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        openActivity(SlideCutActivity.class);
                        showToast("listView item is clicked") ;

                        break;
                    case 1:
                        openActivity(SwipeDelListViewActivity.class);
                        showToast("swipeDelListView is clicked");
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void openActivity(Class name){
        Intent slideCutIntent = new Intent() ;
        slideCutIntent.setClass(this,name) ;
        startActivity(slideCutIntent);
    }

    private void showToast(String msg){
        Toast.makeText(MainActivity.this,msg,Toast.LENGTH_LONG).show();
    }

}
