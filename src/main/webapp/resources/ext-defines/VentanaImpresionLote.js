/**
 * Ventana para mostrar la impresión de pólizas
 * COMPONENTE 0 => #C0
 * recibe ntramite
 * o records {cdunieco,cdramo,estado,nmpoliza,nmsuplem,nsuplogi,cdtipsup,ntramite}
 */
Ext.define('VentanaImpresionLote',
{
    extend       : 'Ext.window.Window'
    ,title       : 'Impresi\u00F3n'
    ,width       : 750
    ,height      : 400
    ,modal       : true
    ,items       :
    [
        {
            xtype   : 'panel'
            ,layout :
            {
                type     : 'table'
                ,columns : 2
            }
            ,defaults : { style : 'margin:5px;' }
            ,items    :
            [
                {
                    xtype  : 'displayfield'
                    ,value : 'Papeler\u00EDa: incluye car\u00E1tulas y endosos'
                }
                ,{
                    xtype : 'button'
                    ,text : 'Imprimir papeler\u00EDa'
                    ,icon : _GLOBAL_DIRECTORIO_ICONOS+'page_go.png'
                }
                ,{
                    xtype  : 'displayfield'
                    ,value : 'Credenciales: para todos los asegurados'
                }
                ,{
                    xtype : 'button'
                    ,text : 'Imprimir credenciales'
                    ,icon : _GLOBAL_DIRECTORIO_ICONOS+'group_key.png'
                }
                ,{
                    xtype  : 'displayfield'
                    ,value : 'Recibos: impresi\u00F3n de recibos de pago'
                }
                ,{
                    xtype : 'button'
                    ,text : 'Imprimir recibos'
                    ,icon : _GLOBAL_DIRECTORIO_ICONOS+'coins.png'
                }
                ,{
                    xtype    : 'grid'
                    ,title   : 'ORDEN DE IMPRESI\u00D3N'
                    ,width   : 700
                    ,height  : 220
                    ,colspan : 2
                    ,columns :
                    [
                        {
                            xtype  : 'actioncolumn'
                            ,width : 60
                            ,items :
                            [
                                {
                                    icon    : _GLOBAL_DIRECTORIO_ICONOS+'arrow_up.png'
                                    ,tooltip : 'Subir'
                                    ,handler : function(grid,row,col,item,e,record)
                                    {
                                        if(grid.getStore().indexOf(record)>0)
                                        {
                                            grid.getStore().removeAt(row);
                                            grid.getStore().insert(row-1,record);
                                        }
                                    }
                                }
                                ,{
                                    icon    : _GLOBAL_DIRECTORIO_ICONOS+'arrow_down.png'
                                    ,tooltip : 'Bajar'
                                    ,handler : function(grid,row,col,item,e,record)
                                    {
                                        if(grid.getStore().indexOf(record)<grid.getStore().getCount()-1)
                                        {
                                            grid.getStore().removeAt(row);
                                            grid.getStore().insert(row+1,record);
                                        }
                                    }
                                }
                            ]
                        }
                        ,{
                            text       : 'SUCURSAL'
                            ,dataIndex : 'cdunieco'
                            ,width     : 80
                        }
                        ,{
                            text       : 'PRODUCTO'
                            ,dataIndex : 'dsramo'
                            ,width     : 120
                        }
                        ,{
                            text       : 'P\u00D3LIZA'
                            ,dataIndex : 'nmpoliza'
                            ,width     : 80
                        }
                        ,{
                            text       : 'NO. MOV.'
                            ,dataIndex : 'nsuplogi'
                            ,width     : 80
                        }
                        ,{
                            text       : 'MOVIMIENTO'
                            ,dataIndex : 'dstipsup'
                            ,flex      : 1
                        }
                    ]
                    ,store :
                    {
                        xtype   : 'store'
                        ,fields : []
                        ,data   : []
                    }
                }
            ]
        }
    ]
    ,constructor : function(config)
    {
        debug('config:',config,',arguments:',arguments);
        if(Ext.isEmpty(config))
        {
            throw '#C0 - No hay configuraci\u00F3n para la ventana de impresi\u00F3n';
        }
        if(Ext.isEmpty(config.records)||config.records.length==0)
        {
            throw '#C0 - La ventana de impresi\u00F3n necesita los registros de suplementos';
        }
        for(var i in config.records)
        {
            var record = config.records[i];
            if(   Ext.isEmpty(record.get('cdunieco'))
                ||Ext.isEmpty(record.get('cdramo'))
                ||Ext.isEmpty(record.get('dsramo'))
                ||Ext.isEmpty(record.get('estado'))
                ||Ext.isEmpty(record.get('nmpoliza'))
                ||Ext.isEmpty(record.get('nmsuplem'))
                ||Ext.isEmpty(record.get('nsuplogi'))
                ||Ext.isEmpty(record.get('cdtipsup'))
                ||Ext.isEmpty(record.get('dstipsup'))
                ||Ext.isEmpty(record.get('ntramite'))
            )
            {
                throw '#C0 - Los registros recibidos no cumplen con el formato';
            }
        }
        this.callParent(arguments);
    }
    ,listeners :
    {
        afterrender : function(me)
        {
            debug('records:',me.records);
            me.down('grid').getStore().add(me.records);
            me.down('[text*=Imprimir pape]').handler = function(){ me.buscarImpresora('P'); }
            me.down('[text*=Imprimir cred]').handler = function(){ me.buscarImpresora('C'); }
            me.down('[text*=Imprimir reci]').handler = function(){ me.buscarImpresora('R'); }
        }
    }
    ,buscarImpresora : function(papel)
    {
        debug('buscarImpresora papel:',papel);
        
        var me = this;
        
        var ck = 'Buscando impresoras';
        try
        {
            _setLoading(true,me);
            Ext.Ajax.request(
            {
                url     : _GLOBAL_URL_RECUPERACION
                ,params :
                {
                    'params.consulta' : 'RECUPERAR_IMPRESORAS'
                    ,'params.papel'   : papel
                }
                ,success : function(response)
                {
                    _setLoading(false,me);
                    var ck = 'Decodificando respuesta al recuperar impresoras';
                    try
                    {
                        var json = Ext.decode(response.responseText);
                        debug('### impresoras:',json);
                        if(json.success==true)
                        {
                            centrarVentanaInterna(Ext.create('Ext.window.Window',
                            {
                                title  : 'IMPRESORAS DISPONIBLES'
                                ,modal : true
                                ,items :
                                [
                                    Ext.create('Ext.grid.Panel',
                                    {
                                        width    : 500
                                        ,itemId  : '_c0_gridImpresoras'
                                        ,height  : 250
                                        ,columns :
                                        [
                                            {
                                                text       : 'SUCURSAL'
                                                ,width     : 80
                                                ,dataIndex : 'cdunieco'
                                            }
                                            ,{
                                                text       : 'IMPRESORA'
                                                ,dataIndex : 'dsimpres'
                                                ,flex      : 1
                                            }
                                            ,{
                                                text       : 'TIPO'
                                                ,width     : 100
                                                ,dataIndex : 'dspapel'
                                            }
                                        ]
                                        ,store : Ext.create('Ext.data.Store',
                                        {
                                            fields : [ 'cdunieco' ,'dsimpres' ,'dspapel' ]
                                            ,data  : json.list
                                        })
                                        ,selModel :
                                        {
                                            selType        : 'checkboxmodel'
                                            ,allowDeselect : true
                                            ,mode          : 'SINGLE'
                                            ,listeners     :
                                            {
                                                selectionchange : function(me,selected,eOpts)
                                                {
                                                    if(selected.length==1)
                                                    {
                                                        _fieldById('_c0_botonImprimir').enable();
                                                    }
                                                    else
                                                    {
                                                        _fieldById('_c0_botonImprimir').disable();
                                                    }
                                                }
                                            }
                                        }
                                        ,buttonAlign : 'center'
                                        ,buttons     :
                                        [
                                            {
                                                text      : 'Imprimir'
                                                ,itemId   : '_c0_botonImprimir'
                                                ,icon     : _GLOBAL_DIRECTORIO_ICONOS+'printer.png'
                                                ,disabled : true
                                                ,handler  : function(boton){ me.imprimir(boton,_fieldById('_c0_gridImpresoras').getSelectionModel().getSelection(),papel); }
                                            }
                                            ,{
                                                text    : 'Descargar'
                                                ,icon   : _GLOBAL_DIRECTORIO_ICONOS+'disk.png'
                                                ,hidden : true
                                            }
                                        ]
                                    })
                                ]
                            }).show());
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
                    _setLoading(false,me);
                    errorComunicacion(null,'Error recuperando impresoras');
                }
            });
        }
        catch(e)
        {
            manejaException(e,ck);
        }
    }
    ,imprimir : function(boton,printerRecords,papel)
    {
        debug('imprimir printerRecords:',printerRecords,',papel:',papel);
        
        var me = this;
        
        var ck = 'Imprimiendo';
        try
        {
            if(Ext.isEmpty(papel))
            {
                throw 'No se recibi\u00F3 el tipo de impresi\u00F3n';
            }
            if(Ext.isEmpty(printerRecords)||Ext.isEmpty(printerRecords[0]))
            {
                throw 'No se recibi\u00F3 la impresora';
            }
            var printer = printerRecords[0];
            
            var jsonData =
            {
                params :
                {
                    printerCdunieco : printer.raw.cdunieco
                    ,printerIp      : printer.raw.ip
                    ,printerPuerto  : printer.raw.puerto
                    ,printerCdpapel : printer.raw.cdpapel
                    ,cdpapel        : papel
                }
                ,list : []
            };
            for(var i=0;i<me.down('grid').getStore().getCount();i++)
            {
                var record = me.down('grid').getStore().getAt(i);
                jsonData.list.push(
                {
                    cdunieco  : record.get('cdunieco')
                    ,cdramo   : record.get('cdramo')
                    ,estado   : record.get('estado')
                    ,nmpoliza : record.get('nmpoliza')
                    ,nmsuplem : record.get('nmsuplem')
                    ,nsuplogi : record.get('nsuplogi')
                    ,cdtipsup : record.get('cdtipsup')
                    ,ntramite : record.get('ntramite')
                });
            }
            debug('jsonData imprimir:',jsonData);
            
            var window = boton.up('window');
            _setLoading(true,window);
            Ext.Ajax.request(
            {
                url       : _GLOBAL_URL_IMPRIMIR_LOTE
                ,jsonData : jsonData
                ,success  : function(response)
                {
                    _setLoading(false,window);
                    var ck = 'Decodificando respuesta al imprimir';
                    try
                    {
                        var json = Ext.decode(response.responseText);
                        if(json.success==true)
                        {
                            mensajeCorrecto(
                                'Documentos impresos'
                                ,'Documentos impresos'
                                ,function()
                                {
                                    window.destroy();
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
                    _setLoading(false,window);
                    errorComunicacion(null,'Error al imprimir');
                }
            });
        }
        catch(e)
        {
            manejaException(e,ck);
        }
    }
});