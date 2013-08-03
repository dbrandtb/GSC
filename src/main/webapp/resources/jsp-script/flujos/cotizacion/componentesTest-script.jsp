<script type="text/javascript">
var _VALOR_PROCESO = '<s:property value="proceso" />';

Ext.onReady(function(){

    Ext.QuickTips.init();
    Ext.QuickTips.enable();
    
    // turn on validation errors beside the field globally
    Ext.form.Field.prototype.msgTarget = 'side';
   
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    var storeProceso = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: 'flujocotizacion/obtenerListaCombo.action'+'?endPointName=OBTIENE_PROCESOS'
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
        
        var storeRegistro;
function getRegistro(){   
 storeRegistro = new Ext.data.Store({
         proxy: new Ext.data.HttpProxy({
               url: 'flujocotizacion/getDataEjemplo.action'
           
          }),
            reader: new Ext.data.JsonReader({
            root: 'registroList',
            id: 'proceso'
            },[
           {name: 'proceso', type: 'string',mapping:'hproceso'},
           {name: 'startdt', type: 'string',mapping:'startdt'},
           {name: 'enddt', type: 'string',mapping:'enddt'},
           {name: 'timevalue', type: 'string',mapping:'timevalue'},
           {name: 'sexo', type: 'string',mapping:'sexo'}
           ]),
            remoteSort: true
        });
        storeRegistro.load();
        return storeRegistro;
}
    
       

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    

        
     var aceptarButton = new Ext.Button({
        text:'Aceptar',  
        handler: function() {      
        
        if (simple.form.isValid()) {
                simple.form.submit({                 
                      waitMsg:'Procesando...',
                                        
                      failure: function(form, action) {
                        Ext.MessageBox.alert('Error', 'Se produjo un error');                                                    
                     },
                     success: function(form, action) {
                       Ext.MessageBox.alert('Exito', '..');                           
                     }
                });                   
            } else{
                 Ext.MessageBox.alert('Error', 'No valido form');
            }  
        
                                              
          
        }      //end handler
    
    });
    
    var cancelButton = new Ext.Button({
        text:'Limpiar',
        handler: function() {
            simple.form.reset();
        }
    });
    
   

    //var simple is a panel with some elements...
    var simple = new Ext.FormPanel({
        labelWidth: 75, 
        id:'simpleForm',
        url:'flujocotizacion/generaDataEjemplo.action',
        layout:'form',
        title: 'EJEMPLO COMPONENTES',
        bodyStyle:'padding:5px 5px 0',
        autoHeight : true,    
        defaultType: 'textfield',

/*/////////////////////////////////////////////////////////////////
    ELEMENTOS TOMADOS DEL EXT BUILDER....
*///////
            <s:component template="builderComponentes.vm" templateDir="templates" theme="components" />
                   
        //// ELEMENTOS OBTENIDOS DEL EXT-BUILDER


        ,buttons: [aceptarButton,cancelButton]
       
       ,listeners: {
         
            beforerender  : function() {
                       getRegistro();
                       storeRegistro.on('load', function(){
                       Ext.getCmp('proceso').store.reload();
                       var record = storeRegistro.getAt(0);
                      // Ext.getCmp('proceso').store.load();
                     //  Ext.getCmp('proceso').on('load', function(){
                     //  Ext.MessageBox.alert('Onloadr', '...');
                      //  });
                      if(record.get('sexo')== 'M'){
                      Ext.MessageBox.alert('sexoM', record.get('sexo'));
                       Ext.get('mas').dom.checked = true;
                       } 
                      if(record.get('sexo')== 'F'){
                       Ext.MessageBox.alert('sexoF', record.get('sexo'));
                       // Ext.getCmp('fem').setValue(true);
                       Ext.get('fem').dom.checked = true;
                       } 
                       simple.getForm().loadRecord(record);
                          
                        });
          }//beforerender
       }//listeners
      
    });

    ////se renderea en la pantalla
    // simple.render(document.body);

   
   simple.render('items'); 

   

});



</script>