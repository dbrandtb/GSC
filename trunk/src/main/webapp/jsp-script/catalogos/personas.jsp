<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script>
////// overrides //////
////// overrides //////

////// variables //////
var _p22_urlObtenerPersonas  = '<s:url namespace="/catalogos" action="obtenerPersonasPorRFC"              />';
var _p22_urlGuardar          = '<s:url namespace="/catalogos" action="guardarPantallaPersonas"            />';
var _p22_urlObtenerDomicilio = '<s:url namespace="/catalogos" action="obtenerDomicilioPorCdperson"        />';
var _p22_urlTatriperTvaloper = '<s:url namespace="/catalogos" action="obtenerTatriperTvaloperPorCdperson" />';
var _p22_urlGuadarTvaloper   = '<s:url namespace="/catalogos" action="guardarDatosTvaloper"               />';

var _p22_storeGrid;
////// variables //////

Ext.onReady(function()
{
	////// modelos //////
	Ext.define('_p22_modeloGrid',
	{
		extend  : 'Ext.data.Model'
		,fields : [ <s:property value="imap.gridModelFields" /> ]
	});
	
	Ext.define('_p22_modeloDomicilio',
	{
		extend  : 'Ext.data.Model'
		,fields : [ <s:property value="imap.fieldsDomicilio" /> ]
	});
	////// modelos //////
	
	////// stores //////
	_p22_storeGrid=Ext.create('Ext.data.Store',
	{
		model     : '_p22_modeloGrid'
		,autoLoad : false
		,proxy    :
		{
			url     : _p22_urlObtenerPersonas
			,type   : 'ajax'
			,reader :
			{
				type  : 'json'
				,root : 'slist1'
			}
		}
	});
	////// stores //////
	
	////// componentes //////
	////// componentes //////
	
	////// contenido //////
	Ext.create('Ext.panel.Panel',
	{
		renderTo  : '_p22_divpri'
		,defaults : { style : 'margin:5px;' }
	    ,border   : 0
	    ,items    :
	    [
	        Ext.create('Ext.form.Panel',
	        {
	        	 title        : 'Buscar por RFC'
	        	 ,itemId      : '_p22_formBusqueda'
	        	 ,layout      :
	        	 {
	        	     type     : 'table'
	        	     ,columns : 2
	        	 }
	        	 ,defaults    : { style : 'margin:5px;' }
	        	 ,items       :
	        	 [
	        	     <s:property value="imap.BUSQUEDA" />
	        	     ,{
                         text     : 'Buscar'
                         ,xtype   : 'button'
                         ,icon    : '${ctx}/resources/fam3icons/icons/zoom.png'
                         ,handler : _p22_buscarClic
                     }
	        	 ]
	        })
	        ,Ext.create('Ext.grid.Panel',
	        {
	        	title      : 'Personas encontradas'
	        	,height    : 200
	        	,border    : 0
	        	,columns   : [ <s:property value="imap.gridColumns" /> ]
	            ,store     : _p22_storeGrid
	            ,listeners :
	            {
	                itemclick : _p22_itemclick
	            }
	        })
	        ,Ext.create('Ext.tab.Panel',
	        {
	        	title        : 'Edici&oacute;n de persona'
	        	,itemId      : '_p22_tabPanel'
	        	,border      : 0
	        	,tbar        :
	        	[
	        	    {
	        	        text     : 'Datos adicionales'
	        	        ,icon    : '${ctx}/resources/fam3icons/icons/application_form_add.png'
	        	        ,handler : function(){_p22_guardarClic(_p22_datosAdicionalesClic);}
	        	    }
	        	]
	        	,items       :
	        	[
	        	    Ext.create('Ext.form.Panel',
	        	    {
	        	    	title     : 'Datos generales'
	        	    	,itemId   : '_p22_formDatosGenerales'
                        ,border   : 0
	        	    	,defaults : { style : 'margin:5px' }
	        	        ,layout   :
	        	        {
	        	        	type     : 'table'
	        	        	,columns : 2
	        	        }
	        	    	,items    : [ <s:property value="imap.datosGeneralesItems" /> ]
	        	    })
	        	    ,Ext.create('Ext.form.Panel',
                    {
                        title     : 'Domicilio'
                        ,itemId   : '_p22_formDomicilio'
                        ,border   : 0
                        ,defaults : { style : 'margin:5px' }
                        ,layout   :
                        {
                            type     : 'table'
                            ,columns : 2
                        }
                        ,items    : [ <s:property value="imap.itemsDomicilio" /> ]
                    })
	        	]
	        	,buttonAlign : 'center'
	        	,buttons     :
	        	[
	        	    {
	        	        text     : 'Nuevo'
                        ,icon    : '${ctx}/resources/fam3icons/icons/add.png'
                        ,handler : _p22_nuevoClic
	        	    }
	        		,{
	        			text     : 'Guardar'
	        			,icon    : '${ctx}/resources/fam3icons/icons/disk.png'
	        			,handler : function(){_p22_guardarClic();}
	        		}
	        	]
	        })
	    ]
	});
	////// contenido //////
	
	////// loaders //////
	_p22_comboCodPostal().addListener('blur',_p22_heredarColonia);
	_p22_fieldTipoPersona().addListener('change',_p22_tipoPersonaChange);
	////// loaders //////
});

