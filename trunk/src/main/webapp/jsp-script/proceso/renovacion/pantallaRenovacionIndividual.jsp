<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script>
////// variables //////
var _p25_urlBuscarPolizas  		= '<s:url namespace="/renovacion" action="buscarPolizasRenovables" 			   />';
var _p25_urlRenovarPolizas 		= '<s:url namespace="/renovacion" action="renovarPolizas"          			   />';
var _p25_urlBuscarContratantes  = '<s:url namespace="/endoso" 	  action="cargarContratantesEndosoContratante" />';

var _p25_storePolizas;
var _p25_ultimosParams;
////// variables //////

////// componentes dinamicos //////
var itemsFormularioContratante = [<s:property value="imap.itemsFormularioContratante" escapeHtml="false" />];
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
        ,fields : [ <s:property value="imap.itemsFormularioContratante" escapeHtml="false" /> ]
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
                ,items    :
                [
                    <s:property value="imap.itemsFormularioContratante" escapeHtml="false" />
                ]
            })
            ,Ext.create('Ext.grid.Panel',
            {
                title        : 'Informacion de poliza'
                ,itemId      : '_p25_poliza'
                ,selType     : 'checkboxmodel'
                //,store       : _p25_storePolizas
                ,minHeight   : 200
                ,maxHeight   : 700
                ,columns     : [ <s:property value="imap.itemsFormularioPolizaColumns" escapeHtml="false" /> ]
                ,viewConfig  : viewConfigAutoSize
                ,buttonAlign : 'center'
                ,hidden		 : 'true'
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
                ,plugins     :
                [
                    Ext.create('Ext.grid.plugin.CellEditing',
                    {
                        clicksToEdit: 1
                    })
                ]
            })
            ,Ext.create('Ext.grid.Panel',
            {
                title        : 'Resultados'
                ,itemId      : '_p25_grid'
                ,selType     : 'checkboxmodel'
                ,store       : _p25_storePolizas
                ,minHeight   : 200
                ,maxHeight   : 700
                ,columns     : [ <s:property value="imap.gridColumns" escapeHtml="false" /> ]
                ,viewConfig  : viewConfigAutoSize
                ,buttonAlign : 'center'
                ,hidden		 : 'true'
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
                ,plugins     :
                [
                    Ext.create('Ext.grid.plugin.CellEditing',
                    {
                        clicksToEdit: 1
                    })
                ]
            })
        ]
    });
    ////// contenido //////
    
    ////// custom //////
    var form = _fieldById('_p25_busquedaForm');
    _fieldByName('cdunieco',form).getStore().on(
    {
        load : function(me)
        {
            me.insert(0,new Generic({key:'-1',value:'Todas'}));
            _fieldByName('cdunieco',form).setValue('-1');
        }
    });
    _fieldByName('cdramo',form).getStore().on(
    {
        load : function(me)
        {
            me.insert(0,new Generic({key:'-1',value:'Todos'}));
            _fieldByName('cdramo',form).setValue('-1');
        }
    });
    /* _fieldByName('nmpoliza',form).getStore().on(
    {
        load : function(me)
        {
            _fieldByName('nmpoliza',form).setValue('-1');
        }
    }); */
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
  	
    /*_fieldById('_p25_grid').getSelectionModel().on(
    {
        selectionChange : _p25_gridSelectionChange
    });*/
    ////// custom //////
    
    ////// loaders //////
    ////// loaders //////
});

////// funciones //////
function _p25_buscarClic(button,e)
{
    debug('>_p25_buscarClic');
    var form=button.up('form');
    _p25_selectionCharge(_fieldByName('tipo'     , form));
    /*
    _p25_ultimosParams =
    {
    	'tipo'	    : _fieldByName('tipo'     , form).getValue()
    	,'cdunieco' : _fieldByName('cdunieco' , form).getValue()
        ,'cdramo'   : _fieldByName('cdramo'   , form).getValue()
        ,'nmpoliza' : _fieldByName('nmpoliza' , form).getValue()
    };*/
    /*_p25_storePolizas.load(
    {
        params    :
        {
        	'smap1.tipo'	 : _fieldByName('tipo' 	   , form).getValue()
        	,'smap1.cdunieco': _fieldByName('cdunieco' , form).getValue()
            ,'smap1.cdramo'  : _fieldByName('cdramo'   , form).getValue()
            ,'smap1.nmpoliza': _fieldByName('nmpoliza' , form).getValue()
        }
        //,callback : _p25_storePolizasLoadCallback
    });*/
    debug('<_p25_buscarClic');
}

/*function _p25_storePolizasLoadCallback(records,op,success)
{
    debug('>_p25_storePolizasLoad',records,success,'dummy');
    if(success)
    {
        for(var i=0;i<records.length;i++)
        {
            records[i].set('cducreno',records[i].get('cdunieco'));
        }
    }
    else
    {
        var error=op.getError();
        debug('error:',error);
        if(typeof error=='object')
        {
            errorComunicacion();
        }
        else
        {
            mensajeError(error);
        }
    }
    debug('>_p25_storePolizasLoad');
}*/

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
    json['smap1']  = _p25_ultimosParams;
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

/*function _p25_gridSelectionChange(selModel,selected,e)
{
    debug('>_p25_gridSelectionChange selected.length:',selected.length);
    _fieldById('_p25_gridBotonRenovar').setDisabled(selected.length==0);
}*/

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

////// funciones //////
</script>
</head>
<body><div id="_p25_divpri" style="height:600px;"></div></body>
</html>