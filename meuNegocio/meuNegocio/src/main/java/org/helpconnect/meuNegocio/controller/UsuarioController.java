package org.helpconnect.meuNegocio.controller;

import java.util.List;
import java.util.Optional;

import org.helpconnect.meuNegocio.model.UserLogin;
import org.helpconnect.meuNegocio.model.Usuario;
import org.helpconnect.meuNegocio.repository.UsuarioRepository;
import org.helpconnect.meuNegocio.service.ProdutoService;
import org.helpconnect.meuNegocio.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private ProdutoService service;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping
	public ResponseEntity<List<Usuario>> findAllUsuario(){
		
		return ResponseEntity.ok(repository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Usuario> findByIdUsuario(@PathVariable long id){
		
		return repository.findById(id)
				.map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}
	
	/*@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Usuario>> findByNomeUsuario(@PathVariable String nome){
		
		return ResponseEntity.ok(repository.findAllByNomeContainingIgnoreCase(nome));
	}*/
	
	/*@PostMapping
	public ResponseEntity<Usuario> postUsuario(@RequestBody Usuario usuario){
		
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(usuario));
	}
	
	@PutMapping
	public ResponseEntity<Usuario> putUsuario(@RequestBody Usuario usuario){
		
		return ResponseEntity.ok(repository.save(usuario));
	}*/
	
	@PostMapping("/login")
	public ResponseEntity<UserLogin> logar(@RequestBody Optional<UserLogin> login) {
		
		return usuarioService.Logar(login)
				.map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}
	
	@PostMapping("/cadastrar")
	public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuario) {
		
		Optional<Usuario> user = usuarioService.CadastrarUsuario(usuario);
		
		try {
			return ResponseEntity.ok(user.get());
			
		}catch(Exception erro) {
			return ResponseEntity.badRequest().build();
			
		}
	}
	
	@DeleteMapping("/compra/produtos/{idUsuario}/usuarios/{idProduto}")
	public void postCompra(@PathVariable long idProduto, @PathVariable long idUsuario){
		
		service.excluirProduto(idProduto, idUsuario);
	}
	
	@DeleteMapping("/{id}")
	public void deleteUsuario(@PathVariable long id) {
		
		repository.deleteById(id);
	}
}
