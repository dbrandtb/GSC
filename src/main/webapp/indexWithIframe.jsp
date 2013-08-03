<%@ include file="/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">


<html>
<head>

<script type="text/javascript">
	var ACTION_TABLA_EXPRESIONES ="<s:url namespace='expresiones' action='ComboTabla' includeParams='none'/>";
	var ACTION_COLUMNA_EXPRESIONES = "<s:url namespace='expresiones' action='ComboColumna' includeParams='none'/>";
	var ACTION_CLAVE_EXPRESIONES = "<s:url namespace='expresiones' action='ComboClave' includeParams='none'/>";
    var LIMPIA_SESION_CARGA_MANUAL="<s:url action='LimpiarSesionCargaManual' namespace='/atributosVariables'/>";
    var _ACTION_COBERTURAS = "<s:url action='CargaCombosCoberturas' namespace='/coberturas'/>";
    var _ACTION_AGREGA_COBERTURAS = "<s:url action='AgregaCobertura' namespace='/coberturas'/>";
</script>

<script type="text/javascript" src="${ctx}/resources/scripts/utilities/vTypes/combo.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/utilities/vTypes/date.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/utilities/checkColumnPlugin.js"></script>


<script type="text/javascript" src="${ctx}/resources/scripts/utilities/coberturas/catalogo/agregarCoberturas.js"></script>

<script type="text/javascript" src="${ctx}/resources/scripts/jsp/productos/tipoObjetos/principalTipoObjetos.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/jsp/utilities/tipoObjetos/agregarCatalogoObjetos.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/jsp/utilities/tipoObjetos/agregarDatosVariablesObjeto.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/jsp/utilities/tipoObjetos/eliminarDatoVariableObjeto.js"></script>

<script type="text/javascript" src="${ctx}/resources/scripts/jsp/utilities/tablaApoyo5Claves/AgregarAtributo.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/jsp/utilities/tablaApoyo5Claves/AgregarClaves.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/jsp/productos/tablaApoyo5Claves/tablaApoyo5Claves.js"></script>



<script type="text/javascript" src="${ctx}/resources/scripts/jsp/utilities/atributosVariables/listasDeValores/agregarValoresCargaManual.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/jsp/utilities/atributosVariables/listasDeValores/eliminarValoresCargaManual.js"></script>

<script type="text/javascript" src="${ctx}/resources/scripts/jsp/utilities/rol/catalogo/atributosVariablesPersona.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/jsp/utilities/atributosVariables/listasDeValores/listasDeValores.js"></script>


<script type="text/javascript" src="${ctx}/resources/scripts/jsp/utilities/definicion/clausula/clausulaIE.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/jsp/utilities/expresiones/expresiones.js"></script>

<script type="text/javascript" src="${ctx}/resources/scripts/jsp/librerias/principal/autorizacionesLibrerias.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/jsp/librerias/principal/condicionesLibrerias.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/jsp/librerias/principal/tarificacionLibrerias.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/jsp/librerias/principal/validacionesLibrerias.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/jsp/librerias/principal/variablesLibrerias.js"></script>

<script type="text/javascript" src="${ctx}/resources/scripts/jsp/utilities/definicion/periodo/periodoIE.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/jsp/utilities/definicion/periodo/periodoB.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/jsp/utilities/incisos/catalogo/agregarIncisos.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/jsp/productos/datosFijos/datosFijos.js"></script>

<script type="text/javascript" src="${ctx}/resources/scripts/jsp/utilities/conceptosCobertura/configurarConceptoCobertura.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/jsp/productos/conceptosCobertura/conceptosPorCobertura.js"></script>

<script type="text/javascript" src="${ctx}/resources/scripts/jsp/utilities/reglasValidacion/configurarReglasValidacion.js"></script>

<script type="text/javascript" src="${ctx}/resources/scripts/jsp/productos/reglasValidacion/reglasDeValidacion.js"></script>


<script type="text/javascript" src="${ctx}/resources/scripts/jsp/utilities/sumaAsegurada/eliminarSumaAsegurada.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/jsp/utilities/sumaAsegurada/agregaSumaAseguradaInciso.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/jsp/productos/sumaAsegurada/principalSumaAsegurada.js"></script>

