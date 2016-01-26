<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<script>
///////////////////////
////// overrides //////
/*///////////////////*/
Ext.override(Ext.form.TextField,
{
    initComponent:function()
    {
        Ext.apply(this,
        {
        	labelWidth : 250
        });
        return this.callParent();
    }
});
/*///////////////////*/
////// overrides //////
///////////////////////

///////////////////////
////// variables //////
/*///////////////////*/

//Obtenemos el contenido en formato JSON de la propiedad solicitada:
var _0_smap1      = <s:property value="%{convertToJSON('smap1')}" escapeHtml="false" />;

var _0_flujo      = <s:property value="%{convertToJSON('flujo')}" escapeHtml="false" />;

var _0_reporteCotizacion = '<s:text name='%{"rdf.cotizacion.nombre."+smap1.cdtipsit.toUpperCase()}' />';
var _0_urlImprimirCotiza = '<s:text name="ruta.servidor.reports" />';
var _0_reportsServerUser = '<s:text name="pass.servidor.reports" />';

var _0_urlCotizar                  = '<s:url namespace="/emision"         action="cotizar"                        />';
var _0_urlCotizarExterno           = '<s:url namespace="/externo"         action="cotizar"                        />';
var _0_urlDetalleCotizacion        = '<s:url namespace="/"                action="detalleCotizacion"              />';
var _0_urlCoberturas               = '<s:url namespace="/flujocotizacion" action="obtenerCoberturas4"             />';
var _0_urlDetalleCobertura         = '<s:url namespace="/flujocotizacion" action="obtenerAyudaCoberturas4"        />';
var _0_urlEnviarCorreo             = '<s:url namespace="/general"         action="enviaCorreo"                    />';
var _0_urlViewDoc                  = '<s:url namespace ="/documentos"     action="descargaDocInline"              />';
var _0_urlComprar                  = '<s:url namespace="/flujocotizacion" action="comprarCotizacion4"             />';
var _0_urlVentanaDocumentos        = '<s:url namespace="/documentos"      action="ventanaDocumentosPoliza"        />';
var _0_urlDatosComplementarios     = '<s:url namespace="/"                action="datosComplementarios"           />';
var _0_urlUpdateStatus             = '<s:url namespace="/mesacontrol"     action="actualizarStatusTramite"        />';
var _0_urlMesaControl              = '<s:url namespace="/mesacontrol"     action="mcdinamica"                     />';
var _0_urlLoad                     = '<s:url namespace="/emision"         action="cargarCotizacion"               />';
var _0_urlNada                     = '<s:url namespace="/emision"         action="webServiceNada"                 />';
var _0_urlCargarCduniecoAgenteAuto = '<s:url namespace="/emision"         action="cargarCduniecoAgenteAuto"       />';
var _0_urlRecuperarCliente         = '<s:url namespace="/"                action="buscarPersonasRepetidas"        />';
var _0_urlCargarAgentePorFolio     = '<s:url namespace="/emision"         action="cargarCdagentePorFolio"         />';
var _0_urlObtenerParametros        = '<s:url namespace="/emision"         action="obtenerParametrosCotizacion"    />';
var _0_urlCargarAutoPorClaveGS     = '<s:url namespace="/emision"         action="cargarAutoPorClaveGS"           />';
var _0_urlCargarSumaAsegurada      = '<s:url namespace="/emision"         action="cargarSumaAseguradaAuto"        />';
var _0_urlObtenerCliente           = '<s:url namespace="/emision"         action="cargarClienteCotizacion"        />';
var _0_urlValidarCambioZonaGMI     = '<s:url namespace="/emision"         action="validarCambioZonaGMI"           />';
var _0_urlValidarEnfermCatasGMI    = '<s:url namespace="/emision"         action="validarEnfermedadCatastGMI"     />';
var _0_urlRecuperacionSimple       = '<s:url namespace="/emision"         action="recuperacionSimple"             />';
var _0_urlRetroactividadDifer      = '<s:url namespace="/emision"         action="cargarRetroactividadSuplemento" />';
var _0_urlObtieneValNumeroSerie    = '<s:url namespace="/emision" 		  action="obtieneValNumeroSerie"          />';
var _0_modeloExtraFields = [
<s:if test='%{getImap().get("modeloExtraFields")!=null}'>
    <s:property value="imap.modeloExtraFields" />
</s:if>
];

var _0_necesitoIncisos = true;
<s:if test='%{getImap().get("fieldsIndividuales")==null}'>
    _0_necesitoIncisos = false;
</s:if>
_0_smap1.conincisos=_0_necesitoIncisos?'si':'no';
debug('_0_necesitoIncisos:',_0_necesitoIncisos);

if(!Ext.isEmpty(_0_flujo))
{
    <s:url namespace="/flujomesacontrol" action="mesaControl" var="urlMesaFlujo" includeParams="get">
        <s:param name="params.AGRUPAMC" value="%{'PRINCIPAL'}" />
    </s:url>
    _0_urlMesaControl = '<s:property value="urlMesaFlujo" />';
    debug('_0_urlMesaControl:',_0_urlMesaControl);
}

var _0_panelPri;
var _0_formAgrupados;
var _0_formAvisos;
var _0_gridIncisos;
var _0_botonera;
var _0_storeIncisos;
var _0_gridTarifas;
var _0_botCotizar;
var _0_botCargar;
var _0_botLimpiar;
var _0_fieldNtramite;
var _0_fieldNmpoliza;
var _0_selectedCdplan;
var _0_selectedDsplan;
var _0_selectedCdperpag;
var _0_selectedNmsituac;
var _0_gridCoberturas;
var _0_storeCoberturas;
var _0_windowCoberturas;
var _0_botDetalleCobertura;
var _0_windowAyudaCobertura;
var _0_selectedIdcobertura;
var _0_recordClienteRecuperado;
var _0_semaforoAux;

var _0_validacion_custom;

var _parentescoTitular = 'T';

debug('_0_smap1: ',_0_smap1);

var image = Ext.create('Ext.Img', {
	src: '../../images/confpantallas/icon/paneles.png',rowspan: 2
});

var _CONTEXT = "${ctx}";

//parche para RAMO 16 (FRONTERIZOS) con rol distinto de SUSCRIPTOR AUTO, se oculta el botón Detalle:
var ocultarDetalleCotizacion = false; 
if(_0_smap1.cdramo == Ramo.AutosFronterizos && _0_smap1.cdsisrol != 'SUSCRIAUTO') {
    ocultarDetalleCotizacion = true;
}

var _0_rowEditing = Ext.create('Ext.grid.plugin.RowEditing',{
	clicksToEdit : 1,
	errorSummary : true,
	listeners: {
		beforeedit: function(){
			_0_botCotizar.disable();
		},
		edit: function(){
			_0_botCotizar.enable();
		},
		canceledit: function(){
			_0_botCotizar.enable();
		}
	}
});

/*///////////////////*/
////// variables //////
///////////////////////

///////////////////////
////// funciones //////
/*///////////////////*/
function _0_cargarNumPasajerosAuto()
{
    Ext.Ajax.request(
    {
        url      : _0_urlCargarAutoPorClaveGS
        ,params  :
        {
            'smap1.cdramo'    : _0_smap1.cdramo
            ,'smap1.clavegs'  : _fieldByName('parametros.pv_otvalor22').getValue()
            ,'smap1.cdtipsit' : _0_smap1.cdtipsit
            ,'smap1.tipounidad' : _fieldByLabel('TIPO DE UNIDAD').getValue()
        }
        ,success : function(response)
        {
            var ijson=Ext.decode(response.responseText);
            debug('### obtener auto por clave gs:',ijson);
            if(ijson.exito)
            {
                _fieldByName('parametros.pv_otvalor06').setValue(ijson.smap1.NUMPASAJEROS);
                _fieldByName('parametros.pv_otvalor06').setMinValue(ijson.smap1.PASAJMIN);
                _fieldByName('parametros.pv_otvalor06').setMaxValue(ijson.smap1.PASAJMAX);
                _fieldByName('parametros.pv_otvalor06').isValid();
            }
            else
            {
                mensajeWarning(ijson.respuesta);
            }
        }
        ,failure : function()
        {
            errorComunicacion();
        }
    });
}

function _0_obtenerClaveGSPorAuto()
{
    _fieldByName('parametros.pv_otvalor22').getStore().load(
    {
        params :
        {
            'params.substr' : _fieldByLabel('VERSION').getValue()
        }
        ,callback : function(records)
        {
            debug('callback records:',records);
            var valor=_fieldByLabel('VERSION').getValue()
                +' - '+_fieldByLabel('TIPO DE UNIDAD').findRecord('key',_fieldByLabel('TIPO DE UNIDAD').getValue()).get('value')
                +' - '+_fieldByLabel('MARCA').findRecord('key',_fieldByLabel('MARCA').getValue()).get('value')
                +' - '+_fieldByLabel('SUBMARCA').findRecord('key',_fieldByLabel('SUBMARCA').getValue()).get('value')
                +' - '+_fieldByLabel('MODELO').findRecord('key',_fieldByLabel('MODELO').getValue()).get('value')
                +' - '+_fieldByLabel('VERSION').findRecord('key',_fieldByLabel('VERSION').getValue()).get('value');
            debug('valor para el auto:',valor);
            _fieldByName('parametros.pv_otvalor22').setValue(
                _fieldByName('parametros.pv_otvalor22').findRecord('value',valor)
            );
            _0_cargarNumPasajerosAuto();
        }
    });
}

function _0_obtenerSumaAseguradaRamo6(mostrarError,respetarValue)
{
    _0_panelPri.setLoading(true);
    Ext.Ajax.request(
    {
        url      : _0_urlCargarSumaAsegurada
        ,params  :
        {
            'smap1.modelo'    : _fieldByName('parametros.pv_otvalor04').getValue()
                                     .substr(_fieldByName('parametros.pv_otvalor04').getValue().length-4,4)
            ,'smap1.version'  : _fieldByName('parametros.pv_otvalor05').getValue()
            ,'smap1.cdsisrol' : _0_smap1.cdsisrol
            ,'smap1.cdramo'   : _0_smap1.cdramo
            ,'smap1.cdtipsit' : _0_smap1.cdtipsit
        }
        ,success : function(response)
        {
            _0_panelPri.setLoading(false);
            var json=Ext.decode(response.responseText);
            debug('### json response obtener suma asegurada:',json);
            if(json.exito)
            {
                if(Ext.isEmpty(respetarValue)||respetarValue==false)
                {
                    _fieldByName('parametros.pv_otvalor25').setValue(json.smap1.SUMASEG);
                }
                else
                {
                    debug('SE RESPETA VALUE de VALOR COMERCIAL');
                }
                _fieldByName('parametros.pv_otvalor25').setMinValue((json.smap1.SUMASEG-0)*(1-(json.smap1.FACREDUC-0)));
                _fieldByName('parametros.pv_otvalor25').setMaxValue((json.smap1.SUMASEG-0)*(1+(json.smap1.FACINCREM-0)));
                _fieldByName('parametros.pv_otvalor25').isValid();
            }
            else if(mostrarError==true)
            {
                mensajeWarning(json.respuesta);
            }
        }
        ,failure : function()
        {
            _0_panelPri.setLoading(false);
            errorComunicacion();
        }
    });
}

function _0_funcionFechaChange(field,value)
{
    try
    {
        Ext.getCmp('fechaFinVigencia').setValue(Ext.Date.add(value,Ext.Date.YEAR,1));
    }
    catch (e) {}
}

