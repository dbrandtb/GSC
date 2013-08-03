<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<style type="text/css">    
       
        .alinear {
        margin-left: auto; margin-right: auto; 
        }
                
    </style>
<title>Datos Rol</title>

    <link rel="stylesheet" type="text/css" href="${ctx}/resources/extjs/resources/css/ext-all.css" />
    <link rel="stylesheet" type="text/css"  href="${ctx}/resources/extjs/resources/css/xtheme-gray.css" />
    <script type="text/javascript" src="${ctx}/resources/extjs/adapter/ext/ext-base.js"></script>
    <script type="text/javascript" src="${ctx}/resources/extjs/ext-all.js"></script>
    <script type="text/javascript">
        //var ElementosExt = <s:component template="camposPantallaDatosExt.vm" templateDir="templates" theme="components" ><s:param name="dext" value="dext" /></s:component>;
        var _ACTION_HOME   = "detallePoliza.action";
        
        var _cdUnieco = <s:property value="cdUnieco" />;
        var _cdRamo   = <s:property value="cdRamo" />;
        var _estado   = '<s:property value="estado" />';
        var _nmPoliza = <s:property value="nmPoliza" />;
        var _poliza   = '<s:property value="poliza" />';
        var _producto = '<s:property value="producto" />';
        var _aseguradora = '<s:property value="aseguradora" />';
    </script>
    
</head>
    <body>
    <table class="headlines" cellspacing="10">
       <tr valign="top">
           <td class="headlines">
                <div id="gridDatosRol" />
           </td>
       </tr>
    </table>
    
    <script type="text/javascript" src="${ctx}/resources/scripts/procesos/emision/datosRol.js"/></script>
    
    </body>
</html>    