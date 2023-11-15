package com.miaplicacion.libreria2.repository;

import com.miaplicacion.libreria2.entity.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAutorRepository extends JpaRepository<Autor,String>{

}
