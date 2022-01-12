package com.trabajodegrado.freshfruitventas.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trabajodegrado.freshfruitventas.modelos.Productos;

@Repository
public interface ProductosRepositorio extends JpaRepository<Productos, Integer>{
      public List<Productos> findByNombre(String nombre);
 }