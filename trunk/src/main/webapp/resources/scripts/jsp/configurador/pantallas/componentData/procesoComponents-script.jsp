<%@ include file="/taglibs.jsp"%>

    // **************************************************************************************************   
    // Elementos de Proceso ....
    // **************************************************************************************************



ScreenProcesoComponents = {
    
	
    
    getComponents : function() {

		var data = [];

		// Form elements
		
        
		
            <s:component template="master-items.vm" templateDir="templates" theme="components">
                <s:param name="master" value="#session.elementos_proceso_master" />                
            </s:component>            
        
        
   
		return data;

	}
};