package dominio;

import java.util.List;

public class EnviarRanking {
	
	private List<String> usuarios;
	private List<Integer> pontos;
	
	public EnviarRanking(List<String> usuarios,List<Integer> pontos){
		this.usuarios = usuarios;
		this.pontos = pontos;
	}
	
	public List<String> getUsuarios() {
		return usuarios;
	}
	public void setUsuarios(List<String> usuarios) {
		this.usuarios = usuarios;
	}
	public List<Integer> getPontos() {
		return pontos;
	}
	public void setPontos(List<Integer> pontos) {
		this.pontos = pontos;
	}
}
