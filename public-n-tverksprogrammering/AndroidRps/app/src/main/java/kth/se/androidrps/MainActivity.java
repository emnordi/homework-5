package kth.se.androidrps;

import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private PrintWriter toServ;
    public static Socket socket;
    BufferedReader fromServ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button playBtn = (Button) findViewById(R.id.playBtn);
        Button quitBtn = (Button) findViewById(R.id.quitBtn);
        Button rockBtn = (Button) findViewById(R.id.rockBtn);
        Button paperBtn = (Button) findViewById(R.id.paperBtn);
        Button scissorsBtn = (Button) findViewById(R.id.scissorsBtn);
        new Connectivity().execute();

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.playBtn:
                SocketButton sb = new SocketButton(socket, "start", this);
                GameOptions g = new GameOptions();
                g.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, sb);
                break;
            case R.id.quitBtn:
                SocketButton sb1 = new SocketButton(socket, "quit", this);
                GameOptions g1 = new GameOptions();
                g1.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, sb1);
                printToUser("Quitting... Goodbye!");
                this.finishAffinity();
                break;

            case R.id.rockBtn:
                SocketButton sb2 = new SocketButton(socket, "rock", this);
                GameOptions g2 = new GameOptions();
                g2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, sb2);
                break;
            case R.id.paperBtn:
                SocketButton sb3 = new SocketButton(socket, "paper", this);
                GameOptions g3 = new GameOptions();
                g3.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, sb3);
                break;
            case R.id.scissorsBtn:
                SocketButton sb4 = new SocketButton(socket, "scissors", this);
                GameOptions g4 = new GameOptions();
                g4.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, sb4);
                break;
            default:
                break;
        }


    }

    public void printToUser(String msg){
        LinearLayout mylayout = findViewById(R.id.main);
        TextView tv = new TextView(this);
        tv.setText(msg);
        mylayout.addView(tv);
    }
    /*
        * A connection is started on (localhost) on port 8000, and with a lingertime of 30000ms
        * toServ PrintWriter is created to forward inputs to the server.
         */
    public class Connectivity extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                socket = new Socket();
                //Socket.connect{ip/port/setsolinger}
                socket.connect(new InetSocketAddress("10.0.2.2", 8000), 30000);
                socket.setSoTimeout(1800000);
                //What is in toServ is sent to server
                toServ = new PrintWriter(socket.getOutputStream(), true);
                fromServ = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
                System.err.println("no connect");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void v){
            LinearLayout mylayout = findViewById(R.id.main);
            TextView tv = new TextView(MainActivity.this);
            String s = "connected";
            tv.setText(s);
            mylayout.addView(tv);
        }
    }



}

