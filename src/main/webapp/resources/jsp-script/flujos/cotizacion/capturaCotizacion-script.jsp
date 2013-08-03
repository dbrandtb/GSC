Ext.onReady(function(){

    Ext.QuickTips.init();
    Ext.QuickTips.enable();
    
    // turn on validation errors beside the field globally
    Ext.form.Field.prototype.msgTarget = 'side';
    
	    var connRegresar = new Ext.data.Connection();
					        connRegresar.request ({
					            		url:'<s:url namespace="/flujocotizacion" action="obtenerRegresar" />',
										method: 'POST',
										successProperty : '@success',
										callback: function (options, success, response){
										var mensaje=Ext.util.JSON.decode(response.responseText).flujoCorrecto;
										
                       					
                       					if(mensaje!='true'){
                       					var mascaraRegresar = new Ext.LoadMask(Ext.getBody(), {msg:/*"Se ha utilizado un medio no adecuado para regresar a esta página;*/"Redireccionando..."});
												mascaraRegresar.show();
                       						
                       						mensaje.replace(/&amp;/g , '&');
                       						var urlRegreso=_URL_REGRESO_DE_RESULTADOS+mensaje;
											window.location=urlRegreso;
                       					}
                       					
	                       							
       		               			}
							});
										
	
		
	<s:if test="%{#session.containsKey('CAPTURA_PANTALLA')}">
		<s:component template="capturaCotizacion.vm" templateDir="templates" theme="components" >
			<s:param name="CAPTURA_PANTALLA" value="%{#session['CAPTURA_PANTALLA']}" />
        </s:component>
    </s:if>
    
    if(_CREGRESAR=='S'){
    //Asignar valores a los elementos que no usan store
    <s:if test="%{#session.containsKey('ComboLabelValue')}">
        <s:component template="capturaCotizacionRegresar.vm" templateDir="templates" theme="components" >
            <s:param name="CAPTURA_PANTALLA_REGRESAR" value="%{#session['ComboLabelValue']}" />
        </s:component>
    </s:if>
    //Asignar valores a los combos (usan store)
    <s:if test="%{#session.containsKey('storesCargados')}">
        <s:component template="storesCotizacionRegresar.vm" templateDir="templates" theme="components" >
            <s:param name="STORES_PANTALLA_REGRESAR" value="%{#session['storesCargados']}" />
        </s:component>
    </s:if>
    }
     
    function ejecutaValidaciones(){
    
    	var mask = new Ext.LoadMask(Ext.getBody(), {msg:'Validando los campos...'});
		mask.show();
    
    	var conn = new Ext.data.Connection();
		conn.request({
			url: '<s:url namespace="/flujocotizacion" action="validaCamposEntrada" />',
			method: 'POST',
			successProperty : '@success',
            callback: function (options, success, response) {
            				var ok = Ext.util.JSON.decode(response.responseText).success;

            				if(ok){
            				  window.location  = '<s:url namespace="/flujocotizacion" action="resultCotizacion" />';
	                      	}else{
	                      	  var mensajeRespuesta = Ext.util.JSON.decode(response.responseText).mensajeValidacion;
		                      if(Ext.isEmpty(mensajeRespuesta))mensajeRespuesta = 'Error al validar los campos. Consulte a soporte';
		                      Ext.Msg.alert('Error', mensajeRespuesta);
	                     	}
					  }
		});
		
	}
    
     
	function muestraMensajeValidacion(){
 		var mensajeValidacion = '<s:property value='mensajeValidacion'/>';
 		if(mensajeValidacion != ''){
 			Ext.Msg.alert('Error', mensajeValidacion);
 		}
 	}
 	muestraMensajeValidacion();   
 });

 