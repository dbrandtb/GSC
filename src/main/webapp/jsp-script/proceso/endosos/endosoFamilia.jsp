<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script>
////// urls //////
var _p48_urlRecuperacion  = '<s:url namespace="/recuperacion" action="recuperar"   />';
var _p48_urlMovimientoSMD = '<s:url namespace="/movimientos"  action="ejecutarSMD" />';
////// urls //////

////// variables //////
var _p48_params = <s:property value="%{convertToJSON('params')}" escapeHtml="false" />;
debug('_p48_params:',_p48_params);

var _p48_store;
var _p48_storeMov;
////// variables //////

////// overrides //////
////// overrides //////

////// componentes dinamicos //////
var _p48_itemsPoliza  = [ <s:property value="items.itemsPoliza"  escapeHtml="false" /> ];
var _p48_fieldsInciso = [ <s:property value="items.fieldsInciso" escapeHtml="false" /> ];
var _p48_colsInciso   = [ <s:property value="items.colsInciso"   escapeHtml="false" /> ];
var _p48_colsMovimi   = [
                            {
                                xtype    : 'actioncolumn'
                                ,width   : 30
                                ,icon    : '${icons}arrow_undo.png'
                                ,tooltip : 'Deshacer'
                                ,handler : _p48_deshacerMov
                            }
                            ,{
                                text       : 'MOV.'
                                ,width     : 70
                                ,dataIndex : 'MOV'
                                ,renderer  : function(v)
                                {
                                    if(v=='-')
                                    {
                                        return '<span style="font-size:10px;">Quitar</span>';
                                    }
                                    if(v=='+')
                                    {
                                        return '<span style="font-size:10px;">Agregar</span>';
                                    }
                                    return v;
                                }
                            }
                            ,<s:property value="items.colsInciso"   escapeHtml="false" />
                        ];
var _p48_itemsEndoso  = [ <s:property value="items.itemsEndoso"  escapeHtml="false" /> ];
////// componentes dinamicos //////

