package com.Milton.Consultorio.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.Milton.Consultorio.Fragment.Pacientes_Tab1;
import com.Milton.Consultorio.Fragment.Pacientes_Tab2;
import com.Milton.Consultorio.Fragment.Pacientes_Tab3;
import com.Milton.Consultorio.Fragment.Pacientes_Tab4;
import com.Milton.Consultorio.Fragment.Pacientes_Tab5;

public class PageAdapter extends FragmentStatePagerAdapter {

	int tabIndex;

	public PageAdapter(@NonNull FragmentManager fm, int _tabindex) {
		super(fm);
		this.tabIndex = _tabindex;
	}

	@NonNull
	@Override
	public Fragment getItem(int index) {
		switch (index){
			case 0:
				return new Pacientes_Tab1();
			case 1:
				return new Pacientes_Tab2();
			case 2:
				return new Pacientes_Tab3();
			case 3:
				return new Pacientes_Tab4();
			case 4:
				return new Pacientes_Tab5();
			default:
				return null;
		}
	}

	@Override
	public int getCount() {
		return tabIndex;
	}
}
