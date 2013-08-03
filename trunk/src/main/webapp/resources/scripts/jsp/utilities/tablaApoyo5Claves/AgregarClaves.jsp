<%@ include file="/taglibs.jsp"%>
<!-- LIBRARIES -->
<!-- SOURCE CODE -->
<script type="text/javascript">

CreaClave = function(dataStore,row) {

if(row!=null){

	 var rec = dataStore.getAt(row);
 
   dataStore.on('load', function(){    						                                                   
                           Ext.getCmp('agrega-claves-tabla-5-claves').getForm().loadRecord(rec);                       
                           Ext.getCmp('hidden-numero-tabla-clave-cinco-claves').setValue(rec.get('numeroTabla'));
                           Ext.getCmp('hidden-numero-clave-cinco-claves').setValue(rec.get('numeroClave'));
                           Ext.getCmp('hidden-edita-clave-cinco-claves').setValue('base');
                           
                           });
	dataStore.load(); 	

}
    var myData = [
        ["N","Numerico"],
        ["A","Alfanumerico"]
        ];
   var comboStore = new Ext.data.SimpleStore({
        fields: [
           {name: 'key'},
           {name: 'value'}           
        ]
    });   	
    comboStore.loadData(myData);
   var formato =new Ext.form.ComboBox({
      	                    typeAhead: true,
					    	mode: 'local',					    	
    	                    tpl: '<tpl for="."><div ext:qtip="{value}. {nick}" class="x-combo-list-item">{value}</div></tpl>',
							store: comboStore,
						    displayField:'value',
						    valueField: 'key',
						    allowBlank:false,
						    blankText : '<s:text name="productos.tabla5claves.atributosVar.valida.formato.req"/>',
					    	triggerAction: 'all',
						    emptyText:'<s:text name="productos.tabla5claves.atributosVar.select.formato"/>',
					    	selectOnFocus:true,
						    fieldLabel: '<s:text name="productos.tabla5claves.AtributosVar.formato"/>',
					    	name:"descripcionFormatoClave"
					    	
			});
    // pre-define fields in the form
	var descripcion = new Ext.form.TextField({
        fieldLabel: '<s:text name="productos.tabla5claves.AtributosVar.descripcion"/>',
        allowBlank: false,
        blankText : '<s:text name="productos.tabla5claves.atributosVar.valida.descripcion.req"/>',
        name: 'descripcionClave',
        anchor: '100%' 
    });
    
    var limiteMaximo = new Ext.form.NumberField({
        fieldLabel: '<s:text name="productos.tabla5claves.AtributosVar.maximo"/>',
        allowBlank: false,
        blankText : '<s:text name="productos.tabla5claves.atributosVar.valida.maximo.req"/>',
        name: 'maximoClave',
        anchor: '50%' 
    });  
    
    var limiteMinimo = new Ext.form.NumberField({
        fieldLabel: '<s:text name="productos.tabla5claves.AtributosVar.minimo"/>',
        allowBlank: false,
        blankText : '<s:text name="productos.tabla5claves.atributosVar.valida.minimo.req"/>',
        name: 'minimoClave',
        anchor: '50%' 
    });
        
    var formPanel = new Ext.form.FormPanel({
  
        id:'agrega-claves-tabla-5-claves',
        baseCls: 'x-plain',
        labelWidth: 115,
        url:'<s:url namespace="tablaCincoClaves" action="InsertarClave" includeParams="none"/>',
        //url:'/tablaCincoClaves/InsertarClave.action',
		//defaultType: 'textfield',
        //collapsed : false,

        items: [{xtype:'hidden',id:'hidden-numero-clave-cinco-claves',name:'codigoClave'},
        		{xtype:'hidden',id:'hidden-edita-clave-cinco-claves',name:'edita'},
        		{xtype:'hidden',id:'hidden-numero-tabla-clave-cinco-claves',name:'num1'},
            descripcion,
            formato,
            limiteMinimo,
            limiteMaximo
    	]
    });

    // define window and show it in desktop
    var atributosWindow = new Ext.Window({
        title: '<s:text name="productos.tabla5claves.claves.titulo"/>',
        width: 350,
        height:180,
        //minWidth: 300,
        //minHeight: 150,
        layout: 'fit',
        plain:true,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        modal : true,
  
        items: formPanel,
        

buttons: [{
            text: '<s:text name="productos.tabla5claves.btn.guardar"/>', 
            handler: function() {
                // check form value 
                if (formPanel.form.isValid()) {
	 		        formPanel.form.submit({			      
			            waitTitle:'<s:text name="ventana.tabla5claves.proceso.mensaje.titulo"/>',
					    waitMsg:'<s:text name="ventana.tabla5claves.proceso.mensaje"/>',
			            failure: function(form, action) {
						    Ext.MessageBox.alert('Error', 'Clave no agregada');
						},
						success: function(form, action) {
						    Ext.MessageBox.alert('Status', 'Clave agregada');
						    dataStore.load();
						    atributosWindow.hide();
						    
						}
			        });                   
                } else{
					Ext.MessageBox.alert('Error', 'Favor de llenar datos requeridos.');
				}             
	        }
        },{
            text: '<s:text name="productos.tabla5claves.btn.cancelar"/>',
            handler: function(){atributosWindow.hide();}
        }]
    });

	atributosWindow.show();
};
</script>