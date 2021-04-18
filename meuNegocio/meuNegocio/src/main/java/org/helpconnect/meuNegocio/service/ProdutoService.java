package org.helpconnect.meuNegocio.service;

import java.util.Optional;

import org.helpconnect.meuNegocio.model.Produto;
import org.helpconnect.meuNegocio.model.Usuario;
import org.helpconnect.meuNegocio.repository.ProdutoRepository;
import org.helpconnect.meuNegocio.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	/*ADICIONAR PRODUTO AO USUARIO*/
	public Produto compraProduto(long idProduto, long idUsuario) {
		
		Optional<Usuario> usuarioExistente = usuarioRepository.findById(idUsuario);
		Optional<Produto> produtoExistente = produtoRepository.findById(idProduto);
		
		if(usuarioExistente.isPresent() && produtoExistente.isPresent() && produtoExistente.get().getQtdProduto() > 0 && produtoExistente.get().isAtivo()) {
			/*ADICIONA O USUARIO AO PRODUTO*/
			produtoExistente.get().getUsuarios().add(usuarioExistente.get());
			
			produtoRepository.save(produtoExistente.get());
			
			/*GERENCIAR CARRINHO USUARIO - INCLUINDO PRODUTO*/
			this.atualizarCarrinho(usuarioExistente.get().getId(), produtoExistente.get().getPreco());
			
			/*GERENCIAR ESTOQUE*/
			this.gerenciarEstoqueRetiradaProduto(idProduto);
			
			return produtoRepository.save(produtoExistente.get()); 	
		}
		
		return null;
	}
	
	/*RETIRAR PRODUTO DO USUARIO*/
	public Produto excluirProduto(long idProduto, long idUsuario) {
		
		Optional<Usuario> usuarioExistente = usuarioRepository.findById(idUsuario);
		Optional<Produto> produtoExistente = produtoRepository.findById(idProduto);
		
		if(usuarioExistente.isPresent() && produtoExistente.isPresent()) {
			produtoExistente.get().getUsuarios().remove(usuarioExistente.get());
			
			produtoRepository.save(produtoExistente.get());
			
			/*GERENCIAR CARRINHO USUARIO - EXCLUINDO PRODUTO*/
			this.ajustarCarrinho(usuarioExistente.get().getId(), produtoExistente.get().getPreco());
			
			/*GERENCIAR ESTOQUE*/
			this.gerenciarEstoqueVoltaProduto(idProduto);
			
			return produtoRepository.save(produtoExistente.get()); 	
		}
		
		return null;
	}
	
	/*GERENCIAR CARRINHO USUARIO - INCLUINDO PRODUTO*/
	public void atualizarCarrinho(long idUsuario, double preco) {
		
		Optional<Usuario> usuarioExistente = usuarioRepository.findById(idUsuario);
		
		usuarioExistente.get().setCarrinho(usuarioExistente.get().getCarrinho() + preco);
		
		usuarioRepository.save(usuarioExistente.get());
		
	}
	
	/*GERENCIAR CARRINHO USUARIO - EXCLUINDO PRODUTO*/
	public void ajustarCarrinho(long idUsuario, double preco) {
		
		Optional<Usuario> usuarioExistente = usuarioRepository.findById(idUsuario);
		
		double a=usuarioExistente.get().getCarrinho();
		a=Math.floor(a*100) / 100;
		
		usuarioExistente.get().setCarrinho(a - preco);
		
		if(usuarioExistente.get().getCarrinho() < 0) {
			usuarioExistente.get().setCarrinho(0);
		}
		
		usuarioRepository.save(usuarioExistente.get());
		
	}
	
	/*GERENCIAR ESTOQUE - RETIRAR PRODUTO*/
	public void gerenciarEstoqueRetiradaProduto(long idProduto) {
		Optional<Produto> produtoExistente = produtoRepository.findById(idProduto);
		
		produtoExistente.get().setQtdProduto(produtoExistente.get().getQtdProduto() - 1);
		
	}
	
	/*GERENCIAR ESTOQUE - INCLUIR PRODUTO*/
	public void gerenciarEstoqueVoltaProduto(long idProduto) {
		Optional<Produto> produtoExistente = produtoRepository.findById(idProduto);
		
		produtoExistente.get().setQtdProduto(produtoExistente.get().getQtdProduto() + 1);
		
	}
	
	/*APLICAR DESCONTO PRODUTO*/
	public void ajustarPreco(Produto produto) {
		Optional<Produto> produtoExistente = produtoRepository.findById(produto.getId());
		
		double precoProduto = produtoExistente.get().getPreco();
		double desconto = produtoExistente.get().getPreco() * produtoExistente.get().getPromocao();
		double p = produto.getPreco();
		
		produtoExistente.get().setPreco(precoProduto - desconto);
		
		/*CASO O VALOR DO PRECO SEJA O MESMO E O VALOR DA PROMOCAO TAMBEM SEJA O MESMO*/
		if((produtoExistente.get().getPreco() + desconto) == produto.getPreco() && produtoExistente.get().getPromocao() == produto.getPromocao()) {
			if(p != produto.getPreco()) {
				produto.setPreco(p);
				produtoRepository.save(produto);
				
			}else {
				produto.setPreco(produtoExistente.get().getPreco());
				produtoRepository.save(produto);
				
			}
			
		/*CASO O VALOR DO PRECO SEJA DIFERENTE E O VALOR DA PROMOCAO TAMBEM SEJA DIFERENTE*/
		}else {
			produtoExistente.get().setPreco(p);
			
			double resultado = produtoExistente.get().getPreco() * produto.getPromocao();
			produto.setPreco(produto.getPreco() - resultado);
			
			produtoRepository.save(produto);

		}
		
	}

}
