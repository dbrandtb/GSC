<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Tipos de Documentos por Cliente</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<!-- link rel="stylesheet" type="text/css" href="${ctx}/resources/extjs/resources/css/ext-all.css" / -->
<!-- link rel="stylesheet" type="text/css" href="${ctx}/resources/extjs/resources/css/xtheme-gray.css" / -->

<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>

<!-- <script type="text/javascript" src="${ctx}/resources/extjs/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="${ctx}/resources/extjs/ext-all.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script> -->



<script language="javascript">
    var _CONTEXT = "${ctx}";
    
	var _ACTION_COMBO_CLIENTES = '<s:url namespace="/dctosPresiniestros" action="obtenComboCliente" includeParams="none"/>';
    var _ACTION_COMBO_ASEGURADORAS = '<s:url namespace="/dctosPresiniestros" action="obtenComboAseguradora" includeParams="none"/>';
    var _ACTION_COMBO_PRODUCTOS = '<s:url namespace="/dctosPresiniestros" action="obtenComboProducto" includeParams="none"/>';
    var _ACTION_COMBO_DOCUMENTOS = '<s:url namespace="/dctosPresiniestros" action="obtenComboDocumentos" includeParams="none"/>';
    var _ACTION_FORMA_BUSQUEDA = '<s:url namespace="/dctosPresiniestros" action="obtenGridInstrumetosPagoCliente" includeParams="none"/>';
    var _ACTION_AGREGAR_INSCTE = '<s:url namespace="/dctosPresiniestros" action="agregarIntrumentoPagoCliente" includeParams="none"/>';
    var _ACTION_BORRAR_INSCTE = '<s:url namespace="/dctosPresiniestros" action="borrarIntrumentoPagoCliente" includeParams="none"/>';
    
    //ACTIONS PARA LA CONFIGURACION DE LOS ATRIBUTOS POR INSTRUMENTO DE PAGO
    
    var _ACTION_FORMA_BUSQUEDA_ATR = '<s:url namespace="/dctosPresiniestros" action="obtenAtributosGridInstrumetosPago" includeParams="none"/>';
    
    var _ACTION_COMBO_PADRE = '<s:url namespace="/atributosVariables" action="CargaComboPadre" includeParams="none"/>';
    var _ACTION_COMBO_CONDICIONES = '<s:url namespace="/librerias" action="CargaReglaNegocio" includeParams="none"/>';
    var _ACTION_COMBO_LISTA_VALORES = '<s:url namespace="/atributosVariables" action="ListaValoresAtributos" includeParams="none"/>';
    
    var _ACTION_GUARDA_ATRIBUTO = '<s:url namespace="/dctosPresiniestros" action="guardaAtributoIntrumentoPago" includeParams="none"/>';
    var _ACTION_ELIMINA_ATRIBUTO = '<s:url namespace="/dctosPresiniestros" action="borrarAtributoIntrumentoPago" includeParams="none"/>';
    
    //PARA EL MANEJO DE EXPRESIONES
    
	var _ACTION_OBTENER_CODIGO_EXP = '<s:url namespace="/atributosVariables" action="ObtenerCodigoExpresion" includeParams="none"/>';
	var _ACTION_LIMPIAR_SESSION_EXP = '<s:url namespace="/atributosVariables" action="LimpiarSesionExpresion" includeParams="none"/>';
	
	//EXPRESIONES
	var ACTION_TABLA_EXPRESIONES ="<s:url namespace='expresiones' action='ComboTabla' includeParams='none'/>";
	var ACTION_COLUMNA_EXPRESIONES = "<s:url namespace='expresiones' action='ComboColumna' includeParams='none'/>";
	var ACTION_CLAVE_EXPRESIONES = "<s:url namespace='expresiones' action='ComboClave' includeParams='none'/>";    
    
	//var CODIGO_DESCUENTO = "<s:property value='cdDscto'/>";
	
	//TODO: OBTENER LA PANTALLA DE TOOLTIPS Y ASIGNAR LOS CODIGOS CORRESPONDIENTES EN TODOS LOS ARCHIVOS JS
    var helpMap = new Map();
    <%=session.getAttribute("helpMap")%>
    <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("")%>    
    
</script>

<!-- Para usar las listas de valores:  -->
<script type="text/javascript" src="${ctx}/resources/scripts/utilities/vTypes/combo.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/utilities/vTypes/date.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/utilities/checkColumnPlugin.js"></script>

<!-- Para usar lo de Expresiones, tabla de 5 y 1 clave:  -->
<script type="text/javascript" src="${ctx}/resources/scripts/jsp/productos/tablaApoyo5Claves/tablaApoyo5Claves.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/flujos/presiniestros/beneficios/documentos/creacionListasDeValores.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/jsp/utilities/expresiones/expresiones.js"></script>

<!-- Para el contenido de esta pagina:  -->
<script type="text/javascript" src="${ctx}/resources/scripts/flujos/presiniestros/beneficios/documentos/documentoPorCliente.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/flujos/presiniestros/beneficios/documentos/documentoPorCliente_agregar.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/flujos/presiniestros/beneficios/documentos/atributosVariablesDocumento.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/flujos/presiniestros/beneficios/documentos/atributosVariablesPorDocumento_abm.js"></script>

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
               <div id="grilla" />
           </td>
       </tr>
    </table>
</body>
</html>