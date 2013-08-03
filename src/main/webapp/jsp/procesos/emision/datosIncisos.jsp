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
<title>Incisos</title>
 
    <script type="text/javascript">
        var ElementosExt = <s:component template="camposPantallaDatosExt.vm" templateDir="templates" theme="components" ><s:param name="dext" value="dext" /></s:component>;
        
        var _ACTION_HOME   = "detallePoliza.action";
        
        var _cdunieco       = '<s:property value="cdUnieco" />';
        var _cdramo         = '<s:property value="cdRamo" />';
        var _estado         = '<s:property value="estado" />';
        var _nmpoliza       = '<s:property value="nmPoliza" />';
        var _nmsituac       = '<s:property value="nmSituac" />';
        var _poliza         = '<s:property value="poliza" />';
        var _cdinciso       = '<s:property value="cdInciso" />';
        var _dsdescripcion  = '<s:property value="dsDescripcion" />';
        var _producto       = '<s:property value="producto" />';
        var _aseguradora    = '<s:property value="aseguradora" />';
        var _status         = '<s:property value="status" />';
        var _cdcia          = '<s:property value="cdCia" />';
        
        var _VOLVER1 		= '<s:property value="volver1" />';  //para regresar a consulta de polizas canceladas
        var _VOLVER2 		= '<s:property value="volver2" />';  //para regresar a Polizas a Renovar luego de regresar a Detalle de poliza
        var _VOLVER3 		= '<s:property value="volver3" />';  //para regresar a Polizas a Renovadas luego de regresar a Detalle de poliza
        var _VOLVER4 		= '<s:property value="volver4" />';  //para regresar a Rehabilitacion Masiva luego de regresar a Detalle de poliza
        
        var _MSG_AVISO		= 'Aviso';
        var _MSG_NO_ROW_SELECTED= 'Debe seleccionar un registro para realizar esta operacion';
    </script>
<script type="text/javascript" src="${ctx}/resources/scripts/procesos/emision/datosIncisos.js" /></script>

</head>
    <body>
    <table cellspacing="10" border="0">
        <tr valign="top">
            <td>
                <div id="encabezadoInciso" />
            </td>
        </tr>
        <tr valign="top">
            <td>
                <div id="datosInciso" />
            </td>
        </tr>
        <tr valign="top">
            <td>
                <div id="pantallaInciso" />
            </td>
        </tr>
    </table>
    
    
    
    </body>
</html>    