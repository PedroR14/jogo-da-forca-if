package dominio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuariosService {
	
	@Autowired
	private UsuarioRepositorio repositorio;
	
	public void inserir(Usuario cliente, Integer tipo_usuario){
		repositorio.inserir(cliente, tipo_usuario);
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
	
	public List<Integer> getPontos_Ranking(){
		return repositorio.getPontos_Ranking();
	}
	
	public List<Integer> getUsuario_Ranking(){
		return repositorio.getUsuario_Ranking();
	}
	
	public Integer getRanking_Data(Integer id_usuario,String inicio, String fim){
		return repositorio.getRanking_Data(id_usuario,inicio,fim);
	}
	
	public Integer getRanking_Dias(Integer id_usuario, Integer dias){
		return repositorio.getRanking_Dias(id_usuario, dias);
	}
	
	public List<Usuario> getPor_Login(String login){
		return repositorio.getPor_Login(login);
	}
	
	public boolean VerificarId(Integer id_usuario){
		return repositorio.VerificarId(id_usuario);
	}
	
}
