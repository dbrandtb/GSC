<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script>
////// urls //////
var _p49_urlRecuperacion  = '<s:url namespace="/recuperacion" action="recuperar"                />';
var _p49_urlGenerarLote   = '<s:url namespace="/consultas"    action="generarLote"              />';
var _p49_urlRecuperarCols = '<s:url namespace="/consultas"    action="recuperarColumnasGridPol" />';
////// urls //////

////// variables //////
var _p49_params = <s:property value="%{convertToJSON('params')}" escapeHtml="false" />;
debug('_p49_params:',_p49_params);

var _p49_storePolizas;

var _p49_dirIconos = '${icons}';
////// variables //////

////// overrides //////
////// overrides //////

////// componentes dinamicos //////
var _p49_formBusqItems = [<s:property value="items.itemsFormBusq"     escapeHtml="false" />];
var _p49_gridPolFields = [<s:property value="items.gridPolizasFields" escapeHtml="false" />];
var _p49_gridPolCols   = [];

_fieldByName('cdtipram').rowspan = 5;

var _p49_formBusqItemsCustom = [];
for(var i in _p49_formBusqItems)
{
    if(i==3)
    {
        _p49_formBusqItemsCustom.push(Ext.create('Ext.grid.Panel',
        {
            itemId       : '_p49_gridSucursales'
            ,width       : 255
            ,height      : 80
            ,hideHeaders : true
            ,rowspan     : 2
            ,store       : Ext.create('Ext.data.Store',
            {
                model : 'Generic'
                ,data : []
            })
            ,columns :
            [
                {
                    dataIndex : 'value'
                    ,flex     : 1
                }
                ,{
                    xtype    : 'actioncolumn'
                    ,width   : 30
                    ,icon    : '${icons}delete.png'
                    ,handler : function(view,row,col,item,e,record){ _fieldById('_p49_gridSucursales').getStore().remove(record); }
                }
            ]
        }));
    }
    _p49_formBusqItemsCustom.push(_p49_formBusqItems[i]);
}

////// componentes dinamicos //////

