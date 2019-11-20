package br.darkwolf.truthorchallenge1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import br.darkwolf.truthorchallenge1.R;

public class PlayerAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> playerList;

    public PlayerAdapter(Context context, ArrayList<String> playerList){
        this.context = context;
        this.playerList = playerList;
    }

    @Override
    public int getCount() {
        return playerList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view1 = inflater.inflate(R.layout.player_item_listview, viewGroup, false);

        String playerName = playerList.get(i);

        TextView playerNameTextView = view1.findViewById(R.id.playerNameTextView);
        playerNameTextView.setText(playerName);

        return view1;
    }
}
