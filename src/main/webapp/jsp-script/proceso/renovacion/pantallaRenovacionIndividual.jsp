<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script>
////// variables //////
var contexto						= '${ctx}';
var urlGuardar                   	= '<s:url namespace="/"           action="guardarDatosComplementarios" 				  />';
var urlCargarCatalogos              = '<s:url namespace="/catalogos"  action="obtieneCatalogo"             />';
var _p25_urlBuscarPolizas  			= '<s:url namespace="/renovacion" action="buscarPolizasIndividualesRenovables" 		  />';
var _p25_urlBuscarPolizasMasivas	= '<s:url namespace="/renovacion" action="buscarPolizasIndividualesMasivasRenovables" />';
var _p25_urlRenovarPolizaIndividual	= '<s:url namespace="/renovacion" action="renovarPolizaIndividual"			   		  />';
var _p25_urlBuscarContratantes  	= '<s:url namespace="/endoso" 	  action="cargarContratantesEndosoContratante" 		  />';
var urlPantallaValosit           	= '<s:url namespace="/"           action="pantallaValosit"             				  />';
var urlEditarAsegurados 			= '<s:url namespace="/" 		  action="editarAsegurados"							  />';           
var _p25_urlPantallaCliente        	= '<s:url namespace="/catalogos"  action="includes/personasLoader"            		  />';
var sesionDsrol   					= _GLOBAL_CDSISROL;

var _p25_storePolizas;
var _p25_storePolizasMasivas;
var _p25_ultimosParams;

var winAutoServicio;
var winServicioAsistido;
var winCambioPago;
var winCambioDomicilio;
var wineditarContratante;

var pantallaValositParche = false;

var panDatComUpdateFerenova = function(field,value)
{
	try
    	{
        	if(!Ext.isEmpty(plazoEnDias) && '|16|5|6|'.lastIndexOf('|'+inputCdramo+'|')!=-1)
            	{
                	Ext.getCmp('fechaRenovacion').setValue(Ext.Date.add(value, Ext.Date.DAY, plazoEnDias));
                }
                else
                {
                	Ext.getCmp('fechaRenovacion').setValue(Ext.Date.add(value, Ext.Date.YEAR, 1));
                }
		}catch(e)
        {
        	debugError(e);
		}
}
////// variables //////

////// componentes dinamicos //////
var itemsFormularioBusqueda      = [<s:property value="imap.busquedaItems" 				  escapeHtml="false" />];
var itemsFormularioContratante   = [<s:property value="imap.itemsFormularioContratante"   escapeHtml="false" />];
var itemsFormularioPolizaColumns = [<s:property value="imap.itemsFormularioPolizaColumns" escapeHtml="false" />];
var gridColumns					 = [<s:property value="imap.gridColumns"                  escapeHtml="false" />];
var itemsEditarPago				 = [<s:property value="imap.itemsEditarPago"              escapeHtml="false" />];
var itemsEditarDomicilio	     = [<s:property value="imap.itemsEditarDomicilio"         escapeHtml="false" />];
var itemsTatrisit	     		 = [<s:property value="imap.itemsTatrisit"				  escapeHtml="false" />];
itemsEditarPago.splice(1, 0, { border : 0 });
itemsEditarPago.push({ 
						xtype      : 'button',
						itemId     : 'itemEditDomicil',
						icon      : '${ctx}/resources/fam3icons/icons/report_key.png',
						text       : 'Editar domicilio',
						arrowAlign : 'bottom',
						handler    : function(){
							debug('antes de _p25_ventanaCambioDomicilio');
							_p25_ventanaCambioDomicilio();
						} 
					});
////// componentes dinamicos //////

