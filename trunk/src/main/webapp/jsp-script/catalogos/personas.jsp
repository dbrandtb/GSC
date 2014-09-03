<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script>
////// overrides //////
////// overrides //////

////// variables //////
var _p22_urlObtenerPersonas     = '<s:url namespace="/catalogos"  action="obtenerPersonasPorRFC"              />';
var _p22_urlGuardar             = '<s:url namespace="/catalogos"  action="guardarPantallaPersonas"            />';
var _p22_urlObtenerDomicilio    = '<s:url namespace="/catalogos"  action="obtenerDomicilioPorCdperson"        />';
var _p22_urlTatriperTvaloper    = '<s:url namespace="/catalogos"  action="obtenerTatriperTvaloperPorCdperson" />';
var _p22_urlGuadarTvaloper      = '<s:url namespace="/catalogos"  action="guardarDatosTvaloper"               />';
var _p22_urlPantallaDocumentos  = '<s:url namespace="/catalogos"  action="pantallaDocumentosPersona"          />';
var _p22_urlSubirArchivo        = '<s:url namespace="/"           action="subirArchivoPersona"                />';
var _p22_UrlUploadPro           = '<s:url namespace="/"           action="subirArchivoMostrarBarra"           />';
var _p22_urlViewDoc             = '<s:url namespace="/documentos" action="descargaDocInlinePersona"           />';
var _p22_urlCargarNombreArchivo = '<s:url namespace="/catalogos"  action="cargarNombreDocumentoPersona"       />';

var _URL_CARGA_CATALOGO = '<s:url namespace="/catalogos" action="obtieneCatalogo" />';
var _CAT_NACIONALIDAD  = '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@NACIONALIDAD"/>';

var _UrlCargaAccionistas = '<s:url namespace="/catalogos" action="obtieneAccionistas" />';
var _UrlGuardaAccionista = '<s:url namespace="/catalogos" action="guardaAccionista" />';
var _UrlEliminaAccionistas = '<s:url namespace="/catalogos" action="eliminaAccionistas" />';

/* PARA EL LOADER */
var _p22_urlCargarPersonaCdperson = '<s:url namespace="/catalogos" action="obtenerPersonaPorCdperson" />';
/* PARA EL LOADER */

var _p22_storeGrid;
var _p22_windowAgregarDocu;

var windowAccionistas = undefined;
var accionistasStore;
var gridAccionistas;
var fieldEstCorp;

