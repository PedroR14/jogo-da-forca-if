package dominio;

public class JogarResposta {
	private String letra;

	private int lugar;

	public JogarResposta(String letra, int lugar)
	{
		this.setLetra(letra);
		this.setLugar(lugar);
	}

	public String getLetra() {
		return letra;
	}

	public void setLetra(String letra) {
		this.letra = letra;
	}

	public int getLugar() {
		return lugar;
	}

	public void setLugar(int lugar) {
		this.lugar = lugar;
	}
	
	
}
