<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script>
////// urls //////
var _URL_CARGA_CATALOGO = '<s:url namespace="/catalogos" action="obtieneCatalogo" />';
////// urls //////

////// variables //////
////// variables //////

////// overrides //////
////// overrides //////

////// componentes dinamicos //////
////// componentes dinamicos //////

Ext.onReady(function()
{
    ////// modelos //////
    Ext.define('icdModel', {
	    extend: 'Ext.data.Model',
	    fields: [
	         { name: 'icd'			,type: 'string' },
	         { name: 'reservado'	,type: 'int'  	},
	         { name: 'aprovado' 	,type: 'int' 	},
	         { name: 'pagado'	  	,type: 'int'	}
	    ]
	});
    ////// modelos //////
    
    ////// stores //////
    
    var store = Ext.create('Ext.data.Store', {
	    model: 'icdModel',
	    data: [
	        { icd: "ICD 1", reservado: 5, aprovado: 8, pagado: 2 },
	        { icd: "ICD 2", reservado: 5, aprovado: 8, pagado: 2 },
	        { icd: "ICD 3", reservado: 5, aprovado: 8, pagado: 2 },
	        { icd: "ICD 4", reservado: 5, aprovado: 8, pagado: 2 },
	        { icd: "ICD 5", reservado: 5, aprovado: 8, pagado: 2 }
	    ]
	});
    ////// stores //////
    
    ////// componentes //////
    ////// componentes //////
    
    ////// contenido //////
    var chart=Ext.create('Ext.chart.Chart', {
    	   
    	   width: 400,
    	   height: 300,
    	   store: store,
    	   axes: [{
               type: 'numeric',
               position: 'left',
               fields: 'reservado',
               grid: true,
               minimum: 0,
               label: {
                   renderer: function(v) { return v + '%'; }
               }
           }, {
               type: 'category',
               position: 'bottom',
               fields: 'icd',
               grid: true,
               label: {
                   rotate: {
                       degrees: -45
                   }
               }
           }],
           series: [{
               type: 'column',
               axis: 'left',
               title: [ 'IE', 'Firefox', 'Chrome', 'Safari' ],
               xField: 'icd',
               yField: [ 'reservado', 'aprovado', 'pagado',  ],
               style: {
                   opacity: 0.80
               },
               highlight: {
                   fill: '#000',
                   'stroke-width': 1,
                   stroke: '#000'
               },
               tips: {
                   trackMouse: true,
                   style: 'background: #FFF',
                   height: 20,
                   renderer: function(storeItem, item) {
                       var browser = item.series.title[Ext.Array.indexOf(item.series.yField, item.yField)];
                       this.setTitle(browser + ' for ' + storeItem.get('month') + ': ' + storeItem.get(item.yField) + '%');
                   }
               }
           }]
    	});
    
    Ext.create('Ext.panel.Panel',
    {
        defaults  : { style : 'margin:5px;' }
        ,renderTo : 'divpri'
        ,border   : 0
        ,items    :
        [
        	Ext.create('Ext.form.Panel', {
        	    title: 'B&Úacute;SQUEDA',
        	    bodyPadding: 5,
        	   

        	    // The form will submit an AJAX request to this URL when submitted
        	    url: 'save-form.php',

        	    // Fields will be arranged vertically, stretched to full width
        	    layout: 'anchor',
        	    defaults: {
        	        anchor: '100%'
        	    },

        	    // The fields
        	    defaultType: 'textfield',
        	    items: [
        	    	{
                        xtype       : 'combo',
                        name        : 'params.cdunieco',
                        fieldLabel  : 'Sucursal',
                        allowBlank  : false,
                        valueField  : 'key',
                        displayField: 'value',
                        forceSelection: true,
                        queryMode   :'local',
                        anyMatch    : true,
                        store       : Ext.create('Ext.data.Store', {
                            model : 'Generic',
                            autoLoad : true,
                            proxy : {
                                type : 'ajax',
                                url : _URL_CARGA_CATALOGO,
                                extraParams : {
                                    catalogo : 'MC_SUCURSALES_DOCUMENTO',
                                    'params.cdusuari'   : 'ICE' 
                                },
                                reader : {
                                    type : 'json',
                                    root : 'lista'
                                }
                            },
                            listeners: {
                                load: function (){
                                    
                                }
                            }
                        })
        	    	},
        	    	{
        	    		fieldLabel		: "CDRAMO",
        	    		name			: "cdramo",
        	    	},
        	    	{
        	    		fieldLabel		: "POLIZA",
        	    		name			: "cdramo",
        	    	}
        	    ],

        	    // Reset and Submit buttons
        	    buttons: [{
        	        text: 'Limpiar',
        	        handler: function() {
        	            this.up('form').getForm().reset();
        	        }
        	    }, {
        	        text: 'Buscar',
        	        formBind: true, //only enabled once the form is valid
        	        disabled: true,
        	        handler: function() {
        	            var form = this.up('form').getForm();
        	            if (form.isValid()) {
        	                form.submit({
        	                    success: function(form, action) {
        	                       Ext.Msg.alert('Success', action.result.msg);
        	                    },
        	                    failure: function(form, action) {
        	                        Ext.Msg.alert('Failed', action.result.msg);
        	                    }
        	                });
        	            }
        	        }
        	    }]
        	}),
        	Ext.create('Ext.tab.Panel', {
        	    items: [{
        	        title: 'SINIESTRAL',
        	        items:
        	        	[
        	        		chart
        	        	]
        	    }, {
        	        title: 'TOP 15 ICD',
        	        tabConfig: {
        	            title: 'Custom Title',
        	            tooltip: 'A button tooltip'
        	        }
        	    }]
        	})
        ]
    });
    
    ////// contenido //////
    
    ////// custom //////
    ////// custom //////
    
    ////// loaders //////
    ////// loaders //////
});

////// funciones //////
////// funciones //////
</script>
</head>
<body>
<div id="divpri" style="height:400px;border:1px solid #CCCCCC"></div>
</body>
</html>