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
	
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/main.css" />" />
	
	<title>Criar Forca</title>
</head>
<body>

<h2>Criar Forca</h2>

	<c:url var="actionUrl" value="forcasalvar" />
	<form method="post">

	Dica: <textarea id="dica" name="dica" style="width: 150px; max-width: 150px; height: 126px; max-height: 126px; text-align: left;"></textarea> <br>
			<div id="dicaerro" style="text-color: red;"></div>
	Palavra: <input type="text" id="palavra" name="palavra" style="width: 150px; max-width: 150px; margin-top:20px; text-align:left;"/> <br>
			<div id="palavraerro" style="text-color: red;"></div>
	Categoria: <select  id="cod_categoria" name = "cod_categoria"  style="margin-top:20px;"> 
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