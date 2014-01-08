<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
///////////////////////
////// variables //////
/*///////////////////*/
var panendabaseguGridAsegu;
var panendabaseguStoreAsegu;
var panendabaseguPanelLectura;
var panendabaseguPanelPrincipal;
var panEndAltBajAseStoreAltas;
var panEndAltBajAseStoreBajas;
var panEndAltBajAseGridAltas;
var panEndAltBajAseGridBajas;
var panEndAltBajAseWindowAsegu;
var panEndAltBajAseNmsituac;
var panEndAltBajAseValues='cadena';

var panendabaseguUrlLoadAsegu = '<s:url namespace="/" action="cargarComplementariosAsegurados" />';
var panendabaseguInputSmap1   = <s:property value='%{getSmap1().toString().replace("=",":\'").replace(",","\',").replace("}","\'}")}' />;
var panendabaseguUrlSave      = '<s:url namespace="/endosos" action="guardarEndosoAltaBajaAsegurado" />';
var panEndAltBajAseUrlDoc     = '<s:url namespace="/documentos" action="ventanaDocumentosPoliza" />';

debug('panendabaseguInputSmap1',panendabaseguInputSmap1);
/*///////////////////*/
////// variables //////
///////////////////////

///////////////////////
////// funciones //////
/*///////////////////*/
function panendabaseguFunAgregar()
{
	debug('panendabaseguFunAgregar');
	if(panEndAltBajAseStoreAltas.getCount()+panEndAltBajAseStoreBajas.getCount()==0)
    {
		panEndAltBajAseNmsituac = panendabaseguStoreAsegu.getCount();
        debug('panEndAltBajAseNmsituac:',panEndAltBajAseNmsituac);
        panEndAltBajAseWindowAsegu=new PanEndAltBajAseWindowAsegu();
        panEndAltBajAseWindowAsegu.show();
    }
    else
    {
        Ext.Msg.show(
        {
            title    : 'Error'
            ,icon    : Ext.Msg.WARNING
            ,msg     : 'No se pueden quitar/agregar m&aacute;s asegurados'
            ,buttons : Ext.Msg.OK
        });
    }
}

function panendabaseguFunQuitar()
{
	debug('panendabaseguFunQuitar');
	var aseguSelec=0;
	var aseguActivo;
    panendabaseguStoreAsegu.each(function(record)
    {
        if(record.get('activo')==true)
        {
            aseguSelec=aseguSelec+1;
            aseguActivo=record;   
        }
    });
    if(aseguSelec==1)
    {
        if(aseguActivo.get('cdrol')==2)
        {
        	if(panEndAltBajAseStoreAltas.getCount()+panEndAltBajAseStoreBajas.getCount()==0)
        	{
        		panEndAltBajAseStoreBajas.add(aseguActivo);
        		panendabaseguStoreAsegu.remove(aseguActivo);
        	}
        	else
        	{
        		Ext.Msg.show(
   	            {
   	                title    : 'Error'
   	                ,icon    : Ext.Msg.WARNING
   	                ,msg     : 'No se pueden quitar/agregar m&aacute;s asegurados'
   	                ,buttons : Ext.Msg.OK
   	            });
        	}
        }
        else
        {
        	Ext.Msg.show(
   	        {
   	            title    : 'Error'
   	            ,icon    : Ext.Msg.WARNING
   	            ,msg     : 'No se puede quitar el contratante'
   	            ,buttons : Ext.Msg.OK
   	        });
        }
    }
    else
    {
        Ext.Msg.show(
        {
            title    : 'Error'
            ,icon    : Ext.Msg.WARNING
            ,msg     : 'Seleccione un asegurado'
            ,buttons : Ext.Msg.OK
        });
    }
}
/*///////////////////*/
////// funciones //////
///////////////////////

