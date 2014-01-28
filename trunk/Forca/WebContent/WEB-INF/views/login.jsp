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
<title>Acesso</title>
</head>
<body>
	<div id=login>
		<c:url var="entrar" value="/usuario/logar"/>
		<c:url var="cadastro" value="/usuario/novo"/>
		<form action="${entrar}" method="post" role="form">
		<h1>JOGO DA FORCA</h1>
  		<div class="form-group">
    		<input type="text" class="form-control" id="exampleInputEmail1" placeholder="Login" name="login" value="${usuario.login}">
    		<form:errors path="usuario.login" cssStyle="color:red"/>
  		</div>
  		<div class="form-group">
    		<input type="password" class="form-control" id="exampleInputPassword1" placeholder="Senha" name="senha" value="${usuario.senha}">
    		<form:errors path="usuario.senha" cssStyle="color:red"/>
  		</div>
  		<button type="submit" class="btn btn-default" value="Login">entrar</button>
		</form>
		<font color="red">${mensagem}</font>
		<a href="#">Esqueci minha senha</a>
	</div>

	<div id=cadastro>
		<c:url var="cadastrar" value="/usuario/salvar"/>
		<form action="${cadastrar}" method="post" role="form">
		<h1>CADASTRE-SE</h1>
		<font color="gray" style='font-style:italic;'>È gratuito e sempre será</font>
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
  		<button type="submit" class="btn btn-default" value="Login">Cadastrar</button>
		</form>
	</div>	

</body>
</html>