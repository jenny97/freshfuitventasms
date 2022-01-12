package com.trabajodegrado.freshfruitventas.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trabajodegrado.freshfruitventas.modelos.Movimientosinventario;


@Repository
public interface MovimientosinventarioRepositorio extends JpaRepository<Movimientosinventario, Integer>{
      public List<Movimientosinventario> findByIdinventario(Integer idinventario);
 }