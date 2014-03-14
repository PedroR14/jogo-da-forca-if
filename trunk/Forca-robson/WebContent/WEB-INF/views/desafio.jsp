<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Desafio</title>
</head>
<body>
	
	<h2>Desafiar</h2>

	<c:url var="actionurl" value="desafio/salvar?id_destin="/>
	<form action="${actionurl}${usuario_destinatario}" method="post">
	

	Dica: <input type="text" name="dica" value=""/> <br>
	
	Palavra: <input type="text" name="palavra" value=""/> <br>
	
	Aposta: <input type="text" name="aposta" value=""/> <br>
	
	<select name = "cod_categoria" value = "${forca.cod_categoria }"> 
	<c:forEach var="categoria" items="${categorias}">
	<option value="${categoria.idcategoria}">
	${categoria.tipocategoria}
	</option>		
	</c:forEach> 
	</select>
	
	<input type="submit" value="Criar">
	
	</form>

</body>
</html>