/* PARA LOADER */
var _p22_smap1 = <s:property value='%{convertToJSON("smap1")}' escapeHtml="false" />;
debug('_p22_smap1:',_p22_smap1);
var _p22_cdperson = false;
if(_p22_smap1!=null && !Ext.isEmpty(_p22_smap1.cdperson))
{
    _p22_cdperson = _p22_smap1.cdperson;
}
debug('_p22_cdperson:',_p22_cdperson);
/* PARA LOADER */

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
	        	 ,hidden      : _p22_cdperson!=false
	        	 ,layout      :
	        	 {
	        	     type     : 'table'
	        	     ,columns : 2
	        	 }
	        	 ,defaults    : { style : 'margin:5px;' }
	        	 ,items       : [ <s:property value="imap.BUSQUEDA" /> ]
	        	 ,buttonAlign : 'center'
	        	 ,buttons     :
	        	 [
	        	     {
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
                ,hidden    : _p22_cdperson!=false
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
	        	        ,handler : function(){
	        	        		 windowAccionistas = undefined;
	        	        		_p22_guardarClic(_p22_datosAdicionalesClic);
	        	        }
	        	    }
	        	    ,{
                        text     : 'Documentos'
                        ,icon    : '${ctx}/resources/fam3icons/icons/printer.png'
                        ,handler : function(){_p22_guardarClic(_p22_documentosClic);}
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
	        	        text     : 'Nueva Persona'
                        ,icon    : '${ctx}/resources/fam3icons/icons/add.png'
                        ,hidden  : _p22_cdperson!=false
                        ,handler : _p22_nuevoClic
	        	    }
	        		,{
	        			text     : 'Guardar'
	        			,icon    : '${ctx}/resources/fam3icons/icons/disk.png'
	        			,handler : function(){_p22_guardarClic(_recargaBusqueda);}
	        		}
	        	]
	        })
	    ]
	});
	////// contenido //////
	
	////// loaders //////
	
	/* PARA EL LOADER */
	if(_p22_cdperson!=false)
	{
	    _p22_loadRecordCdperson();
	}
	/* PARA EL LOADER */
	
	_p22_comboCodPostal().addListener('blur',_p22_heredarColonia);
	_p22_fieldTipoPersona().addListener('change',_p22_tipoPersonaChange);
	_fieldByName('CDNACION').addListener('change',_p22_nacionalidadChange);
	_p22_tipoPersonaChange(_p22_fieldTipoPersona(),'F');
	_p22_nacionalidadChange(_fieldByName('CDNACION'),'001');
	_fieldByName('NMNUMERO').regex = /^[A-Za-z0-9-]*$/;
	_fieldByName('NMNUMERO').regexText = 'Solo d&iacute;gitos, letras y guiones';
    _fieldByName('NMNUMINT').regex = /^[A-Za-z0-9-]*$/;
    _fieldByName('NMNUMINT').regexText = 'Solo d&iacute;gitos, letras y guiones';
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
				'smap1.rfc'     : _p22_formBusqueda().down('[name=rfc]').getValue()
				,'smap1.nombre' : _p22_formBusqueda().down('[name=nombre]').getValue()
				,'smap1.snombre' : _p22_formBusqueda().down('[name=snombre]').getValue()
                ,'smap1.apat'   : _p22_formBusqueda().down('[name=apat]').getValue()
                ,'smap1.amat'   : _p22_formBusqueda().down('[name=amat]').getValue()
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

function _p22_nacionalidadChange(combo,value)
{
    debug('>_p22_nacionalidadChange',value);
    if(value!='001')//extranjero
    {
        _fieldByName('RESIDENTE').show();
        _fieldByName('RESIDENTE').allowBlank = false;
        _fieldByName('RESIDENTE').validate();
    }
    else//nacional
    {
        _fieldByName('RESIDENTE').hide();
        _fieldByName('RESIDENTE').allowBlank = true;
        _fieldByName('RESIDENTE').validate();
    }
    debug('<_p22_nacionalidadChange');
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
        _fieldByName('DSNOMBRE').setFieldLabel('Raz&oacute;n social');
        _fieldByName('FENACIMI').setFieldLabel('Fecha de constituci&oacute;n');
        
        if(value == 'S'){
        	_fieldByName('FENACIMI').allowBlank = true;
        	_fieldByName('FENACIMI').setValue('');
        	_fieldByName('FENACIMI').hide();
        }else {
        	_fieldByName('FENACIMI').allowBlank = false;
        	_fieldByName('FENACIMI').setValue('');
        	_fieldByName('FENACIMI').show();
        }
    }
    else
    {
        _p22_fieldSegundoNombre().show();
        _p22_fieldApat().show();
        _p22_fieldAmat().show();
        _p22_fieldSexo().show();
        _fieldByName('DSNOMBRE').setFieldLabel('Nombre');
        _fieldByName('FENACIMI').setFieldLabel('Fecha de nacimiento');
        
        _fieldByName('FENACIMI').allowBlank = false;
    	_fieldByName('FENACIMI').setValue('');
    	_fieldByName('FENACIMI').show();
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
    return Ext.ComponentQuery.query('[name=CODPOSTAL]')[Ext.ComponentQuery.query('[name=CODPOSTAL]').length-1];
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
    return Ext.ComponentQuery.query('[name=OTFISJUR]')[Ext.ComponentQuery.query('[name=OTFISJUR]').length-1];
}

