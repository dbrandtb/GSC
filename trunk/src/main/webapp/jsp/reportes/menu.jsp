<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html>
<link href="${ctx}/resources/css/template_css.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/resources/css/header_light.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/resources/css/toolbar_magenta.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/resources/css/footer_magenta.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${ctx}/resources/extjs/${ctx}/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/resources/extjs/${ctx}/resources/css/xtheme-gray.css" />
<body id="page_bg" class="w-wide f-default header-ligth toolbar-magneta footer-magneta">
<br/>
<br/>
<table>
    <tr>
        <td width="15px">&nbsp;</td>
        <td>
        <h3>Indice</h3>
            <ul>
                <li><a href="${ctx}/reportes/reportesPrincipal.action" target="contenido">Administrar Reporte</a></li>
                <li><a href="${ctx}/reportes/reportesGenerador.action" target="contenido">Ejecutar Reportes</a></li>
                <li><a href="${ctx}/reportes/reportesAdministracionPlantillas.action" target="contenido">Administrar Plantillas</a></li>
                <li><a href="${ctx}/reportes/reportesAsociarPlantillas.action" target="contenido">Asociar Plantillas</a></li>
                <li><a href="${ctx}/portal.action" target="_top">Salir</a></li>                
            </ul>
        </td>
    </tr>
</table>
</body>
</html>