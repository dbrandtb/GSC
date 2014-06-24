<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<script>
////// overrides //////
////// overrides //////

////// variables //////
var _p20_urlCargar    = '<s:url namespace="/"        action="cargarComplementariosAsegurados" />';
var _p20_urlGuardar   = '<s:url namespace="/emision" action="guardarSituacionesAuto"          />';
var _p20_urlDomicilio = '<s:url namespace="/"        action="pantallaDomicilio"               />';
var _p20_urlBuscarRFC = '<s:url namespace="/"        action="buscarPersonasRepetidas"         />';

var _p20_map1 = <s:property value="%{convertToJSON('map1')}" escapeHtml="false" />;
debug('_p20_map1:',_p20_map1);
var _p20_itemsFormAsegurados = [ <s:property value="item2" /> ];
debug('_p20_itemsFormAsegurados:',_p20_itemsFormAsegurados);
var _p20_columnasGridAsegurados = [ <s:property value="item3" /> ];
debug('_p20_columnasGridAsegurados:',_p20_columnasGridAsegurados);

var _p20_rowEditingPlugin;

var _p20_gridAsegurados;
var _p20_storeAsegurados;
var _p20_formAsegurado;
var contextop2='${ctx}';//lo usa la de domicilio

var _p20_validaBorrar = function()
{
    mensajeWarning('Falta definir la validaci&oacute;n');
    return true;
};

var _p20_validaGuardar = function()
{
    mensajeWarning('Falta definir la validaci&oacute;n');
    return true;
}

<s:if test='%{getItem4()!=null}'>
    var aux = <s:property value="item4" escapeHtml="false" />;
    _p20_validaBorrar = aux.handler;
    debug('_p20_validaBorrar:',_p20_validaBorrar);
</s:if>

<s:if test='%{getItem5()!=null}'>
	var aux = <s:property value="item5" escapeHtml="false" />;
	_p20_validaGuardar = aux.handler;
	debug('_p20_validaGuardar:',_p20_validaGuardar);
</s:if>
////// variables //////

