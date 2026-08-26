package com.senati.appproforma;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText txtCod, txtProduc, txtPrecio, txtCant;
    private Button btnGrabar, btnEditar, btnEliminar, btnNuevo;
    private ListView listProforma;
    private TextView txtResult;

    ArrayList<ProformaItem> lista = new ArrayList<>();
    ArrayAdapter<ProformaItem> adaptador;
    int posSelec = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Enlaces con la vista XML
        txtCod = findViewById(R.id.txtcodigo);
        txtProduc = findViewById(R.id.txtProducto);
        txtPrecio = findViewById(R.id.txtPrecio);
        txtCant = findViewById(R.id.txtCantidad);
        txtResult = findViewById(R.id.txtResultado);

        btnNuevo = findViewById(R.id.btnNuevo);
        btnGrabar = findViewById(R.id.btnGrabar);
        btnEditar = findViewById(R.id.btnEditar);
        btnEliminar = findViewById(R.id.btnEliminar);
        listProforma = findViewById(R.id.listProforma);

        // Inicializar y asignar el adaptador al ListView
        adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lista);
        listProforma.setAdapter(adaptador);

        // Botón Nuevo
        btnNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpiarCampos();
                txtResult.setText("Total: S/. 0.00");
                txtCod.requestFocus();
            }
        });

        // Botón Grabar (Corregido y unificado)
        btnGrabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String dni = txtCod.getText().toString();
                    String prod = txtProduc.getText().toString();
                    double precio = Double.parseDouble(txtPrecio.getText().toString());
                    int cant = Integer.parseInt(txtCant.getText().toString());

                    // Si el DNI tiene más de 6 dígitos, lanzará el error hacia el catch
                    lista.add(new ProformaItem(dni, prod, precio, cant));

                    // Si todo sale bien, calculamos y mostramos:
                    double total = precio * cant;
                    txtResult.setText("Total: S/. " + total);
                    adaptador.notifyDataSetChanged();
                    limpiarCampos();

                } catch (IllegalArgumentException e) {
                    // Atrapa el error de los 6 dígitos y lo muestra en rojo
                    txtCod.setError(e.getMessage());
                    txtCod.requestFocus();
                }
            }
        });

        // Seleccionar item de la lista
        listProforma.setOnItemClickListener((parent, view, position, id) -> {
            posSelec = position;
            ProformaItem item = lista.get(position);

            txtCod.setText(item.getcodigo());
            txtProduc.setText(item.getProducto());
            txtPrecio.setText(String.valueOf(item.getPrecio()));
            txtCant.setText(String.valueOf(item.getCantidad()));
            txtResult.setText("Total: S/. " + item.getTotal());
        });

        // Botón Editar
        btnEditar.setOnClickListener(v -> {
            if (posSelec != -1) {
                try {
                    ProformaItem item = lista.get(posSelec);
                    item.setProducto(txtProduc.getText().toString());
                    item.setPrecio(Double.parseDouble(txtPrecio.getText().toString()));
                    item.setCantidad(Integer.parseInt(txtCant.getText().toString()));
                    adaptador.notifyDataSetChanged();
                    limpiarCampos();
                    txtResult.setText("Total: S/. 0.00");
                } catch (NumberFormatException e) {
                }
            }
        });

        // Botón Eliminar
        btnEliminar.setOnClickListener(v -> {
            if (posSelec != -1) {
                lista.remove(posSelec);
                adaptador.notifyDataSetChanged();
                limpiarCampos();
                txtResult.setText("Total: S/. 0.00");
            }
        });

        // Ajustes de ventana
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (View v, WindowInsetsCompat insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Método para limpiar los campos
    private void limpiarCampos() {
        txtCod.setText("");
        txtProduc.setText("");
        txtPrecio.setText("");
        txtCant.setText("");
        posSelec = -1;
    }
}