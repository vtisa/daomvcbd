<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.2/font/bootstrap-icons.css">
    <title>Resultados de Búsqueda</title>
</head>
<body>

    <div class="container py-5">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card">
                    <div class="card-header">
                        <h1 class="mb-0"><i class="bi bi-search me-2"></i>Resultados de Búsqueda</h1>
                    </div>
                    <div class="card-body">
                        <form action="BuscarServlet" method="post">
                            <div class="mb-3">
                                <label for="nombre" class="form-label">Nombre del Producto</label>
                                <input type="text" id="nombre" name="nombre" class="form-control" required>
                            </div>
                            <div class="mb-3">
                                <label for="origen" class="form-label">Origen de la Base de Datos</label>
                                <select id="origen" name="origen" class="form-select" required>
                                    <option value="todos" <c:if test="${param.origen == 'todos'}">selected</c:if>>Todos</option>
                                    <option value="mysql" <c:if test="${param.origen == 'mysql'}">selected</c:if>>MySQL</option>
                                    <option value="mongodb" <c:if test="${param.origen == 'mongodb'}">selected</c:if>>MongoDB</option>
                                </select>
                            </div>
                            <div class="d-flex justify-content-between">
                                <button type="submit" class="btn btn-primary"><i class="bi bi-search me-2"></i>Buscar</button>
                                <a href="/daomvcbd/" class="btn btn-secondary"><i class="bi bi-arrow-left me-2"></i>Volver</a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <c:choose>
        <c:when test="${not empty productosEncontrados}">
            <div class="container py-5">
                <div class="row justify-content-center">
                    <div class="col-md-10">
                        <div class="card">
                            <div class="card-header bg-success text-white">
                                <h2 class="mb-0"><i class="bi bi-list-check me-2"></i>Productos Encontrados</h2>
                            </div>
                            <div class="card-body">
                                <table class="table table-bordered table-striped">
                                    <thead>
                                        <tr>
                                            <th>Código</th>
                                            <th>Nombre</th>
                                            <th>Precio</th>
                                            <th>Stock</th>
                                            <th>Origen</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="producto" items="${productosEncontrados}">
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
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <c:choose>
                <c:when test="${not empty param.nombre}">
                    <div class="container py-5">
                        <div class="row justify-content-center">
                            <div class="col-md-8">
                                <div class="card">
                                    <div class="card-header bg-danger text-white">
                                        <h2 class="mb-0"><i class="bi bi-exclamation-triangle-fill me-2"></i>No hay productos</h2>
                                    </div>
                                    <div class="card-body">
                                        <p class="lead text-center">No se encontraron productos con el nombre y origen especificados.</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="container py-5">
                        <div class="row justify-content-center">
                            <div class="col-md-8">
                                <div class="card">
                                    <div class="card-header bg-info text-white">
                                        <h2 class="mb-0"><i class="bi bi-info-circle-fill me-2"></i>Información</h2>
                                    </div>
                                    <div class="card-body">
                                        <p class="lead text-center">Aquí aparecen los productos buscados.</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
        </c:otherwise>
    </c:choose>

</body>
</html>