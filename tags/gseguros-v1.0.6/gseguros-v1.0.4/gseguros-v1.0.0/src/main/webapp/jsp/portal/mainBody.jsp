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

<!-- gabriel F Estilos para extJs mainBody.jsp-->

<link rel="stylesheet" type="text/css" href="${ctx}/resources/extjs/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/resources/extjs/resources/css/xtheme-gray.css" />


<!-- Estilo para algunos elementos de las secciones -->
<link href="${ctx}/resources/css/secciones.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/resources/css/ie.css" rel="stylesheet" type="text/css" />

<style type="text/css">
  strong {font-weight: bold;}
  em {font-style: italic;}
</style>

<script language="javascript">

// función para mostrar la pantalla de los link

 function openWind ( URL ){ 
        window.open(URL,'',"width=800,height=600,Scrollbars=YES,Resizable=YES"); 
 }
</script>


</head>
<body>
	<table class="headlines" cellspacing="10">
			<tr valign="top">
				<td class="headlines" colspan="2">														
					<s:if test="%{#session.containsKey('CONTENIDO_MAIN')}">
						<div id="main">
							<s:component template="main.vm" templateDir="templates" theme="pages" >
								<s:param name="CONTENIDO_MAIN" value="%{#session['CONTENIDO_MAIN']}"/>
							</s:component>
						</div>
					</s:if>
					<s:elseif test="%{#session.containsKey('CONTENIDO_MAIN_IMAGE')}">
						<div id="main" >
							<img src="<s:property value="%{#session['CONTENIDO_MAIN_IMAGE']}"/>" width="840" height="330" />
						</div>
					</s:elseif>
					<s:else>
						<div id="main" style="display:none;"></div>
					</s:else>														
				</td>
			</tr>                                                                                                
			<tr>
				<td class="headlines">																												
					<s:if test="%{#session.containsKey('CONTENIDO_NEWS')}">
						<div id="news">
							<s:component template="news.vm" templateDir="templates" theme="pages" >
								<s:param name="CONTENIDO_NEWS" value="%{#session['CONTENIDO_NEWS']}"/>
							</s:component>
						</div>                                                        	
					</s:if>
					<s:elseif test="%{#session.containsKey('CONTENIDO_NEWS_IMAGE') && #session.containsKey('CONTENIDO_NEWS_FILE')}" >
						<div id="news">
							<a href="<s:url value="%{#session['CONTENIDO_NEWS_FILE']}"/>"><img src="<s:property  value="%{#session['CONTENIDO_NEWS_IMAGE']}"/>" width="130" height="150" /></a>
						</div>
					</s:elseif>	
					<s:elseif test="%{#session.containsKey('CONTENIDO_NEWS_IMAGE')}">
						<div id="news">
							<img src="<s:property value="%{#session['CONTENIDO_NEWS_IMAGE']}"/>" width="420" height="125" />
						</div>
					</s:elseif>
					<s:else>
						<div id="news" style="display:none;"></div>
					</s:else>                                                    	
				</td>
				<td class="headlines">																										
					<s:if test="%{#session.containsKey('CONTENIDO_KNEWTHAT')}">
						<div id="knewthat">
							<s:component template="knewThat.vm" templateDir="templates" theme="pages" >
								<s:param name="CONTENIDO_KNEWTHAT" value="%{#session['CONTENIDO_KNEWTHAT']}"/>
							</s:component>
						</div>                                                        	                                               
					</s:if>
					<s:elseif test="%{#session.containsKey('CONTENIDO_KNEWTHAT_IMAGE') && #session.containsKey('CONTENIDO_KNEWTHAT_FILE')}" >
						<div id="knewthat">
							<a href="<s:url value="%{#session['CONTENIDO_KNEWTHAT_FILE']}"/>"><img src="<s:property value="%{#session['CONTENIDO_KNEWTHAT_IMAGE']}"/>" width="130" height="150" /></a>
						</div>
					</s:elseif>	
					<s:elseif test="%{#session.containsKey('CONTENIDO_KNEWTHAT_IMAGE')}">
						<div id="knewthat">
							<img src="<s:property value="%{#session['CONTENIDO_KNEWTHAT_IMAGE']}"/>" width="420" height="125" />
						</div>
					</s:elseif>	
					<s:else>
						<div id="knewthat" style="display:none;"></div>
					</s:else>                                                        
				</td>
			</tr>
			<tr valign="top">
				<td class="headlines" colspan="2">														
					<s:if test="%{#session.containsKey('CONTENIDO_MAINDOWN')}">
						<div id="mainDown">                                                        	
							<s:component template="mainDown.vm" templateDir="templates" theme="pages" >
								<s:param name="CONTENIDO_MAINDOWN" value="%{#session['CONTENIDO_MAINDOWN']}"/>
							</s:component>
						</div>                                                        	
					</s:if>
					<s:elseif test="%{#session.containsKey('CONTENIDO_MAINDOWN_IMAGE')}">
						<div id="mainDown">
							<img src="<s:property value="%{#session['CONTENIDO_MAINDOWN_IMAGE']}"/>" width="840" height="125" />
						</div>
					</s:elseif>	
					<s:else>
							<div id="mainDown" style="display:none;"></div>
					</s:else>													
				</td>
			</tr>
			<tr>
				<td class="headlines">														
					<s:if test="%{#session.containsKey('CONTENIDO_OTHERLEFT')}">
						<div id="otherLeft">                                                        	
							<s:component template="otherLeft.vm" templateDir="templates" theme="pages" >
								<s:param name="CONTENIDO_OTHERLEFT" value="%{#session['CONTENIDO_OTHERLEFT']}"/>
							</s:component>
						</div>                                                        	
					</s:if>
					<s:elseif test="%{#session.containsKey('CONTENIDO_OTHERLEFT_IMAGE') && #session.containsKey('CONTENIDO_OTHERLEFT_FILE')}" >
						<div id="otherLeft">
							<a href="<s:url value="%{#session['CONTENIDO_OTHERLEFT_FILE']}"/>"><img src="<s:property value="%{#session['CONTENIDO_OTHERLEFT_IMAGE']}"/>" width="130" height="150" /></a>
						</div>
					</s:elseif>	
					<s:elseif test="%{#session.containsKey('CONTENIDO_OTHERLEFT_IMAGE')}">
						<div id="otherLeft">
							<img src="<s:property value="%{#session['CONTENIDO_OTHERLEFT_IMAGE']}"/>" width="420" height="125" />
						</div>
					</s:elseif>	
					<s:else>
						<div id="otherLeft" style="display:none;"></div>
					</s:else>														
				</td>
				<td class="headlines">														
					<s:if test="%{#session.containsKey('CONTENIDO_OTHERRIGHT')}">
						<div id="otherRight">
							<s:component template="otherRight.vm" templateDir="templates" theme="pages" >
								<s:param name="CONTENIDO_OTHERRIGHT" value="%{#session['CONTENIDO_OTHERRIGHT']}"/>
							</s:component>
						</div>                                                        	
					</s:if>
					<s:elseif test="%{#session.containsKey('CONTENIDO_OTHERRIGHT_IMAGE') && #session.containsKey('CONTENIDO_OTHERRIGHT_FILE')}" >
						<div id="otherRight">
							<a href="<s:url value="%{#session['CONTENIDO_OTHERRIGHT_FILE']}"/>"><img src="<s:property value="%{#session['CONTENIDO_OTHERRIGHT_IMAGE']}"/>" width="130" height="150" /></a>
						</div>
					</s:elseif>	
					<s:elseif test="%{#session.containsKey('CONTENIDO_OTHERRIGHT_IMAGE')}">
						<div id="otherRight">
							<img src="<s:property  value="%{#session['CONTENIDO_OTHERRIGHT_IMAGE']}"/>" width="420" height="125" />
						</div>
					</s:elseif>
					<s:else>
						<div id="otherRight" style="display:none;"></div>
					</s:else>														
				</td>
			</tr>
		</table>
		<div>
			<div id="others" class="padding">
				<s:if test="%{#session.containsKey('CONTENIDO_OTHERS')}">
					<s:component template="others.vm" templateDir="templates" theme="pages" >
						<s:param name="CONTENIDO_OTHERS" value="%{#session['CONTENIDO_OTHERS']}"/>
					</s:component>	
				</s:if>
				<s:elseif test="%{#session.containsKey('CONTENIDO_OTHERS_IMAGE')}">
					<img src="<s:property  value="%{#session['CONTENIDO_OTHERS_IMAGE']}"/>" width="840" height="125" />
				</s:elseif>
         	</div>
	</div>
</body>
</html>