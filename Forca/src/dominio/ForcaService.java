package dominio;

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
	
	public Forca getPorid_usuario(Integer id_usuario){
		return forca_repositorio.getPorid_usuario(id_usuario);
	}
	
	public List<Forca> getTodasForca(){
		return forca_repositorio.getTodasForca();
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
	
	public void Notificar(int id_usuario, String texto){
		forca_repositorio.Notificar(id_usuario, texto);
	}
	
	public List<String> getNotificacoes(Integer id_usuario){
		return forca_repositorio.getNotificacoes(id_usuario);
		
	}

}
