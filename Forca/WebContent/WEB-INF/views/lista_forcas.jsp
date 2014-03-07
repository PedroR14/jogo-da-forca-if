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

var quant = 0;

function listar_forcas(categoria){
	
	var forcas = [];

	$.ajax({
		url:'listaforcas',
		type: 'get',
		dataType: 'json',
		data: {quant:quant,categoria:categoria},
		success: function (data) {
			forcas = data.forcas;
			var texto = '';
			for(var i=0; i < forcas.length; i++){
				texto = texto +  data.forcas[i].dica +'<br>'+'<a  href="#janela1" rel="modal">Responder</a>'+'<br><hr>';
			}
			/* onclick="abrir_forca('+data.forcas[i].id_forca+')" */
	    		texto = texto + '<div class = botoes>';
	    		texto = texto + '<ul class="pager">';
	    		if(quant != 0){
	    			texto = texto + '<li class="previous" onclick = "voltar_pagina()" ><a>&larr; Anterior</a></li> ';
	    		}
	    		if(forcas.length == 5 && data.possui_prox == true){
	    			texto = texto + '<li class="next" onclick = "passar_pagina()" ><a>Proxima &rarr; </a></li> ';
	    		}
	    		texto = texto + '</ul>';
	    		texto = texto + '</div>';
				$(".lista_forcas").empty();
				$(".lista_forcas").append(texto);
		},
		error: function () {
			console.log('Deu erro');
		}
	});

}

function passar_pagina(){
	$(".lista_forcas").empty();
	quant = quant + 5;
	listar_forcas(0);
}

function voltar_pagina(){
	$(".lista_forcas").empty();
	quant = quant - 5;
	listar_forcas(0);
}



function filtrar(){
	$(".lista_forcas").empty();
	listar_forcas($("#categoria").val());
}

function abrir_forca(id_forca){
	$(".window").load('http://localhost:8080/spring/jogar?idforca='+id_forca);
	$("#menu_notificacoes").removeClass("active");
	$("#menu_forcas").removeClass("active");
	$("#menu_criar").removeClass("active");
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
	quant = 0;
	listar_forcas(0);
});

</script>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/main.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/modal.css" />" />

</head>
<body>
<div class="lista_forcas"></div>
<div class="window" id="janela1"></div>
<div id="mascara"></div>
	
</body>
</html>