<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/style.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.min.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap-theme.min.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap-theme.css" />" />
<script src="<c:url value="/resources/css/jquery.js" />"></script>
<script type="text/javascript">
	$(function(){
		$("#forcas").load('http://localhost:8080/spring/listaforcas');
		$("#usuarios").load('http://localhost:8080/spring/lista_usuarios');
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
	visulizar Ranking, Sair<br></p>
</div>

<c:url var="url" value="/usuario"/>
<c:url var="url2" value="/inserir_categoria"/>
<c:url var="url3" value="/criar_forca"/>


<a href="${url}/editar?id=${usuario.id}">EDITAR PERFIL</a>
<a href="${url2}">Categoria</a>
<a href="${url3}">Criar forca</a>


<iframe width='250px' height='250px' frameborder='0' src='http://localhost:8080/spring/usuario/notificacoes'></iframe>
<div id=forcas ></div>
<div id=usuarios ></div>
</body>
</html>