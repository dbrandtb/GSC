<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"  %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="../../js/confpantallas/extjs/include-ext.js?theme=neptune"></script>
<title>Vista de tu panel parametrizado</title>
<script>
Ext.onReady(function() {
	Ext.QuickTips.init();
	Ext.define('ComboData', {extend: 'Ext.data.Model',fields: [{type: 'string', name: 'key'},{type: 'string', name: 'value'}]});
	/******************************* Data ******************************************/
    <c:forEach var="dataVar" items="${listaCatalogosEA}">
		var val<c:out value="${dataVar.nombreVar}" escapeXml="false" /> = '<c:out value="${dataVar.nombreVar}" escapeXml="false" />';
        var store<c:out value="${dataVar.nombreVar}" escapeXml="false" /> = Ext.create('Ext.data.Store',{model:'ComboData',proxy: {type: 'ajax',url: '../../confpantallas/cargainfo.action',reader: {type: 'json',root: 'success'},extraParams: {tarea: '<c:out value="${dataVar.descripcion}" escapeXml="false" />', tabla:val<c:out value="${dataVar.nombreVar}" escapeXml="false" />, valor:val<c:out value="${dataVar.nombreVar}" escapeXml="false" />}},autoLoad: false});	
    </c:forEach>
    /******************************* STORE ******************************************/
	
	var target = new Ext.Panel({ 
        id: 'contenedor',autoScroll:true,
        border: false,renderTo: Ext.getBody()});

	<c:forEach var="panel" items="${datoSesion}"> 
	 
	 <c:choose>
	 	<c:when test="${panel.xtype=='window'}">
	 		var <c:out value="${panel.nombreVar}" escapeXml="false" /> = new <c:out value="${panel.codigo}" escapeXml="false" />
	 		<c:out value="${panel.nombreVar}" escapeXml="false" />.show();
	 	</c:when>
        <c:otherwise>
        	var <c:out value="${panel.nombreVar}" escapeXml="false" /> = <c:out value="${panel.codigo}" escapeXml="false" />
        	target.add(<c:out value="${panel.nombreVar}" escapeXml="false" />);  
        </c:otherwise>
	 </c:choose>
  	</c:forEach>
 
});
</script>
</head>
<body>
</body>
</html>








