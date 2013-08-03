<%@ include file="/taglibs.jsp"%>
<!-- LIBRARIES -->
<!-- SOURCE CODE -->
<script type="text/javascript">

//***********
eliminaAtributoVariableRol = function(store,row,rolesForm) {

if(row!=null){
	var rec = store.getAt(row);
    var codigoAtributoRol= rec.get('codigoAtributoVariable');    
	//alert("codigoAtributo"+codigoAtributoRol);
}
    // get the selected items
   Ext.MessageBox.confirm('Mensaje','Esta seguro que desea eliminar este elemento?', function(btn) {
           if(btn == 'yes'){ 
           
			     
			     rolesForm.form.load({	
			     url:'rol/EliminaAtributoVariableRol.action'+'?codigoAtributoVariable='+codigoAtributoRol,
			     waitTitle:'<s:text name="ventana.configRoles.proceso.mensaje.titulo"/>',
				 waitMsg:'<s:text name="ventana.configRoles.proceso.mensaje"/>',
			            failure: function(form, action) {
						    Ext.MessageBox.alert('Status', 'Elemento eliminado');						    
						    store.load();
						},
						success: function(form, action) {
						    Ext.MessageBox.alert('Status', 'Elemento eliminado');						    
						    store.load();
						    
						}
        	 }); 
        	 
        	}
       } 
      );
};      
//************

</script>