Ext.onReady(function()
{
    ////// requires //////
    Ext.require('${defines}VentanaImpresionLote');
    ////// requires //////

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
                itemId       : '_p49_formBusq'
                ,title       : 'B\u00DASQUEDA DE P\u00D3LIZAS'
                ,defaults    : { style : 'margin:5px;' }
                ,items       : _p49_formBusqItemsCustom
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
                        ,name    : 'botonbuscar'
                        ,icon    : '${icons}zoom.png'
                        ,handler : function(me)
                        {
                            var ck = 'Buscando p\u00F3lizas';
                            try
                            {
                                var form = me.up('form');
                                
                                if(!form.isValid())
                                {
                                    throw 'Favor de capturar todos los campos requeridos';
                                }
                                
                                if(_fieldById('_p49_gridSucursales').getStore().getCount()==0)
                                {
                                    throw 'Seleccione al menos una sucursal';
                                }
                                
                                _fieldById('_p49_botonImprimir1').disable();
                                _fieldById('_p49_botonImprimir2').disable();
                                
                                var cduniecos = '|';
                                _fieldById('_p49_gridSucursales').getStore().each(function(record)
                                {
                                    cduniecos = cduniecos + record.get('key') + '|';
                                });
                                
                                _fieldByName('cdtipram').enable();
                                
                                _setLoading(true,form);
                                _p49_loadPolizas(
                                    {
                                        'params.cdtipram'   : form.getValues()['cdtipram']
                                        ,'params.cduniecos' : cduniecos
                                        ,'params.cdramo'    : form.getValues()['cdramo']
                                        ,'params.ramo'      : form.getValues()['ramo']
                                        ,'params.nmpoliza'  : form.getValues()['nmpoliza']
                                        ,'params.fefecha'   : form.getValues()['fefecha']
                                        ,'params.cdusuari'  : form.getValues()['cdusuari']
                                        ,'params.cdagente'  : form.getValues()['cdagente']
                                    }
                                    ,function()
                                    {
                                        _fieldByName('cdtipram').disable();
                                        _setLoading(false,form);
                                    }
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
                        ,name    : 'botonlimpiar'
                        ,icon    : '${icons}arrow_refresh.png'
                        ,handler : function(me)
                        {
                            me.up('form').getForm().reset();
                            _fieldById('_p49_gridSucursales').getStore().removeAll();
                            _p49_navega(1);
                        }
                    }
                ]
            })
            ,Ext.create('Ext.grid.Panel',
            {
                itemId    : '_p49_gridPolizas'
                ,title    : 'RESULTADOS'
                ,columns  : _p49_gridPolCols
                ,height   : 250
                ,store    : _p49_storePolizas
                ,selModel :
                {
                    selType    : 'checkboxmodel'
                    ,mode      : 'SIMPLE'
                    ,listeners :
                    {
                        selectionchange : function(me,selected,eOpts)
	                    {
	                        _fieldById('_p49_botonImprimir1').setDisabled(selected.length==0);
	                        _fieldById('_p49_botonImprimir2').setDisabled(selected.length==0);
	                    }
                    }
                }
                ,buttonAlign : 'center'
                ,buttons     :
                [
                    {
                        text      : 'Impresi\u00F3n gen\u00E9rica'
                        ,icon     : '${icons}printer.png'
                        ,itemId   : '_p49_botonImprimir1'
                        ,disabled : true
                        ,handler  : function(me)
                        {
                            _p49_impresionClic('G');
                        }
                    }
                    ,{
                        text      : 'Impresi\u00F3n intercalada'
                        ,icon     : '${icons}printer.png'
                        ,itemId   : '_p49_botonImprimir2'
                        ,disabled : true
                        ,handler  : function(me)
                        {
                            _p49_impresionClic('I');
                        }
                    }
                ]
            })
        ]
    });
    ////// contenido //////
    
    ////// custom //////
    _fieldByName('cdramo').heredar = function(cdtipram)
    {
        this.getStore().load(
        {
            params :
            {
                'params.idPadre' : cdtipram
            }
        });
    }
    
    _fieldByName('cdtipram').on(
    {
        select : function(me,records)
        {
            var ck = 'Heredando producto';
            try
            {
                var cdtipram = records[0].get('key');
                debug('cdtipram:',cdtipram);
                _fieldByName('cdramo').heredar(cdtipram);
                _p49_navega(2);
            }
            catch(e)
            {
                manejaExeption(e,ck);
            }
        }
    });
    
    _fieldByName('cdunieco').on(
    {
        select : function(me,records)
        {
            var ck = 'Agregando sucursal';
            try
            {
                if(_fieldById('_p49_gridSucursales').getStore().indexOf(records[0])==-1)
                {
                    _fieldById('_p49_gridSucursales').getStore().insert(0,records[0]);
                }
                me.reset();
            }
            catch(e)
            {
                manejaException(e,ck);
            }
        }
    });
    ////// custom //////
    
    ////// loaders //////
    _p49_navega(1);
    
    /*
    setTimeout(function(){
    var ck = 'Invocando ventana de impresi\u00F3n';
                                            try
                                            {
                                                var venImp = Ext.create('VentanaImpresionLote',
                                                {
                                                    lote      : 19
                                                    ,cdtipram : 10
                                                    ,cdtipimp : 'G'
                                                    ,tipolote : 'P'
                                                    ,callback : function(){ _fieldById('_p49_gridPolizas').getStore().removeAll(); }
                                                    ,closable : false
                                                });
                                                centrarVentanaInterna(venImp.show());
                                            }
                                            catch(e)
                                            {
                                                manejaException(e,ck);
                                            }
                                            },1000);
    */
    
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

