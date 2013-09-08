<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/taglibs.jsp"%>
<%@page import="mx.com.aon.portal.model.PerfilVO"%>
<jsp:useBean id="userVO" class="mx.com.aon.portal.model.UserVO" scope="session" />

<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${ctx}/resources/css/template_css.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/resources/css/header_light.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/resources/css/toolbar_magenta.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/resources/css/footer_magenta.css" rel="stylesheet" type="text/css" />

<!-- gabriel F Estilos para extJs portal.jsp-->

<link rel="stylesheet" type="text/css" href="${ctx}/resources/extjs/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/resources/extjs/resources/css/xtheme-gray.css" />	
<!-- ESTE BLOQUE IMPORTA jQUERY Y REEMPLAZA LA LIGA a cotizacion vital 2 -->
<script src="${ctx}/resources/extjs4/jquery-1.10.2.min.js" ></script>
<script>
    var miIntervalCambioLiga;
    function reemplazaLiga()
    {
        var $cmp=$("li a[href*='null?CDTITULO']");
        if($cmp.length>0)
        {
            $cmp.attr({
                href:'javascript:LoadPage("/cotizacionVital.action");'
            	//href:'javascript:LoadPage("/datosComplementarios.action");'
            });
            window.clearInterval(miIntervalCambioLiga);
            window.console&&console.log("STOP");
        }
        else
        {
            window.console&&console.log("SEARCHING LIGA...");
        }
    }
    $(document).ready(function(){
        miIntervalCambioLiga=setInterval(reemplazaLiga,500);
    });
</script>
<!-- FIN ESTE BLOQUE IMPORTA jQUERY Y REEMPLAZA LA LIGA a cotizacion vital 2 -->

<!-- Estilo para algunos elementos de las secciones -->
<link href="${ctx}/resources/css/secciones.css" rel="stylesheet" type="text/css" />

<style type="text/css">
  strong {font-weight: bold;}
  em {font-style: italic;}
</style>

