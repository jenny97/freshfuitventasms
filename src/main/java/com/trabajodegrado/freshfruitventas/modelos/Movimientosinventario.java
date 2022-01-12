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

@Table(name = "movimientosinventario", schema="trabajodegrado")
@Entity
public class Movimientosinventario {
	

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer id;
	    private Integer idinventario;
	    private Integer idusuario;
	    private Date fecha;
	    private Integer idmotivo;
	    private Integer cantidadmovimiento;
	    private String tipomovimiento;

}
