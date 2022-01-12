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

public class ProductosDTO {

	private Integer idProducto;
	private Integer cantidad;
}
