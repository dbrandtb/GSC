<%@ include file="/taglibs.jsp"%>

<script>

var _UrlAltaDeTramite = '<s:url namespace="/siniestros"  action="altaTramite"   />';
var _UrlRevisionDocsSiniestro = '<s:url namespace="/siniestros"  action="revisionDocumentos"   />';
var _UrlRechazarTramite = '<s:url namespace="/siniestros"  action="rechazarTramite"   />';
var _UrlDocumentosSiniestro = '<s:url namespace="/siniestros"  action="ventanaDocumentosSiniestros"   />';
var _UrlGenerarContrarecibo = '<s:url namespace="/siniestros"  action="generarContrarecibo"   />';
var _UrlTurnarAreaReclamaciones = '<s:url namespace="/siniestros"  action="turnarAreaReclamaciones"   />';
var _UrlDetalleSiniestro = '<s:url namespace="/siniestros"  action="detalleAfiliadosAfectados"   />';
var _UrlTurnarAreaMedica = '<s:url namespace="/siniestros"  action="turnarAreaMedica"   />';
var _UrlSolicitarPago = '<s:url namespace="/siniestros"  action="solicitarPago"   />';
var _UrlTurnarOperadorAR = '<s:url namespace="/siniestros"  action="turnarOperadorAR"   />';

var windowLoader; 

Ext.onReady(function() {
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
	
	_4_botones = {
			xtype: 'actioncolumn',
			hidden: false,
			width: 200,
			menuDisabled : true,
			sortable: false,
			items: [{
	            icon     : '${ctx}/resources/fam3icons/icons/folder_table.png',
	            tooltip : 'Revisi&oacute;n de Documentos',
	            handler : revDocumentosWindow
	        }
	        ,{
	            icon     : '${ctx}/resources/fam3icons/icons/cancel.png',
	            tooltip : 'Rechazar',
	            handler : rechazarTramiteWindow
	        }
	        ,{
	            icon     : '${ctx}/resources/fam3icons/icons/page_attach.png',
	            tooltip : 'Documentos',
	            handler : documentosWindow
	        },{
	            icon     : '${ctx}/resources/fam3icons/icons/accept.png',
	            tooltip : 'Generar Contrarecibo',
	            handler : generaContrareciboWindow
	        },{
	            icon     : '${ctx}/resources/fam3icons/icons/user_go.png',
	            tooltip : 'Turnar a &aacute;rea de Reclamaciones',
	            handler : turnarAreclamaciones
	        },{
	            icon     : '${ctx}/resources/fam3icons/icons/page_white_magnify.png',
	            tooltip : 'Detalle de Reclamaci&oacute;',
	            handler : detalleReclamacionWindow
	        },{
	            icon     : '${ctx}/resources/fam3icons/icons/user_go.png',
	            tooltip : 'Turnar a &aacute;rea M&eacute;dica',
	            handler : turnarAareaMedica
	        },{
	            icon     : '${ctx}/resources/fam3icons/icons/money_dollar.png',
	            tooltip : 'Solicitar Pago',
	            handler : solicitarPago
	        },{
	            icon     : '${ctx}/resources/fam3icons/icons/user_go.png',
	            tooltip : 'Turnar a Operador de Reclamaciones',
	            handler : turnarAoperadorReclamaciones
	        }
	    ]
	};

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
	            autoLoad : true
	        }
	    }).show();
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
	                'params.nmTramite'  : '1010',
	                'params.cdTipoPago' : '1',
	                'params.cdTipoAtencion'  : '1'
	            },
	            scripts  : true,
	            autoLoad : true
	        }
	    }).show();
	}
	function rechazarTramiteWindow(grid,rowIndex,colIndex){
		Ext.Msg.show({
	        title: 'Aviso',
	        msg: '¿Esta seguro que desea rechazar el tr&aacute;mite?',
	        buttons: Ext.Msg.YESNOCANCEL,
	        icon: Ext.Msg.QUESTION,
	        fn: function(buttonId, text, opt){
	        	if(buttonId == 'yes'){
	        		
	        		Ext.Ajax.request({
						url: _UrlRechazarTramite,
						jsonData: {
							/*params: {
					    		'pv_ntramite_i' : _nmTramite,
					    		'pv_cdtippag_i' : _tipoPago,
					    		'pv_cdtipate_i' : _tipoAtencion
					    	}*/
						},
						success: function() {
							mensajeCorrecto('Aviso','Se ha rechazado con exito.');
						},
						failure: function(){
							mensajeError('Error','No se pudo rechazar.');
						}
					});
	        	}
	        	
	        }
	    });
	}
	function documentosWindow(grid,rowIndex,colIndex){
		var record = grid.getStore().getAt(rowIndex);
	    
	    windowLoader = Ext.create('Ext.window.Window',{
	        modal       : true,
	        buttonAlign : 'center',
	        width       : 600,
	        height      : 400,
	        autoScroll  : true,
	        loader      : {
	            url     : _UrlDocumentosSiniestro,
	            params  : {
	                'params.nmTramite'  : '1010',
	                'params.cdTipoPago' : '1',
	                'params.cdTipoAtencion'  : '1'
	            },
	            scripts  : true,
	            autoLoad : true
	        }
	    }).show();
	}
	function generaContrareciboWindow(grid,rowIndex,colIndex){
		
		Ext.Msg.show({
	        title: 'Aviso',
	        msg: '¿Esta seguro que desea generar el contrarecibo?',
	        buttons: Ext.Msg.YESNOCANCEL,
	        icon: Ext.Msg.QUESTION,
	        fn: function(buttonId, text, opt){
	        	if(buttonId == 'yes'){
	        		
	        		Ext.Ajax.request({
						url: _UrlGenerarContrarecibo,
						jsonData: {
							/*params: {
					    		'pv_ntramite_i' : _nmTramite,
					    		'pv_cdtippag_i' : _tipoPago,
					    		'pv_cdtipate_i' : _tipoAtencion
					    	}*/
						},
						success: function() {
							mensajeCorrecto('Aviso','Se ha generado el contrarecibo con exito.');
						},
						failure: function(){
							mensajeError('Error','No se pudo generar contrarecibo.');
						}
					});
	        	}
	        	
	        }
	    });
		
	}
	function turnarAreclamaciones(grid,rowIndex,colIndex){
		Ext.Msg.show({
	        title: 'Aviso',
	        msg: '¿Esta seguro que desea turnar al Area de Reclamaciones?',
	        buttons: Ext.Msg.YESNOCANCEL,
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
		Ext.Msg.show({
	        title: 'Aviso',
	        msg: '¿Esta seguro que desea turnar al Area M&eacute;dica?',
	        buttons: Ext.Msg.YESNOCANCEL,
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
		
	}
	
	function turnarAoperadorReclamaciones(grid,rowIndex,colIndex){
		Ext.Msg.show({
	        title: 'Aviso',
	        msg: '¿Esta seguro que desea turnar al Operador de Reclamaciones?',
	        buttons: Ext.Msg.YESNOCANCEL,
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
	}
	function solicitarPago(grid,rowIndex,colIndex){
		Ext.Msg.show({
	        title: 'Aviso',
	        msg: '¿Esta seguro que desea solicitar el pago?',
	        buttons: Ext.Msg.YESNOCANCEL,
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
	}
	

});
</script>