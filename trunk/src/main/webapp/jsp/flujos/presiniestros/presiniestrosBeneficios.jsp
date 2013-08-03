<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pre-Siniestros Beneficios</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>

<script language="javascript">
    var _CONTEXT = "${ctx}";
	
	var _ACTION_COMBO_TIPO_TRAMITE = '<s:url namespace="/presiniestros" action="obtieneComboTipTramit" includeParams="none"/>';
	var _ACTION_COMBO_PADECIMIENTOS = '<s:url namespace="/presiniestros" action="obtieneComboPadecimientos" includeParams="none"/>';
	
	var _ACTION_GUARDA_PREBEN = '<s:url namespace="/presiniestros" action="guardaPresiniestroBeneficios" includeParams="none"/>';
	var _ACTION_IR_CONSULTA_POLIZAS_PRESINIESTROS = '<s:url namespace="/presiniestros" action="consultaPolizasPresiniestros" includeParams="none"/>';
	var _ACTION_OBTIENE_DESC_OBSV = '<s:url namespace="/presiniestros" action="obtieneDescObs" includeParams="none"/>';
    
    
	//DOCUMENTOS
	var _ACTION_DOCUMENTOS = '<s:url namespace="/presiniestros" action="obtieneDocumentos" includeParams="none"/>';
	var _ACTION_AGREGA_DOCUMENTO = '<s:url namespace="/presiniestros" action="agregarDocumento" includeParams="none"/>';
	var _ACTION_EDITA_DOCUMENTO = '<s:url namespace="/presiniestros" action="editarDocumento" includeParams="none"/>';
	var _ACTION_ELIMINA_DOCUMENTO = '<s:url namespace="/presiniestros" action="eliminaDocumento" includeParams="none"/>';
	
	var _ACTION_COMBO_DOCUMENTOS = "<s:url namespace='/dctosPresiniestros' action='obtenGridInstrumetosPagoCliente' includeParams='none'/>";
	var _ACTION_OBTIENE_ATRIBUTOS = "<s:url namespace='/dctosPresiniestros' action='obtenAtributosDocumento' includeParams='none'/>";
	
	
	var _UPDATE_MODE = false;
	
	if( "U" == "<s:property value='tipoOperacion'/>") _UPDATE_MODE = true;
	
	var BENEFICIO;
	
	if(_UPDATE_MODE){
		BENEFICIO = {
			folio: "<s:property value='beneficio.folio'/>",
			folioAA: "<s:property value='beneficio.folioAA'/>",
			fecha: "<s:property value='beneficio.fecha'/>",
			poliza: "<s:property value='beneficio.poliza'/>",
			polizaExt: "<s:property value='beneficio.polizaExt'/>",
			inciso: "<s:property value='beneficio.inciso'/>",
			subinciso: "<s:property value='beneficio.subinciso'/>",
			aseguradora: "<s:property value='beneficio.dsAseguradora'/>",
			cdaseguradora: "<s:property value='beneficio.cdAseguradora'/>",
			cdramo: "<s:property value='beneficio.cdRamo'/>",
			dsramo: "<s:property value='beneficio.dsRamo'/>",
			cdElemento: "<s:property value='beneficio.cdCorporativo'/>",
			titular: "<s:property value='beneficio.titular'/>",
			empresa: "<s:property value='beneficio.empresa'/>",
			asegurado: "<s:property value='beneficio.asegurado'/>",
			cdunieco: "<s:property value='beneficio.cdUnieco'/>",
			noSiniestroAseg: "<s:property value='beneficio.noSiniestroAseg'/>",
			cdTipoTramite: "<s:property value='beneficio.cdTipoTramite'/>",
			fechaPrimerGasto: "<s:property value='beneficio.fechaPrimerGasto'/>",
			dsPadecimiento: "<s:property value='beneficio.dsPadecimiento'/>",
			reportadoPor: "<s:property value='beneficio.reportadoPor'/>",
			telefonoRep: "<s:property value='beneficio.telefonoRep'/>"
		}
	}else{
		BENEFICIO = {
			folio: '',
			folioAA: '',
			fecha: new Date().format('d/m/Y'),
			poliza: "<s:property value='beneficio.poliza'/>",
			polizaExt: "<s:property value='beneficio.polizaExt'/>",
			inciso: "<s:property value='beneficio.inciso'/>",
			subinciso: "<s:property value='beneficio.subinciso'/>",
			aseguradora: "<s:property value='beneficio.dsAseguradora'/>",
			cdaseguradora: "<s:property value='beneficio.cdAseguradora'/>",
			cdramo: "<s:property value='beneficio.cdRamo'/>",
			dsramo: "<s:property value='beneficio.dsRamo'/>",
			cdElemento: "<s:property value='beneficio.cdCorporativo'/>",
			titular: "<s:property value='beneficio.titular'/>",
			empresa: "<s:property value='beneficio.empresa'/>",
			asegurado: "<s:property value='beneficio.asegurado'/>",
			cdunieco: "<s:property value='beneficio.cdUnieco'/>"
		}
	}
	
	
	//TODO: OBTENER LA PANTALLA DE TOOLTIPS Y ASIGNAR LOS CODIGOS CORRESPONDIENTES EN TODOS LOS ARCHIVOS JS
    var helpMap = new Map();
    <%=session.getAttribute("helpMap")%>
    <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("")%>     
    
</script>

<!-- Para el contenido de esta pagina:  -->
<script type="text/javascript" src="${ctx}/resources/scripts/flujos/presiniestros/beneficios/capturaPresiniestroBeneficios.js"></script>

<!-- Para el contenido de esta pagina (agregado y editado de documentos para beneficios)  -->
<script type="text/javascript" src="${ctx}/resources/scripts/flujos/presiniestros/beneficios/documentos/documentoPresiniestro_abm.js"></script>

</head>
<body>
   <table class="headlines" cellspacing="10">
        <tr valign="top">
            <td class="headlines" colspan="2">
                <div id="formPresiniestros" />
            </td>
        </tr>
       
    </table>
</body>
</html>