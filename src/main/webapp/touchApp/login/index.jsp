<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${ctx}/resources/touch-2.3.1/resources/css/sencha-touch.css"></style>
<script src="${ctx}/resources/touch-2.3.1/sencha-touch-all.js"></script>
<script src="${ctx}/resources/touch-2.3.1/src/locale/ext-lang-es.js"></script>
<script>
////// textos //////
var _t01 = 'Inicio de sesi&oacute;n';
var _t02 = 'Datos de usuario:';
var _t03 = 'Usuario';
var _t04 = 'Contrase&ntilde;a';
var _t05 = 'Iniciar sesi&oacute;n';
var _t06 = 'Introduzca su nombre de usuario y contrase&ntilde;a';
var _t07 = 'Introduce el nombre de usuario';
var _t08 = 'Introduce la contrase&ntilde;a';
var _t09 = 'Error interno del servidor, consulte a soporte';
var _t10 = 'Cargando...';
////// textos //////

////// variables globales //////
var _urlLogin    = '<s:url namespace="/seguridad" action="autenticaUsuario"     />';
var _urlRedirect = '<s:url namespace="/"          action="seleccionaRolCliente" />';
var _CTX         = '${ctx}';
////// variables globales //////

function debug(a,b,c,d)
{
	if(true)
    {
        if(d!=undefined)
            console.log(a,b,c,d);
        else if(c!=undefined)
            console.log(a,b,c);
        else if(b!=undefined)
            console.log(a,b);
        else
            console.log(a);
    }
}

function datosIncompletos(mensaje,callback)
{
	Ext.Msg.alert('<s:text name="touch.datosIncompletos" />', message, callback);
}
</script>
<script src="app.js"></script>
</head>
</html>
<body>
</body>
</html>