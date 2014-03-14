package dominio;

import javax.validation.constraints.Size;

import com.sun.istack.internal.NotNull;

public class Categoria {
	
	@NotNull
	int idcategoria;
	@NotNull @Size(min=3)
	String Tipocategoria;
	
	public int getIdcategoria() {
		return idcategoria;
	}
	public void setIdcategoria(int idcategoria) {
		this.idcategoria = idcategoria;
	}
	public String getTipocategoria() {
		return Tipocategoria;
	}
	public void setTipocategoria(String tipo_categoria) {
		Tipocategoria = tipo_categoria;
	}
	
	

}
