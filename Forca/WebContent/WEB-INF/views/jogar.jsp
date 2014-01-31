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
					alert('Você encontrou a letra: ' + data.letra);
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
						var novaURL = 'http://localhost:8080/spring/fimdejogo';
						$(window.document.location).attr('href',novaURL);
					}
				},
				error: function () {
					console.log('Deu erro');
				}
			});
			
			
			$("#letra").empty();
			$("#letra").append(letra);
			
			
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
	
		$(function(){
			mostrarTeclado();
			iniciar();
		});

	</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Responder</title>
</head>
<body>
Bem vindo ao jogo da forca! Ha hai!
<input type="text" name="palavra">
<input type="button" onClick="mostraTraco()">
<div id="naoApaga"> 
</div>
<div id="teclado">
</div>
<div id="letra">
</div>
<div id="palavra">
${tracos}
</div>
</body>
</html>