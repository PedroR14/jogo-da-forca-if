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
		
		function salvar(){
			
			var idJogador = $("#idJogador").val();
			var idForca = $("#idForca").val();
			var observacao = $("#observacao").val();
			
			 $.ajax({
					url:'reportarsalvar',
					type: 'post',
					dataType: 'json',
					data: {idJogador:idJogador, idForca:idForca, observacao:observacao},
					success: function (data) {
						if(data == true){
							alert('Reportagem cadastrada com sucesso');
							 $("#idJogador").val('');
							$("#idForca").val('');
							$("#observacao").val('');
						}
						if(data == false){
							if($("#idJogador").val() == ''){
								$("#idJogadorErro").append("Campo Obrigatório");
							}
							if($("#idForca").val() == ''){
								$("#idForcaErro").append("Campo Obrigatório");
							}
							if($("#observacao").val() == ''){
								$("#observacaoErro").append("Campo Obrigatório");
							}
							alert('forca não');
						}
					},
					error: function () {
						console.log('Deu erro');
					}
				});
		}
		
		$( "#idJogador" ).focusin(function() {
			$("#idJogadorErro").empty();
		});
		$( "#idForca" ).focusin(function() {
			$("#idForcaErro").empty();
		});
		$( "#observacao" ).focusin(function() {
			$("#observacaoErro").empty();
		});

		
	</script>
	<title>Reportar Jogador</title>
</head>
<body>

<h2>Reportar Jogador</h2>

	<c:url var="actionUrl" value="reportarsalvar" />
	<form method="post">

	ID do Jogador: <input type="text" id="idJogador" name="idJogador" value="${reportarJogador.idJogador}"/> <br>
			<div id="idJogadorErro"></div>
	ID da Forca: <input type="text" id="idForca" name="idForca" value="${reportarJogador.idForca}"/> <br>
			<div id="idForcaErro"></div>  
	Observação <textarea id="observacao" name="observacao" rows="3" cols="50" >
	${reportarJogador.observacao}
	</textarea>		
	<div id="observacaoErro"></div>
	
	<input type="button" onclick="salvar()" value="Reportar">
	
	</form>
	 
 
</body>
</html>