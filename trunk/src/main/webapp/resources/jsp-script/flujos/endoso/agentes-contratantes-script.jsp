
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
               url: 'flujoendoso/obtenerDatosRegistroAgentes.action'
           
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
           {name: 'rol', type: 'string',mapping:'cliente'},
           {name: 'numeroEmpleado', type: 'string',mapping:'cdConjunto'},
           {name: 'idPersona', type: 'string',mapping:'cdProceso'},
           {name: 'fechaNacimiento', type: 'string',mapping:'nombreConjunto'},
           {name: 'edad', type: 'string',mapping:'cdProceso'}
           
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
            header: "Agente",
            dataIndex:'descripcion',
            width:200,
             editor: new Ext.form.ComboBox({
               name:'agente',
               hiddenName:'hagente',
               store: storeProceso,
               displayField:'label',
               valueField:'value', 
               triggerAction: 'all',
               typeAhead: true,
               mode:'local'
               })
            },{
            header: "Tipo de Agente",
            dataIndex:'cdProceso', 
            width:200, 
            sortable:true,
            editor: new Ext.form.ComboBox({
               name:'tipoAgente',
               hiddenName:'htipoAgente',
               store: storeProceso,
               displayField:'label',
               valueField:'value', 
               triggerAction: 'all',
               typeAhead: true,
               mode:'local'
               })
            },{
            header: "Sucursal",
            dataIndex:'descripcion',
            width:200,
            sortable:true,
            editor: new Ext.form.TextField({
               allowBlank: false
               })
            
            },{
            header: "Participacion",
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
                  allowDecimals: false,
               maxValue: 100
                  
               })

            }
           
                      
    ]);
    
    
     var cm1 = new Ext.grid.ColumnModel([
      new Ext.grid.RowNumberer(),
         {  header: "ClaveConjunto",
            dataIndex:'cdConjunto',
            sortable:true,
            width:20,
            locked:false,
            id:'cdConjunto',
            hidden:true
            },{
            header: "Datos",
            dataIndex:'descripcion',
            width:200,
              editor: new Ext.form.TextField({
               allowBlank: false
               })
            },{
            header: "Valor del Dato",
            dataIndex:'cdProceso', 
            width:200, 
            sortable:true,
            editor: new Ext.form.ComboBox({
               name:'valorDato',
               hiddenName:'hvalorDato',
               store: storeProceso,
               displayField:'label',
               valueField:'value', 
               triggerAction: 'all',
               typeAhead: true,
               mode:'local'
               })
            },{
            header: "Descripcion del Dato",
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
        url='flujoendoso/obtenerDatosGridAgentes.action';
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


    var windowDel = new Ext.Window({
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
                                    windowDel.hide();
                                },
                                success: function(form, action) {
                                    Ext.MessageBox.alert('Registro eliminado', 'existosamente');
                                    windowDel.hide();
                                    storeGrid.load();
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
                xtype:"combo", 
                fieldLabel:"Agente",
                emptyText:"Seleccione...",
                name:"agente",
                hiddenName:"hagente",
                store: storeProceso,
                displayField:"label",
                valueField:"value", 
                triggerAction: "all",
                mode:'local',
                anchor:'85%', 
                editable:true
              },{
                xtype:"combo", 
                fieldLabel:"Tipo Agente",
                emptyText:"Seleccione...",
                name:"tipoAgente1",
                hiddenName:"htipoAgente1",
                store: storeProceso,
                displayField:"label",
                valueField:"value", 
                triggerAction: "all",
                mode:'local',
                anchor:'85%', 
                editable:true
               },{
                xtype:"textfield",
                fieldLabel: 'Sucursal',
                anchor:'90%',
                name:'sucursal'
               },{
                xtype:"textfield",
                fieldLabel: 'Participacion',
                anchor:'90%',
                name:'participacion'
        }] 
 });


var windowAdd = new Ext.Window({
            title: 'Agregar Registro',
            plain:true,
            width: 350,
            height:230,
            minWidth: 350,
            minHeight: 230,
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
                                    windowAdd.hide();
                                   
                            },
                            success: function(form, action) {
                                Ext.MessageBox.alert('Registro guardado', 'existosamente');
                                windowAdd.hide();                          
                            }
                        });                   
                    }else{
                        Ext.MessageBox.alert('Error', 'Porfavor revise los errores!');
                        }             
                    }
                },{
                    text: 'Nuevo',
                    handler: function(){
                        windowAdd.hide();
                    }    
                
                },{
                    text: 'Regresar',
                    handler: function(){
                        windowAdd.hide();
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
    




var agentesForm = new Ext.form.FormPanel({  
       layout:'form',
        id:'agentesForm',
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
                <s:component template="builderResultAgentes.vm" templateDir="templates" theme="components" />
              },{  
                border:false,
                bodyStyle:'margin-top: 20px; margin-left: 30px;',
                layout:'table',
                layoutConfig: {
                    columns: 3
                 },
               <s:component template="builderItemsContratante.vm" templateDir="templates" theme="components" />
             },{  
                border:false,
                layout:'form',
                bodyStyle:'margin-top: 10px; margin-left: 20px',
                <s:component template="builderResultContratante.vm" templateDir="templates" theme="components" />
            
        }] 
         ,buttons: [confirmarButton,borrarButton]
        ,listeners: {
            beforerender  : function() {
                       getRegistro();
                       storeRegistro.on('load', function(){
                       var record = storeRegistro.getAt(0);
                       agentesForm.getForm().loadRecord(record);
                        });
          }//beforerender
       }//listeners
  
  });






agentesForm.render('agentes');




});



</script>