package com.esgi.ecole.bestway.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.esgi.ecole.bestway.R;
import com.esgi.ecole.bestway.adapters.ResultAdapter;
import com.esgi.ecole.bestway.models.Trajet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by mohsen raeisi on 07/09/2016.
 */
public class ResultTempsFragment extends Fragment {

    ListView resultListView;
    ResultAdapter adapter;
    public ResultTempsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_result_temps, container, false);
        assignViews(view);
        populateList();

        return view;

    }

    public void assignViews(View view){
        resultListView = (ListView) view.findViewById(R.id.listViewResultTemp);
    }

    public void populateList(){
       ArrayList<Trajet> trajets =  getArguments().getParcelableArrayList("trajets");

        if (trajets != null) {
            Collections.sort(trajets, new Comparator<Trajet>() {
                @Override
                public int compare(Trajet t1, Trajet t2) {
                    return t1.getDurationIndex() - t2.getDurationIndex();
                }
            });
        }

        adapter = new ResultAdapter(getContext());
        adapter.addItemsCollection(trajets);

        resultListView.setAdapter(adapter);

    }
}
