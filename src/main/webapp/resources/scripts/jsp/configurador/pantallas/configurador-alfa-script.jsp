<%@ include file="/taglibs.jsp"%>


var componente = ' ';


/*************************************************************
** Combo Activacion de Conjunto
**************************************************************/ 
var myData = [
        ['S',TAB_3_ACCION_OPCION_1],
        ['N',TAB_3_ACCION_OPCION_2]
   ];

var storeActivacion = new Ext.data.SimpleStore({
        fields: [
           {name: 'idAccion'},
           {name: 'descripcion'}
          
       ]
    });
storeActivacion.loadData(myData);

  var cdProceso = new Ext.form.TextField({
    name:'cdProceso',
    id:'cdProceso',
    type:'hidden',
    hidden:true,
    labelSeparator:''
 });
 
  var cdProcesoActivacion = new Ext.form.TextField({
    name:'cdProcesoActivacion',
    id:'cdProcesoActivacion',
    type:'hidden',
    hidden:true,
    labelSeparator:''
 });
 
  var cdPantalla = new Ext.form.TextField({
    name:'cdPantalla',
    type:'hidden',
    hidden:true,
    labelSeparator:''
 });
 
 var cdProducto = new Ext.form.TextField({
    name:'cdProducto',
    id:'cdProducto',
    type:'hidden',
    hidden:true,
    labelSeparator:''
 });
 
 var cdProductoActivacion = new Ext.form.TextField({
    name:'cdProductoActivacion',
    id:'cdProductoActivacion',
    type:'hidden',
    hidden:true,
    labelSeparator:''
 });
 
 var cdCliente = new Ext.form.TextField({
    name:'cdCliente',
    id:'cdCliente',
    type:'hidden',
    hidden:true,
    labelSeparator:''
 });
 
var cdTipoMaster = new Ext.form.TextField({
    name:'cdTipoMaster',
    id:'cdTipoMaster',
    type:'hidden',
    hidden:true,
    labelSeparator:''
 });
 
 var dsArchivo = new Ext.form.TextField({
    name:'dsArchivo',
    id:'dsArchivo',
    type:'hidden',
    hidden:true,
    labelSeparator:''
 });

/*************************************************************
** Store 
**************************************************************/ 

var storeProceso = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                //url: 'confpantallas/obtenerListasParamConjunto.action'
            	url: 'configuradorpantallas/obtenerListasParamConjunto.action'
            }),
            reader: new Ext.data.JsonReader({
            root: 'procesoList',
            id: 'value'
            },[
           {name: 'value', type: 'string', mapping:'value'},
           {name: 'label', type: 'string', mapping:'label'}  
             ]),
            remoteSort: true
        });
        storeProceso.setDefaultSort('value', 'desc');
        storeProceso.load();

  
  var  storeCliente = new Ext.data.Store({
           proxy: new Ext.data.HttpProxy({
                //url: 'confpantallas/obtenerListasParamConjunto.action'
           		url: 'configuradorpantallas/obtenerListasParamConjunto.action'
           }),
            reader: new Ext.data.JsonReader({
            root: 'clienteList',
            id: 'cdCliente'
            },[
           {name: 'cdCliente', type: 'string',mapping:'cdCliente'},
           {name: 'dsCliente', type: 'string',mapping:'dsCliente'}    
            ]),
            remoteSort: true
        });
        storeCliente.setDefaultSort('cdCliente', 'desc');
        storeCliente.load();
       
       


  
var storeRegistro;
function getRegistro(){   
storeRegistro = new Ext.data.Store({
         proxy: new Ext.data.HttpProxy({
               //url: 'confpantallas/obtenerRegistro.action'
         		url: 'configuradorpantallas/obtenerRegistro.action'
           
          }),
            reader: new Ext.data.JsonReader({
            root: 'registroList',
            id: 'cdConjunto'
            },[
           {name: 'cdConjunto', type: 'string',mapping:'cdConjunto'},
           {name: 'proceso', type: 'string',mapping:'proceso'},
           {name: 'cdProceso', type: 'string',mapping:'cdProceso'},
           {name: 'cdProducto', type: 'string',mapping:'cdRamo'},
           {name: 'cdCliente', type: 'string',mapping:'cdCliente'},
           {name: 'cliente', type: 'string',mapping:'cliente'},
           {name: 'producto', type: 'string',mapping:'dsRamo'},
           {name: 'nombreConjunto', type: 'string',mapping:'nombreConjunto'},
           {name: 'descripcion', type: 'string',mapping:'descripcion'}
           ]),
            remoteSort: true
        });
        storeRegistro.load();
        return storeRegistro;
}


var storeRegistroPantalla;
var clavePantalla = ' ';
function getRegistroPantalla(clavePantalla){   
storeRegistroPantalla = new Ext.data.Store({
         proxy: new Ext.data.HttpProxy({
                //url: 'confpantallas/obtenerRegistroPantalla.action'+'?idP='+clavePantalla
         	url: 'configuradorpantallas/obtenerRegistroPantalla.action'+'?idP='+clavePantalla
           
          }),
            reader: new Ext.data.JsonReader({
            root: 'registroPantallaList',
            id: 'cdPantalla'
            },[
           {name: 'cdPantalla', type: 'string',mapping:'cdPantalla'},
           {name: 'nombrePantalla', type: 'string',mapping:'nombrePantalla'},
           {name: 'htipoPantalla', type: 'string',mapping:'cdMaster'},
           {name: 'descripcionPantalla', type: 'string',mapping:'descripcion'},
           {name: 'dsArchivo', type: 'string',mapping:'dsArchivo'}
            ]),
            remoteSort: true
        });
        storeRegistroPantalla.load();
        return storeRegistroPantalla;
}


