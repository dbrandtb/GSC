<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
///////////////////////
////// variables //////
/*///////////////////*/
var panrehuniUrlDetCanc = '<s:url namespace="/cancelacion"    action="obtenerDetalleCancelacion"/>';
var panrehuniUrlRehab   = '<s:url namespace="/rehabilitacion" action="rehabilitacionUnica"/>';
var panRehInput         = [];
panRehInput['cdunieco'] = '<s:property value="smap1.cdunieco" />';
panRehInput['cdramo']   = '<s:property value="smap1.cdramo"   />';
panRehInput['cdtipsit'] = '<s:property value="smap1.cdtipsit" />';
panRehInput['estado']   = '<s:property value="smap1.estado"   />';
panRehInput['nmpoliza'] = '<s:property value="smap1.nmpoliza" />';
panRehInput['dsunieco'] = '<s:property value="smap1.dsunieco" />';
panRehInput['dsramo']   = '<s:property value="smap1.dsramo"   />';
panRehInput['dstipsit'] = '<s:property value="smap1.dstipsit" />';
panRehInput['nmpoliex'] = '<s:property value="smap1.nmpoliex" />';
debug('panRehInput:',panRehInput);
/*///////////////////*/
////// variables //////
///////////////////////

///////////////////////
////// funciones //////
/*///////////////////*/

/*///////////////////*/
////// funciones //////
///////////////////////

