package dominio;

import java.util.List;

public interface UsuarioRepositorio {
	void inserir(Usuario cliente);
	void atualizar(Usuario cliente);
	void excluir(Integer codigoCliente);
	Usuario getPorid(Integer codigoCliente);
	List<Usuario> getTodos();
	List<Integer> getPontos_Ranking(); 
	List<Integer> getUsuario_Ranking(); 
	Integer getRanking_Data(Integer id_usuario,String inicio, String fim); 
}
