package com.sergiescoruela.parties.ui;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.sergiescoruela.parties.R;

public class RecuperarFragment extends Fragment {

    private RecuperarViewModel mViewModel;
    private Button btnRecuperar;
    private EditText txtemail;
    private FirebaseAuth mAuth;

    public static RecuperarFragment newInstance() {
        return new RecuperarFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        mViewModel =
                ViewModelProviders.of(this).get(RecuperarViewModel.class);
        View root = inflater.inflate(R.layout.recuperar_fragment, container, false);




        btnRecuperar = root.findViewById(R.id.btnRecuperar);
        txtemail = root.findViewById(R.id.txtCorreoRecuperar);
        mAuth = FirebaseAuth.getInstance();

        btnRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                mAuth.sendPasswordResetEmail(txtemail.getText().toString());

            }
        });



        return root;



    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(RecuperarViewModel.class);
        // TODO: Use the ViewModel
    }

}
