package dominio;

import java.util.List;

public class ListaForcas {
	
	private List<Forca> forcas;
	private boolean possui_prox;
	
	

	public ListaForcas(List<Forca> forcas,boolean possui_prox){
		this.forcas = forcas;
		this.possui_prox = possui_prox;
	}

	public List<Forca> getForcas() {
		return forcas;
	}

	public void setForcas(List<Forca> forcas) {
		this.forcas = forcas;
	}
	
	public boolean isPossui_prox() {
		return possui_prox;
	}

	public void setPossui_prox(boolean possui_prox) {
		this.possui_prox = possui_prox;
	}

}
