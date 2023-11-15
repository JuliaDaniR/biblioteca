package com.miaplicacion.libreria2.service;

import com.miaplicacion.libreria2.entity.Imagen;
import com.miaplicacion.libreria2.entity.Usuario;
import com.miaplicacion.libreria2.enumeraciones.Rol;
import com.miaplicacion.libreria2.excepciones.MiException;
import com.miaplicacion.libreria2.repository.IUsuarioRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;


@Service
public class UsuarioService implements UserDetailsService{
    
    @Autowired
    private IUsuarioRepository usuRepo;
    
    @Autowired
    private ImagenService imagenService;
    
    @Transactional
    public void registrar(MultipartFile archivo,String nombre,String email,
            String password,String password2) throws MiException{
    
        validar(nombre,email,password,password2);
        Usuario usuario= new Usuario();
        
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        usuario.setRol(Rol.USER);
        
        Imagen imagen=imagenService.guardar(archivo);
        
        usuario.setImagen(imagen);
        
        usuRepo.save(usuario);        
    }
    
    public void actualizar(MultipartFile archivo,String idUsuario,String nombre
            ,String email,String password,String password2) throws MiException{
    
        validar(nombre, email,password, password2);
        
        Optional <Usuario> respuesta = usuRepo.findById(idUsuario);
        if(respuesta.isPresent()){
        
            Usuario usuario= respuesta.get();
            usuario.setNombre(nombre);
            usuario.setEmail(email);
            usuario.setPassword(new BCryptPasswordEncoder().encode(password));
            
            usuario.setRol(Rol.USER);
            String idImagen = null;
            
            if(usuario.getImagen() != null){
            
                idImagen = usuario.getImagen().getId();
                
            }
            Imagen imagen = imagenService.actualizar(archivo, idImagen);
            
            usuario.setImagen(imagen);
            usuRepo.save(usuario);
        }
    
    }
    
    private void validar(String nombre,String email,
            String password,String password2) throws MiException{
    
         if (nombre.isEmpty() || nombre == null) {
            throw new MiException("El nombre no puede ser nulo ni vacio");
        }
          if (email.isEmpty() || email == null) {
            throw new MiException("El email no puede ser nulo ni vacio");
        }
           if (password.isEmpty() || password == null||password.length()<=5) {
            throw new MiException("El password no puede ser nulo ni vacio y"
                    + " debe tener mas de cinco dígitos");
        }
            if (!password.equals(password2)) {
            throw new MiException("Las contraseñas deben ser iguales");
        }
        
    }
         public Usuario getOne(String id){
    
        return usuRepo.getReferenceById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
      
        Usuario usuario = usuRepo.buscarPorEmail(email);
        
        if(usuario != null){
            
            List<GrantedAuthority> permisos = new ArrayList();
            
            GrantedAuthority p= new SimpleGrantedAuthority("ROLE_"+usuario.getRol().toString()); 
            
            permisos.add(p);
            
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            
            HttpSession sesion= attr.getRequest().getSession(true);
           
            sesion.setAttribute("usuariosession", usuario);
            
            return new User(usuario.getEmail(),usuario.getPassword(),permisos);        
        
        }else{
            return null;
        }
    }

       @Transactional(readOnly=true)
    public List<Usuario> listarUsuarios() {

        List<Usuario> usuarios = new ArrayList();


        usuarios = usuRepo.findAll();

        return usuarios;
    }

    @Transactional
    public void cambiarRol(String id) {
        Optional<Usuario> respuesta = usuRepo.findById(id);
        
        if(respuesta.isPresent()){
        
            Usuario usuario= respuesta.get();
            
            if(usuario.getRol().equals(Rol.USER)){
            
                usuario.setRol(Rol.ADMIN);
            }else if(usuario.getRol().equals(Rol.ADMIN)){
            
                usuario.setRol(Rol.USER);
            }
             usuRepo.save(usuario);
        }    
    }
}