Ext.onReady(function()
{
    Ext.Ajax.timeout = 15*60*1000;
    ////// modelos //////
    Ext.define('_p25_modeloPoliza',
    {
        extend  : 'Ext.data.Model'
        ,fields : [ <s:property value="imap.gridFields" escapeHtml="false" /> ]
    });
    
    Ext.define('_p25_modeloContratante',
    {
        extend  : 'Ext.data.Model'
        ,fields : [ <s:property value="imap.fieldsFormularioContratante" escapeHtml="false" /> ]
    });
    
    Ext.define('_p25_modeloPolizasMasivas',
    {
        extend  : 'Ext.data.Model'
        ,fields : [ <s:property value="imap.itemsFormularioPolizaFields" escapeHtml="false" /> ]
    });
    ////// modelos //////
    
    ////// stores //////
    _p25_storePolizas = Ext.create('Ext.data.Store',
    {
        model     : '_p25_modeloPoliza'
        ,autoLoad : false
        ,proxy    :
        {
            type    : 'ajax'
            ,url    : _p25_urlBuscarPolizas
            ,reader :
            {
                type             : 'json'
                ,root            : 'slist1'
                ,messageProperty : 'respuesta'
                ,successProperty : 'success'
            }
        }
    });
    
    _p25_storePolizasMasivas = Ext.create('Ext.data.Store',
    {
        model     : '_p25_modeloPolizasMasivas'
        ,autoLoad : false
        ,proxy    :
        {
            type    : 'ajax'
            ,url    : _p25_urlBuscarPolizasMasivas
            ,reader :
            {
                type             : 'json'
                ,root            : 'slist1'
                ,messageProperty : 'respuesta'
            }
        }
    });
    
    _p25_storeContratantes = Ext.create('Ext.data.Store',
    {
        model     : '_p25_modeloContratante'
        ,autoLoad : false
        ,proxy    :
        {
            type    : 'ajax'
            ,url    : _p25_urlBuscarContratantes
            ,reader :
            {
                type             : 'json'
                ,root            : 'slist1'
                ,messageProperty : 'respuesta'
            }
        }
    });
    ////// stores //////
    
    ////// componentes //////
    winCambioDomicilio = Ext.create('Ext.window.Window', {
		title       : 'Autoservicio-Editar domicilio',
		itemId      : 'winCambioDomicilio',
    	layout      : 'fit',
    	closeAction : 'hide',
    	items       : [
    		Ext.create('Ext.form.Panel', {
    			bodyPadding : 10,
    			items       : itemsEditarDomicilio,
    			layout :
                {
                    type     : 'table',
                    columns : 2
                },
    			buttons	:	
    				[
    					{ 
    						text : 'Guardar',
    						handler : function(){
    							_fieldById('winCambioDomicilio').close();
    						}
    					},
    					{ 
    						text    : 'Cancelar',
    						handler : function(){
    							_fieldById('winCambioDomicilio').close();
    						}
    					}
    				]
    		})
        ]
    });
    
    winAutoServicio = Ext.create('Ext.window.Window', {
		title       : 'Auto-servicio',
		itemId      : 'winAutoServicio',
    	layout      : 'fit',
    	closeAction : 'hide',
    	items       : [
    		Ext.create('Ext.form.Panel', {
    			bodyPadding : 10,
    			items       : [
    				{
    					xtype       : 'radiogroup',
    					itemId      : 'itemRadio',
    					columns		: 1,
        				vertical	: false,
            			items       : [
                			{
                    			boxLabel   : 'Cambio en forma de pago, contratante y/o domicilio',
                    			name       : 'topping',
                    			inputValue : '1',
                    			itemId     : 'checkbox1',
                    			checked	   : true
                			},
                			{
                    			boxLabel   : 'Inclusion de coberturas',
                    			name       : 'topping',
                    			inputValue : '2',
                    			itemId     : 'checkbox2'
                			}
            			]
    				}	
    			],
    			buttons	:	
    				[
    					{ 
    						text    : 'Aceptar',
    						handler : function(){
    							if(_fieldById('itemRadio').getValue()['topping'] = 1){
    								_fieldById('winAutoServicio').close();
    								_p25_ventanaCambioFormaPago(this.up('window').resRenova);
    							}
    						} 
    					},
    					{ 
    						text    : 'Cancelar',
    						handler : function(){
    							_fieldById('winAutoServicio').close();
    						}
    					}
    				]
    		})
        ]
    });
    
    winServicioAsistido = Ext.create('Ext.window.Window', {
		title       : 'Auto-servicio',
		itemId      : 'winServicioAsistido',
    	layout      : 'fit',
    	closeAction : 'hide',
    	items  : [
    		Ext.create('Ext.form.Panel', {
    			bodyPadding : 10,
    			items       : [
    				{
    					xtype       : 'fieldcontainer',
            			defaultType : 'checkboxfield',
            			layout :
            			{
                    		type     : 'table',
                    		columns : 1
                		},
            			items       : [
                			{
                    			boxLabel   : 'Cambio de ',
                    			name       : 'topping',
                    			inputValue : 'S',
                    			id         : 'checkbox1'
                			},
                			{
                    			boxLabel   : 'Modificar los datos del tramite',
                    			name       : 'topping',
                    			inputValue : 'M',
                    			id         : 'checkbox2'
                			}
            			]
    				}	
    			],
    			buttons	:	
    				[
    					{ 
    						text : 'Aceptar' 
    					},
    					{ 
    						text    : 'Cancelar',
    						handler : function(){
    							_fieldById('winServicioAsistido').close();
    						}
    					}
    				]
    		})
        ]
    });
    
    winCambioPago = Ext.create('Ext.window.Window', {
		title       : 'Autoservicio-Editar',
		itemId      : 'winCambioPago',
    	closeAction : 'hide',
    	layout      : 'fit',
    	items       : [
    		Ext.create('Ext.form.Panel', {
    			bodyPadding : 10,
    			items       : itemsEditarPago,
    			layout :
                {
                    type     : 'table',
                    columns  : 2
                },
    			buttons	:	
    				[
    					{ 
    						text : 'Guardar' 
    					},
    					{ 
    						text    : 'Cancelar',
    						handler : function(){
    							_fieldById('winCambioPago').close();
    						}
    					}
    				]
    		})
        ]
    });
    
    wineditarContratante = Ext.create('Ext.window.Window', {
		title       : 'Autoservicio-Editar',
		itemId      : 'winCambioPago',
    	closeAction : 'hide',
    	layout      : 'fit',
    	width		: 880,
    	height      : 550,
    	autoScroll  : 'true',
    	items		: [
			Ext.create('Ext.tab.Panel', {
    			width		: 300,
    			height		: 200,
    			activeTab	: 0,
    			items: [
					Ext.create('Ext.panel.Panel',{
						itemId     : '_p25_clientePanel',
						title      : 'CLIENTE',
						height     : 400,
						autoScroll : true,
						loader     :
								{
				    				url       	: _p25_urlPantallaCliente,
				    				scripts  	: true,
				    				autoLoad 	: false,
				    				ajaxOptions	: {
				                    	method: 'POST'
				                 	},
				                 	params:	{
										//'smap1.cdperson' 			: _fieldById('winCambioPago').resRenova['cdperson'],	//_fieldByName('cdperson',form).getValue(),
				    					'smap1.cdideper' 			: '',	//json.smap1.cdideper,
				    					'smap1.cdideext' 			: '',	//json.smap1.cdideext,
				    					'smap1.esSaludDanios' 		: 'S',
				    					'smap1.esCargaClienteNvo' 	: 'N',	//(Ext.isEmpty(json.smap1.cdperson)? 'S' : 'N' ),
				    					'smap1.ocultaBusqueda' 		: 'N',
				    					'smap1.cargaCP' 			: '',	//json.smap1.cdpostal,
				    					'smap1.cargaTipoPersona' 	: '',	//json.smap1.otfisjur,
				    					'smap1.cargaSucursalEmi' 	: '',	//_fieldByName('cdunieco',form).getValue(),
				    					'smap1.activaCveFamiliar'	: 'N',
										'smap1.modoRecuperaDanios'	: 'N',
										//'smap1.modoSoloEdicion'		: 'S',
										'smap1.modoEdicionDomicilio': 'S'
									}
								}
					}),
					Ext.create('Ext.panel.Panel',{
						title 	    : 'Editar datos complementarios / emitir',
				        cls		    : 'claseTitulo',
				        id		    : 'formPanel',
				        itemId 	    : 'formPanel',
				        url         : urlGuardar,
				        buttonAlign : 'center',
				        autoScroll  : true,
				        border		: 0,
				        items		: [
				            Ext.create('Ext.panel.Panel',{
				                id				: 'panelDatosGeneralesPoliza',
				                title			: 'Datos generales de la poliza',
				                style			: 'margin:5px',
				                frame			: false,
				                collapsible		: true,
				                titleCollapse	: true,
				                layout			: {
				                    type	: 'table',
				                    columns : 3
				                },
				                items:[
				                    Ext.create('Ext.form.TextField', {
				                        id			: 'companiaAseguradora',//id3
				                        name		: 'panel1.dsciaaseg',
				                        fieldLabel	: 'Sucursal',
				                        readOnly	: true,
				                        style		: 'margin:5px;'
				                    }),
				                    Ext.create('Ext.form.TextField', {
				                        id			: 'agenteVentas',
				                        name		: 'panel1.nombreagente',
				                        fieldLabel	: 'Agente',		                            
				                        readOnly	: true,
				                        style		:'margin:5px;'
				                    }),
				                    Ext.create('Ext.form.TextField', {
				                        id			: 'producto',
				                        name		: 'panel1.dsramo',
				                        fieldLabel	: 'Producto',		                                 
				                        readOnly	: true,
				                        style		: 'margin:5px;'
				                    })
				                ]
				            }),
				            Ext.create('Ext.panel.Panel',{
			                    id				: 'panelDatosGenerales',//id5
			                    title			: 'Datos generales',
			                    style			: 'margin:5px',
			                    frame			:  false,
			                    collapsible		: true,
			                    titleCollapse	: true,
			                    layout			: {
			                        type	: 'table',
			                        columns	: 3
			                    },
			                    items:[
			                        {
			                            xtype		: 'textfield',
			                            id			: 'poliza',//id6
			                            name		: 'panel2.nmpoliza',
			                            readOnly	: true,
			                            fieldLabel	: 'Poliza',
			                            style		: 'margin:5px;',
			                            hidden		: true
			                        },
			                        {                        
			                        	xtype		: 'textfield',     
	                                    readOnly	: true,       
	                                    fieldLabel	: 'Poliza', 
	                                    value		: '0',           
	                                    style		:' margin:5px;' 
			                        },                       
			                        {
			                            xtype			: 'combo',
			                            id				: 'estadoPoliza',//id8
			                            name			: 'panel2.estado',
			                            fieldLabel		: 'Estado',
			                            displayField	: 'value',
			                            valueField		: 'key',
			                            readOnly		: true,
			                            store			: Ext.create('Ext.data.Store', {
			                                model		: 'Generic',
			                                autoLoad	: true,
			                                proxy		:
			                                {
			                                    type		: 'ajax',
			                                    url			: urlCargarCatalogos,
			                                    extraParams : {catalogo:'<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@STATUS_POLIZA"/>'},
			                                    reader		:
			                                    {
			                                        type	: 'json',
			                                        root	: 'lista'
			                                    }
			                                }
			                            }),
			                            editable	: false,
			                            queryMode	: 'local',
			                            style		: 'margin:5px;',
			                            allowBlank	: false
			                        },
			                        {
			                            xtype		: 'numberfield',
			                            id			: 'solicitud',
			                            name		: 'panel2.solici',
			                            fieldLabel	: 'Cotizaci&oacute;n',
			                            style		: 'margin:5px;',
			                            allowBlank	: false,
			                            readOnly	: true
			                        },
			                        {
			                            xtype		: 'datefield',
			                            id			: 'fechaSolicitud',
			                            name		: 'panel2.fesolici',
			                            fieldLabel	: 'Fecha de solicitud',
			                            allowBlank	: false,
			                            style		: 'margin:5px;',
			                            format		: 'd/m/Y',
			                            readOnly	: false
			                        },
			                        {
			                            xtype		: 'datefield',
			                            id			: 'fechaEfectividad',//id11
			                            name		: 'panel2.feefec',
			                            fieldLabel	: 'Fecha de inicio de vigencia',
			                            allowBlank	: false,
			                            style		: 'margin:5px;',
			                            format		: 'd/m/Y',
			                            listeners	: {
			                                change  : panDatComUpdateFerenova
			                            }//,
			                            //minValue:fechaMinEmi,
	                                    //maxValue:fechaMaxEmi
			                        },
			                        {
			                            xtype		: 'datefield',
			                            id			: 'fechaRenovacion',//id14
			                            name		: 'panel2.ferenova',
			                            fieldLabel	: 'Fecha de t&eacute;rmino de vigencia',
			                            allowBlank	: false,
			                            style		: 'margin:5px;',
			                            format		: 'd/m/Y',
			                            readOnly	: true
			                        },
			                        {
			                            xtype			: 'combo',
			                            id				: 'tipoPoliza',//id12
			                            name			: 'panel2.cdtipopol',
			                            fieldLabel		: 'Tipo de poliza',
			                            displayField	: 'value',
			                            valueField		: 'key',
			                            store			: Ext.create('Ext.data.Store', {
			                                model	 :'Generic',
			                                autoLoad :true,
			                                proxy	 :
			                                {
			                                    type		: 'ajax',
			                                    url			: urlCargarCatalogos,
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
			                            editable	: false,
			                            queryMode	: 'local',
			                            style		: 'margin:5px;',
			                            allowBlank	: false
			                        },
			                        {
			                            xtype			: 'combo',
			                            id				: 'formaPagoPoliza',//id15
			                            name			: 'panel2.cdperpag',
			                            fieldLabel		: 'Forma de pago',
			                            displayField	: 'value',
			                            valueField		: 'key',
			                            store:Ext.create('Ext.data.Store', {
			                                model		: 'Generic',
			                                autoLoad	: true,
			                                proxy		: {
			                                    type		: 'ajax',
			                                    url			: urlCargarCatalogos,
			                                    /*extraParams : {
			                                        catalogo           : 'FORMAS_PAGO_POLIZA_POR_RAMO_TIPSIT',
			                                        'params.cdramo'    : inputCdramo,
			                                        'params.cdtipsit'  : inputCdtipsit
			                                    },*/
			                                    reader:
			                                    {
			                                        type: 'json',
			                                        root: 'lista'
			                                    }
			                                }
			                            }),
			                            editable	: false,
			                            queryMode	: 'local',
			                            style		: 'margin:5px;',
			                            allowBlank	: false,
			                            //readOnly 	: Number(inputCdramo)==16
			                        },
			                        {
	                                    xtype	 	: 'textfield',
	                                    name	  	: 'panel2.dsplan',
	                                    readOnly  	: true,
	                                    fieldLabel	: 'Plan',
	                                    style		: 'margin:5px;'
	                                },
			                        {
	                                    xtype		: 'numberfield',
	                                    name		: 'panel2.nmrenova',
	                                    allowBlank	: false,
	                                    maxValue	: 99,
	        							minValue	: 0,
	                                    value		: 0,
	                                    fieldLabel	: 'N&uacute;mero Renovaci&Oacute;n',
	                                    style		: 'margin:5px;'
	                                },
			                        {
	                                    xtype		: 'textfield',
	                                    name		: 'panel2.nmpolant',
	                                    fieldLabel	: 'P&oacute;liza Anterior',
	                                    style		: 'margin:5px;'
	                                }
	                                ,{
	                                    xtype           : 'combo',
	                                    itemId         : '_panDatCom_nmcuadroCmp',
	                                    fieldLabel     : 'Cuadro de comisiones',
	                                    name           : 'panel2.nmcuadro',
	                                    style          : 'margin:5px;',
	                                    forceSelection : true,
	                                    valueField     : 'key',
	                                    displayField   : 'value',       
	                                    editable       : true,
	                                    queryMode      : 'local',
	                                    //disabled       : panDatComMap1.cambioCuadro!='S',
	                                    //hidden         : panDatComMap1.cambioCuadro!='S',
	                                    allowBlank     : false,
	                                    store          :
	                                    Ext.create('Ext.data.Store',
	                                    {
	                                        model     	: 'Generic',
	                                        autoLoad 	: true,
	                                        proxy    	:
	                                        {
	                                           type         : 'ajax',
	                                           url         	: urlCargarCatalogos,
	                                           /*extraParams 	:
	                                           {
	                                               catalogo          : 'CUADROS_POR_SITUACION',
	                                               'params.cdtipsit' : inputCdtipsit
	                                           },*/
	                                           reader      :
	                                           {
	                                               type  : 'json',
	                                               root  : 'lista'
	                                           }
	                                        }
	                                    })
	                                }
			                    ]
			                }),
			                Ext.create('Ext.panel.Panel',{
			                    id				: 'panelDatosAdicionales',
			                    title			: 'Datos adicionales',
			                    style			: 'margin:5px',
			                    collapsible		: true,
			                    titleCollapse	: true,
			                    autoScroll		: true,
			                    maxHeight		: 300,
			                    layout			: {
			                        type	: 'table',
			                        columns	: 3
			                    },
			                    items 			: itemsTatrisit
			                })
				        ],
				        buttons:	[
	                        {
	                            text	: 'Guardar',
	                            icon	: contexto+'/resources/extjs4/resources/ext-theme-classic/images/icons/fam/accept.png',
	                            handler	: function(){ _p29_guardarComplementario(null);}	
	                        },
                            {
	                            text		: 'Editar agentes',
	                            icon		: contexto+'/resources/extjs4/resources/ext-theme-classic/images/icons/fam/user_gray.png',
	                            disabled	: true,
	                            hidden		: true
	                        },
	                        {
	                            text		: 'Editar documentos',
	                            icon		: contexto+'/resources/extjs4/resources/ext-theme-classic/images/icons/fam/book.png',
	                            disabled	: true,
	                            hidden		: true
	                        },
	                        {
	                            text    : ['COTIZADOR', 'SUPTECSALUD'].indexOf(sesionDsrol) != -1 ? 'Cotizar' : 'Emitir',
                                   itemId  : 'panDatComBotonRetarificar',
                                   icon    : contexto+'/resources/fam3icons/icons/key.png',
                                   hidden  : /*panDatComMap1.SITUACION !== 'AUTO' &&*/ (['SUSCRIPTOR', 'COTIZADOR', 'SUPTECSALUD'].indexOf(sesionDsrol) === -1),
                                   handler : function(me)
                                   {
                                   	try
                                   	{
                                    	_p29_guardarComplementario
                                    	(
                                    	  function()
                                    	  {
                                    			if(inputCdramo == 16)
                                                {
                                                  Ext.Ajax.request(
                                                             {
                                                                 url     : _URL_urlCargarTvalosit,
                                                                 params  :
                                                                 {
                                                                     'smap1.cdunieco' : inputCdunieco,
                                                                     'smap1.cdramo'   : inputCdramo,
                                                                     'smap1.estado'   : inputEstado,
                                                                     'smap1.nmpoliza' : inputNmpoliza,
                                                                     'smap1.nmsituac' : '1'
                                                                 }
                                                                 ,success : function(response)
                                                                 {
                                                                     var json=Ext.decode(response.responseText);
                                                                     if(json.exito)
                                                                     {
                                                                             var _p29_validaSeguro = json.smap1['parametros.pv_seguroVida'];	                                                                            
                                                                             debug('fn:', _p29_validaSeguro);
                                                                             if(_p29_validaSeguro == "S")
                                                                             {    
                                                                                 var suma=0;
                                                                                 _p32_store.each(function(record)
                                                                                 {
                                                                                     if(record.get('mov')+'x'!='-x')
                                                                                     {
                                                                                         suma=suma+(record.get('PORBENEF')-0);
                                                                                     }
                                                                                 });
                                                                                 if(suma!=100)
                                                                                 {  
                                                                                     mensajeError('La suma de porcentajes de beneficiarios activos es '+suma+', en lugar de 100');
                                                                                     return
                                                                                 }
                                                                                 else
                                                                                 {
                                                                                	 try{
                                                                                     _p32_guardarClic(_p29_emitirClicComplementarios);
                                                                                	 }
                                                                                	 catch(e){manejaException(e);}
                                                                                     
                                                                                 }

                                                                             }
                                                                             else
                                                                             {
                                                                                 _p29_emitirClicComplementarios();
                                                                             }
                                                                     }
                                                                     else
                                                                     {
                                                                         mensajeError(json.respuesta);
                                                                     }
                                                                 }
                                                             });
                                                }
                                                else
                                                {
                                                    _p29_emitirClicComplementarios();
                                                }
                                    	  }
                                    	);
                                   	}
                                   	catch(e)
                                       {
                                           debugError(e,' panDatComBotonRetarificar handler ');
                                       }
                                   }
                                 },
	                        {
                                   text    : 'Guardar y dar Vo. Bo.',
                                   icon    : '${ctx}/resources/fam3icons/icons/heart_add.png',
                                   hidden  : ((!sesionDsrol)||sesionDsrol!='MEDICO'),//||!Ext.isEmpty(panDatComFlujo),
                                   handler : function()
                                   {
                                   	var form=Ext.getCmp('formPanel');                                         
                                       if(form.isValid()) {           
                                   	
                                    	Ext.create('Ext.window.Window',
                                        {
                                            title       : 'Dictamen para mesa de control',
                                            width       : 600,
                                            height      : 430,
                                            buttonAlign : 'center',
                                            modal       : true,
                                            closable    : false,
                                            autoScroll  : true,
                                            items       :
                                            [
												{
												    id     : 'inputTextareaCommentsToMCFromMedico',
											    	width  : 570,
                                                    height : 300,
                                                    xtype  : 'textarea',
												},
												{
									                xtype      : 'radiogroup',
									                fieldLabel : 'Mostrar al agente',
									                columns    : 2,
									                width      : 250,
									                style      : 'margin:5px;',
									                hidden     : _GLOBAL_CDSISROL===RolSistema.Agente,
									                items      :
									                [
									                    {
									                        boxLabel   : 'Si',
									                        itemId     : 'SWAGENTE',
									                        name       : 'SWAGENTE',
									                        inputValue : 'S',
									                        checked    : _GLOBAL_CDSISROL===RolSistema.Agente
									                    },
									                    {
									                        boxLabel   : 'No',
									                        name       : 'SWAGENTE',
									                        inputValue : 'N',
                                                               checked    : _GLOBAL_CDSISROL!==RolSistema.Agente
									                    }
									                ]
									            }
                                            ]
                                            ,buttons    :
                                            [
                                                {
                                                    text    : 'Guardar y dar Vo. Bo.',
                                                    icon    : '${ctx}/resources/fam3icons/icons/heart_add.png',
                                                    handler : function()
                                                    {
				                                        var form=Ext.getCmp('formPanel');
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
				                                                            'smap1.ntramite'  : inputNtramite,
				                                                            'smap1.status'    : '5',//Vo.Bo.Medico
				                                                            'smap1.comments'  : Ext.getCmp('inputTextareaCommentsToMCFromMedico').getValue(),
				                                                            'smap1.swagente'  : _fieldById('SWAGENTE').getGroupValue()
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
                                                                                        'smap1.gridTitle'	  : 'Tareas',
                                                                                        'smap2.pv_cdtiptra_i' : 1,
                                                                                        'smap1.editable'	  : 1
                                                                                    }
				                                                                });
				                                                            }
				                                                            else
				                                                            {
				                                                            	window.setLoading(false);
				                                                                Ext.Msg.show({
				                                                                    title	: 'Error',
				                                                                    msg		: 'Error al guardar Vo. Bo.',
				                                                                    buttons	: Ext.Msg.OK,
				                                                                    icon	: Ext.Msg.ERROR
				                                                                });
				                                                            }
				                                                        }
				                                                        ,failure : function()
				                                                        {
				                                                        	window.setLoading(false);
				                                                            Ext.Msg.show({
				                                                                title	: 'Error',
				                                                                msg		: 'Error de comunicaci&oacute;n',
				                                                                buttons	: Ext.Msg.OK,
				                                                                icon	: Ext.Msg.ERROR
				                                                            });
				                                                        }
				                                                    });
				                                                },
				                                                failure:function(){
				                                                	window.setLoading(false);
				                                                    Ext.Msg.show({
				                                                        title	: 'Error',
				                                                        msg		: 'Error de comunicaci&oacute;n',
				                                                        buttons	: Ext.Msg.OK,
				                                                        icon	: Ext.Msg.ERROR
				                                                    });
				                                                }
				                                            });
                                                    }
                                                }
                                                ,
                                                {
                                                	text  	: 'Cancelar',
                                                    icon 	: '${ctx}/resources/fam3icons/icons/cancel.png',
                                                    handler : function()
                                                    {
                                                        this.up().up().destroy();
                                                    }
                                                }
                                            ]
                                        }).show();
                                       } else {
                                           Ext.Msg.show({
                                               title	: 'Datos incompletos',
                                               msg		: 'Favor de introducir todos los campos requeridos',
                                               buttons : Ext.Msg.OK,
                                               icon	: Ext.Msg.WARNING
                                           });
                                       }
                                   }
                               },
	                        {
                                   text    : 'Guardar como pendiente de informaci&oacute;n',
                                   icon    : '${ctx}/resources/fam3icons/icons/clock.png',
                                   hidden  : ((!sesionDsrol)||sesionDsrol!='MEDICO'),//||!Ext.isEmpty(panDatComFlujo),
                                   handler : function()
                                   {
                                   	var form=Ext.getCmp('formPanel');                                         
                                       if(form.isValid()) {                                        	
                                           Ext.create('Ext.window.Window',
                                           {
                                               title        : 'Dictamen para mesa de control'
                                               ,width       : 600
                                               ,height      : 430
                                               ,buttonAlign : 'center'
                                               ,modal       : true
                                               ,closable    : false
                                               ,autoScroll  : true
                                               ,items       :
                                               [
                                                   {
                                                       id     : 'inputTextareaCommentsToMCFromMedico',
                                                       width  : 570,
                                                       height : 300,
                                                       xtype  : 'textarea'
                                                   }
                                                   ,{
									                xtype      : 'radiogroup',
									                fieldLabel : 'Mostrar al agente',
									                columns    : 2,
									                width      : 250,
									                style      : 'margin:5px;',
									                hidden     : _GLOBAL_CDSISROL===RolSistema.Agente,
									                items      :
									                [
									                    {
									                        boxLabel   : 'Si',
									                        itemId     : 'SWAGENTE',
									                        name       : 'SWAGENTE',
									                        inputValue : 'S',
									                        checked    : _GLOBAL_CDSISROL===RolSistema.Agente
									                    }
									                    ,{
									                        boxLabel   : 'No',
									                        name       : 'SWAGENTE',
									                        inputValue : 'N',
                                                               checked    : _GLOBAL_CDSISROL!==RolSistema.Agente
									                    }
									                ]
									            }
                                               ]
                                               ,buttons    :
                                               [
                                                   {
                                                   	text    : 'Guardar como pendiente de informaci&oacute;n',
                                                       icon    : '${ctx}/resources/fam3icons/icons/clock.png',
                                                       handler : function()
                                                       {
                                                           var form=Ext.getCmp('formPanel');
                                                           var window=this.up().up();
                                                           //console.log(form.getValues());
                                                          
                                                               window.setLoading(true);
                                                               form.submit({
                                                                   params:{
                                                                       'map1.pv_cdunieco' :  inputCdunieco,
                                                                       'map1.pv_cdramo'   :  inputCdramo,
                                                                       'map1.pv_estado'   :  inputEstado,
                                                                       'map1.pv_nmpoliza' :  inputNmpoliza
                                                                   },
                                                                   success:function(){
                                                                       Ext.Ajax.request
                                                                       ({
                                                                           url     : datComUrlMCUpdateStatus
                                                                           ,params : 
                                                                           {
                                                                               'smap1.ntramite'  : inputNtramite,
                                                                               'smap1.status'    : '6',
                                                                               'smap1.comments'  : Ext.getCmp('inputTextareaCommentsToMCFromMedico').getValue(),
                                                                               'smap1.swagente'  : _fieldById('SWAGENTE').getGroupValue() 
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
                                                                                           'smap1.gridTitle'	  : 'Tareas',
                                                                                           'smap2.pv_cdtiptra_i' : 1,
                                                                                           'smap1.editable'	  : 1
                                                                                       }
                                                                                   });
                                                                               }
                                                                               else
                                                                               {
                                                                                   window.setLoading(false);
                                                                                   Ext.Msg.show({
                                                                                       title	: 'Error',
                                                                                       msg		: 'Error al guardar Vo. Bo.',
                                                                                       buttons	: Ext.Msg.OK,
                                                                                       icon	: Ext.Msg.ERROR
                                                                                   });
                                                                               }
                                                                           }
                                                                           ,failure : function()
                                                                           {
                                                                               window.setLoading(false);
                                                                               Ext.Msg.show({
                                                                                   title	: 'Error',
                                                                                   msg		: 'Error de comunicaci&oacute;n',
                                                                                   buttons	: Ext.Msg.OK,
                                                                                   icon	: Ext.Msg.ERROR
                                                                               });
                                                                           }
                                                                       });
                                                                   },
                                                                   failure:function(){
                                                                       window.setLoading(false);
                                                                       Ext.Msg.show({
                                                                           title	: 'Error',
                                                                           msg		: 'Error de comunicaci&oacute;n',
                                                                           buttons	: Ext.Msg.OK,
                                                                           icon	: Ext.Msg.ERROR
                                                                       });
                                                                   }
                                                               });
                                                           
                                                         
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
                                       } else {                                        	    
                                               Ext.Msg.show({                                                	
                                                   title	: 'Datos incompletos',
                                                   msg		: 'Favor de introducir todos los campos requeridos',
                                                   buttons	: Ext.Msg.OK,
                                                   icon	: Ext.Msg.WARNING
                                               });
                                         }
                                   }
                               },
	                        {
                                   text     : 'Rechazar'
                                   ,icon    : '${ctx}/resources/fam3icons/icons/cancel.png'
                                   ,hidden  : ((!sesionDsrol)||(sesionDsrol!='SUSCRIPTOR'&&sesionDsrol!='MEDICO'))//||!Ext.isEmpty(panDatComFlujo)
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
  		                                                    title       : 'Guardar detalle',
  		                                                    width       : 600,
  		                                                    height      : 430,
  		                                                    buttonAlign : 'center',
  		                                                    modal       : true,
  		                                                    closable    : false,
  		                                                    autoScroll  : true,
  		                                                    items       :
  		                                                    [
  		                                                        Ext.create('Ext.form.field.TextArea', {
  		                                                            id     : 'inputTextareaCommentsToRechazo',
  		                                                            width  : 570,
  		                                                            height : 200,
  		                                                            value  : txtContenido
  		                                                        }),
  		                                                     	Ext.create('Ext.form.field.TextArea', {
	                                                            id     : 'inputTextareaComments',
	                                                            width  : 570,
	                                                            height : 100
	                                                        })
	                                                        ,{
												                xtype      : 'radiogroup',
												                fieldLabel : 'Mostrar al agente',
												                columns    : 2,
												                width      : 250,
												                style      : 'margin:5px;',
												                hidden     : _GLOBAL_CDSISROL===RolSistema.Agente,
												                items      :
												                [
												                    {
												                        boxLabel   : 'Si',
												                        itemId     : 'SWAGENTE',
												                        name       : 'SWAGENTE',
												                        inputValue : 'S',
												                        checked    : _GLOBAL_CDSISROL===RolSistema.Agente
												                    }
												                    ,{
												                        boxLabel   : 'No',
												                        name       : 'SWAGENTE',
												                        inputValue : 'N',
                                                                           checked    : _GLOBAL_CDSISROL!==RolSistema.Agente
												                    }
												                ]
												            }
  		                                                    ]
  		                                                    ,buttons    :
  		                                                    [
  		                                                        {
  		                                                            text    : 'Rechazar',
  		                                                            icon    : '${ctx}/resources/fam3icons/icons/cancel.png',
  		                                                            handler : function()
  		                                                            {
  		                                                                if(true||form.isValid())
  		                                                                {
  		                                                                    var window=this.up().up();
  		                                                                    window.setLoading(true);
  		                                                                    
                                                                           Ext.Ajax.request
                                                                           ({
                                                                               url     : datComUrlMCUpdateStatus
                                                                               ,params : 
                                                                               {
                                                                                   'smap1.ntramite' : inputNtramite,
                                                                                   'smap1.status'  : '4',//rechazado
                                                                                   'smap1.comments' : Ext.getCmp('inputTextareaComments').getValue(),
                                                                                   'smap1.swagente' : _fieldById('SWAGENTE').getGroupValue()
                                                                               }
                                                                               ,success : function(response)
                                                                               {
                                                                                   var json=Ext.decode(response.responseText);
                                                                                   if(json.success==true)
                                                                                   {
                                                                                       Ext.Ajax.request(
                                                                                       {
                                                                                           url     : compleUrlGuardarCartoRechazo,
                                                                                        	method  : 'GET',
                                                                                           params  :
                                                                                           {
                                                                                               'map1.ntramite' : inputNtramite,
                                                                                               'map1.comments' : Ext.getCmp('inputTextareaCommentsToRechazo').getValue(),
                                                                                               'map1.cdsisrol' : sesionDsrol,
                                                                                               'map1.cdunieco' : inputCdunieco,
                                                                                               'map1.cdramo'   : inputCdramo,
                                                                                               'map1.estado'   : inputEstado,
                                                                                               'map1.nmpoliza' : inputNmpoliza
                                                                                           }
	                                                                                    ,success : function(response)
   		                                                                                {
	                                                                                    	Ext.create('Ext.form.Panel').submit
	                                                                                        ({
	                                                                                            url            : datComUrlMC,
	                                                                                            standardSubmit : true,
	                                                                                            params         :
	                                                                                            {
	                                                                                                'smap1.gridTitle'		: 'Tareas',
	                                                                                                'smap2.pv_cdtiptra_i'	: 1,
	                                                                                                'smap1.editable'		: 1
	                                                                                            }
	                                                                                        });
   		                                                                                }
		                                                                                 ,failure : function()
		                                                                                {
		                                                                                    Ext.Msg.show({
		                                                                                        title	: 'Error',
		                                                                                        msg		: 'Error de comunicaci&oacute;n',
		                                                                                        buttons	: Ext.Msg.OK,
		                                                                                        icon	: Ext.Msg.ERROR
		                                                                                    });
		                                                                                }
                                                                                       });
                                                                                   }else{
                                                                                       window.setLoading(false);
                                                                                       Ext.Msg.show({
                                                                                           title	: 'Error',
                                                                                           msg		: 'Error al rechazar',
                                                                                           buttons	: Ext.Msg.OK,
                                                                                           icon	: Ext.Msg.ERROR
                                                                                       });
                                                                                   }
                                                                               }
                                                                               ,failure : function()
                                                                               {
                                                                                   Ext.Msg.show({
                                                                                       title	: 'Error',
                                                                                       msg		: 'Error de comunicaci&oacute;n',
                                                                                       buttons	: Ext.Msg.OK,
                                                                                       icon	: Ext.Msg.ERROR
                                                                                   });
                                                                               }
                                                                           });
  		                                                                }
  		                                                                else
  		                                                                {
  		                                                                    Ext.Msg.show({
  		                                                                        title	: 'Datos incompletos',
  		                                                                        msg		: 'Favor de introducir todos los campos requeridos',
  		                                                                        buttons	: Ext.Msg.OK,
  		                                                                        icon	: Ext.Msg.WARNING
  		                                                                    });
  		                                                                }
  		                                                            }
  		                                                        }
  		                                                        ,{
  		                                                            text  	: 'Cancelar',
  		                                                            icon 	: '${ctx}/resources/fam3icons/icons/cancel.png',
  		                                                            handler : function()
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
       										            title	: 'Error',
       										            msg		: 'Error de comunicaci&oacute;n',
       										            buttons	: Ext.Msg.OK,
       										            icon	: Ext.Msg.ERROR
       										        });
       										    }
       										});
                          				    },
                          				    failure : function ()
                          				    {
                          				        Ext.Msg.show({
                          				            title	: 'Error',
                          				            msg		: 'Error de comunicaci&oacute;n',
                          				            buttons	: Ext.Msg.OK,
                          				            icon	: Ext.Msg.ERROR
                          				        });
                          				    }
                          				});

                                   }
                               }				                	 
		            		]
					})
    			]
			})
		]
    });    
    ////// componentes //////
    
    ////// contenido //////
    
    Ext.create('Ext.panel.Panel',
    {
        renderTo  : '_p25_divpri'
        ,itemId   : 'formBusqueda'
        ,defaults : { style : 'margin : 5px;' }
        ,border   : 0
        ,items    :
        [
            Ext.create('Ext.form.Panel',
            {
                title     : 'Buscar p&oacute;lizas a renovar'
                ,itemId   : '_p25_busquedaForm'
                ,defaults : { style : 'margin :5px;' }
                ,layout   :
                {
                    type     : 'table'
                    ,columns : 3
                }
                ,items       : itemsFormularioBusqueda
                ,buttonAlign : 'center'
                ,buttons     :
                [
                    {
                        text     : 'Buscar'
                        ,icon    : '${ctx}/resources/fam3icons/icons/zoom.png'
                        ,handler : _p25_buscarClic
                    },
                    {
                        text     : 'Limpiar'
                        ,icon    : '${ctx}/resources/fam3icons/icons/control_repeat.png'
                        ,handler : _p25_limpiarFiltros
                    }
                ]
                ,listeners	:	{
                	click	:	function(options) {
                		debug('click panel');
                		}
                }
            })
            ,Ext.create('Ext.form.Panel',
            {
                title        : 'Contratante'
                ,itemId      : '_p25_contratante'
                ,defaults : { style : 'margin :5px;' }
                ,autoScroll	 : true
                ,hidden		 : 'true'
                ,layout   :
                {
                    type     : 'table'
                    ,columns : 3
                }
                ,items    :	itemsFormularioContratante
            })
            ,Ext.create('Ext.grid.Panel',
            {
                title        : 'Informacion de poliza'
                ,itemId      : '_p25_poliza'
                ,selType     : 'checkboxmodel'
                ,store       : _p25_storePolizas                
                ,minHeight   : 200
                ,maxHeight   : 400
                ,columns     : itemsFormularioPolizaColumns
                //,viewConfig  : viewConfigAutoSize
                ,buttonAlign : 'center'
                ,hidden		 : 'true'
                ,autoScroll	 : true
                ,buttons     :
                [
                    {
                        text      : 'Renovar'
                        ,itemId   : '_p25_polizaBotonRenovar'
                        ,icon     : '${ctx}/resources/fam3icons/icons/date_add.png'
                        ,handler  : _p25_renovarPolizaClic
                        ,disabled : true
                    }
                ]
            })
            ,Ext.create('Ext.grid.Panel',
            {
                title        : 'Resultados'
                ,itemId      : '_p25_grid'
                ,selType     : 'checkboxmodel'
                ,store       : _p25_storePolizasMasivas
                ,minHeight   : 200
                ,maxHeight   : 400
                ,columns     : gridColumns
                ,viewConfig  : viewConfigAutoSize
                ,buttonAlign : 'center'
                ,hidden		 : 'true'
                ,autoScroll	 : true
                ,buttons     :
                [
                    {
                        text      : 'Renovar'
                        ,itemId   : '_p25_gridBotonRenovar'
                        ,icon     : '${ctx}/resources/fam3icons/icons/date_add.png'
                        ,handler  : _p25_renovarClic
                        ,disabled : true
                    }
                ]
            })
        ]
    });
    ////// contenido //////
    
    ////// custom //////
    var form = _fieldById('_p25_busquedaForm');
    _fieldByName('tipo',form).getStore().on(
  	{
  		load : function(me)
	    {
  		    _fieldByName('tipo',form).setValue('AS');
  		}
  	});
  	
  	_fieldByName('tipo',form).on(
  	{
  		change: function(me, newValue) {
  				if(me.getValue() == 'AS' || me.getValue() == 'SA'){
  					_fieldByName('fecini',form).hide();
  					_fieldByName('fecfin',form).hide();
  					_fieldByName('status',form).hide();
  					_fieldByName('cdunieco',form).show();
  					_fieldByName('cdramo',form).show();
  					_fieldByName('nmpoliza',form).show();
  					_fieldByName('cdtipsit',form).hide();
  					_fieldByName('retenedora',form).hide();
  					_fieldByName('cdperson',form).hide();
  					_fieldById('_p25_contratante').hide();
  					_fieldById('_p25_poliza').hide();
  					_fieldById('_p25_grid').hide();
  					form.doLayout();
  				}else if(me.getValue() == 'P'){
  					_fieldByName('fecini',form).show();
  					_fieldByName('fecfin',form).show();
  					_fieldByName('status',form).show();
  					_fieldByName('cdunieco',form).show();
  					_fieldByName('cdramo',form).show();
  					_fieldByName('nmpoliza',form).show();
  					_fieldByName('cdtipsit',form).hide();
  					_fieldByName('retenedora',form).hide();
  					_fieldByName('cdperson',form).hide();
  					_fieldById('_p25_contratante').hide();
  					_fieldById('_p25_poliza').hide();
  					_fieldById('_p25_grid').hide();
  					form.doLayout();
  				}else if(me.getValue() == 'M'){
  					_fieldByName('fecini',form).show();
  					_fieldByName('fecfin',form).show();
  					_fieldByName('status',form).hide();
  					_fieldByName('cdunieco',form).show();
  					_fieldByName('cdramo',form).show();
  					_fieldByName('nmpoliza',form).hide();
  					_fieldByName('cdtipsit',form).show();
  					_fieldByName('retenedora',form).show();
  					_fieldByName('cdperson',form).show();
  					_fieldById('_p25_contratante').hide();
  					_fieldById('_p25_poliza').hide();
  					_fieldById('_p25_grid').hide();
  					form.doLayout();
  				}
  			}
  	});
  	
  	_fieldByName('cdunieco',form).on(
  	{
  		select : function(){
  			debug('> Cambiando cdperson');
  			var cdperson = _fieldByName('cdperson',form);
  			cdperson.store.proxy.extraParams['params.cdunieco'] = _fieldByName('cdunieco',form).getValue();
  			cdperson.store.proxy.extraParams['params.cdramo']   = _fieldByName('cdramo',form).getValue(); 
  			debug(cdperson.store.extraParams);
			debug('< Cambiando cdperson');  															
  		}
  	});
    
	_fieldByName('cdramo',form).on(
  	{
  		select : function(){
  			debug('> Cambiando cdperson');
  			var cdperson = _fieldByName('cdperson',form);
  			cdperson.store.proxy.extraParams['params.cdunieco'] = _fieldByName('cdunieco',form).getValue();
  			cdperson.store.proxy.extraParams['params.cdramo']   = _fieldByName('cdramo',form).getValue(); 
 			debug(cdperson.store.extraParams);
			debug('< Cambiando cdperson');  															
  		}
  	});
  	
    _fieldById('_p25_grid').getSelectionModel().on(
    {
        selectionChange : _p25_gridSelectionChange
    });
    
    _fieldById('_p25_poliza').getSelectionModel().on(
    {
        selectionChange : _p25_polizaSelectionChange
    });
    
     /*_fieldById('_p25_clientePanel').loader.load(
     {
     	params:
        	{
            	'smap1.cdperson' 			: _fieldByName('cdperson',form).getValue(),
                'smap1.cdideper' 			: '',	//json.smap1.cdideper,
                'smap1.cdideext' 			: '',	//json.smap1.cdideext,
                'smap1.esSaludDanios' 		: 'S',
                'smap1.polizaEnEmision'		: 'S',
                'smap1.esCargaClienteNvo' 	: 'N',	//(Ext.isEmpty(json.smap1.cdperson)? 'S' : 'N' ),
                'smap1.cargaCP' 			: '',	//json.smap1.cdpostal,
                'smap1.cargaTipoPersona' 	: '',	//json.smap1.otfisjur,
                'smap1.cargaSucursalEmi' 	: _fieldByName('cdunieco',form).getValue(),
	            'smap1.cargaFenacMin' 		: '',	//_aplicaCobVida?_FechaMinEdad:'',
	            'smap1.cargaFenacMax' 		: '',	//_aplicaCobVida?_FechaMaxEdad:'',
	            'smap1.tomarUnDomicilio' 	: 'S',
	            'smap1.cargaOrdDomicilio' 	: ''   	//json.smap1.nmorddom
		}
     });*/
    ////// custom //////
    
    ////// loaders //////
    ////// loaders //////
});

