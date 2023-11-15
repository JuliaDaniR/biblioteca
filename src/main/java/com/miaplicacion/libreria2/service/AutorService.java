package com.miaplicacion.libreria2.service;

import com.miaplicacion.libreria2.entity.Autor;
import com.miaplicacion.libreria2.entity.Libro;
import com.miaplicacion.libreria2.excepciones.MiException;
import com.miaplicacion.libreria2.repository.IAutorRepository;
import com.miaplicacion.libreria2.repository.ILibroRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AutorService {

    @Autowired
    private IAutorRepository autorRepo;
    
    @Autowired
    private ILibroRepository libroRepo;

    @Transactional
    public void crearAutor(String nombre) throws MiException {

        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("El nombre no puede ser nulo ni vacio");
        }
        
        Autor autor = new Autor();

        autor.setNombre(nombre);

        autorRepo.save(autor);
    }
    
    
    public List<Autor> listarAutores() {

        List<Autor> listaAutores = new ArrayList();

        listaAutores = autorRepo.findAll();

        return listaAutores;

    }
    
      public List<Libro> listaLibros(String id) {
       
       List<Libro> lista=libroRepo.findByAutor(id);
       
    return lista;
}
  
    @Transactional
    public void modificarAutor(String nombre, String id) throws MiException {

        validar(nombre, id);

        Optional<Autor> respuesta = autorRepo.findById(id);

        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();

            autor.setNombre(nombre);

            autorRepo.save(autor);
        }

    }
     @Transactional
    public void eliminarAutor(String nombre, String id) throws MiException{
    
        validar(nombre, id);
        Optional<Autor> resp = autorRepo.findById(id);
        
        if(resp.isPresent()){
          Autor edit = resp.get();
          
          autorRepo.delete(edit);
        }
    }

    public Autor getOne(String id){
    
        return autorRepo.getReferenceById(id);
    }
    
        
 
    private void validar(String nombre, String id) throws MiException {

        if (id == null) {
            throw new MiException("El id no puede ser nulo");
        }
        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("El nombre no puede ser nulo ni vacio");
        }
    }
}
