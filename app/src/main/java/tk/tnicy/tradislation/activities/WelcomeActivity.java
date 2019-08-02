package tk.tnicy.tradislation.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.EditText;
import tk.tnicy.tradislation.R;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends Activity {
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        //getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        setContentView(R.layout.activity_welcome);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
    }


    void init(){
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
                startActivity(intent);
                WelcomeActivity.this.finish();
            }
        };
        timer.schedule(timerTask, 1000);
    }
    }
        /*editText = findViewById(R.id.search_text);
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b){
                    Intent intent=new Intent(WelcomeActivity.this,MainActivity.class);
                    ActivityOptionsCompat options = ActivityOptionsCompat
                            .makeSceneTransitionAnimation(WelcomeActivity.this,
                                    editText,"EditText");
                    startActivity(intent,options.toBundle());
                    finish();
                    /*Timer timer=new Timer();
                    TimerTask task = new TimerTask() {
                        @Override
                        public void run() {
                            finish();
                        }
                    };
                    timer.schedule(task,0,5000);

                }else{

                }

            }
        });
    }*/

