package org.helpconnect.meuNegocio.controller;

import java.util.List;

import org.helpconnect.meuNegocio.model.Produto;
import org.helpconnect.meuNegocio.repository.ProdutoRepository;
import org.helpconnect.meuNegocio.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutoController {
	
	@Autowired
	private ProdutoRepository repository;
	
	@Autowired
	private ProdutoService service;
	
	@GetMapping
	public ResponseEntity<List<Produto>> findAllProduto(){
		
		return ResponseEntity.ok(repository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Produto> findByIdProduto(@PathVariable long id){
		
		return repository.findById(id)
				.map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("nome/{nome}")
	public ResponseEntity<List<Produto>> findByNomeProduto(@PathVariable String nome){
		
		return ResponseEntity.ok(repository.findAllByNomeContainingIgnoreCase(nome));
	}
	
	@GetMapping("descricao/{descricao}")
	public ResponseEntity<List<Produto>> findByDescricaoProduto(@PathVariable String descricao){
		
		return ResponseEntity.ok(repository.findAllByDescricaoContainingIgnoreCase(descricao));
	}
	
	@PostMapping
	public ResponseEntity<Produto> postProduto(@RequestBody Produto produto){
		
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(produto));
	}
	
	/*
	 * EXPLICACAO URI:
	 * 
	 * 	/compra -> nome da tabela associativa
	 * 	/usuarios -> nome da lista de usuarios dentro da classe Produto
	 * 	/produtos -> nome da lista de produtos dentro da classe Usuario
	 * 
	 * */
	@PutMapping("/compra/usuarios/{idProduto}/produtos/{idUsuario}")
	public ResponseEntity<Produto> postCompra(@PathVariable long idProduto, @PathVariable long idUsuario){
		
		return ResponseEntity.status(HttpStatus.CREATED).body(service.compraProduto(idProduto, idUsuario));
	}
	
	@PutMapping
	public ResponseEntity<Produto> putProduto(@RequestBody Produto produto){
		
		return ResponseEntity.ok(repository.save(produto));
	}
	
	@DeleteMapping("/{id}")
	public void deleteProduto(@PathVariable long id){
		
		repository.deleteById(id);
	}
}
