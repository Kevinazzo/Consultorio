package com.Milton.Consultorio.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.Milton.Consultorio.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Pacientes_Tab2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Pacientes_Tab2 extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_pacientes_tab2,container,false);
	}
}