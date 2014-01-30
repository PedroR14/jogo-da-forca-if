package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import dominio.Categoria;
import dominio.Desafio;
import dominio.Forca;
import dominio.ForcaRepositorio;

public class ForcaDAO implements ForcaRepositorio{
	
	@Autowired
	private DataSource dataSource;
	
	public void CriarForca(Forca forca) {
		try{
			Connection conexao = dataSource.getConnection();
			String sql = "insert into forcas (id_forca,id_usuario, cod_categoria, dica, palavra, ter_desfio) "
					+ "values (?,?, ?, ?, ?, ?)";
			PreparedStatement stm = conexao.prepareStatement(sql);
			stm.setString(1, Integer.toString(forca.getId_forca()));
			stm.setString(2, Integer.toString(forca.getId_usuario()));
			stm.setString(3, Integer.toString(forca.getCod_categoria()));
			stm.setString(4, forca.getDica());
			stm.setString(5, forca.getPalavra());
			stm.setString(6, Integer.toString(forca.getTem_desafio()));
			stm.executeUpdate();
			stm.close();
		}catch(SQLException ex){
			throw new RuntimeException(ex);
		}
		
	}

	
	public void excluirForca(Integer id_forca) {
		// TODO Auto-generated method stub
		
	}

	
	public List<Forca> getTodasForca() {
		try{
			Connection conexao = dataSource.getConnection();
			Statement stm = conexao.createStatement();
			String sql = "select * from forcas";
			ResultSet rs = stm.executeQuery(sql);
			ArrayList<Forca> forcas = new ArrayList<>();
			while(rs.next()){
				Forca forca = new Forca();
				forca.setId_forca( rs.getInt("id_forca") );
				forca.setId_usuario(rs.getInt("id_usuario"));
				forca.setCod_categoria(rs.getInt("cod_categoria"));
				forca.setDica(rs.getString("dica"));
				forca.setPalavra(rs.getString("palavra"));
				forca.setTem_desafio(rs.getInt("ter_desfio"));
				forcas.add(forca);
			}
			rs.close();
			stm.close();
			return forcas;
		}catch(SQLException ex){
			throw new RuntimeException(ex);
		}
	}


	public Forca getPorid_forca(Integer id_forca) {
		// TODO Auto-generated method stub
		return null;
	}


	public Forca getPorid_usuario(Integer id_usuario) {
		// TODO Auto-generated method stub
		return null;
	}


	
	public List<Categoria> getTotas_categoria() {
		try{
			Connection conexao = dataSource.getConnection();
			Statement stm = conexao.createStatement();
			String sql = "select * from categoria";
			ResultSet rs = stm.executeQuery(sql);
			ArrayList<Categoria> categorias = new ArrayList<>();
			while(rs.next()){
				Categoria c = new Categoria();
				c.setIdcategoria( rs.getInt("id_categoria") );
				c.setTipocategoria(rs.getString("Tipo_categoria"));
				categorias.add(c);
			}
			rs.close();
			stm.close();
			return categorias;
		}catch(SQLException ex){
			throw new RuntimeException(ex);
		}
	}


	
	public Categoria getPor_id_categoria(Integer id_categoria) {
		try{
			Connection conexao = dataSource.getConnection();
			String sql = "select * from categoria where id_categoria = ?";
			PreparedStatement stm = conexao.prepareStatement(sql);
			stm.setInt(1, id_categoria);
			ResultSet rs = stm.executeQuery();
			while(rs.next()){
				Categoria c = new Categoria();
				c.setIdcategoria( rs.getInt("id_categoria") );
				c.setTipocategoria( rs.getString("login") );
				rs.close();
				stm.close();
				return c;
			}
			return null;
		}catch(SQLException ex){
			throw new RuntimeException(ex);
		}
	}


	
	public void CriarCategoria(Categoria categoria) {
		try{
			Connection conexao = dataSource.getConnection();
			String sql = "insert into categoria (Tipo_categoria) "
					+ "values (?)";
			PreparedStatement stm = conexao.prepareStatement(sql);
			stm.setString(1, categoria.getTipocategoria());
			stm.executeUpdate();
			stm.close();
		}catch(SQLException ex){
			throw new RuntimeException(ex);
		}
		
	}


	@Override
	public void Desafiar(Desafio desafio) {
		try{
			Connection conexao = dataSource.getConnection();
			String sql = "insert into desafio (id_usuario_remetente, id_usuario_destinatario,"
					+ " id_forca, aposta) values (?, ?, ?, ?)";
			PreparedStatement stm = conexao.prepareStatement(sql);
			stm.setString(1, Integer.toString(desafio.getId_usuario_remetente()));
			stm.setString(2, Integer.toString(desafio.getId_usuario_destinatario()));
			stm.setString(3, Integer.toString(desafio.getId_forca()));
			stm.setString(4, Integer.toString(desafio.getAposta()));
			stm.executeUpdate();
			stm.close();
		}catch(SQLException ex){
			throw new RuntimeException(ex);
		}
		
	}


	@Override
	public void Notificar(int id_usuario, String texto) {
		try{
			Connection conexao = dataSource.getConnection();
			String sql = "insert into notificacao (id_usuario, texto) "
					+ "values (?,?)";
			PreparedStatement stm = conexao.prepareStatement(sql);
			stm.setInt(1, id_usuario);
			stm.setString(2, texto);
			stm.executeUpdate();
			stm.close();
		}catch(SQLException ex){
			throw new RuntimeException(ex);
		}
	}


	@Override
	public List<String> getNotificacoes(Integer id_usuario) {
		
		try{
			Connection conexao = dataSource.getConnection();
			String sql =  "select * from notificacao where id_usuario='"+id_usuario+"'" ;
			PreparedStatement stm = conexao.prepareStatement(sql);
			//stm.setInt(1 ,id_usuario);
			ResultSet rs = stm.executeQuery(sql);
			ArrayList<String> notificacoes = new ArrayList<>();
			while(rs.next()){
				String notificacao = rs.getString("texto");
				notificacoes.add(notificacao);
			}
			return notificacoes;
		}catch(SQLException ex){
			throw new RuntimeException(ex);
		}
	}


	@Override
	public void ForcaVitoria(int id_usuario, int pontos) {
		try{
			Connection conexao = dataSource.getConnection();
			String sql = "update pontuacao set pontos = pontos + ?, vitorias = vitorias + 1 "
					+ " where id = ?";
			PreparedStatement stm = conexao.prepareStatement(sql);
			stm.setInt(1, pontos);
			stm.setInt(2, id_usuario);
			stm.executeUpdate();
			stm.close();
		}catch(SQLException ex){
			throw new RuntimeException(ex);
		}
	}


	@Override
	public void ForcaDerrota(int id_usuario) {
		// TODO Auto-generated method stub
		
	}

}
