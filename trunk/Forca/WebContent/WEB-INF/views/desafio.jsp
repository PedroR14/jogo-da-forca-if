<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="<c:url value="/resources/css/jquery.js" />"></script>
<script type="text/javascript">
function salvar_desafio(id_destin){
	
	var palavra = $("#palavra").val();
	var dica = $("#dica").val();
	var cod_categoria = $("#cod_categoria").val();
	var aposta = $("#aposta").val();
	
	 $.ajax({
			url:'desafio/salvar',
			type: 'post',
			dataType: 'json',
			data: {palavra:palavra, dica:dica, cod_categoria:cod_categoria, id_destin:id_destin, aposta:aposta},
			success: function (data) {
				if(data == true){
					alert('forca salva');
					 $("#dica").val('');
					$("#palavra").val('');
					$("#aposta").val('');
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
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Desafio</title>
</head>
<body>
	
	<h2>Desafiar</h2>

	<form method="post">
	

	Dica: <textarea id="dica" name="dica" style="width: 150px; max-width: 150px; height: 126px; max-height: 126px; text-align: left;"></textarea> <br>
	
	Palavra: <input type="text" id="palavra" name="palavra" style="width: 150px; max-width: 150px; margin-top:20px; text-align:left;"/> <br>
	
	Aposta: <input type="text" id="aposta" name="aposta" style="width: 150px; max-width: 150px; margin-top:20px; text-align:left;"/> <br>
	
	Categoria: <select name = "cod_categoria" id="cod_categoria" style="margin-top:20px;"> 
	<c:forEach var="categoria" items="${categorias}">
	<option value="${categoria.idcategoria}">
	${categoria.tipocategoria}
	</option>		
	</c:forEach> 
	</select>
	
	<input type="button" onclick="salvar_desafio(${usuario_destinatario})" value="Criar">
	
	</form>

</body>
</html>