////// funciones //////
function _p22_buscarClic()
{
    debug('>_p22_buscarClic');
    _p22_nuevoClic();
	var form=_p22_formBusqueda();
	var exito = true;
	if(exito)
	{
		exito=form.isValid();
		if(!exito)
		{
			mensajeWarning('Favor de revisar los campos requeridos');
		}
	}
	
	if(exito)
	{
		_p22_storeGrid.load(
		{
			params :
			{
				'smap1.rfc' : _p22_formBusqueda().down('[name=rfc]').getValue()
			}
		});
	}
	debug('<_p22_buscarClic');
}

function _p22_formBusqueda()
{
    debug('>_p22_formBusqueda<');
	return Ext.ComponentQuery.query('#_p22_formBusqueda')[0];
}

function _p22_heredarColonia()
{
    debug('>_p22_heredarColonia');
    var comboColonias  = _p22_comboColonias();
    var comboCodPostal = _p22_comboCodPostal();
    var codigoPostal   = comboCodPostal.getValue();
    debug('comboColonias:',comboColonias,'comboCodPostal:',comboCodPostal);
    debug('codigoPostal:',codigoPostal);
    comboColonias.getStore().load(
    {
        params :
        {
            'params.cp' : codigoPostal
        }
        ,callback : function()
        {
            var hay=false;
            comboColonias.getStore().each(function(record)
            {
                if(comboColonias.getValue()==record.get('key'))
                {
                    hay=true;
                }
            });
            if(!hay)
            {
                comboColonias.setValue('');
            }
        }
    });
    debug('<_p22_heredarColonia');
}

function _p22_tipoPersonaChange(combo,value)
{
    debug('>_p22_tipoPersonaChange',value);
    if(value!='F')
    {
        _p22_fieldSegundoNombre().hide();
        _p22_fieldApat().hide();
        _p22_fieldAmat().hide();
        _p22_fieldSexo().hide();
    }
    else
    {
        _p22_fieldSegundoNombre().show();
        _p22_fieldApat().show();
        _p22_fieldAmat().show();
        _p22_fieldSexo().show();
    }
    debug('<_p22_tipoPersonaChange');
}

function _p22_comboColonias()
{
    debug('>_p22_comboColonias<');
    return Ext.ComponentQuery.query('[name=CDCOLONI]')[0];
}

function _p22_comboCodPostal()
{
    debug('>_p22_comboCodPostal<');
    return Ext.ComponentQuery.query('[name=CODPOSTAL]')[0];
}

function _p22_fieldSegundoNombre()
{
    debug('>_p22_fieldSegundoNombre<');
    return Ext.ComponentQuery.query('[name=DSNOMBRE1]')[0];
}

function _p22_fieldApat()
{
    debug('>_p22_fieldApat<');
    return Ext.ComponentQuery.query('[name=DSAPELLIDO]')[0];
}

function _p22_fieldAmat()
{
    debug('>_p22_fieldAmat<');
    return Ext.ComponentQuery.query('[name=DSAPELLIDO1]')[0];
}

function _p22_fieldSexo()
{
    debug('>_p22_fieldSexo<');
    return Ext.ComponentQuery.query('[name=OTSEXO]')[0];
}

function _p22_fieldTipoPersona()
{
    debug('>_p22_fieldTipoPersona<');
    return Ext.ComponentQuery.query('[name=OTFISJUR]')[0];
}

