<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<script src="<c:url value="/resources/css/jquery.js" />"></script>

<script type="text/javascript">

function mostrar_notificacoes(){
	$.ajax({
		url:'mostrarnotificacoes',
		type: 'get',
		dataType: 'json',
		success: function (data) {
			texto = '';
			for (var i = 0; i < data.lista.length; i++) {
				

				texto = texto + '<a href="#janela1" onclick="abrir_notificacao('+data.lista[i].id_notificacao+')" rel="modal" class="list-group-item"> <h5 class="list-group-item-heading">'+data.lista[i].tipo+'</h5>'+
				'<p class="list-group-item-text">'+data.lista[i].texto+'</p>'+'</a>';
			}
			$(".list-group").empty();
			$(".list-group").append(texto);
		},
		error: function () {
			console.log('Deu erro');
		}
	});
}

function abrir_notificacao(id_notificacao){
	$.ajax({
		url:'abrirnotificacao',
		type: 'post',
		data: {id_notificacao:id_notificacao},
		dataType: 'json',
		success: function (data) {
			texto = data.texto;
			$(".window").empty();
			$(".window").append(texto);
		},
		error: function () {
			console.log('Deu erro');
		}
	});
}

$(document).ready(function(){
    $(document).on('click', "a[rel=modal]", function(ev){
        ev.preventDefault();
 
        var id = $(this).attr("href");
 
        var alturaTela = $(document).height();
        var larguraTela = $(window).width();
     
        //colocando o fundo preto
        $('#mascara').css({'width':larguraTela,'height':alturaTela});
        $('#mascara').fadeIn(1000);
        $('#mascara').fadeTo("slow",0.8);
 
        var left = ($(window).width() /2) - ( $(id).width() / 2 );
        var top = ($(window).height() / 2) - ( $(id).height() / 2 );
     
        $(id).css({'top':top,'left':left});
        $(id).show();  
    });
 
    $("#mascara").click( function(){
        $(this).hide();
        $(".window").hide();
    });
 
    $('.fechar').click(function(ev){
        ev.preventDefault();
        $("#mascara").hide();
        $(".window").hide();
    });
});

$(function(){
	mostrar_notificacoes();
});
</script>

<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/modal.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/main.css" />" />

</head>
<body>
	<div class="list-group"></div>
	<div class="window" id="janela1"></div>
	<div id="mascara"></div>
</body>
</html>