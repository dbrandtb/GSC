<script type="text/javascript">

Ext.onReady(function(){

    Ext.QuickTips.init();
    Ext.QuickTips.enable();
    
    // turn on validation errors beside the field globally
    Ext.form.Field.prototype.msgTarget = 'side';
    
    
   
    
    
    
    ///////////////////////////////////////////////////
    
   
        
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
                                               
                     }
                });                   
            } else{
                 Ext.MessageBox.alert('Error', 'Se produjo error');
            }  
        }      //end handler
    
    });
    
  var regresarButton = new Ext.Button({
        text: 'Regresar',
        handler: function(){
            window.location  = 'resultadosCotizacion.action';
        }
    });

    //var simple is a panel with some elements...
    var simple = new Ext.FormPanel({
        labelWidth: 75, 
        layout:'form',
        title: 'COTIZACION',
        bodyStyle:'padding:5px 5px 0',
        autoHeight : true,    
        defaultType: 'textfield',

/*/////////////////////////////////////////////////////////////////
    ELEMENTOS TOMADOS DEL EXT BUILDER....
*///////
            <s:component template="builderDetalleItems.vm" templateDir="templates" theme="components" />
                   
        //// ELEMENTOS OBTENIDOS DEL EXT-BUILDER


        ,buttons: [aceptarButton,regresarButton]
    });

   simple.render('items'); 

});


</script>