function _p22_formDatosGenerales()
{
    debug('>_p22_formDatosGenerales<');
    return Ext.ComponentQuery.query('#_p22_formDatosGenerales')[Ext.ComponentQuery.query('#_p22_formDatosGenerales').length-1];
}

/* PARA EL LOADER */
function _p22_loadRecordCdperson()
{
    debug('>_p22_loadRecordCdperson');
    _p22_tabPanel().setLoading(true);
    Ext.Ajax.request(
    {
        url     : _p22_urlCargarPersonaCdperson
        ,params :
        {
            'smap1.cdperson' : _p22_cdperson
        }
        ,success : function(response)
        {
            _p22_tabPanel().setLoading(false);
            var json=Ext.decode(response.responseText);
            if(json.exito)
            {
                _p22_itemclick(null,new _p22_modeloGrid(json.smap2));
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
    debug('<_p22_loadRecordCdperson');
}
/* PARA EL LOADER */

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
    return Ext.ComponentQuery.query('#_p22_tabPanel')[Ext.ComponentQuery.query('#_p22_tabPanel').length-1];
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
                    if(_p22_cdperson!=false&&_p22_parentCallback)
                    {
                        _p22_parentCallback(json);
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
    return Ext.ComponentQuery.query('#_p22_formDomicilio')[Ext.ComponentQuery.query('#_p22_formDomicilio').length-1];
}

function _p22_fieldRFC()
{
    debug('>_p22_fieldRFC<');
    return Ext.ComponentQuery.query('[name=CDRFC]')[Ext.ComponentQuery.query('[name=CDRFC]').length-1];
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

function _recargaBusqueda(){
	debug('Recargando Busqueda');
	_p22_storeGrid.reload();
}

function _p22_datosAdicionalesClic()
{
    debug('>_p22_datosAdicionalesClic');
    _recargaBusqueda();
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
                    ,itemId : '_p22_ventanaDatosAdicionales'
                    ,width  : 650
                    ,height : 600
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
                fieldMail=_fieldByLabel('Correo electrónico');
                if(fieldMail)
                {
                    fieldMail.regex = /^[_A-Z0-9-]+(\.[_A-Z0-9-]+)*@[A-Z0-9-]+(\.[A-Z0-9-]+)*(\.[A-Z]{2,4})$/;
                }
                
				fieldEstCorp = _fieldByLabel('Estructura corporativa');
				var fieldEstCorpAux = Ext.clone(fieldEstCorp);
				
				if(fieldEstCorp){
					var panelDatAdic = fieldEstCorp.up();
					var indEstCorp = panelDatAdic.items.indexOf(fieldEstCorp);
					
					/*debug("fieldEstCorp" , fieldEstCorp);
					
					if(( (indEstCorp) %2) != 0){
						panelDatAdic.insert(indEstCorp,{
	            	    	layout: 'column',
	            	    	border: false,
	            	    	html:'<br/>'
	            	    });
						indEstCorp = indEstCorp + 1;
					}*/
					
					_p22_formDatosAdicionales().items.remove(fieldEstCorp, true);
					fieldEstCorp = fieldEstCorpAux;
					
					panelDatAdic.insert(indEstCorp,{
						xtype      : 'panel',
						//padding    :  '2px 2px 2px 2px',
						defaults : { style : 'margin:3px' },
						border     :  1,
						items      : [fieldEstCorp,{
                       	 xtype    : 'button'
                         	,text     : 'Ver/Editar Accionistas'
                             ,icon     : '${ctx}/resources/fam3icons/icons/award_star_add.png'
                             ,tooltip  : 'Ver/Editar Accionostas'
                             ,handler  : function(button)
                             {
                                verEditarAccionistas(_p22_fieldCdperson().getValue(), fieldEstCorp.getName().substring(fieldEstCorp.getName().length-2, fieldEstCorp.getName().length), fieldEstCorp.getValue());
                             }
 					}]
					});
					
					fieldEstCorp.addListener('beforeselect',function (combo){
						if(windowAccionistas){
							
							var valorAnterior = combo.getValue();
							
							Ext.Msg.show({
		    		            title: 'Confirmar acci&oacute;n',
		    		            msg  : 'Si cambia la Estructura Coorporativa perder&aacute; la lista de Accionistas guardada. &iquest;Desea continuar?',
		    		            buttons: Ext.Msg.YESNO,
		    		            fn: function(buttonId, text, opt) {
		    		            	if(buttonId == 'yes') {
		    		            		
		    		            		windowAccionistas = undefined;
		    		            		
		    		            		Ext.Ajax.request(
		    		                	        {
		    		                	            url       : _UrlEliminaAccionistas
		    		                	            ,params: {
	    		                	            		'params.pv_cdperson_i':   _p22_fieldCdperson().getValue(),
	    		                    	            	'params.pv_cdatribu_i':  fieldEstCorp.getName().substring(fieldEstCorp.getName().length-2, fieldEstCorp.getName().length)
	    		                	            	}
		    		                	            ,success  : function(response)
		    		                	            {
		    		                	                _p22_formDatosAdicionales().setLoading(false);
		    		                	                var json = Ext.decode(response.responseText);
		    		                	                debug('response text:',json);
		    		                	                if(json.exito)
		    		                	                {
		    		                	                    mensajeCorrecto('Aviso','Datos de Accionistas eliminados correctamente.');
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
		    		            	
		    		            		
		    		            	}else{
		    		            		combo.setValue(valorAnterior);
		    		            	}
		            			},
		    		            icon: Ext.Msg.QUESTION
		        			});	
						}else {
							return true;
						}
					});
				}
				
				
                _p22_formDatosAdicionales().loadRecord(new _p22_modeloTatriper(json.smap2));
                var itemsDocumento=Ext.ComponentQuery.query('[tieneDocu]');
                debug('itemsDocumento:',itemsDocumento);
                
                /*debug("agregar espacio docs: ", _p22_formDatosAdicionales().items.getCount());
                if(( (_p22_formDatosAdicionales().items.getCount()) %2 ) == 0){
                	_p22_formDatosAdicionales().add({
            	    	layout: 'column',
            	    	border: false,
            	    	html:'<br/>'
            	     });
                }*/
                
                for(var i=0;i<itemsDocumento.length;i++)
                {
                    itemDocumento=itemsDocumento[i];
                    
                    
                    if('DOC' == itemDocumento.tieneDocu){
	                    itemDocumento.up().add(
	                    		{
	        						xtype      : 'panel',
	        						//padding    :  '2px 2px 2px 2px',
	        						defaults : { style : 'margin:3px' },
	        						border     :  1,
	        						items      : [
	        						         Ext.clone(itemDocumento),
	        						        {
	        	                       		 xtype    : 'panel'
	        		                        ,layout  : 'hbox'
	        		                        ,border  : 0
	        		                        ,items   :
	        		                        [
	        		                            {
	        		                                xtype       : 'displayfield'
	        		                                ,labelWidth : 180
	        		                                ,fieldLabel : 'Documento digital' + (itemDocumento.allowBlank==false ? '<span style="font-size:10px;">(obligatorio)</span>' : '')
	        		                            }
	        		                            ,{
	        		                                xtype     : 'button'
	        		                                ,icon     : '${ctx}/resources/fam3icons/icons/arrow_up.png'
	        		                                ,tooltip  : 'Subir nuevo'
	        		                                ,codidocu : itemDocumento.codidocu
	        		                                ,descrip  : itemDocumento.fieldLabel
	        		                                ,handler  : function(button)
	        		                                {
	        		                                    _p22_subirArchivo(_p22_fieldCdperson().getValue(),button.codidocu,button.descrip);
	        		                                }
	        		                            },{
	        		                                xtype     : 'button'
	        		                                ,icon     : '${ctx}/resources/fam3icons/icons/eye.png'
	        		                                ,tooltip  : 'Descargar'
	        		                                ,codidocu : itemDocumento.codidocu
	        		                                ,descrip  : itemDocumento.fieldLabel
	        		                                ,handler  : function(button)
	        		                                {
	        		                                    _p22_cargarArchivo(_p22_fieldCdperson().getValue(),button.codidocu,button.descrip);
	        		                                }
	        		                            }
	        		                        ]
	        		                    }]
	        					}
	                    	);
	                }/*else{
	                	itemDocumento.up().add({
                	    	layout: 'column',
                	    	border: false,
                	    	html:'<br/>'
                	     });
	                }*/
                    //itemDocumento.destroy();
                    //itemDocumento.allowBlank = true;
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
    debug('<_p22_datosAdicionalesClic');
}

function _p22_guardarDatosAdicionalesClic()
{
    debug('>_p22_guardarDatosAdicionalesClic');
    
    var saveList = [];
    var updateList = [];
    var deleteList = [];
    
    if(windowAccionistas){
    	accionistasStore.getRemovedRecords().forEach(function(record,index,arr){
        	deleteList.push(record.data);
    	});
        accionistasStore.getNewRecords().forEach(function(record,index,arr){
    		if(record.dirty) saveList.push(record.data);
    	});
        accionistasStore.getUpdatedRecords().forEach(function(record,index,arr){
    		updateList.push(record.data);
    	});
    }
	
    debug('Accionistas Removed: ' , deleteList);
    debug('Accionistas Added: '   , saveList);
    debug('Accionistas Updated: ' , updateList);
    
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
    
    if(valido && (deleteList.length > 0 || saveList.length > 0 || updateList.length > 0)){
    	_p22_formDatosAdicionales().setLoading(true);
    	
    	Ext.Ajax.request(
    	        {
    	            url       : _UrlGuardaAccionista
    	            ,jsonData :
    	            {
    	            	params: {
    	            		'pv_cdperson_i':   _p22_fieldCdperson().getValue(),
        	            	'pv_cdatribu_i':  fieldEstCorp.getName().substring(fieldEstCorp.getName().length-2, fieldEstCorp.getName().length),
        	            	'pv_cdtpesco_i':  fieldEstCorp.getValue()
    	            	},
    	                'saveList'   : saveList,
    	                'deleteList' : deleteList,
    	                'updateList' : updateList
    	            }
    	            ,success  : function(response)
    	            {
    	                _p22_formDatosAdicionales().setLoading(false);
    	                var json = Ext.decode(response.responseText);
    	                debug('response text:',json);
    	                if(json.exito)
    	                {
    	                	windowAccionistas = undefined;
    	                    debug('Datos de Accionistas guardados correctamente.');
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

function _p22_documentosClic()
{
    debug('>_p22_documentosClic');
    centrarVentanaInterna(Ext.create('Ext.window.Window',
    {
        title        : 'Documentos'
        ,modal       : true
        ,buttonAlign : 'center'
        ,width       : 600
        ,height      : 400
        ,autoScroll  : true
        ,loader      :
        {
            url       : _p22_urlPantallaDocumentos
            ,params   :
            {
                'smap1.cdperson'  : _p22_fieldCdperson().getValue()
            }
            ,scripts  : true
            ,autoLoad : true
        }
    }).show());
    debug('<_p22_documentosClic');
}

function _p22_subirArchivo(cdperson,codidocu,descrip)
{
    debug('>_p22_subirArchivo',cdperson,codidocu,descrip);
    _p22_windowAgregarDocu=Ext.create('Ext.window.Window',
            {
                id           : '_p22_WinPopupAddDoc'
                ,title       : 'Agregar documento'
                ,closable    : false
                ,modal       : true
                ,width       : 500
                //,height   : 700
                ,bodyPadding : 5
                ,items       :
                [
                    panelSeleccionDocumento= Ext.create('Ext.form.Panel',
                    {
                        border       : 0
                        ,url         : _p22_urlSubirArchivo
                        ,buttonAlign : 'center'
                        ,items       :
                        [
                            {
                                xtype       : 'datefield'
                                ,readOnly   : true
                                ,format     : 'd/m/Y'
                                ,name       : 'smap1.fecha'
                                ,value      : new Date()
                                ,fieldLabel : 'Fecha'
                            }
                            ,{
                                xtype       : 'textfield'
                                ,fieldLabel : 'Descripci&oacute;n'
                                ,name       : 'smap1.descripcion'
                                ,value      : descrip
                                ,readOnly   : true
                                ,width      : 450
                            }
                            ,{
                                xtype       : 'filefield'
                                ,fieldLabel : 'Documento'
                                ,buttonText : 'Examinar...'
                                ,buttonOnly : false
                                ,width      : 450
                                ,name       : 'file'
                                ,cAccept    : ['jpg','png','gif','zip','pdf','rar','jpeg','doc','docx','xls','xlsx','ppt','pptx']
                                ,listeners  :
                                {
                                    change : function(me)
                                    {
                                        var indexofPeriod = me.getValue().lastIndexOf("."),
                                        uploadedExtension = me.getValue().substr(indexofPeriod + 1, me.getValue().length - indexofPeriod).toLowerCase();
                                        if (false&&!Ext.Array.contains(this.cAccept, uploadedExtension))
                                        {
                                            Ext.MessageBox.show(
                                            {
                                                title   : 'Error de tipo de archivo',
                                                msg     : 'Extensiones permitidas: ' + this.cAccept.join(),
                                                buttons : Ext.Msg.OK,
                                                icon    : Ext.Msg.WARNING
                                            });
                                            me.reset();
                                            Ext.getCmp('_p22_botGuaDoc').setDisabled(true);
                                        }
                                        else
                                        {
                                            Ext.getCmp('_p22_botGuaDoc').setDisabled(false);
                                        }
                                    }
                                }
                            }
                            ,Ext.create('Ext.panel.Panel',
                            {
                                html    :'<iframe id="_p22_IframeUploadDoc" name="_p22_IframeUploadDoc"></iframe>'
                                ,hidden : true
                            })
                            ,Ext.create('Ext.panel.Panel',
                            {
                                border  : 0
                                ,html   :'<iframe id="_p22_IframeUploadPro" name="_p22_IframeUploadPro" width="100%" height="30" src="'+_p22_UrlUploadPro+'" frameborder="0"></iframe>'
                                ,hidden : false
                            })
                        ]
                        ,buttons     :
                        [
                            {
                                id        : '_p22_botGuaDoc'
                                ,text     : 'Agregar'
                                ,icon     : '${ctx}/resources/fam3icons/icons/disk.png'
                                ,disabled : true
                                ,handler  : function (button,e)
                                {
                                    debug(button.up().up().getForm().getValues());
                                    button.setDisabled(true);
                                    Ext.getCmp('_p22_BotCanDoc').setDisabled(true);
                                    Ext.create('Ext.form.Panel').submit(
                                    {
                                        url             : _p22_UrlUploadPro
                                        ,standardSubmit : true
                                        ,target         : '_p22_IframeUploadPro'
                                        ,params         :
                                        {
                                            uploadKey : '1'
                                        }
                                    });
                                    button.up().up().getForm().submit(
                                    {
                                        standardSubmit : true
                                        ,target        : '_p22_IframeUploadDoc'
                                        ,params        :
                                        {
                                            'smap1.cdperson'  : cdperson
                                            ,'smap1.codidocu' : codidocu
                                        }
                                    });
                                }
                            }
                            ,{
                                id       : '_p22_BotCanDoc'
                                ,text    : 'Cancelar'
                                ,icon    : '${ctx}/resources/fam3icons/icons/cancel.png'
                                ,handler : function (button,e)
                                {
                                    _p22_windowAgregarDocu.destroy();
                                }
                            }
                        ]
                    })
                ]
            }).show();
            centrarVentanaInterna(_p22_windowAgregarDocu);
    debug('<_p22_subirArchivo');
}

function _p22_cargarArchivo(cdperson,codidocu,dsdocume)
{
    debug('>_p22_cargarArchivo',cdperson,codidocu,dsdocume);
    Ext.ComponentQuery.query('#_p22_ventanaDatosAdicionales')[0].setLoading(true);
    Ext.Ajax.request(
    {
        url      : _p22_urlCargarNombreArchivo
        ,params  :
        {
            'smap1.cdperson'  : cdperson
            ,'smap1.codidocu' : codidocu
        }
        ,success : function(response)
        {
            Ext.ComponentQuery.query('#_p22_ventanaDatosAdicionales')[0].setLoading(false);
            var json=Ext.decode(response.responseText);
            debug('json response:',json);
            if(json.exito)
            {
                var numRand=Math.floor((Math.random()*100000)+1);
                debug('numRand a: ',numRand);
                var windowVerDocu=Ext.create('Ext.window.Window',
                {
                    title          : dsdocume
                    ,width         : 700
                    ,height        : 500
                    ,collapsible   : true
                    ,titleCollapse : true
                    ,html          : '<iframe innerframe="'+numRand+'" frameborder="0" width="100" height="100"'
                                     +'src="'+_p22_urlViewDoc+'?idPoliza='+cdperson+'&filename='+json.smap1.cddocume+'">'
                                     +'</iframe>'
                    ,listeners     :
                    {
                        resize : function(win,width,height,opt){
                            debug(width,height);
                            $('[innerframe="'+numRand+'"]').attr({'width':width-20,'height':height-60});
                        }
                    }
                }).show();
                centrarVentanaInterna(windowVerDocu);
            }
            else
            {
                mensajeError(json.respuesta);
            }
        }
        ,failure : function()
        {
            Ext.ComponentQuery.query('#_p22_ventanaDatosAdicionales')[0].setLoading(false);
            errorComunicacion();
        }
    });
    
    debug('<_p22_cargarArchivo');
}

function panDocSubido()
{
    _p22_windowAgregarDocu.destroy();
}


function verEditarAccionistas(cdperson, cdatribu, cdestructcorp){
	
	
	if(Ext.isEmpty(cdestructcorp)){
		mensajeWarning('Debe seleccionar una Estructura Coorporativa.');
		return;
	}
	
	if(!windowAccionistas){
		Ext.define('modeloAccionistas',{
	        extend  : 'Ext.data.Model'
	        ,fields :
	        [
	            'NMORDINA'
	            ,'DSNOMBRE'
	            ,'CDNACION'
	            ,'PORPARTI'
	        ]
		});
		
		accionistasStore = Ext.create('Ext.data.Store',
			    {
					pageSize : 20,
			        autoLoad : true
			        ,model   : 'modeloAccionistas'
			        ,proxy   :
			        {
			            type         : 'memory'
			            ,enablePaging : true
			            ,reader      : 'json'
			            ,data        : []
			        }
			    });
		
		gridAccionistas = Ext.create('Ext.grid.Panel',
		    {
		    title    : 'Para Editar un Accionista de Doble Clic en la fila deseada.'
		    ,height  : 200
		    ,plugins : Ext.create('Ext.grid.plugin.RowEditing',
		    {
		    	pluginId: 'accionistasRowId',
		        clicksToEdit  : 2,
		        errorSummary : false
		        
		    })
		    ,tbar     :
		        [
		            {
		                text     : 'Agregar'
		                ,icon    : '${ctx}/resources/fam3icons/icons/add.png'
		                ,handler : function(){
		                	accionistasStore.add(new modeloAccionistas());
		                	gridAccionistas.getPlugin('accionistasRowId').startEdit(accionistasStore.getCount()-1,0);
		                }
		            },{
		                text     : 'Eliminar'
			                ,icon    : '${ctx}/resources/fam3icons/icons/delete.png'
			                ,handler : function(){
			                	accionistasStore.remove(gridAccionistas.getSelectionModel().getSelection());
			                }
			            }
		        ]
		    ,columns :
		    [
		        {
		            header     : 'Accionista'
		            ,dataIndex : 'DSNOMBRE'
		            ,flex      : 1
		            ,editor    :
		            {
		                xtype             : 'textfield'
		                ,allowBlank       : false
		            }
		        }
		        ,{
		            header     : 'Nacionalidad'
		            ,dataIndex : 'CDNACION'
		            ,flex      : 1
		            ,renderer  : function(valor){
		            	return rendererColumnasDinamico(valor,'CDNACION'); 
		            }
		            ,editor    :
		            {
		                xtype         : 'combobox',
		                allowBlank    : false,
		                name          : 'CDNACION',
		                valueField    : 'key',
		                displayField  : 'value',
		                forceSelection: true,
		                typeAhead     : true,
		                anyMatch      : true,
		                store         : Ext.create('Ext.data.Store', {
		                    model     : 'Generic',
		                    autoLoad  : true,
		                    proxy     : {
		                        type        : 'ajax'
		                        ,url        : _URL_CARGA_CATALOGO
		                        ,extraParams: {catalogo:_CAT_NACIONALIDAD}
		                        ,reader     :
		                        {
		                            type  : 'json'
		                            ,root : 'lista'
		                        }
		                    }
		                })
		               
		            }
		        }
		        ,{
		            header     : 'Porcentaje Participaci&oacute;n'
		            ,dataIndex : 'PORPARTI'
		            ,flex      : 1
		            ,editor    :
		            {
		                xtype             : 'numberfield'
		                ,allowBlank       : false
		                ,allowDecimals    : true
		                ,decimalSeparator : '.'
		            }
		        }
		    ]
		    ,store : accionistasStore
		});
		
		windowAccionistas = Ext.create('Ext.window.Window', {
	          title: 'Accionistas',
	          closeAction: 'close',
	          modal:true,
	          height : 320,
	          width  : 800,
		      items: [gridAccionistas],
	          bodyStyle:'padding:15px;',
	          buttons:[
	           {
	                 text: 'Aceptar',
	                 icon:'${ctx}/resources/fam3icons/icons/accept.png',
	                 handler: function() {
	                	 windowAccionistas.close();
	                 }
	           }
	          ]
	        });
		
			var params = {
				'params.pv_cdperson_i' : cdperson,
				'params.pv_cdatribu_i' : cdatribu,
				'params.pv_cdtpesco_i' : cdestructcorp
			};
			
			cargaStorePaginadoLocal(accionistasStore, _UrlCargaAccionistas, 'slist1', params, function (options, success, response){
	    		if(success){
	                var jsonResponse = Ext.decode(response.responseText);
	                
	                if(!jsonResponse.success) {
	                    showMessage(_MSG_SIN_DATOS, _MSG_BUSQUEDA_SIN_DATOS, Ext.Msg.OK, Ext.Msg.INFO);
	                }
	            }else{
	                showMessage('Error', 'Error al obtener los datos.', Ext.Msg.OK, Ext.Msg.ERROR);
	            }
	    	}, gridAccionistas);
	}
	
			windowAccionistas.show();
			
}


////// funciones //////
</script>
</head>
<body>
<div id="_p22_divpri" style="height : 700px;"></div>
</body>
</html>