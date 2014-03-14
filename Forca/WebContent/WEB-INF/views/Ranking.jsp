<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
	<link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
	<script src="//code.jquery.com/jquery-1.9.1.js"></script>
	<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
	<script>
		
		function ranking_data(){
			
			var inicio = $('#datainicio').val();
			var fim = $('#datafim').val();
			
			 $.ajax({
				url:'ranking_data',
				type: 'post',
				dataType: 'json',
				data: {inicio:inicio,fim:fim},
				success: function (data) {
			
				},
				error: function () {
					console.log('Deu erro');
				}
			});
			 
		}
	
  		$(function() {
    		$( "#datainicio" ).datepicker();
    		$( "#datafim" ).datepicker();
  		});
  	</script>
</head>
<body>
	<p>Inicio: <input type="text" id="datainicio"></p>
	<p>Fim: <input type="text" id="datafim"></p>
	<button type="submit" onclick="ranking_data()" value="mostrar">Mostrar</button>
</body>
</html>