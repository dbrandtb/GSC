<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Cotizar</title>
<link rel="stylesheet" href="${ctx}/resources/touch-2.3.1/resources/css/sencha-touch.css"></style>
<script src="${ctx}/resources/touch-2.3.1/sencha-touch-all.js"></script>
<script src="${ctx}/resources/touch-2.3.1/src/locale/ext-lang-es.js"></script>
<script>
////// overrides //////
Ext.util.Format.usMoney = function (v)
{
    v = (Math.round((v - 0) * 100)) / 100;
    v = (v == Math.floor(v)) ? v + ".00" : ((v * 10 == Math.floor(v * 10)) ? v + "0" : v);
    return ('$' + v);//.replace(/\./, ',');
};

Ext.override(Ext.field.Select,
{
    initialize : function()
    {
        this.callParent();
        this.setDefaultPhonePickerConfig(
        {
            cancelButton : 'Cancelar'
            ,doneButton  : 'Aceptar'
        });
        this.setDefaultTabletPickerConfig(
        {
            cancelButton : 'Cancelar'
            ,doneButton  : 'Aceptar'
        });
    }
});
Ext.override(Ext.Picker,
{
    initialize : function()
    {
        this.callParent();
        this.setCancelButton('Cancelar');
        this.setDoneButton('Aceptar');
    }
});
////// overrides //////

////// variables //////
var _mcotiza_navView;
var _mcotiza_storeIncisos;

var _mcotiza_urlCotizar = '<s:url namespace="/emision"     action="cotizar" />';
var _mcotiza_urlViewDoc = '<s:url namespace ="/documentos" action="descargaDocInline" />';

var _mcotiza_smap1         = <s:property value="%{convertToJSON('smap1')}"  escapeHtml="false" />;
var EDAD_MAXIMA_COTIZACION = <s:property value="smap1.edadMaximaCotizacion" />;

var _mcotiza_urlImprimirCotiza = '<s:text name="ruta.servidor.reports" />';
var _mcotiza_reportsServerUser = '<s:text name="pass.servidor.reports" />';
var _mcotiza_reporteCotizacion = '<s:text name="rdf.cotizacion.nombre" />';

debug('_mcotiza_smap1:',_mcotiza_smap1);
////// variables //////

////// funciones //////
function _mcotiza_imprimir()
{
    var me = this;
    var urlRequestImpCotiza = _mcotiza_urlImprimirCotiza
            + '?p_cdplan='
            + _mcotiza_selectedCdplan
            + '&p_estado=W'
            + '&p_poliza='
            + Ext.ComponentQuery.query('#_mcotiza_nmpolizaField')[0].getValue()
            + '&p_unieco='
            + _mcotiza_smap1.cdunieco
            + '&p_ramo='
            + _mcotiza_smap1.cdramo
            + '&p_cdusuari='
            + _mcotiza_smap1.user
            + '&p_ntramite='
            + _mcotiza_smap1.ntramite
            + '&destype=cache'
            + "&desformat=PDF"
            + "&userid="
            + _mcotiza_reportsServerUser
            + "&ACCESSIBLE=YES"
            + "&report="
            + _mcotiza_reporteCotizacion
            + "&paramform=no";
    debug(urlRequestImpCotiza);
    var numRand = Math.floor((Math.random() * 100000) + 1);
    debug(numRand);
    var windowVerDocu = Ext.create('Ext.Container',
    {
        title : 'Impresi&oacute;n'
        ,html : '<iframe innerframe="'
                + numRand
                + '" frameborder="0" width="800" height="800"'
                + 'src="'
                + _mcotiza_urlViewDoc
                + "?contentType=application/pdf&url="
                + encodeURIComponent(urlRequestImpCotiza)
                + "\">"
                + '</iframe>'
    });
    _mcotiza_navView.push(windowVerDocu);
}

