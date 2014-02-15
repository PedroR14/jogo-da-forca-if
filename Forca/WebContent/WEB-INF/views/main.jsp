<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	
	<script src="<c:url value="/resources/css/jquery.js" />"></script>
	<script type="text/javascript">
	
	
		
	
		var quant = 0;
		
		function ranking_dias(dias){
			
			 $.ajax({
				url:'ranking_dias',
				type: 'post',
				dataType: 'json',
				data: {dias:dias},
				success: function (data) {
			
				},
				error: function () {
					console.log('Deu erro');
				}
			});
			 
		}
		
		
		
		function listar_forcas(){
		
			var forcas = [];
		
			$.ajax({
				url:'listaforcas',
				type: 'get',
				dataType: 'json',
				data: {quant:quant},
				success: function (data) {
					forcas = data.forcas;
					var texto = '';
					for(var i=0; i < forcas.length; i++){
						texto = texto +  data.forcas[i].dica +'<br>'+'<a href="/spring/jogar?idforca='+data.forcas[i].id_forca+'">Responder</a>'+'<br><hr>';
					}
				
			    		texto = texto + '<div class = botoes>';
			    		texto = texto + '<ul class="pager">';
			    		if(quant != 0){
			    			texto = texto + '<li class="previous" onclick = "voltar_pagina()" ><a>&larr; Anterior</a></li> ';
			    		}
			    		if(forcas.length == 5 && data.possui_prox == true){
			    			texto = texto + '<li class="next" onclick = "passar_pagina()" ><a>Proxima &rarr; </a></li> ';
			    		}
			    		texto = texto + '</ul>';
			    		texto = texto + '</div>';
						$(".forcas").append(texto);
				},
				error: function () {
					console.log('Deu erro');
				}
			});
		
		}
		
	
		function passar_pagina(){
			$(".forcas").empty();
			quant = quant + 5;
			listar_forcas();
		}
	
		function voltar_pagina(){
			$(".forcas").empty();
			quant = quant - 5;
			listar_forcas();
		}
		
		function mostrar_dias(dias){
			$.ajax({
				url:'ranking_dias',
				type: 'get',
				data: {dias:dias},
				dataType: 'json',
				success: function (data) {
					if(dias == 8){
						var texto = '<strong>Top 5 Semanal</strong><br><hr>';
					}else{
						var texto = '<strong>Top 5 Mensal</strong><br><hr>';
					}
					for(var i=0; i < 5; i++){
						if(data.usuarios[i] != null){
							texto = texto +  data.usuarios[i] +'  ' + data.pontos[i]+'<br><hr>';
						}
					}
					texto = texto + '<button type="submit" class="btn btn-primary" value="MaisRanking">Ver Mais Ranking</button>';
					$("#usuarios").empty();
					$("#usuarios").append(texto);
				},
				error: function () {
					console.log('Deu erro');
				}
			});
		}
		
		function mostrar_geral(){
			$.ajax({
				url:'mostraranking',
				type: 'get',
				dataType: 'json',
				success: function (data) {
					var texto = '<strong>Top 5 Geral</strong><br><hr>';
					for(var i=0; i < 5; i++){
						if(data.usuarios[i] != null){
							texto = texto +  data.usuarios[i] +'  ' + data.pontos[i]+'<br><hr>';
						}
					}
					texto = texto + '<button type="submit" class="btn btn-primary" value="MaisRanking">Ver Mais Ranking</button>';
					$("#usuarios").empty();
					$("#usuarios").append(texto);
				},
				error: function () {
					console.log('Deu erro');
				}
			});
		}
		
		
		$(function(){
			quant = 0;
			listar_forcas();
			mostrar_geral();
			$("#notificacoes").load('http://localhost:8080/spring/usuario/notificacoes');
		});
	</script>
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.css" />" />
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/main.css" />" />
	<title>Principal</title>
</head>
<body>
	<c:url var="url" value="/usuario"/>
	<div id=imagem>
		<img src="#" alt="#" class="img-thumbnail">
	</div>
	<header>
		<nav class="navbar navbar-inverse" role="navigation">
			<div class="navbar-header">
    			<h1> Jogo da Forca </h1>
    		</div>
    			<h4 class="navbar-brand">Olá,${sessionScope.usuario.nome}, você possui ${pontos} pts<h4>
    			<a href="${url}/editar?id=${sessionScope.usuario.id}">EDITAR PERFIL</a>
		</nav>
  	</header>
  	<div class="criar">
  		<button type="submit" class="btn btn-primary" value="Login">Criar Forca</button>
  	</div>

	<input type="text" id="pesquisa">
<c:url var="url3" value="/criar_forca"/>
<c:url var="url4" value="/ranking"/>
<c:url var="url2" value="/inserir_categoria"/>

<a href="${url3}">Criar forca</a>
<a href="${url4}">Ranking</a>

	<c:if test = "${usuario.tipo_usuario == 1}">
		<a href="${url2}">Categoria</a>
	</c:if>
	

<!-- <div id=notificacoes></div> -->
<div class = forcas ></div>
<div class="btn-group">
  <button type="button" onclick = "mostrar_dias(8)" class="btn btn-default">Semana</button>
  <button type="button" onclick = "mostrar_dias(30)" class="btn btn-default">Mês</button>
  <button type="button" onclick = "mostrar_geral()" class="btn btn-default">Geral</button>
</div>
<div id=usuarios ></div>

	
	<footer class="nav navbar-inverse navbar-fixed-bottom">
		<p>Jogo da Forca &copy 2014</p>
	</footer>
	
	 
	<script>
	$("#pesquisa").focusin(function(){
		alert("focou");
	});
	</script>
</body>
</html>