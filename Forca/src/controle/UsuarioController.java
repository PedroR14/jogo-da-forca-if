package controle;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.servlet.http.*;

import org.apache.commons.mail.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import dominio.AlgoritmoDerpofoldao;
import dominio.ComparadorRanking;
import dominio.EnviarRanking;
import dominio.Forca;
import dominio.ForcaService;
import dominio.InformacoesJogo;
import dominio.IniciarJogo;
import dominio.Usuario;
import dominio.UsuarioPontos;
import dominio.UsuariosService;

@Controller
public class UsuarioController {
	
	@Autowired
	private UsuariosService service;
	
	@Autowired
	private ForcaService service_forca;
	
	@Autowired
	private Usuario usuario;
	
	@RequestMapping(value="login")
	public String login(Model model){
	
		model.addAttribute("usuario", new Usuario());
		
		return "index";
	}
		
	
	
	@RequestMapping(value="usuario/logar", method=RequestMethod.POST)
	public String logar(@Valid Usuario usuario, BindingResult result,
			Model model,HttpSession session) throws EmailException{

			List<Usuario> usuarios = service.getTodos();
		
			for (int i = 0; i < usuarios.size(); i++) {
				if(usuarios.get(i).getlogin().equals(usuario.getlogin()) &&
						usuarios.get(i).getSenha().equals(usuario.getSenha())){
					model.addAttribute("usuario",usuarios.get(i));
					session.setAttribute("usuario", usuarios.get(i));
					model.addAttribute("pontos", service_forca.getpontos(usuarios.get(i).getid()));
					model.addAttribute("notificacoes", service_forca.getNotificacoes(usuario.getid()));
					return "main";
				}
			}
			model.addAttribute("mensagem","Login ou senha incorretos");
			return "forward:/login";
	}
	
	@RequestMapping(value="usuario/main")
	public String Principal(
			Model model,HttpSession session){
		if((Usuario)session.getAttribute("usuario") == null){
			return "login";
		}
		Usuario usuario = (Usuario)session.getAttribute("usuario");
		model.addAttribute("notificacoes", service_forca.getNotificacoes(usuario.getid()));
		model.addAttribute("pontos", service_forca.getpontos(usuario.getid()));
		model.addAttribute("usuario",usuario);
		return "main";
	}
	
	@RequestMapping(value="usuario/mostraranking")
	public @ResponseBody EnviarRanking Ranking (Model model, HttpServletResponse response) throws IOException{
		
		List<Integer> usuarios = service.getUsuario_Ranking();
		List<Integer> pontos = service.getPontos_Ranking();
		
		List<String> usuarios_nomes = new ArrayList<String>();
		
		for (int i = 0; i < usuarios.size(); i++) {
			usuarios_nomes.add(usuario.getNome_porId(usuarios.get(i)));
		}
		
		return new EnviarRanking(usuarios_nomes,pontos);
	}
	
	@RequestMapping(value="ranking_data")
	public @ResponseBody EnviarRanking Ranking_data (String fim, String inicio, Model model, HttpServletResponse response,
			HttpSession session) throws IOException, ParseException{
		
		List<Usuario> usuarios = service.getTodos();
		
		List<UsuarioPontos> usuario_pontos = usuario.gerar_ranking_data(service.getTodos(), usuario.data_sql(inicio), usuario.data_sql(fim));
		
		for (int i = 0; i < usuario_pontos.size(); i++) {
			System.out.println(usuario_pontos.get(i).getPontos()+" pts "+usuario_pontos.get(i).getId_usuario());
		}
		
		ComparadorRanking comparador = new ComparadorRanking();
		Collections.sort(usuario_pontos, comparador);
		
		for (int i = 0; i < usuario_pontos.size(); i++) {
			System.out.println("ordenado "+usuario_pontos.get(i).getPontos()+" pts "+usuario_pontos.get(i).getId_usuario());
		}
		
		List<Integer> pontos = service.getPontos_Ranking();
		
		
		List<String> usuarios_nomes = new ArrayList<String>();
		
		
		return new EnviarRanking(usuarios_nomes,pontos);
	}
	