function _mcotiza_tarifaSelect(columnName, record, row, column, eOpts)
{
    debug('record',record);
    debug('columnName',columnName);
    if(columnName=='DSPERPAG')
    {
        debug('DSPERPAG');
        Ext.ComponentQuery.query('#_mcotiza_botImprimirId')[0].setDisabled(true);
        Ext.ComponentQuery.query('#_mcotiza_botImprimirId')[0].setText('Sin plan');
        /*
        Ext.getCmp('_mcotiza_botDetallesId').setDisabled(true);
        Ext.getCmp('_mcotiza_botCoberturasId').setDisabled(true);
        Ext.getCmp('_mcotiza_botMailId').setDisabled(true);
        Ext.getCmp('_mcotiza_botImprimirId').setDisabled(true);
        Ext.getCmp('_mcotiza_botComprarId').setDisabled(true);
        */
    }
    else
    {
        // M N P R I M A X
        //0 1 2 3 4 5 6 7
        _mcotiza_selectedCdperpag = record.get("CDPERPAG");
        _mcotiza_selectedCdplan   = columnName.substr(7);
        _mcotiza_selectedDsplan   = record.get("DSPLAN"+_mcotiza_selectedCdplan);
        _mcotiza_selectedNmsituac = record.get("NMSITUAC");
        debug('_mcotiza_selectedCdperpag',_mcotiza_selectedCdperpag);
        debug('_mcotiza_selectedCdplan',_mcotiza_selectedCdplan);
        debug('_mcotiza_selectedDsplan',_mcotiza_selectedDsplan);
        debug('_mcotiza_selectedNmsituac',_mcotiza_selectedNmsituac);
        Ext.ComponentQuery.query('#_mcotiza_botImprimirId')[0].setDisabled(false);
        Ext.ComponentQuery.query('#_mcotiza_botImprimirId')[0].setText('Imprimir '+_mcotiza_selectedDsplan);
        /*
        Ext.getCmp('_mcotiza_botDetallesId').setDisabled(false);
        Ext.getCmp('_mcotiza_botCoberturasId').setDisabled(false);
        Ext.getCmp('_mcotiza_botMailId').setDisabled(false);
        Ext.getCmp('_mcotiza_botImprimirId').setDisabled(false);
        Ext.getCmp('_mcotiza_botComprarId').setDisabled(false);
        */
    }
}

function _mcotiza_construirGrid(json)
{
	debug('>_mcotiza_construirGrid');
	debug('json:',json);
	
	Ext.define('_mcotiza_modeloTarifa',
    {
        extend  : 'Ext.data.Model'
        ,config :
        {
        	fields : Ext.JSON.decode(json.smap1.fields)
        }
    });
	
	var columns = Ext.JSON.decode(json.smap1.columnas,false);
	debug('columns:',columns);
	var grid = Ext.create('Ext.grid.Grid',
	{
		title    : 'Cotizaci&oacute;n'
		,columns : columns
		,items   :
		[
		    {
		    	xtype   : 'toolbar'
		    	,docked : 'bottom'
	    		,layout :
                   {
                       pack : 'center'
                   }
		    	,items  :
		    	[
		    	    {
		    	    	xtype     : 'button'
		    	    	,text     : 'Imprimir'
		    	    	,disabled : true
		    	    	,itemId   : '_mcotiza_botImprimirId'
		    	    	,handler  : _mcotiza_imprimir
		    	    }
		    	]
		    }
		]
		,store   : Ext.create('Ext.data.Store',
        {
            model : '_mcotiza_modeloTarifa'
            ,data : json.slist2
        })
        ,listeners:
        {
        	itemtap: function (dataview, index, target, record, e, eOpts)
        	{
        		var colText      = e.target.$column._text;
        		var colDataIndex = e.target.$column._dataIndex;
        		var colIndex     = -1;
        		debug('dataview:',dataview);
        		debug('event:',e);
        		debug('colText:',colText);
        		debug('colDataIndex:',colDataIndex);
        		for(var i=0;i<dataview._columns.length;i++)
        		{
        			var column = dataview._columns[i];
        			if(column.text==colText)
        			{
        				debug('indice:',i);
        				colIndex = i;
        			}
        		}
        		debug('colIndex:',colIndex);
        		_mcotiza_tarifaSelect(colDataIndex, record, index, colIndex, eOpts);
        	}
        }
	});
	_mcotiza_navView.push(grid);
	debug('<_mcotiza_construirGrid');
}

