
function agregarGuionLlamadas () {
  
   var storeAseguradoras = new Ext.data.Store({

    			proxy: new Ext.data.HttpProxy({
				url: _ACTION_COMBO_ASEGURADORA
                }),

                reader: new Ext.data.JsonReader({
            	root:'aseguradoraComboBox',
            	totalProperty: 'totalCount',
            	successProperty : '@success'
	        },[
		        {name: 'codigo',  type: 'string',  mapping:'cdUniEco'},
		        {name: 'descripcion',  type: 'string',  mapping:'dsUniEco'}
			])
        });	
        
    var dsGruposPersona = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_GRUPOS_PERSONA
            }),
            reader: new Ext.data.JsonReader({
            root: 'grupoPersonasCliente',
            id: 'codigo'
            },[
           {name: 'codigo', type: 'string',mapping:'codigo'},
           {name: 'descripcion', type: 'string',mapping:'descripcion'}
        ])
    });	
    
    var dsEstado = new Ext.data.Store ({
						proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_ESTADO}),
						reader: new Ext.data.JsonReader({
								root: 'estadosEjecutivo',
								id: 'id',
								successProperty: '@success'
							}, [
								{name: 'codigo', type: 'string', mapping: 'codigo'},
								{name: 'descripcion', type: 'string', mapping: 'descripcion'}
							]),
						remoteSort: true
				});
	var storeGridDialogo  = new Ext.data.Store ({
						proxy: new Ext.data.HttpProxy({url: _ACTION_OBTENER_DIALOGO}),
						reader: new Ext.data.JsonReader({
								root: 'ComboFormatosArchivos',
								id: 'codigo',
								successProperty: '@success'
							}, [
								{name: 'codigo', type: 'string', mapping: 'codigo'},
								{name: 'descripcion', type: 'string', mapping: 'descripcion'}
							]),
						remoteSort: true
				});
				
	var dsComboGrupo = new Ext.data.Store({
 	    proxy: new Ext.data.HttpProxy({
     	url: _ACTION_COMBO_GRUPO
 	}),
    reader: new Ext.data.JsonReader({
        root: 'comboClientesCorpBO',
        id: 'cdElemento',
        successProperty: '@success'
    }, [
        {name: 'cdElemento', type: 'string', mapping: 'cdElemento'},
        {name: 'cdPerson', type: 'string', mapping: 'cdPerson'},
        {name: 'dsElemen', type: 'string', mapping: 'dsElemen'} 
    ])
    

});	

var dsProcesos = new Ext.data.Store ({
						proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_PROCESOS}),
						reader: new Ext.data.JsonReader({
								root: 'comboProcesosCat',
								id: 'cdProceso',
								successProperty: '@success'
							}, [
								{name: 'cdProceso', type: 'string', mapping: 'cdProceso'},
								{name: 'dsProceso', type: 'string', mapping: 'dsProceso'}
							]),
						remoteSort: true
				});	

var dsProductos = new Ext.data.Store({
 	proxy: new Ext.data.HttpProxy({
     	url: _ACTION_COMBO_RAMOS
 	}),
 	reader: new Ext.data.JsonReader({
 		root: 'ramosComboBox',
 		totalProperty: 'totalCount',
 		id: 'cdRamo'
 		},[
			{name: 'cdRamo', type: 'string',mapping:'codigo'},
			{name: 'dsRamo', type: 'string',mapping:'descripcion'}
		  ])
});

var dsTipoGuion = new Ext.data.Store({
 	proxy: new Ext.data.HttpProxy({
     	url: _ACTION_COMBO_TIPO_GUION
 	}),
 	reader: new Ext.data.JsonReader({
 		root: 'comboTipoGuion',
 		totalProperty: 'totalCount',
 		id: 'cdTipGuion'
 		},[
			{name: 'cdTipGuion', type: 'string',mapping:'codigo'},
			{name: 'dsTipGuion', type: 'string',mapping:'descripcion'}
		  ])
});



var cm = new Ext.grid.ColumnModel([
        {
        header: getLabelFromMap('cmSecuenciaId',helpMap,'Secuencia'),
        tooltip: getToolTipFromMap('cmSecuenciaId',helpMap,'Secuencia'),
        dataIndex: 'cdSecuencia',
        width: 85,
        sortable: true
        },
        {
        header: getLabelFromMap('cmDialogoId',helpMap,'Di&aacute;logo'),
        tooltip: getToolTipFromMap('cmDialogoId',helpMap,'Di&aacute;logo'),
        dataIndex: 'cdDialogo',
        width: 75,
        sortable: true
        },
        {
        header: getLabelFromMap('cmDescripcionId',helpMap,'Descripci&oacute;n'),
        tooltip: getToolTipFromMap('cmDescripcionId',helpMap,'Descripci&oacute;n'),
        dataIndex: 'dsDialogo',
        width: 110,
        sortable: true
        },
		{       
        header: getLabelFromMap('cmListaValoresId',helpMap,'Lista de Valores'),
        tooltip: getToolTipFromMap('cmListaValoresId',helpMap,'Lista de Valores'),
        dataIndex: 'otTapVal',
        width: 100,
        sortable: true
        }
	]);
				
