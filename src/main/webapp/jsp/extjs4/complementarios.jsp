<%@ include file="/taglibs.jsp"%>
<%@page contentType="text/html" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <%--////////////////////////////////////
        ////// para el parser de archivos //////
        ////////////////////////////////////--%
        <script>var urlFrameArchivo='<s:url namespace="/" action="subirArchivoMostrarPanel" />';</script>
        <script type="text/javascript" src="${ctx}/resources/extjs4/jQuerySubirArchivosParser.js"></script>
        <%--////////////////////////////////--%>
        <!--// para el parser de archivos //////
        /////////////////////////////////////-->
        <!--<script src="${ctx}/resources/jsp-script/extjs4/complementarios.js"></script>-->
        <script>
            var urlGuardar='<s:url namespace="/" action="guardarDatosComplementarios" />';
            var urlCargar='<s:url namespace="/" action="cargarDatosComplementarios" />';
            var urlCargarCatalogos='<s:url namespace="/flujocotizacion" action="cargarCatalogos" />';
            Ext.onReady(function(){
                
                Ext.define('MiModeloDinamico',{
                    extend:'Ext.data.Model',
                    <s:property value="fields" />
                });
                
                Ext.create('Ext.form.Panel',{
                    id:'formPanel',//id1
                    renderTo:'maindiv',
                    url:urlGuardar,
                    buttonAlign:'center',
                    items:[
                        Ext.create('Ext.panel.Panel',{
                            id:'panelDatosGeneralesPoliza',//id2
                            title:'Datos generales de la poliza',
                            style:'margin:5px',
                            collapsible:true,
                            titleCollapse:true,
                            layout: {
                                type: 'table',
                                columns: 3
                            },
                            items:[
                                Ext.create('Ext.form.TextField', {
                                    id:'companiaAseguradora',//id3
                                    name:'panel1.dsciaaseg',
                                    fieldLabel: 'Compa&ntilde;&iacute;a aseguradora',
                                    /*store:Ext.create('Ext.data.Store', {
                                        model: 'Generic',
                                        data : [{key:'20',value:'General de seguros'}]
                                    }),
                                    queryMode: 'local',
                                    displayField: 'value',
                                    valueField: 'key',*/
                                    readOnly:true,
                                    style:'margin:5px;'
                                }),
                                Ext.create('Ext.form.TextField', {
                                    id:'agenteVentas',
                                    name:'panel1.nombreagente',
                                    fieldLabel: 'Agente',
                                    /*store:Ext.create('Ext.data.Store', {
                                        model: 'Generic',
                                        data : [{key:'20',value:'General de seguros'}]
                                    }),
                                    queryMode: 'local',
                                    displayField: 'value',
                                    valueField: 'key',
                                    //value:Ext.create('Generic',{key:'20',value:'General de seguros'}),*/
                                    readOnly:true,
                                    style:'margin:5px;'
                                }),
                                Ext.create('Ext.form.TextField', {
                                    id:'producto',//id4
                                    name:'panel1.dsramo',
                                    fieldLabel: 'Producto',
                                    /*store:Ext.create('Ext.data.Store', {
                                        model: 'Generic',
                                        data : [{key:'2',value:'Salud Vital'}]
                                    }),
                                    queryMode: 'local',
                                    displayField: 'value',
                                    valueField: 'key',
                                    //value:Ext.create('Generic',{key:'2',value:'Salud vital'}),*/
                                    readOnly:true,
                                    style:'margin:5px;'
                                })
                            ]
                        }),
                        Ext.create('Ext.panel.Panel',{
                            id:'panelDatosGenerales',//id5
                            title:'Datos generales',
                            style:'margin:5px',
                            collapsible:true,
                            titleCollapse:true,
                            layout:{
                                type: 'table',
                                columns: 3
                            },
                            items:[
                                {
                                    xtype:'textfield',
                                    id:'poliza',//id6
                                    name:'panel2.nmpoliza',
                                    readOnly:true,
                                    fieldLabel:'Poliza',
                                    style:'margin:5px;'
                                },
                                {
                                    xtype:'combo',
                                    id:'estadoPoliza',//id8
                                    name:'panel2.estado',
                                    fieldLabel:'Estado',
                                    displayField: 'value',
                                    valueField: 'key',
                                    store:Ext.create('Ext.data.Store', {
                                        model:'Generic',
                                        autoLoad:true,
                                        proxy:
                                        {
                                            type: 'ajax',
                                            url:urlCargarCatalogos,
                                            extraParams:{catalogo:'<s:property value="CON_CAT_POL_ESTADO" />'},
                                            reader:
                                            {
                                                type: 'json',
                                                root: 'lista'
                                            }
                                        }
                                    }),
                                    editable:false,
                                    queryMode:'local',
                                    style:'margin:5px;',
                                    allowBlank:false
                                },
                                {
                                    xtype:'datefield',
                                    id:'fechaSolicitud',//id9
                                    name:'panel2.fesolici',
                                    fieldLabel:'Fecha de solicitud',
                                    allowBlank:false,
                                    style:'margin:5px;',
                                    format:'d/m/Y'
                                },
                                {
                                    xtype:'numberfield',
                                    id:'solicitud',//id10
                                    name:'panel2.solici',
                                    fieldLabel:'Solicitud',
                                    style:'margin:5px;',
                                    allowBlank:false
                                },
                                {
                                    xtype:'datefield',
                                    id:'fechaEfectividad',//id11
                                    name:'panel2.feefec',
                                    fieldLabel:'Fecha de efectividad',
                                    allowBlank:false,
                                    style:'margin:5px;',
                                    format:'d/m/Y',
                                    listeners:{
                                        change:function(field,value)
                                        {
                                            try
                                            {
                                                Ext.getCmp('fechaRenovacion').setValue(Ext.Date.add(value, Ext.Date.YEAR, 1));
                                            }catch(e){}
                                        }
                                    }
                                },
                                {
                                    xtype:'datefield',
                                    id:'fechaRenovacion',//id14
                                    name:'panel2.ferenova',
                                    fieldLabel:'Fecha de renovaci&oacute;n',
                                    allowBlank:false,
                                    style:'margin:5px;',
                                    format:'d/m/Y',
                                    readOnly:true
                                },
                                {
                                    xtype:'combo',
                                    id:'tipoPoliza',//id12
                                    name:'panel2.cdtipopol',
                                    fieldLabel:'Tipo de poliza',
                                    displayField: 'value',
                                    valueField: 'key',
                                    store:Ext.create('Ext.data.Store', {
                                        model:'Generic',
                                        autoLoad:true,
                                        proxy:
                                        {
                                            type: 'ajax',
                                            url:urlCargarCatalogos,
                                            extraParams:{catalogo:'<s:property value="CON_CAT_POL_TIPO_POLIZA" />'},
                                            reader:
                                            {
                                                type: 'json',
                                                root: 'lista'
                                            }
                                        }
                                    }),
                                    editable:false,
                                    queryMode:'local',
                                    style:'margin:5px;',
                                    allowBlank:false
                                },
                                {
                                    xtype:'combo',
                                    id:'formaPagoPoliza',//id15
                                    name:'panel2.cdperpag',
                                    fieldLabel:'Forma de pago',
                                    displayField: 'value',
                                    valueField: 'key',
                                    store:Ext.create('Ext.data.Store', {
                                        model:'Generic',
                                        autoLoad:true,
                                        proxy:
                                        {
                                            type: 'ajax',
                                            url:urlCargarCatalogos,
                                            extraParams:{catalogo:'<s:property value="CON_CAT_POL_TIPO_PAGO" />'},
                                            reader:
                                            {
                                                type: 'json',
                                                root: 'lista'
                                            }
                                        }
                                    }),
                                    editable:false,
                                    queryMode:'local',
                                    style:'margin:5px;',
                                    allowBlank:false
                                }
                            ]
                        })<%--,
                        Ext.create('Ext.panel.Panel',{
                            id:'panelDatosAdicionales',//id16
                            title:'Datos adicionales',
                            style:'margin:5px',
                            collapsible:true,
                            titleCollapse:true,
                            layout:{
                                type: 'table',
                                columns: 2
                            },
                            <s:property value="items" />
                        })--%>
                    ],
                    buttons:[
                        {
                            text:'Guardar',
                            icon: 'resources/extjs4/resources/ext-theme-classic/images/icons/fam/accept.png',
                            handler:function()
                            {
                                var form=Ext.getCmp('formPanel');
                                //console.log(form.getValues());
                                if(form.isValid())
                                {
                                    form.setLoading(true);
                                    form.submit({
                                        success:function(){
                                            form.setLoading(false);
                                            Ext.Msg.show({
                                                title:'Cambios guardados',
                                                msg: 'Sus cambios han sido guardados',
                                                buttons: Ext.Msg.OK
                                            });
                                        },
                                        failure:function(){
                                            form.setLoading(false);
                                            Ext.Msg.show({
                                                title:'Error',
                                                msg: 'Error de comunicaci&oacute;n',
                                                buttons: Ext.Msg.OK,
                                                icon: Ext.Msg.ERROR
                                            });
                                        }
                                    });
                                }
                                else
                                {
                                    Ext.Msg.show({
                                        title:'Datos incompletos',
                                        msg: 'Favor de introducir todos los campos requeridos',
                                        buttons: Ext.Msg.OK,
                                        icon: Ext.Msg.WARNING
                                    });
                                }
                            }
                        },
                        {
                            text:'Editar asegurados',
                            icon: 'resources/extjs4/resources/ext-theme-classic/images/icons/fam/user.png'
                        },
                        {
                            text:'Editar agentes',
                            icon: 'resources/extjs4/resources/ext-theme-classic/images/icons/fam/user_gray.png'
                        },
                        {
                            text:'Editar documentos',
                            icon: 'resources/extjs4/resources/ext-theme-classic/images/icons/fam/book.png'
                        }
                    ]
                });
                
                
                ///////////////////////////////////////////////
                ////// Cargador de formulario (sin grid) //////
                /*///////////////////////////////////////////*/
                Ext.define('LoaderForm',
                {
                    extend:'MiModeloDinamico',
                    proxy:
                    {
                        extraParams:{
                            cdunieco : '<s:property value="nmpoliza" />',
                            cdramo :   '<s:property value="cdramo" />',
                            estado :   '<s:property value="estado" />',
                            nmpoliza : '<s:property value="nmpoliza" />'
                        },
                        type:'ajax',
                        url:urlCargar,
                        reader:{
                            type:'json'
                        }
                    }
                });

                var loaderForm=Ext.ModelManager.getModel('LoaderForm');
                loaderForm.load(123, {
                    success: function(resp) {
                        //console.log(resp);
                        Ext.getCmp('formPanel').loadRecord(resp);
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
                /*//////////////////////////////////////////////////*/
                ////// Fin de cargador de formulario (sin grid) //////
                //////////////////////////////////////////////////////
                
                //Ext.getCmp('formPanel').loadRecord(storeLoader.getAt(0));
            });
        </script>
    </head>
    <body>
        <div id="maindiv" style="height:1000px;"></div>
        <%--////////////////////////////////////
        ////// para el parser de archivos //////
        ////////////////////////////////////--%
        <script>Ext.onReady(afterExtReady);</script>
        <%--////////////////////////////////--%>
        <!--// para el parser de archivos //////
        /////////////////////////////////////-->
    </body>
</html>
