package com.example.afinochka.weatherapp.Animation;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.afinochka.weatherapp.Activities.MainActivity;
import com.example.afinochka.weatherapp.R;

public class GoAnimation {

    public static void usedAnimation(final Context context, Button button, final MainActivity activity) {
        Animation anim = AnimationUtils.loadAnimation(context, R.anim.alpha);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Toast toast = Toast.makeText(context, "Updating...", Toast.LENGTH_SHORT);
                toast.show();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                activity.wasUpdate = true;
                activity.checkContentView();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        button.startAnimation(anim);
    }


}