function _0_comprar()
{
	debug('comprar');
	_0_panelPri.setLoading(true);
	var nombreTitular = '';
	
	Ext.Ajax.request(
	{
        url      : _0_urlComprar
        ,params  :
        {
            comprarNmpoliza        : _0_fieldNmpoliza.getValue()
            ,comprarCdplan         : _0_selectedCdplan
            ,comprarCdperpag       : _0_selectedCdperpag
            ,comprarCdramo         : _0_smap1.cdramo
            ,comprarCdciaaguradora : '20'
            ,comprarCdunieco       : _0_smap1.cdunieco
            ,cdtipsit              : _0_smap1.cdtipsit
            ,'smap1.fechaInicio'   : Ext.Date.format(Ext.getCmp('fechaInicioVigencia').getValue(),'d/m/Y')
            ,'smap1.fechaFin'      : Ext.Date.format(Ext.getCmp('fechaFinVigencia').getValue(),'d/m/Y')
            ,'smap1.ntramite'      : _0_smap1.ntramite
            ,'smap1.cdpersonCli'   : Ext.isEmpty(_0_recordClienteRecuperado) ? '' : _0_recordClienteRecuperado.get('CLAVECLI')
            ,'smap1.cdideperCli'   : Ext.isEmpty(_0_recordClienteRecuperado) ? '' : _0_recordClienteRecuperado.raw.CDIDEPER
            ,'smap1.cdagenteExt'   : (_0_smap1.cdramo == '6' || _0_smap1.cdramo == '16') ? _fieldByLabel('AGENTE').getValue() : ''
        }
	    ,success : function(response,opts)
	    {
	    	_0_panelPri.setLoading(false);
            var json = Ext.decode(response.responseText);
            if (json.exito)
            {
            	Ext.getCmp('_0_botComprarId').hide();
            	Ext.getCmp('_0_botDetallesId').hide();
            	Ext.getCmp('_0_botCoberturasId').hide();
            	Ext.getCmp('_0_botEditarId').hide();
            	Ext.getCmp('_0_botMailId').hide();
            	Ext.getCmp('_0_botImprimirId').hide();
                //window.parent.scrollTo(0, 0);//ELIMINADA
                
                debug("mostrar documentos");
                
                var ntramite = json.comprarNmpoliza;
                debug("ntramite",ntramite);
                if (!(_0_smap1.ntramite&&_0_smap1.ntramite>0))
                {
                    centrarVentanaInterna(
                        Ext.create('Ext.window.Window',
                        {
                        	width        : 600
                        	,height      : 400
                        	,title       : 'Subir documentos de tu tr&aacute;mite'
                        	,closable    : false
                        	,modal       : true
                        	,buttonAlign : 'center'
                        	,loadingMask : true
                        	,loader      :
                        	{
                        		url       : _0_urlVentanaDocumentos
                        		,scripts  : true
                        		,autoLoad : true
                        		,params   :
                        		{
                        			'smap1.cdunieco'  : _0_smap1.cdunieco
                        			,'smap1.cdramo'   : _0_smap1.cdramo
                        			,'smap1.estado'   : 'W'
                        			,'smap1.nmpoliza' : ''
                        			,'smap1.nmsuplem' : '0'
                        			,'smap1.ntramite' : json.smap1.ntramite
                        			,'smap1.tipomov'  : '0'
                        			,'smap1.nmsolici' : _0_fieldNmpoliza.getValue()
                        		}
                        	}
                        	,buttons     :
                        	[
                        	    {
                        	    	text     : 'Aceptar'
                        	    	,icon    : '${ctx}/resources/fam3icons/icons/accept.png'
                        	    	,handler : function()
                        	    	{
                        	    		this.up().up().destroy();
                        	    	}
                        	    }
                        	]
                        }).show()
                    );
                    if(_0_smap1.SITUACION=='AUTO')
                    {
                        debug("_0_smap1.SITUACION=='AUTO'");
                        var msg = Ext.Msg.show(
                        {
                            title    : 'Tr&aacute;mite actualizado'
                            ,msg     : 'La cotizaci&oacute;n se guard&oacute; para el tr&aacute;mite '
                                        + json.smap1.ntramite
                                        + '<br/>y no podr&aacute; ser modificada posteriormente'
                            ,buttons : Ext.Msg.OK
                            ,y       : 50
                            ,fn      : function()
                            {
                                Ext.create('Ext.form.Panel').submit(
                                {
                                    url             : _0_urlDatosComplementarios
                                    ,standardSubmit : true
                                    ,params         :
                                    {
                                        cdunieco         : _0_smap1.cdunieco
                                        ,cdramo          : _0_smap1.cdramo
                                        ,estado          : 'W'
                                        ,nmpoliza        : _0_fieldNmpoliza.getValue()
                                        ,'map1.ntramite' : json.smap1.ntramite
                                        ,cdtipsit        : _0_smap1.cdtipsit
                                    }
                                });
                            }
                        });
                    }
                    else
                    {
                        var msg = Ext.Msg.show(
                        {
                        	title    : 'Solicitud enviada'
                        	,msg     : 'Su solicitud ha sido enviada a mesa de control con el n&uacute;mero de tr&aacute;mite '
                        	            + json.smap1.ntramite
                        	            + ', ahora puede subir los documentos del trámite'
                        	,buttons : Ext.Msg.OK
                        	,y       : 50
                        });
                        msg.setY(50);
                    }
                }
                else
                {
                	var msg = Ext.Msg.show(
                	{
                		title    : 'Tr&aacute;mite actualizado'
                		,msg     : 'La cotizaci&oacute;n se guard&oacute; para el tr&aacute;mite '
                		            + _0_smap1.ntramite
                		            + '<br/>y no podr&aacute; ser modificada posteriormente'
                		,buttons : Ext.Msg.OK
                		,y       : 50
                		,fn      : function()
                		{
                			Ext.create('Ext.form.Panel').submit(
                			{
                				url             : _0_urlDatosComplementarios
                				,standardSubmit : true
                				,params         :
                				{
                					cdunieco         : _0_smap1.cdunieco
                					,cdramo          : _0_smap1.cdramo
                					,estado          : 'W'
                					,nmpoliza        : _0_fieldNmpoliza.getValue()
                					,'map1.ntramite' : _0_smap1.ntramite
                					,cdtipsit        : _0_smap1.cdtipsit
                				}
                			});
                		}
                	});
                    msg.setY(50);
                }
            }
            else
            {
            	mensajeError(json.respuesta);
            }
        }
	    ,failure : function()
        {
            _0_panelPri.setLoading(false);
            errorComunicacion();
        }
    });
}

function _0_imprimir()
{
	var me = this;
    var urlRequestImpCotiza = _0_urlImprimirCotiza
            + '?p_unieco='      + _0_smap1.cdunieco
            + '&p_ramo='        + _0_smap1.cdramo
            + '&p_subramo='     + _0_smap1.cdtipsit
            + '&p_estado=W'
            + '&p_poliza='      + _0_fieldNmpoliza.getValue()
            + '&p_suplem=0'
            + '&p_cdplan='      + _0_selectedCdplan
            + '&p_plan='        + _0_selectedCdplan
            + '&p_perpag='      + _0_selectedCdperpag
            + '&p_ntramite='    + _0_smap1.ntramite
            + '&p_cdusuari='    + _0_smap1.cdusuari
            + '&destype=cache'
            + "&desformat=PDF"
            + "&userid="        + _0_reportsServerUser
            + "&ACCESSIBLE=YES"
            + "&report="        + _0_reporteCotizacion
            + "&paramform=no";
    debug(urlRequestImpCotiza);
    var numRand = Math.floor((Math.random() * 100000) + 1);
    debug(numRand);
    var windowVerDocu = Ext.create('Ext.window.Window',
    {
        title          : 'Cotizaci&oacute;n'
        ,width         : 700
        ,height        : 500
        ,collapsible   : true
        ,titleCollapse : true
        ,html : '<iframe innerframe="'
                + numRand
                + '" frameborder="0" width="100" height="100"'
                + 'src="'
                + _0_urlViewDoc
                + "?contentType=application/pdf&url="
                + encodeURIComponent(urlRequestImpCotiza)
                + "\">"
                + '</iframe>'
        ,listeners :
        {
            resize : function(win,width,height,opt)
            {
                debug(width,height);
                $('[innerframe="'+ numRand+ '"]').attr(
                {
                	'width'   : width - 20
                	,'height' : height - 60
                });
            }
        }
    }).show();
    windowVerDocu.center();
}

function _0_mail()
{
	Ext.create('Ext.window.Window',
	{
		title : 'Enviar cotizaci&oacute;n'
		,width : 550
		,modal : true
		,height : 150
		,buttonAlign : 'center'
		,bodyPadding : 5
		,items :
		[
		    {
		    	xtype       : 'textfield'
		    	,id         : '_0_idInputCorreos'
		    	,fieldLabel : 'Correo(s)'
		    	,emptyText  : 'Correo(s) separados por ;'
		    	,labelWidth : 100
		    	,allowBlank : false
		    	,blankText  : 'Introducir correo(s) separados por ;'
		    	,width      : 500
		    }
		]
		,buttons :
		[
		    {
		    	text : 'Enviar'
		    	,icon : '${ctx}/resources/fam3icons/icons/accept.png'
		    	,handler : function()
		    	{
		    		var me = this;
		    		if (Ext.getCmp('_0_idInputCorreos').getValue().length > 0
		    				&&Ext.getCmp('_0_idInputCorreos').getValue() != 'Correo(s) separados por ;')
		    		{
		    			debug('Se va a enviar cotizacion');
		    			me.up().up().setLoading(true);
		    			Ext.Ajax.request(
		    			{
		    				url : _0_urlEnviarCorreo
		    				,params :
		    				{
		    					to : Ext.getCmp('_0_idInputCorreos').getValue(),
		    					urlArchivo : _0_urlImprimirCotiza
                                             + '?p_unieco='      + _0_smap1.cdunieco
                                             + '&p_ramo='        + _0_smap1.cdramo
                                             + '&p_subramo='     + _0_smap1.cdtipsit
                                             + '&p_estado=W'
                                             + '&p_poliza='      + _0_fieldNmpoliza.getValue()
                                             + '&p_suplem=0'
                                             + '&p_cdplan='      + _0_selectedCdplan
                                             + '&p_plan='        + _0_selectedCdplan
                                             + '&p_perpag='      + _0_selectedCdperpag
                                             + '&p_ntramite='    + _0_smap1.ntramite
                                             + '&p_cdusuari='    + _0_smap1.cdusuari
                                             + '&destype=cache'
                                             + "&desformat=PDF"
                                             + "&userid="        + _0_reportsServerUser
                                             + "&ACCESSIBLE=YES"
                                             + "&report="        + _0_reporteCotizacion
                                             + "&paramform=no",
		    					nombreArchivo : 'cotizacion_'+Ext.Date.format(new Date(),'Y-d-m_g_i_s_u')+'.pdf'
		    			    },
		    			    callback : function(options,success,response)
		    			    {
		    			    	me.up().up().setLoading(false);
		    			    	if (success)
		    			    	{
		    			    		var json = Ext.decode(response.responseText);
		    			    		if (json.success == true)
		    			    		{
		    			    			me.up().up().destroy();
		    			    			Ext.Msg.show(
		    			    			{
		    			    				title : 'Correo enviado'
		    			    				,msg : 'El correo ha sido enviado'
		    			    				,buttons : Ext.Msg.OK
		    			    			});
		    			    		}
		    			    		else
		    			    		{
		    			    			mensajeError('Error al enviar');
		    			    		}
		    			    	}
		    			    	else
		    			    	{
		    			    		errorComunicacion();
		    			    	}
		    			    }
		    			});
		    		}
		    		else
		    		{
		    			mensajeWarning('Introduzca al menos un correo');
		    		}
		    	}
		    }
		    ,
		    {
		    	text     : 'Cancelar'
		    	,icon    : '${ctx}/resources/fam3icons/icons/cancel.png'
                ,handler : function()
                {
                    this.up().up().destroy();
                }
		    }
		]
    }).show();
	Ext.getCmp('_0_idInputCorreos').focus();
}

function _0_bloquear(b)
{
	for(var i=2;i<_0_formAgrupados.items.items.length-1;i++)
	{
		_0_formAgrupados.items.items[i].setReadOnly(b);
	}
	_0_gridIncisos.setDisabled(b);
	_0_botonera.setDisabled(b);
	if(b)
	{
		//window.parent.scrollTo(0, _0_formAgrupados.getHeight()+_0_gridIncisos.getHeight());//ELIMINADA
        // Se aplica el focus en algun boton habilitado del grid de tarifas:
        try {
           _0_gridTarifas.down('button[disabled=false]').focus(false, 1000);
        } catch(e) {
            debug(e);
        }
	} else {
		// Se aplica el focus en el numero de poliza:
        try {
            _0_fieldNmpoliza.focus();
        } catch(e) {
            debug(e);
        }
	}
}

function _0_detallesCobertura()
{
	_0_windowCoberturas.setLoading(true);
	Ext.Ajax.request(
	{
		url      : _0_urlDetalleCobertura
        ,params  :
        {
            idCobertura       : _0_selectedIdcobertura
            ,idCiaAseguradora : '20'
            ,idRamo           : _0_smap1.cdramo
        }
        ,success : function(response)
        {
            _0_windowCoberturas.setLoading(false);
            var jsonResp = Ext.decode(response.responseText);
            if (jsonResp.ayudaCobertura
                    && jsonResp.ayudaCobertura.dsGarant
                    && jsonResp.ayudaCobertura.dsGarant.length > 0
                    && jsonResp.ayudaCobertura.dsAyuda
                    && jsonResp.ayudaCobertura.dsAyuda.length > 0) {
                _0_windowAyudaCobertura.html = '<table width=430 ><tr><td align=left bgcolor="#98012e" style="color:white;font-size:11px;"><b>'
                        + jsonResp.ayudaCobertura.dsGarant
                        + '</b></td></tr><tr><td style="font-size:11px; ">'
                        + jsonResp.ayudaCobertura.dsAyuda
                        + '</td></tr></table>';
                _0_windowAyudaCobertura.show();
            }
            else
            {
            	mensajeWarning('No hay detalle de cobertura');
            }
        }
        ,failure : function()
        {
        	_0_windowCoberturas.setLoading(false);
            mensajeError('Error al obtener detalle');
        }
    });
}
function _0_coberturas()
{
	_0_storeCoberturas.load(
	{
        params :
        {
            jsonCober_unieco   : _0_smap1.cdunieco
            ,jsonCober_estado  : 'W'
            ,jsonCober_nmpoiza : _0_fieldNmpoliza.getValue()
            ,jsonCober_cdplan  : _0_selectedCdplan
            ,jsonCober_cdramo  : _0_smap1.cdramo
            ,jsonCober_cdcia   : '20'
            ,jsonCober_situa   : _0_selectedNmsituac
        }
    });
	_0_gridCoberturas.setTitle('Plan ' + _0_selectedDsplan);
	_0_botDetalleCobertura.setDisabled(true);
	_0_windowCoberturas.show();
}

