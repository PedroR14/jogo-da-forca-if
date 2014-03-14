package dominio;

import java.util.List;

public class EnviarNotificacoes {
	
	private List<Notificacao> lista;
	
	public EnviarNotificacoes(List<Notificacao> lista){
		this.lista = lista;
	}

	public List<Notificacao> getLista() {
		return lista;
	}

	public void setLista(List<Notificacao> lista) {
		this.lista = lista;
	}

}