var storeTipoPantalla = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
             //   url: 'confpantallas/obtenerMasters.action'
                url: 'configuradorpantallas/obtenerMasters.action'
            }),
            reader: new Ext.data.JsonReader({
            root: 'tipoPantallaList',
            id: 'cdMaster'
            },[
           {name: 'cdTipo', type: 'string', mapping:'cdTipo'},
           {name: 'cdMaster', type: 'string', mapping:'cdMaster'},
           {name: 'dsMaster', type: 'string', mapping:'dsMaster'}    
             ]),
            remoteSort: true,
            baseParams: {cdProceso: cdProceso.getValue() }
        });
        storeTipoPantalla.setDefaultSort('cdMaster', 'desc');
        storeTipoPantalla.load();
 



 /*************************************************************
** Combos del combosFieldSet (funcion onchange)
**************************************************************/ 

var comboProceso = new Ext.form.ComboBox({
    name: 'proceso',
    id: 'proceso',
    fieldLabel: TAB_1_PROCESO,
    store: storeProceso,
    displayField:'label',
    hiddenName: 'hproceso',  
    valueField:'value',
    typeAhead: true,
    mode: 'local',
    triggerAction: 'all',
    listWidth : 200,
    width:200,
    emptyText: TAB_1_PROCESO_EMPTY,
    editable:true,
    allowBlank: false,
    labelSeparator:'', 
    selectOnFocus:true
});




 var comboNivel = new Ext.form.ComboBox({
     name: 'cliente',
     id: 'cliente',
     fieldLabel: TAB_1_NIVEL,
     store: storeCliente,
     displayField:'dsCliente',
     hiddenName: 'hcliente',  
     valueField:'cdCliente',
     typeAhead: true,
     listWidth : 200,
     width: 200,
     mode: 'local',
     triggerAction: 'all',
     emptyText: TAB_1_NIVEL_EMPTY,
     editable:true,
     allowBlank: false,
     labelSeparator:'', 
     selectOnFocus:true
  });
  
  
  var storeProducto = new Ext.data.Store({
           proxy: new Ext.data.HttpProxy({
            //   url: 'confpantallas/obtenerListaProductos.action'
               url: 'configuradorpantallas/obtenerListaProductos.action'
          }),
            reader: new Ext.data.JsonReader({
            root: 'productoList',
            id: 'value'
            },[
           {name: 'value', type: 'string',mapping:'value'},
           {name: 'label', type: 'string',mapping:'label'}    
            ]),
            remoteSort: true,
            baseParams: {hcliente: comboNivel.getValue()}
           
        });
      
        storeProducto.setDefaultSort('value', 'desc');
        storeProducto.load();
  
  
  
 var comboProducto = new Ext.form.ComboBox({
     name: 'producto',
     id: 'producto',
     fieldLabel: TAB_1_PRODUCTO,
     store: storeProducto,
     displayField:'label',
     hiddenName: 'hproducto',  
     valueField:'value',
     typeAhead: true,
     listWidth : 200,
     width: 200,
     mode: 'local',
     triggerAction: 'all',
     emptyText: TAB_1_PRODUCTO_EMPTY,
     selectOnFocus:true,
     editable:true,
   //   tpl: '<tpl for="."><div ext:qtip="{value}. {nick}" class="x-combo-list-item">{label}</div></tpl>',
     labelSeparator:''
  });
  
  comboNivel.on('select', function(){
    comboProducto.clearValue();
    storeProducto.baseParams['hcliente'] = comboNivel.getValue();
    storeProducto.load({
                  callback : function(r,options,success) {
                      if (!success) {
                        //  Ext.MessageBox.alert('Aviso','No se encontraron registros');  
                         storeProducto.removeAll();
                      }
                  }

              }
            );
   });
  
  
/*************************************************************
** Textfields del datosFieldSet
**************************************************************/ 
var nombreConjunto = new Ext.form.TextField({
     fieldLabel: TAB_1_NOMBRE_CONJUNTO,
     name:'nombreConjunto',
     allowBlank: false,
     width : 250,
     maxLengthText:'30'
   });

var descripcion = new Ext.form.TextArea({
    fieldLabel: TAB_1_DESCRIPCION,
    name:'descripcion',
    allowBlank: false,
    width : 250,
    grow: false,
    preventScrollbars:true
   
 
   });

var cdConjunto = new Ext.form.TextField({
    name:'cdConjunto',
    type:'hidden',
    hidden:true,
    labelSeparator:''
   
 });
 
 
 