function _0_detalles()
{
	Ext.Ajax.request(
	{
        url      : _0_urlDetalleCotizacion
        ,params  :
        {
            'panel1.pv_cdunieco_i'  : _0_smap1.cdunieco
            ,'panel1.pv_cdramo_i'   : _0_smap1.cdramo
            ,'panel1.pv_estado_i'   : 'W'
            ,'panel1.pv_nmpoliza_i' : _0_fieldNmpoliza.getValue()
            ,'panel1.pv_cdperpag_i' : _0_selectedCdperpag
            ,'panel1.pv_cdplan_i'   : _0_selectedCdplan
        }
        ,success : function(response)
        {
            var json = Ext.decode(response.responseText);
            debug(json);
            if (json.success == true)
            {
            	var orden = 0;
                var parentescoAnterior = 'werty';
                for ( var i = 0; i < json.slist1.length; i++)
                {
                    if (json.slist1[i].parentesco != parentescoAnterior)
                    {
                        orden++;
                        parentescoAnterior = json.slist1[i].parentesco;
                    }
                    json.slist1[i].orden_parentesco = orden+ '_'+ json.slist1[i].parentesco;
                }
                debug(json);
                var wndDetalleCotizacion = Ext.create('Ext.window.Window',
                {
                	title       : 'Detalles de cotizaci&oacute;n'
                	//,maxHeight  : 500
                	,width      : 600
                	,autoScroll : true
                	,modal      : true
                	,items      :
                	[
                	    Ext.create('Ext.grid.Panel',
                	    {
                	    	store    : Ext.create('Ext.data.Store',
                	    	{
                	    		model       : 'ModeloDetalleCotizacion'
                	    		,groupField : 'orden_parentesco'
                	    		,sorters    :
                	    		[
                	    		    {
                	    		    	sorterFn : function(o1,o2)
                	    		    	{
                	    		    	    debug('sorting:',o1,o2);
                	    		    		if (o1.get('orden') === o2.get('orden'))
                	    		    		{
                	    		    			return 0;
                	    		    		}
                	    		    		return o1.get('orden')-0 < o2.get('orden')-0 ? -1 : 1;
                	    		    	}
                	    		    }
                	    		]
                	    	    ,proxy      :
                	    	    {
                	    	    	type    : 'memory'
                	    	    	,reader : 'json'
                	    	    }
                	    	    ,data : json.slist1
                	    	})
                	    	,columns :
                	    	[
                	    	    {
                	    	    	header           : 'Nombre de la cobertura'
                	    	    	,dataIndex       : 'Nombre_garantia'
                	    	    	,flex            : 3
                	    	    	,summaryType     : 'count'
                	    	    	,summaryRenderer : function(value)
                	    	    	{
                	    	    		return Ext.String.format('Total de {0} cobertura{1}',value,value !== 1 ? 's': '');
                	    	    	}
                	    	    }
                	    	    ,{
                                    header       : 'Importe por cobertura'
                                    ,dataIndex   : 'Importe'
                                    ,flex        : 1
                                    ,renderer    : Ext.util.Format.usMoney
                                    ,align       : 'right'
                                    ,summaryType : 'sum'
                                } 
                	    	]
                	        ,features :
                	        [
                	            {
                	            	groupHeaderTpl :
                	            	[
                	            	    '{name:this.formatName}'
                	            	    ,{
                	            	    	formatName : function(name)
                	            	    	{
                	            	    		return name.split("_")[1];
                	            	    	}
                	            	    }
                	            	]
                	            ,ftype          : 'groupingsummary'
                	            ,startCollapsed : true
                	            }
                	        ]
                	    })
                	    ,Ext.create('Ext.toolbar.Toolbar',
                	    {
                	    	buttonAlign : 'right'
                	    	,items      :
                	    	[
                	    	    '->'
                	    	    ,Ext.create('Ext.form.Label',
                	    	    {
                	    	    	style          : 'color:white;'
                	    	    	,initComponent : function()
                	    	    	{
                	    	    		var sum = 0;
                	    	    		for ( var i = 0; i < json.slist1.length; i++)
                	    	    		{
                	    	    			sum += parseFloat(json.slist1[i].Importe);
                	    	    		}
                	    	    		this.setText('Total: '+ Ext.util.Format.usMoney(sum));
                	    	    		this.callParent();
                	    	    	}
                	    	    })
                	    	]
                	    })
                	]
                }).show();
                centrarVentanaInterna(wndDetalleCotizacion);
            }
            else
            {
            	mensajeError('Error al obtener detalle');
            }
        }
        ,failure : errorComunicacion
	});
}

function _0_nueva()
{
	_0_formAgrupados.getForm().reset();
	_0_fieldNmpoliza.setValue('');
    _0_storeIncisos.removeAll();
    _0_panelPri.remove(_0_gridTarifas);
    _0_panelPri.doLayout();
    _0_bloquear(false);
    if(_0_formAgrupados.items.items[2])
    {
        _0_formAgrupados.items.items[2].focus();
    }
    else if(_0_formAgrupados.items.items[0].items.items[2])
    {
        _0_formAgrupados.items.items[0].items.items[2].focus();
    }
}

function _0_clonar()
{
	_0_panelPri.remove(_0_gridTarifas);
	_0_panelPri.doLayout();
    _0_fieldNmpoliza.setValue('');
    _0_bloquear(false);
    if(_0_formAgrupados.items.items[2])
    {
        _0_formAgrupados.items.items[2].focus();
    }
    else if(_0_formAgrupados.items.items[0].items.items[2])
    {
        _0_formAgrupados.items.items[0].items.items[2].focus();
    }
}

function _0_editar()
{
	_0_panelPri.remove(_0_gridTarifas);
	_0_panelPri.doLayout();
	_0_bloquear(false);
	if(_0_formAgrupados.items.items[2])
    {
        _0_formAgrupados.items.items[2].focus();
    }
    else if(_0_formAgrupados.items.items[0].items.items[2])
    {
        _0_formAgrupados.items.items[0].items.items[2].focus();
    }
}

function _0_limpiar()
{
	_0_formAgrupados.getForm().reset();
	_0_storeIncisos.removeAll();
	if(_0_formAgrupados.items.items[2])
    {
        _0_formAgrupados.items.items[2].focus();
    }
    else if(_0_formAgrupados.items.items[0].items.items[2])
    {
        _0_formAgrupados.items.items[0].items.items[2].focus();
    }
}

function _0_cargar()
{
	debug('>_0_cargar');
	Ext.Msg.prompt(
    'Cargar cotizaci&oacute;n',
    'N&uacute;mero de cotizaci&oacute;n:',
    function (buttonId, value)
    {
        debug('nmpoliza',value);
        var valido=true;
        //boton pulsado y valor capturado
        if(valido)
        {
            valido = buttonId=='ok'&&(value+'').length>0;
        }
        //valor numerico
        if(valido)
        {
            valido = !isNaN(value);
            if(!valido)
            {
                mensajeWarning('Introduce un n&uacute;mero v&aacute;lido');
            }
        }
        //request
        if(valido)
        {
        	_0_panelPri.setLoading(true);
            Ext.Ajax.request(
            {
                url      : _0_urlLoad
                ,params  :
                {
                    'smap1.nmpoliza'  : value
                    ,'smap1.cdramo'   : _0_smap1.cdramo
                    ,'smap1.cdunieco' : _0_smap1.cdunieco
                    ,'smap1.cdtipsit' : _0_smap1.cdtipsit
                }
                ,success : function(response)
                {
                	_0_panelPri.setLoading(false);
                    var json=Ext.decode(response.responseText);
                    debug('json response:',json);
                    if(json.success)
                    {
                        if(!json.smap1.NTRAMITE)
                        {
                    	    _0_limpiar();
                            for(var i=0;i<json.slist1.length;i++)
                            {
                                if(_0_smap1.SITUACION=='AUTO')
                                {
                                    debug("_0_smap1.SITUACION=='AUTO'");
                                    if(json.slist1[i].nombre&&json.slist1[i].nombre+'x'!='x')
                                    {
                            	        _0_storeIncisos.add(new _0_modelo(json.slist1[i]));
                            	    }
                            	}
                            	else
                            	{
                            	    _0_storeIncisos.add(new _0_modelo(json.slist1[i]));
                            	}
                            }
                            debug('store:',_0_storeIncisos);
                            var primerInciso = new _0_modeloAgrupado(json.slist1[0]);
                            primerInciso.set('FESOLICI',json.smap1.FESOLICI);
                            if(_0_smap1.cdramo=='6')
                            {
                                primerInciso.set('parametros.pv_otvalor24','S');
                            }
                            debug('primerInciso:',primerInciso);
                            //leer elementos anidados
                            var form      = _0_formAgrupados;
                            var formItems = form.items.items;
                            var hayDerechos = false;
                            <s:if test='%{getSmap1().get("CDATRIBU_DERECHO")!=null}'>
                                hayDerechos = true;
                                formItems = form.items.items[0].items.items;
                            </s:if>
                            debug('hayDerechos:' , hayDerechos);
                            debug('formItems:'   , formItems);
                            var numBlurs  = 0;
                            for(var i=0;i<formItems.length;i++)
                            {
                                var item=formItems[i];
                                if(item.hasListener('blur'))
                                {
                                    var numBlursSeguidos = 1;
                                    debug('contando blur:',item);
                                    for(var j=i+1;j<formItems.length;j++)
                                    {
                                        if(formItems[j].hasListener('blur'))
                                        {
                                            numBlursSeguidos=numBlursSeguidos+1;
                                        }
                                    }
                                    if(numBlursSeguidos>numBlurs)
                                    {
                                        numBlurs=numBlursSeguidos;
                                    }
                                }
                            }
                            debug('numBlurs:',numBlurs);
                            var i=0;
                            var renderiza=function()
                            {
                                debug('renderiza',i);
                                form.loadRecord(primerInciso);
                                if(i<numBlurs)
                                {
                                    i=i+1;
                                    for(var j=0;j<formItems.length;j++)
                                    {
                                        var iItem  = formItems[j]; 
                                        var iItem2 = formItems[j+1];
                                        debug('iItem2:',iItem2,'store:',iItem2?iItem2.store:'iItem2 no');
                                        if(iItem.hasListener('blur')&&iItem2&&iItem2.store&&iItem2.heredar)
                                        {
                                            debug('tiene blur y lo hacemos heredar',formItems[j]);
                                            iItem2.heredar(true);
                                        }
                                    }
                                    setTimeout(renderiza,1000);
                                }
                                else
                                {
                            	    _0_fieldNmpoliza.setValue(value);
                            	    _0_panelPri.setLoading(false);
                            	    if(_0_smap1.cdramo=='6')
                            	    {
                            	        if(_0_smap1.cdtipsit=='AT')
                            	        {
                            	            _0_obtenerClaveGSPorAuto();
                            	            _0_obtenerSumaAseguradaRamo6(true,true);
                            	        }
                            	        if(_fieldByLabel('FOLIO').getValue()==0)
                            	        {
                            	            _fieldByLabel('FOLIO').reset();
                        	                if(_0_smap1.cdsisrol=='SUSCRIAUTO')
                            	            {
                            	                _fieldByLabel('AGENTE').getStore().load(
                            	                {
                            	                    params :
                        	                        {
                            	                        'params.agente' : primerInciso.get('parametros.pv_otvalor17')
                            	                    }
                            	                    ,callback : function()
                            	                    {
                            	                        _fieldByLabel('AGENTE').setValue(
                            	                            _fieldByLabel('AGENTE').findRecord('key',primerInciso.get('parametros.pv_otvalor17'))
                            	                        );
                            	                    }
                            	                });
                            	            }
                            	            else
                            	            {
                            	                var cdagente=_fieldByLabel('AGENTE').getValue();
                            	                _fieldByLabel('FOLIO').reset();
                            	                _fieldByLabel('AGENTE').setValue(
                            	                    _fieldByLabel('AGENTE').findRecord('key',primerInciso.get('parametros.pv_otvalor17'))
                            	                );
                            	            }
                            	        }
                            	    }
                            	    if(_0_smap1.cdtipsit=='GMI')
                            	    {
                            	        _0_gmiPostalSelect(1,2,3,true);
                            	        _0_gmiCirchospSelect(1,2,3,true);
                            	    }
                            	    if(_0_smap1.cdtipsit == 'AF') {
                                    	if(_0_smap1.cdsisrol == 'SUSCRIAUTO') {
                                            _fieldByLabel('AGENTE').getStore().load({
                                                params : {
                                                    'params.agente' : primerInciso.get('parametros.pv_otvalor32')
                                                },
                                                callback : function() {
                                                    _fieldByLabel('AGENTE').setValue(
                                                        _fieldByLabel('AGENTE').findRecord('key',primerInciso.get('parametros.pv_otvalor32'))
                                                    );
                                                }
                                            });
                                        } else {
                                            _fieldByLabel('AGENTE').setValue(
                                                _fieldByLabel('AGENTE').findRecord('key',primerInciso.get('parametros.pv_otvalor32'))
                                            );
                                        }
                                    }
                                }
                            };
                            _0_panelPri.setLoading(true);
                            renderiza();
                        }
                        else
                        {
                            Ext.create('Ext.form.Panel').submit(
                            {
                                url             : _0_urlDatosComplementarios
                                ,standardSubmit : true
                                ,params         :
                                {
                                    cdunieco         : json.smap1.CDUNIECO
                                    ,cdramo          : json.smap1.cdramo
                                    ,estado          : 'W'
                                    ,nmpoliza        : json.smap1.nmpoliza
                                    ,'map1.ntramite' : json.smap1.NTRAMITE
                                    ,cdtipsit        : json.smap1.cdtipsit
                                }
                            });
                        }
                    }
                    else
                    {
                        mensajeError(json.error);
                    }
                }
                ,failure : function()
                {
                	_0_panelPri.setLoading(false);
                    errorComunicacion();
                }
            });
        }
    });
    debug('<_mcotiza_load');
	debug('<_0_cargar');
}

function _0_agregarAsegu(boton)
{
	var valido=true;
	if(valido)
	{
		if(!_0_necesitoIncisos)
		{
			valido=_0_storeIncisos.getCount()<1;
			if(!valido)
			{
				mensajeWarning('Solo se puede introducir un inciso');
			}
		}
	}
	if(valido)
	{
	    var grid=boton.up().up();
		debug('_0_agregarAsegu');
		var arrayEditores = _0_rowEditing.editor.form.monitor.getItems().items; 
		debug('arrayEditores:',arrayEditores);
		var record = new _0_modelo();
		for(var i = 0;i<arrayEditores.length;i++)
		{
			var iEditor = arrayEditores[i];
			if(iEditor.store)
			{
				record.set(iEditor.name,iEditor.store.getAt(0).get('key'));
			}
			else if(iEditor.format)
			{
				record.set(iEditor.name,Ext.Date.format(new Date(),'d/m/Y'));
			}
			else
			{
				var estaEnModeloExtra=false;
				debug('_0_modeloExtraFields.length:',_0_modeloExtraFields.length);
				for(var j=0;j<_0_modeloExtraFields.length;j++)
				{
					if(iEditor.name==_0_modeloExtraFields[j].name)
					{
						estaEnModeloExtra=true;
					}
				}
				if(!estaEnModeloExtra)
	            {
	                record.set(iEditor.name,iEditor.name);
	            }
			}
		}
		record.set('contador',_0_storeIncisos.getCount()+1);
		_0_storeIncisos.add(record);
		_0_rowEditing.startEdit(_0_storeIncisos.getCount()-1,1);
		_0_rowEditing.startEdit(_0_storeIncisos.getCount()-1,1);
		// Se aplica el focus en algun boton habilitado del grid de incisos:
		try {
            _0_botonera.down('button[disabled=false]').focus();
		} catch(e) {
        	debug(e);
        }
		//window.parent.scrollTo(0, _0_formAgrupados.getHeight());//ELIMINADA
	}
}

