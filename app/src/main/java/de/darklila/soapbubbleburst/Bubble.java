package de.darklila.soapbubbleburst;

import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.Random;

/**
 * Created by Bettina on 15.02.2016.
 */
public class Bubble {
    private final static int LIFETIME=1000;
    private float x,y,vx,vy,size;
    private int lifetime;
    private ImageView view;

    public Bubble (FrameLayout container, float vMax,float sizeMax, Random rnd, Drawable drawable) {
        lifetime=LIFETIME;
        size=(0.5f + rnd.nextFloat()/2) * sizeMax;
        x=rnd.nextFloat()* (container.getWidth()-size);
        y = rnd.nextFloat()*(container.getHeight()-size);
        vx = rnd.nextFloat()*vMax*(rnd.nextBoolean()?1:-1);
        vy = rnd.nextFloat()*vMax*(rnd.nextBoolean()?1:-1);
        view = new ImageView(container.getContext());
        view.setImageDrawable(drawable);
        //view.setOnClickListener(this);
        container.addView(view);
        move();
    }

    private void move() {
        x += vx;
        y += vy;
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
        params.width = Math.round(size);
        params.height = Math.round(size);
        params.leftMargin= Math.round(x);
        params.topMargin= Math.round(y);
        params.gravity= Gravity.LEFT+ Gravity.TOP;
        view.setLayoutParams(params);
        lifetime--;
        //if(lifetime<=0) burst();
    }

}
