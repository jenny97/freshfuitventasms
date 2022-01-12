package com.trabajodegrado.freshfruitventas.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trabajodegrado.freshfruitventas.modelos.Movimientosventa;


@Repository
public interface MovimientosventaRepositorio extends JpaRepository<Movimientosventa, Integer>{
      public List<Movimientosventa> findByIdventa(Integer idventa);
 }