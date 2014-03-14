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

import dominio.Categoria;
import dominio.EnviarResposta;
import dominio.Forca;
import dominio.ForcaService;
import dominio.InformacoesJogo;
import dominio.ListaForcas;
import dominio.ReportarJogador;
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
	
	@Autowired
	private Usuario usuario;
	
	private  String[] palavra_array;
	
	private int acertos;
	private int erros;
	private int aposta = 0;
	private boolean acabou = false;

	

	
	
	@RequestMapping(value="inserir_categoria")
	public String criarcategoria(Model model,HttpSession session){
		
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		
		if(usuario == null || usuario.getTipo_usuario() != 1){
			return "redirect:/main";
		}
		
		model.addAttribute("categorias", service.getTotas_categoria());
		model.addAttribute("categoria", new Categoria());
		
		return "criar_categoria";
	}
	
	@RequestMapping(value="jogar", method=RequestMethod.GET)
	public String jogar(@RequestParam("idforca") Integer idforca,Model model,HttpSession session){
		
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		
		if(usuario.getid() == service.getPorid_forca(idforca).getId_usuario()){
			return "redirect:/usuario/main";
		}
		
		int idusuario = usuario.getid();
		if(service.isDesafio(idforca)){
			int destinatario = service.getDestinatarioDesafio(idforca);
			if(destinatario == idusuario){
				aposta = service.getApostaDesafio(idforca);
				service.excluirdesafio(idforca);
			}else{
				return "redirect:/usuario/main";
			}
		}
		
		palavra_array = forca.gerar_array_letras(idforca);
		service.excluir(idforca);
		
		
		
		model.addAttribute("tracos",forca.gerar_tracos(palavra_array.length -1 ));
		
		return "jogar";
	}
	
	
	
	@RequestMapping(value="usuario/listaforcas")
	public @ResponseBody ListaForcas listar_forcas(@RequestParam("quant") Integer quant, Integer categoria, Model model,HttpSession session,HttpServletResponse response) throws IOException{
		Usuario usuario = (Usuario)session.getAttribute("usuario");
		boolean possui_prox = false;
		
		List<Forca> forcas;
		
		if(categoria == 0){
			forcas = service.getTodasForca(usuario.getid());
		}else{
			forcas = service.getPorcategoria_forca(categoria,usuario.getid());
		}
		
		List<Forca> forcas_part = forca.get_intervalo_forcas(quant, forcas);
		
		if(forca.possui_prox(quant, forcas)){
			possui_prox = true;
		}
		
		return new ListaForcas(forcas_part, possui_prox);
	}
	
	@RequestMapping(value="usuario/responder")
	public @ResponseBody InformacoesJogo responder(Model model, HttpServletResponse response) throws IOException{
			
		return new InformacoesJogo(palavra_array.length - 1);
	}
	
	@RequestMapping(value="usuario/verificar")
	public @ResponseBody EnviarResposta verificar(@RequestParam("letra") String letra,Model model, HttpServletResponse response) throws IOException{
		System.out.println(letra);
		int resultado = 0;
		acabou = false;
		
		int[] posicoes = null; 
		
		if(forca.conten_letra(letra, palavra_array)){
			posicoes = forca.posicoes(letra, palavra_array);
			for (int i = 0; i < posicoes.length; i++) {
				acertos++;
			}
			resultado = 1;
		}else{
			erros++;
		}
		
		
	
		if(acertos == (palavra_array.length-1) || erros == 6)
			acabou = true;
		
		return new EnviarResposta(letra,posicoes,resultado,acertos,erros,acabou);
	}
	
	@RequestMapping(value="usuario/fimdejogo")
	public @ResponseBody List<Integer> fimdejogo(Model model,HttpSession session){
		Usuario usuario = (Usuario)session.getAttribute("usuario");
		boolean desafio = true;
		List<Integer> retorno = new ArrayList<>();
		if(aposta == 0){
			desafio = false;
			aposta = 10;
		}
		
		if(acertos == (palavra_array.length-1)){
			model.addAttribute("mensagem", "Você ganhou "+aposta+"pts");
			service.ForcaVitoria(usuario.getid(), aposta);
			model.addAttribute("pontos", "Sua nova pontuação é "+service.getpontos(usuario.getid()));
			acertos = 0;
			erros = 0;
			retorno.add(1);
		}else{
			if(desafio == false){
				service.ForcaDerrota(usuario.getid(), 0);
			}else{
				service.ForcaDerrota(usuario.getid(), aposta);
			}
			model.addAttribute("mensagem", "Você perdeu cara");
			model.addAttribute("pontos", "Sua pontuação é "+service.getpontos(usuario.getid()));
			acertos = 0;
			erros = 0;
			retorno.add(0);
		}
		retorno.add(aposta);
		retorno.add(service.getpontos(usuario.getid()));
		
		aposta = 0;
		return retorno;
	}
	
	@RequestMapping(value="categoria/salvar", method=RequestMethod.POST)
	public String salvar(@Valid Categoria categoria, BindingResult result, 
			Model model,HttpSession session){
		if(result.hasErrors()){
			return "forward:/inserir_categoria";
		}
		
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		
		if(usuario == null || usuario.getTipo_usuario() != 1){
			return "redirect:/main";
		}
		
		List<Categoria> categorias = service.getTotas_categoria();
		
		for (int i = 0; i < categorias.size(); i++) {
			if(categorias.get(i).getTipocategoria().equalsIgnoreCase(categoria.getTipocategoria())){
				model.addAttribute("mensagem", "Categoria já existe!");
				return"forward:/inserir_categoria";
			}
		}
		
		service.CriarCategoria(categoria);
				
		return "redirect:/usuario/main";
	}
	
	@RequestMapping(value="criar_forca")
	public String criarforca(Model model){
		
		
		model.addAttribute("forca", new Forca());
		model.addAttribute("categoria", new Categoria());
		
		List<Categoria> categorias = service.getTotas_categoria();
		model.addAttribute("categorias", categorias);
		
		return "criar_forca";
	}
	
	@RequestMapping(value="usuario/forcasalvar")
	public @ResponseBody boolean salvarForca(String palavra, String dica, Integer cod_categoria, 
			Model model,HttpSession session){
		
		Forca forca_criada = new Forca();
		boolean val = false;
		
		if(palavra.length() < 2 || dica.length() < 5 || cod_categoria == 0){
			return val;
		}else{
			forca_criada.setPalavra(palavra);
			forca_criada.setDica(dica);
			forca_criada.setCod_categoria(cod_categoria);
		}
		
		
		
		Usuario usuario = (Usuario)session.getAttribute("usuario");
		
		
		forca_criada.setId_forca(forca.gerar_id_forca());
		
		
		forca_criada.setId_usuario(usuario.getid());
		forca_criada.setTem_desafio(0);
		service.CriarForca(forca_criada);
		val = true;
		return val;
	}
	
