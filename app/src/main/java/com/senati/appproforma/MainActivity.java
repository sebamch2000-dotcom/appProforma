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
    ArrayList<ProformaItem> lista = new ArrayList<>();
    ArrayAdapter<ProformaItem> adaptador;
    int posSelec = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        txtCod = findViewById(R.id.txtCod);
        txtProduc = findViewById(R.id.txtProduc);
        txtPrecio = findViewById(R.id.txtPrecio);
        txtCant = findViewById(R.id.txtCant);
        TextView txtResult = findViewById(R.id.txtResult);
        btnNuevo = findViewById(R.id.btnNuevo);
        btnGrabar = findViewById(R.id.btnGrabar);
        btnEditar = findViewById(R.id.btnEditar);
        btnEliminar = findViewById(R.id.btnEliminar);
        listProforma = findViewById(R.id.listProforma);

        // Nuevo
        btnNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtCod.setText("");
                txtProduc.setText("");
                txtPrecio.setText("");
                txtCant.setText("");
                txtResult.setText("S/. 0.00");
                txtCod.requestFocus();
            }
        });

        // Grabar
        btnGrabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dni = txtCod.getText().toString();
                String prod = txtProduc.getText().toString();
                double precio = Double.parseDouble(txtPrecio.getText().toString());
                int cant = Integer.parseInt(txtCant.getText().toString());
                // Operacion Aritmetica
                double total = precio * cant;
                txtResult.setText("Total: S/. " + total);
                lista.add(new ProformaItem(dni, prod, precio, cant));
                adaptador.notifyDataSetChanged();
                limpiarCampos();
            }
        });
        // Seleccionar item
        listProforma.setOnItemClickListener((parent, view, position, id) -> {
            posSelec = position;
            ProformaItem item = lista.get(position);
            txtCod.setText(item.getCodigo());
            txtProduc.setText(item.getProducto());
            txtPrecio.setText(String.valueOf(item.getPrecio()));
            txtCant.setText(String.valueOf(item.getCantidad()));
            //txtResultado.setText(String.valueOf(item.getTotal()));
            txtResult.setText("S/ " + item.getTotal());
        });
        // Editar
        btnEditar.setOnClickListener(View v -> {
            if (posSelec != -1) {
                ProformaItem item = lista.get(posSelec);
                item.setProducto(txtProduc.getText().toString());
                item.setPrecio(Double.parseDouble(txtPrecio.getText().toString()));
                item.setCantidad(Integer.parseInt(txtCant.getText().toString()));
                adaptador.notifyDataSetChanged();
                limpiarCampos();
            }
        });
        // Eliminar
        btnEliminar.setOnClickListener(v -> {
            if (posSelec != -1) {
                lista.remove(posSelec);
                adaptador.notifyDataSetChanged();
                limpiarCampos();
            }
        });
        // Fin Programacion
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (View v, WindowInsetsCompat insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Inicio Imlementar
    private void limpiarCampos() {
        txtCod.setText("");
        txtProduc.setText("");
        txtPrecio.setText("");
        txtCant.setText("");
        posSelec = -1;
    }
// Fin Implementacion
}