<%@ include file="/taglibs.jsp"%>


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
               url: 'flujoendoso/obtenerDatosRegistro.action'
           
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
           {name: 'fechaSolicitud', type: 'string',mapping:'nombreConjunto'},
           {name: 'fechaRenovacion', type: 'string',mapping:'nombreConjunto'},
           {name: 'fechaInicio', type: 'string',mapping:'nombreConjunto'},
           {name: 'fechaEfectividad', type: 'string',mapping:'nombreConjunto'},
           {name: 'moneda', type: 'string',mapping:'descripcion'},
           {name: 'numeroSolicitud', type: 'string',mapping:'cliente'},
           {name: 'hformaPago', type: 'string',mapping:'cdProceso'},
           {name: 'tipoPoliza', type: 'string',mapping:'cliente'}
           
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
        {name: 'proceso',  type: 'string',  mapping:'proceso'},
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
            {header: "ClaveConjunto",      dataIndex:'cdConjunto',sortable:true, width:20, locked:false,   id:'cdConjunto', hidden:true},          
            {header: "Datos ",   dataIndex:'proceso', width:200, sortable:true},
            {header: "Valor del Dato",dataIndex:'cliente', width:200, sortable:true},
            {header: "Descripcion del Dato", dataIndex:'descripcion', width:380, sortable:true}
            
                      
    ]);
    
   var storeGrid;
    
   function getResultado(){
       // filterAreaForm.getForm().submit();
        url='flujoendoso/obtenerDatosGrid.action';
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
    



var simpleForm = new Ext.form.FormPanel({  
        //url:'confpantallas/obtenerDatos.action',                        
        layout:'form',
        id:'simpleForm',
        border:false,
        bodyStyle:'padding:5px 5px 0',
        autoHeight : true,   
        items: [{
                border:false,
                bodyStyle:'margin-top: 5px; margin-left: 20px;',
                layout:'table',
                layoutConfig: {
                    columns: 5
                 },
                <s:component template="builderItemsPoliza.vm" templateDir="templates" theme="components" />
              },{  
                xtype:'fieldset',
                style:'margin:5px',
                bodyStyle:'padding:5px',
                title: '<span style="color:#98012e;font-size:11px;font-family:Arial,Helvetica,sans-serif;">Datos generales</span>',  
                autoHeight:true,
                autoWidth : true, 
                buttonAlign : 'center',
                <s:component template="builderItemsDatosGenerales.vm" templateDir="templates" theme="components" />
              },{  
                border:false,
                layout:'form',
                bodyStyle:'margin-top: 20px; margin-left: 20px',
                <s:component template="builderResultDatosGenerales.vm" templateDir="templates" theme="components" />
        }] 
         ,buttons: [confirmarButton,borrarButton]
        ,listeners: {
            beforerender  : function() {
                       getRegistro();
                       storeRegistro.on('load', function(){
                       var record = storeRegistro.getAt(0);
                       simpleForm.getForm().loadRecord(record);
                        });
          }//beforerender
       }//listeners
  
  });






simpleForm.render('datosGenerales');




});



</script>