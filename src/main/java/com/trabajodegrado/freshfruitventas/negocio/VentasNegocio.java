package com.trabajodegrado.freshfruitventas.negocio;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.trabajodegrado.freshfruitventas.excepciones.ConflictException;
import com.trabajodegrado.freshfruitventas.excepciones.DatosInvalidosExcepcion;
import com.trabajodegrado.freshfruitventas.modelos.Detallesventa;
import com.trabajodegrado.freshfruitventas.modelos.Estados;
import com.trabajodegrado.freshfruitventas.modelos.Inventarios;
import com.trabajodegrado.freshfruitventas.modelos.Metas;
import com.trabajodegrado.freshfruitventas.modelos.Metasusuario;
import com.trabajodegrado.freshfruitventas.modelos.Motivos;
import com.trabajodegrado.freshfruitventas.modelos.Movimientosinventario;
import com.trabajodegrado.freshfruitventas.modelos.Movimientosventa;
import com.trabajodegrado.freshfruitventas.modelos.Plantillas;
import com.trabajodegrado.freshfruitventas.modelos.Productos;
import com.trabajodegrado.freshfruitventas.modelos.Usuarios;
import com.trabajodegrado.freshfruitventas.modelos.Ventas;
import com.trabajodegrado.freshfruitventas.modelos.dto.CambioEstadoVentaDTO;
import com.trabajodegrado.freshfruitventas.modelos.dto.ProductosDTO;
import com.trabajodegrado.freshfruitventas.modelos.dto.VentasDTO;
import com.trabajodegrado.freshfruitventas.repositorio.DetallesventaRepositorio;
import com.trabajodegrado.freshfruitventas.repositorio.EstadosRepositorio;
import com.trabajodegrado.freshfruitventas.repositorio.InventariosRepositorio;
import com.trabajodegrado.freshfruitventas.repositorio.MetasRepositorio;
import com.trabajodegrado.freshfruitventas.repositorio.MetasusuarioRepositorio;
import com.trabajodegrado.freshfruitventas.repositorio.MotivosRepositorio;
import com.trabajodegrado.freshfruitventas.repositorio.MovimientosinventarioRepositorio;
import com.trabajodegrado.freshfruitventas.repositorio.MovimientosventaRepositorio;
import com.trabajodegrado.freshfruitventas.repositorio.PlantillasRepositorio;
import com.trabajodegrado.freshfruitventas.repositorio.ProductosRepositorio;
import com.trabajodegrado.freshfruitventas.repositorio.UsuariosRepositorio;
import com.trabajodegrado.freshfruitventas.repositorio.VentasRepositorio;
import com.trabajodegrado.freshfruitventas.utilidades.Constantes;

@Service
public class VentasNegocio {

	@Autowired
	private VentasRepositorio ventasRepositorio;
	
	@Autowired
	private DetallesventaRepositorio detallesventaRepositorio;
	
	@Autowired
	private MovimientosventaRepositorio movimientosventaRepositorio;
	
	@Autowired
	private EstadosRepositorio estadosRepositorio;
	
	@Autowired
	private ProductosRepositorio productosRepositorio;
	
	@Autowired
	private InventariosRepositorio inventarioRepositorio;
	
	@Autowired
	private MovimientosinventarioRepositorio movimientosinventarioRepositorio;
	
	@Autowired
	private MetasRepositorio metasRepositorio;
	
	@Autowired
	private MetasusuarioRepositorio metasusuarioRepositorio;
	
	@Autowired
	private UsuariosRepositorio usuariosRepositorio;
	
	@Autowired
	private PlantillasRepositorio plantillasRepositorio;
	
	@Autowired
	private MotivosRepositorio motivosRepositorio;
	
	@Autowired
	private  JavaMailSender sender;
	
	
	
	
	public List<Ventas> obtenerListaVentas() {
		return ventasRepositorio.findAll();	    	
	}
	
	public List<Ventas> obtenerListaVentasPorUsuario(Integer id) {
		return ventasRepositorio.findByIdusuario(id);	    	
	}
	
