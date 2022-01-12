package com.trabajodegrado.freshfruitventas.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trabajodegrado.freshfruitventas.modelos.Inventarios;


@Repository
public interface InventariosRepositorio extends JpaRepository<Inventarios, Integer>{
	public Optional<Inventarios> findByIdproducto(Integer idproducto);
 }