<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<jsp:useBean id="userVO" class="mx.com.aon.portal.model.UserVO" scope="session" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<style type="text/css">    
       
        .alinear {
        	margin-left: auto; margin-right: auto; 
        }
        
        #impresionAyuda *{ 
			visibility:hidden;
			height:0px;
			width:0px;
		}
		
		#impresionResultadosCotizacion {
			visibility:hidden;
			height:0px;
			width:0px;
		}
                
    </style>
    
<%-- 
La hoja de estilo de print.css es para los estilos de impresion de la ventana de ayuda de las coberturas
--%>

<link href="${ctx}/resources/css/print.css" rel="stylesheet" type="text/css" media="print" />


<title>Array Grid Example</title>
    <script type="text/javascript">
        var _CONTEXT = "${ctx}";  
        var _ACTION_REDIRECT_BUY = "<s:url action='comprarCotizaciones' namespace='/flujocotizacion'/>";
        var _URL_REGRESO = "<s:url action='regresaCapturaCotizacion' namespace='/flujocotizacion'/>" + '<s:property value="paramsEntradaCotizacion" />'.replace(/&amp;/g , '&');
        var _URL_REGRESO_CONSULTA_COTIZACION = "<s:url action='consultaCotizaciones' namespace='/flujocotizacion'/>";
        var _MSG_AVISO	= 'Aviso';
		var _MSG_NO_ROW_SELECTED= 'Debe seleccionar un registro para realizar esta operacion';
		var _MSG_NO_ROW_SELECTED_DETAIL= 'Seleccione un registro para ver el detalle del plan';
		var _CDRAMO = '<s:property value="#session.CCCDRAMO" />';
        var _CDTIPSIT = '<s:property value="#session.CCCDTIPSIT" />';
        var _ACTION_CONSULTA_COTIZACIONES_COMPRAR = "<s:url action='consultaCotizacionesComprar' namespace='/flujocotizacion'/>";
        var _ACTION_ENVIAR_CORREO = "<s:url action='enviaMail' namespace='/flujocotizacion'/>";
        var _USER = '<%=userVO.getName()%>';
    </script>

	<script type="text/javascript">
	function noBackButtons()
	{

                /*
                switch (event.keyCode)
                {
                    case 8 : //Backspace
                        event.returnValue = false;
                        event.keyCode = 0;
                        break;
                        
                    
                    default:
                        if(event.ctrlKey == false && event.altKey == false)
                            break;
                        else
                            if (event.keyCode != 50 && event.keyCode != 67 && event.keyCode != 86 && event.keyCode != 88)
                                event.returnValue = false;
                }
                */
	}
        
	//document.attachEvent("onkeydown", noBackButtons);    

	</script>

	<script type="text/javascript">
        <%@ include file="/resources/jsp-script/flujos/cotizacion/resultCotizacion-script.jsp"%>
    </script>
</head>
<body>
<div id="impresionAyuda"></div>
<div id="impresionResultadosCotizacion"></div>
<div id="items">
</div>


</body>
</html>