<%@ include file="/taglibs.jsp"%>
<%@page contentType="text/html" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <style>
        div.claseTitulo>div.x-panel-header>div.x-header-body>div.x-box-inner>div.x-box-target>div.x-panel-header-text-container>span.x-header-text
        {
            font-size:16px;
            font-weight: bold;
            text-transform: uppercase;
        }
        </style>
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
            var inputCdunieco='<s:property value="cdunieco" />';
            var inputCdramo='<s:property value="cdramo" />';
            var inputEstado='<s:property value="estado" />';
            var inputNmpoliza='<s:property value="nmpoliza" />';
            var inputNtramite='<s:property value="map1.ntramite" />';
            debug("inputNtramite",inputNtramite);
            var urlEditarAsegurados='<s:url namespace="/" action="editarAsegurados" />';
            var contexto='${ctx}';
            var urlRecotizar='<s:url namespace="/" action="recotizar" />';
            var accordion;
            var urlEmitir='<s:url namespace="/" action="emitir" />';
            var panDatComUrlDoc='<s:url namespace="/documentos" action="ventanaDocumentosPoliza" />';
            var panDatComUrlCotiza='<s:url namespace="/" action="cotizacionVital" />';
            var datComPolizaMaestra;
            
            function expande(indice)
            {
            	var comp;
            	if(indice==1)
           		{
            		comp=Ext.getCmp('formPanel');
           		}
            	else if(indice==2)
           		{
            	    comp=Ext.getCmp('tabPanelAsegurados');
           		}
           		//window.parent.scrollTo(0,150+comp.y);
          		comp.expand();
          	}
            
            Ext.onReady(function(){
                
                Ext.define('MiModeloDinamico',{
                    extend:'Ext.data.Model',
                    <s:property value="fields" />
                });
                
                Ext.define('ModeloDetalleCotizacion',{
                    extend:'Ext.data.Model',
                    fields:
                    [
                        {name : 'Codigo_Garantia'},
                        {name : 'Importe',type : 'float'},
                        {name : 'Nombre_garantia'},
                        {name : 'cdtipcon'},
                        {name : 'nmsituac'},
                        {name : 'orden'},
                        {name : 'parentesco'}
                    ]
                });
                
                accordion=Ext.create('Ext.panel.Panel',
                {
                	title:'Poliza '+inputNmpoliza,
                	renderTo : 'maindiv'
                	,layout   :
               		{
                		type           : 'accordion'
                		,animate       : true
                		,titleCollapse : true
               		}
                    ,items:
                    [
                    /**/
                        Ext.create('Ext.form.Panel',{
                        	title : 'Editar datos complementarios / emitir',
                        	cls:'claseTitulo',
		                    id:'formPanel',//id1
		                    //renderTo:'maindiv',
		                    url:urlGuardar,
		                    buttonAlign:'center',
		                    //bodyPadding:5,
		                    items:[
		                        Ext.create('Ext.panel.Panel',{
		                            id:'panelDatosGeneralesPoliza',//id2
		                            title:'Datos generales de la poliza',
		                            style:'margin:5px',
		                            frame:false,
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
		                            frame: false,
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
		                                    readOnly:true,
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
		                        }),
		                        Ext.create('Ext.panel.Panel',{
		                            id:'panelDatosAdicionales',//id16
		                            title:'Datos adicionales',
		                            style:'margin:5px',
		                            collapsible:true,
		                            titleCollapse:true,
		                            maxHeight:200,
		                            layout:{
		                                type: 'table',
		                                columns: 2
		                            },
		                            <s:property value="items" />
		                        })
		                    ],
		                    buttons:[
		                        {
		                            text:'Guardar',
		                            icon: contexto+'/resources/extjs4/resources/ext-theme-classic/images/icons/fam/accept.png',
		                            handler:function()
		                            {
		                                var form=Ext.getCmp('formPanel');
		                                //console.log(form.getValues());
		                                if(form.isValid())
		                                {
		                                    form.setLoading(true);
		                                    form.submit({
		                                        params:{
		                                            'map1.pv_cdunieco' :  inputCdunieco,
		                                            'map1.pv_cdramo' :    inputCdramo,
		                                            'map1.pv_estado' :    inputEstado,
		                                            'map1.pv_nmpoliza' :  inputNmpoliza
		                                        },
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
		                        <%--
		                        {
		                            text:'Editar asegurados',
		                            hidden:true,
		                            icon: contexto+'/resources/extjs4/resources/ext-theme-classic/images/icons/fam/user.png',
		                            handler:function(){
		                                Ext.create('Ext.form.Panel').submit({
		                                    url : urlEditarAsegurados,
		                                    standardSubmit:true,
		                                    params:{
		                                        'map1.cdunieco' :  inputCdunieco,
		                                        'map1.cdramo' :    inputCdramo,
		                                        'map1.estado' :    inputEstado,
		                                        'map1.nmpoliza' :  inputNmpoliza
		                                    }
		                                });
		                            }
		                        },
		                        --%>
		                        {
		                            text:'Editar agentes',
		                            icon: contexto+'/resources/extjs4/resources/ext-theme-classic/images/icons/fam/user_gray.png',
		                            disabled:true,
		                            hidden:true
		                        },
		                        {
		                            text:'Editar documentos',
		                            icon: contexto+'/resources/extjs4/resources/ext-theme-classic/images/icons/fam/book.png',
		                            disabled:true,
		                            hidden:true
		                        },
		                        {
		                            text     : 'Continuar'
		                            ,icon    : contexto+'/resources/fam3icons/icons/key.png'
		                            ,handler : function()
		                            {
		                                var form=Ext.getCmp('formPanel');
		                                form.setLoading(true);
		                                Ext.Ajax.request(
		                                {
		                                    url     : urlRecotizar
		                                    ,params :
		                                    {
		                                        'panel1.nmpoliza' : inputNmpoliza
		                                    }
		                                    ,success : function(response)
		                                    {
		                                        form.setLoading(false);
		                                        var json=Ext.decode(response.responseText);
		                                        //console.log(json);
		                                        /**/
		                                        Ext.create('Ext.window.Window',
		                                        {
		                                            title: 'Tarifa final',
		                                            maxHeight: 400,
		                                            autoScroll:true,
		                                            width: 560,
		                                            height: 250,
		                                            defaults: {
		                                            	width: 550
		                                            },
		                                            modal:true,
		                                            closable:false,
		                                            items:[  // Let's put an empty grid in just to illustrate fit layout
		                                                Ext.create('Ext.grid.Panel',{
		                                                    store:Ext.create('Ext.data.Store',{
		                                                        model:'ModeloDetalleCotizacion',
		                                                        groupField: 'parentesco',
		                                                        proxy: {
		                                                            type: 'memory',
		                                                            reader: 'json'
		                                                        },
		                                                        data:json.slist1
		                                                    }),
		                                                    columns:
		                                                    [
		                                                        {
		                                                            header    : 'Nombre de la cobertura',
		                                                            dataIndex : 'Nombre_garantia',
		                                                            flex      : 2,
		                                                            summaryType: 'count',
		                                                            summaryRenderer: function(value){
		                                                                return Ext.String.format('Total de {0} cobertura{1}', value, value !== 1 ? 's' : '');
		                                                            }
		                                                        },
		                                                        {
		                                                            header      : 'Importe por cobertura',
		                                                            dataIndex   : 'Importe',
		                                                            flex        : 1,
		                                                            renderer    : Ext.util.Format.usMoney,
		                                                            align       : 'right',
		                                                            summaryType : 'sum'
		                                                        }
		                                                    ],
		                                                    features: [{
		                                                        groupHeaderTpl: 'Parentezco: {name}',
		                                                        ftype:'groupingsummary',
		                                                        startCollapsed :true
		                                                    }]
		                                                })
		                                                ,Ext.create('Ext.toolbar.Toolbar',{
		                                                    buttonAlign:'right'
		                                                    ,items:
		                                                    [
		                                                        '->'
		                                                        ,Ext.create('Ext.form.Label',
		                                                        {
		                                                            style:'color:white;'
		                                                            ,initComponent:function()
		                                                            {
		                                                                var sum=0;
		                                                                for(var i=0;i<json.slist1.length;i++)
		                                                                {
		                                                                    sum+=parseFloat(json.slist1[i].Importe);
		                                                                }
		                                                                this.setText('Total: '+Ext.util.Format.usMoney(sum));
		                                                                this.callParent();
		                                                            }
		                                                        })
		                                                    ]
		                                                })
		                                                ,Ext.create('Ext.form.Panel',
		                                                {
		                                                    layout:
		                                                    {
		                                                        type    : 'table',
		                                                        columns : 5
		                                                    }
		                                                    ,defaults:
		                                                    {
		                                                        style : 'margin:5px;'
		                                                    }
		                                                    ,items:
		                                                    [
		                                                        {
		                                                            xtype : 'textfield'
		                                                            ,id   : 'numerofinalpoliza'
		                                                            ,fieldLabel : 'N&uacute;mero de poliza'
		                                                            ,readOnly   : true
		                                                        }
		                                                        ,{
		                                                        	id     : 'botonEmitirPolizaFinal'
		                                                            ,xtype : 'button'
		                                                            ,text  : 'Emitir'
		                                                            ,icon  : contexto+'/resources/fam3icons/icons/award_star_gold_3.png'
		                                                            //,disabled : true
		                                                            ,handler:function()
		                                                            {
		                                                            	var me=this;
		                                                            	me.up().up().setLoading(true);
		                                                            	Ext.Ajax.request(
		                                                            	{
		                                                            		url     : urlEmitir
		                                                            		,params :
		                                                            		{
		                                                                        'panel1.pv_nmpoliza' : inputNmpoliza
		                                                                        ,'panel1.pv_ntramite'   : inputNtramite
		                                                            		}
		                                                            	    ,success:function(response)
		                                                            	    {
		                                                            	    	me.up().up().setLoading(false);
		                                                            	    	var json=Ext.decode(response.responseText);
		                                                            	    	debug(json);
		                                                            	    	if(json.success==true)
		                                                            	    	{
		                                                            	    		datComPolizaMaestra=json.panel2.nmpoliza;
		                                                            	    		debug("datComPolizaMaestra",datComPolizaMaestra);
		                                                            	    		Ext.getCmp('numerofinalpoliza').setValue(json.panel2.nmpoliex);
		                                                            	    		Ext.getCmp('botonEmitirPolizaFinal').setDisabled(true);
		                                                            	    		Ext.getCmp('botonImprimirPolizaFinal').setDisabled(false);
		                                                            	    		//me.up().up().setClosable(false);
		                                                            	    		Ext.getCmp('venDocVenEmiBotNueCotiza').show();
		                                                            	    		Ext.getCmp('venDocVenEmiBotCancelar').setDisabled(true);
		                                                            	    	}
		                                                            	    	else
		                                                            	    	{
		                                                            	    		Ext.Msg.show({
	                                                                                    title:'Error',
	                                                                                    msg: 'Error al emitir la poliza',
	                                                                                    buttons: Ext.Msg.OK,
	                                                                                    icon: Ext.Msg.ERROR
	                                                                                });
		                                                            	    	}
		                                                            	    }
		                                                            	    ,failure:function()
		                                                            	    {
		                                                            	    	me.up().up().setLoading(false);
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
		                                                            xtype     : 'button'
		                                                            ,id       : 'botonImprimirPolizaFinal'
		                                                            ,text     : 'Imprimir'
		                                                            ,icon     : contexto+'/resources/fam3icons/icons/printer.png'
		                                                            ,disabled : true
		                                                            ,handler  : function()
		                                                            {
		                                                            	Ext.create('Ext.window.Window',
                                                            			{
		                                                            		width        : 600
		                                                            		,height      : 400
		                                                            		,title       : 'Documentos de la poliza '+inputNmpoliza
		                                                            		,closable    : false
		                                                            		,modal       : true
		                                                            		,buttonAlign : 'center'
	                                                            	        ,loadingMask : true
	                                                            	        ,autoScroll  : true
		                                                            		,loader      :
		                                                            		{
		                                                            			url       : panDatComUrlDoc
		                                                            	        ,scripts  : true
		                                                            	        ,autoLoad : true
		                                                            			,params   :
		                                                            			{
		                                                            				'smap1.nmpoliza'   : datComPolizaMaestra
		                                                            				,'smap1.cdunieco'  : inputCdunieco
		                                                            				,'smap1.cdramo'    : inputCdramo
		                                                            				,'smap1.estado'    : 'M'
		                                                            				,'smap1.nmsuplem'  : 0
		                                                            				,'smap1.ntramite'  : inputNtramite
		                                                            			}
		                                                            		}
		                                                            	    ,buttons   :
		                                                            	    [
		                                                            	    	{
		                                                            	    		text     : 'Cerrar'
		                                                            	    		,icon    : contexto+'/resources/fam3icons/icons/cancel.png'
		                                                            	    		,handler : function()
		                                                            	    		{
		                                                            	    			this.up().up().destroy();
		                                                            	    		}
		                                                            	    	}
		                                                            	    ]
                                                            			}).show();
		                                                            }
		                                                        }
		                                                        ,{
		                                                        	xtype    : 'button'
		                                                            ,id      : 'venDocVenEmiBotNueCotiza'
		                                                            ,text    : 'Nueva cotizaci&oacute;n'
		                                                            ,icon    : '${ctx}/resources/fam3icons/icons/page_add.png'
		                                                            ,handler : function()
		                                                            {
		                                                                var me=this;
		                                                                Ext.create('Ext.form.Panel').submit(
		                                                                {
		                                                                    standardSubmit : true
		                                                                    ,url           : panDatComUrlCotiza
		                                                                });
		                                                            }
		                                                        }
		                                                        ,{
		                                                        	xtype    : 'button'
	                                                                ,id      : 'venDocVenEmiBotCancelar'
		                                                            ,text    : 'Cancelar'
		                                                            ,icon    : '${ctx}/resources/fam3icons/icons/cancel.png'
		                                                            ,handler : function()
		                                                            {
		                                                                var me=this;
		                                                                me.up().up().destroy();
		                                                            }
		                                                        }
		                                                    ]
		                                                })
		                                            ]
		                                        }).showAt(50,50);
		                                        Ext.getCmp('venDocVenEmiBotNueCotiza').hide();
		                                        /**/
		                                    }
		                                    ,failure : function()
		                                    {
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
		                        }
		                    ]
                            ,listeners:
                            {
                            	expand:function( p, eOpts )
                            	{
                            		window.parent.scrollTo(0,150+p.y);
                            	}
                            }
		                })
                    /**/
                        ,Ext.create('Ext.panel.Panel',
                        {
                        	id:'tabPanelAsegurados'
                        	,title:'Editar asegurados'
                        	,cls:'claseTitulo'
                        	,loader:
                        	{
                        		url:urlEditarAsegurados
                        		,params:{
                                    'map1.cdunieco' :  inputCdunieco,
                                    'map1.cdramo' :    inputCdramo,
                                    'map1.estado' :    inputEstado,
                                    'map1.nmpoliza' :  inputNmpoliza
                                }
                        		,scripts:true
                        		,autoLoad:true
                        	}
	                        ,listeners:
	                        {
	                            expand:function( p, eOpts )
	                            {
	                                window.parent.scrollTo(0,150+p.y);
	                            }
	                        }
                        })
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
                            cdunieco :  inputCdunieco,
                            cdramo :    inputCdramo,
                            estado :    inputEstado,
                            nmpoliza :  inputNmpoliza
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
        <div id="maindiv" style="height:600px;"></div>
        <%--////////////////////////////////////
        ////// para el parser de archivos //////
        ////////////////////////////////////--%
        <script>Ext.onReady(afterExtReady);</script>
        <%--////////////////////////////////--%>
        <!--// para el parser de archivos //////
        /////////////////////////////////////-->
    </body>
</html>
