<%@ include file="/taglibs.jsp"%>
<%@page contentType="text/html" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <%--<style>
        div.claseTitulo>div.x-panel-header>div.x-header-body>div.x-box-inner>div.x-box-target>div.x-panel-header-text-container>span.x-header-text
        {
            font-size:16px;
            font-weight: bold;
            text-transform: uppercase;
        }
        </style>--%>
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
            var panDatComMap1 = <s:property value="%{convertToJSON('map1')}" escapeHtml="false" />;
            debug('panDatComMap1:',panDatComMap1);
            var urlGuardar='<s:url namespace="/" action="guardarDatosComplementarios" />';
            var urlCargar='<s:url namespace="/" action="cargarDatosComplementarios" />';
            //var urlCargarCatalogos='<s:url namespace="/flujocotizacion" action="cargarCatalogos" />';
            var urlCargarCatalogos='<s:url namespace="/catalogos" action="obtieneCatalogo" />';
            var inputCdunieco='<s:property value="cdunieco" />';
            var inputCdramo='<s:property value="cdramo" />';
            var inputEstado='<s:property value="estado" />';
            var inputNmpoliza='<s:property value="nmpoliza" />';
            var inputNtramite='<s:property value='map1.ntramite' />';
            var inputCdtipsit='<s:property value='cdtipsit' />';
            debug("inputNtramite",inputNtramite);
            var urlEditarAsegurados='${ctx}<s:property value="map1.urlAsegurados" />';
            var contexto='${ctx}';
            var urlRecotizar='<s:url namespace="/" action="recotizar" />';
            var accordion;
            var urlEmitir='<s:url namespace="/" action="emitir" />';
            var urlReintentarWS ='<s:url namespace="/" action="reintentaWSautos" />';
            var panDatComUrlDoc= '<s:url namespace="/documentos" action="ventanaDocumentosPoliza" />';
            var panDatComUrlDoc2='<s:url namespace="/documentos" action="ventanaDocumentosPolizaClon" />';
            var panDatComUrlCotiza='<s:url namespace="/" action="cotizacionVital" />';
            var datComPolizaMaestra;
            var sesionDsrol='<s:property value="map1.sesiondsrol" />';
            var datComUrlMCUpdateStatus= '<s:url namespace="/mesacontrol"     action="actualizarStatusTramite" />';
            var datComUrlMC            = '<s:url namespace="/mesacontrol"     action="mcdinamica" />';
            var urlPantallaValosit     = '<s:url namespace="/"                action="pantallaValosit" />';
            var urlPantallaAgentes     = '<s:url namespace="/flujocotizacion" action="principal" />';
            var urlServidorReports      = '<s:text name="ruta.servidor.reports" />';
            var _NOMBRE_REPORTE_CARATULA = '<s:text name="rdf.caratula.previa.nombre" />';
            if(panDatComMap1.SITUACION=='AUTO')
            {
                _NOMBRE_REPORTE_CARATULA = '<s:text name="rdf.caratula.previa.auto.nombre" />';
            }
            
            var complerepSrvUsr            = '<s:text name="pass.servidor.reports" />';
            var compleUrlViewDoc     = '<s:url namespace ="/documentos"     action="descargaDocInline" />';
            var compleUrlGuardarCartoRechazo = '<s:url namespace="/" action="guardarCartaRechazo" />';
            var compleUrlCotizacion = '<s:url namespace="/emision" action="cotizacion" />';
           
            var _urlEnviarCorreo         = '<s:url namespace="/general"         action="enviaCorreo"             />';
            
            var _URL_CONSULTA_CLAUSU_DETALLE =      '<s:url namespace="/catalogos" action="consultaClausulaDetalle" />';
            var _URL_CONSULTA_CLAUSU =      '<s:url namespace="/catalogos" action="consultaClausulas" />';
            
            var fechaMinEmi = Ext.Date.parse('<s:property value="map1.fechamin" />','d/m/Y');
            var fechaMaxEmi = Ext.Date.parse('<s:property value="map1.fechamax" />','d/m/Y');
            debug('fechaMinEmi:',fechaMinEmi);
            debug('fechaMaxEmi:',fechaMaxEmi);
            debug(sesionDsrol);
            
            var _paramsRetryWS;
            var _mensajeEmail;
            
            var panDatComUpdateFerenova = function(field,value)
            {
                try
                {
                    Ext.getCmp('fechaRenovacion').setValue(Ext.Date.add(value, Ext.Date.YEAR, 1));
                }catch(e)
                {}
            }
            
            var pantallaValositParche = false;
            var panDatComAux1         = 0;
            
            if(inputCdramo+'x'=='6x')
            {
                pantallaValositParche = function()
                {
                    debug('>parchando tvalosit');
                    
                    _fieldByName('panel2.feefec').removeListener('change',panDatComUpdateFerenova);
                    
                    _fieldByName('panel2.feefec').addListener('change',function()
                    {
                        if(Ext.isEmpty(_fieldByName('parametros.pv_otvalor20').getValue()))
                        {
                            mensajeWarning('Favor de capturar la vigencia');
                        }
                        else
                        {
                            _fieldByName('panel2.ferenova').setValue(
                                Ext.Date.add(_fieldByName('panel2.feefec').getValue(),Ext.Date.MONTH,_fieldByName('parametros.pv_otvalor20').getValue())
                            );
                        }
                    });
                    
                    _fieldByName('parametros.pv_otvalor20').addListener('select',function()
                    {
                        _fieldByName('panel2.ferenova').setValue(
                            Ext.Date.add(_fieldByName('panel2.feefec').getValue(),Ext.Date.MONTH,_fieldByName('parametros.pv_otvalor20').getValue())
                        );
                        var vigencia=_fieldByName('parametros.pv_otvalor20').getValue();
                        debug('vigencia:',vigencia);
                        if(vigencia-0==3||vigencia-0==6)
                        {
                            _fieldByName('panel2.cdperpag').setValue('12');
                            _fieldByName('panel2.cdperpag').setReadOnly(true);
                        }
                        else
                        {
                            _fieldByName('panel2.cdperpag').setReadOnly(false);
                        }
                    });
                    
                    debug('<parchando tvalosit');
                };
            }
            
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
           		window.parent.scrollTo(0,0);
          		accordion.setActiveTab(comp);
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
                        {name : 'parentesco'},
                        {name : 'orden_parentesco'}
                    ]
                });
                
                accordion=Ext.create('Ext.tab.Panel',
                {
                	title:'Tr&aacute;mite '+inputNtramite,
                	border:0,
                	renderTo : 'maindiv'
                	/*,layout   :
               		{
                		type           : 'accordion'
                		,animate       : true
                		,titleCollapse : true
               		}*/
                    ,items:
                    [
                    /**/Ext.create('Ext.panel.Panel',
                        {
                            id:'tabPanelValosit'
                            ,title:'Datos de cotizaci&oacute;n'
                            ,cls:'claseTitulo'
                            ,border:0
                            ,loader:
                            {
                                url       : urlPantallaValosit
                                ,params   :
                                {
                                    'smap1.cdunieco'  : inputCdunieco
                                    ,'smap1.cdramo'   : inputCdramo
                                    ,'smap1.estado'   : inputEstado
                                    ,'smap1.nmpoliza' : inputNmpoliza
                                    ,'smap1.cdtipsit' : inputCdtipsit
                                    ,'smap1.agrupado' : 'si'
                                }
                                ,scripts  : true
                                ,autoLoad : true
                            }
                            ,listeners:
                            {
                                /*expand:function( p, eOpts )
                                {
                                    window.parent.scrollTo(0,150+p.y);
                                }*/
                                afterrender:function(tab)
                                {
                                    debug('afterrender tabPanelValosit');
                                    tab.loader.load();
                                }
                            }
                        })
                        ,Ext.create('Ext.panel.Panel',
                        {
                            id:'tabPanelAsegurados'
                            ,title:inputCdtipsit=='AF'||inputCdtipsit=='PU'?'Editar clientes':'Editar asegurados'
                            ,cls:'claseTitulo'
                            ,border:0
                            ,loader:
                            {
                                url     : urlEditarAsegurados
                                ,params :
                                {
                                    'map1.cdunieco'  : inputCdunieco
                                    ,'map1.cdramo'   : inputCdramo
                                    ,'map1.cdtipsit' : inputCdtipsit
                                    ,'map1.estado'   : inputEstado
                                    ,'map1.nmpoliza' : inputNmpoliza
                                }
                                ,scripts:true
                                ,autoLoad:true
                            }
                            ,listeners:
                            {
                                /*expand:function( p, eOpts )
                                {
                                    window.parent.scrollTo(0,150+p.y);
                                }*/
                                afterrender:function(tab)
                                {
                                    debug('afterrender tabPanelAsegurados');
                                    tab.loader.load();
                                }
                            }
                        })
                        ,Ext.create('Ext.form.Panel',{
                        	title : 'Editar datos complementarios / emitir',
                        	cls:'claseTitulo',
		                    id:'formPanel',//id1
		                    //renderTo:'maindiv',
		                    url:urlGuardar,
		                    buttonAlign:'center',
		                    border:0,
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
		                                    fieldLabel: 'Sucursal',
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
		                                    style:'margin:5px;',
		                                    hidden:true
		                                },
		                                {                        //<<maquillado
		                                	xtype:'textfield',   //<<maquillado  
                                            readOnly:true,       //<<maquillado
                                            fieldLabel:'Poliza', //<<maquillado
                                            value:'0',           //<<maquillado
                                            style:'margin:5px;', //<<maquillado
		                                },                       //<<maquillado
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
		                                            extraParams : {catalogo:'<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@STATUS_POLIZA"/>'},
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
		                                    xtype:'numberfield',
		                                    id:'solicitud',//id10
		                                    name:'panel2.solici',
		                                    fieldLabel:'Cotizaci&oacute;n',
		                                    style:'margin:5px;',
		                                    allowBlank:false,
		                                    readOnly:true
		                                },
		                                {
		                                    xtype:'datefield',
		                                    id:'fechaSolicitud',//id9
		                                    name:'panel2.fesolici',
		                                    fieldLabel:'Fecha de solicitud',
		                                    allowBlank:false,
		                                    style:'margin:5px;',
		                                    format:'d/m/Y',
		                                    //minValue:fechaMinEmi,
		                                    //maxValue:fechaMaxEmi,
		                                    readOnly:panDatComMap1.SITUACION=='AUTO'
		                                },
		                                {
		                                    xtype:'datefield',
		                                    id:'fechaEfectividad',//id11
		                                    name:'panel2.feefec',
		                                    fieldLabel:'Fecha de inicio de vigencia',
		                                    allowBlank:false,
		                                    style:'margin:5px;',
		                                    format:'d/m/Y',
		                                    listeners:{
		                                        change:panDatComUpdateFerenova
		                                    },
		                                    minValue:fechaMinEmi,
                                            maxValue:fechaMaxEmi
		                                },
		                                {
		                                    xtype:'datefield',
		                                    id:'fechaRenovacion',//id14
		                                    name:'panel2.ferenova',
		                                    fieldLabel:'Fecha de t&eacute;rmino de vigencia',
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
		                                            <s:if test='%{getMap1().get("SITUACION").equals("AUTO")}'>
		                                            extraParams : {catalogo:'<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@TIPOS_POLIZA_AUTO"/>'},
		                                            </s:if>
		                                            <s:else>
		                                            extraParams : {catalogo:'<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@TIPOS_POLIZA"/>'},
		                                            </s:else>
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
		                                            <s:if test='map1.SITUACION=="AUTO"'>
		                                            extraParams : {catalogo:'<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@TIPOS_PAGO_POLIZA_SIN_DXN"/>'},
		                                            </s:if>
		                                            <s:else>
		                                            extraParams : {catalogo:'<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@TIPOS_PAGO_POLIZA"/>'},
		                                            </s:else>
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
		                            maxHeight:300,
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
		                        }
		                        ,{
                                    text     : 'Guardar y enviar a revisión médica'
                                    ,icon    : '${ctx}/resources/fam3icons/icons/heart_add.png'
                                    ,hidden  : (!sesionDsrol)||sesionDsrol!='SUSCRIPTOR'
                                    ,handler:function()
                                    {
                                        var form=Ext.getCmp('formPanel');
                                        //console.log(form.getValues());
                                        Ext.create('Ext.window.Window',
                                        {
                                        	title        : 'Observaciones para el m&eacute;dico'
                                        	,width       : 600
                                        	,height      : 400
                                        	,buttonAlign : 'center'
                                        	,modal       : true
                                        	,closable    : false
                                        	,autoScroll  : true
                                        	,items       :
                                        	[
												Ext.create('Ext.form.HtmlEditor', {
												    id        : 'inputTextareaCommentsToMedico'
												    ,width  : 570
												    ,height : 300
												})
                                        	]
                                        	,buttons    :
                                        	[
                                        	    {
                                        	    	text     : 'Guardar y enviar a revisión médica'
                                                    ,icon    : '${ctx}/resources/fam3icons/icons/heart_add.png'
                                        	    	,handler : function()
                                        	    	{
                                        	    		if(form.isValid())
                                                        {
                                        	    			var window=this.up().up();
                                                            window.setLoading(true);
                                                            form.submit({
                                                                params:{
                                                                    'map1.pv_cdunieco' :  inputCdunieco,
                                                                    'map1.pv_cdramo' :    inputCdramo,
                                                                    'map1.pv_estado' :    inputEstado,
                                                                    'map1.pv_nmpoliza' :  inputNmpoliza
                                                                },
                                                                success:function(){
                                                                    Ext.Ajax.request
                                                                    ({
                                                                        url     : datComUrlMCUpdateStatus
                                                                        ,params : 
                                                                        {
                                                                            'smap1.ntramite' : inputNtramite
                                                                            ,'smap1.status'  : '1'//en revision medica
                                                                            ,'smap1.comments' : Ext.getCmp('inputTextareaCommentsToMedico').getValue()
                                                                        }
                                                                        ,success : function(response)
                                                                        {
                                                                            var json=Ext.decode(response.responseText);
                                                                            if(json.success==true)
                                                                            {
                                                                                Ext.create('Ext.form.Panel').submit
                                                                                ({
                                                                                    url             : datComUrlMC
                                                                                    ,params         :
                                                                                    {
                                                                                    	'smap1.gridTitle':'Tareas',
                                                                                    	'smap2.pv_cdtiptra_i':1,
                                                                                    	'smap1.editable':1
                                                                                    }
                                                                                    ,standardSubmit : true
                                                                                });
                                                                            }
                                                                            else
                                                                            {
                                                                            	window.setLoading(false);
                                                                                Ext.Msg.show({
                                                                                    title:'Error',
                                                                                    msg: 'Error al enviar a revisi&oacute;n m&eacute;dica',
                                                                                    buttons: Ext.Msg.OK,
                                                                                    icon: Ext.Msg.ERROR
                                                                                });
                                                                            }
                                                                        }
                                                                        ,failure : function()
                                                                        {
                                                                            Ext.Msg.show({
                                                                                title:'Error',
                                                                                msg: 'Error de comunicaci&oacute;n',
                                                                                buttons: Ext.Msg.OK,
                                                                                icon: Ext.Msg.ERROR
                                                                            });
                                                                        }
                                                                    });
                                                                },
                                                                failure:function(){
                                                                	window.setLoading(false);
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
                                        	    }
                                        	    ,{
                                        	    	text  : 'Cancelar'
                                        	    	,icon : '${ctx}/resources/fam3icons/icons/cancel.png'
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
		                            text     : 'Emitir'
		                            ,icon    : contexto+'/resources/fam3icons/icons/key.png'
		                            ,hidden  : ((!sesionDsrol)||sesionDsrol!='SUSCRIPTOR')&&panDatComMap1.SITUACION!='AUTO'
		                            ,handler : function()
		                            {
		                                var form=Ext.getCmp('formPanel');
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
		                                        	 //--
		                                        	 Ext.Ajax.request(
									                                {
									                                    url     : urlRecotizar
									                                    ,params :
									                                    {
									                                        'panel1.nmpoliza' : inputNmpoliza
									                                        ,cdunieco         : inputCdunieco
									                                        ,cdramo           : inputCdramo
									                                        ,cdtipsit         : inputCdtipsit
									                                    }
									                                    ,success : function(response)
									                                    {
									                                        form.setLoading(false);
									                                        var json=Ext.decode(response.responseText);
									                                        //console.log(json);
									                                        /**/
									                                        if(json.mensajeRespuesta&&json.mensajeRespuesta.length>0)
									                                        {
									                                        	centrarVentanaInterna(Ext.Msg.show({
							                                                        title:'Verificar datos',
							                                                        msg: json.mensajeRespuesta,
							                                                        buttons: Ext.Msg.OK,
							                                                        icon: Ext.Msg.WARNING
							                                                    }));
									                                        }
									                                        else
									                                        {
										                                        var orden=0;
															                    var parentescoAnterior='qwerty';
															                    for(var i=0;i<json.slist1.length;i++)
															                    {
															                        if(json.slist1[i].parentesco!=parentescoAnterior)
															                        {
															                            orden++;
															                            parentescoAnterior=json.slist1[i].parentesco;
															                        }
															                        json.slist1[i].orden_parentesco=orden+'_'+json.slist1[i].parentesco;
															                    }
										                                        Ext.create('Ext.window.Window',
										                                        {
										                                            title: 'Tarifa final',
										                                            //maxHeight: 400,
										                                            autoScroll:true,
										                                            width: 660,
										                                            height: 400,
										                                            defaults: {
										                                            	width: 650
										                                            },
										                                            modal:false,
										                                            closable:false,
										                                            collapsible:true,
										                                            titleCollapse:true,
										                                            items:[  // Let's put an empty grid in just to illustrate fit layout
										                                                Ext.create('Ext.grid.Panel',{
										                                                    store:Ext.create('Ext.data.Store',{
										                                                        model:'ModeloDetalleCotizacion',
										                                                        groupField: 'orden_parentesco',
										                                                        sorters: [{
										                                                            sorterFn: function(o1, o2){
										                                                                if (o1.get('orden') === o2.get('orden'))
										                                                                {
										                                                                    return 0;
										                                                                }
										                                                                return o1.get('orden') < o2.get('orden') ? -1 : 1;
										                                                            }
										                                                        }],
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
										                                                    	groupHeaderTpl:
										                                                            [
										                                                                '{name:this.formatName}',
										                                                                {
										                                                                    formatName:function(name)
										                                                                    {
										                                                                        return name.split("_")[1];
										                                                                    }
										                                                                }
										                                                            ],
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
										                                                            		,timeout : 240000
										                                                            		,params :
										                                                            		{
										                                                                        'panel1.pv_nmpoliza'  : inputNmpoliza
										                                                                        ,'panel1.pv_ntramite' : inputNtramite
										                                                                        ,'panel2.pv_cdramo'   : inputCdramo
										                                                                        ,'panel2.pv_cdunieco' : inputCdunieco
										                                                                        ,'panel2.pv_estado'   : inputEstado
										                                                                        ,'panel2.pv_nmpoliza' : inputNmpoliza
										                                                                        ,'panel2.pv_cdtipsit' : inputCdtipsit
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
										                                                            	    		Ext.getCmp('botonEmitirPolizaFinal').hide();
										                                                            	    		Ext.getCmp('botonEmitirPolizaFinalPreview').hide();
										                                                            	    		Ext.getCmp('botonImprimirPolizaFinal').setDisabled(false);
										                                                            	    		
                                	    																			Ext.getCmp('botonReenvioWS').hide();
                                	    																			
                                	    																			if(panDatComMap1.SITUACION == 'AUTO'){
                                	    																				_mensajeEmail = json.mensajeEmail;
                                	    																				//debug("Mensaje Mail: " + _mensajeEmail);
                                	    																				Ext.getCmp('botonEnvioEmail').enable();
                                	    																			}else {
                                	    																				Ext.getCmp('botonEnvioEmail').hide();
                                	    																			}
										                                                            	    		
										                                                            	    		if(panDatComMap1.SITUACION=='AUTO')
										                                                            	    		{
										                                                            	    		    Ext.getCmp('venDocVenEmiBotIrCotiza').show();
										                                                            	    		}
										                                                            	    		else
										                                                            	    		{
										                                                            	    		    Ext.getCmp('venDocVenEmiBotNueCotiza').show();
										                                                            	    		}
										                                                            	    		Ext.getCmp('venDocVenEmiBotCancelar').setDisabled(true);
										                                                            	    		if(json.mensajeRespuesta&&json.mensajeRespuesta.length>0)
										                                                            	    		{
										                                                            	    			var ventanaTmp = Ext.Msg.show({
									                                                                                        title:'Aviso del sistema',
									                                                                                        msg: json.mensajeRespuesta,
									                                                                                        buttons: Ext.Msg.OK,
									                                                                                        icon: Ext.Msg.WARNING,
									                                                                                        fn: function(){
									                                                                                        	if(!Ext.isEmpty(json.nmpolAlt)){
									                                                                                        		mensajeCorrecto("Aviso","P&oacute;liza Emitida: " + json.nmpolAlt);
									                                                                                        	}
									                                                                                        }
									                                                                                    });
										                                                            	    			centrarVentanaInterna(ventanaTmp);
										                                                            	    		}else { 
										                                                            	    			if(!Ext.isEmpty(json.nmpolAlt)){
							                                                                                        		mensajeCorrecto("Aviso","P&oacute;liza Emitida: " + json.nmpolAlt);
							                                                                                        	}
										                                                            	    		}
										                                                            	    	}
										                                                            	    	else
										                                                            	    	{
										                                                            	    		if(json.retryWS){
										                                                            	    			datComPolizaMaestra=json.panel2.nmpoliza;
											                                                            	    		debug("datComPolizaMaestra",datComPolizaMaestra);
											                                                            	    		
											                                                            	    		Ext.getCmp('botonEmitirPolizaFinal').hide();
											                                                            	    		Ext.getCmp('botonEmitirPolizaFinalPreview').hide();
											                                                            	    		
											                                                            	    		if(panDatComMap1.SITUACION=='AUTO')
											                                                            	    		{
											                                                            	    		    Ext.getCmp('venDocVenEmiBotIrCotiza').show();
											                                                            	    		}
											                                                            	    		else
											                                                            	    		{
											                                                            	    		    Ext.getCmp('venDocVenEmiBotNueCotiza').show();
											                                                            	    		}
											                                                            	    		Ext.getCmp('venDocVenEmiBotCancelar').setDisabled(true);
										                                                            	    		}
										                                                            	    		Ext.Msg.show({
									                                                                                    title    :'Aviso'
									                                                                                    ,msg     : json.mensajeRespuesta
									                                                                                    ,buttons : Ext.Msg.OK
									                                                                                    ,icon    : Ext.Msg.WARNING
									                                                                                    ,fn      : function(){
									                                                                                    	if(json.retryWS){
									                                                                                    		var paramsWS = {
																                                                                        'panel1.pv_nmpoliza'  : inputNmpoliza
																                                                                        ,'panel1.pv_ntramite' : inputNtramite
																                                                                        ,'panel2.pv_cdramo'   : inputCdramo
																                                                                        ,'panel2.pv_cdunieco' : inputCdunieco
																                                                                        ,'panel2.pv_estado'   : inputEstado
																                                                                        ,'panel2.pv_nmpoliza' : inputNmpoliza
																                                                                        ,'panel2.pv_cdtipsit' : inputCdtipsit
																                                                                        ,'nmpoliza'           : json.nmpoliza
																                                                                        ,'nmsuplem'           : json.nmsuplem
																                                                                        ,'cdIdeper'           : json.cdIdeper
																                                                            		};
									                                                                                    		reintentarWSAuto(me.up().up(), paramsWS);
									                                                                                    	}
									                                                                                    }
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
										                                                        	id     : 'botonEnvioEmail'
										                                                            ,xtype : 'button'
										                                                            ,text  : 'Enviar Email'
										                                                            ,icon  : contexto+'/resources/fam3icons/icons/email.png'
										                                                            ,disabled: true
										                                                            ,hidden: (panDatComMap1.SITUACION != 'AUTO') ? true: false
										                                                            ,handler:function()
										                                                            {
										                                                            	Ext.Msg.prompt('Envio de Email', 'Escriba los correos que recibir&aacute;n la documentaci&oacute;n (separados por ;)', 
										                                                            	function(buttonId, text){
										                                                            		if(buttonId == "ok" && !Ext.isEmpty(text)){
										                                                            			
										                                                            			if(Ext.isEmpty(_mensajeEmail)){
										                                                            				mensajeError('Mensaje de Email sin contenido. Consulte a Soporte T&eacute;cnico');
										                                                            				return;
										                                                            			}
										                                                            			
										                                                            			Ext.Ajax.request(
										                                                        		    			{
										                                                        		    				url : _urlEnviarCorreo,
										                                                        		    				params :
										                                                        		    				{
										                                                        		    					to     : text,
										                                                        		    					asunto : 'Documentación de póliza de Autos',
										                                                        		    					mensaje: _mensajeEmail,
										                                                        		    					html   : true
										                                                        		    			    },
										                                                        		    			    callback : function(options,success,response)
										                                                        		    			    {
										                                                        		    			    	if (success)
										                                                        		    			    	{
										                                                        		    			    		var json = Ext.decode(response.responseText);
										                                                        		    			    		if (json.success == true)
										                                                        		    			    		{
										                                                        		    			    			Ext.Msg.show(
										                                                        		    			    			{
										                                                        		    			    				title : 'Correo enviado'
										                                                        		    			    				,msg : 'El correo ha sido enviado'
										                                                        		    			    				,buttons : Ext.Msg.OK
										                                                        		    			    			});
										                                                        		    			    		}
										                                                        		    			    		else
										                                                        		    			    		{
										                                                        		    			    			mensajeError('Error al enviar el correo');
										                                                        		    			    		}
										                                                        		    			    	}
										                                                        		    			    	else
										                                                        		    			    	{
										                                                        		    			    		errorComunicacion();
										                                                        		    			    	}
										                                                        		    			    }
										                                                        		    			});
										                                                            		
										                                                            		}else {
										                                                            			mensajeWarning('Introduzca al menos una direcci&oacute;n de email');	
										                                                            		}
										                                                            	})
										                                                            }
										                                                        }
										                                                        ,{
										                                                        	id     : 'botonReenvioWS'
										                                                            ,xtype : 'button'
										                                                            ,text  : 'Reintentar Emisi&oacute;n'
										                                                            ,icon  : contexto+'/resources/fam3icons/icons/award_star_gold_3.png'
										                                                            ,disabled: true
										                                                            ,hidden: (panDatComMap1.SITUACION != 'AUTO') ? true: false
										                                                            ,handler:function()
										                                                            {
										                                                            	var me=this;
										                                                            	reintentarWSAuto(me.up().up(), _paramsRetryWS);
										                                                            }
										                                                        }
										                                                        ,{
										                                                        	xtype    : 'button'
										                                                        	,id      : 'botonEmitirPolizaFinalPreview'
										                                                        	,text    : 'Vista previa'
										                                                        	,icon    : '${ctx}/resources/fam3icons/icons/zoom.png'
										                                                        	,handler : function()
										                                                            {
										                                                                var me=this;
										                                                                var urlRequestImpCotiza=urlServidorReports
										                                                                +'?destype=cache'
										                                                                +"&desformat=PDF"
										                                                                +"&report="+_NOMBRE_REPORTE_CARATULA
										                                                                +"&paramform=no"
										                                                                +"&userid="+complerepSrvUsr
										                                                                +"&ACCESSIBLE=YES" //parametro que habilita salida en PDF
										                                                                +'&p_unieco='+inputCdunieco
										                                                                +'&p_estado=W'
										                                                                +'&p_ramo='+inputCdramo
										                                                                +'&p_poliza='+inputNmpoliza
										                                                                debug(urlRequestImpCotiza);
										                                                                var numRand=Math.floor((Math.random()*100000)+1);
										                                                                debug(numRand);
										                                                                var windowVerDocu=Ext.create('Ext.window.Window',
										                                                                {
										                                                                    title          : 'Vista previa'
										                                                                    ,width         : 700
										                                                                    ,height        : 500
										                                                                    ,collapsible   : true
										                                                                    ,titleCollapse : true
										                                                                    ,html          : '<iframe innerframe="'+numRand+'" frameborder="0" width="100" height="100"'
										                                                                                     +'src="'+compleUrlViewDoc+"?contentType=application/pdf&url="+encodeURIComponent(urlRequestImpCotiza)+"\">"
										                                                                                     +'</iframe>'
										                                                                    ,listeners     :
										                                                                    {
										                                                                        resize : function(win,width,height,opt){
										                                                                            debug(width,height);
										                                                                            $('[innerframe="'+numRand+'"]').attr({'width':width-20,'height':height-60});
										                                                                        }
										                                                                    }
										                                                                }).show();
										                                                                windowVerDocu.center();
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
										                                                            	venDocuTramite.destroy();
										                                                            	Ext.create('Ext.window.Window',
								                                                                        {
								                                                                            title        : 'Documentos del tr&aacute;mite '+inputNtramite
								                                                                            ,modal       : true
								                                                                            ,buttonAlign : 'center'
								                                                                            ,width       : 600
								                                                                            ,height      : 400
								                                                                            ,autoScroll  : true
								                                                                            ,loader      :
								                                                                            {
								                                                                                url       : panDatComUrlDoc2
								                                                                                ,params   :
								                                                                                {
								                                                                                	'smap1.nmpoliza'  : datComPolizaMaestra
								                                                                                    ,'smap1.cdunieco' : inputCdunieco
								                                                                                    ,'smap1.cdramo'   : inputCdramo
								                                                                                    ,'smap1.estado'   : 'M'
								                                                                                    ,'smap1.nmsuplem' : '0'
								                                                                                    ,'smap1.ntramite' : inputNtramite
								                                                                                    ,'smap1.nmsolici' : inputNmpoliza
								                                                                                    ,'smap1.tipomov'  : '0'
								                                                                                }
								                                                                                ,scripts  : true
								                                                                                ,autoLoad : true
								                                                                            }
								                                                                        }).show();
										                                                            }
										                                                        }
										                                                        ,{
										                                                        	xtype    : 'button'
										                                                            ,id      : 'venDocVenEmiBotNueCotiza'
										                                                            ,text    : 'Regresar a mesa de control'
										                                                            ,icon    : '${ctx}/resources/fam3icons/icons/house.png'
										                                                            ,hidden  : panDatComMap1.SITUACION=='AUTO'
										                                                            ,handler : function()
										                                                            {
										                                                                var me=this;
										                                                                Ext.create('Ext.form.Panel').submit(
										                                                                {
										                                                                    standardSubmit : true
										                                                                    ,url           : datComUrlMC
										                                                                    ,params        :
						                                                                                    {
						                                                                                        'smap1.gridTitle':'Tareas',
						                                                                                        'smap2.pv_cdtiptra_i':1,
						                                                                                        'smap1.editable':1
						                                                                                    }
										                                                                });
										                                                            }
										                                                        }
										                                                        ,{
										                                                            xtype    : 'button'
                                                                                                    ,id      : 'venDocVenEmiBotIrCotiza'
                                                                                                    ,text    : 'Nueva cotizaci&oacute;n'
                                                                                                    ,icon    : '${ctx}/resources/fam3icons/icons/book_open.png'
                                                                                                    ,hidden  : panDatComMap1.SITUACION!='AUTO'
                                                                                                    ,handler : function()
                                                                                                    {
                                                                                                        var me=this;
                                                                                                        Ext.create('Ext.form.Panel').submit(
                                                                                                        {
                                                                                                            standardSubmit : true
                                                                                                            ,url           : compleUrlCotizacion
                                                                                                            ,params        :
                                                                                                            {
                                                                                                                'smap1.cdramo'    : inputCdramo
                                                                                                                ,'smap1.cdtipsit' : inputCdtipsit
                                                                                                            }
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
										                                        Ext.getCmp('venDocVenEmiBotIrCotiza').hide();
										                                        /**/
									                                        }
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
									                                //--
		                                            
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
		                                	datosIncompletos();
		                        	    }
		                            }
		                        }
		                        ,{
                                    text     : 'Guardar y dar Vo. Bo.'
                                    ,icon    : '${ctx}/resources/fam3icons/icons/heart_add.png'
                                    ,hidden  : (!sesionDsrol)||sesionDsrol!='MEDICO'
                                    ,handler:function()
                                    {
                                    	Ext.create('Ext.window.Window',
                                        {
                                            title        : 'Dictamen para mesa de control'
                                            ,width       : 600
                                            ,height      : 400
                                            ,buttonAlign : 'center'
                                            ,modal       : true
                                            ,closable    : false
                                            ,autoScroll  : true
                                            ,items       :
                                            [
												Ext.create('Ext.form.HtmlEditor', {
												    id        : 'inputTextareaCommentsToMCFromMedico'
											    	,width  : 570
                                                    ,height : 300
												})
                                            ]
                                            ,buttons    :
                                            [
                                                {
                                                    text     : 'Guardar y dar Vo. Bo.'
                                                    ,icon    : '${ctx}/resources/fam3icons/icons/heart_add.png'
                                                    ,handler : function()
                                                    {
				                                        var form=Ext.getCmp('formPanel');
				                                        var window=this.up().up();
				                                        //console.log(form.getValues());
				                                        if(form.isValid())
				                                        {
				                                        	window.setLoading(true);
				                                            form.submit({
				                                                params:{
				                                                    'map1.pv_cdunieco' :  inputCdunieco,
				                                                    'map1.pv_cdramo' :    inputCdramo,
				                                                    'map1.pv_estado' :    inputEstado,
				                                                    'map1.pv_nmpoliza' :  inputNmpoliza
				                                                },
				                                                success:function(){
				                                                    Ext.Ajax.request
				                                                    ({
				                                                        url     : datComUrlMCUpdateStatus
				                                                        ,params : 
				                                                        {
				                                                            'smap1.ntramite'   : inputNtramite
				                                                            ,'smap1.status'    : '5'//Vo.Bo.Medico
				                                                            ,'smap1.comments' : Ext.getCmp('inputTextareaCommentsToMCFromMedico').getValue() 
				                                                        }
				                                                        ,success : function(response)
				                                                        {
				                                                            var json=Ext.decode(response.responseText);
				                                                            if(json.success==true)
				                                                            {
				                                                                Ext.create('Ext.form.Panel').submit
				                                                                ({
				                                                                    url             : datComUrlMC
				                                                                    ,standardSubmit : true
				                                                                    ,params         :
                                                                                    {
                                                                                        'smap1.gridTitle':'Tareas',
                                                                                        'smap2.pv_cdtiptra_i':1,
                                                                                        'smap1.editable':1
                                                                                    }
				                                                                });
				                                                            }
				                                                            else
				                                                            {
				                                                            	window.setLoading(false);
				                                                                Ext.Msg.show({
				                                                                    title:'Error',
				                                                                    msg: 'Error al guardar Vo. Bo.',
				                                                                    buttons: Ext.Msg.OK,
				                                                                    icon: Ext.Msg.ERROR
				                                                                });
				                                                            }
				                                                        }
				                                                        ,failure : function()
				                                                        {
				                                                        	window.setLoading(false);
				                                                            Ext.Msg.show({
				                                                                title:'Error',
				                                                                msg: 'Error de comunicaci&oacute;n',
				                                                                buttons: Ext.Msg.OK,
				                                                                icon: Ext.Msg.ERROR
				                                                            });
				                                                        }
				                                                    });
				                                                },
				                                                failure:function(){
				                                                	window.setLoading(false);
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
                                                }
                                                ,
                                                {
                                                	text  : 'Cancelar'
                                                    ,icon : '${ctx}/resources/fam3icons/icons/cancel.png'
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
                                    text     : 'Guardar como pendiente de informaci&oacute;n'
                                    ,icon    : '${ctx}/resources/fam3icons/icons/clock.png'
                                    ,hidden  : (!sesionDsrol)||sesionDsrol!='MEDICO'
                                    ,handler:function()
                                    {
                                        Ext.create('Ext.window.Window',
                                        {
                                            title        : 'Dictamen para mesa de control'
                                            ,width       : 600
                                            ,height      : 400
                                            ,buttonAlign : 'center'
                                            ,modal       : true
                                            ,closable    : false
                                            ,autoScroll  : true
                                            ,items       :
                                            [
                                                Ext.create('Ext.form.HtmlEditor', {
                                                    id        : 'inputTextareaCommentsToMCFromMedico'
                                                    ,width  : 570
                                                    ,height : 300
                                                })
                                            ]
                                            ,buttons    :
                                            [
                                                {
                                                	text     : 'Guardar como pendiente de informaci&oacute;n'
                                                    ,icon    : '${ctx}/resources/fam3icons/icons/clock.png'
                                                    ,handler : function()
                                                    {
                                                        var form=Ext.getCmp('formPanel');
                                                        var window=this.up().up();
                                                        //console.log(form.getValues());
                                                        if(form.isValid())
                                                        {
                                                            window.setLoading(true);
                                                            form.submit({
                                                                params:{
                                                                    'map1.pv_cdunieco' :  inputCdunieco,
                                                                    'map1.pv_cdramo' :    inputCdramo,
                                                                    'map1.pv_estado' :    inputEstado,
                                                                    'map1.pv_nmpoliza' :  inputNmpoliza
                                                                },
                                                                success:function(){
                                                                    Ext.Ajax.request
                                                                    ({
                                                                        url     : datComUrlMCUpdateStatus
                                                                        ,params : 
                                                                        {
                                                                            'smap1.ntramite'   : inputNtramite
                                                                            ,'smap1.status'    : '6'//Esperando info
                                                                            ,'smap1.comments' : Ext.getCmp('inputTextareaCommentsToMCFromMedico').getValue() 
                                                                        }
                                                                        ,success : function(response)
                                                                        {
                                                                            var json=Ext.decode(response.responseText);
                                                                            if(json.success==true)
                                                                            {
                                                                                Ext.create('Ext.form.Panel').submit
                                                                                ({
                                                                                    url             : datComUrlMC
                                                                                    ,standardSubmit : true
                                                                                    ,params         :
                                                                                    {
                                                                                        'smap1.gridTitle':'Tareas',
                                                                                        'smap2.pv_cdtiptra_i':1,
                                                                                        'smap1.editable':1
                                                                                    }
                                                                                });
                                                                            }
                                                                            else
                                                                            {
                                                                                window.setLoading(false);
                                                                                Ext.Msg.show({
                                                                                    title:'Error',
                                                                                    msg: 'Error al guardar Vo. Bo.',
                                                                                    buttons: Ext.Msg.OK,
                                                                                    icon: Ext.Msg.ERROR
                                                                                });
                                                                            }
                                                                        }
                                                                        ,failure : function()
                                                                        {
                                                                            window.setLoading(false);
                                                                            Ext.Msg.show({
                                                                                title:'Error',
                                                                                msg: 'Error de comunicaci&oacute;n',
                                                                                buttons: Ext.Msg.OK,
                                                                                icon: Ext.Msg.ERROR
                                                                            });
                                                                        }
                                                                    });
                                                                },
                                                                failure:function(){
                                                                    window.setLoading(false);
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
                                                }
                                                ,
                                                {
                                                    text  : 'Cancelar'
                                                    ,icon : '${ctx}/resources/fam3icons/icons/cancel.png'
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
                                    text     : 'Rechazar'
                                    ,icon    : '${ctx}/resources/fam3icons/icons/cancel.png'
                                    ,hidden  : (!sesionDsrol)||(sesionDsrol!='SUSCRIPTOR'&&sesionDsrol!='MEDICO')
                                    ,handler:function()
                                    {
                                        var form=Ext.getCmp('formPanel');
                                        var idClausula;
                                        
                                        if(sesionDsrol=='MEDICO'){
                                        	descripcion = 'CARTA RECHAZO MEDICO';
                                        }else{
                                        	descripcion ='CARTA RECHAZO ADMINISTRATIVA';
                                        }
                                        
                                        //Obtengo el valor del ID para obtener el valor de la descripcion
                                        Ext.Ajax.request(
                           				{
                           				    url     : _URL_CONSULTA_CLAUSU
                           				    ,params : 
                           				    {
                           						'params.cdclausu' : null,
												'params.dsclausu' : descripcion
                           				    }
                           				    ,success : function (response)
                           				    {
                           				    	var json=Ext.decode(response.responseText);
                           				    	var claveClausula = json.listaGenerica[0].key;
                                                
                           				    	Ext.Ajax.request(
        										{
        										    url     : _URL_CONSULTA_CLAUSU_DETALLE
        										    ,params : 
        										    {
        										        'params.cdclausu'  : claveClausula
        										    }
        										    ,success : function (response)
        										    {
        										    	var json=Ext.decode(response.responseText);
        										    	txtContenido =json.msgResult;
        										    	
        										    	Ext.create('Ext.window.Window',
   		                                                {
   		                                                    title        : 'Guardar detalle'
   		                                                    ,width       : 600
   		                                                    ,height      : 400
   		                                                    ,buttonAlign : 'center'
   		                                                    ,modal       : true
   		                                                    ,closable    : false
   		                                                    ,autoScroll  : true
   		                                                    ,items       :
   		                                                    [
   		                                                        Ext.create('Ext.form.field.TextArea', {
   		                                                            id        : 'inputTextareaCommentsToRechazo'
   		                                                            ,width  : 570
   		                                                            ,height : 200
   		                                                            ,value  : txtContenido
   		                                                        }),
   		                                                     	Ext.create('Ext.form.field.TextArea', {
		                                                            id        : 'inputTextareaComments'
		                                                            ,width  : 570
		                                                            ,height : 100
		                                                        })
   		                                                        
   		                                                    ]
   		                                                    ,buttons    :
   		                                                    [
   		                                                        {
   		                                                            text     : 'Rechazar'
   		                                                            ,icon    : '${ctx}/resources/fam3icons/icons/cancel.png'
   		                                                            ,handler : function()
   		                                                            {
   		                                                                if(true||form.isValid())
   		                                                                {
   		                                                                    var window=this.up().up();
   		                                                                    window.setLoading(true);
   		                                                                    /*form.submit({
   		                                                                        params:{
   		                                                                            'map1.pv_cdunieco' :  inputCdunieco,
   		                                                                            'map1.pv_cdramo' :    inputCdramo,
   		                                                                            'map1.pv_estado' :    inputEstado,
   		                                                                            'map1.pv_nmpoliza' :  inputNmpoliza
   		                                                                        },
   		                                                                        success:function(){*/
   		                                                                            Ext.Ajax.request
   		                                                                            ({
   		                                                                                url     : datComUrlMCUpdateStatus
   		                                                                                ,params : 
   		                                                                                {
   		                                                                                    'smap1.ntramite' : inputNtramite
   		                                                                                    ,'smap1.status'  : '4'//rechazado
   		                                                                                    ,'smap1.comments' : Ext.getCmp('inputTextareaComments').getValue()
   		                                                                                }
   		                                                                                ,success : function(response)
   		                                                                                {
   		                                                                                    var json=Ext.decode(response.responseText);
   		                                                                                    if(json.success==true)
   		                                                                                    {
   		                                                                                        Ext.Ajax.request(
   		                                                                                        {
   		                                                                                            url     : compleUrlGuardarCartoRechazo
   		                                                                                         	,method:'GET'
   		                                                                                            ,params :
   		                                                                                            {
   		                                                                                                'map1.ntramite'  : inputNtramite
   		                                                                                                ,'map1.comments' : Ext.getCmp('inputTextareaCommentsToRechazo').getValue()
   		                                                                                                ,'map1.cdsisrol' : sesionDsrol
   		                                                                                                ,'map1.cdunieco' : inputCdunieco
   		                                                                                                ,'map1.cdramo'   : inputCdramo
   		                                                                                                ,'map1.estado'   : inputEstado
   		                                                                                                ,'map1.nmpoliza' : inputNmpoliza
   		                                                                                            }
		   		                                                                                    ,success : function(response)
		   	   		                                                                                {
		   		                                                                                    	Ext.create('Ext.form.Panel').submit
		   		                                                                                        ({
		   		                                                                                            url             : datComUrlMC
		   		                                                                                            ,standardSubmit : true
		   		                                                                                            ,params         :
		   		                                                                                            {
		   		                                                                                                'smap1.gridTitle':'Tareas',
		   		                                                                                                'smap2.pv_cdtiptra_i':1,
		   		                                                                                                'smap1.editable':1
		   		                                                                                            }
		   		                                                                                        });
		   	   		                                                                                }
			   		                                                                                 ,failure : function()
		    		                                                                                {
		    		                                                                                    Ext.Msg.show({
		    		                                                                                        title:'Error',
		    		                                                                                        msg: 'Error de comunicaci&oacute;n',
		    		                                                                                        buttons: Ext.Msg.OK,
		    		                                                                                        icon: Ext.Msg.ERROR
		    		                                                                                    });
		    		                                                                                }
   		                                                                                        });
   		                                                                                    }else{
   		                                                                                        window.setLoading(false);
   		                                                                                        Ext.Msg.show({
   		                                                                                            title:'Error',
   		                                                                                            msg: 'Error al rechazar',
   		                                                                                            buttons: Ext.Msg.OK,
   		                                                                                            icon: Ext.Msg.ERROR
   		                                                                                        });
   		                                                                                    }
   		                                                                                }
   		                                                                                ,failure : function()
   		                                                                                {
   		                                                                                    Ext.Msg.show({
   		                                                                                        title:'Error',
   		                                                                                        msg: 'Error de comunicaci&oacute;n',
   		                                                                                        buttons: Ext.Msg.OK,
   		                                                                                        icon: Ext.Msg.ERROR
   		                                                                                    });
   		                                                                                }
   		                                                                            });/*
   		                                                                        },
   		                                                                        failure:function(){
   		                                                                            window.setLoading(false);
   		                                                                            Ext.Msg.show({
   		                                                                                title:'Error',
   		                                                                                msg: 'Error de comunicaci&oacute;n',
   		                                                                                buttons: Ext.Msg.OK,
   		                                                                                icon: Ext.Msg.ERROR
   		                                                                            });
   		                                                                        }
   		                                                                    });*/
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
   		                                                        }
   		                                                        ,{
   		                                                            text  : 'Cancelar'
   		                                                            ,icon : '${ctx}/resources/fam3icons/icons/cancel.png'
   		                                                            ,handler : function()
   		                                                            {
   		                                                                this.up().up().destroy();
   		                                                            }
   		                                                        }
   		                                                    ]
   		                                                }).show();
        										    },
        										    failure : function ()
        										    {
        										        Ext.Msg.show({
        										            title:'Error',
        										            msg: 'Error de comunicaci&oacute;n',
        										            buttons: Ext.Msg.OK,
        										            icon: Ext.Msg.ERROR
        										        });
        										    }
        										});
                           				    },
                           				    failure : function ()
                           				    {
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
                            id:'tabPanelAgentes'
                            ,title:'Agentes'
                            ,cls:'claseTitulo'
                            ,border:0
                            ,loader:
                            {
                                url       : urlPantallaAgentes
                                /*,params   :
                                {
                                    'smap1.cdunieco'  : inputCdunieco
                                    ,'smap1.cdramo'   : inputCdramo
                                    ,'smap1.estado'   : inputEstado
                                    ,'smap1.nmpoliza' : inputNmpoliza
                                    ,'smap1.cdtipsit' : inputCdtipsit
                                    ,'smap1.agrupado' : 'si'
                                }*/
                                ,scripts  : true
                                ,autoLoad : true
                            }
                            ,listeners:
                            {
                                /*expand:function( p, eOpts )
                                {
                                    window.parent.scrollTo(0,150+p.y);
                                }*/
                                afterrender:function(tab)
                                {
                                    debug('afterrender tabPanelAgentes');
                                    tab.loader.load();
                                }
                            }
                        })
                    ]
                    ,listeners:
                    {
                        add :function(panel,tab,pos)
                        {
                            debug('>accordion add pos:',pos);
                            if(pos>3)
                            {
                                tab.border=0;
                                panel.setActiveTab(tab);
                            }
                            debug('<accordion add');
                        }
                    }
                });
                
                //funcion para reintentar WS auto
                
                function reintentarWSAuto(loading, params){

                	Ext.Msg.show({
	                   title    :'Confirmaci&oacute;n'
	                   ,msg     : '&iquest;Desea Reenviar los Web Services de Autos?'
	                   ,buttons : Ext.Msg.YESNO
	                   ,icon    : Ext.Msg.QUESTION
	                   ,fn      : function(boton, text, opt){
	                   	if(boton == 'yes'){
	                   		
	                   		loading.setLoading(true);
	                    	
	                    	Ext.Ajax.request(
	                            	{
	                            		url     : urlReintentarWS
	                            		,timeout: 240000
	                            		,params :params
	                            	    ,success:function(response)
	                            	    {
	                            	    	loading.setLoading(false);
	                            	    	var json=Ext.decode(response.responseText);
	                            	    	debug(json);
	                            	    	if(json.success==true)
	                            	    	{
	                            	    		mensajeCorrecto('Aviso', 'Ejecuci&oacute;n Correcta de Web Services. P&oacute;liza Emitida: ' + json.nmpolAlt);
	                            	    		Ext.getCmp('numerofinalpoliza').setValue(json.nmpolAlt);
                                	    		Ext.getCmp('botonImprimirPolizaFinal').setDisabled(false);
                                	    		Ext.getCmp('botonReenvioWS').setDisabled(true);
                                	    		Ext.getCmp('botonReenvioWS').hide();
                                	    		
                                	    		_mensajeEmail = json.mensajeEmail;
												Ext.getCmp('botonEnvioEmail').enable();
	                            	    	}
	                            	    	else
	                            	    	{
	                            	    		Ext.Msg.show({
	                                                title    :'Aviso'
	                                                ,msg     : json.mensajeRespuesta
	                                                ,buttons : Ext.Msg.OK
	                                                ,icon    : Ext.Msg.WARNING
	                                                ,fn      : function(){
	                                                	reintentarWSAuto(loading, params);
	                                                }
	                                            });
	                            	    	}
	                            	    }
	                            	    ,failure:function()
	                            	    {
	                            	    	loading.setLoading(false);
	                            	    	Ext.Msg.show({
	                                            title:'Error',
	                                            msg: 'Error de comunicaci&oacute;n',
	                                            buttons: Ext.Msg.OK,
	                                            icon: Ext.Msg.ERROR
	                                            ,fn      : function(){
	                                            	reintentarWSAuto(loading, params);
	                                            }
	                                        });
	                            	    }
	                            	});
	                   	}else{
	                   		_paramsRetryWS = params;
	                   		debug("Habilitando Boton Reenvio WS");
	                   		Ext.getCmp('botonReenvioWS').setDisabled(false);
	                   	}
	                   }
                	});
                	                	
                }
                
                //para ver documentos en vivo
                var venDocuTramite=Ext.create('Ext.window.Window',
		        {
		            title           : 'Documentos del tr&aacute;mite '+inputNtramite
		            ,closable       : false
		            ,width          : 500
		            ,height         : 300
		            ,autoScroll     : true
		            ,collapsible    : true
		            ,titleCollapse  : true
		            ,startCollapsed : true
		            ,resizable      : false
		            ,loader         :
		            {
		                scripts   : true
		                ,autoLoad : true
		                ,url      : panDatComUrlDoc
		                ,params   :
		                {
		                    'smap1.cdunieco'  : inputCdunieco
		                    ,'smap1.cdramo'   : inputCdramo
		                    ,'smap1.estado'   : inputEstado
		                    ,'smap1.nmpoliza' : '0'
		                    ,'smap1.nmsuplem' : '0'
		                    ,'smap1.nmsolici' : ''
		                    ,'smap1.ntramite' : inputNtramite
		                    ,'smap1.tipomov'  : '0'
		                }
		            }
		        }).showAt(500,0);
                venDocuTramite.collapse();
                //para ver documentos en vivo
                
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
                        panDatComAux1=panDatComAux1+1;
                        if(panDatComAux1==2&&inputCdramo+'x'=='6x')
                        {
                            pantallaValositParche();
                        }
                        else
                        {
                            debug('complementarios>todavia no se parcha tvalosit');
                        }
                    },
                    failure:function()
                    {
                        centrarVentanaInterna(Ext.Msg.show({
                            title:'Error',
                            icon: Ext.Msg.ERROR,
                            msg: 'Error al cargar',
                            buttons: Ext.Msg.OK
                        }));
                    }
                });
                /*//////////////////////////////////////////////////*/
                ////// Fin de cargador de formulario (sin grid) //////
                //////////////////////////////////////////////////////
                
                <s:if test='%{getActionErrors()!=null&&getActionErrors().size()>0}' >
                var items=[];
                <s:iterator value="actionErrors" var="iError">
                items.push(
                {
                    xtype : 'panel'
                    ,layout   : 'hbox'
                    ,border   : 0
                    ,defaults : { style : 'margin : 5px;' }
                    ,items    :
                    [
                        {
                            xtype   : 'image'
                            ,src    : '${ctx}/resources/fam3icons/icons/error.png'
                            ,width  : 16
                            ,height : 16
                        }
                        ,{
                            xtype  : 'label'
                            ,text  : '<s:property value="iError" escapeHtml="false" />'
                            ,style : 'color:red;margin:5px;'
                        }
                    ]
                });
                </s:iterator>
                centrarVentanaInterna(
                Ext.create('Ext.window.Window',
                {
                    title      : 'Aviso(s)'
                    ,width     : 600
                    ,height    : (items.length*40)+40
                    ,modal     : true
                    ,items     : items
                }).show());
                </s:if>
                
                //Ext.getCmp('formPanel').loadRecord(storeLoader.getAt(0));
            });
        </script>
    </head>
    <body>
        <div id="maindiv" style="height:1200px;"></div>
        <%--////////////////////////////////////
        ////// para el parser de archivos //////
        ////////////////////////////////////--%
        <script>Ext.onReady(afterExtReady);</script>
        <%--////////////////////////////////--%>
        <!--// para el parser de archivos //////
        /////////////////////////////////////-->
    </body>
</html>
