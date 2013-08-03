<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<style type="text/css">    
        #button-grid .x-panel-body {border:1px solid #99bbe8; border-top:0 none;}.logo{background-image:url(../resources/images/aon/bullet_titulo.gif) !important;}        
    </style>
<title>Seleccione Rol y Cliente </title>
<meta http-equiv="Expires" content="Tue, 20 Aug 2010 14:25:27 GMT">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

</head>
<body>
	<table class="headlines" cellspacing="10" align="center">
		<tr valign="top" >
			<td class="headlines" colspan="2" >
				<table align="center">
					<tr>
						<td>
							<div id="" >
								<div >            		     		
									<div id="lugar" ></div>
								</div>
							</div>
						</td>
					</tr>	
				</table>				
			</td>
		</tr>
</table>
<script type="text/javascript" src="${ctx}/resources/extjs/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="${ctx}/resources/extjs/ext-all.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript">
		var _CONTEXT = "${ctx}";
		var _ACTION_ARBOL = "<s:url action='ArbolRolCliente'/>";
		var _ACTION_PORTAL = "<s:url action='load'/>";
		var _ACTION_REGRESA= "<s:url action='regresaCodigo' />";
		var _ACTION_VALIDAR_CONFIGURACION_COMPLETA = "<s:url action='validaConfiguracionCompleta' namespace='/principal'/>";
		var _ACTION_LOGIN = "<s:property value='#session.URL_INICIO' />";
</script>
<script type="text/javascript">
Ext.onReady(function(){
	/*
	*	Si viene en sesión MessageConf != null, es porque tiene un único rol y no está completa la configuración para esa cuenta.
	*	Ver issue ACW-693
	*/	
	<%
		if (session.getAttribute("MessageConf") != null) {
	%>
		Ext.Msg.alert('Error', '<%=session.getAttribute("MessageConf")%>', function () {
			//window.location.href = _ACTION_LOGIN;
		                   		window.location.replace(_CONTEXT);
		});	
	<%
		} else {%>
			/**
			*	Si tiene más de un rol, entonces cada vez que se selecciona uno, se chequea 
			*	que esté completa la configuración para esa cuenta.
			*	Ver issue ACW-693
			*/
			var treeLoader= new Ext.tree.TreeLoader({
					clearOnLoad:true,
					preloadChildren:true,
					dataUrl:_ACTION_ARBOL,
						listeners: {
						load: function () {
						}
					}
			});			
			var idNodo = -1;
			var arbolProductos= new  Ext.tree.TreePanel({
		        	id:'arbol-clientes-roles',        	                  
		            title: '<span style="color:black;font-weight:bold;font-size:14px;">Seleccione Rol y Cliente</span>',
		            xtype: 'treepanel',
		            region:'center', 
		            minSize: 175,
		            maxSize: 400,
		            margins: '0 0 0 0',
		            width: 300,
		            autoScroll: true,
		            split: true,
		            rootVisible: false,
		            lines: true,
		            loader: new Ext.tree.TreeLoader({
						dataUrl:_ACTION_ARBOL,
						listeners: {
							load: function (_this, _node, _response) {
								var tree_items = Ext.util.JSON.decode(_response.responseText);
							}
						}
					}), 
		            root:  new Ext.tree.AsyncTreeNode({
		            	text: 'Ext JS', 
		                draggable:false,
		                id:'source'
		            }),
		            rootVisible: false,
		            listeners: {
		            	click: function(n){
		            	if (n.leaf != false) {
			            	idNodo = n.attributes.codigoObjeto;
			            	var params = "codigoRol = "+idNodo;//var params = "codigoCliente = "+idNodo;
			            	var path2= n.getPath("codigoObjeto");            	                               
			                var codigosPath2=new Array();
			                codigosPath2= path2.split("/");
			       			params +="&& codigoCliente =" + codigosPath2[2];//params +="&& codigoRol =" + codigosPath2[2];
			       		    var params2 = {
			       		    	    codigoRol : idNodo,
			       		    	    codigoCliente : codigosPath2[2]
			       		    };
			       			startMask (arbolProductos.id, 'Espere...');
			            	var conn = new Ext.data.Connection();
			                	conn.request ({
			                		url:_ACTION_REGRESA,
			                		method: 'POST',
			                		successProperty : '@success',
			                		params : params2,
			                		callback: function (options, success, response) {  
			                			endMask();
			                			if(Ext.util.JSON.decode(response.responseText).codigoValido==true){
			                			
											window.location.replace(_ACTION_PORTAL);   
										}else {
											Ext.Msg.alert('Error', Ext.util.JSON.decode(response.responseText).actionErrors[0]);
										}
									}
			                	});
			               }
		            	}		            
		            },
		             tbar:[{
		                    text:'<span style="font-weight:bold;font-size:13px;text-decoration:underline;">Salir</span>',
		                   tooltip:'Salir',                   
		                   handler:function (){
		                   		window.location.replace(_ACTION_LOGIN);
		                    }
					}]		           
				});		
		    	if (arbolProductos.root.childNodes.length <= 1) {
					arbolProductos.render('lugar');
				}
				//console.log(arbolProductos);
				treeLoader.on ('load', function (_this, _node, _response) {
					/*console.log("_this: "); console.log(_this);
					console.log("_node: "); console.log(_node);
					console.log("_response: "); console.log(_response);*/
				});							
	<%}%>
});
</script>    
</body>
</html>