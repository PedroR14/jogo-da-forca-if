package dominio;

public class JogarResposta {
	private String letra;
	private char[] palavra;
	private int lugar;

	public JogarResposta(String letra, int lugar,char[] palavrao)
	{
		this.setLetra(letra);
		this.setLugar(lugar);
		this.setPalavra(palavrao);
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

	public char[] getPalavra() {
		return palavra;
	}

	public void setPalavra(char[] palavra) {
		this.palavra = palavra;
	}
	
	
}
