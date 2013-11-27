<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Endosos</title>
<script>
    ///////////////////////
    ////// variables //////
    /*///////////////////*/
    var marendurlcata      = '<s:url namespace="/flujocotizacion"   action="cargarCatalogos" />';
    var marendurlramos     = '<s:url namespace="/"                  action="obtenerRamos" />';
    var marendStorePolizas;
    var marendUrlFiltro    = '<s:url namespace="/endosos"           action="obtenerEndosos" />'
    var marendUrlAgentes   = '<s:url namespace="/mesacontrol"       action="obtieneAgentes" />';
    /*///////////////////*/
    ////// variables //////
    ///////////////////////
    
    ///////////////////////
    ////// funciones //////
    /*///////////////////*/
    function marendMostrarControlesFiltro(tipo)
    {
        Ext.getCmp('marendFilForm').getForm().reset();
        debug('marendMostrarControlesFiltro',tipo);
        if(tipo==1)
        {
            Ext.getCmp('marendFilUnieco').show();
            Ext.getCmp('marendFilRamo').show();
            Ext.getCmp('marendFilEstado').show();
            Ext.getCmp('marendFilNmpoliza').show();
            Ext.getCmp('marendFilFereferen').show();
        }
        else if(tipo==2)
        {
            Ext.getCmp('marendFilUnieco').hide();
            Ext.getCmp('marendFilRamo').hide();
            Ext.getCmp('marendFilEstado').hide();
            Ext.getCmp('marendFilNmpoliza').hide();
            Ext.getCmp('marendFilFereferen').show();
        }
    }
    
    function marendValidaOperacion(recordOperacion)
    {
    	if(recordOperacion.get('funcion')=='endososcoberturas')
    	{
    		debug('endososcoberturas')
    	}
        else if(recordOperacion.get('funcion')=='endososcoberturas2')
        {
            debug('rehabilitacion unica');
            var nRecordsActivos=0;
            var recordActivo;
            marendStorePolizas.each(function(record)
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
                Ext.getCmp('marendMenuOperaciones').collapse();
                Ext.getCmp('marendLoaderFrame').setTitle(recordOperacion.get('texto'));
                Ext.getCmp('marendLoaderFrame').getLoader().load(
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
    
    Ext.define('marendPoliza',
    {
        extend  : 'Ext.data.Model'
        ,fields :
        [
			"CDUNIECO" 
			,"CDRAMO" 
			,"ESTADO" 
			,"NMPOLIZA" 
			,"NMSUPLEM" 
			,"NMPOLIEX" 
			,"NSUPLOGI"
			,"DSCOMENT" 
			,"CDTIPSIT" 
			,"DSTIPSIT" 
			,"PRIMA_TOTAL"
			,{
                name        : "FEEMISIO"
                ,type       : "date"
                ,dateFormat : "d/m/Y"
            }
            ,{
                name        : "FEINIVAL"
                ,type       : "date"
                ,dateFormat : "d/m/Y"
            }
            ,{
            	name  : 'activo'
            	,type : 'boolean'
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
    marendStorePolizas = Ext.create('Ext.data.Store',
    {
        pageSize  : 10
        ,autoLoad : true
        ,model    : 'marendPoliza'
        ,proxy    :
        {
            enablePaging  : true
            ,reader       : 'json'
            ,type         : 'memory'
            ,data         : []
        }
    });
    
    marendStoreLigas = Ext.create('Ext.data.Store',
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
                    texto    : 'Endoso de suma asegurada'
                    ,liga    : '<s:url namespace="/endosos" action="pantallaCoberturas" />'
                    ,funcion : 'endososcoberturas'
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
        renderTo  : 'marcoEndososDiv'
        ,title    : 'Endosos'
        ,defaults :
        {
            style : 'margin : 5px'
        }
        ,items    :
        [
            Ext.create('Ext.form.Panel',
            {
                title          : 'B&uacute;squeda'
                ,id            : 'marendFilForm'
                //,width         : 1000
                ,url           : marendUrlFiltro
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
                        ,id             : 'marendFilUnieco'
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
                                ,url         : marendurlcata
                                ,extraParams : {catalogo:'<s:property value="CON_CAT_MESACONTROL_SUCUR_DOCU" />'}
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
                                Ext.getCmp('marendFilRamo').getStore().load(
                                {
                                    params : {'map1.cdunieco':this.getValue()}
                                });
                            }
                        }
                    }
                    ,{
                        xtype           : 'combo'
                        ,id             : 'marendFilRamo'
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
                                ,url    : marendurlramos
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
                        ,id             : 'marendFilEstado'
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
                                ,url         : marendurlcata
                                ,extraParams : {catalogo:'<s:property value="CON_CAT_POL_ESTADO" />'}
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
                        ,id         : 'marendFilNmpoliza'
                        ,name       : 'smap1.pv_nmpoliza_i'
                        ,fieldLabel : 'P&oacute;liza/Cotizaci&oacute;n'
                    }
                    ,{
                        xtype       : 'datefield'
                        ,id         : 'marendFilFereferen'
                        ,format     : 'd/m/Y'
                        ,fieldLabel : 'Fecha de referencia'
                        ,name       : 'smap1.pv_fereferen_i'
                        ,value      : new Date()
                    }
                ]
                ,buttons       :
                [
                    {
                        text    : 'Tipo de b&uacute;squeda'
                        ,icon   : '${ctx}/resources/fam3icons/icons/cog.png'
                        ,hidden : true
                        ,menu   :
                        {
                            xtype  : 'menu'
                            ,items :
                            [
                                {
                                    text     : 'General'
                                    ,handler : function(){marendMostrarControlesFiltro(1);}
                                }
                                ,{
                                    text     : 'Por p&oacute;liza'
                                    ,handler : function(){marendMostrarControlesFiltro(2);}
                                }
                            ]
                        }
                    }
                    ,{
                        text     : 'Buscar'
                        ,id      : 'marendFilBotGen'
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
                                            marendStorePolizas.removeAll();
                                            marendStorePolizas.add(json.slist1);
                                            debug(marendStorePolizas);
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
                title          : 'Hist&oacute;rico de movimientos'
                //,width         : 1000
                ,collapsible   : true
                ,titleCollapse : true
                ,height        : 200
                ,store         : marendStorePolizas
                ,frame         : true
                ,tbar          :
                [
                    {
                        text     : 'Marcar/desmarcar'
                        ,icon    : '${ctx}/resources/fam3icons/icons/table_lightning.png'
                        ,handler : function()
                        {
                            var nRecordsActivos=0;
                            marendStorePolizas.each(function(record)
                            {
                                if(record.get('activo')==true)
                                {
                                    nRecordsActivos=nRecordsActivos+1;
                                }
                            });
                            marendStorePolizas.each(function(record)
                            {
                                record.set('activo',nRecordsActivos!=marendStorePolizas.getCount());
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
                    /*
                    "CDUNIECO" 
                ,"CDRAMO" 
                ,"ESTADO" 
                ,"NMPOLIZA" 
                ,"NMSUPLEM" 
                ,"NMPOLIEX" 
                ,"NSUPLOGI" 
                ,"FEEMISIO" 
                ,"FEINIVAL" 
                ,"DSCOMENT" 
                ,"CDTIPSIT" 
                ,"DSTIPSIT" 
                ,"PRIMA_TOTAL"
                    */
                    ,{
                    	header     : 'Sucursal'
                    	,dataIndex : 'CDUNIECO'
                    	,flex      : 1
                    }
                    ,{
                        header     : 'Producto'
                        ,dataIndex : 'CDRAMO'
                        ,flex      : 1
                    }
                    ,{
                        header     : 'P&oacute;liza'
                        ,dataIndex : 'NMPOLIEX'
                        ,flex      : 1
                    }
                    ,{
                        header     : 'No. Endoso'
                        ,dataIndex : 'NSUPLOGI'
                        ,flex      : 1
                    }
                    ,{
                        header     : 'Tipo de endoso'
                        ,dataIndex : 'DSCOMENT'
                        ,flex      : 1
                    }
                    ,{
                        header     : 'Fecha de emisi&oacute;n'
                        ,dataIndex : 'FEEMISIO'
                        ,flex      : 1
                        ,xtype     : 'datecolumn'
                        ,format    : 'd M Y'
                    }
                    ,{
                        header     : 'Inicio de vigencia'
                        ,dataIndex : 'FEINIVAL'
                        ,flex      : 1
                        ,xtype     : 'datecolumn'
                        ,format    : 'd M Y'
                    }
                    ,{
                        header     : 'Prima total'
                        ,dataIndex : 'PRIMA_TOTAL'
                        ,renderer  : Ext.util.Format.usMoney
                        ,flex      : 1
                    }
                ]
            })
            ,Ext.create('Ext.panel.Panel',
            {
                id        : 'marendLoaderFrameParent'
                ,layout   : 'border'
                //,width    : 1000
                ,height   : 1000
                ,border   : 0
                ,items    : 
                [
                    Ext.create('Ext.grid.Panel',
                    {
                        style        : 'margin-right : 5px;'
                        ,id          : 'marendMenuOperaciones'
                        ,width       : 180
                        ,region      : 'west'
                        ,collapsible : true
                        ,margins     : '0 5 0 0'
                        ,layout      : 'fit'
                        ,frame       : false
                        ,title       : 'Operaciones'
                        ,store       : marendStoreLigas
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
                                marendValidaOperacion(record);
                            }
                        }
                    })
                    ,Ext.create('Ext.panel.Panel',
                    {
                        frame      : true
                        ,region    : 'center'
                        ,id        : 'marendLoaderFrame'
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
    
    marendMostrarControlesFiltro(1);
    
});
</script>
</head>
<body>
<div id="marcoEndososDiv"></div>
</body>
</html>