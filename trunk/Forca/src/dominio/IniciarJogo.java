package dominio;

public class IniciarJogo {
	
	
	public String[] gerar_arraypalavra(String palavra){
		
		String[] arrayletras = palavra.split("");

		
		return arrayletras;
	}
	
	public int quantidade(String palavra){
		
		return palavra.length();
	}

}
