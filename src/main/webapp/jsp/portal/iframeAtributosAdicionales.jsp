<%@ include file="/taglibs.jsp"%>   
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<!-- Estilos para extJs iframesAtributosAdicionales.jsp -->
<link rel="stylesheet" type="text/css" href="${ctx}/resources/extjs/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/resources/extjs/resources/css/xtheme-gray.css" />

<link rel="shortcut icon" href="${ctx}/resources/images/favicon.ico" />
<!-- Links para extJs -->
<script type="text/javascript" src="${ctx}/resources/extjs/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="${ctx}/resources/extjs/ext-all.js"></script>
<!--script type="text/javascript" src="${ctx}/resources/extjs/ext-all-debug.js"></script-->
<link href="${ctx}/resources/css/template_css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script> 

<script language="javascript" type="text/javascript">
var helpMap = new Map();
var _CONTEXT = "${ctx}";
	var _ACTION_REGRESAR = "<s:url action='personas' namespace='/'/>";
	var _ACTION_COMBO_GENERICO = "<s:url action='comboListas' namespace='/combos'/>";
	var CODIGO_PERSONA = "<s:property value='codigoPersona'/>";
	var klsmd= 	function () {
						if (Ext.getCmp('el_formDatosAdicionales').getForm().items.items != null && Ext.getCmp('el_formDatosAdicionales').getForm().items.items != undefined) {
							Ext.each (Ext.getCmp('el_formDatosAdicionales').getForm().items.items, function(campito) {
								campito.setValue('');
								if (campito.xtype == "combo" || campito.xtype == "datefield") {
									campito.setRawValue('');
									campito.clearValue();
								}
							});
						}
						if (Ext.getCmp('el_formDatosAdicionales').items.items != undefined) {
							Ext.getCmp('el_formDatosAdicionales').items.each(
										function(item) {
											//el_formDatosAdicionales.remove(item, true);
											item.setValue('');
										}
							);
						}
	}
	
Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.QuickTips.enable();
	Ext.form.Field.prototype.msgTarget = "side";

	var agregarNuevaPersona = false;

	var _ACTION_COMBO_GENERICO = "<s:url action='obtenerComboGenerico' namespace='/combos'/>";
	var _ACTION_ESTADO_CIVIL = "<s:url action='comboEstadoCivil' namespace='/combos'/>";
 	var _ACTION_GUARDAR_DATOS_ADICIONALES = "<s:url action='guardarDatosAdicionales' namespace='/personas'/>";

	/**************Comienzo Datos Adicionales ***********************************************/
	var readerDatosGenericos = new Ext.data.JsonReader({
			root: 'comboGenerico',
			totalProperty: 'totalCount',
			successProperty: 'success'
			}, [
				{name: 'codigo', type: 'string', mapping: 'codigo'},
				{name: 'descripcion', type: 'string', mapping: 'descripcion'}
			]
	);

	function crearStoreDatosAdicionales2 (_idTablaLogica, _comboId, _value) {
		var dsDatosAdicionales;
		dsDatosAdicionales = new Ext.data.Store({
			proxy: new Ext.data.HttpProxy({
						url: _ACTION_COMBO_GENERICO
			}),
			reader: readerDatosGenericos
		});

		dsDatosAdicionales.load({
			params: {
				idTablaLogica: _idTablaLogica
			},
			callback: function(r, opt, success) {
				Ext.getCmp(_comboId).setValue(_value);
					if (Ext.getCmp(_comboId).allowBlank == "false") {
						Ext.getCmp(_comboId).allowBlank = false;
						//Ext.getCmp(_comboId).clearInvalid();
					}
					if (Ext.getCmp(_comboId).allowBlank == "true") {
						Ext.getCmp(_comboId).allowBlank = true;
					}
					if (_value == null || _value == undefined || _value == "") {
						if (Ext.getCmp(_comboId).allowBlank == false) {
							//alert(Ext.getCmp(_comboId).blankText);
							Ext.getCmp(_comboId).markInvalid(Ext.getCmp(_comboId).blankText);
							//formDatosAdicionales.getForm().markInvalid({_comboId: 'lslsls'});
						}
					}
				Ext.getCmp(_comboId).on('blur', function() {
					var combito = Ext.getCmp(_comboId);
					if (combito.allowBlank == "false") {
						combito.allowBlank = false;
						//combito.clearInvalid();
					}
					if (combito.allowBlank == "true") {
						combito.allowBlank = true;
					}
					if (combito.getRawValue() == "") {
						combito.clearValue();
						combito.setValue("");
						if (combito.allowBlank == false) {
							//combito.markInvalid({id: _comboId, msg: Ext.getCmp(_comboId).blankText});
							//formDatosAdicionales.getForm().markInvalid({_comboId: 'lslsls'});
						}
					}
				});
			}
		});
		return dsDatosAdicionales;
	}	
	var _height = 275;

	var el_formDatosAdicionales;
	        readerDatosAdicionales = new Ext.data.JsonReader( {
	            root : 'comboEstadoCivil',
	            totalProperty: 'totalCount',
	            successProperty : '@success'
	
	        }, [ {
	            name : 'id',
	            mapping : 'id',
	            type : 'string'
	        }, {
	            name : 'texto',
	            type : 'string',
	            mapping : 'texto'
	        }]);
			var dsDatosAdicionales = new Ext.data.Store({
	            proxy: new Ext.data.HttpProxy({
	                url: _ACTION_ESTADO_CIVIL
	            }),
	            reader: readerDatosAdicionales
	        });		

	        el_formDatosAdicionales = new Ext.FormPanel ({
	        	id: 'el_formDatosAdicionales',
	        	title: '',
				renderTo: 'atributosVariables',
				labelAlign:'right',
	            frame : true,
	            width : 695,
	            height: _height,
	            //autoWidth: true,
	            bodyStyle: {background: 'white', position: 'relative'},
	            //autoHeight: true,
	            //waitMsgTarget : true,
	            successProperty: 'success',
	            //bodyStyle:'background: white',
	            labelWidth: 250,
	            autoScroll: true,
	            defaults: {width: 100},
				//deferredRender: false,
	            items: <%
	            			if (session.getAttribute("modelControl") != null) {%>
	            				<%=session.getAttribute("modelControl")%>
	            			<%}else {%>
	            				[{xtype: 'hidden'}]
	            			<%}
	            		%>,
	            		buttonAlign: 'center',
	            		buttons: [
	            					{
	            					text:getLabelFromMap('abmPersonasDtAdcButtonGuardar', helpMap,'Guardar'),
           							tooltip:getToolTipFromMap('abmPersonasDtAdcButtonGuardar', helpMap,'Guardar'),
	            					//text: 'Guardar',
	            					handler: function () {
	            					
	            					 agregarNuevaPersona = false;
	            					 if (el_formDatosAdicionales.form.isValid()) {
	            					 	guardarDatosAdicionales();
	            					 }else {
				                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
	            					 }
	            					 }
	            					},{
	            					text:getLabelFromMap('abmPersonasDtAdcButtonGuardarAgregar', helpMap,'Guardar y Agregar'),
           							tooltip:getToolTipFromMap('abmPersonasDtAdcButtonGuardarAgregar', helpMap,'Guardar y Agregar'),	
	            					//text: 'Guardar y Agregar', 
	            					handler: function () {
	            											agregarNuevaPersona = true; 
							            					if (el_formDatosAdicionales.form.isValid()) {
	            												guardarDatosAdicionales();
							            					}else {
										                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
							            					}
	            								}
	            					},{
      								text:getLabelFromMap('abmPersonasDtAdcButtonRegresar', helpMap,'Regresar'),
           							tooltip:getToolTipFromMap('abmPersonasDtAdcButtonRegresar', helpMap,'Regresar'),	            					
	            					//text: 'Regresar', 
	            					handler: function() {
	            								window.parent.location.href = _ACTION_REGRESAR;
	            								}
	            					}
	            				],
	            		listeners: {
	            				show: function (obj) {
	            					//console.log(this);
	            					Ext.each(this.getForm().items.items, function (campito) {
	            						if (campito.allowBlank == "false") {
	            							campito.allowBlank = false;
	            						}else {
	            							campito.allowBlank = true;
	            						}
	            					});
	            					return true;
	            				}
	            		}
			});


	function guardarDatosAdicionales () {
		var contCampos = 0;
		var _params = "datosAdicionales[0].pi_cdperson=" + CODIGO_PERSONA;
		//window.parent.mostrarOcultarMascara(true);

		//window.parent.mostrarOcultarMascara(false);
		Ext.each(el_formDatosAdicionales.getForm().items.items, function(campito){
					if (campito.name && campito.name != "") {
						var idx = campito.name.indexOf(".");
						if (idx > 0) {
							contCampos++;
							var valor = campito.name.substring(idx + 1);
							if (valor.length == 1) valor = "0" + valor;
							_params += "&datosAdicionales[0].pi_otvalor" + valor + "=" + campito.getValue();
						}
					}else {
						_params += "&datosAdicionales[0]." + campito.name + "=" + campito.getValue();
					}
		});
		if (_params.length > 0 && contCampos > 0) {
			//startMask('ttabs', 'Espere...');
			my_Mask = new Ext.LoadMask(el_formDatosAdicionales.id, {msg: 'Espere ...', disabled: false});
			my_Mask.show();
			execConnection(_ACTION_GUARDAR_DATOS_ADICIONALES, _params, cbkGuardarDatosAdicionales);
		}
	}
	function cbkGuardarDatosAdicionales (_success, _message) {
		//endMask();
		if (my_Mask) {
			my_Mask.hide();
			my_Mask = null;
		}
		if (!_success) {
			Ext.Msg.alert('Error', _message, function() {window.parent.resetPersona2 ();});
		}else {
			Ext.Msg.alert('Aviso', _message, function() {
					if (agregarNuevaPersona) {
						limpiarCamposForm();
					    window.parent.resetPersona2 ();
						//el_formDatosAdicionales.destroy();
					}else {
						window.location.reload(true);
					}
			});
		}
	}

	el_formDatosAdicionales.render();
	el_formDatosAdicionales.body.dom.style.height = 219;
	el_formDatosAdicionales.doLayout(true);
	//el_formDatosAdicionales.syncSize()
	Ext.each (Ext.getCmp('el_formDatosAdicionales').getForm().items.items, function(campito) {
			if (campito.xtype != "hidden" && campito.errorIcon) {
				campito.alignErrorIcon(200);
				//campito.focus();
				//alert(2);
			}
	});
	//el_formDatosAdicionales.body.dom.style.width = 680;
	//console.log(el_formDatosAdicionales);
	/**************Fin Datos Adicionales ***********************************************/
	function limpiarCamposForm () {
						if (Ext.getCmp('el_formDatosAdicionales').getForm().items.items != null && Ext.getCmp('el_formDatosAdicionales').getForm().items.items != undefined) {
							Ext.each (Ext.getCmp('el_formDatosAdicionales').getForm().items.items, function(campito) {
								campito.setValue('');
								if (campito.xtype == "combo" || campito.xtype == "datefield") {
									campito.setRawValue('');
									campito.clearValue();
								}
							});
						}
						if (Ext.getCmp('el_formDatosAdicionales').items.items != undefined) {
							Ext.getCmp('el_formDatosAdicionales').items.each(
										function(item) {
											//el_formDatosAdicionales.remove(item, true);
											item.setValue('');
										}
							);
						}
	}
});
</script>
</head>	
<body><div id="atributosVariables"></div></body>