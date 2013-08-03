<html>
<head>
<script type="text/javascript">
//	function redirect () {
		var width, height;
		width = screen.width;
		height = screen.height;
		window.open('/aonintegracion/seleccionaRolCliente.action', 'SSOAON', config='height=' + height + ', width=' + width + ', toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, directories=no, status=no');
		window.close();
//	}
</script>
</head>
<body onLoad="redirect ();">
<div style="width: 100%; font-weight: bold; color: black;">Redireccionando....</div>
</body>
</html>
<% //response.sendRedirect("seleccionaRolCliente.action"); %>
