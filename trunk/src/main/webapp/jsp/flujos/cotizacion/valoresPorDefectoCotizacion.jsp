<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<style type="text/css">    
        #button-grid .x-panel-body {border:1px solid #99bbe8; border-top:0 none;}.logo{background-image:url(../resources/images/aon/bullet_titulo.gif) !important;}        
    </style>
<title>Cotización</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<script type="text/javascript"> 
var ElementosExt = <s:component template="despliegaItemsIncisos.vm" templateDir="templates" theme="components" ><s:param name="item" value="item" /></s:component>;
</script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<%@ include file="/resources/jsp-script/flujos/cotizacion/valoresPorDefectoCotizacion-script.jsp"%>
<script type="text/javascript">
		var _CONTEXT = "${ctx}";
</script>
</head>
<body>
   <table class="headlines" cellspacing="10">
	   <tr valign="top">
            <td class="headlines" colspan="2">
                <div id="items" />
            </td>
       </tr>
       <tr valign="top">
           <td class="headlines" colspan="2">
                <div id="gridConfig" />
           </td>
       </tr>
    </table>
</body>
</html>