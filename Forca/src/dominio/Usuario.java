package dominio;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Usuario {
	
	@Autowired
	private UsuariosService service;
	
	@Autowired
	private ForcaService service_forca;
	
	@Autowired
	private Forca forca;
	
	@NotNull
	private Integer id;
	@NotNull(message="Campo Obrigatorio") @Size(min=5, max=50, message = "Tamanho Minimo 5 e Maximo 50")
	private String nome;
	@NotNull(message = "Campo Obrigatorio!")
	private String login;
	@NotNull(message = "Campo Obrigatorio!") @Email
	private String email;
	@NotNull(message = "Campo Obrigatorio!")
	private String senha;
	@NotNull(message = "Campo Obrigatorio!")
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
		
		for (int i = 0; i < usuarios.size(); i++) {
			UsuarioPontos usuario = new UsuarioPontos();
			Integer id = usuarios.get(i).getid();
			usuario.setPontos(service.getRanking_Data(usuarios.get(i).getid(), inicio, fim));
			usuario.setId_usuario(usuarios.get(i).getid());
			usuario_pontos.add(usuario);
		}
		
		return usuario_pontos;
	}
	public List<UsuarioPontos> gerar_ranking_dias(List<Usuario> usuarios, Integer dias){
		
		List<UsuarioPontos> usuario_pontos = new ArrayList<UsuarioPontos>();
		
		for (int i = 0; i < usuarios.size(); i++) {
			UsuarioPontos usuario = new UsuarioPontos();
			Integer id = usuarios.get(i).getid();
			usuario.setPontos(service.getRanking_Dias(usuarios.get(i).getid(), dias));
			usuario.setId_usuario(usuarios.get(i).getid());
			usuario_pontos.add(usuario);
		}
		
		return usuario_pontos;
	}

	public void EnviarDesafio(Forca forca_criada, Integer aposta, Usuario usuario, Integer id_destinatario){
		
		Desafio desafio = new Desafio();
		
		desafio.setId_usuario_destinatario(id_destinatario);
		
		forca_criada.setId_forca(forca.gerar_id_forca());
		forca_criada.setTem_desafio(1);
		desafio.setId_forca(forca_criada.getId_forca());
		desafio.setAposta(aposta);
		desafio.setId_usuario_remetente(usuario.getid());
		
		forca_criada.setId_usuario(usuario.getid());
		service_forca.Desafiar(desafio);
		service_forca.CriarForca(forca_criada);
		service_forca.Notificar(desafio.getId_usuario_destinatario(), "Você foi desafiado pelo usuario "+
		usuario.getlogin());
		
	}
	
}
