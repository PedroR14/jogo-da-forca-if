package dominio;

import java.util.ArrayList;

public class EnviarResposta {
	private String letra;
	private int resultado;
	private int[] lugar;

	public EnviarResposta(String letra, int[] posicoes,int resultado)
	{
		this.setLetra(letra);
		this.setLugar(posicoes);
		this.setResultado(resultado);
	}

	public String getLetra() {
		return letra;
	}

	public void setLetra(String letra) {
		this.letra = letra;
	}

	public int[] getLugar() {
		return lugar;
	}

	public void setLugar(int[] posicoes) {
		this.lugar = posicoes;
	}

	public int getResultado() {
		return resultado;
	}

	public void setResultado(int resultado) {
		this.resultado = resultado;
	}

	
	
}
