package br.darkwolf.truthorchallenge1.View;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Random;

import br.darkwolf.truthorchallenge1.R;
import br.darkwolf.truthorchallenge1.Utils.ConstantUtils;

public class GameActivity extends AppCompatActivity {

    private String categoryParameter = "category";
    private int category;
    private ArrayList<String> playerList;

    private SharedPreferences prefs = null;

    private String[] truthsList;
    private String[] challengesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        category = getIntent().getIntExtra(categoryParameter, -1);

        getTruthAndChallengeList();

        prefs = getSharedPreferences(ConstantUtils.APPLICATION_ID, MODE_PRIVATE);

        getPlayersList();

        choiceRandomPlayer();
    }

    private void getTruthAndChallengeList(){

        switch (category){

            case ConstantUtils.CATEGORY_INNOCENT:

                truthsList = getResources().getStringArray(R.array.truth_innocent);
                challengesList = getResources().getStringArray(R.array.challenge_innocent);

                break;
            case ConstantUtils.CATEGORY_HOT:

                truthsList = getResources().getStringArray(R.array.truth_hot);
                challengesList = getResources().getStringArray(R.array.challenge_hot);

                break;
        }
    }

    private void choiceRandomPlayer(){

        Log.i("TAG", "Choice Random Player");

        Random r = new Random();
        int playerIndex = r.nextInt(((playerList.size()-1) - 0) + 1) + 0;

        createAndShowGameDialog(playerIndex);

    }

    private void createAndShowGameDialog(int playerIndex){

        Log.i("TAG", "Create and Show Game Dialog");

        AlertDialog alerta = null;

        //Cria o gerador do AlertDialog
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.MyDialogTheme);
        //define o titulo
        builder.setTitle("Escolha");
        //define a mensagem
        builder.setMessage("Jogador "+playerList.get(playerIndex));
        //define um botão como positivo
        builder.setPositiveButton("Verdade", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Toast.makeText(GameActivity.this, "Clicou em verdade", Toast.LENGTH_SHORT).show();
                showTruthOrChallengeDialog(ConstantUtils.TYPE_TRUTH);
            }
        });
        //define um botão como negativo.
        builder.setNegativeButton("Desafio", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Toast.makeText(GameActivity.this, "Clicou em desafio", Toast.LENGTH_SHORT).show();
                showTruthOrChallengeDialog(ConstantUtils.TYPE_CHALLENGE);
            }
        });
        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
    }

    private void showTruthOrChallengeDialog(int type){

        String question;

        if(type == ConstantUtils.TYPE_TRUTH){
            //Truth

            Random r = new Random();
            int truthIndex = r.nextInt(((truthsList.length-1) - 0) + 1) + 0;

            question = truthsList[truthIndex];
        }
        else{
            //Challenge

            Random r = new Random();
            int challengeIndex = r.nextInt(((challengesList.length-1) - 0) + 1) + 0;

            question = challengesList[challengeIndex];
        }

        showQuestionDialog(question);
    }

    private void showQuestionDialog(String question){
        AlertDialog alerta = null;

        //Cria o gerador do AlertDialog
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.MyDialogTheme);
        //define o titulo
        builder.setTitle("Escolha");
        //define a mensagem
        builder.setMessage(question);
        //define um botão como positivo
        builder.setPositiveButton("Concluído", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                choiceRandomPlayer();
            }
        });
        //define um botão como negativo.
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                choiceRandomPlayer();
            }
        });
        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
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

    private Context getContext(){
        return this;
    }
}
