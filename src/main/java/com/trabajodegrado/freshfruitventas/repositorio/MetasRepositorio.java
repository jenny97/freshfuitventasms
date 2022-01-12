package com.trabajodegrado.freshfruitventas.repositorio;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trabajodegrado.freshfruitventas.modelos.Metas;


@Repository
public interface MetasRepositorio extends JpaRepository<Metas, Integer>{
	public Optional<Metas> findByIdproducto(Integer idproducto);
	
	public Optional<Metas>findByIdproductoAndFechainicioLessThanEqualAndFechafinGreaterThanEqual(Integer idproducto, Date fecha1, Date fecha2);

 }