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
import com.esgi.ecole.bestway.models.Line;
import com.esgi.ecole.bestway.models.LineSubway;
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
        mData.clear();
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


            convertView = mInflater.inflate(R.layout.trajet_cell, null);

            holder = new ResultViewHolder();
            holder.tempsTextView = (TextView) convertView.findViewById(R.id.textViewTemps) ;
            holder.typeTextView = (TextView) convertView.findViewById(R.id.textViewType) ;
            holder.lineTextView = (TextView) convertView.findViewById(R.id.textViewLines) ;
            holder.infoTextView = (TextView) convertView.findViewById(R.id.textViewInfo) ;

            holder.iconImageView = (ImageView) convertView.findViewById(R.id.imageViewIcon) ;
            convertView.setTag(holder);


        if(currentItem.getDuration()/60 < 60) {
            holder.tempsTextView.setText(currentItem.getDuration() / 60 + " mins");
        }else{
            int heures = (currentItem.getDuration()/60)/60;
            int mins = (currentItem.getDuration()/60) - (heures * 60 );
            holder.tempsTextView.setText(heures + "h " + mins +" mins" );
        }



        if(currentItem.getLines() != null && currentItem.getLines().size() >0) {

            for (int i = 0 ; i<currentItem.getLines().size() ; i++) {
                if(currentItem.getLines().get(i).getVehicle().equals("SUBWAY") && currentItem.getType() == 3){
                    LineSubway line = (LineSubway) currentItem.getLines().get(i);
                    holder.lineTextView.setText("Ligne : " + line.getNumber());

                    holder.infoTextView.setText("Info : " + line.getMessage());
                    break;
                }


                if(currentItem.getLines().get(i).getVehicle().equals("BUS") && currentItem.getType() == 4){
                    Line line = currentItem.getLines().get(i);
                    holder.lineTextView.setText("Ligne : " + line.getNumber());
                    break;
                }

                if(currentItem.getLines().get(i).getVehicle().equals("COMMUTER_TRAIN") && currentItem.getType() == 6){
                    Line line = currentItem.getLines().get(i);
                    holder.lineTextView.setText("Ligne : " + line.getNumber());
                    break;
                }

            }

        }






        switch (currentItem.getType()){
            case 1 :
                holder.iconImageView.setImageResource(R.drawable.walk);
                holder.typeTextView.setText("A pied");

                break;
            case 2 :
                holder.iconImageView.setImageResource(R.drawable.bike);
                holder.typeTextView.setText("A vélo");

                break;
            case 3 :
                holder.iconImageView.setImageResource(R.drawable.metro);
                holder.typeTextView.setText("Métro");

                break;
            case 4 :
                holder.iconImageView.setImageResource(R.drawable.bus);
                holder.typeTextView.setText("Bus");

                break;
            case 5 :
                holder.iconImageView.setImageResource(R.drawable.car);
                holder.typeTextView.setText("En voiture");
                break;
            case 6 :
                holder.iconImageView.setImageResource(R.drawable.train);
                holder.typeTextView.setText("Train");
                break;
            case 7 :
                holder.iconImageView.setImageResource(R.drawable.metro);
                holder.typeTextView.setText("Tram");
                break;
        }


        if(position==0) {
            holder.tempsTextView.setTypeface(null, Typeface.BOLD);
            holder.tempsTextView.setTextSize(25);
        }

        return convertView;
    }


    static class ResultViewHolder {
        public TextView tempsTextView;
        public TextView typeTextView;
        public TextView lineTextView;
        public TextView infoTextView;

        public ImageView iconImageView;

    }
}