	public List<Ventas> obtenerListaVentasPorEstado(Integer id) {
		return ventasRepositorio.findByIdestado(id);	    	
	}
	
	public List<Ventas> obtenerHistorialVentasPorRepartidor(Integer id) {
		Estados estadoDespachado = consultarEstados(Constantes.ESTADOS_VENTAS.DESPACHADO);
		return ventasRepositorio.findByIdusuariorepartidorAndIdestadoNot(id, estadoDespachado.getId());	    	
	}
	
	public List<Ventas> obtenerVentasPorRepartidor(Integer id) {
		Estados estadoDespachado = consultarEstados(Constantes.ESTADOS_VENTAS.DESPACHADO);
		return ventasRepositorio.findByIdusuariorepartidorAndIdestado(id, estadoDespachado.getId());	    	
	}
	
	public Ventas obtenerVenta(Integer id) {
		Optional<Ventas> venta =  ventasRepositorio.findById(id);	   
		
		if(venta.isPresent()) {
			return venta.get();
		}else {
			throw new ConflictException("Inventario no encontrado");
		}
	}
	
	public List<Movimientosventa> obtenerListaMovimientos() {
		return movimientosventaRepositorio.findAll();	    	
	}
	
	public List<Movimientosventa> obtenerMovimientosVenta(Integer idventa) {
		return movimientosventaRepositorio.findByIdventa(idventa);	    	
	}
	
	public List<Detallesventa> obtenerDetallesVenta(Integer idventa) {
		return detallesventaRepositorio.findByIdventa(idventa);	    	
	}
	
	
	
	@Transactional
	public String crearVenta(VentasDTO venta) {
		
		if(venta.getListaProductos().isEmpty()) {
			throw new DatosInvalidosExcepcion("La venta debe contener productos.");
		}
		
		List<Integer> listaIds = validarListaProductos(venta.getListaProductos());
		if(listaIds == null) {
			throw new DatosInvalidosExcepcion("Error en los datos. Verifique los productos seleccionados y que las cantidades si sean válidas.");
		}
		
		Estados estadoCreado = consultarEstados(Constantes.ESTADOS_VENTAS.CREADO);
		
		Integer valorTotalVenta = 0;
		
		List<Productos> productos = productosRepositorio.findAllById(listaIds);
		
		List<Detallesventa> listaDetalles = new ArrayList<>();
		
		for (ProductosDTO prod : venta.getListaProductos()) {
			Detallesventa detalle = new Detallesventa();
			
			Optional<Productos> producto = productos.stream()
					.filter(x -> x.getId() == prod.getIdProducto())
					.findAny();
			
			detalle.setCantidad(prod.getCantidad());
			detalle.setIdproducto(prod.getIdProducto());
			detalle.setValorunitario(producto.get().getPrecio());
			detalle.setValortotal(producto.get().getPrecio() * prod.getCantidad());
			valorTotalVenta += detalle.getValortotal();
			listaDetalles.add(detalle);
			
		}
		 
		
		Ventas nuevaVenta = new Ventas();
		nuevaVenta.setFecha(new Date());
		nuevaVenta.setIdusuario(1); //Tomarlo del token
		nuevaVenta.setIdestado(estadoCreado.getId());
		nuevaVenta.setValortotal(valorTotalVenta);
		
		ventasRepositorio.save(nuevaVenta);
		
		listaDetalles.forEach(x -> x.setIdventa(nuevaVenta.getId()));
		
		detallesventaRepositorio.saveAll(listaDetalles);
		
		insertarMovimiento(estadoCreado.getId(), nuevaVenta.getId(), null, null,null);
		actualizarInventario(venta);
		revisarMetas(venta);
		
		//Enviar correo
		armarCorreo(Constantes.ESTADOS_VENTAS.CREADO, nuevaVenta, null);		
		
		return "La compra se ha realizado correctamente. Su número de pedido es : " + nuevaVenta.getId();
	}
	
	
	@Transactional
	public String marcarVentaEnProceso(CambioEstadoVentaDTO cambioEstadoVenta) {
		
		cambiarEstadoVenta(cambioEstadoVenta,
				Constantes.ESTADOS_VENTAS.CREADO, Constantes.ESTADOS_VENTAS.EN_PROCESO);
		
		armarCorreo(Constantes.ESTADOS_VENTAS.EN_PROCESO, obtenerVenta(cambioEstadoVenta.getIdVenta()), null );	
		
		return "El pedido ha pasado a estado EN PROCESO correctamente";
	}
	
