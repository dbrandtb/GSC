<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script>
////// urls //////
var _p38_urlCargarSumaAseguradaRamo5 = '<s:url namespace="/emision" action="cargarSumaAseguradaRamo5"    />';
var _p38_urlCargarParametros         = '<s:url namespace="/emision" action="obtenerParametrosCotizacion" />';
var _p38_urlConfirmarEndoso          = '<s:url namespace="/endosos" action="guardarEndosoClaveAuto"      />';
var _p38_urlRecuperacionSimple       = '<s:url namespace="/emision" action="recuperacionSimple"          />';

var _p38_urlNadaEndoso               = '<s:url namespace="/emision" action="webServiceNadaEndosos"              />';
var _p38_urlObtieneValNumeroSerie    = '<s:url namespace="/emision" action="obtieneValNumeroSerie"       />';

var _0_urlCargarSumaAsegurada      = '<s:url namespace="/emision"         action="cargarSumaAseguradaAuto"        />';
var _0_urlCargarAutoPorClaveGS     = '<s:url namespace="/emision"         action="cargarAutoPorClaveGS"           />';

////// urls //////

////// variables //////
var _p38_smap1  = <s:property value="%{convertToJSON('smap1')}"  escapeHtml="false" />;
var _p38_slist1 = <s:property value="%{convertToJSON('slist1')}" escapeHtml="false" />;
var _p38_flujo  = <s:property value="%{convertToJSON('flujo')}"  escapeHtml="false" />;
debug('_p38_smap1:'  , _p38_smap1);
debug('_p38_slist1:' , _p38_slist1);
debug('_p38_flujo:'  , _p38_flujo);

// var rolesSuscriptores = '|SUSCRIAUTO|TECNISUSCRI|EMISUSCRI|JEFESUSCRI|GERENSUSCRI|SUBDIRSUSCRI|';

////// variables //////

////// overrides //////
////// overrides //////

////// dinamicos //////
var _p38_items = [ <s:property value="imap.items" escapeHtml="false" /> ];
////// dinamicos //////