////// funciones //////
function _p25_buscarClic(button,e)
{
    debug('>_p25_buscarClic');
    _fieldById('_p25_contratante').hide();
  	_fieldById('_p25_poliza').hide();
  	_fieldById('_p25_grid').hide();
    var form = button.up('form');
    var tipo = _fieldByName('tipo'     , form);
    debug('tipo ',tipo.getValue());
    if (tipo.getValue() == 'AS' || tipo.getValue() == 'SA'){
    //AS && SA
    	debug('entro por AS & SA');
    	_p25_ultimosParams =
    	{
    		'smap1.cdunieco' 	: _fieldByName('cdunieco' 	, form).getValue()
        	,'smap1.cdramo'   	: _fieldByName('cdramo'   	, form).getValue()
        	,'smap1.estado'   	: 'M'
        	,'smap1.nmpoliza' 	: _fieldByName('nmpoliza' 	, form).getValue()       
    	};
    	_mask('Obteniendo datos de poliza');
    	_p25_storePolizas.load({
    		params    :	_p25_ultimosParams,
        	callback :  function(records, op, success)
        	{
            	debug('entro a callback');
            	debug('op',op);
            	if(success)
            	{
            		debug('records',records);
                	if(records.length==0)
                	{
                    	mensajeWarning('Poliza no existe');
                    	_unmask();
                	}else{
                		_p25_storeContratante(records, op, success);
                		var tipo = _fieldByName('tipo');
                		_p25_selectionCharge(tipo);
                		_unmask();
                	}
            	}
            	else
            	{
                	mensajeError(op.getError());
            	}
        	}
    	});
    	debug(_p25_ultimosParams);
    	debug('sale de AS & SA');
    }else if (tipo.getValue() == 'M'){
    //M
    	debug('Entra a M');
    	_p25_ultimosParams =
    	{
    		'smap1.fecini'		: Ext.Date.format(_fieldByName('fecini'		, form).getValue(), "d-M-Y")
    		,'smap1.fecfin'		: Ext.Date.format(_fieldByName('fecfin'		, form).getValue(), "d-M-Y")
    		,'smap1.cdunieco' 	: _fieldByName('cdunieco' 	, form).getValue()
        	,'smap1.cdramo'   	: _fieldByName('cdramo'   	, form).getValue()
        	,'smap1.estado'   	: 'M'
        	,'smap1.cdtipsit'	: _fieldByName('cdtipsit'	, form).getValue()
        	,'smap1.retenedora'	: _fieldByName('retenedora'	, form).getValue()
        	,'smap1.cdperson'	: _fieldByName('cdperson'	, form).getValue()
    	};
    	_p25_storePolizasMasivas.load({
    		params    :	_p25_ultimosParams,
    		callback :  function(records, op, success)
        	{
            	debug('entro a callback');
            	if(success)
            	{
                	if(records.length==0)
                	{
                    	mensajeWarning('No hay resultados');
                	}else{
                		_p25_selectionCharge(tipo);
                	}
            	}
            	else
            	{
                	mensajeError(op.getError());
            	}
        	}
    	});    	
    	debug('_p25_ultimosParams',_p25_ultimosParams);
    	debug('Sale de M');
    }else if (tipo.getValue() == 'P'){
    //P
    	debug('Entra a P');
    	_p25_ultimosParams =
    	{
    		'smap1.fecini'		: Ext.Date.format(_fieldByName('fecini'		, form).getValue(), "d-M-Y")
    		,'smap1.fecfin'		: Ext.Date.format(_fieldByName('fecfin'		, form).getValue(), "d-M-Y")
    		,'smap1.status'		: _fieldByName('status'		, form).getValue()
    		,'smap1.cdunieco' 	: _fieldByName('cdunieco' 	, form).getValue()
        	,'smap1.cdramo'   	: _fieldByName('cdramo'   	, form).getValue()
        	,'smap1.estado'   	: 'M'
        	,'smap1.nmpoliza' 	: _fieldByName('nmpoliza' 	, form).getValue()
    	};
    	_p25_storePolizasMasivas.load({
    		params    :	_p25_ultimosParams,
    		callback :  function(records, op, success)
        	{
            	debug('entro a callback');
            	if(success)
            	{
                	if(records.length==0)
                	{
                    	mensajeWarning('No hay resultados');
                	}else{
                		_p25_selectionCharge(tipo);
                	}
            	}
            	else
            	{
                	mensajeError(op.getError());
            	}
        	}
    	});
    	debug('_p25_ultimosParams',_p25_ultimosParams);
    	debug('Sale de P');
    }
    debug('<_p25_buscarClic');
}

