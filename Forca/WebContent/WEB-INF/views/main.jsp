<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	
	<script src="<c:url value="/resources/css/jquery.js" />"></script>
	
	<script type="text/javascript">
		
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
					$(".list-rank").empty();
					$(".list-rank").append(texto);
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
					$(".list-rank").empty();
					$(".list-rank").append(texto);
				},
				error: function () {
					console.log('Deu erro');
				}
			});
		}
		
		function exibir_notificacoes(){
			$(".forcas").empty();
			$(".filtro").hide();
			$(".forcas").load('http://localhost:8080/spring/usuario/notificacoes');
			$("#menu_notificacoes").addClass("active");
			$("#menu_forcas").removeClass("active");
			$("#menu_criar").removeClass("active");
			
		}
		
		function exibir_forcas(){
			$(".forcas").empty();
			$(".filtro").show();
			$(".forcas").load('http://localhost:8080/spring/usuario/forcas');
			$("#menu_notificacoes").removeClass("active");
			$("#menu_forcas").addClass("active");
			$("#menu_criar").removeClass("active");
			$("#menu_reportar").removeClass("active");
		}
		
		function exibir_criar(){
			$(".forcas").empty();
			$(".filtro").hide();
			$(".forcas").load('http://localhost:8080/spring/criar_forca');
			$("#menu_notificacoes").removeClass("active");
			$("#menu_forcas").removeClass("active");
			$("#menu_criar").addClass("active");
			$("#menu_reportar").removeClass("active");
		}
		
		function exibir_reportar(){
			$(".forcas").empty();
			$(".filtro").hide();
			$(".forcas").load('http://localhost:8080/spring/reportar_jogador');
			$("#menu_notificacoes").removeClass("active");
			$("#menu_forcas").removeClass("active");
			$("#menu_criar").removeClass("active");
			$("#menu_reportar").addClass("active");
			
		}
		
		$(function(){
			$(".forcas").load('http://localhost:8080/spring/usuario/forcas');
			mostrar_geral();
		});
	</script>
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.css" />" />
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/main.css" />" />
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/modal.css" />" />
	<title>Principal</title>
</head>
<body>
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="navbar-header">
    		<a href="#" class="navbar-brand"><h2>Jogo da Forca</h2></a>
    	</div>
    	<h4 class="navbar-brand navbar-right">Olá,${sessionScope.usuario.nome}, você possui ${pontos} pts<br>
    	<a href="/spring/usuario/sair">SAIR</a>
    	<a class="navbar-right" href="/spring/editar?id=${sessionScope.usuario.id}">MEU PERFIL</a></h4>
	</nav>
 



<aside class="busca well">
	<form role="form">
		<div class="form-group">
			Busca Usuarios:
			<input class="form-control" type="text" id="pesquisa">
		</div>
	</form>
	<div id="lista_usuarios"></div>
</aside>
	
<div class="main well">
	<ul class="nav nav-tabs">
  		<li class="active" id="menu_forcas" onclick="exibir_forcas()"><a href="#">Lista Forcas</a></li>
  		<li id="menu_notificacoes" onclick="exibir_notificacoes()"><a href="#">Notificações</a></li>
  		<li id="menu_criar" onclick="exibir_criar()"><a href="#">Criar Forca</a></li>
  		<li id="menu_reportar" onclick="exibir_reportar()"><a href="#">Reportar</a></li>
	</ul>
	<div class="filtro">
	<p>Filtro Categoria:</p>
	<select id = "categoria" class="form-control">
		<option value="0" >Todas</option>
  		<c:forEach var="categoria" items="${categorias}">
		<option value="${categoria.idcategoria}">
		${categoria.tipocategoria}
	</option>		
	</c:forEach>
	</select>
	<input type=button onclick="filtrar()" value="Filtrar">
	</div>
	<div class="forcas well"></div>
</div>

<aside class="rank well">
	<div class="btn-group">
	  <button type="button" onclick="mostrar_dias(8)" class="btn btn-default">Semana</button>
	  <button type="button" onclick="mostrar_dias(30)" class="btn btn-default">Mês</button>
	  <button type="button" onclick="mostrar_geral()" class="btn btn-default">Geral</button>
	</div>
	<div class="list-rank"></div>
</aside>

	<script>
		
		$( "#pesquisa" ).keyup(function() {
			
			var login = $("#pesquisa").val();
			
			 $.ajax({
				url:'pesquisa',
				type: 'post',
				dataType: 'json',
				data: {login:login},
				success: function (data) {
					var texto = '';
						for(var i=0; i < data.usuarios.length; i++){
							texto = texto +  ''+'<a href="/spring/perfil_usuario?idusuario='+data.usuarios[i].id+'">'+data.usuarios[i].nome+'</a>'+'<hr>';
						}
						$("#lista_usuarios").empty();
						$("#lista_usuarios").append(texto);
				},
				error: function () {
					console.log('Deu erro');
				}
			});
			 
		});	 
	</script>
	
	<footer class="nav navbar-inverse navbar-fixed-bottom">
		<p>Jogo da Forca © 2014</p>
	</footer>
</body>
</html>