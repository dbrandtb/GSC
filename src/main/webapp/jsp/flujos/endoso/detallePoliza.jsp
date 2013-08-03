<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<style type="text/css">    
        .alinear {
        margin-left: auto; margin-right: auto; 
        }
    </style>
<title>Detalle de poliza</title>

	<script type="text/javascript">
        var _CONTEXT = '${ctx}';
        var _MSG_AVISO		= 'Aviso';
        var _MSG_NO_ROW_SELECTED= 'Debe seleccionar un registro para realizar esta operacion';
		var FECHA_EFECTIVIDAD_ENDOSO='<s:property value="fechaefectividadendoso" />';
        var _VOLVER1 = '<s:property value="volver1" />';
        var _VOLVER2 = '<s:property value="volver2" />';
        var _VOLVER3 = '<s:property value="volver3" />';
	    var PROCESO = '<s:property value="proc"/>';
	    var RENOVACION = 'ren';
	    var ENDOSO = 'end';

		var _ACTION_ELIMINA_INCISO = "<s:url action='bajaInciso' namespace='/flujoendoso'/>";
        var _ACTION_IR_POLIZAS_CANCELADAS = "<s:url action='irPolizasCanceladas' namespace='/procesoemision'/>";
        var _ACTION_IR_POLIZAS_A_RENOVAR = "<s:url action='irPolizasARenovar' namespace='/procesoemision'/>";
        var _ACTION_IR_POLIZAS_RENOVADAS = "<s:url action='irPolizasRenovadas' namespace='/procesoemision'/>";
        
	    var PROCESO_RENOVACION = ( PROCESO!=null && PROCESO==RENOVACION)? true:false;

    </script> 
    <%@ include file="/resources/jsp-script/flujos/endoso/detallePoliza-script.jsp"%>    
  
</head>
<body>
    <table>
    <tr>
        <td>
        <div id="items"></div>
        </td>
    </tr>
    </table>
</body>
</html>