function _p25_storeContratante(records, op, success){
debug('>_p25_storeContratante');
	if(success && records.length > 0){
		_fieldById('_p25_contratante').loadRecord(new _p25_modeloContratante(records[0].raw));
	}
debug('<_p25_storeContratante');
}

function _p25_renovarPolizaClic(button,e)
{
    debug('>_p25_renovarPolizaClic');
    var pol = _fieldById('_p25_poliza').store.data.items[0].raw;
    if(pol['renovada'] == 'NO'){
    	/*var form = _fieldById('_p25_busquedaForm');
    	if(_fieldByName('tipo',form).getValue() == 'AS'){
    		var resRenova = { nmtramite : '25231', cdperson : '501321'};
    		_p25_ventanaAutoServicio(resRenova);
    	}
    	else{
    		_p25_ventanaServicioAsistido();
    	}*/
    	//else{
    		_mask('Renovando poliza');
    		Ext.Ajax.request(
    		{
        		url       : _p25_urlRenovarPolizaIndividual,
        		params     : 
        		{
        			'params.cdunieco'  : pol['cdunieco'],
        			'params.cdramo'    : pol['cdramo'],
        			'params.estado'    : 'M',
        			'params.nmpoliza'  : pol['nmpoliza']
        		},
        		success  : function(response)
        		{
            		var resp = Ext.decode(response.responseText);
            		//debug("resp ",resp.slist1[0]['ntramite']);
            		var ntramite = resp.slist1[0]['ntramite'];
            		debug('resp',ntramite);
            		if(!Ext.isEmpty(ntramite))
            		{
                		_unmask();
                		mensajeCorrecto('Proceso completo','Se creo el tramite '+ntramite);
                		var form = _fieldById('_p25_busquedaForm');
                    	if(_fieldByName('tipo',form).getValue() == 'AS'){
                    		var resRenova = resp.slist1[0];//{ nmtramite : '25231', cdperson : '501321'};
                    		_p25_ventanaAutoServicio(resRenova);
                    	}
                    	else{
                    		_p25_ventanaServicioAsistido();
                    	}
                		_fieldById('_p25_contratante').hide();
  						_fieldById('_p25_poliza').hide();
  						_fieldById('_p25_grid').hide();
            		}
            		else
            		{
            			_unmask();
                		mensajeError(resp.respuesta);
            		}
        		},
        		failure  : function()
        		{
            		errorComunicacion();
        		}
    		});
    	//}
    }
    else{
    	mensajeError('La poliza ya esta renovada');
    }
    debug('<_p25_renovarPolizaClic');
}

