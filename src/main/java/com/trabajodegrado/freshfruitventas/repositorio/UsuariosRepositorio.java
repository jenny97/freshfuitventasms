package com.trabajodegrado.freshfruitventas.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trabajodegrado.freshfruitventas.modelos.Usuarios;

@Repository
public interface UsuariosRepositorio extends JpaRepository<Usuarios, Integer>{
      public Optional<Usuarios> findByNombreusuario(String nombreusuario);
 }