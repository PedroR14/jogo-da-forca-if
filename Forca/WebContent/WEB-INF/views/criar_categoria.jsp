<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/categoria.css" />" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h2>Inserir Categoria</h2>
	<c:url var="actionUrl" value="/categoria/salvar"/>
	<form action="${actionUrl}" method="post">


				Nome categoria
				<input type="text" name="tipocategoria" value="${categoria.tipocategoria}"/>
				<form:errors path="categoria.tipocategoria" cssStyle="color:red"/>
				${mensagem}
			<input type="submit" value="Salvar">
	</form>
	<div class= "categorias">
		<c:forEach var="categoria" items="${categorias}">
			${categoria.tipocategoria}
			<hr>
		</c:forEach>
	</div>

</body>
</html>