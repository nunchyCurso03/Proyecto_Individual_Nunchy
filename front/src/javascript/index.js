$.get('http://localhost:1234/api/productos');

$(document).ready(function () {
    const API_URL = "/api/productos"; // Usar el proxy configurado


    // 1. Crear un producto
    $("#formularioCrearProducto").submit(function (event) {
        event.preventDefault();
        const nuevoProducto = {
            nombre: $("#nombre").val(),
            descripcion: $("#descripcion").val(),
            precio: parseFloat($("#precio").val()),
            cantidad: parseInt($("#cantidad").val())
        };

        $.ajax({
            url: API_URL,
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(nuevoProducto),
            success: function (response) {
                alert("Producto creado con éxito: " + response.nombre);
                $("#formularioCrearProducto")[0].reset();
            },
            error: function () {
                alert("Error al crear el producto.");
            }
        });
    });

    // 2. Cargar todos los productos
    $("#cargarProductos").click(function () {
        $.get(API_URL, function (data) {
            $("#listaProductos").empty();
            data.forEach(producto => {
                $("#listaProductos").append(`
                <li>
                    ID: ${producto.id} - ${producto.nombre} (${producto.descripcion})
                    - Precio: ${producto.precio} € - Cantidad: ${producto.cantidad}
                </li>
            `);
            });
        }).fail(function () {
            alert("Error al cargar productos.");
        });
    });

    // 3. Comprar un producto
    $("#comprarProducto").click(function () {
        const id = $("#producto-id-compra").val();

        $.post(`${API_URL}/${id}/compra`, function (response) {
            $("#compra-status").text(response);
            $("#producto-id-compra").val("");
        }).fail(function (jqXHR) {
            if (jqXHR.status === 404) {
                $("#compra-status").text("Producto no encontrado.");
            } else if (jqXHR.status === 400) {
                $("#compra-status").text("Producto sin stock disponible.");
            } else {
                $("#compra-status").text("Error al realizar la compra.");
            }
        });
    });

// Eliminar producto
$('#eliminarProducto').click(function() {
    let productoId = $('#producto-id-eliminar').val();
    $.ajax({
        url: `${API_URL}/${productoId}`,
        method: 'DELETE',
        success: function() {
            $('#eliminar-status').text('Producto eliminado con éxito.');
            $('#producto-id-eliminar').val(''); // Limpiar el input
            $('#cargarProductos').click(); // Recargar la lista de productos
        },
        error: function() {
            $('#eliminar-status').text('Producto no encontrado.');
        }
    });
});





});