Ext.onReady(function()
{	
	/////////////////////
	////// modelos //////
	/*/////////////////*/
	Ext.define('panendabaseguModelo',
	{
		extend  : 'Ext.data.Model'
		,fields :
		[
		    {
		    	name  : 'activo'
		    	,type : 'boolean'
		    }
		    ,<s:property value="imap1.modelo" />
		]
	});
	/*/////////////////*/
	////// modelos //////
	/////////////////////
	
	////////////////////
	////// stores //////
	/*////////////////*/
	panendabaseguStoreAsegu = Ext.create('Ext.data.Store',
    {
        autoLoad : true
        ,model   : 'panendabaseguModelo'
        ,proxy   :
        {
            type         : 'ajax'
            ,url         : panendabaseguUrlLoadAsegu
            ,extraParams :
            {
            	 'map1.pv_cdunieco' : panendabaseguInputSmap1.CDUNIECO
            	,'map1.pv_cdramo'   : panendabaseguInputSmap1.CDRAMO
            	,'map1.pv_estado'   : panendabaseguInputSmap1.ESTADO
            	,'map1.pv_nmpoliza' : panendabaseguInputSmap1.NMPOLIZA
            	,'map1.pv_nmsuplem' : panendabaseguInputSmap1.NMSUPLEM
            }
            ,reader      :
            {
            	type  : 'json'
            	,root : 'list1'
            }
        }
    });
	
	panEndAltBajAseStoreAltas = Ext.create('Ext.data.Store',
    {
        model  : 'panendabaseguModelo'
        ,proxy :
        {
            type    : 'memory'
            ,reader : 'json'
            ,data   : []
        }
    });
	
	panEndAltBajAseStoreBajas = Ext.create('Ext.data.Store',
    {
        model  : 'panendabaseguModelo'
        ,proxy :
        {
            type    : 'memory'
            ,reader : 'json'
            ,data   : []
        }
    });
	/*////////////////*/
	////// stores //////
	////////////////////
	
	/////////////////////////
	////// componentes //////
	/*/////////////////////*/
	Ext.define('PanEndAltBajAseWindowAsegu',
	{
		extend         : 'Ext.window.Window'
		,initComponent : function()
        {
			debug('PanEndAltBajAseWindowAsegu initComponent');
			Ext.apply(this,
			{
				title   : 'Agregar asegurado'
		        ,icon   : '${ctx}/resources/fam3icons/icons/add.png'
		        ,width  : 700
		        ,modal  : true
		        ,height : 400
		        ,items  :
		        [
			        Ext.create('Ext.form.Panel',
			        {
			        	items        :
			        	[
			        	    <s:property value="imap1.formulario" />
			        	]
				        ,layout      :
				        {
				        	type     : 'table'
				        	,columns : 2
				        }
			            ,buttonAlign : 'center'
			            ,buttons     :
			            [
			                {
			                	text     : 'Agregar'
			                	,icon    : '${ctx}/resources/fam3icons/icons/add.png'
			                	,handler : function()
			                	{
			                		var form=this.up().up();
			                		var _window=form.up();
			                		if(form.isValid())
			                		{
			                			panEndAltBajAseValues=form.getValues();
			                			debug('panEndAltBajAseValues',panEndAltBajAseValues);
			                			panEndAltBajAseStoreAltas.add(
			                			{
			                				nmsituac          : panEndAltBajAseNmsituac
			                				,nombre           : panEndAltBajAseValues['smap1.nombre']
			                				,segundo_nombre   : panEndAltBajAseValues['smap1.nombre2']
			                			    ,Apellido_Paterno : panEndAltBajAseValues['smap1.apat']
			                			    ,Apellido_Materno : panEndAltBajAseValues['smap1.amat']
			                			    ,cdrol            : 2
			                			    ,Parentesco       : panEndAltBajAseValues['parametros.pv_otvalor16']
			                			    ,cdrfc            : panEndAltBajAseValues['smap1.rfc']
			                			    ,tpersona         : panEndAltBajAseValues['smap1.tpersona']
			                			    ,sexo             : panEndAltBajAseValues['parametros.pv_otvalor01']
			                			    ,fenacimi         : panEndAltBajAseValues['parametros.pv_otvalor19']
			                			});
			                			_window.destroy();
			                		}
			                		else
			                		{
			                			Ext.Msg.show(
	                			        {
	                			            title    : 'Error'
	                			            ,icon    : Ext.Msg.WARNING
	                			            ,msg     : 'Datos incompletos'
	                			            ,buttons : Ext.Msg.OK
	                			        });
			                		}
			                	}
			                }
			            ]
			        })
		        ]
			});
			this.callParent();
        }
	});
	Ext.define('PanEndAltBajAseGridAltas',
    {
        extend         : 'Ext.grid.Panel'
        ,initComponent : function()
        {
            debug('PanEndAltBajAseGridAltas initComponent');
            Ext.apply(this,
            {
                title      : 'Alta de asegurados'
                ,icon      : '${ctx}/resources/fam3icons/icons/add.png'
                ,store     : panEndAltBajAseStoreAltas
                ,minHeight : 100
                ,columns   :
                [
                    <s:property value="imap1.columnas" />
                    ,{
                    	xtype         : 'actioncolumn'
                    	,width        : 30
                    	,icon         : '${ctx}/resources/fam3icons/icons/delete.png'
                    	,tooltip      : 'No agregar'
                    	,menuDisabled : true
                    	,sortable     : false
                    	,handler      : this.onDeleteClick
                    }
                ]
            });
            this.callParent();
        }
	    ,onDeleteClick : function(grid,rowIndex)
	    {
	    	grid.getStore().removeAt(rowIndex);
	    }
    });
	Ext.define('PanEndAltBajAseGridBajas',
    {
        extend         : 'Ext.grid.Panel'
        ,initComponent : function()
        {
            debug('PanEndAltBajAseGridBajas initComponent');
            Ext.apply(this,
            {
                title      : 'Baja de asegurados'
                ,icon      : '${ctx}/resources/fam3icons/icons/delete.png'
                ,store     : panEndAltBajAseStoreBajas
                ,minHeight : 100
                ,columns   :
                [
                    <s:property value="imap1.columnas" />
                    ,{
                        xtype         : 'actioncolumn'
                        ,width        : 30
                        ,icon         : '${ctx}/resources/fam3icons/icons/delete.png'
                        ,tooltip      : 'No quitar'
                       	,menuDisabled : true
                        ,sortable     : false
                        ,handler      : this.onDeleteClick
                    }
                ]
            });
            this.callParent();
        }
        ,onDeleteClick : function(grid,rowIndex)
        {
            var record = grid.getStore().getAt(rowIndex);
            grid.getStore().removeAt(rowIndex);
            panendabaseguStoreAsegu.add(record);
        }
    });
	Ext.define('PanendabaseguPanelLectura',
    {
        extend         : 'Ext.panel.Panel'
        ,initComponent : function()
        {
            debug('PanendabaseguPanelLectura initComponent');
            Ext.apply(this,
            {
                title   : 'P&oacute;liza afectada'
                ,layout :
                {
                	columns : 3
                	,type   : 'table'
                }
                ,items  :
                [
                    <s:property value="imap1.panelLectura" />
                ]
            });
            this.callParent();
        }
    });
	Ext.define('PanendabaseguGridAsegu',
	{
		extend         : 'Ext.grid.Panel'
		,initComponent : function()
		{
			debug('PanendabaseguGridAsegu initComponent');
			Ext.apply(this,
			{
				title    : 'Asegurados'
		        ,icon    : '${ctx}/resources/fam3icons/icons/user.png'
				,store   : panendabaseguStoreAsegu
				,minHeight : 100
				,tbar    :
				[
				    {
				    	text     : 'Agregar'
				    	,icon    : '${ctx}/resources/fam3icons/icons/add.png'
				    	,handler : panendabaseguFunAgregar
				    }
				    ,{
                        text     : 'Quitar'
                        ,icon    : '${ctx}/resources/fam3icons/icons/delete.png'
                        ,handler : panendabaseguFunQuitar
                    }
				]
				,columns :
				[
					{
					    dataIndex     : 'activo'
					    ,xtype        : 'checkcolumn'
					    ,width        : 30
					    ,menuDisabled : true
					    ,sortable     : false
					}
				    ,<s:property value="imap1.columnas" />
				]
			});
			this.callParent();
		}
	});
	Ext.define('PanendabaseguPanelPrincipal',
    {
        extend         : 'Ext.panel.Panel'
        ,initComponent : function()
        {
            debug('PanendabaseguPanelPrincipal initComponent');
            Ext.apply(this,
            {
            	border       : 0
            	,defaults    :
            	{
            		style : 'margin : 5px;'
            	}
                ,items       :
                [
                    panendabaseguPanelLectura
                    ,panendabaseguGridAsegu
                    ,panEndAltBajAseGridAltas
                    ,panEndAltBajAseGridBajas
                ]
                ,renderTo    : 'panendabaseDivPri'
                ,buttonAlign : 'center'
                ,buttons     :
                [
                    {
                    	text     : 'Confirmar endoso'
                    	,id      : 'panEndAltBajAseBotConfirmar'
                    	,icon    : '${ctx}/resources/fam3icons/icons/key.png'
                    	,handler : this.onConfirmarClick
                    }
                    ,{
                        text     : 'Documentos'
                        ,icon    : '${ctx}/resources/fam3icons/icons/printer.png'
                        ,handler : function()
                        {
                            Ext.create('Ext.window.Window',
                            {
                                title        : 'Documentos del tr&aacute;mite '+panendabaseguInputSmap1.NTRAMITE
                                ,modal       : true
                                ,buttonAlign : 'center'
                                ,width       : 600
                                ,height      : 400
                                ,autoScroll  : true
                                ,loader      :
                                {
                                    url       : panEndAltBajAseUrlDoc
                                    ,params   :
                                    {
                                         'smap1.cdunieco' : panendabaseguInputSmap1.CDUNIECO
                                        ,'smap1.cdramo'   : panendabaseguInputSmap1.CDRAMO
                                        ,'smap1.estado'   : panendabaseguInputSmap1.ESTADO
                                        ,'smap1.nmpoliza' : panendabaseguInputSmap1.NMPOLIZA
                                        ,'smap1.nmsuplem' : '0'
                                        ,'smap1.ntramite' : panendabaseguInputSmap1.NTRAMITE
                                        ,'smap1.nmsolici' : ''
                                        ,'smap1.tipomov'  : '0'
                                    }
                                    ,scripts  : true
                                    ,autoLoad : true
                                }
                            }).show();
                        }
                    }
                ]
            });
            this.callParent();
        }
	    ,onConfirmarClick : function()
	    {
	    	debug('onConfirmarClick');
	    	if(panEndAltBajAseStoreAltas.getCount()+panEndAltBajAseStoreBajas.getCount()==1)
	    	{
	    		var json={};
	    		if(panEndAltBajAseStoreAltas.getCount()==1)
	    		{
	    			json['smap1']=panEndAltBajAseValues;
	    			json['smap1']['nmsituac'] = panEndAltBajAseNmsituac;
	    		}
	    		else
	    		{
	    			json['smap1']=panEndAltBajAseStoreBajas.getAt(0).raw;
	    		}
	    		json['smap2']          = panendabaseguInputSmap1;
	    		debug('json:',json);
	    		panendabaseguPanelPrincipal.setLoading(true);
	    		Ext.Ajax.request(
	    		{
	    			url       : panendabaseguUrlSave
	    			,jsonData : json
	    			,success  : function(response)
		            {
	    				panendabaseguPanelPrincipal.setLoading(false);
	    				json=Ext.decode(response.responseText);
	    				debug('response',json);
	    				if(json.success==true)
	    				{
	    					Ext.getCmp('panEndAltBajAseBotConfirmar').hide();
	    					Ext.Msg.show(
                            {
                                title    : 'Endoso confirmado'
                                ,msg     : 'Se ha confirmado el endoso'
                                ,buttons : Ext.Msg.OK
                            });
	    				}
	    				else
	    				{
	    					Ext.Msg.show(
   	                        {
   	                            title    : 'Error'
   	                            ,icon    : Ext.Msg.ERROR
   	                            ,msg     : 'Error de comunicaci&oacute;n'
   	                            ,buttons : Ext.Msg.OK
   	                        });
	    				}
		            }
	    		    ,failure  : function()
	    		    {
	    		    	panendabaseguPanelPrincipal.setLoading(false);
	    		    	Ext.Msg.show(
   		                {
   		                    title    : 'Error'
   		                    ,icon    : Ext.Msg.ERROR
   		                    ,msg     : 'Error de comunicaci&oacute;n'
   		                    ,buttons : Ext.Msg.OK
   		                });
	    		    }
	    		});
	    	}
	    	else
	    	{
	    		Ext.Msg.show(
   		        {
   		            title    : 'Error'
   		            ,icon    : Ext.Msg.WARNING
   		            ,msg     : 'No hay alta ni baja de asegurados'
   		            ,buttons : Ext.Msg.OK
   		        });
	    	}
	    }
    });
	/*/////////////////////*/
	////// componentes //////
	/////////////////////////
	
	///////////////////////
	////// contenido //////
	/*///////////////////*/
	panEndAltBajAseGridAltas    = new PanEndAltBajAseGridAltas();
	panEndAltBajAseGridBajas    = new PanEndAltBajAseGridBajas();
	panendabaseguGridAsegu      = new PanendabaseguGridAsegu();
	panendabaseguPanelLectura   = new PanendabaseguPanelLectura();
	panendabaseguPanelPrincipal = new PanendabaseguPanelPrincipal(); 
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
<div id="panendabaseDivPri" style="height:1500px;"></div>