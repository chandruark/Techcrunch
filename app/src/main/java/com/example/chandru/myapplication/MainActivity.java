package com.example.chandru.myapplication;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.chandru.myapplication.Latest;
import com.example.chandru.myapplication.Popular;
import com.example.chandru.myapplication.R;
import com.example.chandru.myapplication.Top;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;


    private ViewPager mViewPager;
    JSONArray articles = new JSONArray();
    JSONArray articles1 = new JSONArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        Log.i("Zx","exec");
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://newsapi.org/v1/articles?source=techcrunch&sortBy=top&apiKey=c3adcc66d869449b88dddcf98d928211";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    articles = response.getJSONArray("articles");
                    mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
                    mViewPager = (ViewPager) findViewById(R.id.container);
                    mViewPager.setAdapter(mSectionsPagerAdapter);
                    TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
                    tabLayout.setupWithViewPager(mViewPager);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Oops something went wrong",Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonObjectRequest);
        String url1 = "https://newsapi.org/v1/articles?source=techcrunch&sortBy=latest&apiKey=c3adcc66d869449b88dddcf98d928211";
        JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.GET, url1, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    articles1 = response.getJSONArray("articles");
                    mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
                    mViewPager = (ViewPager) findViewById(R.id.container);
                    mViewPager.setAdapter(mSectionsPagerAdapter);
                    TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
                    tabLayout.setupWithViewPager(mViewPager);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Oops something went wrong",Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonObjectRequest1);
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
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

//    /**
//     * A placeholder fragment containing a simple view.
//     */
//    public static class PlaceholderFragment extends Fragment {
//        /**
//         * The fragment argument representing the section number for this
//         * fragment.
//         */
//        private static final String ARG_SECTION_NUMBER = "section_number";
//
//        public PlaceholderFragment() {
//        }
//
//        /**
//         * Returns a new instance of this fragment for the given section
//         * number.
//         */
//        public static PlaceholderFragment newInstance(int sectionNumber) {
//            PlaceholderFragment fragment = new PlaceholderFragment();
//            Bundle args = new Bundle();
//            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
//            fragment.setArguments(args);
//            return fragment;
//        }
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                                 Bundle savedInstanceState) {
//            View rootView = inflater.inflate(R.layout.top, container, false);
//            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
//            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
//            return rootView;
//        }
//    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    Top t1 = new Top(MainActivity.this,articles);
                    return t1;
                case 1:
                    Popular t2 = new Popular();
                    return t2;
                case 2:
                    Latest t3 = new Latest(MainActivity.this,articles1);
                    return t3;
                default:
                    return null;
            }

        }
        @Override
        public int getCount () {
            // Show 3 total pages.
            return 3;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Top";
                case 1:
                    return "Popular";
                case 2:
                    return "Latest";
            }
            return null;
        }
    }
}