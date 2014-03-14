package dominio;

import java.util.List;

public class EnviarPesquisa {
	
	private List<Usuario> usuarios;
	
	public  EnviarPesquisa(List<Usuario> usuarios){
		this.usuarios = usuarios;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
}
