<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script>
////// variables //////
var _CONTEXT = '${ctx}';

var _URL_RENOVA_SINIESTRALIDAD  =	'<s:url namespace="/siniestros" action="consultaRenovaSiniestros" />';

var sesionDsrol   						      = _GLOBAL_CDSISROL;

var itemsFormularioBusqueda      = [<s:property value="imap.busquedaItems" 				  escapeHtml="false" />];

////// variables //////

////// componentes dinamicos //////
////// componentes dinamicos //////

Ext.onReady(function()
{
    Ext.Ajax.timeout = 60*60*1000;
    Ext.override(Ext.form.Basic, { timeout: Ext.Ajax.timeout / 1000 });
    Ext.override(Ext.data.proxy.Server, { timeout: Ext.Ajax.timeout });
    Ext.override(Ext.data.Connection, { timeout: Ext.Ajax.timeout });
    ////// modelos //////
    Ext.define('modeloAutEspecial',{
		extend:'Ext.data.Model',
		fields:[
			{type:'string',		name:'CDUNIECO'	        },	
			{type:'string',		name:'CDRAMO'	        },
			{type:'string',		name:'ESTADO'		    },
			{type:'string',		name:'NMPOLIZA'	        },
			{type:'string',		name:'NTRAMITE'		    },
			{type:'string',		name:'TIPO_PAGO'        },
			{type:'string',		name:'POLIZA'           },
			{type:'string',		name:'NMSINIES'   	    },
			{type:'string',		name:'DSUNIECO'         },
			{type:'string',		name:'FECINIVIG'  	    },
			{type:'string',		name:'FECFINVIG'        },
			{type:'string',		name:'AAAPERTU' 	    }, 
			{type:'string',		name:'FECHA_OCURRENCIA' },
			{type:'string',		name:'CDICD' 	        }, 
			{type:'string',		name:'DESC_ICD' 	    }, 
			{type:'string',		name:'CDPERSON' 		}, 
		    {type:'string',		name:'NOMBRE_ASEGURADO' },
            {type:'string',		name:'EDAD' 		    }, 
            {type:'string',		name:'SEXO' 		    }, 
            {type:'string',		name:'TIPO_PAGO' 		}, 
            {type:'string',		name:'MONTO_RESERVADO'  },
            {type:'string',		name:'MONTO_APROBADO' 	}, 
            {type:'string',		name:'MONTO_PAGADO'     },
            {type:'string',		name:'DSRAMO'           },
            {type:'string',		name:'CDCAUSA'          },
			{type:'string',     name:'FENACIMI'        }
        ]
	});
    ////// modelos //////
    
    ////// stores //////
	var storeGridAutEspecial = new Ext.data.Store({
		pageSize	: 25
		,model		: 'modeloAutEspecial'
		,autoLoad	: false
		,proxy		: {
			enablePaging	:	true,
			reader			:	'json',
			type			:	'memory',
			data			:	[]
		}
	});
    ////// stores //////
    
    ////// componentes //////
    
    ////// contenido //////
    
    Ext.create('Ext.panel.Panel',
    {
        renderTo    : '_p25_divpri'
        ,itemId     : 'formBusqueda'
        ,defaults   : { style : 'margin : 5px;' }
        ,border     : 0
        ,autoScroll : true
        ,items      : [
        
        	Ext.create('Ext.form.Panel',{
                title     : 'Buscar p&oacute;lizas a renovar'
                ,itemId   : '_p25_busquedaForm'
                ,defaults : { style : 'margin :5px;' }
                ,layout   : {
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
                        ,handler : function(){
                        	storeGridAutEspecial.removeAll();
							var params = {
								'params.pv_CdUniEco_i'   : _fieldByName('cdunieco').getValue()
								,'params.pv_CdRamo_i'    : _fieldByName('cdramo').getValue()
								,'params.pv_nmpoliza_i'  : _fieldByName('nmpoliza').getValue()
								,'params.pv_fecdesde'   : _fieldByName('fecini').getValue()
								,'params.pv_fechasta'   : _fieldByName('fecfin').getValue()
								,'params.pv_cdperson'  : _fieldByName('cdperson').getValue()
								,'params.pv_nmsinies' : _fieldByName('siniestro').getValue()
								,'params.pv_ntramite_i'  : _fieldByName('tramite').getValue()
								,'params.pv_start_i'  : 0
								,'params.pv_limit_i'  : 25
							};
							cargaStorePaginadoLocal(storeGridAutEspecial, _URL_RENOVA_SINIESTRALIDAD, 'datosValidacion', params, function(options, success, response){
								if(success){
									var jsonResponse = Ext.decode(response.responseText);
									if(jsonResponse.datosValidacion &&jsonResponse.datosValidacion.length == 0) {
										if(null == null){
											centrarVentanaInterna(showMessage("Aviso", "No existe tr&aacute;mites.", Ext.Msg.OK, Ext.Msg.INFO));
										}
										return;
									}
								}else{
									centrarVentanaInterna(showMessage('Error', 'Error al obtener los datos',Ext.Msg.OK, Ext.Msg.ERROR));
								}
							})}
                    },
                    {
                        text     : 'Limpiar'
                        ,icon    : '${ctx}/resources/fam3icons/icons/control_repeat.png'
                        ,handler : function (){}
                    }
                ]
                ,listeners	:	{
                	click	:	function(options) {
                		debug('click panel');
                		}
                }
            })
        ,Ext.create('Ext.grid.Panel',{
				id             : 'clausulasGridId'
				,title         : 'Reserva Siniestros'
				,store         :  storeGridAutEspecial
				,titleCollapse : true
				,style         : 'margin:5px'
				,height        : 400
				,columns       : [
					{   xtype: 'actioncolumn',      width: 40,          sortable: false,            menuDisabled: true,
	                    items: [{
	                        icon: _CONTEXT+'/resources/fam3icons/icons/application_edit.png',
	                        tooltip: 'Generar Autorizaci\u00F3n Especial',	//(EGS)
	                        scope: this,
	                        handler: function(){}
	                    }]
	                 },
					{	header     : 'Cdunieco'       ,	dataIndex : 'CDUNIECO',		flex : 1, 	hidden   : true	},
					{	header     : 'Cdramo'         ,	dataIndex : 'CDRAMO',		flex : 1, 	hidden   : true	},
					{	header     : 'Estado'         ,	dataIndex : 'ESTADO',		flex : 1, 	hidden   : true	},
					{	header     : 'Nmpoliza'       ,	dataIndex : 'NMPOLIZA',		flex : 1, 	hidden   : true	},
					{	header     : 'Ntramite'       ,	dataIndex : 'NTRAMITE',		flex : 1, 	hidden   : true	},
					{	header     : 'Tipo Pago'      , dataIndex : 'TIPO_PAGO',    width : 200             },
					{	header     : 'Poliza'         , dataIndex : 'POLIZA'      , width : 150				},
					{	header     : 'Siniestro'      , dataIndex : 'NMSINIES'    ,	width : 130				},
					{	header     : 'Sucursal'       ,	dataIndex : 'DSUNIECO' ,	width : 150				},
					{	header     : 'Fecha Inicio'   ,	dataIndex : 'FECINIVIG'   ,	width : 80				},
					{	header     : 'Fecha Fin'      ,	dataIndex : 'FECFINVIG'   ,	width : 100				},
					{	header     : 'Autorizaci&oacute;n<br/> (Pre-Siniestro)' , dataIndex : 'AAAPERTU',	      width : 200},
					{	header     : 'Fecha <br/>Ocurrencia'                    , dataIndex : 'FECHA_OCURRENCIA', width : 80 },
					{	header     : 'ICD Principal'                            , dataIndex : 'CDICD',	          width : 80 },
					{	header     : 'Des. ICD <br/>Principal'                  , dataIndex : 'DESC_ICD',	      width : 150 },
					{	header     : 'Cdperson'                                 , dataIndex : 'CDPERSON',		  width : 120 },
					{	header     : 'Nom. Asegurado<br/> Afectado'             , dataIndex : 'NOMBRE_ASEGURADO', width : 150},
					{	header     : 'Edad'                                     , dataIndex : 'EDAD',		      width : 100},
					{	header     : 'Sexo'                                     , dataIndex : 'SEXO',		      width : 200},
					{	header     : 'Monto Reservado'                          , dataIndex : 'MONTO_RESERVADO',  width : 200},
					{	header     : 'Monto Aprobado'                           , dataIndex : 'MONTO_APROBADO',	  width : 200},
					{	header     : 'Monto Pagado'                             , dataIndex : 'MONTO_PAGADO',     width : 200},
					{	header     : 'Ramo'                                     , dataIndex : 'DSRAMO',           width : 200},
					{	header     : 'Causa Siniestro'                          , dataIndex : 'CDCAUSA',          width : 200},
					{	header     : 'Fecha Nacimiento'                         , dataIndex : 'FENACIMI',         width : 200}
					
				],
				bbar     :{
				displayInfo : true,
					store		: storeGridAutEspecial,
					xtype		: 'pagingtoolbar'
				}
			})
        ]
    });
    ////// contenido //////
    
    ////// custom //////
    var form = _fieldById('_p25_busquedaForm');
    
   /* _fieldByName('division',form).getStore().on({
        load : function(me){
  		    var division = _fieldByName('division',form);
  		    division.setValue('COLECTIVO');  		    
  		    if(true){
  		        debug('division',division);
  		        division.store.removeAt(2);
  		    }  		    
  	    }
  	});
  	    
  	_fieldByName('division',form).on({
  	    change: function(me, newValue) {
  	    	debug(me, newValue);
  	    	
  	    	debug('Sucursal',_fieldByName('Sucursal').getValue());
			debug('Ramo'    ,_fieldByName('Ramo').getValue()    );
			debug('Poliza'  ,_fieldByName('Poliza').getValue()  );
			debug('feDesde' ,_fieldByName('feDesde').getValue() );
			debug('feHasta' ,_fieldByName('feHasta').getValue() );
			debug('division',_fieldByName('division').getValue());
			
  		    if(me.getValue() == 'COLECTIVO'){
  			    _fieldByName('feDesde',form).hide();
  				_fieldByName('feHasta',form).hide();
  				
  				_fieldByName('Sucursal',form).show();
  				_fieldByName('Ramo',form).show();
  				_fieldByName('Poliza',form).show();
  				
  				form.doLayout();
  		    }
  		    else if(me.getValue() == 'RENOVAR_X_FECHAS'){
  			    _fieldByName('feDesde',form).show();
  				_fieldByName('feHasta',form).show();
  				
  				_fieldByName('Sucursal',form).hide();
  				_fieldByName('Ramo',form).hide();
  				_fieldByName('Poliza',form).hide();
  				
  				form.doLayout();
  			}
  	    }
  	});
  	*/
  	
    ////// custom //////
    
    ////// loaders //////
  	
    ////// loaders //////
});

