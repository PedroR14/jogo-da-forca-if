package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import dominio.Categoria;
import dominio.Desafio;
import dominio.Forca;
import dominio.ForcaRepositorio;
import dominio.Notificacao;
import dominio.ReportarJogador;

public class ForcaDAO implements ForcaRepositorio{
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private Forca forca;
	
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
			stm.setInt(6,forca.getTem_desafio());
			stm.executeUpdate();
			stm.close();
		}catch(SQLException ex){
			throw new RuntimeException(ex);
		}
		
	}

	public void Reportar(ReportarJogador reportarJogador){
		try{
			Connection conexao = dataSource.getConnection();
			String sql = "insert into reportar (id_forca, id_usuario, observacao_reportar) "
					+ "values (?,?,?)";
			PreparedStatement stm = conexao.prepareStatement(sql);
			stm.setString(1, Integer.toString(reportarJogador.getId_forca()));
			stm.setString(2, Integer.toString(reportarJogador.getId_usuario()));
			stm.setString(3, reportarJogador.getObservacao_reportar());
			stm.executeUpdate();
			stm.close();
		}catch(SQLException ex){
			throw new RuntimeException(ex);
		}
		
	}
	
	public void excluirForca(Integer id_forca) {
		
		try{
			Connection conexao = dataSource.getConnection();
			String sql = " delete from forcas where id_forca = "+id_forca+"";
			PreparedStatement stm = conexao.prepareStatement(sql);
			stm.executeUpdate();
			stm.close();
		}catch(SQLException ex){
			throw new RuntimeException(ex);
		}
		
	}

	
	public List<Forca> getTodasForca(Integer id_usuario) {
		try{
			Connection conexao = dataSource.getConnection();
			Statement stm = conexao.createStatement();
			String sql = "select * from forcas where id_usuario != "+id_usuario+" and ter_desfio = 0";
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
	
	public List<Forca> getTodasForca_semexcessao() {
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
		
		try{
			Connection conexao = dataSource.getConnection();
			Statement stm = conexao.createStatement();
			String sql = "select * from forcas where id_forca = "+id_forca+"";
			ResultSet rs = stm.executeQuery(sql);
			Forca forca = new Forca();
			while(rs.next()){
				forca.setId_forca( rs.getInt("id_forca") );
				forca.setId_usuario(rs.getInt("id_usuario"));
				forca.setCod_categoria(rs.getInt("cod_categoria"));
				forca.setDica(rs.getString("dica"));
				forca.setPalavra(rs.getString("palavra"));
				forca.setTem_desafio(rs.getInt("ter_desfio"));
			}
			rs.close();
			stm.close();
			return forca;
		}catch(SQLException ex){
			throw new RuntimeException(ex);
		}
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
	public void Notificar(int id_usuario, String cabecalho, String texto, String botoes, String tipo) {
		try{
			Date data = new Date();
			Connection conexao = dataSource.getConnection();
			String sql = "insert into notificacao (id_notificacao,id_usuario,visualizada,cabecalho,texto,botoes,data,tipo) "
					+ "values (?,?,?,?,?,?,?,?)";
			PreparedStatement stm = conexao.prepareStatement(sql);
			stm.setInt(1, forca.gerar_id_notificacao());
			stm.setInt(2, id_usuario);
			stm.setInt(3, 0);
			stm.setString(4, cabecalho);
			stm.setString(5, texto);
			stm.setString(6, botoes);
			stm.setDate(7,new java.sql.Date(data.getTime()));
			stm.setString(8, tipo);
			stm.executeUpdate();
			stm.close();
		}catch(SQLException ex){
			throw new RuntimeException(ex);
		}
	}


	@Override
	public List<Notificacao> getNotificacoes(Integer id_usuario) {
		
		try{
			Connection conexao = dataSource.getConnection();
			String sql =  "select * from notificacao where id_usuario='"+id_usuario+"'" ;
			PreparedStatement stm = conexao.prepareStatement(sql);
			//stm.setInt(1 ,id_usuario);
			ResultSet rs = stm.executeQuery(sql);
			ArrayList<Notificacao> notificacoes = new ArrayList<>();
			while(rs.next()){
				Notificacao notify = new Notificacao();
				notify.setId_notificacao(rs.getInt("id_notificacao"));
				notify.setId_usuario(rs.getInt("id_usuario"));
				notify.setCabecalho(rs.getString("cabecalho"));
				notify.setTexto(rs.getString("texto"));
				notify.setBotoes(rs.getString("botoes"));
				notify.setVisualizada(rs.getInt("visualizada"));
				notify.setData(rs.getDate("data"));
				notify.setTipo(rs.getString("tipo"));
				notificacoes.add(notify);
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
					+ " where id_usuario = ?";
			PreparedStatement stm = conexao.prepareStatement(sql);
			stm.setInt(1, pontos);
			stm.setInt(2, id_usuario);
			stm.executeUpdate();
			stm.close();
			salvar_historico(id_usuario, pontos, 1);
		}catch(SQLException ex){
			throw new RuntimeException(ex);
		}
	}


	@Override
	public void ForcaDerrota(int id_usuario, int pontos) {
		try{
			Connection conexao = dataSource.getConnection();
			String sql = "update pontuacao set pontos = pontos - ?, derrotas = derrotas + 1 "
					+ " where id_usuario = ?";
			PreparedStatement stm = conexao.prepareStatement(sql);
			stm.setInt(1, pontos);
			stm.setInt(2, id_usuario);
			stm.executeUpdate();
			stm.close();
			salvar_historico(id_usuario, pontos, 0);
		}catch(SQLException ex){
			throw new RuntimeException(ex);
		}
	}


	@Override
	public int getpontos(int id_usuario) {
		try{
			Connection conexao = dataSource.getConnection();
			Statement stm = conexao.createStatement();
			String sql = "select pontos from pontuacao where id_usuario = "+id_usuario+"";
			ResultSet rs = stm.executeQuery(sql);
			int pontos = 0;
			while(rs.next()){
				pontos = rs.getInt(1);
			}
			rs.close();
			stm.close();
			return pontos;
		}catch(SQLException ex){
			throw new RuntimeException(ex);
		}
	}


	public List<Forca> getPorcategoria_forca(Integer categoria,Integer id_usuario) {
		try{
			Connection conexao = dataSource.getConnection();
			Statement stm = conexao.createStatement();
			String sql = "select * from forcas where id_usuario != "+id_usuario+" and cod_categoria = "+categoria+" and ter_desfio = 0";
			ResultSet rs = stm.executeQuery(sql);
			ArrayList<Forca> forcas = new ArrayList<>();
			while(rs.next()){
				Forca forca = new Forca();
				forca.setId_forca( rs.getInt("id_forca") );
				forca.setId_usuario(rs.getInt("id_usuario"));
				forca.setCod_categoria(rs.getInt("cod_categoria"));
				forca.setDica(rs.getString("dica"));
				//forca.setPalavra(rs.getString("palavra"));
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
	
	public void salvar_historico(Integer id_usuario, Integer pontos, Integer vitoria){
		try{
			Date data = new Date();
			Connection conexao = dataSource.getConnection();
			String sql = "insert into historico (id_usuario, vitoria, pontos, data) "
					+ "values (?,?,?,?)";
			PreparedStatement stm = conexao.prepareStatement(sql);
			stm.setInt(1, id_usuario);
			stm.setInt(2, vitoria);
			stm.setInt(3, pontos);
			stm.setDate(4,new java.sql.Date(data.getTime()));
			stm.executeUpdate();
			stm.close();
		}catch(SQLException ex){
			throw new RuntimeException(ex);
		}
	}


	@Override
	public boolean isDesafio(Integer id_forca) {
		try{
			Connection conexao = dataSource.getConnection();
			Statement stm = conexao.createStatement();
			String sql = "select * from desafio";
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()){
				if(id_forca == rs.getInt("id_forca")){
					return true;
				}
			}
			rs.close();
			stm.close();
			return false;
		}catch(SQLException ex){
			throw new RuntimeException(ex);
		}
	}


	@Override
	public Integer getApostaDesafio(Integer id_forca) {
		try{
			Connection conexao = dataSource.getConnection();
			Statement stm = conexao.createStatement();
			String sql = "select * from desafio where id_forca = '"+id_forca+"'";
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()){
				return rs.getInt("aposta");				
			}
			rs.close();
			stm.close();
			return null;
		}catch(SQLException ex){
			throw new RuntimeException(ex);
		}
	}


	@Override
	public Integer getDestinatarioDesafio(Integer id_forca) {
		try{
			Connection conexao = dataSource.getConnection();
			Statement stm = conexao.createStatement();
			String sql = "select * from desafio where id_forca = '"+id_forca+"'";
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()){
				return rs.getInt("id_usuario_destinatario");				
			}
			rs.close();
			stm.close();
			return null;
		}catch(SQLException ex){
			throw new RuntimeException(ex);
		}
	}


	@Override
	public boolean VerificarId(Integer id_forca) {
		try{
			Connection conexao = dataSource.getConnection();
			Statement stm = conexao.createStatement();
			String sql = "select * from forcas where id_forca = "+id_forca+"";
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()){
				return false;
			}
			rs.close();
			stm.close();
			return true;
		}catch(SQLException ex){
			throw new RuntimeException(ex);
		}
	}


	@Override
	public boolean VerificarId_notificacao(Integer id_notificacao) {
		try{
			Connection conexao = dataSource.getConnection();
			Statement stm = conexao.createStatement();
			String sql = "select * from notificacao where id_notificacao = "+id_notificacao+"";
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()){
				return false;
			}
			rs.close();
			stm.close();
			return true;
		}catch(SQLException ex){
			throw new RuntimeException(ex);
		}
	}


	@Override
	public Notificacao getNotificacao_porid(Integer id_notificacao) {
		try{
			Connection conexao = dataSource.getConnection();
			Statement stm = conexao.createStatement();
			String sql = "select * from notificacao where id_notificacao = "+id_notificacao+"";
			ResultSet rs = stm.executeQuery(sql);
			Notificacao notify = new Notificacao();
			while(rs.next()){
				notify.setId_notificacao(rs.getInt("id_notificacao"));
				notify.setId_usuario(rs.getInt("id_usuario"));
				notify.setCabecalho(rs.getString("cabecalho"));
				notify.setTexto(rs.getString("texto"));
				notify.setBotoes(rs.getString("botoes"));
				notify.setVisualizada(rs.getInt("visualizada"));
				notify.setData(rs.getDate("data"));
				notify.setTipo(rs.getString("tipo"));
			}
			rs.close();
			stm.close();
			return notify;
		}catch(SQLException ex){
			throw new RuntimeException(ex);
		}
	}


	@Override
	public void MarcarLida_Notificao(Integer id_notificacao) {
		try{
			Connection conexao = dataSource.getConnection();
			String sql = "update notificacao set visualizada = ? where id_notificacao = ?";
			PreparedStatement stm = conexao.prepareStatement(sql);
			stm.setInt(1, 1);
			stm.setInt(2, id_notificacao);
			stm.executeUpdate();
			stm.close();
		}catch(SQLException ex){
			throw new RuntimeException(ex);
		}
	}


	@Override
	public void excluirdesafio(Integer id_forca) {
		try{
			Connection conexao = dataSource.getConnection();
			String sql = " delete from desafio where id_forca = "+id_forca+"";
			PreparedStatement stm = conexao.prepareStatement(sql);
			stm.executeUpdate();
			stm.close();
		}catch(SQLException ex){
			throw new RuntimeException(ex);
		}
		
	}


	@Override
	public void excluirnotificacao(Integer id_notificacao) {
		try{
			Connection conexao = dataSource.getConnection();
			String sql = " delete from notificacao where id_notificacao = "+id_notificacao+"";
			PreparedStatement stm = conexao.prepareStatement(sql);
			stm.executeUpdate();
			stm.close();
		}catch(SQLException ex){
			throw new RuntimeException(ex);
		}
		
	}


	@Override
	public Desafio get_desafio_idforca(Integer id_forca) {
		try{
			Connection conexao = dataSource.getConnection();
			Statement stm = conexao.createStatement();
			String sql = "select * from desafio where id_forca = "+id_forca+"";
			ResultSet rs = stm.executeQuery(sql);
			Desafio desafio = new Desafio();
			while(rs.next()){
				desafio.setId_forca( rs.getInt("id_forca") );
				desafio.setId_usuario_destinatario(rs.getInt("id_usuario_destinatario"));
				desafio.setId_usuario_remetente(rs.getInt("id_usuario_remetente"));
				desafio.setAposta(rs.getInt("aposta"));
			}
			rs.close();
			stm.close();
			return desafio;
		}catch(SQLException ex){
			throw new RuntimeException(ex);
		}
	}

}
