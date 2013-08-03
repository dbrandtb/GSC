Ext.onReady(function(){ 
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";

/*
 function createOptionGrid(){                                       
            url='configmenu/opciones.action'+'?dsTitulo='+nombreLista.getValue();                                 
            store = new Ext.data.Store({
                proxy: new Ext.data.HttpProxy({
                url: url
                }),
                reader: new Ext.data.JsonReader({
                root:'opciones',   
                totalProperty: 'totalCount'             
                },[
                    {name: 'cdTitulo',     type: 'string',  mapping:'cdTitulo'},
                    {name: 'dsTitulo',     type: 'string', 	mapping:'dsTitulo'},
                    {name: 'dsTituloDes',  type: 'string',  mapping:'dsTituloDes'},
                    {name: 'dsUrl',  	   type: 'string',  mapping:'dsUrl'}               
                ]),
            remoteSort: false
        });
        store.setDefaultSort('dsTitulo', 'ASC');
        //store.load();
        return store;
    }
*/


function createOptionGrid(){                                       
            url='configmenu/opciones.action';                          
            store = new Ext.data.Store({
                proxy: new Ext.data.HttpProxy({
                url: url
                }),
                reader: new Ext.data.JsonReader({
                root:'opciones',   
                totalProperty: 'totalCount'             
                },[
                    {name: 'cdTitulo',     type: 'string',  mapping:'cdTitulo'},
                    {name: 'dsTitulo',     type: 'string', 	mapping:'dsTitulo'},
                    {name: 'dsTituloDes',  type: 'string',  mapping:'dsTituloDes'},
                    {name: 'dsUrl',  	   type: 'string',  mapping:'dsUrl'},
                    {name: 'dsTipDes',     type: 'string',  mapping:'dsTipDes'}                     
                ]),
            remoteSort: false,
            baseParams: {dsTitulo: nombreLista.getValue()}
        });
        store.setDefaultSort('dsTitulo', 'ASC');
        //store.load();
        return store;
    }




    
    
    
        var ds = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: 'configmenu/ObtieneOpciones.action'
            }),
            reader: new Ext.data.JsonReader({
                root: 'productos',
                id: 'listas'
            },[
                {name: 'cdRamoP',   type: 'string',  mapping:'cdRamoP'},
                {name: 'dsRamo',    type: 'string',  mapping:'dsRamo'}    
            ]),
            remoteSort: true
        });
    ds.setDefaultSort('listas', 'desc');

/***************************************
*Se crea la ventana de agregar opciones.  
****************************************/
        
