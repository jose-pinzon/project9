package com.example.project9.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project9.Adaptadores.adapterUsuario;
import com.example.project9.MainActivity;
import com.example.project9.Modelos.autor;
import com.example.project9.Modelos.usuario;
import com.example.project9.R;
import com.example.project9.configuracion.config;
import com.example.project9.databinding.FragmentHomeBinding;
import com.example.project9.retrofit.interfaceRetrofit;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private View myView;
    private RecyclerView myRecycler;
    private adapterUsuario myAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.myView = view;
        final MainActivity navigation = (MainActivity)  getActivity();
        FloatingActionButton fabGuardar = navigation.findViewById(R.id.fab);

        if(fabGuardar != null) {
            fabGuardar.setImageResource(R.drawable.guardar_usuario);
            traerUsuario();
            fabGuardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("accion", "N");
                    NavHostFragment.findNavController(getParentFragment()).navigate(R.id.homeFragmentDetalle, bundle);
                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void traerUsuario(){
    interfaceRetrofit cliente = config.getRetrofit().create(interfaceRetrofit.class);
        Call <List<autor>>call = cliente.getUsername("authors");
        call.enqueue(new Callback<List<autor>>() {
            @Override
            public void onResponse(Call<List<autor>> call, Response<List<autor>> response) {
                mostrarRecycler(response.body());
            }
            @Override
            public void onFailure(Call<List<autor>> call, Throwable t) {
                Snackbar mensaje = Snackbar.make(myView, "ha ocurrido un error",Snackbar.LENGTH_SHORT);
                mensaje.show();
            }
        });
    }


    private void mostrarRecycler (List<autor> listado) {
        myRecycler = (RecyclerView) myView.findViewById(R.id.rcvCfragUser);
        myAdapter = new adapterUsuario(listado, getContext());
        myRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        myRecycler.setAdapter(myAdapter);

        myAdapter.setOnclickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast message = Toast.makeText(getContext(), listado.get(myRecycler.getChildAdapterPosition(view)).getUsername(),Toast.LENGTH_SHORT);
                message.show();
                Bundle bundle = new Bundle();
                bundle.putString("accion","M");
                bundle.putString("id",listado.get(myRecycler.getChildLayoutPosition(view)).getId());
                bundle.putString("username",listado.get(myRecycler.getChildLayoutPosition(view)).getUsername());
                bundle.putString("lastname",listado.get(myRecycler.getChildLayoutPosition(view)).getLastname());
                bundle.putString("location",listado.get(myRecycler.getChildLayoutPosition(view)).getLocation());
                bundle.putString("pass",listado.get(myRecycler.getChildLayoutPosition(view)).getPassword());
                bundle.putString("imagen",listado.get(myRecycler.getChildLayoutPosition(view)).getImagen());
                NavHostFragment.findNavController(getParentFragment()).navigate(R.id.homeFragmentDetalle);

            }
        });
    }
}