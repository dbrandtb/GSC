Ext.require([ 'Ext.form.*', 'Ext.data.*', 'Ext.chart.*', 'Ext.grid.Panel','Ext.layout.container.Column', 'Ext.selection.CheckboxModel' ]);

Ext.onReady(function() {

	
    Ext.selection.CheckboxModel.override( {
        mode: 'SINGLE',
        allowDeselect: true
    });

    Ext.define('modelListadoCoberturaPol',{
        extend: 'Ext.data.Model',
        fields: [
                 	{type:'string',    name:'cdgarant'},		{type:'string',    name:'dsgarant'},		{type:'string',    name:'ptcapita'}
				]
    });
    

    
    storeCoberturaPol = new Ext.data.Store(
    {
    	pageSize : 10
        ,model      : 'modelListadoCoberturaPol'
        ,autoLoad  : false
        ,proxy     :
        {
            enablePaging : true,
            reader       : 'json',
            type         : 'memory',
            data         : []
        }
    });
    
    /*var storeCobertura = Ext.create('Ext.data.Store', {
        model:'modelListadoCobertura',
        autoLoad:false,
        proxy: {
            type: 'ajax',
            url : _URL_LISTA_COBERTURA,
            reader: {
                type: 'json',
                root: 'listaCoberturaPoliza'
            }
        }
    });*/
    
    /*storeListadoPoliza = new Ext.data.Store(
    {
    	pageSize : 10
        ,model      : 'modelListadoPoliza'
        ,autoLoad  : false
        ,proxy     :
        {
            enablePaging : true,
            reader       : 'json',
            type         : 'memory',
            data         : []
        }
    });*/

    
    registroCobertura=Ext.create('Ext.grid.Panel',
			{
			    id             : 'idCoberturaPol'
			    ,store         :  storeCoberturaPol
			    ,collapsible   : true
			    ,titleCollapse : true
			    //,style         : 'margin:5px'
			    ,width   : 605
			    ,height: 350
			    ,columns       :
			    [
			        
			         {
			             header     : 'Clave'
			             ,dataIndex : 'cdgarant'
			             ,width     : 100
			         },
			         {
			             header     : 'Descripci&oacute;n'
			             ,dataIndex : 'dsgarant'
			             ,width	    : 350
			         }
			         ,
			         {
			             header     : 'Monto'
			             ,dataIndex : 'ptcapita'
			             ,width	    : 150
			             ,renderer	: Ext.util.Format.usMoney
			         }
			     ],
			     bbar     :
			     {
			         displayInfo : true,
			         store       : storeCoberturaPol,
			         xtype       : 'pagingtoolbar'
			     }
			});
    
    		registroCobertura.store.sort([
	            { 
	            	property    : 'dsgarant',
	            	direction   : 'ASC'
	            }
	        ]);
    
    Ext.create('Ext.panel.Panel',
    	    {
    	        border    : 0
    	        ,renderTo : 'div_clau2'
    	        ,items    :
    	        [
    	            registroCobertura
    	        ]
    	    });
    
    
    
    var params = {
            	'params.cdunieco':_7_smap1.cdunieco,
            	'params.estado':_7_smap1.estado,
            	'params.cdramo':_7_smap1.cdramo,
            	'params.nmpoliza':_7_smap1.nmpoliza,
            	'params.nmsituac':_7_smap1.nmsituac
    };
    
    console.log("RESPUESTA");
    console.log(params);
    cargaStorePaginadoLocal(storeCoberturaPol,_URL_LISTA_COBERTURAPOL, 'listaCoberturaPoliza', params, function(options, success, response){
        if(success){
        	
            var jsonResponse = Ext.decode(response.responseText);
            if(jsonResponse.listaCoberturaPoliza == null) {
                Ext.Msg.show({
                    title: 'Aviso',
                    msg: 'No se encontraron datos.',
                    buttons: Ext.Msg.OK,
                    icon: Ext.Msg.WARNING
                });
                return;
            }
            
        }else{
            Ext.Msg.show({
                title: 'Aviso',
                msg: 'Error al obtener los datos.',
                buttons: Ext.Msg.OK,
                icon: Ext.Msg.ERROR
            });
        }
    });
});
