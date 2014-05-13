<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<s:if test="false">
<script>
</s:if>
////// variables //////
var _4_urlAutorizarEmision = '<s:url namespace="/"           action="autorizaEmision"         />';
var _4_urlVerDocumentos    = '<s:url namespace="/documentos" action="ventanaDocumentosPoliza" />';
////// variables //////

////// funciones //////
function _4_onFolderClick(grid,rowIndex)
{
    debug(rowIndex);
    var record=grid.getStore().getAt(rowIndex);
    debug(record);
    Ext.create('Ext.window.Window',
    {
        title        : 'Documentaci&oacute;n'
        ,modal       : true
        ,buttonAlign : 'center'
        ,width       : 600
        ,height      : 400
        ,autoScroll  : true
        ,loader      :
        {
            url       : _4_urlVerDocumentos
            ,params   :
            {
                'smap1.nmpoliza'  : record.get('nmpoliza')&&record.get('nmpoliza').length>0?record.get('nmpoliza'):'0'
                ,'smap1.cdunieco' : record.get('cdunieco')
                ,'smap1.cdramo'   : record.get('cdramo')
                ,'smap1.estado'   : record.get('estado')
                ,'smap1.nmsuplem' : '0'
                ,'smap1.ntramite' : record.get('parametros.pv_otvalor03')
                ,'smap1.nmsolici' : record.get('nmsolici')&&record.get('nmsolici').length>0?record.get('nmsolici'):'0'
                ,'smap1.tipomov'  : '0'
            }
            ,scripts  : true
            ,autoLoad : true
        }
    }).show();
}

function _4_preVerComments(grid,rowIndex)
{
	debug('>_4_preVerComments');
	debug(grid.getStore().getAt(rowIndex));
	var ventana = Ext.create('Ext.window.Window',
	{
		title   : 'Observaciones'
		,modal  : true
		,width  : 600
		,height : 400
		,html   : grid.getStore().getAt(rowIndex).get('comments')
	});
	ventana.show();
	centrarVentanaInterna(ventana);
	debug('<_4_preVerComments');
}

function _4_preAutorizarEmision(grid,rowIndex)
{
	debug('>_4_preAutorizarEmision',grid.getStore().getAt(rowIndex).raw);
	if(grid.getStore().getAt(rowIndex).get('status')=='11')
	{
		grid.setLoading(true);
		Ext.Ajax.request(
		{
			url       : _4_urlAutorizarEmision
			,jsonData :
			{
				panel1 : grid.getStore().getAt(rowIndex).raw
			}
			,success  : function(response)
			{
				grid.setLoading(false);
				var jsonResponse = Ext.decode(response.responseText);
				debug('jsonResponse:',jsonResponse);
				if(jsonResponse.success)
				{
					mensajeCorrecto('Aviso',jsonResponse.mensajeRespuesta);
				}
				else
				{
					mensajeError(jsonResponse.mensajeRespuesta);
				}
			}
		    ,failure  : function()
		    {
		    	grid.setLoading(false);
		    	errorComunicacion();
		    }
		});
	}
	else
	{
		mensajeWarning('El tr&aacute;mite ya est&aacute; autorizado');
	}
	debug('<_4_preAutorizarEmision');
}
////// funciones //////

Ext.onReady(function()
{
	////// modelos //////
	////// modelos //////
	
	////// stores //////
	////// stores //////
	
	////// componentes //////
	////// componentes //////
	
	////// contenido //////
	////// contenido //////
});
<s:if test="false">
</script>
</s:if>