<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<s:if test="false">
<script>
</s:if>
////// variables //////
var _4_urlAutorizarEmision = '<s:url namespace="/"            action="autorizaEmision"         />';
var _4_urlVerDocumentos    = '<s:url namespace="/documentos"  action="ventanaDocumentosPoliza" />';
var _4_urlAutorizarEndoso  = '<s:url namespace="/endosos"     action="autorizarEndoso"         />';
var _4_urlUpdateStatus     = '<s:url namespace="/mesacontrol" action="actualizarStatusTramite" />';
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
	debug('>_4_preAutorizarEmision',grid.getStore().getAt(rowIndex).data);
	if(grid.getStore().getAt(rowIndex).get('status')=='11')
	{
		grid.setLoading(true);
		var record=grid.getStore().getAt(rowIndex);
		if(record.get('parametros.pv_otvalor05')=='EMISION')
		{
			debug('Autorizar emision');
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
			debug('Autorizar endoso');
			Ext.Ajax.request(
	        {
	            url       : _4_urlAutorizarEndoso
	            ,params   :
	            {
	                'smap1.ntramiteemi'  : record.get('parametros.pv_otvalor08')
	                ,'smap1.ntramiteend' : record.get('parametros.pv_otvalor03')
	                ,'smap1.cdunieco'    : record.get('cdunieco')
	                ,'smap1.cdramo'      : record.get('cdramo')
	                ,'smap1.estado'      : record.get('estado')
	                ,'smap1.nmpoliza'    : record.get('nmpoliza')
	                ,'smap1.nmsuplem'    : record.get('nmsuplem')
	                ,'smap1.nsuplogi'    : record.get('parametros.pv_otvalor07')
	                ,'smap1.cdtipsup'    : record.get('parametros.pv_otvalor06')
	                ,'smap1.status'      : '3'
	                ,'smap1.fechaEndoso' : Ext.Date.format(record.get('ferecepc'),'d/m/Y')
	                ,'smap1.observacion' : ''
	            }
	            ,success  : function(response)
	            {
	            	grid.setLoading(false);
	                var json=Ext.decode(response.responseText);
	                if(json.success==true)
	                {
	                	Ext.Ajax.request
                        ({
                            url     : _4_urlUpdateStatus
                            ,params : 
                            {
                                'smap1.ntramite'  : record.get('ntramite')
                                ,'smap1.status'   : '3'//confirmado 
                                ,'smap1.comments' : ''
                            }
                        });
	                	mensajeCorrecto('Aviso','Endoso autorizado');
	                }
	                else
	                {
	                    mensajeError(json.error);
	                }
	            }
	            ,failure  : function()
	            {
	            	grid.setLoading(false);
	                errorComunicacion();
	            }
	        });
		}
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