	@Transactional
	public String marcarVentaRechazada(CambioEstadoVentaDTO cambioEstadoVenta) {
		
		if(cambioEstadoVenta.getIdMotivo() == null || cambioEstadoVenta.getIdMotivo().equals(0)) {
			throw new DatosInvalidosExcepcion("Para generar un rechazo debe seleccionar un motivo.");
		}
		
		cambiarEstadoVenta(cambioEstadoVenta,  
				Constantes.ESTADOS_VENTAS.CREADO, Constantes.ESTADOS_VENTAS.RECHAZADO);
		
		reintegrarInventario(cambioEstadoVenta);
		armarCorreo(Constantes.ESTADOS_VENTAS.RECHAZADO, obtenerVenta(cambioEstadoVenta.getIdVenta()), cambioEstadoVenta.getIdMotivo() );
		
		return "El pedido ha sido rechazado correctamente";
	}
	
	@Transactional
	public String marcarVentaDespachada(CambioEstadoVentaDTO cambioEstadoVenta) {
		
		if(cambioEstadoVenta.getIdusuariorepartidor() == null || cambioEstadoVenta.getIdusuariorepartidor().equals(0)) {
			throw new DatosInvalidosExcepcion("Para generar un despacho debe asignar un repartidor.");
		}
		
		cambiarEstadoVenta(cambioEstadoVenta, 
				Constantes.ESTADOS_VENTAS.EN_PROCESO, Constantes.ESTADOS_VENTAS.DESPACHADO);
		
		armarCorreo(Constantes.ESTADOS_VENTAS.DESPACHADO, obtenerVenta(cambioEstadoVenta.getIdVenta()), null );
		
		return "El pedido ha pasado a DESPACHADO correctamente.";
	}
	
	@Transactional
	public String marcarVentaEntregada(CambioEstadoVentaDTO cambioEstadoVenta) {
		
		if(cambioEstadoVenta.getIdmetausuario() != null && !cambioEstadoVenta.getIdmetausuario().equals(0)) {
			
			Optional<Metasusuario> metaUsuario = metasusuarioRepositorio.findById(cambioEstadoVenta.getIdmetausuario());
			if(!metaUsuario.isPresent()) {
				throw new ConflictException("No se encontró la meta a redmir.");
			}
			
			if(!metaUsuario.get().isIsalcanzada()) {
				throw new ConflictException("La meta aún no ha sido alcanzada.");
			}
			
			if(metaUsuario.get().isIsredimido()) {
				throw new ConflictException("La meta ya ha sido redimida anteriormente.");
			}
			
			Optional<Metas> meta = metasRepositorio.findById(metaUsuario.get().getId());
			Integer valorBono = meta.get().getValorbono();
			
			Ventas venta = obtenerVenta(cambioEstadoVenta.getIdVenta());
			
			if(venta.getValortotal()<=valorBono) {
				throw new ConflictException("El valor a redimir no puede ser mayor o igual al valor total a pagar del pedido.");
			}
			metaUsuario.get().setIsredimido(true);
			metasusuarioRepositorio.save(metaUsuario.get());
		}
		cambiarEstadoVenta(cambioEstadoVenta, 
				Constantes.ESTADOS_VENTAS.DESPACHADO, Constantes.ESTADOS_VENTAS.ENTREGADO);
		
		armarCorreo(Constantes.ESTADOS_VENTAS.ENTREGADO, obtenerVenta(cambioEstadoVenta.getIdVenta()), null );
		
		return "El pedido a sido entregado correctamente.";
	}
	
