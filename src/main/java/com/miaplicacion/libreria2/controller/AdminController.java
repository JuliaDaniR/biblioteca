
package com.miaplicacion.libreria2.controller;

import com.miaplicacion.libreria2.entity.Usuario;
import com.miaplicacion.libreria2.service.UsuarioService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private UsuarioService usuarioService;
    
   @GetMapping("/dashboard")
   public String panelAdministrativo(){

       return "panel.html";
   }
   
    @GetMapping("/usuarios")
    public String listar(ModelMap modelo) {

        List<Usuario> usuarios = usuarioService.listarUsuarios();

        modelo.addAttribute("usuarios", usuarios);

        return "usuario_list";
    }
   
   @GetMapping("/modificarRol/{id}")
   public String cambiarRol(@PathVariable String id){
   
       usuarioService.cambiarRol(id);
       return "redirect:/admin/usuarios";
   }
}

