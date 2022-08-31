package com.example.calcioconlaf.GameTransfer;

import android.content.Intent;
import android.util.Log;
import com.example.calcioconlaf.HomeFragment;

public class FragmentToActivityThreadTransfer extends Thread{
    String usernameTransfer;
    String indexLobbyTransfer;
    HomeFragment homeFragment;
    public FragmentToActivityThreadTransfer(String username, String indexLobby, HomeFragment homeFragment) {
        this.usernameTransfer=username;
        this.indexLobbyTransfer=indexLobby;
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
                Intent intent= new Intent(homeFragment.getActivity(), LobbyActivityTransfer.class);
                Log.v("IndexLobbyProva", indexLobbyTransfer);
                intent.putExtra("UsernameLobby", usernameTransfer);
                intent.putExtra("IndexLobby", indexLobbyTransfer);
                homeFragment.getActivity().startActivity(intent);
            }
        });

    }
}