Ext.onReady(function()
{
    ////// modelos //////
    Ext.define('_p48_modelo',
    {
        extend  : 'Ext.data.Model'
        ,fields : _p48_fieldsInciso
    });
    ////// modelos //////
    
    ////// stores //////
    _p48_store = Ext.create('Ext.data.Store',
    {
        autoLoad : false
        ,model   : '_p48_modelo'
        ,proxy   :
        {
            type         : 'ajax'
            ,url         : _p48_urlRecuperacion
            ,extraParams :
            {
                'params.consulta'  : 'RECUPERAR_INCISOS_POLIZA_GRUPO_FAMILIA'
                ,'params.cdunieco' : _p48_params.CDUNIECO
                ,'params.cdramo'   : _p48_params.CDRAMO
                ,'params.estado'   : _p48_params.ESTADO
                ,'params.nmpoliza' : _p48_params.NMPOLIZA
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
    _p48_cargarStore();
    
    _p48_storeMov = Ext.create('Ext.data.Store',
    {
        autoLoad : false
        ,model   : '_p48_modelo'
        ,proxy   :
        {
            type         : 'ajax'
            ,url         : _p48_urlRecuperacion
            ,extraParams :
            {
                'params.consulta'  : 'RECUPERAR_MOVIMIENTOS_ENDOSO_ALTA_BAJA_ASEGURADO'
                ,'params.cdunieco' : _p48_params.CDUNIECO
                ,'params.cdramo'   : _p48_params.CDRAMO
                ,'params.estado'   : _p48_params.ESTADO
                ,'params.nmpoliza' : _p48_params.NMPOLIZA
                ,'params.nmsuplem' : _p48_params.nmsuplem_endoso
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
    if(!Ext.isEmpty(_p48_params.nmsuplem_endoso))
    {
        _p48_cargarStoreMov();
    }
    ////// stores //////
    
    ////// componentes //////
    ////// componentes //////
    
    ////// contenido //////
    Ext.create('Ext.panel.Panel',
    {
        renderTo  : '_p48_divpri'
        ,defaults : { style : 'margin:5px;' }
        ,border   : 0
        ,items    :
        [
            Ext.create('Ext.panel.Panel',
            {
                title     : 'DATOS DE P\u00D3LIZA'
                ,defaults : { style : 'margin:5px;' }
                ,layout   : 'hbox'
                ,items    : _p48_itemsPoliza
            })
            ,Ext.create('Ext.grid.Panel',
            {
                title     : 'ASEGURADOS'
                ,itemId   : '_p48_gridAsegurados'
                ,columns  : _p48_colsInciso
                ,store    : _p48_store
                ,height   : 200
                ,selModel :
                {
                    selType        : 'checkboxmodel'
                    ,allowDeselect : true
                    ,mode          : 'SINGLE'
                }
                ,tbar     :
                [
                    {
                        text    : 'Agregar'
                        ,icon   : '${icons}accept.png'
                        ,hidden : _p48_params.operacion!='alta'
                    }
                    ,{
                        text     : 'Quitar'
                        ,icon    : '${icons}delete.png'
                        ,hidden  : _p48_params.operacion!='baja'
                        ,handler : _p48_quitarAseguradoClic
                    }
                ]
            })
            ,Ext.create('Ext.grid.Panel',
            {
                title    : 'MOVIMIENTOS'
                ,itemId  : '_p48_gridMovimientos'
                ,columns : _p48_colsMovimi
                ,store   : _p48_storeMov
                ,height  : 200
            })
            ,Ext.create('Ext.form.Panel',
            {
                title        : 'DATOS DEL ENDOSO'
                ,defaults    : { style : 'margin : 5px;' }
                ,items       : _p48_itemsEndoso
                ,buttonAlign : 'center'
                ,buttons     :
                [
                    {
                        text  : 'Confirmar'
                        ,icon : '${ctx}/resources/fam3icons/icons/key.png'
                    }
                ]
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
function _p48_cargarStore()
{
    _p48_store.load(function(records,op,success)
    {
        if(!success)
        {
            mensajeError('Error al recuperar asegurados: '+op.getError());
        }
    });
}

function _p48_cargarStoreMov()
{
    _p48_storeMov.load(function(records,op,success)
    {
        if(!success)
        {
            mensajeError('Error al recuperar movimientos: '+op.getError());
        }
    });
}

function _p48_quitarAseguradoClic(me)
{
    debug('>_p48_quitarAseguradoClic');
    var ck = 'Quitando asegurado';
    try
    {
        var gridAsegurados = _fieldById('_p48_gridAsegurados');
        if(gridAsegurados.getSelectionModel().getSelection().length==0)
        {
            throw 'Seleccione un asegurado';
        }
        
        var record = gridAsegurados.getSelectionModel().getSelection()[0];
        debug('record:',record);
        
        _p48_storeMov.each(function(record2)
        {
            if(Number(record2.get('NMSITUAC'))==Number(record.get('NMSITUAC')))
            {
                throw 'Este inciso ya se encuentra en los movimientos';
            }
        });
        
        ck                   = 'Preparando par\u00E1metros para petici\u00F3n';
        var params           = record.raw;
        params['movimiento'] = 'PASO_QUITAR_ASEGURADO';
        params['cdtipsup']   = _p48_params.cdtipsup;
        debug('params:',params);
        
        _setLoading(true,gridAsegurados);
        Ext.Ajax.request(
        {
            url       : _p48_urlMovimientoSMD
            ,jsonData : { params : params }
            ,success  : function(response)
            {
                _setLoading(false,gridAsegurados);
                var ck = 'Decodificando respuesta al quitar asegurado';
                try
                {
                    var json = Ext.decode(response.responseText);
                    debug('### quitar:',json);
                    if(json.success==true)
                    {
                        _p48_params['nmsuplem_endoso'] = json.params.nmsuplem_endoso;
                        _p48_storeMov.proxy.extraParams['params.nmsuplem'] = _p48_params['nmsuplem_endoso'];
                        _p48_cargarStoreMov();
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
            ,failure  : function()
            {
                _setLoading(false,gridAsegurados);
            }
        });
    }
    catch(e)
    {
        manejaException(e,ck);
    }
}

function _p48_deshacerMov(v,row,col,item,e,record)
{
    debug('_p48_deshacerMov record.raw:',record.raw);
    var ck = 'Revirtiendo movimiento';
    try
    {
        var grid = _fieldById('_p48_gridMovimientos');
        
        var jsonData =
        {
            params : record.raw
        };
        jsonData.params.cdtipsup        = _p48_params.cdtipsup;
        jsonData.params.nmsuplem_endoso = _p48_params.nmsuplem_endoso;
        jsonData.params.movimiento      = 'DESHACER_PASO_ASEGURADO';
        
        _setLoading(true,grid);
        Ext.Ajax.request(
        {
            url       : _p48_urlMovimientoSMD
            ,jsonData : jsonData
            ,success  : function(response)
            {
                _setLoading(false,grid);
                var ck = 'Decodificando respuesta al deshacer movimiento';
                try
                {
                    var json = Ext.decode(response.responseText);
                    debug('### deshacer:',json);
                    if(json.success==true)
                    {
                        _p48_cargarStoreMov();
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
                _setLoading(false,grid);
                errorComunicacion(null,'Error al deshacer movimiento');
            }
        });
    }
    catch(e)
    {
        manejaException(e,ck);
    }
}
////// funciones //////
</script>
</head>
<body>
<div id="_p48_divpri" style="height:500px;border:1px solid #999999"></div>
</body>
</html>