Ext.onReady(function()
{
	////// modelos //////
	Ext.define('_p20_modeloAsegurado',
	{
		extend  : 'Ext.data.Model'
		,fields : [ <s:property value="item1" /> ]
	});
	
	Ext.define('RFCPersona',
    {
        extend  : 'Ext.data.Model'
        ,fields : ["RFCCLI","NOMBRECLI","FENACIMICLI","DIRECCIONCLI","CLAVECLI","DISPLAY", "CDIDEPER", "SEXO", "TIPOPERSONA", "NACIONALIDAD", "NOMBRE", "SNOMBRE", "APPAT", "APMAT"]
    });
	////// modelos //////
	
	////// stores //////
	_p20_storeAsegurados = new Ext.data.Store(
	{
		model     : '_p20_modeloAsegurado'
		,autoLoad : true
		,proxy    :
        {
            url          : _p20_urlCargar
            ,type        : 'ajax'
            ,extraParams :
            {
                'map1.pv_cdunieco'  : _p20_map1.cdunieco
                ,'map1.pv_cdramo'   : _p20_map1.cdramo
                ,'map1.pv_estado'   : _p20_map1.estado
                ,'map1.pv_nmpoliza' : _p20_map1.nmpoliza
                ,'map1.pv_nmsuplem' : 0
            }
            ,reader:
            {
                type: 'json',
                root: 'list1'
            }
        }
	});
	////// stores //////
	
	////// componentes //////
	////// componentes //////
	
	////// contenido //////
	_p20_formAsegurado = Ext.create('Ext.form.Panel',
	{
		hidden : true
		,items : _p20_itemsFormAsegurados
	});
	_p20_columnasGridAsegurados.push(
	{
		xtype         : 'actioncolumn'
		,sortable     : false
        ,menuDisabled : true
		,items        :
		[
		    {
                icon          : '${ctx}/resources/fam3icons/icons/report_key.png'
                ,handler      : _p20_editarDomicilio
                ,tooltip      : 'Editar domicilio'
            }
		    ,{
		    	icon          : '${ctx}/resources/fam3icons/icons/delete.png'
		    	,handler      : _p20_quitarAsegurado
		    	,tooltip      : 'Borrar asegurado'
		    }
		]
	});
	
	_p20_rowEditingPlugin = Ext.create('Ext.grid.plugin.RowEditing',{
		clicksToEdit : 1, 
		errorSummary : true,
		listeners: {
			beforeedit: function(editor, context, eopts){
				var sinCdideper = Ext.isEmpty(context.record.get('cdideper'));
				if(!sinCdideper) return false;
			}
		}
	});
	
	if (Ext.grid.RowEditor) {
		Ext.apply(Ext.grid.RowEditor.prototype, {
			saveBtnText : "Buscar/Actualizar",
			cancelBtnText : "Cancelar",
			errorsText : "Errores",
			dirtyText : "Debe guardar o cancelar sus cambios"
		});
	}
	
	_p20_gridAsegurados = Ext.create('Ext.grid.Panel',
	{
		title       : 'Clientes'
		,store      : _p20_storeAsegurados
		,columns    : _p20_columnasGridAsegurados
		,viewConfig :
		{
			listeners :
		    {
		        refresh : function(dataview)
		        {
		            Ext.each(dataview.panel.columns, function(column)
		            {
		                //column.autoSize();
		                debug(column);
		                if(column.text=='RFC')
		                {
		                	column.flex=0;
		                	column.setWidth(120);
		                }
		                if(column.text=="Sexo")
		                {
		                	column.flex=0;
		                	column.setWidth(70);
		                }
		                if(column.text=="Nombre")
                        {
		                	column.flex=0;
                            column.setWidth(100);
                        }
		                if(column.text=="Fecha de nacimiento")
                        {
                            column.flex=0;
                            column.setWidth(100);
                        }
		                if(column.xtype=='actioncolumn')
                        {
                            column.flex=0;
                            column.setWidth(50);
                        }
		            });
		        }
		    }
		}
		,plugins    : _p20_rowEditingPlugin
		,minHeight  : 250
		,tbar       :
		[
			{
				text     : 'Agregar'
				,icon    : '${ctx}/resources/fam3icons/icons/add.png'
				,handler : _p20_agregarAsegurado
			}
		]
	    ,buttonAlign : 'center'
	    ,buttons    :
	    [
	        {
	        	text     : 'Guardar'
	        	,icon    : '${ctx}/resources/fam3icons/icons/disk.png'
	        	,handler : _p20_guardar
	        }
	    ]
	    ,listeners  :
	    {
	    	edit : _p20_buscarRFC
	    }
	});
	
	
	var editorRfc;
	
	_p20_columnasGridAsegurados.forEach(function(element, index, array){
		//debug('Columna ITerada: ', element.dataIndex);
		if('cdrfc' == element.dataIndex){
			editorRfc = element;
		}
	});
	
	editorRfc.editor.on('change', function(){
		var recordRfc = _p20_gridAsegurados.getSelectionModel().getLastSelected();
		recordRfc.set('swexiper', 'N')
		recordRfc.set('cdperson', '')
		recordRfc.set('cdideper', '')
	});
	
	Ext.create('Ext.panel.Panel',
	{
		defaults  :
		{
			style : 'margin : 5px;'
		}
	    ,renderTo : '_p20_divpri'
	    ,border   : 0
	    ,items    :
	    [
	        _p20_gridAsegurados
	        ,_p20_formAsegurado
	    ]
	});
	////// contenido //////
	
	////// loaders //////
	////// loaders //////
});

////// funciones //////
function _p20_agregarAsegurado()
{
	debug('>_p20_agregarAsegurado');
	_p20_storeAsegurados.add(new _p20_modeloAsegurado(
	{
		swexiper : 'N'
	}));
	debug('<_p20_agregarAsegurado');
}