	@Transactional
	public String marcarVentaDevuelta(CambioEstadoVentaDTO cambioEstadoVenta) {
		
		if(cambioEstadoVenta.getIdMotivo() == null || cambioEstadoVenta.getIdMotivo().equals(0)) {
			throw new DatosInvalidosExcepcion("Para generar una devolución debe seleccionar un motivo.");
		}
		
		cambiarEstadoVenta(cambioEstadoVenta, 
				Constantes.ESTADOS_VENTAS.DESPACHADO, Constantes.ESTADOS_VENTAS.DEVUELTO);
		
		reintegrarInventario(cambioEstadoVenta);
		
		armarCorreo(Constantes.ESTADOS_VENTAS.DEVUELTO, obtenerVenta(cambioEstadoVenta.getIdVenta()),cambioEstadoVenta.getIdMotivo() );
		
		return "El pedido a sido devuelto.";
		
	}
	
	
	private void cambiarEstadoVenta(CambioEstadoVentaDTO cambioEstadoVenta, String codigoEstadoAnterior, String codigoEstadoNuevo){
		
		Ventas venta = obtenerVenta(cambioEstadoVenta.getIdVenta());
		Estados estadoAnterior = consultarEstados(codigoEstadoAnterior);
		Estados estadoNuevo = consultarEstados(codigoEstadoNuevo);
		
		if(!venta.getIdestado().equals(estadoAnterior.getId())) {
			throw new ConflictException("La orden debe estar en estado " + codigoEstadoAnterior);
		}
		
		venta.setIdestado(estadoNuevo.getId());
		if(cambioEstadoVenta.getIdusuariorepartidor() != null)
			venta.setIdusuariorepartidor(cambioEstadoVenta.getIdusuariorepartidor());
		
		ventasRepositorio.save(venta);
		
		insertarMovimiento(estadoNuevo.getId(), cambioEstadoVenta.getIdVenta(), cambioEstadoVenta.getIdMotivo(), cambioEstadoVenta.getIdmetausuario(), cambioEstadoVenta.getIdusuariorepartidor());
		
	}
	
	
	
	private void insertarMovimiento(Integer idEstado, Integer idVenta, Integer idMotivo, Integer idMetaUsuario, Integer idusuariorepartidor){
		
		Movimientosventa movimiento = new Movimientosventa();
		movimiento.setFecha(new Date());
		movimiento.setIdventa(idVenta);
		movimiento.setIdusuario(1); //Coger del token
		movimiento.setIdestado(idEstado);
		movimiento.setIdmotivo(idMotivo);
		movimiento.setIdmetausuario(idMetaUsuario);
		movimiento.setIdusuariorepartidor(idusuariorepartidor);
		
		movimientosventaRepositorio.save(movimiento);
	}
	
	private void actualizarInventario(VentasDTO venta ){
		//Voy a asumir que si encuentra los inventarios porque se validó antes para dejarlos seleccionar en el carrito.
		
		List<Movimientosinventario> listaMovimientos = new ArrayList<>();
		
		for (ProductosDTO prod : venta.getListaProductos()) {
			
			Optional<Inventarios> inventarioExistente = inventarioRepositorio.findByIdproducto(prod.getIdProducto());
			if(inventarioExistente.get().getExistencias()< prod.getCantidad()) {
				throw new ConflictException("No hay inventario disponible para el producto " + prod.getIdProducto());
			}
			inventarioExistente.get().setExistencias(inventarioExistente.get().getExistencias()-prod.getCantidad());
			inventarioRepositorio.save(inventarioExistente.get());
			
			listaMovimientos.add(
				Movimientosinventario.builder()
					.idinventario(inventarioExistente.get().getId())
					.cantidadmovimiento(prod.getCantidad())
					.fecha(new Date())
					.idusuario(1) //Coger el del token, que debe ser el usuario que está comprando
					.tipomovimiento(Constantes.TIPOS_MOVIMIENTO_INVENTARIO.DISMINUCION)
					.build()
				);
		}
		
		movimientosinventarioRepositorio.saveAll(listaMovimientos);
	}
	
