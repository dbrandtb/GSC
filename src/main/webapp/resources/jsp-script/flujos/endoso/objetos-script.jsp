
<%@ include file="/taglibs.jsp"%>

<script type="text/javascript" src="${ctx}/resources/jsp-script/flujos/endoso/GroupSummary.js"></script>

<script type="text/javascript">

Ext.onReady(function(){

    Ext.QuickTips.init();
    Ext.QuickTips.enable();
    
    // turn on validation errors beside the field globally
    Ext.form.Field.prototype.msgTarget = 'side';
   
/*************************************************************
** Store combo ejemplo
**************************************************************/

        var storeProceso = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: 'flujoendoso/obtenerListaCombo.action'+'?endPointName=OBTIENE_PROCESOS'
            }),
            reader: new Ext.data.JsonReader({
            root: 'itemList',
            id: 'value'
            },[
           {name: 'value', type: 'string', mapping:'value'},
           {name: 'label', type: 'string', mapping:'label'}  
             ]),
            remoteSort: true
        });
        storeProceso.setDefaultSort('value', 'desc');
        storeProceso.load();
        

/*************************************************************
** Store datos Filtro ejemplo
**************************************************************/
var storeRegistro;
function getRegistro(){   
 storeRegistro = new Ext.data.Store({
         proxy: new Ext.data.HttpProxy({
               url: 'flujoendoso/obtenerDatosRegistroObjetos.action'
           
          }),
            reader: new Ext.data.JsonReader({
            root: 'registroList',
            id: 'proceso'
            },[
           {name: 'poliza', type: 'string',mapping:'cdConjunto'},
           {name: 'poliza1', type: 'string',mapping:'cdConjunto'},
           {name: 'poliza2', type: 'string',mapping:'cdConjunto'},
           {name: 'poliza3', type: 'string',mapping:'cdConjunto'},
           {name: 'polizaExterna', type: 'string',mapping:'cdProceso'},
           {name: 'objeto', type: 'string',mapping:'cliente'},
           {name: 'htipoObjeto', type: 'string',mapping:'cdProceso'},
           {name: 'importe', type: 'string',mapping:'cdConjunto'}
         
            ]),
            remoteSort: true
        });
        storeRegistro.load();
        return storeRegistro;
}



/*************************************************************
** Store Grid
**************************************************************/ 


    var lasFilas=new Ext.data.Record.create([
        {name: 'cdConjunto',  type: 'string',  mapping:'cdConjunto'},
        {name: 'nombreConjunto',  type: 'string',  mapping:'nombreConjunto'},
        {name: 'descripcion',  type: 'string',  mapping:'descripcion'},
        {name: 'cdProceso',  type: 'int',  mapping:'cdProceso'},
        {name: 'cliente',  type: 'string',  mapping:'cliente'}                    
    ]);
    
    var jsonGrilla= new Ext.data.JsonReader({    
        root:'dataGrid',
        totalProperty: 'totalCount',
        successProperty : '@success',
        id: 'cdConjunto'
        },
        lasFilas
    );
    
    var cm = new Ext.grid.ColumnModel([
    new Ext.grid.RowNumberer(),
        {   header: "ClaveConjunto",
            dataIndex:'cdConjunto',
            sortable:true,
            width:20,
            locked:false,
            id:'cdConjunto',
            hidden:true
            },{
            header: "Objeto",
            dataIndex:'descripcion',
            width:200,
            editor: new Ext.form.TextField({
               allowBlank: false
               })
            
            },{
            header: "Tipo de Objeto",
            dataIndex:'cdProceso', 
            width:200, 
            sortable:true,
            editor: new Ext.form.TextField({
               allowBlank: false
               })
            },{
           
            header: "Importe",
            dataIndex:'cdProceso', 
            width:200, 
            sortable:true,
            summaryType:'sum',
             summaryRenderer: function(v, params, data){
                    return 'Total ' + v ;
                },
              editor: new Ext.form.NumberField({
                   allowBlank: false,
                  allowNegative: false,
                  allowDecimals: true
                   
               })

            }
           
                      
    ]);
    
    
    var cm1 = new Ext.grid.ColumnModel([
        {   header: "ClaveConjunto",
            dataIndex:'cdConjunto',
            sortable:true,
            width:20,
            locked:false,
            id:'cdConjunto',
            hidden:true
            },{
            header: "Inciso",
            dataIndex:'cdProceso', 
            width:200, 
            sortable:true
          
            },{
            header: "Inciso Externo",
            dataIndex:'cdProceso', 
            width:200, 
            sortable:true
            },{
            header: "Tipo de Inciso",
            dataIndex:'descripcion',
            width:200,
            sortable:true
            },{
            header: "Descripción",
            dataIndex:'descripcion',
            width:200,
            sortable:true
            }
           
                      
    ]);
    
    
    
    
     var cm2 = new Ext.grid.ColumnModel([
         {  header: "ClaveConjunto",
            dataIndex:'cdConjunto',
            sortable:true,
            width:20,
            locked:false,
            id:'cdConjunto',
            hidden:true
            },{
            header: "Atributos ",
            dataIndex:'descripcion',
            width:200
            
            },{
            header: "Valor del Atributo",
            dataIndex:'cdProceso', 
            width:200, 
            sortable:true,
            editor: new Ext.form.ComboBox({
               name:'valorAtributo',
               hiddenName:'hvalorAtributo',
               store: storeProceso,
               displayField:'label',
               valueField:'value', 
               triggerAction: 'all',
               typeAhead: true,
               mode:'local'
               })
            },{
            header: "Descripción del Valor ",
            dataIndex:'descripcion',
            width:380,
            sortable:true,
            editor: new Ext.form.TextField({
               allowBlank: false
               })
            
            }        
    ]);
    

