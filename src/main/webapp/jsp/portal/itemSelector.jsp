<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Mantenimiento Planes Por Cliente</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script language="javascript">
		var _CONTEXT = "${ctx}";
		var _ACTION_LOGIN = "<s:url action='login' namespace='/login'/>";
		var _ACTION_OBTENER_DATOS_TREE = "<s:url action='obtenerDatosTreeView' namespace='/treeView'/>";
		var _ACTION_OBTENER_DATOS_MULTI = "<s:url action='arreglito' namespace='/notificaciones'/>";

		/*
		var _ACTION_MANTTO = "<s:url action='PlanesGrid' namespace='/planes'/>";
		var _ACTION_EXPORT_PLAN="<s:url action='exportPlan' namespace='/planes'/>";
		var _MANTENIMIENTO_PLANES="<s:url action='detallePlanes' namespace='/detallePlanes'/>";
		var _IR_BUSCAR_PLANES="<s:url action='irBuscarPlanes' namespace='/detallePlanes'/>";
		*/
	</script>
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/Multiselect.css"/>
<script type="text/javascript" src="${ctx}/resources/scripts/util/Multiselect.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/DDView.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<!--script type="text/javascript" src="${ctx}/resources/scripts/util/PagingToolbar.js"></script-->
<script type="text/javascript" src="${ctx}/resources/scripts/bo/itemSelector/itemSelector.js"></script>	
</head>
<body>
<div style="width:800px;">
    <div class="x-box-tl"><div class="x-box-tr"><div class="x-box-tc"></div></div></div>
    <div class="x-box-ml"><div class="x-box-mr"><div class="x-box-mc">

        <div id="form-ct-multiselect"></div>
    </div></div></div>
    <div class="x-box-bl"><div class="x-box-br"><div class="x-box-bc"></div></div></div>
</div>

<br><p>


<div style="width:800px;">
    <div class="x-box-tl"><div class="x-box-tr"><div class="x-box-tc"></div></div></div>
    <div class="x-box-ml"><div class="x-box-mr"><div class="x-box-mc">
        <div id="form-ct-itemselector"></div>

    </div></div></div>
    <div class="x-box-bl"><div class="x-box-br"><div class="x-box-bc"></div></div></div>
</div>

</body>
</html>