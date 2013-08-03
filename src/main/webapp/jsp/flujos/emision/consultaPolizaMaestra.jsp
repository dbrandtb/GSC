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
<title>Pólizas Maestras</title>
    <script type="text/javascript">
        var _CONTEXT = "${ctx}";
        var _ACTION_EXPORT = "<s:url action='exportGenerico' namespace='/principal'/>";
        var _NUEVO_ACTION_EXPORT = "<s:url action='exportarConsultaPolizaMaestra' namespace='/principal'/>";
        var _NUEVA_BUSQUEDA = "<s:url action='obtienePolizaMaestra' namespace='/procesoemision'/>";
       	var _ACTION_IR_AGRUPACION_POR_ROL = "<s:url action='irAgrupacionPorRol' namespace='/procesoemision'/>";
       	var _ACTION_IR_AGRUPACION_POR_GRUPO_PERSONA = "<s:url action='irAgrupacionPorGrupoPersona' namespace='/procesoemision'/>";
       	var _ACTION_ASIGNAR_NUMERO = "<s:url action='irAsignarNumero' namespace='/procesoemision'/>";
       	//Nuevo action de los combos
       	var _ACTION_COMBO_NIVEL = "<s:url action='getComboNivel' namespace='/procesoemision'/>";
       	var _ACTION_COMBO_ASEGURADORA = "<s:url action='getComboAseguradora' namespace='/procesoemision'/>";
       	var _ACTION_COMBO_PRODUCTO = "<s:url action='getComboProducto' namespace='/procesoemision'/>"; 
       	var _ACTION_COMBO_TIPO = "<s:url action='getComboTipos' namespace='/procesoemision'/>";
       	var _ACTION_COMBO_TIPO_DE_SITUACION = "<s:url action='getComboTipoDeSituacion' namespace='/procesoemision'/>";
       	var _GUARDAR_POLIZA_MAESTRA = "<s:url action='guardaPolizaMaestra' namespace='/procesoemision'/>";
       	var _ACTION_COMBO_ASEGURADORA_CLIENTE = "<s:url action='getComboAseguradoraPorCliente' namespace='/procesoemision'/>"
       	var _ACTION_COMBO_PRODUCTO_ASEGURADORA = "<s:url action='getComboProductoPorAseguradora' namespace='/procesoemision'/>"; 
       	
       	
       	// Ira 
	    var itemsPerPage=20;
	    
	    
	    var CODIGO_CONFIGURACION = "<s:property value='codigoConfiguracion'/>"; 
	    var DESC_NIVEL = "<s:property value='descripcionNivel'/>"; 
	    var DESC_ASEGURADORA = "<s:property value='descripcionAseguradora'/>"; 
	    var DESC_PRODUCTO = "<s:property value='descripcionProducto'/>"; 
	    var CODIGO_AGRUPACION = "<s:property value='codigoAgrupacion'/>";
		var CODIGO_TIPO = "<s:property value='codigoTipo'/>"; 
		//Vuelta de Numeracion de Polizas
		var CODIGO_POLMTRA= "<s:property value='cdPolMtra'/>";
		var COD_NUMPOL = "<s:property value='cdNumPol'/>"; 
		var DESC_ASEG = "<s:property value='dsUnieco'/>"; 
	    var DESC_PROD = "<s:property value='dsRamo'/>"; 
        var DESC_NIV = "<s:property value='dsElemen'/>"; 
		var CODIGO_TIPO = "<s:property value='codigoTipo'/>";
		
		// Popup Numero Poliza
		var _ACTION_BUSCAR_NUMERO_POLIZAS = "<s:url action='buscarNumeroPolizas' namespace='/numeroPolizas'/>";
    	var _ACTION_INSERTAR_NUMERO_POLIZA = "<s:url action='insertarNumeroPolizas' namespace='/numeroPolizas'/>";
    	var _ACTION_GUARDAR_NUMERO_POLIZA = "<s:url action='guardarNumeroPolizas' namespace='/numeroPolizas'/>";
    	var _ACTION_BORRAR_NUMERO_POLIZA = "<s:url action='borrarNumeroPolizas' namespace='/numeroPolizas'/>";
    	var _ACTION_EXPORT_NUMERO_POLIZA = "<s:url action='exportarNumeroPolizas' namespace='/numeroPolizas'/>";
    	var _ACTION_GET_NUMERO_POLIZA = "<s:url action='getNumeroPoliza' namespace='/numeroPolizas'/>";
		
		var _ACTION_OBTENER_CLIENTE_CORPO = "<s:url action='obtenerClientesCorp' namespace='/combos'/>";
    	var _ACTION_OBTENER_ASEGURADORAS_CLIENTE = "<s:url action='obtenerAseguradorasCliente' namespace='/combos'/>";
    	var _ACTION_OBTENER_PRODUCTOS_ASEGURADORA_CLIENTE = "<s:url action='obtenerProductosAseguradoraCliente' namespace='/combos'/>";
    	var _ACTION_COMBO_FORMA_CALCULO_FOLIO = "<s:url action='comboFormaCalculoFolioNroIncisos' namespace='/combos'/>";
    	var _ACTION_COMBO_INDICADOR_NUMERACION = "<s:url action='comboIndicadorNumeracionNroIncisos' namespace='/combos'/>";
    	var _ACTION_COMBO_INDICADOR_SP= "<s:url action='comboIndicador_SP_NroIncisos' namespace='/combos'/>";
    	var _ACTION_COMBO_PROCESO_POLIZA= "<s:url action='comboProcesoPoliza' namespace='/combos'/>";
    	var _ACTION_COMBO_TIPO_POLIZA= "<s:url action='obtenerTipoPoliza' namespace='/combos'/>";
    	var _ACTION_COMBO_USA_AGRUPACION= "<s:url action='obtenerSiNo' namespace='/combos'/>";
    	var _ACTION_POLIZAS_MAESTRAS= "<s:url action='irPolizasMaestras' namespace='/numeroPolizas'/>";
    	
    	//EXPRESIONES
		var ACTION_TABLA_EXPRESIONES ="<s:url namespace='expresiones' action='ComboTabla' includeParams='none'/>";
		var ACTION_COLUMNA_EXPRESIONES = "<s:url namespace='expresiones' action='ComboColumna' includeParams='none'/>";
		var ACTION_CLAVE_EXPRESIONES = "<s:url namespace='expresiones' action='ComboClave' includeParams='none'/>";
    	
    	var DES_UNIECO = "<s:property value='dsUnieco'/>";
	    var DES_ELEMENTO = "<s:property value='dsElemen'/>";
    	var DES_RAMO = "<s:property value='dsRamo'/>";
   	 	var CODIGO_POLMTRA= "<s:property value='cdPolMtra'/>";
    	var COD_NUM_POL= "<s:property value='cdNumPol'/>";
    	var COD_NUMPOL_REGRESO= "<s:property value='cdNumPol'/>";
    	
    	var PANTALLA_ORIGEN= "<s:property value='pantallaOrigen'/>";
    	var NUMERACION_POLIZAS = "NUMERACION_POLIZAS"; 
		
		var retorno = "";
    </script>
    <script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script language="javascript">
	var helpMap = new Map();
</script>
    <script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
	<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>

	<!-- Para usar lo de Expresiones:  -->
	<script type="text/javascript" src="${ctx}/resources/scripts/utilities/checkColumnPlugin.js"></script>
	<script type="text/javascript" src="${ctx}/resources/scripts/jsp/productos/tablaApoyo5Claves/tablaApoyo5Claves.js"></script>
	<script type="text/javascript" src="${ctx}/resources/scripts/jsp/utilities/expresiones/expresiones.js"></script>

	<script type="text/javascript" src="${ctx}/resources/scripts/procesos/emision/numeroPolizas.js"/></script>
    <script type="text/javascript" src="${ctx}/resources/scripts/procesos/emision/consultaPolizaMaestra.js"/></script>

</head>
    <body>
        <table cellspacing="10" border="0">
            <tr valign="top">
                <td>
                    <div id="filtrosPolizaMaestra" />
                </td>
            </tr>
            <tr valign="top">
                <td>
                    <div id="listadoPolizaMaestra" />
                </td>
            </tr>
            <tr valign="top">
                <td>
                    <div id="mainPolizaMaestra" />
                </td>
            </tr>
        </table>
    </body>
</html>    
