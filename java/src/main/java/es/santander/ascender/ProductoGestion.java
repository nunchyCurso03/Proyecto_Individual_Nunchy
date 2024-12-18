package es.santander.ascender;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ProductoGestion {

    private Map<Long, Producto> productos = new HashMap<>();

    public ProductoGestion() {
        productos.put(1L, new Producto(1, "Producto A", "Descripción A", 100.0f, 10));
        productos.put(2L, new Producto(2, "Producto B", "Descripción B", 150.0f, 0));
    }

    // 1. Obtener un producto por ID
    public Producto getProductoPorId(long id) {
        if (!productos.containsKey(id)) {
            System.out.println("Producto no encontrado.");
            return null;
        }
        return productos.get(id);
    }

    // 2. Obtener todos los productos
    public Collection<Producto> getTodosLosProductos() {
        return productos.values();
    }

    // 3. Crear un nuevo producto
    public Producto crearProducto(Producto producto) {
        long maxId = productos.keySet().stream().mapToLong(k -> k).max().orElse(0);
        producto.setId(maxId + 1);
        productos.put(producto.getId(), producto);

        //System.out.println("Producto creado: " + producto.getNombre());
        return producto;
    }

    // 4. Actualizar un producto existente
    public Producto actualizarProducto(Long id, Producto productoActualizado) {
        Producto productoExistente = productos.get(id);
        if (productoExistente == null) {
            System.out.println("Producto no encontrado.");
            return null;
        }

        productoExistente.setNombre(productoActualizado.getNombre());
        productoExistente.setDescripcion(productoActualizado.getDescripcion());
        productoExistente.setPrecio(productoActualizado.getPrecio());
        productoExistente.setCantidad(productoActualizado.getCantidad());

        System.out.println("Producto actualizado: " + productoExistente.getNombre());
        return productoExistente;
    }

    // 5. Eliminar un producto
    public boolean eliminarProducto(Long id) {
        Producto productoExistente = productos.get(id);
        if (productoExistente == null) {
            System.out.println("Producto no encontrado.");
            return false;
        }
        productos.remove(id);
        System.out.println("Producto eliminado con ID: " + id);
        return true;
    }

    // 6. Comprar un producto (disminuye la cantidad en 1)
    public String comprarProducto(Long id) {
        Producto producto = productos.get(id);

        if (producto == null) {
            return "Producto no encontrado.";
        }

        if (producto.getCantidad() <= 0) {
            return "Producto sin stock disponible.";
        }

        producto.setCantidad(producto.getCantidad() - 1);
        return "Compra realizada con éxito. Producto: " + producto.getNombre();
    }

    // Métodos auxiliares para depuración
    public Map<Long, Producto> getProductos() {
        return productos;
    }

    public void setProductos(Map<Long, Producto> productos) {
        this.productos = productos;
    }

     
}