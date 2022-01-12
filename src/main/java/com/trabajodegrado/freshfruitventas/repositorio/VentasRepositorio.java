package com.trabajodegrado.freshfruitventas.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trabajodegrado.freshfruitventas.modelos.Ventas;


@Repository
public interface VentasRepositorio extends JpaRepository<Ventas, Integer>{
	public List<Ventas> findByIdusuario(Integer idusuario);
	public List<Ventas> findByIdestado(Integer idusuario);
	public List<Ventas> findByIdusuariorepartidorAndIdestadoNot(Integer idusuariorepartidor, Integer idestado);
	public List<Ventas> findByIdusuariorepartidorAndIdestado(Integer idusuariorepartidor, Integer idestado);
 }