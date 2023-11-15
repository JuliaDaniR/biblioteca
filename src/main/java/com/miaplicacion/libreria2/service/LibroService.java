package com.miaplicacion.libreria2.service;

import com.miaplicacion.libreria2.entity.Autor;
import com.miaplicacion.libreria2.entity.Editorial;
import com.miaplicacion.libreria2.entity.Libro;
import com.miaplicacion.libreria2.excepciones.MiException;
import com.miaplicacion.libreria2.repository.IAutorRepository;
import com.miaplicacion.libreria2.repository.IEditorialRepository;
import com.miaplicacion.libreria2.repository.ILibroRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LibroService {

    @Autowired
    private ILibroRepository libroRepo;

    @Autowired
    private IAutorRepository autorRepo;

    @Autowired
    private IEditorialRepository editRepo;

    @Transactional
    public void crearLibro(Long isbn, String titulo, Integer ejemplares,
            String idAutor, String idEditorial) throws MiException {

        validar(isbn, titulo, ejemplares, idAutor, idEditorial);

        Autor autor = autorRepo.findById(idAutor).get();
        Editorial edit = editRepo.findById(idEditorial).get();
        Libro libro = new Libro();

        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setEjemplares(ejemplares);
        libro.setAlta(new Date());
        libro.setAutor(autor);
        libro.setEditorial(edit);

        libroRepo.save(libro);

    }

    public List<Libro> listarLibros() {

        List<Libro> listaLibros = new ArrayList();

        listaLibros = libroRepo.findAll();
        return listaLibros;
    }

    public List<Libro> listarLibroPorAutor(String nombre) {

        List<Libro> lista = new ArrayList();

        lista = libroRepo.buscarPorAutor(nombre);

        return lista;
    }

        @Transactional
    public void modificarLibro(Long isbn, String titulo, Integer ejemplares,
            String idAutor, String idEditorial) throws MiException {

        validar(isbn, titulo, ejemplares, idAutor, idEditorial);

        Optional<Libro> respuesta = libroRepo.findById(isbn);
        Optional<Autor> respAutor = autorRepo.findById(idAutor);
        Optional<Editorial> respEdit = editRepo.findById(idEditorial);
        
        Autor autor = new Autor();
        Editorial edit = new Editorial();
        
        if (respAutor.isPresent()) {
            autor = respAutor.get();
        }
        if (respEdit.isPresent()) {
            edit = respEdit.get();
        }
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();

            libro.setTitulo(titulo);
            libro.setEjemplares(ejemplares);
            libro.setAutor(autor);
            libro.setEditorial(edit);
            libroRepo.save(libro);
        }
    }
    
        @Transactional
    public void eliminarLibro(Long isbn, String titulo, Integer ejemplares,
            String idAutor, String idEditorial) throws MiException {

        validar(isbn, titulo, ejemplares, idAutor, idEditorial);

        Optional<Libro> respuesta = libroRepo.findById(isbn);
        Optional<Autor> respAutor = autorRepo.findById(idAutor);
        Optional<Editorial> respEdit = editRepo.findById(idEditorial);
        
        Autor autor = new Autor();
        Editorial edit = new Editorial();
        
        if (respAutor.isPresent()) {
            autor = respAutor.get();
        }
        if (respEdit.isPresent()) {
            edit = respEdit.get();
        }
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();

            libroRepo.delete(libro);
        }
    }
     public Libro getOne(Long isbn){
    
        return libroRepo.getOne(isbn);
    }
    
    private void validar(Long isbn, String titulo, Integer ejemplares,
            String idAutor, String idEditorial) throws MiException {

        if (isbn == null) {
            throw new MiException("El isbn no puede ser nulo");
        }
        if (titulo.isEmpty() || titulo == null) {
            throw new MiException("El titulo no puede ser nulo ni vacio");
        }
        if (ejemplares == null) {
            throw new MiException("El isbn no puede ser nulo");
        }
        if (idAutor.isEmpty() || idAutor == null) {
            throw new MiException("El id del Autor no puede ser nulo ni vacio");
        }

        if (idEditorial.isEmpty() || idEditorial == null) {
            throw new MiException("La id de la editorial no puede ser nulo ni vacio");
        }

    }
}
