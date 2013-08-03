<%@ include file="/taglibs.jsp"%>
<!-- LIBRARIES -->
<!-- SOURCE CODE -->
<script type="text/javascript">

CreaAtributo = function(store,row) {

if(row!=null){

	 var rec = store.getAt(row);
 
   store.on('load', function(){    						                                                   
                           Ext.getCmp('agrega-atributos-tabla-5-claves').getForm().loadRecord(rec);                       
                           });
	store.load(); 	

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
					    	triggerAction: 'all',
						    emptyText:'<s:text name="productos.tabla5claves.atributosVar.select.formato"/>',
					    	selectOnFocus:true,
    						blankText : '<s:text name="productos.tabla5claves.atributosVar.valida.formato.req"/>',
						    fieldLabel: '<s:text name="productos.tabla5claves.AtributosVar.formato"/>',
					    	name:"descripcionFormatoAtributo"
					    	
			});
    // pre-define fields in the form
	var descripcion = new Ext.form.TextField({
        fieldLabel: '<s:text name="productos.tabla5claves.AtributosVar.descripcion"/>',
        allowBlank: false,
        blankText : '<s:text name="productos.tabla5claves.atributosVar.valida.descripcion.req"/>',
        name: 'descripcionAtributo',
        anchor: '100%' 
    });
 
    var limiteMaximo = new Ext.form.NumberField({
        fieldLabel: '<s:text name="productos.tabla5claves.AtributosVar.maximo"/>',
        allowBlank: false,
        blankText : '<s:text name="productos.tabla5claves.atributosVar.valida.maximo.req"/>',
        name: 'maximoAtributo',
        anchor: '50%' 
    });  
    
    var limiteMinimo = new Ext.form.NumberField({
        fieldLabel: '<s:text name="productos.tabla5claves.AtributosVar.minimo"/>',
        allowBlank: false,
        blankText : '<s:text name="productos.tabla5claves.atributosVar.valida.minimo.req"/>',
        name: 'minimoAtributo',
        anchor: '50%' 
    });
        
    var formPanel = new Ext.form.FormPanel({
  		id:'agrega-atributos-tabla-5-claves',
        baseCls: 'x-plain',
        labelWidth: 115,
        url:'<s:url namespace="tablaCincoClaves" action="InsertarAtributo" includeParams="none"/>',
        //url:'/tablaCincoClaves/InsertarAtributo.action',
        //defaultType: 'textfield',
        //collapsed : false,

        items: [
            descripcion,
            formato,
            limiteMinimo,
            limiteMaximo
    	]
    });

    // define window and show it in desktop
    var atributosWindow = new Ext.Window({
        title: '<s:text name="productos.tabla5claves.atributoVar.titulo"/>',
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
						    Ext.MessageBox.alert('Error', 'Atributo no agregado');
						},
						success: function(form, action) {
						    Ext.MessageBox.alert('Status', 'Atributo agregado');
						    store.load();
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