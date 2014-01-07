<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
///////////////////////
////// variables //////
/*///////////////////*/
var panendabaseguGridAsegu;
var panendabaseguStoreAsegu;
var panendabaseguPanelLectura;
var PanendabaseguPanelPrincipal;

var panendabaseguUrlLoadAsegu = '<s:url namespace="/" action="cargarComplementariosAsegurados" />';
var panendabaseguInputSmap1   = <s:property value='%{getSmap1().toString().replace("=",":\'").replace(",","\',").replace("}","\'}")}' />;

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
	/*////////////////*/
	////// stores //////
	////////////////////
	
	/////////////////////////
	////// componentes //////
	/*/////////////////////*/
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
            	border    : 0
            	,defaults :
            	{
            		style : 'margin : 5px;'
            	}
                ,items    :
                [
                    panendabaseguPanelLectura
                    ,panendabaseguGridAsegu
                ]
                ,renderTo : 'panendabaseDivPri'
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
	panendabaseguGridAsegu      = new PanendabaseguGridAsegu();
	panendabaseguPanelLectura   = new PanendabaseguPanelLectura();
	panendabaseguPanelPrincipal = new PanendabaseguPanelPrincipal();//<-- contenido 
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