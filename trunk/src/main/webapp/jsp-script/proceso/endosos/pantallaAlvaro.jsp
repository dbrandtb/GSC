<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Alvaro</title>
<script>
Ext.onReady(function()
{
	Ext.create('Ext.panel.Panel',
	{
		title     : 'Cargador de pantallas'
		,renderTo : Ext.getBody()
		,defaults :
		{
			style : 'margin : 5px;'
		}
		,items    :
		[
            Ext.create('Ext.form.Panel',
		    {
		        title        : 'Filtro'
		        ,layout      :
		        {
		            type     : 'table'
		            ,columns : 2
		        }
		        ,defaults    :
		        {
		            style : 'margin : 5px;'
		        }
		        ,items       :
		        [
		            <s:property value="item1" />
		        ]
		        ,buttonAlign : 'center'
		        ,buttons     :
		        [
		            {
		            	text     : 'Cargar'
		            	,icon    : '${ctx}/resources/fam3icons/icons/lightning.png'
		            	,handler : function()
		            	{
		            		var me=this;
		            		var form=me.up().up();
		            		if(form.isValid())
		            		{
		            			debug(form.getValues());
		            			Ext.getCmp('vispanLoaderFrame').getLoader().load(
                                {
                                    url       : '<s:url namespace="/endosos" action="visorPantallas" />'
                                    ,scripts  : true
                                    ,autoLoad : true
                                    ,params   : form.getValues()
                                });
		            		}
		            	}
		            }
		            ,{
		            	text     : 'Limpiar'
		            	,icon    : '${ctx}/resources/fam3icons/icons/control_repeat_blue.png'
		            	,handler : function()
		            	{
		            		this.up().up().getForm().reset();
		            	}
		            }
		            ,{
		            	text     : 'Reabrir'
		            	,icon    : '${ctx}/resources/fam3icons/icons/connect.png'
		            	,handler : function()
		            	{
		            		Ext.create('Ext.form.Panel').submit({standardSubmit:true});
		            	}
		            }
		        ]
		    })
            ,Ext.create('Ext.panel.Panel',
            {
	            frame      : true
	            ,id        : 'vispanLoaderFrame'
	            ,height    : 1000
	            ,loader    :
	            {
	                autoLoad: false
	            }
	        })
		]
	});
	
	
	
}); 
</script>
</head>
<body>
</body>
</html>