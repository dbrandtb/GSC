var gridConf;
Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";
function menuusuario(){
    url='opcMenuUsuariosJson.action';
    store= new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: url,
            method: 'POST'  
        }),
        reader: new Ext.data.JsonReader({
            root:'opcionMenuList',
            totalProperty:'totalCount',
            id:'cdMenu, cdNivel'
        },[
        {name:'cdMenu',        type:'string',      mapping:'cdMenu'},
        {name:'dsMenu',        type:'string',      mapping:'dsMenu'},
        {name:'cdElemento',    type:'string',      mapping:'cdElemento'},
        {name:'dsElemento',    type:'string',      mapping:'dsElemento'},
        {name:'opcionMenu',    type:'string',      mapping:'opcionMenu'},
        {name:'cdRol',         type:'string',      mapping:'cdRol'},
        {name:'dsUsuario',     type:'string',      mapping:'dsUsuario'},
        {name:'cdNivel',       type:'string',      mapping:'cdNivel'},
        {name:'cdNivelPadre',  type:'string',      mapping:'cdNivelPadre'},
        {name:'dsMenuEst',     type:'string',      mapping:'dsMenuEst'},
        {name:'cdRamo',        type:'string',      mapping:'cdRamo'},
        {name:'dsRamo',        type:'string',      mapping:'dsRamo'},
        {name:'cdTipsit',      type:'string',      mapping:'cdTipsit'},
        {name:'dsTipsit',      type:'string',      mapping:'dsTipsit'},
        {name:'cdTitulo',      type:'string',      mapping:'cdTitulo'},
        {name:'dsTitulo',      type:'string',      mapping:'dsTitulo'},
        {name:'dsTituloCliente',      type:'string',      mapping:'dsTituloCliente'},
        {name:'cdEstado',      type:'string',      mapping:'cdEstado'},
        {name:'dsEstado',      type:'string',      mapping:'dsEstado'}
                
        ]),
        remoteSort:false
        });
        store.params = {start: 0, limit: itemsPerPage};
        store.setDefaultSort('dsMenuEst','desc');
        store.load();
        return store;
    }
    
    var store;
    
    var dataStoreCliente = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: 'clienteAction.action'
            }),
            reader: new Ext.data.JsonReader({
                root: 'clientes',
                id: 'listas'
            },[
                {name: 'claveCliente',  type: 'string',  mapping:'claveCliente'},
                {name: 'descripcion',   type: 'string',  mapping:'descripcion'},
                {name: 'clavePersona',  type: 'string',  mapping:'clavePersona'}    
            ]),
            remoteSort: false
        });
    dataStoreCliente.setDefaultSort('descripcion', 'desc');

    
    var dataStoreUsuarios = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: 'usuariosAction.action'
            }),
            reader: new Ext.data.JsonReader({
                root: 'usuarios',
                id: 'usu'
            },[
                {name: 'cdUsuario',  type: 'string',  mapping:'cdUsuario'},
                {name: 'dsUsuario',  type: 'string',  mapping:'dsUsuario'}  
            ]),
            remoteSort: false
        });
    dataStoreUsuarios.setDefaultSort('dsUsuario', 'desc');
    
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
            remoteSort: false
        });
    dataStoreProductoCliente.setDefaultSort('dsTituloCliente', 'desc');
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
            remoteSort: false
        });
    dataStoreNivelMenu.setDefaultSort('dsMenuEst', 'desc');
    dataStoreNivelMenu.load();
        
    var dataStoreTipo = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url:'tipoMenuAction.action'
        }),
        reader: new Ext.data.JsonReader({
            root:'tiposMenu',
            id:'tip'
        },[
            {name:'clave',  type:'string',  mapping:'clave'},
            {name:'valor',  type:'string',  mapping:'valor'}
        ]),
        remoteSort:false
    }); 
    dataStoreTipo.setDefaultSort('valor','desc');
    dataStoreTipo.load();   
    
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
        remoteSort:false
    });    
    dataStoreEstado.setDefaultSort('valor','desc');
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
        remoteSort:false
    });    
    dataStoreOpciones.setDefaultSort('dsTitulo','desc');
    dataStoreOpciones.load();
    
    
