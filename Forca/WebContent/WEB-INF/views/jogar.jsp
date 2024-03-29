<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<script src="<c:url value="/resources/css/jquery.js" />"></script>
	 <script type="text/javascript">

		var traco = " __ ";
		var tracos = [" "];
		var fim = false;
		           
		function iniciar(){
			var letra = 'a';
			
			$.ajax({
				url: 'responder',
				type: 'get',
				dataType: 'json',
				success: function (data) {
					gerartracos(data.tamanho);
				},
				error: function () {
					console.log('Deu erro');
				}
			});
	
			
		}
		
		function fim_de_jogo(){
			
			 $.ajax({
				url:'fimdejogo',
				type: 'post',
				dataType: 'json',
				success: function (data) {
					texto = '';
					listar_forcas(0);
			        $(".window").empty();
			        if(data[0] == 1){
			        	texto = texto + '<h2>Vit�ria</h2><br> <p> voc� ganhou '+ data[1]+' pts<br>Sua nova Pontua��o � '+data[2] +'</p>';
			        	texto = texto + '<button type="button"onclick="fechar_modal()" class="btn btn-default">Fechar</button>';
			        }
			        if(data[0] == 0){
			        	texto = texto + '<h2>Derrota</h2><br> <p> voc� perdeu <br>Sua Pontua��o � '+data[2] +'</p>';
			        	texto = texto + '<button type="button"onclick="fechar_modal()" class="btn btn-default">Fechar</button>';
			        }
			        $(".window").append(texto);
				},
				error: function () {
					console.log('Deu erro no fim');
				}
			});
			 
		}
		
		function gerartracos(quantidade){
			for (var i = 0; i < quantidade; i++){
				tracos[i] = traco;
			}
		}
		
		function modificartracos(posicao, substituta){
			tracos[posicao] = substituta;
			mostrartracos();
		}
		
		function mostrartracos(){
			$( "#palavra" ).empty();
			$( "#palavra" ).append(tracos);
		}
		
		function verificar(letra){
			
			
			
			$.ajax({
				url: 'verificar',
				type: 'get',
				dataType: 'json',
				data: {letra:letra},
				success: function (data) {
					alert('Voc� encontrou a letra: ' + data.letra);
					if(data.resultado > 0){
						if(data.lugar.length == 1){
							modificartracos(data.lugar - 1 ,letra);
						}
						if(data.lugar.length > 1){
							for (var i = 0; i < data.lugar.length; i++) {
								modificartracos(data.lugar[i] - 1, letra);
							}
						}
						
					}
					if(data.acabou == true){
						fim_de_jogo();
						fim = true;
					}
				},
				error: function () {
					console.log('Deu erro');
				}
			});
			
			
			$("#letra").empty();
			$("#letra").append(letra);
			
			
		}
		
		function fechar_modal(){
			 $("#mascara").hide();
		     $(".window").hide();
		}
	

		function mostrarTeclado(){

			var t;
			t = "<table id=tecladao cellpadding=3 cellspacing=6 border=1 width=390 height=90 bgcolor=#000000";
			t = t + "style='border: 1px solid #666666;'><tr style='border: 1px #0000000;'> <form name=f action=# onsubmit='return false;'>";
		var linha=0;
		for(i=65; i < 91; i++){
    	if(linha == 8) {
       		linha=0;
       		t = t + "</tr><tr style='border: 1px #000000;'>";
    	}
    		t = t + "<td align=center valign=middle width=15  style='border: 1px solid #000000;' bgcolor=#000000 ";
    		t = t + "onmouseover=style.backgroundColor='#FF0000;' onmouseout=style.backgroundColor='#000000' > ";
    		t = t + "<input type=submit name='" + String.fromCharCode(i)+"'  onclick=\"verificar('" + String.fromCharCode(i);
    		t = t + "');document.f."+ String.fromCharCode(i)+".style.display ='none';\" class=teclado value=" + String.fromCharCode(i) + " ></td>";
    	linha++;
		}
    		t = t + "</tr></form></table>";

    		$("#teclado").append(t);
		}
		
		 var tempo = 0;

		tempo = 30;
		 
		
		function startCountdown(){
		 
		    if((tempo - 1) >= 0){
		 
		        var seg = tempo%60;
				
		        if(seg <=9){
		
		            seg = "0"+seg;
		
		        }
		
		        TempoImprimivel = seg;
				
		        $("#sessao").html(TempoImprimivel);
		
				if(fim == false){
		        setTimeout('startCountdown()',1000);
				}
		        tempo--;
		
		    } else {
		    	fim_de_jogo();
		    }
		    
		    
		}
		
		if(fim == false){
			
			$(window).bind('beforeunload', function(){
				return '>>>>>>>>Aten��o<<<<<<<< \n Se voc� sair Essa partida ser� computada como derrota';
			});
		
			$(window).unload(function(){
				fim_de_jogo();
			});
			
		}
		

		$(function(){
			$(".filtro").hide();
			$("#menu_notificacoes").removeClass("active");
			$("#menu_forcas").removeClass("active");
			$("#menu_criar").removeClass("active");
			mostrarTeclado();
			iniciar();
			startCountdown();
		});
		
				 


	</script>
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/modal.css" />" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Responder</title>
</head>
<body>
<div id="naoApaga"> 
</div>
<div id="teclado">
</div>
<div id="letra">
</div>
<div id="palavra">
${tracos}
</div>
<div id="sessao"></div>
</body>
</html>