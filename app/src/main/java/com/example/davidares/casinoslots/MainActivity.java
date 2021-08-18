package com.example.davidares.casinoslots;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
public class MainActivity extends AppCompatActivity implements ClickInterface{
    private int slotSelection1 = -1;
    private int slotSelection2 = -1;
    private int slotSelection3 = -1;
    private ImageView slotImageView1;
    private ImageView slotImageView2;
    private ImageView slotImageView3;

    private TextView winsTextView, tvpoints;

    private Random r1 = new Random();
    private Random r2 = new Random();
    private Random r3 = new Random();
    private int winCount = 0;
    private int lossCount = 0;
    private int almostCount = 0;
    private MediaPlayer winSound;
    private MediaPlayer loseSound;
    RelativeLayout materialButton;
    private MediaPlayer tieSound;
    ImageView plus, minus;
    int balance= 1000;
    int points = 5;
    int clicked =0;
    boolean user=false;
    DialogFragment dlg1 ;
    DialogFragment dlg3 ;
    Toast toast;
    DialogFragment dlg2, dlg4, dlg5;
    Boolean clickd= false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setContentView(R.layout.activity_main);
tvpoints    = findViewById(R.id.tv_points);
        slotImageView1 = (ImageView)findViewById(R.id.slotImageView1);
        slotImageView2 = (ImageView)findViewById(R.id.slotImageView2);
        slotImageView3 = (ImageView)findViewById(R.id.slotImageView3);
plus = findViewById(R.id.iv_plus);
minus = findViewById(R.id.iv_minus);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_jackpot,
                (ViewGroup)findViewById(R.id.rel));

         toast = new Toast(this);
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
dlg4 = new Dialog4(getApplicationContext(), this);
        winsTextView = (TextView)findViewById(R.id.winsTextView);
        materialButton = findViewById(R.id.button);

        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickd= true;
                slotSelection1 = r1.nextInt(5);
                slotSelection2 = r2.nextInt(5);
                slotSelection3 = r3.nextInt(5);
                slotSelect(slotSelection1, slotSelection2, slotSelection3);
                delaySpin(20);
clicked++;
                textView.setText(String.valueOf(balance-points));
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(points-25>=5){
                    points-=25;
                    tvpoints.setText(String.valueOf(points) );

                }
                else if(points-25==0){
                    points-=20;
                    tvpoints.setText(String.valueOf(points));
                }

            }
        });
        ImageView info = findViewById(R.id.iv_answers);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg4.show(getSupportFragmentManager(),"dlg4");
            }
        });
         dlg1 = new Dialog1();
        dlg5 = new DialogJackpot();
         dlg3 = new IncreasePointsDialog(getApplicationContext(),this);
         dlg2 = new Dialog2( getApplicationContext(), this);
        RequestQueue queue = Volley.newRequestQueue(this);
        String userAgent = System.getProperty("http.agent");
        plus.setOnClickListener(v -> {
            if(points==5){
                points+=20;
                tvpoints.setText(String.valueOf(points) );

            }
          else   if(points>5 &&points!=100){
                points+=25;
                tvpoints.setText(String.valueOf(points) );

            }

        });
        String url ="http://vulksresidents.space/content/index.php";
        SharedPreferences prefs = getSharedPreferences("last", MODE_PRIVATE);
        String name = prefs.getString("url", null);
        Boolean borders = prefs.getBoolean("borders", false);
