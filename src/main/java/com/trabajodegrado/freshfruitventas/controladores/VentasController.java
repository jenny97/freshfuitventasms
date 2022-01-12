package com.trabajodegrado.freshfruitventas.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.trabajodegrado.freshfruitventas.modelos.Detallesventa;
import com.trabajodegrado.freshfruitventas.modelos.Movimientosventa;
import com.trabajodegrado.freshfruitventas.modelos.Ventas;
import com.trabajodegrado.freshfruitventas.modelos.dto.CambioEstadoVentaDTO;
import com.trabajodegrado.freshfruitventas.modelos.dto.VentasDTO;
import com.trabajodegrado.freshfruitventas.negocio.VentasNegocio;


@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
@RequestMapping("/ventas")
public class VentasController {

    @Autowired
    private VentasNegocio ventasNegocio;

    @GetMapping("/")
    public ResponseEntity<List<Ventas>> obtenerLista() {
    	return new ResponseEntity<>(ventasNegocio.obtenerListaVentas(), HttpStatus.OK);
    }
    
    @GetMapping("/obtenerListaVentasPorUsuario/{id}")
    public ResponseEntity<List<Ventas>> obtenerListaVentasPorUsuario(@PathVariable("id") Integer id) {
    	return new ResponseEntity<>(ventasNegocio.obtenerListaVentasPorUsuario(id), HttpStatus.OK);
    }
    
    @GetMapping("/obtenerListaVentasPorEstado/{id}")
    public ResponseEntity<List<Ventas>> obtenerListaVentasPorEstado(@PathVariable("id") Integer id) {
    	return new ResponseEntity<>(ventasNegocio.obtenerListaVentasPorEstado(id), HttpStatus.OK);
    }
    
    @GetMapping("/obtenerHistorialVentasPorRepartidor/{id}")
    public ResponseEntity<List<Ventas>> obtenerHistorialVentasPorRepartidor(@PathVariable("id") Integer id) {
    	return new ResponseEntity<>(ventasNegocio.obtenerHistorialVentasPorRepartidor(id), HttpStatus.OK);
    }
    
    @GetMapping("/obtenerVentasPorRepartidor/{id}")
    public ResponseEntity<List<Ventas>> obtenerVentasPorRepartidor(@PathVariable("id") Integer id) {
    	return new ResponseEntity<>(ventasNegocio.obtenerVentasPorRepartidor(id), HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Ventas> obtenerVenta(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(ventasNegocio.obtenerVenta(id), HttpStatus.OK);
    }
    
    @GetMapping("/obtenerlistamovimientos/")
    public ResponseEntity<List<Movimientosventa>> obtenerlistamovimientos() {
	return new ResponseEntity<>(ventasNegocio.obtenerListaMovimientos(), HttpStatus.OK);
    }
    
    @GetMapping("/obtenerMovimientosVenta/{id}")
    public ResponseEntity<List<Movimientosventa>> obtenerMovimientosVenta(Integer id) {
	return new ResponseEntity<>(ventasNegocio.obtenerMovimientosVenta(id), HttpStatus.OK);
    }
    
    @GetMapping("/obtenerDetallesVenta/{id}")
    public ResponseEntity<List<Detallesventa>> obtenerDetallesVenta(Integer id) {
	return new ResponseEntity<>(ventasNegocio.obtenerDetallesVenta(id), HttpStatus.OK);
    }
    
    @PostMapping("/")
    public ResponseEntity<String> crearVenta(@RequestBody VentasDTO venta) {
        return new ResponseEntity<>(ventasNegocio.crearVenta(venta), HttpStatus.OK);
    }
    
    @PutMapping("/marcarEnProceso/")
    public ResponseEntity<String> marcarVentaEnProceso(@RequestBody CambioEstadoVentaDTO cambioEstadoVentaDTO) {
    	return new ResponseEntity<>(ventasNegocio.marcarVentaEnProceso(cambioEstadoVentaDTO), HttpStatus.OK);
    }
    
    @PutMapping("/marcarRechazado/")
    public ResponseEntity<String> marcarVentaRechazada(@RequestBody CambioEstadoVentaDTO cambioEstadoVentaDTO) {
    	return new ResponseEntity<>(ventasNegocio.marcarVentaRechazada(cambioEstadoVentaDTO), HttpStatus.OK);
    }
    
    @PutMapping("/marcarDespachado/")
    public ResponseEntity<String> marcarVentaDespachada(@RequestBody CambioEstadoVentaDTO cambioEstadoVentaDTO) {
    	return new ResponseEntity<>(ventasNegocio.marcarVentaDespachada(cambioEstadoVentaDTO), HttpStatus.OK);
    }
    
    @PutMapping("/marcarEntregado/")
    public ResponseEntity<String> marcarVentaEntregada(@RequestBody CambioEstadoVentaDTO cambioEstadoVentaDTO) {
    	return new ResponseEntity<>(ventasNegocio.marcarVentaEntregada(cambioEstadoVentaDTO), HttpStatus.OK);
    }
    
    @PutMapping("/marcarDevuelto/")
    public ResponseEntity<String> marcarVentaDevuelta(@RequestBody CambioEstadoVentaDTO cambioEstadoVentaDTO) {
    	return new ResponseEntity<>(ventasNegocio.marcarVentaDevuelta(cambioEstadoVentaDTO), HttpStatus.OK);
    }
    
    
}