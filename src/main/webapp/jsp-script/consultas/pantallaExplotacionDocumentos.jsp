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
                                
                                _fieldById('_p49_botonImprimir').disable();
                                
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
	                        _fieldById('_p49_botonImprimir').setDisabled(selected.length==0);
	                    }
                    }
                }
                ,buttonAlign : 'center'
                ,buttons     :
                [
                    {
                        text      : 'Imprimir'
                        ,icon     : '${icons}printer.png'
                        ,itemId   : '_p49_botonImprimir'
                        ,disabled : true
                        ,handler  : function(me)
                        {
                            var ck = 'Creando ventana de impresi\u00F3n';
                            try
                            {
                                centrarVentanaInterna(Ext.MessageBox.confirm('Confirmar', 'Se generar\u00E1 un tr\u00E1mite de impresi\u00F3n ¿Desea continuar?', function(btn)
                                {
                                    if(btn === 'yes')
                                    {   
                                        var venImp = Ext.create('VentanaImpresionLote',
                                        {
                                            records   : me.up('grid').getSelectionModel().getSelection()
                                            ,ntramite : ''
                                            ,callback : function(){ _fieldById('_p49_gridPolizas').getStore().removeAll(); }
                                        });
                                        centrarVentanaInterna(venImp.show());
                                    }
                                }));
                            }
                            catch(e)
                            {
                                manejaException(e,ck);
                            }
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
        _fieldByName('cdtipram').disable();
        _fieldById('_p49_gridPolizas').show();
    }
}
////// funciones //////
</script>
</head>
<body>
<div id="_p49_divpri" style="height:500px;border:1px solid #999999;"></div>
</body>
</html>