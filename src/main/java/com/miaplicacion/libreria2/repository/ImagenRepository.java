package com.miaplicacion.libreria2.repository;

import com.miaplicacion.libreria2.entity.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagenRepository extends JpaRepository<Imagen,String>{
    
}
