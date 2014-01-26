package com.simonedev.androtools;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ScreenRecorder extends Activity {
	
	/*
	 *  ==============================
	 *  KITKAT
	 *      SCREENRECORDER
	 *               OPEN SOURCE
	 *                       PROJECT
	 *  ===============================                    
	 */
	
	boolean bitRateBoolean = true, tempoBoolean = true, nomeFile = true;
	String testoBitrate, testoSecondi, dataAttuale;
	EditText editText, editText2, editText3;
	SimpleDateFormat simpleDateFormat;
	public static String testoFile;
	public static File cartelle;
	public static int secondi;
	DisplayMetrics display;
	Process su, check;
	boolean attendi;
	Button bottone;
	int larghezza;
	int altezza;
	int bitrate;	
	  
	public void registra() {
		new Thread(new Runnable() {
			public void run() {
				try {
					su = Runtime.getRuntime().exec("su");
					DataOutputStream outputStream = new DataOutputStream(su.getOutputStream());
					outputStream.writeBytes("screenrecord --size "+larghezza+"x"+altezza+" --bit-rate "+bitrate+"000000"+" --time-limit "+secondi+" /sdcard/AndroTools/Video/"+testoFile.trim()+".mp4"+"\n");
					outputStream.flush();
					outputStream.writeBytes("exit\n");
					outputStream.flush();
					su.waitFor();
		} catch(IOException e) {
			e.printStackTrace();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				}
				}).start();
		}
	
	
	protected void check() {
		
		if (android.os.Build.VERSION.SDK_INT < 19){
			//You can't use this function
		}
	}
	
	protected void onResume() {
		super.onResume();
		check();
	}
	
    @Override
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);				
        
        check();
		
        editText = (EditText)findViewById(R.id.editText1);
        editText2 = (EditText)findViewById(R.id.editText2);
        editText3 = (EditText)findViewById(R.id.editText3);
        bottone = (Button)findViewById(R.id.button1);
                
        display = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(display);
        
        larghezza = display.widthPixels;
        altezza = display.heightPixels;
        
        cartelle = new File(Environment.getExternalStorageDirectory().getPath().toString()+"/Recorder/VideoRec/");
        
        if(!cartelle.exists()) {
        	cartelle.mkdirs();
        }
                        
        bottone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	
                testoBitrate = editText.getText().toString();
                testoSecondi = editText2.getText().toString();
                testoFile = editText3.getText().toString().replaceAll("\\s","_");
                                
                if(testoBitrate.trim().length()>0) {
                bitrate = Integer.valueOf(testoBitrate);
                }
                
                else {
                	bitrate = 5;
                }
                
                if(testoSecondi.trim().length()>0) {
                    secondi = Integer.valueOf(testoSecondi);
                	}
                
                else {
                	secondi = 5;
                	}
                
            	if(bitrate>50) {
                	editText.setError(getString(R.string.invalidBitrate));
                	bitRateBoolean = false;

                }
                
                if(secondi>180) {
                	editText2.setError(getString(R.string.invalidSeconds));
                	tempoBoolean = false;

                }
                
                if(testoFile.trim().length()==0) {
                	simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy_HH:dd:ss");
                	dataAttuale = simpleDateFormat.format(new Date());
                	testoFile = "screen_recorder_"+dataAttuale;
                	
                }
                
                else {
                    testoFile = editText3.getText().toString().replaceAll("\\s","_");
                    testoFile =  Normalizer.normalize(testoFile, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");

                }
                if(bitRateBoolean && tempoBoolean && testoFile!=null) {  
        	    	 
                	new CountDownTimer(10000, 1000) {
              	    	 
                	     public void onTick(long secondi) {
                	     final Toast toast = Toast.makeText(getBaseContext(), getString(R.string.startTra)+" "+secondi/1000, Toast.LENGTH_SHORT);
                	     toast.show();
                	     attendi = true;
                	        Handler handler = new Handler();
                	            handler.postDelayed(new Runnable() {
                	               @Override
                	               public void run() {
                	                   toast.cancel(); 
                	               }
                	        }, 1000);
                	     }
                	     
                	     public void onFinish() {
                	    	 attendi = false;
                	         registra();
                	     }
                	  }.start();        	  
                	  
                }  
            	  if(attendi) {
            		  Toast.makeText(getApplicationContext(), getString(R.string.attendi), Toast.LENGTH_SHORT).show();
              	  }

            }
            
            
    });
}
}
