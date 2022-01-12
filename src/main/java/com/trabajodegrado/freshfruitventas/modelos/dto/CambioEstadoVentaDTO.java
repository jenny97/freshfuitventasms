package com.trabajodegrado.freshfruitventas.modelos.dto;

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

public class CambioEstadoVentaDTO {

	private Integer idVenta;
	private Integer idMotivo;
	private Integer idusuariorepartidor;
	private Integer idmetausuario;
}
