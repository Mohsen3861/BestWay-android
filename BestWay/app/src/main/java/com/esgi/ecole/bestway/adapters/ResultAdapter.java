package com.esgi.ecole.bestway.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.esgi.ecole.bestway.R;
import com.esgi.ecole.bestway.models.Trajet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by mohsen raeisi on 07/09/2016.
 */
public class ResultAdapter extends BaseAdapter {

    private List<Trajet> mData = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;

    public ResultAdapter(Context context){
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addItem(Trajet item) {
        mData.add(item);
        notifyDataSetChanged();
    }

    public void addItemsCollection(List<Trajet> elements) {
        // mData.clear();
        mData.addAll(elements);


    }


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
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ResultViewHolder holder = null;
        final Trajet currentItem = mData.get(position);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.trajet_cell, null);

            holder = new ResultViewHolder();
            holder.tempsTextView = (TextView) convertView.findViewById(R.id.textViewTemps) ;
            holder.iconImageView = (ImageView) convertView.findViewById(R.id.imageViewIcon) ;
            convertView.setTag(holder);
        } else {
            holder = (ResultViewHolder) convertView.getTag();
        }
        holder.tempsTextView.setText(currentItem.getTemps());

        if(position==0) {
            holder.tempsTextView.setTypeface(null, Typeface.BOLD);
            holder.tempsTextView.setTextSize(25);
        }


        switch (currentItem.getType()){
            case 1 :
                holder.iconImageView.setImageResource(R.drawable.walk);
                break;
            case 2 :
                holder.iconImageView.setImageResource(R.drawable.bike);

                break;
            case 3 :
                holder.iconImageView.setImageResource(R.drawable.metro);

                break;
            case 4 :
                holder.iconImageView.setImageResource(R.drawable.bus);

                break;
            case 5 :
                holder.iconImageView.setImageResource(R.drawable.car);

                break;
        }

        return convertView;
    }


    static class ResultViewHolder {
        public TextView tempsTextView;
        public ImageView iconImageView;

    }
}
