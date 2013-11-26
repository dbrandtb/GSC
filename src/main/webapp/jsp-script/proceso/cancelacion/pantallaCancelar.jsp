<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
    ///////////////////////
    ////// variables //////
    /*///////////////////*/
    var panCanUrlCat        = '<s:url namespace="/flujocotizacion"  action="cargarCatalogos" />';
    var panCanUrlCancelar   = '<s:url namespace="/cancelacion"      action="cancelacionUnica" />';
    var panCanInput         = [];
    panCanInput['cdunieco'] = '<s:property value="smap1.cdunieco" />';
    panCanInput['cdramo']   = '<s:property value="smap1.cdramo"   />';
    panCanInput['cdtipsit'] = '<s:property value="smap1.cdtipsit" />';
    panCanInput['estado']   = '<s:property value="smap1.estado"   />';
    panCanInput['nmpoliza'] = '<s:property value="smap1.nmpoliza" />';
    panCanInput['dsunieco'] = '<s:property value="smap1.dsunieco" />';
    panCanInput['dsramo']   = '<s:property value="smap1.dsramo"   />';
    panCanInput['dstipsit'] = '<s:property value="smap1.dstipsit" />';
    panCanInput['nmpoliex'] = '<s:property value="smap1.nmpoliex" />';
    debug('panCanInput:',panCanInput);
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
    /*/////////////////////*
    Ext.define('Panel1',{
        extend:'Ext.panel.Panel',
        title:'Objeto asegurado',
        layout:{
            type:'column',
            columns:2
        },
        frame:false,
        style:'margin:5px;',
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
    	renderTo     : 'panCanDivPri'
    	,id          : 'panCanFormPri'
    	,url         : panCanUrlCancelar
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
    	    {
    	    	xtype       : 'textfield'
    	    	,fieldLabel : 'Sucursal'
    	    	,readOnly   : true
    	    	,value      : panCanInput['dsunieco']
    	    }
    	    ,{
    	        xtype       : 'textfield'
                ,fieldLabel : 'Producto'
                ,readOnly   : true
                ,value      : panCanInput['dsramo']
            }
    	    ,{
                xtype       : 'textfield'
                ,fieldLabel : 'Modalidad'
                ,readOnly   : true
                ,value      : panCanInput['dstipsit']
            }
    	    ,{
                xtype       : 'textfield'
                ,fieldLabel : 'P&oacute;liza'
                ,readOnly   : true
                ,value      : panCanInput['nmpoliex']
            }
    	    ,Ext.create('Ext.form.field.ComboBox',
	        {
	            fieldLabel      : 'Motivo de cancelaci&oacute;n'
	            ,name           : 'smap1.motivo'
	            ,displayField   : 'value'
	            ,valueField     : 'key'
	            ,allowBlank     : false
	            ,forceSelection : true
	            ,queryMode      : 'local'
	            ,store          : Ext.create('Ext.data.Store',
	            {
	                model     : 'Generic'
	                ,autoLoad : true
	                ,proxy    :
	                {
	                    type         : 'ajax'
	                    ,url         : panCanUrlCat
	                    ,extraParams : {catalogo:'<s:property value="CON_CAT_CANCELA_MOTIVOS" />'}
	                    ,reader      :
	                    {
	                        type  : 'json'
	                        ,root : 'lista'
	                    }
	                }
	            })
	        })
	        ,{
    	    	xtype       : 'datefield'
    	    	,name       : 'smap1.fecha'
    	    	,fieldLabel : 'Fecha de cancelaci&oacute;n'
    	    	,format     : 'd/m/Y'
    	    	,value      : new Date()
    	    	,allowBlank : false
    	    }
    	    ,{
    	    	xtype       : 'textarea'
    	    	,name       : 'smap1.comentarios'
    	    	,fieldLabel : 'Comentarios'
    	    	,allowBlank : true
    	    	,colspan    : 2
    	    	,width      : 450
    	    }
    	]
    	,buttonAlign : 'center'
    	,buttons     :
    	[
    	    {
    	    	text     : 'Confirmar cancelaci&oacute;n'
    	    	,icon    : '${ctx}/resources/fam3icons/icons/key.png'
    	    	,handler : function()
    	    	{
    	    		var form=this.up().up();
    	    		if(form.isValid())
    	    		{
    	    			form.setLoading(true);
    	    			form.submit(
    	    			{
    	    				params   :
    	    				{
    	    					'smap1.cdunieco'  : panCanInput['cdunieco']
    	    			        ,'smap1.cdramo'   : panCanInput['cdramo']
    	    			        ,'smap1.cdtipsit' : panCanInput['cdtipsit']
    	    			        ,'smap1.estado'   : panCanInput['estado']
    	    			        ,'smap1.nmpoliza' : panCanInput['nmpoliza']
    	    				}
    	    				,success : function(formu,action)
                            {
    	    					form.setLoading(false);
                                debug(action);
                                var json = Ext.decode(action.response.responseText);
                                debug(json);
                                if(json.success==true)
                                {
                                    debug('ok');
                                }
                                else
                                {
                                    Ext.Msg.show(
                                    {
                                        title    : 'Error'
                                        ,msg     : 'Error al cancelar la p&oacute;liza'
                                        ,icon    : Ext.Msg.ERROR
                                        ,buttons : Ext.Msg.OK
                                    });
                                }
                            }
                            ,failure : function()
                            {
                            	form.setLoading(false);
                                Ext.Msg.show(
                                {
                                    title   : 'Error',
                                    icon    : Ext.Msg.ERROR,
                                    msg     : 'Error de comunicaci&oacute;n',
                                    buttons : Ext.Msg.OK
                                });
                            }
    	    			});
    	    		}
    	    		else
    	    		{
    	    			Ext.Msg.show(
                        {
                            title   : 'Datos incompletos',
                            icon    : Ext.Msg.WARNING,
                            msg     : 'Favor de llenar los campos requeridos',
                            buttons : Ext.Msg.OK
                        });
    	    		}
    	    	}
    	    }
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
    
});
</script>
<div id="panCanDivPri"></div>