function _p20_quitarAsegurado(grid, rowIndex, colIndex)
{
	var record=grid.getStore().getAt(rowIndex);
	debug('>_p20_quitarAsegurado',record.data);
	var valido = _p20_validaBorrar(record);
	if(valido)
	{
		grid.getStore().remove(record);
	}
	debug('<_p20_quitarAsegurado');
}

function _p20_guardar(button,event,callback)
{
	debug('>_p20_guardar');
	var valido = true;
	if(valido)
	{
		debug('count:',_p20_storeAsegurados.getCount());
		valido = _p20_storeAsegurados.getCount()>0;
		if(!valido)
		{
			mensajeWarning('Debe introducir los asegurados');
		}
	}
	if(valido)
	{
		var grid    = _p20_gridAsegurados;
		var form    = _p20_formAsegurado;
		var i       = 0;
		var errores = '';
		grid.getStore().each(function(record)
		{
			i=i+1;
			form.loadRecord(record);
			if(!form.isValid())
			{
				debug('errores para '+i);
				form.getForm().getFields().each(function(field)
				{
					var erroresField = field.getErrors().join(';')
					debug('iterando errores para:',field,erroresField);
					if(erroresField)
					{
						errores = errores + 'Situaci&oacute;n '+i+') ' + field.getFieldLabel()+': '+erroresField + '<br/>';
					}
				});
				valido = false;
			}
			else
			{
				debug('correcto para '+i);
			}
		});
		if(!valido)
		{
			mensajeWarning('Revisar los siguientes datos:<br/>'+errores);
		}
	}
	if(valido)
	{
		valido = _p20_validaGuardar();
	}
	if(valido)
	{
		var slist1 = [];
		_p20_storeAsegurados.each(function(record)
		{
			var inciso = {};
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
            slist1.push(inciso);
		});
		_p20_gridAsegurados.setLoading(true);
		Ext.Ajax.request(
		{
			url       : _p20_urlGuardar
			,jsonData :
			{
				smap1   : _p20_map1
				,slist1 : slist1
			}
		    ,success  : function(response)
		    {
		    	_p20_gridAsegurados.setLoading(false);
		    	var json = Ext.decode(response.responseText);
		    	debug('json response:',json);
		    	if(json.success)
		    	{
		    		_p20_storeAsegurados.removeAll();
		    		for(var i=0;i<json.slist1.length;i++)
		    		{
		    			_p20_storeAsegurados.add(new _p20_modeloAsegurado(json.slist1[i]));
		    		}
		    		if(callback)
		    		{
		    			debug('callback:',callback);
		    			callback();
		    		}
		    		else
		    		{
		    			var ven=Ext.Msg.show({
                            title:'Datos guardados',
                            msg: 'Se han guardado los datos',
                            buttons: Ext.Msg.OK
                        });
		    			centrarVentanaInterna(ven);
                        expande(1);
		    		}
		    	}
		    	else
		    	{
		    		mensajeError(json.error);
		    	}
		    }
		    ,failure  : function()
		    {
		    	_p20_gridAsegurados.setLoading(false);
		    	errorComunicacion();
		    }
		});
	}
	
	debug('<_p20_guardar');
}