function _p22_formDatosGenerales()
{
    debug('>_p22_formDatosGenerales<');
    return Ext.ComponentQuery.query('#_p22_formDatosGenerales')[0];
}

function _p22_itemclick(grid,record)
{
    debug('>_p22_itemclick:',record.data);
    _p22_nuevoClic();
    _p22_tabPanel().setLoading(true);
    _p22_formDatosGenerales().loadRecord(record);
    Ext.Ajax.request(
    {
        url      : _p22_urlObtenerDomicilio
        ,params  :
        {
            'smap1.cdperson' : record.get('CDPERSON')
        }
        ,success : function(response)
        {
            _p22_tabPanel().setLoading(false);
            var json=Ext.decode(response.responseText);
            debug('json response:',json);
            if(json.exito)
            {
                _p22_formDomicilio().loadRecord(new _p22_modeloDomicilio(json.smap1));
                heredarPanel(_p22_formDomicilio());
            }
            else
            {
                mensajeError(json.respuesta);
            }
        }
        ,failure : function()
        {
            _p22_tabPanel().setLoading(false);
            errorComunicacion();
        }
    });
    debug('<_p22_itemclick');
}

function _p22_tabPanel()
{
    debug('>_p22_tabPanel<');
    return Ext.ComponentQuery.query('#_p22_tabPanel')[0];
}

function _p22_guardarClic(callback)
{
    debug('>_p22_guardarClic');
    var valido = true;
    
    if(valido)
    {
        valido = _p22_formDatosGenerales().isValid();
        if(!valido)
        {
            mensajeWarning('Favor de verificar los datos generales');
            _p22_ponerActivo(1);
        }
    }
    
    if(valido)
    {
        valido = validarRFC(_p22_fieldRFC().getValue(),_p22_fieldTipoPersona().getValue());
        if(!valido)
        {
            _p22_ponerActivo(1);
        }
    }
    
    if(valido&&_p22_fieldTipoPersona().getValue()=='F')
    {
        valido = !Ext.isEmpty(_p22_fieldApat().getValue())
                 &&!Ext.isEmpty(_p22_fieldAmat().getValue())
                 &&!Ext.isEmpty(_p22_fieldSexo().getValue());
        if(!valido)
        {
            mensajeWarning('Favor de introducir apellidos y sexo para persona f&iacute;sica');
            ponerActivo(1);
        }
    }
    
    if(valido&&Ext.isEmpty(_p22_fieldConsecutivo().getValue()))
    {
        _p22_fieldConsecutivo().setValue(1);
    }
    
    if(valido)
    {
        valido = _p22_formDomicilio().isValid();
        if(!valido)
        {
            mensajeWarning('Favor de verificar los datos del domicilio');
            _p22_ponerActivo(2);
        }
    }
    
    if(valido)
    {
        _p22_tabPanel().setLoading(true);
        Ext.Ajax.request(
        {
            url       : _p22_urlGuardar
            ,jsonData :
            {
                smap1  : _p22_formDatosGenerales().getValues()
                ,smap2 : _p22_formDomicilio().getValues()
            }
            ,success : function(response)
            {
                _p22_tabPanel().setLoading(false);
                var json = Ext.decode(response.responseText);
                debug('json response:',json);
                if(json.exito)
                {
                    _p22_fieldCdperson().setValue(json.smap1.CDPERSON);
                    if(callback)
                    {
                        callback();
                    }
                    else
                    {
                        mensajeCorrecto('Datos guardados',json.respuesta);
                    }
                }
                else
                {
                    mensajeError(json.respuesta);
                }
            }
            ,failure : function()
            {
                _p22_tabPanel().setLoading(false);
                errorComunicacion();
            }
        });
    }
    
    debug('<_p22_guardarClic');
}

function _p22_ponerActivo(indice)
{
    var tab=0;
    if(indice==1)
    {
        tab = _p22_formDatosGenerales();
    }
    else if(indice==2)
    {
        tab = _p22_formDomicilio();
    }
    _p22_tabPanel().setActiveTab(tab);
}

function _p22_formDomicilio()
{
    debug('>_p22_formDomicilio<');
    return Ext.ComponentQuery.query('#_p22_formDomicilio')[0];
}

function _p22_fieldRFC()
{
    debug('>_p22_fieldRFC<');
    return Ext.ComponentQuery.query('[name=CDRFC]')[0];
}

