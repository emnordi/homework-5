package kth.se.androidrps;


import android.app.Activity;

import java.net.Socket;

public class SocketButton {

    private Socket socket;
    private String selection;
    private Activity activity;

    public SocketButton(){
    }
    public SocketButton(Socket socket, String selection, Activity activity){
        this.socket = socket;
        this.selection = selection;
        this.activity = activity;
    }

    public String getSelection(){
        return selection;
    }
    public Socket getSocket(){
        return socket;
    }
    public Activity getActivity(){
        return activity;
    }


}