/*************************************************************
** Textfields y combo del FormPanel del TAB 2
**************************************************************/ 
var nombrePantalla = new Ext.form.TextField({
     fieldLabel: TAB_2_NOMBRE,
     name:'nombrePantalla',
     allowBlank: false,
     width : 250,
     maxLengthText:'30'
   });
 
 var descripcionPantalla = new Ext.form.TextArea({
     fieldLabel: TAB_2_DESCRIPCION,
     name:'descripcionPantalla',
     allowBlank: false,
     grow: false,
     height:40,  
     preventScrollbars:true,
     width : 250
     
   });
   
    var comboTipoPantalla = new Ext.form.ComboBox({
    name: 'tipoPantalla',
    id: 'tipoPantalla',
    fieldLabel: TAB_2_MASTER,
    store: storeTipoPantalla, 
    displayField:'dsMaster',
    hiddenName: 'htipoPantalla',  
    valueField:'cdMaster',
    typeAhead: true,
    mode: 'local',
    triggerAction: 'all',
    listWidth : 240,
    width:240,
    emptyText: TAB_2_MASTER_EMPTY,
    editable:true,
    autoHeight:'true',
    allowBlank: false,
    labelSeparator:'', 
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
        cdTipoMaster.setValue(record.get('cdTipo'));
       },
       
   
    selectOnFocus:true
});
   
  
 
 /*
 var cdP = new Ext.form.TextField({
    name:'cdP',
    id:'cdP',
    type:'hidden',
    hidden:true,
    labelSeparator:''
 });
 */
/*************************************************************
** Textfields y combo del FormPanel del TAB 3
**************************************************************/ 
   var comboActivacion = new Ext.form.ComboBox({
    name: 'activacion',
    id: 'activacion',
    fieldLabel: TAB_3_ACCION,
    listWidth : 200,
    store: storeActivacion,
    displayField:'descripcion',
    hiddenName: 'hactivacion',  
    valueField:'idAccion',
    typeAhead: true,
    mode: 'local',
    triggerAction: 'all',
    width:200,
    emptyText: TAB_3_ACCION_EMPTY,
    editable:true,
    autoHeight:'true',
    allowBlank: false,
    selectOnFocus:true
});
 
/*
  var ejemAreaTrabajo = new Ext.form.TextField({
     fieldLabel: 'Dato ejemplo',
     type:'hidden',
     hidden:true,
     id:'ejemAreaTrabajo',
     value: 'items:[{xtype:\"textfield\", fieldLabel: \"Ejemplo area\", name:\"ejemplo1\"  }]',
     name:'ejemAreaTrabajo'
    
   });*/
 
/*************************************************************
** Paneles del viewport(3):parametros, master, area de trabajo
**************************************************************/
   var paramEntradaPanel = new Ext.Panel({
        region: 'north',
        title: PANEL_1_TITLE,
        iconCls:'logo',
        split:true,
        height:260,
        id:'up',
        bodyStyle:'padding:5px',
        collapsible: true,
        layoutConfig:{
             animate:true
        }
    });
 
  /*
    var masterPanel = new Ext.Panel({
        region: 'west',
        title: 'MENU',
        split:true,
        width:200,
        heigth:'500',
        minSize:'200',
        maxSize:'200',
        margins:'0 5 0 5',
        bodyStyle:'padding:5px',
        collapsible: true,
        layout:'accordion',
        layoutConfig:{
             animate:true
        }     
    });
 */
 
