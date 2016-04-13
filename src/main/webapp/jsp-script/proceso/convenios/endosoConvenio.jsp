<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script>
////// urls //////
////// urls //////

////// variables //////
var store;
////// variables //////

////// overrides //////
////// overrides //////

////// componentes dinamicos //////
var itemsFormulario = [<s:property value="items.itemsFormulario" escapeHtml="false" />];
var itemsGrid = [<s:property value="items.itemsGrid" escapeHtml="false" />];
var itemsInsert = [<s:property value="items.itemsInsert" escapeHtml="false" />];
var itemsGridModel = [<s:property value="items.itemsGridModel" escapeHtml="false" />];

itemsGrid.push({xtype : 'actioncolumn'
		        ,icon : '${icons}pencil.png'
		        ,tooltip : 'editar'
			    });
////// componentes dinamicos //////

Ext.onReady(function()
{
    ////// modelos //////
     Ext.define('ModeloConvenio',
    {
        extend  : 'Ext.data.Model'
        ,fields : itemsGridModel
      }); 
    ////// modelos //////
    
    ////// stores //////
    store = Ext.create('Ext.data.Store',
    {
        model : 'ModeloConvenio'
    }); 
    ////// stores //////
    
    ////// componentes //////
    ////// componentes //////
    
    ////// contenido //////
    Ext.create('Ext.panel.Panel',
    {
        title     : 'Panel principal'
        ,panelPri : 'S'
        ,renderTo : '_p100_divpri'
        ,defaults :
        {
            style : 'margin:5px;'
        }
        ,items    :
        [
		    Ext.create('Ext.form.Panel',
		    {
		        title     : 'Consulta y registro de convenios'
		        ,layout   :
		        {
		            type     : 'table'
		            ,columns : 3
		        }
		        ,defaults :
		        {
		            style : 'margin:5px;'
		        }
		        ,items : itemsFormulario
		        ,buttonAlign : 'center'
		        ,buttons :
		        [
		            {
                        text     : 'buscar'
                        ,icon    : '${icons}find.png'
                        ,handler : function(me)
                        {
                            var values = me.up('form').getValues();
                            _mask('Buscando...');
                            Ext.Ajax.request(
                            {
                                url     : '<s:url namespace="/convenios" action="buscarPorPoliza" />'
                                ,params :
                                {
                                    'params.cdunieco'    : values.cdunieco
                                    ,'params.cdramo'     : values.cdramo
                                    ,'params.estado' 	 : values.estatus
                                    ,'params.nmpoliza' 	 : values.nmpoliza
                                }
                                ,success : function(response)
                                {
                                    _unmask();
                                    var json = Ext.decode(response.responseText);
                                    if(json.success==true)
                                    {
                                    	store.removeAll();
                                        store.add(json.list);
//                                         debug('json',json);
                                    }
                                    else
                                    {
                                        mensajeError(json.message);
                                    }
                                }
                                ,failure : function()
                                {
                                    _unmask();
                                    errorComunicacion(null,'error de red al guardar');
                                }
                            });
                        }
                    }
		            ,{
		                text     : 'agregar'
                        ,icon    : '${icons}add.png'
                        ,handler : function(me)
                        {
		                    Ext.create('Ext.window.Window',
				                    {
				                        title   : 'Agregar nuevo convenio'
				                        ,width  : 300
				                        ,height : 400
				                        ,modal  : true
				                        ,closable : false
				                        ,items  : [
			                        	Ext.create('Ext.form.Panel',
	                        		    {
	                        		        title     : 'Consulta y registro de convenios'
	                        		        ,layout   :
	                        		        {
	                        		            type     : 'table'
	                        		            ,columns : 1
	                        		        }
	                        		        ,defaults :
	                        		        {
	                        		            style : 'margin:5px;'
	                        		        }
	                        		        ,items : itemsInsert
	                        		        ,buttonAlign : 'center'
			                        		        	
				                        	
				                        ,buttons :
				                        [
				                            {
				                                text     : 'cerrar'
				                                ,handler : function(me){
				                                    me.up('window').close();
				                                }				                             
				                            }
				                            ,{
				                            text     : 'guardar'
			                                ,handler : function(me){
			                                	var values = me.up("form").getValues();
			                                	_mask('Guardando...');
			                                	Ext.Ajax.request({
			                                                url     : '<s:url namespace="/convenios" action="guardarEnBase" />'
			                                                ,params :
			                                                {
			                                                    'params.cdunieco'    : values.cdunieco
			                                                    ,'params.cdramo'     : values.cdramo
			                                                    ,'params.estado' 	 : values.estado
			                                                    ,'params.nmpoliza' 	 : values.nmpoliza
			                                                    ,'params.diasgrac' 	 : values.nmdias
			                                                    ,'params.cdconven' 	 : values.convenio
			                                                    ,'params.estatus' 	 : values.estatus
			                                                }
			                                                ,success : function(response)
			                                                {
			                                                    _unmask();
			                                                    var json = Ext.decode(response.responseText);
			                                                    if(json.success==true){
			                                                        mensajeCorrecto('datos guardados','se guardo');				                                                        
			                                                    }
			                                                    else
			                                                    {
			                                                        mensajeError('Error al guardar',json.message);
			                                                    }
			                                                }
			                                                ,failure : function()
			                                                {
			                                                    _unmask();
			                                                    errorComunicacion(null,'Error de red al guardar');
			                                                }
			                                            }); 
			                                }
				                    	  }
				                        ]
	                        		    })
	                        		    ]
				                    }).show();
                        }
		            }
 		            ,{
		                text     : 'limpiar'
		                ,icon    : '${icons}arrow_refresh.png'
		                ,handler : function()
		                {
		                	store.removeAll();
		                }
		            } 
		        ]
		    })
		    ,Ext.create('Ext.grid.Panel',
		    {
		        title    : 'Tabla'
		        //,width   : 900
		        ,height  : 200
		        ,store   : store
		        ,columns : itemsGrid
		        ,plugins : Ext.create('Ext.grid.plugin.RowEditing',
		                {
		                    clicksToEdit  : 1
		                    ,errorSummary : false
		                    ,listeners    :
		                    {
		                        afteredit : function(editor,context)
		         		        {
		                        	var values = context.record.getData();
		                        	debug('context',context.record.getData());
		                        	_mask('Editando...');
		                        	Ext.Ajax.request({
                                        url     : '<s:url namespace="/convenios" action="editarEnBase" />'
                                        ,params :
                                        {
                                            'params.cdunieco'    : values.CDUNIECO
                                            ,'params.cdramo'     : values.CDRAMO
                                            ,'params.estado' 	 : values.ESTADO
                                            ,'params.nmpoliza' 	 : values.NMPOLIZA
                                            ,'params.diasgrac' 	 : values.DIASGRAC
//                                             ,'params.leyenda' 	 : values.LEYENDA
                                            ,'params.cdconven' 	 : values.CODIGO
                                            ,'params.estatus' 	 : values.STATUS
                                        }
                                        ,success : function(response)
                                        {
                                            _unmask();
                                            var json = Ext.decode(response.responseText);
                                            if(json.success==true){
                                                mensajeCorrecto('datos guardados','Se guardó');				                                                        
                                            }
                                            else
                                            {
                                                mensajeError('Error al guardar',json.message);
                                            }
                                        }
                                        ,failure : function()
                                        {
                                            _unmask();
                                            errorComunicacion(null,'Error de red al guardar');
                                        }
                                    });
		                        }
		                    }
		                })
		    })
        ]
    });
    ////// contenido //////
    
    ////// custom //////
    ////// custom //////
    
    ////// loaders //////
    ////// loaders //////
});

////// funciones //////
// };
////// funciones //////
</script>
</head>
<body>
<div id="_p100_divpri" style="height:600px;border:1px solid #CCCCCC"></div>
</body>
</html>