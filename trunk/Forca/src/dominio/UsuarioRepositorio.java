package dominio;

import java.util.List;

public interface UsuarioRepositorio {
	void inserir(Usuario cliente);
	void atualizar(Usuario cliente);
	void excluir(Integer codigoCliente);
	Usuario getPorid(Integer codigoCliente);
	List<Usuario> getTodos();
}
