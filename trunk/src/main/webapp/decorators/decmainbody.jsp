<%@ include file="/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
    "http://www.w3.org/TR/html4/loose.dtd">
    
    <!-- pagina para decorator "default2": -->
    
<html>
<head> 
<script language="javascript">

    var _AUTHORIZED_EXPORT = "${authorizedExport}";

	if (window.location.href.indexOf("login.html") != -1) {
		window.parent.location.href = "http://acw.biosnettcs.com:7778/login/login.html";
	}
</script>    
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link href="${ctx}/resources/css/header_light.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/resources/css/toolbar_magenta.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/resources/css/footer_magenta.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="${ctx}/resources/extjs/resources/css/ext-all.css" />
	<link rel="stylesheet" type="text/css" href="${ctx}/resources/extjs/resources/css/xtheme-gray.css" />
	<link href="${ctx}/resources/css/template_css.css" rel="stylesheet" type="text/css" />
    <decorator:head />
</head>
<body>
    <decorator:body />
</body>
</html>
