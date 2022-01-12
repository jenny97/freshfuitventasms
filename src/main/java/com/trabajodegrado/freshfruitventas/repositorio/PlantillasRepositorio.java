package com.trabajodegrado.freshfruitventas.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trabajodegrado.freshfruitventas.modelos.Plantillas;

@Repository
public interface PlantillasRepositorio extends JpaRepository<Plantillas, Integer>{

 }