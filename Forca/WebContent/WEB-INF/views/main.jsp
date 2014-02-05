<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/main.css" />" />
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.min.css" />" />
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.css" />" />
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap-theme.min.css" />" />
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap-theme.css" />" />
	<script src="<c:url value="/resources/css/jquery.js" />"></script>
	<script type="text/javascript">
	
		var quant = 0;

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
						texto = texto + data.forcas[i].dica +'<br>'+'<a href="/spring/jogar?idforca='+data.forcas[i].id_forca+'">Responder</a>'+'<br><hr>';
					}
				
			    		if(forcas.length == 5 && data.possui_prox == true){
			    			texto = texto + '<input type = "submit" value = "Proximo ->" onclick = "passar_pagina()" style = "font-size: 10 pt; font-family: Verdana"> ';
			    		}
			    		if(quant != 0){
			    			texto = texto + '<input type = "submit" value = "<- Anterior" onclick = "voltar_pagina()" style = "font-size: 10 pt; font-family: Verdana"> ';
			    		}
						$("#forcas").append(texto);
				},
				error: function () {
					console.log('Deu erro');
				}
			});
		
		}
	
		function passar_pagina(){
			$("#forcas").empty();
			quant = quant + 5;
			listar_forcas();
		}
	
		function voltar_pagina(){
			$("#forcas").empty();
			quant = quant - 5;
			listar_forcas();
		}
	
		$(function(){
			quant = 0;
			listar_forcas();
			//$("#forcas").load('http://localhost:8080/spring/listaforcas');
			$("#usuarios").load('http://localhost:8080/spring/lista_usuarios');
			$("#notificacoes").load('http://localhost:8080/spring/usuario/notificacoes');
		});
	</script>
	<title>Principal</title>
</head>
<body>

<div id=imagem>
	<img src="#" alt="#" class="img-thumbnail">
</div>

<div id=topo>
	<p align="right">Olá,${usuario.nome}, você possui ${pontos} pts<br>
	<a href="${url}/editar?id=${usuario.id}">EDITAR PERFIL</a>, Sair<br></p>
</div>

<c:url var="url" value="/usuario"/>
<c:url var="url2" value="/inserir_categoria"/>
<c:url var="url3" value="/criar_forca"/>

<a href="${url2}">Categoria</a>
<a href="${url3}">Criar forca</a>

<div id=notificacoes></div>
<div id=forcas ></div>
<div id=usuarios ></div>
	
</body>
</html>