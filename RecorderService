package com.simonedev.screenrecorder;

import java.io.IOException;
import java.io.OutputStream;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class RecorderService extends Service{

	String comando;
	Process su, registrazione;
	public static boolean isServiceEnabled = false;
	MainActivity screen;
	boolean isRooted;
	OutputStream stream;
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		isServiceEnabled = true;
		comando = MainActivity.interoComando.toString();
		screen = new MainActivity();
		isRooted = screen.haiRoot();
		
		new Thread(new Runnable(){
		    public void run() {
			    // TODO Auto-generated method stub
		    	try {
		    		if(isRooted  == false) {
		    			//You have root access
		    			registrazione = Runtime.getRuntime().exec("su");
		    			registrazione = Runtime.getRuntime().exec(comando);
		    			try {
							registrazione.waitFor();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		    		}	
		    		
		    		
		    		else {
		    			//You haven't root access
		    		}
		    		
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	
		    }
		}).start();
		
	     }
}
