
var _UrlRevisionDocsSiniestro = '<s:url namespace="/siniestros"  action="revisionDocumentos"   />';

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

function revDocumentosWindow(grid,rowIndex,colIndex){
    var record = grid.getStore().getAt(rowIndex);
    
    var windowRevDocs = Ext.create('Ext.window.Window',{
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
        },
        buttons: [{
        	text: 'Cerrar',
        	handler: function(){windowRevDocs.close();}
        }]
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

