package dominio;

public class ReportarJogador {

	private int id_forca;
	private int id_usuario;
	private String observacao_reportar;
	
	public ReportarJogador(int id_forca, int id_usuario,
			String observacao_reportar) {
		this.id_forca = id_forca;
		this.id_usuario = id_usuario;
		this.observacao_reportar = observacao_reportar;
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
	public String getObservacao_reportar() {
		return observacao_reportar;
	}
	public void setObservacao_reportar(String observacao_reportar) {
		this.observacao_reportar = observacao_reportar;
	}

}
