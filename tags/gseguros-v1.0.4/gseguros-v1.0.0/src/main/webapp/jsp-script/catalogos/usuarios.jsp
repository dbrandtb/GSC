<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Admin Usuarios</title>
<script>
///////////////////////
////// variables //////
/*///////////////////*/
var busquedaUsuariosStore;

var _UrlBusquedaUsuarios    = '<s:url namespace="/catalogos"    action="busquedaUsuarios" />';
var _windowAgregarUsuario			= '<s:url namespace="/catalogos"    action="agregaUsuarios" />';
var _UrlAgregarUsuario      = '<s:url namespace="/catalogos"    action="guardaUsuario" />';
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
	Ext.define('gridUsuariosModel',
	{
		extend : 'Ext.data.Model'
		,fields :
		[
            <s:property value="fields" />
		]
	});
	/*/////////////////*/
	////// modelos //////
	/////////////////////
	
	////////////////////
	////// stores //////
	/*////////////////*/
	busquedaUsuariosStore = Ext.create('Ext.data.Store',
    {
		pageSize : 20,
        autoLoad : true
        ,model   : 'gridUsuariosModel'
        ,proxy   :
        {
            type         : 'memory'
            ,enablePaging : true
            ,reader      : 'json'
            ,data        : []
        }
    });
	/*////////////////*/
	////// stores //////
	////////////////////
	
	/////////////////////////
	////// componentes //////
	/*/////////////////////*/
	/*/////////////////////*/
	////// componentes //////
	/////////////////////////
	
	///////////////////////
	////// contenido //////
	/*///////////////////*/
	Ext.create('Ext.panel.Panel',
	{
		defaults :
		{
			style : 'margin : 5px;'
		}
	    ,renderTo : 'mainDiv'
	    ,items :
	    [
	        Ext.create('Ext.form.Panel',
	        {
	        	title : 'B&uacute;squeda de Usuarios'
	        	,layout :
	        		{
	        		type : 'table'
	        		,columns : 2
	        		}
	        	,items :
	        	[
	        	    <s:property value="items" />
	        	]
	        	,buttonAlign: 'center'
	        	,buttons       :
	                [
	                    {
	                        text     : 'Buscar'
	                        ,icon    : '${ctx}/resources/fam3icons/icons/zoom.png'
	                        ,handler : function()
	                        {
	                        	var form=this.up().up();
	                            if(form.isValid())
	                            {
	                            	form.setLoading(true);
	                            	Ext.getCmp('usuariosbbar').moveFirst();
	                            	
	                            	cargaStorePaginadoLocal(busquedaUsuariosStore, 
	                            			_UrlBusquedaUsuarios, 
	                                        'usuarios', 
	                                        this.up().up().getValues(), 
	                                        function (options, success, response){
	                            		
	                            		if(success){
	                                        var jsonResponse = Ext.decode(response.responseText);
	                                        if(jsonResponse.usuarios && jsonResponse.usuarios.length == 0) {
	                                            showMessage(_MSG_SIN_DATOS, _MSG_BUSQUEDA_SIN_DATOS, Ext.Msg.OK, Ext.Msg.INFO);
	                                            return;
	                                        }
	                                        
	                                    }else{
	                                        showMessage('Error', 'Error al obtener los datos.', 
	                                            Ext.Msg.OK, Ext.Msg.ERROR);
	                                    }
	                            		form.setLoading(false);
	                            	});
	                            	
	                            }
	                            else
	                            {
	                                Ext.Msg.show(
	                                {
	                                    title    : 'Datos imcompletos'
	                                    ,icon    : Ext.Msg.WARNING
	                                    ,msg     : 'Favor de llenar los campos requeridos'
	                                    ,buttons : Ext.Msg.OK
	                                });
	                            }
	                        }
	                    },
	                    {
		                    text     : 'Limpiar'
		                    ,icon    : '${ctx}/resources/fam3icons/icons/control_repeat_blue.png'
		                    ,handler : function()
		                    {
		                    	this.up().up().getForm().reset();
		                    }
		                }
	                ]
	        })
	        ,Ext.create('Ext.grid.Panel',
	        {
	        	title : 'Grid'
	        	,height : 250
	        	,store : busquedaUsuariosStore
	        	,columns :
	        	[
                    <s:property value="columns" />
	        	]
	        	,bbar     :
	            {
	                displayInfo : true,
	                store       : busquedaUsuariosStore,
	                xtype       : 'pagingtoolbar',
	                id: 'usuariosbbar'
	                
	            },
	            tbar: [{
                    icon    : '${ctx}/resources/fam3icons/icons/add.png',
                    text    : 'Agregar usuario',
                    scope   : this,
                    handler : function()
                    {
                		Ext.create('Ext.window.Window',
                        {
                            title        : 'Agregar Usuario'
                            ,modal       : true
                            ,buttonAlign : 'center'
                            ,width       : 600
                            ,height      : 400
                            ,autoScroll  : true
                            ,loader      :
                            {
                                url       : _windowAgregarUsuario
                                ,scripts  : true
                                ,autoLoad : true
                            }
                        }).show();
                    }
                },{
                    icon    : '${ctx}/resources/fam3icons/icons/delete.png',
                    text    : 'Eliminar usuario',
                    scope   : this,
                    handler : function (btn, e){
                    	var model =  btn.findParentByType('gridpanel').getSelectionModel();
                    	if(model.hasSelection()){
                    		alert(model.getLastSelected().get('cdUsuario'));
                    	}else{
                    		showMessage("Aviso","Debe seleccionar un registro", Ext.Msg.OK, Ext.Msg.INFO);
                    	}
                    }
                }]
	        })
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
<div id="mainDiv" style="height:1500px;"></div>
</body>
</html>