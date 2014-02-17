<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>

var _UrlAltaDeTramite = '<s:url namespace="/siniestros"  action="altaTramite"   />';
var _UrlRevisionDocsSiniestro = '<s:url namespace="/siniestros"  action="revisionDocumentos"   />';

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
		
	}
	function documentosWindow(grid,rowIndex,colIndex){
		
	}
	function generaContrareciboWindow(grid,rowIndex,colIndex){
		
	}
	function turnarAreclamaciones(grid,rowIndex,colIndex){
		
	}
	function detalleReclamacionWindow(grid,rowIndex,colIndex){
		
	}
	function turnarAareaMedica(grid,rowIndex,colIndex){
		
	}
	function solicitarPago(grid,rowIndex,colIndex){
		
	}
	function turnarAoperadorReclamaciones(grid,rowIndex,colIndex){
		
	}

});
</script>