<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>ICE</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!-- Styles ExtJs without changes menu.jsp-->
<link rel="stylesheet" type="text/css" href="${ctx}/resources/extjs/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/resources/extjs/resources/css/xtheme-gray.css" />


<link href="${ctx}/resources/css/template_css.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/resources/css/header_light.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/resources/css/toolbar_magenta.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/resources/css/footer_magenta.css" rel="stylesheet" type="text/css" />
</head>
	<body>
		<div id="header">
			<div class="wrapper">
			    <br/>
				<font style="font-size: 17px;font-family:calibri,tahoma,arial,helvetica;padding: 20px 0px 0px 0px;">
				    Bienvenido
				    <s:if test="%{#session.containsKey('USUARIO')}">
					    usuario: <s:property value="%{#session['USUARIO'].name}" />
					    rol:     <s:property value="%{#session['USUARIO'].rolActivo.objeto.label}" />
				    </s:if>
				</font>
            	<div>            		
            		<div>
                		<table class="showcase" cellspacing="1" height="120">
							<tr valign="top">
								<td class="showcase">
                            		<div id="top_lefta">
                            			<s:if test="%{#session.containsKey('CONTENIDO_TOPLEFT')}">
                            				<s:component template="topLeft.vm" templateDir="templates" theme="pages" >
												<s:param name="CONTENIDO_TOPLEFT" value="%{#session['CONTENIDO_TOPLEFT']}"/>
											</s:component>
										</s:if>
										<s:elseif test="%{#session.containsKey('CONTENIDO_TOPLEFT_IMAGE')}">
											<img src="<s:url value="%{#session['CONTENIDO_TOPLEFT_IMAGE']}"/>" width="300" height="100" />
										</s:elseif>													
										<s:else>
											<a href="#" title="home"><span id="logo_left">&nbsp;</span></a>									
										</s:else>
                            		</div>
								</td>
								<td class="showcase">
                            		<div id="top_centera">
                            			<s:if test="%{#session.containsKey('CONTENIDO_TOPCENTER')}">
                            				<s:component template="topCenter.vm" templateDir="templates" theme="pages" >
												<s:param name="CONTENIDO_TOPCENTER" value="%{#session['CONTENIDO_TOPCENTER']}"/>
											</s:component>
										</s:if>
										<s:elseif test="%{#session.containsKey('CONTENIDO_TOPCENTER_IMAGE')}">
											<img src="<s:url value="%{#session['CONTENIDO_TOPCENTER_IMAGE']}"/>" width="300" height="100" />
										</s:elseif>										
                            		</div>
								</td>
								<td class="showcase">
                            		<div id="top_righta">
                            			<s:if test="%{#session.containsKey('CONTENIDO_TOPRIGHT')}">
                            				<s:component template="topRight.vm" templateDir="templates" theme="pages" >
												<s:param name="CONTENIDO_TOPRIGHT" value="%{#session['CONTENIDO_TOPRIGHT']}"/>
											</s:component>
										</s:if>
										<s:elseif test="%{#session.containsKey('CONTENIDO_TOPRIGHT_IMAGE')}">
											<img src="<s:url value="%{#session['CONTENIDO_TOPRIGHT_IMAGE']}"/>" width="300" height="100" />
										</s:elseif>							
                            		</div>
								</td>
							</tr>
						</table>		
			    	</div>
			    	<div id="top"></div>
            	</div>
			</div>
		</div>
		<div id="toolbar">
			<div class="wrapper">
				<div id="nav"></div>
			</div>
		</div>
		<div id="nav-toolbar"></div>
	</body>	
<script type="text/javascript" src="${ctx}/resources/extjs/ext-all.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<script type="text/javascript">
Ext.onReady(function(){
	Ext.QuickTips.init();
	
	//Cargar Contenido para el Menu Horizontal		
	var conn = new Ext.data.Connection();
	conn.request({
		url: 'obtieneMenuPrincipal.action',
		success: function(a){
			var tb = new Ext.Toolbar({
				items: Ext.util.JSON.decode(a.responseText),
				renderTo: 'nav-toolbar',
				listeners:{
					render: function(){
						
						//Se asignan dinamicamente el ancho del div 'header' y del menu(tb)
						var obj = document.getElementById('mainbody');
						this.width = obj.offsetWidth;
						
						var header = document.getElementById('header'); 
						header.style.width = obj.offsetWidth;
					}
				}
								
			});					
		},
		failure: function(){
			Ext.MessageBox.alert('Error', 'Error cargando Menu principal');
		}
	});
	
	//Cargar Contenido para el Menu Vertical
	var conn2 = new Ext.data.Connection();
	conn2.request({
		url: 'obtieneMenuVertical.action',
		success: function(a){
			//Se crea el nodo del menu vertical:
			var tagUL = document.createElement("ul");
			//Se crean elementos para el nodo (li con links):
			var items = Ext.util.JSON.decode(a.responseText);
			Ext.each(items, function(item) {
				if(!Ext.isEmpty(item.text)){
					var nuevoTagLI = document.createElement("li");
					var nuevoLink = document.createElement('a');
					nuevoLink.setAttribute('href', "${ctx}" + item.href);
					nuevoLink.appendChild(document.createTextNode(item.text));
					nuevoTagLI.appendChild(nuevoLink);
					tagUL.appendChild(nuevoTagLI);
    				//alert('item=' + item.href + ',' + item.text);
    			}
			});
			//Se agrega el nodo al menu vertical:
			var divMenuVertical = document.getElementById('nav-toolbar-vertical');
			divMenuVertical.appendChild(tagUL);
		},
		failure: function(){
			Ext.MessageBox.alert('Error', 'Error cargando Menu vertical');
		}
	});
});
</script>		
</html>