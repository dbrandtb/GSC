<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"  %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="../../js/confpantallas/extjs/include-ext.js?theme=neptune"></script>
<title>Vista de codigo ExtJS</title>

</head>
<body>
<c:forEach var="panel" items="${datoSesion}"> 
<c:out value="${panel.codigo}" escapeXml="false" />
</c:forEach>











</body>
</html>








