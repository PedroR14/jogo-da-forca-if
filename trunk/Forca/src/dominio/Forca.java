package dominio;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Forca {
	
	@Autowired
	private ForcaService service;
	
	@NotNull
	private int id_forca;
	@NotNull
	private int id_usuario;
	@NotNull
	private int cod_categoria;
	@NotNull @Pattern(regexp = "^[\\w\\-]+(\\.[\\w\\-]+)*@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,4}$", message="Palavra com formato incorreto.")
	private String palavra;
	@NotNull @Size(min=5, max=130)
	private String dica;
	@NotNull
	private int tem_desafio;
	
	public Forca(int id_forca,int id_usuario,int cod_categoria,String palavra,
			String dica,int tem_desafio){
		this.id_forca = id_forca;
		this.id_usuario  = id_usuario;
		this.cod_categoria = cod_categoria;
		this.dica = dica;
		this.tem_desafio = tem_desafio;
	}
	
	public Forca() {
		
	}

	public int getId_forca() {
		return id_forca;
	}
	public void setId_forca(int id_forca) {
		this.id_forca = id_forca;
	}
	public int getId_usuario() {
		return id_usuario;
	}
	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
	}
	public int getCod_categoria() {
		return cod_categoria;
	}
	public void setCod_categoria(int cod_categoria) {
		this.cod_categoria = cod_categoria;
	}
	public String getPalavra() {
		return palavra;
	}
	public void setPalavra(String palavra) {
		this.palavra = palavra;
	}
	public String getDica() {
		return dica;
	}
	public void setDica(String dica) {
		this.dica = dica;
	}
	public int getTem_desafio() {
		return tem_desafio;
	}
	public void setTem_desafio(int tem_desafio) {
		this.tem_desafio = tem_desafio;
	}
	public String[] gerar_array_letras(Integer idforca){
		
		String palavra = service.getPorid_forca(idforca).getPalavra();
		System.out.println(service.getPorid_forca(idforca).getPalavra());
		
		String[] arrayletras = palavra.split("");
		
		return arrayletras;
	}
	
	public String gerar_tracos(int quant){
		String tracos = "";
		
		for (int i = 0; i < quant; i++) {
			tracos = tracos.concat(" __ ");
		}
		
		return tracos;
	}
	
	
	public boolean conten_letra(String Letra, String[] palavra_array){
		
		for (int i = 0; i < palavra_array.length; i++) {
			if(Letra.equalsIgnoreCase(palavra_array[i])){
				return true;
			}
		}	
		return false;	
	}
	public int[] posicoes(String Letra, String[] palavra_array){
		
		ArrayList<Integer> positions_list = new ArrayList<Integer>();
		
		for (int i = 0; i < palavra_array.length; i++) {
			if(Letra.equalsIgnoreCase(palavra_array[i])){
				positions_list.add(i);
			}
		}
		
		int[] posicoes = new int[positions_list.size()];
		for (int i = 0; i < posicoes.length; i++) {
			posicoes[i] = positions_list.get(i);
		}
		
		return posicoes;	
	}
	public List<Forca> get_intervalo_forcas(int quant, List<Forca> forcas){
		
		List<Forca> forcas_part = new ArrayList<Forca>();
		
		for (int i = quant; i < (quant + 5) ; i++) {
			if(forcas.size() > i){
				forcas_part.add(forcas.get(i));
			}
			
		}
		
		return forcas_part;
	}
	public boolean possui_prox(int quant, List<Forca> forcas){
		if((forcas.size() - 6) < quant){
			return false;
		}
		return  true;
	}
	
	public Integer gerar_id_forca(){
		
		AlgoritmoDerpofoldao derpofoldao = new AlgoritmoDerpofoldao();
		List<Forca> forcas = service.getTodasForca_semexcessao();
		
		int id_forca = derpofoldao.gerarNumero();
		int retorno = id_forca;
		int i = forcas.size() - 1;
		if(forcas.size() != 0){
			while(i>=0){
				if(id_forca != forcas.get(i).getId_forca()){
					retorno = id_forca;
					i--;
				} else if (id_forca == forcas.get(i).getId_forca()){
					id_forca = derpofoldao.gerarNumero();
					i = forcas.size() - 1;
				}
			}	
		}else{
			return id_forca;
		}
		
		return retorno;
	}

}
