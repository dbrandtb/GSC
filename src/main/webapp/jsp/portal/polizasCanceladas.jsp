<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Pantalla Polizas Canceladas</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script language="javascript">
    var _CONTEXT = "${ctx}";
    
    var _ACTION_BUSCAR_POLIZAS_CANCELADAS= "<s:url action='buscarPolizasCanceladas' namespace='/polizas'/>";
    var _ACTION_BORRAR_POLIZA="";
    var _ACTION_COMBO_RAZON_CANCELACION = "<s:url action='comboRazones' namespace='/combos'/>";
    var _ACTION_REVERTIR_POLIZAS_CANCELADAS= "<s:url action='revertirPolizasCanceladas' namespace='/polizas'/>";
    var _ACTION_EXPORTAR_POLIZAS_CANCELADAS= "<s:url action='exportPolizasCanceladas' namespace='/polizas'/>";
    var _ACTION_IR_REHABILITACION_MANUAL= "<s:url action='irRehabilitacionManual' namespace='/polizas'/>";
    var _ACTION_VALIDA_ENDOSO= "<s:url action='validacionPolizaRehabilitacion' namespace='/polizas'/>";
 
    var _SESSION_PARAMETROS_REGRESAR = null;
    <s:if test="%{#session.containsKey('PARAMETROS_REGRESAR')}">
	    	if ( "<s:property value='#session.PARAMETROS_REGRESAR.clicBotonRegresar' />" == "S" ) {
    		_SESSION_PARAMETROS_REGRESAR = {
    			idRegresar: "<s:property value='#session.PARAMETROS_REGRESAR.idRegresar' />",
    			asegurado: "<s:property value='#session.PARAMETROS_REGRESAR.asegurado' />",
    			aseguradora: "<s:property value='#session.PARAMETROS_REGRESAR.aseguradora' />",
    			producto: "<s:property value='#session.PARAMETROS_REGRESAR.producto' />",
    			poliza: "<s:property value='#session.PARAMETROS_REGRESAR.poliza' />",
    			razon: "<s:property value='#session.PARAMETROS_REGRESAR.razon' />",
    			fecha_inicio: "<s:property value='#session.PARAMETROS_REGRESAR.fecha_inicio' />",
    			fecha_fin: "<s:property value='#session.PARAMETROS_REGRESAR.fecha_fin' />",
    			inciso: "<s:property value='#session.PARAMETROS_REGRESAR.inciso' />"
    		};
    	}
    </s:if>

    var itemsPerPage=10;
    var vistaTipo=1;
    <%=session.getAttribute("helpMap")%>
    
</script>

<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript">var helpMap= new Map()</script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>

<script type="text/javascript" src="${ctx}/resources/scripts/portal/polizasCanceladas/polizasCanceladas_stores.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/polizasCanceladas/polizasCanceladas.js"></script>

</head>
<body>

   <table class="headlines" cellspacing="10">
        <tr valign="top">
            <td class="headlines" colspan="2">
                <div id="formBusqueda" />
            </td>
        </tr>
       <tr valign="top">
           <td class="headlines" colspan="2">
               <div id="gridElementos" />
           </td>
       </tr>
    </table>
</body>
</html>