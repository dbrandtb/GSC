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

////// urls //////

////// variables //////
var _p38_smap1  = <s:property value="%{convertToJSON('smap1')}"  escapeHtml="false" />;
var _p38_slist1 = <s:property value="%{convertToJSON('slist1')}" escapeHtml="false" />;
var _p38_flujo  = <s:property value="%{convertToJSON('flujo')}"  escapeHtml="false" />;
debug('_p38_smap1:'  , _p38_smap1);
debug('_p38_slist1:' , _p38_slist1);
debug('_p38_flujo:'  , _p38_flujo);

var rolesSuscriptores = '|SUSCRIAUTO|TECNISUSCRI|EMISUSCRI|JEFESUSCRI|GERENSUSCRI|SUBDIRSUSCRI|';

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
    if(_p38_slist1[0].CDTIPSIT != 'AF' && _p38_slist1[0].CDTIPSIT != 'PU'){
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
	            marcaCmp.setValue(marcaCmp.findRecordByDisplay(tok[1]));
	            submarcaCmp.heredar(true,function()
	            {
	                submarcaCmp.setValue(submarcaCmp.findRecordByDisplay(tok[2]));
	            });
	            modeloCmp.setValue(tok[3]);
	            versionCmp.setValue(tok[4]);
	            _p38_cargarSumaAseguradaRamo5();
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
        _38_formAuto.down('[name=OTVALOR92]').addListener('change',function()
        {
            debug('cleaning');
            _38_formAuto.down('[name=OTVALOR93]').setValue('');
            _38_formAuto.down('[name=OTVALOR95]').setValue('');
            _38_formAuto.down('[name=OTVALOR96]').setValue('');
            _38_formAuto.down('[name=OTVALOR97]').setValue('');
        });
        _38_formAuto.down('[name=OTVALOR92]').addListener('blur',function()
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
                    ,'smap1.tipoveh'  : _38_formAuto.down('[name=OTVALOR91]').getValue()
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
                        var precioDolar = 200000;/*_38_formAuto.down('[name=parametros.pv_otvalor24]').getValue()-0;
                        debug('precioDolar:',precioDolar); */
                        _38_formAuto.down('[name=OTVALOR93]').setValue(json.smap1.AUTO_MARCA);
                        _38_formAuto.down('[name=OTVALOR95]').setValue(json.smap1.AUTO_ANIO);
                        _38_formAuto.down('[name=OTVALOR96]').setValue(json.smap1.AUTO_DESCRIPCION);
                        _38_formAuto.down('[name=OTVALOR97]').setMinValue(((json.smap1.AUTO_PRECIO*precioDolar).toFixed(2))*(1-(json.smap1.FACTOR_MIN-0)));
                        _38_formAuto.down('[name=OTVALOR97]').setMaxValue(((json.smap1.AUTO_PRECIO*precioDolar).toFixed(2))*(1+(json.smap1.FACTOR_MAX-0)));
                        _38_formAuto.down('[name=OTVALOR97]').setValue((json.smap1.AUTO_PRECIO*precioDolar).toFixed(2));
                        debug('set min value:',((json.smap1.AUTO_PRECIO*precioDolar).toFixed(2))*(1-(json.smap1.FACTOR_MIN-0)));
                        debug('set max value:',((json.smap1.AUTO_PRECIO*precioDolar).toFixed(2))*(1+(json.smap1.FACTOR_MAX-0)));
                        
                        Ext.Ajax.request({
            				url     : _p38_urlObtieneValNumeroSerie
            				,params :
            				{
            					'smap1.numSerie'  :  _38_formAuto.down('[name=OTVALOR92]').getValue()
            					,'smap1.feini'    :  _fieldByName('feefecto').getValue()
            				}
            				,success : function(response)
            				{
            					var json=Ext.decode(response.responseText);
            					debug(json);
                    	    	if(json.exito!=true)
                    	    	{
                                    if(rolesSuscriptores.lastIndexOf('|'+_p38_smap1.cdsisrol+'|')==-1)                    	    			
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
                		_38_formAuto.down('[name=OTVALOR93]').setValue();
                        _38_formAuto.down('[name=OTVALOR95]').setValue();
                        _38_formAuto.down('[name=OTVALOR96]').setValue();
                        _38_formAuto.down('[name=OTVALOR97]').setValue();
                        _38_formAuto.down('[name=OTVALOR97]').setMinValue();
                        _38_formAuto.down('[name=OTVALOR97]').setMaxValue();
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
                    _38_formAuto.down('[name=OTVALOR95]').validator=function(value)
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
                    valormin = valorCargado*(1+(json.smap1.P1VALOR-0));
                    valormax = valorCargado*(1+(json.smap1.P2VALOR-0));
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
////// funciones //////
<%@ include file="/jsp-script/proceso/documentos/scriptImpresionRemesaEmisionEndoso.jsp"%>
</script>
</head>
<body><div id="_p38_divpri" style="height:500px;border:1px solid #999999;"></div></body>
</html>