var storeGrid;
    var summary = new Ext.grid.GroupSummary(); 
    
   function getResultado(){
        url='flujoendoso/obtenerDatosGridObjetos.action';
        storeGrid = new Ext.data.GroupingStore({
            proxy: new Ext.data.HttpProxy({
            url: url          
            }),
            reader: jsonGrilla,
            remoteSort: true,
            //groupOnSort : true
            groupField:'cdProceso'
           
        });

        storeGrid.setDefaultSort('cdProceso', 'desc');
        storeGrid.load();
        return storeGrid;
    }


/*************************************************************
** FormPanel de Ventana de Borrar
**************************************************************/           

var borrarForm= new Ext.form.FormPanel({
        id:'borrarForm',
        labelAlign:'top',
        border:false,
        anchor:'100%',
    //    url:'flujocotizacion/getDetalle.action',
         items: [{
                 xtype:'textfield',
                 fieldLabel:'¿Desea eliminar el registro?',
                labelSeparator:'',
                hidden:true
         },{
                    xtype:'textfield',
                    fieldLabel: '',   
                    name:'cdConjunto',
                    hiddeParent:true,
                    labelSeparator:'',
                    hidden:true
                   
         }]
 
    });


    var windowDel5 = new Ext.Window({
        title: 'Eliminar Registro',
        minHeight: 50,
        minWidth: 250,
        width: 200,
        height:120,
        layout: 'fit',
        plain:true,
        modal:true,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        items: borrarForm,
        buttons: [{
                text: 'Aceptar', 
                handler: function() {
                    if (borrarForm.form.isValid()) {
                            borrarForm.form.submit({          
                                waitMsg:'Procesando...',
                                failure: function(form, action) {
                                    Ext.MessageBox.alert('Registro no eliminado', 'Se produjo error');
                                    windowDel5.hide();
                                },
                                success: function(form, action) {
                                    Ext.MessageBox.alert('Registro eliminado', 'existosamente');
                                    windowDel5.hide();
                                    storeGrid.load();
                                }
                            });                   
                    }else{
                        Ext.MessageBox.alert('Error', 'Verifique!');
                    }             
                }
            },{
                text: 'Cancelar',
                handler: function(){windowDel5.hide();}
            }]
        });


