<%@ include file="/taglibs.jsp"%>


<script type="text/javascript">
    var _CONTEXT = '${ctx}';
    
    var _URL_CATALOGOS                  = '<s:url namespace="/catalogos"  action="obtieneCatalogo" />';
    var _URL_CONSULTA_AUTORIZACION_ESP1 = '<s:url namespace="/siniestros" action="consultaAutorizacionServicio" />';
    var _URL_CONSULTA_DEDUCIBLE_COPAGO1 = '<s:url namespace="/siniestros" action="consultaListaDatSubGeneral" />';
    var _URL_LISTA_SUBCOBERTURA1		= '<s:url namespace="/siniestros" action="consultaListaSubcobertura" />';
    var _URL_LISTA_TMANTENI1			= '<s:url namespace="/siniestros" action="consultaListaManteni" />';
    var _URL_LISTADO_CONCEP_EQUIP1      = '<s:url namespace="/siniestros" action="consultaListaTDeTauts" />';
    var _UR_LISTA_PLAZAS 					= '<s:url namespace="/siniestros"  action="consultaListaPlazas"/>';
    
    var _CAT_AUTORIZACION1	 = '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@MC_SUCURSALES_ADMIN"/>';
    var _CAT_CAUSASINIESTRO1 = '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@CAUSA_SINIESTRO"/>';
    var _CAT_TRATAMIENTO1	 = '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@TRATAMIENTOS"/>';
    var _CAT_MEDICOS         = '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@MEDICOS"/>';
    var _URL_EXCLUSION_PENALIZACION			= '<s:url namespace="/siniestros"  action="validaExclusionPenalizacion"/>';
    var _URL_POLIZA_UNICA					= '<s:url namespace="/siniestros"  action="consultaPolizaUnica"/>';
    var _URL_PORCENTAJE_PENALIZACION1		= '<s:url namespace="/siniestros"  action="validaPorcentajePenalizacion"/>';
    
    // Obtenemos el contenido en formato JSON de la propiedad solicitada:
    var valorAction = <s:property value="%{convertToJSON('params')}" escapeHtml="false" />;
    
</script>
       
<script type="text/javascript" src="${ctx}/js/proceso/siniestros/verAutorizacionServicios.js"></script>
   
<div style="height:100px;">
    <div id="div_clau21"></div>
</div>