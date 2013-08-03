//<script type="text/javascript">


//Ext singleton
Ext.onReady(function(){

    Ext.QuickTips.init();
    Ext.QuickTips.enable();
    Ext.form.Field.prototype.msgTarget = "side";
    
    
    
    /*************************************************************
** Store combo ejemplo
**************************************************************/

        var storeProceso = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: 'flujocotizacion/obtenerListaCombo.action'+'?endPointName=OBTIENE_PROCESOS'
            }),
            reader: new Ext.data.JsonReader({
            root: 'itemList',
            id: 'value'
            },[
           {name: 'value', type: 'string', mapping:'value'},
           {name: 'label', type: 'string', mapping:'label'}  
             ]),
            remoteSort: true
        });
        storeProceso.setDefaultSort('value', 'desc');
        storeProceso.load();
    

   
    
   
 
 ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
 
 
 /*************************************************************
** FormPanel de Ventana de Detalle
**************************************************************/           
var detalleFormPanel =  new Ext.form.FormPanel({                          
   id:'detalleFormPanel',
   url:'flujocotizacion/getDetalle.action',
   border:false,
   layout:'form',
   //defaultType: 'textfield',
  items: [{
  	
  	 border:false,
                bodyStyle:'margin-top: 20px; margin-left: 20px;',
                layout:'table',
                layoutConfig: {
                    columns: 2
                 },
                 <s:component template="builderDetalleResultadoCotizacion.vm" templateDir="templates" theme="components" />
              },{  
  							border:false,
                            layout:'form',
                            bodyStyle:'margin-top: 10px; margin-left: 10px;',
                            width:450,
                            items:[{
                                    xtype: 'editorgrid',
                                    frame:true,
                                    id:'gridResultado',
                                        store: getResultado(),
                                        title: '<span style="color:#98012e;font-size:11px;font-family:Arial,Helvetica,sans-serif;">Coberturas</span>',
                                        stripeRows: true,
                                        cm:new Ext.grid.ColumnModel([
										       {   header: "ClaveConjunto",
										            dataIndex:'cdConjunto',
										            sortable:true,
										            width:20,
										            locked:false,
										            id:'cdConjunto',
										            hidden:true
										            },{
										                    
										            header: "Coberturas",
										            dataIndex:'descripcion',
										            width:150
										           
										            },{
										            header: "Descripcion",
										            dataIndex:'descripcion',
										            width:150
										            },{
										            header: "Deducible",
										            dataIndex:'cliente',
										            width:150     
										         }
										   ]),
                                        clicksToEdit : 2,
                                        height:150,
                                        width:450,
                                        
                                    
                                    viewConfig: {autoFill: true,forceFit:true}, 
                                        bbar: new Ext.PagingToolbar({
                                        pageSize: 25,
                                        store: storeGrid,
                                        displayInfo: true,
                                        displayMsg: 'Displaying rows {0} - {1} of {2}',
                                        emptyMsg: "No rows to display"
                                              })
                                 
                               }]//grid 1
  }]
 
   
 });//end FormPanel


var windowDetalle = new Ext.Window({
            title: 'COBERTURA',
            plain:true,
            id:'windowDetalle',
            width: 500,
            height:450,
            minWidth: 500,
            minHeight: 450,
            layout: 'fit',
            bodyStyle:'padding:5px;',
            buttonAlign:'center',
            modal:true,
            closable : false,
            items: detalleFormPanel,
             buttons: [{
               text:'Regresar',
               handler: function() { 
               windowDetalle.hide(); 
               }
          }]
            
        });


  /*************************************************************
** FormPanel de Ventana de Continuar
**************************************************************/           
var continuarFormPanel =  new Ext.form.FormPanel({                          
   id:'continuarFormPanel',
   url:'flujocotizacion/getDetalle.action',
   border:false,
   layout:'form',
   //defaultType: 'textfield',
  items: [{
  	       xtype:"textfield",
    	   fieldLabel:"Aseguradora",
    	   disabled:true,
    	   name:"cdProceso"
           },{
      	   xtype:"textfield",
    	   fieldLabel:"Descripcion",
    	    disabled:true,
    	   name:"descripcion"
           },{
           xtype:"textfield",
    	   fieldLabel:"Marca",
    	    disabled:true,
    	   name:"proceso"
    	   },{
           xtype:"textfield",
    	   fieldLabel:"Pagar",
    	    disabled:true,
    	   name:"cliente"
    	    },{
    	 xtype:"textfield",
    	 name:'id',
    	 id:'id',
    	 type:'hidden',
    	 hidden:true,
    	labelSeparator:'' 
   		
    	},{
    	        border:false,
		        bodyStyle:'margin-bottom: 20px;',
			    html: '<div align="left"><span style="color:#98012e;font-size:12px;font-family:Arial,Helvetica,sans-serif;">Nombre (Certificado)</span></div>'
		},{
		xtype:"textfield",
    	fieldLabel:"Apellido paterno",
    	name:"paterno"
    	
    	},{
		xtype:"textfield",
    	fieldLabel:"Apellido materno",
    	name:"materno"
        
    	},{
		xtype:"textfield",
    	fieldLabel:"Nombre",
    	name:"nombre"
  }]
 
   
 });//end FormPanel


var windowContinuar = new Ext.Window({
            title: 'COTIZACION',
            plain:true,
            id:'windowContinuar',
            width: 500,
            height:450,
            minWidth: 500,
            minHeight: 450,
            layout: 'fit',
            bodyStyle:'padding:5px;',
            buttonAlign:'center',
            modal:true,
            closable : false,
            items: continuarFormPanel,
             buttons: [{
               text:'Regresar',
               handler: function() { 
               windowContinuar.hide(); 
               }
             },{
             	 text:'Continuar',
               handler: function() { 
               windowContinuar.hide(); 
               windowDetalle.show();
               }
          }]
            
        });


 
 
 ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 
    //var simple is a panel with some elements...
    var simple = new Ext.FormPanel({
        id:'simpleForm',
        layout:'form',
        bodyStyle:'padding:5px 5px 0',
        autoHeight : true, 
        autoWidht : true,
        items: [{
               
                border:false,
                bodyStyle:'margin-top: 20px; margin-left: 30px;',
                layout:'table',
                layoutConfig: {
                    columns: 1
                 },
                <s:component template="builderItemsResultadoCotizacion.vm" templateDir="templates" theme="components" />
              },{  
               
                border:false,
                layout:'form',
                bodyStyle:'margin-top: 10px; margin-left: 20px',
                <s:component template="builderResultResultadoCotizacionMatriz.vm" templateDir="templates" theme="components" />
             
        }] 

    ////////////////////// FOOTERS: BOTONES, LISTENERS...
   <s:component template="builderFootersResultadoCotizacionMatriz.vm" templateDir="templates" theme="components" />
  
      
    });

   
   simple.render('items'); 

 ////////////////////// FUNCIONES...
   <s:component template="builderFunctionsResultadoCotizacionMatriz.vm" templateDir="templates" theme="components" />


});

//</script>