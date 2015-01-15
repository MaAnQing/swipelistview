package com.huami.madroid.swipelistview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
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

        String[] dataSourceList = {"listview Demo1" ,"listview Demo2"} ;

        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,dataSourceList) ;

        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        openActivity(SlideCutActivity.class);
                        showToast("listview item is clicked") ;
                       // Toast.makeText(MainActivity.this,"listview item is clicked",Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        openActivity(SwipeDelListViewActivity.class);
                        showToast("swipeDelListview is clicked");
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void openActivity(Class name){
        Intent slidecutIntent = new Intent() ;
        slidecutIntent.setClass(this,name) ;
        startActivity(slidecutIntent);
    }

    private void showToast(String msg){
        Toast.makeText(MainActivity.this,msg,Toast.LENGTH_LONG).show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