/*************************************************************
** Paneles del masterPanel (5)
**************************************************************/
   /******Paneles del masterPanel ********/
   
   /*
   var atributosMasterPanel = new Ext.Panel({
             border:false,
             title: 'Atributos del Master',
             autoScroll:true,
             items:[{
                    xtype: 'treepanel',
                    width: 190,
                    border : false, 
                    autoScroll: true,
                    split: true,
                    loader: new Ext.tree.TreeLoader({
                           dataUrl:''
                            
                            
                    }), 
                    root: new Ext.tree.AsyncTreeNode({
                          text: 'Coberturas', 
                          draggable:false, // disable root node dragging
                          id:'source'
                    }),
                   rootVisible: false
                                
             }]
            
   });

  */
   
    /*
    var elementosComunesPanel = new Ext.Panel({
              border:false,
              title: 'Elementos Comunes a las Pantallas'
    });
   */
   
    /*
   var gridPropiedadesPanel = new Ext.Panel({
             border:false,
             title: 'Grid de Propiedades'
   });
   */
   
    /*
   var arbolElementosPanel = new Ext.Panel({
             border:false,
             title: 'Arbol de Elementos'
   });
   */
   
    
   var navegacionPantallasPanel = new Ext.Panel({
             border:false,
             title: MENU_NAVEGACION_TITLE,
             autoScroll:true,
             items:[{
                    xtype: 'treepanel',
                    id:'treeNavegacion',
                    width: 190,
                    border : false, 
                    autoScroll: true,
                    split: true,
                    clearOnLoad : true,
                    lines: false,
                    loader: new Ext.tree.TreeLoader({
                           dataUrl:'<s:url value="../configuradorpantallas/obtenerTreeNavegacionPantallas.action"/>'
                            
                            
                    }), 
                    root: new Ext.tree.AsyncTreeNode({
                          text: MENU_NAVEGACION_ROOT, 
                          draggable:false, // disable root node dragging
                          id:'sourceP'
                    }),
                   rootVisible: false,
                   listeners: {
                              click: function(n) {
                              clavePantalla = n.attributes.id;
                              
                              getRegistroPantalla(clavePantalla);
                                storeRegistroPantalla.on('load', function(){
                                 if(storeRegistroPantalla.getTotalCount()==null || storeRegistroPantalla.getTotalCount()==""){
                                 }else{
                                    var recordP = storeRegistroPantalla.getAt(0);
                                    formPanelDatos.getForm().loadRecord(recordP);
                                     cdTipoMaster.setValue(recordP.get('htipoPantalla'));
                                        if(recordP.get('cdPantalla')==null || recordP.get('cdPantalla')=="")
                                            {
                                                 comboTipoPantalla.enable();
                                                 formPanelDatos.getForm().reset();
                                                 Ext.getCmp('btnEliminar').disable();
                                                 Ext.getCmp('btnNPantalla').disable();
                                                 Ext.getCmp('btnVistaPrevia').disable();
                                        }else{
                                                 comboTipoPantalla.disable();
                                                 Ext.getCmp('btnEliminar').enable();
                                                 Ext.getCmp('btnNPantalla').enable();
                                                 Ext.getCmp('btnVistaPrevia').enable();
                                                
                                                storeTipoPantalla.baseParams['cdProceso'] = cdProceso.getValue();
                                                storeTipoPantalla.load({
                                                
                                                        callback : function(r,options,success) {
                                                            if (!success) {
                                                             //  Ext.MessageBox.alert('Aviso','No se encontraron registros');  
                                                            storeProducto.removeAll();
                                                            } 
                                                         //   comboTipoPantalla.setValue(recordP.get('htipoPantalla'));
                                                             }
                                                            
                                                      });
                                       }
                                       
										//SI dsArchivo TRAE ELEMENTOS, LOS CARGAMOS EN EL EDITOR
										if(dsArchivo.getValue() != "{}"){
											var configuracion = null;
											configuracion = Ext.decode(dsArchivo.getValue());
                            				main.setConfig({items:[configuracion]});
                            			}
                            			//SINO, DEJAMOS VACIO EL EDITOR
                            			else{
                            				main.setConfig({items:[]});
                            			}
                                     }//else totalcount
                                     
                                   });                                   
                              }
                    }// end listeners
                                
             }]
             
            
   });
   
   /*
   var layoutPanel = new Ext.Panel({
   		border:false,
        title: 'Elementos Layout',
        autoScroll: true
   
   });
   */
   
   /*
   masterPanel.add(elementosComunesPanel);
   masterPanel.add(gridPropiedadesPanel);
   masterPanel.add(arbolElementosPanel);         
   masterPanel.add(atributosMasterPanel);
   masterPanel.add(navegacionPantallasPanel);
     */   
   
    /*
   var areaTrabajoPanel = new Ext.Panel({
        region: 'center',
        title: 'Area de Trabajo',
        split:true,
        items:[ejemAreaTrabajo]
    });
    */
    
/*************************************************************
** FieldSets del form de parametros (TAB 1)
**************************************************************/ 
var combosFieldSet = new Ext.form.FieldSet({
    id:'combosFieldSet',
    style:'margin:5px',
    labelWidth: 70,
    bodyStyle:'padding:5px',
    title: '', 
    height:130,
    hideBorders : true,
    items: [comboProceso, comboNivel, comboProducto, cdConjunto]//end items FieldSet
 }); // end FieldSet de combos

var datosFieldSet = new Ext.form.FieldSet({
    id:'datosFieldSet',
    style:'margin:5px',
    labelWidth: 100,
    bodyStyle:'padding:5px',
    title: '', 
     //autoHeight:true,
    height:130,  
    hideBorders : true,
    //defaultType: 'textfield',
    items: [nombreConjunto, descripcion]
    
           
 }); // end FieldSet de datos
 
 
 
 
