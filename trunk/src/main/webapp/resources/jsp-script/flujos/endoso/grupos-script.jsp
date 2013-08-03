
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
               url: 'flujoendoso/obtenerDatosRegistroGrupos.action'
           
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
           {name: 'agrupador', type: 'string',mapping:'cliente'},
           {name: 'formaPago', type: 'string',mapping:'cdConjunto'},
           {name: 'idPersona', type: 'string',mapping:'cdProceso'},
           {name: 'domicilio', type: 'string',mapping:'cliente'},
           {name: 'grupo', type: 'string',mapping:'cliente'},
           {name: 'cuentaDeudor', type: 'string',mapping:'cdProceso'}
           
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
        {   header: "ClaveConjunto",
            dataIndex:'cdConjunto',
            sortable:true,
            width:20,
            locked:false,
            id:'cdConjunto',
            hidden:true
            },{
            header: "Banco",
            dataIndex:'descripcion',
            width:200,
             editor: new Ext.form.ComboBox({
               name:'banco',
               hiddenName:'hbanco',
               store: storeProceso,
               displayField:'label',
               valueField:'value', 
               triggerAction: 'all',
               typeAhead: true,
               mode:'local'
               })
            },{
            header: "Tipo de Cuenta",
            dataIndex:'cdProceso', 
            width:200, 
            sortable:true,
            editor: new Ext.form.ComboBox({
               name:'tipoCuenta',
               hiddenName:'htipoCuenta',
               store: storeProceso,
               displayField:'label',
               valueField:'value', 
               triggerAction: 'all',
               typeAhead: true,
               mode:'local'
               })
            },{
            header: "Cuenta",
            dataIndex:'descripcion',
            width:200,
            sortable:true,
            editor: new Ext.form.TextField({
               allowBlank: false
               })
            
            },{
            header: "Prestamo",
            dataIndex:'cdProceso', 
            width:200, 
            sortable:true,
               editor: new Ext.form.NumberField({
                   allowBlank: false,
                  allowNegative: false,
                  allowDecimals: false
                })
         }
   ]);
  
    
  var storeGrid;
  function getResultado(){
        url='flujoendoso/obtenerDatosGridGrupos.action';
        storeGrid = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
            url: url          
            }),
            reader: jsonGrilla,
            remoteSort: true
           
        });

        storeGrid.setDefaultSort('cdConjunto', 'desc');
        storeGrid.load();
        return storeGrid;
    }



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
    



var gruposForm = new Ext.form.FormPanel({  
       layout:'form',
        id:'gruposForm',
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
              
        }] 
         ,buttons: [confirmarButton,borrarButton]
        ,listeners: {
            beforerender  : function() {
                       getRegistro();
                       storeRegistro.on('load', function(){
                       var record = storeRegistro.getAt(0);
                       gruposForm.getForm().loadRecord(record);
                        });
          }//beforerender
       }//listeners
  
  });






gruposForm.render('grupos');




});



</script>