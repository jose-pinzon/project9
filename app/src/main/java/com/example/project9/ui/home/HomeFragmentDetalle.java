package com.example.project9.ui.home;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project9.MainActivity;
import com.example.project9.R;
import com.example.project9.configuracion.config;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

public class HomeFragmentDetalle extends Fragment {
    private TextView id_usuario;
    private EditText nombreUsuario, apellido_p, apellido_m, usuario, password;
    private ImageButton camara, galeria;
    private ImageView imgUsuario;
    private  String accion = "";

    private HomeFragmentDetalleViewModel mViewModel;

    public static HomeFragmentDetalle newInstance() {
        return new HomeFragmentDetalle();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_fragment_detalle, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        id_usuario = view.findViewById(R.id.duCEtxtid);
        nombreUsuario = view.findViewById(R.id.duCEtxtNombre);
        apellido_p = view.findViewById(R.id.duCEtxtApellidop);
        apellido_m = view.findViewById(R.id.duCEtxtApelldiom);
        usuario = view.findViewById(R.id.duCEtxtUsuario);
        password = view.findViewById(R.id.duCEtxtPassword);
        imgUsuario = view.findViewById(R.id.duCimgVUsuario);
        camara = view.findViewById(R.id.duCBtnCamara);
        galeria = view.findViewById(R.id.duCBtnGaleria);
       // if (getArguments() != null)
        this.accion = getArguments().getString("accion");
        final MainActivity navigation = (MainActivity)  getActivity();
        FloatingActionButton fabGuardar = navigation.findViewById(R.id.fab);
        fabGuardar.setImageResource(R.drawable.guardar);
        switch (accion){
            case "N":
                break;
            case  "M":
                id_usuario.setText(getArguments().getString("id_usuario"));
                nombreUsuario.setText(getArguments().getString("nombre"));
                apellido_p.setText(getArguments().getString("apellidop"));
                apellido_m.setText(getArguments().getString("apellido_m"));
                usuario.setText(getArguments().getString("usuario"));
                password.setText(getArguments().getString("pass"));

                String rutaImagen = config.getUrlImages() + getArguments().getString("imagen");
                Picasso.with(getContext()).load(rutaImagen).fit().into(imgUsuario);

                break;
        }


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HomeFragmentDetalleViewModel.class);
        // TODO: Use the ViewModel
    }

}