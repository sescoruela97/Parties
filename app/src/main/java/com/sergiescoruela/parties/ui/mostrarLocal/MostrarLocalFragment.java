package com.sergiescoruela.parties.ui.mostrarLocal;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sergiescoruela.parties.Adapter.AdapterPrecio;
import com.sergiescoruela.parties.Adapter.AdapterSlider;
import com.sergiescoruela.parties.R;
import com.sergiescoruela.parties.pojo.Local;
import com.sergiescoruela.parties.pojo.Precio;

import java.util.ArrayList;

public class MostrarLocalFragment extends Fragment {

    private MostrarLocalViewModel mViewModel;
    private ViewPager viewPager;
    private TextView lblNombreMostrar, lblDesMostrar;
    private Button btnMaps;
    private RecyclerView recyclerView;
    private Local local1;
    private AdapterSlider adapter;
    private AdapterPrecio adapterPrecio;
    private ArrayList<Precio> listaPrecio;
    private RecyclerView.LayoutManager layoutManager;
    private int elementoFila = R.layout.elemento_precio;
    private String direccion;




    public static MostrarLocalFragment newInstance() {
        return new MostrarLocalFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mViewModel =
                ViewModelProviders.of(this).get(MostrarLocalViewModel.class);
        View root = inflater.inflate(R.layout.mostrar_local_fragment, container, false);


        lblDesMostrar = root.findViewById(R.id.lblDesMostrarLocal);
        lblNombreMostrar = root.findViewById(R.id.lblnombreMostrarLocal);
        btnMaps = root.findViewById(R.id.btnMaps);
        recyclerView = root.findViewById(R.id.recyclerPrecio);



        btnMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=1600 "+direccion +", Valencia, Spain");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        viewPager = root.findViewById(R.id.slider);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MostrarLocalViewModel.class);
        // TODO: Use the ViewModel
    }




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        local1 = new Local();
        //lblNombreMostrar = view.findViewById(R.id.lblnombreMostrarLocal);

        if (getArguments() != null) {
            local1 = (Local) getArguments().getParcelable("localUnico");
            System.out.println("llega mostrar");



            System.out.println(local1.getNombre());
            lblNombreMostrar.setText(local1.getNombre());
            lblDesMostrar.setText(local1.getDescripcion());
            direccion=local1.getDireccion();

            adapter = new AdapterSlider(local1.getImagen(),getActivity());
            viewPager.setAdapter(adapter);

            viewPager.setCurrentItem(1, true);


            listaPrecio= new ArrayList<>();
            listaPrecio=local1.getListaPrecios();
            System.out.println(local1.getListaPrecios().get(0).getPrecio());
            adapterPrecio = new AdapterPrecio(getActivity(), elementoFila ,listaPrecio);
            layoutManager = new GridLayoutManager(getActivity(),1);



            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapterPrecio);

            adapterPrecio.notifyDataSetChanged();

        }


    }


}
