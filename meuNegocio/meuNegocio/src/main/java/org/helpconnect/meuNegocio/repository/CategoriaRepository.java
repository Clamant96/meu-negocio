package org.helpconnect.meuNegocio.repository;

import java.util.List;

import org.helpconnect.meuNegocio.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long>{
	
	public List<Categoria> findAllByNomeContainingIgnoreCase(String nome);

}
