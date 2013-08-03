// Funcion de Agregar Desglose de Polizas
function agregar(accion) {
    var dsClientesCorporativo = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_CLIENTES_CORPORATIVO
            }),
            reader: new Ext.data.JsonReader({
            root: 'clientesCorp'
            },[
           {name: 'cdElemento', type: 'string',mapping:'cdElemento'},
           {name: 'cdPerson', type: 'string',mapping:'cdPerson'},
           {name: 'dsElemen', type: 'string',mapping:'dsElemen'}
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
    
    
   var dsLineasAtencion = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_LINEAS_ATENCION
            }),
            reader: new Ext.data.JsonReader({
            root: 'lineasAtencion',
            id: 'id'
            },[
           {name: 'id', type: 'string',mapping:'codigo'},
           {name: 'texto', type: 'string',mapping:'descripcion'}
        ])
    });


   var dsTipoRamo = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_TIPO_RAMO
            }),
            reader: new Ext.data.JsonReader({
            root: 'tiposRamoClienteEjecCuenta',
            id: 'cdTipRam'
            },[
           {name: 'cdTipRam', type: 'string',mapping:'cdTipRam'},
           {name: 'dsTipRam', type: 'string',mapping:'dsTipRam'}
        ])
    });
    
     var dsProductos = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_PRODUCTOS
            }),
            reader: new Ext.data.JsonReader({
            root: 'productosEjecutivosCuenta',
            id: 'cdRamo'
            },[
           {name: 'cdRamo', type: 'string',mapping:'cdRamo'},
           {name: 'dsRamo', type: 'string',mapping:'dsRamo'}
        ])
    });
    
    var dsEjecutivos = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_EJECUTIVOS
            }),
            reader: new Ext.data.JsonReader({
            root: 'ejecutivosAon',
            id: 'cdAgente'
            },[
           {name: 'cdAgente', type: 'string',mapping:'cdAgente'},
           {name: 'nomAgente', type: 'string',mapping:'nomAgente'}
        ])
    });
    
	var dsTipoEjecutivo = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: _ACTION_OBTENER_TIPO_EJECUTIVOS
		}),
		reader: new Ext.data.JsonReader({
			root: 'tipoEjecutivosAon',
			id: 'cdTipage'
		},[
			{name: 'cdTipage', type: 'string', mapping:'cdTipage'},
			{name: 'dsTipage', type: 'string', mapping:'dsTipage'}
		])
	});
	dsTipoEjecutivo.load();
    
      var dsEstadoEjecutivos = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_ESTADO_EJECUTIVOS
            }),
            reader: new Ext.data.JsonReader({
            root: 'estadosEjecutivo',
            id: 'id'
            },[ 
           {name: 'id', type: 'string',mapping:'codigo'},
           {name: 'texto', type: 'string',mapping:'descripcion'}
        ])
    });
    