var grid = new Ext.grid.GridPanel({
       		id: 'gridId',
       		//el:'gridListado',
            store: storeGridDialogo,
            border:true,
            cm: cm,
	        successProperty: 'success', 
	        title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Configurar Di&aacute;logo</span>',           
            width:690,
            loadMask: {msg: getLabelFromMap('400058',helpMap,'Cargando datos ...'), disabled: false},
            buttonAlign:'center',
            frame:true,
            height:320,  
            //frame: true,
            buttons:[
                  {
                  text:getLabelFromMap('gridBtnGuardarId',helpMap,'Guardar'),
                  tooltip: getToolTipFromMap('gridBtnGuardarId',helpMap,'Guardar'),
                 
                  handler : function() {
                     if (formPanel.form.isValid()) {
                         /*formPanel.form.submit( {
                         //action a invocar cuando el formulario haga submit
                          url : _ACTION_GUARDAR_GUION_LLAMADA,
                         //funcion a ejecutar cuando la llamada a la action retorna satisfactoriamente
                        success : function(from, action) {
                        Ext.MessageBox.alert('Aviso', action.result.actionMessages[0], function(){});
                        window.close();
                      },
                       //funcion a ejecutar cuando la llamada a la action no se ejecuta satisfactoriamente
                        failure : function(form, action) {
                        Ext.MessageBox.alert('Error', action.result.errorMessages[0]);
                      },
                     //mensaje a mostrar mientras se guardan los datos
                     waitMsg : 'guardando datos ...'
                  });*/
                  guardarDatosGuionAndDialogo();
                } else {
                    Ext.Msg.alert('Aviso', 'Complete la informaci&oacute;n requerida');
                   }
              }
                 
                 
                 
                 
                  },
                  {
                  text:getLabelFromMap('gridBtnBorrarId',helpMap,'Eliminar'),
                  tooltip: getToolTipFromMap('gridBtnBorrarId',helpMap,'Eliminar')
                  }, 
                  {
                  text:getLabelFromMap('gridBtnExportarId',helpMap,'Exportar'),
                  tooltip: getToolTipFromMap('gridBtnExportarId',helpMap,'Exportar')
                  },
                  {
                  text:getLabelFromMap('gridBtnRegresarId',helpMap,'Regresar'),
                  tooltip: getToolTipFromMap('gridBtnRegresarId',helpMap,'Regresar'),
                  handler:function(){
                  		window.close();
                  }}
                  
                  ],          
            sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			stripeRows: true,
			collapsible: true,
			bbar: new Ext.PagingToolbar({
					pageSize:20,
					store: storeGridDialogo,
					displayInfo: true,
	                displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
					emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
			    })          
        });
        
    var guionText = new Ext.form.TextField({
        
        id: 'txtGuionId',
		fieldLabel: getLabelFromMap('txtGuionId',helpMap,'Guion'),
		tooltip: getToolTipFromMap('txtGuionId',helpMap,'Guion'), 	        
		name: 'dsGuion',
		//allowBlank: true,
		anchor: '80%'				        
		 
        
    });    

	var formPanel = new Ext.FormPanel({			
	        //el:'formBusqueda',
	        id: 'acFormPanelId',
	        url: _ACTION_OBTENER_DIALOGO,	        
	        title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Configurar Guion</span>',
	        //title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Configurar Guion</span><br/>',
	        iconCls:'logo',
	        bodyStyle:'background: white',	              
	        frame:true,
	        width: 690,
	        autoHeight:true,	        
            layout: 'table',
           	layoutConfig: { columns: 2, columnWidth: .50},
            labelAlign: 'right',
            items:[
            		{          
            		layout:'form',
            		//width:'100',
            		items:
            		           [{xtype: 'combo',
	           				   tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
		                       id:'cmbAseguradoraId',
		                       fieldLabel: getLabelFromMap('cmbAseguradoraId',helpMap,'Aseguradora'),
	        				   tooltip: getToolTipFromMap('cmbAseguradoraId',helpMap,'Aseguradora'),
		                       store: storeAseguradoras,
		                       displayField:'descripcion',
		                       valueField:'codigo',
		                       hiddenName: 'cdUniEco',
		                       typeAhead: true,
		                       mode: 'local',
		                       allowBlank: true,
		                       triggerAction: 'all',
		                       width: 200,
		                       emptyText:'Seleccione Aseguradora...',
		                       selectOnFocus:true,
		                       forceSelection:true,
		                       colspan:1}]
		              },
	        		          {layout:'form',
	        		          items:[
	        		          {xtype: 'combo',
	           				   tpl: '<tpl for="."><div ext:qtip="{cdTipGuion}. {dsTipGuion}" class="x-combo-list-item">{dsTipGuion}</div></tpl>',
		                       id:'cmbTipoGuionId',
		                       fieldLabel: getLabelFromMap('cmbTipoGuionId',helpMap,'Tipo de Guion'),
	        				   tooltip: getToolTipFromMap('cmbTipoGuionId',helpMap,'Tipo de Guion'),
		                       store: dsTipoGuion,
		                       displayField:'dsTipGuion',
		                       valueField:'cdTipGuion',
		                       hiddenName: 'cdTipGuion',
		                       typeAhead: true,
		                       mode: 'local',
		                       triggerAction: 'all',
		                       width: 200,
		                       emptyText:'Seleccione Tipo de Guion...',
		                       selectOnFocus:true,
		                       forceSelection:true,
		                       colspan:2}]
		                  
		            },
	        		{          layout:'form',
	        		           items:[{
	        		           xtype: 'combo',
	           				   tpl: '<tpl for="."><div ext:qtip="{cdElemento}.{cdPerson2}.{dsElemen}" class="x-combo-list-item">{dsElemen}</div></tpl>',
		                       id:'cmbGrupoId',
		                       fieldLabel: getLabelFromMap('cmbGrupoId',helpMap,'Grupo'),
	        				   tooltip: getToolTipFromMap('cmbGrupoId',helpMap,'Grupo'),
		                       store: dsComboGrupo,
		                       displayField:'dsElemen',
		                       valueField:'cdElemento',
		                       hiddenName: 'cdGrupo',
		                       typeAhead: true,
		                       mode: 'local',
		                       triggerAction: 'all',
		                       width: 200,
		                       emptyText:'Seleccione Grupo...',
		                       selectOnFocus:true,
		                       forceSelection:true,
		                       colspan:1
		                       }]
	        		},
	        		           				
					{	    
	           				  layout: 'form',
	        		          items:[{
	           				   xtype: 'combo',
	           				   tpl: '<tpl for="."><div ext:qtip="{cdRamo}. {dsRamo}" class="x-combo-list-item">{dsRamo}</div></tpl>',
		                       id:'cmbProductoId',
		                       fieldLabel: getLabelFromMap('cmbProductoId',helpMap,'Producto'),
	        				   tooltip: getToolTipFromMap('cmbProductoId',helpMap,'Producto'),
		                       store: dsProductos,
		                       displayField:'dsRamo',
		                       valueField:'cdRamo',
		                       hiddenName: 'cdRamo',
		                       typeAhead: true,
		                       mode: 'local',
		                       triggerAction: 'all',
		                       width: 200,
		                       emptyText:'Seleccione Producto...',
		                       selectOnFocus:true,
		                       forceSelection:true,
		                       colspan:2 }]
		                       
	        		},
	        		{
	        		 layout:'form',
	        		 items:[{
	        		 xtype: 'combo',
	           				   tpl: '<tpl for="."><div ext:qtip="{cdProceso}. {dsProceso}" class="x-combo-list-item">{dsProceso}</div></tpl>',
		                       id:'cmbProcesoId',
		                       fieldLabel: getLabelFromMap('cmbProcesoId',helpMap,'Proceso'),
	        				   tooltip: getToolTipFromMap('cmbProcesoId',helpMap,'Proceso'),
		                       store: dsProcesos,
		                       displayField:'dsProceso',
		                       valueField:'cdProceso',
		                       hiddenName: 'cdProceso',
		                       typeAhead: true,
		                       mode: 'local',
		                       triggerAction: 'all',
		                       width: 200,
		                       emptyText:'Seleccione Proceso...',
		                       selectOnFocus:true,
		                       forceSelection:true,
		                       colspan:2
	        		 }]
	        		},
	        		{layout:'form',
	        		items:[{
	        		 xtype: 'combo',
	           				   tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
		                       id:'cmbEstadoId',
		                       fieldLabel: getLabelFromMap('cmbEstadoId',helpMap,'Estado'),
	        				   tooltip: getToolTipFromMap('cmbEstadoId',helpMap,'Estado'),
		                       store: dsEstado,
		                       displayField:'descripcion',
		                       valueField:'codigo',
		                       hiddenName: 'cdEstado',
		                       typeAhead: true,
		                       mode: 'local',
		                       triggerAction: 'all',
		                       width: 200,
		                       emptyText:'Seleccione Estado...',
		                       selectOnFocus:true,
		                       forceSelection:true,
		                       colspan:3
	        		
	        		}]
	        		},
	        		{
	        		
	        		layout: 'form',
	        		
	        		items:[{
	        		xtype:'numberfield',
			        id: 'cdGuionId2',
					fieldLabel: getLabelFromMap('cdGuionId2',helpMap,'Guion'),
					tooltip: getToolTipFromMap('cdGuionId2',helpMap,'C&oacute;digo de Guion'), 	        
					name: 'cdGuion',
					width: 200
					}]
					},
					{layout: 'form',
					items:
					[{
					xtype:'textfield',
					id: 'textGuionId2',
					fieldLabel: getLabelFromMap('textGuionId2',helpMap,'Descripci&oacute;n de Guion'),
					tooltip: getToolTipFromMap('textGuionId2',helpMap,'Descripci&oacute;n de Guion'),
			        id: 'txtGuionId3',
			        name: 'dsGuion',
			        width: 200,
			        colspan:2
	        		}]
	        		}
	        		,
	        		{
	        		layout:'form',
	        		items:[
	        		{
	        		xtype:'checkbox',
			            		id: 'txtScriptInicialId',
						        fieldLabel: getLabelFromMap('txtScriptInicialId',helpMap,'Script Inicial'),
						        tooltip:getToolTipFromMap('txtScriptInicialId',helpMap,'Script Inicial'), 	        
						        name: 'dsScripInicial',
						        allowBlank: true,
						        colspan:3
						        }]
	        		}
	        		]
	        		
	        		
	        	
	        		});  
    
    
    //alert(formPanel);
    
	//alert( formPanel.findById("textGuionId2").getValue());

	var window = new Ext.Window ({
			//title: titulo,
			//title: '<span style="color:black;font-size:14px;">Editar Secci&oacute;n</span>',
			width: 700,
			autoHeight: true,
        	bodyStyle:'padding:5px;',
        	bodyStyle:'background: white',
        	buttonAlign:'center',
        	modal: true,
        	items: [
        	formPanel,
        	grid
        	]
           });

	
    //storeGridDialogo.load();
    storeAseguradoras.load({
			callback: function () {
			     dsGruposPersona.load({
			     	callback: function () {
			     	    dsEstado.load();
			     	    dsComboGrupo.load();
			     	    dsProcesos.load();
			     	    dsProductos.load();
			     	    dsTipoGuion.load();
			     	    
			     	}
			       }
			     )
					/*storeRazonCancelacion.load({
							callback: function () {
													if (CODIGO_ASEGURADORA != "" || CODIGO_PRODUCTO != "" || CODIGO_ESTADO != "" || NUMERO_POLIZA != "") {
														cargarDatosForm();
													}

							}
					});*/
			}
	});
 
 function guardarDatosGuionAndDialogo (){
   var _params = "";
   var conn = new Ext.data.Connection();
   conn.request({
     url: _ACTION_GUARDAR_GUION_LLAMADA,
     method: 'POST',
     params: {
        cdUnieco: formPanel.findById("cmbAseguradoraId").getValue(),
        cdRamo: formPanel.findById("cmbProductoId").getValue(),
        cdElemento: formPanel.findById("cmbGrupoId").getValue(),
        //cdElemento: 5216,
        cdProceso: formPanel.findById("cmbProcesoId").getValue(),
        cdGuion: formPanel.findById("cdGuionId2").getValue(),
        dsGuion: formPanel.findById("txtGuionId3").getRawValue(),
        cdTipGuion: formPanel.findById("cmbTipoGuionId").getValue(),
        //cdTipGuion: 1,
        indInicial: formPanel.findById("txtScriptInicialId").getValue(),
        //indInical: 1,
        estado: formPanel.findById("cmbEstadoId").getValue()
        //status: 1
        },
     callback: function (options, success, response) {
        if (Ext.util.JSON.decode(response.responseText).success == false) {
            Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), 'No se pudo guardar los datos');
            //alert(1);
        }else {
            //alert(2);
            //guardarDatosDialogo();
            _window.close();
                       
        }
    }                
 })
};


    window.show();
    
}