agregar = function() {
        var dsTitulo = new Ext.form.TextField({
            fieldLabel: 'Nombre',
            allowBlank: false,
            name: 'dsTitulo',
            width: 300 
        });
        var dsUrl = new Ext.form.TextField({
            fieldLabel: 'URL',
            allowBlank: false,
            name: 'dsUrl',
            width: 300 
        });
        var dsTipDesCheck = new Ext.form.Checkbox({
                id:'id-tip-des-check',
                name:'dsTipDes',
                fieldLabel: 'Página Externa',
                labelSepartor:'', 
                checked:false,
                onClick:function(){
					if(this.getValue()){
						this.setRawValue("E");                              
					}else{
						this.setRawValue("N");
						}
					}
		});
    
        var formPanel = new Ext.form.FormPanel({
        url:'configmenu/InsertaOpcion.action',
        baseCls: 'x-plain',
        labelWidth: 90,
        //defaultType: 'textfield',
        items: [            
                dsTitulo,
                dsUrl,
                dsTipDesCheck
            ]
        });

        var window = new Ext.Window({
            title: 'Agregar Opciones',
            plain:true,
            width: 500,
            height:150,
            minWidth: 300,
            minHeight: 100,
            layout: 'fit',
            bodyStyle:'padding:5px;',
            buttonAlign:'center',
            modal:true,
            items: formPanel,
            buttons: [{
                text: 'Guardar', 
                handler: function() {                
                    if (formPanel.form.isValid()) {
                        formPanel.form.submit({               
                            waitMsg:'Procesando...',
                            failure: function(form, action) {
                                    Ext.MessageBox.alert('Agregar Opciones', Ext.util.JSON.decode(action.response.responseText).msgRespuesta);
                                    window.hide();
                                    grid2.destroy();
                                    createGrid();
                                    store.load();
                            },
                            success: function(form, action) {
                                Ext.MessageBox.alert('Agregar Opciones', Ext.util.JSON.decode(action.response.responseText).msgRespuesta);
                                window.hide();                          
                            }
                        });                   
                    } else{
                        Ext.MessageBox.alert('Error', 'Por favor revise los errores!');
                          }             
                    }
            },{
            text: 'Regresar',
            handler: function(){
                    window.hide();
                    }
            }]
        });

        window.show();
    };

 /*************************************
*Crea la ventana de editar
 **************************************/
    
    var cdTitulo = new Ext.form.TextField({
            fieldLabel: '',
            allowBlank: false,   
            name:'cdTitulo',
            anchor:'90%',
            hiddeParent:true,
            labelSeparator:'',
            hidden:true
    });
            
    var dsUrl = new Ext.form.TextField({
            fieldLabel: 'URL',
            allowBlank: false,
            name: 'dsUrl',
            width: 300 
    });
    
    var dsTitulo = new Ext.form.TextField({
            fieldLabel: '',
            allowBlank: false,
            name: 'dsTitulo',
            labelSeparator:'',
            hidden:true 
    });
    
    var dsTituloDes = new Ext.form.TextField({
            fieldLabel: 'Titulo',
            allowBlank: false,
            name: 'dsTituloDes',
            disabled: true,
            width: 300 
    });
    
    var dsTipDesEditCheck = new Ext.form.Checkbox({
                id:'id-tip-des',
                name:'dsTipDes',
                fieldLabel: 'Página Externa',
                labelSepartor:'', 
                checked:false,
                onClick:function(){
					if(this.getValue()){
						this.setRawValue("E");                              
					}else{
						this.setRawValue("N");
						}
					}
		});

    
    var editForm= new Ext.FormPanel({
        id:'editForm',
        labelWidth: 90,
        baseCls: 'x-plain',
        url:'configmenu/EditaOpcion.action',
        items:[cdTitulo, dsTituloDes, dsUrl, dsTipDesEditCheck, dsTitulo]
    });
    var window2 = new Ext.Window({
        title: 'Editar Opciones',
        width: 500,
        height:180,
        minWidth: 450,
        minHeight: 160,
        layout: 'fit',
        plain:true,
        modal:true,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        items: editForm,
        buttons: [{
            text: 'Guardar', 
            handler: function() {
                    if (editForm.form.isValid()) {
                            editForm.form.submit({        
                                waitMsg:'Procesando...',
                                failure: function(form, action) {
                                    Ext.MessageBox.alert('Editar Opciones', Ext.util.JSON.decode(action.response.responseText).msgRespuesta);
                                    window2.hide();
                                },
                                success: function(form, action) {
                                    Ext.MessageBox.alert('Editar Opciones', Ext.util.JSON.decode(action.response.responseText).msgRespuesta);
                                    window2.hide();
                                    grid2.destroy();
                                    createGrid();
                                    store.load();                                      
                                }
                            });                   
                     }else{
  				      // Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'))
                        Ext.MessageBox.alert('Error', 'Por favor revise los errores!');
                    }             
                }
            },{
                text: 'Regresar',
                handler: function(){window2.hide();}
            }]
        });
        
