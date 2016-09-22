<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script>
////// variables //////
var _p25_urlBuscarPolizas  			= '<s:url namespace="/renovacion" action="buscarPolizasIndividualesRenovables" 		  />';
var _p25_urlBuscarPolizasMasivas	= '<s:url namespace="/renovacion" action="buscarPolizasIndividualesMasivasRenovables" />';
var _p25_urlRenovarPolizaIndividual	= '<s:url namespace="/renovacion" action="renovarPolizaIndividual"			   		  />';
var _p25_urlBuscarContratantes  	= '<s:url namespace="/endoso" 	  action="cargarContratantesEndosoContratante" 		  />';

var _p25_storePolizas;
var _p25_storePolizasMasivas;
var _p25_ultimosParams;
////// variables //////

////// componentes dinamicos //////
var itemsFormularioContratante   = [<s:property value="imap.itemsFormularioContratante" escapeHtml="false" />];
var itemsFormularioPolizaColumns = [<s:property value="imap.itemsFormularioPolizaColumns" escapeHtml="false" />];
var gridColumns					 = [<s:property value="imap.gridColumns" escapeHtml="false" />];
/*itemsFormularioPolizaColumns.push({
									xtype : 'actioncolumn'
									,icon : '${icons}pencil.png'
		        					,tooltip : 'observaciones'
			    				},
			    				{
									xtype : 'actioncolumn'
									,icon : '${icons}doc_ok.png'
		        					,tooltip : 'documentacion adicional'
			    				});*/
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
                ,items    :
                [
                    <s:property value="imap.busquedaItems" escapeHtml="false" />
                ]
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
    if (tipo.getValue() == 'AS' || tipo == 'SA'){
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
    	var form = _fieldById('_p25_busquedaForm');
    	if(_fieldByName('tipo',form).getValue() == 'AS'){
    		_p25_ventanaAutoServicio();
    	}
    	else{
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
            		debug('resp',resp.slist1[0]['ntramite']);
            		if(!Ext.isEmpty(resp.slist1[0]['ntramite']))
            		{
                		_unmask();
                		mensajeCorrecto('Proceso completo','Se creo el tramite '+resp.slist1[0]['ntramite']);
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
    	}
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

function _p25_ventanaAutoServicio(){
	debug('>_p25_ventanaAutoServicio');
	Ext.create('Ext.window.Window', {
		title  : 'Auto-servicio',
		itemId : 'winAutoServicio',
    	height : 200,
    	width  : 400,
    	layout : 'fit',
    	items  : [
    		Ext.create('Ext.form.Panel', {
    			bodyPadding : 10,
    			width       : 300,
    			items       : [
    				{
    					xtype       : 'fieldcontainer',
            			defaultType : 'checkboxfield',
            			items       : [
                			{
                    			boxLabel   : 'Tratamiento de renovación por Servicio Asistido',
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
    							_fieldById('winAutoServicio').close();
    						}
    					}
    				]
    		})
        ]
    }).show();
	debug('<_p25_ventanaAutoServicio');
}
////// funciones //////
</script>
</head>
<body><div id="_p25_divpri" style="height:600px;"></div></body>
</html>