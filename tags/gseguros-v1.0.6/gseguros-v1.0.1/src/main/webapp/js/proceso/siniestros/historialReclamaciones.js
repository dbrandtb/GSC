Ext.require([ 'Ext.form.*', 'Ext.data.*', 'Ext.chart.*', 'Ext.grid.Panel','Ext.layout.container.Column', 'Ext.selection.CheckboxModel' ]);

Ext.onReady(function() {

    Ext.selection.CheckboxModel.override( {
        mode: 'SINGLE',
        allowDeselect: true
    });

    //////////////////////////////////////
    ////////		MODEL		/////////
    /////////////////////////////////////
    
    Ext.define('modelHistoricoReclamaciones',{
        extend: 'Ext.data.Model',
        fields: [
					{type:'string',    name:'key'      						},//	QUITAR AL FINAL
					{type:'string',    name:'value'      					},//	QUITAR AL FINAL
					{type:'string',    name:'tipoAtencion'      			},
					{type:'string',    name:'fechaReclamo'      			},
					{type:'string',    name:'proveedor'      				},
					{type:'string',    name:'diagnostico'      				},
					{type:'string',    name:'numSiniestro'      			},
					{type:'string',    name:'montoPagado'      				},
					{type:'string',    name:'montoGastadoTotal'      		},
					{type:'string',    name:'montoGastadoAnticipado'      	},
					{type:'string',    name:'montoAutorizado'      			},
					{type:'string',    name:'polizaAfectada'      			}
        ]
    });
    //////////////////////////////////////
    ////////		STORE 		/////////
    /////////////////////////////////////
    var storeGridHistoricoReclamaciones = new Ext.data.Store(
    {
    	pageSize : 10
        ,model      : 'modelHistoricoReclamaciones'
        ,autoLoad  : false
        ,proxy     :
        {
            enablePaging : true,
            reader       : 'json',
            type         : 'memory',
            data         : []
        }
    });
    
    
    
    Ext.create('Ext.panel.Panel',
    	    {
    	        border    : 0
    	        ,renderTo : 'div_clau2'
    	        ,items    :
    	        [
    	            /*TABLA CON LA INFORMACION DE DE HISTORIAL DE RECLAMACIONES*/
    	            Ext.create('Ext.grid.Panel',
    	            {
    	                id             : 'clausulasGridId'
    	                //,title         : 'Hist&oacute;rico de Reclamaciones'
    	                ,store         :  storeGridHistoricoReclamaciones
    	                ,collapsible   : true
    	                ,titleCollapse : true
    	                ,style         : 'margin:5px'
    	                //,height        : 400
    	                ,width   : 800
    	                ,height: 400
    	                ,columns       :
    	                [
    	                    {
    	                        header     : 'Clave'
    	                        ,dataIndex : 'key'
	                        	,width	   : 200
    	                        //,flex      : 1
    	                    }
    	                    ,
    	                    {
    	                        header     : 'Descripcion de la Cl&aacute;usula'
        	                    ,dataIndex : 'value'
        	                    ,width	   : 200	
        	                    //,flex      : 1
        	                }
    	                    ,
    	                    {	
    	                    	text            :'Tipo de atenci&oacute;n',			width			: 200,
    	                        dataIndex       :'tipoAtencion'                
    	                    },
    	                    {	
    	                    	text            :'fecha reclamo',  					width           : 150,
    	                        dataIndex       :'fechaReclamo'                
    	                    }
    	                    ,
    	                    {	
    	                    	text            :'Proveedor',  						width           : 200,
    	                        dataIndex       :'proveedor'                
    	                    },
    	                    {	
    	                    	text            :'Di&aacute;gnostico',				width           : 200,
    	                        dataIndex       :'diagnostico'                
    	                    },
    	                    {
    	                        text            :'No. Suministro',  				width           : 150,
    	                        dataIndex       :'numSiniestro'                
    	                    },            
    	                    {
    	                        text            :'Monto Pagado',  					width           : 150, 			renderer        :Ext.util.Format.usMoney,
    	                        dataIndex       :'montoPagado'
    	                    },
    	                    {
    	                        text            :'Monto gasto <br/> total',			width           : 150, 			renderer        :Ext.util.Format.usMoney, 
    	                        dataIndex       :'montoGastadoTotal'
    	                    },
    	                    {
    	                        text            :'Monto gasto <br/> anticipado',	width           : 150, 			renderer        :Ext.util.Format.usMoney, 
    	                        dataIndex       :'montoGastadoAnticipado'
    	                    },
    	                    {
    	                        text            :'Monto autorizado',				width           : 150, 			renderer        :Ext.util.Format.usMoney, 
    	                        dataIndex       :'montoAutorizado'
    	                    },
    	                    {
    	                        text            :'P&oacute;liza afectada',  	    width           : 150,
    	                        dataIndex       :'polizaAfectada'                               
    	                    }
    	                ],
    	                bbar     :
    	                {
    	                    displayInfo : true,
    	                    store       : storeGridHistoricoReclamaciones,
    	                    xtype       : 'pagingtoolbar'
    	                }
    	            })
    	        ]
    	    });
});