function _0_cotizar(boton)
{
	debug('_0_cotizar');
	if(_0_validarBase())
	{
		var json=
		{
		    slist1 : []
		    ,smap1 : _0_smap1 
		};
		if(_0_necesitoIncisos)
		{
			_0_storeIncisos.each(function(record)
			{
				var inciso=_0_formAgrupados.getValues();
				for(var key in record.data)
				{
					var value=record.data[key];
					debug(typeof value,key,value);
					if((typeof value=='object')&&value&&value.getDate)
					{
						var fecha='';
						fecha+=value.getDate();
						if((fecha+'x').length==2)//1x 
						{
							fecha = ('x'+fecha).replace('x','0');//x1=01 
						}
						fecha+='/';
						fecha+=value.getMonth()+1<10?
								(('x'+(value.getMonth()+1)).replace('x','0'))
								:(value.getMonth()+1);
						fecha+='/';
						fecha+=value.getFullYear();
						value=fecha;
					}
					inciso[key]=value;
				}
				json['slist1'].push(inciso);
			});
		}
		else
		{
			var inciso=_0_formAgrupados.getValues();
			if(_0_storeIncisos.getCount()==1)
			{
				var record=_0_storeIncisos.getAt(0);
				for(var key in record.data)
                {
                    var value=record.data[key];
                    debug(typeof value,key,value);
                    if((typeof value=='object')&&value&&value.getDate)
                    {
                        var fecha='';
                        fecha+=value.getDate();
                        if((fecha+'x').length==2)//1x 
                        {
                            fecha = ('x'+fecha).replace('x','0');//x1=01 
                        }
                        fecha+='/';
                        fecha+=value.getMonth()+1<10?
                                (('x'+(value.getMonth()+1)).replace('x','0'))
                                :(value.getMonth()+1);
                        fecha+='/';
                        fecha+=value.getFullYear();
                        value=fecha;
                    }
                    inciso[key]=value;
                }
			}
			json['slist1'].push(inciso);
		}
		debug('json para cotizar:',json);
		_0_panelPri.setLoading(true);
		Ext.Ajax.request(
		{
			url       : _0_smap1['externo']=='si'?_0_urlCotizarExterno:_0_urlCotizar
			,jsonData : json
			,success  : function(response)
			{
			    _0_bloquear(true);
				_0_panelPri.setLoading(false);
				json=Ext.decode(response.responseText);
				if(json.success==true)
				{
					debug(Ext.decode(json.smap1.fields));
					debug(Ext.decode(json.smap1.columnas));
					debug(json.slist2);
					
					_0_fieldNmpoliza.setValue(json.smap1.nmpoliza);
					
					_grabarEvento('COTIZACION'
					              ,'COTIZA'
					              ,_0_smap1.ntramite
					              ,_0_smap1.cdunieco
					              ,_0_smap1.cdramo
					              ,'W'
					              ,json.smap1.nmpoliza
					              ,json.smap1.nmpoliza
					              ,'buscar'
					              );
					
					Ext.define('_0_modeloTarifa',
					{
						extend  : 'Ext.data.Model'
						,fields : Ext.decode(json.smap1.fields)
					});
					
					_0_gridTarifas=Ext.create('Ext.grid.Panel',
                    {
                        title             : 'Resultados'
                        ,store            : Ext.create('Ext.data.Store',
                        {
                            model : '_0_modeloTarifa'
                            ,data : json.slist2
                        })
                        ,columns          : Ext.decode(json.smap1.columnas)
                        ,selType          : 'cellmodel'
                        ,minHeight        : 100
                        ,enableColumnMove : false
                        ,buttonAlign      : 'center'
                        ,buttons          :
                        [
                            new _0_BotComprar()
                            ,new _0_BotDetalles()
                            ,new _0_BotCoberturas()
                            ,new _0_BotEditar()
                            ,new _0_BotClonar()
                            ,new _0_BotNueva()
                            ,new _0_BotMail()
                            ,new _0_BotImprimir()
                        ]
                        ,listeners        :
                        {
                            select : _0_tarifaSelect
                        }
                    });
                    
                    if(_0_smap1.cdramo+'x'=='6x')
                    {
                        Ext.getCmp('_0_botDetallesId').setDisabled(true);
                        Ext.getCmp('_0_botCoberturasId').setDisabled(true);
                    }
					
					_0_panelPri.add(_0_gridTarifas);
					_0_panelPri.doLayout();
					//setTimeout(function(){debug('timeout 1000');window.parent.scrollTo(0, 99999);},1000);//ELIMINADO
					// Se aplica el focus en algun boton habilitado del grid de tarifas:
					try {
					   _0_gridTarifas.down('button[disabled=false]').focus(false, 1000);
					} catch(e) {
                        debug(e);
                    }
				}
				else
				{
					_0_bloquear(false);
					mensajeError('Error al cotizar:<br/>'+json.error);
				}
			}
		    ,failure  : function(response)
		    {
		    	_0_panelPri.setLoading(false);
		    	errorComunicacion();
		    }
		});
	}
}

function _0_validarBase()
{
	var valido=true;
	debug('>_0_validarBase');
	
	//form validation
	if(valido)
	{
		valido=_0_formAgrupados.isValid();
		if(!valido)
		{
			datosIncompletos();
		}
	}
	
	//algun inciso
	if(valido&&_0_necesitoIncisos)
	{
		valido=_0_storeIncisos.getCount()>0;
		if(!valido)
		{
			mensajeWarning('No hay incisos');
		}
	}
	
	//validar atributos de tatrisit en grid
	if(valido&&_0_necesitoIncisos)
	{
		_0_storeIncisos.each(function(record)
        {
            for(var key in record.data)
            {
            	debug('validando:',record.data);
                //debug('modelo extra fields:',_0_modeloExtraFields);
                var estaEnModeloExtra=false;
                //debug('_0_modeloExtraFields.length:',_0_modeloExtraFields.length);
                for(var i=0;i<_0_modeloExtraFields.length;i++)
                {
                    if(key==_0_modeloExtraFields[i].name)
                    {
                        estaEnModeloExtra=true;
                    }
                }
                if(!estaEnModeloExtra)
                {
                	var value=record.data[key];
                	valido=valido&&value;
                    if(!valido)
                    {
                        debug('falta: ',key);
                    }
                }
            }
        });
        if(!valido)
        {
            mensajeWarning('Los datos de los incisos son requeridos');
        }
	}
	
	if(valido && _0_necesitoIncisos && _0_smap1.SITUACION=='PERSONA'){
		
		try{
			var colNameParentesco = _0_gridIncisos.down('[text=PARENTESCO]').dataIndex; 
			var colNameFechaNacimi = _0_gridIncisos.down('[text*=NACIMIENTO]').dataIndex; 
			var fechaHoy = new Date();
			
			_0_storeIncisos.each(function(record){
	
				var parentescoRecord = record.get(colNameParentesco);
				var fechaRecord      = record.get(colNameFechaNacimi);
				
				debug('fechaHoy: ', fechaHoy);
				debug('fechaRecord: ', fechaRecord);
				
				var years = calculaAniosTranscurridos(fechaRecord,fechaHoy);
				
				debug('years: ', years);
				
				if((parentescoRecord == _parentescoTitular) && years != null && (years < 18) ){
					mensajeWarning('El Titular es Menor de Edad, se requerir&aacute; una autorizaci&oacute;n posterior.');
				}
	            
	        });
		}catch(e){
			debug('Error en la validacion de Edad del Titular!!!',e);
		}
	}
	
	//custom validation
	if(valido)
	{
		valido=_0_validacion_custom();
	}
	
	debug('<_0_validarBase');
	return valido;
}

function _0_tarifaSelect(selModel, record, row, column, eOpts)
{
	debug('column:',column);
	if(column>0)
	{
		column = (column * 2) -1;
	}
	debug('( column * 2 )-1:',column);
    var columnName=_0_gridTarifas.columns[column].dataIndex;
    debug('record',record);
    debug('columnName',columnName);
    if(columnName=='DSPERPAG')
    {
    	debug('DSPERPAG');
    	Ext.getCmp('_0_botDetallesId').setDisabled(true);
    	Ext.getCmp('_0_botCoberturasId').setDisabled(true);
    	Ext.getCmp('_0_botMailId').setDisabled(true);
    	Ext.getCmp('_0_botImprimirId').setDisabled(true);
    	Ext.getCmp('_0_botComprarId').setDisabled(true);
    }
    else
    {
    	// M N P R I M A X
    	//0 1 2 3 4 5 6 7
    	_0_selectedCdperpag = record.get("CDPERPAG");
    	_0_selectedCdplan   = columnName.substr(7);
    	_0_selectedDsplan   = record.get("DSPLAN"+_0_selectedCdplan);
    	_0_selectedNmsituac = record.get("NMSITUAC");
    	debug('_0_selectedCdperpag',_0_selectedCdperpag);
    	debug('_0_selectedCdplan',_0_selectedCdplan);
    	debug('_0_selectedDsplan',_0_selectedDsplan);
    	debug('_0_selectedNmsituac',_0_selectedNmsituac);
    	
    	Ext.getCmp('_0_botDetallesId').setDisabled(false);
    	Ext.getCmp('_0_botCoberturasId').setDisabled(false);
    	if(_0_smap1.cdramo+'x'!='6x')
    	{
    	    Ext.getCmp('_0_botMailId').setDisabled(false);
    	    Ext.getCmp('_0_botImprimirId').setDisabled(false);
    	}
    	Ext.getCmp('_0_botComprarId').setDisabled(false);
    }
}

function _0_gmiPostalSelect(a,b,c,sinReset)
{
    var postal = _fieldLikeLabel('POSTAL');
    var zona   = _fieldLikeLabel('CAMBIO DE ZONA');
    debug('POSTAL select:',postal.getValue(),sinReset,'<sinReset');
    Ext.Ajax.request(
    {
        url     : _0_urlValidarCambioZonaGMI
        ,params :
        {
            'smap1.cdramo'     : _0_smap1.cdramo
            ,'smap1.cdtipsit'  : _0_smap1.cdtipsit
            ,'smap1.codpostal' : postal.getValue()
        }
        ,success : function(response)
        {
            var json=Ext.decode(response.responseText);
            debug('### validar eliminacion cambio zona:',json);
            if(json.exito)
            {
                if(!sinReset)
                {
                    zona.reset();
                }
                zona.show();
            }
            else
            {
                zona.setValue('N');
                zona.hide();
            }
        }
        ,failure : function()
        {
            errorComunicacion();
        }
    });
}

function _0_gmiCirchospSelect(a,b,c,sinReset)
{
    var circ  = _fieldLikeLabel('CULO HOSPITALARIO');
    var enfer = _fieldLikeLabel('ENFERMEDAD CATAS');
    debug('CIRCULO HOSP select:',circ.getValue(),sinReset,'<sinReset');
    Ext.Ajax.request(
    {
        url     : _0_urlValidarEnfermCatasGMI
        ,params :
        {
            'smap1.cdramo'    : _0_smap1.cdramo
            ,'smap1.circHosp' : circ.getValue()
        }
        ,success : function(response)
        {
            var json=Ext.decode(response.responseText);
            debug('### validar enfermedad catastrofica:',json);
            if(json.exito)
            {
                if(!sinReset)
                {
                    enfer.reset();
                }
                enfer.show();
            }
            else
            {
                enfer.setValue('N');
                enfer.hide();
            }
        }
        ,failure : function()
        {
            errorComunicacion();
        }
    });
}

/**
 * Verifica que el Codigo Postal pertenezca a algun estado
 * @type String
 */
function agregaValidacionCPvsEstado() {
    // Si existen los campos de codigo postal y estado, y este ultimo esta anidado, agregar validacion:
    var tmpPostal = _fieldLikeLabel('POSTAL', null, true);
    var tmpEstado = _fieldLikeLabel('ESTADO', null, true);
    if( tmpPostal && tmpEstado && !Ext.isEmpty(tmpEstado.anidado) && tmpEstado.anidado == true) {
    	// Se agrega listener al store de Estado para verificar si tiene elementos:
    	debug("***** Se agrega validacion de CP VS Estado *****");
        tmpEstado.getStore().on({
            load : function(store, records, successful, eOpts) {
                if(store.count() == 0) {
                    mensajeWarning('El C&oacute;digo Postal no existe, introduzca uno v&aacute;lido');
                }
            }
        });
    }
}

function _0_recuperarDescuento()
{
    var ck = 'Recuperando descuento';
    try
    {
    	Ext.Ajax.request(
	    {
	        url     : _0_urlRecuperacionSimple
	        ,params :
	        {
	            'smap1.procedimiento' : 'RECUPERAR_DESCUENTO_RECARGO_RAMO_5'
	            ,'smap1.cdtipsit'     : _0_smap1.cdtipsit
	            ,'smap1.cdagente'     : _fieldByLabel('AGENTE').getValue()
	            ,'smap1.negocio'      : _0_smap1.cdsisrol == 'SUSCRIAUTO' ? '999999' : '0'
	            ,'smap1.tipocot'      : 'I'
	            ,'smap1.cdsisrol'     : _0_smap1.cdsisrol
	            ,'smap1.cdusuari'     : _0_smap1.cdusuari
	        }
	        ,success : function(response)
	        {
	            var ck = 'Decodificando descuento por rol/usuario';
	            try
	            {
	                var json = Ext.decode(response.responseText);
	                debug('### cargar rango descuento fronterizo:',json);
	                
	                ck           = 'Recuperando componente de descuento';
	                var itemDesc = _fieldLikeLabel('DESCUENTO',null,true);
	                
	                if(json.exito)
	                {
	                    itemDesc.minValue=100*Number(json.smap1.min);
	                    itemDesc.maxValue=100*Number(json.smap1.max);
	                    itemDesc.isValid();
	                    debug('min:',itemDesc.minValue);
	                    debug('max:',itemDesc.maxValue);
	                    itemDesc.setReadOnly(false);
	                }
	                else
	                {
	                    itemDesc.minValue=0;
	                    itemDesc.maxValue=0;
	                    itemDesc.setValue(0);
	                    itemDesc.isValid();
	                    itemDesc.setReadOnly(true);
	                    mensajeError(json.respuesta);
	                }
	            }
	            catch(e)
	            {
	                manejaException(e,ck);
	            }
	        }
	        ,failure : function()
	        {
	            var ck = 'Recuperando componente de descuento';
	            try
	            {
	                var itemDesc = _fieldLikeLabel('DESCUENTO',null,true);
	                itemDesc.minValue=0;
	                itemDesc.maxValue=0;
	                itemDesc.setValue(0);
	                itemDesc.isValid();
	                itemDesc.setReadOnly(true);
	                errorComunicacion();
	            }
	            catch(e)
	            {
	                manejaException(e,ck);
	            }
	        }
	    });
    }
    catch(e)
    {
        manejaException(e,ck);
    }
}