function _p25_renovarClic(button,e)
{
    debug('>_p25_renovarClic');
    var json   = {};
    var slist1 = [];
    var arr    = _fieldById('_p25_grid').getSelectionModel().getSelection();
    Ext.Array.each(arr,function(record)
    {
        var val=record.raw;
        val['cducreno']=record.get('cducreno');
        slist1.push(val);
    });
    json['slist1'] = slist1;
    //json['smap1']  = _p25_ultimosParams;
    debug('### renovar json params:',json);
    _fieldById('_p25_grid').setLoading(true);
    Ext.Ajax.request(
    {
        url       : _p25_urlRenovarPolizas
        ,jsonData : json
        ,success  : function(response)
        {
            _fieldById('_p25_grid').setLoading(false);
            var resp = Ext.decode(response.responseText);
            debug('### renovar json response:',resp);
            if(resp.exito)
            {
                _fieldById('_p25_grid').getStore().removeAll();
                mensajeCorrecto('Proceso completo',resp.respuesta);
            }
            else
            {
                mensajeError(resp.respuesta);
            }
        }
        ,failure  : function()
        {
            _fieldById('_p25_grid').setLoading(false);
            errorComunicacion();
        }
    });
    debug('<_p25_renovarClic');
}

function _p25_gridSelectionChange(selModel,selected,e)
{
    debug('>_p25_gridSelectionChange selected.length:',selected.length);
    _fieldById('_p25_gridBotonRenovar').setDisabled(selected.length==0);
}

