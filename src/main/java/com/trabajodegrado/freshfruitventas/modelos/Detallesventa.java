package com.trabajodegrado.freshfruitventas.modelos;

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

@Table(name = "detallesventa", schema="trabajodegrado")
@Entity
public class Detallesventa {
	

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer id;
	    private Integer idventa;
	    private Integer idproducto;
	    private Integer valorunitario;
	    private Integer valortotal;
	    private Integer cantidad;

}