function _mcotiza_validarCustom()
{
    var valido=true;
    var cdunieco = _mcotiza_smap1.cdunieco;
    var cdramo   = _mcotiza_smap1.cdramo;
    var cdtipsit = _mcotiza_smap1.cdtipsit;
    debug('_mcotiza_validarCustom');
    debug('cdunieco' , cdunieco);
    debug('cdramo'   , cdramo);
    debug('cdtipsit' , cdtipsit);
    
    //////////////////////////
    ////// para SL y SN //////
    if(valido&&(cdtipsit=='SL'||cdtipsit=='SN'))
    {
        //////////////////////////////////////
        ////// repeticion de parentesco //////
        if(valido)
        {
            var nTitular   = 0;
            var nConyugue  = 0;
            _mcotiza_storeIncisos.each(function(record)
            {
                if(record.get('parametros.pv_otvalor16')=='T')
                {
                    nTitular=nTitular+1;
                }
                else if(record.get('parametros.pv_otvalor16')=='C')
                {
                    nConyugue=nConyugue+1;
                }
            });
            valido=nTitular==1&&nConyugue<2;
            if(!valido)
            {
                Ext.Msg.alert('Aviso','Solo puede haber un titular y un(a) c&oacute;nyugue',function()
                {
                	_mcotiza_navView.items.items[0].setActiveItem(2);
                });
            }
        }
        ////// repeticion de parentesco //////
        //////////////////////////////////////
        
        ////////////////////////////////
        ////// validacion de edad maxima de cotizacion //////
        if(valido)
        {
            var algunMayor = false;
            _mcotiza_storeIncisos.each(function(record)
            {
                var fechaNacimiento = new Date(record.get('parametros.pv_otvalor02'));
                var hoy = new Date();
                var edad = parseInt((hoy
                        / 365
                        / 24
                        / 60
                        / 60
                        / 1000 - fechaNacimiento
                        / 365
                        / 24
                        / 60
                        / 60
                        / 1000));
                if (edad > EDAD_MAXIMA_COTIZACION)
                {
                    algunMayor = true;
                }
            });
            valido = !algunMayor;
            if(!valido)
            {
                Ext.Msg.alert('Aviso','La edad del asegurado no debe exceder de '+EDAD_MAXIMA_COTIZACION+' a&ntilde;os',function()
                {
                	_mcotiza_navView.items.items[0].setActiveItem(2);
                });
            }
        }
        ////// validacion de edad maxima de cotizacion //////
        ////////////////////////////////
    }
    ////// para SL y SN //////
    //////////////////////////
    
    ////// para SN //////
    if(valido&&cdtipsit=='SN')
    {
        var cp=_mcotiza_formAgrupados.items.items[2].getValue();
        debug('cp',cp);
        valido=cp>=36000&&cp<=38998;
        if(!valido)
        {
            Ext.Msg.alert('Aviso','C&oacute;digo postal no v&aacute;lido para este producto',function()
            {
            	_mcotiza_navView.items.items[0].setActiveItem(0);
            });
        }
    }
    ////// para SN //////
    debug('_mcotiza_validarCustom fin');
    return valido;
}

function _mcotiza_validaAsegurado(values)
{
	var valido = true;
	if(valido)
	{
		for(var att in values)
        {
            if(att!='rowIndex' && att!='nombre2')
            {
                if(values[att]==null || values[att]==undefined || values[att]=='')
                {
                    valido = false;
                }
            }
        }
        if(!valido)
        {
            Ext.Msg.alert('Aviso','Verifica los datos');
        }
	}
	return valido;
}

