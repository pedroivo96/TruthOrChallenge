package br.darkwolf.truthorchallenge1.View;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.darkwolf.truthorchallenge1.R;

public class MainActivity extends AppCompatActivity {

    private final int TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent start = new Intent(getContext(), MenuActivity.class);
                startActivity(start);
                finish();
            }
        }, TIME_OUT);

    }

    private Context getContext(){
        return this;
    }
}
