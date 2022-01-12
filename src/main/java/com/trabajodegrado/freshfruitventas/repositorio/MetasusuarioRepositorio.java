package com.trabajodegrado.freshfruitventas.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trabajodegrado.freshfruitventas.modelos.Metasusuario;

@Repository
public interface MetasusuarioRepositorio extends JpaRepository<Metasusuario, Integer>{
      public Optional<Metasusuario> findByIdusuarioAndIdmeta(Integer idusuario, Integer idmeta);
 }