//	INICIO DE REPORTAR
	@RequestMapping(value="reportar_jogador")
	public String criarReportar(Model model){
		return "reportar_jogador";
	}
	
	@RequestMapping(value="usuario/reportarsalvar")
	public @ResponseBody boolean salvarReportar(int idJogador, int idForca, String observacao, 
			Model model,HttpSession session){
		
		ReportarJogador reportarJogador = new ReportarJogador(idJogador,idForca,observacao);
		
		boolean val = false;
		
		service.Reportar(reportarJogador);
		
		val = true;
		return val;
	}
	
//FIM REPORTAR	
	
	
	@RequestMapping(value="desafiar")
	public String desafio(@RequestParam("id_destin") Integer id_destin,Model model){
		
		model.addAttribute("forca", new Forca());
		
		List<Categoria> categorias = service.getTotas_categoria();
		int aposta = 0;
		
		model.addAttribute("aposta", aposta);
		model.addAttribute("usuario_destinatario", id_destin);
		model.addAttribute("categorias", categorias);
		
		return "desafio";
	}
	
	@RequestMapping(value="usuario/desafio/salvar", method=RequestMethod.POST)
	public @ResponseBody boolean salvardesafio(Integer id_destin,int aposta,
			 String palavra, String dica, Integer cod_categoria, Model model,
			 HttpSession session){
		
		Forca forca = new Forca();
		boolean val = false;
		
		if(palavra.length() < 2 || dica.length() < 5 || cod_categoria == 0){
			return val;
		}else{
			forca.setPalavra(palavra);
			forca.setDica(dica);
			forca.setCod_categoria(cod_categoria);
		}
		
		
		
		Usuario usuario_remetente = (Usuario)session.getAttribute("usuario");
		
		usuario.EnviarDesafio(forca, aposta, usuario_remetente, id_destin);
				
		val = true;
		return val;
	} 
	
}