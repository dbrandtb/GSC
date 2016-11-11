Ext.define('VentanaHistorial',
{
    extend       : 'Ext.window.Window'
    ,title       : 'Historial'
    ,itemId      : '_c21_instance'
    ,closeAction : 'destroy'
    ,modal       : true
    ,width       : 800
    ,height      : 430
    ,mostrar     : function()
    {
        var me = this;
        centrarVentanaInterna(me.show());
    }
    ,constructor : function(config)
    {
        var me = this;
        Ext.apply(me,
        {
            items :
            [
                Ext.create('Ext.grid.Panel',
                {
                    height      : 190
                    ,autoScroll : true
                    ,store      : new Ext.data.Store(
                    {
                        fields    :
                        [
                            "NTRAMITE"
                            ,"NMORDINA"
                            ,"CDTIPTRA"
                            ,"CDCLAUSU"
                            ,{name:"FECHAINI",type:'date',dateFormat:'d/m/Y H:i'}
                            ,{name:"FECHAFIN",type:'date',dateFormat:'d/m/Y H:i'}
                            ,"COMMENTS"
                            ,"CDUSUARI_INI"
                            ,"CDUSUARI_FIN"
                            ,"usuario_ini"
                            ,"usuario_fin"
                            ,"STATUS"       , "DSSTATUS"
                            ,"CDSISROL_INI" , "DSSISROL_INI"
                            ,"CDSISROL_FIN" , "DSSISROL_FIN"
                            ,"DSUSUARI_INI" , "DSUSUARI_FIN"
                            ,'STATUS'       , 'DSSTATUS'
                        ]
                        ,autoLoad : true
                        ,proxy    :
                        {
                            type         : 'ajax'
                            ,url         : _GLOBAL_COMP_URL_GET_HISTORIAL
                            ,extraParams :
                            {
                                'smap1.pv_ntramite_i' : config.ntramite
                            }
                            ,reader      :
                            {
                                type  : 'json'
                                ,root : 'slist1'
                            }
                        }
                    })
                    ,columns : 
                    [
                        {
                            header     : 'Tr&aacute;mite'
                            ,dataIndex : 'NTRAMITE'
                            ,width     : 60
                            ,hidden    : true
                        }
                        ,{
                            header     : 'No.'
                            ,dataIndex : 'NMORDINA'
                            ,width     : 40
                        }
                        ,{
                            header     : 'Usuario'
                            ,dataIndex : 'usuario_fin'
                            ,width     : 300
                            ,renderer  : function(v,md,rec)
                            {
                                return _NVL(rec.get('DSUSUARI_FIN')) + ' - ' +  _NVL(rec.get('DSSISROL_FIN'));
                            }
                        }
                        ,{
                            header     : 'Usuario'
                            ,dataIndex : 'usuario_ini'
                            ,width     : 300
                            ,renderer  : function(v,md,rec)
                            {
                                return _NVL(rec.get('DSUSUARI_INI')) + ' - ' +  _NVL(rec.get('DSSISROL_INI'));
                            }
                            ,hidden    : true
                        }
                        ,{
                            header     : 'Inicio'
                            ,xtype     : 'datecolumn'
                            ,dataIndex : 'FECHAINI'
                            ,format    : 'd M Y H:i'
                            ,width     : 125
                        }
                        ,{
                            header     : 'Fin'
                            ,xtype     : 'datecolumn'
                            ,dataIndex : 'FECHAFIN'
                            ,format    : 'd M Y H:i'
                            ,width     : 125
                        }
                        ,{
                            header     : 'Estatus'
                            ,dataIndex : 'DSSTATUS'
                            ,width     : 160
                            ,renderer  : function(v)
                            {
                                return _NVL(v);
                            }
                        }
                        ,{
                            dataIndex : 'usuario_ini'
                            ,width    : 30
                            ,renderer : function(val,md,rec)
                            {
                                var r = '';
                                if(config.cdsisrol!='EJECUTIVOCUENTA'||rec.raw.SWAGENTE=='S')
                                {
                                    r = '<img src="'+_GLOBAL_DIRECTORIO_ICONOS+'book_edit.png" style="cursor:pointer;" data-qtip="Modificar detalle" />';
                                }
                                return r;
                            }
                        }
                    ]
                    ,listeners :
                    {
                        cellclick : function(grid, td,
                                cellIndex, record, tr,
                                rowIndex, e, eOpts)
                        {
                            debug(record);
                            if($(td).find('img').length==0)
                            {
                                _fieldById('_c21_inputReadDetalleHtmlVisor').setValue((config.cdsisrol!='EJECUTIVOCUENTA'||record.raw.SWAGENTE=='S')?record.get('COMMENTS'):'');
                            }
                            else if($(td).find('img').length>0)
                            {
                                debug('finalizar');
                                centrarVentanaInterna(Ext.create('Ext.window.Window',
                                {
                                    title        : 'Modificar detalle'
                                    ,width       : 600
                                    ,height      : 400
                                    ,buttonAlign : 'center'
                                    ,modal       : true
                                    ,closable    : false
                                    ,autoScroll  : true
                                    ,items       :
                                    [
                                        {
                                            xtype   : 'textarea'
                                            ,width  : 570
                                            ,height : 300
                                            //,value  : record.get('COMMENTS')
                                        }
                                    ]
                                    ,buttons     :
                                    [
                                        {
                                            text     : 'Guardar'
                                            ,icon    : _GLOBAL_DIRECTORIO_ICONOS+'disk.png'
                                            ,handler : function(me)
                                            {
                                                var mask, ck = 'Modificando detalle';
                                                try
                                                {
                                                    mask = _maskLocal(ck);
                                                    Ext.Ajax.request(
                                                    {
                                                        url      : _GLOBAL_COMP_URL_MODIF_HIST
                                                        ,params  :
                                                        {
                                                            'params.ntramite'  : record.get('NTRAMITE')
                                                            ,'params.nmordina' : record.get('NMORDINA')
                                                            ,'params.comments' : me.up('window').down('textarea').getValue()
                                                        }
                                                        ,success : function (response)
                                                        {
                                                            mask.close();
                                                            var ck = 'Decodificando respuesta al modificar detalle';
                                                            try
                                                            {
                                                                var json=Ext.decode(response.responseText);
                                                                if(json.success === true)
                                                                {
                                                                    mensajeCorrecto(
                                                                        'Detalle actualizado'
                                                                        ,'Detalle actualizado'
                                                                        ,function()
                                                                        {
                                                                            record.set('COMMENTS',json.params.comments);
                                                                            _fieldById('_c21_inputReadDetalleHtmlVisor').setValue(record.get('COMMENTS'));
                                                                            me.up('window').destroy();
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
                                                            mask.close();
                                                            errorComunicacion(null,'Error al modificar detalle');
                                                        }
                                                    });
                                                }
                                                catch(e)
                                                {
                                                    manejaException(e,ck,mask);
                                                }
                                            }
                                        }
                                        ,{
                                            text     : 'Cancelar'
                                            ,icon    : _GLOBAL_DIRECTORIO_ICONOS+'cancel.png'
                                            ,handler : function()
                                            {
                                                this.up('window').destroy();
                                            }
                                        }
                                    ]
                                }).show());
                            }
                        }
                    }
                })
                ,{
                    xtype       : 'textarea'
                    ,itemId     : '_c21_inputReadDetalleHtmlVisor'
                    ,width      : 780
                    ,height     : 180
                    ,readOnly   : true
                    ,labelAlign : 'top'
                    ,fieldLabel : 'Se recibe con los siguientes comentarios:'
                }
            ]
        });
        this.callParent(arguments);
    }
});