function _p20_editarDomicilio(grid, rowIndex, colIndex)
{
	debug('>_p20_editarDomicilio');
	var record=grid.getStore().getAt(rowIndex);
	var recordClave = record.get('nombre')+' '
	                + (record.get('segundo_nombre')?record.get('segundo_nombre')+' ':' ')
	                + record.get('Apellido_Paterno')+' '
	                + record.get('Apellido_Materno');
	debug('>_p20_editarDomicilio: ',recordClave);
	var dirigir = function()
	{
		debug('>dirigir');
		
		//buscar el seleccionado
		var recordSeleccionado=false;
		_p20_storeAsegurados.each(function(record)
		{
			if(recordClave==record.get('nombre')+' '
                    + (record.get('segundo_nombre')?record.get('segundo_nombre')+' ':' ')
                    + record.get('Apellido_Paterno')+' '
                    + record.get('Apellido_Materno'))
			{
				recordSeleccionado=record;
			}
		});
		var record = recordSeleccionado;
		
		//abrir acordion
		if(Ext.getCmp('domicilioAccordionEl'))
        {
            Ext.getCmp('domicilioAccordionEl').destroy();
        }
        accordion.add(
        {
            id:'domicilioAccordionEl'
            ,title:'Editar domicilio de '+record.get('nombre')+' '+(record.get('segundo_nombre')?record.get('segundo_nombre')+' ':' ')+record.get('Apellido_Paterno')+' '+record.get('Apellido_Materno')
            ,cls:'claseTitulo'
            ,loader:
            {
                url     : _p20_urlDomicilio
                ,params :
                {
                    'smap1.pv_cdunieco'      : _p20_map1.cdunieco
                    ,'smap1.pv_cdramo'       : _p20_map1.cdramo
                    ,'smap1.cdtipsit'        : _p20_map1.cdtipsit
                    ,'smap1.pv_estado'       : _p20_map1.estado
                    ,'smap1.pv_nmpoliza'     : _p20_map1.nmpoliza
                    ,'smap1.pv_nmsituac'     : record.get("nmsituac")
                    ,'smap1.pv_cdperson'     : record.get('cdperson')
                    ,'smap1.pv_cdrol'        : record.get("cdrol")
                    ,'smap1.nombreAsegurado' : record.get('nombre')+' '+(record.get('segundo_nombre')?record.get('segundo_nombre')+' ':' ')+record.get('Apellido_Paterno')+' '+record.get('Apellido_Materno')
                    ,'smap1.cdrfc'           : record.get('cdrfc')
                    ,'smap1.cdideper'        : record.get('cdideper')
                    ,'smap1.botonCopiar'     : '0'
                }
                ,autoLoad:true
                ,scripts:true
            }
        });
		debug('<dirigir');
	};
	_p20_guardar(null,null,dirigir);
	debug('<_p20_editarDomicilio');	
}

