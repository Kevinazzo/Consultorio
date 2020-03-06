package com.Milton.Consultorio.Fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cursoradapter.widget.ResourceCursorAdapter;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.Milton.Consultorio.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Pacientes_Tab1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Pacientes_Tab1 extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_pacientes_tab1, container, false);

	}
}
