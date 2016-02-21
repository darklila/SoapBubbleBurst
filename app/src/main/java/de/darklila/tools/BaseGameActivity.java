package de.darklila.tools;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bettina on 15.02.2016.
 */
public class BaseGameActivity extends Activity {
    private Map<String,Typeface> typefaces=new HashMap<>();
    private float density;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        density=getResources().getDisplayMetrics().density;
    }

    protected float scale(float v) {
        return density * v;
    }

    protected void addTypeface(String name) {
        Typeface typeface=Typeface.createFromAsset(getAssets(),name+".ttf");
        typefaces.put(name,typeface);
    }

    protected void setTypeface(TextView v, String typefaceName) {
        Typeface t=typefaces.get(typefaceName);
        if(t!=null) {
            v.setTypeface(t);
        }
    }

}
