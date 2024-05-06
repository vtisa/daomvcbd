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

@WebServlet(name = "BuscarServlet", urlPatterns = {"/BuscarServlet"})
public class BuscarServlet extends HttpServlet {
    private ProductoDAOImpl productoDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        productoDAO = new ProductoDAOImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nombre = request.getParameter("nombre");
        String origen = request.getParameter("origen");
        List<Producto> productosEncontrados = productoDAO.buscarProductosPorNombre(nombre, origen);
        request.setAttribute("productosEncontrados", productosEncontrados);
        request.getRequestDispatcher("productosEncontrados.jsp").forward(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }
}
