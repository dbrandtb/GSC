<%@ include file="/taglibs.jsp"%>
<!-- LIBRARIES -->
<!-- SOURCE CODE -->

<script type="text/javascript">
creaCatalogoTipoObjeto= function(dsCatalogoObjeto) {

	var descripcion = new Ext.form.TextField({
        fieldLabel: '<s:text name="productos.configObjetos.descripcion"/>',
        allowBlank: false,
        blankText : '<s:text name="productos.configObjetos.descripcion.req"/>',
        name: 'descripcionObjeto',
        anchor: '90%'  
    });  
    
        
    var formPanel = new Ext.form.FormPanel({
  
        baseCls: 'x-plain',
        labelWidth: 75,
        url:'tipoObjeto/AgregaTipoObjetoCatalogo.action',
        defaultType: 'textfield',
        //collapsed : false,

        items: [
            descripcion
    	]
    });

    // define window and show it in desktop
    var objetosWindow = new Ext.Window({
        title: '<s:text name="productos.configObjetos.title.catalogoTipoObjetos"/>',
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
            text: '<s:text name="productos.configObjetos.btn.guardar"/>', 
            handler: function() {
                // check form value 
                if (formPanel.form.isValid()) {
	 		        formPanel.form.submit({			      
			            waitTitle:'<s:text name="ventana.configObjetos.proceso.mensaje.titulo"/>',
					    waitMsg:'<s:text name="ventana.configObjetos.proceso.mensaje"/>',
			            failure: function(form, action) {
						    Ext.MessageBox.alert('Status', 'Tipo de objeto no agregado al catalogo');
						},
						success: function(form, action) {
						    Ext.MessageBox.alert('Status', 'Tipo de objeto agregado al catalogo');
						    objetosWindow.close();
						    dsCatalogoObjeto.load();
						    
						    
						}
			        });                   
                } else{
					Ext.MessageBox.alert('Error', 'Favor de llenar campos requeridos.');
				}             
	        }
        },{
            text: '<s:text name="productos.configObjetos.btn.cancelar"/>',
            handler: function(){
            				objetosWindow.close();
            		 }
        }]
    });

	objetosWindow.show();
};
</script>