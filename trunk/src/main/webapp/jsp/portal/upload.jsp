<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Mantenimiento Planes Por Cliente</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style>
       .upload-icon {
           background: url('../shared/icons/fam/image_add.png') no-repeat 0 0 !important;
       }
       #fi-button-msg {
           border: 2px solid #ccc;
           padding: 5px 10px;
           background: #eee;
           margin: 5px;
           float: left;
       }
</style>
<script language="javascript">
		var _CONTEXT = "${ctx}";
		var _ACTION_LOGIN = "<s:url action='login' namespace='/login'/>";
		var _ACTION_OBTENER_DATOS_TREE = "<s:url action='obtenerDatosTreeView' namespace='/treeView'/>";
		var _ACTION_UPLOAD_FILE = "<s:url action='uploadFile' namespace='/treeView'/>";
		/*
		var _ACTION_MANTTO = "<s:url action='PlanesGrid' namespace='/planes'/>";
		var _ACTION_EXPORT_PLAN="<s:url action='exportPlan' namespace='/planes'/>";
		var _MANTENIMIENTO_PLANES="<s:url action='detallePlanes' namespace='/detallePlanes'/>";
		var _IR_BUSCAR_PLANES="<s:url action='irBuscarPlanes' namespace='/detallePlanes'/>";
		*/
	</script>

<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/FileUploadField.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/bo/Upload/upload.js"></script>	
</head>
<body>
        <div id="fi-basic"></div>
        <div id="fi-basic-btn"></div>
 <div id="fi-button"></div>
        <div id="fi-button-msg" style="display:none;"></div>
        <div class="x-clear"></div>
			<div id="div_form"/>
</body>
</html>