package dominio;

import java.util.List;

public interface ForcaRepositorio {
	void CriarForca(Forca forca);
	void excluirForca(Integer id_forca);
	Forca getPorid_forca(Integer id_forca);
	List<Forca> getPorcategoria_forca(Integer categoria,Integer id_usuario);
	Forca getPorid_usuario(Integer id_usuario);
	List<Forca> getTodasForca(Integer id_usuario);
	List<Forca> getTodasForca_semexcessao();
	List<Categoria> getTotas_categoria();
	Categoria getPor_id_categoria(Integer id_categoria);
	void CriarCategoria(Categoria categoria);
	void Desafiar(Desafio desafio);
	void Reportar(ReportarJogador reportarJogador);
	void excluirdesafio(Integer id_forca);
	void Notificar(int id_usuario, String cabecalho, String texto, String botoes, String tipo);
	void excluirnotificacao(Integer id_notificacao);
	List<Notificacao> getNotificacoes(Integer id_usuario);
	Notificacao getNotificacao_porid(Integer id_notificacao);
	void MarcarLida_Notificao(Integer id_notificacao);
	void ForcaVitoria(int id_usuario, int pontos);
	void ForcaDerrota(int id_usuario, int pontos);
	int getpontos(int id_usuario);
	boolean isDesafio(Integer id_forca);
	Integer getApostaDesafio(Integer id_forca);
	Integer getDestinatarioDesafio(Integer id_forca);
	boolean VerificarId(Integer id_forca);
	boolean VerificarId_notificacao(Integer id_notificacao);
	Desafio get_desafio_idforca(Integer id_forca);
}
