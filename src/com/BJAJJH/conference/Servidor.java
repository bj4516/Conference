package com.BJAJJH.conference;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import android.content.Context;
import android.media.AudioManager;
import android.net.rtp.AudioCodec;
import android.net.rtp.AudioGroup;
import android.net.rtp.AudioStream;
import android.net.rtp.RtpStream;
import android.os.StrictMode;
import android.util.Log;


public class Servidor extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_servidor);
		Bundle parametros = this.getIntent().getExtras(); //Definimos el contenedor de parametros
        String nombre_SERVIDOR = parametros.getString("nombre");
		int CODEC = parametros.getInt("codec");
		//ya tengo las variables, lleno la informacion de la sesion
		TextView txt_NOMBRE = (TextView) findViewById(R.id.txt_nombre_server);
		TextView txt_CODEC = (TextView) findViewById(R.id.txt_nombre_codec);
		TextView txt_DIRE = (TextView) findViewById(R.id.txt_direccion_IP);
		txt_NOMBRE.setText(getString(R.string.Nombre_servidor) + nombre_SERVIDOR);		
		String codec_nombre;
		if (CODEC == 1) {
			codec_nombre = getString(R.string.codec_1);
		} else {
			codec_nombre = getString(R.string.codec_2);
		}
		txt_CODEC.setText(getString(R.string.Nombre_codec) + codec_nombre);
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		  StrictMode.setThreadPolicy(policy);
		  
		  //incia el servidor
		  try {   
		      AudioManager audio =  (AudioManager) getSystemService(Context.AUDIO_SERVICE); 
		      audio.setMode(AudioManager.MODE_IN_COMMUNICATION);
		      AudioGroup audioGroup = new AudioGroup();
		      audioGroup.setMode(AudioGroup.MODE_NORMAL);      
		      byte[] ip = getLocalIPAddress ();
		      txt_DIRE.setText(getString(R.string.direccion_IP) + new String(ip));
		      AudioStream audioStream = new AudioStream(InetAddress.getByAddress(ip));
		      switch(CODEC){
		      case 1:
		    	  audioStream.setCodec(AudioCodec.GSM);
		    	  break;
		      case 2:
		    	  audioStream.setCodec(AudioCodec.PCMU);
		      }
		    	  
		      
		      audioStream.setMode(RtpStream.MODE_NORMAL);
		                           //set receiver(vlc player) machine ip address(please update with your machine ip)
		      audioStream.associate(InetAddress.getByAddress(new byte[] {(byte)192, (byte)168, (byte)2, (byte)2 }), 22222);
		      audioStream.join(audioGroup);
		     
		   
		  } catch (Exception e) {
		   Log.e("----------------------", e.toString());
		   e.printStackTrace();
		  }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.servidor, menu);
		return true;
	}
	
	
	
	//obtner la direccion IP actual del dispositivo
	
	public static byte[] getLocalIPAddress () {
	    byte ip[]=null;
	       try {
	           for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
	               NetworkInterface intf = en.nextElement();
	               for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
	                   InetAddress inetAddress = enumIpAddr.nextElement();
	                   if (!inetAddress.isLoopbackAddress()) {
	                    ip= inetAddress.getAddress();
	                   }
	               }
	           }
	       } catch (SocketException ex) {
	           Log.i("SocketException ", ex.toString());
	       }
	       return ip;
	       
	 }
	

}