function mensajeValidacionNumSerie(titulo,imagenSeccion,txtRespuesta){
	var panelImagen = new Ext.Panel({
		defaults 	: {
			style   : 'margin:5px;'
		},
		layout: {
			type: 'hbox'
			,align: 'center'
			,pack: 'center'
		}
		,border: false
		,items:[{	        	
			xtype   : 'image'
			,src    : '${ctx}/images/cotizacionautos/menu_endosos.png'
			,width: 200
			,height: 100
		}]
	});

	validacionNumSerie = Ext.create('Ext.window.Window',{
		title        : titulo
		,modal       : true
		,buttonAlign : 'center'
		,width		 : 520
		,icon :imagenSeccion
		,resizable: false
		,height      : 250
		,items       :[
			Ext.create('Ext.form.Panel', {
				id: 'panelClausula'
				,width		 : 500
				,height      : 150
				,bodyPadding: 5
				,renderTo: Ext.getBody()
				,defaults 	 : {
					style : 'margin:5px;'
				}
				,border: false
				,items: [
				{
					xtype  : 'label'
					,text  : txtRespuesta
					,width: 100
					,height      : 100
					,style : 'color:red;margin:10px;'
				}
				,{
					border: false
					,items    :
						[	panelImagen		]
				}]
			})
		],
		buttonAlign:'center',
		buttons: [{
			text: 'Aceptar',
			icon: _CONTEXT+'/resources/fam3icons/icons/accept.png',
			buttonAlign : 'center',
			handler: function() {
				validacionNumSerie.close();
			}
		}]
	});
	centrarVentanaInterna(validacionNumSerie.show());
}

/*///////////////////*/
////// funciones //////
///////////////////////
    
