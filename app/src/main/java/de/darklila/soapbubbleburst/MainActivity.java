package de.darklila.soapbubbleburst;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import de.darklila.tools.BaseGameActivity;
import de.darklila.tools.SimpleAnimationListener;

public class MainActivity extends BaseGameActivity implements View.OnClickListener {
    private ViewGroup container;
    public static final String TYPEFACE_TITLE="FantasticFont";
    
    private  static final int BUBBLE_MAX=10;
    private static final float V_MAX=1f;
    private static final float SIZE_MAX = 128f;
    private Random rnd = new Random();
    private Drawable bubbleDrawable;
    private Set<Bubble> bubbles = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        container=(ViewGroup) findViewById(R.id.container);
        showStartFragment();
        addTypeface(TYPEFACE_TITLE);
        bubbleDrawable = getResources().getDrawable(R.drawable.soapbubble);
    }

    private void showStartFragment() {
        container.removeAllViews();
        View start=getLayoutInflater().inflate(R.layout.start,null);

        start.findViewById(R.id.start).setOnClickListener(this);

        container.addView(start);
        Animation a= AnimationUtils.loadAnimation(this, R.anim.fade_in);
        start.startAnimation(a);
        setTypeface((TextView) start.findViewById(R.id.title),TYPEFACE_TITLE);

    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.start){
            Animation a=AnimationUtils.loadAnimation(this,R.anim.pulse);
            a.setAnimationListener(new SimpleAnimationListener() {
                @Override
                public void onAnimationEnd(Animation animation) {
                    startGame();
                }
            });
            v.startAnimation(a);
        }
    }

    private void startGame() {
        
        //Start-Fragment + bubbles löschen
        container.removeAllViews();
        bubbles.clear();
        
        //Neue Bubbles erzeugen
        for (int i=0; i<BUBBLE_MAX;i++) {
            bubbles.add(new Bubble((FrameLayout) container, scale(V_MAX), scale(SIZE_MAX), rnd,bubbleDrawable));

        }
    }
}
