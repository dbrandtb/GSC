<%@ Language="VBSCRIPT" %>
<%OPTION EXPLICIT%>
<%Server.ScriptTimeout = 300%>
<%
Dim clsUtilerias
Set clsUtilerias=Server.CreateObject("catwebSanMina.clsUtilerias")	

'Response.Cookies("ClimaCatweb")= ""

	Const sTWCi_PARTNER_ID = "1019076962"
	Const sTWCi_LICENSE_KEY = "de89b4b8f69ef1fe"
	
	Dim MsCity
	Dim MsLocID
	Dim MbMultipleLoc
	Dim MbGetLocWeather
	Dim MsIdLoc
	
	Dim FechaClima, TempClima, IconoClima, CiudadClima, LocIdClima, TempUnitsClima, HoraClima, FechaHoraClima

	FechaClima		= ""
	TempClima		= ""
	IconoClima		= ""
	CiudadClima		= ""
	LocIdClima		= ""
	TempUnitsClima	= ""
	HoraClima		= ""
	FechaHoraClima	= ""

		'Agregado para que afecte el combo al cargar la asp con el resultado....
		IF request("cmblocid") <> "" THEN
			Response.Cookies("ClimaCatweb")("LocId") = request("cmblocid")
		END IF

	Sub getData()
		MsCity = Request.Form("txtCity")
		MsLocID = Request.Form("hdnLocID")
	End Sub
	
	Function getWebServiceXML(PsWebServiceURL)
	Dim LoXmlHttp
	Dim LsXML
	
		' Objeto ServerXMLHTTP
		Set LoXmlHttp = Server.CreateObject("Msxml2.ServerXMLHTTP")
		
		' Aqui creamos la solicitud lista para ser enviada.
		' Sintaxis: .open(bstrMethod, bstrUrl, bAsync, bstrUser, bstrPassword);
		LoXmlHttp.open "GET", PsWebServiceURL, false
		
		' Enviar la solicitud. Aquí es donde se hace la consulta
		LoXmlHttp.send
		
		' Obtener el texto de la respuesta.
		' Este objeto está diseñado para lidiar com XML así que también tiene las
		' siguientes propiedades: responseText, responseBody, responseStream, y responseXML.
		' Como el web service regresa XML usamos:
		Response.ContentType="text/text"
		LsXML = LoXmlHttp.responseText
		'Response.Write LsXML
		
		Response.ContentType="text/html"
		
		' Liberar Recursos del Objeto
		Set LoXmlHttp = Nothing
		
		getWebServiceXML = LsXML
		
	End Function
	
	Function getLocId(PsCity)
	Dim LsWsURL
	Dim LsXML
	Dim LoXML
	Dim LoXMLNodeList, LoXMLNode
	Dim LoXSL
	Dim LsLocId
		
		' URL del Web Service a consultar
		LsWsURL = "http://xoap.weather.com/search/search?where=" & Trim(PsCity)
		
		' Obtener el XML resultado de utilizar el web service
		LsXML = getWebServiceXML(LsWsURL)
		
		Set LoXML = Server.CreateObject("Microsoft.XMLDOM.1.0")
			' Cargar el XML al objeto
			LoXML.loadXML(LsXML)
			
			Set LoXMLNodeList = LoXML.selectNodes(".//search/loc")
			
			Select  Case LoXMLNodeList.Length
				Case 0:
					MbGetLocWeather = False
					Response.Write "<script language='JavaScript'>alert('No se encontró información para \'" & PsCity & "\'...\nPor favor proporciona otra localidad.');</script>"
					LsLocID = ""
				Case 1:
					MbGetLocWeather = True
					
					Set LoXMLNode = LoXMLNodeList.nextNode()
					LsLocID = LoXMLNode.getAttribute("id")

				Case Else
					MbMultipleLoc = True
					MbGetLocWeather = False
					
					Set LoXSL = Server.CreateObject("Microsoft.XMLDOM.1.0")
					
					If LoXSL.Load(Server.MapPath("clima.xsl")) Then
						LsLocId = LoXML.transformNode(LoXSL)
					Else
						Response.Write "No cargó hoja de estilos XSL"
					End If
					
					Set LoXSL = Nothing
			End Select
			
			Set LoXMLNode = Nothing
			Set LoXMLNodeList = Nothing
			
		Set LoXML = Nothing
		
		getLocId = LsLocId
		
	End Function
	
	Sub getWeather(PsLocId)
	Dim LsWsURL
	Dim LsXML
	Dim LoXML
	Dim LoXMLNode
	
	Dim MsTempUnits, MsDisplayName, MsTempValue, MsIconId
		
		' URL del Web Service a consultar
		'LsWsURL = "http://xoap.weather.com/weather/local/" & Trim(PsLocId) & "?cc=*&prod=xoap&par=" & sTWCi_PARTNER_ID & "&key=" & sTWCi_LICENSE_KEY & "&unit=m"
		'Ajuste del 22/05/2008
		LsWsURL = "http://xoap.weather.com/weather/local/" & Trim(PsLocId) & "?cc=*&par=" & sTWCi_PARTNER_ID & "&key=" & sTWCi_LICENSE_KEY & "&unit=m"
		
		' Obtener el XML resultado de utilizar el web service
		LsXML = getWebServiceXML(LsWsURL)
		
		Set LoXML = Server.CreateObject("Microsoft.XMLDOM.1.0")
			' Cargar el XML al objeto
			LoXML.loadXML(LsXML)
			
			Set LoXMLNode = LoXML.selectSingleNode(".//weather/head/ut")
			MsTempUnits = LoXMLNode.text
			
			Set LoXMLNode = LoXML.selectSingleNode(".//weather/loc/dnam")
			MsDisplayName = LoXMLNode.text
			
			Set LoXMLNode = LoXML.selectSingleNode(".//weather/cc/tmp")
			MsTempValue = LoXMLNode.text
			
			Set LoXMLNode = LoXML.selectSingleNode(".//weather/cc/icon")
			MsIconId = LoXMLNode.text
			If Not IsNumeric(MsIconId) Then
				MsIconId = "na"
			End If
			
			Set LoXMLNode = Nothing
			
		Set LoXML = Nothing
		
		'Cookie
		'clsUtilerias.Generic_TimeFormat(FechaInicial,2)
		Response.Cookies("ClimaCatweb")("Fecha")	= clsUtilerias.Generic_TimeFormat(now(),9)
		Response.Cookies("ClimaCatweb")("Hora")		= clsUtilerias.Generic_TimeFormat(now(),3)
		Response.Cookies("ClimaCatweb")("Temp")		= MsTempValue
		Response.Cookies("ClimaCatweb")("Ico")		= MsIconId
		Response.Cookies("ClimaCatweb")("Ciudad")	= MsDisplayName
		Response.Cookies("ClimaCatweb")("LocId")	= PsLocId
		Response.Cookies("ClimaCatweb")("TempUnits")= MsTempUnits
		Response.Cookies("ClimaCatweb").Expires		= DateAdd("d", 1, Now) 
	
		Response.Write "<table width='100%' border='0' cellpadding='0' cellspacing='0'>" & vbCrLf
		Response.Write "<tr>" & vbCrLf
		Response.Write "	<td class='clima' colspan='2'>&nbsp;"& MsDisplayName &"</td>" & vbCrLf
		Response.Write "</tr>" & vbCrLf
		Response.Write "<tr>" & vbCrLf
		Response.Write "	<td width='50%' class='clima' align='right'><img src='/images/img_weather/"&MsIconId&".gif' width='32' height='32'>&nbsp;</td>" & vbCrLf
		Response.Write "	<td width='50%' class='clima' align='left'>&nbsp;"& MsTempValue &"&deg;&nbsp;"&MsTempUnits&"</td>" & vbCrLf
		Response.Write "</tr>" & vbCrLf
		Response.Write "</table>" & vbCrLf

	End Sub



	Sub getWeatherCookie(MsTempValue, MsIconId, MsDisplayName, MsTempUnits)
		Response.Write "<table width='100%' border='0' cellpadding='0' cellspacing='0'>" & vbCrLf
		Response.Write "<tr>" & vbCrLf
		Response.Write "	<td class='clima' colspan='2'>&nbsp;"& MsDisplayName &"</td>" & vbCrLf
		Response.Write "</tr>" & vbCrLf
		Response.Write "<tr>" & vbCrLf
		Response.Write "	<td width='50%' class='clima' align='right'><img src='/images/img_weather/"&MsIconId&".gif' width='32' height='32'>&nbsp;</td>" & vbCrLf
		Response.Write "	<td width='50%' class='clima' align='left'>&nbsp;"& MsTempValue &"&deg;&nbsp;"&MsTempUnits&"</td>" & vbCrLf
		Response.Write "</tr>" & vbCrLf
		Response.Write "</table>" & vbCrLf
	End Sub	

