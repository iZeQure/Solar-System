package com.example.solarsystem;

import android.app.Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

public class MyListAdapter extends ArrayAdapter<Planet> {

    private final Activity context;
    private ArrayList<Planet> planets;

    public MyListAdapter(Activity context, ArrayList<Planet> planets) {
        super(context, R.layout.planet_list_item,planets);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.planets = planets;

    }

    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.planet_list_item, null,true);

        TextView titleText = (TextView) rowView.findViewById(R.id.title);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        Switch planetLight = (Switch) rowView.findViewById(R.id.isOn);

        titleText.setText(planets.get(position).getName());
        imageView.setImageResource(planets.get(position).getImageId());

        planetLight.setChecked(planets.get(position).getLightIsOn());
        planetLight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                planets.get(position).setLightIsOn(isChecked);
            }
        });

        return rowView;

    };
}