<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>

<html>

 <head>

  <title>Configurador de Pantallas</title>
  <link rel="stylesheet" type="text/css" media="screen" href="${ctx}/resources/extjs/resources/css/ext-all.css" />
  
  <link rel="stylesheet" type="text/css" href="${ctx}/resources/extjs/resources/css/xtheme-gray.css" />
  
  <link rel="stylesheet" type="text/css" media="screen" href="${ctx}/resources/scripts/configurador/designer/main.css" />

  <!-- Algunos css para el FileTree:   -->
  <link rel="stylesheet" type="text/css" media="screen" href="${ctx}/resources/scripts/configurador/designer/fileTree/css/icons.css" />
  <link rel="stylesheet" type="text/css" media="screen" href="${ctx}/resources/scripts/configurador/designer/fileTree/css/filetree.css" />
  <link rel="stylesheet" type="text/css" media="screen" href="${ctx}/resources/scripts/configurador/designer/fileTree/css/filetype.css" />

  <meta http-equiv="expires" content="never"/>
  <meta http-equiv="content-language" content="fr"/>
  <meta lang="fr" />

  <meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />


 </head>

<body>
<style>

.notice {
    color: #559;
    font-style: italic;
}
.x-form-multicheckbox {
    background:#FFFFFF;
    border:1px solid #B5B8C8;
    padding:3px;
}


</style>
<script type="text/javascript" src="${ctx}/resources/scripts/configurador/designer/include.js"></script>
<script type="text/javascript" src="${ctx}/resources/extjs/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="${ctx}/resources/extjs/ext-all.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/configurador/designer/Ext.drasill2.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/configurador/designer/cfg.js"></script>




<!-- Algunos archivos para el control FileTree: -->
<script type="text/javascript">
	var rutaImagenes = "${ctx}/resources/images/";
	Ext.BLANK_IMAGE_URL = '${ctx}/resources/extjs/resources/images/default/s.gif';
</script>
<script src="${ctx}/resources/scripts/configurador/designer/fileTree/Ext.ux.FileTreeMenu.js" type="text/javascript"></script>
<script src="${ctx}/resources/scripts/configurador/designer/fileTree/Ext.ux.FileTreePanel.js" type="text/javascript"></script>
<script src="${ctx}/resources/scripts/configurador/designer/fileTree/Ext.ux.FileUploader.js" type="text/javascript"></script>
<script src="${ctx}/resources/scripts/configurador/designer/fileTree/Ext.ux.form.BrowseButton.js" type="text/javascript"></script>
<script src="${ctx}/resources/scripts/configurador/designer/fileTree/Ext.ux.UploadPanel.js" type="text/javascript"></script>
	<!--  script type="text/javascript" src="${ctx}/resources/scripts/util/Ext.ux.helpIcon.js"></script-->
	<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
	<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>


<!-- Nota: el script   configurador-alfa-script.jsp    no se incluye puesto que se separo en 7 archivos -->
<script type="text/javascript">
	var _CONTEXT = "${ctx}";
	<jsp:include page="/resources/scripts/jsp/configurador/pantallas/variables.jsp" flush="true" />

    <jsp:include page="/resources/scripts/jsp/configurador/pantallas/setParametersTab-script.jsp" flush="true" />
    <jsp:include page="/resources/scripts/jsp/configurador/pantallas/panelsViewport-script.jsp" flush="true" />
    <jsp:include page="/resources/scripts/jsp/configurador/pantallas/parametrosTab-script.jsp" flush="true" />
    <jsp:include page="/resources/scripts/jsp/configurador/pantallas/datosPantallaTab-script.jsp" flush="true" />
    <jsp:include page="/resources/scripts/jsp/configurador/pantallas/activationTab-script.jsp" flush="true" />
    <jsp:include page="/resources/scripts/jsp/configurador/pantallas/panelsTabZone-script.jsp" flush="true" />
    <jsp:include page="/resources/scripts/jsp/configurador/pantallas/vistaPrevia-script.jsp" flush="true" />
     
          
    <jsp:include page="/resources/scripts/jsp/configurador/pantallas/componentData/layoutComponents-script.jsp" flush="true" />
    <jsp:include page="/resources/scripts/jsp/configurador/pantallas/componentData/commonComponents-script.jsp" flush="true" />
    <jsp:include page="/resources/scripts/jsp/configurador/pantallas/componentData/procesoComponents-script.jsp" flush="true" />

    <jsp:include page="/resources/scripts/jsp/configurador/pantallas/main.jsp" flush="true" />
</script>




<script type="text/javascript"> 

    function seleccionarComponente()
    {
     alert('builderPanel is ' + document.getElementById('FBBuilderPanel').value );
     document.getElementsByName('componente').value = document.getElementById('FBBuilderPanel').value;
     // alert("componente.. " + document.getElementsByName('componente').value);
     } 
   
</script>
<div id="divFormPanelDatos"></div>
<canvas width="100" height="100"></canvas>
</body>
</html>
