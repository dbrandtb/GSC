<%@ include file="/taglibs.jsp"%>
<!-- LIBRARIES -->
<!-- SOURCE CODE -->

<script type="text/javascript">
creaCatalogoDeRoles= function(dsCatalogoRol) {

    // pre-define fields in the form
	/*var clave = new Ext.form.TextField({
        fieldLabel: 'Clave',
        allowBlank: false,
        name: 'clave',
        anchor: '90%' 
    });
    */
	var descripcion = new Ext.form.TextField({
        fieldLabel: '<s:text name="productos.configRoles.descripcion"/>',
        allowBlank: false,
        blankText : '<s:text name="productos.configRoles.descripcion.req"/>',
        name: 'descripcion',
        anchor: '90%'  
    });  
    
        
    var formPanel = new Ext.form.FormPanel({
  
        baseCls: 'x-plain',
        labelWidth: 75,
        url:'rol/AgregaRolCatalogo.action',
        defaultType: 'textfield',
        //collapsed : false,

        items: [
            //clave,
            descripcion
    	]
    });

    // define window and show it in desktop
    var rolWindow = new Ext.Window({
        title: '<s:text name="productos.configRoles.title.catalogoRoles"/>',
        width: 300,
        height:110,
        //minWidth: 300,
        //minHeight: 150,
        layout: 'fit',
        plain:true,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        modal : true,
  
        items: formPanel,
        

buttons: [{
            text: '<s:text name="productos.configRoles.btn.guardar"/>', 
            handler: function() {
                // check form value 
                if (formPanel.form.isValid()) {
	 		        formPanel.form.submit({			      
			            waitTitle:'<s:text name="ventana.configRoles.proceso.mensaje.titulo"/>',
					    waitMsg:'<s:text name="ventana.configRoles.proceso.mensaje"/>',
			            failure: function(form, action) {
						    Ext.MessageBox.alert('Status', 'Rol no agregado al catalogo');
						},
						success: function(form, action) {
						    Ext.MessageBox.alert('Status', 'Rol agregado al catalogo');
						    rolWindow.close();
						    dsCatalogoRol.load();
						    
						    
						}
			        });                   
                } else{
					Ext.MessageBox.alert('Error', 'Favor de llenar campos requeridos.');
				}             
	        }
        },{
            text: '<s:text name="productos.configRoles.btn.cancelar"/>',
            handler: function(){
            				rolWindow.close();
            		 }
        }]
    });

	rolWindow.show();
};
</script>