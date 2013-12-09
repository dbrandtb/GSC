<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Rehabilitar</title>
<script>
    ///////////////////////
    ////// variables //////
    /*///////////////////*/
    //var marrehurlcata      = '<s:url namespace="/flujocotizacion"   action="cargarCatalogos" />';
    var marrehurlcata      = '<s:url namespace="/catalogos"         action="obtieneCatalogo" />';
    var marrehurlramos     = '<s:url namespace="/"                  action="obtenerRamos" />';
    var marrehStorePolizas;
    var marrehUrlFiltro    = '<s:url namespace="/rehabilitacion"    action="buscarPolizas" />'
    var marrehUrlAgentes   = '<s:url namespace="/mesacontrol"       action="obtieneAgentes" />';
    /*///////////////////*/
    ////// variables //////
    ///////////////////////
    
    ///////////////////////
    ////// funciones //////
    /*///////////////////*/
    function marrehMostrarControlesFiltro(tipo)
    {
        Ext.getCmp('marrehFilForm').getForm().reset();
        debug('marrehMostrarControlesFiltro',tipo);
        if(tipo==1)
        {
            Ext.getCmp('marrehFilUnieco').show();
            Ext.getCmp('marrehFilRamo').show();
            Ext.getCmp('marrehFilEstado').show();
            Ext.getCmp('marrehFilNmpoliza').show();
            Ext.getCmp('marrehFilNmpoliex').hide();
        }
        else if(tipo==2)
        {
        	Ext.getCmp('marrehFilUnieco').hide();
            Ext.getCmp('marrehFilRamo').hide();
            Ext.getCmp('marrehFilEstado').hide();
            Ext.getCmp('marrehFilNmpoliza').hide();
            Ext.getCmp('marrehFilNmpoliex').show();
        }
    }
    
    function marRehValidaOperacion(recordOperacion)
    {
        if(recordOperacion.get('funcion')=='rehabilitacionunica')
        {
            debug('rehabilitacion unica');
            var nRecordsActivos=0;
            var recordActivo;
            marrehStorePolizas.each(function(record)
            {
                if(record.get('activo')==true)
                {
                    nRecordsActivos=nRecordsActivos+1;
                    recordActivo=record;
                }
            });
            if(nRecordsActivos==1)
            {
                debug('continuar');
                Ext.getCmp('marrehMenuOperaciones').collapse();
                Ext.getCmp('marrehLoaderFrame').setTitle(recordOperacion.get('texto'));
                Ext.getCmp('marrehLoaderFrame').getLoader().load(
                {
                    url       : recordOperacion.get('liga')
                    ,scripts  : true
                    ,autoLoad : true
                    ,params   :
                    {
                        'smap1.cdunieco'  : recordActivo.get('CDUNIECO')
                        ,'smap1.cdramo'   : recordActivo.get('CDRAMO')
                        ,'smap1.cdtipsit' : recordActivo.get('CDTIPSIT')
                        ,'smap1.estado'   : recordActivo.get('ESTADO')
                        ,'smap1.nmpoliza' : recordActivo.get('NMPOLIZA')
                        ,'smap1.dsunieco' : recordActivo.get('DSUNIECO')
                        ,'smap1.dsramo'   : recordActivo.get('DSRAMO')
                        ,'smap1.dstipsit' : recordActivo.get('DSTIPSIT')
                        ,'smap1.nmpoliex' : recordActivo.get('NMPOLIEX')
                    }
                });
            }
            else
            {
                Ext.Msg.show(
                {
                    title   : 'Error',
                    icon    : Ext.Msg.WARNING,
                    msg     : 'Seleccione solo una p&oacute;liza',
                    buttons : Ext.Msg.OK
                });
            }
        }
    }
    /*///////////////////*/
    ////// funciones //////
    ///////////////////////
    
