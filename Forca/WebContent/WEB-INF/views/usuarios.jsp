<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>

<title>Lista Usuarios</title>

<link href="style.css" rel="stylesheet" >

</head>
<body>
	<h2>Usuarios</h2>
	<p>${mensagem}</p>
	<c:url var="root" value="/usuario"/>
	<table border="1">
		<tr>
			<th>Nome</th>
			<th>Login</th>
			<th>Email</th>
			<th></th>
		</tr>
		<c:forEach var="usuario" items="${usuarios}">
			<tr>
				<td>${usuario.nome}</td>
				<td>${usuario.login}</td>
				<td>${usuario.email}</td>
				<td>
					<a href="${root}/excluir?id=${usuario.id}">EXCLUIR</a>
				</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>