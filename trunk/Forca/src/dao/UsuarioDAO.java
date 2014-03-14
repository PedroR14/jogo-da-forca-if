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

import dominio.Usuario;
import dominio.UsuarioRepositorio;

public class UsuarioDAO implements UsuarioRepositorio {
	
	@Autowired
	private DataSource dataSource;
	
	public List<Usuario> getTodos() { // List<Usuario> é o tipo de retorno, getTodos() é o nome do método. Ele busca todos os usuários. É chamado na hora do login
		try{
			Connection conexao = dataSource.getConnection();
			Statement stm = conexao.createStatement();
			String sql = "select * from usuarios";
			ResultSet rs = stm.executeQuery(sql);
			ArrayList<Usuario> usuarios = new ArrayList<>(); // Cria um arrayList que só recebe objetos tipo Usuário
			while(rs.next()){
				Usuario u = new Usuario();
				u.setid( rs.getInt("id") ); // "id" é o nome da coluna na tabela usuarios
				u.setNome(rs.getString("nome"));
				u.setlogin(rs.getString("login"));
				u.setemail(rs.getString("email"));
				u.setSenha(rs.getString("senha"));
				u.setTipo_usuario(rs.getInt("tipo_usuario"));
				usuarios.add(u);
			}
			rs.close(); // fecha o objeto que contem os resultados
			stm.close(); // fecha a conexão
			return usuarios; // É o arrayList que contem todos os objetos Usuário que foram guardados no banco
		}catch(SQLException ex){
			throw new RuntimeException(ex);
		}
	}
	
	public void inserir(Usuario usuario, Integer tipo_usuario) {
		try{
			Connection conexao = dataSource.getConnection();
			String sql = "insert into usuarios (id ,nome, login, email, senha, tipo_usuario) values (? ,?, ?, ?, ?, ?)";
			PreparedStatement stm = conexao.prepareStatement(sql);
			stm.setInt(1, usuario.getid());
			stm.setString(2, usuario.getNome());
			stm.setString(3, usuario.getlogin());
			stm.setString(4, usuario.getemail());
			stm.setString(5, usuario.getSenha());
			stm.setInt(6, tipo_usuario);
			stm.executeUpdate();
			stm.close();
			inserir_pontuacao(usuario.getid());
		}catch(SQLException ex){
			throw new RuntimeException(ex);
		}
	}
	
	public void inserir_pontuacao(int id_usuario) {
		try{
			Connection conexao = dataSource.getConnection();
			String sql = "insert into pontuacao (id_usuario) value (?)";
			PreparedStatement stm = conexao.prepareStatement(sql);
			stm.setInt(1, id_usuario);
			stm.executeUpdate();
			stm.close();
		}catch(SQLException ex){
			throw new RuntimeException(ex);
		}
	}
	
	public void atualizar(Usuario usuario) {
		try{
			Connection conexao = dataSource.getConnection();
			String sql = "update usuarios set nome = ?, login = ?, "
					+ "email = ?, senha = ? where id = ?";
			PreparedStatement stm = conexao.prepareStatement(sql);
			stm.setString(1, usuario.getNome());
			stm.setString(2, usuario.getlogin());
			stm.setString(3, usuario.getemail());
			stm.setString(4, usuario.getSenha());
			stm.setInt(5, usuario.getid());
			stm.executeUpdate();
			stm.close();
		}catch(SQLException ex){
			throw new RuntimeException(ex);
		}
	}
	
	public void excluir(Integer idUsuario) {
		try{
			Connection conexao = dataSource.getConnection();
			String sql = "delete from usuarios where id = ?";
			PreparedStatement stm = conexao.prepareStatement(sql);
			stm.setInt(1, idUsuario);
			stm.executeUpdate();
			stm.close();
		}catch(SQLException ex){
			throw new RuntimeException(ex);
		}
	}
	
	public Usuario getPorid(Integer idusuarios) {
		try{
			Connection conexao = dataSource.getConnection();
			String sql = "select * from usuarios where id = ?";
			PreparedStatement stm = conexao.prepareStatement(sql);
			stm.setInt(1, idusuarios);
			ResultSet rs = stm.executeQuery();
			while(rs.next()){
				Usuario u = new Usuario();
				u.setid( rs.getInt("id") );
				u.setlogin( rs.getString("login") );
				u.setNome( rs.getString("nome") );
				u.setemail(rs.getString("email"));
				u.setSenha(rs.getString("senha"));
				rs.close();
				stm.close();
				return u;
			}
			return null;
		}catch(SQLException ex){
			throw new RuntimeException(ex);
		}
	}

	@Override
	public List<Integer> getPontos_Ranking() {
		try{
			Connection conexao = dataSource.getConnection();
			Statement stm = conexao.createStatement();
			String sql = "select * from pontuacao ORDER BY pontos DESC";
			ResultSet rs = stm.executeQuery(sql);
			ArrayList<Integer> pontos = new ArrayList<>();
			while(rs.next()){
				pontos.add( rs.getInt("pontos"));
			}
			rs.close();
			stm.close();
			return pontos;
		}catch(SQLException ex){
			throw new RuntimeException(ex);
		}
	}

