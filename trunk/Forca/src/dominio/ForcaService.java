package dominio;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ForcaService {
	

	@Autowired
	private ForcaRepositorio forca_repositorio;
	
	public void CriarForca(Forca forca){
		forca_repositorio.CriarForca(forca);
	}
	public void excluir(Integer id_forca){
		forca_repositorio.excluirForca(id_forca);
	}
	public Forca getPorid_forca(Integer id_forca){
		return forca_repositorio.getPorid_forca(id_forca);
	}
	
	public List<Forca> getPorcategoria_forca(Integer categoria, Integer id_usuario){
		return forca_repositorio.getPorcategoria_forca(categoria, id_usuario);
	}
	
	public Forca getPorid_usuario(Integer id_usuario){
		return forca_repositorio.getPorid_usuario(id_usuario);
	}
	
	public List<Forca> getTodasForca(Integer id_usuario){
		return forca_repositorio.getTodasForca(id_usuario);
	}
	
	public List<Forca> getTodasForca_semexcessao(){
		return forca_repositorio.getTodasForca_semexcessao();
	}
	
	public List<Categoria> getTotas_categoria(){
		return forca_repositorio.getTotas_categoria();
		
	}
	
	public Categoria getPor_id_categoria(Integer id_categoria){
		return forca_repositorio.getPor_id_categoria(id_categoria);
		
	}
	
	public void CriarCategoria(Categoria categoria){
		forca_repositorio.CriarCategoria(categoria);
	}
	
	public void Desafiar(Desafio desafio){
		forca_repositorio.Desafiar(desafio);
	}
	
	public void Notificar(int id_usuario, String texto, String tipo){
		forca_repositorio.Notificar(id_usuario, texto,tipo);
	}
	
	public List<Notificacao> getNotificacoes(Integer id_usuario){
		return forca_repositorio.getNotificacoes(id_usuario);
		
	}
	
	public Notificacao getNotificacao_porid(Integer id_notificacao){
		return forca_repositorio.getNotificacao_porid(id_notificacao);
		
	}
	
	public void ForcaVitoria(int id_usuario, int pontos){
		forca_repositorio.ForcaVitoria(id_usuario, pontos);
	}
	
	public void ForcaDerrota(int id_usuario, int pontos){
		forca_repositorio.ForcaDerrota(id_usuario, pontos);
	}
	
	public int getpontos(int id_usuario){
		return forca_repositorio.getpontos(id_usuario);
	}
	
	public boolean isDesafio(Integer id_forca){
		return forca_repositorio.isDesafio(id_forca);
	}
	
	public Integer getApostaDesafio(Integer id_forca){
		return forca_repositorio.getApostaDesafio(id_forca);
	}
	
	public Integer getDestinatarioDesafio(Integer id_forca){
		return forca_repositorio.getDestinatarioDesafio(id_forca);
	}
	
	public boolean VerificarId(Integer id_forca){
		return forca_repositorio.VerificarId(id_forca);
	}
	
	public boolean VerificarId_notificacao(Integer id_notificacao){
		return forca_repositorio.VerificarId_notificacao(id_notificacao);
	}

}
