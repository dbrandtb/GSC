<%@ include file="/taglibs.jsp"%>
<!-- LIBRARIES -->
<!-- SOURCE CODE -->
<script type="text/javascript">

creaAtributosVariables = function(dsCatalogoAtributos) {

    // pre-define fields in the form
	var descripcion = new Ext.form.TextField({
        fieldLabel: '<s:text name="productos.configRoles.persona.descripcion"/>',
        allowBlank: false,
        blankText : '<s:text name="productos.configRoles.descripcion.req"/>',
        name: 'descripcionAtributoVariable',
        anchor: '90%' 
    });
    
    var functionRadioAlfa= new Ext.form.Radio({
        name:'formato',
        fieldLabel: '<s:text name="productos.configRoles.persona.formato"/>',
        //labelSeparator : "",
        boxLabel: '<s:text name="productos.configRoles.persona.alfaNumerico"/>',
        checked:true,
  	  	onClick: function(){			            		
       				if(this.getValue()){					            	
            			this.setRawValue("A");
				 	}
			    }
        });   
            
    var functionRadioNumeric= new Ext.form.Radio({
        name:'formato',
        labelSeparator : "",
        boxLabel: '<s:text name="productos.configRoles.persona.numerico"/>',
        hideLabel:true,
  	  	onClick:function(){			            		
        			if(this.getValue()){					            	            			
            			this.setRawValue("N");
				    }
				}
        });  
            
    var functionRadioPercent= new Ext.form.Radio({
        name:'formato',
        labelSeparator : "",
        boxLabel: '<s:text name="productos.configRoles.persona.porcentaje"/>',
        //hideLabel:true,
  	  	onClick: function(){			            		
        			if(this.getValue()){            			
						this.setRawValue("P");
				   	}
			    }
        }); 
            
    var functionRadioDate= new Ext.form.Radio({
        name: 'formato',
		boxLabel: '<s:text name="productos.configRoles.persona.fecha"/>',
		hideLabel:true,
  	  	onClick: function(){			            		
        			if(this.getValue()){         
				        this.setRawValue("F");
				        }
			        }
    });
    
    var limiteMaximo = new Ext.form.NumberField({
        fieldLabel: '<s:text name="productos.configRoles.persona.Lmaximo"/>',
        //allowBlank: false,
        allowDecimals : false,
		allowNegative : false,
		maxLength: 2, 
		maxLengthText:'<s:text name="productos.configRoles.limite.valida"/>',
        name: 'limiteMaximo',
        width:40 
    });  
    
    var limiteMinimo = new Ext.form.NumberField({
        fieldLabel: '<s:text name="productos.configRoles.persona.Lminimo"/>',
        //allowBlank: false,
	    allowDecimals : false,
	    allowNegative : false,
	    maxLength: 2,
	    maxLengthText:'<s:text name="productos.configRoles.limite.valida"/>',
        name: 'limiteMinimo',
        width:40 
    });
        
    var formPanel = new Ext.form.FormPanel({
  
        baseCls: 'x-plain',
        labelWidth: 70,
        url:'rol/AgregaAtributoVariableCatalogo.action',       
        //collapsed : false,

        items: [
            descripcion,
            {
        		layout:'column',
        		border: false,
        		baseCls: 'x-plain',
            		items:[{
				    		columnWidth:.65,
                			layout: 'form',
                			border: false,
                			baseCls: 'x-plain',
				            items: [
				                	functionRadioAlfa
				            ]
        			},{
               				columnWidth:.35,
			               	layout: 'form',
			               	border: false,
			               	baseCls: 'x-plain',
               				items: [
               						functionRadioNumeric
               				]
				    }]
        		
		    },{
				   		layout: 'column',
				   		border: false,
				   		baseCls: 'x-plain',
				   		labelAlign:"right",
                		items: [{
                					columnWidth:.65,
				                   	layout:'form',
				                   	border: false,
				                   	baseCls: 'x-plain',
				                   	items:[
				                    		functionRadioPercent
				                   	]
				                },{
                						columnWidth:.35,
                						layout:'form',
                						border: false,
                						baseCls: 'x-plain',
                						items:[
                							functionRadioDate
                						]
				         		}
				         ]
			},     		         
            limiteMaximo,
            limiteMinimo
    	]
    });

    // define window and show it in desktop
    var atributosWindow = new Ext.Window({
        title: '<s:text name="productos.configRoles.title.atributoPersona"/>',
        width: 350,
        height:200,
        minWidth: 350,
        minHeight: 200,
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
						    Ext.MessageBox.alert('Status', 'Atributo variable no agregado');
						},
						success: function(form, action) {
						    //Ext.MessageBox.alert('Status', 'Atributo Variable agregado');
						    atributosWindow.close();
						    dsCatalogoAtributos.load();
						    
						    
						}
			        });                   
                } else{
					Ext.MessageBox.alert('Error', 'Favor de llenar campos requeridos.');
				}             
	        }
        },{
            text: '<s:text name="productos.configRoles.btn.cancelar"/>',
            handler: function(){atributosWindow.close();}
        }]
    });

	atributosWindow.show();
};
</script>