Ext.define('VentanaHistorial',
{
    extend       : 'Ext.window.Window'
    ,title       : 'Historial'
    ,itemId      : '_c21_instance'
    ,closeAction : 'destroy'
    ,modal       : true
    ,width       : 700
    ,height      : 400
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
                        }
                        ,{
                            header     : 'No.'
                            ,dataIndex : 'NMORDINA'
                            ,width     : 40
                        }
                        ,{
                            header     : 'Fecha de inicio'
                            ,xtype     : 'datecolumn'
                            ,dataIndex : 'FECHAINI'
                            ,format    : 'd M Y H:i'
                            ,width     : 130
                        }
                        ,{
                            header     : 'Usuario inicio'
                            ,dataIndex : 'usuario_ini'
                            ,width     : 150
                        }
                        ,{
                            header     : 'Fecha de fin'
                            ,xtype     : 'datecolumn'
                            ,dataIndex : 'FECHAFIN'
                            ,format    : 'd M Y H:i'
                            ,width     : 90
                        }
                        ,{
                            header     : 'Usuario fin'
                            ,dataIndex : 'usuario_fin'
                            ,width     : 150
                        }
                        ,{
                            width         : 30
                            ,menuDisabled : true
                            ,dataIndex    : 'FECHAFIN'
                            ,renderer     : function(value)
                            {
                                debug(value);
                                if(value&&value!=null)
                                {
                                    value='';
                                }
                                else
                                {
                                    value='<img src="'+_GLOBAL_DIRECTORIO_ICONOS+'accept.png" style="cursor:pointer;" data-qtip="Finalizar" />';
                                }
                                return value;
                            }
                        }
                        /*,{
                            width         : 30
                            ,menuDisabled : true
                            ,dataIndex    : 'CDCLAUSU'
                            ,renderer     : function(value)
                            {
                                debug(value);
                                if(value&&value!=null&&value.length>0)
                                {
                                    value='<img src="${ctx}/resources/fam3icons/icons/printer.png" style="cursor:pointer;" data-qtip="Imprimir" />';
                                }
                                else
                                {
                                    value='';
                                }
                                return value;
                            }
                        }*/
                    ]
                    ,listeners :
                    {
                        cellclick : function(grid, td,
                                cellIndex, record, tr,
                                rowIndex, e, eOpts)
                        {
                            debug(record);
                            if(cellIndex<6)
                            {
                                _fieldById('_c21_inputReadDetalleHtmlVisor').setValue((config.cdsisrol!='EJECUTIVOCUENTA'||record.raw.SWAGENTE=='S')?record.get('COMMENTS'):'');
                            }
                            else if(cellIndex==6&&$(td).find('img').length>0)
                            {
                                debug('finalizar');
                                centrarVentanaInterna(Ext.create('Ext.window.Window',
                                {
                                    title        : 'Finalizar detalle'
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
                                            ,itemId : '_c21_inputHtmlEditorFinalizarDetalleMesCon'
                                            ,width  : 570
                                            ,height : 300
                                            ,value  : record.get('COMMENTS')
                                        }
                                    ]
                                    ,buttons     :
                                    [
                                        {
                                            text     : 'Guardar'
                                            ,icon    : _GLOBAL_DIRECTORIO_ICONOS+'disk.png'
                                            ,handler : function(me)
                                            {
                                                var win = me.up('window');
                                                win.setLoading(true);
                                                Ext.Ajax.request(
                                                {
                                                    url      : _GLOBAL_COMP_URL_FINAL_HIST
                                                    ,params  :
                                                    {
                                                        'smap1.pv_ntramite_i'  : record.get('NTRAMITE')
                                                        ,'smap1.pv_nmordina_i' : record.get('NMORDINA')
                                                        ,'smap1.pv_comments_i' : _fieldById('_c21_inputHtmlEditorFinalizarDetalleMesCon').getValue()
                                                    }
                                                    ,success : function (response)
                                                    {
                                                        var json=Ext.decode(response.responseText);
                                                        if(json.success==true)
                                                        {
                                                            win.destroy();
                                                            Ext.Msg.show({
                                                                title:'Detalle actualizado',
                                                                msg: 'Se finaliz&oacute; el detalle',
                                                                buttons: Ext.Msg.OK
                                                            });
                                                        }
                                                        else
                                                        {
                                                            win.setLoading(false);
                                                            Ext.Msg.show({
                                                                title:'Error',
                                                                msg: 'Error al finalizar detalle',
                                                                buttons: Ext.Msg.OK,
                                                                icon: Ext.Msg.ERROR
                                                            });
                                                        }
                                                    }
                                                    ,failure : function()
                                                    {
                                                        win.setLoading(false);
                                                        Ext.Msg.show({
                                                            title:'Error',
                                                            msg: 'Error de comunicaci&oacute;n',
                                                            buttons: Ext.Msg.OK,
                                                            icon: Ext.Msg.ERROR
                                                        });
                                                    }
                                                });
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
                            /*else if(cellIndex==5&&$(td).find('img').length>0)
                            {
                                debug("APRETASTE EL BOTON IMPRIMIR PARA EL RECORD:",record);
                            }*/
                        }
                    }
                })
                ,{
                    xtype     : 'textarea'
                    ,itemId   : '_c21_inputReadDetalleHtmlVisor'
                    ,width    : 690
                    ,height   : 200
                    ,readOnly : true
                }
            ]
        });
        this.callParent(arguments);
    }
});