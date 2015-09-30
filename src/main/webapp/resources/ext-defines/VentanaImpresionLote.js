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
        alert(papel);
    }
});