function _p22_fieldCdperson()
{
    debug('>_p22_fieldCdperson<');
    return Ext.ComponentQuery.query('[name=CDPERSON]')[0];
}

function _p22_fieldConsecutivo()
{
    debug('>_p22_fieldConsecutivo<');
    return Ext.ComponentQuery.query('[name=NMORDDOM]')[0];
}

function _p22_nuevoClic()
{
    debug('>_p22_nuevoClic');
    _p22_ponerActivo(1);
    _p22_formDatosGenerales().getForm().reset();
    _p22_formDomicilio().getForm().reset();
    debug('<_p22_nuevoClic');
}

function _p22_datosAdicionalesClic()
{
    debug('>_p22_datosAdicionalesClic');
    _p22_tabPanel().setLoading(true);
    Ext.Ajax.request(
    {
        url      : _p22_urlTatriperTvaloper
        ,params  : { 'smap1.cdperson' : _p22_fieldCdperson().getValue() }
        ,success : function(response)
        {
            _p22_tabPanel().setLoading(false);
            var json = Ext.decode(response.responseText);
            debug('json response:',json);
            if(json.exito)
            {
                Ext.define('_p22_modeloTatriper',
                {
                    extend  : 'Ext.data.Model'
                    ,fields : Ext.decode(json.smap1.fieldsTatriper.substring("fields:".length))
                });
                centrarVentanaInterna(Ext.create('Ext.window.Window',
                {
                    title   : 'Datos adicionales'
                    ,width  : 600
                    ,height : 400
                    ,autoScroll : true
                    ,modal  : true
                    ,items  :
                    [
                        Ext.create('Ext.form.Panel',
                        {
                            border    : 0
                            ,itemId   : '_p22_formDatosAdicionales'
                            ,width    : 570
                            ,defaults : { style : 'margin:5px;' }
                            ,layout   :
                            {
                                type     : 'table'
                                ,columns : 2
                            }
                            ,items    : Ext.decode(json.smap1.itemsTatriper.substring("items:".length))
                        })
                    ]
                    ,bbar    :
                    [
                        '->'
                        ,{
                            text     : 'Guardar'
                            ,icon    : '${ctx}/resources/fam3icons/icons/disk.png'
                            ,handler : _p22_guardarDatosAdicionalesClic
                        }
                        ,'->'
                    ]
                }).show());
                _p22_formDatosAdicionales().loadRecord(new _p22_modeloTatriper(json.smap2));
            }
            else
            {
                mensajeError(json.respuesta);
            }
        }
        ,failure : function()
        {
            _p22_tabPanel().setLoading(false);
            errorComunicacion();
        }
    });
    debug('<_p22_datosAdicionalesClic');
}

function _p22_guardarDatosAdicionalesClic()
{
    debug('>_p22_guardarDatosAdicionalesClic');
    
    var valido=true;
    
    if(valido)
    {
        valido = _p22_formDatosAdicionales().isValid();
        if(!valido)
        {
            mensajeWarning('Favor de verificar los datos');
        }
    }
    
    if(valido)
    {
        _p22_formDatosAdicionales().setLoading(true);
        var jsonData = _p22_formDatosAdicionales().getValues();
        jsonData['cdperson'] = _p22_fieldCdperson().getValue();
        Ext.Ajax.request(
        {
            url       : _p22_urlGuadarTvaloper
            ,jsonData :
            {
                smap1 : jsonData
            }
            ,success  : function(response)
            {
                _p22_formDatosAdicionales().setLoading(false);
                var json = Ext.decode(response.responseText);
                debug('response text:',json);
                if(json.exito)
                {
                    mensajeCorrecto('Datos guardados',json.respuesta);                
                }
                else
                {
                    mensajeError(json.respuesta);
                }
            }
            ,failure  : function()
            {
                _p22_formDatosAdicionales().setLoading(false);
                errorComunicacion();
            }
        });
    }
    
    debug('<_p22_guardarDatosAdicionalesClic');
}

function _p22_formDatosAdicionales()
{
    debug('>_p22_formDatosAdicionales<');
    return Ext.ComponentQuery.query('#_p22_formDatosAdicionales')[0];
}
////// funciones //////
</script>
</head>
<body>
<div id="_p22_divpri" style="height : 1500px;"></div>
</body>
</html>