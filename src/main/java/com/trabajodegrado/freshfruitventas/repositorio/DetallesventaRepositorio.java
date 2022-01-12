package com.trabajodegrado.freshfruitventas.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trabajodegrado.freshfruitventas.modelos.Detallesventa;


@Repository
public interface DetallesventaRepositorio extends JpaRepository<Detallesventa, Integer>{
      public List<Detallesventa> findByIdventa(Integer idventa);
 }