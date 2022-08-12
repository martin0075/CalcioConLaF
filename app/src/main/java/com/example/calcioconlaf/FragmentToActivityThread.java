package com.example.calcioconlaf;

import android.content.Intent;
import android.util.Log;

public class FragmentToActivityThread extends Thread{
    String username;
    String indexLobby;
    HomeFragment homeFragment;
    public FragmentToActivityThread(String username, String indexLobby, HomeFragment homeFragment) {
        this.username=username;
        this.indexLobby=indexLobby;
        this.homeFragment=homeFragment;
    }

    @Override
    public void run() {
        super.run();
        sendIntent();
    }
    public void sendIntent(){
        homeFragment.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent= new Intent(homeFragment.getActivity(), LobbyActivity.class);
                Log.v("IndexLobbyProva", indexLobby);
                intent.putExtra("UsernameLobby", username);
                intent.putExtra("IndexLobby", indexLobby);
                homeFragment.getActivity().startActivity(intent);
            }
        });

    }
}
