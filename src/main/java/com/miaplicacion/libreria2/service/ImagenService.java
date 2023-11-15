package com.miaplicacion.libreria2.service;

import com.miaplicacion.libreria2.entity.Imagen;
import com.miaplicacion.libreria2.excepciones.MiException;
import com.miaplicacion.libreria2.repository.ImagenRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImagenService {

    @Autowired
    private ImagenRepository imagenRepo;

    public Imagen guardar(MultipartFile archivo) throws MiException {

        if (archivo != null) {

            try {

                Imagen imagen = new Imagen();

                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());

                return imagenRepo.save(imagen);
                
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        }
        return null;
    }

    public Imagen actualizar(MultipartFile archivo, String idImagen) throws MiException {

        if (archivo != null) {

            try {

                Imagen imagen = new Imagen();
                if (idImagen != null) {
                    Optional<Imagen> respuesta = imagenRepo.findById(idImagen);
                    if (respuesta.isPresent()) {
                        imagen = respuesta.get();
                    }
                }
                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());

                return imagenRepo.save(imagen);
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        }
        return null;

    }
}

