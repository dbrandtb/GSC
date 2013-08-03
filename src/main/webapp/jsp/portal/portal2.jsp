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

<!-- Estilos para extJs -->

<link rel="stylesheet" type="text/css" href="${ctx}/resources/extjs/${ctx}/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/resources/extjs/${ctx}/resources/css/xtheme-gray.css" />


<script language="javascript">
	var _CONTEXT = "${ctx}";
	
	// Se inicializan las fuentes de la pantalla para la carga
    var sourceForPanel = new Array();

    // Se asignan las fuentes iniciales
    <%  PerfilVO perfil = userVO.getFuentesPerfil();  %>
    
    sourceForPanel[0]  = '<%=perfil.getTop()!=null?perfil.getTop():""%>';
    sourceForPanel[1]  = '<%=perfil.getNav()!=null?perfil.getNav():""%>';
    sourceForPanel[2]  = '<%=perfil.getTopLeft()!=null?perfil.getTopLeft():""%>';
    sourceForPanel[3]  = '<%=perfil.getTopCenter()!=null?perfil.getTopCenter():""%>';
    sourceForPanel[4]  = '<%=perfil.getTopRight()!=null?perfil.getTopRight():""%>';
    sourceForPanel[5]  = '<%=perfil.getLeft_1()!=null?perfil.getLeft_1():""%>';
    sourceForPanel[6]  = '<%=perfil.getLeft_2()!=null?perfil.getLeft_2():""%>';
    sourceForPanel[7]  = '<%=perfil.getLeft_3()!=null?perfil.getLeft_3():""%>';
    sourceForPanel[8]  = '<%=perfil.getLeft_4()!=null?perfil.getLeft_4():""%>';
    sourceForPanel[9]  = '<%=perfil.getLeft_5()!=null?perfil.getLeft_5():""%>';
    sourceForPanel[10] = '<%=perfil.getRight_1()!=null?perfil.getRight_1():""%>';
    sourceForPanel[11] = '<%=perfil.getRight_2()!=null?perfil.getRight_2():""%>';
    sourceForPanel[12] = '<%=perfil.getRight_3()!=null?perfil.getRight_3():""%>';
    sourceForPanel[13] = '<%=perfil.getRight_4()!=null?perfil.getRight_4():""%>';
    sourceForPanel[14] = '<%=perfil.getRight_5()!=null?perfil.getRight_5():""%>';
    sourceForPanel[15] = '<%=perfil.getMain()!=null?perfil.getMain():""%>';
    sourceForPanel[16] = '<%=perfil.getNews()!=null?perfil.getNews():""%>';
    sourceForPanel[17] = '<%=perfil.getKnewthat()!=null?perfil.getKnewthat():""%>';
    sourceForPanel[18] = '<%=perfil.getMainDown()!=null?perfil.getMainDown():""%>';
    sourceForPanel[19] = '<%=perfil.getOtherLeft()!=null?perfil.getOtherLeft():""%>';
    sourceForPanel[20] = '<%=perfil.getOtherRight()!=null?perfil.getOtherRight():""%>';
    sourceForPanel[21] = '<%=perfil.getOthers()!=null?perfil.getOthers():""%>';
    sourceForPanel[22] = '<%=perfil.getFooterLeft()!=null?perfil.getFooterLeft():""%>';
    sourceForPanel[23] = '<%=perfil.getFooterCenter()!=null?perfil.getFooterCenter():""%>';
    sourceForPanel[24] = '<%=perfil.getFooterRight()!=null?perfil.getFooterRight():""%>';

</script>
<link rel="shortcut icon" href="${ctx}/resources/images/favicon.ico" />

<!-- Links para extJs -->

<script type="text/javascript" src="${ctx}/resources/extjs/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="${ctx}/resources/extjs/ext-all.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/portal/loadPortal.js"></script>
<jsp:include page="/jsp/menu/menu.jsp" flush="true" />

<decorator:head/>

