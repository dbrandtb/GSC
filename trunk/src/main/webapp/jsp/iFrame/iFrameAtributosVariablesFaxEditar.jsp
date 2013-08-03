<%@ include file="/taglibs.jsp"%>   
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<!-- Estilos para extJs -->
<link rel="stylesheet" type="text/css" href="${ctx}/resources/extjs/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/resources/extjs/resources/css/xtheme-gray.css" />

<link rel="shortcut icon" href="${ctx}/resources/images/favicon.ico" />
<!-- Links para extJs -->
<script type="text/javascript" src="${ctx}/resources/extjs/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="${ctx}/resources/extjs/ext-all.js"></script>
<!--script type="text/javascript" src="${ctx}/resources/extjs/ext-all-debug.js"></script-->
<link href="${ctx}/resources/css/template_css.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>

<script language="javascript">

var _CONTEXT = "${ctx}";
var _ACTION_COMBO_GENERICO = "<s:url action='comboListas' namespace='/combos-catbo'/>";

Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.QuickTips.enable();
	Ext.form.Field.prototype.msgTarget = "side";
	
	var readerComboAtributosVariables = new Ext.data.JsonReader({
			root: 'comboGetListas',
			totalProperty: 'totalCount',
			successProperty: 'success'
			}, [
				{name: 'cd', type: 'string', mapping:'cd'},
				{name: 'ds', type: 'string', mapping:'ds'}
			]
	);

	function crearStoreComboAtributosVariables(_idTablaLogica, _comboId, _value) {
		var storeComboAtributosVariables;
		storeComboAtributosVariables = new Ext.data.Store({
			proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_GENERICO}),
			reader: readerComboAtributosVariables
		});

		storeComboAtributosVariables.load({
			params: {cdTabla: _idTablaLogica, valAnter: 0, valAntAnt: 0},
			callback: function(r, opt, success){
						if (_value != null) {
							Ext.getCmp(_comboId).setValue(_value);
						}
					}
		});
		return storeComboAtributosVariables;
	}
	
		
	var dinamicFormPanel = new Ext.FormPanel({
				renderTo:'atributosVariablesFaxEditar',
		        id: 'dinamicFormPanelId',
		        iconCls:'logo',
		        bodyStyle:'background: white',
		        buttonAlign: "center",
		        labelAlign: 'right',
		        frame:true,
		        header:false,
		        autoHeight:true,
		        headerAsText: false,
		        layout: 'table',
		        autoWidth: true,
	            layoutConfig: {columns: 1, align: 'center'},            
	            items://items principal que encierra todo
	            	[{
	            		layout: 'form',
	            		border: false,
	            		autoHeight: true,
	            		//layoutConfig: {columns: 2, align: 'rigth'},
	            		items: [{//items del layout
	            				border:false,
	            				baseCls:'',
	            				layoutConfig: {columns: 2, align: 'left'},
	            				layout:'column',
	            				defaults:{labelWidth:250},
	            				items:[// items de campos
			            				{
										 //columnWidth:1,
										  colspan:2,
		                				 layout: 'form',
		                				 align:'rigth',
		                				 width:550,
		                				items:
		                				[{ 
		                				 html:'&nbsp;'
		                				}]
		                				},
		                				{
										 //columnWidth:1,
										  colspan:2,
		                				 layout: 'form',
		                				 align:'rigth',
		                				 width:550,
		                				items:
		                				[{ 
		                				 html:'&nbsp;'
		                				}]
		                				},
		                				 {
		                				 //columnWidth:.50,
		                				 colspan:2,
		                				 layout: 'form',
		                				 align:'rigth',
		                				 width:550,
		                				 items:
			            			     	<s:component template="camposPantallaDatosExt.vm" templateDir="templates" theme="components" ><s:param name="dext" value="modelControl" /></s:component>
						            	},
						            	{
										 //columnWidth:1,
										  colspan:2,
		                				 layout: 'form',
		                				 align:'rigth',
		                				 width:550,
		                				items:
		                				[{ 
		                				 html:'&nbsp;'
		                				}]
		                				},
		                				{
										 //columnWidth:1,
										  colspan:2,
		                				 layout: 'form',
		                				 align:'rigth',
		                				 width:550,
		                				items:
		                				[{ 
		                				 html:'&nbsp;'
		                				}]
		                				}
		                		]
	            		    }]
		            }]
		        
		});
		dinamicFormPanel.render();
		var altura = dinamicFormPanel.body.dom.clientHeight;//window.parent.document.getElementById('atributosVariables').contentWindow.document.body.height;
		window.parent.document.getElementById('atributosVariablesFaxEditar').height = eval(altura)+35;
		window.parent.parent.setSizeHeight(altura);
		window.parent.finMask();
		
	/*	 Ext.each(dinamicFormPanel.form.items.items, function (campito) {
      if (campito.xtype == "numberfield") {
       campito.setRawValue('');
       campito.clearInvalid();
      }
     });*/
		
		
	 Ext.each(dinamicFormPanel.form.items.items, function (campito) {
	      if (campito.xtype == "numberfield") {
	      			 if (!isNaN(campito.value)) {
					 	 campito.getValue();               
					  } else {
					  	 campito.setRawValue('');
					  	 campito.clearInvalid();
					  }
	     	 }
     });
		
		window.parent.habilitarBotonGuarda();
		
});
</script>
</head>	
<body><div id="atributosVariablesFaxEditar"></div></body>