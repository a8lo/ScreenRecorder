package com.simonedev.screenrecorder;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
        
        String percorso, testoBitRate, testoSecondi;
        EditText editText, editText2, editText3;
        String testoFile;
        File cartelle;
        int bitrate;
        int secondi;
        DisplayMetrics display;
        Button bottone;        
        int contatore;
        int larghezza;
        int altezza;
        boolean bitRateBoolean, tempoBoolean, nomeFile;
        boolean isRooted = haiRoot();
        Process su;
        
		public void registra() {
		try {
			//Get root access
			su = Runtime.getRuntime().exec("su");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        DataOutputStream outputStream = new DataOutputStream(su.getOutputStream());
        try {
			outputStream.writeBytes("screenrecord --size "+larghezza+"x"+altezza+" --bit-rate "+bitrate+"000000"+" --time-limit "+secondi+" /sdcard/Folder/SubFolder/"+testoFile.trim()+".mp4"+"\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			outputStream.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        try {
			outputStream.writeBytes("exit\n");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        try {
			outputStream.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			su.waitFor();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
        public boolean haiRoot() {
                try {
                        //Check for root access
                        su = Runtime.getRuntime().exec("su");
                } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
                return false;
                
        }
        
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
                if (android.os.Build.VERSION.SDK_INT < 19){
                        //You can't use this function because you've an android version less than KitKat
                }

                if(haiRoot()) {
                        //You can't use this function becase you haven't root access
                }
                
                else {

                }

        editText = (EditText)findViewById(R.id.editText1);
        editText2 = (EditText)findViewById(R.id.editText2);
        editText3 = (EditText)findViewById(R.id.editText3);
        bottone = (Button)findViewById(R.id.button1);
        
        display = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(display);
        
	//I take the values ​​of height and width in pixels
        larghezza = display.widthPixels;
        altezza = display.heightPixels;
        
        cartelle = new File(Environment.getExternalStorageDirectory().getPath().toString()+File.separator+"MyFolder"+File.separator+"SubFolder");
        
        if(!cartelle.exists()) {
	    //Create the folders
            cartelle.mkdirs();    
        }
        
        else {
		//Nothing
        }
                        
        bottone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    
                testoBitRate = editText.getText().toString();
                testoSecondi = editText2.getText().toString();
                testoFile = editText3.getText().toString();
                                
                if(testoBitRate.trim().length()>0) {
                
                bitrate = Integer.valueOf(testoBitRate);
                
                }
                
                else {
                        editText.setError("This filed can't be empty");
                        
                }
                
                if(testoSecondi.trim().length()>0) {
                    secondi = Integer.valueOf(testoSecondi);

                }
                else {
                        
                        editText2.setError("This field can't be empty");
                        
                }
                bitRateBoolean = !TextUtils.isEmpty(editText.getText().toString());
                tempoBoolean = !TextUtils.isEmpty(editText2.getText().toString());
                nomeFile = !TextUtils.isEmpty(testoFile);
                
                    if(bitrate>30 || bitrate==0) {
                        editText.setError("Invalide BitRate");
                        bitRateBoolean = false;
                }
                
                if(secondi>180 || secondi == 0) {
                        editText2.setError("Invalide Seconds");
                        tempoBoolean = false;
                }
                
                
                if(testoFile.trim().length()==0) {
                        editText3.setError("This filed can't be empty");
                        nomeFile = false;
                }
                
                else {
                        editText3.setError(null);
                }
                
                if(bitRateBoolean && tempoBoolean && nomeFile) {
                	//Call the method
			registra();
                    
                }
                
            }
    });
}
}
