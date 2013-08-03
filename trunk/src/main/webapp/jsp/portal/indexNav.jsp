<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Navegacion</title>
<meta http-equiv="content-type" content="text/html; charset=ISO-8859-1" />

<script language="javascript">
	var _CONTEXT = "${ctx}";
	var _BROWSER = navigator.appName;
	var _CLASS = _BROWSER=="Netscape"?"class":"className";
	
	function actual( componente ){
		//alert(_BROWSER);
		// Se limpian la pestaña de donde este
		var lista = document.getElementById("nav_index");
		for(var i = 0 ; i<lista.childNodes.length; i++){
			var node = lista.childNodes[i];
			if ( node.nodeName == "LI" ) {
				//alert(node + ' : ' + i + ' : ' + node.nodeName + ' : ' + node.getAttribute("class"));
				node.setAttribute(_CLASS,"");
			}
		}
		var actual = document.getElementById( componente );
		actual.setAttribute(_CLASS,"active_menu");
		// Se actualiza el indice lateral
	}
</script>

<!--[if lte IE 6]>
<link href="${ctx}/resources/css/template_ie.css" rel="stylesheet" type="text/css" />
<![endif]-->

</head>
<body>
<ul id="nav_index">
	<s:iterator id="listaMenu" value="listaMenu" status="st">
		<li id="<s:property value='id'/>"
			<s:if test="#st.first">class="active_menu"</s:if> > 
			<a href="#"
			onClick="actual('<s:property value='id'/>','<s:property value='link'/>');"> <s:property
			value='texto' /> </a></li>
	</s:iterator>
</ul>
</body>
</html>
