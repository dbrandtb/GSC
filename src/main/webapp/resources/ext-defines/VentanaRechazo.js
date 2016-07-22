Ext.define('VentanaRechazo',
{
    extend       : 'Ext.window.Window'
    ,title       : 'Rechazo'
    ,itemId      : '_c22_instance'
    ,closeAction : 'destroy'
    ,border      : 0
    ,modal       : true
    //,width       : 600
    //,height      : 460
    ,mostrar     : function()
    {
        var me = this;
        centrarVentanaInterna(me.show());
    }
    ,constructor : function(config)
    {
        var me = this;
        debug('config:',config);
        if(Ext.isEmpty(config))
        {
            throw '#C22 - No se recibieron datos';
        }
        if(Ext.isEmpty(config.aux))
        {
            throw '#C22 - No se recibi\u00f3 el par\u00e1metro';
        }
        Ext.apply(me,
        {
            items :
            [
                {
                    xtype     : 'form'
                    ,border   : 0
                    ,defaults : { style : 'margin:5px;' }
                    ,items    :
                    [
                        {
                            xtype       : 'textfield'
                            ,name       : 'CDTIPFLU'
                            ,value      : config.cdtipflu
                            ,allowBlank : false
                            ,readOnly   : true
                            ,hidden     : true
                        }
                        ,{
                            xtype       : 'textfield'
                            ,name       : 'CDFLUJOMC'
                            ,value      : config.cdflujomc
                            ,allowBlank : false
                            ,readOnly   : true
                            ,hidden     : true
                        }
                        ,{
                            xtype       : 'textfield'
                            ,name       : 'NTRAMITE'
                            ,value      : config.ntramite
                            ,allowBlank : false
                            ,readOnly   : true
                            ,hidden     : true
                        }
                        ,{
                            xtype       : 'textfield'
                            ,name       : 'STATUSOLD'
                            ,value      : config.status
                            ,allowBlank : false
                            ,readOnly   : true
                            ,hidden     : true
                        }
                        ,{
                            xtype       : 'textfield'
                            ,name       : 'STATUSNEW'
                            ,value      : config.aux
                            ,allowBlank : false
                            ,readOnly   : true
                            ,hidden     : true
                        }
                        ,{
                            xtype       : 'textfield'
                            ,name       : 'CDCLAUSU'
                            ,allowBlank : false
                            ,readOnly   : true
                            ,hidden     : true
                        }
                        ,{
                            xtype       : 'textarea'
                            ,fieldLabel : 'Comentarios de rechazo'
                            ,labelAlign : 'top'
                            ,name       : 'COMMENTSEXT'
                            ,width      : 570
                            ,height     : 200
                            ,listeners  :
                            {
                                afterrender : function(me)
                                {
                                    _setLoading(true,me);
                                    var ck = 'Recuperando texto rechazo';
                                    try
                                    {
                                        Ext.Ajax.request(
                                        {
                                            url      : _GLOBAL_COMP_URL_CONS_CLAU
                                            ,params  :
                                            {
                                                'params.cdclausu'  : ''
                                                ,'params.dsclausu' : config.cdsisrol=='MEDICO' ? 'CARTA RECHAZO MEDICO' : 'CARTA RECHAZO ADMINISTRATIVA'
                                            }
                                            ,success : function(response)
                                            {
                                                _setLoading(false,me);
                                                var ck = 'Decodificando respuesta al recuperar detalle de cl00e1usula';
                                                try
                                                {
                                                    var json = Ext.decode(response.responseText);
                                                    debug('### clausula:',json);
                                                    var key = json.listaGenerica[0].key;
                                                    
                                                    me.up('window').down('[name=CDCLAUSU]').setValue(key);
                                                    
                                                    ck = 'Recuperando texto rechazo a detalle';
                                                    _setLoading(true,me);
                                                    Ext.Ajax.request(
                                                    {
                                                        url      : _GLOBAL_COMP_URL_CONS_CLAU_DET
                                                        ,params  :
                                                        {
                                                            'params.cdclausu' : key
                                                        }
                                                        ,success : function(response)
                                                        {
                                                            _setLoading(false,me);
                                                            var ck = 'Decodificando respuesta al recuperar texto a detalle';
                                                            try
                                                            {
                                                                var json2 = Ext.decode(response.responseText);
                                                                debug('### detalle:',json2);
                                                                me.setValue(json2.msgResult);
                                                            }
                                                            catch(e)
                                                            {
                                                                manejaException(e,ck);
                                                            }
                                                        }
                                                        ,failure : function()
                                                        {
                                                            _setLoading(false,me);
                                                            errorComunicacion(null,'Error al recuperar detalle de cl\u00e1usula');
                                                        }
                                                    });
                                                }
                                                catch(e)
                                                {
                                                    manejaException(e,ck);
                                                }
                                            }
                                            ,failure : function()
                                            {
                                                _setLoading(false,me);
                                                errorComunicacion(null,'Error al recuperar cl\u00e1usula');
                                            }
                                        });
                                    }
                                    catch(e)
                                    {
                                        manejaException(e,ck);
                                    }
                                }
                            }
                        }
                        ,{
                            xtype       : 'textarea'
                            ,fieldLabel : 'Comentarios internos'
                            ,labelAlign : 'top'
                            ,name       : 'COMMENTSINT'
                            ,width      : 570
                            ,height     : 100
                        }, {
                            xtype           : 'combobox',
                            fieldLabel      : 'Motivo de rechazo',
                            valueField      : 'key',
                            displayField    : 'value',
                            editable        : true,
                            forceSelection  : config.cdsisrol.indexOf('MED') == -1, // Es force cuando no es medico
                            typeAhead       : true,
                            anyMatch        : true,
                            matchFieldWidth : false,
                            name            : 'CDRAZRECHA',
                            allowBlank      : false,
                            style           : 'margin : 5px; margin-bottom : 20px;',
                            queryMode       : 'local',
                            readOnly        : config.cdsisrol.indexOf('MED') != -1, // Es solo lectura solo para medicos
                            listConfig      : {
                                maxHeight:150,
                                minWidth:120
                            },
                            value           : config.cdsisrol.indexOf('MED') != -1
                                ? '21'
                                : '',
                            store           : Ext.create('Ext.data.Store', {
                                model    : 'Generic',
                                autoLoad : true,
                                cdsisrol : config.cdsisrol,
                                proxy    : {
                                    type        : 'ajax',
                                    url         : _GLOBAL_CONTEXTO + '/catalogos/obtieneCatalogo.action',
                                    extraParams : {
                                        catalogo : 'MOTIVOS_RECHAZO_TRAMITE'
                                    },
                                    reader : {
                                        type         : 'json',
                                        root         : 'lista',
                                        rootProperty : 'lista'
                                    }
                                },
                                listeners : {
                                    load : function (me, records) {
                                        if (me.cdsisrol.indexOf('MED') == -1) { // Si no es medico
                                            var recordRechazoMedico;
                                            for (var i = 0; i < records.length; i++) {
                                                if (records[i].get('key') == 21) {
                                                    recordRechazoMedico = records[i];
                                                }
                                            }
                                            if (!Ext.isEmpty(recordRechazoMedico)) {
                                                me.remove(recordRechazoMedico);
                                            }
                                        }
                                    }
                                }
                            })
                        }
                    ]
                }
                ,{
                    xtype       : 'radiogroup'
                    ,fieldLabel : 'Mostrar al agente'
                    ,columns    : 2
                    ,width      : 250
                    ,style      : 'margin:5px;'
                    ,hidden     : _GLOBAL_CDSISROL===RolSistema.Agente
                    ,items      :
                    [
                        {
                            boxLabel    : 'Si'
                            ,itemId     : 'SWAGENTE'
                            ,name       : 'SWAGENTE'
                            ,inputValue : 'S'
                            ,checked    : _GLOBAL_CDSISROL===RolSistema.Agente
                        }
                        ,{
                            boxLabel    : 'No'
                            ,name       : 'SWAGENTE'
                            ,inputValue : 'N'
                            ,checked    : _GLOBAL_CDSISROL!==RolSistema.Agente
                        }
                    ]
                }
            ]
            ,buttonAlign : 'center'
            ,buttons     :
            [{
                text     : 'Rechazar'
                ,icon    : _GLOBAL_DIRECTORIO_ICONOS+'cancel.png'
                ,handler : function(me)
                {
                    var win = me.up('window');
                    var ck = 'Turnando tr\u00e1mite';
                    try
                    {
                        var form = win.down('form');
                        if(!form.isValid())
                        {
                            throw 'Favor de revisar los datos';
                        }
                        var values = form.getValues();
                        values.SWAGENTE = _fieldById('SWAGENTE',win).getGroupValue();
                        debug('values:',values);
                        values.COMMENTS = values.COMMENTSINT;
                        values.cerrado  = 'S';
                        
                        _mask(ck);
                        Ext.Ajax.request(
                        {
                            url     : _GLOBAL_COMP_URL_TURNAR
                            ,params : _formValuesToParams(values)
                            ,success : function(response)
                            {
                                _unmask();
                                var ck = '';
                                try
                                {
                                    var json = Ext.decode(response.responseText);
                                    debug('### turnar:',json);
                                    if(json.success)
                                    {
                                        var ck = 'Guardando carta rechazo';
                                        try
                                        {
                                            _mask(ck);
                                            Ext.Ajax.request(
                                            {
                                                url      : _GLOBAL_COMP_URL_GUARDA_CARTA_RECHAZO
                                                ,params  :
                                                {
                                                    'map1.ntramite'  : config.ntramite
                                                    ,'map1.comments' : me.up('window').down('[name=COMMENTSEXT]').getValue()
                                                    ,'map1.cdsisrol' : config.cdsisrol
                                                    ,'map1.cdunieco' : config.cdunieco
                                                    ,'map1.cdramo'   : config.cdramo
                                                    ,'map1.estado'   : config.estado
                                                    ,'map1.nmpoliza' : 'R'
                                                }
                                                ,success : function()
                                                {
                                                    _unmask();
                                                    _mask('Redireccionando...');
                                                    Ext.create('Ext.form.Panel').submit(
                                                    {
                                                        url             : _GLOBAL_COMP_URL_MCFLUJO
                                                        ,standardSubmit : true
                                                    });
                                                }
                                                ,failure : function()
                                                {
                                                    _unmask();
                                                    errorComunicacion(null,'Error al guardar carta rechazo');
                                                }
                                            });
                                        }
                                        catch(e)
                                        {
                                            manejaException(e,ck);
                                        }
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
                                _unmask();
                                errorComunicacion(null,'Error al turnar tr\u00e1mite');
                            }
                        });
                    }
                    catch(e)
                    {
                        _unmask();
                        manejaException(e,ck);
                    }
                }
            }]
        });
        this.callParent(arguments);
    }
});