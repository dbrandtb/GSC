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
				    fields:['TRAMITE',		'CONTRARECIBO',		'TIPOPAGO',				'CDTIPATE',
							'TIPOATENCION',	'STATUS',			'FACTURA',				'POLIZA',
							'NOSINIES',		'DSPROVEED',		'DESCICD',				'DESCICD2',
							'IMPFACTURA',	'IMPORTEPAGADO']
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
							        header    : 'Tr&aacute;mite',
							        dataIndex : 'TRAMITE'
							        
				    			},{
							        header    : 'Contra Recibo',
							        dataIndex : 'CONTRARECIBO'
				    			}
				    			,{
							        header    : 'Estatus',
							        dataIndex : 'STATUS'
				    			}
				    			,
				    			{
							        header    : 'Tipo Pago',
							        dataIndex : 'TIPOPAGO'
				    			},
				    			{
							        header    : 'Tipo atenci&oacute;n',
							        dataIndex : 'TIPOATENCION'
				    			},
				    			{
							        header    : 'Factura',
							        dataIndex : 'FACTURA'
				    			},
				    			{
							        header    : 'P&oacute;liza',
							        dataIndex : 'POLIZA'
				    			},
				    			{
							        header    : 'No. Siniestro',
							        dataIndex : 'NOSINIES'
				    			},
				    			{
							        header    : 'Proveedor',
							        dataIndex : 'DSPROVEED'
				    			},
				    			{
							        header    : 'Diagn&oacute;stico',
							        dataIndex : 'DESCICD'
				    			},
				    			{
							        header    : 'Diagn&oacute;stico',
							        dataIndex : 'DESCICD2'
				    			},
				    			{
							        header    : 'Importe Factura',
							        dataIndex : 'IMPFACTURA',
							        renderer  : Ext.util.Format.usMoney
				    			},
				    			{
							        header    : 'Importe Pagado',
							        dataIndex : 'IMPORTEPAGADO',
							        renderer  : Ext.util.Format.usMoney
				    			}
				    			
				    			]
				});
		            	
		    });
		</script>

    </head>
    <body>
        <div id="maindivHist"></div>
    </body>
</html>