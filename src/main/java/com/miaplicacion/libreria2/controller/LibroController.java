package com.miaplicacion.libreria2.controller;

import com.miaplicacion.libreria2.entity.Autor;
import com.miaplicacion.libreria2.entity.Editorial;
import com.miaplicacion.libreria2.entity.Libro;
import com.miaplicacion.libreria2.excepciones.MiException;
import com.miaplicacion.libreria2.service.AutorService;
import com.miaplicacion.libreria2.service.EditorialService;
import com.miaplicacion.libreria2.service.LibroService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/libro")
public class LibroController {

    @Autowired
    private AutorService autorService;

    @Autowired
    private EditorialService editService;

    @Autowired
    private LibroService libroService;

    @GetMapping("/registrar")
    public String registrar(ModelMap modelo) {

        List<Autor> autores = autorService.listarAutores();
        List<Editorial> editoriales = editService.listarEditoriales();

        modelo.addAttribute("autores", autores);
        modelo.addAttribute("editoriales", editoriales);
        return "libro_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam(required = false) Long isbn,
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) Integer ejemplares,
            @RequestParam(required = false) String idAutor,
            @RequestParam(required = false) String idEditorial, ModelMap modelo) {

        try {
            libroService.crearLibro(isbn, titulo, ejemplares, idAutor, idEditorial);

            modelo.put("exito", "El libro fue cargado exitosamente");

        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());
            List<Autor> autores = autorService.listarAutores();
            List<Editorial> editoriales = editService.listarEditoriales();

            modelo.addAttribute("autores", autores);
            modelo.addAttribute("editoriales", editoriales);
            return "libro_form.html";
        }

        return "index.html";
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {

        List<Libro> libros = libroService.listarLibros();

        modelo.addAttribute("libros", libros);

        return "libro_list.html";
    }
    
      @GetMapping("/descripcion/{isbn}")
    public String descripcion(@PathVariable Long isbn, ModelMap modelo) {
        
        modelo.put("libro", libroService.getOne(isbn));
        
            return "libro_descripcion.html";
    }

    @GetMapping("/modificar")
    public String modificar(ModelMap modelo) {
        List<Libro> libros = libroService.listarLibros();

        modelo.addAttribute("libros", libros);
        return "libro_list_modificar.html";
    }

    @GetMapping("/modificar/{isbn}")
    public String modificar(@PathVariable Long isbn, ModelMap modelo) {

        modelo.put("libro", libroService.getOne(isbn));

        List<Autor> autores = autorService.listarAutores();
        List<Editorial> editoriales = editService.listarEditoriales();

        modelo.addAttribute("autores", autores);
        modelo.addAttribute("editoriales", editoriales);

        return "libro_modificar.html";
    }

    @PostMapping("/modificar/{isbn}")
    public String modificar(@PathVariable Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial, ModelMap modelo) {
        try {
            List<Autor> autores = autorService.listarAutores();
            List<Editorial> editoriales = editService.listarEditoriales();

            modelo.addAttribute("autores", autores);
            modelo.addAttribute("editoriales", editoriales);

            libroService.modificarLibro(isbn, titulo, ejemplares, idAutor, idEditorial);
            modelo.put("exito", "El libro se modificó correctamente");
            return "index.html";
        } catch (MiException ex) {
            List<Autor> autores = autorService.listarAutores();
            List<Editorial> editoriales = editService.listarEditoriales();

            modelo.put("error", ex.getMessage());

            modelo.addAttribute("autores", autores);
            modelo.addAttribute("editoriales", editoriales);

            return "libro_modificar.html";
        }

    }

    @GetMapping("/eliminar/{isbn}")
    public String eliminar(@PathVariable Long isbn, ModelMap modelo) {

        modelo.put("libro", libroService.getOne(isbn));

        List<Autor> autores = autorService.listarAutores();
        List<Editorial> editoriales = editService.listarEditoriales();

        modelo.addAttribute("autores", autores);
        modelo.addAttribute("editoriales", editoriales);

        return "libro_eliminar.html";
    }

    @PostMapping("/eliminar/{isbn}")
    public String eliminar(@PathVariable Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial, ModelMap modelo) {
        try {
            List<Autor> autores = autorService.listarAutores();
            List<Editorial> editoriales = editService.listarEditoriales();

            modelo.addAttribute("autores", autores);
            modelo.addAttribute("editoriales", editoriales);

            libroService.eliminarLibro(isbn, titulo, ejemplares, idAutor, idEditorial);
            modelo.put("exito", "El libro se modificó correctamente");
            return "index.html";
        } catch (MiException ex) {
            List<Autor> autores = autorService.listarAutores();
            List<Editorial> editoriales = editService.listarEditoriales();

            modelo.put("error", ex.getMessage());

            modelo.addAttribute("autores", autores);
            modelo.addAttribute("editoriales", editoriales);

            return "libro_eliminar.html";
        }

    }
}
