<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script>
///////////////////////
////// variables //////
/*///////////////////*/
extjs_custom_override_mayusculas = false;

var edipanFiltro;
var edipanGrid;
var edipanEditor;
var edipanStore;
var edipanIndexEditado;

var edipanUrlLoad = '<s:url namespace="/endosos" action="obtenerParametrosPantalla" />';
var edipanUrlSave = '<s:url namespace="/endosos" action="guardarParametrosPantalla" />';
var edipanUrlView = '<s:url namespace="/endosos" action="visorPantallas"        />';
/*///////////////////*/
////// variables //////
///////////////////////

///////////////////////
////// funciones //////
/*///////////////////*/
/*///////////////////*/
////// funciones //////
///////////////////////

Ext.onReady(function()
{
	/////////////////////
	////// modelos //////
	/*/////////////////*/
	Ext.define('EdipanModelo',
	{
		extend  : 'Ext.data.Model'
		,fields :
		[
			<s:property value="imap1.fieldsModelo" />
		]
	});
	/*/////////////////*/
	////// modelos //////
	/////////////////////
	
	////////////////////
	////// stores //////
	/*////////////////*/
	edipanStore=Ext.create('Ext.data.Store',
	{
		model     : 'EdipanModelo'
		,autoLoad : false
		,proxy    :
        {
            type    : 'ajax'
            ,url    : edipanUrlLoad
            ,reader :
            {
                type  : 'json'
                ,root : 'slist1'
            }
        }
	});
	/*////////////////*/
	////// stores //////
	////////////////////
	
	/////////////////////////
	////// componentes //////
	/*/////////////////////*/
	Ext.define('EdipanFiltro',
	{
		extend         : 'Ext.form.Panel'
		,initComponent : function()
		{
			debug('Instancia de EdipanFiltro')
			Ext.apply(this,
			{
				title        : 'Filtro'
				,collapsible : true
				,layout      :
				{
					type     : 'table'
					,columns : 3
				}
				,items       :
				[
				    <s:property value="imap1.itemsFiltro" />
				]
				,buttonAlign : 'center'
				,buttons     :
				[
				    {
				    	text     : 'Filtrar'
				    	,icon    : '${ctx}/resources/fam3icons/icons/zoom.png'
				    	,handler : function()
				    	{
				    		var form=this.up().up();
				    		if(form.isValid())
				    		{
				    			debug(form.getValues());
				    			
				    			edipanStore.load(
				    			{
				    				params : form.getValues()
				    			});
				    			
				    			edipanFiltro.setDisabled(true);
				    			edipanGrid.setDisabled(false);
				    			edipanEditor.setDisabled(true);
				    			
				    			edipanFiltro.collapse();
				    			edipanGrid.expand();
                                edipanEditor.collapse();
				    		}
				    	}
				    }
				]
			});
			this.callParent();
		}
	});
	
	Ext.define('EdipanGrid',
	{
		extend         : 'Ext.grid.Panel'
		,disabled      : true
		,initComponent : function()
		{
			debug('Instancia de EdipanGrid');
			Ext.apply(this,
			{
				title        : 'Campos'
				,store       : edipanStore
                ,collapsible : true
				,height      : 400
				,columns     :
				[
                    <s:property value="imap1.columnsModelo" />
				]
			    ,buttonAlign : 'center'
			    ,buttons     :
			    [
					{
					    text     : 'Guardar'
					    ,icon    : '${ctx}/resources/fam3icons/icons/disk.png'
					    ,handler : function()
					    {
					        
					    	var json={};
					    	
					        edipanFiltro.setDisabled(false);//para poder usar los valores (*)
					        var values=edipanFiltro.getValues();
					    	edipanFiltro.setDisabled(true);//regresarlo a como estaba (*)
					    	
					        var smap1={};
					        smap1['cduno']    = values['smap1.cduno'];
					        smap1['cddos']    = values['smap1.cddos'];
					        smap1['cdtres']   = values['smap1.cdtres'];
					        smap1['cdcuatro'] = values['smap1.cdcuatro'];
					        smap1['cdcinco']  = values['smap1.cdcinco'];
					        smap1['cdseis']   = values['smap1.cdseis'];
					        smap1['cdsiete']  = values['smap1.cdsiete'];
					        smap1['cdocho']   = values['smap1.cdocho'];
					        smap1['cdnueve']  = values['smap1.cdnueve'];
					        smap1['cddiez']   = values['smap1.cddiez'];
					        json['smap1']     = smap1;
					    	
					    	var slist1=[];
					    	edipanStore.each(function(record)
					    	{
					    		slist1.push(record.getData());
					    	});
					    	json['slist1']=slist1;
					    	
					    	debug(json);
					    	
					    	edipanGrid.setLoading(true);
					    	Ext.Ajax.request(
					    	{
					    		url       : edipanUrlSave
					    		,jsonData : json
					    		,success  : function(response)
					    		{
					    			edipanGrid.setLoading(false);
					    			var jsonres=Ext.decode(response.responseText);
					    			if(jsonres.success==true)
					    			{
						    			edipanFiltro.setDisabled(false);
			                            edipanGrid.setDisabled(true);
			                            edipanEditor.setDisabled(true);
			                            
			                            edipanFiltro.expand();
			                            edipanGrid.collapse();
			                            edipanEditor.collapse();
					    			}
					    			else
					    			{
					    				Ext.Msg.show(
	                                    {
	                                        title    : 'Error'
	                                        ,msg     : 'Error al guardar'
	                                        ,buttons : Ext.Msg.OK
	                                        ,icon    : Ext.Msg.ERROR
	                                    });
					    			}
					    		}
					    	    ,failure  : function()
					    	    {
					    	    	edipanGrid.setLoading(false);
					    	    	
					    	    	Ext.Msg.show(
					    	    	{
	                                    title    : 'Error'
	                                    ,msg     : 'Error de comunicaci&oacute;n'
	                                    ,buttons : Ext.Msg.OK
	                                    ,icon    : Ext.Msg.ERROR
	                                });
					    	    }
					    	});
					    }
					}
					,{
						text     : 'Vista previa'
						,icon    : '${ctx}/resources/fam3icons/icons/zoom.png'
						,handler : function()
						{
							edipanFiltro.setDisabled(false);
							var params=edipanFiltro.getValues();
							edipanFiltro.setDisabled(true);
							
							Ext.create('Ext.form.Panel').submit(
							{
								standardSubmit : true
								,url           : edipanUrlView
								,params        : params 
								,target        : '_blank'
							});
						}
					}
			        ,{
			        	text     : 'Cancelar'
			        	,icon    : '${ctx}/resources/fam3icons/icons/cancel.png'
			        	,handler : function()
			        	{
			        		edipanFiltro.setDisabled(false);
                            edipanGrid.setDisabled(true);
                            edipanEditor.setDisabled(true);
                            
                            edipanFiltro.expand();
                            edipanGrid.collapse();
                            edipanEditor.collapse();
			        	}
			        }
			    ]
			    ,listeners   :
			    {
			    	itemclick : function(grid,record,item,index)
			    	{
			    		edipanIndexEditado=index;
			    		debug('edipanIndexEditado',edipanIndexEditado);
			    		
			    		edipanEditor.loadRecord(record);
			    		
			    		edipanFiltro.setDisabled(true);
                        edipanGrid.setDisabled(true);
                        edipanEditor.setDisabled(false);
                        
                        edipanFiltro.collapse();
                        edipanGrid.collapse();
                        edipanEditor.expand();
			    	}
			    }
			});
			this.callParent();
		}
	});
	
	Ext.define('EdipanEditor',
	{
		extend         : 'Ext.form.Panel'
		,initComponent : function()
		{
			debug('Instancia de EdipanEditor');
			Ext.apply(this,
			{
				title        : 'Editor'
	            ,collapsible : true
	            ,disabled    : true
	            ,layout      :
	            {
	                type     : 'table'
	                ,columns : 3
	            }
		        ,items       :
		        [
		            <s:property value="imap1.itemsModelo" />
		        ]
		        ,buttonAlign : 'center'
                ,buttons     :
                [
                    {
                    	text     : 'Aceptar'
                    	,icon    : '${ctx}/resources/fam3icons/icons/accept.png'
                    	,handler : function()
                    	{
                    		debug(this.up().up().getValues());
                    		
                    		if(this.up().up().isValid())
                    		{
	                    		edipanStore.removeAt(edipanIndexEditado);
	                    		edipanStore.insert(edipanIndexEditado,this.up().up().getValues());
	                    		
	                    		edipanFiltro.setDisabled(true);
	                            edipanGrid.setDisabled(false);
	                            edipanEditor.setDisabled(true);
	                            
	                            edipanFiltro.collapse();
	                            edipanGrid.expand();
	                            edipanEditor.collapse();
                    		}
                    	}
                    }
                    ,{
                        text     : 'Duplicar'
                        ,icon    : '${ctx}/resources/fam3icons/icons/add.png'
                        ,handler : function()
                        {
                            debug(this.up().up().getValues());
                            
                            edipanStore.add(this.up().up().getValues());
                            
                            edipanFiltro.setDisabled(true);
                            edipanGrid.setDisabled(false);
                            edipanEditor.setDisabled(true);
                            
                            edipanFiltro.collapse();
                            edipanGrid.expand();
                            edipanEditor.collapse();
                        }
                    }
                    ,{
                        text     : 'Borrar'
                        ,icon    : '${ctx}/resources/fam3icons/icons/delete.png'
                        ,handler : function()
                        {
                            debug(this.up().up().getValues());
                            
                            edipanStore.removeAt(edipanIndexEditado);
                            
                            edipanFiltro.setDisabled(true);
                            edipanGrid.setDisabled(false);
                            edipanEditor.setDisabled(true);
                            
                            edipanFiltro.collapse();
                            edipanGrid.expand();
                            edipanEditor.collapse();
                        }
                    }
                    ,{
                        text     : 'Cancelar'
                        ,icon    : '${ctx}/resources/fam3icons/icons/cancel.png'
                        ,handler : function()
                        {
                            edipanFiltro.setDisabled(true);
                            edipanGrid.setDisabled(false);
                            edipanEditor.setDisabled(true);
                            
                            edipanFiltro.collapse();
                            edipanGrid.expand();
                            edipanEditor.collapse();
                        }
                    }
                ]
			});
			this.callParent();
		}
	});
	/*/////////////////////*/
	////// componentes //////
	/////////////////////////
	
	///////////////////////
	////// contenido //////
	/*///////////////////*/
	edipanFiltro = new EdipanFiltro();
	edipanGrid   = new EdipanGrid();
	edipanEditor = new EdipanEditor();
	
	Ext.create('Ext.panel.Panel',
	{
		renderTo  : 'edipanDivPri'
		,border   : 0
		,defaults :
		{
			style       : 'margin : 5px;'
		}
		,items    :
		[
            edipanFiltro
            ,edipanGrid
            ,edipanEditor
		]
	});
	/*///////////////////*/
	////// contenido //////
	///////////////////////
	
	//////////////////////
	////// cargador //////
	/*//////////////////*/
	/*//////////////////*/
	////// cargador //////
	//////////////////////
});
</script>
</head>
<body>
<div id="edipanDivPri"></div>
</body>
</html>