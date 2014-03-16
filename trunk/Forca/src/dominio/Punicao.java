package dominio;

import javax.validation.constraints.Size;

import com.sun.istack.internal.NotNull;

public class Punicao {
	
	@NotNull
	int COD_PUNICAO;
	@NotNull @Size(min=3)
	String NOME_PUNICAO;
	@NotNull 
	String TIPO_PUNICAO;
	
	
	
	
	
	public int getCOD_PUNICAO() {
		return COD_PUNICAO;
	}
	public void setCOD_PUNICAO(int cOD_PUNICAO) {
		COD_PUNICAO = cOD_PUNICAO;
	}
	public String getNOME_PUNICAO() {
		return NOME_PUNICAO;
	}
	public void setNOME_PUNICAO(String nOME_PUNICAO) {
		NOME_PUNICAO = nOME_PUNICAO;
	}
	public String getTIPO_PUNICAO() {
		return TIPO_PUNICAO;
	}
	public void setTIPO_PUNICAO(String tIPO_PUNICAO) {
		TIPO_PUNICAO = tIPO_PUNICAO;
	}
	

}
