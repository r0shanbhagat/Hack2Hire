package com.example.myapplication.activities.useractivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

import com.example.myapplication.R;
import com.example.myapplication.base.BaseActivity;

import java.util.Objects;

public class SecondFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.navigation_second_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BaseActivity.getBaseActivity().setTitle("HELLO");
        Toast.makeText(getActivity().getApplicationContext(), "Bundle args " + getArguments().getBoolean("test_boolean"), Toast.LENGTH_SHORT).show();
     //  /Toast.makeText(getActivity().getApplicationContext(), "Bundle args " + FirstFragmentAfromBundle(getArguments()).getTestString(), Toast.LENGTH_SHORT).show();

        Button button = view.findViewById(R.id.button_frag2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((UserActivity) Objects.requireNonNull(getActivity())).navController.navigateUp();
              //  final NavController navController = Navigation.findNavController(getActivity(), R.id.my_nav_host_fragment);
               // navController.navigateUp();


            }
        });
    }



}