	public void revisarMetas(VentasDTO venta) {
		
		for (ProductosDTO prod : venta.getListaProductos()) {
			
			Optional<Metas> meta = metasRepositorio
					.findByIdproductoAndFechainicioLessThanEqualAndFechafinGreaterThanEqual(prod.getIdProducto(), new Date(), new Date());
			//Si existe una meta vigente para el producto.
			if(meta.isPresent()) {
				Optional<Metasusuario> metaUsuario = metasusuarioRepositorio.findByIdusuarioAndIdmeta(1, meta.get().getId()); //tomar el usuario del token.
				if(metaUsuario.isPresent()) {
					
					if(!metaUsuario.get().isIsalcanzada()) {
						//Si el cliente ya empezó a cumplir la meta se le actualiza.
						metaUsuario.get().setCantidad(metaUsuario.get().getCantidad() + prod.getCantidad());
						if(metaUsuario.get().getCantidad() >= meta.get().getCantidad()) {
							metaUsuario.get().setIsalcanzada(true);
						}
						metasusuarioRepositorio.save(metaUsuario.get());
					}		
				}else {
					metasusuarioRepositorio.save(
						Metasusuario.builder()
							.idusuario(1) //Cogerlo del token
							.idmeta(meta.get().getId())
							.cantidad(prod.getCantidad())
							.isactivo(true)
							.isredimido(false)
							.isalcanzada(prod.getCantidad() >= meta.get().getCantidad())
							.build()
					);
				}
			}
		}
	}
	
private void reintegrarInventario(CambioEstadoVentaDTO cambioEstadoVenta) {
		
		//Necesito la lista de detalles para devolverlos al inventario
		
				List<Detallesventa> listaDetallesVenta = detallesventaRepositorio.findByIdventa(cambioEstadoVenta.getIdVenta());
				
				List<Inventarios> listaInventariosActualizar = new ArrayList<>();
				List<Movimientosinventario> listaMovimientosInventario = new ArrayList<>();
				
				for (Detallesventa detalleventa : listaDetallesVenta) {
					
					Optional<Inventarios> inventarioExistente = inventarioRepositorio.findByIdproducto(detalleventa.getIdproducto());
					inventarioExistente.get().setExistencias(inventarioExistente.get().getExistencias() + detalleventa.getCantidad());
					listaInventariosActualizar.add(inventarioExistente.get());
					//Creo el movimiento
					listaMovimientosInventario.add(
						Movimientosinventario.builder()
						.idinventario(inventarioExistente.get().getId())
						.cantidadmovimiento(detalleventa.getCantidad())
						.fecha(new Date())
						.idusuario(1) //Coger el del token, que debe ser el usuario que está comprando
						.tipomovimiento(Constantes.TIPOS_MOVIMIENTO_INVENTARIO.ADICION)
						.idmotivo(cambioEstadoVenta.getIdMotivo())
						.build()
						);
				}
				
				inventarioRepositorio.saveAll(listaInventariosActualizar);
				movimientosinventarioRepositorio.saveAll(listaMovimientosInventario);
	}
	
	private Estados consultarEstados(String codigoEstado) {
		Optional<Estados> estado = estadosRepositorio.findByCodigo(codigoEstado);
		if(!estado.isPresent()) {
			throw new ConflictException("Hay problemas con la cofiguración de estados. Por favor contáctese con el administrador.");
		}
		return estado.get();
	}
	
