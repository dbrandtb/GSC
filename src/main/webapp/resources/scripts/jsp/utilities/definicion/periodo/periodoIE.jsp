<%@ include file="/taglibs.jsp"%>
<!-- LIBRARIES -->
<script type="text/javascript"
	src="${ctx}/resources/scripts/utilities/vTypes/date.js"></script>
<!-- SOURCE CODE -->
<script type="text/javascript">

PeriodoIE = function(dataStore, selectedId) {

if(selectedId!=null){
    	var rec = dataStore.getAt(selectedId);
 
   		dataStore.on('load', function(){ 
   						   Ext.getCmp('load-record').getForm().loadRecord(rec);                           
                           });
		dataStore.load(); 	 
}
  
    var formPanel = new Ext.form.FormPanel({
  		id:'load-record',
        baseCls: 'x-plain',
        
        url:'definicion/AsociarPeriodo.action',
        defaultType: 'datefield',
		reader: new Ext.data.JsonReader({		//el edit aun no esta probado
            root: '' 
        }, ['inicio','fin']
        ),
        items: [{xtype:'hidden',id:'id-hidden-codigo-periodo-ventana',name:'codigoPeriodo'},{
                xtype:'datefield',
                fieldLabel: '<s:text name="def.productos.fechaIni"/>',
		        name: 'inicio',
		        id: 'startdt',
		        vtype: 'daterange',
		        allowBlank:false,
		        blankText : '<s:text name="insert.periodo.dateinicio"/>',
                endDateField: 'enddt',
                format:'d/m/Y'
		    
        	},{
        		xtype:'datefield',
                allowBlank:false,
                blankText : '<s:text name="insert.periodo.datefin"/>',
                fieldLabel: '<s:text name="def.productos.fechaFin"/>',
		        name: 'fin',
		        id: 'enddt',
        		vtype: 'daterange',
		        startDateField: 'startdt',
		        format:'d/m/Y'
            
        	}
		]
    });
    
 
    // define window and show it in desktop
    var window = new Ext.Window({
        title: '<s:text name="def.alta.periodo.title"/>',
        width: 230,
        height:150,
        layout: 'fit',
        plain:true,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        items: formPanel,
		modal:true,
        buttons: [{
            text: '<s:text name="def.productos.btn.guardar"/>', 
            handler: function() {
                // check form value 
                if (formPanel.form.isValid()) {
                //alert(Ext.getCmp('id-hidden-codigo-periodo-ventana').getValue());
	 		        formPanel.form.submit({			      
			            waitTitle:'<s:text name="ventana.proceso.clausula.mensaje.titulo"/>',
			            waitMsg:'<s:text name="ventana.proceso.clausula.mensaje.proceso"/>',
			            failure: function(form, action) {
						    Ext.MessageBox.alert('Error al Guardar el Periodo', "La fecha inicial de un periodo no puede ser anterior <br />a la fecha final del ultimo periodo agregado");
						},
						success: function(form, action) {
						    //Ext.MessageBox.alert('Confirm', action.result.info);
						   
						    window.close();
						    dataStore.load({params:{start:0, limit:10}});
						}
			        });                   
                } else{
					Ext.MessageBox.alert('Errors', 'Favor de llenar los datos requeridos');
				}             
	        }
        },{
            text: '<s:text name="def.productos.btn.cancelar"/>',
            handler: function(){window.close();}
        }]
    });

    window.show();
};
</script>
