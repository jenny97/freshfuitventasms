package com.trabajodegrado.freshfruitventas.modelos;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "metas", schema="trabajodegrado")
@Entity
public class Metas {
	

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer id;
	    private String nombre;
	    private String descripcion;
	    private Integer idproducto;
	    private Integer cantidad;
	    private Date fechainicio;
	    private Date fechafin;
	    private Integer valorbono;
}
