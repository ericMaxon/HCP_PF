package com.example.pf_hpa4.NFC;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.pf_hpa4.NFC.util.NFCManager;
import com.example.pf_hpa4.NFC.util.WriteTagHelper;
import com.example.pf_hpa4.R;

import java.util.Base64;

public class NFC_Actitvity extends AppCompatActivity {

    NFCManager nfcManager;
    WriteTagHelper writeHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc_actitvity);



        nfcManager = new NFCManager(this);
        nfcManager.onActivityCreate();

        nfcManager.setOnTagReadListener(new NFCManager.TagReadListener() {
            @Override
            public void onTagRead(String[] tagRead) {
                Toast.makeText(NFC_Actitvity.this, tagRead[0] + " | " + tagRead[1], Toast.LENGTH_LONG).show();
            }
        });

        writeHelper= new WriteTagHelper(this, nfcManager);
        nfcManager.setOnTagWriteErrorListener(writeHelper);
        nfcManager.setOnTagWriteListener(writeHelper);

        Button writeButton = (Button) findViewById(R.id.write_button);
        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text = "Bryan Campos";
                String text2 = "8-957-1721";
                String text3 = "1";
                String encodedText = Base64.getEncoder().encodeToString(text.getBytes());
                String encodedText2 = Base64.getEncoder().encodeToString(text2.getBytes());
                String encodedText3 = Base64.getEncoder().encodeToString(text3.getBytes());

                writeHelper.writeText(encodedText, encodedText2, encodedText3);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        nfcManager.onActivityResume();
    }

    @Override
    protected void onPause() {
        nfcManager.onActivityPause();
        super.onPause();
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        nfcManager.onActivityNewIntent(intent);
    }
}