Ext.onReady(function()
{
    
    /////////////////////
    ////// modelos //////
    /*/////////////////*/
    Ext.define('Ramo',
    {
        extend  : 'Ext.data.Model'
        ,fields :
        [
            "cdramo"
            ,"dsramo"
        ]
    });
    
    Ext.define('MarRehPoliza',
    {
        extend  : 'Ext.data.Model'
        ,fields :
        [
            {
                name        : "FEMISION"
                ,type       : "date"
                ,dateFormat : "d/m/Y"
            }
            ,{
                name        : "FEINICOV"
                ,type       : "date"
                ,dateFormat : "d/m/Y"
            }
            ,{
                name        : "FEFINIV"
                ,type       : "date"
                ,dateFormat : "d/m/Y"
            }
            ,{
                name        : "PRITOTAL"
                ,type       : "float"
            }
            ,"NOMBRE"
            ,"DSRAMO"
            ,"CDRAMO"
            ,"DSTIPSIT"
            ,"CDTIPSIT"
            ,"DSUNIECO"
            ,"CDUNIECO"
            ,"NMPOLIZA"
            ,"NMPOLIEX"
            ,"NMSOLICI"
            ,"ESTADO"
            ,{
                name  : 'activo'
                ,type : 'boolean'
            }
            ,{
                name        : "FERECIBO"
                ,type       : "date"
                ,dateFormat : "d/m/Y"
            }
        ]
    });
    
    Ext.define('Liga',
    {
        extend  : 'Ext.data.Model'
        ,fields :
        [
            "texto"
            ,"liga"
            ,"funcion"
        ]
    });
    /*/////////////////*/
    ////// modelos //////
    /////////////////////
    
    ////////////////////
    ////// stores //////
    /*////////////////*/
    marrehStorePolizas = Ext.create('Ext.data.Store',
    {
        pageSize  : 10
        ,autoLoad : true
        ,model    : 'MarRehPoliza'
        ,proxy    :
        {
            enablePaging  : true
            ,reader       : 'json'
            ,type         : 'memory'
            ,data         : []
        }
    });
    
    marrehStoreLigas = Ext.create('Ext.data.Store',
    {
        autoLoad  : true
        ,model    : 'Liga'
        ,proxy    :
        {
            enablePaging  : true
            ,reader       : 'json'
            ,type         : 'memory'
            ,data         :
            [
                {
                    texto    : 'Rehabilitaci&oacute;n &uacute;nica'
                    ,liga    : '<s:url namespace="/rehabilitacion" action="pantallaRehabilitacionUnica" />'
                    ,funcion : 'rehabilitacionunica'
                }
            ]
        }
    });
    
    /*////////////////*/
    ////// stores //////
    ////////////////////
    
    /////////////////////////
    ////// componentes //////
    /*/////////////////////*
    Ext.define('Panel1',{
        extend:'Ext.panel.Panel',
        title:'Objeto asegurado',
        layout:{
            type:'column',
            columns:2
        },
        frame:false,
        style:'margin : 5px;',
        collapsible:true,
        titleCollapse:true,
        <s:property value="item2" />
    });
    Ext.define('FormPanel',{
        extend:'Ext.form.Panel',
        renderTo:'maindiv',
        frame:false,
        buttonAlign:'center',
        items:[
            new Panel1()
        ]
    });
    /*/////////////////////*/
    ////// componentes //////
    /////////////////////////
    
    ///////////////////////
    ////// contenido //////
    /*///////////////////*/
    Ext.create('Ext.panel.Panel',
    {
        renderTo  : 'marcoRehabilitacionDiv'
        ,title    : 'Rehabilitaci&oacute;n'
        ,defaults :
        {
            style : 'margin : 5px'
        }
        ,items    :
        [
            Ext.create('Ext.form.Panel',
            {
                title          : 'B&uacute;squeda'
                ,id            : 'marrehFilForm'
                //,width         : 1000
                ,url           : marrehUrlFiltro
                ,layout        :
                {
                    type     : 'table'
                    ,columns : 3
                }
                ,defaults      :
                {
                    style : 'margin:5px;'
                }
                ,collapsible   : true
                ,titleCollapse : true
                ,buttonAlign   : 'center'
                ,frame         : true
                ,items         :
                [
                    {
                        xtype           : 'combo'
                        ,id             : 'marrehFilUnieco'
                        ,fieldLabel     : 'Sucursal'
                        ,name           : 'smap1.pv_cdunieco_i'
                        ,displayField   : 'value'
                        ,valueField     : 'key'
                        ,forceSelection : true
                        ,queryMode      :'local'
                        ,store          : Ext.create('Ext.data.Store',
                        {
                            model     : 'Generic'
                            ,autoLoad : true
                            ,proxy    :
                            {
                                type         : 'ajax'
                                ,url         : marrehurlcata
                                <%-- ,extraParams : {catalogo:'<s:property value="CON_CAT_MESACONTROL_SUCUR_DOCU" />'} --%>
                                ,extraParams : {catalogo:'<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@MC_SUCURSALES_DOCUMENTO"/>'}
                                ,reader      :
                                {
                                    type  : 'json'
                                    ,root : 'lista'
                                }
                            }
                        })
                        ,listeners      :
                        {
                            'change' : function()
                            {
                                Ext.getCmp('marrehFilRamo').getStore().load(
                                {
                                    params : {'map1.cdunieco':this.getValue()}
                                });
                            }
                        }
                    }
                    ,{
                        xtype           : 'combo'
                        ,id             : 'marrehFilRamo'
                        ,fieldLabel     : 'Producto'
                        ,name           : 'smap1.pv_cdramo_i'
                        ,valueField     : 'cdramo'
                        ,displayField   : 'dsramo'
                        ,forceSelection : true
                        ,queryMode      :'local'
                        ,store          : Ext.create('Ext.data.Store',
                        {
                            model     : 'Ramo'
                            ,autoLoad : false
                            ,proxy    :
                            {
                                type    : 'ajax'
                                ,url    : marrehurlramos
                                ,reader :
                                {
                                    type  : 'json'
                                    ,root : 'slist1'
                                }
                            }
                        })
                    }
                    ,{
                        xtype           : 'combo'
                        ,id             : 'marrehFilEstado'
                        ,fieldLabel     : 'Estado'
                        ,name           : 'smap1.pv_estado_i'
                        ,displayField   : 'value'
                        ,valueField     : 'key'
                        ,forceSelection : true
                        ,queryMode      :'local'
                        ,store          : Ext.create('Ext.data.Store',
                        {
                            model     : 'Generic'
                            ,autoLoad : true
                            ,proxy    :
                            {
                                type         : 'ajax'
                                ,url         : marrehurlcata
                                <%-- ,extraParams : {catalogo:'<s:property value="CON_CAT_POL_ESTADO" />'} --%>
                                ,extraParams : {catalogo:'<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@STATUS_POLIZA"/>'}
                                ,reader      :
                                {
                                    type  : 'json'
                                    ,root : 'lista'
                                }
                            }
                        })
                    }
                    ,{
                        xtype       : 'numberfield'
                        ,id         : 'marrehFilNmpoliza'
                        ,name       : 'smap1.pv_nmpoliza_i'
                        ,fieldLabel : 'P&oacute;liza/Cotizaci&oacute;n'
                    }
                    ,{
                        xtype       : 'textfield'
                        ,id         : 'marrehFilNmpoliex'
                        ,name       : 'smap1.pv_nmpoliex_i'
                        ,fieldLabel : 'N&uacute;mero de p&oacute;liza'
                    }
                    ,Ext.create('Ext.form.field.ComboBox',
                    {
                        fieldLabel       : 'Agente'
                        ,id              : 'marrehFilAgente'
                        ,name            : 'smap1.pv_cdagente_i'
                        ,displayField    : 'value'
                        ,valueField      : 'key'
                        ,forceSelection  : true
                        ,matchFieldWidth : false
                        ,hidden          : true
                        ,hideTrigger     : true
                        ,minChars        : 3
                        ,queryMode       : 'remote'
                        ,queryParam      : 'smap1.pv_cdagente_i'
                        ,store           : Ext.create('Ext.data.Store', {
                            model     : 'Generic'
                            ,autoLoad : false
                            ,proxy    :
                            {
                                type    : 'ajax'
                                ,url    : marrehUrlAgentes
                                ,reader :
                                {
                                    type  : 'json'
                                    ,root : 'lista'
                                }
                            }
                        })
                    })
                    ,{
                        xtype       : 'datefield'
                        ,id         : 'marrehFilFereferen'
                        ,format     : 'd/m/Y'
                        ,fieldLabel : 'Fecha de referencia'
                        ,name       : 'smap1.pv_fereferen_i'
                        ,value      : new Date()
                        ,hidden     : true
                    }
                ]
                ,buttons       :
                [
					{
					    text  : 'Tipo de b&uacute;squeda'
					    ,icon : '${ctx}/resources/fam3icons/icons/cog.png'
					    ,menu :
					    {
					        xtype     : 'menu'
					        ,items    :
					        [
					            {
					                text     : 'General'
					                ,handler : function(){marrehMostrarControlesFiltro(1);}
					            }
					            ,{
					                text     : 'Por p&oacute;liza'
					                ,handler : function(){marrehMostrarControlesFiltro(2);}
					            }
					        ]
					    }
					}
                    ,{
                        text     : 'Buscar'
                        ,id      : 'marrehFilBotGen'
                        ,icon    : '${ctx}/resources/fam3icons/icons/zoom.png'
                        ,handler : function()
                        {
                            if(this.up().up().isValid())
                            {
                                this.up().up().submit(
                                {
                                    success  : function(form,action)
                                    {
                                        debug(action);
                                        var json = Ext.decode(action.response.responseText);
                                        debug(json);
                                        if(json.success==true&&json.slist1&&json.slist1.length>0)
                                        {
                                            marrehStorePolizas.removeAll();
                                            marrehStorePolizas.add(json.slist1);
                                            debug(marrehStorePolizas);
                                        }
                                        else
                                        {
                                            Ext.Msg.show(
                                            {
                                                title    : 'Sin resultados'
                                                ,msg     : 'No hay resultados'
                                                ,icon    : Ext.Msg.WARNING
                                                ,buttons : Ext.Msg.OK
                                            });
                                        }
                                    }
                                    ,failure : function()
                                    {
                                        Ext.Msg.show(
                                        {
                                            title   : 'Error',
                                            icon    : Ext.Msg.ERROR,
                                            msg     : 'Error de comunicaci&oacute;n',
                                            buttons : Ext.Msg.OK
                                        });
                                    }
                                });
                            }
                            else
                            {
                                Ext.Msg.show(
                                {
                                    title   : 'Datos imcompletos',
                                    icon    : Ext.Msg.WARNING,
                                    msg     : 'Favor de llenar los campos requeridos',
                                    buttons : Ext.Msg.OK
                                });
                            }
                        }
                    }
                ]
            })
            ,Ext.create('Ext.grid.Panel',
            {
                title          : 'P&oacute;lizas canceladas'
                //,width         : 1000
                ,collapsible   : true
                ,titleCollapse : true
                ,height        : 200
                ,store         : marrehStorePolizas
                ,frame         : true
                ,tbar          :
                [
                    {
                        text     : 'Marcar/desmarcar'
                        ,icon    : '${ctx}/resources/fam3icons/icons/table_lightning.png'
                        ,handler : function()
                        {
                            var nRecordsActivos=0;
                            marrehStorePolizas.each(function(record)
                            {
                                if(record.get('activo')==true)
                                {
                                    nRecordsActivos=nRecordsActivos+1;
                                }
                            });
                            marrehStorePolizas.each(function(record)
                            {
                                record.set('activo',nRecordsActivos!=marrehStorePolizas.getCount());
                            });
                        }
                    }
                ]
                ,columns       :
                [
                    {
                        dataIndex     : 'activo'
                        ,xtype        : 'checkcolumn'
                        ,width        : 30
                        ,menuDisabled : true
                    }
                    ,{
                        header     : "Sucursal"
                        ,dataIndex : "DSUNIECO"
                        ,flex      : 1
                    }
                    ,{
                        header     : "Producto"
                        ,dataIndex : "DSRAMO"
                        ,flex      : 1
                    }
                    ,{
                        header     : "P&oacute;liza"
                        ,dataIndex : "NMPOLIEX"
                        ,flex      : 1
                    }
                    ,{
                        header     : 'Inicio de vigencia'
                        ,dataIndex : 'FEINICOV'
                        ,xtype     : 'datecolumn'
                        ,format    : 'd M Y'
                        ,flex      : 1
                    }
                    ,{
                        header     : 'Fin de vigencia'
                        ,dataIndex : 'FEFINIV'
                        ,xtype     : 'datecolumn'
                        ,format    : 'd M Y'
                        ,flex      : 1
                    }
                    ,{
                        header     : 'Fecha de recibo'
                        ,dataIndex : 'FERECIBO'
                        ,xtype     : 'datecolumn'
                        ,format    : 'd M Y'
                        ,flex      : 1
                    }
                    ,{
                        header     : 'Cliente'
                        ,dataIndex : 'NOMBRE'
                        ,flex      : 1
                    }
                    ,{
                        header     : 'Prima total'
                        ,dataIndex : 'PRITOTAL'
                        ,renderer  : Ext.util.Format.usMoney
                        ,flex      : 1
                    }
                ]
            })
            ,Ext.create('Ext.panel.Panel',
            {
                id        : 'marrehLoaderFrameParent'
                ,layout   : 'border'
                //,width    : 1000
                ,height   : 1000
                ,border   : 0
                ,items    : 
                [
                    Ext.create('Ext.grid.Panel',
                    {
                        style        : 'margin-right : 5px;'
                        ,id          : 'marrehMenuOperaciones'
                        ,width       : 180
                        ,region      : 'west'
                        ,collapsible : true
                        ,margins     : '0 5 0 0'
                        ,layout      : 'fit'
                        ,frame       : false
                        ,title       : 'Operaciones'
                        ,store       : marrehStoreLigas
                        ,hideHeaders : true
                        ,columns     :
                        [
                            {
                                dataIndex : 'texto'
                                ,flex     : 1
                            }
                        ]
                        ,listeners   :
                        {
                            'cellclick' : function(grid, td, cellIndex, record)
                            {
                                marRehValidaOperacion(record);
                            }
                        }
                    })
                    ,Ext.create('Ext.panel.Panel',
                    {
                        frame      : true
                        ,region    : 'center'
                        ,id        : 'marrehLoaderFrame'
                        ,loader    :
                        {
                            autoLoad: false
                        }
                    })
                ]
            })
        ]
    });
    /*///////////////////*/
    ////// contenido //////
    ///////////////////////
    
    //////////////////////
    ////// cargador //////
    /*//////////////////*
    Ext.define('LoaderForm',
    {
        extend:'Modelo1',
        proxy:
        {
            extraParams:{
            },
            type:'ajax',
            url : urlCargar,
            reader:{
                type:'json'
            }
        }
    });

    var loaderForm=Ext.ModelManager.getModel('LoaderForm');
    loaderForm.load(123, {
        success: function(resp) {
            //console.log(resp);
            formPanel.loadRecord(resp);
        },
        failure:function()
        {
            Ext.Msg.show({
                title:'Error',
                icon: Ext.Msg.ERROR,
                msg: 'Error al cargar',
                buttons: Ext.Msg.OK
            });
        }
    });
    /*//////////////////*/
    ////// cargador //////
    //////////////////////
    
    marrehMostrarControlesFiltro(1);
    
});
</script>
</head>
<body>
<div id="marcoRehabilitacionDiv"></div>
</body>
</html>