<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="<c:url value="/resources/css/jquery.js" />"></script>
<script type="text/javascript">
	function desafiar(id_usuario){
		$(".forcas").empty();
		$(".filtro").hide();
		$(".forcas").load('http://localhost:8080/spring/desafiar?id_destin='+id_usuario);
		$("#menu_notificacoes").removeClass("active");
		$("#menu_forcas").removeClass("active");
		$("#menu_criar").removeClass("active");
	}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h2>${perfil.login}</h2>
	<h4>Nome: ${perfil.nome}</h4>
	<div class="panel panel-default">
  		<div class="panel-heading">Estatísticas</div>
  			<div class="panel-body">
  			<span class="label label-default">Partidas Jogadas: ${vitorias + derrotas}</span><br><br>
  			<span class="label label-success" style="float:left;">N° Vitórias: ${vitorias}</span>
  			<span class="label label-danger" style="float:right;">N° Derrotas: ${derrotas}</span><br><br>
  			<span class="label label-success" style="float:left;">Percentual vitorias: ${percent_vitorias}%</span>
  			<span class="label label-danger" style="float:right;">Percentual Derrotas: ${percent_derrotas}%</span><br>
				 <div class="progress">
  					<div class="progress-bar progress-bar-success" style="width: ${percent_vitorias}%">
    					<span class="sr-only">${percent_vitorias}% Vitórias (success)</span>
  				</div>
  					<div class="progress-bar progress-bar-danger" style="width: ${percent_derrotas}%">
    					<span class="sr-only">${percent_derrotas}% Derrotas (danger)</span>
  					</div>
				</div> 			
    			
  			</div>
	</div>
	<a href="#" class="btn btn-primary btn-lg active" role="button" onclick="desafiar(${perfil.id})">Desafiar</a>
</body>
</html>