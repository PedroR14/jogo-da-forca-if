package dominio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuariosService {
	
	@Autowired
	private UsuarioRepositorio repositorio;
	
	public void inserir(Usuario cliente){
		repositorio.inserir(cliente);
	}
	public void atualizar(Usuario cliente){
		repositorio.atualizar(cliente);
	}
	public void excluir(Integer codigoCliente){
		repositorio.excluir(codigoCliente);
	}
	public Usuario getPorid(Integer codigoCliente){
		return repositorio.getPorid(codigoCliente);
	}
	
	public List<Usuario> getTodos(){
		return repositorio.getTodos();
	}
}