'	///*** M A I N  ***///
	
	MbMultipleLoc = False
	MbGetLocWeather = True
	
	getData
	
	If MsLocID = "" Then
		If MsCity <> "" Then
			MsLocID = getLocId(MsCity)
		End If
	End If
%>

<script language="JavaScript">
	function ValidaForma(forma, valor){
		if (valor!=0)
		{ forma.submit(); }
		else
		{ alert('Selecciona una localidad...'); return false; }
	}
</script>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
	<title>aoncatweb - clima</title>
	<LINK REL="StyleSheet" HREF="/library/ie.css" type="text/css">
	<script language="JavaScript" type="text/javascript">
		function setLocation(objSelect){
			document.getWeather.hdnLocID.value = objSelect.options[objSelect.selectedIndex].value;
			document.getWeather.submit();
		}
	</script>
</head>

<body topmargin="0" leftmargin="0" bgcolor="#E6E6E6">
<form name="getWeather" method="post">
<input type="Hidden" name="hdnLocID" value="">
<input type="hidden" name="hdnbuscar" value="1">
<table>
<tr>
	<td height="35"><a href="http://www.weather.com/?prod=xoap&par=<%=sTWCi_PARTNER_ID%>" target="_blank"><img src="/images/img_weather/clima_top_2.gif" width="152" height="35" border="0" alt="Servicio proporcionado por The Weather Channel"></a></td>
