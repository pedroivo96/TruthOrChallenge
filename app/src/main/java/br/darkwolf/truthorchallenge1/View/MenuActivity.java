package br.darkwolf.truthorchallenge1.View;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import br.darkwolf.truthorchallenge1.R;
import br.darkwolf.truthorchallenge1.Utils.ConstantUtils;

public class MenuActivity extends AppCompatActivity {

    private Button buttonInocentMode;
    private Button buttonReflexiveMode;
    private Button buttonHotMode;

    private String categoryParameter = "category";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        bindViews();

        buttonInocentMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), PlayersActivity.class);
                intent.putExtra(categoryParameter, ConstantUtils.CATEGORY_INNOCENT);
                startActivity(intent);
            }
        });

        buttonReflexiveMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), PlayersActivity.class);
                intent.putExtra(categoryParameter, ConstantUtils.CATEGORY_REFLEXIVE);
                startActivity(intent);
            }
        });

        buttonHotMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), PlayersActivity.class);
                intent.putExtra(categoryParameter, ConstantUtils.CATEGORY_HOT);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_screen_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.bAbout:

                Toast.makeText(getContext(), "Desenvolvido por DarkWolfP para fins de lazer e contra o tédio", Toast.LENGTH_SHORT).show();
                break;
        }

        return true;
    }

    private Context getContext(){
        return this;
    }

    private void bindViews(){
        buttonInocentMode = findViewById(R.id.buttonInocentMode);
        buttonReflexiveMode = findViewById(R.id.buttonReflexiveMode);
        buttonHotMode = findViewById(R.id.buttonHotMode);
    }
}
