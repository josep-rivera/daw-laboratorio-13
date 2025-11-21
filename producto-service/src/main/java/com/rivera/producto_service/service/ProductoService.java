package com.rivera.producto_service.service;

import com.rivera.producto_service.client.CategoriaClient;
import com.rivera.producto_service.dto.Categoria;
import com.rivera.producto_service.model.Producto;
import com.rivera.producto_service.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    private final ProductoRepository repository;
    private final CategoriaClient categoriaClient;

    public ProductoService(ProductoRepository repository, CategoriaClient categoriaClient) {
        this.repository = repository;
        this.categoriaClient = categoriaClient;
    }

    public List<Producto> listarTodos() {
        return repository.findAll();
    }

    public Optional<Producto> obtenerPorId(Long id) {
        return repository.findById(id);
    }

    public Categoria obtenerCategoriaPorId(Long categoriaId) {
        return categoriaClient.obtenerCategoria(categoriaId);
    }

    public Producto guardar(Producto producto) {
        return repository.save(producto);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    public boolean existePorId(Long id) {
        return repository.existsById(id);
    }
}