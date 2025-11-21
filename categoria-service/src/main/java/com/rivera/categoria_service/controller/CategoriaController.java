package com.rivera.categoria_service.controller;

import com.rivera.categoria_service.model.Categoria;
import com.rivera.categoria_service.repository.CategoriaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/categorias")
public class CategoriaController {

    private final CategoriaRepository repository;

    public CategoriaController(CategoriaRepository repository) {
        this.repository = repository;
    }

    // LISTAR TODAS LAS CATEGORÍAS
    @GetMapping
    public List<Categoria> listar() {
        return repository.findAll();
    }

    // BUSCAR CATEGORÍA POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> obtenerPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // CREAR CATEGORÍA
    @PostMapping
    public Categoria crear(@RequestBody Categoria categoria) {
        return repository.save(categoria);
    }

    // ACTUALIZAR CATEGORÍA
    @PutMapping("/{id}")
    public ResponseEntity<Categoria> actualizar(@PathVariable Long id, @RequestBody Categoria datosCategoria) {
        return repository.findById(id)
                .map(categoriaExistente -> {
                    categoriaExistente.setNombre(datosCategoria.getNombre());
                    return ResponseEntity.ok(repository.save(categoriaExistente));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // ELIMINAR CATEGORÍA
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}