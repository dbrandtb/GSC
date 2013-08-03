<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Checklist de la configuración de la cuenta</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script language="javascript">
	var _CONTEXT = "${ctx}";
	var _ACTION_OBTENER_CLIENTE_CORPO = "<s:url action='obtenerClientesCorpo' namespace='/checklistConfiguraCuenta'/>";
	var _ACTION_COPIA_CONFIGURA_CUENTA = "<s:url action='copiarConfiguraCuenta' namespace='/checklistConfiguraCuenta'/>";
    var itemsPerPage=10;
	<%=session.getAttribute("helpMap")%>  
    
</script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>

<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<!--[if lte IE 6]>
<link href="${ctx}/resources/css/template_ie.css" rel="stylesheet" type="text/css" />
<![endif]-->
<style type="text/css">
    #logo {
    	background: url(${ctx}/resources/images/login/logo.png) no-repeat;
        position: relative;
        top: 0px;
        left: 0px;
        display: block;
        width:200px;
        height: 66px;
    }
    #message {
        position: relative;
        top: 5px;
        left: 15px;
        bottom: 5px;
        width: 280px;
    }
    #messageError {
    	color: #840C2C;
    }
    
</style>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/checkListCuenta/checkListCopiaConfiguraCuenta.js"></script>
</head>
<body>
    <br/><br/><br/><br/><br/>
    <div id="main" align="center">
        <div style="width:600px">
            <div class="x-box-tl"><div class="x-box-tr"><div class="x-box-tc"></div></div></div>
                <div class="x-box-ml"><div class="x-box-mr"><div class="x-box-mc">
                    <table style="width:100%"/>
                        <tr>
                            <td id="formulario" align="center">&nbsp;</td>
                        </tr>
				        <tr valign="top">
				            <td class="headlines" colspan="2">
				                <div id="formCopiaConfigura" />
				            </td>
				        </tr>
                    </table>
                </div></div></div>
            <div class="x-box-bl"><div class="x-box-br"><div class="x-box-bc"></div></div></div>
        </div>
    </div>
</body>
</html>