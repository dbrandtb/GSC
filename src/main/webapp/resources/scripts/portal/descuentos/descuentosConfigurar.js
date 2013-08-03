Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.QuickTips.enable();
	Ext.form.Field.prototype.msgTarget = "side";

	var itemsPerPage = 10;
	
	var codigoDescuento = new Ext.form.Hidden({
    	disabled:false,
        name:'cdDscto',
        value: CODIGO_DESCUENTO 
    });
    var codigoTipoDescuento = new Ext.form.Hidden({
    	disabled:false,
        name:'tipoDscto',
        value: CODIGO_TIPO_DESCUENTO 
    });
	
	(codigoDescuento.getValue() == "")?cargarDescuentosAgregar():cargarDescuentosEditar(codigoTipoDescuento);
	
	regresar = function(){
    	window.location=_ACTION_REGRESAR_A_DESCUENTOS
    }
    
});