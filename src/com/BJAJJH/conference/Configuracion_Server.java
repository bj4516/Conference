package com.BJAJJH.conference;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ShowToast")
public class Configuracion_Server extends Activity {

	public String NOMBRE;
	public int CODEC;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_configuracion__server);
		final TextView texto = (TextView) findViewById(R.id.txt_Nombre_servidor);
		final RadioButton Rbtn1 = (RadioButton) findViewById(R.id.rbt_PCMA);
		final RadioButton Rbtn2 = (RadioButton) findViewById(R.id.rbt_PCMU);
		Rbtn1.setChecked(true);
		CODEC = 1;
		Rbtn1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Rbtn1.setChecked(true);
				Rbtn2.setChecked(false);
				CODEC = 1;
			}
		});
		
		Rbtn2.setOnClickListener(new OnClickListener() {
					
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Rbtn1.setChecked(false);
				Rbtn2.setChecked(true);
				CODEC = 2;
			}
		});
		
		Button btn_enviar = (Button) findViewById(R.id.btn_enviar_server_configuracion);
		btn_enviar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (texto.length() == 0){
					Toast.makeText(getApplicationContext(), R.string.error_vacio_configurar_server, Toast.LENGTH_SHORT).show();
					//hasta el momento no se ha revisado si ya otro tiene el mismo nombre en la red o los caracteres validos
				} else {
					Bundle parametros = new Bundle();
			        parametros.putString("nombre", texto.getText().toString());
			        parametros.putInt("codec", CODEC);
					Intent iniciar = new Intent(getApplication(),Servidor.class);
					iniciar.putExtras(parametros);
					startActivity(iniciar);
				}
			}
		});
		
	}
	
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.configuracion__server, menu);
		return true;
	}
	
	

}
