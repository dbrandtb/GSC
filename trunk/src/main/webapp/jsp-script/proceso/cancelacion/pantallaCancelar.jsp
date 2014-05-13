<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
    ////// variables //////
    //var panCanUrlCat        = '<s:url namespace="/flujocotizacion"  action="cargarCatalogos" />';
    var panCanUrlCat        = '<s:url namespace="/catalogos"        action="obtieneCatalogo" />';
    var panCanUrlCancelar   = '<s:url namespace="/cancelacion"      action="cancelacionUnica" />';
    var panCanInputFecha;
    var panCanForm;
    
    // Obtenemos el contenido en formato JSON de la propiedad solicitada:
    var pancanInSmap1=<s:property value="%{convertToJSON('smap1')}" escapeHtml="false" />;
    debug('pancanInSmap1',pancanInSmap1);
    ////// variables //////
    
    ////// funciones //////
    function comboMotivocambio(combo,nue,ant)
    {
    	debug('>comboMotivocambio:',combo,ant,nue);
    	if(nue=='22')
    	{
    		debug(22);
    		panCanInputFecha.setValue(new Date());
    		panCanInputFecha.setReadOnly(true);
    	}
    	else if(nue=='24')
    	{
    		debug(24);
    		panCanInputFecha.setValue(new Date());
            panCanInputFecha.setReadOnly(true);
            panCanForm.setLoading(true);
            Ext.Ajax.request(
       	    {
       	        url       : _global_urlConsultaDinamica
       	        ,jsonData :
       	        {
       	             stringMap :
       	             {
       	                 accion : Accion.ValidaCancelacionProrrata           
       	             }
       	             ,linkedObjectMap :
       	             {
       	            	 param1  : pancanInSmap1.CDUNIAGE
       	            	 ,param2 : pancanInSmap1.CDRAMO
       	            	 ,param3 : pancanInSmap1.ESTADO
       	            	 ,param4 : pancanInSmap1.NMPOLIZA
       	             }
       	         }
       	        ,success  : function(response)
       	        {
       	        	panCanForm.setLoading(false);
       	            jsonData = Ext.decode(response.responseText);
       	            if(jsonData.success)
       	            {
       	            }
       	            else
       	            {
       	                mensajeError(jsonData.mensaje);
       	                combo.setValue('22');
       	            }
       	        }
       	        ,failure  : function()
       	        {
       	        	panCanForm.setLoading(false);
       	            errorComunicacion();
       	            combo.setValue('22');
       	        }
       	    });
    	}
    	else if(nue=='25')
    	{
    		debug(25);
    		panCanInputFecha.setValue(new Date());
            panCanInputFecha.setReadOnly(false);
            panCanForm.setLoading(true);
            Ext.Ajax.request(
            {
                url       : _global_urlConsultaDinamica
                ,jsonData :
                {
                     stringMap :
                     {
                         accion : Accion.ValidaCancelacionProrrata           
                     }
                     ,linkedObjectMap :
                     {
                         param1  : pancanInSmap1.CDUNIAGE
                         ,param2 : pancanInSmap1.CDRAMO
                         ,param3 : pancanInSmap1.ESTADO
                         ,param4 : pancanInSmap1.NMPOLIZA
                     }
                 }
                ,success  : function(response)
                {
                	panCanForm.setLoading(false);
                    jsonData = Ext.decode(response.responseText);
                    if(jsonData.success)
                    {
                    }
                    else
                    {
                        mensajeError(jsonData.mensaje);
                        combo.setValue('22');
                    }
                }
                ,failure  : function()
                {
                	panCanForm.setLoading(false);
                    errorComunicacion();
                    combo.setValue('22');
                }
            });
    	}
    	debug('<comboMotivocambio');
    }
    ////// funciones //////
    
Ext.onReady(function()
{
    
    ////// modelos //////
    ////// modelos //////
    
    ////// stores //////
    ////// stores //////
    
    ////// componentes //////
    ////// componentes //////
    
    ////// contenido //////
    panCanForm = Ext.create('Ext.form.Panel',
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
    	    <s:property value="imap.itemsMarcocancelacionModelocandidata" />
    	]
    	,buttonAlign : 'center'
    	,buttons     :
    	[
    	    {
    	    	text     : 'Confirmar cancelaci&oacute;n'
    	    	,icon    : '${ctx}/resources/fam3icons/icons/key.png'
    	    	,handler : function()
    	    	{
    	    		var boton=this;
    	    		var form=this.up().up();
    	    		if(form.isValid())
    	    		{
    	    			form.setLoading(true);
    	    			form.submit(
    	    			{
    	    				success : function(formu,action)
                            {
    	    					form.setLoading(false);
                                debug(action);
                                var json = Ext.decode(action.response.responseText);
                                debug(json);
                                if(json.success==true)
                                {
                                    debug('ok');
                                    Ext.Msg.show({
                                        title    : 'Cancelaci&oacute;n exitosa'
                                        ,msg     : 'Se ha cancelado la p&oacute;liza'
                                        ,buttons : Ext.Msg.OK
                                    });
                                    boton.hide();
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
    
    var comboMotivoCanc = panCanForm.items.items[6];
    debug('comboMotivoCanc:',comboMotivoCanc);
    panCanInputFecha = panCanForm.items.items[9];
    debug('panCanInputFecha:',panCanInputFecha);
    comboMotivoCanc.addListener('change',comboMotivocambio);
    comboMotivocambio(comboMotivoCanc,'22');
    ////// contenido //////
    
    ////// cargador //////
    ////// cargador //////
    
});
</script>
<div id="panCanDivPri"></div>