function _p49_navega(nivel)
{
    if(nivel==1)
    {
        var cmps = Ext.ComponentQuery.query('[name][name!=cdtipram]',_fieldById('_p49_formBusq'));
        debug('cmps:',cmps);
        for(var i in cmps)
        {
            cmps[i].hide();
        }
        _fieldById('_p49_gridSucursales').hide();
        _fieldByName('cdtipram').enable();
        _fieldById('_p49_gridPolizas').hide();
        _fieldById('_p49_gridPolizas').getStore().removeAll();
    }
    else if(nivel==2)
    {
        var cmps = Ext.ComponentQuery.query('[name][fieldLabel!=TIPO DE RAMO]',_fieldById('_p49_formBusq'));
        debug('cmps:',cmps);
        for(var i in cmps)
        {
            cmps[i].show();
        }
        _fieldById('_p49_gridSucursales').show();
        
        var cdtipram = _fieldByName('cdtipram').getValue();
        
        _fieldByName('cdtipram').disable();
        _fieldById('_p49_gridPolizas').show();
        
        var grid = _fieldById('_p49_gridPolizas');
        
        _setLoading(true,grid);
        Ext.Ajax.request(
        {
            url      : _p49_urlRecuperarCols
            ,params  :
            {
                'params.cdtipram'  : cdtipram
                ,'params.pantalla' : 'EXPLOTACION_DOCUMENTOS'
            }
            ,success : function(response)
            {
                _setLoading(false,grid);
                var ck = 'Decodificando respuesta al recuperar columnas';
                try
                {
                    var json = Ext.decode(response.responseText);
                    debug('### cols:',json);
                    if(json.success==true)
                    {
                        ck       = 'Transformando columnas';
                        var cols = Ext.decode('['+json.params.columns+']');
                        debug('cols:',cols);
                        
                        ck = 'Mostrando columnas';
                        grid.reconfigure(_p49_storePolizas,cols);
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
                errorComunicacion(null,'Error recuperando columnas');
            }
        });
    }
}

function _p49_impresionClic(tipoimp)
{
    var ck = 'Generando tr\u00E1mite';
    try
    {
        debug('_p49_impresionClic timoimp:',tipoimp);
        centrarVentanaInterna(Ext.MessageBox.confirm('Confirmar', 'Se generar\u00E1 el lote y los tr\u00E1mites de impresi\u00F3n ¿Desea continuar?', function(btn)
        {
            if(btn === 'yes')
            {
                var ck = 'Construyendo petici\u00F3n';
                try
                {
                    var jsonData =
                    {
                        params :
                        {
                            cdtipimp  : tipoimp
                            ,tipolote : 'P'
                        }
                        ,list  : []
                    };
                
                    var grid    = _fieldById('_p49_gridPolizas');
                    var records = grid.getSelectionModel().getSelection();
                    debug('records:',records);
                    
                    jsonData.params.cdtipram = records[0].get('cdtipram');
                    
                    for(var i in records)
                    {
                        var rec  = records[i];
                        jsonData.list.push(
                        {
                            cdunieco  : rec.get('cdunieco')
                            ,cdramo   : rec.get('cdramo')
                            ,estado   : rec.get('estado')
                            ,nmpoliza : rec.get('nmpoliza')
                            ,nmsuplem : rec.get('nmsuplem')
                            ,cdagente : rec.get('cdagente')
                            ,ntramite : rec.get('ntramite')
                        });
                    }
                    debug('jsonData:',jsonData);
                    
                    _setLoading(true,grid);
                    Ext.Ajax.request(
                    {
                        url       : _p49_urlGenerarLote
                        ,jsonData : jsonData
                        ,success  : function(response)
                        {
                            _setLoading(false,grid);
                            var ck = 'Decodificando respuesta al generar lote';
                            try
                            {
                                var json = Ext.decode(response.responseText);
                                debug('### generarLote:',json);
                                if(json.success==true)
                                {
                                    mensajeCorrecto(
                                        'Lote generado'
                                        ,'Se gener\u00F3 el lote '+json.params.lote
                                        ,function()
                                        {
                                            var ck = 'Invocando ventana de impresi\u00F3n';
                                            try
                                            {
                                                var venImp = Ext.create('VentanaImpresionLote',
                                                {
                                                    lote      : json.params.lote
                                                    ,cdtipram : json.params.cdtipram
                                                    ,cdtipimp : json.params.cdtipimp
                                                    ,tipolote : 'P'
                                                    ,callback : function(){ _fieldById('_p49_gridPolizas').getStore().removeAll(); }
                                                    ,closable : false
                                                });
                                                centrarVentanaInterna(venImp.show());
                                            }
                                            catch(e)
                                            {
                                                manejaException(e,ck);
                                            }
                                        });
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
                            _setLoading(false,grid);
                            errorComunicacion(null,'Error al generar lote');
                        }
                    });
                }
                catch(e)
                {
                    manejaException(e,ck);
                }
            }
        }));
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
<div id="_p49_divpri" style="height:600px;border:1px solid #CCCCCC;"></div>
</body>
</html>