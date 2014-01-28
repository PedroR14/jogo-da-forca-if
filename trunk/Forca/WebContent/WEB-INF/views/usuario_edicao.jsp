<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<body>
	<h2>${titulo}</h2>
	<c:url var="actionUrl" value="/usuario/salvar"/>
	<form action="${actionUrl}" method="post">
		<table border="0">
			<tr>
				<td>Nome</td>
				<td>
					<input type="text" name="nome" value="${usuario.nome}"/>
					<form:errors path="usuario.nome" cssStyle="color:red"/>
				</td>
			</tr>
			<tr>
				<td>Login</td>
				<td>
					<input type="text" name="login" value="${usuario.login}"/>
					<form:errors path="usuario.login" cssStyle="color:red"/>
				</td>
			</tr>
			<tr>
				<td>Email</td>
				<td>
					<input type="text" name="email" value="${usuario.email}"/>
					<form:errors path="usuario.email" cssStyle="color:red"/>
				</td>
			</tr>
			<tr>
				<td>Senha</td>
				<td>
					<input type="text" name="senha" value="${usuario.senha}"/>
					<form:errors path="usuario.senha" cssStyle="color:red"/>
				</td>
			</tr>
		</table>
		<input type="hidden" name="id" value="${usuario.id}" />
		<input type="submit" value="Salvar">
	</form>
</body>
</html>