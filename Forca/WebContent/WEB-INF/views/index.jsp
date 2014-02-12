<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html" charset=utf-8>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/index.css" />" />
<title>Acesso</title>
</head>
<body>
	<header>
		<nav class="navbar navbar-inverse" role="navigation">
			<div class="navbar-header">
    			<h1> Jogo da Forca </h1>
    		</div>
    			<h3 class="navbar-brand"> No Jogo da Forca você pode se conectar e
			desafiar quem você quiser.<h3>
		</nav>
  	</header>
  	<c:if test = "${not empty mensagem}">
  		<div class="alert alert-danger">
  			<p>${mensagem}</p>
		</div>
	</c:if>

  	<div class="login"> 
		<c:url var="entrar" value="/usuario/logar"/>
		<form action="${entrar}" method="post" role="form">
			<h3> Autentique-se! </h3>
  			<div class="form-group">
    			<input type="text" class="form-control" id="exampleInputEmail1" placeholder="Login" name="login" value="${usuario.login}">
    			<form:errors path="usuario.login" cssStyle="color:red"/>
  			</div>
  			<div class="form-group">
    			<input type="password" class="form-control" id="exampleInputPassword1" placeholder="Senha" name="senha" value="${usuario.senha}">
    			<form:errors path="usuario.senha" cssStyle="color:red"/>
  			</div>
  			<button type="submit" class="btn btn-success" value="Login">Entrar</button>
  			<a class="btn btn-warning" href="#">Esqueci minha senha</a>
		</form>
	</div>


	<div class = cadastro >
		<c:url var="cadastrar" value="/usuario/salvar"/>
		<form action="${cadastrar}" method="post" role="form">
			<h3> Crie uma conta </h3>
  			<div class="form-group">
    			<input type="text" class="form-control" id="exampleInputEmail1" placeholder="nome" name="nome" value="${usuario.nome}">
    			<form:errors path="usuario.nome" cssStyle="color:red"/>
  			</div>
  			<div class="form-group">
    			<input type="text" class="form-control" id="exampleInputPassword1" placeholder="login" name="login" value="${usuario.login}">
    			<form:errors path="usuario.login" cssStyle="color:red"/>
  			</div>
  			<div class="form-group">
    			<input type="text" class="form-control" id="exampleInputPassword1" placeholder="email" name="email" value="${usuario.email}">
    			<form:errors path="usuario.email" cssStyle="color:red"/>
  			</div>
  			<div class="form-group">
    			<input type="password" class="form-control" id="exampleInputPassword1" placeholder="Senha" name="senha" value="${usuario.senha}">
    			<form:errors path="usuario.senha" cssStyle="color:red"/>
  			</div>
  			<button type="submit" class="btn btn-primary" value="Login">Cadastrar</button>
  			${mensagemerroinsercao}
		</form>
	</div>
	
	<footer class="nav navbar-inverse navbar-fixed-bottom">
		<p>Jogo da Forca &copy 2014</p>
	</footer> 
</body>
</html>