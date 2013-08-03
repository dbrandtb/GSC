<%@ include file="/taglibs.jsp"%>
<!-- LIBRARIES -->

<!-- SOURCE CODE -->

<script type="text/javascript">
   agregarValoresGrid = function(store) {

    // pre-define fields in the form
    
    var valorClave= new Ext.form.TextField({
                name:'valorClave',
                fieldLabel: '<s:text name="def.datos.variables.listas.valores.valores.clave"/>',
                allowBlank: false,
                width: 80
            	});
            
	var ValorDescripcion = new Ext.form.TextField({
        		name: 'valorDescripcion',
        		fieldLabel: '<s:text name="def.datos.variables.listas.valores.valores.descripcion"/>',
        		allowBlank: false,
        		width: 160  
    			});   
	
    
        
    var formPanel = new Ext.form.FormPanel({
  
        baseCls: 'x-plain',
        labelWidth: 115,
        url:'atributosVariables/CargaManual.action',
        defaultType: 'textfield',
        //collapsed : false,

        items: [
            new Ext.form.TextField({
                name:'valorClave',
                fieldLabel: '<s:text name="def.datos.variables.listas.valores.valores.clave"/>',
                allowBlank: false,
                blankText : '<s:text name="def.datos.variables.listas.valores.valida.valor.cve.vacio"/>',
                width: 130
            	}),
            new Ext.form.TextField({
        		name: 'valorDescripcion',
        		fieldLabel: '<s:text name="def.datos.variables.listas.valores.valores.descripcion"/>',
        		allowBlank: false,
        		blankText : '<s:text name="def.datos.variables.listas.valores.valida.valor.desc.vacio"/>',
        		width: 130  
    			})
    	]
    });

    // define window and show it in desktop
    var agregarValoresGridWindow = new Ext.Window({
        title: '<s:text name="lista.valores.datos.variables.subtitulo.cargaManual"/>',
        width: 350,
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
            text: '<s:text name="btn.datos.variables.listas.valores.guardar"/>', 
            handler: function() {
                // check form value 
                if (formPanel.form.isValid()) {
	 		        formPanel.form.submit({			      
			            waitTitle:'<s:text name="ventana.datos.variables.listas.valores.proceso.mensaje.titulo"/>',
			            waitMsg:'<s:text name="ventana.datos.variables.listas.valores.proceso.mensaje"/>',
			            failure: function(form, action) {
						    Ext.MessageBox.alert('Status', 'Elemento ya existe');
						},
						success: function(form, action) {
						    Ext.MessageBox.alert('Status', 'Elemento agregado');
						    agregarValoresGridWindow.close();
						    store.load();
						    //createGrid();
						    
						}
			        });                   
                } else{
					Ext.MessageBox.alert('Error', 'Favor de llenar datos requeridos.');
				}             
	        }
        },{
            text: '<s:text name="btn.datos.variables.listas.valores.cancelar"/>',
            handler: function(){
            	agregarValoresGridWindow.close();
            }
        }]
    });

	agregarValoresGridWindow.show();
};

</script>