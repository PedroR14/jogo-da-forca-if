package controle;

import java.io.IOException;
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
import dominio.JogarResposta;
import dominio.Usuario;
import dominio.UsuariosService;

@Controller
public class ForcaController {
	
	@Autowired
	private ForcaService service;
	
	@Autowired
	private UsuariosService service_usuario;
	
	
	@RequestMapping(value="inserir_categoria")
	public String criarcategoria(Model model){
	
		model.addAttribute("categoria", new Categoria());
		
		return "criar_categoria";
	}
	
	@RequestMapping(value="jogar")
	public String jogar(Model model){
	
		model.addAttribute("letra", "letra");
		
		return "jogar";
	}
	
	@RequestMapping(value="verificar")
	public @ResponseBody JogarResposta verificar(@RequestParam("letra") String letra,Model model, HttpServletResponse response) throws IOException{
		System.out.println(letra);
		model.addAttribute("letra", letra);
		
		return new JogarResposta(letra, 3);
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
	public String criaforca(Model model){
	
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
		
		List<Forca> forcas = service.getTodasForca();
		
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
		service.CriarForca(forca);
				
		return "main";
	}
	
	@RequestMapping(value="desafiar")
	public String desafio(Model model){
		
		model.addAttribute("forca", new Forca());
		
		List<Categoria> categorias = service.getTotas_categoria();
		String usuario_destinatario = null;
		int aposta = 0;
		
		model.addAttribute("aposta", aposta);
		model.addAttribute("usuario_destinatario", usuario_destinatario);
		model.addAttribute("categorias", categorias);
		
		return "desafio";
	}
	
	@RequestMapping(value="desafio/salvar", method=RequestMethod.POST)
	public String salvardesafio(@Valid Forca forca, BindingResult result, 
			String usuario_destinatario,int aposta,Model model,HttpSession session){
		
		if(result.hasErrors()){
			return "desafio";
		}
		
		Desafio desafio = new Desafio();
		AlgoritmoDerpofoldao derpofoldao = new AlgoritmoDerpofoldao();
		Usuario usuario = (Usuario)session.getAttribute("usuario");
		
		
		List<Forca> forcas = service.getTodasForca();
		List<Usuario> usuarios = service_usuario.getTodos();
		boolean usuario_existe = false;
		
		for (int i = 0; i < usuarios.size(); i++) {
			if(usuarios.get(i).getlogin().equals(usuario_destinatario)){
				desafio.setId_usuario_destinatario(usuarios.get(i).getid());
				usuario_existe = true;
			}
		}
		
		if(!usuario_existe){
			model.addAttribute("mensagem", "usuario não existe");
			return "desafio";
		}
		System.out.println(usuario_destinatario);
		
		
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
		
		desafio.setId_forca(id_forca);
		desafio.setAposta(aposta);
		desafio.setId_usuario_remetente(usuario.getid());
		
		forca.setId_usuario(usuario.getid());
		service.Desafiar(desafio);
		service.CriarForca(forca);
		service.Notificar(desafio.getId_usuario_destinatario(), "Você foi desafiado pelo usuario"+
		usuario.getlogin());
				
		return "main";
	} 
	
	
}