<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head>

<script type="text/javascript"> 

    function seleccionarComponente()
    {
     
     document.getElementsByName('componente').value = document.getElementById('ejemAreaTrabajo').value;
     // alert("componente.. " + document.getElementsByName('componente').value);
     } 
  
</script>


<script type="text/javascript">

Ext.onReady(function(){
/*
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";
*/
var componente = ' ';


/*************************************************************
** Combo Activacion de Conjunto
**************************************************************/ 
var myData = [
        ['1','Activar'],
        ['2','Desactivar']
   ];

var storeActivacion = new Ext.data.SimpleStore({
        fields: [
           {name: 'idAccion'},
           {name: 'descripcion'}
          
       ]
    });
storeActivacion.loadData(myData);



/*************************************************************
** Store combos
**************************************************************/ 
/*
var storeProceso = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: 'confpantallas/obtenerListasParamConjunto.action'
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
                url: 'confpantallas/obtenerListasParamConjunto.action'
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
       
       
var storeProducto;
function test(){   
    url='confpantallas/obtenerListaProductos.action'+'?hcliente='+ comboNivel.getValue();
    storeProducto = new Ext.data.Store({
           proxy: new Ext.data.HttpProxy({
               url: url
           
          }),
            reader: new Ext.data.JsonReader({
            root: 'productoList',
            id: 'value'
            },[
           {name: 'value', type: 'string',mapping:'value'},
           {name: 'label', type: 'string',mapping:'label'}    
            ]),
            remoteSort: true
        });
      
        storeProducto.setDefaultSort('value', 'desc');
        storeProducto.load();
        return storeProducto;
        
        
 }
 
 
var storeRegistro;
function getRegistro(){   
storeRegistro = new Ext.data.Store({
         proxy: new Ext.data.HttpProxy({
               url: 'confpantallas/obtenerRegistro.action'
           
          }),
            reader: new Ext.data.JsonReader({
            root: 'registroList',
            id: 'cdConjunto'
            },[
           {name: 'cdConjunto', type: 'string',mapping:'cdConjunto'},
           {name: 'proceso', type: 'string',mapping:'proceso'},
           {name: 'cdProceso', type: 'string',mapping:'cdProceso'},
           {name: 'cdProducto', type: 'string',mapping:'cdRamo'},
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
               url: 'confpantallas/obtenerRegistroPantalla.action'+'?idP='+clavePantalla
           
          }),
            reader: new Ext.data.JsonReader({
            root: 'registroPantallaList',
            id: 'cdPantalla'
            },[
           {name: 'cdPantalla', type: 'string',mapping:'cdPantalla'},
           {name: 'cdProceso', type: 'string',mapping:'cdMaster'},
           {name: 'nombrePantalla', type: 'string',mapping:'nombrePantalla'},
           {name: 'htipoPantalla', type: 'string',mapping:'cdMaster'},
           {name: 'descripcionPantalla', type: 'string',mapping:'descripcion'}
            ]),
            remoteSort: true
        });
        storeRegistroPantalla.load();
        return storeRegistroPantalla;
}
*/

/*
var storeTipoPantalla;
var idProceso= ' ';
function getTiposPantallas(idProceso){  
url =  'confpantallas/obtenerMasters.action'+'?cdProceso='+idProceso;
 storeTipoPantalla = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: url
            }),
            reader: new Ext.data.JsonReader({
            root: 'tipoPantallaList',
            id: 'cdMaster'
            },[
           {name: 'cdTipo', type: 'string', mapping:'cdTipo'},
           {name: 'cdMaster', type: 'string', mapping:'cdMaster'},
           {name: 'dsMaster', type: 'string', mapping:'dsMaster'}    
             ]),
            remoteSort: true
        });
        storeTipoPantalla.setDefaultSort('cdMaster', 'desc');
        storeTipoPantalla.load();
        return storeTipoPantalla;
 }
*/

 /*************************************************************
** Combos del combosFieldSet (funcion onchange)
**************************************************************/ 
/*
var comboProceso = new Ext.form.ComboBox({
    name: 'proceso',
    id: 'proceso',
    fieldLabel: 'Proceso',
    store: storeProceso,
    displayField:'label',
    hiddenName: 'hproceso',  
    valueField:'value',
    typeAhead: true,
    mode: 'local',
    triggerAction: 'all',
    listWidth : 200,
    width:200,
    emptyText:'Seleccione...',
    editable:false,
    allowBlank: false,
    labelSeparator:'', 
    selectOnFocus:true
});




 var comboNivel = new Ext.form.ComboBox({
     name: 'cliente',
     id: 'cliente',
     fieldLabel: 'Nivel',
     store: storeCliente,
     displayField:'dsCliente',
     hiddenName: 'hcliente',  
     valueField:'cdCliente',
     typeAhead: true,
     listWidth : 200,
     width: 200,
     mode: 'local',
     triggerAction: 'all',
     emptyText:'Seleccione...',
     editable:false,
     allowBlank: false,
     labelSeparator:'', 
     selectOnFocus:true
  });
  
  
 var comboProducto = new Ext.form.ComboBox({
     name: 'producto',
     id: 'producto',
     fieldLabel: 'Producto',
     store: test(),
     displayField:'label',
     hiddenName: 'hproducto',  
     valueField:'value',
     typeAhead: true,
     listWidth : 200,
     width: 200,
     mode: 'local',
     triggerAction: 'all',
     emptyText:'Seleccione...',
     editable:false,
     labelSeparator:'',  
     selectOnFocus:true
  });
  
  comboNivel.on('select', function(){
    comboProducto.store =  test();
   });
  */
  
/*************************************************************
** Textfields del datosFieldSet
**************************************************************/ 
/*
var nombreConjunto = new Ext.form.TextField({
     fieldLabel: 'Nombre Conjunto de Pantallas',
     name:'nombreConjunto',
     allowBlank: false,
     width : 250,
     maxLengthText:'30'
   });

var descripcion = new Ext.form.TextArea({
    fieldLabel: 'Descripcion Breve',
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
 
 */
 
/*************************************************************
** Textfields y combo del FormPanel del TAB 2
**************************************************************/ 
/*
var nombrePantalla = new Ext.form.TextField({
     fieldLabel: 'Nombre de Pantalla',
     name:'nombrePantalla',
     allowBlank: false,
     width : 250,
     maxLengthText:'30'
   });
 
 var descripcionPantalla = new Ext.form.TextArea({
     fieldLabel: 'Descripcion de la Pantalla',
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
    fieldLabel: 'Pantalla Master',
    store: getTiposPantallas(),
    displayField:'dsMaster',
    hiddenName: 'htipoPantalla',  
    valueField:'cdMaster',
    typeAhead: true,
    mode: 'local',
    triggerAction: 'all',
    listWidth : 240,
    width:240,
    emptyText:'Seleccione...',
    editable:false,
    autoHeight:'true',
    allowBlank: false,
    labelSeparator:'', 
    selectOnFocus:true
});
   
   
   var cdProceso = new Ext.form.TextField({
    name:'cdProceso',
    id:'cdProceso',
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
 */
 var cdProducto = new Ext.form.TextField({
    name:'cdProducto',
    id:'cdProducto',
    type:'hidden',
    hidden:true,
    labelSeparator:''
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
/* 
   var comboActivacion = new Ext.form.ComboBox({
    name: 'activacion',
    id: 'activacion',
    fieldLabel: 'Acción',
    listWidth : 200,
    store: storeActivacion,
    displayField:'descripcion',
    hiddenName: 'hactivacion',  
    valueField:'idAccion',
    typeAhead: true,
    mode: 'local',
    triggerAction: 'all',
    width:200,
    emptyText:'Seleccione...',
    editable:false,
    autoHeight:'true',
    allowBlank: false,
    selectOnFocus:true
});
 */
 /*
  var ejemAreaTrabajo = new Ext.form.TextField({
     fieldLabel: 'Dato ejemplo',
     type:'hidden',
     hidden:true,
     id:'ejemAreaTrabajo',
     value: 'items:[{xtype:\"textfield\", fieldLabel: \"Ejemplo area\", name:\"ejemplo1\"  }]',
     name:'ejemAreaTrabajo'
    
   });
 */
/*************************************************************
** Paneles del viewport(3):parametros, master, area de trabajo
**************************************************************/
   /*
   var paramEntradaPanel = new Ext.Panel({
        region: 'north',
        title: 'PARAMETROS DE ENTRADA',
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
 */
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
                           dataUrl:'<s:url value="../confpantallas/obtenerAtributosMaster.action"/>'
                            
                            
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
   
   var navegacionPantallasPanel = new Ext.Panel({
             border:false,
             title: 'Navegacion de Pantallas',
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
                           dataUrl:'<s:url value="../confpantallas/obtenerTreeNavegacionPantallas.action"/>'
                            
                            
                    }), 
                    root: new Ext.tree.AsyncTreeNode({
                          text: 'Pantallas', 
                          draggable:false, // disable root node dragging
                          id:'sourceP'
                    }),
                   rootVisible: false,
                   listeners: {
                              click: function(n) {
                              clavePantalla = n.attributes.id;
                              
                              getRegistroPantalla(clavePantalla);
                                storeRegistroPantalla.on('load', function(){
                                    var recordP = storeRegistroPantalla.getAt(0);
                                     formPanelDatos.getForm().loadRecord(recordP);
                                        if(recordP.get('cdPantalla')==null || recordP.get('cdPantalla')=="")
                                            {
                                                 comboTipoPantalla.enable();
                                                 formPanelDatos.getForm().reset();
                                                 Ext.get('btnEliminar').dom.disabled = true;
                                                 Ext.get('btnNPantalla').dom.disabled = true;
                                        }else{
                                                comboTipoPantalla.disable();
                                                Ext.get('btnEliminar').dom.disabled = false;
                                                Ext.get('btnNPantalla').dom.disabled = false;
                                                idProceso=Ext.get('cdP').dom.value;
                                                comboTipoPantalla.store = getTiposPantallas(idProceso);
                                        }
                                
                                      
                                   });                                   
                              }
                    }// end listeners
                                
             }]
             
            
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
/*
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
 */
 
 
 
/*************************************************************
** FormPanel del TAB 1
**************************************************************/ 
/*
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
           text:'Salvar',
           handler: function() { 
                    if (formPanelParam.form.isValid()) {
                   //  configuradorPanel.form.submit({ 
                       formPanelParam.form.submit({ 
                       
                        url:'confpantallas/salvarConjunto.action',
                        waitMsg:'Procesando...',
                                failure: function(form, action) {
                                         Ext.MessageBox.alert('Conjunto no salvado', 'Se produjo error');
                                },
                                success: function(form, action) {
                                getRegistro();
                                storeRegistro.on('load', function(){
                                if(storeRegistro.getTotalCount()==null || storeRegistro.getTotalCount()==""){
                                }else{
                          
                                var record = storeRegistro.getAt(0);
                                formPanelParam.getForm().loadRecord(record);
                                idProceso=record.get('cdProceso');
                                cdProceso.setValue(record.get('cdProceso'));
                                cdProducto.setValue(record.get('cdProducto'));
                                comboTipoPantalla.store = getTiposPantallas(idProceso);
                                }//else getTotalCount
                                });
                               
                                Ext.MessageBox.alert('Conjunto salvado', 'existosamente');
                                 
                                 comboProceso.disable();
                                 comboNivel.disable();
                                 comboProducto.disable();
                                 var treeN = Ext.getCmp('treeNavegacion');  
                                 treeN.root.reload();
                                      
                                  
                                }
                                  });   
                              } else{
                                   Ext.MessageBox.alert('Error', 'Por favor revise los errores');
                              }
             }      //end handler    
            
           },{
           text:'Nuevo Conjunto Pantallas',
            handler: function() { 
                                 
                   formPanelParam.getForm().reset();
                   formPanelDatos.form.reset();
                   comboProceso.enable();
                   comboNivel.enable();
                   comboProducto.enable();
                   comboTipoPantalla.enable();
                   formPanelParam.form.load({ 
                    url:'confpantallas/nuevoConjunto.action',
                                failure: function(form, action) {
                                          var treeN = Ext.getCmp('treeNavegacion');  
                                          treeN.root.reload();
                                }
                   
                           });
                   idProceso=Ext.get('cdProceso').dom.value;
                    comboTipoPantalla.store = getTiposPantallas(idProceso);
                 
            }//end handler
           },{
           text:'Copiar Conjunto',
           id:'btnCopiarConjunto',
                 handler: function() { 
                
                          if(cdConjunto.getValue()== null || cdConjunto.getValue()==""){
                           Ext.MessageBox.alert('Error', 'Conjunto no creado');
                          }else{
                           window.open ('entrarCopiarConjunto.action'+'?cdConjunto='+cdConjunto.getValue(),'','height=600,width=800')
                     
                          }
                  
             }      //end handler     
           }]// end buttons FormPanel
 });//end FormPanel
*/

/*************************************************************
** FormPanel del TAB 2
**************************************************************/ 
/*
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
                                width:400,
                                 items:[cdProceso]
                              },{    
                                border:false,
                                layout:'form',
                                colspan: 2,
                                width:400,
                                items:[cdProducto]
                               
                           }],//end items FormPanel
                        buttons:[{                               
                                 text:'Salvar',
                                      handler: function() { 
                                        if (formPanelDatos.form.isValid()) {
                                                formPanelDatos.form.submit({ 
                                                    url:'confpantallas/salvarPantalla.action',
                                                    waitMsg:'Procesando...',
                                                    failure: function(form, action) {
                                                        Ext.MessageBox.alert('Pantalla no salvada', 'Se produjo error');
                                                    },
                                                    success: function(form, action) {
                                                        Ext.MessageBox.alert('Pantalla salvada', 'existosamente');
                                                        var treeN = Ext.getCmp('treeNavegacion');  
                                                        treeN.root.reload();
                                                        comboTipoPantalla.disable();
                                                    }
                                                 });                   
                                        } else{
                                             Ext.MessageBox.alert('Error', 'Por favor revise los errores');
                                        }
                                    }      //end handler  
                                   
                                 },{
                                 text:'Eliminar Pantalla',
                                 id:'btnEliminar',
                                 handler: function() { 
                                 Ext.MessageBox.buttonText.ok = 'Aceptar';
                                 Ext.MessageBox.buttonText.cancel = 'Cancelar';
                                 Ext.Msg.show({
                                            title:'Eliminar pantalla',
                                            msg: '¿Desea eliminar esta pantalla?',
                                            buttons: Ext.Msg.OKCANCEL,
                                            fn: procesarResultado,
                                            icon: Ext.MessageBox.QUESTION
                                    
                                });
                                 
                                 function procesarResultado (btn){
                                    if(btn=='ok'){
                                            if (formPanelDatos.form.isValid()) {
                                                formPanelDatos.form.submit({ 
                                                    url:'confpantallas/eliminarPantalla.action',
                                                    waitMsg:'Procesando...',
                                                    failure: function(form, action) {
                                                        Ext.MessageBox.alert('Pantalla no eliminada', 'Se produjo error');
                                                    },
                                                    success: function(form, action) {
                                                        Ext.MessageBox.alert('Pantalla eliminada', 'existosamente');
                                                        formPanelDatos.form.reset();
                                                        Ext.get('btnEliminar').dom.disabled = true;
                                                        Ext.get('btnNPantalla').dom.disabled = true;
                                                        comboTipoPantalla.enable();
                                                        var treeN = Ext.getCmp('treeNavegacion');  
                                                        treeN.root.reload();
                                                    }
                                                 });                   
                                        } else{
                                             Ext.MessageBox.alert('Error', 'Por favor revise los errores');
                                        }
                                    }else{
                                     }
                                     }//functionprocesarResultado
                                   }      //end handler  
                                 
                                 },{
                                 text:'Nueva Pantalla',
                                 id:'btnNPantalla',
                                        handler: function() { 
                                            Ext.get('btnEliminar').dom.disabled = true;
                                            Ext.get('btnNPantalla').dom.disabled = true;
                                            formPanelDatos.form.reset();
                                             comboTipoPantalla.enable();
                                               formPanelDatos.form.submit({ 
                                               url:'confpantallas/nuevaPantalla.action'
                                            });
                                            
                                            
                                 }//end handler
                                 },{
                                 text:'Vista Previa de Pantalla',
                                 handler: function() { 
                                // windowVistaPrevia.show();
                                 formPanelDatos.form.load({ 
                                               url:'confpantallas/obtenerComponente.action'+'?componente='+Ext.get('ejemAreaTrabajo').dom.value,
                                               waitMsg:'Procesando...',
                                                    failure: function(form, action) {
                                                    componente=Ext.util.JSON.decode(action.response.responseText).componente;
                                                   // Ext.MessageBox.alert('Componente', componente);
                               
                                                        windowVistaPrevia.show();
                                                      }
                                            });
                                            
                                 
                                  }//end handler
                              }]// end buttons FormPanel
});//end FormPanel
*/

/*************************************************************
** FormPanel del TAB 3
**************************************************************/ 
/*
var formPanelActivacion = new Ext.form.FormPanel({                          
                        id:'formPanelActivacion',
                        border:false,
                        hideBorders : true,
                        labelWidth: 50,
                        layout:'form',
                        buttonAlign : 'left',
                        bodyStyle:'margin-top: 10px; margin-left: 10px; margin-bottom: 20px;',
                        items: [comboActivacion
                           ],//end items FormPanel
                        buttons:[{    
                                 text:'Aceptar',
                                 handler: function() { 
                                    if (formPanelActivacion.form.isValid()) {
                                                formPanelActivacion.form.submit({ 
                                                   url:'confpantallas/activarConjunto.action',
                                                    waitMsg:'Procesando...',
                                                    failure: function(form, action) {
                                                        Ext.MessageBox.alert('Activacion no realizada', 'Se produjo error');
                                                    },
                                                    success: function(form, action) {
                                                        Ext.MessageBox.alert('Activacion realizada', 'existosamente');
                                                       
                                                    }
                                                 });                   
                                        } else{
                                             Ext.MessageBox.alert('Error', 'Por favor revise los errores');
                                        }
                                    }      //end handler  
                            
                      }]//end buttons FormPanel
});//end FormPanel
*/
/*************************************************************
** Paneles del TAB (3)
**************************************************************/ 
/*
var  parametrosPanel = new Ext.Panel({
                id:'parametrosPanel',
                title: 'Parametros Conjunto',
                layout:'form',
                items: [formPanelParam]// end items panel
 });//end panel Parametros Conjunto
   

var  datosPantallaPanel = new Ext.Panel({
                id:'datosPantallaPanel',
                title: 'Datos de Pantalla',
                layout:'form',
                items: [formPanelDatos]// end items panel
 });//end panel Datos de Pantalla

var  activacionConjuntoPanel = new Ext.Panel({
                title: 'Activacion de Conjunto',
                layout:'form',
                items: [formPanelActivacion]// end items panel
 });//end panel Activacion de Conjunto
  */ 
/*************************************************************
** TabPanel
**************************************************************/ 
/*
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
*/

        
/*************************************************************
** FormPanel de Ventana de vista previa
**************************************************************/       
 /*
var vistaPreviaFormPanel =  new Ext.form.FormPanel({                          
   id:'vistaPreviaFormPanel',
   border:false,
   layout:'form',
   
 //  <s:set name="componente" value="document.getElementById('ejemAreaTrabajo');" />
 
  <s:if test="componente!=null">
  
  <s:component template="entradaCotizacion.vm" templateDir="templates" theme="components" />
  </s:if>
  <s:else>
  
  
 
 
  <s:component template="vistaPrevia.vm" templateDir="templates" theme="components" >
  <s:param name="componente" value="''" /> 
  </s:component>  
   </s:else>
   ,listeners: {
            beforerender : function() {
             seleccionarComponente();
              }//beforerender
       }
 });//end FormPanel


var windowVistaPrevia = new Ext.Window({
            title: 'VISTA PREVIA',
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
               text:'Cerrar',
               handler: function() { 
               windowVistaPrevia.hide(); 
               }
          }]
            
        });

*/

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
                       cdProceso.setValue(record.get('cdProceso'));
                       cdProducto.setValue(record.get('cdProducto'));
                       idProceso=record.get('cdProceso');
                         
                            if(record.get('cdConjunto')==null || record.get('cdConjunto')=="")
                            {
                                comboProceso.enable();
                                comboNivel.enable();
                                comboProducto.enable();
                                formPanelParam.getForm().reset();
                                formPanelDatos.getForm().reset();
                                Ext.get('btnCopiarConjunto').dom.disabled = true;
                                idProceso=Ext.get('cdP').dom.value;
                                comboTipoPantalla.store = getTiposPantallas(idProceso);
                                
                             }else{
                                comboProceso.disable();
                                comboNivel.disable();
                                comboProducto.disable();
                                Ext.get('btnCopiarConjunto').dom.disabled = false;
                                comboTipoPantalla.store = getTiposPantallas(idProceso);
                            }
                            
            }//else getTotalCount not null                
         });
         clavePantalla = ' ';
          getRegistroPantalla(clavePantalla);
          storeRegistroPantalla.on('load', function(){
                      var recordP = storeRegistroPantalla.getAt(0);
                       formPanelDatos.getForm().loadRecord(recordP);
                            if(recordP.get('cdPantalla')==null || recordP.get('cdPantalla')=="")
                            {
                                comboTipoPantalla.enable();
                                formPanelDatos.getForm().reset();
                                Ext.get('btnEliminar').dom.disabled = true;
                                Ext.get('btnNPantalla').dom.disabled = true;
                                  
                              
                             }else{
                                comboTipoPantalla.disable();
                                Ext.get('btnEliminar').dom.disabled = false;
                                Ext.get('btnNPantalla').dom.disabled = false;
                                idProceso=Ext.get('cdP').dom.value;
                               
                                comboTipoPantalla.store = getTiposPantallas(idProceso);
                             }
                                
         });
         
  
       }//beforerender
       },
       
         items:[paramEntradaPanel, masterPanel, areaTrabajoPanel] 
    });
    

 var configuradorPanel = new Ext.form.FormPanel({
         layout: 'fit'
            });

 configuradorPanel.add(viewport);
 configuradorPanel.render(document.body);

        */
});
                                        

</script>

</head>
<body>


</body>
</html>