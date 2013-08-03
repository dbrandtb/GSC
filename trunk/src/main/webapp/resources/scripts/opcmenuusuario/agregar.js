/********************* GUARDAR ***************/
agregar = function(store) {
	
    /***Store que carga el combo de productos***/
    
    var dataStoreProductoCliente = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: 'productoClienteAction.action'
            }),
            reader: new Ext.data.JsonReader({
                root: 'productoCliente',
                id: 'prod'
            },[
                {name: 'cdTituloCliente',  type: 'string',  mapping:'cdTituloCliente'},
                {name: 'dsTituloCliente',  type: 'string',  mapping:'dsTituloCliente'}  
            ]),
            remoteSort: true
        });
    dataStoreProductoCliente.setDefaultSort('prod', 'desc');
    dataStoreProductoCliente.load();
    
    var dataStoreNivelMenu = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: 'nivelMenuAction.action'
            }),
            reader: new Ext.data.JsonReader({
                root: 'nivelMenu',
                id: 'niv'
            },[
                {name: 'cdNivelPadre',  type: 'string',  mapping:'cdNivelPadre'},
                {name: 'dsMenuEst',  type: 'string',  mapping:'dsMenuEst'}  
            ]),
            remoteSort: true
        });
    dataStoreNivelMenu.setDefaultSort('niv', 'desc');
    dataStoreNivelMenu.load();
    
    var dataStoreEstado = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url:'estadoAction.action'
        }),
        reader: new Ext.data.JsonReader({
            root:'estados',
            id:'edo'
        },[
            {name:'clave',  type:'string',  mapping:'clave'},
            {name:'valor',  type:'string',  mapping:'valor'}
        ]),
        remoteSort:true
    });    
    dataStoreEstado.setDefaultSort('edo','desc');
    dataStoreEstado.load();
    
    var dataStoreOpciones = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url:'opcionesAction.action'
        }),
        reader: new Ext.data.JsonReader({
            root:'opciones',
            id:'opc'
        },[
            {name: 'cdTitulo',  type: 'string',  mapping:'cdTitulo'},
            {name: 'dsTitulo',  type: 'string',  mapping:'dsTitulo'}  
        ]),
        remoteSort:true
    });    
    dataStoreOpciones.setDefaultSort('opc','desc');
    dataStoreOpciones.load();
        
    var dsMenu = new Ext.form.TextField({
                fieldLabel:'Nombre',
                disabled : true,labelSeparator:':',
                name:'dsMenu',
                value:_MENU_TITULO,
                width: 300
        });
    var clienteCombo = new Ext.form.TextField({
                fieldLabel:'Nivel',//la etiqueta Cliente ahora es Nivel
                disabled : true,labelSeparator:':',
                name:'dsElemento',
                value:_MENU_CLIENTE,
                width: 300
        });
    var usuarioCombo = new Ext.form.TextField({
                fieldLabel:'Usuario',
                disabled : true,labelSeparator:':',
                name:'dsUsuario',
                value:_MENU_USUARIO,
                width: 300
        });
    var cdRol = new Ext.form.TextField({
                fieldLabel:'Rol',
                disabled : true,labelSeparator:':',
                name:'cdRol',
                value:_MENU_ROL,
                width: 300
        });
    var productoClienteCombo = new Ext.form.ComboBox({    				 
                tpl: '<tpl for="."><div ext:qtip="{dsTituloCliente}. {cdTituloCliente}" class="x-combo-list-item">{dsTituloCliente}</div></tpl>',
                store: dataStoreProductoCliente,
                width: 300,
                mode: 'local',
                name: 'dsTituloCliente',
                typeAhead: true,
                labelSeparator:':',
                triggerAction: 'all',           
                displayField:'dsTituloCliente',
                valueField:'cdTituloCliente',
                //forceSelection: true,
                fieldLabel: "Producto",// cambio de etiqueta de Ramo a Producto
                emptyText:'Seleccionar un Producto...',
                selectOnFocus:true
        });  
        
    ////////////////////////////////////// combo anidado
    var storeTipoSituacion = new Ext.data.Store({
           proxy: new Ext.data.HttpProxy({
               url: 'obtenerTipoSituacion.action'
           
          }),
            reader: new Ext.data.JsonReader({
            root: 'tipoSituacionList',
            id: 'clave'
            },[
           {name: 'clave', type: 'string',mapping:'clave'},
           {name: 'valor', type: 'string',mapping:'valor'}    
            ]),
            remoteSort: true,
            baseParams: {cdTituloCliente: productoClienteCombo.getValue()}
           
        });
      
        storeTipoSituacion.setDefaultSort('clave', 'desc');
        storeTipoSituacion.load();

  
 var comboTipoSituacion = new Ext.form.ComboBox({
     tpl: '<tpl for="."><div ext:qtip="{valor}. {clave}" class="x-combo-list-item">{valor}</div></tpl>',
     name: "dsTipoSituacion",
     id: "dsTipoSituacion",
     fieldLabel: "Riesgo",
     store: storeTipoSituacion,
     displayField:'valor',
     valueField:'clave',
     typeAhead: true,
     listWidth : 300,
     width: 300,
     mode: 'local',
     triggerAction: 'all',
     emptyText:'Seleccionar un Riesgo...',//cambio de etiqueta de Tipo de Situacion a Riesgo
     selectOnFocus:true,
     //editable:false,
     labelSeparator:':'
  });
  
  productoClienteCombo.on('select', function(){
    
    storeTipoSituacion.removeAll();
  	comboTipoSituacion.emptyText='Seleccionar un Riesgo...';
    comboTipoSituacion.reset();
    storeTipoSituacion.baseParams['cdTituloCliente'] = productoClienteCombo.getValue();
    storeTipoSituacion.load({
                  callback : function(r, options, success) {
                      if (!success) {
                         Ext.MessageBox.alert('Aviso','No se encontraron registros para el Producto seleccionado');  
                         storeTipoSituacion.removeAll();
                      }
                  }

              }
            );
   });
   /////////////////////////////////////////////////////
    var nivelMenuCombo = new Ext.form.ComboBox({
                tpl: '<tpl for="."><div ext:qtip="{dsMenuEst}. {cdNivelPadre}" class="x-combo-list-item">{dsMenuEst}</div></tpl>',
                store: dataStoreNivelMenu,
                width: 300,
                mode: 'local',
                name: 'dsMenuEst',
                typeAhead: true,
                labelSeparator:':',          
                triggerAction: 'all',           
                displayField:'dsMenuEst',                
                fieldLabel: "Opción Padre",
                emptyText:'Seleccionar una Opción...',
                selectOnFocus:true,
                selectFirst : function() {
                                this.focusAndSelect(this.store.data.first());
                            },
                            focusAndSelect : function(record) {
                                var index = typeof record === 'number' ? record : this.store.indexOf(record);
                                this.select(index, this.isExpanded());
                                this.onSelect(this.store.getAt(record), index, this.isExpanded());
                            },
                            onSelect : function(record, index, skipCollapse){
                                if(this.fireEvent('beforeselect', this, record, index) !== false){
                                    this.setValue(record.data[this.valueField || this.displayField]);
                                    if( !skipCollapse ) {
                                        this.collapse();
                                    }
                                    this.lastSelectedIndex = index + 1;
                                    this.fireEvent('select', this, record, index);
                                }
                                var valor=record.get('key');
                                var mStore = gridConf.store;
                                mStore.baseParams = mStore.baseParams || {};
                                mStore.baseParams['nivelMenuCombo'] = valor;
                                mStore.reload({
                                      callback : function(r,options,success) {
                                            if (!success) {
                                                 Ext.MessageBox.alert('No se encontraron registros');
                                                 mStore.removeAll();
                                              }
                                      }

                                  });                                
                            }
        });     
    var opcionMenu = new Ext.form.TextField({
                fieldLabel:'T&iacute;tulo del submen&uacute;',
                allowBlank:false,labelSeparator:':',
                name:'opcionMenu',
                width: 300
        }); 
    var estadoCombo = new Ext.form.ComboBox({ 
                tpl: '<tpl for="."><div ext:qtip="{valor}. {clave}" class="x-combo-list-item">{valor}</div></tpl>',
                store: dataStoreEstado,
                width: 300,
                mode: 'local',
                name: 'dsEstado',
                typeAhead: true,
                labelSeparator:':',          
                triggerAction: 'all',           
                displayField:'valor',
                forceSelection: true,
                fieldLabel: 'Estado',
                emptyText:'Seleccionar estado...',
                selectOnFocus:true
        });
    var opcionesCombo = new Ext.form.ComboBox({
                tpl: '<tpl for="."><div ext:qtip="{dsTitulo}. {cdTitulo}" class="x-combo-list-item">{dsTitulo}</div></tpl>',
                store: dataStoreOpciones,
                width: 300,
                mode: 'local',
                name: 'dsTitulo',
                typeAhead: true,
                labelSeparator:':',          
                triggerAction: 'all',           
                displayField:'dsTitulo',
                forceSelection: true,
                fieldLabel: "Opci&oacute;n del Men&uacute;",// cambio de Pantalla Asociada a Opción del Menú
                emptyText:'Seleccionar una Opcion...',
                selectOnFocus:true
        });   
    var agregarOpcionForm = new Ext.form.FormPanel({
                url:'guardarOpcMenuAction.action',
                boder:false,
                frame:true,
                method:'post',                          
                labelAlign: 'right',
                width: 470,
                buttonAlign: "center",
                baseCls:'x-plain',
                labelWidth:75,
                
                items:[
                    dsMenu,
                    clienteCombo,
                    cdRol,
                    usuarioCombo,
                    opcionMenu,
                    productoClienteCombo,
                    comboTipoSituacion,
                    nivelMenuCombo,
                    opcionesCombo,
                    estadoCombo
                ]
            });
    var windowGuardar = new Ext.Window({
        title: 'Configurar Opciones del Menú',
        width: 500,
        height:400,
        minWidth: 480,
        minHeight: 380,
        layout: 'fit',
        plain:true,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        items: agregarOpcionForm,
        buttons: [{
            text: 'Guardar', 
            handler: function() {
                if (agregarOpcionForm.form.isValid()) {
                    //alert(fileLoad.getValue());
                    agregarOpcionForm.form.submit({
                        url:'guardarOpcMenuAction.action',                 
                        waitMsg:'Procesando...',
                        failure: function(form, action) {
			            	var mensajeRes = Ext.util.JSON.decode(action.response.responseText).mensajeRespuesta;
						    Ext.MessageBox.alert('Error',mensajeRes);
                            windowGuardar.close();
                            dataStoreNivelMenu.load();
                            store.load();
                        },
                        success: function(form, action) {
			            	var mensajeRes = Ext.util.JSON.decode(action.response.responseText).mensajeRespuesta;
						    Ext.MessageBox.alert('Aviso',mensajeRes);
                            windowGuardar.close();
                            dataStoreNivelMenu.load();
                            store.load();
                        }
                    });                   
                } else{
                    Ext.MessageBox.alert('Informacion incompleta', 'Por favor verifique');
                }             
            }
        },{
            text: 'Cancelar',
            handler: function(){windowGuardar.close();}
        }]
    });
    /*
    storeTipoSituacion.load();
    dataStoreProductoCliente.load();
    dataStoreNivelMenu.load();
    dataStoreOpciones.load();
    dataStoreEstado.load();*/
    windowGuardar.show();
    };
/************** TERMINA GUARDAR **************/