	@RequestMapping(value="usuario/notificacoes")
	public String notificacoes(
			Model model,HttpSession session){
		Usuario usuario = (Usuario)session.getAttribute("usuario");
		model.addAttribute("notificacoes", service_forca.getNotificacoes(usuario.getid()));
		return "notificacoes";
	}
	
	@RequestMapping(value="ranking")
	public String pagina_ranking(Model model){
	
		//model.addAttribute("usuario", new Usuario());
		
		return "Ranking";
	}
	
	@RequestMapping(value="usuarios")
	public String usuarios(Model model){
		List<Usuario> usuarios = service.getTodos();
		model.addAttribute("usuarios", usuarios);
		
		return "usuarios";
	}
	
	
	
	
	@RequestMapping(value="usuario/editar")
	public String editar(@RequestParam("id") Integer id, 
			Model model){
		try{
			Usuario usuarioEmEdicao = service.getPorid(id);
			model.addAttribute("usuario", usuarioEmEdicao);
			model.addAttribute("titulo", "Edi��o de Perfil");
			return "usuario_edicao";
		}
		catch(Exception ex){
			ex.printStackTrace();
			return "forward:/usuarios";
		}
	}
	
	@RequestMapping(value="usuario/excluir")
	public String excluir(@RequestParam("id") Integer id, 
			Model model){
		
		try{
			service.excluir(id);
			model.addAttribute("mensagem", "Cliente exclu�do com sucesso.");
		}catch(Exception ex){
			ex.printStackTrace();
			model.addAttribute("mensagem", "Ocorreu um erro "
					+ "durante a tentativa de exclus�o.");
		}
		
		return "forward:/usuarios";
	}
	
	@RequestMapping(value="usuario/salvar", method=RequestMethod.POST)
	public String salvar(@Valid Usuario usuario, BindingResult result, 
			Model model){
		try{
			if(usuario.getid() == null){
				if(result.hasErrors()){
					return "index";
				}
				AlgoritmoDerpofoldao derpofoldao = new AlgoritmoDerpofoldao();
				
				List<Usuario> usuarios = service.getTodos();
				
				for (int i = 0; i < usuarios.size(); i++) {
					if(usuarios.get(i).getlogin().equalsIgnoreCase(usuario.getlogin())){
						model.addAttribute("mensagem erro insercao", "login j� existe");
						return "index";
					}
					if(usuarios.get(i).getemail().equalsIgnoreCase(usuario.getemail())){
						model.addAttribute("mensagemerroinsercao", "email j� existe");
						return "index";
					}
				}
				
				int id_usuario = derpofoldao.gerarNumero();
				int i = usuarios.size() - 1;
				if(usuarios.size() != 0){
					while(i>=0){
						if(id_usuario != usuarios.get(i).getid()){
							usuario.setid(id_usuario);
							i--;
						} else if (id_usuario == usuarios.get(i).getid()){
							id_usuario = derpofoldao.gerarNumero();
							i = usuarios.size() - 1;
						}
					}	
				}else{
					usuario.setid(id_usuario);
				}
				
				service.inserir(usuario,1);
				model.addAttribute("mensagem", "Usuario cadastrado com sucesso.");
				return "forward:/usuario/logar";
			}
			else{
				if(result.hasErrors()){
					return "usuario_edicao";
				}
				service.atualizar(usuario);
				model.addAttribute("mensagem", "Dados atualizados com sucesso.");
				return "forward:/usuario/logar";
			}
		}catch(Exception ex){
			model.addAttribute("mensagem", "Ocorreu um erro durante a opera��o.");
			ex.printStackTrace();
			return "forward:/usuarios";
		}
		
	}
	
	@RequestMapping(value="perfil_usuario", method=RequestMethod.GET)
	public String PaginaUsuario(@RequestParam("idusuario") Integer idusuario,Model model){
		
		Usuario pag_usuario = service.getPorid(idusuario);
		model.addAttribute("perfil", pag_usuario);
		
		return "pagina_usuario";
	}
	
	@RequestMapping(value="lista_usuarios", method=RequestMethod.GET)
	public String ListarUsuarios(Model model){
		
		List<Usuario> usuarios = service.getTodos();
		model.addAttribute("usuarios", usuarios);
		
		return "lista_usuarios";
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
		binder.registerCustomEditor(Date.class, 
				new CustomDateEditor(fmt, true));
	}
}
