<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registro de Productos</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.2/font/bootstrap-icons.css">
</head>
<body>
    <div class="container py-5">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card">
                    <div class="card-body">
                        <h1 class="card-title text-center mb-4">Registro de Productos</h1>
                        <% if (request.getAttribute("error") != null) { %>
                            <div class="alert alert-danger" role="alert"><%= request.getAttribute("error") %></div>
                        <% } %>
                        <form action="RegistrarServlet" method="post" onsubmit="return validarFormulario();">
                            <div class="mb-3">
                                <label for="origen" class="form-label">Origen de la Base de Datos:</label>
                                <select id="origen" name="origen" class="form-select">
                                    <option value="">Seleccionar</option>
                                    <option value="mysql" <%= ("mysql".equals(request.getParameter("origen"))) ? "selected" : "" %>>MySQL</option>
                                    <option value="mongodb" <%= ("mongodb".equals(request.getParameter("origen"))) ? "selected" : "" %>>MongoDB</option>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label for="codigo" class="form-label">Código:</label>
                                <input type="text" class="form-control" id="codigo" name="codigo" required>
                            </div>
                            <div class="mb-3">
                                <label for="nombre" class="form-label">Nombre:</label>
                                <input type="text" class="form-control" id="nombre" name="nombre" required>
                            </div>
                            <div class="mb-3">
                                <label for="precio" class="form-label">Precio:</label>
                                <input type="number" class="form-control" id="precio" name="precio" step="any">
                            </div>
                            <div class="mb-3">
                                <label for="stock" class="form-label">Stock:</label>
                                <input type="number" class="form-control" id="stock" name="stock">
                            </div>
                            <div class="d-flex justify-content-between">
                                <button type="submit" class="btn btn-primary"><i class="bi bi-plus-circle me-2"></i>Agregar Producto</button>
                                <button type="button" class="btn btn-danger" onclick="limpiarCampos()"><i class="bi bi-eraser me-2"></i>Limpiar</button>
                                <a href="/daomvcbd/" class="btn btn-secondary"><i class="bi bi-arrow-left me-2"></i>Volver</a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="js/registro.js"></script>
</body>
</html>