function _mcotiza_cotiza()
{
	debug('_mcotiza_cotiza');
	var valido = true;
	if(valido)
	{
		var values = _mcotiza_getFormDatosGenerales().getValues();
		debug('values:',values);
		for(var att in values)
		{
			if(att!='nmpoliza')
			{
				if(values[att]==null || values[att]==undefined || values[att]=='')
				{
					valido = false;
				}
			}
		}
		if(!valido)
		{
			Ext.Msg.alert('Aviso','Verifica los datos generales',function()
			{
				_mcotiza_navView.items.items[0].setActiveItem(0);
			});
		}
	}
	if(valido)
	{
		valido = _mcotiza_storeIncisos.getCount()>0;
		if(!valido)
		{
			Ext.Msg.alert('Aviso','Introduce un asegurado',function()
            {
                _mcotiza_navView.items.items[0].setActiveItem(1);
            });
		}
	}
	if(valido)
	{
		valido = _mcotiza_validarCustom();
	}
	if(valido)
	{
		var json=
        {
            slist1 : []
            ,smap1 : _mcotiza_smap1
        };
        _mcotiza_storeIncisos.each(function(record)
        {
            var inciso=_mcotiza_getFormDatosGenerales().getValues();
            for(var key in inciso)
            {
                var value=inciso[key];
                debug(typeof value,key,value);
                if((typeof value=='object')&&value&&value.getDate)
                {
                    debug('fecha object');
                    var fecha='';
                    fecha+=value.getDate();
                    fecha+='/';
                    fecha+=value.getMonth()+1<10?
                            (('x'+(value.getMonth()+1)).replace('x','0'))
                            :(value.getMonth()+1);
                    fecha+='/';
                    fecha+=value.getFullYear();
                    value=fecha;
                    debug('pasado a:',value);
                    inciso[key]=value;
                }
            }
            for(var key in record.data)
            {
                var value=record.data[key];
                debug(typeof value,key,value);
                if((typeof value=='object')&&value&&value.getDate)
                {
                	debug('fecha object');
                    var fecha='';
                    fecha+=value.getDate();
                    fecha+='/';
                    fecha+=value.getMonth()+1<10?
                            (('x'+(value.getMonth()+1)).replace('x','0'))
                            :(value.getMonth()+1);
                    fecha+='/';
                    fecha+=value.getFullYear();
                    value=fecha;
                    debug('pasado a:',value);
                }
                else
                {
                	debug('no fecha object');
                }
                inciso[key]=value;
            }
            json['slist1'].push(inciso);
        });
        debug(json);
        Ext.Ajax.request(
        {
            url       : _mcotiza_urlCotizar
            ,jsonData : json
            ,success  : function(response)
            {
            	json = Ext.decode(response.responseText);
            	debug('response:',json);
            	Ext.ComponentQuery.query('#_mcotiza_nmpolizaField')[0].setValue(json.smap1.nmpoliza);
            	_mcotiza_construirGrid(json);
            }
            ,failure  : function()
            {
            	Ext.Msg.alert('Aviso','Error al cotizar');
            }
        });
	}
}

function _g_heredarCombo(remoto,compId,compAnteriorId)
{
	var thisCmp=Ext.getCmp(compId);
	debug('Heredar "+name+"');
	if(!thisCmp.noEsPrimera||remoto==true)
	{
		debug('Hereda por primera vez o porque la invoca el padre');
		thisCmp.noEsPrimera=true;
		thisCmp.getStore().load(
		{
			params    :
			{
				'params.idPadre':Ext.getCmp(compAnteriorId).getValue()
			}
		    ,callback : function()
		    {
		    	
		    	var valorActual=thisCmp.getValue();
		    	var dentro=false;
		    	thisCmp.getStore().each(function(record)
		    	{
		    		if(valorActual==record.get('key'))
		    		{
		    			dentro=true;
		    		}
		    	});
		    	if(!dentro)
		    	{
		    		thisCmp.reset();
		    	}
		    }
		});
	}
	else
	{
		debug('No hereda porque es un change repetitivo');
	}
}

function _mcotiza_incisoItemDisclosure(listPanel,event,rowIndex)
{
	debug('>_mcotiza_incisoItemDisclosure');
	var recordTmp = _mcotiza_storeIncisos.getAt(rowIndex);
	debug('recordTmp:',recordTmp);
	var formIncisoTmp = _mcotiza_getFormInciso();
	debug('formIncisoTmp:',formIncisoTmp);
	formIncisoTmp.setRecord(recordTmp);
	formIncisoTmp.items.items[0].items.items[0].setValue(rowIndex);
	_mcotiza_navView.items.items[0].setActiveItem(1);
	debug('<_mcotiza_incisoItemDisclosure');
}

