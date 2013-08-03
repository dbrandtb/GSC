function guardar(_record){
    	
    	//CONFIGURACION PARA EL COMBO TARIFACION AUTOMATICA *****************************	
   		//var _proxy = new Ext.data.HttpProxy({url: _ACTION_OBTENER_INDICADORES_SI_NO});   		
   		var _proxy = new Ext.data.HttpProxy({url: _ACTION_OBTENER_P_LISTA_TCATALOG });
   		var _reader = new Ext.data.JsonReader({
	     		root: 'comboIndicadoresSINO',
	     		totalProperty: 'totalCount',
	     		id: 'id'
	     		},[
	    			{name: 'id', type: 'string',mapping:'id'},
	    			{name: 'texto', type: 'string',mapping:'texto'}
	 	]);   		
    	var dsTarificacionAutomatica = new Ext.data.Store({proxy: _proxy,reader: _reader});		 	
		
		//CONFIGURACION PARA EL GET DEL REGISTRO SELECCIONADO* ***************************
		var _proxy = new Ext.data.HttpProxy({url: _ACTION_OBTENER_TIPOS_ENDOSOS});			
		var _reader = new Ext.data.JsonReader({
	         	root:'endososEstructuraList',
	         	totalProperty: 'totalCount',
	          	successProperty : '@success',
	          	id:'cdTipSup'
	       },
	       [
	       {name: 'cdTipSup',   type: 'string',  mapping:'cdTipSup'},
	       {name: 'cdTipoSup',   type: 'string',  mapping:'cdTipSup'},
	       {name: 'dsTipSup',   type: 'string',  mapping:'dsTipSup'},
		   {name: 'swSuplem',  type: 'string',  mapping:'swSuplem'},
	       {name: 'swTariFi',  type: 'string',  mapping:'swTariFi'},
	       {name: 'cdTipDoc', type: 'string',  mapping:'cdTipDoc'}
		]);		
		var _store  = new Ext.data.Store({proxy: _proxy,reader: _reader});						                   
		
		//CONFIGURACION PARA EL FORMULARIO DE EDICIÓN *****************************
		var formWindowEdit = new Ext.FormPanel({
				id:'formWindowEditId',
		        title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('formWindowEditId',helpMap,'Tipos de Endosos')+'</span>',
		        iconCls:'logo',
		        bodyStyle:'background: white',
		        buttonAlign: "center",
		        labelAlign: 'right',
		        frame:true,
		        url: _ACTION_OBTENER_TIPOS_ENDOSOS,
		        reader:_reader,
		        width: 520,
		        height:200,
		        items: [
	           		{
	          			layout: 'form',
	          			items:[
	          					{//1ra columna
	           					columnWidth: .5,
	           					layout: 'form',
	           					items: [
									new Ext.form.TextField({
									    fieldLabel: getLabelFromMap('editConfTipEndTxtCod',helpMap,'C&oacute;digos'),
									    tooltip:getToolTipFromMap('editConfTipEndTxtCod',helpMap,'C&oacute;digo del tipo de endoso'),
									    hasHelpIcon:getHelpIconFromMap('editConfTipEndTxtCod',helpMap),
				 						Ayuda: getHelpTextFromMap('editConfTipEndTxtCod',helpMap),	
							     		name:'cdTipoSup',
							     		disabled : true,  
							     	//anchor: '100%'
							     		 width: 300
									}),
									new Ext.form.Hidden({
								 		id:'codigoId',
							     		name:'cdTipSup',
							     		anchor: '100%'
									})
									
								]
	          				    },
	          				    {//2da columna
	           				   	columnWidth: .5,
	           					layout: 'form',
	           					items: [
		           					new Ext.form.TextField({
									     fieldLabel: getLabelFromMap('descripcionId',helpMap,'Descripci&oacute;n'),
								         tooltip:getToolTipFromMap('descripcionId',helpMap,'Descripci&oacute;n del tipo de endoso'),
								          hasHelpIcon:getHelpIconFromMap('descripcionId',helpMap),
				 						 Ayuda: getHelpTextFromMap('descripcionId',helpMap),	
										 id:'descripcionId',
									     name:'dsTipSup',
									     readOnly:false,
									     width: 300,
							     		 allowBlank: false,
							     		 anchor: '100%'
									})
								]
	          				    }
	          			]        			
	          		   },
	          		   	new Ext.form.ComboBox({
			                    tpl: '<tpl for="."><div ext:qtip="{id}.{texto}" class="x-combo-list-item">{texto}</div></tpl>',
			                    id:'comboTarificacionAutomaticaId',
			                    store: dsTarificacionAutomatica,
			                    displayField:'texto',
			                    valueField:'id',
			                    hiddenName: 'swTariFi',
			                    typeAhead: true,
			                    mode: 'local',
							    allowBlank: false,			                    
			                    triggerAction: 'all',
							    fieldLabel: getLabelFromMap('comboTarificacionAutomaticaId',helpMap,'Tarificaci&oacute;n Autom&aacute;tica'),
							    tooltip:getToolTipFromMap('comboTarificacionAutomaticaId',helpMap,'Tarificaci&oacute;n Autom&aacute;tica del tipo de endoso'),
							    hasHelpIcon:getHelpIconFromMap('comboTarificacionAutomaticaId',helpMap),
				 				Ayuda: getHelpTextFromMap('comboTarificacionAutomaticaId',helpMap),	
			                    forceSelection: true,
			                    width: 150,
			                    selectOnFocus:true,
			                    emptyText:'Tarificacion Automatica'
	    				})
	          		   ]
				});
	
		 var window = new Ext.Window({
	       	width: 520,
	       	height:200,
	       	minWidth: 300,
	       	modal: true,
	       	minHeight: 100,
	       	layout: 'fit',
	       	plain:true,
	       	bodyStyle:'padding:5px;',
	       	buttonAlign:'center',
	       	items: formWindowEdit,
	        buttons: [{
               text:getLabelFromMap('editConfTipEndBtnSave',helpMap,'Guardar'),
               tooltip: getToolTipFromMap('editConfTipEndBtnSave',helpMap,'Guarda tipos de endosos'),
               handler : function(){
                   if(formWindowEdit.form.isValid()){
                       formWindowEdit.form.submit({
                           url: _ACTION_GUARDAR_TIPO_ENDOSO,
                           success : function(from, action){
                               //Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),'Guardado satisfactoriamente', function(){reloadGrid();});
                               Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),action.result.actionMessages[0], function(){reloadGrid();});
                               window.close();
                           },
                           failure: function(form, action){
                               //Ext.MessageBox.alert(getLabelFromMap('400010', helpMap,'Error'),'Problemas al Insertar : ' + action.result.errorMessages[0]);
                               Ext.MessageBox.alert(getLabelFromMap('400010', helpMap,'Error'),action.result.errorMessages[0]);
                           },
                           waitTitle: getLabelFromMap('400017',helpMap,'Espere ...'),
                           waitMsg : getLabelFromMap('400027', helpMap,'Guardando datos...')
                       });
                   }else{
                   	   Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
                   }
               }
	        },
	           {
                text:getLabelFromMap('editConfTipEndBtnBack',helpMap,'Regresar'),
                tooltip: getToolTipFromMap('editConfTipEndBtnBack',helpMap,'Regresa a la pantalla anterior'),
	            handler : function(){window.close();}
	           }]
	   	});
	   	window.show();
	   	
	   	dsTarificacionAutomatica.load();	   
	   	formWindowEdit.form.load({
	   		params: {cdTipSup: _record.get('cdTipSup')},
	   		callback:function(){
	   				if(_record.get('swTariFi')=="Si"  ){
	   					//formWindowEdit.findById("comboTarificacionAutomaticaId").setValue(_store.reader.jsonData.endososEstructuraList[0].swTariFi);
	   					formWindowEdit.findById("comboTarificacionAutomaticaId").setValue("S");
	   				}else{
	   					formWindowEdit.findById("comboTarificacionAutomaticaId").setValue("N");
	   				}
	   		}
	   		});	   
	};