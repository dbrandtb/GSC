<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<title>Pre-Siniestros - Da&ntilde;os</title>
	<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
	<%! java.text.SimpleDateFormat formatoSimple = new java.text.SimpleDateFormat("dd/MM/yyyy"); %>
    <script type="text/javascript">
		var _CONTEXT = "${ctx}";

		var _ACTION_CONSULTA_PRESINIESTROS = "<s:url action='consultaPolizasPresiniestros' namespace='/presiniestros' />";
		var _ACTION_GUARDAR_PRESINIESTRO_DANOS = "<s:url action='guardarPresiniestroDano' namespace='/presiniestros' />";
		var _TIPO_OPERACION = "<s:property value='tipoOperacion' />";
		var _folio = "<s:property value='dano.folio' />";
		var _producto = "<s:property value='dano.producto' />";
		var _fecha = "<s:property value='dano.fecha' />";
		var _poliza = "<s:property value='dano.poliza' />";
		var _inciso = "<s:property value='dano.inciso' />";
		var _aseguradora = "<s:property value='dano.aseguradora' />";
		var _ramo = "<s:property value='dano.ramo' />";
		var _nombreAsegurado = "<s:property value='dano.nombreAsegurado' />";
		var _personaRecibeReporte = "<s:property value='dano.personaRecibeReporte' />";
		var _embarqueBienesDanados = "<s:property value='dano.embarqueBienesDanados' />";
		var _descripcionDano = "<s:property value='dano.descripcionDano' />";
		var _fechaDano = "<s:property value='dano.fechaDano' />";
		var _lugarBienesAfectados = "<s:property value='dano.lugarBienesAfectados' />";
		var _personaEntrevista = "<s:property value='dano.personaEntrevista' />";
		var _telefono1 = "<s:property value='dano.telefono1' />";
		var _estimacionDano = "<s:property value='dano.estimacionDano' />";
		var _personaReporto = "<s:property value='dano.personaReporto' />";
		var _telefono2 = "<s:property value='dano.telefono2' />";

		var fechaHoy = "<%= formatoSimple.format(new java.util.Date()) %>"; 

	</script>
	
	<script type="text/javascript" src="${ctx}/resources/scripts/flujos/presiniestros/presiniestrosDanos-script.js"></script>
</head>

<body>
	<div id="items"></div>
</body>
</html>