package com.codepath.newyorktimessearch.activities;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.codepath.newyorktimessearch.Article;
import com.codepath.newyorktimessearch.ArticleRecyclerViewAdapter;
import com.codepath.newyorktimessearch.EndlessRecyclerViewScrollListener;
import com.codepath.newyorktimessearch.ItemClickSupport;
import com.codepath.newyorktimessearch.R;
import com.codepath.newyorktimessearch.SearchFilters;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity {

    EditText etQuery;
    RecyclerView rvResults;
    Button btnSearch;
    ArticleRecyclerViewAdapter adapter;
    String query;
    ClipData.Item filter;
    public static final int REQUEST_CODE = 55;
    SearchFilters filters;

    ArrayList<Article> articles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setUpViews();

        filters = new SearchFilters();

        getIntent().getStringExtra("date");

    }

    public void setUpViews(){
        //etQuery = (EditText) findViewById(R.id.etQuery);
        rvResults = (RecyclerView) findViewById(R.id.rvResults);
        //btnSearch = (Button) findViewById(R.id.btnSearch);
        articles = new ArrayList<>();
        // Create adapter passing in the sample user data

        adapter = new ArticleRecyclerViewAdapter(this, articles);
        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        rvResults.setLayoutManager(gridLayoutManager);
        rvResults.addOnScrollListener(new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                customLoadMoreDataFromApi(page);
            }
        });


        // Attach the adapter to the recyclerview to populate items
        rvResults.setAdapter(adapter);

        ItemClickSupport.addTo(rvResults).setOnItemClickListener(
                new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        //create intent to display article
                        Intent i = new Intent(getApplicationContext(), ArticleActivity.class);
                        //get article to display
                        Article article = articles.get(position);
                        //pass in that article into the intent
                        i.putExtra("article", article);
                        //launch the activity
                        startActivity(i);
                    }
                }
        );

        //filter = (ClipData.Item) findViewById(R.id.menu_item_filter);

    }

    public void customLoadMoreDataFromApi(int page) {
        loadArticles(page);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String myQuery) {
                // perform query here
                query = myQuery;
                loadArticles(0);
                // workaround to avoid issues with some emulators and keyboard devices firing twice if a keyboard enter is used
                // see https://code.google.com/p/android/issues/detail?id=24599
                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        //MenuItem filterItem = menu.findItem(R.id.menu_item_filter);

        /*
        filterItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(SearchActivity.this, FilterActivity.class);
                startActivity(intent);
                System.out.println("filter clicked");
                return true;
            }
        });

        */
        return super.onCreateOptionsMenu(menu);
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

    public void loadArticles(int page){
        //String query = etQuery.getText().toString();
        //Toast.makeText(this, "Searching for " + query, Toast.LENGTH_LONG).show();

        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://api.nytimes.com/svc/search/v2/articlesearch.json";
        RequestParams params = new RequestParams();
        params.put("api-key", "e9eaa94eab9b4156ab10c4acdaf1780c");
        params.put("page", page);
        params.put("q", query);
        params.put("sort", filters.order);
        System.out.println("order" + filters.order);
        //DO I NEED TO DO THIS IS OR IS IT FINE IF DATE EQUALS NULL?
        System.out.println("begin date" + filters.beginDate);
        if (filters.beginDate != null && !filters.beginDate.equals("0000")) {
            params.put("begin_date", filters.beginDate);
            System.out.println("looking by date");
        }
        if (page == 0) {
            adapter.clear();
        }
        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("DEBUG", response.toString());
                JSONArray articleJsonResults = null;

                try{
                    articleJsonResults = response.getJSONObject("response").getJSONArray("docs");
                    adapter.addAll(Article.fromJSONArray(articleJsonResults));
                    Log.d("DEBUG", articles.toString());
                } catch(JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    public void launchActivity(MenuItem item) {
        Intent intent = new Intent(this, FilterActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
        System.out.println("in launch activity");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE){
            if(resultCode == RESULT_OK){
               filters = (SearchFilters) data.getSerializableExtra("filter");
                       //= data.getIntExtra("date", 0);
               //String myDate = data.getStringExtra("myDate");
               //System.out.println("the date: " + d);
               //System.out.println("my date: " + myDate);
                //query = query + "&fq=begin_date:" + date;
                loadArticles(0);
                System.out.println(articles.size());
            } else {
                //Handle faliure case
            }
        }

    }

    /*
    public void onArticleSearch(View view) {
        loadArticles(0);
    }

    */
}
