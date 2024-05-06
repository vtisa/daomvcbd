package controlador;

import modelo.Producto;
import modelo.ProductoDAOImpl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "MostrarProductosServlet", value = "/MostrarProductosServlet")
public class MostrarServlet extends HttpServlet {
    private final ProductoDAOImpl productoDAO = new ProductoDAOImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String baseDatos = request.getParameter("baseDatos");
        List<Producto> productos = productoDAO.obtenerProductosPorOrigen(baseDatos);
        request.setAttribute("productos", productos);
        request.getRequestDispatcher("productos.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
