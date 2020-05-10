package com.sergiescoruela.parties.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.sergiescoruela.parties.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

public class AdapterSlider extends PagerAdapter {

    private ArrayList<String> imagen ;
    private Context context;
    private LayoutInflater layoutInflater;

    public AdapterSlider(ArrayList<String> imagen, Context context ) {
        this.imagen = imagen;
        this.context = context;
        layoutInflater = layoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return imagen.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return  (view.equals(object));
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {


        View item_view = layoutInflater.inflate(R.layout.elemento_slider,container,false);
        ImageView imageView =  item_view.findViewById(R.id.imgslider);

        Picasso.get().load(imagen.get(position)).into(imageView);
        container.addView(item_view);

        return item_view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
