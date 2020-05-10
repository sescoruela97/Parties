package com.sergiescoruela.parties.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.sergiescoruela.parties.R;
import com.sergiescoruela.parties.pojo.Local;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;

public class LocalAdapter extends RecyclerView.Adapter<LocalAdapter.ElementoLocal> {

    private Context context;
    private int resource;
    private ArrayList<Local> listaLocales;

    public LocalAdapter(Context context, int resource, ArrayList<Local> listaLocales) {
        this.context = context;
        this.resource = resource;
        this.listaLocales = listaLocales;
    }

    /**
     * Intancia cuando sea necesario una nueva card que es (ElemntoRegalo)
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public ElementoLocal onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View card = LayoutInflater.from(context).inflate(resource, null);
        ElementoLocal elementoLocal = new ElementoLocal(card);
        return elementoLocal;
    }

    /**
     * Rellenar con datos el Objeto generado
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ElementoLocal holder, int position) {

        final Local local= listaLocales.get(position);


        //holder.imagen.setImageResource(local.getImgagen());
        holder.txtNombre.setText(local.getNombre());
        holder.txtDescripcion.setText(local.getDescripcion());
       // System.out.println(local.getListaPrecios().size() + " Lista de precios ");

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "has echo click", Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("localUnico", local);
                Navigation.findNavController(view).navigate(R.id.mostrarLocalFragment,bundle);
            }
        });

        if (local != null) {
            Picasso.get()
                    .load(local.getImagen().get(0))
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_background)
                    .into(holder.imagen);
        }

    }

    /**
     * Cuantos elementos voy a necesitar
     * @return
     */
    @Override
    public int getItemCount() {
        return listaLocales.size();
    }

    public class ElementoLocal extends RecyclerView.ViewHolder{

        private ImageView imagen;
        private TextView txtNombre, txtDescripcion;
        private CardView card;


        public ElementoLocal(@NonNull View itemView) {
            super(itemView);

            imagen = itemView.findViewById(R.id.imgElemento);
            txtNombre=itemView.findViewById(R.id.txtNombreElemnto);
            txtDescripcion=itemView.findViewById(R.id.txtDescripcionElemento);
            card =itemView.findViewById(R.id.card);

        }
    }

}
