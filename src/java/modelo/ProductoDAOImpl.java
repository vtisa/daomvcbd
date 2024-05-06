package modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class ProductoDAOImpl {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/ventas";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "";

    private final MongoCollection<Document> collectionMongo;
    private final Connection conexionJDBC;

    // Constructor para inicializar con base de datos MySQL
    public ProductoDAOImpl() {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("venta");
        collectionMongo = database.getCollection("producto");

        try {
            conexionJDBC = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Error al conectar con la base de datos MySQL", e);
        }
    }

    // Constructor para inicializar con base de datos MongoDB
    public ProductoDAOImpl(MongoCollection<Document> collectionMongo) {
        this.collectionMongo = collectionMongo;
        conexionJDBC = null; // No se usa para MongoDB
    }

    public void insertarProducto(Producto producto) {
        switch (producto.getOrigen()) {
            case "mysql":
                insertarProductoMySQL(producto);
                break;
            case "mongodb":
                insertarProductoMongoDB(producto);
                break;
            case "ambas":
                insertarProductoEnAmbas(producto);
                break;
            default:
                insertarProductoEnAmbas(producto);
                break;
        }
    }

    private void insertarProductoEnAmbas(Producto producto) {
        insertarProductoMySQL(producto);
        insertarProductoMongoDB(producto);
    }

    private void insertarProductoMySQL(Producto producto) {
        String sql = "INSERT INTO producto (codigo, nombre, precio, stock, origen) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = conexionJDBC.prepareStatement(sql)) {
            statement.setString(1, producto.getCodigo());
            statement.setString(2, producto.getNombre());
            statement.setDouble(3, producto.getPrecio());
            statement.setInt(4, producto.getStock());
            statement.setString(5, "mysql");
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar producto en MySQL", e);
        }
    }

    private void insertarProductoMongoDB(Producto producto) {
        Document doc = new Document("codigo", producto.getCodigo())
                .append("nombre", producto.getNombre())
                .append("precio", producto.getPrecio())
                .append("stock", producto.getStock())
                .append("origen", "mongodb");
        collectionMongo.insertOne(doc);
    }

    public List<Producto> obtenerProductosPorOrigen(String origen) {
        List<Producto> productos = new ArrayList<>();

        if (origen == null || origen.equals("seleccionar")) {
            // No hacer nada, se mostrará un mensaje de "No hay productos disponibles"
        } else if (origen.isEmpty() || origen.equals("")) {
            productos.addAll(obtenerProductosMySQL());
            productos.addAll(obtenerProductosMongoDB());
        } else if (origen.equals("mysql")) {
            productos.addAll(obtenerProductosMySQL());
        } else if (origen.equals("mongodb")) {
            productos.addAll(obtenerProductosMongoDB());
        }

        return productos;
    }

    private List<Producto> obtenerProductosMySQL() {
        List<Producto> listaProductos = new ArrayList<>();
        String sql = "SELECT * FROM producto WHERE origen = 'mysql'";
        try (PreparedStatement statement = conexionJDBC.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Producto producto = new Producto(
                        resultSet.getString("codigo"),
                        resultSet.getString("nombre"),
                        resultSet.getDouble("precio"),
                        resultSet.getInt("stock"),
                        resultSet.getString("origen")
                );
                listaProductos.add(producto);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener productos de MySQL", e);
        }
        return listaProductos;
    }

    private List<Producto> obtenerProductosMongoDB() {
        List<Producto> listaProductos = new ArrayList<>();
        for (Document doc : collectionMongo.find(new Document("origen", "mongodb"))) {
            Producto producto = new Producto(
                    doc.getString("codigo"),
                    doc.getString("nombre"),
                    doc.getDouble("precio"),
                    doc.getInteger("stock"),
                    doc.getString("origen")
            );
            listaProductos.add(producto);
        }
        return listaProductos;
    }

    public List<Producto> buscarProductosPorNombre(String nombre, String origen) {
        List<Producto> listaProductos = new ArrayList<>();
        if (origen == null) {
            listaProductos.addAll(buscarProductosPorNombreMySQL(nombre));
            listaProductos.addAll(buscarProductosPorNombreMongoDB(nombre));
        } else {
            switch (origen) {
                case "mysql":
                    listaProductos.addAll(buscarProductosPorNombreMySQL(nombre));
                    break;
                case "mongodb":
                    listaProductos.addAll(buscarProductosPorNombreMongoDB(nombre));
                    break;
                default:
                    listaProductos.addAll(buscarProductosPorNombreMySQL(nombre));
                    listaProductos.addAll(buscarProductosPorNombreMongoDB(nombre));
                    break;
            }
        }
        return listaProductos;
    }

    private List<Producto> buscarProductosPorNombreMySQL(String nombre) {
        List<Producto> listaProductos = new ArrayList<>();
        String sql = "SELECT * FROM producto WHERE nombre LIKE ? AND origen = 'mysql'";
        try (PreparedStatement statement = conexionJDBC.prepareStatement(sql)) {
            statement.setString(1, "%" + nombre + "%");
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Producto producto = new Producto(
                            resultSet.getString("codigo"),
                            resultSet.getString("nombre"),
                            resultSet.getDouble("precio"),
                            resultSet.getInt("stock"),
                            resultSet.getString("origen")
                    );
                    listaProductos.add(producto);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar productos por nombre en MySQL", e);
        }
        return listaProductos;
    }

    private List<Producto> buscarProductosPorNombreMongoDB(String nombre) {
        List<Producto> listaProductos = new ArrayList<>();
        com.mongodb.client.FindIterable<Document> cursor = collectionMongo.find(
                new Document("nombre", java.util.regex.Pattern.compile(nombre, java.util.regex.Pattern.CASE_INSENSITIVE))
                        .append("origen", "mongodb")
        );
        for (Document doc : cursor) {
            Producto producto = new Producto(
                    doc.getString("codigo"),
                    doc.getString("nombre"),
                    doc.getDouble("precio"),
                    doc.getInteger("stock"),
                    doc.getString("origen")
            );
            listaProductos.add(producto);
        }
        return listaProductos;
    }
}
