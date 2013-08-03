<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<%@page contentType="text/html; charset=UTF-8" %>
<%@page import="org.apache.commons.lang.StringEscapeUtils" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">    
       
        .alinear {
        margin-left: auto; margin-right: auto; 
        }
                
    </style>
<title>Ayuda Cobertura</title>

    <link rel="stylesheet" type="text/css" href="${ctx}/resources/extjs/resources/css/ext-all.css" />
    <script type="text/javascript" src="${ctx}/resources/extjs/adapter/ext/ext-base.js"></script>
    <script type="text/javascript" src="${ctx}/resources/extjs/ext-all.js"></script>
    <link href="${ctx}/resources/css/template_css.css" rel="stylesheet" type="text/css" />
       
    <script type="text/javascript">
        <%@ include file="/resources/jsp-script/procesos/emision/ayudaCobertura-script.jsp"%>    
    </script>
</head>
<body>
<div id="items"></div>
</body>
</html>