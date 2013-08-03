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
<title>Endosos de Polizas</title>

    <%@ include file="/resources/jsp-script/flujos/endoso/endoso-script.jsp"%>
    <%@ include file="/resources/jsp-script/flujos/endoso/datos-generales-script.jsp"%>     
    <%@ include file="/resources/jsp-script/flujos/endoso/agentes-contratantes-script.jsp"%>  
    <%@ include file="/resources/jsp-script/flujos/endoso/agrupadores-incisos-script.jsp"%>    
    
    <%@ include file="/resources/jsp-script/flujos/endoso/grupos-script.jsp"%>
    <%@ include file="/resources/jsp-script/flujos/endoso/personas-aseguradas-script.jsp"%>
    <%@ include file="/resources/jsp-script/flujos/endoso/clausulas-script.jsp"%>
    <%@ include file="/resources/jsp-script/flujos/endoso/objetos-script.jsp"%>       
    <%@ include file="/resources/jsp-script/flujos/endoso/coberturas-sumas-aseguradas-script.jsp"%>
    <%@ include file="/resources/jsp-script/flujos/endoso/tarificacion-script.jsp"%>
  
    <!-- Common Styles for the examples -->
    <link rel="stylesheet" type="text/css" href="${ctx}/resources/css/examples/examples.css" />
</head>
<body>

<div id="principal"></div>
<div id="datosGenerales"></div>
<div id="agentes"></div>
<div id="incisos"></div>

<div id="grupos"></div>
<div id="personas"></div>
<div id="clausulas"></div>
<div id="objetos"></div>
<div id="coberturas"></div>
<div id="tarificacion"></div>
</body>
</html>