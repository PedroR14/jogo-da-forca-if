package dominio;

public class EnviarResposta {
	private String letra;
	private int resultado;
	private int[] lugar;
	private int acertos;
	private int erros;
	private boolean acabou;

	public EnviarResposta(String letra, int[] posicoes,int resultado,int acertos,int erros
			,boolean acabou)
	{
		this.setLetra(letra);
		this.setLugar(posicoes);
		this.setResultado(resultado);
		this.setAcertos(acertos);
		this.setErros(erros);
		this.setAcabou(acabou);
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

	public int getAcertos() {
		return acertos;
	}

	public void setAcertos(int acertos) {
		this.acertos = acertos;
	}

	public int getErros() {
		return erros;
	}

	public void setErros(int erros) {
		this.erros = erros;
	}

	public boolean isAcabou() {
		return acabou;
	}

	public void setAcabou(boolean acabou) {
		this.acabou = acabou;
	}

	
}