function _p25_polizaSelectionChange(selModel,selected,e)
{
    debug('>_p25_polizaSelectionChange selected.length:',selected.length);
    _fieldById('_p25_polizaBotonRenovar').setDisabled(selected.length==0);
}

function _p25_selectionCharge(field){
debug('>_p25_selectionCharge');
	if(field.getValue() == 'AS' || field.getValue() == 'SA'){
  		_fieldById('_p25_contratante').show();
  		_fieldById('_p25_poliza').show();
  		_fieldById('_p25_grid').hide();
  	}else if(field.getValue() == 'P'){
  		_fieldById('_p25_contratante').hide();
  		_fieldById('_p25_poliza').hide();
  		_fieldById('_p25_grid').show();
  	}else if(field.getValue() == 'M'){
  		_fieldById('_p25_contratante').hide();
  		_fieldById('_p25_poliza').hide();
  		_fieldById('_p25_grid').show();
  	}
debug('<_p25_selectionCharge');
}

function _p25_limpiarFiltros(button,e)
{
    debug('>_p25_limpiarFiltros');
    var form = button.up('form');
    var cdperson = _fieldByName('cdperson',form);
  	cdperson.store.proxy.extraParams['params.cdunieco'] = '';
  	cdperson.store.proxy.extraParams['params.cdramo']   = ''; 
    _fieldByName('fecini',form).setValue('');
  	_fieldByName('fecfin',form).setValue('');
  	_fieldByName('status',form).setValue('');
  	_fieldByName('cdunieco',form).setValue('');
  	_fieldByName('cdramo',form).setValue('');
  	_fieldByName('nmpoliza',form).setValue('');
  	_fieldByName('cdtipsit',form).setValue('');
  	_fieldByName('retenedora',form).setValue('');
  	_fieldByName('cdperson',form).setValue('');
    debug('<_p25_limpiarFiltros');
}

