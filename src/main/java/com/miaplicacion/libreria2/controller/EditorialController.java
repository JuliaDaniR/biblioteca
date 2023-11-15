package com.miaplicacion.libreria2.controller;

import com.miaplicacion.libreria2.entity.Autor;
import com.miaplicacion.libreria2.entity.Editorial;
import com.miaplicacion.libreria2.entity.Libro;
import com.miaplicacion.libreria2.excepciones.MiException;
import com.miaplicacion.libreria2.service.EditorialService;
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
@RequestMapping("/editorial")
public class EditorialController {
   
    @Autowired
    private EditorialService editorialService;
    
    @GetMapping("/registrar")
    public String Registrar(){
    
        return "editorial_form.html";
    }
    
    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, ModelMap modelo) {

        try {
            editorialService.crearEditorial(nombre);
            
            modelo.put("exito", "La editorial fue cargada correctamente");
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "editorial_form.html";
        }
        return "editorial_form.html";

    }
        @GetMapping("/lista")
    public String listar(ModelMap modelo){
    
        List<Editorial> editoriales= editorialService.listarEditoriales();
        
        modelo.addAttribute("editoriales", editoriales);
        
        return "editorial_list.html";
    }
    
      @GetMapping("/listado/{id}")
    public String verEditorial(@PathVariable String id, ModelMap modelo) {

        Editorial editorial = editorialService.getOne(id);
        List<Libro> libros = editorialService.listaLibros(id);
        modelo.addAttribute("editorial", editorial);
        modelo.addAttribute("libros", libros);

        return "editorial_listado.html";
    }
    

    @GetMapping("/modificar")
    public String modificar(ModelMap modelo) {
        List<Editorial> editoriales = editorialService.listarEditoriales();

        modelo.addAttribute("editoriales", editoriales);
        return "editorial_list_modificar.html";
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) {

        modelo.put("editorial", editorialService.getOne(id));

        return "editorial_modificar.html";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, String nombre, ModelMap modelo) {

        try {
            editorialService.modificarEditorial(nombre, id);
            modelo.put("exito", "La editorial se modificó correctamente");
            return "index.html";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "editorial_modificar.html";
        }
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarEditorial(@PathVariable String id, ModelMap modelo) {

        modelo.put("editorial", editorialService.getOne(id));

        return "editorial_eliminar.html";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id, String nombre, ModelMap modelo) {

        try {
            editorialService.eliminarEditorial(nombre, id);
            modelo.put("exito", "La editorial se eliminó correctamente");
            return "index.html";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "editorial_eliminar.html";
        }
    }
}

