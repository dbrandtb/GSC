<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<title>Pre-Siniestros - Autom&oacute;viles</title>
	<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
	<%! java.text.SimpleDateFormat formatoSimple = new java.text.SimpleDateFormat("dd/MM/yyyy"); %>
    <script type="text/javascript">
		var _CONTEXT = "${ctx}";

		var _ACTION_CONSULTA_PRESINIESTROS = "<s:url action='consultaPolizasPresiniestros' namespace='/presiniestros' />";
		var _ACTION_GUARDAR_PRESINIESTRO_AUTOMOVILES = "<s:url action='guardarPresiniestroAutomovil' namespace='/presiniestros' />";
		var _TIPO_OPERACION = "<s:property value='tipoOperacion' />";
		var _folio = "<s:property value='automovil.folio' />";
		var _fecha = "<s:property value='automovil.fecha' />";
		var _poliza = "<s:property value='automovil.poliza' />";
		var _certificado = "<s:property value='automovil.certificado' />";
		var _aseguradora = "<s:property value='automovil.aseguradora' />";
		var _asegurado = "<s:property value='automovil.asegurado' />";
		var _reportadoPor = "<s:property value='automovil.reportadoPor' />";
		var _conductor = "<s:property value='automovil.conductor' />";
		var _telefono1 = "<s:property value='automovil.telefono1' />";
		var _telefono2 = "<s:property value='automovil.telefono2' />";
		var _telefono3 = "<s:property value='automovil.telefono3' />";
		var _marca = "<s:property value='automovil.marca' />";
		var _modelo = "<s:property value='automovil.modelo' />";
		var _numeroMotor = "<s:property value='automovil.numeroMotor' />";
		var _numeroSerie = "<s:property value='automovil.numeroSerie' />";
		var _numeroPlacas = "<s:property value='automovil.numeroPlacas' />";
		var _color = "<s:property value='automovil.color' />";
		var _lugarVehiculo = "<s:property value='automovil.lugarVehiculo' />";
		var _colonia = "<s:property value='automovil.colonia' />";
		var _delegacion = "<s:property value='automovil.delegacion' />";
		var _descripcionAccidente = "<s:property value='automovil.descripcionAccidente' />";
		var _fechaAccidente = "<s:property value='automovil.fechaAccidente' />";
		var _horaAccidente = "<s:property value='automovil.horaAccidente' />";
		var _tercero = "<s:property value='automovil.tercero' />";
		var _taller =  "<s:property value='automovil.taller' />";
		var _reportoAPersonal = "<s:property value='automovil.reportoAPersonal' />";
		var _fechaReportada = "<s:property value='automovil.fechaReportada' />";
		var _horaReportada = "<s:property value='automovil.horaReportada' />";
		var _numeroReporte = "<s:property value='automovil.numeroReporte' />";
		var _fechaReporte = "<s:property value='automovil.fechaReporte' />";
		var _horaReporte = "<s:property value='automovil.horaReporte' />";

		var fechaHoy = "<%= formatoSimple.format(new java.util.Date()) %>";

	</script>
	
	<script type="text/javascript" src="${ctx}/resources/scripts/flujos/presiniestros/presiniestrosAutomoviles-script.js"></script>
</head>

<body>
	<div id="items"></div>
</body>
</html>