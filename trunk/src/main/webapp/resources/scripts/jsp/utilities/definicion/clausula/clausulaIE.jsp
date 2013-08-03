<%@ include file="/taglibs.jsp"%>
<!-- LIBRARIES -->
<!-- SOURCE CODE -->
<script type="text/javascript">
    
ClausulaIE = function(dataStore, selectedId) {

   var clave= new Ext.form.TextField({
				    maxLengthText:'maximo 4 caracteres',
				    maxLength:'4',
				    maxLengthText: '<s:text name="def.alta.catalogo.clausula.longitudMax"/>',
                    fieldLabel: '<s:text name="def.alta.catalogo.clausula.clave"/>',
                    name: 'clave',
                    allowBlank:false,
					blankText : '<s:text name="def.alta.catalogo.clausulas.valida.clave"/>',
                    anchor:'40%'   
   });
   var descripcion= new Ext.form.TextField({
                    fieldLabel: '<s:text name="def.alta.catalogo.clausula.descripcion"/>',
                    name: 'clausulaDescripcion',
                    maxLength:'60',
                    allowBlank:false,
					blankText : '<s:text name="def.alta.catalogo.clausulas.valida.descripcion"/>',
                    anchor:'98%'   
   });
   var htmledit= new Ext.form.HtmlEditor({
    		id:'bio',
            fieldLabel:'Texto',
            name:'linea',
            height:150,
            anchor:'98%'
   });
    var formPanel = new Ext.FormPanel({
    	id:'another-id',
        labelAlign: 'top',
        frame:true,
        reader: new Ext.data.JsonReader({			//el edit aun no esta probado
            root: ''
        }, ['clave','descripcion', 'linea']
        ),
        
        bodyStyle:'padding:5px 5px 0',
        width: 600,
        url:'definicion/AgregarClausula.action',
        items: [{xtype:'hidden', id:'hidden-bandera-editar-agregar-clausula',name:'banderaEditar', value:'1'},
        	clave,
        	descripcion,
        	htmledit
        ]
    });
 	if(selectedId!=null){
 	
		 Ext.getCmp('another-id').getForm().loadRecord(selectedId);  
    }
    // define window and show it in desktop
    var window = new Ext.Window({
        title: '<s:text name="def.alta.clausulas.title"/>',
        width: 600,
        height:400,
        minWidth: 300,
        minHeight: 150,
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
	 		        formPanel.form.submit({			      
			            waitTitle:'<s:text name="ventana.proceso.clausula.mensaje.titulo"/>',
			            waitMsg:'<s:text name="ventana.proceso.clausula.mensaje.proceso"/>',
			            failure: function(form, action) {
						    Ext.MessageBox.alert('Error', "Clausula no agregada");
						},
						success: function(form, action) {
						    Ext.MessageBox.alert('Status', "Clausula agregada correctamente");
						    window.close();
						    dataStore.load({params:{start:0, limit:10}});
						}
			        });                   
                } else{
					Ext.MessageBox.alert('Error', 'Existen errores al agregar una Clausula, por favor verifiquelos e intente de nuevo!');
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