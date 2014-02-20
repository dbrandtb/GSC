<%@ include file="/taglibs.jsp"%>
<s:if test="false">
<script>
</s:if>

var _UrlAltaDeTramite = '<s:url namespace="/siniestros"  action="altaTramite"   />';
var _UrlRevisionDocsSiniestro = '<s:url namespace="/siniestros"  action="revisionDocumentos"   />';
var _UrlRechazarTramiteWindwow = '<s:url namespace="/siniestros"  action="rechazoReclamaciones"   />';
var _UrlDocumentosPoliza = '<s:url namespace="/documentos" action="ventanaDocumentosPoliza" />';
var _UrlGenerarContrarecibo = '<s:url namespace="/siniestros"  action="generarContrarecibo"   />';
var _UrlTurnarAreaReclamaciones = '<s:url namespace="/siniestros"  action="turnarAreaReclamaciones"   />';
var _UrlDetalleSiniestro = '<s:url namespace="/siniestros"  action="detalleAfiliadosAfectados"   />';
var _UrlTurnarAreaMedica = '<s:url namespace="/siniestros"  action="turnarAreaMedica"   />';
var _UrlSolicitarPago = '<s:url namespace="/siniestros"  action="solicitarPago"   />';
var _UrlTurnarOperadorAR = '<s:url namespace="/siniestros"  action="turnarOperadorAR"   />';

var panDocUrlViewDoc     = '<s:url namespace ="/documentos" action="descargaDocInline" />';

