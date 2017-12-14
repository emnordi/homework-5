package kth.se.androidrps;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/*
* Checks what button the user pressed and sends information to the server accordingly.
 */
public class GameOptions extends AsyncTask<SocketButton, String, Void> {

    private Activity ac;
    private BufferedReader fromServ;
    private String toUser = "";

    @Override
    protected Void doInBackground(SocketButton... sockets) {
        SocketButton sb = sockets[0];
        ac = sb.getActivity();

        try {
            PrintWriter toServ = new PrintWriter(MainActivity.socket.getOutputStream(), true);
            fromServ = new BufferedReader(new InputStreamReader(MainActivity.socket.getInputStream()));
            System.out.println(sb.getSelection());
            switch (sb.getSelection()) {
                case "start":
                    toServ.println("start");
                    break;

                case "quit":
                    toServ.println("quit");
                    break;

                case "rock":
                    toServ.println("rock");
                    break;

                case "paper":
                    toServ.println("paper");
                    break;

                case "scissors":
                    toServ.println("scissors");
                    break;

            }

            while ((toUser = fromServ.readLine()) != null) {
                publishProgress(toUser);
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(String... strings) {
        LinearLayout mylayout = this.ac.findViewById(R.id.main);
        TextView tv = new TextView(this.ac);
        String s = strings[0];
        tv.setText(s);
        mylayout.addView(tv);
    }

    @Override
    protected void onPostExecute(Void s) {
    }

}
