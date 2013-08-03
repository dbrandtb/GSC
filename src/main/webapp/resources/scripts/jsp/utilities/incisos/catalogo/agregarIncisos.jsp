<%@ include file="/taglibs.jsp"%>
<!-- LIBRARIES -->
<!-- SOURCE CODE -->
<script type="text/javascript">

    creaAltaCatalogoIncisos = function(dataStore) {
	
    // pre-define fields in the form
	var claveCatalogo = new Ext.form.TextField({
        fieldLabel: '<s:text name="productos.configIncisos.clave"/>',
        allowBlank: false,
        blankText : '<s:text name="productos.configIncisos.clave.req"/>',
        maxLength : '2',
   		maxLengthText : '<s:text name="productos.configIncisos.longitudMax"/>',
        name: 'claveCatalogo',
        anchor: '90%' 
    });
    
	var descripcionCatalogo = new Ext.form.TextField({
        fieldLabel: '<s:text name="productos.configIncisos.descripcion"/>',
        allowBlank: false,
        maxLength : '30',
   		maxLengthText : '<s:text name="productos.configIncisos.longitudDescripcion"/>',
        blankText : '<s:text name="productos.configIncisos.descripcion.req"/>',
        name: 'descripcionCatalogo',
        anchor: '90%'  
    });  
    
    var subIncisos = new Ext.form.Checkbox({
        fieldLabel: '<s:text name="productos.configIncisos.subinciso"/>',
        name: 'subIncisos',
        checked:false,                
                onClick:function(){
				            	if(this.getValue()){				            	
				            		this.setRawValue("S");				            	
				            	}
				            }
          
    });  
        
    var formPanel = new Ext.form.FormPanel({
  
        
        baseCls: 'x-plain',
        labelWidth: 75,
        url:'incisos/agregarInciso.action',
        defaultType: 'textfield',

        items: [
            claveCatalogo,
            descripcionCatalogo,
            subIncisos
    	]
    });

    // define window and show it in desktop
    var window = new Ext.Window({
        title: '<s:text name="productos.configIncisos.title.agregarInciso"/>',
        width: 300,
        height:150,
        //minWidth: 300,
        //minHeight: 150,
        layout: 'fit',
        plain:true,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        modal : true,
  
        items: formPanel,
        

buttons: [{
            text: '<s:text name="btn.guardar"/>', 
            handler: function() {
                // check form value 
                if (formPanel.form.isValid()) {
	 		        formPanel.form.submit({			      
			            waitTitle:'<s:text name="ventana.proceso.mensaje.titulo"/>',
					    waitMsg:'<s:text name="ventana.proceso.mensaje.proceso"/>',
			            failure: function(form, action) {					
						    Ext.MessageBox.alert('Status', 'Error al agregar nuevo inciso');
						},
						success: function(form, action) {						   
						    Ext.MessageBox.alert('Status', 'Inciso Agregado');
						    window.close();
						    dataStore.load();
						}
			        });                   
                } else{
					Ext.MessageBox.alert('Error', 'Favor de llenar datos requeridos');
				}             
	        }
        },{
            text: '<s:text name="btn.limpiar"/>',
            handler: function(){formPanel.form.reset();}
        },{
            text: '<s:text name="btn.cancelar"/>',
            handler: function(){window.close();}
        }]
    });

	window.show();
};	
</script>