package com.esgi.ecole.bestway.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.esgi.ecole.bestway.R;
import com.esgi.ecole.bestway.commun.BestWayClient;
import com.esgi.ecole.bestway.fragments.ResultEcologieFragment;
import com.esgi.ecole.bestway.fragments.ResultPratiqueFragment;
import com.esgi.ecole.bestway.fragments.ResultTempsFragment;
import com.esgi.ecole.bestway.models.Session;
import com.esgi.ecole.bestway.models.Trajet;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class ResulatActivity extends AppCompatActivity {


    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String arr = "";
    private String dep ="";
    private String choices ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resulat);

      arr =  getIntent().getExtras().getString("arr");
      dep =  getIntent().getExtras().getString("dep");
      choices = getIntent().getExtras().getString("choices");

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        FindBestWay();


       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    private void initIHM(ArrayList<Trajet> trajets){
        setupViewPager(viewPager,trajets);

        tabLayout.setupWithViewPager(viewPager);
    }
    private void FindBestWay(){
        String url = BestWayClient.BASE_URL + BestWayClient.CALCUL;

        RequestParams requestParams = new RequestParams();

        requestParams.add("dep",dep);
        requestParams.add("arr",arr);
        requestParams.add("transp",choices);

        Session session = BestWayClient.getSession(this);

        BestWayClient.getWithSession(url,session,requestParams,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                ArrayList<Trajet> trajets =  parseResponseTrajets(response);
                Log.e("SUCCES",response.toString());
                initIHM(trajets);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e("ERROR","REGISTER");

            }
        });


    }

    private ArrayList<Trajet> parseResponseTrajets(JSONObject response){
        ArrayList<Trajet> trajets = new ArrayList<>();

        try {
            JSONObject resp = response.getJSONObject("response");
            if(resp!=null){
                JSONArray trajetsArray = resp.getJSONArray("responseArray");
                if(trajetsArray!=null){
                    for (int i = 0 ; i < trajetsArray.length();i++){
                        JSONObject trajetObject = trajetsArray.getJSONObject(i);

                        Trajet trajet = new Trajet(Integer.parseInt(trajetObject.getString("duration")),
                                trajetObject.getString("transport"),
                                Integer.parseInt(trajetObject.getString("durationIndex")),
                                Integer.parseInt(trajetObject.getString("praticalIndex")));


                        trajets.add(trajet);
                    }
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return trajets;
    }


    private void setupViewPager(ViewPager viewPager , ArrayList<Trajet> trajets) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        Bundle args = new Bundle();
        args.putParcelableArrayList("trajets",trajets);


        ResultTempsFragment resultTempsFragment = new ResultTempsFragment();
        resultTempsFragment.setArguments(args);

        ResultPratiqueFragment resultPratiqueFragment = new ResultPratiqueFragment();
        resultPratiqueFragment.setArguments(args);


        adapter.addFragment(resultTempsFragment, getString(R.string.classement_temps));

        adapter.addFragment(resultPratiqueFragment, getString(R.string.classement_pratique));
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
