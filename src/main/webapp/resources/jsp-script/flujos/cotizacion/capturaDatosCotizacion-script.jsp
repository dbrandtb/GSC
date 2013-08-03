<script type="text/javascript">
var _VALOR_PROCESO = '<s:property value="proceso" />';

Ext.onReady(function(){

    Ext.QuickTips.init();
    Ext.QuickTips.enable();
    
    // turn on validation errors beside the field globally
    Ext.form.Field.prototype.msgTarget = 'side';
   
  
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 
   
   <s:component template="builderVariables.vm" templateDir="templates" theme="components" />

    //var simple is a panel with some elements...
    var simple = new Ext.FormPanel({
        labelWidth: 75, 
        id:'simpleForm',
        url:'flujocotizacion/generaCotizacion.action',
        layout:'form',
        title: 'COTIZACION',
        bodyStyle:'padding:5px 5px 0',
        autoHeight : true,    
        labelAlign :'right',
        labelWidth : 120,
        defaultType: 'textfield',

        ////////////////////// ELEMENTOS TOMADOS DEL EXT BUILDER...
        <s:component template="builderItems.vm" templateDir="templates" theme="components" />
  
      
    });

 
   simple.render('items'); 
   
   ////////////////////// FUNCIONES...
   <s:component template="builderFunctions.vm" templateDir="templates" theme="components" />
  
});



</script>