Ext.onReady(function(){
    
    /////////////////////
    ////// modelos //////
    /*/////////////////*/
    Ext.define('PanRehUniDetalleCancelacion',
    {
    	extend  : 'Ext.data.Model'
    	,fields :
    	[
    	    "smap1.CDMOTANU"
    	    ,"smap1.DSMOTANU"
    	    ,{
    	    	name        : 'smap1.FEANULAC'
    	    	,type       : 'date'
    	    	,dateFormat : 'd/m/Y'
    	    }
    	    ,"smap1.DSCANCEL"
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
    /*/////////////////////*/
    ////// componentes //////
    /////////////////////////
    
    ///////////////////////
    ////// contenido //////
    /*///////////////////*/
    Ext.create('Ext.form.Panel',
    {
    	id        : 'panrehuniFormPri'
    	,renderTo : 'panrehuniDivPri'
    	,border   : 0
    	,defaults :
    	{
    		style : 'margin : 5px;'
    	}
    	,items    :
    	[
    	    Ext.create('Ext.panel.Panel',
    	    {
    	    	title     : 'Datos de la p&oacute;liza'
    	    	,border   : 0
    	    	,layout   :
    	        {
    	            type     : 'table'
    	            ,columns : 2
    	        }
    	        ,defaults :
    	        {
    	            style : 'margin : 5px;'
    	        }
    	        ,items    :
    	        [
    	            {
    	                xtype       : 'textfield'
    	                ,readOnly   : true
    	                ,fieldLabel : 'Sucursal'
    	                ,value      : panRehInput['dsunieco']
    	            }
    	            ,{
    	                xtype       : 'textfield'
    	                ,readOnly   : true
    	                ,fieldLabel : 'Producto'
    	                ,value      : panRehInput['dsramo']
    	            }
    	            ,{
    	                xtype       : 'textfield'
    	                ,readOnly   : true
    	                ,fieldLabel : 'Modalidad'
    	                ,value      : panRehInput['dstipsit']
    	            }
    	            ,{
    	                xtype       : 'textfield'
    	                ,readOnly   : true
    	                ,fieldLabel : 'P&oacute;liza'
    	                ,value      : panRehInput['nmpoliex']
    	            }
    	        ]
    	    })
            ,Ext.create('Ext.form.Panel',
            {
            	title     : 'Informaci&oacute;n de la cancelaci&oacute;n'
            	,id       : 'panrehuniFormDetaCanc'
            	,border   : 0
            	,layout   :
            	{
            		type     : 'table'
            		,columns : 2
            	}
            	,defaults :
            	{
            		style : 'margin : 5px;'
            	}
            	,items    :
            	[
            	    {
            	    	xtype       : 'textfield'
            	    	,fieldLabel : 'Motivo de cancelaci&oacute;n'
            	    	,readOnly   : true
            	    	,name       : 'smap1.DSMOTANU'
            	    }
            	    ,{
            	    	xtype       : 'datefield'
            	    	,fieldLabel : 'Fecha de cancelaci&oacute;n'
            	    	,readOnly   : true
            	    	,name       : 'smap1.FEANULAC'
            	    	,format     : 'd M Y'
            	    }
            	    ,{
            	    	xtype       : 'textarea'
            	    	,fieldLabel : 'Comentarios de cancelaci&oacute;n'
            	    	,readOnly   : true
            	    	,colspan    : 2
            	    	,width      : 520
            	    	,name       : 'smap1.DSCANCEL'
            	    }
            	]
            })
            ,Ext.create('Ext.form.Panel',
            {
            	title        : 'Informaci&oacute;n de rehabilitaci&oacute;n'
            	,buttonAlign : 'center'
            	,url         : panrehuniUrlRehab
            	,defaults    :
            	{
            	    style : 'margin : 5px;'
                }
            	,items       : 
            	[
            	    {
            	    	xtype       : 'datefield'
            	    	,format     : 'm/d/Y'
            	    	,fieldLabel : 'Fecha de rehabilitaci&oacute;n'
            	    	,name       : 'smap1.ferehabi'
            	    	,value      : new Date()
            	    	,allowBlank : false
            	    }
            	    ,{
            	    	xtype       : 'textarea'
            	    	,fieldLabel : 'Comentarios'
            	    	,width      : 520
            	    	,name       : 'smap1.dscancel'
            	    }
            	]
            	,buttonAlign : 'center'
            	,buttons     :
            	[
            	    {
            	    	text     : 'Confirmar rehabilitaci&oacute;n'
            	    	,icon    : '${ctx}/resources/fam3icons/icons/key.png'
            	    	,handler : function()
            	    	{
            	    		debug('confirmar rehabilitacion');
            	    		var form=this.up().up();
            	    		if(form.isValid())
            	    		{
            	    			form.setLoading(true);
            	    			form.submit(
            	    			{
        	    					params   :
                                    {
                                        'smap1.cdunieco'  : panRehInput['cdunieco']
                                        ,'smap1.cdramo'   : panRehInput['cdramo']
                                        ,'smap1.cdtipsit' : panRehInput['cdtipsit']
                                        ,'smap1.estado'   : panRehInput['estado']
                                        ,'smap1.nmpoliza' : panRehInput['nmpoliza']
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
                                                ,msg     : 'Error al rehabilitar la p&oacute;liza'
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
       	                            title    : 'Datos incompletos'
       	                            ,icon    : Ext.Msg.WARNING
       	                            ,msg     : 'Favor de llenar los campos requeridos'
       	                            ,buttons : Ext.Msg.OK
       	                        });
            	    		}
            	    	}
            	    }
            	]
            })
        ]
    });
    /*///////////////////*/
    ////// contenido //////
    ///////////////////////

    //////////////////////
    ////// cargador //////
    /*//////////////////*/
    Ext.define('LoaderDetalleCancelacion',
    {
        extend :'PanRehUniDetalleCancelacion'
        ,proxy :
        {
            extraParams :
            {
            	'smap1.pv_cdunieco_i'  : panRehInput['cdunieco']
                ,'smap1.pv_cdramo_i'   : panRehInput['cdramo']
                ,'smap1.pv_estado_i'   : panRehInput['estado']
                ,'smap1.pv_nmpoliza_i' : panRehInput['nmpoliza']
            }
            ,type   : 'ajax'
            ,url    : panrehuniUrlDetCanc
            ,reader :
            {
            	type : 'json'
            }
        }
    });

    var loaderDetalleCancelacion=Ext.ModelManager.getModel('LoaderDetalleCancelacion');
    loaderDetalleCancelacion.load(123,
    {
        success  : function(resp)
        {
            debug(resp);
            Ext.getCmp('panrehuniFormDetaCanc').loadRecord(resp);
        }
        ,failure : function()
        {
            Ext.Msg.show(
            {
                title    : 'Error'
                ,icon    : Ext.Msg.ERROR
                ,msg     : 'Error al cargar'
                ,buttons : Ext.Msg.OK
            });
        }
    });
    /*//////////////////*/
    ////// cargador //////
    //////////////////////
    
});
    
</script>
<div id="panrehuniDivPri"></div>