//se define el formulario
var formPanel = new Ext.FormPanel({
            frame : true,
            labelWidth : 100,
            bodyStyle : 'padding:0px 5px 0px 0px',
            bodyStyle:'background: white',
            width : 380,
            waitMsgTarget : true,
            //defaults : {width : 250 },
            defaultType : 'textfield',
            labelAlign:'right',
            //bodyStyle: 'align:left',
            //se definen los campos del formulario
            items : [
                     new Ext.form.Hidden({
                    name : 'cdElemento',
                    id : 'cdElementoId'
                    })
                    ,
                	new Ext.form.Hidden( {
                 	name : 'accion',
                 	value: accion
                	}),
                    {
                	xtype: 'combo',
                    labelWidth: 50,
                    tpl: '<tpl for="."><div ext:qtip="{cdPerson}.{cdElemento}.{dsElemen}" class="x-combo-list-item">{dsElemen}</div></tpl>',
                    store: dsClientesCorporativo,                    
                   	fieldLabel: getLabelFromMap('cdPersonId',helpMap,'Cliente'),
                   	tooltip: getToolTipFromMap('cdPersonId',helpMap,'Cliente'),	
                   	hasHelpIcon:getHelpIconFromMap('cdPersonId',helpMap),
					Ayuda: getHelpTextFromMap('cdPersonId',helpMap),
                    displayField:'dsElemen',
                    valueField: 'cdElemento',
                    hiddenName: 'cdPerson',
                    typeAhead: true,
                    mode: 'local',
                    allowBlank:false,
                    triggerAction: 'all',
                    //fieldLabel: "Cliente",
                    forceSelection: true,
                    width: 300,
                    emptyText:'Seleccione Cliente ...',
                    selectOnFocus:true,                               
                    id:'cdPersonId',                    
                    onSelect: function (record) {
                                   this.setValue(record.get("cdElemento"));
                                   Ext.getCmp("cdGrupoId").setValue('');
                                   formPanel.findById("cdElementoId").setValue(record.get("cdElemento"));
                                   dsGruposPersona.removeAll();
                                   dsGruposPersona.load({
                                           params: {
                                                     cdElemento: record.get("cdElemento")
                                                   }
                                   });
                                   Ext.getCmp("cdRamoId").setValue('');
                                   dsProductos.removeAll();
                                   dsProductos.load({
                                           params: {
                                                     cdElemento: record.get("cdElemento"),
                                                     cdTipRam: formPanel.findById("cdTipRamId").getValue()
                                                   }
                                   });
                                    Ext.getCmp("cdTipRamId").setValue('');
                                   dsTipoRamo.removeAll();
                                   dsTipoRamo.load({
                                           params: {
                                                     cdElemento: record.get("cdElemento")
                                                   }
                                   });
                                   this.collapse();
                                 }
                },{
                
                	xtype: 'combo',
                    labelWidth: 50,
                    tpl: '<tpl for="."><div ext:qtip="{codigo}.{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
                    store: dsGruposPersona,
	       			fieldLabel: getLabelFromMap('cdGrupoId',helpMap,'Grupo de Personas'),
                   	tooltip: getToolTipFromMap('cdGrupoId',helpMap,'Grupo de Personas al que pertenece'),			
                   	 hasHelpIcon:getHelpIconFromMap('cdGrupoId',helpMap),
					Ayuda: getHelpTextFromMap('cdGrupoId',helpMap),
                    displayField:'descripcion',
                    valueField: 'codigo',
                    hiddenName: 'cdGrupo',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    //fieldLabel: "Grupo de Personas",
                    forceSelection: true,
                    width: 300,
                    emptyText:'Seleccione Grupo de Personas ...',
                    selectOnFocus:true,
                    id:'cdGrupoId'
                },{
                    xtype: 'combo',
                    labelWidth: 50,
                    tpl: '<tpl for="."><div ext:qtip="{id}.{id}.{texto}" class="x-combo-list-item">{texto}</div></tpl>',
                    store: dsLineasAtencion,
	       			fieldLabel: getLabelFromMap('cdLinCtaId',helpMap,'L&iacute;neas de Atenci&oacute;n'),
                   	tooltip: getToolTipFromMap('cdLinCtaId',helpMap,'L&iacute;neas de Atenci&oacute;n'),	
                    hasHelpIcon:getHelpIconFromMap('cdLinCtaId',helpMap),
					Ayuda: getHelpTextFromMap('cdLinCtaId',helpMap),
                    displayField:'texto',
                    valueField: 'id',
                    hiddenName: 'cdLinCta',
                    typeAhead: true,
                    mode: 'local',
                    allowBlank:false,
                    triggerAction: 'all',
                    //fieldLabel: "Lineas de Atención",
                    forceSelection: true,
                    width: 300,
                    emptyText:'Seleccione Linea de Atencion ...',
                    selectOnFocus:true,
                    id:'cdLinCtaId'
                },{
                    xtype: 'combo',
                    tpl: '<tpl for="."><div ext:qtip="{cdTipRam}. {dsTipRam}" class="x-combo-list-item">{dsTipRam}</div></tpl>',
                    store: dsTipoRamo,
	       			fieldLabel: getLabelFromMap('cdTipRamId',helpMap,'Ramo'),
                   	tooltip: getToolTipFromMap('cdTipRamId',helpMap,'Ramo'),
                    hasHelpIcon:getHelpIconFromMap('cdTipRamId',helpMap),
					Ayuda: getHelpTextFromMap('cdTipRamId',helpMap),
                    displayField:'dsTipRam',
                    valueField:'cdTipRam',
                    hiddenName: 'cdTipRam',
                    typeAhead: true,
                    mode: 'local',
                    allowBlank:false,
                    triggerAction: 'all',
                    fieldLabel: "Ramo",
                    forceSelection: true,
                    width: 300,
                    emptyText:'Seleccione Ramo...',
                    selectOnFocus:true,
                    id:'cdTipRamId'
                },{
                    xtype: 'combo',
                    tpl: '<tpl for="."><div ext:qtip="{cdRamo}. {dsRamo}" class="x-combo-list-item">{dsRamo}</div></tpl>',
                    store: dsProductos,
	       			fieldLabel: getLabelFromMap('cdRamoId',helpMap,'Producto'),
                   	tooltip: getToolTipFromMap('cdRamoId',helpMap,'Producto'),			
                    hasHelpIcon:getHelpIconFromMap('cdRamoId',helpMap),
					Ayuda: getHelpTextFromMap('cdRamoId',helpMap),
                    displayField:'dsRamo',
                    valueField:'cdRamo',
                    hiddenName: 'cdRamo',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    //fieldLabel: "Producto",
                    forceSelection: true,
                    width: 300,
                    emptyText:'Seleccione Producto ...',
                    selectOnFocus:true,
                    id:'cdRamoId'
                },{
                    xtype: 'combo',
                    tpl: '<tpl for="."><div ext:qtip="{cdAgente}. {nomAgente}" class="x-combo-list-item">{nomAgente}</div></tpl>',
                    store: dsEjecutivos,
	       			fieldLabel: getLabelFromMap('cdAgenteId',helpMap,'Ejecutivo'),
                   	tooltip: getToolTipFromMap('cdAgenteId',helpMap,'Ejecutivo'),
                   	hasHelpIcon:getHelpIconFromMap('cdAgenteId',helpMap),
					Ayuda: getHelpTextFromMap('cdAgenteId',helpMap),
                    displayField:'nomAgente',
                    valueField:'cdAgente',
                    hiddenName: 'cdAgente',
                    typeAhead: true,
                    mode: 'local',
                    allowBlank:false,
                    triggerAction: 'all',
                    //fieldLabel: "Ejecutivo",
                    forceSelection: true,
                    width: 300,
                    emptyText:'Seleccione Ejecutivo ...',
                    selectOnFocus:true,
                    id:'cdAgenteId'
                },{
                    xtype: 'combo',
                    tpl: '<tpl for="."><div ext:qtip="{cdTipage}. {dsTipage}" class="x-combo-list-item">{dsTipage}</div></tpl>',
                    store: dsTipoEjecutivo,
	       			fieldLabel: getLabelFromMap('cdTipageId',helpMap,'Tipo de Ejecutivo'),
                   	tooltip: getToolTipFromMap('cdTipageId',helpMap,'Tipo de Ejecutivo'),
                   	hasHelpIcon:getHelpIconFromMap('cdTipageId',helpMap),
					Ayuda: getHelpTextFromMap('cdTipageId',helpMap),
                    displayField:'dsTipage',
                    valueField:'cdTipage',
                    hiddenName: 'cdTipage',
                    typeAhead: true,
                    mode: 'local',
                    allowBlank: false,
                    triggerAction: 'all',
                    forceSelection: true,
                    width: 250,
                    emptyText:'Seleccione Tipo de Ejecutivo ...',
                    selectOnFocus: true,
                    id: 'cdTipageId'
                },{
                	xtype: 'combo',
                    tpl: '<tpl for="."><div ext:qtip="{id}. {texto}" class="x-combo-list-item">{texto}</div></tpl>',
                    store: dsEstadoEjecutivos,
	       			fieldLabel: getLabelFromMap('cdEstadoId',helpMap,'Estado'),
                   	tooltip: getToolTipFromMap('cdEstadoId',helpMap,'Estado'),	
                   	hasHelpIcon:getHelpIconFromMap('cdEstadoId',helpMap),
					Ayuda: getHelpTextFromMap('cdEstadoId',helpMap),
                    displayField:'texto',
                    valueField:'id',
                    hiddenName: 'cdEstado',
                    typeAhead: true,
                    mode: 'local',
                    allowBlank:false,
                    triggerAction: 'all',
                    //fieldLabel: "Estado",
                    forceSelection: true,
                    width: 200,
                    emptyText:'Seleccione Estado Ejecutivo ...',
                    selectOnFocus:true,
                    id:'cdEstadoId',
                    onSelect: function (record) {
                                  //Estado Inactivo 
                                    if (record.get('id')=="0"){
                                       if (formPanel.findById("feFin").getValue() == "") 
                                            formPanel.findById("feFin").setValue(new Date()); 
                                   }else{
                                           if(formPanel.findById("feInicio").getValue() == ""){
                                           		formPanel.findById("feInicio").setValue(new Date());
                                           }
                                           formPanel.findById("feFin").setRawValue(); 
                                   }
                                   this.collapse();
                                   this.setValue(record.get('id'));
                   }
                },{
	                xtype:'datefield',
					fieldLabel: getLabelFromMap('feInicio', helpMap,'Inicio'), 
					tooltip: getToolTipFromMap('feInicio', helpMap,'Inicio'),  
					hasHelpIcon:getHelpIconFromMap('feInicio',helpMap),
					Ayuda: getHelpTextFromMap('feInicio',helpMap),
	                //fieldLabel:"Inicio",
	             //   format:'d/m/Y',
					altFormats : "m/d/Y|m-d-y|m-d-Y|m/d|m-d|md|mdy|mdY|d|Y-m-d|Y-m-d H:i:s.g",
	                allowBlank: false,
	                value:new Date(),
	                width: 100,
	                id:'feInicio'
                },{
	                xtype:'datefield',
					fieldLabel: getLabelFromMap('feFin', helpMap,'T&eacute;rmino'), 
					tooltip: getToolTipFromMap('feFin', helpMap,'T&eacute;rmino'),  
					hasHelpIcon:getHelpIconFromMap('feFin',helpMap),
					Ayuda: getHelpTextFromMap('feFin',helpMap),
	                //fieldLabel:"Término",
	                //allowBlank: false,
	              //  format:'d/m/Y',
					altFormats : "m/d/Y|m-d-y|m-d-Y|m/d|m-d|md|mdy|mdY|d|Y-m-d|Y-m-d H:i:s.g",
					 width: 100,
	                id:'feFin'
                },{
                	xtype:'checkbox',
					fieldLabel: getLabelFromMap('swNivelPosterior', helpMap,'¿Accesa niveles inferiores?'), 
					tooltip: getToolTipFromMap('swNivelPosterior', helpMap,'Niveles Inferiores'), 
					hasHelpIcon:getHelpIconFromMap('swNivelPosterior',helpMap),
					Ayuda: getHelpTextFromMap('swNivelPosterior',helpMap),
					 width: 25,
					id:'swNivelPosterior'
                }
          ]});


