package br.darkwolf.truthorchallenge1.View;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;

import br.darkwolf.truthorchallenge1.Adapter.PlayerAdapter;
import br.darkwolf.truthorchallenge1.R;
import br.darkwolf.truthorchallenge1.Utils.ConstantUtils;

public class PlayersActivity extends AppCompatActivity {

    private String categoryParameter = "category";
    private String category;
    private ArrayList<String> playerList;

    private SharedPreferences prefs = null;

    private LinearLayout layoutExistPlayers;
    private LinearLayout layoutNoExistPlayers;
    private ListView playersListView;

    private PlayerAdapter playerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);

        bindViews();

        playersListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                openRemovePlayerDialog(position);
                return true;
            }
        });

        category = getIntent().getStringExtra(categoryParameter);

        prefs = getSharedPreferences(ConstantUtils.APPLICATION_ID, MODE_PRIVATE);

        getPlayersList();
        checkPlayersExistence();

        playerAdapter = new PlayerAdapter(getContext(), playerList);

        playersListView.setAdapter(playerAdapter);
    }

    private void checkPlayersExistence(){

        if(playerList.size() == 0){
            //Não há jogadores
            showNoExistPlayersLayout();
            notShowExistPlayersLayout();

        }
        else{
            //Há jogadores
            showExistPlayersLayout();
            notShowNoExistPlayersLayout();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.player_list_screen_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.bAddPlayer:

                openAddPlayerDialog();

                break;
            case R.id.bGameStart:

                break;
        }

        return true;
    }

    private void showExistPlayersLayout(){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1.0f);
        layoutExistPlayers.setLayoutParams(params);
    }

    private void notShowExistPlayersLayout(){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 0.0f);
        layoutExistPlayers.setLayoutParams(params);
    }

    private void showNoExistPlayersLayout(){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1.0f);
        layoutNoExistPlayers.setLayoutParams(params);
    }

    private void notShowNoExistPlayersLayout(){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 0.0f);
        layoutNoExistPlayers.setLayoutParams(params);
    }

    private void openRemovePlayerDialog(final int position){
        AlertDialog alerta = null;

        //Cria o gerador do AlertDialog
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.MyDialogTheme);
        //define o titulo
        builder.setTitle("Remoção");
        //define a mensagem
        builder.setMessage("Você realmente deseja remover esse jogador ?");
        //define um botão como positivo
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                //Toast.makeText(getContext(), "positivo=" + arg1, Toast.LENGTH_SHORT).show();

                playerList.remove(position);
                playerAdapter.notifyDataSetChanged();

                savePlayersList();

            }
        });
        //define um botão como negativo.
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                //Toast.makeText(getContext(), "negativo=" + arg1, Toast.LENGTH_SHORT).show();

            }
        });
        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
    }

    private void openAddPlayerDialog(){
        LayoutInflater li = LayoutInflater.from(getContext());
        View promptsView = li.inflate(R.layout.add_player_dialog, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext(), R.style.MyDialogTheme);

        alertDialogBuilder.setView(promptsView);

        final EditText playerNameEditText = promptsView.findViewById(R.id.playerNameEditText);

        // set dialog message
        alertDialogBuilder
                .setCancelable(true)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                playerList.add(playerNameEditText.getText().toString());

                                playerAdapter.notifyDataSetChanged();

                                savePlayersList();
                                checkPlayersExistence();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    private Context getContext(){
        return this;
    }

    private void bindViews(){
        layoutExistPlayers = findViewById(R.id.layoutExistPlayers);
        layoutNoExistPlayers = findViewById(R.id.layoutNoExistPlayers);
        playersListView = findViewById(R.id.playersListView);
    }

    private void getPlayersList(){
        Gson gson = new Gson();
        String json = prefs.getString("playersList", "");

        if(json == null || json.isEmpty()){
            playerList = new ArrayList<>();
        }
        else{
            playerList = gson.fromJson(json, ArrayList.class);
        }
    }

    private void savePlayersList(){
        SharedPreferences.Editor prefsEditor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(playerList);
        prefsEditor.putString("playersList", json);
        prefsEditor.commit();
    }
}
