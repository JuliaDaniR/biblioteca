package com.miaplicacion.libreria2.controller;

import com.miaplicacion.libreria2.entity.Autor;
import com.miaplicacion.libreria2.entity.Libro;
import com.miaplicacion.libreria2.excepciones.MiException;
import com.miaplicacion.libreria2.service.AutorService;
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
@RequestMapping("/autor")
public class AutorController {

    @Autowired
    private AutorService autorService;

    @Autowired
    private LibroService libroService;

    @GetMapping("/registrar")
    public String registrar() {

        return "autor_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, ModelMap modelo) {

        try {
            autorService.crearAutor(nombre);
            modelo.put("exito", "El autor fue cargado correctamente");
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "autor_form.html";
        }
        return "index.html";

    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {

        List<Autor> autores = autorService.listarAutores();

        modelo.addAttribute("autores", autores);

        return "autor_list.html";
    }

    @GetMapping("/listado/{id}")
    public String verAutor(@PathVariable String id, ModelMap modelo) {

        Autor autor = autorService.getOne(id);
        List<Libro> libros = autorService.listaLibros(id);
        modelo.addAttribute("autor", autor);
        modelo.addAttribute("libros", libros);

        return "autor_listado.html";
    }
          @GetMapping("/biografia/{id}")
    public String biografia(@PathVariable String id, ModelMap modelo) {
        
        Autor autor = autorService.getOne(id);
        modelo.addAttribute("autor", autor);
        
            return "autor_biografia.html";
    }

    @GetMapping("/modificar")
    public String modificar(ModelMap modelo) {
        List<Autor> autores = autorService.listarAutores();

        modelo.addAttribute("autores", autores);
        return "autor_list_modificar.html";
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) {

        modelo.put("autor", autorService.getOne(id));

        return "autor_modificar.html";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, String nombre, ModelMap modelo) {

        try {
            autorService.modificarAutor(nombre, id);
            modelo.put("exito", "El autor se modificó correctamente");
            return "index.html";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "autor_modificar.html";
        }
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarAutor(@PathVariable String id, ModelMap modelo) {

        modelo.put("autor", autorService.getOne(id));

        return "autor_eliminar.html";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id, String nombre, ModelMap modelo) {

        try {
            autorService.eliminarAutor(nombre, id);
            modelo.put("exito", "El autor se eliminó correctamente");
            return "index.html";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "autor_eliminar.html";
        }
    }
}