Ext.onReady(function()
{
	
	Ext.Ajax.timeout = 1*60*60*1000; // 1 hora
	
	
    ////// modelos //////
    ////// modelos //////
    
    ////// stores //////
    ////// stores //////
    
    ////// componentes //////
    ////// componentes //////
    
    ////// contenido //////
    _38_formAuto = Ext.create('Ext.form.Panel',
    {
        renderTo  : '_p38_divpri'
        ,itemId   : '_p38_form'
        ,border   : 0
        ,defaults : { style : 'margin:5px;' }
        ,items    :
        [
            {
                xtype  : 'fieldset'
                ,title : '<span style="font:bold 14px Calibri;">Datos del veh&iacute;culo</span>'
                ,items : _p38_items
            }
            ,{
                xtype  : 'fieldset'
                ,title : '<span style="font:bold 14px Calibri;">Datos del endoso</span>'
                ,items :
                [
                    {
                        xtype       : 'datefield'
                        ,format     : 'd/m/Y'
                        ,value      : new Date()
                        ,fieldLabel : 'Fecha de efecto'
                        ,name       : 'feefecto'
                    }
                ]
            }
        ]
        ,buttonAlign : 'center'
        ,buttons     :
        [
            {
                itemId   : '_p38_botonConfirmar'
                ,text    : 'Confirmar'
                ,icon    : '${ctx}/resources/fam3icons/icons/key.png'
                ,handler : function(me)
                {
                    if(!me.up('form').getForm().isValid())
                    {
                        datosIncompletos();
                        return;
                    }
                    
                    me.setDisabled(true);
                    me.setText('Cargando...');
                    
                    var jsonConfirmar =
                    {
                        smap1   : _p38_smap1
                        ,smap2  : me.up('form').getValues()
                        ,slist1 : _p38_slist1
                    };
                    
                    if(!Ext.isEmpty(_p38_flujo))
                    {
                        jsonConfirmar.flujo = _p38_flujo;
                    }
                    
                    debug('jsonConfirmar:',jsonConfirmar);
                    
                    Ext.Ajax.request(
                    {
                        url       : _p38_urlConfirmarEndoso
                        ,jsonData : jsonConfirmar
                        ,success  : function(response)
                        {
                            me.setText('Confirmar');
                            me.setDisabled(false);
                            var json = Ext.decode(response.responseText);
                            debug('### confirmar:',json);
                            if(json.success)
                            {
                                var callbackRemesa = function()
                                {
                                    marendNavegacion(2);
                                };
                                mensajeCorrecto('Endoso generado','Endoso generado',function()
                                {
                                    _generarRemesaClic(
                                        true
                                        ,_p38_smap1.CDUNIECO
                                        ,_p38_smap1.CDRAMO
                                        ,_p38_smap1.ESTADO
                                        ,_p38_smap1.NMPOLIZA
                                        ,callbackRemesa
                                    );
                                });
                            }
                            else
                            {
                                mensajeError(json.respuesta);
                            }
                        }
                        ,failure  : function()
                        {
                            me.setText('Confirmar');
                            me.setDisabled(false);
                            errorComunicacion();
                        }
                    });
                }
            }
        ]
    });
    ////// contenido //////
    
    ////// custom //////
    if(_p38_slist1[0].CDTIPSIT != 'AF' && _p38_slist1[0].CDTIPSIT != 'PU' && _p38_slist1[0].CDTIPSIT != 'MC'){
	    _fieldLikeLabel('CLAVE').on(
	    {
	        select : function(me,records)
	        {
	            var marcaCmp    = _fieldByLabel('MARCA');
	            var submarcaCmp = _fieldByLabel('SUBMARCA');
	            var modeloCmp   = _fieldByLabel('MODELO');
	            var versionCmp  = _fieldLikeLabel('VERSI');
	            var record      = records[0];
	            
	            debug('marcaCmp:'            , marcaCmp);
	            debug('submarcaCmp:'         , submarcaCmp);
	            debug('submarcaCmp.heredar:' , submarcaCmp.heredar , '.');
	            debug('record.data'          , record.data);
	            
	            var cadena = record.get('value');
	            debug('cadena:',cadena);
	            
	            
	            var tok = cadena.split(' - ');
	            
	            
	            if(_p38_slist1[0].CDTIPSIT == 'AT'){
	            	
		            var value    = records[0].get('value');
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
                                _fieldLikeLabel('VERSI').heredar(true,function()
                                {
                                	_fieldLikeLabel('VERSI').setValue(_fieldLikeLabel('VERSI').findRecord('value',version));    
                                    _0_obtenerSumaAseguradaRamo6(true);                        
                                });
                            });
                        });
                    });
                    
                    _0_cargarNumPasajerosAuto();
	            }else{
	            	marcaCmp.setValue(marcaCmp.findRecordByDisplay(tok[1]));
		            submarcaCmp.heredar(true,function()
		            {
		                submarcaCmp.setValue(submarcaCmp.findRecordByDisplay(tok[2]));
		            });
		            modeloCmp.setValue(tok[3]);
		            versionCmp.setValue(tok[4]);
	            	_p38_cargarSumaAseguradaRamo5();
	            }
	            
	        }
	    });
	    
	}
    ////// custom //////
    
    ////// loaders //////
    for(var key in _p38_slist1[0])
    {
        var cmp = _fieldByName(key,_fieldById('_p38_form'),true);
        if(cmp)
        {
            debug('encontrado:',key,_p38_slist1[0][key]);
            if(!Ext.isEmpty(cmp.store))
            {
                if(cmp.store.getCount()>0)
                {
                    cmp.setValue(_p38_slist1[0][key]);
                }
                else
                {
                    cmp.store.valor = _p38_slist1[0][key];
                    cmp.store.padre = cmp;
                    cmp.store.on(
                    {
                        load : function(me)
                        {
                            me.padre.setValue(me.valor);
                        }
                    });
                }
            }
            else
            {
                cmp.setValue(_p38_slist1[0][key]);
            }
        }
        else
        {
            debug('no encontrado:',key);
        }
    }
    
    _fieldByName('feefecto').setValue(_p38_smap1.FEEFECTO);
    _fieldByName('feefecto').allowBlank = false;
    _fieldByName('feefecto').setReadOnly(true);
    _fieldByName('feefecto').isValid();
    
    /**
     * SE QUITA CODIGO PARA TOMAR FEEFECTO DE POLIZA
    Ext.Ajax.request(
    {
        url      : _p38_urlRecuperacionSimple
        ,params  :
        {
            'smap1.procedimiento' : 'RECUPERAR_FECHAS_LIMITE_ENDOSO'
            ,'smap1.cdunieco'     : _p38_smap1.CDUNIECO
            ,'smap1.cdramo'       : _p38_smap1.CDRAMO
            ,'smap1.estado'       : _p38_smap1.ESTADO
            ,'smap1.nmpoliza'     : _p38_smap1.NMPOLIZA
            ,'smap1.cdtipsup'     : _p38_smap1.cdtipsup
        }
        ,success : function(response)
        {
            var json = Ext.decode(response.responseText);
            debug('### fechas:',json);
            if(json.exito)
            {
                _fieldByName('feefecto').setMinValue(json.smap1.FECHA_MINIMA);
                _fieldByName('feefecto').setMaxValue(json.smap1.FECHA_MAXIMA);
                _fieldByName('feefecto').setReadOnly(json.smap1.EDITABLE=='N');
                _fieldByName('feefecto').isValid();
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
    
    */
    
    ////// loaders //////
    
    if(_p38_slist1[0].CDTIPSIT == 'AF' || _p38_slist1[0].CDTIPSIT == 'PU')
    {
    	
//     	if(rolesSuscriptores.lastIndexOf('|'+_p38_smap1.cdsisrol+'|') != -1)
	    if(RolSistema.puedeSuscribirAutos(_p38_smap1.cdsisrol))
    	{
    		_38_formAuto.down('[name=OTVALOR92]').setReadOnly(false);
            _38_formAuto.down('[name=OTVALOR93]').setReadOnly(false);
            _38_formAuto.down('[name=OTVALOR94]').setReadOnly(false);
            _38_formAuto.down('[name=OTVALOR95]').setReadOnly(false);
    	}
    	
    	//Al cambiar el numero de serie limpian los campos marca modelo descripcion y valor
        _38_formAuto.down('[name=OTVALOR91]').addListener('change',function()
        {
            debug('cleaning');
            _38_formAuto.down('[name=OTVALOR92]').setValue('');
            _38_formAuto.down('[name=OTVALOR93]').setValue('');
            _38_formAuto.down('[name=OTVALOR94]').setValue('');
            _38_formAuto.down('[name=OTVALOR95]').setValue('');
        });
    	
    	//Al cambiar el tipo de vehiculo se limpia el numero de serie

        _38_formAuto.down('[name=OTVALOR96]').addListener('change',function()
        {
            debug('cleaning');
            _38_formAuto.down('[name=OTVALOR91]').setValue('');
        });
        
        _38_formAuto.down('[name=OTVALOR91]').addListener('blur',function()
        {
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
            _38_formAuto.setLoading(true);
            
            Ext.Ajax.request(
            {
                url     : _p38_urlNadaEndoso
                ,params :
                {
                    'smap1.vim'       : vim
                    ,'smap1.cdunieco' : _p38_slist1[0].CDUNIECO
                    ,'smap1.cdramo'   : _p38_slist1[0].CDRAMO
                    ,'smap1.nmpoliza' : _p38_slist1[0].NMPOLIZA
                    ,'smap1.cdtipsit' : _p38_slist1[0].CDTIPSIT
                    ,'smap1.tipoveh'  : _38_formAuto.down('[name=OTVALOR96]').getValue()
                    ,'smap1.nmsituac' : _p38_slist1[0].NMSITUAC
                    ,'smap1.nmsuplem' : _p38_slist1[0].NMSUPLEM
                }
                ,success : function(response)
                {
                    _38_formAuto.setLoading(false);
                    var json = Ext.decode(response.responseText);
                    debug('nada response:', json);
                    if(json.success)
                    {
                        var precioDolar = Number(json.smap1.PRECIO_DOLAR);
                        debug('precioDolar:',precioDolar);
                        _38_formAuto.down('[name=OTVALOR92]').setValue(json.smap1.AUTO_MARCA);
                        _38_formAuto.down('[name=OTVALOR93]').setValue(json.smap1.AUTO_ANIO);
                        _38_formAuto.down('[name=OTVALOR94]').setValue(json.smap1.AUTO_DESCRIPCION);
                        _38_formAuto.down('[name=OTVALOR95]').setMinValue(((json.smap1.AUTO_PRECIO*precioDolar).toFixed(2))*(1-(json.smap1.FACTOR_MIN-0)));
                        _38_formAuto.down('[name=OTVALOR95]').setMaxValue(((json.smap1.AUTO_PRECIO*precioDolar).toFixed(2))*(1+(json.smap1.FACTOR_MAX-0)));
                        _38_formAuto.down('[name=OTVALOR95]').setValue((json.smap1.AUTO_PRECIO*precioDolar).toFixed(2));
                        debug('set min value:',((json.smap1.AUTO_PRECIO*precioDolar).toFixed(2))*(1-(json.smap1.FACTOR_MIN-0)));
                        debug('set max value:',((json.smap1.AUTO_PRECIO*precioDolar).toFixed(2))*(1+(json.smap1.FACTOR_MAX-0)));
                        
                        Ext.Ajax.request({
            				url     : _p38_urlObtieneValNumeroSerie
            				,params :
            				{
            					'smap1.numSerie'  :  _38_formAuto.down('[name=OTVALOR91]').getValue()
            					,'smap1.feini'    :  _fieldByName('feefecto').getValue()
            				}
            				,success : function(response)
            				{
            					var json=Ext.decode(response.responseText);
            					debug(json);
                    	    	if(json.exito!=true)
                    	    	{
//                                     if(rolesSuscriptores.lastIndexOf('|'+_p38_smap1.cdsisrol+'|')==-1)   
	                                if(!RolSistema.puedeSuscribirAutos(_p38_smap1.cdsisrol))
                    	    		{
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
                    	// Si no obtuvo datos el servicio "NADA", reseteamos valores:
                		_38_formAuto.down('[name=OTVALOR92]').setValue();
                        _38_formAuto.down('[name=OTVALOR93]').setValue();
                        _38_formAuto.down('[name=OTVALOR94]').setValue();
                        _38_formAuto.down('[name=OTVALOR95]').setValue();
                        _38_formAuto.down('[name=OTVALOR95]').setMinValue();
                        _38_formAuto.down('[name=OTVALOR95]').setMaxValue();
                    }
                }
                ,failure : function()
                {
                    _38_formAuto.setLoading(false);
                    debug("Entra a esta parte");
                    errorComunicacion();
                }
            });
            debug('<llamando a nada');            
        });
        
        Ext.Ajax.request(
        {
            url      : _p38_urlCargarParametros
            ,params  :
            {
                'smap1.parametro' : 'RANGO_ANIO_MODELO'
                ,'smap1.cdramo'   : _p38_slist1[0].CDRAMO
                ,'smap1.cdtipsit' : _p38_slist1[0].CDTIPSIT
                ,'smap1.clave4'   : _p38_smap1.cdsisrol
            }
            ,success : function(response)
            {
                var json=Ext.decode(response.responseText);
                debug('### obtener rango años response:',json);
                if(json.exito)
                {
                    var limiteInferior = json.smap1.P1VALOR-0;
                    var limiteSuperior = json.smap1.P2VALOR-0;
                    _38_formAuto.down('[name=OTVALOR93]').validator=function(value)
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
        
	}
    
    if(_p38_slist1[0].CDTIPSIT == 'AT'){
    	
//         if(rolesSuscriptores.lastIndexOf('|'+_p38_smap1.cdsisrol+'|') != -1)
	    RolSistema.puedeSuscribirAutos(_p38_smap1.cdsisrol)
        {
        	_fieldByLabel('TIPO DE UNIDAD').setReadOnly(false);
    		_fieldByLabel('MARCA').setReadOnly(false);
    		_fieldByLabel('SUBMARCA').setReadOnly(false);
    		_fieldByLabel('MODELO').setReadOnly(false);
    		_fieldLikeLabel('VERSI').setReadOnly(false);
    		_fieldLikeLabel('PASAJEROS').setReadOnly(false);
    		_fieldLikeLabel('VALOR').setReadOnly(false);
    		
    		_fieldByLabel('MODELO').on(
    	            {
    	                select : function()
    	                {
    	                    _0_obtenerClaveGSPorAuto();
    	                    _0_obtenerSumaAseguradaRamo6(true);
    	                }
    	            });
    		
    		_fieldLikeLabel('VERSI').on(
    	            {
    	                'select' : function()
    	                {
    	                    _0_obtenerSumaAseguradaRamo6(true);
    	                }
    	            });
    	}
    }
    
	if(_p38_slist1[0].CDTIPSIT == 'MC'){
		
		alert('En inicio: ' + _p38_slist1[0].CVE_MODELO);
		_fieldByLabel('MODELO').setValue(_p38_slist1[0].CVE_MODELO);
		_fieldLikeLabel('VERSI').setValue(_p38_slist1[0].DES_VERSION);
		_fieldLikeLabel('VALOR').setValue(_p38_slist1[0].CVE_VALOR_COMERCIAL);
		
		_fieldByLabel('MODELO').setLoading(true);
        Ext.Ajax.request(
        {
            url      : _p38_urlCargarParametros
            ,params  :
            {
                'smap1.parametro' : 'RANGO_ANIO_MODELO'
                ,'smap1.cdramo'   : _p38_slist1[0].CDRAMO
                ,'smap1.cdtipsit' : _p38_slist1[0].CDTIPSIT
            }
            ,success : function(response)
            {
                _fieldByLabel('MODELO').setLoading(false);
                var json=Ext.decode(response.responseText);
                debug('respuesta json obtener rango:',json);
                if(json.exito)
                {
                    //_fieldByLabel('MODELO').setValue(json.smap1.P1VALOR);// valor original del auto en emision
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
    
});

////// funciones //////

function mensajeValidacionNumSerie(titulo,imagenSeccion,txtRespuesta)
{
    var panelImagen = new Ext.Panel({
        defaults    : {
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
        ,width       : 520
        ,icon :imagenSeccion
        ,resizable: false
        ,height      : 250
        ,items       :[
            Ext.create('Ext.form.Panel', {
                id: 'panelClausula'
                ,width       : 500
                ,height      : 150
                ,bodyPadding: 5
                ,renderTo: Ext.getBody()
                ,defaults    : {
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
                        [   panelImagen     ]
                }]
            })
        ],
        buttonAlign:'center',
        buttons: [{
            text: 'Aceptar',
            icon: '${ctx}/resources/fam3icons/icons/accept.png',
            buttonAlign : 'center',
            handler: function() {
                validacionNumSerie.close();
            }
        }]
    });
    centrarVentanaInterna(validacionNumSerie.show());
}


function _p38_cargarSumaAseguradaRamo5()
{
    debug('>_p38_cargarSumaAseguradaRamo5');
    var form=_fieldById('_p38_form');
    form.setLoading(true);
    Ext.Ajax.request(
    {
        url      : _p38_urlCargarSumaAseguradaRamo5
        ,params  :
        {
            'smap1.cdtipsit'  : _p38_slist1[0].CDTIPSIT
            ,'smap1.clave'    : _fieldLikeLabel('CLAVE').getValue()
            ,'smap1.modelo'   : _fieldByLabel('MODELO').getValue()
            ,'smap1.cdsisrol' : _p38_smap1.cdsisrol
        }
        ,success : function(response)
        {
            form.setLoading(false);
            var json = Ext.decode(response.responseText);
            debug('### cargar suma asegurada:',json);
            if(json.exito)
            {
                /*
                Se comenta porque no quieren el aviso de error en pantalla
                if(!Ext.isEmpty(json.respuesta))
                {
                    mensajeWarning(json.respuesta);
                }
                */
                var sumaseg = _fieldByLabel('VALOR');
                sumaseg.setValue(json.smap1.sumaseg);
                sumaseg.valorCargado=json.smap1.sumaseg;
                _p38_cargarRangoValorRamo5();
            }
            else
            {
                mensajeError(json.respuesta);
            }
        }
        ,failure : function()
        {
            form.setLoading(false);
            errorComunicacion();
        }
    });
    debug('<_p38_cargarSumaAseguradaRamo5');
}

function _p38_cargarRangoValorRamo5()
{
    debug('>_p38_cargarRangoValorRamo5');
    var tipovalor = _fieldByLabel('TIPO VALOR');
    var valor     = _fieldByLabel('VALOR');
    
    var tipovalorval = tipovalor.getValue();
    var valorval     = valor.getValue();
    var valorCargado = valor.valorCargado;
    
    var valido = !Ext.isEmpty(tipovalorval)&&!Ext.isEmpty(valorval)&&!Ext.isEmpty(valorCargado);
    
    if(valido)
    {
        var panelpri = _fieldById('_p38_form');
        panelpri.setLoading(true);
        Ext.Ajax.request(
        {
            url      : _p38_urlCargarParametros
            ,params  :
            {
                'smap1.parametro' : 'RANGO_VALOR'
                ,'smap1.cdramo'   : _p38_slist1[0].CDRAMO
                ,'smap1.cdtipsit' : _p38_slist1[0].CDTIPSIT
                ,'smap1.clave4'   : tipovalorval
                ,'smap1.clave5'   : _p38_smap1.cdsisrol
            }
            ,success : function(response)
            {
                panelpri.setLoading(false);
                var json = Ext.decode(response.responseText);
                debug('### obtener rango valor:',json);
                if(json.exito)
                {
                    // http://stackoverflow.com/questions/11832914/round-to-at-most-2-decimal-places-in-javascript
                    valormin = Math.round((valorCargado*(1+(json.smap1.P1VALOR-0))) * 100) / 100;
                    valormax = Math.round((valorCargado*(1+(json.smap1.P2VALOR-0))) * 100) / 100;
                    valor.setMinValue(valormin);
                    valor.setMaxValue(valormax);
                    valor.isValid();
                    debug('valor:',valorCargado);
                    debug('valormin:',valormin);
                    debug('valormax:',valormax);
                }
                else
                {
                    mensajeError(json.respuesta);
                }
            }
            ,failure : function()
            {
                panelpri.setLoading(false);
                errorComunicacion();
            }
        });
    }
    debug('<_p38_cargarRangoValorRamo5');
}

function _0_obtenerSumaAseguradaRamo6(mostrarError,respetarValue)
{
    var loading_0_obtenerSumaAseguradaRamo6 = _maskLocal();
    Ext.Ajax.request(
    {
        url      : _0_urlCargarSumaAsegurada
        ,params  :
        {
            'smap1.modelo'    : String(_fieldByLabel('MODELO').getValue()).substr(_fieldByLabel('MODELO').getValue().length-4,4)
            ,'smap1.version'  : _fieldLikeLabel('VERSI').getValue()
            ,'smap1.cdsisrol' : _p38_smap1.cdsisrol
            ,'smap1.cdramo'   : _p38_smap1.CDRAMO
            ,'smap1.cdtipsit' : _p38_slist1[0].CDTIPSIT
        }
        ,success : function(response)
        {

//           _0_panelPri.setLoading(false); 
            loading_0_obtenerSumaAseguradaRamo6.close();
            var json=Ext.decode(response.responseText);
            debug('### json response obtener suma asegurada:',json);
            if(json.exito)
            {
                if(Ext.isEmpty(respetarValue)||respetarValue==false)
                {
                	_fieldLikeLabel('VALOR').setValue(json.smap1.SUMASEG);
                }
                else
                {
                    debug('SE RESPETA VALUE de VALOR COMERCIAL');
                }
                _fieldLikeLabel('VALOR').setMinValue((json.smap1.SUMASEG-0)*(1-(json.smap1.FACREDUC-0)));
                _fieldLikeLabel('VALOR').setMaxValue((json.smap1.SUMASEG-0)*(1+(json.smap1.FACINCREM-0)));
                _fieldLikeLabel('VALOR').isValid();
            }
            else if(mostrarError==true)
            {
                mensajeWarning(json.respuesta);
            }
        }
        ,failure : function()
        {
//             _0_panelPri.setLoading(false);
            loading_0_obtenerSumaAseguradaRamo6.close();
            errorComunicacion();
        }
    });
}

function _0_cargarNumPasajerosAuto()
{
    Ext.Ajax.request(
    {
        url      : _0_urlCargarAutoPorClaveGS
        ,params  :
        {
            'smap1.cdramo'    : _p38_smap1.CDRAMO
            ,'smap1.clavegs'  : _fieldLikeLabel('CLAVE').getValue()
            ,'smap1.cdtipsit' : _p38_slist1[0].CDTIPSIT
            ,'smap1.tipounidad' : _fieldByLabel('TIPO DE UNIDAD').getValue()
        }
        ,success : function(response)
        {
            var ijson=Ext.decode(response.responseText);
            debug('### obtener auto por clave gs:',ijson);
            if(ijson.exito)
            {
            	_fieldLikeLabel('PASAJEROS').setValue(ijson.smap1.NUMPASAJEROS);
            	_fieldLikeLabel('PASAJEROS').setMinValue(ijson.smap1.PASAJMIN);
            	_fieldLikeLabel('PASAJEROS').setMaxValue(ijson.smap1.PASAJMAX);
            	_fieldLikeLabel('PASAJEROS').isValid();
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
	_fieldLikeLabel('CLAVE').getStore().load(
    {
        params :
        {
            'params.substr' : _fieldLikeLabel('VERSI').getValue()
        }
        ,callback : function(records)
        {
            debug('callback records:',records);
            var valor=_fieldLikeLabel('VERSI').getValue()
                +' - '+_fieldByLabel('TIPO DE UNIDAD').findRecord('key',_fieldByLabel('TIPO DE UNIDAD').getValue()).get('value')
                +' - '+_fieldByLabel('MARCA').findRecord('key',_fieldByLabel('MARCA').getValue()).get('value')
                +' - '+_fieldByLabel('SUBMARCA').findRecord('key',_fieldByLabel('SUBMARCA').getValue()).get('value')
                +' - '+_fieldByLabel('MODELO').findRecord('key',_fieldByLabel('MODELO').getValue()).get('value')
                +' - '+_fieldLikeLabel('VERSI').findRecord('key',_fieldLikeLabel('VERSI').getValue()).get('value');
            debug('valor para el auto:',valor);
            _fieldLikeLabel('CLAVE').setValue(
            		_fieldLikeLabel('CLAVE').findRecord('value',valor)
            );
            _0_cargarNumPasajerosAuto();
        }
    });
}

////// funciones //////
<%@ include file="/jsp-script/proceso/documentos/scriptImpresionRemesaEmisionEndoso.jsp"%>
</script>
</head>
<body><div id="_p38_divpri" style="height:500px;border:1px solid #999999;"></div></body>
</html>