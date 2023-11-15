package com.miaplicacion.libreria2.repository;

import com.miaplicacion.libreria2.entity.Libro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ILibroRepository extends JpaRepository<Libro, Long> {

    @Query("SELECT l FROM Libro l WHERE l.titulo = :titulo ")
    public Libro buscarPorTitulo(@Param("titulo") String titulo);

    @Query("SELECT l FROM Libro l WHERE l.autor.nombre = :nombre")
    public List<Libro> buscarPorAutor(@Param("nombre") String nombre);

    @Query("SELECT l FROM Libro l WHERE l.autor.id = :id")
    public List<Libro> findByAutor(@Param("id") String id);

     @Query("SELECT l FROM Libro l WHERE l.editorial.id = :id")
    public List<Libro> findByEditorial(@Param("id") String id);
}