//Windows donde se van a visualizar la pantalla
var window = new Ext.Window({
	    id:'WindAgreEjeId',
	   	title: getLabelFromMap('WindAgreEjeId', helpMap,'Agregar Ejecutivos por Cuenta'),
        width: 540,
        height:450,
        layout: 'fit',
        modal: true,
        plain:true,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        items: formPanel,
        //se definen los botones del formulario
            buttons : [ {
				text:getLabelFromMap('agrEjctCuentaButtonGuardar', helpMap,'Guardar'),
				tooltip:getToolTipFromMap('agrEjctCuentaButtonBuscar', helpMap,'Guarda Nuevo Ejecutivo por Cuenta'),
                //text : 'Guardar',
                disabled : false,
                handler : function() {
                    if (formPanel.form.isValid()) {
                        formPanel.form.submit( {
                            //action a invocar cuando el formulario haga submit
                            url : _ACTION_INSERTAR_GUARDAR_EJECUTIVOS_CUENTA,
                            //funcion a ejecutar cuando la llamada a la action retorna satisfactoriamente
                            success : function(from, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'), action.result.actionMessages[0], function(){reloadGrid();});
                                window.close();
                                },
                            //funcion a ejecutar cuando la llamada a la action no se ejecuta satisfactoriamente
                            failure : function(form, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400010', helpMap,'Error'),'Problemas al Guardar: ' + action.result.errorMessages[0]);
                                },
                            //mensaje a mostrar mientras se guardan los datos
                            waitMsg : getLabelFromMap('400027', helpMap,'Guardando datos...')
                        });
                     } else {
                           Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
                          }
                }
            },
             {
				text:getLabelFromMap('agrEjctCuentaButtonCancelar', helpMap,'Cancelar'),
				tooltip:getToolTipFromMap('agrEjctCuentaButtonCancelar', helpMap,'Regresa a la pantalla anterior'),
                handler : function() {
                window.close();
                    }
            }]
    	});



   dsClientesCorporativo.load();
   dsLineasAtencion.load();
   dsEjecutivos.load();
   dsEstadoEjecutivos.load({
   			callback: function (r, o, success) {
        			if (success) {
						formPanel.findById("cdEstadoId").setValue('1');
						//comboMetodoEnvio.setValue(cdMet_Env);
        			}
        	}
    });
   
   
   window.show();

};

