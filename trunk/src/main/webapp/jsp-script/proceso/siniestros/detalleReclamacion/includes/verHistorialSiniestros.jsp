<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Historial Reclamaci&oacute;n</title>

		<script type="text/javascript">
            
		    var _CONTEXT = '${ctx}';
            
            var _cdPerson =  '<s:property value="params.cdperson" />';
            
            var _URL_cargaHistorialSinies = '<s:url namespace="/siniestros" action="cargaHistorialSiniestros" />';
		            
			Ext.onReady(function() {
		
				Ext.define('ModelHistorial', {
				    extend:'Ext.data.Model',
				    fields:['CDTIPATE','DSTIPATE','FEOCURRE','CDPROVEE','CDROL',
				            'DSPROVEED','CDICD','DESCICD','CDICD2','DESCICD2',
				            'NMSINIES','MONTO_PAGADO','MONTO_GAST_TOT','MONTO_GAST_ANTIC',
				            'MONTO_AUTORIZADO','CDUNIECO','CDRAMO','ESTADO','NMPOLIZA']
				});
				
				var storeHistorial = new Ext.data.Store({
			        model      : 'ModelHistorial',
			        //autoLoad: true,
			        proxy     : {
			        	type        : 'ajax',
			            url         : _URL_cargaHistorialSinies,
			            extraParams : {
			                'params.pv_cdperson_i' : _cdPerson
			            },
			            reader : {
			                type : 'json',
			                root : 'loadList'
			            }
			        }
			    });
				
				// Cargar store
				storeHistorial.load({
                    callback: function(records, operation, success) {
                    	if (!success) {
                            showMessage('Error', 'Error en la consulta, intente m&aacute;s tarde', Ext.Msg.OK, Ext.Msg.ERROR);
                            return;
                        }
                        if(records.length == 0){
                            showMessage('Aviso', 'No se encontraron Siniestros', Ext.Msg.OK, Ext.Msg.INFO);
                            return;
                        }
                    }
                });
				
				Ext.create('Ext.grid.Panel', {
					title      : 'Historial de Reclamaciones',
                	renderTo  : 'maindivHist',
                	height: 200,
                	border: false,
                	defaults  : {
						style : 'margin:5px'
					},
				    store       : storeHistorial,
				    autoScroll: true,
				    collapsible: true,
				    titleCollapse: true,
				    buttonAlign : 'center',
				    columns     : [{
							        header    : 'Tipo de Ateni&oacute;n',
							        dataIndex : 'DSTIPATE'
							        
				    			},{
							        header    : 'Fecha de Reclamo',
							        dataIndex : 'FEOCURRE'
				    			},{
							        header    : 'Proveedor',
							        dataIndex : 'DSPROVEED'
				    			},{
							        header    : 'Diagn&oacute;stico',
							        dataIndex : 'DESCICD'
				    			},{
							        header    : 'N&uacute;mero de Siniestro',
							        dataIndex : 'NMSINIES'
				    			},{
							        header    : 'Monto Pagado',
							        dataIndex : 'MONTO_PAGADO'
				    			},{
							        header    : 'Monto Gastado Total',
							        dataIndex : 'MONTO_GAST_TOT'
				    			},{
							        header    : 'Monto gastado por anticipado',
							        dataIndex : 'MONTO_GAST_ANTIC'
				    			},{
							        header    : 'Monto Autorizado',
							        dataIndex : 'MONTO_AUTORIZADO'
				    			},{
							        header    : 'P&oacute;liza afectada',
							        dataIndex : 'NMPOLIEX'
				    			}]
				});
		            	
		    });
		</script>

    </head>
    <body>
        <div id="maindivHist"></div>
    </body>
</html>