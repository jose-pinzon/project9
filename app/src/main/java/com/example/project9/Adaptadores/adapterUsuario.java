package com.example.project9.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project9.Modelos.autor;
import com.example.project9.Modelos.usuario;
import com.example.project9.R;
import com.example.project9.configuracion.config;
import com.squareup.picasso.Picasso;

import java.text.BreakIterator;
import java.util.List;

public class adapterUsuario extends RecyclerView.Adapter<adapterUsuario.itemVistaActual> implements View.OnClickListener{

    private List<autor> listado;
    private Context context;
    private View.OnClickListener listener;

    public  adapterUsuario(List<autor>listado, Context context){
        this.listado = listado;
        this.context = context;
    }


    @NonNull
    @Override
    public itemVistaActual onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myview;
        LayoutInflater mostrarcontrol = LayoutInflater.from(parent.getContext());
        myview = mostrarcontrol.inflate(R.layout.list_view_user,parent, false);
        myview.setOnClickListener(listener);
        return new itemVistaActual(myview);


    }

    @Override
    public void onBindViewHolder(@NonNull itemVistaActual holder, int position) {
        String image = config.getUrlImages();
        holder.rcvId.setText(listado.get(position).getId());
        holder.rcvNombre.setText(listado.get(position).getUsername());
        holder.rcvApellidos.setText(listado.get(position).getLastname() + " " + listado.get(position).getLocation());
        //holder.rcvRol.setText(listado.get(position).getRoles().getNombre());

        Picasso.with(context).load(image + listado.get(position).getImagen()).fit().into(holder.rcvImagen);
    }

    @Override
    public int getItemCount() {
        return listado.size();
    }

    @Override
    public void onClick(View view) {
        if (listener != null){
            listener.onClick(view);

        }
    }

    //Este es solo para asignarlo los datos a listado que esta declarado a nivel de la clase
    public void setOnclickListener(View.OnClickListener listener){
        this.listener= listener;
    }

    public class itemVistaActual extends RecyclerView.ViewHolder{
        TextView rcvId, rcvNombre, rcvApellidos, rcvRol;
        ImageView rcvImagen;

        public  itemVistaActual(@NonNull View itemView){
            super(itemView);
            //union parte grafica y logica
            rcvId = itemView.findViewById(R.id.txtVUCId);
            rcvNombre = itemView.findViewById(R.id.txtVUCNombre);
            rcvApellidos = itemView.findViewById(R.id.txtVUCApAm);
            rcvRol = itemView.findViewById(R.id.txtVUCRol);
            rcvImagen = itemView.findViewById(R.id.imgVUser);

        }

    }
}
