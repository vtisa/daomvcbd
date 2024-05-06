package controlador;

import modelo.Producto;
import modelo.ProductoDAOImpl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "RegistrarServlet", urlPatterns = {"/RegistrarServlet"})
public class RegistrarServlet extends HttpServlet {

    private final ProductoDAOImpl productoDAO; 

    public RegistrarServlet() {
        this.productoDAO = new ProductoDAOImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String codigo = request.getParameter("codigo");
        String nombre = request.getParameter("nombre");
        double precio = Double.parseDouble(request.getParameter("precio"));
        int stock = Integer.parseInt(request.getParameter("stock"));
        String origen = request.getParameter("origen");

        Producto producto = new Producto(codigo, nombre, precio, stock, origen);
        productoDAO.insertarProducto(producto);

        response.sendRedirect("MostrarServlet");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
