 function insertar(){ 
   
   	    var _proxy = new Ext.data.HttpProxy({url: _ACTION_OBTENER_P_LISTA_TCATALOG });
   		
   		var _reader = new Ext.data.JsonReader({
	     		root: 'comboIndicadoresSINO',
	     		totalProperty: 'totalCount',
	     		id: 'id'
	     		},[
	    			{name: 'id', type: 'string',mapping:'id'},
	    			{name: 'texto', type: 'string',mapping:'texto'}
	 			  ]);
   		
    	var dsTarificacionAutomatica = new Ext.data.Store({
	     	proxy: _proxy,
	     	reader: _reader
	     	
	 	});		 	
   	
   		
   		dsTarificacionAutomatica.load();
    		
		var codigo = new Ext.form.TextField({
			     fieldLabel: getLabelFromMap('codigoId',helpMap,'C&oacute;digo'),
			     tooltip:getToolTipFromMap('codigoId',helpMap,'C&oacute;digo del tipo de endoso'), 
			     hasHelpIcon:getHelpIconFromMap('codigoId',helpMap),
				 Ayuda: getHelpTextFromMap('codigoId',helpMap),	
				 id:'codigoId',
			     name:'cdTipSup',
			     disabled: true,
			     //anchor: '100%'
			     width: 300
			 });
			 
		var descripcion = new Ext.form.TextField({
			     fieldLabel: getLabelFromMap('descripcionId',helpMap,'Descripci&oacute;n'),
			     tooltip:getToolTipFromMap('descripcionId',helpMap,'Descripci&oacute;n del tipo de endoso'),
			     hasHelpIcon:getHelpIconFromMap('descripcionId',helpMap),
				 Ayuda: getHelpTextFromMap('descripcionId',helpMap),	
				 id:'descripcionId',
			     name:'dsTipSup',
			     width: 300,
		         allowBlank: false,
			     anchor: '100%'
			 });
		
		
		var comboTarificacionAutomatica = new Ext.form.ComboBox({
	                    tpl: '<tpl for="."><div ext:qtip="{id}.{texto}" class="x-combo-list-item">{texto}</div></tpl>',
	                    id:'comboTarificacionAutomaticaId',
	                    store: dsTarificacionAutomatica,
	                    displayField:'texto',
	                    valueField:'id',
	                    hiddenName: 'swTariFi',
	                    typeAhead: true,
	                    mode: 'local',
	                    triggerAction: 'all',
	                    allowBlank: false,
					    fieldLabel: getLabelFromMap('comboTarificacionAutomaticaId',helpMap,'Tarificaci&oacute;n Autom&aacute;tica'),
					    tooltip:getToolTipFromMap('comboTarificacionAutomaticaId',helpMap,'Tarificaci&oacute;n Autom&aacute;tica del tipo de endoso'),
					    hasHelpIcon:getHelpIconFromMap('comboTarificacionAutomaticaId',helpMap),
				        Ayuda: getHelpTextFromMap('comboTarificacionAutomaticaId',helpMap),	
	                    forceSelection: true,
	                    width: 150,
	                    selectOnFocus:true,
	                    emptyText:'Seleccione Tarificación Automática...',
	                    labelSeparator:''
	   });
	   
		var formWindowInsertar = new Ext.FormPanel({
				title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('frmWndwIns',helpMap,'Tipos de Endosos')+'</span>',
		        iconCls:'logo',
		        bodyStyle:'background: white',
		        buttonAlign: "center",
		        labelAlign: 'right',
		        frame:true,
		        url: _ACTION_GUARDAR_TIPO_ENDOSO,
		        width: 550,
		        height:200,
		        items: [
            		{
           			layout: 'form',
           			items:[
           					{//1ra columna
	           					columnWidth: .5,
	           					layout: 'form',
	           					items: [codigo]
           				    },
           				    {//2da columna
	           				   	columnWidth: .5,
	           					layout: 'form',
	           					items: [descripcion]
           				    }
           			]        			
           		   },
           		   comboTarificacionAutomatica
           		   ]
				});
			
		 var window = new Ext.Window({
        	width: 550,
        	height:195,
        	minWidth: 300,
        	minHeight: 100,
        	modal: true,
        	layout: 'fit',
        	plain:true,
        	bodyStyle:'padding:5px;',
        	buttonAlign:'center',
        	items: formWindowInsertar,
            buttons: [{
                text:getLabelFromMap('agrConfTipEndBtnSave',helpMap,'Guardar'),
                tooltip: getToolTipFromMap('agrConfTipEndBtnSave',helpMap,'Guarda tipos de endosos'),
                disabled : false,
                handler : function() {
                    if (formWindowInsertar.form.isValid()) {
                        formWindowInsertar.form.submit( {
                            url : _ACTION_GUARDAR_TIPO_ENDOSO,
                            success : function(from, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),action.result.actionMessages[0], function(){reloadGrid();});
                                window.close();
                            },
                            failure : function(form, action) {
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
              text:getLabelFromMap('agrConfTipEndBtnBack',helpMap,'Regresar'),
              tooltip: getToolTipFromMap('agrConfTipEndBtnBack',helpMap,'Regresa a la pantalla anterior'),
              handler : function(){window.close();}
            }]
    	});
    	window.show();
	}