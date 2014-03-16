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
			var COD_PUNICAO = $("#COD_PUNICAO").val();
			var observacao = $("#observacao").val();
			
			 $.ajax({
					url:'punirsalvar',
					type: 'post',
					dataType: 'json',
					data: {idJogador:idJogador, COD_PUNICAO:COD_PUNICAO, observacao:observacao},
					success: function (data) {
						if(data == true){
							alert('Punição cadastrada com sucesso');
							 $("#idJogador").val('');
							$("#COD_PUNICAO").val('');
							$("#observacao").val('');
						}
						if(data == false){
							if($("#idJogador").val() == ''){
								$("#idJogadorErro").append("Campo Obrigatório");
							}
							if($("#COD_PUNICAO").val() == ''){
								$("#COD_PUNICAOErro").append("Campo Obrigatório");
							}
							if($("#observacao").val() == ''){
								$("#observacaoErro").append("Campo Obrigatório");
							}
							alert('punir não');
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
		$( "#COD_PUNICAO" ).focusin(function() {
			$("#COD_PUNICAOErro").empty();
		});
		$( "#observacao" ).focusin(function() {
			$("#observacaoErro").empty();
		});

		
	</script>
	<title>Punir Jogador</title>
</head>
<body>

<h2>Punir Jogador</h2>

	<c:url var="actionUrl" value="punirsalvar" />
	<form method="post">

	ID do Jogador: <input type="text" id="idJogador" name="idJogador" value="${punirJogador.idJogador}"/> <br>
			<div id="idJogadorErro"></div>
	Punição: <select  id="COD_PUNICAO" name = "COD_PUNICAO"  style="margin-top:20px;"> 
	<c:forEach var="punicao" items="${punicoes}">
	<option value="${punicao.COD_PUNICAO}">
	${punicao.NOME_PUNICAO}
	</option>		
	</c:forEach> 
	</select> 
	Observação <textarea id="observacao" name="observacao" rows="3" cols="50" >
	${punirJogador.observacao}
	</textarea>		
	<div id="observacaoErro"></div>
	
	<input type="button" onclick="salvar()" value="Punir">
	
	</form>
	 
 
</body>
</html>