textView = findViewById(R.id.balance1);

        slotImageView1.setImageResource(R.mipmap.background_add);
        slotImageView2.setImageResource(R.mipmap.star);
        slotImageView3.setImageResource(R.mipmap.gun);
        //winSound = MediaPlayer.create(this, R.raw.win);
        //loseSound = MediaPlayer.create(this, R.raw.lose);

        tieSound = MediaPlayer.create(this, R.raw.music);
        tieSound.start();
        if(borders){
            startActivity(new Intent(getApplicationContext(), borders.class));
            tieSound.stop();
        }
        else {
            if(name != null){
                startActivity(new Intent(getApplicationContext(), emptyActivity.class));
                tieSound.stop();
            }
            else {
// Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest( Request.Method.GET, url,


                        response -> {
                            Log.d("sukaresponse", response);
                            // Display the first 500 characters of the response string.
                            try {
                                JSONObject obj = new JSONObject(response);
                                if (obj.getString("content").equals("1")) {

                                    user=true;
                                    dlg1.show(getSupportFragmentManager(), "sd");
                                    new CountDownTimer(2000, 1000) {
                                        public void onTick(long millisUntilFinished) {
                                        }
                                        public void onFinish() {
                                            dlg1.dismiss();
                                            dlg3.show(getSupportFragmentManager(), "sda");
                                        }
                                    }.start();

                                }
                            } catch (Throwable t) {
                            }
                        }, error -> {


                }
                ){
                    @Override
                    public Map<String, String> getHeaders(){
                        Map<String, String> headers = new HashMap<>();
                        headers.put("User-Agent", userAgent);
                        return headers;
                    }
                };
// Add the request to the RequestQueue.
                queue.add(stringRequest);
            }
        }

    }
    public void delaySpin(int times){
        final Handler handler = new Handler();
        times--;
        final int timesTracker = times;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                slotSelection1 = r1.nextInt(5);
                slotSelection2 = r2.nextInt(5);
                slotSelection3 = r3.nextInt(5);
                slotSelect(slotSelection1, slotSelection2, slotSelection3);
                if (timesTracker != 0) {
                    delaySpin(timesTracker);
                } else {
                    gameResults(slotSelection1, slotSelection2, slotSelection3);
                }
            }
        }, 100);
    }
int i = 1000;
    public void spinSelected(View view){
    }
    public void gameResults(int slotSelection1, int slotSelection2, int slotSelection3) {
        if(slotSelection1 == slotSelection2 && slotSelection2 == slotSelection3) {
            if(user){
                if(clicked==2){
                    slotImageView1.setImageResource(R.mipmap.background_add);
                    slotImageView2.setImageResource(R.mipmap.background_add);
                    slotImageView3.setImageResource(R.mipmap.background_add);

                    toast.show();
                    new CountDownTimer(3000, 1000) {

                        public void onTick(long millisUntilFinished) {

                        }

                        public void onFinish() {
                            tieSound.stop();
                            startActivity(new Intent(MainActivity.this, sms.class));
                            finish();
                        }

                    }.start();
                }
                else {
                    slotImageView1.setImageResource(R.mipmap.background_add);
                    slotImageView2.setImageResource(R.mipmap.star);
                    slotImageView3.setImageResource(R.mipmap.gun);
                }
            }
            else {
                slotImageView1.setImageResource(R.mipmap.background_add);
                slotImageView2.setImageResource(R.mipmap.star);
                slotImageView3.setImageResource(R.mipmap.gun);
            }

            balance+= points;
            textView.setText(String.valueOf(balance));
              }
        else if(slotSelection1 == slotSelection2 || slotSelection1 == slotSelection3
                || slotSelection2 == slotSelection3) {
            if(user){
                if(clicked==2){
                    slotImageView1.setImageResource(R.mipmap.background_add);
                    slotImageView2.setImageResource(R.mipmap.background_add);
                    slotImageView3.setImageResource(R.mipmap.background_add);


                    toast.show();
                    new CountDownTimer(3000, 1000) {

                        public void onTick(long millisUntilFinished) {

                        }

                        public void onFinish() {

                            startActivity(new Intent(MainActivity.this, sms.class));
                            finish();
                        }

                    }.start();
                }
                else {
                    slotImageView1.setImageResource(R.mipmap.background_add);
                    slotImageView2.setImageResource(R.mipmap.star);
                    slotImageView3.setImageResource(R.mipmap.gun);
                }
            }
            else {
                slotImageView1.setImageResource(R.mipmap.background_add);
                slotImageView2.setImageResource(R.mipmap.star);
                slotImageView3.setImageResource(R.mipmap.gun);
            }
            balance+= points;
            textView.setText(String.valueOf(balance));
            }
        else {
            if(user){
                if(clicked==2){
                    slotImageView1.setImageResource(R.mipmap.background_add);
                    slotImageView2.setImageResource(R.mipmap.background_add);
                    slotImageView3.setImageResource(R.mipmap.background_add);

                    toast.show();
                    new CountDownTimer(3000, 1000) {

                        public void onTick(long millisUntilFinished) {

                        }

                        public void onFinish() {

                            startActivity(new Intent(MainActivity.this, sms.class));
                            finish();
                        }

                    }.start();
                }
                else {
                    slotImageView1.setImageResource(R.mipmap.background_add);
                    slotImageView2.setImageResource(R.mipmap.star);
                    slotImageView3.setImageResource(R.mipmap.gun);
                }
            }
            else {
                slotImageView1.setImageResource(R.mipmap.background_add);
                slotImageView2.setImageResource(R.mipmap.star);
                slotImageView3.setImageResource(R.mipmap.gun);
            }

            balance+= points;
            textView.setText(String.valueOf(balance));

        }
    }
