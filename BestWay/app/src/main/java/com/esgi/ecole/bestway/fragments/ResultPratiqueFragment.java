package com.esgi.ecole.bestway.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.esgi.ecole.bestway.R;
import com.esgi.ecole.bestway.adapters.ResultAdapter;
import com.esgi.ecole.bestway.models.Trajet;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResultPratiqueFragment extends Fragment {

    ListView resultListView;
    ResultAdapter adapter;

    public ResultPratiqueFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_result_pratique, container, false);
        assignViews(view);
        populateList();


        return view;
    }



    public void assignViews(View view){
        resultListView = (ListView) view.findViewById(R.id.listViewResultTemp);
    }

    public void populateList(){

        Trajet trajet1 = new Trajet("20 mn" , 3);
        Trajet trajet2 = new Trajet("25 mn" , 4);
        Trajet trajet3 = new Trajet("30 mn" , 2);
        Trajet trajet4 = new Trajet("25 mn" , 5);
        Trajet trajet5 = new Trajet("45 mn" , 1);

        ArrayList<Trajet> trajetsList = new ArrayList<>();
        trajetsList.add(trajet1);
        trajetsList.add(trajet2);
        trajetsList.add(trajet3);
        trajetsList.add(trajet4);
        trajetsList.add(trajet5);

        adapter = new ResultAdapter(getContext());
        adapter.addItemsCollection(trajetsList);

        resultListView.setAdapter(adapter);

    }

}
