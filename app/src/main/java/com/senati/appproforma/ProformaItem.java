package com.senati.appproforma;

public class ProformaItem {
    private String codigo;
    private String producto;
    private double precio;
    private int cantidad;

    // Constructor que utiliza los setters para validar desde la creación
    public ProformaItem(String codigo, String producto, double precio, int cantidad) {
        setCodigo(codigo);
        setProducto(producto);
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public String getcodigo() { return codigo; }
    public String getProducto() { return producto; }
    public double getPrecio() { return precio; }
    public int getCantidad() { return cantidad; }

    // Modificador que LANZA UN ERROR si excede los 6 caracteres
    public void setCodigo(String codigo) {
        if (codigo != null && codigo.length() > 6) {
            // Esto "dispara" el error hacia la aplicación
            throw new IllegalArgumentException("El código no puede tener más de 6 dígitos.");
        }
        this.codigo = codigo;
    }

    // Modificador con límite máximo de 40 caracteres (este lo dejamos recortando)
    public void setProducto(String producto) {
        if (producto != null && producto.length() > 40) {
            this.producto = producto.substring(0, 40);
        } else {
            this.producto = producto;
        }
    }

    public void setPrecio(double precio) { this.precio = precio; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public double getTotal() {
        return precio * cantidad;
    }

    @Override
    public String toString() {
        return "Código: " + codigo +
                " | " + producto +
                " | S/. " + precio +
                " | Cant: " + cantidad +
                " | Total: S/. " + getTotal();
    }
}