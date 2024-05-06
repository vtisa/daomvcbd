package modelo;

import java.util.List;

public class ProductoDAO {
    
    private final ProductoDAOImpl productoDAO;

    public ProductoDAO(ProductoDAOImpl productoDAO) {
        this.productoDAO = productoDAO;
    }

    public void agregarProducto(Producto producto) {
        productoDAO.insertarProducto(producto);
    }

    public List<Producto> obtenerTodosLosProductos(String origen) {
        return productoDAO.obtenerProductosPorOrigen(origen);
    }

    public List<Producto> buscarProductosPorNombre(String nombre, String origen) {
        return productoDAO.buscarProductosPorNombre(nombre, origen);
    }
}