//////////////////////////////
/*************************************************************
** FormPanel de Ventana de Agregar
**************************************************************/    
var agregarForm = new Ext.form.FormPanel({  
        id:'agregarForm',
        border:false,
        //    url:'flujocotizacion/getDetalle.action',
        bodyStyle:'padding:5px 5px 0',
        items: [{
                border:false,
                bodyStyle:'margin-top: 5px; margin-left: 20px;',
                layout:'table',
                layoutConfig: {
                    columns: 3
                 },
                 items :[{
                        border:false,
                        width:150,
                        layout:'form',
                        labelWidth : 70,
                        items:[{
                                xtype:'textfield',
                                fieldLabel: 'Objeto',
                                width:55,
                                disabled:true,
                                name:'objeto'
                              }]
                              
                       },{    
                        border:false,
                        width:250,
                        layout:'form',
                        labelWidth : 100,
                        items:[{
                                xtype:"combo", 
                                fieldLabel:"Tipo de Objeto",
                                emptyText:"Seleccione...",
                                name:"tipoObjeto",
                                hiddenName:"htipoObjeto",
                                store: storeProceso,
                                displayField:"label",
                                valueField:"value", 
                                triggerAction: "all",
                                mode:'local',
                                width:100,
                                editable:true
                              }]
                        },{   
                        border:false,
                        width:200,
                        layout:'form',
                        labelWidth : 70,
                        items:[{
                                xtype:'textfield',
                                fieldLabel: 'Importe',
                                width:80,
                                disabled:true,
                                name:'objeto'
                              }]
                    }]    
                },{
                 border:false,
                 layout:'form',
                bodyStyle:'margin-top: 10px; ',
                <s:component template="builderResultObjetoVariable.vm" templateDir="templates" theme="components" />
        }] 
 });


var windowAdd5 = new Ext.Window({
            title: 'Agregar Registro',
            plain:true,
            width: 820,
            height:300,
            minWidth: 800,
            minHeight: 300,
            layout: 'fit',
            bodyStyle:'padding:5px;',
            buttonAlign:'center',
            modal:true,
            items: agregarForm,
            buttons: [{
                text: 'Guardar', 
                handler: function() {                
                    if (agregarForm.form.isValid()) {
                        agregarForm.form.submit({               
                            waitMsg:'Procesando...',
                            failure: function(form, action) {
                                    Ext.MessageBox.alert('Registro no guardado', 'Se produjo error');
                                    windowAdd5.hide();
                                   
                            },
                            success: function(form, action) {
                                Ext.MessageBox.alert('Registro guardado', 'existosamente');
                                windowAdd5.hide();                          
                            }
                        });                   
                    }else{
                        Ext.MessageBox.alert('Error', 'Porfavor revise los errores!');
                        }             
                    }
                },{
                    text: 'Nuevo',
                    handler: function(){
                        windowAdd5.hide();
                    }    
                
                },{
                    text: 'Regresar',
                    handler: function(){
                        windowAdd5.hide();
                    }
                }]
  });
   

//////////////////////////////
/*************************************************************
** FormPanel de Ventana de Editar
**************************************************************/    
var editarForm = new Ext.form.FormPanel({  
        id:'editarForm',
        border:false,
        //    url:'flujocotizacion/getDetalle.action',
        bodyStyle:'padding:5px 5px 0',
        items: [{
                border:false,
                bodyStyle:'margin-top: 5px; margin-left: 20px;',
                layout:'table',
                layoutConfig: {
                    columns: 3
                 },
                 items :[{
                        border:false,
                        width:150,
                        layout:'form',
                        labelWidth : 70,
                        items:[{
                                xtype:'textfield',
                                fieldLabel: 'Objeto',
                                width:55,
                                disabled:true,
                                name:'objeto'
                              }]
                              
                       },{    
                        border:false,
                        width:250,
                        layout:'form',
                        labelWidth : 100,
                        items:[{
                                xtype:"combo", 
                                fieldLabel:"Tipo de Objeto",
                                emptyText:"Seleccione...",
                                name:"tipoObjeto",
                                hiddenName:"htipoObjeto",
                                store: storeProceso,
                                displayField:"label",
                                valueField:"value", 
                                triggerAction: "all",
                                mode:'local',
                                width:100,
                                editable:true
                              }]
                        },{   
                        border:false,
                        width:200,
                        layout:'form',
                        labelWidth : 70,
                        items:[{
                                xtype:'textfield',
                                fieldLabel: 'Importe',
                                width:80,
                                disabled:true,
                                name:'importe'
                              }]
                    }]    
                },{
                 border:false,
                 layout:'form',
                bodyStyle:'margin-top: 10px; ',
                <s:component template="builderResultObjetoVariable.vm" templateDir="templates" theme="components" />
        }] 
 });