	private void armarCorreo(String codigoEstado, Ventas venta, Integer idMotvivo) {
		
		DecimalFormat formato = new DecimalFormat("$#,###.###");		
		
		String cuerpoCorreo = "";
		String asuntoCorreo = "Cambio de estado de pedido Fresh Fruit";
		
		switch (codigoEstado) {
		case Constantes.ESTADOS_VENTAS.CREADO:
			
			cuerpoCorreo= "Su pedido ha sido creado correctamente. <br> El número de su pedido es " 
							+ venta.getId() + ". <br>" + "El valor total de su compra es de " + formato.format(venta.getValortotal()) + ".";
			
			asuntoCorreo = "Creación de pedido Fresh Fruit";
			
			break;
		case Constantes.ESTADOS_VENTAS.EN_PROCESO:
			cuerpoCorreo = "Su pedido con número " + venta.getId() + " se encuentra en proceso." ;
			
			break;
		case Constantes.ESTADOS_VENTAS.RECHAZADO:
			Optional<Motivos> motivo = motivosRepositorio.findById(idMotvivo);
			cuerpoCorreo = "Su pedido con número " + venta.getId() + " ha sido rechazado por el administrador de fresh ." +
							"<br> Motivo : " + motivo.get().getDescripcion();
			
			break;
		case Constantes.ESTADOS_VENTAS.DESPACHADO:
			
			Optional<Usuarios> usuarioRepartidor = usuariosRepositorio.findById(venta.getIdusuariorepartidor());
			cuerpoCorreo = "Su pedido con número " + venta.getId() + " ha sido despachado ." +
							"<br> En el transcurso del día el repartidor " + usuarioRepartidor.get().getNombre() + " realizará la entrega.";
			break;
			
		case Constantes.ESTADOS_VENTAS.ENTREGADO:
			Optional<Usuarios> usuarioRepartidorEnt = usuariosRepositorio.findById(venta.getIdusuariorepartidor());
			cuerpoCorreo = "Su pedido con número " + venta.getId() + " acaba de ser entregado por el repartidor " +
							usuarioRepartidorEnt.get().getNombre() + 
							". <br> ¡Gracias por comprar con nosotros! " ;
			break;
			
		case Constantes.ESTADOS_VENTAS.DEVUELTO:
			
			Optional<Usuarios> usuarioRepartidorDev = usuariosRepositorio.findById(venta.getIdusuariorepartidor());
			Optional<Motivos> motivoDev = motivosRepositorio.findById(idMotvivo);
			
			cuerpoCorreo = "Su pedido con número " + venta.getId() + " ha sido devuelto al momento de su entrega." +
							"<br> Motivo : " + motivoDev.get().getDescripcion() + "." + 
							"<br> Repartidor encargado : " + usuarioRepartidorDev.get().getNombre();
			
			break;

		default:
			break;
		}
		
		Optional<Usuarios> usuario = usuariosRepositorio.findById(venta.getIdusuario());
		List<Plantillas> plantilla = plantillasRepositorio.findAll();
		String htmlPlantilla = plantilla.get(0).getPlantilla();
		htmlPlantilla = plantilla.get(0).getPlantilla().replace("{1}", "¡Hola " + usuario.get().getNombre() + "!");
		htmlPlantilla = htmlPlantilla.replace("{2}", cuerpoCorreo);
		
		boolean enviado = enviarCorreo(usuario.get().getCorreoelectronico(), asuntoCorreo, htmlPlantilla);
		System.out.print(plantilla.get(0).getPlantilla());
	}
	
	private boolean enviarCorreo(String email, String asunto, String cuerpoCorreo) {
		
		boolean enviado = false;
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);		
		try {
			helper.setTo(email);
			helper.setText(cuerpoCorreo, true);
			helper.setSubject(asunto);
			sender.send(message);
			enviado = true;
			System.out.println("Mail enviado!");
		} catch (MessagingException e) {
			System.err.println("Hubo un error al enviar el mail: {}");
		}
		return enviado;

	}
	
	private List<Integer> validarListaProductos(List<ProductosDTO> listaProductos) {
			
			List<Integer> listaIds = new ArrayList<>();
			for (ProductosDTO productosDTO : listaProductos) {
				
				if(productosDTO.getCantidad()<1 || productosDTO.getIdProducto() == null || productosDTO.getIdProducto().equals(0) ) {
					return null;
				}
				listaIds.add(productosDTO.getIdProducto());
			}
		return listaIds;
	}
	
	
}
