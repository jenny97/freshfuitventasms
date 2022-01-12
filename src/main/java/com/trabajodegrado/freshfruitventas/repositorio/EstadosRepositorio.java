package com.trabajodegrado.freshfruitventas.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trabajodegrado.freshfruitventas.modelos.Estados;


@Repository
public interface EstadosRepositorio extends JpaRepository<Estados, Integer>{
    public Optional<Estados> findByCodigo(String codigo);
 }