<script language="javascript">
	var _CONTEXT = "${ctx}";
	
	var sizeIFrame = 0;
	
	function setSizeHeight(_size){
		var objFrame = parent.document.getElementById('bodyFrame');
		var _height = window.frames["bodyFrame"].document.activeElement.clientHeight;
		if(_size && _size != undefined){if(_height != 0){objFrame.height = _height + 129 + _size;}}
		else {if(_height != 0){objFrame.height = _height + 129;}}
		
	}	
	
	function setAbsoluteSizeHeight(_size){
		var objFrame = parent.document.getElementById('bodyFrame');
		if(_size != undefined){objFrame.height = _size;}
	}
	 
	function LoadPage(url, _sizeIFrame){
		var objFrame = document.getElementById("bodyFrame");
		var rutaValida = true;
			
		if( url.indexOf('?') >= 0 ){
		   ruta = url.split('?');
		   direction = ruta[0];   
		   
		     if( direction == null || direction == 'null'  || direction == '\/#' ){
		        //alert('dont validate src');
		        url = "";
		        rutaValida = false;
		     }
		}
		
       if(  rutaValida ){
		 if (url.substring(0,4) == 'http')
			objFrame.src = url;
		 else
			objFrame.src = _CONTEXT + url;
	   }
	}	
	
	//CODIFICAR PARA QUE LA ALTURA DEL FORMULARIO SEA DINAMICA
	//var altura = dinamicFormPanel.body.dom.clientHeight;
	//window.parent.document.getElementById('atributosVariables').contentWindow.document.body.height;
	//window.parent.document.getElementById('atributosVariables').height = eval(altura) + 20;
	
	
	// funci�n para mostrar la pantalla de los link
    function openWind ( URL ){ 
        window.open(URL,'',"width=800,height=600,Scrollbars=YES,Resizable=YES"); 
    } 
   
	
	
	
	// Se inicializan las fuentes de la pantalla para la carga
    var sourceForPanel = new Array();

    // Se asignan las fuentes iniciales

    <%  PerfilVO perfil = userVO.getFuentesPerfil();  %>
    
    sourceForPanel[0]  = '<%=perfil.getTop()!=null?perfil.getTop():""%>'; // /resources/static/info.html
    sourceForPanel[1]  = '<%=perfil.getNav()!=null?perfil.getNav():""%>'; // nada
    sourceForPanel[2]  = '<%=perfil.getTopLeft()!=null?perfil.getTopLeft():""%>'; // /resources/static/infoTit.html
    sourceForPanel[3]  = '<%=perfil.getTopCenter()!=null?perfil.getTopCenter():""%>'; // /resources/static/infoTit.html
    sourceForPanel[4]  = '<%=perfil.getTopRight()!=null?perfil.getTopRight():""%>'; // /resources/static/infoTit.html
    sourceForPanel[5]  = '<%=perfil.getLeft_1()!=null?perfil.getLeft_1():""%>'; // /resources/static/navTit.jsp ++++ despliegaMenuVertical.vm
    sourceForPanel[6]  = '<%=perfil.getLeft_2()!=null?perfil.getLeft_2():""%>'; // /resources/static/infoTit.html
    sourceForPanel[7]  = '<%=perfil.getLeft_3()!=null?perfil.getLeft_3():""%>'; // /resources/static/infoTit.html
    sourceForPanel[8]  = '<%=perfil.getLeft_4()!=null?perfil.getLeft_4():""%>'; // /resources/static/infoTit.html
    sourceForPanel[9]  = '<%=perfil.getLeft_5()!=null?perfil.getLeft_5():""%>'; // /resources/static/infoTit.html
    sourceForPanel[10] = '<%=perfil.getRight_1()!=null?perfil.getRight_1():""%>'; // /resources/static/infoTit.html
    sourceForPanel[11] = '<%=perfil.getRight_2()!=null?perfil.getRight_2():""%>'; // /resources/static/infoTit.html
    sourceForPanel[12] = '<%=perfil.getRight_3()!=null?perfil.getRight_3():""%>'; // /resources/static/infoTit.html
    sourceForPanel[13] = '<%=perfil.getRight_4()!=null?perfil.getRight_4():""%>'; // /resources/static/infoTit.html
    sourceForPanel[14] = '<%=perfil.getRight_5()!=null?perfil.getRight_5():""%>'; // /resources/static/infoTit.html
    sourceForPanel[15] = '<%=perfil.getMain()!=null?perfil.getMain():""%>'; // /resources/static/infoTit.html
    sourceForPanel[16] = '<%=perfil.getNews()!=null?perfil.getNews():""%>'; // /resources/static/infoTit.html
    sourceForPanel[17] = '<%=perfil.getKnewthat()!=null?perfil.getKnewthat():""%>'; // /resources/static/infoTit.html
    sourceForPanel[18] = '<%=perfil.getMainDown()!=null?perfil.getMainDown():""%>';// /resources/static/infoTit.html
    sourceForPanel[19] = '<%=perfil.getOtherLeft()!=null?perfil.getOtherLeft():""%>'; // /resources/static/infoTit.html
    sourceForPanel[20] = '<%=perfil.getOtherRight()!=null?perfil.getOtherRight():""%>'; // /resources/static/infoTit.html
    sourceForPanel[21] = '<%=perfil.getOthers()!=null?perfil.getOthers():""%>'; // /resources/static/infoTit.html
    sourceForPanel[22] = '<%=perfil.getFooterLeft()!=null?perfil.getFooterLeft():""%>'; // /resources/static/infoTit.html
    sourceForPanel[23] = '<%=perfil.getFooterCenter()!=null?perfil.getFooterCenter():""%>'; // /resources/static/infoTit.html
    sourceForPanel[24] = '<%=perfil.getFooterRight()!=null?perfil.getFooterRight():""%>'; // /resources/static/infoTit.html
    
        
   

</script>
<link rel="shortcut icon" href="${ctx}/resources/images/favicon.ico" />

<!-- Links para extJs -->

<script type="text/javascript" src="${ctx}/resources/extjs/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="${ctx}/resources/extjs/ext-all.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/loadPortal.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/mecanismoAlerta/mecanismoAlerta.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/mecanismoAlerta/mecanismoAlertaPantalla.js"></script>

<jsp:include page="/jsp/menu/menu.jsp" flush="true" />
<jsp:include page="/jsp/alertas/alertaPopUp.jsp" flush="true"/>

