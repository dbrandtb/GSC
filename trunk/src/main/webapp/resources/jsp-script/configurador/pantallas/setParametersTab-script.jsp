<%@ include file="/taglibs.jsp"%>



//<script type="text/javascript">


/*************************************************************
** setting action names to url variables... 
**************************************************************/ 

var _PROCESS_URL = '<s:url action="obtenerListasParamConjunto" namespace="confbeta" />';

var _CUSTOMER_URL ='<s:url action="obtenerListasParamConjunto" namespace="confbeta" />';

var _PRODUCT_URL = '';

var _RECORD_URL = '<s:url action="obtenerRegistro" namespace="confbeta" />';

var _SAVE_SET_URL = '<s:url action="salvarConjunto" namespace="confbeta" />';

var _NEW_SET_URL = '<s:url action="nuevoConjunto" namespace="confbeta" />';


/*************************************************************
** Combo Store Section
**************************************************************/ 

var clavePantalla = ' ';

var storeProceso = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                // url: 'confbeta/obtenerListasParamConjunto.action'
                url: _PROCESS_URL
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
                // url: 'confbeta/obtenerListasParamConjunto.action'
                url: _CUSTOMER_URL
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
    
    //<s:url action="obtenerListaProductos" namespace="confbeta" id="productosUrl" includeParams="get" >
      //  <s:param name="hcliente">comboNivel.getValue()</s:param>
    //</s:url>
     
    url='confbeta/obtenerListaProductos.action'+'?hcliente='+ comboNivel.getValue();
    
    storeProducto = new Ext.data.Store({
           proxy: new Ext.data.HttpProxy({
               //url: url
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
               // url: 'confbeta/obtenerRegistro.action'
               url: _RECORD_URL           
          }),
            reader: new Ext.data.JsonReader({
            root: 'registroList',
            id: 'cdConjunto'
            },[
           {name: 'cdConjunto', type: 'string',mapping:'cdConjunto'},
           {name: 'proceso', type: 'string',mapping:'proceso'},
           {name: 'cdProceso', type: 'string',mapping:'cdProceso'},
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
function getRegistroPantalla(clavePantalla){   
storeRegistroPantalla = new Ext.data.Store({
         proxy: new Ext.data.HttpProxy({
               url: 'confbeta/obtenerRegistroPantalla.action'+'?idP='+clavePantalla
           
          }),
            reader: new Ext.data.JsonReader({
            root: 'registroPantallaList',
            id: 'cdPantalla'
            },[
           {name: 'cdPantalla', type: 'string',mapping:'cdPantalla'},
           {name: 'cdProceso', type: 'string',mapping:'cdMaster'},
           {name: 'nombrePantalla', type: 'string',mapping:'nombrePantalla'},
           {name: 'tipoPantalla', type: 'string',mapping:'cdMaster'},
           {name: 'descripcionPantalla', type: 'string',mapping:'descripcion'}
            ]),
            remoteSort: true
        });
        storeRegistroPantalla.load();
        return storeRegistroPantalla;
}


 /*************************************************************
** Combos from combosFieldSet (funcion onchange)
**************************************************************/ 

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
    width:200,
    emptyText:'Seleccione...',
    editable:true,
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
     width: 200,
     mode: 'local',
     triggerAction: 'all',
     emptyText:'Seleccione...',
     editable:true,
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
     width: 200,
     mode: 'local',
     triggerAction: 'all',
     emptyText:'Seleccione...',
     editable:true,
     labelSeparator:'',  
     selectOnFocus:true
  });
  
  comboNivel.on('select', function(){
    comboProducto.store =  test();
   });
  
  
/*************************************************************
** Textfields from datosFieldSet
**************************************************************/ 
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
           text:'Salvar',
           handler: function() { 
                    if (formPanelParam.form.isValid()) {
                   //  configuradorPanel.form.submit({ 
                       formPanelParam.form.submit({ 
                         
                           // url:'confbeta/salvarConjunto.action',
                           url: _SAVE_SET_URL,
                           
                           waitMsg:'Procesando...',
                           failure: function(form, action) {
                               Ext.MessageBox.alert('Conjunto no salvado', 'Se produjo error');
                           },
                           success: function(form, action) {
                               getRegistro();
                               storeRegistro.on('load', function(){
                                   var record = storeRegistro.getAt(0);
                                   formPanelParam.getForm().loadRecord(record);                                
                               });
                                
                               Ext.MessageBox.alert('Conjunto salvado', 'existosamente');
                                 
                               comboProceso.disable();
                               comboNivel.disable();
                               comboProducto.disable();
                               var treeN = Ext.getCmp('treeNavegacion');  
                               treeN.root.reload();
                                       
                           }
                       });   
                    } else {
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
                   formPanelParam.form.load({ 
                       //url:'confbeta/nuevoConjunto.action',
                       url:_NEW_SET_URL,
                       
                       failure: function(form, action) {
                           var treeN = Ext.getCmp('treeNavegacion');  
                           treeN.root.reload();
                       }
                   
                   });
                   idProceso = ' ';
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
 

//</script>