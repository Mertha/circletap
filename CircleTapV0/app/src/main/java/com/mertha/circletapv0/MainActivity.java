package com.mertha.circletapv0;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.github.nkzawa.emitter.Emitter;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import static android.graphics.Color.valueOf;

public class MainActivity extends AppCompatActivity {

    private EditText mInputMessageView;
    private TextView mUserName;
    private TextView mMessage;
    private Button sendButton;
    private Button blueBtn;
    private Button redBtn;
    private Button grnBtn;
    public int myColor;
    private TextView colorTest;

    private Socket mSocket;
    {
        try {
            //mSocket = IO.socket("http://chat.socket.io");
            mSocket = IO.socket("http://192.168.68.70:3000");
        } catch (URISyntaxException e) {}
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendButton = findViewById(R.id.buttonSend);

        blueBtn = findViewById(R.id.blueBtn);
        redBtn = findViewById(R.id.redBtn);
        grnBtn = findViewById(R.id.grnBtn);

        colorTest = findViewById(R.id.colorTest);

        mInputMessageView = findViewById(R.id.inputText);
        mMessage = findViewById(R.id.message);

        mSocket.on("new message", onNewMessage);
        mSocket.connect();

        blueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myColor = Color.BLUE;
                colorTest.setBackgroundColor(myColor);
                Log.i("BTN", "color: " + myColor);
            }
        });

        redBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myColor = Color.RED;
                colorTest.setBackgroundColor(myColor);
                Log.i("BTN", "color: " + myColor);
            }
        });

        grnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myColor = Color.GREEN;
                colorTest.setBackgroundColor(myColor);
                Log.i("BTN", "color: " + myColor);
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptSend();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mSocket.disconnect();
        mSocket.off("new message", onNewMessage);
    }

    private void attemptSend() {
        String message = mInputMessageView.getText().toString().trim();
        if (TextUtils.isEmpty(message)) {
            return;
        }

        mInputMessageView.setText("");
        mSocket.emit("new message", message);
    }

   /* private void addMessage(String username, String message){
        mUserName.setText(username);
        mMessage.setText(message);
    }*/

    private void addMessage(String message){
        mMessage.setText(message);
    }

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
           runOnUiThread(new Runnable() {
                @Override
                public void run() {
                   // JSONObject data = (JSONObject) args[0];
                    String message = args[0].toString();
                    //String message;
                    /*try {
                        username = data.getString("username");
                        message = data.getString("message");
                    } catch (JSONException e) {
                        return;
                    }*/

                    // add the message to view
                   // addMessage(username, message);
                    addMessage(message);
                }
            });
        }
    };
}
