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
	
	public List<Usuario> getTodos() {
		try{
			Connection conexao = dataSource.getConnection();
			Statement stm = conexao.createStatement();
			String sql = "select * from usuarios";
			ResultSet rs = stm.executeQuery(sql);
			ArrayList<Usuario> usuarios = new ArrayList<>();
			while(rs.next()){
				Usuario u = new Usuario();
				u.setid( rs.getInt("id") );
				u.setNome(rs.getString("nome"));
				u.setlogin(rs.getString("login"));
				u.setemail(rs.getString("email"));
				u.setSenha(rs.getString("senha"));
				usuarios.add(u);
			}
			rs.close();
			stm.close();
			return usuarios;
		}catch(SQLException ex){
			throw new RuntimeException(ex);
		}
	}
	
	public void inserir(Usuario usuario) {
		try{
			Connection conexao = dataSource.getConnection();
			String sql = "insert into usuarios (nome, login, email, senha) "
					+ "values (?, ?, ?, ?)";
			PreparedStatement stm = conexao.prepareStatement(sql);
			stm.setString(1, usuario.getNome());
			stm.setString(2, usuario.getlogin());
			stm.setString(3, usuario.getemail());
			stm.setString(4, usuario.getSenha());
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
}
