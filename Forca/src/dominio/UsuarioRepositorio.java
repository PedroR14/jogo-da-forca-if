package dominio;

import java.util.List;

public interface UsuarioRepositorio {
	void inserir(Usuario cliente, Integer tipo_usuario);
	void atualizar(Usuario cliente);
	void excluir(Integer codigoCliente);
	Usuario getPorid(Integer codigoCliente);
	List<Usuario> getTodos();
	List<Integer> getPontos_Ranking(); 
	List<Integer> getUsuario_Ranking(); 
	Integer getRanking_Data(Integer id_usuario,String inicio, String fim);
	Integer getRanking_Dias(Integer id_usuario, Integer dias);
	List<Usuario> getPor_Login(String login);
	boolean VerificarId(Integer id_usuario);
}