/*************************************************************
** FormPanel del TAB 1
**************************************************************/ 
var formPanelParam = new Ext.form.FormPanel({                          
    id:'formPanelParam',
    layout:'column',
    border:false,
    columnWidth: .1,
    hideBorders : true,
    items: [{
            columnWidth:.4,
            layout: 'form',                     
            border: false,                      
            items:[combosFieldSet]//end items columna 1     
            },{
            columnWidth:.5,
            layout: 'form',                     
            border: false,                   
            items:[datosFieldSet]//end items columna2
           }],//end items FormPanel
    buttons:[{                               
           text:TAB_1_BOTON_SALVAR,
           id:'btnSalvarConjunto',
           handler: function() { 
                    if (formPanelParam.form.isValid()) {
                   //  configuradorPanel.form.submit({ 
                       formPanelParam.form.submit({ 
                       
                        //url:'confpantallas/salvarConjunto.action',
                       	url:'configuradorpantallas/salvarConjunto.action',
                        
                        waitMsg:TAB_1_BOTON_SALVAR_WAIT_MSG,
                                failure: function(form, action) {
                                         Ext.MessageBox.alert(TAB_1_BOTON_SALVAR_FAILURE, TAB_1_BOTON_SALVAR_FAILURE_DESC);
                                },
                                success: function(form, action) {
                                getRegistro();
                                storeRegistro.on('load', function(){
                                if(storeRegistro.getTotalCount()==null || storeRegistro.getTotalCount()==""){
                                }else{
                          
                                var record = storeRegistro.getAt(0);
                                formPanelParam.getForm().loadRecord(record);
                                cdProceso.setValue(record.get('cdProceso'));
                                cdProducto.setValue(record.get('cdProducto'));
                                cdCliente.setValue(record.get('cdCliente'));
                                cdProcesoActivacion.setValue(record.get('cdProceso'));
                                cdProductoActivacion.setValue(record.get('cdProducto'));
                                
                                comboTipoPantalla.clearValue();
                                storeTipoPantalla.baseParams['cdProceso'] = cdProceso.getValue();
                                storeTipoPantalla.load({
                                        callback : function(r,options,success) {
                                            if (!success) {
                                             //  Ext.MessageBox.alert('Aviso','No se encontraron registros');  
                                            storeProducto.removeAll();
                                            }  }
                                      });
                                      
                                Ext.getCmp('btnAceptarActivacion').enable();
                                Ext.getCmp('btnCopiarConjunto').enable();
                                Ext.getCmp('btnSalvarPantalla').enable();
                                
                                Ext.getCmp('btnEliminar').enable();
								Ext.getCmp('btnNPantalla').enable();
                                Ext.getCmp('btnVistaPrevia').enable();
                                }//else getTotalCount
                                });
                               
                                Ext.MessageBox.alert(TAB_1_BOTON_SALVAR_SUCCESS, TAB_1_BOTON_SALVAR_SUCCESS_DESC);
                                 
                                 comboProceso.disable();
                                 comboNivel.disable();
                                 comboProducto.disable();
                                 var treeN = Ext.getCmp('treeNavegacion');  
                                 treeN.root.reload();
                                      
                                  
                                }
                                  });   
                              } else{
                                   Ext.MessageBox.alert(TAB_1_BOTON_SALVAR_ERROR_VALIDA, TAB_1_BOTON_SALVAR_ERROR_VALIDA_DESC);
                              }
             }      //end handler    
            
           },{
           text: TAB_1_BOTON_NUEVO_CONJUNTO,
            handler: function() { 
                                 
                   formPanelParam.getForm().reset();
                   formPanelDatos.form.reset();
                   formPanelActivacion.form.reset();
                   comboProceso.enable();
                   comboNivel.enable();
                   comboProducto.enable();
                   comboTipoPantalla.enable();
                   formPanelParam.form.load({ 
                    //url:'confpantallas/nuevoConjunto.action',
                   	url:'/confpantallas/nuevoConjunto.action',
                    
                                failure: function(form, action) {
                                          var treeN = Ext.getCmp('treeNavegacion');  
                                          treeN.root.reload();
                                }
                   
                           });
                    comboTipoPantalla.clearValue();
                                storeTipoPantalla.baseParams['cdProceso'] = cdProceso.getValue();
                                storeTipoPantalla.load({
                                        callback : function(r,options,success) {
                                            if (!success) {
                                             //  Ext.MessageBox.alert('Aviso','No se encontraron registros');  
                                            storeProducto.removeAll();
                                            }  }
                                      });
                   Ext.getCmp('btnAceptarActivacion').disable();
                   Ext.getCmp('btnSalvarPantalla').disable();
                   Ext.getCmp('btnVistaPrevia').disable();
                   Ext.getCmp('btnCopiarConjunto').disable();
                   
                   //BORRAR LOS COMPONENTES DEL EDITOR
                   main.resetAll();
                   
            }//end handler
           },{
           text: TAB_1_BOTON_COPIAR_CONJUNTO,
           id:'btnCopiarConjunto',
                 handler: function() { 
                
                          if(cdConjunto.getValue()== null || cdConjunto.getValue()==""){
                           Ext.MessageBox.alert(TAB_1_BOTON_COPIAR_CONJUNTO_ERROR, TAB_1_BOTON_COPIAR_CONJUNTO_ERROR_DESC);
                          }else{
                           window.open ('entrarCopiarConjunto.action'+'?cdConjunto='+cdConjunto.getValue(),'','height=600,width=800')
                     
                          }
                  
             }      //end handler     
           }]// end buttons FormPanel
 });//end FormPanel


/*************************************************************
** FormPanel del TAB 2
**************************************************************/ 