agregarOpcion = function(store) {
	
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
                fieldLabel: "Producto",// cambio de etiqueta de Ramo a Producto
                emptyText:'Seleccionar un Producto...',
                selectOnFocus:true
        });  
        
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
                fieldLabel:'T&iacute;tulo del submen&uacute;',//cambio de Opción del menú a Título del submenú
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
                fieldLabel: "Opci&oacute;n del Men&uacute;",
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
    windowGuardar.show();
    };
    
    /*****EDITAR CONFIGURACION********/    
    var claveMenu = new Ext.form.TextField({
            fieldLabel: '',
            allowBlank: false,   
            name:'cdMenu',
            width: 300,
            hiddeParent:true,
            labelSeparator:'',
            hidden:true
    });    
    var dsMenu = new Ext.form.TextField({
                fieldLabel:'Menu',
                allowBlank:false,labelSeparator:':',
                name:'dsMenu',
                disabled:true,
                width: 300
        });
    var claveNivelMenu = new Ext.form.TextField({
            fieldLabel: '',
            allowBlank: false,   
            name:'cdNivel',
            width: 300,
            hiddeParent:true,
            labelSeparator:'',
            hidden:true
    });
    var pantallaAsociada = new Ext.form.TextField({
                fieldLabel:'T&iacute;tulo del submen&uacute;',// cambio de Opción del Menú a Título del submenú
                allowBlank:true,
                width: 300,
                name:'pantallaAsociada'
                
        });
    var clienteCombo = new Ext.form.ComboBox({
                tpl: '<tpl for="."><div ext:qtip="{descripcion}. {claveCliente}" class="x-combo-list-item">{descripcion}</div></tpl>',
                store: dataStoreCliente,
                width: 300,
                mode: 'local',
                name: 'dsElemento',
                typeAhead: true,
                labelSeparator:':',          
                triggerAction: 'all',           
                displayField:'descripcion',
                forceSelection: true,
                fieldLabel: "Nivel",//la etiqueta Cliente ahora es Nivel
                emptyText:'Seleccionar un Nivel...',
                disabled:true,
                selectOnFocus:true
        });  
    var usuarioCombo = new Ext.form.ComboBox({
                tpl: '<tpl for="."><div ext:qtip="{dsUsuario}. {cdUsuario}" class="x-combo-list-item">{dsUsuario}</div></tpl>',
                store: dataStoreUsuarios,
                width: 300,
                mode: 'local',
                name: 'dsUsuario',
                typeAhead: true,
                labelSeparator:':',          
                triggerAction: 'all',           
                displayField:'dsUsuario',
                forceSelection: true,
                fieldLabel: "Usuario",
                emptyText:'Seleccionar un Usuario...',
                disabled:true,
                selectOnFocus:true
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
                forceSelection: true,
                fieldLabel: "Producto",//la etiqueta Ramo ahora es Producto
                emptyText:'Seleccionar un Producto...',
                selectOnFocus:true
        }); 
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
            remoteSort: false,
            baseParams: {cdTituloCliente: productoClienteCombo.getValue()}
           
        });
      
        storeTipoSituacion.setDefaultSort('valor', 'desc');
  
 var comboTipoSituacion = new Ext.form.ComboBox({
     tpl: '<tpl for="."><div ext:qtip="{valor}. {clave}" class="x-combo-list-item">{valor}</div></tpl>',
     name: "dsTipsit",
     id: "dsTipsit",
     fieldLabel: "Riesgo",//la etiqueta Tipo de situacion ahora es Riesgo
     store: storeTipoSituacion,
     displayField:'valor',
     valueField:'clave',
     typeAhead: true,
     listWidth : 300,
     width: 300,
     mode: 'local',labelSeparator:':',
     triggerAction: 'all',
     emptyText:'Seleccionar un Riesgo...',
     selectOnFocus:true,
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
   var nivelMenuCombo = new Ext.form.ComboBox({
                tpl: '<tpl for="."><div ext:qtip="{dsMenuEst}. {cdNivelPadre}" class="x-combo-list-item">{dsMenuEst}</div></tpl>',
                store: dataStoreNivelMenu,
                width: 300,
                mode: 'local',
                name: 'opcionMenu',
                typeAhead: true,
                labelSeparator:':',          
                triggerAction: 'all',           
                displayField:'dsMenuEst',
                //forceSelection: true,
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
                //forceSelection: true,
                fieldLabel: "Opci&oacute;n del Men&uacute;",
                emptyText:'Seleccionar una Opcion...',
                selectOnFocus:true
        }); 
    var cdRol = new Ext.form.TextField({
                fieldLabel:'Rol',
                allowBlank:false,labelSeparator:':',
                name:'cdRol',
                disabled:true,
                width: 300
        });
    var opcionMenu = new Ext.form.TextField({
                fieldLabel:'T&iacute;tulo del submen&uacute;',//cambio de Opción del Menú a Título del submenú
                allowBlank:false,
                name:'dsMenuEst',labelSeparator:':',
                width: 300
        }); 
    var ramo = new Ext.form.TextField({
                fieldLabel:'Producto',
                allowBlank:false,
                name:'ramo',
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
                fieldLabel: 'Estado',
                emptyText:'Seleccionar estado...',
                selectOnFocus:true
        });
        
    var editarForm = new Ext.form.FormPanel({
                id:'editarForm',
                url:'editarOpcMenuAction.action',
                boder:false,
                width: 470,
                buttonAlign: "center",
                baseCls:'x-plain',
                labelWidth:90,
        		labelAlign: 'right',
                items:[
                    claveMenu,
                    dsMenu,
                    clienteCombo,
                    cdRol,
                    usuarioCombo,
                    opcionMenu,
                    productoClienteCombo,
                    comboTipoSituacion,
                    nivelMenuCombo,
                    opcionesCombo,
                    estadoCombo,
                    claveNivelMenu
                ]
            });
    var window = new Ext.Window({
        title: 'Editar Configuración Opciones del Menú',
        width: 500,
        height:410,
        minWidth: 480,
        minHeight: 390,
        layout: 'fit',
        plain:true,
        modal:true,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        closeAction:'hide',
        items: editarForm,

        buttons: [{
            text: 'Actualizar', 
            handler: function() {
                if (editarForm.form.isValid()) {
                    
                    editarForm.form.submit({
                        waitMsg:'Procesando...',
                        failure: function(form, action) {
			            	var mensajeRes = Ext.util.JSON.decode(action.response.responseText).mensajeRespuesta;
						    Ext.MessageBox.alert('Error',mensajeRes);
						    window.hide();
                            dataStoreNivelMenu.load();
                            store.load();
                        },
                        success: function(form, action) {
			            	var mensajeRes = Ext.util.JSON.decode(action.response.responseText).mensajeRespuesta;
						    Ext.MessageBox.alert('Aviso',mensajeRes);
                            editarForm.form.reset();
                            window.hide();
                            dataStoreNivelMenu.load();
                            store.load();
                            
                        }
                    });                   
                } else{
                    Ext.MessageBox.alert('Informacion incompleta', 'Por favor verifique');
                }             
            }
        },{
            text: 'Regresar',
            handler: function(){window.hide();}
        }]
    });
    /*******TERMINA EDITAR**********/

    var msgBorrar = new Ext.form.TextField({
        fieldLabel:'¿Esta seguro que desea Eliminar la Opción del Menu?',
        labelSeparator:'',
        anchor:'90%',
        hidden:true
    });                   
   
    var cdMenu = new Ext.form.NumberField({
        fieldLabel: '',   
        name:'cdMenu',
        anchor:'90%',
        hiddeParent:true,
        labelSeparator:'',
        hidden:true
    });  
    var cdNivel = new Ext.form.NumberField({
        fieldLabel: '',   
        name:'cdNivel',
        anchor:'90%',
        hiddeParent:true,
        labelSeparator:'',
        hidden:true
    });   
    var borrarForm= new Ext.FormPanel({
        id:'borrarForm',
        labelAlign:'top',
        baseCls: 'x-plain',
        anchor:'100%',
        url:'borrarOpcMenuAction.action',
        items:[ msgBorrar, cdMenu, cdNivel]
    });

    var windowDel = new Ext.Window({
        title: 'Eliminar Opci&oacute;n',
        minHeight: 50,
        minWidth: 250,
        width: 250,
        height:120,
        layout: 'fit',
        plain:true,
        modal:true,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        closeAction:'hide',
        items: borrarForm,
        buttons: [{
                text: 'Eliminar', 
                handler: function() {
                    if (borrarForm.form.isValid()) {
                            borrarForm.form.submit({          
                                waitMsg:'Procesando...',
                                failure: function(form, action) {
                                    var mensajeRes = Ext.util.JSON.decode(action.response.responseText).mensajeRespuesta;
						    		Ext.MessageBox.alert('Error',mensajeRes);
                                    windowDel.hide();
                                },
                                success: function(form, action) {
                                    var mensajeRes = Ext.util.JSON.decode(action.response.responseText).mensajeRespuesta;
						    		Ext.MessageBox.alert('Aviso',mensajeRes);
                                    windowDel.hide();
                                    gridConf.destroy();
                                    createGrid();
                                    dataStoreNivelMenu.load();
                                    store.load();
                                }
                            });                   
                    }else{
                        Ext.MessageBox.alert('Error', 'Verifique!');
                    }             
                }
            },{
                text: 'Cancelar',
                handler: function(){windowDel.hide();}
            }]
        });

	var tipoForm = new Ext.form.TextField({
		fieldLabel:'Tipo',
		name:'dsTipoMenu',
		disabled:true,
		value:_MENU_TIPO,
		width:200
	});    
    var nombreForm = new Ext.form.TextField({
        fieldLabel:'Nombre',
        name:'dsMenu',
        disabled : true,
        value: _MENU_TITULO,
        width:200
    }); 
    var clienteForm = new Ext.form.TextField({
        fieldLabel:'Nivel',//la etiqueta Cliente ahora es Nivel 
        name:'dsElemento',
        disabled : true,
        value:_MENU_CLIENTE,
        width:200
    }); 
    var usuarioForm = new Ext.form.TextField({
        fieldLabel:'Usuario',
        name:'dsUsuario',
        disabled : true,
        value:_MENU_USUARIO,
        width:200
    }); 
    var rolForm = new Ext.form.TextField({
        fieldLabel:'Rol',
        name:'cdRol',
        disabled : true,
        value:_MENU_ROL,
        width:200
    });
    
    var opcion = new Ext.form.TextField({
        fieldLabel:'Opci&oacute;n',
        name:'parametrosBusqueda.opcion',
        width:200
    });
    var opcionPadre = new Ext.form.TextField({
        fieldLabel:'Opci&oacute;n Padre',
        name:'parametrosBusqueda.opcionPadre',
        width:200
    });
    var menuUsuarioFiltrosForm = new Ext.form.FormPanel({
        title:'<span style="color:black;font-size:14px;">Configurar Men&uacute; de Usuario</span>',
    	url:'opcMenuUsuariosJson.action?store=STORE',
        bodyStyle:'background:white',
        buttonAlign:'center',
        labelAlign:'right',
        frame:true,
        width:600,
        autoHeight:true,
        items:[{
			layout:'form',
			border:false,
            items:[{
                    bodyStyle:'background:white',
                    labelWidth:200,
                    layout:'form',
                    frame:true,
                    baseCls:'',
                    items:[{
                		layout:'form',
                		border:false,
                		items:[{
                    		bodyStyle:'background:white',
                    		labelWidth:60,
                    		layout:'form',
                    		frame:true,
                    		baseCls:'',
                    		items:[{
                            	layout:'column',
                            	border:false,
                            	items:[
                            		{
                                    	layout:'form',
                                    	border:false,
                                    	items:[
                                        	nombreForm,
                                        	clienteForm,
                                        	tipoForm
                                    	]
                                    },{
                                    	layout:'form',
                                    	border:false,
                                    	items:[
                                        	rolForm,    
                                        	usuarioForm
                                    	]
                            		}
                            	]
                    		}]
                		}]
        			},
                	{
                	layout:'form',
					title:'<span style="color:#98012e;font-size:14px;font-family:Arial,Helvetica,sans-serif;">B&uacute;squeda</span>',
					baseCls:'',
                    	items:[{
                    		layout:'form',
                        	border:false,
                        	items:[
                        		opcion,
                            	opcionPadre
							]
                    	}]
                	}
            	]
            }]
        }],
        buttons:[{                                  
                text:'Buscar',  
                handler: function() {                                            
                    if (menuUsuarioFiltrosForm.form.isValid()) {
                            menuUsuarioFiltrosForm.form.submit({                 
                                waitMsg:'Procesando...',
                                failure: function(form, action) {
                                    var mensajeRes = Ext.util.JSON.decode(action.response.responseText).mensajeRespuesta;
						    		Ext.MessageBox.alert('Error',mensajeRes);
                                    store.load();
                                },
                                success: function(form, action) {
                                	var mensajeExito = Ext.util.JSON.decode(action.response.responseText).exito;
									if(mensajeExito == 'true'){
										store.load();
									}else{
										var mensajeRes = Ext.util.JSON.decode(action.response.responseText).mensajeRespuesta;
						    			Ext.MessageBox.alert('Aviso',mensajeRes);
						    			store.load();
									}
                                }
                            });                   
                    } else{
                        Ext.MessageBox.alert('Error','Ocurrio un error');
                    }  
                }                                                       
                },{
                text:'Cancelar',
                handler: function() {
                    menuUsuarioFiltrosForm.form.reset();
                }
        }]
    });                     
    menuUsuarioFiltrosForm.render('formFiltro');
    
    function toggleDetails(btn, pressed){
        var view = grid.getView();
        view.showPreview=pressed;
        view.refresh();
    }

    var cm = new Ext.grid.ColumnModel([
        new Ext.grid.RowNumberer(),
        {header:'Opci&oacute;n', 		dataIndex:'dsMenuEst',   width:150, sortable:true},
        {header:'Opci&oacute;n Padre', 	dataIndex:'opcionMenu',  width:150, sortable:true},
        {header:'Producto', 			dataIndex:'dsRamo',      width:150, sortable:true},
        {header:'Riesgo', 				dataIndex:'dsTipsit',    width:150, sortable:true},
        {header:'Nombre', 				dataIndex:'dsTitulo',     width:150, sortable:true}
    ]);
 
    var selectedId;
    function createGrid(){
        gridConf = new Ext.grid.GridPanel({
        store: menuusuario(),
        id:'lista-lista',
        border:true,
        buttonAlign:'center',
        cm: cm,
        buttons:[
                {text:'Agregar',
                tooltip:'Agregar nueva configuración del Menu',
                handler:function(){
                	agregarOpcion(store);}
                },{
                id:'editar',
                text:'Editar',
                tooltip:'Editar Menu seleccionado',
                handler:function(){
                   if(!gridConf.getSelectionModel().getSelected()){
                   	  Ext.MessageBox.alert('Aviso','Debe seleccionar un registro para realizar esta operaci&oacute;n');
                     }
                   }
                },{                
                text:'Eliminar',
                id:'borrar',
                tooltip:'Elimina Menu seleccionado',
                handler:function(){
                   if(!gridConf.getSelectionModel().getSelected()){
                   	  Ext.MessageBox.alert('Aviso','Debe seleccionar un registro para realizar esta operaci&oacute;n');
                     }
                   }                
                },{
                text:'Exportar',
                tooltip:'Exporta Menu Usuarios',
                handler: exportButton(_ACTION_EXPORT_MENU_USUARIO)
                },{
                text:'Regresar',
                tooltip:'Regresar a la pagina anterior',
                handler:function(){
                    document.forms[0].action = _CONTEXT+'/menuusuario/menuUsuario.action'; 
                    document.forms[0].submit(); 
                }        
                }],
        width:600,
        frame:true,
        height:578,
        title:'<span style="color:#98012e;font-size:14px;font-family:Arial,Helvetica,sans-serif;">Listado</span>',
        sm:new Ext.grid.RowSelectionModel({
        singleSelect:true,
        listeners: {                            
                        rowselect: function(sm, row, rec) {
                                selectedId = store.data.items[row].id;
                                Ext.getCmp('borrar').on('click', function(){
                                	if(!gridConf.getSelectionModel().getSelected()){
				                   	  Ext.MessageBox.alert('Aviso','Debe seleccionar un registro para realizar esta operaci&oacute;n');
				                    } else {
	                                    windowDel.show();
	                                    Ext.getCmp('borrarForm').getForm().loadRecord(rec);
				                    }
                                });                                                                                                                         
                                Ext.getCmp('editar').on('click',function(){       
                                	if(!gridConf.getSelectionModel().getSelected()){
				                   	  Ext.MessageBox.alert('Aviso','Debe seleccionar un registro para realizar esta operaci&oacute;n');
				                    } else {
	                                	window.show();
	                                    Ext.getCmp('editarForm').getForm().loadRecord(rec);
				                    }
                                });                                 
                        }
                }
        }),
    bbar: new Ext.PagingToolbar({
        store: store,                                               
        displayInfo: true,
        pageSize: 20,
        displayMsg: 'Registros mostrados {0} - {1} de {2}',
        emptyMsg: 'No hay registros para mostrar',
		beforePageText: 'P&aacute;gina',
		afterPageText: 'de {0}'
        })              
          
    });
    gridConf.render('gridConfig');
    }
    createGrid();
});