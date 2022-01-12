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

	@Table(name = "usuarios", schema="trabajodegrado")
	@Entity
	public class Usuarios {
		
		    @Id
		    @GeneratedValue(strategy = GenerationType.IDENTITY)
		    private Integer id;
		    private Integer idestado;
		    private Integer idrol;
		    private String nombreusuario;
		    private String nombre;
		    private String clave;
		    private String correoelectronico;
		    private String celular;
		    private String direccion;
}
