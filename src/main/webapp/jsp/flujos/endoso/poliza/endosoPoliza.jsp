<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>Consulta de datos de Cotizacion</title>
<script type="text/javascript">
    var _CONTEXT = "${ctx}"; 
    var _Origen         = '<s:property value="Origen" />';
    var _nmSituac       = '<s:property value="nmSituac" />';
    var _dsDescripcion  = '<s:property value="dsDescripcion" />';
    var _cdInciso       = '<s:property value="cdInciso" />';
    var _status         = '<s:property value="status" />';
    var _aseguradora    = '<s:property value="aseguradora" />';
    var _poliza         = '<s:property value="poliza" />';
    var _producto       = '<s:property value="producto" />';
    
    var _cdUnieco     = '<s:property value="cdUnieco" />';
    
    var _cdRamo       = '<s:property value="cdRamo" />';
    var _estado       = '<s:property value="estado" />';
    var _nmPoliza     = '<s:property value="nmPoliza" />';
    var _poliza       = '<s:property value="poliza" />';
    var _producto     = '<s:property value="producto" />';
    var _aseguradora  = '<s:property value="aseguradora" />';
    
    var _ACTION_IR_POLIZAS_RENOVADAS = "<s:url action='irPolizasRenovadas' namespace='/procesoemision'/>";
    
    var PROCESO = '<s:property value="proc"/>';
    var RENOVACION = 'ren';
    var ENDOSO = 'end';

    var PROCESO_RENOVACION = ( PROCESO!=null && PROCESO==RENOVACION ) ? true:false;
    var PROCESO_ENDOSO = ( PROCESO!=null && PROCESO==ENDOSO ) ? true:false;
        
 	var FECHA_EFECTIVIDAD_ENDOSO='<s:property value="fechaefectividadendoso" />';
	    

	    
</script> 

<script type="text/javascript"><!--
/*
 * Ext JS Library 2.1
 * Copyright(c) 2006-2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */

