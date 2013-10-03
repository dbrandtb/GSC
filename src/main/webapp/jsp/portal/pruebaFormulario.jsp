<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Inicio</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
    <s:form action="enviaCorreo" namespace="/">
        <s:textfield name="to" label="Para"/>
        <s:textfield name="cc" label="CC"/>
        <s:textfield name="bcc" label="BCC"/>
        <s:textfield name="archivo" label="Ruta de archivo 1" />
        <s:textfield name="archivo" label="Ruta de archivo 1" />
        <s:textfield name="archivo" label="Ruta de archivo 3" />
        <s:submit label="Enviar Correo"/>
    </s:form>
</body>
</html>