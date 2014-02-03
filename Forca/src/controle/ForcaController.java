package controle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import dominio.AlgoritmoDerpofoldao;
import dominio.Categoria;
import dominio.Desafio;
import dominio.Forca;
import dominio.ForcaService;
import dominio.InformacoesJogo;
import dominio.IniciarJogo;
import dominio.EnviarResposta;
import dominio.Usuario;
import dominio.UsuariosService;

@Controller
public class ForcaController {
	
	@Autowired
	private ForcaService service;
	
	@Autowired
	private UsuariosService service_usuario;
	
	@Autowired
	private Forca forca;
	
	private  String[] palavra_array;
	
	private int acertos;
	private int erros;
	private boolean acabou = false;

	

	
	
	@RequestMapping(value="inserir_categoria")
	public String criarcategoria(Model model){
	
		model.addAttribute("categoria", new Categoria());
		
		return "criar_categoria";
	}
	
	@RequestMapping(value="jogar", method=RequestMethod.GET)
	public String jogar(@RequestParam("idforca") Integer idforca,Model model){
		
		palavra_array = forca.gerar_array_letras(idforca);
		service.excluir(idforca);
		
		model.addAttribute("tracos",forca.gerar_tracos(palavra_array.length -1 ));
		
		return "jogar";
	}
	
	
	
	@RequestMapping(value="listaforcas")
	public String notificacoes(
			Model model,HttpSession session){
		Usuario usuario = (Usuario)session.getAttribute("usuario");
		model.addAttribute("forcas", service.getTodasForca(usuario.getid()));
		return "lista_forcas";
	}
	
	@RequestMapping(value="responder")
	public @ResponseBody InformacoesJogo responder(Model model, HttpServletResponse response) throws IOException{
			
		return new InformacoesJogo(palavra_array.length - 1);
	}
	
	@RequestMapping(value="verificar")
	public @ResponseBody EnviarResposta verificar(@RequestParam("letra") String letra,Model model, HttpServletResponse response) throws IOException{
		System.out.println(letra);
		int resultado = 0;
		acabou = false;
		
		if(forca.conten_letra(letra, palavra_array)){
			acertos++;
			resultado = 1;
		}else{
			erros++;
		}
		
		int[] posicoes = forca.posicoes(letra, palavra_array);
	
		if(acertos == (palavra_array.length-1) || erros == 6)
			acabou = true;
		
		return new EnviarResposta(letra,posicoes,resultado,acertos,erros,acabou);
	}
	
	@RequestMapping(value="fimdejogo")
	public String fimdejogo(Model model,HttpSession session){
		Usuario usuario = (Usuario)session.getAttribute("usuario");
		if(acertos == (palavra_array.length-1)){
			model.addAttribute("mensagem", "Voc� ganhou 10pts");
			service.ForcaVitoria(usuario.getid(), 10);
			model.addAttribute("pontos", "Sua nova pontua��o � "+service.getpontos(usuario.getid()));
			acertos = 0;
			erros = 0;
		}else{
			model.addAttribute("mensagem", "Voc� perdeu cara");
			model.addAttribute("pontos", "Sua pontua��o � "+service.getpontos(usuario.getid()));
			acertos = 0;
			erros = 0;
		}
		
		
		return "Fimdejogo";
	}
	
	@RequestMapping(value="categoria/salvar", method=RequestMethod.POST)
	public String salvar(@Valid Categoria categoria, BindingResult result, 
			Model model){
		if(result.hasErrors()){
			return "criar_categoria";
		}
		
		service.CriarCategoria(categoria);
				
		return "main";
	}
	
	@RequestMapping(value="criar_forca")
	public String criarforca(Model model){
		
		model.addAttribute("forca", new Forca());
		model.addAttribute("categoria", new Categoria());
		
		List<Categoria> categorias = service.getTotas_categoria();
		model.addAttribute("categorias", categorias);
		
		return "criar_forca";
	}
	
	@RequestMapping(value="forca/salvar", method=RequestMethod.POST)
	public String salvarForca(@Valid Forca forca, BindingResult result, 
			Model model,HttpSession session){
		if(result.hasErrors()){
			
			return "criar_forca";
			
		}
		
		AlgoritmoDerpofoldao derpofoldao = new AlgoritmoDerpofoldao();
		Usuario usuario = (Usuario)session.getAttribute("usuario");
		
		List<Forca> forcas = service.getTodasForca_semexcessao();
		
		int id_forca = derpofoldao.gerarNumero();
		int i = forcas.size() - 1;
		if(forcas.size() != 0){
			while(i>=0){
				if(id_forca != forcas.get(i).getId_forca()){
					forca.setId_forca(id_forca);
					i--;
				} else if (id_forca == forcas.get(i).getId_forca()){
					id_forca = derpofoldao.gerarNumero();
					i = forcas.size() - 1;
				}
			}	
		}else{
			forca.setId_forca(id_forca);
		}
		
		forca.setId_usuario(usuario.getid());
		forca.setTem_desafio(0);
		service.CriarForca(forca);
				
		return "main";
	}
	
	@RequestMapping(value="desafiar")
	public String desafio(@RequestParam("id_destin") Integer id_destin,Model model){
		
		model.addAttribute("forca", new Forca());
		
		List<Categoria> categorias = service.getTotas_categoria();
		String usuario_destinatario = null;
		int aposta = 0;
		
		model.addAttribute("aposta", aposta);
		model.addAttribute("usuario_destinatario", id_destin);
		model.addAttribute("categorias", categorias);
		
		return "desafio";
	}
	
	@RequestMapping(value="desafio/salvar", method=RequestMethod.POST)
	public String salvardesafio(@Valid Forca forca, BindingResult result, 
			@RequestParam("id_destin") Integer id_destin,int aposta,Model model,HttpSession session){
		
		if(result.hasErrors()){
			return "desafio";
		}
		
		Desafio desafio = new Desafio();
		AlgoritmoDerpofoldao derpofoldao = new AlgoritmoDerpofoldao();
		Usuario usuario = (Usuario)session.getAttribute("usuario");
		
		
		List<Forca> forcas = service.getTodasForca_semexcessao();
		List<Usuario> usuarios = service_usuario.getTodos();
		
				desafio.setId_usuario_destinatario(id_destin);
		
		
		int id_forca = derpofoldao.gerarNumero();
		int i = forcas.size() - 1;
		if(forcas.size() != 0){
			while(i>=0){
				if(id_forca != forcas.get(i).getId_forca()){
					forca.setId_forca(id_forca);
					i--;
				} else if (id_forca == forcas.get(i).getId_forca()){
					id_forca = derpofoldao.gerarNumero();
					i = forcas.size() - 1;
				}
			}	
		}else{
			forca.setId_forca(id_forca);
		}
		forca.setTem_desafio(1);
		desafio.setId_forca(id_forca);
		desafio.setAposta(aposta);
		desafio.setId_usuario_remetente(usuario.getid());
		
		forca.setId_usuario(usuario.getid());
		service.Desafiar(desafio);
		service.CriarForca(forca);
		service.Notificar(desafio.getId_usuario_destinatario(), "Voc� foi desafiado pelo usuario"+
		usuario.getlogin());
				
		return "main";
	} 
	
	
}