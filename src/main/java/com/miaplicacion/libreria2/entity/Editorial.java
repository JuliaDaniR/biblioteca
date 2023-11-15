package com.miaplicacion.libreria2.entity;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Editorial {
    
    @Id
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name="uuid", strategy="uuid2")
    private String id;
    private String nombre;
    
     @OneToMany(mappedBy="editorial")
    private List<Libro> listaLibros;
     
     
}