</tr>
<tr>
	<td>Condiciones actuales en:</td>
</tr>
<tr>
	<td>
<%
	IF Request.Cookies("ClimaCatweb") <> "" THEN 
		LocIdClima = request.Cookies("ClimaCatweb")("LocId")
	ELSE
		LocIdClima = ""
	END IF
%>
		<select name="cmblocid" onchange="javascript:ValidaForma(this.form, this.value);">
			<option value="0">Selecciona...</option>
			<% IF CSTR(LocIdClima) <> "MXDF0132" THEN %>
			<option value="MXDF0132">&nbsp;&nbsp;México</option>
			<% END IF %>
			<% IF CSTR(LocIdClima) <> "MXNL0068" THEN %>
			<option value="MXNL0068">&nbsp;&nbsp;Monterrey</option>
			<% END IF %>
			<% IF CSTR(LocIdClima) <> "MXJO0043" THEN %>
			<option value="MXJO0043">&nbsp;&nbsp;Guadalajara</option>
			<% END IF %>
		</select>
		<!--<a href="JavaScript:document.getWeather.submit();"><img src="/images/lupa.gif" border="0"></a>-->
	</td>
</tr>
<!--
<%If Not MbMultipleLoc Then%>
<tr>
	<td><input type="text" name="txtCity" size="15">&nbsp;<a href="JavaScript:document.getWeather.submit();"><img src="/images/lupa.gif" border="0"></a></td>
</tr>
<%ElseIf MbMultipleLoc And MsLocId <> "" Then%>
<tr>
	<td><%=MsLocId%></td>
</tr>
<%End If%>
-->
<%

If request("hdnbuscar")=1 THEN

	'If MbGetLocWeather And MsLocId <> "" Then
	IF request("cmblocid")<>"" THEN
		Response.Write "<tr><td>"
		'getWeather MsLocId
		getWeather request("cmblocid")
	
		Response.Write "</td></tr>"
	End If

ElseIf Request.Cookies("ClimaCatweb") <> "" THEN

	FechaClima		= clsUtilerias.Generic_TimeFormat(request.Cookies("ClimaCatweb")("Fecha"),9)
	HoraClima		= clsUtilerias.Generic_TimeFormat(request.Cookies("ClimaCatweb")("Hora"),3)
	
	FechaHoraClima  = CDATE(FechaClima & " " & HoraClima)

	TempClima		= request.Cookies("ClimaCatweb")("Temp")
	IconoClima		= request.Cookies("ClimaCatweb")("Ico")
	CiudadClima		= request.Cookies("ClimaCatweb")("Ciudad")
	LocIdClima		= request.Cookies("ClimaCatweb")("LocId")
	TempUnitsClima	= request.Cookies("ClimaCatweb")("TempUnits")
	
	IF CINT(DateDiff("h", FechaHoraClima ,CDATE(clsUtilerias.Generic_TimeFormat(Now,8)))) >= 4 THEN

		If LocIdClima <> "" Then
			Response.Write "<tr><td>"
			getWeather LocIdClima
			Response.Write "</td></tr>"
		End If

	ELSE
		
		getWeatherCookie TempClima, IconoClima, CiudadClima, TempUnitsClima

	END IF

END IF	
%>
</table>
</form>

</body>
</html>