function _p25_ventanaAutoServicio(resRenova){
	debug('>_p25_ventanaAutoServicio');
	debug(resRenova);
	winAutoServicio.show();
	winAutoServicio.resRenova = resRenova;	
	debug('<_p25_ventanaAutoServicio');
}

function _p25_ventanaServicioAsistido(){
	debug('>_p25_ventanaServicioAsistido');
	winServicioAsistido.show();	
	debug('<_p25_ventanaServicioAsistido');
}

function _p25_ventanaCambioFormaPago(resRenova){
	debug('>_p25_ventanaCambioFormaPago');
	//winCambioPago.show();
	_fieldById('winCambioPago').resRenova = resRenova;
	wineditarContratante.show();
	alert(resRenova['cdperson']);
	_fieldById('_p25_clientePanel').getLoader().load({params : { 'smap1.cdperson' : resRenova['cdperson']}});
	//_fieldById('_p25_clientePanel').getLoader().load();	
	debug('<_p25_ventanaCambioFormaPago');
}

function _p25_ventanaCambioDomicilio(){
	debug('>_p25_ventanaCambioDomicilio');
    winCambioDomicilio.show();
	debug('<_p25_ventanaCambioDomicilio');
}

function _p29_guardarComplementario(callback){
	debug('>_p29_guardarComplementario');
	
    /*var form=Ext.getCmp('formPanel');
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
                centrarVentanaInterna(Ext.Msg.show({
                    title    : 'Cambios guardados'
                    ,msg     : 'Sus cambios han sido guardados'
                    ,buttons : Ext.Msg.OK
                    ,fn      : function()
                    {
                    	 if(!Ext.isEmpty(callback))
                         {
                             callback();
                         }
                    }
                }));
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
    }*/
    debug('<_p29_guardarComplementario');
}
////// funciones //////
</script>
</head>
<body><div id="_p25_divpri" style="height:600px;"></div></body>
</html>