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


 
 ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //var simple is a panel with some elements...
    var simple = new Ext.FormPanel({
        id:'simpleForm',
        layout:'form',
        bodyStyle:'padding:5px 5px 0',
        autoHeight : true, 
        autoWidht : true,
        items: [{
               
                border:false,
                bodyStyle:'margin-top: 20px; margin-left: 30px;',
                layout:'table',
                layoutConfig: {
                    columns: 1
                 },
                <s:component template="builderItemsResultadoCotizacion.vm" templateDir="templates" theme="components" />
              },{  
               
                border:false,
                layout:'form',
                bodyStyle:'margin-top: 10px; margin-left: 20px',
                <s:component template="builderResultResultadoCotizacion.vm" templateDir="templates" theme="components" />
             
        }] 
        
    ////////////////////// FOOTERS: BOTONES, LISTENERS...
   <s:component template="builderFootersResultadoCotizacion.vm" templateDir="templates" theme="components" />
        
    });

   
   simple.render('items'); 

    ////////////////////// FUNCIONES...
   <s:component template="builderFunctionsResultadoCotizacion.vm" templateDir="templates" theme="components" />

});

</script>