function _p20_buscarRFC()
{
	_p20_gridAsegurados.setLoading(true);
	Ext.Ajax.request(
	{
        url     : _p20_urlBuscarRFC
        ,params :
        {
            'map1.pv_rfc_i' : _p20_gridAsegurados.getView().getSelectionModel().getSelection()[0].get('cdrfc'),
            'map1.cdtipsit'     : _p20_map1.cdtipsit,
			'map1.pv_cdunieco_i': _p20_map1.cdunieco,
       		'map1.pv_cdramo_i'  : _p20_map1.cdramo,
       		'map1.pv_estado_i'  : _p20_map1.estado,
       		'map1.pv_nmpoliza_i': _p20_map1.nmpoliza
			
        }
        ,success:function(response)
        {
        	_p20_gridAsegurados.setLoading(false);
            var json=Ext.decode(response.responseText);
            debug('json response:',json);
            if(json && !json.success){
	    		mensajeError("Error al Buscar RFC, Intente nuevamente. Si el problema persiste consulte a soporte t&eacute;cnico.");
	    		return;
	    	}
            if(json&&json.slist1&&json.slist1.length>0)
            {
                var ven=Ext.create('Ext.window.Window',
                {
                    width        : 600
                    ,height      : 400
                    ,modal       : true
                    ,autoScroll  : true
                    ,title       : 'Coincidencias'
                    ,items       : Ext.create('Ext.grid.Panel',
                                   {
                                       store    : Ext.create('Ext.data.Store',
                                                  {
                                                      model     : 'RFCPersona'
                                                      ,autoLoad : true
                                                      ,proxy :
                                                      {
                                                          type    : 'memory'
                                                          ,reader : 'json'
                                                          ,data   : json['slist1']
                                                      }
                                                  })
                                       ,columns :
                                       [
                                           {
                                               xtype         : 'actioncolumn'
                                               ,menuDisabled : true
                                               ,width        : 30
                                               ,items        :
                                               [
                                                   {
                                                       icon     : '${ctx}/resources/fam3icons/icons/accept.png'
                                                       ,tooltip : 'Seleccionar usuario'
                                                       ,handler : function(grid, rowIndex, colIndex) {
                                                           var record = grid.getStore().getAt(rowIndex);
                                                           debug(record);
                                                           debug('cliente obtenido de WS? ', json.clienteWS);
                                                           
                                                           _p20_gridAsegurados.getView().getSelectionModel().getSelection()[0].set("cdrfc",record.get("RFCCLI"));
                                                           
                                                           
                                                           _p20_gridAsegurados.getView().getSelectionModel().getSelection()[0].set("nombre",record.get("NOMBRE"));
                                                           _p20_gridAsegurados.getView().getSelectionModel().getSelection()[0].set("segundo_nombre",record.get("SNOMBRE"));
                                                           _p20_gridAsegurados.getView().getSelectionModel().getSelection()[0].set("Apellido_Paterno",record.get("APPAT"));
                                                           _p20_gridAsegurados.getView().getSelectionModel().getSelection()[0].set("Apellido_Materno",record.get("APMAT"));
                                                           _p20_gridAsegurados.getView().getSelectionModel().getSelection()[0].set("fenacimi",record.get("FENACIMICLI"));
                                                    	   _p20_gridAsegurados.getView().getSelectionModel().getSelection()[0].set("sexo",record.get("SEXO"));
                                                    	   _p20_gridAsegurados.getView().getSelectionModel().getSelection()[0].set("tpersona",record.get("TIPOPERSONA"));
                                                    	   _p20_gridAsegurados.getView().getSelectionModel().getSelection()[0].set("nacional",record.get("NACIONALIDAD"));
                                                           
                                                           if(json.clienteWS){
                                                        	   _p20_gridAsegurados.getView().getSelectionModel().getSelection()[0].set("cdideper",record.get("CDIDEPER"));
                                                           }else{
                                                        	   _p20_gridAsegurados.getView().getSelectionModel().getSelection()[0].set("cdperson",record.get("CLAVECLI"));
                                                        	   _p20_gridAsegurados.getView().getSelectionModel().getSelection()[0].set("cdideper",record.get("CDIDEPER"));
                                                               _p20_gridAsegurados.getView().getSelectionModel().getSelection()[0].set("swexiper",'S');
                                                           }
                                                           
                                                           
                                                           grid.up().up().destroy();
                                                       }
                                                   }
                                               ]
                                           },{
                                               header     : 'RFC'
                                               ,dataIndex : 'RFCCLI'
                                               ,flex      : 1
                                           }
                                           ,{
                                               header     : 'Nombre'
                                               ,dataIndex : 'NOMBRECLI'
                                               ,flex      : 1
                                           }
                                           ,{
                                               header     : 'Direcci&oacute;n'
                                               ,dataIndex : 'DIRECCIONCLI'
                                               ,flex      : 3
                                           }
                                       ]
                                   })
                }).show();
                centrarVentanaInterna(ven);
            }
            else
            {
                centrarVentanaInterna(Ext.Msg.show({
                    title:'Sin coincidencias',
                    msg: 'No hay coincidencias de RFC',
                    buttons: Ext.Msg.OK
                }));
            }
        }
        ,failure:function()
        {
        	_p20_gridAsegurados.setLoading(false);
            errorComunicacion();
        }
    });
}
////// funciones //////
</script>
</head>
<body><div id="_p20_divpri" style="height:500px;"></div>
</body>
</html>