Ext.onReady(function()
{
    
    _grabarEvento('COTIZACION','ACCCOTIZA',_0_smap1.ntramite,_0_smap1.cdunieco,_0_smap1.cdramo);
    
    Ext.Ajax.timeout = 5*60*1000;
    
    /////////////////////
    ////// modelos //////
    /*/////////////////*/
	_0_validacion_custom = function()
    {
		mensajeWarning('Falta definir la validaci&oacute;n para el producto');
        return true;
    };
    <s:if test='%{getImap().get("validacionCustomButton")!=null}'>
	    var botonValidacionCustom = <s:property value="imap.validacionCustomButton" escapeHtml='false' />;
	     _0_validacion_custom=botonValidacionCustom.handler;
    </s:if>
    
    Ext.define('_0_modeloRecuperado',
    {
        extend  : 'Ext.data.Model'
        ,fields :
        [
            'NOMBRECLI'
            ,'RFCCLI'
            ,'CLAVECLI'
            ,'DIRECCIONCLI'
        ]
    });
    
    Ext.define('ModeloDetalleCotizacion',
    {
    	extend : 'Ext.data.Model'
    	,fields :
    	[
			'Codigo_Garantia'
			,{
				name : 'Importe'
				,type : 'float'
			}
			,'Nombre_garantia'
			,'cdtipcon'
			,'nmsituac'
			,'orden'
			,'parentesco'
			,'orden_parentesco'
        ]
    });
    
    var tmp = [];
    <s:if test='%{getImap().get("fieldsIndividuales")!=null}'>
        tmp.push(<s:property value="imap.fieldsIndividuales" />);
	</s:if>
	<s:if test='%{getImap().get("modeloExtraFields")!=null}'>
	    tmp.push(<s:property value="imap.modeloExtraFields"  />);
	</s:if>
	debug('_0_modelo fields:',tmp);
    Ext.define('_0_modelo',
    {
    	extend  : 'Ext.data.Model'
    	,fields : tmp
    });
    
    Ext.define('_0_modeloAgrupado',
    {
        extend  : 'Ext.data.Model'
        ,fields :
        [
            <s:property value="imap.fieldsAgrupados"/>,'FESOLICI'
        ]
    });
    /*/////////////////*/
    ////// modelos //////
    /////////////////////
    
    ////////////////////
    ////// stores //////
    /*////////////////*/
    _0_storeCoberturas=Ext.create('Ext.data.Store',
    {
        autoLoad : false
        ,model   : 'RowCobertura'
        ,sorters    :
        [
            {
                sorterFn : function(o1,o2)
                {
                    debug('sorting:',o1,o2);
                    if (o1.get('orden') === o2.get('orden'))
                    {
                        return 0;
                    }
                    return o1.get('orden')-0 < o2.get('orden')-0 ? -1 : 1;
                }
            }
        ]
        ,proxy   :
        {
            type    : 'ajax'
            ,url    : _0_urlCoberturas
            ,reader :
            {
                type  : 'json'
                ,root : 'listaCoberturas'
            }
        }
    });
    
    _0_storeIncisos = Ext.create('Ext.data.Store',
    {
    	model : '_0_modelo'
    });
    /*////////////////*/
    ////// stores //////
    ////////////////////
    
    /////////////////////////
    ////// componentes //////
    /*/////////////////////*/
    Ext.define('_0_BotComprar',
    {
        extend    : 'Ext.Button'
        ,id       : '_0_botComprarId'
        ,text     : _0_smap1.ntramite?'Complementar tr&aacute;mite':'Generar tr&aacute;mite'
        ,icon     : '${ctx}/resources/fam3icons/icons/book_next.png'
        ,disabled : true
        ,handler  : _0_comprar
        ,hidden   : _0_smap1.readOnly&&_0_smap1.readOnly.length>0
    });
    
    Ext.define('_0_BotImprimir',
    {
        extend    : 'Ext.Button'
        ,id       : '_0_botImprimirId'
        ,text     : 'Imprimir'
        ,icon     : '${ctx}/resources/fam3icons/icons/printer.png'
        ,disabled : true
        ,handler  : _0_imprimir
    });
    
    Ext.define('_0_BotMail',
    {
        extend    : 'Ext.Button'
        ,id       : '_0_botMailId'
        ,text     : 'Enviar'
        ,icon     : '${ctx}/resources/fam3icons/icons/email.png'
        ,disabled : true
        ,handler  : _0_mail
    });
    
    Ext.define('_0_BotCoberturas',
    {
        extend    : 'Ext.Button'
        ,id       : '_0_botCoberturasId'
        ,text     : 'Coberturas'
        ,icon     : '${ctx}/resources/fam3icons/icons/table.png'
        ,disabled : true
        ,handler  : _0_coberturas
    });
    
    
    Ext.define('_0_BotDetalles',
    {
        extend    : 'Ext.Button'
        ,id       : '_0_botDetallesId'
        ,text     : 'Detalles'
        ,icon     : '${ctx}/resources/fam3icons/icons/text_list_numbers.png'
        ,disabled : true
        ,hidden   : ocultarDetalleCotizacion  
        ,handler  : _0_detalles
    });
    
    Ext.define('_0_BotNueva',
    {
    	extend   : 'Ext.Button'
    	,id      : '_0_botNuevaId'
        ,text    : 'Nueva'
        ,icon    : '${ctx}/resources/fam3icons/icons/arrow_refresh.png'
        ,handler : _0_nueva
    });
    
    Ext.define('_0_BotClonar',
    {
    	extend   : 'Ext.Button'
        ,id      : '_0_botClonarId'
        ,text    : 'Clonar'
        ,icon    : '${ctx}/resources/fam3icons/icons/control_repeat_blue.png'
        ,handler : _0_clonar
    });
    
    Ext.define('_0_BotEditar',
    {
    	extend   : 'Ext.Button'
        ,id      : '_0_botEditarId'
        ,text    : 'Editar'
        ,icon    : '${ctx}/resources/fam3icons/icons/pencil.png'
        ,handler : _0_editar
    });
    
    Ext.define("_0_FormAgrupados",
    {
    	extend         : 'Ext.form.Panel'
    	,title         : 'Datos generales de '+(_0_smap1.MODALIDAD.toLowerCase())
    	,initComponent : function()
    	{
    		debug('_0_FormAgrupados initComponent');
    		Ext.apply(this,
    		{
    			defaults :
    			{
    				style : 'margin : 5px;'
    			}
    			,items   :
    			[
    			    _0_fieldNtramite
    			    ,_0_fieldNmpoliza
    			    ,<s:property value="imap.camposAgrupados"/>
    			    ,{
                        name        : 'FESOLICI'
                        ,fieldLabel : 'FECHA DE SOLICITUD'
                        ,xtype      : 'datefield'
                        ,format     : 'd/m/Y'
                        ,editable   : true
                        ,allowBlank : false
                        ,value      : new Date()
                    }
    			    ,{
                        id          : 'fechaInicioVigencia'
                        ,name       : 'feini'
                        ,fieldLabel : 'INICIO DE VIGENCIA'
                        ,xtype      : 'datefield'
                        ,format     : 'd/m/Y'
                        ,editable   : true
                        ,allowBlank : false
                        ,value      : new Date()
                        ,listeners  :
                        {
                            change : _0_funcionFechaChange
                        }
                    },
                    {
                        id          : 'fechaFinVigencia'
                        ,name       : 'fefin'
                        ,fieldLabel : 'FIN DE VIGENCIA'
                        ,xtype      : 'datefield'
                        ,format     : 'd/m/Y'
                        ,readOnly   : true
                        ,allowBlank : false
                        ,value      : Ext.Date.add(new Date(),Ext.Date.YEAR,1)
                    }
    			]
    		});
    		this.callParent();
    	}
    });
    
    var tmp = [
		{
	        dataIndex     : 'contador'
	        ,width        : 30
	        ,menuDisabled : true
	    }
	];
    <s:if test='%{getImap().get("camposIndividuales")!=null}' >
       tmp.push(<s:property value="imap.camposIndividuales"/>);
    </s:if>
    <s:if test='%{getImap().get("modeloExtraColumns")!=null}' >
       tmp.push(<s:property value="imap.modeloExtraColumns" />);
    </s:if>
    tmp.push(
    {
        xtype  : 'actioncolumn'
        ,width : 30
        ,menuDisabled : true
        ,sortable : false
        ,icon : '${ctx}/resources/fam3icons/icons/delete.png'
        ,handler : function(grid,rowIndex,colIndex)
        {
            _0_storeIncisos.removeAt(rowIndex);
            var contador=1;
            _0_storeIncisos.each(function(record)
            {
                record.set('contador',contador);
                contador=contador+1;
            });
        }
    });
    debug('_0_GridIncisos columns:',tmp);
    Ext.define('_0_GridIncisos',
    {
        extend         : 'Ext.grid.Panel'
        ,initComponent : function()
        {
            debug('_0_GridIncisos initComponent');
            Ext.apply(this,
            {
            	title        : 'Datos de incisos'
            	,store       : _0_storeIncisos
            	,minHeight   : 170
            	//,hidden      : !_0_necesitoIncisos
            	,tbar        :
            	[
            	    {
            	    	text     : 'Agregar'
            	    	,icon    : '${ctx}/resources/fam3icons/icons/add.png'
            	    	,handler : _0_agregarAsegu
            	    }
            	]
                ,columns     : tmp
                ,plugins     :
                [
                    _0_rowEditing
                ]
            });
            this.callParent();
        }
    });
    
    Ext.define('_0_Botonera',
    {
    	extend         : 'Ext.panel.Panel'
    	,initComponent : function()
    	{
    		Ext.apply(this,
    		{
                buttonAlign : 'center'
                ,border     : 0
		        ,buttons    :
		        [
		            _0_botCotizar
		            ,_0_botLimpiar
		            ,_0_botCargar
		            //>agregado para cancelar un tramite
		            ,{
		                text     : 'Rechazar'
		                ,icon    : '${ctx}/resources/fam3icons/icons/cancel.png'
		                ,hidden  : (!_0_smap1.ntramite) || _0_smap1.ntramite.length==0
		                ,handler : function()
		                {
		                    //debug(form.getValues());
		                    Ext.create('Ext.window.Window',
		                    {
		                        title        : 'Guardar detalle'
		                        ,width       : 600
		                        ,height      : 430
		                        ,buttonAlign : 'center'
		                        ,modal       : true
		                        ,closable    : false
		                        ,autoScroll  : true
		                        ,items       :
		                        [
		                            {
		                                id        : 'inputTextareaCommentsToRechazo'
		                                ,width  : 570
		                                ,height : 300
		                                ,xtype  : 'textfield'
		                            }
		                            ,{
						                xtype       : 'radiogroup'
						                ,fieldLabel : 'Mostrar al agente'
						                ,columns    : 2
						                ,width      : 250
						                ,style      : 'margin:5px;'
						                ,items      :
						                [
						                    {
						                        boxLabel    : 'Si'
						                        ,itemId     : 'SWAGENTE'
						                        ,name       : 'SWAGENTE'
						                        ,inputValue : 'S'
						                    }
						                    ,{
						                        boxLabel    : 'No'
						                        ,name       : 'SWAGENTE'
						                        ,inputValue : 'N'
                                                ,checked    : true
						                    }
						                ]
						            }
		                        ]
		                        ,buttons    :
		                        [
		                            {
		                                text     : 'Rechazar'
		                                ,icon    : '${ctx}/resources/fam3icons/icons/cancel.png'
		                                ,handler : function()
		                                {
		                                    debug('rechazar');
		                                    var window=this.up().up();
		                                    window.setLoading(true);
		                                    Ext.Ajax.request
		                                    ({
		                                        url     : _0_urlUpdateStatus
		                                        ,params : 
		                                        {
		                                            'smap1.ntramite' : _0_smap1.ntramite
		                                            ,'smap1.status'  : '4'//rechazado
		                                            ,'smap1.comments' : Ext.getCmp('inputTextareaCommentsToRechazo').getValue()
		                                            ,'smap1.swagente' : _fieldById('SWAGENTE').getGroupValue()
		                                        }
		                                        ,success : function(response)
		                                        {
		                                            window.setLoading(false);
		                                            var json=Ext.decode(response.responseText);
		                                            if(json.success==true)
		                                            {
		                                                Ext.create('Ext.form.Panel').submit
		                                                ({
		                                                    url             : _0_urlMesaControl
		                                                    ,standardSubmit : true
                                                            ,params         :
                                                            {
                                                                'smap1.gridTitle'      : 'Tareas'
                                                                ,'smap2.pv_cdtiptra_i' : 1
                                                                ,'smap1.editable'      : 1
                                                            }
		                                                });
		                                            }
		                                            else
		                                            {
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
		            //<agregado para cancelar un tramite
		        ]
    		});
    		this.callParent();
    	}
    });
    
    Ext.define('_0_PanelPri',
    {
    	extend         : 'Ext.panel.Panel'
    	,initComponent : function()
        {
    		debug('_0_panelPri initComponent');
    		Ext.apply(this,
    		{
    			renderTo  : '_0_divPri'
   		        ,defaults :
   		        {
   		            style : 'margin:5px;'
   		        }
   		        ,items    :
   		        [
   		            _0_formAvisos
   		            ,_0_formAgrupados
   		            ,_0_gridIncisos
   		            ,_0_botonera
   		        ]
    		});
    		this.callParent();
        }
    });
    /*/////////////////////*/
    ////// componentes //////
    /////////////////////////
    
    ///////////////////////
    ////// contenido //////
    /*///////////////////*/
    var auxItemsAvisos = [];
    <s:if test='%{getActionErrors()!=null&&getActionErrors().size()>0}' >
        <s:iterator value="actionErrors" var="iError">
            auxItemsAvisos.push(
            {
                xtype     : 'panel'
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
    </s:if>
    _0_formAvisos=Ext.create('Ext.panel.Panel',
    {
        hidden    : <s:property value='%{getActionErrors()!=null&&getActionErrors().size()>0?false:true}' />
        ,defaults : { style : 'margin : 5px;'}
        ,border   : 0
        ,items    : auxItemsAvisos
    });
    
    _0_windowAyudaCobertura = new Ext.Window(
    {
    	title        : 'Detalle de cobertura'
    	,width       : 450
    	,height      : 350
    	,bodyStyle   : 'background:white'
    	,overflow    : 'auto'
    	,modal       : true
    	,autoScroll  : true
    	,buttonAlign : 'center'
    	,closable    : false
    	,buttons     :
    	[
    	    {
    	    	text     : 'Cerrar'
    	    	,handler : function()
    	    	{
    	    		this.up().up().hide();
    	    	}
    	    }
    	]
    });
    
    _0_botDetalleCobertura=Ext.create('Ext.Button',
    {
        text     : 'Ver detalle'
        ,icon    : '${ctx}/resources/fam3icons/icons/text_list_numbers.png'
        ,handler : _0_detallesCobertura
    });
    
    _0_gridCoberturas=Ext.create('Ext.grid.Panel',
    {
        title        : 'Sin plan'
        ,store       : _0_storeCoberturas
        ,height      : 300
        ,selType     : 'cellmodel'
        ,buttonAlign : 'center'
        ,buttons     : [ _0_botDetalleCobertura ]
        ,columns     :
        [
            {
                dataIndex : 'dsGarant'
                ,text : 'Cobertura'
                ,flex : 3
            }
            ,{
                dataIndex : 'sumaAsegurada'
                ,text : 'Suma asegurada'
                ,flex : 1
            }
            ,{
                dataIndex : 'deducible'
                ,text : 'Deducible'
                ,flex : 1
            }
        ]
        ,listeners   :
        {
            itemclick : function(dv, record, item,index, e)
            {
                var y = this.getSelectionModel().getCurrentPosition().row;
                var x = this.getSelectionModel().getCurrentPosition().column;
                if (x == 0)
                {
                	_0_selectedIdcobertura=record.get('cdGarant');
                    _0_botDetalleCobertura.setDisabled(false);
                }
                else
                {
                    _0_botDetalleCobertura.setDisabled(true);
                }
            }
        }
    });
    
    _0_windowCoberturas = new Ext.Window(
    {
        plain        : true
        ,width       : 500
        ,height      : 400
        ,modal       : true
        ,autoScroll  : true
        ,title       : 'Coberturas'
        ,layout      : 'fit'
        ,bodyStyle   : 'padding:5px;'
        ,buttonAlign : 'center'
        ,closeAction : 'hide'
        ,closable    : true
        ,items       : [ _0_gridCoberturas ] 
        ,buttons     :
        [
            {
            	text     : 'Regresar'
            	,icon    : '${ctx}/resources/fam3icons/icons/arrow_left.png'
            	,handler : function()
            	{
                    this.up().up().hide();
                }
            }
        ]
    });
    
    _0_fieldNtramite=Ext.create('Ext.form.field.Number',
	{
	    name        : 'ntramite'
	    ,fieldLabel : 'TR&Aacute;MITE'
	    ,hidden     : !(_0_smap1.ntramite&&_0_smap1.ntramite>0)
	    ,readOnly   : true
	    ,value      : _0_smap1.ntramite
	});
    
    _0_fieldNmpoliza=Ext.create('Ext.form.field.Number',
    {
        name        : 'nmpoliza'
        ,fieldLabel : 'COTIZACI&Oacute;N'
        ,readOnly   : true
    });
    
    _0_botCotizar=Ext.create('Ext.Button',
    {
        text     : _0_smap1.ntramite?'Precaptura':'Cotizar'
        ,icon    : '${ctx}/resources/fam3icons/icons/calculator.png'
        ,handler : _0_cotizar
    });
    
    _0_botCargar=Ext.create('Ext.Button',
    {
        text     : 'Cargar'
        ,icon    : '${ctx}/resources/fam3icons/icons/database_refresh.png'
        ,handler : _0_cargar
    });
    
    _0_botLimpiar=Ext.create('Ext.Button',
    {
        text     : 'Limpiar'
        ,icon    : '${ctx}/resources/fam3icons/icons/arrow_refresh.png'
        ,handler : _0_limpiar
    });
    
    _0_formAgrupados = new _0_FormAgrupados();
    _0_gridIncisos   = new _0_GridIncisos();
    
    //[parche] para AF y PU
    if(_0_smap1.cdtipsit=='AF' || _0_smap1.cdtipsit=='PU')
    {
        _0_gridIncisos.setTitle('Datos del contratante');
        _0_formAgrupados.down('[name=parametros.pv_otvalor03]').addListener('change',function()
        {
            debug('cleaning');
            _0_formAgrupados.down('[name=parametros.pv_otvalor04]').setValue('');
            _0_formAgrupados.down('[name=parametros.pv_otvalor05]').setValue('');
            _0_formAgrupados.down('[name=parametros.pv_otvalor06]').setValue('');
            _0_formAgrupados.down('[name=parametros.pv_otvalor07]').setValue('');
            _0_formAgrupados.down('[name=parametros.pv_otvalor26]').setValue('');
        });
        _0_formAgrupados.down('[name=parametros.pv_otvalor31]').addListener('change',function()
        {
            _0_formAgrupados.down('[name=parametros.pv_otvalor03]').setValue('');
        });
        _0_formAgrupados.down('[name=parametros.pv_otvalor08]').addListener('change',function()
        {
        	_0_formAgrupados.down('[name=parametros.pv_otvalor03]').setValue('');
        });
        _0_formAgrupados.down('[name=parametros.pv_otvalor03]').addListener('blur',function()
        {
        	if(Ext.isEmpty(_0_formAgrupados.down('[name=parametros.pv_otvalor08]').getValue()) || !_0_formAgrupados.down('[name=parametros.pv_otvalor08]').isValid()){
        		mensajeWarning('Debe de capturar primero el C&oacute;digo Postal');
                return;
        	}
        	
            var vim = this.value;
            if( (this.minLength > 0 && vim.length < this.minLength) || (vim.length < this.minLength || vim.length > this.maxLength) )
            {
            	if(this.minLength == this.maxLength) {
            		mensajeWarning('La longitud del n&uacute;mero de serie debe ser ' + this.minLength);
            	} else {
            		mensajeWarning('La longitud del n&uacute;mero de serie debe ser entre ' + this.minLength + ' y ' + this.maxLength);
            	}
                return;
            }
            debug('>llamando a nada:',vim);
            _0_formAgrupados.setLoading(true);
            Ext.Ajax.request(
            {
                url     : _0_urlNada
                ,params :
                {
                    'smap1.vim'       : vim
                    ,'smap1.cdramo'   : _0_smap1.cdramo
                    ,'smap1.cdtipsit' : _0_smap1.cdtipsit
                    ,'smap1.tipoveh'  : _0_formAgrupados.down('[name=parametros.pv_otvalor31]').getValue()
                    ,'smap1.codpos'   : _0_formAgrupados.down('[name=parametros.pv_otvalor08]').getValue()
                }
                ,success : function(response)
                {
                    _0_formAgrupados.setLoading(false);
                    var json = Ext.decode(response.responseText);
                    debug('nada response:', json);
                    if(json.success)
                    {
                        var precioDolar = _0_formAgrupados.down('[name=parametros.pv_otvalor24]').getValue()-0;
                        debug('precioDolar:',precioDolar);
                        _0_formAgrupados.down('[name=parametros.pv_otvalor04]').setValue(json.smap1.AUTO_MARCA);
                        _0_formAgrupados.down('[name=parametros.pv_otvalor05]').setValue(json.smap1.AUTO_ANIO);
                        _0_formAgrupados.down('[name=parametros.pv_otvalor06]').setValue(json.smap1.AUTO_DESCRIPCION);
                        _0_formAgrupados.down('[name=parametros.pv_otvalor07]').setMinValue(((json.smap1.AUTO_PRECIO*precioDolar).toFixed(2))*(1-(json.smap1.FACTOR_MIN-0)));
                        _0_formAgrupados.down('[name=parametros.pv_otvalor07]').setMaxValue(((json.smap1.AUTO_PRECIO*precioDolar).toFixed(2))*(1+(json.smap1.FACTOR_MAX-0)));
                        _0_formAgrupados.down('[name=parametros.pv_otvalor07]').setValue((json.smap1.AUTO_PRECIO*precioDolar).toFixed(2));
                        _0_formAgrupados.down('[name=parametros.pv_otvalor26]').setValue((json.smap1.AUTO_PRECIO*precioDolar).toFixed(2));
                        debug('set min value:',((json.smap1.AUTO_PRECIO*precioDolar).toFixed(2))*(1-(json.smap1.FACTOR_MIN-0)));
                        debug('set max value:',((json.smap1.AUTO_PRECIO*precioDolar).toFixed(2))*(1+(json.smap1.FACTOR_MAX-0)));
                        
                        Ext.Ajax.request({
            				url     : _0_urlObtieneValNumeroSerie
            				,params :
            				{
            					'smap1.numSerie'  :  _0_formAgrupados.down('[name=parametros.pv_otvalor03]').getValue()
            					,'smap1.feini'   :  Ext.getCmp('fechaInicioVigencia').getValue()
            				}
            				,success : function(response)
            				{
            					var json=Ext.decode(response.responseText);
            					debug(json);
                    	    	if(json.exito!=true)
                    	    	{
                    	    		if(_0_smap1.cdsisrol!='SUSCRIAUTO'){
                    	    			mensajeValidacionNumSerie("Error","${ctx}/resources/fam3icons/icons/exclamation.png", json.respuesta);
                    				}else{
                    					mensajeValidacionNumSerie("Aviso","${ctx}/resources/fam3icons/icons/error.png", json.respuesta);
                    				}
                    	    	}
            				}
            				,failure : errorComunicacion
            			});
                    }
                    else
                    {
                    	//parche para RAMO 16 (FRONTERIZOS) con rol SUSCRIPTOR AUTO, no se lanza la validación:
                    	if(_0_smap1.cdramo == Ramo.AutosFronterizos && _0_smap1.cdsisrol == 'SUSCRIAUTO') {
                    	    // Si no obtuvo datos el servicio "NADA", reseteamos valores:
                    		_0_formAgrupados.down('[name=parametros.pv_otvalor04]').setValue();
                            _0_formAgrupados.down('[name=parametros.pv_otvalor05]').setValue();
                            _0_formAgrupados.down('[name=parametros.pv_otvalor06]').setValue();
                            _0_formAgrupados.down('[name=parametros.pv_otvalor07]').setValue();
                            _0_formAgrupados.down('[name=parametros.pv_otvalor07]').setMinValue();
                            _0_formAgrupados.down('[name=parametros.pv_otvalor07]').setMaxValue();
                            _0_formAgrupados.down('[name=parametros.pv_otvalor26]').setValue();
                    	} else {
                    		mensajeError(json.error);
                    	}
                    }
                }
                ,failure : function()
                {
                    _0_formAgrupados.setLoading(false);
                    debug("Entra a esta parte");
                    errorComunicacion();
                }
            });
            debug('<llamando a nada');            
        });
        var comboTipoValor =_0_formAgrupados.down('[name=parametros.pv_otvalor02]');
        var itemSumaAsegu  =_0_formAgrupados.down('[name=parametros.pv_otvalor07]');
        var changeFunction = function()
        {
            debug('>comboTipoValor change');
            if(_0_smap1.cdsisrol!='SUSCRIAUTO'){
            	itemSumaAsegu.setValue('');
                itemSumaAsegu.setReadOnly((comboTipoValor.getValue()+'x')=='2x');
            }
            debug('<comboTipoValor change');
        };
        comboTipoValor.addListener('change',changeFunction);
        changeFunction();
        
        Ext.Ajax.request(
        {
            url      : _0_urlObtenerParametros
            ,params  :
            {
                'smap1.parametro' : 'RANGO_ANIO_MODELO'
                ,'smap1.cdramo'   : _0_smap1.cdramo
                ,'smap1.cdtipsit' : _0_smap1.cdtipsit
                ,'smap1.clave4'   : _0_smap1.cdsisrol
            }
            ,success : function(response)
            {
                var json=Ext.decode(response.responseText);
                debug('### obtener rango años response:',json);
                if(json.exito)
                {
                    var limiteInferior = json.smap1.P1VALOR-0;
                    var limiteSuperior = json.smap1.P2VALOR-0;
                    _0_formAgrupados.down('[name=parametros.pv_otvalor05]').validator=function(value)
                    {
                        var r = true;
                        var anioActual = new Date().getFullYear();
                        var max = anioActual+limiteSuperior;
                        var min = anioActual+limiteInferior;
                        debug('limiteInferior:',limiteInferior);
                        debug('limiteSuperior:',limiteSuperior);
                        debug('anioActual:',anioActual);
                        debug('max:',max);
                        debug('min:',min);
                        debug('value:',value);
                        if(value<min||value>max)
                        {
                            r='El modelo debe estar en el rango '+min+'-'+max;
                        }
                        return r;
                    };
                }
                else
                {
                    mensajeError(json.respuesta);
                }
            }
            ,failure : function()
            {
                errorComunicacion();
            }
        });
        
        if(_0_smap1.cdsisrol=='EJECUTIVOCUENTA')
        {
            _0_recuperarDescuento();
        }
        else
        {
            _fieldLikeLabel('AGENTE').on(
            {
                select : function()
                {
                    _0_recuperarDescuento();
                }
            });
        }
    }
    //fin [parche]
    
    //parche para AUTOS FRONTERIZOS Y PICKUP Fronterizos con rol SUSCRIPTOR AUTO:
    if((_0_smap1.cdtipsit == TipoSituacion.AutosFronterizos || _0_smap1.cdtipsit == TipoSituacion.AutosPickUp) 
        && _0_smap1.cdsisrol=='SUSCRIAUTO') {
        _0_formAgrupados.down('[name=parametros.pv_otvalor04]').setReadOnly(false);
        _0_formAgrupados.down('[name=parametros.pv_otvalor05]').setReadOnly(false);
        _0_formAgrupados.down('[name=parametros.pv_otvalor06]').setReadOnly(false);
        _0_formAgrupados.down('[name=parametros.pv_otvalor07]').setReadOnly(false);
        _0_formAgrupados.down('[name=parametros.pv_otvalor26]').setReadOnly(false);
    }
    
    <s:if test='%{getSmap1().get("CDATRIBU_DERECHO")!=null}'>
        var items=_0_formAgrupados.items.items;
        debug('items a reordenar:',items);
        var cdatribus_derechos=_0_smap1.CDATRIBU_DERECHO.split(',');
        debug('cdatribus_derechos:',cdatribus_derechos);
        var itemsIzq=[];
        var itemsDer=[];
        for(var i=0;i<items.length;i++)
        {
            var iItem=items[i];
            debug('item revisado:',iItem);
            var indexOfIItem=$.inArray(iItem.cdatribu,cdatribus_derechos);
            debug('indexOfIItem:',indexOfIItem);
            if(indexOfIItem==-1)
            {
                debug('izquierdo');
                itemsIzq.push(iItem);
            }
            else
            {
                debug('derecho');
                itemsDer.push(iItem);
            }
        }
        _0_formAgrupados.removeAll(false);
        _0_formAgrupados.layout='hbox';
        _0_formAgrupados.add(
        [
            {
                xtype  : 'fieldset'
                ,title : '<span style="font:bold 14px Calibri;">DATOS GENERALES</span>'
                ,items : itemsIzq
            }
            ,{
                xtype  : 'fieldset'
                ,title : '<span style="font:bold 14px Calibri;">DATOS DE COBERTURAS</span>'
                ,items : itemsDer
            }
        ]);
    </s:if>
    _0_botonera      = new _0_Botonera();
    _0_panelPri      = new _0_PanelPri();
    /*///////////////////*/
    ////// contenido //////
    ///////////////////////
    
    //////////////////////
    ////// cargador //////
    /*//////////////////*/
    /*//////////////////*/
    ////// cargador //////
    //////////////////////
    
    //[parche] para ramo 6
    if(_0_smap1.cdramo+'x'=='6x')
    {
        debug('>parche para ramo 6');
        
        //negocio
        _fieldLikeLabel('NEGOCIO').on(
        {
            focus : function()
            {
                var valido = !Ext.isEmpty(_fieldByLabel('TIPO DE UNIDAD').getValue());
                if(!valido)
                {
                    mensajeWarning('Seleccione el tipo de unidad');
                }
                
                if(valido)
                {
                    valido = !Ext.isEmpty(_fieldByLabel('AGENTE').getValue());
                    if(!valido)
                    {
                        mensajeWarning('Seleccione el agente');
                    }
                }
                
                if(valido)
                {
                    _fieldLikeLabel('NEGOCIO').setLoading(true);
                    _fieldLikeLabel('NEGOCIO').getStore().load(
                    {
                        params :
                        {
                            'params.tipoUnidad' : _fieldByLabel('TIPO DE UNIDAD').getValue()
                            ,'params.cdagente'  : _fieldByLabel('AGENTE').getValue()
                        }
                        ,callback : function()
                        {
                            _fieldLikeLabel('NEGOCIO').setLoading(false);
                        }
                    });
                }
            }
        });
        
        //descuento
        _fieldLikeLabel('DESCUENTO').on(
        {
            focus : function()
            {
                var valido = !Ext.isEmpty(_fieldByLabel('TIPO DE UNIDAD').getValue());
                if(!valido)
                {
                    mensajeWarning('Seleccione el tipo de unidad');
                }
                
                if(valido)
                {
                    valido = !Ext.isEmpty(_fieldByLabel('TIPO DE USO').getValue());
                    if(!valido)
                    {
                        mensajeWarning('Seleccione el tipo de uso');
                    }
                }
                
                if(valido)
                {
                    valido = !Ext.isEmpty(_fieldByLabel('AGENTE').getValue());
                    if(!valido)
                    {
                        mensajeWarning('Seleccione el agente');
                    }
                }
                
                if(valido)
                {
                    _fieldLikeLabel('DESCUENTO').setLoading(true);
                    _fieldLikeLabel('DESCUENTO').getStore().load(
                    {
                        params :
                        {
                            'params.tipoUnidad' : _fieldByLabel('TIPO DE UNIDAD').getValue()
                            ,'params.uso'       : _fieldByLabel('TIPO DE USO').getValue()
                            ,'params.cdagente'  : _fieldByLabel('AGENTE').getValue()
                            ,'params.cdtipsit'  : _0_smap1.cdtipsit
                            ,'params.cdatribu'  : '21'
                        }
                        ,callback : function()
                        {
                            _fieldLikeLabel('DESCUENTO').setLoading(false);
                        }
                    });
                }
            }
        });
        
        //modelo
        if(_0_smap1.cdtipsit+'x'=='MCx')
        {
            _fieldByLabel('MODELO').setLoading(true);
            Ext.Ajax.request(
            {
                url      : _0_urlObtenerParametros
                ,params  :
                {
                    'smap1.parametro' : 'RANGO_ANIO_MODELO'
                    ,'smap1.cdramo'   : _0_smap1.cdramo
                    ,'smap1.cdtipsit' : _0_smap1.cdtipsit
                }
                ,success : function(response)
                {
                    _fieldByLabel('MODELO').setLoading(false);
                    var json=Ext.decode(response.responseText);
                    debug('respuesta json obtener rango:',json);
                    if(json.exito)
                    {
                        _fieldByLabel('MODELO').setValue(json.smap1.P1VALOR);
                        _fieldByLabel('MODELO').setMinValue(json.smap1.P2VALOR);
                        _fieldByLabel('MODELO').setMaxValue(json.smap1.P3VALOR);
                    }
                    else
                    {
                        mensajeError(json.respuesta);
                    }
                }
                ,failure : function()
                {
                    _fieldByLabel('MODELO').setLoading(false);
                    errorComunicacion();
                }
            });
        }
        
        //banco -> meses
        _fieldByName('parametros.pv_otvalor18').on(
        {
            'select' : function()
            {
                if(_fieldByName('parametros.pv_otvalor18').getValue()+'x'=='0x')
                {
                    _fieldByName('parametros.pv_otvalor19').allowBlank=true;
                }
                else
                {
                    _fieldByName('parametros.pv_otvalor19').allowBlank=false;
                }
                _fieldByName('parametros.pv_otvalor19').isValid();
            }
        });
        
        _0_gridIncisos.setTitle('Datos del contratante');
        
        var agente = _fieldByName('parametros.pv_otvalor17');
        var folio  = _fieldByName('parametros.pv_otvalor16');
        
        //agente
        if((_0_smap1.cdsisrol=='PROMOTORAUTO'
            ||_0_smap1.cdsisrol=='SUSCRIAUTO')
            &&Ext.isEmpty(_0_smap1.ntramite)
            )
        {
            agente.on(
            {
                'select' : function(comp,records)
                {
                    folio.reset();
                    Ext.Ajax.request(
                    {
                        url     : _0_urlCargarCduniecoAgenteAuto
                        ,params :
                        {
                            'smap1.cdagente' : records[0].get('key')
                        }
                        ,success : function(response)
                        {
                            var json=Ext.decode(response.responseText);
                            debug('obtener cdunieco agente response:',json);
                            if(json.exito)
                            {
                                _0_smap1.cdunieco=json.smap1.cdunieco;
                                debug('_0_smap1:',_0_smap1);
                            }
                            else
                            {
                                mensajeError(json.respuesta);
                            }
                        }
                        ,failure : errorComunicacion
                    });
                }
            });
        }
        
        //folio
        debug('folio:',folio);
        folio.on(
        {
            'change' : function(comp,val)
            {
                debug('folio change val:',val,'dummy');
                if(_0_smap1.cdsisrol+'x'=='PROMOTORAUTOx')
                {
                    agente.setReadOnly(!Ext.isEmpty(val));
                    agente.reset();
                }
            }
            ,'blur' : function()
            {
                if(!Ext.isEmpty(folio.getValue())&&folio.getValue()>0)
                {
                    _0_panelPri.setLoading(true);
                    Ext.Ajax.request(
                    {
                        url      : _0_urlCargarAgentePorFolio
                        ,params  :
                        {
                            'smap1.folio'     : folio.getValue()
                            ,'smap1.cdunieco' : _0_smap1.cdunieco
                        }
                        ,success : function(response)
                        {
                            _0_panelPri.setLoading(false);
                            var json=Ext.decode(response.responseText);
                            debug('json response obtener agente por folio:',json);
                            if(json.exito)
                            {
                                if(_0_smap1.cdsisrol+'x'=='PROMOTORAUTOx')
                                {
                                    var contiene=false;
                                    agente.getStore().each(function(record)
                                    {
                                        debug('buscando agente',json.smap1.cdagente,'en',record.data);
                                        if(record.get('key')==json.smap1.cdagente)
                                        {
                                            contiene=true;
                                        }
                                    });
                                    if(contiene)
                                    {
                                        agente.setValue(json.smap1.cdagente);
                                    }
                                    else
                                    {
                                        mensajeWarning('El agente '+json.smap1.cdagente+' no se encuentra en la lista del promotor/suscriptor');
                                        agente.reset();
                                    }
                                }
                                //para suscriptor y agente
                                else
                                {
                                    //agente
                                    if(_0_smap1.cdsisrol+'x'=='EJECUTIVOCUENTAx')
                                    {
                                        if(json.smap1.cdagente!=agente.getValue())
                                        {
                                            mensajeWarning('El folio pertenece a otro agente');
                                            folio.reset();
                                            folio.focus();
                                        }
                                    }
                                    //suscriptor
                                    else
                                    {
                                        agente.getStore().load(
                                        {
                                            params :
                                            {
                                                'params.agente' : json.smap1.cdagente 
                                            }
                                            ,callback : function(records)
                                            {
                                               debug('callback records:',records,records.length);
                                               if(_fieldByLabel('AGENTE').findRecord('key',json.smap1.cdagente)){
                                            	   agente.setValue(json.smap1.cdagente);
                                               }else{
                                            	   mensajeWarning('El agente '+json.smap1.cdagente+' no se encuentra en la lista del promotor/suscriptor');
                                            	   folio.reset();
                                            	   agente.reset();
                                               }
                                            }
                                        });
                                    }
                                }
                            }
                            else
                            {
                                mensajeError(json.respuesta);
                                folio.reset();
                                agente.reset();
                            }
                        }
                        ,failure : function(response)
                        {
                            _0_panelPri.setLoading(false);
                            errorComunicacion();
                        }
                    });
                }
            }
        });
        
        //tipo de unidad
        if(_0_smap1.cdtipsit+'x'=='MCx')
        {
            _fieldByName('parametros.pv_otvalor01').on(
            {
                'select' : function(comp,valArray)
                {
                    debug('tipo unidad select value:',valArray[0].data.key);
                    _0_panelPri.setLoading(true);
                    Ext.Ajax.request(
                    {
                        url      : _0_urlObtenerParametros
                        ,params  :
                        {
                            'smap1.parametro' : 'NUMERO_PASAJEROS_SERV_PUBL'
                            ,'smap1.cdramo'   : _0_smap1.cdramo
                            ,'smap1.cdtipsit' : _0_smap1.cdtipsit
                            ,'smap1.clave4'   : valArray[0].data.key
                            ,'smap1.clave5'   : _0_smap1.cdsisrol
                        }
                        ,success : function(response)
                        {
                            _0_panelPri.setLoading(false);
                            var json=Ext.decode(response.responseText);
                            debug('json response numero pasajeros:',json);
                            if(json.exito)
                            {
                                _fieldByName('parametros.pv_otvalor04').setValue(json.smap1.P1VALOR);
                                _fieldByName('parametros.pv_otvalor04').setMinValue(json.smap1.P2VALOR);
                                _fieldByName('parametros.pv_otvalor04').setMaxValue(json.smap1.P3VALOR);
                                _fieldByName('parametros.pv_otvalor22').setValue(json.smap1.P4VALOR);
                                
                                _fieldByName('parametros.pv_otvalor04').isValid();
                                _fieldByName('parametros.pv_otvalor22').isValid();
                            }
                            else
                            {
                                mensajeWarning(json.respuesta);
                            }
                        }
                        ,failure : function()
                        {
                            _0_panelPri.setLoading(false);
                            errorComunicacion();
                        }
                    });
                }
            });
        }
        
        _fieldByName('fefin').setValue('');
        
        _fieldByName('feini').removeListener('change',_0_funcionFechaChange);
        
        _fieldByName('feini').on(
        {
            change : function()
            {
                if(Ext.isEmpty(_fieldByName('parametros.pv_otvalor20').getValue()))
                {
                    mensajeWarning('Favor de capturar la vigencia');
                }
                else
                {
                    _fieldByName('fefin').setValue(
                        Ext.Date.add(
                            _fieldByName('feini').getValue()
                            ,Ext.Date.MONTH
                            ,_fieldByName('parametros.pv_otvalor20').getValue()
                        )
                    );
                }
            }
        });
        
        _fieldByName('parametros.pv_otvalor20').getStore().on(
        {
            load : function()
            {
                _fieldByName('fefin').setValue(
                    Ext.Date.add(
                        _fieldByName('feini').getValue()
                        ,Ext.Date.MONTH
                        ,_fieldByName('parametros.pv_otvalor20').getValue()
                    )
                );
            }
        });
        
        _fieldByName('parametros.pv_otvalor20').addListener('change',function()
        {
            _fieldByName('fefin').setValue(
                Ext.Date.add(_fieldByName('feini').getValue(),Ext.Date.MONTH,_fieldByName('parametros.pv_otvalor20').getValue())
            );
        });
        
        var combcl=_fieldByName('parametros.pv_otvalor24');
        var codpos=_fieldByName('parametros.pv_otvalor23');
        
        
        combcl.on('change',function()
        {
            debug('Combo cliente nuevo change:',combcl.getValue());
            //cliente nuevo
            if(combcl.getValue()=='S')
            {
                codpos.reset();
                _0_storeIncisos.removeAll();
                _0_gridIncisos.show();
                codpos.setReadOnly(false);
                codpos.setFieldLabel('C&Oacute;DIGO POSTAL CLIENTE');
                _0_recordClienteRecuperado=null;
            }
            //recuperar cliente
            else if(combcl.getValue()=='N')
            {
                codpos.reset();
                _0_storeIncisos.removeAll();
                _0_gridIncisos.hide();
                codpos.setReadOnly(true);
                var ventana=Ext.create('Ext.window.Window',
                {
                    title      : 'Recuperar cliente'
                    ,modal     : true
                    ,width     : 600
                    ,height    : 400
                    ,items     :
                    [
                        {
                            layout    : 'hbox'
                            ,defaults : { style : 'margin : 5px;' }
                            ,items    :
                            [
                                {
                                    xtype       : 'textfield'
                                    ,name       : '_0_recuperaRfc'
                                    ,fieldLabel : 'RFC'
                                    ,minLength  : 9
                                    ,maxLength  : 13
                                }
                                ,{
                                    xtype    : 'button'
                                    ,text    : 'Buscar'
                                    ,icon    : '${ctx}/resources/fam3icons/icons/zoom.png'
                                    ,handler : function(button)
                                    {
                                        debug('recuperar cliente buscar');
                                        var rfc=_fieldByName('_0_recuperaRfc').getValue();
                                        var valido=true;
                                        if(valido)
                                        {
                                            valido = !Ext.isEmpty(rfc)
                                                     &&rfc.length>8
                                                     &&rfc.length<14;
                                            if(!valido)
                                            {
                                                mensajeWarning('Introduza un RFC v&aacute;lido');
                                            }
                                        }
                                        if(valido)
                                        {
                                            button.up('window').down('grid').getStore().load(
                                            {
                                                params :
                                                {
                                                    'map1.pv_rfc_i'     : rfc,
                                                    'map1.cdtipsit'     : _0_smap1.cdtipsit,
                                                    'map1.pv_cdtipsit_i': _0_smap1.cdtipsit,
                                    			    'map1.pv_cdunieco_i': _0_smap1.cdunieco,
                                               		'map1.pv_cdramo_i'  : _0_smap1.cdramo,
                                               		'map1.pv_estado_i'  : 'W',
                                               		'map1.pv_nmpoliza_i': _0_fieldNmpoliza.getValue()
                                                }
                                            });
                                        }
                                    }
                                }
                            ]
                        }
                        ,Ext.create('Ext.grid.Panel',
                        {
                            title    : 'Resultados'
                            ,columns :
                            [
                                {
                                    xtype    : 'actioncolumn'
                                    ,width   : 30
                                    ,icon    : '${ctx}/resources/fam3icons/icons/accept.png'
                                    ,handler : function(view,row,col,item,e,record)
                                    {
                                        debug('recuperar cliente handler record:',record);
                                        _0_recordClienteRecuperado=record;
                                        codpos.setValue(record.raw.CODPOSTAL);
                                        codpos.setFieldLabel('C&Oacute;DIGO POSTAL CLIENTE<br/>('+record.get('NOMBRECLI')+')');
                                        ventana.destroy();
                                    }
                                }
                                ,{
                                    text       : 'Nombre'
                                    ,dataIndex : 'NOMBRECLI'
                                    ,width     : 200
                                }
                                ,{
                                    text       : 'Direcci&oacute;n'
                                    ,dataIndex : 'DIRECCIONCLI'
                                    ,flex      : 1
                                }
                            ]
                            ,store : Ext.create('Ext.data.Store',
                            {
                                model     : '_0_modeloRecuperado'
                                ,autoLoad : false
                                ,proxy    :
                                {
                                    type    : 'ajax'
                                    ,url    : _0_urlRecuperarCliente
                                    ,timeout: 240000
                                    ,reader :
                                    {
                                        type  : 'json'
                                        ,root : 'slist1'
                                    }
                                }
                            })
                        })
                    ]
                    ,listeners :
                    {
                        close : function()
                        {
                            combcl.setValue('S');
                        }
                    }
                }).show();
                centrarVentanaInterna(ventana);
            }
        });
        
        combcl.getStore().on('load',function()
        {
            debug('combo cliente nuevo store load');
            combcl.setValue('S');
        });
        combcl.setValue('S');
        
        //modelo
        if(_0_smap1.cdtipsit+'x'=='ATx')
        {
            _fieldByLabel('MODELO').on(
            {
                select : function()
                {
                    _0_obtenerClaveGSPorAuto();
                    _0_obtenerSumaAseguradaRamo6(false);
                }
            });
        }
        
        //version
        if(_0_smap1.cdtipsit+'x'=='ATx')
        {
            _fieldByLabel('VERSION').on(
            {
                'select' : _0_obtenerClaveGSPorAuto
            });
        }
        
        //auto combo
        if(_0_smap1.cdtipsit+'x'=='ATx')
        {
            _fieldByName('parametros.pv_otvalor22').on(
            {
                'select' : function(comp,arr)
                {
                    debug('auto seleccionado:',arr[0]);
                    var value    = arr[0].get('value');
                    var splt     = value.split(' - ');
                    var tipo     = splt[1];
                    var marca    = splt[2];
                    var submarca = splt[3];
                    var modelo   = splt[4];
                    var version  = splt[5];
                    debug('tipo:',tipo);
                    debug('marca:',marca);
                    debug('submarca:',submarca);
                    debug('modelo:',modelo);
                    debug('version:',version);
                    
                    _fieldByLabel('TIPO DE UNIDAD').setValue(_fieldByLabel('TIPO DE UNIDAD').findRecord('value',tipo));
                    _fieldByLabel('MARCA').heredar(true,function()
                    {
                        _fieldByLabel('MARCA').setValue(_fieldByLabel('MARCA').findRecord('value',marca));
                        _fieldByLabel('SUBMARCA').heredar(true,function()
                        {
                            _fieldByLabel('SUBMARCA').setValue(_fieldByLabel('SUBMARCA').findRecord('value',submarca));
                            _fieldByLabel('MODELO').heredar(true,function()
                            {
                                _fieldByLabel('MODELO').setValue(_fieldByLabel('MODELO').findRecord('value',modelo));
                                _fieldByLabel('VERSION').heredar(true,function()
                                {
                                    _fieldByLabel('VERSION').setValue(_fieldByLabel('VERSION').findRecord('value',version));    
                                    _0_obtenerSumaAseguradaRamo6(true);                        
                                });
                            });
                        });
                    });
                    
                    _0_cargarNumPasajerosAuto();
                }
            });
        }
        
        //version
        if(_0_smap1.cdtipsit+'x'=='ATx')
        {
            _fieldByLabel('VERSION').on(
            {
                'select' : function()
                {
                    _0_obtenerSumaAseguradaRamo6(true);
                }
            });
        }
        
        debug('<parche para ramo 6');
    }
    
    // Se busca la imagen para mostrar en el cotizador segun el producto:
    Ext.Ajax.request({
        url    : _0_urlObtenerParametros,
        params :{
            'smap1.parametro' : 'IMAGEN_COTIZACION',
            'smap1.cdramo'   : _0_smap1.cdramo,
            'smap1.cdtipsit' : _0_smap1.cdtipsit
        },
        success : function(response) {
            var json=Ext.decode(response.responseText);
            debug('########## Respuesta:',json);
            if(json.exito) {
                Ext.create('Ext.window.Window', {
                    focusOnToFront : false, //evitamos que obtenga el focus en automatico
                    closable : false,
                    header: false,
                    border: false,
                    resizable: false,
                    width: Number(json.smap1.P2VALOR),
                    height: Number(json.smap1.P3VALOR),
                    items: [{
                        xtype : 'image',
                        src : '${ctx}/images/proceso/cotizacion/'+json.smap1.P1VALOR
                    }]
                }).showAt(Number(json.smap1.P4VALOR), Number(json.smap1.P5VALOR));
           }
        }
    });
    
    if(_0_smap1.ntramite&&_0_smap1.ntramite.length>0)
    {
    	Ext.create('Ext.window.Window',
    	{
            title           : 'Documentos del tr&aacute;mite ' + _0_smap1.ntramite
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
                ,url      : _0_urlVentanaDocumentos
                ,params   :
                {
                    'smap1.cdunieco'  : _0_smap1.cdunieco
                    ,'smap1.cdramo'   : _0_smap1.cdramo
                    ,'smap1.estado'   : 'W'
                    ,'smap1.nmpoliza' : '0'
                    ,'smap1.nmsuplem' : '0'
                    ,'smap1.nmsolici' : '0'
                    ,'smap1.ntramite' : _0_smap1.ntramite
                    ,'smap1.tipomov'  : '0'
                }
            }
        }).showAt(450, 50);
    }
    
    if(_0_formAgrupados.items.items[2])
    {
        _0_formAgrupados.items.items[2].focus();
    }
    else if(_0_formAgrupados.items.items[0].items.items[2])
    {
        _0_formAgrupados.items.items[0].items.items[2].focus();
        debug('_0_formAgrupados.items.items[0].items.items[2].focus();',_0_formAgrupados.items.items[0].items.items[2]);
    }
    else
    {
        debug('no focus:',_0_formAgrupados.items.items[0].items.items);
    }
    
    //obtener minimos y maximo
    /*
    _0_panelPri.setLoading(true);
    Ext.Ajax.request(
    {
        url      : _0_urlObtenerParametros
        ,params  :
        {
            'smap1.parametro' : 'MINIMOS_Y_MAXIMOS'
            ,'smap1.cdramo'   : _0_smap1.cdramo
            ,'smap1.cdtipsit' : _0_smap1.cdtipsit
        }
        ,success : function(response)
        {
            _0_panelPri.setLoading(false);
            var json=Ext.decode(response.responseText);
            debug('respuesta json obtener minimos y maximos:',json);
            if(json.exito)
            {
                for(var i=1;i<=13;i=i+2)
                {
                    if(json.smap1['P'+i+'CLAVE']=='CDATRIBU')
                    {
                        _fieldByName('parametros.pv_otvalor'+(('00'+json.smap1['P'+i+'VALOR']).slice(-2))).setMinValue(json.smap1['P'+(i+1)+'CLAVE']);
                        _fieldByName('parametros.pv_otvalor'+(('00'+json.smap1['P'+i+'VALOR']).slice(-2))).setMaxValue(json.smap1['P'+(i+1)+'VALOR']);
                    }
                }
            }
            else
            {
                debug('### ERROR:',json.respuesta);
            }
        }
        ,failure : function()
        {
            _0_panelPri.setLoading(false);
            errorComunicacion();
        }
    });
    */
    
    //VIGENCIA
    if(_0_smap1.SITUACION=='PERSONA')
    {
        Ext.Ajax.request(
        {
            url      : _0_urlRetroactividadDifer
            ,params  :
            { 
                'smap1.cdunieco'  : _0_smap1.cdunieco
                ,'smap1.cdramo'   : _0_smap1.cdramo
                ,'smap1.cdtipsup' : '1'
                ,'smap1.cdusuari' : _0_smap1.cdusuari
                ,'smap1.cdtipsit' : _0_smap1.cdtipsit
            }
            ,success : function(response)
            {
                _0_panelPri.setLoading(false);
                var ck = 'Recuperando retroactividad';
                try
                {
                    var json=Ext.decode(response.responseText);
                    debug('### respuesta obtener rango vigencia:',json);
                    if(json.exito)
                    {
                        _fieldByName('feini').setMinValue(Ext.Date.add(new Date(),Ext.Date.DAY,json.smap1.retroac*-1));
                        _fieldByName('feini').setMaxValue(Ext.Date.add(new Date(),Ext.Date.DAY,json.smap1.diferi));
                    }
                    else
                    {
                        mensajeWarning('Falta definir rango de vigencia para el producto (RANGOVIGENCIA)');
                    }
                }
                catch(e)
                {
                    manejaException(e,ck);
                }
            }
            ,failure : function()
            {
                _0_panelPri.setLoading(false);
                errorComunicacion();
            }
        });
    }
    //VIGENCIA
   
    //para gmi
    if(_0_smap1.cdramo=='7'&&_0_smap1.cdtipsit=='GMI')
    {
        _fieldLikeLabel('POSTAL').on(
        {
            select : _0_gmiPostalSelect
        });
        
        _fieldLikeLabel('CULO HOSPITALARIO').on(
        {
            select : _0_gmiCirchospSelect
        });
    }
    
    // Para TODOS LOS PRODUCTOS (si aplican), se agrega validacion de Codigo Postal vs Estado:
    agregaValidacionCPvsEstado();

});
</script>
</head>
<body>
<div id="_0_divPri" style="height:1100px;"></div>
</body>
</html>