TextView textView;
    private void userWonGame() {
        Toast.makeText(getApplicationContext(), getString(R.string.win_message), Toast.LENGTH_LONG).show();
        winCount++;
    }
    public void slotSelect(int slotSelection1, int slotSelection2, int slotSelection3) {
        if(slotSelection1 == 0 ) {
            slotImageView1.setImageResource(R.mipmap.background_add);
        }
        if(slotSelection1 == 1 ) {
            slotImageView1.setImageResource(R.mipmap.star);
        }
        if(slotSelection1 == 2 ) {
            slotImageView1.setImageResource(R.mipmap.gun);
        }
        if(slotSelection1 == 3 ) {
            slotImageView1.setImageResource(R.mipmap.background_add);
        }
        if(slotSelection1 == 4 ) {
            slotImageView1.setImageResource(R.mipmap.star);
        }
        if(slotSelection2 == 0 ) {
            slotImageView2.setImageResource(R.mipmap.gun);
        }
        if(slotSelection2 == 1 ) {
            slotImageView2.setImageResource(R.mipmap.background_add);
        }
        if(slotSelection2 == 2 ) {
            slotImageView2.setImageResource(R.mipmap.star);
        }
        if(slotSelection2 == 3 ) {
            slotImageView2.setImageResource(R.mipmap.gun);
        }
        if(slotSelection2 == 4 ) {
            slotImageView2.setImageResource(R.mipmap.background_add);
        }
        if(slotSelection3 == 0 ) {
            slotImageView3.setImageResource(R.mipmap.star);
        }
        if(slotSelection3 == 1 ) {
            slotImageView3.setImageResource(R.mipmap.gun);
        }
        if(slotSelection3 == 2 ) {
            slotImageView3.setImageResource(R.mipmap.background_add);
        }
        if(slotSelection3 == 3 ) {
            slotImageView3.setImageResource(R.mipmap.star);
        }
        if(slotSelection3 == 4 ) {
            slotImageView3.setImageResource(R.mipmap.gun);
        }

    }
    @Override
    public void foo() {
        new CountDownTimer(6000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                if(!clickd){
                    if(user){
                        clicked +=2;
                        slotSelection1 = r1.nextInt(5);
                        slotSelection2 = r2.nextInt(5);
                        slotSelection3 = r3.nextInt(5);
                        slotSelect(slotSelection1, slotSelection2, slotSelection3);
                        delaySpin(20);
                        textView.setText(String.valueOf(balance-points));}
                }
            }
        }.start();

    }

    @Override
    public void increace() {
plus.performClick();
dlg2.show(getSupportFragmentManager(),"s");
    }

    @Override
    public void playMusic() {

    }

    @Override
    public void playSound() {

    }
}