var windowEditar = new Ext.Window({
            title: 'Editar Registro',
            plain:true,
            width: 820,
            height:300,
            minWidth: 800,
            minHeight: 300,
            layout: 'fit',
            bodyStyle:'padding:5px;',
            buttonAlign:'center',
            modal:true,
            items: editarForm,
            buttons: [{
                text: 'Guardar', 
                handler: function() {                
                    if (editarForm.form.isValid()) {
                        editarForm.form.submit({               
                            waitMsg:'Procesando...',
                            failure: function(form, action) {
                                    Ext.MessageBox.alert('Registro no guardado', 'Se produjo error');
                                    windowEditar.hide();
                                   
                            },
                            success: function(form, action) {
                                Ext.MessageBox.alert('Registro guardado', 'existosamente');
                                windowEditar.hide();                          
                            }
                        });                   
                    }else{
                        Ext.MessageBox.alert('Error', 'Porfavor revise los errores!');
                        }             
                    }
                },{
                    text: 'Nuevo',
                    handler: function(){
                        windowEditar.hide();
                    }    
                
                },{
                    text: 'Regresar',
                    handler: function(){
                        windowEditar.hide();
                    }
                }]
  });
   


//////////////////////////////
/*************************************************************
** FormPanel de Ventana de Confirmar
**************************************************************/    
var confirmarForm = new Ext.form.FormPanel({  
        id:'confirmarForm',
        border:false,
        layout:'form',
         bodyStyle:'padding:5px;',
         labelWidth : 100, 
        //    url:'flujocotizacion/getDetalle.action',
        items: [{
                xtype:"textarea",
                fieldLabel: 'Descripcion',
                grow: true,
                preventScrollbars:true,
                width : 200,
                name:'descConfirmacion'
              }] 
 });


var windowConfirmar = new Ext.Window({
            plain:true,
            width: 350,
            height:200,
            title: 'Observaciones',
            minWidth: 350,
            minHeight: 200,
            layout: 'fit',
            bodyStyle:'padding:5px;',
            buttonAlign:'center',
            modal:true,
            items: confirmarForm,
            buttons: [{
                text: 'Guardar', 
                handler: function() {                
                    if (confirmarForm.form.isValid()) {
                        confirmarForm.form.submit({               
                            waitMsg:'Procesando...',
                            failure: function(form, action) {
                                    Ext.MessageBox.alert('Registro no guardado', 'Se produjo error');
                                    windowConfirmar.hide();
                                   
                            },
                            success: function(form, action) {
                                Ext.MessageBox.alert('Registro guardado', 'existosamente');
                                windowConfirmar.hide();                          
                            }
                        });                   
                    }else{
                        Ext.MessageBox.alert('Error', 'Porfavor revise los errores!');
                        }             
                    }
               
                },{
                    text: 'Regresar',
                    handler: function(){
                        windowConfirmar.hide();
                    }
                }]
  });
   

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    

        
     var confirmarButton = new Ext.Button({
        text:'Confirmar',  
        handler: function() {      
        windowConfirmar.show();
                                                      
          
        }      //end handler
    
    });
    
    var borrarButton = new Ext.Button({
        text:'Eliminar Endoso',
        handler: function() {
           Ext.MessageBox.alert('Eliminar', 'Endoso');
        }
    });
    



var objetoForm = new Ext.form.FormPanel({  
       layout:'form',
        id:'objetoForm',
        border:false,
        bodyStyle:'padding:5px 5px 0',
        autoHeight : true,   
        autoWidht : true,
        items: [{
                border:false,
                bodyStyle:'margin-top: 5px; margin-left: 20px;',
                layout:'table',
                layoutConfig: {
                    columns: 5
                 },
                <s:component template="builderItemsPoliza.vm" templateDir="templates" theme="components" />
              },{  
                 border:false,
                layout:'form',
                bodyStyle:'margin-top: 10px; margin-left: 20px',
                <s:component template="builderResultEncabezadoIncisos.vm" templateDir="templates" theme="components" />
            },{  
               
                border:false,
                layout:'form',
                bodyStyle:'margin-top: 10px; margin-left: 20px',
                <s:component template="builderResultObjeto.vm" templateDir="templates" theme="components" />
            },{  
               
                border:false,
                layout:'form',
                bodyStyle:'margin-top: 10px; margin-left: 20px',
                <s:component template="builderResultObjetoVariable.vm" templateDir="templates" theme="components" />
           
        }] 
         ,buttons: [confirmarButton,borrarButton]
        ,listeners: {
            beforerender  : function() {
                       getRegistro();
                       storeRegistro.on('load', function(){
                       var record = storeRegistro.getAt(0);
                       objetoForm.getForm().loadRecord(record);
                        });
          }//beforerender
       }//listeners
  
  });






objetoForm.render('objetos');




});



</script>