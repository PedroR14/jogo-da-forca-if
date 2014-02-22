<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

	<meta http-equiv="Content-Type" content="text/html" charset=utf-8>
	
	<script src="<c:url value="/resources/css/jquery.js" />"></script>
	<script src="http://www.thimbleopensource.com/download/jquery/jquery.filter_input.js"></script>
 
	<script>
		$(document).ready(function() {
			$('#palavra').filter_input({regex:'[a-zA-Z-çÇÊêÈèÉéÛûÙùÌìÍíÎîÔôÒòÓóÕõÂâÀàÁáÃãÌìÍíÎî ]'});
			$('#dica').filter_input({regex:'[a-zA-Z0-9-çÇÊêÈèÉéÛûÙùÌìÍíÎîÔôÒòÓóÕõÂâÀàÁáÃãÌìÍíÎî ]'});
		});
	</script>
	<title>Criar Forca</title>
</head>
<body>

<h2>Criar Forca</h2>

	<c:url var="actionUrl" value="forcasalvar" />
	<form action="${actionUrl}" method="post">
	

	Dica: <input type="text" id="dica" name="dica" value="${forca.dica}"/> <br>
			<form:errors path="forca.dica" cssStyle="color:red"/>
	Palavra: <input type="text" id="palavra" name="palavra" value="${forca.palavra}"/> <br>
			<form:errors path="forca.palavra" cssStyle="color:red"/>
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