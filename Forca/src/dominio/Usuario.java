package dominio;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
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
}
