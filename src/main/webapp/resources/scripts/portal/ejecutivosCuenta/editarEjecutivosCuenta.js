// Funcion de Agregar Desglose de Polizas
function editar (record, accion) {
	//alert(record.get('cdEstado'));
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
            root: 'grupoPersonas',
            id: 'id'
            },[
           {name: 'id', type: 'string',mapping:'id'},
           {name: 'texto', type: 'string',mapping:'texto'}
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
           {name: 'id', type: 'string',mapping:'id'},
           {name: 'texto', type: 'string',mapping:'texto'}
        ])
    });


   var dsTipoRamo = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_TIPO_RAMO
            }),
            reader: new Ext.data.JsonReader({
            root: 'confAlertasAutoTipoRamo',
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
            root: 'productosComboBox',
            id: 'codigo'
            },[
           {name: 'codigo', type: 'string',mapping:'codigo'},
           {name: 'descripcion', type: 'string',mapping:'descripcion'}
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
    
 //se define la forma en que se van a leer los datos que envie la action
        var _jsonFormReader = new Ext.data.JsonReader( {
            root : 'MEjecutivosCuentaList',
            totalProperty: 'total',
            successProperty : 'success'
        },
         [ 
            {name : 'dsNombre',type : 'string',mapping : 'dsNombre'},
            {name : 'cdPerson',type : 'string',mapping : 'cdPerson'},
            {name : 'cdGrupo',type : 'string',mapping : 'cdGrupo'},
            {name : 'desGrupo',type : 'string',mapping : 'desGrupo'},
            {name : 'cdLinCta',type : 'string',mapping : 'cdLinCta'},
            {name : 'dsLinCta',type : 'string',mapping : 'dsLinCta'},
            {name : 'cdTipRam',type : 'string',mapping : 'cdTipRam'},
            {name : 'dsTipRam',type : 'string',mapping : 'dsTipRam'},
            {name : 'cdRamo',type : 'string',mapping : 'cdRamo'},
            {name : 'dsRamo',type : 'string',mapping : 'dsRamo'},
            {name : 'nomAgente',type : 'string',mapping : 'nomAgente'},
            {name : 'cdEstado',type : 'string',mapping : 'cdEstado'},
            {name : 'feInicio',type : 'string',mapping : 'feInicio'},
            {name : 'feFin',type : 'string',mapping : 'feFin'},
            {name : 'swNivelPosterior',type : 'string',mapping : 'swNivelPosterior'},
            {name : 'dsEstado',type : 'string' , mapping: 'dsEstado'},
            {name : 'cdTipage',type : 'string' , mapping: 'cdTipage'},
            {name : 'dsTipage',type : 'string' , mapping: 'dsTipage'}
          ]
        );

//se define el formulario
var formPanel = new Ext.FormPanel({
            labelWidth : 100,
            frame : true,
            url : _ACTION_GET_EJECUTIVOS_CUENTA,
        
            baseParams : {
                 cdElemento: record.get('cdElemento'),
                 cdAgente: record.get('cdAgente')
             },
          
            reader : _jsonFormReader,
            bodyStyle : 'padding:5px 5px 0',
            bodyStyle:'background: white',
            width : 380,
            //defaults : {width : 250 },
            defaultType : 'textfield',
            labelAlign:'right',
            waitMsgTarget : true,
            //se definen los campos del formulario
            items : [
                 new Ext.form.TextField( {
		    	 fieldLabel: getLabelFromMap('txtFldDsNombreEjctCntEdt', helpMap,'Cliente'), 
	    		 tooltip: getToolTipFromMap('txtFldClienteEjctCntEdt', helpMap,'Cliente'),
	    		 hasHelpIcon:getHelpIconFromMap('txtFldDsNombreEjctCntEdt',helpMap),
				 Ayuda: getHelpTextFromMap('txtFldDsNombreEjctCntEdt',helpMap),
                 name: 'dsNombre',
                 //fieldLabel: "Cliente",
                 width: 300,
                 readOnly:true,
                 disabled:true
               }),
                
                new Ext.form.Hidden( {
                 name : 'cdGrupo'
                }),
                new Ext.form.Hidden( {
                 name : 'accion',
                 value: accion
                }),
                 new Ext.form.Hidden( {
                 name : 'cdPerson'
                }),
                /*new Ext.form.Hidden( {
                 name : 'cdEstado'
                }),*/
                
                 new Ext.form.TextField( {
	    		 fieldLabel: getLabelFromMap('txtFldDesGrupoEjctCntEdt', helpMap,'Grupo de personas'), 
	    		 tooltip: getToolTipFromMap('txtFldDesGrupoEjctCntEdt', helpMap,'Grupo de personas al que pertenece'),  
	    		  hasHelpIcon:getHelpIconFromMap('txtFldDesGrupoEjctCntEdt',helpMap),
				 Ayuda: getHelpTextFromMap('txtFldDesGrupoEjctCntEdt',helpMap),
                 name : 'desGrupo',
                 //fieldLabel: "Grupo de personas",
                 width: 300,
                 readOnly:true,
                 disabled:true
                }),
                
                 new Ext.form.Hidden( {
                 name : 'cdLinCta'
                }),
                
                 new Ext.form.TextField( {
	    		 fieldLabel: getLabelFromMap('txtFldDsLinCtaEjctCntEdt', helpMap,'L&iacute;nea de Atenci&oacute;n'), 
	    		 tooltip: getToolTipFromMap('txtFldDsLinCtaEjctCntEdt', helpMap,'L&iacute;nea de Atenci&oacute;n'),
	    		  hasHelpIcon:getHelpIconFromMap('txtFldDsLinCtaEjctCntEdt',helpMap),
				 Ayuda: getHelpTextFromMap('txtFldDsLinCtaEjctCntEdt',helpMap),
                 name : 'dsLinCta',
                 //fieldLabel: "Linea de Atenci&oacute;n",
                 width: 300,
                 readOnly:true,
                 disabled:true
                }),
                
                new Ext.form.Hidden( {
                 name : 'cdTipRam'
                 }),
                
                 new Ext.form.TextField( {
	    		 fieldLabel: getLabelFromMap('txtFldDsTipRamEjctCntEdt', helpMap,'Ramo'), 
	    		 tooltip: getToolTipFromMap('txtFldDsTipRamEjctCntEdt', helpMap,'Ramo'),
	    		   hasHelpIcon:getHelpIconFromMap('txtFldDsTipRamEjctCntEdt',helpMap),
				 Ayuda: getHelpTextFromMap('txtFldDsTipRamEjctCntEdt',helpMap),
                 name : 'dsTipRam',
                 //fieldLabel: "Ramo",
                 width: 300,
                 readOnly:true,
                 disabled:true
                 }),
                
                new Ext.form.Hidden( {
                 name : 'cdRamo'
                }),
                
                new Ext.form.TextField( {
	    		 fieldLabel: getLabelFromMap('txtFldDsRamoEjctCntEdt', helpMap,'Producto'), 
	    		 tooltip: getToolTipFromMap('txtFldDsRamoEjctCntEdt', helpMap,'Producto'),    
	    		    hasHelpIcon:getHelpIconFromMap('txtFldDsRamoEjctCntEdt',helpMap),
				 Ayuda: getHelpTextFromMap('txtFldDsRamoEjctCntEdt',helpMap),
                 name : 'dsRamo',
                 //fieldLabel: "Producto",
                 width: 300,
                 readOnly:true,
                 disabled:true
                }),
                 new Ext.form.TextField( {
	    		 fieldLabel: getLabelFromMap('txtFldNomAgenteEjctCntEdt', helpMap,'Ejecutivo'), 
	    		 tooltip: getToolTipFromMap('txtFldNomAgenteEjctCntEdt', helpMap,'Ejecutivo'), 
	    		     hasHelpIcon:getHelpIconFromMap('txtFldNomAgenteEjctCntEdt',helpMap),
				 Ayuda: getHelpTextFromMap('txtFldNomAgenteEjctCntEdt',helpMap),
                 name : 'nomAgente',
                 //fieldLabel: "Ejecutivo",
                 width: 300,
                 readOnly:true,
                 disabled:true
                }),{
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
                },
                {
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
                    triggerAction: 'all',
                    //fieldLabel: "Estado",
                    forceSelection: true,
                    width: 200,
                    emptyText:'Seleccione Estado Ejecutivo ...',
                    selectOnFocus:true,
                    //labelSeparator:'',
                    id:'cdEstadoId',
                    onSelect: function (record) {
                                  //Estado Inactivo 
                                    if (record.get('id')=="0"){
                                       if (formPanel.findById("feFin").getValue() == "") 
                                            formPanel.findById("feFin").setValue(new Date()); 
                                            
                                   }else{
                                           if(formPanel.findById("feInicioId").getValue() == ""){
                                           		formPanel.findById("feInicioId").setValue(new Date());
                                           }
                                           formPanel.findById("feFin").setRawValue(); 
                                           
                                   }
                                   this.collapse();
                                   this.setValue(record.get('id'));
                   }
                },
	                new Ext.form.DateField({
					fieldLabel: getLabelFromMap('feInicioId', helpMap,'Inicio'), 
					tooltip: getToolTipFromMap('feInicioId', helpMap,'Inicio'), 
					hasHelpIcon:getHelpIconFromMap('feInicioId',helpMap),
				 	Ayuda: getHelpTextFromMap('feInicioId',helpMap),
	                //fieldLabel:"Inicio",
	                allowBlank: false,
	                name:'feInicio',
	                id:'feInicioId',
	               // format:'d/m/Y',
	                width: 100,
	                altFormats : "m/d/Y|m-d-y|m-d-Y|m/d|m-d|md|mdy|mdY|d|Y-m-d|Y-m-d H:i:s.g"	                
	                }),
                {
	                xtype:'datefield',
					fieldLabel: getLabelFromMap('feFin', helpMap,'T&eacute;rmino'), 
					tooltip: getToolTipFromMap('feFin', helpMap,'T&eacute;rmino'),
					hasHelpIcon:getHelpIconFromMap('feFin',helpMap),
				 	Ayuda: getHelpTextFromMap('feFin',helpMap),
	                //fieldLabel:"Término",
	                //allowBlank: false,
	               // format:'d/m/Y',
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
					//fieldLabel:"¿Accesa niveles inferiores?",
	                id:'swNivelPosterior'
                }
            ]});

//Windows donde se van a visualizar la pantalla
var window = new Ext.Window({
	    id:'WindEditEjeId',
	   	title: getLabelFromMap('WindEditEjeId', helpMap,'Editar Ejecutivos por Cuenta'),
	   	
        width: 540,
        height:450,
        layout: 'fit',
        plain:true,
        modal: true,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        items: formPanel,
        //se definen los botones del formulario
            buttons : [ {
				text:getLabelFromMap('edtEjctCuentaButtonGuar', helpMap,'Guardar'),
				tooltip:getToolTipFromMap('edtEjctCuentaButtonGuar', helpMap,'Guarda Ejecutivo por Cuenta'),
                disabled : false,
                handler : function() {
                    if (formPanel.form.isValid()) {
                        formPanel.form.submit( {
                            //action a invocar cuando el formulario haga submit
                            url : _ACTION_INSERTAR_GUARDAR_EJECUTIVOS_CUENTA,
                            //funcion a ejecutar cuando la llamada a la action retorna satisfactoriamente
                            success : function(from, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'), action.result.actionMessages[0], function(){reloadGrid()});
                                window.close();
                                },
                            //funcion a ejecutar cuando la llamada a la action no se ejecuta satisfactoriamente
                            failure : function(form, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400010', helpMap,'Error'),'Problemas al Guardar: ' + action.result.errorMessages[0]);
                                
                                },
                            //mensaje a mostrar mientras se guardan los datos
                            waitMsg : getLabelFromMap('400022', helpMap,'Guardando Actualizaci&oacute;n de datos...')
                        });
                     } else {
						     Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
                          }
                }
            },
             {
				text:getLabelFromMap('edtEjctCuentaButtonRegre', helpMap,'Cancelar'),
				tooltip:getToolTipFromMap('edtEjctCuentaButtonRegre', helpMap,'Regresa a la pantalla anterior'),
                handler : function() {
                window.close();
                    }
            }]
    	});


/*
dsEstadoEjecutivos.load({
			param:{cdIdioma:'1',cdRegion:'1'},   
   			callback: function(){
   					dsGruposPersona.load({
   							callback: function(){
   									dsLineasAtencion.load({
   											callback: function(){
   													 dsTipoRamo.load({
   													 		callback: function(){
   													 				dsProductos.load({
   													 							callback: function(){
   													 									dsEjecutivos.load({
   													 										callback: function(){
   													 											dsClientesCorporativo.load();
   													 															}
   													 													});
   													 												}
   													 								});			
   													 							}
   													 				});
   																}
   														});							
   												}
   										});
   								}
   						});
   */

	dsEstadoEjecutivos.load({
				param:{cdIdioma:'1',cdRegion:'1'},
				callback: function(){
						formPanel.form.load({
									//waitMsg : getLabelFromMap('400028', helpMap,'Leyendo datos ...'),
									success: function(){
										formPanel.findById("cdEstadoId").setValue(record.get('cdEstado'));
				    			    }
						});
				}
	});
   
	
  //se carga el formulario con los datos de la estructura a editar
 /*     formPanel.form.load({
                waitMsg : getLabelFromMap('400028', helpMap,'Leyendo datos ...'),
                success:function(){
                	
                	formPanel.findById("cdEstadoId").setValue(record.get('cdEstado'));
                }
        });
        */
  
   //var fecha = new String;
   //fecha = '2008-05-14';
   //fecha = Ext.util.Format.substr(fecha, 0, 10);
   //Ext.MessageBox.alert('aviso','fecha ' + fecha);
   //formPanel.findById("feInicio").setValue(fecha);
   
   window.show();

};

