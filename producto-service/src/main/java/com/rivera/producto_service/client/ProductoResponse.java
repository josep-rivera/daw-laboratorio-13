package com.rivera.producto_service.client;

import com.rivera.producto_service.dto.Categoria;
import com.rivera.producto_service.model.Producto;
import lombok.Data;

@Data
public class ProductoResponse {
    private Producto producto;
    private Categoria categoria;  // Cambié CategoriaClient por Categoria
}