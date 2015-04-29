<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
	var _CONTEXT = '${ctx}';
	var paramsEntrada          = <s:property value="%{convertToJSON('smap1')}" escapeHtml="false" />;
	var guarda_Despago = '<s:url namespace="/endosos" action=" guardarEndosoDespago"       />';
	
	var _URL_CONSULTA_RECIBOS_PAGADOS = '<s:url namespace="/endosos" action="obtieneRecibosPagados" />';
	
	debug('paramsEntrada  -->:',paramsEntrada);
	
	Ext.onReady(function() {
		
		
		Ext.define('gridRecibosModel',{
			extend : 'Ext.data.Model',
			fields : ['NMRECIBO','NMSUPLEM','NMIMPRES','CDDEVCIA','CDGESTOR','PTIMPORT','FEEMISIO','FEINICIO','FEFINAL','FEESTADO']
		});
		
		var recibosStore = Ext.create('Ext.data.Store',{
			pageSize : 20,
	        model   : 'gridRecibosModel',
	        proxy   :
				{
		            type: 'ajax',
		            url : _URL_CONSULTA_RECIBOS_PAGADOS,
		            reader: {
		                type: 'json',
		                root: 'slist1'
		            }
	        	}
	    });
	    
	    var gridRecibos = Ext.create('Ext.grid.Panel',{
	    	title : 'Recibos Pagados, Seleccione el Recibo a Despagar.',
	    	height : 250,
	    	selType: 'checkboxmodel',
	    	store : recibosStore,
	    	columns :[
	    	  { text: 'No. Recibo', dataIndex : 'NMRECIBO'},
	    	  { text: 'Suplemento',    dataIndex : 'NMSUPLEM', hidden: true},
	    	  { text: 'NMIMPRES',    dataIndex : 'NMIMPRES'},
	    	  { text: 'CDDEVCIA',    dataIndex : 'CDDEVCIA'},
	    	  { text: 'CDGESTOR',    dataIndex : 'CDGESTOR'},
	    	  { text: 'PTIMPORT',    dataIndex : 'PTIMPORT'},
	    	  { text: 'FEEMISIO',    dataIndex : 'FEEMISIO'},
	    	  { text: 'FEINICIO',    dataIndex : 'FEINICIO'},
	    	  { text: 'FEFINAL',    dataIndex : 'FEFINAL'},
	    	  { text: 'FEESTADO',    dataIndex : 'FEESTADO'}
			]
    	});
		
		var panelInicialPral = Ext.create('Ext.form.Panel', {
		    renderTo  : 'maindivHist',
		    bodyPadding: 5,
		    defaultType: 'textfield',
		    
			defaults 	:
			{
				style : 'margin:5px;'
			}
			,
		    items: [
				{	xtype		: 'datefield',	fieldLabel	: 'Fecha Inicio Endoso',	name	: 'feInival',			labelWidth	: 150,
					format		: 'd/m/Y', value: paramsEntrada.FEINIVAL, readOnly: true
				},gridRecibos
	    	]
			,buttonAlign:'center'
			,buttons: [{
				text: 'Confirmar endoso'
				,icon:_CONTEXT+'/resources/fam3icons/icons/key.png'
				,buttonAlign : 'center',
				handler: function() {
					
					var model =  gridRecibos.getSelectionModel();
	            	if(model.hasSelection()){
	            		var record = model.getLastSelected();
	            		var submitValues={};
	        				paramsEntrada.FEINIVAL = Ext.Date.format(panelInicialPral.down('[name="feInival"]').getValue(),'d/m/Y');
	        				paramsEntrada.NMRECIBO = record.get('NMRECIBO');
	        				paramsEntrada.NMIMPRES = record.get('NMIMPRES');
	        				submitValues['smap1']= paramsEntrada;
	        				Ext.Ajax.request( {
	   						    url: guarda_Despago,
	   						    jsonData: Ext.encode(submitValues),
	   						    success:function(response,opts){
	   						    	 panelInicialPral.setLoading(false);
	   						         var jsonResp = Ext.decode(response.responseText);
	   						      	 mensajeCorrecto("Endoso",jsonResp.respuesta,null);
	   						      	 marendNavegacion(2);
	   						    },
	   						    failure:function(response,opts){
	   						        panelInicialPral.setLoading(false);
	   						        Ext.Msg.show({
	   						            title:'Error',
	   						            msg: 'Error de comunicaci&oacute;n',
	   						            buttons: Ext.Msg.OK,
	   						            icon: Ext.Msg.ERROR
	   						        });
	   						    }
	   						});
	            		
	            	}else{
	            		showMessage("Aviso","Debe seleccionar un registro", Ext.Msg.OK, Ext.Msg.INFO);
	            	}
				}
			}]
		});
		
		recibosStore.load({
		            params : {
		                'smap1.CDUNIECO' : paramsEntrada.CDUNIECO,
		                'smap1.CDRAMO' : paramsEntrada.CDRAMO,
		                'smap1.ESTADO' : paramsEntrada.ESTADO,
		                'smap1.NMPOLIZA' : paramsEntrada.NMPOLIZA
		            }
		 });
            

    });
</script>
<div id="maindivHist" style="height:1000px;"></div>