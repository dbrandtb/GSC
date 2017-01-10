<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script>
////// urls //////
////// urls //////

////// variables //////
////// variables //////

////// overrides //////
////// overrides //////

////// componentes dinamicos //////
var _p62_filtroItems            = [ <s:property value="items.filtroItems"            escapeHtml="false" /> ],
    _p62_gridSucursalesFields   = [ <s:property value="items.gridSucursalesFields"   escapeHtml="false" /> ],
    _p62_gridSucursalesColumns  = [ <s:property value="items.gridSucursalesColumns"  escapeHtml="false" /> ],
    _p62_gridUsuariosFields     = [ <s:property value="items.gridUsuariosFields"     escapeHtml="false" /> ],
    _p62_gridUsuariosColumns    = [ <s:property value="items.gridUsuariosColumns"    escapeHtml="false" /> ],
    _p62_gridUsuariosAllFields  = [ <s:property value="items.gridUsuariosAllFields"  escapeHtml="false" /> ],
    _p62_gridUsuariosAllColumns = [ <s:property value="items.gridUsuariosAllColumns" escapeHtml="false" /> ];
////// componentes dinamicos //////

Ext.onReady(function()
{
    ////// modelos //////
    ////// modelos //////
    
    ////// stores //////
    ////// stores //////
    
    ////// componentes //////
    ////// componentes //////
    
    ////// contenido //////
    Ext.create('Ext.panel.Panel',{
        itemId   : '_p62_panelpri',
        renderTo : '_p62_divpri',
        border   : 0,
        defaults : { style : 'margin : 5px;' },
        layout   : {
            type    : 'table',
            columns : 2
        },
        items    : [
            Ext.create('Ext.form.Panel', {
                itemId      : '_p62_formFiltro',
                title       : 'BUSCAR SUCURSALES',
                items       : _p62_filtroItems,
                buttonAlign : 'center',
                colspan     : 2,
                buttons     : [
                    {
                        text    : 'Buscar',
                        icon    : '${icons}zoom.png',
                        handler : function (me) {
                            debug('form.Buscar.clic', arguments);
                            var ck = 'Buscando sucursales';
                            try {
                                if (!me.up('form').isValid()) {
                                    return datosIncompletos();
                                }
                                _fieldById('_p62_gridSucursales').getStore().load({
                                    params : {
                                        'params.cdtipram' : me.up('form').getValues().CDUNIECO
                                    }
                                });
                            } catch (e) {
                                manejaException(e, ck);
                            }
                        }
                    }
                ]
            }),
            Ext.create('Ext.grid.Panel', {
                itemId  : '_p62_gridSucursales',
                title   : 'SUCURSALES',
                height  : 250,
                width   : 750,
                store   : Ext.create('Ext.data.Store', {
                    fields   : _p62_gridSucursalesFields,
                    autoLoad : false,
                    proxy    : {
                        url : _GLOBAL_URL_RECUPERACION,
                        type        : 'ajax',
                        extraParams : {
                            'params.consulta' : 'RECUPERAR_DESPACHADOR_DATOS_SUCURSALES'
                        },
                        reader : {
                            type : 'json',
                            root : 'list'
                        }
                    }
                }),
                columns : _p62_gridSucursalesColumns
            }),
            Ext.create('Ext.grid.Panel', {
                itemId      : '_p62_gridRoles',
                title       : 'ROLES',
                hideHeaders : true,
                height      : 250,
                width       : 350,
                columns     :
                [
                    {
                        dataIndex : 'DSSISROL',
                        width     : 300
                    }
                ],
                store : Ext.create('Ext.data.Store', {
                    autoLoad : true,
                    fields  : [
                        'CDSISROL',
                        'DSSISROL',
                        { name : 'SWACTIVO' , type : 'boolean' }
                    ],
                    proxy   : {
                        type        : 'ajax',
                        url         : _GLOBAL_URL_RECUPERACION,
                        extraParams : {
                            'params.consulta' : 'RECUPERAR_ROLES'
                        },
                        reader      : {
                            type : 'json',
                            root : 'list'
                        }
                    }
                }),
                tbar : [
                    {
                        xtype      : 'textfield',
                        fieldLabel : '<span style="color:white;">FILTRAR:</span>',
                        listeners  : {
                            change : function (me, v) {
                                _fieldById('_p62_gridRoles').getStore().clearFilter();
                                if (!Ext.isEmpty(v)) {
                                    _fieldById('_p62_gridRoles').getStore().filter('DSSISROL', v);
                                }
                            }
                        }
                    }
                ]
            }),
            Ext.create('Ext.grid.Panel', {
                itemId  : '_p62_gridUsuarios',
                title   : 'USUARIOS POR SUCURSAL Y POR ROL',
                height  : 280,
                colspan : 2,
                store   : Ext.create('Ext.data.Store', {
                    fields   : _p62_gridUsuariosFields,
                    autoLoad : false,
                    proxy    : {
                        url : _GLOBAL_URL_RECUPERACION,
                        type        : 'ajax',
                        extraParams : {
                            'params.consulta' : 'RECUPERAR_DESPACHADOR_DATOS_USUARIOS'
                        },
                        reader : {
                            type : 'json',
                            root : 'list'
                        }
                    }
                }),
                columns : _p62_gridUsuariosColumns,
                tbar    : [
                    {
                        text    : 'CARGAR',
                        handler : function () {
                            var ck = 'Cargando usuarios';
                            try {
                                if (_fieldById('_p62_gridSucursales').getSelectionModel().getSelection().length +
                                    _fieldById('_p62_gridRoles').getSelectionModel().getSelection().length < 2
                                    ) {
                                    throw 'Debe seleccionar una sucursal y un rol';
                                }
                                _fieldById('_p62_gridUsuarios').getStore().load({
                                    params : {
                                        'params.cdunieco' : _fieldById('_p62_gridSucursales').getSelectionModel().getSelection()[0].get('CDUNIECO'),
                                        'params.cdnivel'  : _fieldById('_p62_gridSucursales').getSelectionModel().getSelection()[0].get('CDNIVEL'),
                                        'params.cdsisrol' : _fieldById('_p62_gridRoles').getSelectionModel().getSelection()[0].get('CDSISROL')
                                    }
                                });
                            } catch (e) {
                                manejaException(e, ck);
                            }
                        }
                    }
                ]
            }),
            Ext.create('Ext.grid.Panel', {
                itemId  : '_p62_gridUsuariosRol',
                title   : 'USUARIOS POR ROL',
                height  : 280,
                colspan : 2,
                store   : Ext.create('Ext.data.Store', {
                    fields   : _p62_gridUsuariosAllFields,
                    autoLoad : false,
                    proxy    : {
                        url : _GLOBAL_URL_RECUPERACION,
                        type        : 'ajax',
                        extraParams : {
                            'params.consulta' : 'RECUPERAR_DESPACHADOR_DATOS_USER_ALL_X_ROL'
                        },
                        reader : {
                            type : 'json',
                            root : 'list'
                        }
                    }
                }),
                columns : _p62_gridUsuariosAllColumns,
                tbar    : [
                    {
                        text    : 'CARGAR',
                        handler : function () {
                            var ck = 'Cargando usuarios por rol';
                            try {
                                if (_fieldById('_p62_gridRoles').getSelectionModel().getSelection().length === 0) {
                                    throw 'Debe seleccionar un rol';
                                }
                                _fieldById('_p62_gridUsuariosRol').getStore().load({
                                    params : {
                                        'params.cdsisrol' : _fieldById('_p62_gridRoles').getSelectionModel().getSelection()[0].get('CDSISROL')
                                    }
                                });
                            } catch (e) {
                                manejaException(e, ck);
                            }
                        }
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
////// funciones //////
</script>
</head>
<body>
<div id="_p62_divpri" style="height:1100px;border:1px solid #CCCCCC"></div>
</body>
</html>