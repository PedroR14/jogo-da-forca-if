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
		
		function salvar(){
			
			var palavra = $("#palavra").val();
			var dica = $("#dica").val();
			var cod_categoria = $("#cod_categoria").val();
			
			 $.ajax({
					url:'forcasalvar',
					type: 'post',
					dataType: 'json',
					data: {palavra:palavra, dica:dica, cod_categoria:cod_categoria},
					success: function (data) {
						if(data == true){
							alert('forca salva');
							 $("#dica").val('');
							$("#palavra").val('');
						}
						if(data == false){
							if($("#dica").val() == ''){
								$("#dicaerro").append("Campo Obrigatório");
							}else{
								$("#dicaerro").append("Minimo 5 Caracteres");
							}
							if($("#palavra").val() == ''){
								$("#palavraerro").append("Campo Obrigatório");
							}else{
								$("#palavraerro").append("Minimo 2 Caracteres");
							}
							alert('forca não');
						}
					},
					error: function () {
						console.log('Deu erro');
					}
				});
		}
		
		$( "#dica" ).focusin(function() {
			$("#dicaerro").empty();
		});
		$( "#palavra" ).focusin(function() {
			$("#palavraerro").empty();
		});

		
	</script>
	<title>Criar Forca</title>
</head>
<body>

<h2>Criar Forca</h2>

	<c:url var="actionUrl" value="forcasalvar" />
	<form method="post">
	

	Dica: <input type="text" id="dica" name="dica" value="${forca.dica}"/> <br>
			<div id="dicaerro"></div>
	Palavra: <input type="text" id="palavra" name="palavra" value="${forca.palavra}"/> <br>
			<div id="palavraerro"></div>
	<select  id="cod_categoria" name = "cod_categoria" value = "${forca.cod_categoria }"> 
	<c:forEach var="categoria" items="${categorias}">
	<option value="${categoria.idcategoria}">
	${categoria.tipocategoria}
	</option>		
	</c:forEach> 
	</select>
	
	<input type="button" onclick="salvar()" value="Criar">
	
	</form>
	 
 
</body>
</html>