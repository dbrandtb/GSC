<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<title>Consulta de Pre-Siniestros</title>
	<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
	<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
    <script type="text/javascript">
		var _CONTEXT = "${ctx}";

		var _ACTION_BUSCAR_PRESINIESTROS = "<s:url action='buscarPresiniestro' namespace='/presiniestros' />";
 		var _ACTION_AGREGAR_PRESINIESTRO = "<s:url action='agregarPresiniestro' namespace='/presiniestros' />";
 		var _ACTION_CONSULTAR_PRESINIESTROS = "<s:url action='consultarPresiniestro' namespace='/presiniestros' />";
 		var _ACTION_EDITAR_PRESINIESTRO = "<s:url action='editarPresiniestro' namespace='/presiniestros' />";
		var _ACTION_EXPORTAR_BUSQUEDA = "<s:url action='exportarBusqueda' namespace='/presiniestros' />";
		var _ACTION_EXPORTAR_PRESINIESTROS = "<s:url action='exportarConsultarPresiniestro' namespace='/presiniestros' />";
 
 		var _ACTION_STORE_EMPRESA = "<s:url action='obtieneEmpresas' namespace='/presiniestros' includeParams='none' />";
 		var _ACTION_STORE_ASEGURADORA = "<s:url action='obtieneAseguradoras' namespace='/presiniestros' includeParams='none' />";
 		var _ACTION_STORE_RAMO = "<s:url action='obtieneRamos' namespace='/presiniestros' includeParams='none' />";

		var helpMap = new Map();
	</script>
	<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
	<script type="text/javascript" src="${ctx}/resources/scripts/flujos/presiniestros/consultaPresiniestros-script.js"></script>
</head>

<body>
	<div id="items"></div>
</body>
</html>