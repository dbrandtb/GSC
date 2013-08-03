<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Mantenimiento Planes Por Cliente</title>
<meta http-equiv="Expires" content="Tue, 20 Aug 2010 14:25:27 GMT"/> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script language="javascript">
		var _CONTEXT = "${ctx}";
		/*
		var _ACTION_MANTTO = "<s:url action='PlanesGrid' namespace='/planes'/>";
		var _ACTION_EXPORT_PLAN="<s:url action='exportPlan' namespace='/planes'/>";
		var _MANTENIMIENTO_PLANES="<s:url action='detallePlanes' namespace='/detallePlanes'/>";
		var _IR_BUSCAR_PLANES="<s:url action='irBuscarPlanes' namespace='/detallePlanes'/>";
		*/
	</script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<!--script type="text/javascript" src="${ctx}/resources/scripts/util/PagingToolbar.js"></script-->
<script type="text/javascript" src="${ctx}/resources/scripts/login/loginAccepted.js"></script>	
</head>
<body>
   <table class="headlines" cellspacing="10">
        <tr valign="top">
            <td class="headlines" colspan="2">
                <div id="formBusqueda" />
            </td>
        </tr>
       <tr valign="top">
           <td class="headlines" colspan="2">
               	<div id="gridElementos" />
           </td>
       </tr>
    </table>
</body>
</html>