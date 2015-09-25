<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script>
////// urls //////
var _p49_urlRecuperacion = '<s:url namespace="/recuperacion" action="recuperar" />';
////// urls //////

////// variables //////
var _p49_params = <s:property value="%{convertToJSON('params')}" escapeHtml="false" />;
debug('_p49_params:',_p49_params);

var _p49_storePolizas;

var _p49_dirIconos = '${icons}';
////// variables //////

////// overrides //////
////// overrides //////

////// componentes dinamicos /////
var _p49_formBusqItems = [<s:property value="items.itemsFormBusq"     escapeHtml="false" />];
var _p49_gridPolFields = [<s:property value="items.gridPolizasFields" escapeHtml="false" />];
var _p49_gridPolCols   = [<s:property value="items.gridPolizasCols"   escapeHtml="false" />];

_p49_gridPolCols.push(
{
    xtype    : 'actioncolumn'
    ,width   : 30
    ,icon    : '${icons}printer.png'
    ,tooltip : 'Ver documentos'
});

////// componentes dinamicos //////

Ext.onReady(function()
{
    ////// modelos //////
    Ext.define('_p49_modeloPoliza',
    {
        extend  : 'Ext.data.Model'
        ,fields : _p49_gridPolFields
    });
    ////// modelos //////
    
    ////// stores //////
    _p49_storePolizas = Ext.create('Ext.data.Store',
    {
        model     : '_p49_modeloPoliza'
        ,autoLoad : false
        ,proxy    :
        {
            url          : _p49_urlRecuperacion
            ,type        : 'ajax'
            ,extraParams :
            {
                'params.consulta' : 'RECUPERAR_POLIZAS_PARA_EXPLOTAR_DOCS'
            }
            ,reader :
            {
               type             : 'json'
               ,root            : 'list'
               ,successProperty : 'success'
               ,messageProperty : 'message'
            }
        }
    });
    ////// stores //////
    
    ////// componentes //////
    ////// componentes //////
    
    ////// contenido //////
    Ext.create('Ext.panel.Panel',
    {
        renderTo  : '_p49_divpri'
        ,itemId   : '_p49_panelpri'
        ,defaults : { style : 'margin:5px;' }
        ,border   : 0
        ,items    :
        [
            Ext.create('Ext.form.Panel',
            {
                itemId       : '_p48_formBusq'
                ,title       : 'B\u00DASQUEDA DE P\u00D3LIZAS'
                ,defaults    : { style : 'margin:5px;' }
                ,items       : _p49_formBusqItems
                ,layout      :
                {
                    type     : 'table'
                    ,columns : 3
                }
                ,buttonAlign : 'center'
                ,buttons     :
                [
                    {
                        text     : 'Buscar'
                        ,icon    : '${icons}zoom.png'
                        ,handler : function(me)
                        {
                            var ck = 'Buscando p\u00F3lizas';
                            try
                            {
                                var form = me.up('form');
                                _setLoading(true,form);
                                _p49_loadPolizas(
                                    {
                                        'params.cdunieco' : 1000
                                    }
                                    ,function(){ _setLoading(false,form); }
                                );
                            }
                            catch(e)
                            {
                                manejaException(e,ck);
                            }
                        }
                    }
                    ,{
                        text     : 'Limpiar'
                        ,icon    : '${icons}arrow_refresh.png'
                        ,handler : function(me)
                        {
                            me.up('form').getForm().reset();
                        }
                    }
                ]
            })
            ,Ext.create('Ext.grid.Panel',
            {
                itemId   : '_p49_gridPolizas'
                ,title   : 'RESULTADOS'
                ,columns : _p49_gridPolCols
                ,height  : 250
                ,store   : _p49_storePolizas
            })
        ]
    });
    ////// contenido //////
    
    ////// custom //////
    ////// custom //////
    
    ////// loaders //////
    ////// loaders //////
});

////// funciones //////
function _p49_loadPolizas(params,callback)
{
    debug('_p49_loadPolizas params:',params,'callback?:',!Ext.isEmpty(callback));
    _p49_storePolizas.load(
    {
        params    : params
        ,callback : function(records,op,success)
        {
            callback();
            if(success)
            {
                if(records.length==0)
                {
                    mensajeWarning('No hay resultados');
                }
            }
            else
            {
                mensajeError(op.getError());
            }
        }
    });
}
////// funciones //////
</script>
</head>
<body>
<div id="_p49_divpri" style="height:500px;border:1px solid #999999;"></div>
</body>
</html>