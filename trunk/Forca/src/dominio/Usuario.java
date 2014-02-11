package dominio;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Usuario {
	
	@Autowired
	private UsuariosService service;
	
	private Integer id;
	@NotNull @Size(min=5)
	private String nome; 
	private String login;
	private String email;
	private String senha;
	private int tipo_usuario;
	
	public Usuario() {}
	public Usuario(Integer id, String nome, String login, 
			String email, String senha) {
		this.id = id;
		this.nome = nome;
		this.login = login;
		this.email = email;
		this.senha = senha;
	}
	
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Integer getid() {
		return id;
	}
	public void setid(Integer id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getlogin() {
		return login;
	}
	public void setlogin(String login) {
		this.login = login;
	}
	public String getemail() {
		return email;
	}
	public void setemail(String email) {
		this.email = email;
	}
	
	public int getTipo_usuario() {
		return tipo_usuario;
	}
	public void setTipo_usuario(int tipo_usuario) {
		this.tipo_usuario = tipo_usuario;
	}
	
	public String getNome_porId(Integer id_usuario){
		return service.getPorid(id_usuario).getlogin();
	}
	public String data_sql(String data) throws ParseException{
		
		DateFormat formatter = new SimpleDateFormat("MM/dd/yy");  
		Date date = (Date)formatter.parse(data);
		
		System.out.println("function:"+date);
		
		SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd"); 
		String nova_data = f.format(date);
		
		return nova_data; 
	}
	public List<UsuarioPontos> gerar_ranking_data(List<Usuario> usuarios,String inicio, String fim){
		
		List<UsuarioPontos> usuario_pontos = new ArrayList<UsuarioPontos>();
		
		System.out.println("f "+usuarios.get(0).getlogin()+"i "+usuarios.get(0).getid());
		
		for (int i = 0; i < usuarios.size(); i++) {
			UsuarioPontos usuario = new UsuarioPontos();
			Integer id = usuarios.get(i).getid();
			System.out.println(inicio);
			System.out.println(fim);
			System.out.println(id);
			System.out.println(service.getRanking_Data(id, inicio, fim));
			usuario.setPontos(service.getRanking_Data(usuarios.get(i).getid(), inicio, fim));
			usuario.setId_usuario(usuarios.get(i).getid());
			usuario_pontos.add(usuario);
		}
		
		return usuario_pontos;
	}
	
}
