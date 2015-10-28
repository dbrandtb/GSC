<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<s:if test="false">
<script>
</s:if>
////// variables //////
var _4_urlActualizarStatusRemesa = '<s:url namespace="/consultas" action="actualizarStatusRemesa" />';
////// variables //////

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

////// funciones //////

function _4_calculaDetalleImpresion(v,md,rec)
{
    debug('_4_calculaDetalleImpresion rec:',rec,'.');
    
    var calc = '';
    
    try
    {
        var ntramite = rec.get('ntramite');
        var req      = Number(rec.get('parametros.pv_otvalor04'));
        var eje      = Number(rec.get('parametros.pv_otvalor05'));
	    
	    debug('ntramite,req,eje {',ntramite,req,eje,'}');
	    
	    var prim = true;
	    
	    if(req%10==1)//necesito B
	    {
	        if(eje%10==0)//falta B
	        {
	            if(prim)
	            {
	                prim =  false;
	                calc += 'PAPELER\u00CDA';
	            }
	            else
	            {
	                calc += ', PAPELER\u00CDA';
	            }
	        }
	    }
	    
	    req = Math.floor(req/10);
	    eje = Math.floor(eje/10);
	    
	    if(req%10==1)//necesito M
	    {
	        if(eje%10==0)//falta M
	        {
	            if(prim)
	            {
	                prim =  false;
	                calc += 'RECIBOS';
	            }
	            else
	            {
	                calc += ', RECIBOS';
	            }
	        }
	    }
	    
	    req = Math.floor(req/10);
	    eje = Math.floor(eje/10);
	    
	    if(req%10==1)//necesito C
	    {
	        if(eje%10==0)//falta C
	        {
	            if(prim)
	            {
	                prim =  false;
	                calc += 'CREDENCIALES';
	            }
	            else
	            {
	                calc += ', CREDENCIALES';
	            }
	        }
	    }
	}
	catch(e)
	{
	    debugError(e);
	    calc = 'error';
	}
    
    return calc;
}

function _4_actualizarRemesaClic(bot)
{
    centrarVentanaInterna(Ext.create('Ext.window.Window',
    {
        title        : 'ACTUALIZAR REMESA'
        ,modal       : true
        ,defaults    : { style : 'margin:5px;' }
        ,closeAction : 'destroy'
        ,layout      :
        {
            type     : 'table'
            ,columns : 2
        }
        ,items    :
        [
            {
                xtype       : 'numberfield'
                ,fieldLabel : 'Remesa'
                ,status     : 36
            }
            ,{
                xtype    : 'button'
                ,icon    : '${icons}sitemap_color.png'
                ,text    : 'Marcar como armada'
                ,status  : 36
                ,handler : _4_marcarRemesaClic
            }
            ,{
                xtype       : 'numberfield'
                ,fieldLabel : 'Remesa'
                ,status     : 37
            }
            ,{
                xtype    : 'button'
                ,icon    : '${icons}user_comment.png'
                ,text    : 'Marcar como entrega f\u00EDsica'
                ,status  : 37
                ,handler : _4_marcarRemesaClic
            }
            ,{
                xtype       : 'numberfield'
                ,fieldLabel : 'Remesa'
                ,status     : 38
            }
            ,{
                xtype    : 'button'
                ,icon    : '${icons}package.png'
                ,text    : 'Marcar como entraga paqueter\u00EDa'
                ,status  : 38
                ,handler : _4_marcarRemesaClic
            }
        ]
    }).show());
}

function _4_marcarRemesaClic(bot)
{
    debug('_4_marcarRemesaClic');
    var ck = 'Solicitando cambio de status de remesa';
    
    try
    {
	    var textfield = Ext.ComponentQuery.query('[xtype=numberfield][status='+bot.status+']')[0];
	    if(Ext.isEmpty(textfield.getValue()))
	    {
	        throw 'Favor de introducir remesa';
	    }
	    
	    var win = bot.up('window');
	    _setLoading(true,win);
	    Ext.Ajax.request(
	    {
	        url      : _4_urlActualizarStatusRemesa
	        ,params  :
	        {
	            'params.ntramite' : textfield.getValue()
	            ,'params.status'  : bot.status
	        }
	        ,success : function(response)
	        {
	            _setLoading(false,win);
	            
	            var vk = 'Decodificando respuesta al cambiar status de remesa';
	            try
	            {
	                var json = Ext.decode(response.responseText);
	                debug('### actualizar status:',json);
	                if(json.success==true)
	                {
	                    mensajeCorrecto(
	                        'Remesa actualizada'
	                        ,'La remesa ha sido actualizada'
	                        ,function()
	                        {
	                            win.destroy();
	                            loadMcdinStore();
	                        }
	                    );
	                }
	                else
	                {
	                    mensajeError(json.message);
	                }
	            }
	            catch(e)
	            {
	                manejaException(e,ck);
	            }
	        }
	        ,failure : function()
	        {
	            _setLoading(false,win);
	            errorComunicacion(null,'Error al solicitar cambio de status de remesa');
	        }
	    });
	}
	catch(e)
	{
	    manejaException(e,ck);
	}
}
////// funciones //////
<s:if test="false">
</script>
</s:if>