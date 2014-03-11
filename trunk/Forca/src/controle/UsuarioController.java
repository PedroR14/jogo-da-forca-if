package controle;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import dominio.ComparadorRanking;
import dominio.EnviarNotificacoes;
import dominio.EnviarPesquisa;
import dominio.EnviarRanking;
import dominio.ForcaService;
import dominio.Notificacao;
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
	
	@RequestMapping(value="login") // É chamado quando o usuário acessa http://localhost:8080/spring/http://localhost:8080/spring/login onde "login" é o que aciona o método
	public String login(Model model,HttpSession session){
		
		if((Usuario)session.getAttribute("usuario") != null){
			return "redirect:/usuario/main";
		}
		model.addAttribute("usuario", new Usuario());
		return "index";
	}
		
	
	
	@RequestMapping(value="usuario/logar", method=RequestMethod.POST) // o @RequestMapping captura a URL enviada por algum arquivo, como os JSP dentro da pasta views
	public String logar(@Valid Usuario usuario, BindingResult result,
			Model model,HttpSession session) throws EmailException{

			List<Usuario> usuarios = service.getTodos(); // 
		
			for (int i = 0; i < usuarios.size(); i++) {
				if(usuarios.get(i).getlogin().equals(usuario.getlogin()) &&
						usuarios.get(i).getSenha().equals(usuario.getSenha())){
					session.setAttribute("usuario", usuarios.get(i));
					model.addAttribute("pontos", service_forca.getpontos(usuarios.get(i).getid()));
					model.addAttribute("categorias", service_forca.getTotas_categoria());
					return "redirect:/usuario/main";
				}
			}
			model.addAttribute("mensagem","Login ou senha incorretos");
			return "redirect:/login";
	}
	
	@RequestMapping(value="usuario/main")
	public String Principal(
			Model model,HttpSession session){
		if((Usuario)session.getAttribute("usuario") == null){
			return "redirect:/login";
		}
		Usuario usuario = (Usuario)session.getAttribute("usuario");
		model.addAttribute("notificacoes", service_forca.getNotificacoes(usuario.getid()));
		model.addAttribute("pontos", service_forca.getpontos(usuario.getid()));
		model.addAttribute("categorias", service_forca.getTotas_categoria());
		model.addAttribute("usuario",usuario);
		return "main";
	}
	
	@RequestMapping(value="usuario/sair")
	public String Logout(
			Model model,HttpSession session){
		if((Usuario)session.getAttribute("usuario") == null){
			return "redirect:/login";
		}
		session.removeAttribute("usuario");
		
		return "redirect:/login";
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
	
	@RequestMapping(value="usuario/abrirnotificacao")
	public @ResponseBody Notificacao AbrirNotificacao (Integer id_notificacao, Model model, HttpServletResponse response) throws IOException{
		service_forca.MarcarLida_Notificao(id_notificacao);
		return service_forca.getNotificacao_porid(id_notificacao);	
	}
	
	@RequestMapping(value="usuario/mostrarnotificacoes")
	public @ResponseBody EnviarNotificacoes MostrarNotificacoes (Model model, HttpServletResponse response, HttpSession session) throws IOException{

		
		Usuario usuario_logado = (Usuario)session.getAttribute("usuario");		
		
		return new EnviarNotificacoes(service_forca.getNotificacoes(usuario_logado.getid()));
	}
	
	@RequestMapping(value="ranking_data")
	public @ResponseBody EnviarRanking Ranking_data (String fim, String inicio, Model model, HttpServletResponse response,
			HttpSession session) throws IOException, ParseException{
		
		List<UsuarioPontos> usuario_pontos = usuario.gerar_ranking_data(service.getTodos(), usuario.data_sql(inicio), usuario.data_sql(fim));
		
		ComparadorRanking comparador = new ComparadorRanking();
		Collections.sort(usuario_pontos, comparador);
		
		List<Integer> pontos = new ArrayList<Integer>();
		
		
		List<String> usuarios_nomes = new ArrayList<String>();
		
		for (int i = 0; i < usuario_pontos.size(); i++) {
			usuarios_nomes.add(usuario.getNome_porId(usuario_pontos.get(i).getId_usuario()));
			pontos.add(usuario_pontos.get(i).getPontos());
		}
		
		
		return new EnviarRanking(usuarios_nomes,pontos);
	}
	
	@RequestMapping(value="usuario/ranking_dias")
	public @ResponseBody EnviarRanking Ranking_dias (Integer dias, Model model, HttpServletResponse response,
			HttpSession session) throws IOException, ParseException{
		
		List<UsuarioPontos> usuario_pontos = usuario.gerar_ranking_dias(service.getTodos(), dias);
		
		ComparadorRanking comparador = new ComparadorRanking();
		Collections.sort(usuario_pontos, comparador);
		
		List<Integer> pontos = new ArrayList<Integer>();
		List<String> usuarios_nomes = new ArrayList<String>();
		
		for (int i = 0; i < usuario_pontos.size(); i++) {
			usuarios_nomes.add(usuario.getNome_porId(usuario_pontos.get(i).getId_usuario()));
			pontos.add(usuario_pontos.get(i).getPontos());
		}
		
		
		return new EnviarRanking(usuarios_nomes,pontos);
	}
	
	@RequestMapping(value="usuario/pesquisa")
	public @ResponseBody EnviarPesquisa pesquisa_usuario (String login, Model model, HttpServletResponse response,
			HttpSession session) throws IOException, ParseException{
		
		List<Usuario> usuarios = service.getPor_Login(login);
		
		return new EnviarPesquisa(usuarios);
	}
	
	@RequestMapping(value="usuario/notificacoes")
	public String notificacoes(
			Model model,HttpSession session){
		
		if((Usuario)session.getAttribute("usuario") == null){
			return "redirect:/login";
		}
		
		Usuario usuario = (Usuario)session.getAttribute("usuario");
		model.addAttribute("notificacoes", service_forca.getNotificacoes(usuario.getid()));
		return "notificacoes";
	}
	
	@RequestMapping(value="usuario/forcas")
	public String forcas(
			Model model,HttpSession session){
		
		if((Usuario)session.getAttribute("usuario") == null){
			return "redirect:/login";
		}
		
		model.addAttribute("categorias", service_forca.getTotas_categoria());
		
		return "lista_forcas";
	}
	
	@RequestMapping(value="ranking")
	public String pagina_ranking(Model model){
	
		//model.addAttribute("usuario", new Usuario());
		
		return "Ranking";
	}
	
	@RequestMapping(value="usuarios")
	public String usuarios(Model model,HttpSession session){
		
		if((Usuario)session.getAttribute("usuario") == null){
			return "redirect:/login";
		}
		
		List<Usuario> usuarios = service.getTodos();
		model.addAttribute("usuarios", usuarios);
		
		return "usuarios";
	}
	
	
	
	
	@RequestMapping(value="usuario/editar")
	public String editar(@RequestParam("id") Integer id, 
			Model model,HttpSession session){
		try{
			
			if((Usuario)session.getAttribute("usuario") == null){
				return "redirect:/login";
			}
			
			Usuario usuarioEmEdicao = service.getPorid(id);
			model.addAttribute("usuario", usuarioEmEdicao);
			model.addAttribute("titulo", "Edição de Perfil");
			return "usuario_edicao";
		}
		catch(Exception ex){
			ex.printStackTrace();
			return "forward:/usuarios";
		}
	}
	
	@RequestMapping(value="usuario/excluir")
	public String excluir(@RequestParam("id") Integer id, 
			Model model,HttpSession session){
		try{
			if((Usuario)session.getAttribute("usuario") == null){
				return "redirect:/login";
			}
			service.excluir(id);
			model.addAttribute("mensagem", "Cliente excluído com sucesso.");
		}catch(Exception ex){
			ex.printStackTrace();
			model.addAttribute("mensagem", "Ocorreu um erro "
					+ "durante a tentativa de exclusão.");
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
				
				List<Usuario> usuarios = service.getTodos();
				
				for (int i = 0; i < usuarios.size(); i++) {
					if(usuarios.get(i).getlogin().equalsIgnoreCase(usuario.getlogin())){
						model.addAttribute("mensagemerroinsercao", "login já existe");
						return "index";
					}
					if(usuarios.get(i).getemail().equalsIgnoreCase(usuario.getemail())){
						model.addAttribute("mensagemerroinsercao", "email já existe");
						return "index";
					}
				}
						
				usuario.setid(usuario.gerar_id_usuario());
				
				service.inserir(usuario,0);
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
			model.addAttribute("mensagem", "Ocorreu um erro durante a operação.");
			ex.printStackTrace();
			return "redirect:/login";
		}
		
	}
	
	@RequestMapping(value="perfil_usuario", method=RequestMethod.GET)
	public String PaginaUsuario(@RequestParam("idusuario") Integer idusuario,Model model,HttpSession session){
		
		if((Usuario)session.getAttribute("usuario") == null){
			return "redirect:/login";
		}
		
		Usuario pag_usuario = service.getPorid(idusuario);
		model.addAttribute("perfil", pag_usuario);
		
		return "pagina_usuario";
	}
	
	@RequestMapping(value="lista_usuarios", method=RequestMethod.GET)
	public String ListarUsuarios(Model model,HttpSession session){
		
		if((Usuario)session.getAttribute("usuario") == null){
			return "redirect:/login";
		}
		
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
