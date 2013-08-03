<script type="text/javascript">


//Ext singleton
Ext.onReady(function(){

    Ext.QuickTips.init();
    Ext.QuickTips.enable();
    Ext.form.Field.prototype.msgTarget = "side";
    

/*************************************************************
** FormPanel de Ventana de Detalle
**************************************************************/           
var detalleFormPanel =  new Ext.form.FormPanel({                          
   id:'detalleFormPanel',
   url:'flujocotizacion/getDetalle.action',
   border:false,
   layout:'form',
   defaultType: 'textfield',
  
  <s:component template="builderDetalleItems.vm" templateDir="templates" theme="components" />
   
 });//end FormPanel


var windowDetalle = new Ext.Window({
            title: 'DETALLE COTIZACION',
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
            items: detalleFormPanel,
             buttons: [{
               text:'Cerrar',
               handler: function() { 
               windowDetalle.hide(); 
               }
          }]
            
        });

   //var simple is a panel with some elements...
    var simple = new Ext.FormPanel({
        labelWidth: 75, 
        id:'simpleForm',
        layout:'form',
        title: 'RESULTADO COTIZACION',
        bodyStyle:'padding:5px 5px 0',
        autoHeight : true, 
    

/// ELEMENTOS TOMADOS DEL EXT BUILDER....
            <s:component template="builderGrid.vm" templateDir="templates" theme="components" />
                   
//// ELEMENTOS OBTENIDOS DEL EXT-BUILDER

    });

   
   simple.render('items'); 

});

</script>