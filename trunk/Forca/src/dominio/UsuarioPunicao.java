package dominio;

import com.sun.istack.internal.NotNull;

public class UsuarioPunicao {
	

	@NotNull 
	int idUsuario;
	@NotNull 
	int codPunicao;
	@NotNull 
	String observacaoPunicao;
	
	
	
	public UsuarioPunicao(int idUsuario, int codPunicao,
			String observacaoPunicao) {
		super();
		this.idUsuario = idUsuario;
		this.codPunicao = codPunicao;
		this.observacaoPunicao = observacaoPunicao;
	}
	
	

	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public int getCodPunicao() {
		return codPunicao;
	}
	public void setCodPunicao(int codPunicao) {
		this.codPunicao = codPunicao;
	}
	public String getObservacaoPunicao() {
		return observacaoPunicao;
	}
	public void setObservacaoPunicao(String oberservacaoPunicao) {
		this.observacaoPunicao = oberservacaoPunicao;
	}
	
	
	


}
