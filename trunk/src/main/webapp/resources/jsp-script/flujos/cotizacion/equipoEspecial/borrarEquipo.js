borrarEquipo = function(storeEquipo, cdUnieco, cdRamo, estado, nmPoliza, 
                          				nmSituac, cdTipobj, nmSuplem, status,  
                          				nmObjeto, dsObjeto, ptObjeto,cdAgrupa, nmValor) {
	
   Ext.MessageBox.buttonText.yes = 'Si';
   Ext.MessageBox.confirm('Mensaje','Esta seguro de querer eliminar este elemento?', function(btn) {
           	if(btn == 'yes'){
           		var params="";
           		params = "cdUnieco=" +cdUnieco;
           		params +="&&cdRamo="+cdRamo;
           		params +="&&estado="+estado;
           		params +="&&nmPoliza="+nmPoliza;
           		params +="&&nmSituac="+nmSituac;
           		params +="&&cdTipobj="+cdTipobj; 
				params +="&&nmSuplem="+nmSuplem;
				params +="&&status="+ status;
				params +="&&nmObjeto="+nmObjeto;
				params +="&&dsObjeto="+dsObjeto;
				params +="&&ptObjeto="+ptObjeto;
				params +="&&cdAgrupa="+cdAgrupa;
				params +="&&nmValor="+nmValor;
           		params +="&&valor=D";
				
				   
           		var conn = new Ext.data.Connection();
                	conn.request ({
                		url:_ACTION_SERVICIO_EQUIPO,
                		method: 'POST',
                		successProperty : '@success',
                		params : params,
                		callback: function (options, success, response) {  
                			if(Ext.util.JSON.decode(response.responseText).codicion==true){
                				Ext.MessageBox.alert('Estado', 'Error al eliminar');
							}else{
								
								Ext.MessageBox.alert('Estado', 'Elemento eliminado');          
          						storeEquipo.load();
							}
						}
                	});
         	}
    });
};      