package de.darklila.soapbubbleburst;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import de.darklila.tools.BaseGameActivity;
import de.darklila.tools.SimpleAnimationListener;

public class MainActivity extends BaseGameActivity implements View.OnClickListener,Bubble.OnBurstListener {
    private ViewGroup container;
    public static final String TYPEFACE_TITLE="FantasticFont";

    private  static final int BUBBLE_MAX=10;
    private static final float V_MAX=1f;
    private static final float SIZE_MAX = 128f;
    private Random rnd = new Random();
    private Drawable bubbleDrawable;
    private Set<Bubble> bubbles = new HashSet<>();

    private ScheduledExecutorService executor;

    private Runnable moveRunnable = new Runnable() {
        @Override
        public void run() {
            for(final Bubble b:bubbles) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        b.move();
                    }
                });
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        container=(ViewGroup) findViewById(R.id.container);
        addTypeface(TYPEFACE_TITLE);
        bubbleDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.soapbubble, null);
    }

    @Override
    protected void onPause() {
        //Executor wird ausgeschaltet und alle Bubbles gelöscht, um Speicher freizugaben
        super.onPause();
        executor.shutdown();
        bubbles.clear();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showStartFragment();
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

        //Service erzeugen, der die Bubbles bewegt.
        // Ruft 20x in der Sekunde moveRunnable auf.
        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(moveRunnable, 0, 50, TimeUnit.MILLISECONDS);

        //Start-Fragment + bubbles löschen
        container.removeAllViews();
        bubbles.clear();

        //Neue Bubbles erzeugen
        for (int i=0; i<BUBBLE_MAX;i++) {
            bubbles.add(new Bubble((FrameLayout) container, scale(V_MAX), scale(SIZE_MAX),
                    rnd,bubbleDrawable,this));

        }
    }


    @Override
    public void onBurst(Bubble b) {
        bubbles.remove(b);
        bubbles.add(new Bubble((FrameLayout) container, scale(V_MAX), scale(SIZE_MAX),
                rnd,bubbleDrawable,this));

    }
}