	@Override
	public List<Integer> getUsuario_Ranking() {
		try{
			Connection conexao = dataSource.getConnection();
			Statement stm = conexao.createStatement();
			String sql = "select * from pontuacao ORDER BY pontos DESC";
			ResultSet rs = stm.executeQuery(sql);
			ArrayList<Integer> usuarios = new ArrayList<>();
			while(rs.next()){
				usuarios.add( rs.getInt("id_usuario"));
			}
			rs.close();
			stm.close();
			return usuarios;
		}catch(SQLException ex){
			throw new RuntimeException(ex);
		}
	}

	public Integer getRanking_Data(Integer id_usuario,String inicio, String fim) {
		try{
			System.out.println("funçaao");
			Connection conexao = dataSource.getConnection();
			String sql = "select sum(pontos) from historico where data BETWEEN '"+inicio+"' and '"+fim+"' and id_usuario = "+id_usuario;
			PreparedStatement stm = conexao.prepareStatement(sql);
			ResultSet rs = stm.executeQuery(sql);
			Integer pontos = 0;
			while(rs.next()){
				pontos = rs.getInt("sum(pontos)");
			}
			System.out.println(pontos);
			rs.close();
			stm.close();
			return pontos;
		}catch(SQLException ex){
			throw new RuntimeException(ex);
		}
	}

	@Override
	public Integer getRanking_Dias(Integer id_usuario, Integer dias) {
		try{
			Connection conexao = dataSource.getConnection();
			String sql = "SELECT sum(pontos) FROM historico WHERE id_usuario="+id_usuario+" and data BETWEEN CURDATE() - INTERVAL "+dias+" DAY AND CURDATE()";
			PreparedStatement stm = conexao.prepareStatement(sql);
			ResultSet rs = stm.executeQuery(sql);
			Integer pontos = 0;
			while(rs.next()){
				pontos = rs.getInt("sum(pontos)");
			}
			rs.close();
			stm.close();
			return pontos;
		}catch(SQLException ex){
			throw new RuntimeException(ex);
		}
	}
	
	@Override
	public List<Usuario> getPor_Login(String login, Integer id_usuario) {
		try{
			Connection conexao = dataSource.getConnection();
			String sql = "SELECT * FROM usuarios WHERE login like'"+login+"%' and id !="+id_usuario;
			PreparedStatement stm = conexao.prepareStatement(sql);
			ResultSet rs = stm.executeQuery(sql);
			ArrayList<Usuario> usuarios = new ArrayList<>();
			while(rs.next()){
				Usuario u = new Usuario();
				u.setid( rs.getInt("id") );
				u.setNome(rs.getString("nome"));
				u.setlogin(rs.getString("login"));
				u.setemail(rs.getString("email"));
				usuarios.add(u);
			}
			rs.close();
			stm.close();
			return usuarios;
		}catch(SQLException ex){
			throw new RuntimeException(ex);
		}
	}

	@Override
	public boolean VerificarId(Integer id_usuario) {
		try{
			Connection conexao = dataSource.getConnection();
			Statement stm = conexao.createStatement();
			String sql = "select * from usuarios where id = "+id_usuario+"";
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
	public Integer count_vitorias(Integer id_usuario) {
		try{
			Connection conexao = dataSource.getConnection();
			String sql = "SELECT count(*) FROM historico WHERE id_usuario="+id_usuario+" and vitoria=1";
			PreparedStatement stm = conexao.prepareStatement(sql);
			ResultSet rs = stm.executeQuery(sql);
			Integer pontos = 0;
			while(rs.next()){
				pontos = rs.getInt("count(*)");
			}
			rs.close();
			stm.close();
			return pontos;
		}catch(SQLException ex){
			throw new RuntimeException(ex);
		}
	}

	@Override
	public Integer count_derrotas(Integer id_usuario) {
		try{
			Connection conexao = dataSource.getConnection();
			String sql = "SELECT count(*) FROM historico WHERE id_usuario="+id_usuario+" and vitoria=0";
			PreparedStatement stm = conexao.prepareStatement(sql);
			ResultSet rs = stm.executeQuery(sql);
			Integer pontos = 0;
			while(rs.next()){
				pontos = rs.getInt("count(*)");
			}
			rs.close();
			stm.close();
			return pontos;
		}catch(SQLException ex){
			throw new RuntimeException(ex);
		}
	}
	
	@Override
	public Integer count_notificacoes(Integer id_usuario) {
		try{
			Connection conexao = dataSource.getConnection();
			String sql = "SELECT count(*) FROM notificacao WHERE id_usuario="+id_usuario+" and visualizada=0";
			PreparedStatement stm = conexao.prepareStatement(sql);
			ResultSet rs = stm.executeQuery(sql);
			Integer total = 0;
			while(rs.next()){
				total = rs.getInt("count(*)");
			}
			rs.close();
			stm.close();
			return total;
		}catch(SQLException ex){
			throw new RuntimeException(ex);
		}
	}
	
	
}
