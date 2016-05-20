package com.example.admin.cruseders_2;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.view.Menu;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    //ListView list;
    //ArrayList<String> messageList;
    //ArrayAdapter< String> adapter;
    TextView d1bin,d2bin;
    String string,stringDustbin,stringDustbinStatus;
    ImageButton d1,d2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        d1bin = (TextView)findViewById(R.id.d1Status);
        d2bin = (TextView)findViewById(R.id.d2Status);

        d1 = (ImageButton)findViewById(R.id.d1Button);
        d2 = (ImageButton)findViewById(R.id.d2Button);

        //list = (ListView) findViewById(R.id.listView1);

       // messageList  = new ArrayList<String>();
        //messageList.add("check");
        //adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, messageList);
        //list.setAdapter(adapter);

        IntentFilter filter = new IntentFilter(SMS_RECEIVED);
        registerReceiver(receiver_SMS, filter);
    }


    BroadcastReceiver receiver_SMS = new BroadcastReceiver()
    {
        @TargetApi(Build.VERSION_CODES.M)
        public void onReceive(Context context, Intent intent)
        {
            if (intent.getAction().equals(SMS_RECEIVED))
            {
                Bundle bundle = intent.getExtras();
                if (bundle != null)
                {
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    SmsMessage[] messages = new SmsMessage[pdus.length];
                    String format = bundle.getString("format");
                    for (int i = 0; i < pdus.length; i++)
                        messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i],format);

                    for (SmsMessage message : messages) {
                        Toast.makeText(MainActivity.this,message.getDisplayMessageBody(), Toast.LENGTH_LONG).show();
                        //receivedMessage(message.getDisplayOriginatingAddress());
                        //textView.setText(message.getDisplayMessageBody());
                        string = message.getDisplayMessageBody();
                        if (string.contains("D1") ) {
                            stringDustbin = "D1";
                        }
                        if (string.contains("D2") ) {
                            stringDustbin = "D2";
                        }
                        if (string.contains("ON") ) {
                            stringDustbinStatus = "ON";
                        }
                        if(string.contains("OFF") ) {
                            stringDustbinStatus = "OFF";
                        }
                        //textView.setText(stringDustbin + "\t\t\t\t\t" + stringDustbinStatus);
                        changeState(stringDustbin,stringDustbinStatus);
                    }
                }
            }
        }
    };
    private void changeState(String stringDustbin, String stringDustbinStatus ){
        if( stringDustbinStatus.equals("OFF") && stringDustbin.equals("D1")){
           d1.setImageResource(R.drawable.redmark);
            Toast.makeText(getBaseContext(),"Dust-bin 1 is Full Now  !!", Toast.LENGTH_SHORT).show();
            d1bin.setText("OFF");
        }
        else if(stringDustbinStatus.equals("ON")  && stringDustbin.equals("D1")){
            d1.setImageResource(R.drawable.greenmark);
            Toast.makeText(getBaseContext(),"Dust-bin 1 is Empty Now !!", Toast.LENGTH_SHORT).show();
            d1bin.setText("ON");
        }
        else if( stringDustbinStatus.equals("OFF") && stringDustbin.equals("D2")){
            d2.setImageResource(R.drawable.redmark);
            Toast.makeText(getBaseContext(),"Dust-bin 2 is Full Now !!", Toast.LENGTH_SHORT).show();
            d2bin.setText("OFF");
        }
        else if( stringDustbinStatus.equals("ON") && stringDustbin.equals("D2")){
            d2.setImageResource(R.drawable.greenmark);
            Toast.makeText(getBaseContext(),"Dust-bin 2 is Empty Now !!", Toast.LENGTH_SHORT).show();
            d2bin.setText("ON");
        }

    }

    /*
    private void receivedMessage(String message)
    {
        messageList.add(message);
        adapter.notifyDataSetChanged();
    }
    */

}