var formPanelDatos = new Ext.form.FormPanel({                          
                        id:'formPanelDatos',
                        border:false,
                        labelWidth: 140,
                       // layout:'form',
                       layout:'table',
                       layoutConfig: {
                       columns: 3
                       },
                        margins:'5 5 0 5',
                        items: [{
                                border:false,
                                layout:'form',
                                colspan: 3,
                                width:800,
                                items:[cdPantalla]
                               },{    
                                border:false,
                                layout:'form',
                                colspan: 3,
                                width:800,
                                items:[nombrePantalla]
                              },{    
                                border:false,
                                layout:'form',
                                colspan: 3,
                                width:800,
                                items:[comboTipoPantalla]
                              },{    
                                border:false,
                                layout:'form',
                                colspan: 3,
                                width:800,
                                items:[descripcionPantalla]
                              },{ 
                                border:false,
                                layout:'form',
                                items:[cdProceso]
                              },{    
                                border:false,
                                layout:'form',
                                items:[cdProducto]
                              },{    
                                border:false,
                                layout:'form',
                                items:[cdTipoMaster]
                              },{   
                                border:false,
                                layout:'table',
                                items:[dsArchivo]
                              
                              
                               
                           }],//end items FormPanel
                        buttons:[{
                                 text: TAB_2_BOTON_SALVAR,
                                 id:'btnSalvarPantalla',
                                      handler: function() { 
                                        if (formPanelDatos.form.isValid()) {
												//Obtenemos el codigo JSON generado en el editor y lo almacenamos en 'dsArchivo'
												//getTreeConfig() y encode son metodos declarados en main.js
												var cleanConfig = main.getTreeConfig();
												cleanConfig = (cleanConfig.items?cleanConfig.items[0]||{}:{});
												cleanConfig = Main.JSON.encode(cleanConfig);
												dsArchivo.setValue(cleanConfig);
											
                                                formPanelDatos.form.submit({ 
                                                    //url:'confpantallas/salvarPantalla.action',
                                                	url:'configuradorpantallas/salvarPantalla.action',
                                                    
                                                    waitMsg: TAB_2_BOTON_SALVAR_WAIT_MSG,
                                                    failure: function(form, action) {
                                                        Ext.MessageBox.alert(TAB_2_BOTON_SALVAR_FAILURE, TAB_2_BOTON_SALVAR_FAILURE_DESC);
                                                    },
                                                    success: function(form, action) {
                                                        Ext.MessageBox.alert(TAB_2_BOTON_SALVAR_SUCCESS, TAB_2_BOTON_SALVAR_SUCCESS_DESC);
                                                        var treeN = Ext.getCmp('treeNavegacion');  
                                                        treeN.root.reload();
                                                        comboTipoPantalla.disable();
                                                    }
                                                 });                   
                                        } else{
                                             Ext.MessageBox.alert(TAB_2_BOTON_SALVAR_ERROR, TAB_2_BOTON_SALVAR_ERROR_DESC);
                                        }
                                    }      //end handler  
                                   
                                 },{
                                 text: TAB_2_BOTON_ELIMINAR,
                                 id:'btnEliminar',
                                 handler: function() { 
                                 Ext.MessageBox.buttonText.ok = TAB_2_BOTON_ELIMINAR_MSGBOX_BOTON_OK;
                                 Ext.MessageBox.buttonText.cancel = TAB_2_BOTON_ELIMINAR_MSGBOX_BOTON_CANCEL;
                                 Ext.Msg.show({
                                            title: TAB_2_BOTON_ELIMINAR_MSGBOX_TITLE,
                                            msg: TAB_2_BOTON_ELIMINAR_MSGBOX_MSG,
                                            buttons: Ext.Msg.OKCANCEL,
                                            fn: procesarResultado,
                                            icon: Ext.MessageBox.QUESTION
                                    
                                });
                                 
                                 function procesarResultado (btn){
                                    if(btn=='ok'){
                                            if (formPanelDatos.form.isValid()) {
                                                formPanelDatos.form.submit({ 
                                                    //url:'confpantallas/eliminarPantalla.action',
                                                	url:'configuradorpantallas/eliminarPantalla.action',
                                                    
                                                    waitMsg: TAB_2_BOTON_ELIMINAR_MSGBOX_BOTON_OK_WAIT_MSG,
                                                    failure: function(form, action) {
                                                        Ext.MessageBox.alert(TAB_2_BOTON_ELIMINAR_MSGBOX_BOTON_OK_FAILURE, TAB_2_BOTON_ELIMINAR_MSGBOX_BOTON_OK_FAILURE_DESC);
                                                    },
                                                    success: function(form, action) {
                                                        Ext.MessageBox.alert(TAB_2_BOTON_ELIMINAR_MSGBOX_BOTON_OK_SUCCESS, TAB_2_BOTON_ELIMINAR_MSGBOX_BOTON_OK_SUCCESS_DESC);
                                                        cdPantalla.reset();
                                                        nombrePantalla.reset();
                                                        comboTipoPantalla.reset();
                                                        descripcionPantalla.reset();
                                                        cdTipoMaster.reset();
                                                        
                                                        // formPanelDatos.form.reset();
                                                        Ext.getCmp('btnEliminar').disable();
                                                        Ext.getCmp('btnNPantalla').disable();
                                                        Ext.getCmp('btnVistaPrevia').disable();
                                                        comboTipoPantalla.enable();
                                                        var treeN = Ext.getCmp('treeNavegacion');  
                                                        treeN.root.reload();
                                                        
                                                        //BORRAR LOS COMPONENTES DEL EDITOR
                   										main.resetAll();
                                                    }
                                                 });                      
                                        } else{
                                             Ext.MessageBox.alert(TAB_2_BOTON_ELIMINAR_MSGBOX_BOTON_OK_ERROR, TAB_2_BOTON_ELIMINAR_MSGBOX_BOTON_OK_ERROR_DESC);
                                        }
                                    }else{
                                     }
                                     }//functionprocesarResultado
                                   }      //end handler  
                                 
                                 },{
                                 text: TAB_2_BOTON_NUEVA,
                                 id:'btnNPantalla',
                                        handler: function() { 
                                            Ext.getCmp('btnEliminar').disable();
                                            Ext.getCmp('btnNPantalla').disable();
                                            Ext.getCmp('btnVistaPrevia').disable();
                                              cdPantalla.reset();
                                              nombrePantalla.reset();
                                              comboTipoPantalla.reset();
                                              descripcionPantalla.reset();
                                              cdTipoMaster.reset();
                                          //  formPanelDatos.form.reset();
                                             comboTipoPantalla.enable();
                                               formPanelDatos.form.submit({ 
                                               url:'configuradorpantallas/nuevaPantalla.action'
                                            });
                                            //BORRAR LOS COMPONENTES DEL EDITOR
                   							main.resetAll();
                                 }//end handler
                                 },{
                                 text: TAB_2_BOTON_VISTA,
                                 id:'btnVistaPrevia',
                                 handler: function() { 
                                // windowVistaPrevia.show();
                                 formPanelDatos.form.load({ 
                                               //url:'confpantallas/obtenerComponente.action'+'?componente='+Ext.get('ejemAreaTrabajo').dom.value,
                                               url:'configuradorpantallas/obtenerComponente.action'+'?componente='+Ext.get('FBBuilderPanel').dom.value,
                                               waitMsg: TAB_2_BOTON_VISTA_WAIT_MSG,
                                                    failure: function(form, action) {
                                                    componente=Ext.util.JSON.decode(action.response.responseText).componente;
                                                   // Ext.MessageBox.alert('Componente', componente);
                               
                                                        windowVistaPrevia.show();
                                                      }
                                            });
                                            
                                 
                                  }//end handler
                              },{
                				// este boton el handler invoca al atributo undo...                        
                				id      : 'FBUndoBtn',
                				//iconCls : 'icon-undo',
                				text    : TAB_2_BOTON_DESHACER,
                				disabled: true,
                				tooltip : TAB_2_BOTON_DESHACER_TOOLTIP,
                				scope   : this,
				                handler :  function() {
				                	main.undo();
				                }
            				}]// end buttons FormPanel
});//end FormPanel


