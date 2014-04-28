<%@ include file="/taglibs.jsp"%>
<%@ page language="java" %>
<script type="text/javascript">

//URLS:
var _URL_OBTENCION_REPORTE = '<s:url namespace="/reportes" action="procesoObtencionReporte" />';

var cdreporte =  '<s:property value="cdreporte" />';

debug("cdreporte",cdreporte);

Ext.onReady(function(){
    Ext.create('Ext.form.Panel', {
        renderTo : 'dvComponentesReporte',
        url      : _URL_OBTENCION_REPORTE,
        title    : 'Par&aacute;metros de b&uacute;squeda:',
        defaults : {
            style: 'margin:5px'
        },
        border   : false,
        items : [
            <s:property value="params.items" />,
            {
                xtype : 'button',
                text  : 'Consultar',
                handler: function(btn, e) {
                    
                	var formCmpRep = this.up('form').getForm();
                    debug(formCmpRep.getValues());
                	
                	if (formCmpRep.isValid()) {
                        
                        //Iteramos los fields del formulario para buscar los del tipo datefield
                        Ext.Array.each(formCmpRep.getFields().items, function(item){
                        	debug(item);
                        });
                        
                        formCmpRep.submit({
                        	standardSubmit : true,
                            params: {
                                cdreporte : cdreporte
                            },
                            success: function(form, action) {
                                
                            },
                            failure: function(form, action) {
                                switch (action.failureType) {
                                    case Ext.form.action.Action.CONNECT_FAILURE:
                                        Ext.Msg.alert('Error', 'Error de comunicaci&oacute;n');
                                        break;
                                    case Ext.form.action.Action.SERVER_INVALID:
                                    case Ext.form.action.Action.LOAD_FAILURE:
                                        Ext.Msg.alert('Error', 'Error del servidor, consulte a soporte');
                                        break;
                               }
                            }
                        });
                	} else {
                		Ext.Msg.show({
                            title: 'Aviso',
                            msg: 'Complete la informaci&oacute;n requerida',
                            buttons: Ext.Msg.OK,
                            animateTarget: btn,
                            icon: Ext.Msg.WARNING
                        });
                	}
                    
                    
                }
            }
        ]
    });
});
</script>
<div id="dvComponentesReporte"/>