function _mcotiza_getFormDatosGenerales()
{
	return Ext.ComponentQuery.query('#_mcotiza_formDatosGene')[0];
}

function _mcotiza_getFormInciso()
{
	return Ext.ComponentQuery.query('#_mcotiza_formAsegurados')[0];
}

function _mcotiza_nuevoAsegurado()
{
	debug('>_mcotiza_nuevoAsegurado');
	var valuesTmp = _mcotiza_getFormInciso().getValues();
	debug('valuesTmp:',valuesTmp);
	var valido = _mcotiza_validaAsegurado(valuesTmp);
	if(valido)
	{
		var recordTmp = new _mcotiza_Inciso(valuesTmp);
		debug('recordTmp:',recordTmp);
		_mcotiza_storeIncisos.add(recordTmp);
		_mcotiza_getFormInciso().reset();
		_mcotiza_navView.items.items[0].setActiveItem(2);
	}
	debug('<_mcotiza_nuevoAsegurado');
}

function _mcotiza_borrarAsegurado()
{
    debug('>_mcotiza_borrarAsegurado');
    var valido   = true;
    var rowIndex = _mcotiza_getFormInciso().items.items[0].items.items[0].getValue();
    debug('rowIndex:',rowIndex,(rowIndex+'').length,'a');
    if(valido)
    {
    	valido = rowIndex!=null&&rowIndex!=undefined&&(rowIndex+'').length>0;
    	if(!valido)
    	{
    		Ext.Msg.alert('Aviso','Debes seleccionar un asegurado');
    	}
    }
    if(valido)
    {
    	_mcotiza_getFormInciso().items.items[0].items.items[0].setValue('');
    	_mcotiza_storeIncisos.removeAt(rowIndex);
    	_mcotiza_getFormInciso().reset();
    	_mcotiza_navView.items.items[0].setActiveItem(2);
    }
    debug('<_mcotiza_borrarAsegurado');
}

function _mcotiza_editarAsegurado()
{
    debug('>_mcotiza_editarAsegurado');
    var valuesTmp = _mcotiza_getFormInciso().getValues();
    debug('valuesTmp:',valuesTmp);
    var valido   = _mcotiza_validaAsegurado(valuesTmp);
    var rowIndex = _mcotiza_getFormInciso().items.items[0].items.items[0].getValue();
    debug('rowIndex:',rowIndex,(rowIndex+'').length,'a');
    if(valido)
    {
        valido = rowIndex!=null&&rowIndex!=undefined&&(rowIndex+'').length>0;
        if(!valido)
        {
            Ext.Msg.alert('Aviso','Debes seleccionar un asegurado');
        }
    }
    if(valido)
    {
        _mcotiza_getFormInciso().items.items[0].items.items[0].setValue('');
        _mcotiza_storeIncisos.removeAt(rowIndex);
        var recordTmp = new _mcotiza_Inciso(valuesTmp);
        debug('recordTmp:',recordTmp);
        _mcotiza_storeIncisos.insert(rowIndex,recordTmp);
        _mcotiza_getFormInciso().reset();
        _mcotiza_navView.items.items[0].setActiveItem(2);
    }
    debug('<_mcotiza_editarAsegurado');
}

function debug(a,b,c,d)
{
	if(d!=undefined)
	{
		console.log(a,b,c,d);
	}
	else if(c!=undefined)
	{
		console.log(a,b,c);
	}
	else if(b!=undefined)
	{
		console.log(a,b);
	}
	else
	{
		console.log(a);
	}
}
////// funciones //////

