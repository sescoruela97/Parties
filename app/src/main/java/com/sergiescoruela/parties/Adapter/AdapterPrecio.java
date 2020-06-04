package com.sergiescoruela.parties.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.sergiescoruela.parties.R;
import com.sergiescoruela.parties.pojo.Precio;

import java.util.ArrayList;

public class AdapterPrecio extends RecyclerView.Adapter<AdapterPrecio.ElementoPrecio>{


    private Context context;
    private int resource;
    private ArrayList<Precio> listaPrecios;



    public AdapterPrecio(Context context, int resource, ArrayList<Precio> listaPrecios) {
        this.context = context;
        this.resource = resource;
        this.listaPrecios = listaPrecios;
    }

    /**
     * Intancia cuando sea necesario una nueva card que es (ElemntoRegalo)
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public AdapterPrecio.ElementoPrecio onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View card = LayoutInflater.from(context).inflate(resource, null);
        AdapterPrecio.ElementoPrecio elementoPrecio = new AdapterPrecio.ElementoPrecio(card);
        return elementoPrecio;
    }

    /**
     * Rellenar con datos el Objeto generado
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull AdapterPrecio.ElementoPrecio holder, int position) {

        final Precio precio= listaPrecios.get(position);


        holder.txtNombre.setText(precio.getTipoEntrada());
        holder.txtDescripcion.setText(precio.getDescripcionEntrada());
        holder.txtPrecio.setText(precio.getPrecio());
        holder.btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putParcelable("precioUnico", precio);
                Navigation.findNavController(view).navigate(R.id.mostrarPreioFragment,bundle);
            }
        });

    }

    /**
     * Cuantos elementos voy a necesitar
     * @return
     */
    @Override
    public int getItemCount() {

        return listaPrecios.size();
    }

    public class ElementoPrecio extends RecyclerView.ViewHolder{

        private TextView txtNombre, txtDescripcion,txtPrecio;
        private Button btnComprar;
        private CardView card;


        public ElementoPrecio(@NonNull View itemView) {
            super(itemView);


            txtNombre=itemView.findViewById(R.id.lblNombrePrecio);
            txtPrecio=itemView.findViewById(R.id.lblPrecio);
            txtDescripcion=itemView.findViewById(R.id.lblDescripcionprecio);
            btnComprar=itemView.findViewById(R.id.btnComprar);
            card =itemView.findViewById(R.id.card);

        }
    }

}
