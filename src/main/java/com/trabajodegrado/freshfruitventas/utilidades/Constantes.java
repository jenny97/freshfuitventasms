package com.trabajodegrado.freshfruitventas.utilidades;

public class Constantes {

	public static class TIPOS_ESTADO {
		public static final String VENTAS = "VENTAS";
		public static final String MOVIMIENTO = "MOVIMIENTO";
	}

	public static class ESTADOS_VENTAS {
		public static final String CREADO = "CREADO";
		public static final String EN_PROCESO = "EN_PROCESO";
		public static final String RECHAZADO = "RECHAZADO";
		public static final String DESPACHADO = "DESPACHADO";
		public static final String ENTREGADO = "ENTREGADO";
		public static final String DEVUELTO = "DEVUELTO";

	}
	
	public static class TIPOS_MOVIMIENTO_INVENTARIO {
		public static final String CREACION = "CREACION";
		public static final String ADICION = "ADICION";
		public static final String DISMINUCION = "DISMINUCION";

	}
}