/*************************************************************
** FormPanel del TAB 3
**************************************************************/ 
var formPanelActivacion = new Ext.form.FormPanel({                          
                        id:'formPanelActivacion',
                        border:false,
                        hideBorders : true,
                        labelWidth: 50,
                        layout:'form',
                        buttonAlign : 'left',
                        bodyStyle:'margin-top: 10px; margin-left: 10px; margin-bottom: 20px;',
                        items: [comboActivacion, cdProcesoActivacion, cdCliente, cdProductoActivacion
                           ],//end items FormPanel
                        buttons:[{
                                 text: TAB_3_BOTON_ACEPTAR,
                                 id:'btnAceptarActivacion',
                                 handler: function() { 
                                   Ext.MessageBox.buttonText.ok = TAB_3_BOTON_ACEPTAR_MSGBOX_BOTON_OK;
                                     Ext.MessageBox.buttonText.cancel = TAB_3_BOTON_ACEPTAR_MSGBOX_BOTON_CANCEL;
                                     Ext.Msg.show({
                                                title: TAB_3_BOTON_ACEPTAR_MSGBOX_TITLE,
                                                msg: TAB_3_BOTON_ACEPTAR_MSGBOX_MSG,
                                                buttons: Ext.Msg.OKCANCEL,
                                                fn: procesarResult,
                                                icon: Ext.MessageBox.QUESTION
                                        
                                    });
                                     
                                     function procesarResult (btn){
                                        if(btn=='ok'){
                                         if (formPanelActivacion.form.isValid()) {
                                                    formPanelActivacion.form.submit({ 
                                                       url:'configuradorpantallas/activarConjunto.action',
                                                        waitMsg: TAB_3_BOTON_ACEPTAR_MSGBOX_BOTON_OK_WAIT_MSG,
                                                        failure: function(form, action) {
                                                            Ext.MessageBox.alert(TAB_3_BOTON_ACEPTAR_MSGBOX_BOTON_OK_FAILURE, TAB_3_BOTON_ACEPTAR_MSGBOX_BOTON_OK_FAILURE_DESC);
                                                        },
                                                        success: function(form, action) {
                                                            Ext.MessageBox.alert(TAB_3_BOTON_ACEPTAR_MSGBOX_BOTON_OK_SUCCESS, TAB_3_BOTON_ACEPTAR_MSGBOX_BOTON_OK_SUCCESS_DESC);
                                                           
                                                        }
                                                     });
                                            } else{
                                                 Ext.MessageBox.alert(TAB_3_BOTON_ACEPTAR_MSGBOX_BOTON_OK_ERROR, TAB_3_BOTON_ACEPTAR_MSGBOX_BOTON_OK_ERROR_DESC);
                                            }
                                         }else{
                                         }
                                         }//functionprocesarResult
                                    }      //end handler  
                            
                      }]//end buttons FormPanel
});//end FormPanel

/*************************************************************
** Paneles del TAB (3)
**************************************************************/ 
var  parametrosPanel = new Ext.Panel({
                id:'parametrosPanel',
                title: TAB_1_TITLE,
                layout:'form',
                items: [formPanelParam]// end items panel
 });//end panel Parametros Conjunto
   

