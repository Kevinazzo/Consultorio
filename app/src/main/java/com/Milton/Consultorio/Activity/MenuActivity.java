package com.Milton.Consultorio.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.Milton.Consultorio.R;

public class MenuActivity extends AppCompatActivity {

	CardView cardPacientes;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);

		cardPacientes=(CardView)findViewById(R.id.CardPacientes);

		cardPacientes.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//                Intent intent = new Intent(MenuActivity.this, TabsPacientesActivity.class);
				Intent intent = new Intent(MenuActivity.this, PacientesActivity.class);
				MenuActivity.this.startActivity(intent);
			}
		});
	}
}