Ext.setup({onReady:function()
{
	////// modelos //////
	Ext.define('MiSelectField',
	{
		extend  : 'Ext.field.Select'
		,config :
		{
			
		}
	    ,initialize : function()
	    {
	    	debug('MiSelectField initialize');
	    	var me = this;
	    	var lista;
	    	me.callParent();
	    	if(me.getUsePicker())
	    	{
	    		debug('has picker');
	            lista = me.getPhonePicker();
	    	}
	    	else
	    	{
	    		debug('has listPanel');
	    		lista = me.getTabletPicker();
	    	}
    		lista.addListener('hide',function()
    		{
    			var thisCmp = Ext.getCmp(me.id);
    			var hasBlur = thisCmp.hasListener('blur');
    			debug('listPanel hide:',me.id,'hasBlur',hasBlur);
    			if(hasBlur)
    			{
    				var lio = me.id.lastIndexOf('_');
    				debug('lio:',lio);
    				var idPrefix = me.id.substring(0,lio+1);
    				var idNum    = me.id.substring(lio+1,me.id.length);
    				debug('idPrefix:',idPrefix,'idNum:',idNum);
    				if(Ext.getCmp(idPrefix+((idNum*1)+1)).heredar)
    				{
    					Ext.getCmp(idPrefix+((idNum*1)+1)).heredar(true);
    				}
    				else
    				{
    					_g_heredarCombo(true,idPrefix+((idNum*1)+1),idPrefix+(idNum*1));
    				}
    			}
    		});
	    }
	});
	
	Ext.define('Generic',
	{
	    extend : 'Ext.data.Model',
	    config :
	    {
	    	fields:
		    [
		        { name : 'key'   , type : 'string' },
		        { name : 'value' , type : 'string' }
		    ]
	    }
	});
	
	Ext.define('_mcotiza_Inciso',
	{
		extend  : 'Ext.data.Model'
		,config :
		{
			fields :
			[
			    'nombre'
			    ,'nombre2'
			    ,'apat'
			    ,'amat'
			    ,<s:property value="imap.fieldsIndividuales" />
			]
		}
	});
	////// modelos //////
	
	////// stores //////
	_mcotiza_storeIncisos = Ext.create('Ext.data.Store',
    {
        model     : '_mcotiza_Inciso'
    });
	////// stores //////
	
	_mcotiza_navView = Ext.create('Ext.NavigationView',
	{
		defaultBackButtonText : 'Atr&aacute;s'
		,items                :
		[
		    Ext.create('Ext.tab.Panel',
		    {
		    	title           : 'Datos generales'
		    	,tabBarPosition : 'bottom'
		    	,items          :
		    	[
			        {
			        	xtype    : 'formpanel'
			            ,title   : '&nbsp;Datos generales&nbsp;'
			            ,titulo  : 'Datos generales'
			            ,itemId  : '_mcotiza_formDatosGene'
			            ,iconCls : 'info'
			            ,items   :
			            [
			                {
			                    xtype  : 'fieldset'
			                    ,title : 'Datos generales'
			                    ,items :
			                    [
			                        {
			                            xtype     : 'numberfield'
			                            ,itemId   : '_mcotiza_nmpolizaField'
			                            ,label    : 'COTIZACI&Oacute;N'
			                            ,readOnly : true
			                            ,name     : 'nmpoliza'
			                        }
			                        ,<s:property value="imap.camposAgrupados"/>
			                        ,{
			                            xtype      : 'datepickerfield'
			                            ,itemId    : '_mcotiza_feinivalField'
			                            ,label     : 'INICIO DE VIGENCIA'
			                            ,name      : 'feini'
			                            ,value     : new Date()
			                            ,listeners :
			                            {
			                                change : function(field,value)
			                                {
			                                    debug(value);
			                                    debug(Ext.ComponentQuery.query('#_mcotiza_fefinvalField')[0]);
			                                    try
			                                    {
			                                        Ext.ComponentQuery.query('#_mcotiza_fefinvalField')[0].setValue(Ext.Date.add(value,Ext.Date.YEAR,1));
			                                    }
			                                    catch (e) {}
			                                }
			                            }
			                        }
			                        ,{
			                            xtype      : 'datepickerfield'
			                            ,itemId    : '_mcotiza_fefinvalField'
			                            ,label     : 'FIN DE VIGENCIA'
			                            ,name      : 'fefin'
			                            ,readOnly  : true
			                            ,value     : Ext.Date.add(new Date(),Ext.Date.YEAR,1)
			                        }
			                    ]
			                }
			            ]
			        }
			        ,{
			        	xtype    : 'formpanel'
			        	,title   : '&nbsp;Asegurado&nbsp;'
			        	,titulo  : 'Edici&oacute;n de asegurados'
			        	,iconCls : 'add'
			        	,itemId  : '_mcotiza_formAsegurados'
			        	,items   :
			        	[
				        	{
				        		xtype  : 'fieldset'
				        		,title : 'Edici&oacute;n de asegurados'
				        		,items :
				        		[
				        		    {
				        		    	xtype     : 'numberfield'
				        		    	,label    : 'rowIndex'
				        		    	,name     : 'rowIndex'
				        		    	,readOnly : true
				        		    	,hidden   : true
				        		    }
				        		    ,<s:property value="imap.itemsIndividuales"/>
				        		    ,{
				        		    	xtype  : 'textfield'
				        		    	,label : 'NOMBRE'
				        		    	,name  : 'nombre'
				        		    }
				        		    ,{
	                                    xtype  : 'textfield'
	                                    ,label : 'SEGUNDO NOMBRE'
	                                    ,name  : 'nombre2'
	                                }
				        		    ,{
	                                    xtype  : 'textfield'
	                                    ,label : 'APELLIDO PATERNO'
	                                    ,name  : 'apat'
	                                }
				        		    ,{
	                                    xtype  : 'textfield'
	                                    ,label : 'APELLIDO MATERNO'
	                                    ,name  : 'amat'
	                                }
				        		]
				        	}
				        	,{
				        		xtype   : 'toolbar'
				        		,docked : 'bottom'
				        		,layout :
				        		{
				        			pack : 'center'
				        		}
				        		,items  :
				        		[
                                    {
                                        xtype    : 'button'
                                        ,text    : 'Agregar nuevo'
                                        ,ui      : 'confirm'
                                        ,handler : _mcotiza_nuevoAsegurado
                                    }
                                    ,{
                                        xtype    : 'button'
                                        ,text    : 'Modificar'
                                        ,ui      : 'action'
                                        ,handler : _mcotiza_editarAsegurado
                                    }
                                    ,{
                                        xtype    : 'button'
                                        ,text    : 'Borrar'
                                        ,ui      : 'decline'
                                        ,handler : _mcotiza_borrarAsegurado
                                    }
                                ]
				        	}
				        ]
			        }
			        ,{
                        xtype             : 'list'
                        ,title            : '&nbsp;Asegurados&nbsp;'
                        ,titulo           : 'Asegurados'
                        ,iconCls          : 'more'
                        ,itemId           : '_mcotiza_listAsegurados'
                        ,store            : _mcotiza_storeIncisos
                        ,itemTpl          : '{nombre} {nombre2} {apat} {amat}'
                        ,onItemDisclosure : _mcotiza_incisoItemDisclosure
                    }
			        ,{
			        	xtype    : 'formpanel'
			        	,title   : 'Cotizar'
			        	,titulo  : 'Cotizar'
			        	,iconCls : 'star'
			        	,html    : 'Espere un momento...'
			        }
		    	]
		    	,listeners      :
	            {
	                activeitemchange : function (tabpanel,value, oldValue)
	                {
	                    debug('_p1_TabbedAjuste activate:',tabpanel.getActiveItem());
	                    debug('value:',value,'oldValue:',oldValue);
	                    debug('index:',tabpanel.indexOf(value));
	                    _mcotiza_navView.getNavigationBar().setTitle(tabpanel.getActiveItem().config.titulo);
	                    var index = tabpanel.indexOf(value);
	                    if(tabpanel.getActiveItem().config.titulo.toLowerCase()=='cotizar')
	                    {
	                    	_mcotiza_cotiza();
	                    }
	                }
	            }
		    })
		]
	});
	
	Ext.Viewport.add(_mcotiza_navView);
	
}});

</script>
</head>
<body>
</body>
</html>