package com.miaplicacion.libreria2.service;

import com.miaplicacion.libreria2.entity.Editorial;
import com.miaplicacion.libreria2.entity.Libro;
import com.miaplicacion.libreria2.excepciones.MiException;
import com.miaplicacion.libreria2.repository.IEditorialRepository;
import com.miaplicacion.libreria2.repository.ILibroRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EditorialService {

    @Autowired
    private IEditorialRepository editRepo;

    @Autowired
    private ILibroRepository libroRepo;

    @Transactional
    public void crearEditorial(String nombre) throws MiException {

        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("El nombre no puede ser nulo ni vacio");
        }

        Editorial edit = new Editorial();

        edit.setNombre(nombre);

        editRepo.save(edit);
    }

    public List<Editorial> listarEditoriales() {

        List<Editorial> listaEditoriales = new ArrayList();

        listaEditoriales = editRepo.findAll();

        return listaEditoriales;
    }

    public List<Libro> listaLibros(String id) {

        List<Libro> lista = libroRepo.findByEditorial(id);

        return lista;
    }

    @Transactional
    public void modificarEditorial(String nombre, String id) throws MiException {

        validar(nombre, id);
        Optional<Editorial> respuesta = editRepo.findById(id);

        if (respuesta.isPresent()) {
            Editorial edit = respuesta.get();

            edit.setNombre(nombre);

            editRepo.save(edit);
        }
    }

    @Transactional
    public void eliminarEditorial(String nombre, String id) throws MiException {

        validar(nombre, id);
        Optional<Editorial> resp = editRepo.findById(id);

        if (resp.isPresent()) {
            Editorial edit = resp.get();

            editRepo.delete(edit);
        }
    }

    public Editorial getOne(String id) {

        return editRepo.getReferenceById(id);
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