var  datosPantallaPanel = new Ext.Panel({
                id:'datosPantallaPanel',
                title: TAB_2_TITLE,
                layout:'form',
                items: [formPanelDatos]// end items panel
 });//end panel Datos de Pantalla

var  activacionConjuntoPanel = new Ext.Panel({
                title: TAB_3_TITLE,
                layout:'form',
                items: [formPanelActivacion]// end items panel
 });//end panel Activacion de Conjunto
   
/*************************************************************
** TabPanel
**************************************************************/ 
var tabs1 = new Ext.TabPanel({
        renderTo: document.body,
        activeTab: 0,
        bodyStyle:'padding:10px',
        deferredRender:false,
        plain:true,
        height:235,
        items:[parametrosPanel, datosPantallaPanel, activacionConjuntoPanel ]
    });

  paramEntradaPanel.add(tabs1);


        
/*************************************************************
** FormPanel de Ventana de vista previa
**************************************************************/       
 
var vistaPreviaFormPanel =  new Ext.form.FormPanel({                          
   id:'vistaPreviaFormPanel',
   border:false,
   layout:'form',
 <%--
  <s:if test="componente!=null">  
  	<s:component template="entradaCotizacion.vm" templateDir="templates" theme="components" />
  </s:if>
  
  <s:else>  
  	<s:component template="vistaPrevia.vm" templateDir="templates" theme="components" >
  		<s:param name="componente" value="''" /> 
  	</s:component>  
 </s:else>
 
   ,
--%>
   listeners: {
            beforerender : function() {
             seleccionarComponente();
              }//beforerender
       }
 });//end FormPanel


var windowVistaPrevia = new Ext.Window({
            title: WINDOW_1_VISTA_PREVIA_TITLE,
            plain:true,
            width: 800,
            height:300,
            minWidth: 800,
            minHeight: 300,
            layout: 'fit',
            bodyStyle:'padding:5px;',
            buttonAlign:'center',
            modal:true,
            closable : false,
            items: vistaPreviaFormPanel,
             buttons: [{
               text: WINDOW_1_VISTA_PREVIA_BOTON_CERRAR,
               handler: function() { 
               windowVistaPrevia.hide(); 
               }
          }]
            
        });



/*************************************************************
** viewport
**************************************************************/ 
    /*
    var viewport = new Ext.Viewport({
        layout: 'border',
        listeners: {
            beforerender : function() {
                      getRegistro();
                       storeRegistro.on('load', function(){
                       if(storeRegistro.getTotalCount()==null || storeRegistro.getTotalCount()==""){
                       }else{
                       var record = storeRegistro.getAt(0);
                       formPanelParam.getForm().loadRecord(record);
                       cdProducto.setValue(record.get('cdProducto'));
                       cdCliente.setValue(record.get('cdCliente'));
                       cdProcesoActivacion.setValue(record.get('cdProceso'));
                       cdProductoActivacion.setValue(record.get('cdProducto'));
                       cdProceso.setValue(record.get('cdProceso'));
                       Ext.getCmp('btnEliminar').disable();
                       Ext.getCmp('btnNPantalla').disable();
                       Ext.getCmp('btnVistaPrevia').disable();
                            if(record.get('cdConjunto')==null || record.get('cdConjunto')=="")
                            {
                                comboProceso.enable();
                                comboNivel.enable();
                                comboProducto.enable();
                                formPanelParam.getForm().reset();
                                formPanelDatos.getForm().reset();
                                formPanelActivacion.form.reset();
                                Ext.getCmp('btnCopiarConjunto').disable();
                                comboTipoPantalla.clearValue();
                                storeTipoPantalla.baseParams['cdProceso'] = cdProceso.getValue();
   	                            storeTipoPantalla.load({
                                        callback : function(r,options,success) {
                                            if (!success) {
                                             //  Ext.MessageBox.alert('Aviso','No se encontraron registros');  
                                            storeProducto.removeAll();
                                            }  }
                                      });
                                
                                Ext.getCmp('btnAceptarActivacion').disable();
                                Ext.getCmp('btnSalvarPantalla').disable();
                               
                                
                             }else{
                                comboProceso.disable();
                                comboNivel.disable();
                                comboProducto.disable();
                                
                                Ext.getCmp('btnCopiarConjunto').enable();
                                 comboTipoPantalla.clearValue();
                                storeTipoPantalla.baseParams['cdProceso'] = cdProceso.getValue();
                                storeTipoPantalla.load({
                                        callback : function(r,options,success) {
                                            if (!success) {
                                             //  Ext.MessageBox.alert('Aviso','No se encontraron registros');  
                                            storeProducto.removeAll();
                                            }  }
                                      });
                                
                                Ext.getCmp('btnAceptarActivacion').enable();
                                Ext.getCmp('btnSalvarPantalla').enable();
                                
                            }
                            
            }//else getTotalCount not null                
         });
        
       }//beforerender
       },
       
         items:[paramEntradaPanel, masterPanel, areaTrabajoPanel] 
    });
    
    */
    


        /*
 var configuradorPanel = new Ext.form.FormPanel({
         layout: 'fit'
            });

 configuradorPanel.add(viewport);
 configuradorPanel.render(document.body);
*/
        

                                        

