package de.biendara.soapbubbleburst;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import de.biendara.tools.SimpleAnimationListener;

public class MainActivity extends Activity implements View.OnClickListener {
    private ViewGroup container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        container=(ViewGroup) findViewById(R.id.container);
        showStartFragment();

    }

    private void showStartFragment() {
        container.removeAllViews();
        View start=getLayoutInflater().inflate(R.layout.start,null);

        start.findViewById(R.id.start).setOnClickListener(this);

        container.addView(start);
        Animation a= AnimationUtils.loadAnimation(this, R.anim.fade_in);
        start.startAnimation(a);

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
    }
}
