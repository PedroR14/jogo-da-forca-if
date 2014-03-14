package dominio;

import java.sql.Date;

public class Notificacao {
	
	private Integer id_notificacao;
	private Integer id_usuario;
	private Integer visualizada;
	private String texto;
	private Date data;
	private String tipo;
	
	public Integer getId_notificacao() {
		return id_notificacao;
	}
	public void setId_notificacao(Integer id_notificacao) {
		this.id_notificacao = id_notificacao;
	}
	public Integer getId_usuario() {
		return id_usuario;
	}
	public void setId_usuario(Integer id_usuario) {
		this.id_usuario = id_usuario;
	}
	public Integer getVisualizada() {
		return visualizada;
	}
	public void setVisualizada(Integer visualizada) {
		this.visualizada = visualizada;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
