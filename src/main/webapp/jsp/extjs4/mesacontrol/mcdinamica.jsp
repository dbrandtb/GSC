<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script>
///////////////////////
////// variables //////
/*///////////////////*/
var mcdinInput    = [];
var mcdinUrlNuevo = '<s:url namespace="/mesacontrol" action="guardarTramiteDinamico" />';
debug('mcdinInput: ',mcdinInput);

var mcdinGrid;
/*///////////////////*/
////// variables //////
///////////////////////

///////////////////////
////// funciones //////
/*///////////////////*/
function mcdinAgregarTramiteManual()
{
	debug('mcdinAgregarTramiteManual');
	Ext.create('Ext.window.Window',
	{
		title   : 'Nuevo/Agregar'
		,icon   : '${ctx}/resources/fam3icons/icons/page.png'
		,width  : 600
		,modal  : true
		,height : 400
		,items  :
		[
	    	Ext.create('Ext.form.Panel',
	    	{
	    		buttonAlign : 'center'
	    		,border     : 0
	    		,url        : mcdinUrlNuevo
	    		,layout     :
	    		{
	    			type     : 'table'
	    			,columns : 2
	    		}
	    	    ,defaults   :
	    	    {
	    	    	style : 'margin : 5px;'
	    	    }
	    		,items      :
	    		[
                       <s:property value="imap1.formItems" />
	    		]
	    		,buttons    :
	    		[
	    			{
	    				text     : 'Guardar'
	    				,icon    : '${ctx}/resources/fam3icons/icons/disk.png'
	    				,handler : function()
	    				{
	    					var form=this.up().up();
	    					debug(form.getForm().getValues());
	    					if(form.isValid())
	    					{
	    						form.setLoading(true);
	    						form.submit(
	    						{
	    							success  : function(form2, action)
	    						    {
                                        form.setLoading(false);
                                        Ext.Msg.show(
                                        {
                                            title    : 'Cambios guardados'
                                            ,msg     : 'Se agreg&oacute; un nuevo tr&aacute;mite</br> N&uacute;mero: '+ action.result.msgResult
                                            ,buttons : Ext.Msg.OK
                                            ,fn      : function()
                                            {
                                                Ext.create('Ext.form.Panel').submit({standardSubmit:true});
                                            }
                                        });    
                                    }
                                    ,failure : function()
                                    {
                                        form.setLoading(false);
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
	    					else
	    					{
	    						Ext.Msg.show(
	    						{
                                    title    : 'Error'
                                    ,msg     : 'Favor de introducir los campos requeridos'
                                    ,buttons : Ext.Msg.OK
                                    ,icon    : Ext.Msg.WARNING
                                });
	    					}
	    				}
	    			}
	    		]
	    	})
		]
	}).show();
}
/*///////////////////*/
////// funciones //////
///////////////////////

Ext.onReady(function()
{
    
    /////////////////////
    ////// modelos //////
    /*/////////////////*/
    Ext.define('McdinModelo',
    {
    	extend  : 'Ext.data.Model'
    	,fields :
    	[
    	    {
    	    	name  : 'activo'
    	    	,type : 'boolean'
    	    }
    	    ,<s:property value="imap1.modelFields" />
    	]
    });
    /*/////////////////*/
    ////// modelos //////
    /////////////////////
    
    ////////////////////
    ////// stores //////
    /*////////////////*/
    /*////////////////*/
    ////// stores //////
    ////////////////////
    
    /////////////////////////
    ////// componentes //////
    /*/////////////////////*/
    Ext.define('McdinGrid',
    {
    	extend : 'Ext.grid.Panel'
    	,initComponent : function()
    	{
    		debug('initComponent instance of McdinGrid');
    		Ext.apply(this,
    		{
    			title    : '<s:property value="smap1.gridTitle" />'
    			,columns :
    			[
    			    {
    			    	xtype         : 'checkcolumn'
    			    	,dataIndex    : 'activo'
    			    	,width        : 30
    			    	,menuDisabled : true
    			    	,sortable     : false
    			    }
    			    ,<s:property value="imap1.gridColumns" />
    			]
    			,tbar    :
    			[
    				{
    					text     : 'Nuevo/Agregar'
    					,icon    : '${ctx}/resources/fam3icons/icons/page.png'
    					,handler : mcdinAgregarTramiteManual
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
    mcdinGrid=new McdinGrid();
    mcdinGrid.render('mcdinDivPri');
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
<div id="mcdinDivPri"></div>
</body>
</html>