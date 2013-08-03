//<script type="text/javascript">
Ext.onReady(function(){
    Ext.QuickTips.init();
    Ext.QuickTips.enable();
    Ext.form.Field.prototype.msgTarget = "side";
    var afuera = 0;
    var temporal = -1;
	var botonRegresaCotizacion = "<s:property value='botonRegresaCotizacion'/>";
    
     ////////////////////// ELEMENTOS TOMADOS DEL EXT BUILDER...
     <s:if test="%{#session.containsKey('STORES_CONTROL_RESULT')}">
       <s:component template="builderVariablesResultCotizacion.vm" templateDir="templates" theme="components" >
        <s:param name="STORES_CONTROL_RESULT" value="%{#session['STORES_CONTROL_RESULT']}"/>
       </s:component>
    </s:if>

 ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

 /*************************************************************
 * FormPanel de Ventana de Coberturas
 *************************************************************/
var idGarant;
var coberturasFormPanel =  new Ext.form.FormPanel({                          
   id:'coberturasFormPanel',
   url:'flujocotizacion/obtenerAyudaCobertura.action',
   border:false,
   layout:'form',
   items: [{
            border:false,
            bodyStyle:'margin-top: 20px; margin-left: 20px;',
            layout:'table',
            layoutConfig: {
				columns: 4
			},
            <s:component template="builderItemsCoberturasCotizacion.vm" templateDir="templates" theme="components" />
         },{  
            border:false,
            layout:'form',
            bodyStyle:'margin-top: 0px; margin-left: 5px;',
            width:450,
            <s:component template="builderResultCoberturasCotizacion.vm" templateDir="templates" theme="components" />
       }]
 });//end FormPanel

 /*************************************************************
 * Window Coberturas
 *************************************************************/
var windowCoberturas = new Ext.Window({
	plain:true,
	id:'windowCoberturas',
	width: 500,
	height:400,
	autoScroll:true, 
	title: 'Coberturas',
	layout: 'fit',
	bodyStyle:'padding:5px;',
	buttonAlign:'center',
	closeAction:'hide',
	closable : true,
	items: coberturasFormPanel,
	buttons: [{
		text:'Regresar',
		handler: function() { 
			windowCoberturas.hide(); 
		}
	}]
});



 /*************************************************************
 * FormPanel de Ventana de Comprar
 *************************************************************/
var comprarFormPanel =  new Ext.form.FormPanel({                          
   id:'comprarFormPanel',
   url:'flujocotizacion/comprar.action',
   border:false,
   frame:true,
   autoScroll:true,
   width:450,
   height:600,
   layout:'form',
   items: [{
            xtype:"textfield",
            name:"id",
            id:"id",
            type:'hidden',
            hidden:true,
            labelSeparator:'' 
          }]
});//end FormPanel

 /*************************************************************
 * Window Comprar
 *************************************************************/
var windowComprar = new Ext.Window({
	title: 'COTIZACION-COMPRAR',
	id:'windowComprar',
	width: 480,
	height:600,
	minWidth: 200,
	minHeight: 200,
	buttonAlign:'center',
	closable : false,
	items: comprarFormPanel,
	buttons: [{
		text:'Regresar',
		handler: function() { 
			windowComprar.hide(); 
		}
	}]
});


 ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    var recordGridResultado;
    var indexMnPrima;
    var indexCdPlan;
    var indexDsPlan;
    var indexNmPlan;
    var mnPrima;
    var idplan;
    var desplan;
    var nmplan;
    
    
    var txtAseguradora = new Ext.form.TextField({
        fieldLabel: 'ASEGURADORA:',
        labelSeparator:'',
        id:'txtAseguradora',
        width:'195',
        disabled:true
        
    });      
        var txtVigencia = new Ext.form.TextField({
        fieldLabel:'VIGENCIA:',
        labelSeparator:'',
        id:'txtVigencia',
        width:'195',
        disabled:true
        
    });       
 
    //var simple is a panel with some elements...
    var simple = new Ext.FormPanel({
        id:'simpleForm',
        layout:'form',
        // url:'flujocotizacion/obtenerParametrosResult.action',
        bodyStyle:'padding:5px 5px 20px',
        autoHeight : true, 
        width:760,
        items: [{
			border:false,
			layout:'form',
			autoHeight:true,
			bodyStyle:'padding: 0px 0px 30px 10px',
			items: [
				<s:component template="builderItemsResultCotizacion.vm" templateDir="templates" theme="components">
					<s:param name="TEXT_FIELDS" value="%{#session['TEXT_FIELDS']}"/>
				</s:component>,
				txtVigencia,
				txtAseguradora
			]
		},{  
			border:false,
			layout:'form',
			autoHeight:true,
			bodyStyle:'padding: 0px 0px 30px 10px'
			<s:if test="%{#session.containsKey('COLUMN_GRID')}">
				,<s:component template="builderResultResultCotizacion.vm" templateDir="templates" theme="components">
					<s:param name="COLUMN_GRID" value="%{#session['COLUMN_GRID']}"/>
				</s:component>
			</s:if>             
        }] 
    ////////////////////// FOOTERS: BOTONES, LISTENERS...
   <s:component template="builderFootersResultCotizacion.vm" templateDir="templates" theme="components" />    
    });// end FormPanel
    
    if(Ext.getCmp('gridResultado')){

 		/*************************************************************
 		* Boton 'Guardar'
 		*************************************************************/
	    var variabilidadBoton = "<s:property value='botonGuardaCotizacion'/>";
    	if(variabilidadBoton =="S"){

	    	Ext.getCmp('gridResultado').addButton({
	    		text:'Guardar',
	    		id:'guardar-resultado-cotizacion',
	    		cls:'padding: 30px 0px 30px 0px;',
	    		tooltip:'Guarda resultado de cotizaci&oacute;n'
	    	}, function(){
       			//Ext.MessageBox.alert('guardar l', Ext.get('checks').dom.value.length ); 
	   		});

			Ext.getCmp('guardar-resultado-cotizacion').on('click', function(){

                	var conn = new Ext.data.Connection();
                	conn.request ({
	                    url:'guardaCotizacion.action',
                    	method: 'POST',
                    	successProperty : '@success',
                    	callback: function (options, success, response) {
	                        if (Ext.util.JSON.decode(response.responseText).numIdentificador!=null) {
                            	var numIdCotizacion = Ext.util.JSON.decode(response.responseText).numIdentificador;
                            	Ext.MessageBox.alert('Cotización', 'La cotizaci&oacute;n ha sido guardada con el número '+numIdCotizacion);
                        	}else{
	                            Ext.MessageBox.alert('Cotización', 'Ocurri&oacute; un error al guardar la cotizaci&oacute;n');
                        	}
                    	}   
                	});
			});

    	}// end if
 		/*************************************************************
 		* FIN Boton 'Guardar'
 		*************************************************************/

 		/*************************************************************
 		* Boton 'Comprar'
 		*************************************************************/
	    Ext.getCmp('gridResultado').addButton({
	    	text:'Comprar',
	    	id:'comprar',
	    	cls:'padding: 30px 0px 30px 0px;',
	    	tooltip:'Compra cotizaci&oacute;n'
	    });
		/*************************************************************
 		* FIN Boton 'Comprar'
 		*************************************************************/

 		/*************************************************************
 		* Boton 'Nueva Cotización'
 		*************************************************************/
		var botonNuevaCotizacion = "<s:property value='botonNuevaCotizacion'/>";
    	if ( botonNuevaCotizacion == "S" ) {
	    	Ext.getCmp('gridResultado').addButton({
	    		text: 'Nueva cotizaci&oacute;n', 
		    	cls: 'padding: 30px 0px 30px 0px;',
	    		tooltip: 'Nueva cotizaci&oacute;n',
	    		handler: function() {
	    			window.location.replace("${ctx}/flujocotizacion/capturaCotizacion.action?CDRAMO=" + _CDRAMO + "&&CDTIPSIT=" + _CDTIPSIT);
		    	}
			});
		}
 		/*************************************************************
 		* FIN Boton 'Nueva Cotización'
 		*************************************************************/

 		/*************************************************************
 		* Boton 'Imprimir'
 		*************************************************************/
		Ext.getCmp('gridResultado').addButton({
			text:'Imprimir',
			id:'imprimir',
			cls:'padding: 30px 0px 30px 0px;',
			tooltip:'Imprime cotizaci&oacute;n'
		});
 		/*************************************************************
 		* Boton 'Imprimir'
 		*************************************************************/

 		/*************************************************************
 		* Boton 'Enviar Correo'
 		*************************************************************/
		Ext.getCmp('gridResultado').addButton({
			text:'Enviar Correo',	
			id:'enviarCorreo', 
			cls:'padding: 30px 0px 30px 0px;',
			tooltip:'Envia cotizaci&oacute;n a correo'
		});
 		/*************************************************************
 		* Boton 'Enviar Correo'
 		*************************************************************/

        //////////////////////////////////////////////////////////////
        // Funciones botones grid
        //////////////////////////////////////////////////////////////
        Ext.getCmp('comprar').on('click', function(){

			if ( recordGridResultado == undefined || recordGridResultado == null ) {
				Ext.Msg.alert(_MSG_AVISO, _MSG_NO_ROW_SELECTED);
			} else if(mnPrima == undefined || mnPrima == null ||mnPrima == ""){
				Ext.Msg.alert(_MSG_AVISO, 'No existe cotizaci&oacute;n para el plan seleccionado. Favor de revisar');
			}
        	// Se busca el caracter '/' porque el usuario al seleccionar un registro que contiene texto del grid de resultados
        	// p.ej. 'Mensual', 'Contado', la variable 'nmplan' contiene la 'fecha de emision': ##/##/####
			else if ( nmplan.search('/') != -1  ) {
				Ext.Msg.alert(_MSG_AVISO, 'Seleccione la cotizaci&oacute;n que desea comprar');
			}
        	else if( recordGridResultado != undefined &&  recordGridResultado != null ) {
        		var params = "?cdIdentifica=" + recordGridResultado.get('cdIdentifica') +"&&";
            	params += "cdUnieco="   + recordGridResultado.get('cdUnieco')    + "&&";
            	params += "cdCiaaseg="  + recordGridResultado.get('cdCiaaseg')   + "&&";                                         
            	params += "dsUnieco="   + recordGridResultado.get('dsUnieco')    + "&&";
            	params += "cdPerpag="   + recordGridResultado.get('cdPerpag')    + "&&";
            	params += "cdRamo="     + recordGridResultado.get('cdRamo')      + "&&";    
            	params += "dsPerpag="   + recordGridResultado.get('dsPerpag')    + "&&";
            	//params += "numeroSituacion="+ recordGridResultado.get('numeroSituacion')  + "&&";
				
            	params += "cdPlan="         + idplan    + "&&";
            	params += "dsPlan="         + desplan   + "&&";
            	params += "numeroSituacion="         + nmplan   + "&&";
            	params += "feEmisio="       + recordGridResultado.get('feEmisio')   + "&&";
            	params += "feVensim="       + recordGridResultado.get('feVencim')   + "&&";

    			var urlComprarDesdeConsulta = "<s:property value='urlComprarDesdeConsulta'/>";
    			var urlComprar = "";
    			if ( urlComprarDesdeConsulta == "" ) {
					urlComprar = _ACTION_REDIRECT_BUY+params;
    			} else { 
						var params2 = _ACTION_CONSULTA_COTIZACIONES_COMPRAR +
						"?cdUnieco=" + recordGridResultado.get('cdUnieco') +
						"&cdCiaaseg=" + recordGridResultado.get('cdCiaaseg') +
						"&dsUnieco=" + recordGridResultado.get('dsUnieco') +
						"&cdPerpag=" + recordGridResultado.get('cdPerpag') +
						"&cdRamo=" + recordGridResultado.get('cdRamo') +
						"&dsPerpag=" + recordGridResultado.get('dsPerpag') +
						"&cdPlan=" + idplan +
						"&dsPlan=" + desplan +
						"&numeroSituacion=" + nmplan +
						"&feEmisio=" + recordGridResultado.get('feEmisio') +
						"&feVensim=" + recordGridResultado.get('feVencim') +
						urlComprarDesdeConsulta;
    				urlComprar = params2;
    			}

            	window.location.replace( urlComprar );
        	}
        });

		Ext.getCmp('imprimir').on('click', function(){

			var _datosImpresion1 = "<s:property value='datosImpresion1'/>"; // Obtengo datos de TextFields de la pantalla de Resultados
			var _datosImpresion2 = "<s:property value='datosImpresion2'/>"; // Obtengo datos de headers del grid de la pantalla de Resultados
			var html = imprimirCotizacion( storeGrid, _datosImpresion1, _datosImpresion2, _USER );

			// *** Cambiamos los nombres de los id's para que al IMPRIMIR se muestre solo lo que queremos
			if( !Ext.isEmpty( document.getElementById("impresionResultadosCotizacionInvisible") ) ){
				document.getElementById("impresionResultadosCotizacionInvisible").id="impresionResultadosCotizacion";
			}
			if( !Ext.isEmpty( document.getElementById("impresionAyuda") ) ){
				document.getElementById("impresionAyuda").id="impresionAyudaInvisible";
			}

			document.getElementById("impresionResultadosCotizacion").innerHTML=html;
			window.print();
			
			//Esconder elemento que ya se imprimió para que no se muestre en la pantalla de cotizacion
			esconderElemento("impresionResultadosCotizacion");

        });
        
        Ext.getCmp('enviarCorreo').on('click', function(){

			<s:component template="builderFuncionBotonEnviarCorreo.vm" templateDir="templates" theme="components" />

			var _datosImpresion1 = "<s:property value='datosImpresion1'/>"; // Obtengo datos de TextFields de la pantalla de Resultados
			var _datosImpresion2 = "<s:property value='datosImpresion2'/>"; // Obtengo datos de headers del grid de la pantalla de Resultados
			var html = imprimirCotizacion( storeGrid, _datosImpresion1, _datosImpresion2, _USER );				
			enviarCorreo( html );

        });

		Ext.getCmp('gridResultado').addListener("headerclick", function(grid, columnIndex, e) {
        
             if(recordGridResultado==null || recordGridResultado=="undefined"){     
             	Ext.Msg.alert(_MSG_AVISO, _MSG_NO_ROW_SELECTED_DETAIL);        	
             } else if(mnPrima == undefined || mnPrima == null ||mnPrima == ""){
				Ext.Msg.alert(_MSG_AVISO, 'No hay cotizaci&oacute;n para el plan seleccionado. No existen coberturas asociadas');
			}
             // Se busca el caracter '/' porque el usuario al seleccionar un registro que contiene texto del grid de resultados
        	 // p.ej. 'Mensual', 'Contado', la variable 'nmplan' contiene la 'fecha de emision': ##/##/####
			 else if ( nmplan.search('/') != -1  ){
				Ext.Msg.alert(_MSG_AVISO, _MSG_NO_ROW_SELECTED_DETAIL);
			 }
			 //Si el columnIndex del header seleccionado es distinto del indexCol indexMnPrima de la celda seleccionada, NO mostrar nada(ACW-3106)
			 else if(columnIndex != indexMnPrima){
			 }
             else {
                 if(desplan=="Descripcion"){
                 }else{
                
                    windowCoberturas.show();
                     
                    Ext.get('dsAseguradora').dom.value=recordGridResultado.get('dsUnieco');
                    Ext.get('cdAseguradora').dom.value=recordGridResultado.get('cdCiaaseg');
                    Ext.get('cRamo').dom.value=recordGridResultado.get('cdRamo');
                    Ext.get('dsPlan').dom.value=desplan;
                    Ext.get('idPlan').dom.value=idplan;
        
                    storeCoberturas.baseParams['ciaAseg'] = recordGridResultado.get('cdCiaaseg');
                    storeCoberturas.baseParams['cdPlan'] = idplan;
                    //storeCoberturas.baseParams['numeroSituacion'] = recordGridResultado.get('numeroSituacion');
                    storeCoberturas.baseParams['numeroSituacion'] = nmplan;
					//alert("header numero de situacion=" + recordGridResultado.get('numeroSituacion') );
                    storeCoberturas.load({
                            callback : function(r,options,success) {
                                    // Ext.MessageBox.alert('storeCoberturas.getTotalCount()',storeCoberturas.getTotalCount()); 
                            }//callback 
                    });
                 }//else
            }//else
        });

	//////////// EXT BUILDER - function imprimirCotizacion( storeGrid )
	////////////			return String
	<s:component template="builderFuncionBotonImprimir.vm" templateDir="templates" theme="components" />

	}// end if(Ext.getCmp('gridResultado'))
	
	// Para obtener los valores de vigencia y en caso de que solo sea una aseguradora muestre su valor.
	function obtieneVigencia(store){

		if(store.getCount()>0){  
			if((store.collect('dsUnieco',false,true).length)>1){
				txtVigencia.setValue(store.getAt(0).get('feEmisio')+" - "+store.getAt(0).get('feVencim'));
				txtAseguradora.hide();
				txtAseguradora.setFieldLabel('');		
			}else{
				txtVigencia.setValue(store.getAt(0).get('feEmisio')+" - "+store.getAt(0).get('feVencim'));
			    txtAseguradora.setValue(store.getAt(0).get('dsUnieco'));			
			}
		}

    }
    
   simple.render('items');


	////////////////////// FUNCIONES...
	<s:component template="builderFunctionsResultCotizacion.vm" templateDir="templates" theme="components" />


    function getAyudaWindow(cdAseguradora,cRamo,cdGarant){

    var conn = new Ext.data.Connection();
    conn.request({
         url: 'flujocotizacion/obtenerAyudaCobertura.action?idA='+cdAseguradora+'&idR='+cRamo+'&idG='+cdGarant,
         method: 'POST',
         successProperty : '@success',
         callback: function (options, success, response){
                     if (Ext.util.JSON.decode(response.responseText).success == false) {
                         Ext.Msg.alert('Error', 'Ocurrio un error en la transaccion');
                     }else{
                         var ayudaVoE = Ext.util.JSON.decode(response.responseText).ayudaCoberturaVo.dsGarant;
                         var ayudaVoL = Ext.util.JSON.decode(response.responseText).ayudaCoberturaVo.dsAyuda;
                         if(ayudaVoE!='' && ayudaVoE!=null){
    
                            var windowAyuda= new Ext.Window({
                                title: 'Ayuda Coberturas',
                                html:'<table width=430 ><tr><td align=left bgcolor="#98012e" style="color:white;font-size:11px;"><b>'+ayudaVoE+'</b></td></tr><tr><td style="font-size:11px; ">'+ayudaVoL+'</td></tr></table>',
                                width: 450,
                                height:350,
                                bodyStyle:'background:white',
                                overflow:'auto',
                                autoScroll:true, 
                                buttonAlign:'center',
                                closable:false,
                                buttons: [{
						               text:'Imprimir',
						               handler: function() {
						               		// *** Cambiamos los nombres de los id's para que al IMPRIMIR se muestre solo lo que queremos
						               		if( !Ext.isEmpty( document.getElementById("impresionAyudaInvisible") ) ){
						               			document.getElementById("impresionAyudaInvisible").id="impresionAyuda";
						               		}
						               		if( !Ext.isEmpty( document.getElementById("impresionResultadosCotizacion") ) ){
						               			document.getElementById("impresionResultadosCotizacion").id="impresionResultadosCotizacionInvisible";	
						               		}
						               		
							               document.getElementById("impresionAyuda").innerHTML='<table width=430 ><tr><td align=left bgcolor="#98012e" style="color:white;font-size:12px;"><b>Ayuda Coberturas</b></td></tr><tr><td style="font-size:12px; ">'+ayudaVoL+'</td></tr></table>';
							               
							               var spans = document.getElementById("impresionAyuda").getElementsByTagName("span"); 
											for (var i = 0; i < spans.length; i++) { 
											    spans[i].style.fontSize="";
											    spans[i].style.lineHeight="";
											    spans[i].style.textAlign="";
											}
							               window.print();
							               
							               
							               // *** Esconder elemento que ya se imprimió para que no se muestre en la pantalla de cotizacion
							               esconderElemento("impresionAyuda");
						               }
						          },{
                                   text:'Cerrar',
                                   handler: function() { 
                                   windowAyuda.close();
                                   }
                                }]
                            });
                            
                            var pocisionCobertura = windowCoberturas.getPosition();
                            windowCoberturas.toBack();
                            windowAyuda.show();
                            windowAyuda.toFront();
                            windowAyuda.setPosition(pocisionCobertura[0]+200,pocisionCobertura[1]+30);

                         }else{
                            Ext.Msg.alert('Mensaje', 'No se encontraron datos');
                         }
                     }//else
        }//function
    });
    }

    Ext.getCmp('gridCoberturas').on('rowclick', function() {    
        getAyudaWindow(Ext.get('cdAseguradora').dom.value,Ext.get('cRamo').dom.value,idGarant);
    });
    
    
    function esconderElemento(idElemento){
    	var elemento = document.getElementById(idElemento);
		elemento.style.visibility = "hidden";
		elemento.style.height = "0";
		//document.getElementById("impresionResultadosCotizacion").style.visibility = "hidden";
		//document.getElementById("impresionResultadosCotizacion").style.height = "0";
	}

});

//</script>