</head>
<body id="page_bg" class="w-wide f-default header-light toolbar-magenta footer-magenta" onunload="cerrar()">
	<div id="showcase">
		<div class="wrapper">
			<div class="padding">
				<table class="showcase" cellspacing="0">
					<tr valign="top">
						<td class="showcase">
							<div id="top_left">
							</div>
						</td>
						<td class="showcase">
							<div id="top_center">
							</div>
						</td>
						<td class="showcase">
							<div id="top_right">                         
							</div>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<div id="mainbody">
		<div class="wrapper">
			<div id="mainbody-2">
				<div id="mainbody-3">
					<div id="mainbody-4">
						<div id="mainbody-5">
							<div id="mainbody-padding">
								<table class="mainbody" cellspacing="0">
									<tr valign="top">
										<td class="left" id="ContenedorLeft">
											<div>
												<div id="left_1" class="moduletable">
													<div id="nav-toolbar-vertical"><h3>Indice</h3></div>
													<s:if test="%{#session.containsKey('CONTENIDO_LEFT_1')}">
														<s:component template="leftOne.vm" templateDir="templates" theme="pages" >
															<s:param name="CONTENIDO_LEFT_1" value="%{#session['CONTENIDO_LEFT_1']}"/>
														</s:component>
													</s:if>
													<s:elseif test="%{#session.containsKey('CONTENIDO_LEFT_1_IMAGE') && #session.containsKey('CONTENIDO_LEFT_1_FILE')}" >
														<a href="<s:url value="%{#session['CONTENIDO_LEFT_1_FILE']}"/>"><img src="<s:property value="%{#session['CONTENIDO_LEFT_1_IMAGE']}"/>" width="130" height="150" /></a>
													</s:elseif>												
													<s:elseif test="%{#session.containsKey('CONTENIDO_LEFT_1_IMAGE')}">
														<img src="<s:property value="%{#session['CONTENIDO_LEFT_1_IMAGE']}"/>" width="130" height="150" />
													</s:elseif>												
                                            	</div>
                                            </div>
                                            <div>
                                            	<div id="left_2" class="moduletable">
                                            		<s:if test="%{#session.containsKey('CONTENIDO_LEFT_2')}">                                         
                                            			<s:component template="leftTwo.vm" templateDir="templates" theme="pages" >
															<s:param name="CONTENIDO_LEFT_2" value="%{#session['CONTENIDO_LEFT_2']}"/>
														</s:component>
													</s:if>
													<s:elseif test="%{#session.containsKey('CONTENIDO_LEFT_2_IMAGE') && #session.containsKey('CONTENIDO_LEFT_2_FILE')}" >
														<a href="<s:url value="%{#session['CONTENIDO_LEFT_2_FILE']}"/>"><img src="<s:property value="%{#session['CONTENIDO_LEFT_2_IMAGE']}"/>" width="130" height="150" /></a>
													</s:elseif>	
													<s:elseif test="%{#session.containsKey('CONTENIDO_LEFT_2_IMAGE')}">
														<img src="<s:property value="%{#session['CONTENIDO_LEFT_2_IMAGE']}"/>" width="130" height="150" />
													</s:elseif>
                                            	</div>
                                            </div>
                                            <div>
                                            	<div id="left_3" class="moduletable">
                                            		<s:if test="%{#session.containsKey('CONTENIDO_LEFT_3')}">
                                            			<s:component template="leftThree.vm" templateDir="templates" theme="pages" >
															<s:param name="CONTENIDO_LEFT_3" value="%{#session['CONTENIDO_LEFT_3']}"/>
														</s:component>
													</s:if>
													<s:elseif test="%{#session.containsKey('CONTENIDO_LEFT_3_IMAGE') && #session.containsKey('CONTENIDO_LEFT_3_FILE')}" >
														<a href="<s:url value="%{#session['CONTENIDO_LEFT_3_FILE']}"/>"><img src="<s:property value="%{#session['CONTENIDO_LEFT_3_IMAGE']}"/>" width="130" height="150" /></a>
													</s:elseif>	
													<s:elseif test="%{#session.containsKey('CONTENIDO_LEFT_3_IMAGE')}">
														<img src="<s:property value="%{#session['CONTENIDO_LEFT_3_IMAGE']}"/>" width="130" height="150" />
													</s:elseif>
                                            	</div>
                                            </div>
                                            <div>
                                            	<div id="left_4" class="moduletable">
                                            		<s:if test="%{#session.containsKey('CONTENIDO_LEFT_4')}">
                                            			<s:component template="leftFour.vm" templateDir="templates" theme="pages" >
															<s:param name="CONTENIDO_LEFT_4" value="%{#session['CONTENIDO_LEFT_4']}"/>
														</s:component>
													</s:if>
													<s:elseif test="%{#session.containsKey('CONTENIDO_LEFT_4_IMAGE') && #session.containsKey('CONTENIDO_LEFT_4_FILE')}" >
														<a href="<s:url value="%{#session['CONTENIDO_LEFT_4_FILE']}"/>"><img src="<s:property value="%{#session['CONTENIDO_LEFT_4_IMAGE']}"/>" width="130" height="150" /></a>
													</s:elseif>	
													<s:elseif test="%{#session.containsKey('CONTENIDO_LEFT_4_IMAGE')}">
														<img src="<s:property value="%{#session['CONTENIDO_LEFT_4_IMAGE']}"/>" width="130" height="150" />
													</s:elseif>
                                            	</div>
                                            </div>
                                            <div>
                                            	<div id="left_5" class="moduletable">
                                            		<s:if test="%{#session.containsKey('CONTENIDO_LEFT_5')}">
                                            			<s:component template="leftFive.vm" templateDir="templates" theme="pages" >
															<s:param name="CONTENIDO_LEFT_5" value="%{#session['CONTENIDO_LEFT_5']}"/>
														</s:component>
													</s:if>
													<s:elseif test="%{#session.containsKey('CONTENIDO_LEFT_5_IMAGE') && #session.containsKey('CONTENIDO_LEFT_5_FILE')}" >
														<a href="<s:url value="%{#session['CONTENIDO_LEFT_5_FILE']}"/>"><img src="<s:property value="%{#session['CONTENIDO_LEFT_5_IMAGE']}"/>" width="130" height="150" /></a>
													</s:elseif>	
													<s:elseif test="%{#session.containsKey('CONTENIDO_LEFT_5_IMAGE')}">
														<img src="<s:property value="%{#session['CONTENIDO_LEFT_5_IMAGE']}"/>" width="130" height="150" />
													</s:elseif>
                                            	</div>
                                            </div>
										</td>
										<td class="mainbody">
											<iframe id="bodyFrame" align="middle" name="bodyFrame" scrolling="no" frameborder="0" width="900" height="10" src="" onload="setSizeHeight()">
                                            </iframe>
										</td>
										<td class="right" id="ContenedorRight">
                                            <div>
                                            	<div id="right_1" class="moduletable">
                                            		<s:if test="%{#session.containsKey('CONTENIDO_RIGHT_1')}">
                                            			<s:component template="rightOne.vm" templateDir="templates" theme="pages" >
															<s:param name="CONTENIDO_RIGHT_1" value="%{#session['CONTENIDO_RIGHT_1']}"/>
														</s:component>
													</s:if>
													<s:elseif test="%{#session.containsKey('CONTENIDO_RIGHT_1_IMAGE') && #session.containsKey('CONTENIDO_RIGHT_1_FILE')}" >
														<a href="<s:url value="%{#session['CONTENIDO_RIGHT_1_FILE']}"/>"><img src="<s:property value="%{#session['CONTENIDO_RIGHT_1_IMAGE']}"/>" width="130" height="150" /></a>
													</s:elseif>
													<s:elseif test="%{#session.containsKey('CONTENIDO_RIGHT_1_IMAGE')}">
														<img src="<s:property value="%{#session['CONTENIDO_RIGHT_1_IMAGE']}"/>" width="130" height="150" />
													</s:elseif>
                                            	</div>                                                                                       
                                            </div>
                                            <div>
                                            	<div id="right_2" class="moduletable">
                                            		<s:if test="%{#session.containsKey('CONTENIDO_RIGHT_2')}">
                                            			<s:component template="rightTwo.vm" templateDir="templates" theme="pages" >
															<s:param name="CONTENIDO_RIGHT_2" value="%{#session['CONTENIDO_RIGHT_2']}"/>
														</s:component>
													</s:if>
													<s:elseif test="%{#session.containsKey('CONTENIDO_RIGHT_2_IMAGE') && #session.containsKey('CONTENIDO_RIGHT_2_FILE')}" >
														<a href="<s:url value="%{#session['CONTENIDO_RIGHT_2_FILE']}"/>"><img src="<s:property value="%{#session['CONTENIDO_RIGHT_2_IMAGE']}"/>" width="130" height="150" /></a>
													</s:elseif>
													<s:elseif test="%{#session.containsKey('CONTENIDO_RIGHT_2_IMAGE')}">
														<img src="<s:property value="%{#session['CONTENIDO_RIGHT_2_IMAGE']}"/>" width="130" height="150" />
													</s:elseif>
                                            	</div>
                                            </div>
                                            <div>
                                            	<div id="right_3" class="moduletable">
                                            		<s:if test="%{#session.containsKey('CONTENIDO_RIGHT_3')}">
                                            			<s:component template="rightThree.vm" templateDir="templates" theme="pages" >
															<s:param name="CONTENIDO_RIGHT_3" value="%{#session['CONTENIDO_RIGHT_3']}"/>
														</s:component>
													</s:if>
													<s:elseif test="%{#session.containsKey('CONTENIDO_RIGHT_3_IMAGE') && #session.containsKey('CONTENIDO_RIGHT_3_FILE')}" >
														<a href="<s:url value="%{#session['CONTENIDO_RIGHT_3_FILE']}"/>"><img src="<s:property value="%{#session['CONTENIDO_RIGHT_3_IMAGE']}"/>" width="130" height="150" /></a>
													</s:elseif>
													<s:elseif test="%{#session.containsKey('CONTENIDO_RIGHT_3_IMAGE')}">
														<img src="<s:property value="%{#session['CONTENIDO_RIGHT_3_IMAGE']}"/>" width="130" height="150" />
													</s:elseif>
                                            	</div>
                                            </div>
                                            <div>
                                            	<div id="right_4" class="moduletable">
                                            		<s:if test="%{#session.containsKey('CONTENIDO_RIGHT_4')}">
                                            			<s:component template="rightFour.vm" templateDir="templates" theme="pages" >
															<s:param name="CONTENIDO_RIGHT_4" value="%{#session['CONTENIDO_RIGHT_4']}"/>
														</s:component>
													</s:if>
													<s:elseif test="%{#session.containsKey('CONTENIDO_RIGHT_4_IMAGE') && #session.containsKey('CONTENIDO_RIGHT_4_FILE')}" >
														<a href="<s:url value="%{#session['CONTENIDO_RIGHT_4_FILE']}"/>"><img src="<s:property value="%{#session['CONTENIDO_RIGHT_4_IMAGE']}"/>" width="130" height="150" /></a>
													</s:elseif>
													<s:elseif test="%{#session.containsKey('CONTENIDO_RIGHT_4_IMAGE')}">
														<img src="<s:property value="%{#session['CONTENIDO_RIGHT_4_IMAGE']}"/>"width="130" height="150" />
													</s:elseif>
                                            	</div>
                                            </div>
                                            <div>
                                            	<div id="right_5" class="moduletable">
                                            		<s:if test="%{#session.containsKey('CONTENIDO_RIGHT_5')}">
                                            			<s:component template="rightFive.vm" templateDir="templates" theme="pages" >
															<s:param name="CONTENIDO_RIGHT_5" value="%{#session['CONTENIDO_RIGHT_5']}"/>
														</s:component>
													</s:if>
													<s:elseif test="%{#session.containsKey('CONTENIDO_RIGHT_5_IMAGE') && #session.containsKey('CONTENIDO_RIGHT_5_FILE')}" >
														<a href="<s:url value="%{#session['CONTENIDO_RIGHT_5_FILE']}"/>"><img src="<s:property value="%{#session['CONTENIDO_RIGHT_5_IMAGE']}"/>" width="130" height="150" /></a>
													</s:elseif>
													<s:elseif test="%{#session.containsKey('CONTENIDO_RIGHT_5_IMAGE')}">
														<img src="<s:property value="%{#session['CONTENIDO_RIGHT_5_IMAGE']}"/>" width="130" height="150" />
													</s:elseif>
                                            	</div>
                                            </div>
										</td>
                                    </tr>
								</table>
							</div>							
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script language="javascript"> 	
		LoadPage('/jsp/portal/mainBody.jsp');
	</script>


</body>

</html>