Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.QuickTips.enable();
	
	Ext.form.Field.prototype.msgTarget = "side";
	
	var panelDatosGrales = new Ext.FormPanel ({
			renderTo: 'datosGrales',
            title : '<center>' + getLabelFromMap('#',helpMap,'Datos Generales') + '</center>',
            frame : true,
            width : 600,
            height: 350,
            //autoHeight: true,
            successProperty: 'success',
            layout: 'table',
            layoutConfig: { columns: 2, columnWidth: .5},
            labelWidth: 100,
            store: storeGetDtosGrles,
            reader: readerGetDtosGrles,
            url: _ACTION_GET_ENCABEZADO_POLIZA,
            items: [
            		{
           			layout: 'form',
					style: 'padding: 5px 5px 0px 125px',
           			items: [
           				{xtype: 'hidden', name: 'cdunieco'},
           				{
           				xtype: 'textfield',
           				fieldLabel: getLabelFromMap('conPolRenTxtAseg',helpMap,'Aseguradora'),
           				tooltip: getToolTipFromMap('conPolRenTxtAseg',helpMap,'Aseguradora'),
           				/*anchor: '95%'*/width:150, id: 'conPolRenTxtAseg', name: 'dsunieco',
           				readOnly: true
           				}
           			]
            		},
					{
					layout: 'form',
					style: 'padding: 5px 5px 5px 15px',
					items: [
						{xtype: 'hidden', name: 'cdramo'},
						{xtype: 'textfield',
						 fieldLabel: getLabelFromMap('conPolRenTxtProd',helpMap,'Producto'),
						 tooltip: getToolTipFromMap('conPolRenTxtProd',helpMap,'Producto'),
						 /*anchor: '95%'*/width:150, id: 'conPolRenTxtProd', name: 'dsramo',
						 readOnly: true
						 }
					]
					},
					{
           			layout: 'form',
					style: 'padding: 0px 5px 0px 125px',
           			items: [
           				{
           				xtype: 'textfield',
           				fieldLabel: getLabelFromMap('conPolRenTxtPR',helpMap,'Poliza de Renovacion'),
           				tooltip: getToolTipFromMap('conPolRenTxtPR',helpMap,'Poliza de Renovacion'),
           				/*anchor: '95%'*/width:150, id: 'conPolRenTxtPR', name: 'nmpolant',
           				readOnly: true
           				}
           			]
            		},
					{
           			layout: 'form',
					style: 'padding: 0px 5px 0px 15px',
           			items: [
           				{xtype: 'hidden', name: 'cdperson'},
           				{
           				xtype: 'textfield',
           				fieldLabel: getLabelFromMap('conPolRenTxtAsegurado',helpMap,'Asegurado'),
           				tooltip: getToolTipFromMap('conPolRenTxtAsegurado',helpMap,'Asegurado'),
           				/*anchor: '95%'*/width:150, id: 'conPolRenTxtAsegurado', name: 'asegurado',
           				readOnly: true
           				}
           			]
            		},{
           			layout: 'form',
					style: 'padding: 0px 5px 0px 125px',
           			items: [
           				{
           				xtype: 'textfield',
           				fieldLabel: getLabelFromMap('conPolRenTxtPolAct',helpMap,'Poliza Actual'),
           				tooltip: getToolTipFromMap('conPolRenTxtPolAct',helpMap,'Poliza Actual'),
           				/*anchor: '95%'*/width:150, id: 'conPolRenTxtPolAct', name: 'nmpoliza',
           				readOnly: true
           				}
           				]
            		},
					{
					layout: 'form',
					style: 'padding: 0px 5px 0px 15px',
					items: [
						{xtype: 'textfield',
						 fieldLabel: getLabelFromMap('conPolRenTxtPrTot',helpMap,'Prima Total'),
						 tooltip: getToolTipFromMap('conPolRenTxtPrTot',helpMap,'Prima Total'),
						 /*anchor: '95%'*/width:150, id: 'conPolRenTxtPrTot', name: 'prima_total', readOnly: true}
					]
					},
					{
           			layout: 'form',
					style: 'padding: 0px 5px 0px 125px',
           			items: [
           				{
           				xtype: 'textfield',
           				fieldLabel: getLabelFromMap('conPolRenTxtIniVig',helpMap,'Inicio de Vigencia'),
           				tooltip: getToolTipFromMap('conPolRenTxtIniVig',helpMap,'Inicio de Vigencia'),
           				/*anchor: '95%'*/width:150, id: 'conPolRenTxtIniVig', name: 'feefecto', readOnly: true}
           			]
            		},
					{
           			layout: 'form',
					style: 'padding: 0px 5px 0px 15px',
           			items: [
           				{
           				xtype: 'textfield',
           				fieldLabel: getLabelFromMap('conPolRenTxtFinVig',helpMap,'Fin de Vigencia'),
           				tooltip: getToolTipFromMap('conPolRenTxtFinVig',helpMap,'Fin de Vigencia'),
           				/*anchor: '95%'*/width:150, id: 'conPolRenTxtFinVig', name: 'fevencim',
           				readOnly: true
           				}
           			]
            		},
            		{
           			layout: 'form',
					style: 'padding: 0px 5px 0px 125px',
           			items: [
           				{
           				xtype: 'combo',
           				tpl: '<tpl for="."><div ext:qtip="{id}.{texto}" class="x-combo-list-item">{texto}</div></tpl>',
           				store: dataStoreComboFormaPago,
           				//anchor:'100%',
           				width: 150,
           				displayField:'texto',
           				valueField: 'texto',
           				hiddenName: 'cdperpag',
			            typeAhead: true,
			            triggerAction: 'all',
			            lazyRender:true,
			            emptyText:'Seleccione Forma de Pago...',
			            selectOnFocus:true,
			            forceSelection:true,
			            fieldLabel: getLabelFromMap('conPolRenCmbForPag',helpMap,'Forma de Pago'),
           				tooltip: getToolTipFromMap('conPolRenCmbForPag',helpMap,'Forma de Pago'),			            
			            id: 'conPolRenCmbForPag'
			        	}
           			]
            		},
					{
           			layout: 'form',
					style: 'padding: 0px 5px 5px 15px',
           			items: [
           				{
           				xtype: 'combo',
           				tpl: '<tpl for="."><div ext:qtip="{cdForPag}.{dsForPag}.{cdMuestra}" class="x-combo-list-item">{dsForPag}</div></tpl>',
           				store: dataStoreComboInstrumentoPago,
           				//anchor:'100%',
           				width: 150,
           				displayField:'dsForPag',//dsperpag
           				valueField: 'cdPerPag',
           				hiddenName: 'cdperpag',
           				name:'dsforpag',
			            typeAhead: true,
			            triggerAction: 'all',
			            lazyRender:true,
			            emptyText:'Seleccione Instrumento de Pago...',
			            selectOnFocus:true,
			            forceSelection:true,
			            fieldLabel: getLabelFromMap('conPolRenCmbInsPag',helpMap,'Instrumento de Pago'),
           				tooltip: getToolTipFromMap('conPolRenCmbInsPag',helpMap,'Instrumento de Pago'),			            
			            id: 'conPolRenCmbInsPag'
			        	}
           			]
            		}
            ],
       		buttonAlign: 'center',
       		buttons: [
   					{
					text:getLabelFromMap('conPolRenBtnSave',helpMap,'Guardar'),
					tooltpi:getToolTipFromMap('conPolRenBtnSave',helpMap,'Guardar'),
					handler:function(){
							if(panelDatosGrales.form.isValid()){guardarDtosGralesYVariables();}
							else{Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informacion requerida'));}
						}
					},
      				{
					text: getLabelFromMap('conPolRenBtnSave',helpMap,'Regresar'),
      				tooltpi: getToolTipFromMap('conPolRenBtnSave',helpMap,'Regresar a la pagina anterior')/*,
      				handler: function(){}*/
      				}
			]
	});
	
	
	function guardarDtosGralesYVariables(){		
		var _paramsDatGrales = "";
		 	_paramsDatGrales += "listDatosGrales[0].cdunieco=" + storeGetDtosGrles.reader.jsonreader.listEncabezadoPoliza[0].cdunieco;
			_paramsDatGrales += "&listDatosGrales[0].cdramo=" + storeGetDtosGrles.reader.jsonreader.listEncabezadoPoliza[0].cdramo;
			_paramsDatGrales += "&listDatosGrales[0].estado=" + storeGetDtosGrles.reader.jsonreader.listEncabezadoPoliza[0].estado;
			_paramsDatGrales += "&listDatosGrales[0].nmpoliza=" + storeGetDtosGrles.reader.jsonreader.listEncabezadoPoliza[0].nmpoliza;
			_paramsDatGrales += "&listDatosGrales[0].nmsuplem=" + storeGetDtosGrles.reader.jsonreader.listEncabezadoPoliza[0].nmsuplem;
			_paramsDatGrales += "&listDatosGrales[0].status=" + storeGetDtosGrles.reader.jsonreader.listEncabezadoPoliza[0].status;
			_paramsDatGrales += "&listDatosGrales[0].swestado=" + storeGetDtosGrles.reader.jsonreader.listEncabezadoPoliza[0].swestado;
			_paramsDatGrales += "&listDatosGrales[0].nmsolici=" + storeGetDtosGrles.reader.jsonreader.listEncabezadoPoliza[0].nmsolici;
			_paramsDatGrales += "&listDatosGrales[0].feautori=" + storeGetDtosGrles.reader.jsonreader.listEncabezadoPoliza[0].feautori;
			_paramsDatGrales += "&listDatosGrales[0].cdmotanu=" + storeGetDtosGrles.reader.jsonreader.listEncabezadoPoliza[0].cdmotanu;
			_paramsDatGrales += "&listDatosGrales[0].feanulac=" + storeGetDtosGrles.reader.jsonreader.listEncabezadoPoliza[0].feanulac;
			_paramsDatGrales += "&listDatosGrales[0].swautori=" + storeGetDtosGrles.reader.jsonreader.listEncabezadoPoliza[0].swautori;
			_paramsDatGrales += "&listDatosGrales[0].cdmoneda=" + storeGetDtosGrles.reader.jsonreader.listEncabezadoPoliza[0].cdmoneda;
			_paramsDatGrales += "&listDatosGrales[0].feinisus=" + storeGetDtosGrles.reader.jsonreader.listEncabezadoPoliza[0].feinisus;
			_paramsDatGrales += "&listDatosGrales[0].fefinsus=" + storeGetDtosGrles.reader.jsonreader.listEncabezadoPoliza[0].fefinsus;
			_paramsDatGrales += "&listDatosGrales[0].ottempot=" + storeGetDtosGrles.reader.jsonreader.listEncabezadoPoliza[0].ottempot;
			_paramsDatGrales += "&listDatosGrales[0].feefecto=" + storeGetDtosGrles.reader.jsonreader.listEncabezadoPoliza[0].feefecto;
			_paramsDatGrales += "&listDatosGrales[0].hhefecto=" + storeGetDtosGrles.reader.jsonreader.listEncabezadoPoliza[0].hhefecto;
			_paramsDatGrales += "&listDatosGrales[0].feproren=" + storeGetDtosGrles.reader.jsonreader.listEncabezadoPoliza[0].feproren;
			_paramsDatGrales += "&listDatosGrales[0].fevencim=" + storeGetDtosGrles.reader.jsonreader.listEncabezadoPoliza[0].fevencim;
			_paramsDatGrales += "&listDatosGrales[0].nmrenova=" + storeGetDtosGrles.reader.jsonreader.listEncabezadoPoliza[0].nmrenova;
			_paramsDatGrales += "&listDatosGrales[0].ferecibo=" + storeGetDtosGrles.reader.jsonreader.listEncabezadoPoliza[0].ferecibo;
			_paramsDatGrales += "&listDatosGrales[0].feultsin=" + storeGetDtosGrles.reader.jsonreader.listEncabezadoPoliza[0].feultsin;
			_paramsDatGrales += "&listDatosGrales[0].nmnumsin=" + storeGetDtosGrles.reader.jsonreader.listEncabezadoPoliza[0].nmnumsin;
			_paramsDatGrales += "&listDatosGrales[0].cdtipcoa=" + storeGetDtosGrles.reader.jsonreader.listEncabezadoPoliza[0].cdtipcoa;
			_paramsDatGrales += "&listDatosGrales[0].swtarifi=" + storeGetDtosGrles.reader.jsonreader.listEncabezadoPoliza[0].swtarifi;
			_paramsDatGrales += "&listDatosGrales[0].swabrido=" + storeGetDtosGrles.reader.jsonreader.listEncabezadoPoliza[0].swabrido;
			_paramsDatGrales += "&listDatosGrales[0].feemisio=" + storeGetDtosGrles.reader.jsonreader.listEncabezadoPoliza[0].feemisio;
			_paramsDatGrales += "&listDatosGrales[0].cdperpag=" + storeGetDtosGrles.reader.jsonreader.listEncabezadoPoliza[0].cdperpag;
			_paramsDatGrales += "&listDatosGrales[0].nmpoliex=" + storeGetDtosGrles.reader.jsonreader.listEncabezadoPoliza[0].nmpoliex;
			_paramsDatGrales += "&listDatosGrales[0].nmcuadro=" + storeGetDtosGrles.reader.jsonreader.listEncabezadoPoliza[0].nmcuadro;
			_paramsDatGrales += "&listDatosGrales[0].porredau=" + storeGetDtosGrles.reader.jsonreader.listEncabezadoPoliza[0].porredau;
			_paramsDatGrales += "&listDatosGrales[0].swconsol=" + storeGetDtosGrles.reader.jsonreader.listEncabezadoPoliza[0].swconsol;
			_paramsDatGrales += "&listDatosGrales[0].nmpolant=" + storeGetDtosGrles.reader.jsonreader.listEncabezadoPoliza[0].nmpolant;
			_paramsDatGrales += "&listDatosGrales[0].nmpolnva=" + storeGetDtosGrles.reader.jsonreader.listEncabezadoPoliza[0].nmpolnva;
			_paramsDatGrales += "&listDatosGrales[0].fesolici=" + storeGetDtosGrles.reader.jsonreader.listEncabezadoPoliza[0].fesolici;
			_paramsDatGrales += "&listDatosGrales[0].cdramant=" + storeGetDtosGrles.reader.jsonreader.listEncabezadoPoliza[0].cdramant;
			_paramsDatGrales += "&listDatosGrales[0].cdmejred=" + storeGetDtosGrles.reader.jsonreader.listEncabezadoPoliza[0].cdmejred;
			_paramsDatGrales += "&listDatosGrales[0].nmpoldoc=" + storeGetDtosGrles.reader.jsonreader.listEncabezadoPoliza[0].nmpoldoc;
			_paramsDatGrales += "&listDatosGrales[0].nmpoliza2=" + storeGetDtosGrles.reader.jsonreader.listEncabezadoPoliza[0].nmpoliza2;
			_paramsDatGrales += "&listDatosGrales[0].nmrenove=" + storeGetDtosGrles.reader.jsonreader.listEncabezadoPoliza[0].nmrenove;
			_paramsDatGrales += "&listDatosGrales[0].nmsuplee=" + storeGetDtosGrles.reader.jsonreader.listEncabezadoPoliza[0].nmsuplee;
			_paramsDatGrales += "&listDatosGrales[0].ttipcamc=" + storeGetDtosGrles.reader.jsonreader.listEncabezadoPoliza[0].ttipcamc;
			_paramsDatGrales += "&listDatosGrales[0].ttipcamv=" + storeGetDtosGrles.reader.jsonreader.listEncabezadoPoliza[0].ttipcamv;
			_paramsDatGrales += "&listDatosGrales[0].swpatent=" + storeGetDtosGrles.reader.jsonreader.listEncabezadoPoliza[0].swpatent;
			_paramsDatGrales += "&listDatosGrales[0].accion=" + storeGetDtosGrles.reader.jsonreader.listEncabezadoPoliza[0].accion;
		
		var _paramsDatVbles = "";
			_paramsDatVbles = "listValoresPolizas[0].cdunieco=" + storeGetDtosGrles.reader.jsonreader.listEncabezadoPoliza[0].cdunieco;
			_paramsDatVbles = "&listValoresPolizas[0].cdramo=" + storeGetDtosGrles.reader.jsonreader.listEncabezadoPoliza[0].cdramo;
			_paramsDatVbles = "&listValoresPolizas[0].estado=" + storeGetDtosGrles.reader.jsonreader.listEncabezadoPoliza[0].estado;
			_paramsDatVbles = "&listValoresPolizas[0].nmpoliza=" + storeGetDtosGrles.reader.jsonreader.listEncabezadoPoliza[0].nmpoliza;
			_paramsDatVbles = "&listValoresPolizas[0].nmsuplem=" + storeGetDtosGrles.reader.jsonreader.listEncabezadoPoliza[0].nmsuplem;
			_paramsDatVbles = "&listValoresPolizas[0].status=" + storeGetDtosGrles.reader.jsonreader.listEncabezadoPoliza[0].status;
		
		Ext.each(formAtributosVbles.getForm().items.items, function(campito){
					if(campito.name != ""){
						var idx = campito.name.indexOf(".");
						if (idx > 0){
							var valor = campito.name.substring(idx + 1);
							if(valor.length == 1) valor = "0" + valor;
							_paramsDatVbles += "&listValoresPolizas[0].pi_otvalor" + valor + "=" + campito.getValue();
						}
					}
					else{_paramsDatVbles += "&datosAdicionales[0]." + campito.name + "=" + campito.cdAtribu;}
		});
		if(_params.length > 0){
			execConnection(_ACTION_GUARDAR_DATOS_ADICIONALES, _params, cbkGuardarDtosGralesYVariables);
		}
	}
	
	function cbkGuardarDtosGralesYVariables(_success, _message) {
		if (!_success) {
			Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
		}else {
			Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message);
		}
	}
	
	
	
	var panelGeneral = new Ext.FormPanel({
     	renderTo: 'divDatosGrales',
       	title: getLabelFromMap('',helpMap,'Consulta de Poliza Renovada'),
       	width: 780,
       	items: [
	        {
	        xtype:'tabpanel',
	        id: 'ttabs',
			resizeTabs:true,
			enableTabScroll: true,
			layoutOnTabChange:false,
			width:780,
			height:370,
			activeTab: 0,
			defaults: {labelWidth: 80},
			items:[
			{
		     title: getLabelFromMap('conPolRenTabDG',helpMap,'Datos Grales'),
		     tooltip: getToolTipFromMap('conPolRenTabDG',helpMap,'Datos generales y atributos variables definidos a nivel poliza'),
		     layout: 'fit',
		     id: '4',
		     items:[panelDatosGrales]
		     },
		     {
		     title: getLabelFromMap('conPolRenTabOA',helpMap,'Obj. Asegurable'),
		     tooltip: getToolTipFromMap('conPolRenTabOA',helpMap,'Objetos asegurables de la poliza y personas y funciones que realizan la poliza'),
		     layout: 'fit',
		     id: '5'
			},
			{
			title: getLabelFromMap('conPolRenTabDOG',helpMap,'Datos Obj. Asegurable'),
		    tooltip: getToolTipFromMap('conPolRenTabDOG',helpMap,'Pantalla que permite modificar los datos variables de los incisos'),
			layout: 'fit',
		    id: '6'
			},
			{
			title: getLabelFromMap('conPolRenTabCob',helpMap,'Coberturas'),
		    tooltip: getToolTipFromMap('conPolRenTabCob',helpMap,'Coberturas que tiene la poliza'),
			layout: 'fit',
			
		    id: '7'
			},
			{					
			title: getLabelFromMap('conPolRenTabDC',helpMap,'Datos de Coberturas'),
		    tooltip: getToolTipFromMap('conPolRenTabDC',helpMap,'Datos variables de la cobertura'),
			layout: 'fit',
		    id: '8'
			},
			{
			title: getLabelFromMap('conPolRenTabAcc',helpMap,'Accesorios'),
		    tooltip: getToolTipFromMap('conPolRenTabAcc',helpMap,'Accesorios que pueden utilizarse en producto del seguro'),
			layout: 'fit',
		    id: '9'
		    },
			{
			title: getLabelFromMap('conPolRenTabReci',helpMap,'Recibos'),
		    tooltip: getToolTipFromMap('conPolRenTabReci',helpMap,'Recibos que se generan para la poliza renovada acorde a la nueva forma de pago'),
			layout: 'fit',
		    id: '10'
			}]
    	}]
	});
	
	panelGeneral.render();
	/*dataStoreComboFormaPago.load({
				params: {otClave1: storeGetDtosGrles.reader.jsonreader.listEncabezadoPoliza[0].status;},
				callback:function(){
					dataStoreComboFormaPago.load();
					panelDatosGrales.form.load({
							params:{cdUniEco: CODIGO_ASEGURADORA,
								   cdRamo:CODIGO_PRODUCTO,
								   estado:ESTADO,
								   nmPoliza:NUMERO_POLIZA},
							waitTitle: getLabelFromMap('400017',helpMap,'Espere'),
							waitMsg: getLabelFromMap('400028',helpMap,'Leyendo datos ...'),		
							failure: function(){
								Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400033', helpMap,'No se encontraron registros'));
							}
					});
				}
			});*/
			
	dataStoreComboInstrumentoPago.load({
			callback:function(){
				panelDatosGrales.form.load({
							params:{cdUniEco: CODIGO_ASEGURADORA,
								   cdRamo:CODIGO_PRODUCTO,
								   estado:ESTADO,
								   nmPoliza:NUMERO_POLIZA},
							waitTitle: getLabelFromMap('400017',helpMap,'Espere'),
							waitMsg: getLabelFromMap('400028',helpMap,'Leyendo datos ...'),		
							success: function(){
								Ext.getCmp("conPolRenCmbInsPag").setValue(storeGetDtosGrles.reader.jsonData.MListPolizaEncabezado[0].dsforpag);
							},
							failure: function(){
								Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400033', helpMap,'No se encontraron registros'));
							}
					});
			}
	});					
});