/*****se crea la ventana de borrar una opcion***/

    var msgBorrar = new Ext.form.TextField({
        fieldLabel:'¿Esta seguro que desea Eliminar la Opcion?',
        labelSeparator:'',
        anchor:'90%',
        hidden:true
    });                   
   
    var cdTitulo = new Ext.form.NumberField({
        fieldLabel: '',   
        name:'cdTitulo',
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
        url:'configMenu/BorrarAction.action',
        //bodyStyle:'background: white',
        items:[ msgBorrar, cdTitulo]
    });

    var windowDel = new Ext.Window({
        title: 'Eliminar Opcion',
        minHeight: 50,
        minWidth: 280,
        width: 280,
        height:120,
        layout: 'fit',
        plain:true,
        modal:true,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        items: borrarForm,
        buttons: [{
                text: 'Eliminar', 
                handler: function() {
                    if (borrarForm.form.isValid()) {
                            borrarForm.form.submit({          
                                waitMsg:'Procesando...',
                                failure: function(form, action) {
                                    Ext.MessageBox.alert('Eliminar Opcion', Ext.util.JSON.decode(action.response.responseText).msgRespuesta);
                                    windowDel.hide();
                                },
                                success: function(form, action) {
                                    Ext.MessageBox.alert('Eliminar Opcion', Ext.util.JSON.decode(action.response.responseText).msgRespuesta);
                                    windowDel.hide();
                                    grid2.destroy();
                                    createGrid();
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
/*****/

/******************
*Form de busqueda para el grid. 
*******************/   

    var nombreLista = new Ext.form.TextField({
        fieldLabel: 'Nombre',
        name:'dsTitulo',
        width: 200
    });    

    
    var opcionesForm = new Ext.form.FormPanel({    
    	el:'opcionesForm',
        title: '<span style="color:black;font-size:14px;">Opciones de men&uacute;</span>',
        //iconCls:'logo',
        bodyStyle:'background: white',
        buttonAlign: 'center',
        labelAlign: 'right',   
        frame:true,   
        url:'configmenu/opciones.action',
        width: 500,
        height:150,
        labelWidth: 120,       
        items: [{
                layout:'form',
                border: false,
                items:[{
                    title :'<span class="x-form-item" style="color:#98012e;font-size:14px;font-family:Arial,Helvetica,sans-serif;">Búsqueda</span>',
                    bodyStyle:'background: white',      
                layout: 'form',
                frame:true,
                baseCls: '',
                buttonAlign: 'center',
                        items: [{
                            layout:'column',
                            border:false,
                            //columnWidth: 1,
                            items:[{
                                //columnWidth:.6,
                                layout: 'form',                     
                                border: false,                      
                                items:[ 
                                        nombreLista                                                                                                                                  
                                    ]
                                },{
                                //columnWidth:.4,
                                layout: 'form'
                                }]
                            }],
                            buttons:[{                                  
                                    text:'Buscar',  
                                    handler: function() {                                            
                                        if (opcionesForm.form.isValid()) {
                                                opcionesForm.form.submit({                 
                                                    waitMsg:'Procesando...',
                                                    failure: function(form, action) {
                                                        Ext.MessageBox.alert('Busqueda Finalizada', 'No hay datos');
                                                        grid2.destroy();
                                                        createGrid();
                                                        store.load(); 
                                                    },
                                                    success: function(form, action) {
                                                        grid2.destroy();
                                                        createGrid();
                                                        store.load();                           
                                                    }
                                                });                   
                                        } else{
                                            Ext.MessageBox.alert('Error', 'Inserte Parametro de Búsqueda!');
                                        }  
                                    }                                                       
                                    },{
                                    text:'Cancelar',
                                    handler: function() {
                                        opcionesForm.form.reset();
                                    }
                                }]
                        }]
                }]  
        });
        
        opcionesForm.render();
        
function toggleDetails(btn, pressed){
        var view = grid.getView();
        view.showPreview = pressed;
        view.refresh();
        }
        var store;
        var cm = new Ext.grid.ColumnModel([
            new Ext.grid.RowNumberer(),
            {header: "Titulo",  dataIndex:'cdTitulo', width: 120, sortable:true, locked:false, id:'cdTitulo', hidden:true},          
            {header: "Nombre",  dataIndex:'dsTitulo', width: 160, sortable:true},
            {header: "URL",     dataIndex:'dsUrl',    width: 800, sortable:true},
            {header: "dsTipDes",dataIndex:'dsTipDes', width: 800, sortable:true,  hidden:true}
                                
        ]);
        var grid2;
        var selectedId;
/*********************
*Se crea el grid de Planes
***********************/        
        
function createGrid(){
    grid2= new Ext.grid.GridPanel({
        store:createOptionGrid(),
        border:true,
        frame:true,
        //baseCls:'background:white',
        buttonAlign:'center',
        cm: cm,
        buttons:[
                {text:'Agregar',
                tooltip:'Agregar una nueva opcion',
                handler:function(){
                agregar(ds);}
                },{
                id:'editar',
                text:'Editar',
                tooltip:'Edita opcion seleccionada',
                handler:function(){
                   if(!grid2.getSelectionModel().getSelected()){
                   		Ext.MessageBox.alert('Aviso','Debe seleccionar un registro para realizar esta operaci&oacute;n');
                     }
                   }
                },{
                text:'Eliminar',
                id:'borrar',
                tooltip:'Elimina opcion seleccionada',
                handler:function(){
                   if(!grid2.getSelectionModel().getSelected()){
                   	  	Ext.MessageBox.alert('Aviso','Debe seleccionar un registro para realizar esta operaci&oacute;n');
                     }
                   }
                },/*{
                text:'Copiar',
                tooltip:'Copia opcion seleccionada'                
                },*/{
                text:'Exportar',
                tooltip:'Exporta opciones',
                //handler: exportButton(_ACTION_EXPORT_CONFIG)
                handler:function(){                		
                	showExportDialog(_ACTION_EXPORT_CONFIG);
                }
                }],                                                         
        width:500,
        height:590,        
        title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font-family:Arial,Helvetica,sans-serif;">Listado</span>',
        //renderTo:document.body,
        
    sm: new Ext.grid.RowSelectionModel({
        singleSelect: true,
        listeners: {                            
                        rowselect: function(sm, row, rec) {
                         
                                selectedId = store.data.items[row].id;                                                                                                                                    
                                Ext.getCmp('editar').on('click',function(){
                                	if(!grid2.getSelectionModel().getSelected()){
				                   	    Ext.MessageBox.alert('Aviso','Debe seleccionar un registro para realizar esta operaci&oacute;n');
				                    } else {
				                    	window2.show();
				                    	Ext.getCmp('editForm').getForm().loadRecord(rec);
				                    	if (rec.get('dsTipDes') != 'E') {
                                			dsTipDesEditCheck.setValue(false);
                                		} else {
                                			dsTipDesEditCheck.setValue(true);
                                		}
				                    }
                                });
                                                                           
                                Ext.getCmp('borrar').on('click', function(){
                                	if(!grid2.getSelectionModel().getSelected()){
				                   	    Ext.MessageBox.alert('Aviso','Debe seleccionar un registro para realizar esta operaci&oacute;n');
				                    } else {
	                                	windowDel.show();
	                                	Ext.getCmp('borrarForm').getForm().loadRecord(rec);
				                    }
                                });
                            }
                   }
        
        }),
        //viewConfig: {autoFill: true,forceFit:true},                
        bbar: new Ext.PagingToolbar({
            pageSize: itemsPerPage,
            store: store,                                               
            displayInfo: true,
			displayMsg: 'Mostrando registros {0} - {1} de {2}',
			emptyMsg: 'No hay registros para ',
			beforePageText: 'P&aacute;gina',
			afterPageText: 'de {0}'
            })
        });
    grid2.render('gridOpciones');
    }
    createGrid();
    ds.load();
    
});


