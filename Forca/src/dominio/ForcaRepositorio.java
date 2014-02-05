package dominio;

import java.util.ArrayList;
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
	void Notificar(int id_usuario, String texto);
	List<String> getNotificacoes(Integer id_usuario);
	void ForcaVitoria(int id_usuario, int pontos);
	void ForcaDerrota(int id_usuario);
	int getpontos(int id_usuario);
}