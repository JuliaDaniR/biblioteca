package com.miaplicacion.libreria2.repository;

import com.miaplicacion.libreria2.entity.Editorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEditorialRepository extends JpaRepository<Editorial,String>{
    
}
