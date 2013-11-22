<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Cancelar</title>
<script>
    ///////////////////////
    ////// variables //////
    /*///////////////////*/
    var marcanurlcata      = '<s:url namespace="/flujocotizacion" action="cargarCatalogos" />';
    var marcanurlramos     = '<s:url namespace="/"                action="obtenerRamos" />';
    var marcanStorePolizas;
    /*///////////////////*/
    ////// variables //////
    ///////////////////////
    
    ///////////////////////
    ////// funciones //////
    /*///////////////////*/
    function marcanMostrarControlesFiltro(tipo)
    {
    	debug('marcanMostrarControlesFiltro',tipo);
    	if(tipo==1)
    	{
	    	Ext.getCmp('marcanFilUnieco').show();
	        Ext.getCmp('marcanFilRamo').show();
	        Ext.getCmp('marcanFilEstado').show();
	        Ext.getCmp('marcanFilNmpoliza').show();
	        Ext.getCmp('marcanFilBotGen').show();
	        
	        Ext.getCmp('marcanFilBotPol').hide();
	        Ext.getCmp('marcanFilNmpoliex').hide();
    	}
    	else if (tipo==2)
    	{
    		Ext.getCmp('marcanFilUnieco').hide();
            Ext.getCmp('marcanFilRamo').hide();
            Ext.getCmp('marcanFilEstado').hide();
            Ext.getCmp('marcanFilNmpoliza').hide();
            Ext.getCmp('marcanFilBotGen').hide();
            
            Ext.getCmp('marcanFilBotPol').show();
            Ext.getCmp('marcanFilNmpoliex').show();
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
    Ext.define('Ramo',
    {
        extend  : 'Ext.data.Model'
        ,fields :
        [
            "cdramo"
            ,"dsramo"
        ]
    });
    
    Ext.define('MarCanPoliza',
    {
        extend : 'Ext.data.Model'
    });
    /*/////////////////*/
    ////// modelos //////
    /////////////////////
    
    ////////////////////
    ////// stores //////
    /*////////////////*/
    marcanStorePolizas=Ext.create('Ext.data.Store',
    {
        pageSize  : 10
        ,autoLoad : true
        ,model    : 'MarCanPoliza'
        ,proxy    :
        {
            enablePaging  : true
            ,reader       : 'json'
            ,type         : 'memory'
            ,data         : []
        }
    });
    /*////////////////*/
    ////// stores //////
    ////////////////////
    
    /////////////////////////
    ////// componentes //////
    /*/////////////////////*
    Ext.define('Panel1',{
        extend:'Ext.panel.Panel',
        title:'Objeto asegurado',
        layout:{
            type:'column',
            columns:2
        },
        frame:false,
        style:'margin : 5px;',
        collapsible:true,
        titleCollapse:true,
        <s:property value="item2" />
    });
    Ext.define('FormPanel',{
        extend:'Ext.form.Panel',
        renderTo:'maindiv',
        frame:false,
        buttonAlign:'center',
        items:[
            new Panel1()
        ]
    });
    /*/////////////////////*/
    ////// componentes //////
    /////////////////////////
    
    ///////////////////////
    ////// contenido //////
    /*///////////////////*/
    Ext.create('Ext.form.Panel',
    {
    	renderTo  : 'marcoCancelacionDiv'
    	,title    : 'Cancelaci&oacute;n'
    	,defaults :
    	{
    		style : 'margin : 5px'
    	}
    	,items    :
    	[
    	    Ext.create('Ext.form.Panel',
    	    {
    	    	title          : 'B&uacute;squeda'
    	    	,width         : 1000
    	    	,layout        :
    	    	{
    	    		type     : 'table'
    	    		,columns : 3
    	    	}
    	    	,defaults      :
    	    	{
    	    		style : 'margin:5px;'
    	    	}
    	    	,collapsible   : true
    	    	,titleCollapse : true
    	    	,buttonAlign   : 'center'
    	    	,frame         : true
    	    	,tbar          :
    	    	[
    	    		{
    	    			text  : 'Tipo de b&uacute;squeda'
    	    			,icon : '${ctx}/resources/fam3icons/icons/cog.png'
   	    				,menu :
   	                    {
   	                        xtype     : 'menu'
   	                        ,items    :
   	                        [
   	                            {
   	                                text     : 'General'
   	                                ,handler : function(){marcanMostrarControlesFiltro(1);}
   	                            }
   	                            ,{
   	                                text     : 'P&oacute;liza'
	                                ,handler : function(){marcanMostrarControlesFiltro(2);}
   	                            }
   	                        ]
   	                    }
    	    		}
    	    	]
    	    	,items         :
    	    	[
    	    	    {
			        	xtype           : 'combo'
			        	,id             : 'marcanFilUnieco'
			            ,fieldLabel     : 'Sucursal'
			            ,name           : 'smap1.pv_cdunieco_i'
			            ,displayField   : 'value'
			            ,valueField     : 'key'
			            ,allowBlank     : false
			            ,forceSelection : true
			            ,queryMode      :'local'
			            ,store          : Ext.create('Ext.data.Store',
			            {
			                model     : 'Generic'
			                ,autoLoad : true
			                ,proxy    :
			                {
			                    type         : 'ajax'
			                    ,url         : marcanurlcata
			                    ,extraParams : {catalogo:'<s:property value="CON_CAT_MESACONTROL_SUCUR_DOCU" />'}
			                    ,reader      :
			                    {
			                        type  : 'json'
			                        ,root : 'lista'
			                    }
			                }
			            })
			            ,listeners      :
			            {
			            	'change' : function()
			            	{
			            		Ext.getCmp('marcanFilRamo').getStore().load(
			            		{
			            			params : {'map1.cdunieco':this.getValue()}
			            		});
			            	}
			            }
			        }
			        ,{
			        	xtype           : 'combo'
                        ,id             : 'marcanFilRamo'
                        ,fieldLabel     : 'Producto'
                        ,name           : 'smap1.pv_cdramo_i'
                        ,valueField     : 'cdramo'
                        ,displayField   : 'dsramo'
                        ,allowBlank     : false
                        ,forceSelection : true
                        ,queryMode      :'local'
                        ,store          : Ext.create('Ext.data.Store',
                        {
                            model     : 'Ramo'
                            ,autoLoad : false
                            ,proxy    :
                            {
                                type    : 'ajax'
                                ,url    : marcanurlramos
                                ,reader :
                                {
                                    type  : 'json'
                                    ,root : 'slist1'
                                }
                            }
                        })
                    }
			        ,{
                        xtype           : 'combo'
                        ,id             : 'marcanFilEstado'
                        ,fieldLabel     : 'Estado'
                        ,name           : 'smap1.pv_estado_i'
                        ,displayField   : 'value'
                        ,valueField     : 'key'
                       	,allowBlank     : false
                        ,forceSelection : true
                        ,queryMode      :'local'
                        ,store          : Ext.create('Ext.data.Store',
                        {
                            model     : 'Generic'
                            ,autoLoad : true
                            ,proxy    :
                            {
                                type         : 'ajax'
                                ,url         : marcanurlcata
                                ,extraParams : {catalogo:'<s:property value="CON_CAT_POL_ESTADO" />'}
                                ,reader      :
                                {
                                    type  : 'json'
                                    ,root : 'lista'
                                }
                            }
                        })
                    }
			        ,{
			        	xtype       : 'numberfield'
			        	,id         : 'marcanFilNmpoliza'
			        	,name       : 'smap1.pv_nmpoliza_i'
			        	,fieldLabel : 'P&oacute;liza/Cotizaci&oacute;n'
			        	,allowBlank : false
			        }
			        ,{
                        xtype       : 'textfield'
                        ,id         : 'marcanFilNmpoliex'
                        ,name       : 'smap1.pv_nmpoliex_i'
                        ,fieldLabel : 'N&uacute;mero de p&oacute;liza'
                        ,allowBlank : false
                    }
    	    	]
    	    	,buttons       :
    	    	[
    	    		{
    	    			text  : 'B&uacute;squeda general'
    	    			,id   : 'marcanFilBotGen'
    	    			,icon : '${ctx}/resources/fam3icons/icons/zoom.png'
    	    		}
    	    		,{
    	    			text    : 'B&uacute;squeda por p&oacute;liza'
                        ,id     : 'marcanFilBotPol'
                        ,icon   : '${ctx}/resources/fam3icons/icons/zoom.png'
    	    		}
    	    	]
    	    })
    	    ,Ext.create('Ext.grid.Panel',
    	    {
    	    	title          : 'Hist&oacute;rico de movimientos'
   	    		,width         : 1000
    	    	,collapsible   : true
    	    	,titleCollapse : true
    	    	,height        : 150
    	    	,store         : marcanStorePolizas
    	    	,frame         : true
    	    	,columns       :
    	    	[
    	    	    {
    	    	    	header : 'Col 1'
    	    	    }
    	    	]
    	    })
    	    ,Ext.create('Ext.panel.Panel',
    	    {
    	    	layout    :
    	    	{
    	    		type     : 'table'
    	    		,columns : 2
    	    	}
    	        ,border   : 0
    	    	,items    : 
    	    	[
    	    	    Ext.create('Ext.panel.Panel',
    	    	    {
    	    	    	title      : 'Operaciones'
    	    	    	,style     : 'margin-right : 5px;'
    	    	    	,width     : 150
    	    	    	,minHeight : 150
    	    	    	,frame     : true
    	    	    })
    	    	    ,Ext.create('Ext.panel.Panel',
    	    	    {
    	    	    	frame      : true
    	    	    	,width     : 845
    	    	    	,minHeight : 150
    	    	    })
    	    	]
    	    })
    	]
    });
    /*///////////////////*/
    ////// contenido //////
    ///////////////////////
    
    //////////////////////
    ////// cargador //////
    /*//////////////////*
    Ext.define('LoaderForm',
    {
        extend:'Modelo1',
        proxy:
        {
            extraParams:{
            },
            type:'ajax',
            url : urlCargar,
            reader:{
                type:'json'
            }
        }
    });

    var loaderForm=Ext.ModelManager.getModel('LoaderForm');
    loaderForm.load(123, {
        success: function(resp) {
            //console.log(resp);
            formPanel.loadRecord(resp);
        },
        failure:function()
        {
            Ext.Msg.show({
                title:'Error',
                icon: Ext.Msg.ERROR,
                msg: 'Error al cargar',
                buttons: Ext.Msg.OK
            });
        }
    });
    /*//////////////////*/
    ////// cargador //////
    //////////////////////
    
    marcanMostrarControlesFiltro(1);
    
});
</script>
</head>
<body>
<div id="marcoCancelacionDiv"></div>
</body>
</html>