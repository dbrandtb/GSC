<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Pantalla Consulta de Poliza Renovada</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script language="javascript">
    var _CONTEXT = "${ctx}";
    //Variable para una consulta que se mostrara en el encabezado de todas las pantallas y que es llamada una sola vez
    var _ACTION_GET_ENCABEZADO_POLIZA = "<s:url action='obtenerEncabezadoPoliza' namespace='/consultaPolizaRenovada'/>";
    //Datos Generales
    var _ACTION_OBTENER_VALORES_POLIZA = "<s:url action='obtenerValoresPoliza' namespace='/consultaPolizaRenovada'/>";
    var _ACTION_COMBO_INSTRUMENTO_PAGO ="<s:url action='getComboInstrumentoPago' namespace='/combos'/>";
    var _ACTION_COMBO_FORMA_PAGO ="<s:url action='getComboFormaPago' namespace='/combos'/>"; 
    var _ACTION_GUARDAR_MOV_POLIZA = "<s:url action='guardarMovPoliza' namespace='/consultaPolizaRenovada'/>";
    var _ACTION_GUARDAR_VALORES_POLIZA = "<s:url action='guardarValoresPoliza' namespace='/consultaPolizaRenovada'/>";           
	//Objeto asegurable
	var _ACTION_OBTENER_OBJETOS_ASEGURABLES = "<s:url action='obtenerObjetosAsegurables' namespace='/consultaPolizaRenovada'/>";
	var _ACTION_OBTENER_FUNCIONES = "<s:url action='obtenerFunciones' namespace='/consultaPolizaRenovada'/>";
	var _ACTION_ELIMINAR_INCISO = "<s:url action='eliminarInciso' namespace='/consultaPolizaRenovada'/>";
	var _ACTION_ELIMINAR_FUNCION_MOV_MPOLIPER = "<s:url action='eliminarFuncion' namespace='/consultaPolizaRenovada'/>";
	var _ACTION_GUARDAR_OBJETO_ASEGURABLE_MOV_MPOLIZAS = "<s:url action='guardarObjetoAsegurable' namespace='/consultaPolizaRenovada'/>";
	//Datos Variables de la Funcion en la Poliza
	var _ACTION_OBTENER_VALORES_FUNCION_TVALOPER = "<s:url action='obtenerValoresFuncion' namespace='/consultaPolizaRenovada'/>";
	var _ACTION_GUARDAR_DATOS_VAR_ROL = "<s:url action='guardarDatosVariablesRol' namespace='/consultaPolizaRenovada'/>";
	//Agregar Funcion en la Poliza
	var _ACTION_OBTIENE_ROL = "<s:url action='obtenerComboRoles' namespace='/combos'/>";
 	var _ACTION_OBTIENE_PERSONA = "<s:url action='obtenerComboPersona' namespace='/combos'/>";
	var _ACTION_GUARDAR_MOV_MPOLIPER = "<s:url action='guardarMovMPoliPer' namespace='/consultaPolizaRenovada'/>";
	//Datos variables del objeto asegurable
	var _ACTION_OBTIENE_DATOS_OBJETO = "<s:url action='obtenerDatosObjeto' namespace='/consultaPolizaRenovada'/>";
	var _ACTION_GUARDAR_MOV_TVALOSIT = "<s:url action='guardarDatosVblesObjetAsegurable' namespace='/consultaPolizaRenovada'/>";
	//Coberturas de polizas a renovar
	var _ACTION_OBTIENE_INCISOS_MPOLISIT = "<s:url action='obtenerCombosIncisos' namespace='/combos'/>";
	var _ACTION_OBTIENE_COBERTURA_ANT = "<s:url action='obtenerCoberturaAnterior' namespace='/consultaPolizaRenovada'/>";
	var _ACTION_OBTIENE_COBERTURA_RENOVA = "<s:url action='obtenerCoberturaRenovada' namespace='/consultaPolizaRenovada'/>";
	var _ACTION_BORRA_INCISO_COBERTURA = "<s:url action='eliminarIncisoCobertura' namespace='/consultaPolizaRenovada'/>";
	//Agregar Cobertura
	var _ACTION_COMBO_COBERTURAS_PRODUCTO = "<s:url action='comboCoberturasProducto' namespace='/combos'/>";
	var _ACTION_GUARDA_COBERTURA_MOV_MPOLIGAR = "<s:url action='guardaCobertura' namespace='/consultaPolizaRenovada'/>";
	//Datos de Coberturas	
	var _ACTION_OBTIENE_DATOS_VARIABLES_TVALOGAR = "<s:url action='obtenerDatosVariablesTValoGar' namespace='/consultaPolizaRenovada'/>";
	var _ACTION_GUARDAR_DATOS_VARIABLES_MOV_TVALOGAR = "<s:url action='guardarDatosVariablesMovTValoGar' namespace='/consultaPolizaRenovada'/>";
	//Accesorios
	var _ACTION_OBTIENE_EQUIPO_MPOLIOBJ = "<s:url action='obtenerEquipo' namespace='/consultaPolizaRenovada'/>";
	var _ELIMINAR_ACCESORIO_MOV_MPOLIOBJ = "<s:url action='eliminarAccesorio' namespace='/consultaPolizaRenovada'/>";
	var _ACTION_OBTIENE_DETALLE_EQUIPO_TVALOOBJ = "<s:url action='obtenerDetalleEquipo' namespace='/consultaPolizaRenovada'/>";
	var _ACTION_INCLUYE_ACCESORIO_MOV_MPOLIOBJ = "<s:url action='insertarOEditarAccesorio' namespace='/consultaPolizaRenovada'/>";
	var _ACTION_COMBO_OBJETO_RAMO = "<s:url action='comboObjetoRamo' namespace='/combos'/>";
	var _ACTION_OBTIENE_TIPO_OBJ_ATRIBUTOS = "<s:url action='obtenerTipoObjetosAtributos' namespace='/consultaPolizaRenovada'/>";
	//Recibos
	var _ACTION_OBTIENE_RECIBOS = "<s:url action='obtenerRecibos' namespace='/consultaPolizaRenovada'/>";
	
    var itemsPerPage = 10;
    
    var CODIGO_ASEGURADORA = "<s:property value='cdUniEco'/>";
    var CODIGO_PRODUCTO = "<s:property value='cdRamo'/>";
    var ESTADO = "<s:property value='estado'/>";
    var NUMERO_POLIZA = "<s:property value='nmPoliza'/>";
    
    CODIGO_ASEGURADORA = 1;
    CODIGO_PRODUCTO = 115;
    ESTADO = 'M';
    NUMERO_POLIZA = 9;
	
	var VALORES = <%=session.getAttribute("modelControl")%>
	<%=session.getAttribute("helpMap")%>
</script>

<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript"> var helpMap = new Map()</script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/consultaPolizaRenovada/consultaPolizaRenovada_stores.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/consultaPolizaRenovada/consultaPolizaRenovada.js"></script>

</head>

<body>
     <div id="divDatosGrales"/>
     <div id="datosGrales"/>
     <div id="objetoAsegurable"/>
     <div id="datosobjetoAsegurable" />
     <div id="coberturas" />
     <div id="datosCobertura" />
     <div id="accesorios" />
     <div id="recibos" />
</body>
</html>