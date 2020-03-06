package com.Milton.Consultorio.Activity;

import android.os.Bundle;
		import androidx.annotation.Nullable;
		import androidx.appcompat.app.AppCompatActivity;
		import androidx.viewpager.widget.ViewPager;

		import com.Milton.Consultorio.Adapters.PageAdapter;
		import com.Milton.Consultorio.R;
		import com.google.android.material.tabs.TabLayout;

public class PacientesActivity extends AppCompatActivity {
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pacientes);

		TabLayout tableLayout = (TabLayout) findViewById(R.id.Layout_Pacientes_TabSelector);
		tableLayout.addTab(tableLayout.newTab().setText("Info Gral"));
		tableLayout.addTab(tableLayout.newTab().setText("Info Factura"));
		tableLayout.addTab(tableLayout.newTab().setText("Club Salud"));
		tableLayout.addTab((tableLayout.newTab().setText("Banco")));
		tableLayout.addTab((tableLayout.newTab().setText("Eco Anatómico")));
		tableLayout.setTabGravity(TabLayout.GRAVITY_FILL);

		final ViewPager viewPager = (ViewPager) findViewById(R.id.Layout_Pacientes_TabContainer);
		PageAdapter pageAdapter = new PageAdapter(getSupportFragmentManager(),tableLayout.getTabCount());
		viewPager.setAdapter(pageAdapter);
		viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tableLayout));
		tableLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
			@Override
			public void onTabSelected(TabLayout.Tab tab) {
				viewPager.setCurrentItem(tab.getPosition());
			}

			@Override
			public void onTabUnselected(TabLayout.Tab tab) {

			}

			@Override
			public void onTabReselected(TabLayout.Tab tab) {

			}
		});


	}
}