</head>
<body id="page_bg" class="w-wide f-default header-light toolbar-magenta footer-magenta">		
	<div id="showcase">
		<div class="wrapper">
			<div class="padding">
				<table class="showcase" cellspacing="0">
					<tr valign="top">
						<td class="showcase">
                            <div id="top_left">
                            <s:if test="%{#session.containsKey('CONTENIDO_TOPLEFT')}">
                            	<s:component template="topLeft.vm" templateDir="templates" theme="pages" >
									<s:param name="CONTENIDO_TOPLEFT" value="%{#session['CONTENIDO_TOPLEFT']}"/>
								</s:component>
							</s:if><s:else>
							<div id="top_left" style="display: none;"></div>
							</s:else>
                            </div>
						</td>
						<td class="showcase">
                            <div id="top_center">
                            	<s:if test="%{#session.containsKey('CONTENIDO_TOPCENTER')}">
                            		<s:component template="topCenter.vm" templateDir="templates" theme="pages" >
										<s:param name="CONTENIDO_TOPCENTER" value="%{#session['CONTENIDO_TOPCENTER']}"/>
									</s:component>
								</s:if>
                            </div>
						</td>
						<td class="showcase">
                            <div id="top_right">
                            	<s:if test="%{#session.containsKey('CONTENIDO_TOPRIGHT')}">
                            		<s:component template="topRight.vm" templateDir="templates" theme="pages" >
										<s:param name="CONTENIDO_TOPRIGHT" value="%{#session['CONTENIDO_TOPRIGHT']}"/>
									</s:component>
								</s:if>
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
										<td class="mainbody">
											<table class="headlines" cellspacing="10">
												<tr valign="top">
													<td class="headlines" colspan="2">														
                                                    	<iframe src='/AON/confalfa/portalConfigurador.action' width="98%" height="500" scrolling="yes" frameborder="0">
                                                        </iframe>
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
                                                        <s:else>
                                                        	<div id="news" style="display: none;"></div>
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
                                                        <s:else>
                                                        	<div id="knewthat" style="display: none;"></div>
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
														<s:else>
															<div id="mainDown" style="display: none;"></div>
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
														<s:else>
															<div id="otherLeft" style="display: none;"></div>
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
														<s:else>
															<div id="otherRight" style="display: none;"></div>
														</s:else>
													</td>
												</tr>
											</table>
											<div>
												<div class="padding" id="others">
													<s:if test="%{#session.containsKey('CONTENIDO_OTHERS')}">
														<s:component template="others.vm" templateDir="templates" theme="pages" >
															<s:param name="CONTENIDO_OTHERS" value="%{#session['CONTENIDO_OTHERS']}"/>
														</s:component>	
													</s:if>
                                            	</div>
                                            </div>
										</td>										
								</table>
							</div>							
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="footer">
		<div class="wrapper">
			<div class="padding">
				<table class="footer" cellspacing="0">
					<tr valign="top"> 
						<td class="footer">
                        	<div class="moduletable" id="footer_left">
                        		<s:if test="%{#session.containsKey('CONTENIDO_FOOTERLEFT')}">
                        			<s:component template="footerLeft.vm" templateDir="templates" theme="pages" >
										<s:param name="CONTENIDO_FOOTERLEFT" value="%{#session['CONTENIDO_FOOTERLEFT']}"/>
									</s:component>
								</s:if>
                        	</div>
						</td>
						<td class="footer">
                        	<div class="moduletable" id="footer_center">
                        		<s:if test="%{#session.containsKey('CONTENIDO_FOOTERCENTER')}">
                        			<s:component template="footerCenter.vm" templateDir="templates" theme="pages" >
										<s:param name="CONTENIDO_FOOTERCENTER" value="%{#session['CONTENIDO_FOOTERCENTER']}"/>
									</s:component> 
								</s:if>
                        	</div>
						</td>
						<td class="footer">
                        	<div class="moduletable" id="footer_right">
                        		<s:if test="%{#session.containsKey('CONTENIDO_FOOTERRIGHT')}">
                        			<s:component template="footerRight.vm" templateDir="templates" theme="pages" >
										<s:param name="CONTENIDO_FOOTERRIGHT" value="%{#session['CONTENIDO_FOOTERRIGHT']}"/>
									</s:component>
								</s:if>
                        	</div>
						</td>
                    </tr>
				</table>
				<!-- Se agregaron temporalmente los siguientes divs para eliminar el error en Firefox: -->
				<div id="left_1" style="display: none;"></div>
				<div id="left_2" style="display: none;"></div>
				<div id="left_3" style="display: none;"></div>
				<div id="left_4" style="display: none;"></div>
				<div id="left_5" style="display: none;"></div>
				<div id="right_1" style="display: none;"></div>
				<div id="right_2" style="display: none;"></div>
				<div id="right_3" style="display: none;"></div>
				<div id="right_4" style="display: none;"></div>
				<div id="right_5" style="display: none;"></div>
				<div id="main" style="display: none;"></div>
				
				<div id="the-footer">
                    Copyright &#169; 2008
				</div>
			</div>
		</div>
	</div>
</body>
</html>