////// funciones //////

function _p25_limpiarFiltros(button,e)
{
    debug('>_p25_limpiarFiltros');
    var form = button.up('form');
    _fieldByName('tipo', form).enable();
    _fieldByName('anio', form).enable();
    _fieldByName('mes',  form).enable();
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
  	_fieldByName('administradora',form).setValue('');
  	_fieldByName('retenedora',form).setValue('');
  	_fieldByName('cdperson',form).setValue('');
  	_fieldByName('anio',form).setValue('');
  	//_fieldByName('mes',form).setValue('');
    debug('<_p25_limpiarFiltros');
}

 function limpiarValoresCalendario(form){
    debug('>limpiarValoresCalendario', form);    
    _fieldByName('cdunieco',form).setValue('');
    _fieldByName('cdramo',  form).setValue('');
    _fieldByName('feaplica',form).setValue('');
    _fieldByName('feinicio',form).setValue('');
    _fieldByName('fefinal', form).setValue('');
    debug('<limpiarValoresCalendario');
 }

 function daydiff(first, second) {
     return Math.round((second-first)/(1000*60*60*24));
 }
 
 function isValidDate(date){
     debug('>isValidDate',date);
     var isDate = false;
     var dateArr = date.split('/');
     if(dateArr.length === 3){
        isDate = Ext.Date.isValid(dateArr[2],dateArr[1],dateArr[0]);
     }
     debug('<isValidDate',isDate);
     return isDate;
 }

 function toDate(year, month, day){
     debug('>toDate');
     var fecha;
     try{
        var mes = Number(month)-1;
        fecha = new Date(year, mes, day);
     }
     catch(err){
        debug('error al convertir fecha',err);
        return null;
     } 
     return fecha;
     debug('<toDate');
 }


////// funciones //////
</script>
</head>
<body><div id="_p25_divpri" style="height:600px;"></div></body>
</html>