var windowLoader;
var msgWindow;

	_4_botonesGrid =
	[
		<s:if test='%{"MESADECONTROL".equalsIgnoreCase(getRol())}'>
		{
		    text     : 'Alta de tr&aacute;mite'
		    ,icon    : '${ctx}/resources/fam3icons/icons/add.png'
		    ,handler : altaTramiteWindow
		}
		</s:if>
	];
	
	function altaTramiteWindow(){
	    windowLoader = Ext.create('Ext.window.Window',{
	        modal       : true,
	        buttonAlign : 'center',
	        width       : 800,
	        height      : 730,
	        autoScroll  : true,
	        loader      : {
	            url     : _UrlAltaDeTramite,
	            scripts  : true,
	            loadMask : true,
	            autoLoad : true
	        }
	    }).show();
	    centrarVentana(windowLoader);
	}
	
	
	function revDocumentosWindow(grid,rowIndex,colIndex){
	    var record = grid.getStore().getAt(rowIndex);
	    
	    windowLoader = Ext.create('Ext.window.Window',{
	        modal       : true,
	        buttonAlign : 'center',
	        width       : 600,
	        height      : 400,
	        autoScroll  : true,
	        loader      : {
	            url     : _UrlRevisionDocsSiniestro,
	            params  : {
	                'params.nmTramite'  : record.get('ntramite'),
	                'params.cdTipoPago' : record.get('parametros.pv_otvalor02'),
	                'params.cdTipoAtencion'  : record.get('parametros.pv_otvalor07')
	            },
	            scripts  : true,
	            loadMask : true,
	            autoLoad : true
	        }
	    }).show();
	    centrarVentana(windowLoader);
	}
	function rechazarTramiteWindow(grid,rowIndex,colIndex){
		
		var record = grid.getStore().getAt(rowIndex);
		if(record.get('status') == '4'){
			mensajeWarning('Este tr&aacute;mite ya se encuentra rechazado!');
			return;
		}
		
		windowLoader = Ext.create('Ext.window.Window',{
	        modal       : true,
	        buttonAlign : 'center',
	        width       : 700,
	        height      : 500,
	        autoScroll  : true,
	        loader      : {
	            url     : _UrlRechazarTramiteWindwow,
	            params  : {
	                'params.nmTramite'  : record.get('ntramite')
	            },
	            scripts  : true,
	            loadMask : true,
	            autoLoad : true
	        }
	    }).show();
		
		centrarVentana(windowLoader);
	}
	function documentosWindow(grid,rowIndex,colIndex){
		var record = grid.getStore().getAt(rowIndex);
	    debug('record',record);
	    
	    windowLoader = Ext.create('Ext.window.Window',{
	        modal       : true,
	        buttonAlign : 'center',
	        title       : 'Documentos del siniestro',
	        width       : 600,
	        height      : 400,
	        autoScroll  : true,
	        loader      : {
	            url     : _UrlDocumentosPoliza,
	            params  : {
	                'smap1.ntramite'  : record.get('ntramite')
	                ,'smap1.cdtippag' : record.get('parametros.pv_otvalor02')
	                ,'smap1.cdtipate' : record.get('parametros.pv_otvalor07')
	                ,'smap1.cdtiptra' : '16'
                    ,'smap1.cdunieco' : record.get('cdsucdoc')
                    ,'smap1.cdramo'   : record.get('cdramo')
                    ,'smap1.estado'   : record.get('estado')
	                ,'smap1.nmpoliza' : record.get('nmpoliza')
                    ,'smap1.nmsuplem' : '0'
                    ,'smap1.nmsolici' : ''
                    ,'smap1.tipomov'  : record.get('parametros.pv_otvalor02')
	            },
	            scripts  : true,
	            loadMask : true,
	            autoLoad : true
	        }
	    }).show();
	    centrarVentana(windowLoader);
	}
	function generaContrareciboWindow(grid,rowIndex,colIndex){
		
		var record = grid.getStore().getAt(rowIndex);
		msgWindow = Ext.Msg.show({
	        title: 'Aviso',
	        msg: '&iquest;Esta seguro que desea generar el contrarecibo?',
	        buttons: Ext.Msg.YESNO,
	        icon: Ext.Msg.QUESTION,
	        fn: function(buttonId, text, opt){
	        	if(buttonId == 'yes'){
	        		
	        		Ext.Ajax.request({
						url: _UrlGenerarContrarecibo,
						params: {
					    		'paramsO.pv_cdunieco_i' : record.get('cdunieco'),
					    		'paramsO.pv_cdramo_i'   : record.get('cdramo'),
					    		'paramsO.pv_estado_i'   : record.get('estado'),
					    		'paramsO.pv_nmpoliza_i' : record.get('nmpoliza'),
					    		'paramsO.pv_nmsuplem_i' : record.get('nmsuplem'),
					    		'paramsO.pv_ntramite_i' : record.get('ntramite'),
					    		'paramsO.pv_nmsolici_i' : record.get('nmsolici'),
					    		'paramsO.pv_tipmov_i'   : record.get('parametros.pv_otvalor02'),
					    	
						},
						success: function() {
							/* mensajeCorrecto('Aviso','Se ha generado el contrarecibo con exito.'); */
							var numRand=Math.floor((Math.random()*100000)+1);
				        	debug('numRand a: ',numRand);
				        	var windowVerDocu=Ext.create('Ext.window.Window',
				        	{
				        		title          : 'Contrarecibo de Documentos del Siniestro'
				        		,width         : 700
				        		,height        : 500
				        		,collapsible   : true
				        		,titleCollapse : true
				        		,html          : '<iframe innerframe="'+numRand+'" frameborder="0" width="100" height="100"'
				        		                 +'src="'+panDocUrlViewDoc+'?idPoliza=' + record.get('ntramite') + '&filename=' + '<s:text name="nombre.archivo.contrarecibo.siniestro"/>' +'">'
				        		                 +'</iframe>'
				        		,listeners     :
				        		{
				        			resize : function(win,width,height,opt){
				                        debug(width,height);
				                        $('[innerframe="'+numRand+'"]').attr({'width':width-20,'height':height-60});
				                    }
				        		}
				        	}).show();
				        	windowVerDocu.center();
						},
						failure: function(){
							mensajeError('Error','No se pudo generar contrarecibo.');
						}
					});
	        	}
	        	
	        }
	    });
		centrarVentana(msgWindow);
		
	}
	function turnarAreclamaciones(grid,rowIndex,colIndex){
		msgWindow = Ext.Msg.show({
	        title: 'Aviso',
	        msg: '&iquest;Esta seguro que desea turnar al Area de Reclamaciones?',
	        buttons: Ext.Msg.YESNO,
	        icon: Ext.Msg.QUESTION,
	        fn: function(buttonId, text, opt){
	        	if(buttonId == 'yes'){
	        		
	        		Ext.Ajax.request({
						url: _UrlTurnarAreaReclamaciones,
						jsonData: {
							/*params: {
					    		'pv_ntramite_i' : _nmTramite,
					    		'pv_cdtippag_i' : _tipoPago,
					    		'pv_cdtipate_i' : _tipoAtencion
					    	}*/
						},
						success: function() {
							mensajeCorrecto('Aviso','Se ha turnado con exito.');
						},
						failure: function(){
							mensajeError('Error','No se pudo turnar.');
						}
					});
	        	}
	        	
	        }
	    });
		centrarVentana(msgWindow);
	}
	function detalleReclamacionWindow(grid,rowIndex,colIndex){
		
		Ext.create('Ext.form.Panel').submit(
				{
					url     : _UrlDetalleSiniestro
					/*,params :
					{
						'smap1.cdramo'         : _4_smap1.cdramo
						,'smap1.cdtipsit'      : _4_smap1.cdtipsit
						,'smap1.gridTitle'     : _4_smap1.gridTitle
						,'smap2.pv_cdtiptra_i' : cdtiptra
						,'smap1.editable'      : editable
					}*/
				    ,standardSubmit : true
				});
	}
	function turnarAareaMedica(grid,rowIndex,colIndex){
		msgWindow = Ext.Msg.show({
	        title: 'Aviso',
	        msg: '&iquest;Esta seguro que desea turnar al Area M&eacute;dica?',
	        buttons: Ext.Msg.YESNO,
	        icon: Ext.Msg.QUESTION,
	        fn: function(buttonId, text, opt){
	        	if(buttonId == 'yes'){
	        		
	        		Ext.Ajax.request({
						url: _UrlTurnarAreaMedica,
						jsonData: {
							/*params: {
					    		'pv_ntramite_i' : _nmTramite,
					    		'pv_cdtippag_i' : _tipoPago,
					    		'pv_cdtipate_i' : _tipoAtencion
					    	}*/
						},
						success: function() {
							mensajeCorrecto('Aviso','Se ha turnado con exito.');
						},
						failure: function(){
							mensajeError('Error','No se pudo turnar.');
						}
					});
	        	}
	        	
	        }
	    });
		centrarVentana(msgWindow);
		
	}
	
	function turnarAoperadorReclamaciones(grid,rowIndex,colIndex){
		msgWindow = Ext.Msg.show({
	        title: 'Aviso',
	        msg: '&iquest;Esta seguro que desea turnar al Operador de Reclamaciones?',
	        buttons: Ext.Msg.YESNO,
	        icon: Ext.Msg.QUESTION,
	        fn: function(buttonId, text, opt){
	        	if(buttonId == 'yes'){
	        		
	        		Ext.Ajax.request({
						url: _UrlTurnarOperadorAR,
						jsonData: {
							/*params: {
					    		'pv_ntramite_i' : _nmTramite,
					    		'pv_cdtippag_i' : _tipoPago,
					    		'pv_cdtipate_i' : _tipoAtencion
					    	}*/
						},
						success: function() {
							mensajeCorrecto('Aviso','Se ha turnado con exito.');
						},
						failure: function(){
							mensajeError('Error','No se pudo turnar.');
						}
					});
	        	}
	        	
	        }
	    });
		centrarVentana(msgWindow);
	}
	function solicitarPago(grid,rowIndex,colIndex){
		msgWindow = Ext.Msg.show({
	        title: 'Aviso',
	        msg: '&iquest;Esta seguro que desea solicitar el pago?',
	        buttons: Ext.Msg.YESNO,
	        icon: Ext.Msg.QUESTION,
	        fn: function(buttonId, text, opt){
	        	if(buttonId == 'yes'){
	        		
	        		Ext.Ajax.request({
						url: _UrlSolicitarPago,
						jsonData: {
							/*params: {
					    		'pv_ntramite_i' : _nmTramite,
					    		'pv_cdtippag_i' : _tipoPago,
					    		'pv_cdtipate_i' : _tipoAtencion
					    	}*/
						},
						success: function() {
							mensajeCorrecto('Aviso','El pago se ha solicitado con exito.');
						},
						failure: function(){
							mensajeError('Error','No se pudo solicitar el pago.');
						}
					});
	        	}
	        	
	        }
	    });
		centrarVentana(msgWindow);
	}
	
<s:if test="false">
</script>
</s:if>