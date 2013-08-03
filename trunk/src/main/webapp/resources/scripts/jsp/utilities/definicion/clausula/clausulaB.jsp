<%@ include file="/taglibs.jsp"%>
<!-- LIBRARIES -->
<!-- SOURCE CODE -->
<script type="text/javascript">
DeleteDemouser = function(store,selectedId, sel) {

    // get the selected items
   Ext.MessageBox.confirm('Mensaje','Esta seguro de querer eliminar este elemento?', function(btn) {
           if(btn == 'yes'){ 
			     var jsonData = "[{\"id\":\"" + selectedId + "\"}]";
     
			     	store.remove(sel);        
           			store.load({params:{start:0, limit:10, delData:jsonData}});  
        	}
       } 
      );
};      
</script>