<script type="text/javascript" src="${ctx}/resources/scripts/jsp/catalogos/tablasDeApoyo/tablaApoyoTabla5Claves.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/jsp/catalogos/tablasDeApoyo/tablaApoyoListaDeValores.js"></script>

<script type="text/javascript" src="${ctx}/resources/scripts/layouts/application-layout-browser.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/jsp/productos/definicion/principalProductosAON.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/jsp/productos/incisos/principalInciso.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/jsp/productos/atributosVariables/atributosVariables.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/jsp/utilities/rol/atributosVariables/eliminarAtributoVariable.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/jsp/productos/variablesTemporalesProducto/asociarVariablesTemporalesAlProducto.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/jsp/utilities/rol/atributosVariables/agregarAtributoVariable.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/jsp/utilities/rol/catalogo/agregarCatalogoRoles.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/jsp/productos/rol/roles.js"></script>


<script type="text/javascript" src="${ctx}/resources/scripts/jsp/productos/planes/planes.js"></script>
<jsp:include page="/resources/scripts/jsp/utilities/coberturas/catalogo/agregarCoberturas.jsp" flush="true"/>

<!-- jsp que carga el menu-->

<style type="text/css">
        
        #button-grid .x-panel-body {
            border:1px solid #99bbe8;
            border-top:0 none;
        }
        .add {
            background-image:url(resources/images/fam/add.gif) !important;
        }
        .option {
            background-image:url(resources/images/fam/plugin.gif) !important;
        }
        .remove {
            background-image:url(resources/images/fam/delete.gif) !important;
        }
        .save {
            background-image:url(resources/images/save.gif) !important;
        }
</style>
    
</head>
<body>
<div id="north" style="width: 100%;">
<table style="width: 98%; border: 0px solid;">
    <tr>
        <td align="left">
            <!-- <img src="${ctx}/resources/images/logo/logo.png">-->
            <div id="nav-toolbar"></div>
        </td>
    </tr>
</table>
</div>


<div id="center" style="height: 100%">
</div>
<div id="center-anidado" style="height: 100%">
</div>
<div id="center-anidado-periodos" style="height: 100%">
</div>

<div id="center4rolls" style="height: 100%">
</div>

<div id="centerIncisos" style="height: 100%">
</div>

<div id="centerAtributos" style="height: 100%">
</div>
<!--div id="texto-obligatorios">
<p> * Datos requeridos</p>
</div-->

<div id="centerDatosFijos" style="height: 100%">
</div>

<div id="centerConceptosGlobales" style="height: 100%">
</div>

<div id="centerCoberturas" style="height: 100%">
</div>

<div id="centerObjetos" style="height: 100%">
</div>

<div id="centerSumaAsegurada" style="height: 100%">
</div>

<div id="centerReglasValidacion" style="height: 100%">
</div>

<div id="centerConceptosCobertura" style="height: 100%">
</div>

<div id="centerVariablesTemporalesProducto" style="height: 100%">
</div>
<div id="centerPlanes" style="height: 100%">
</div>
<div id="libreriasAutorizaciones" style="height: 100%">
</div>
<div id="libreriasCondiciones" style="height: 100%">
</div>
<div id="libreriasTarificacion" style="height: 100%">
</div>
<div id="libreriasValidaciones" style="height: 100%">
</div>
<div id="libreriasVariables" style="height: 100%">
</div>

<div id="tablaApoyoListaDeValores" style="height: 100%">
</div>
<div id="tablaApoyoTabla5Claves" style="height: 100%">
</div>

<div id="empty" style="height: 100%">
</div>
<div id="empty2" style="height: 100%">
</div>

<div id="south">
<table style="width: 99%; border: 0px solid;">
    <tr>
        <td style="width: 33%;"></td>
        <td style="width: 33%;" align="center"></td>
        <td style="width: 33%;" align="right"></td>
    </tr>
</table>
<p></p>
</div>

</body>
</html>
