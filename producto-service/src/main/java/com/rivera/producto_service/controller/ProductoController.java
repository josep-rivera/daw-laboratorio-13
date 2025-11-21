package com.rivera.producto_service.controller;

import com.rivera.producto_service.client.ProductoResponse;
import com.rivera.producto_service.dto.Categoria;
import com.rivera.producto_service.model.Producto;
import com.rivera.producto_service.service.ProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    // LISTAR TODOS LOS PRODUCTOS
    @GetMapping
    public List<Producto> listar() {
        return service.listarTodos();
    }

    // BUSCAR PRODUCTO POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // OBTENER PRODUCTO CON SU CATEGORÍA (comunicación entre microservicios)
    @GetMapping("/{id}/con-categoria")
    public ResponseEntity<ProductoResponse> obtenerConCategoria(@PathVariable Long id) {
        return service.obtenerPorId(id)
                .map(producto -> {
                    ProductoResponse response = new ProductoResponse();
                    response.setProducto(producto);

                    // Llamada al microservicio de categorías
                    Categoria categoria = service.obtenerCategoriaPorId(producto.getCategoriaId());
                    response.setCategoria(categoria);

                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // CREAR PRODUCTO
    @PostMapping
    public Producto crear(@RequestBody Producto producto) {
        return service.guardar(producto);
    }

    // ACTUALIZAR PRODUCTO
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable Long id, @RequestBody Producto datosProducto) {
        return service.obtenerPorId(id)
                .map(productoExistente -> {
                    productoExistente.setNombre(datosProducto.getNombre());
                    productoExistente.setPrecio(datosProducto.getPrecio());
                    productoExistente.setCategoriaId(datosProducto.getCategoriaId());
                    return ResponseEntity.ok(service.guardar(productoExistente));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // ELIMINAR PRODUCTO
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!service.existePorId(id)) {
            return ResponseEntity.notFound().build();
        }
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