Ext.onReady(function(){
    Ext.QuickTips.init();
	var storeClaves;
	var factor;
	var formPanelEditable;
	var diferencia;
	var importeT;
	var importeP;
	var anual;   
	var grid4;
	var recibo;
	
	var endosoConf;
	
    var cmPoliza = new Ext.grid.ColumnModel([
    
		new Ext.grid.RowNumberer(),
		{header: "Inciso",			dataIndex:'nmSituac',	width: 50, 	sortable:true},
		{header: "Cobertura",		dataIndex:'cdGarant',	width: 50, 	sortable:true, hidden:true},
		{header: "Cobertura",		dataIndex:'dsGarant',	width: 200, sortable:true},
		{header: "Concepto",		dataIndex:'cdContar',	width: 50, 	sortable:true,hidden:true},
		{header: "",				dataIndex:'dsContar',	width: 50,  sortable:true,hidden:true},
		{header: "Tipo de Concepto",dataIndex:'cdTipcon',	width: 50, 	sortable:true,hidden:true},      
		{header: "Concepto",		dataIndex:'dsTipcon',	width: 200, sortable:true},
		{header: "Importe",			dataIndex:'nmImport', 	width: 100, sortable:true, renderer:Ext.util.Format.usMoney}
	]);
    
    cmPoliza.defaultSortable = false;
    
    
    function endosoPoliza(){                      
	url='<s:url namespace="flujoendoso" action="cargaEndosos" includeParams="none"/>';
	storeClaves = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({        
    		url:url
    }),	
	reader: new Ext.data.JsonReader({
		root:'endososList',   
		totalProperty: 'totalCount',
		id: ''
		},[
		{name: 'cdUnieco',  type: 'string',  mapping:'cdUnieco'},
		{name: 'cdRamo',  	type: 'string',  mapping:'cdRamo'},
		{name: 'estado',  	type: 'string',  mapping:'estado'},
		{name: 'nmPoliza',  type: 'string',  mapping:'nmPoliza'},
		{name: 'nmSituac',  type: 'string',  mapping:'nmSituac'},
		{name: 'nmSuplem',  type: 'string',  mapping:'nmSuplem'},
		{name: 'status',  	type: 'string',  mapping:'status'},
		{name: 'cdGarant',  type: 'string',  mapping:'cdGarant'},
		{name: 'cdTipcon',  type: 'string',  mapping:'cdTipcon'},
		{name: 'cdContar',  type: 'string',  mapping:'cdContar'},
		{name: 'nmImport',  type: 'string',  mapping:'nmImport'},
		{name: 'cdAgrupa',  type: 'string',  mapping:'cdAgrupa'},
		{name: 'orden',  	type: 'string',  mapping:'orden'},
		{name: 'dsGarant',  type: 'string',  mapping:'dsGarant'},
		{name: 'dsTipcon',  type: 'string',  mapping:'dsTipcon'},
		{name: 'dsContar',  type: 'string',  mapping:'dsContar'}
                       
		]),
		remoteSort: false
	});
	storeClaves.setDefaultSort('cdUnieco', 'DESC');
	storeClaves.load();
	return storeClaves;
}
    
    
    grid4= new Ext.grid.GridPanel({
		store: endosoPoliza(),
		//id:'grid-autorizaciones',
		border:true,
		cm: cmPoliza,
		width:720,
		height:370,
		buttonAlign:'left',
		frame:true,     
		bodyStyle:'padding:5px',
		//viewConfig: {autoFill: true,forceFit:true},  
		sm: new Ext.grid.RowSelectionModel({
		singleSelect: true,
		listeners: {       
			}
		}),            
		bbar: new Ext.PagingToolbar({
			pageSize:20,
			store: storeClaves,
            displayInfo: true,
            displayMsg: 'Registros mostrados {0} - {1} de {2}',
            emptyMsg: 'No hay registros para mostrar',
            beforePageText: 'P&aacute;gina',
            afterPageText: 'de {0}'            
		})

	});

   storeClaves.load();
   endosoConf = new Ext.form.TextField({
				id:'id-aseguradora-endosoConf',
                fieldLabel: PROCESO_RENOVACION? 'Cambio Confirmado' : 'Endoso Confirmado',
                labelSeparator:'',
                width:'80%',                           
                name:'aseguradora',
                readOnly: true,
                disabled: true 
        });     


	factor = new Ext.form.TextField({
				//id:'contenidoSumaAseg-editable',
				fieldLabel: 'Factor',
				labelSeparator:'',                    
				width:'80%',       
				//disabled:true  ,
				name:'factor'
        });     
	
    recibo = new Ext.form.TextField({
				//id:'id-pisos-editable',
				fieldLabel: 'Recibo',
				labelSeparator:'',                    
				width:'80%',       
				//disabled:true  ,
				name:'pisos'
        });
        
        
    var modoPag = new Ext.form.TextField({
				fieldLabel: 'Modo de Pago',
				labelSeparator:':',                    
				width:'150',       
				readOnly: true,
				disabled: true,
				name:'pisos'
        });
        
    var primTotalPag = new Ext.form.TextField({
				fieldLabel: 'Monto recibos subsecuentes',//cambio de Prima Total a Pagar por Monto recibos subsecuentes
				labelSeparator:':',                    
				width:'100',
				readOnly:true,
				disabled: true,
				name:'pisos'
        });
    var modoPag2 = new Ext.form.TextField({
				//id:'id-pisos-editable',
				fieldLabel: 'Modo de Pago',
				labelSeparator:':',                    
				width:'150',       
				readOnly:true,
				disabled:true,
				name:'pisos'
        });  
     var pSegunModo = new Ext.form.TextField({
				fieldLabel: 'Monto Primer Recibo',//Prima según modo Pago
				labelSeparator:':',                    
				width:'100',       
				readOnly:true,
				disabled: true,
				name:'importeP'
        });

	var nRecibosSub = new Ext.form.TextField({
		fieldLabel: 'N&uacute;mero recibos subsecuentes',
		labelSeparator:':',
		width:'100',
		readOnly:true,
		disabled: true,
		name:'recibosSub'
	});
            
	importeT = new Ext.form.TextField({
				fieldLabel: 'Importe Total: $',
				labelSeparator:':',                    
				width:'80%',       
				readOnly:true,
				disabled: true,
				name:'importeT'
        });     
	importeP = new Ext.form.TextField({
				fieldLabel: 'Importe Prorroteado',
				labelSeparator:':',                    
				width:'80%',       
				readOnly:true,  
				name:'importeP'              
        });
             
	anual  = new Ext.form.TextField({  
                labelSeparator:'',                    
                width:'80%',       
                disabled:true,                               
                name:'anual', 
                hidden:true,
                hideParent:true
        });     
    diferencia = new Ext.form.TextField({
				id:'id-pisos-editable',
				fieldLabel: 'Diferencia',
				labelSeparator:'',                    
				width:'80%',       
				readOnly:true,
				name:'diferencia'
        }); 
        
    var comment = new Ext.form.TextArea({
                fieldLabel: 'Comentario',
                name:'comentario',
                id:'comentario',
                allowBlank: true,
                width : 250,
                grow: false,
                preventScrollbars:true  
       });

	formPanelEditable = new Ext.FormPanel({
    	autoScroll:true,
        id:'panel-editable-grids',
        frame:true,
        region:'center',
        bodyStyle:'padding:5px 5px 0',
        url:'<s:url namespace="flujoendoso" action="poliza" includeParams="none"/>',
        width:760,
        title: PROCESO_RENOVACION?'CAMBIO DE POLIZA' : 'ENDOSOS DE POLIZA',
        border:false,
        items:[{
        		layout:'form',
                border:true,
                buttonAlign:'center',
                width: 720,
                
               	items:[grid4,
            			{
                		layout:'form',
                		border:false,
                		frame:true,
                		width: '720',
                		items:[{
                    			layout:'column',
                    			border:false,
                    			labelAlign:'left',
                    			width: '720',
                    			items: [{
                            			columnWidth:.65,
                            			layout:'form',                            		
                            			border:false,
                            			items:[anual]
                        				},{
                            			columnWidth:.35,
                            			layout:'form',
                            			labelAlign:'right',
                            			border:false,
                            			items:[importeT]
                        			  }]
                			}]          
            		},{
                		layout:'form',
                		border:false,
                		labelAlign:'right',
                		frame:true,
                		width: '720',
                		items:[{
                    			layout:'column',
                    			border:false,
                    			width: '720',
                    			items: [{
                            			columnWidth:.4,
                            			layout:'form',
                            			border:false,
                            			items:[endosoConf]
                        				}]
                			}] 
                    },{
                        layout:'form',
                        title:'Comentario',
                        border:false,
                        labelAlign:'right',
                        frame:true,
                        width: '720',
                        items:[{
                                layout:'column',
                                border:false,
                                width: '720',
                                items: [{
                                        columnWidth:.4,
                                        layout:'form',
                                        border:false,
                                        items:[comment]
                                        }]
                            }]          
            		},{
                		layout:'form',
                		title:'Recibos',//cambio de Resumen a Recibos
                		border:false,
                		frame:true,
                		labelAlign:'right',
                		width: '720',
                		items:[{
                    			layout:'column',
                    			border:false,                    		
                    			width: '720',
                    			items: [
                    			{		columnWidth:.5,
                            			layout:'form',                       
                            			border:false,
                            			items:[modoPag]}
                            			,{
                            			columnWidth:.5,
                            			layout:'form',                       
                            			border:false,
                            			items:[pSegunModo]
                            			},{
                        			  	columnWidth:.5,
                        			  	layout:'form',
                        			  	border:false,
                        			  	items:[nRecibosSub]
                        			  	},{
                        			  	columnWidth:.5,
                        			  	layout:'form',
                        			  	boder:false,
                        			  	items:[primTotalPag]
								}]
                			}]          
            		}],
            		buttons:[{        							
        						text:'Confirmar',	
        						handler: function() {
                                    confirmarTarificar();
                                }
        					},{
        						text: PROCESO_RENOVACION? 'Anular Cambio' : 'Anular Endoso',
        						//hidden: PROCESO_RENOVACION,
        						handler: function() {
        							anularEndoso();
        						}
        					},{
        					text:'Regresar',
        						handler: function() {
        						
	        						var proceso="";
									if(PROCESO_RENOVACION){
										proceso=RENOVACION;
									}else{
										proceso=ENDOSO;
									}

	                                if(_Origen=='inciso'){
	                                
	                                        window.location.href = _CONTEXT+"/flujoendoso/datosIncisosEndoso.action?nmSituac="+_nmSituac+"&dsDescripcion="+_dsDescripcion+"&cdInciso="+_cdInciso+"&status="+_status+"&aseguradora="+_aseguradora+"&poliza="+_poliza+"&producto="+_producto+"&proc="+proceso+"&fechaefectividadendoso="+FECHA_EFECTIVIDAD_ENDOSO;
	                                }
	                                if(_Origen=='detalle'){
	                                		
	                                        window.location.href = _CONTEXT+"/flujoendoso/detallePoliza.action?cdUnieco="+_cdUnieco+"&cdRamo="+_cdRamo+"&estado="+_estado+"&nmPoliza="+_nmPoliza+"&poliza="+_poliza+"&producto="+_producto+"&aseguradora="+_aseguradora+"&proc="+proceso+"&fechaefectividadendoso="+FECHA_EFECTIVIDAD_ENDOSO;
	                                }
                                }
        					}]
		}]     
    });

	function cargaSumas(){
 	var params="";
		var conn = new Ext.data.Connection();
				conn.request ({              
                	url:'<s:url namespace="flujoendoso" action="cargaTotales" includeParams="none"/>',
                  	method: 'POST',
                  	successProperty : '@success',
                  	params : params,
                    	callback: function (options, success, response) {                 
                        	importeT.setValue(Ext.util.JSON.decode(response.responseText).totalString);
						}
					}); 
	}
    cargaSumas();
    
    function anularEndoso(){
    var params="";
    var conn = new Ext.data.Connection();
				conn.request ({              
                	url:'<s:url namespace="flujoendoso" action="anularEndoso" includeParams="none"/>',
                  	method: 'POST',
                  	successProperty : '@success',
                  	params : params,
                    	callback: function (options, success, response) { 
                    		if(Ext.util.JSON.decode(response.responseText).indicador==true){
                    			Ext.MessageBox.alert('Estado',PROCESO_RENOVACION? 'Se anulo el cambio' : 'Se anulo el endoso',
	                                function (){
		                                if(PROCESO_RENOVACION){
		    								window.location=_ACTION_IR_POLIZAS_RENOVADAS;
		    							}else if(PROCESO_ENDOSO){
		    								window.location.replace(_CONTEXT+"/procesoemision/busquedaPoliza.action");
		    							}
	                                });
                    		}else{
                    			Ext.MessageBox.alert('Error','Error intentando anular cambio');
                    		}              
						}
					}); 
    }
 
	formPanelEditable.render("algo");
    
    function cargaDetalles(){
    var params="";
        var conn = new Ext.data.Connection();
                conn.request ({              
                    url:'<s:url namespace="flujoendoso" action="obtieneDetalleTarificar" includeParams="none"/>',
                    method: 'POST',
                    successProperty : '@success',
                    params : params,
                        callback: function (options, success, response) {                 
                            endosoConf.setValue(Ext.util.JSON.decode(response.responseText).nmEndoso);
                            modoPag.setValue(Ext.util.JSON.decode(response.responseText).dsFormaPag);
                            primTotalPag.setValue(Ext.util.JSON.decode(response.responseText).totalPagar);
                            pSegunModo.setValue(Ext.util.JSON.decode(response.responseText).totalPagarF);
                            nRecibosSub.setValue(Ext.util.JSON.decode(response.responseText).nmrecsub);
                        }
                    }); 
    }
    cargaDetalles();
    
    function confirmarTarificar(){
    
    var params="";
    var conn = new Ext.data.Connection();
                conn.request ({              
                    //url:'<s:url namespace="flujoendoso" action="confirmarTarificar" includeParams="none"/>',
                    url: _CONTEXT + '/flujoendoso/confirmarTarificar.action?comentario='+Ext.get('comentario').dom.value,
                    method: 'POST',
                    successProperty : '@success',
                    params : params,
                        callback: function (options, success, response){ 
                            if(Ext.util.JSON.decode(response.responseText).success==true){
                            var msgEndoso="";
                            	if(PROCESO_ENDOSO){
	                            	
	                            	var urlGenDocs="/acw/pdf/endosos/";
	                                /*urlGenDocs+=Ext.util.JSON.decode(response.responseText).namePdf;
	                                //window.open(url, "popup", "width=800,height=600,Scrollbars=YES,Resizable=YES");
	                                //alert(urlGenDocs);
	                            	*/
	                            	
		                            var archivos=Ext.util.JSON.decode(response.responseText).namePdf;
		                            archivos= archivos.split("|");
		                            for(var i=0; i<archivos.length; i++){
			                            var archivo=archivos[i];
				                            if(archivo!=''){
				                            var url = urlGenDocs + archivo;
				                            window.open(url, "popup"+i, "width=800,height=600,Scrollbars=YES,Resizable=YES");
				                            //alert("caratula: "+url);
				                            }
		                            }
		                            var archivosRecibos=Ext.util.JSON.decode(response.responseText).namePdfRecibo;
		                            archivosRecibos= archivosRecibos.split("|");
		                            for(var i=0; i<archivosRecibos.length; i++){
			                            var archivoRecibo=archivosRecibos[i];
				                            if(archivoRecibo!=''){
				                            var url = urlGenDocs + archivoRecibo;
				                            window.open(url, "popup2"+i, "width=800,height=600,Scrollbars=YES,Resizable=YES");
				                            //alert("recibo: "+url);
				                            }
		                            }
		                            
		                            
		                            msgEndoso = Ext.util.JSON.decode(response.responseText).mensajeEndoso;
                            	
                            	}
                                Ext.MessageBox.alert('Aviso',PROCESO_RENOVACION? 'Cambio Realizado' : msgEndoso,
                                function (){
	                                if(PROCESO_RENOVACION){
	    								window.location=_ACTION_IR_POLIZAS_RENOVADAS;
	    							}else if(PROCESO_ENDOSO){
	    								window.location.replace(_CONTEXT+"/procesoemision/busquedaPoliza.action");
	    							}
                                });
                                
                            }else {
                                Ext.MessageBox.alert('Error','Error al confirmar');
                            }             
                        }
                            
                    }); 
    }

});

--></script>

</head>
<body>

<div id="algo"></div>

</body>
</html>