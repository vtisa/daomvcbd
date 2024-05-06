<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Lista de Productos</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.2/font/bootstrap-icons.css">
    
</head>
<body>
    <div class="container py-5">
        <div class="row justify-content-center">
            <div class="col-md-10">
                <div class="card">
                    <div class="card-body">
                        <h1 class="mb-4 text-center">Lista de Productos</h1>
                        <form action="MostrarServlet" method="post" class="mb-4">
                            <div class="form-group">
                                <label for="baseDatos">Selecciona la base de datos:</label>
                                <select class="form-select" name="baseDatos" id="baseDatos">
                                    <option value="seleccionar">--Seleccionar--</option>
                                    <option value="" ${param.baseDatos == '' ? 'selected' : ''}>Todos</option>
                                    <option value="mysql" ${param.baseDatos == 'mysql' ? 'selected' : ''}>MySQL</option>
                                    <option value="mongodb" ${param.baseDatos == 'mongodb' ? 'selected' : ''}>MongoDB</option>
                                </select>
                            </div>
                            <button type="submit" class="btn btn-primary mt-2"><i class="bi-eye-fill me-2"></i>Mostrar</button>
                        </form>
                        <c:choose>
                            <c:when test="${not empty productos}">
                                <table class="table table-striped table-hover">
                                    <thead class="thead-dark">
                                        <tr>
                                            <th>Código</th>
                                            <th>Nombre</th>
                                            <th>Precio</th>
                                            <th>Stock</th>
                                            <th>Origen</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="producto" items="${productos}">
                                            <tr>
                                                <td>${producto.codigo}</td>
                                                <td>${producto.nombre}</td>
                                                <td>${producto.precio}</td>
                                                <td>${producto.stock}</td>
                                                <td>${producto.origen}</td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </c:when>
                            <c:otherwise>
                                <c:if test="${param.baseDatos != null && param.baseDatos != 'seleccionar'}">
                                    <p class="text-center">No hay productos disponibles.</p>
                                </c:if>
                            </c:otherwise>

                        </c:choose>
                        <div class="row justify-content-end mt-4">
                            <div class="col-md-12 text-end">
                                <a href="registro.jsp" class="btn btn-primary"><i class="bi bi-plus-circle me-2"></i>Agregar</a>
                                <a href="productosEncontrados.jsp" class="btn btn-primary"><i class="bi bi-search me-2"></i>Buscar producto</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>