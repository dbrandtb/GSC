Ext.require([ 'Ext.form.*', 'Ext.data.*', 'Ext.chart.*', 'Ext.grid.*', 'Ext.grid.Panel','Ext.layout.container.Column', 'Ext.selection.CheckboxModel' ]);

Ext.onReady(function() {

  Ext.create('Ext.form.Panel', {
    title: 'Revisi&oacute;n de Documento',
    width: 800,
    //height: 125,

    renderTo: Ext.getBody(),
    items:[
	    {
	      xtype: 'checkboxgroup',
	      fieldLabel: 'Hospitalizaci&oacute;n',
	      bodyPadding: 10,
	      columns: 2,
	      vertical: true,
	      itemId: 'hospitalizacion',
	      items: [
		  { boxLabel: 'Factura', name: 'hospitalizacion', inputValue: '1' },
		  { boxLabel: 'Informe de Atenci&oacute;n Hospitalaria y <br/> Cirug&iacute;a  (formato GS)', name: 'hospitalizacion', inputValue: '2', checked: true },
		  { boxLabel: 'Copia de los Resultados y/o Interpretaci&oacute;n <br/>Estudios de Laboratorio, Imagenologia, <br/> Gabinete y Otros', name: 'hospitalizacion', inputValue: '3' },	            
		  { boxLabel: 'Estado de Cuenta', name: 'hospitalizacion', inputValue: '4' },
		  //Si tipo de pago es directo
		    { boxLabel: 'Autorizaci&oacute;n de Servicios', name: 'hospitalizacion', inputValue: '5' },
		  //Si tipo de pago es pago Reembolso
		    { boxLabel: 'Aviso de Accidente y/o Enfermedad (Formato GS)', name: 'hospitalizacion', inputValue: '6' }
	      ]
	    },
	    {
	      xtype: 'checkboxgroup',
	      fieldLabel: 'Sala de Urgencia',
	      // Arrange radio buttons into two columns, distributed vertically
	      columns: 2,
	      vertical: true,
	      itemId: 'salaUrgencia',
	      items: [
		  { boxLabel: 'Factura', name: 'salaUrgencia', inputValue: '1' },
		  { boxLabel: 'Estado de Cuenta', name: 'salaUrgencia', inputValue: '2', checked: true },
		  { boxLabel: 'Copia de los Resultados y/o Interpretaci&oacute;n <br/>Estudios de Laboratorio, Imagenologia, <br/> Gabinete y Otros', name: 'salaUrgencia', inputValue: '3' },
		  //Si tipo de pago es directo
		    { boxLabel: 'Informe M&eacute;dico de Sala de Urgencia (Formato GS)', name: 'salaUrgencia', inputValue: '4' },
		  //Si tipo de pago es pago Reembolso
		    { boxLabel: 'Informe M&eacute;dico  (Formato GS)', name: 'salaUrgencia', inputValue: '5' },	            
		    { boxLabel: 'Aviso de Accidente y/o Enfermedad (Formato GS)', name: 'salaUrgencia', inputValue: '6' },
		    { boxLabel: 'Nota de Ingreso y Egresos', name: 'salaUrgencia', inputValue: '7' }
		]
	    },
	    {
	      xtype: 'checkboxgroup',
	      fieldLabel: 'Consulta MCP',
	      // Arrange radio buttons into two columns, distributed vertically
	      columns: 2,
	      vertical: true,
	      itemId: 'consultaMCP',
	      items: [
		  { boxLabel: 'Recibo de Honorarios y/o Factura', name: 'consultaMCP', inputValue: '1' },
		  { boxLabel: 'Informe M&eacute;dico MCP (Formato GS)', name: 'consultaMCP', inputValue: '2' },
		  //Si tipo de pago es pago Reembolso
		    { boxLabel: 'Aviso de Accidente y/o Enfermedad (Formato GS)', name: 'consultaMCP', inputValue: '3' }	            
	      ]
	    },
	    {
	      xtype: 'checkboxgroup',
	      fieldLabel: 'Consultas Médicos Especialista',
	      // Arrange radio buttons into two columns, distributed vertically
	      columns: 2,
	      vertical: true,
	      itemId: 'consultaMEspec',
	      items: [
		  { boxLabel: 'Recibo de Honorarios y/o Factura', name: 'consultaMEspec', inputValue: '1' },
		  //Si tipo de pago es directo
		    { boxLabel: 'Referencia (Formato GS)', name: 'consultaMEspec', inputValue: '2' },
		  //Si tipo de pago es pago Reembolso
		    { boxLabel: 'Aviso de Accidente y/o Enfermedad (Formato GS)', name: 'consultaMEspec', inputValue: '3' },
		    { boxLabel: 'Informe M&eacute;dico (Formato GS)', name: 'consultaMEspec', inputValue: '4' }	            
		]
	    },
	    {
	      xtype: 'checkboxgroup',
	      fieldLabel: 'Orden de Servicio',
	      // Arrange radio buttons into two columns, distributed vertically
	      columns: 2,
	      vertical: true,
	      itemId: 'ordenServ',
	      items: [
		  { boxLabel: 'Factura', name: 'ordenServ', inputValue: '1' },	            
		  { boxLabel: 'Estado de Cuenta', name: 'ordenServ', inputValue: '2' },
		  //Si tipo de pago es directo
		    { boxLabel: 'Orden de Servicio (Formato GS)', name: 'ordenServ', inputValue: '2' },
		    { boxLabel: 'Copia de los Resultados y/o Interpretaci&oacute;n', name: 'ordenServ', inputValue: '3' },
		  //Si tipo de pago es pago Reembolso
		    { boxLabel: 'Aviso de Accidente y/o Enfermedad (Formato GS)', name: 'ordenServ', inputValue: '5' },
		    { boxLabel: 'Orden del M&eacute;dico que est&aacute; solicitando los estudios', name: 'ordenServ', inputValue: '6' },
		    { boxLabel: 'Copia de los Resultados y/o interpretaci&oacute;n', name: 'ordenServ', inputValue: '7' }
	      ]
	    },
	    {
	      xtype: 'checkboxgroup',
	      fieldLabel: 'Honorario Quir&uacute;rgicos',
	      // Arrange radio buttons into two columns, distributed vertically
	      columns: 2,
	      vertical: true,
	      itemId: 'honorarioQuir',
	      items: [
		{ boxLabel: 'Recibo de Honorarios', name: 'honorarioQuir', inputValue: '1' },
		{ boxLabel: 'Autorización de Servicios', name: 'honorarioQuir', inputValue: '2' }
	      ]
	    },
	]/*,
	buttonAlign:'center',
	buttons: [{
	  text: 'Guardar'
	  ,icon:_CONTEXT+'/resources/fam3icons/icons/accept.png'
	  ,buttonAlign : 'center'
	  ,handler: function() {
	      if (panelClausula.form.isValid()) {
		panelClausula.form.submit({
		  waitMsg:'Procesando...',			        	
		    failure: function(form, action) {
			Ext.Msg.show({
			  title: 'ERROR',
			  msg: action.result.errorMessage,
			  buttons: Ext.Msg.OK,
			  icon: Ext.Msg.ERROR
			});
		    },
		    success: function(form, action) {
		      Ext.Msg.show({
			title: '&Eacute;XITO',
			msg: "La cl&aacute;usula se guardo correctamente",
			buttons: Ext.Msg.OK
		      });
		      panelClausula.form.reset();
		    }
		});
	      } else {
		Ext.Msg.show({
		  title: 'Aviso',
		  msg: 'Complete la informaci&oacute;n requerida',
		  buttons: Ext.Msg.OK,
		  icon: Ext.Msg.WARNING
		});
	      }
	  }
	},{
	    text: 'Cancelar',
	    icon:_CONTEXT+'/resources/fam3icons/icons/cancel.png',
	    buttonAlign : 'center',
	    handler: function() {
	      modificacionClausula.close();
	    }
	}
      ]*/
  });
  
});





/*Ext.onReady(function() {


    motivoRechazo= Ext.create('Ext.form.ComboBox',
    	    {
    	        id:'motivoRechazo',
    	        name:'params.motivoRechazo',
    	        fieldLabel: 'Motivo',
    	        //store: storeCirculoHospitalario,
    	        queryMode:'local',
    	        displayField: 'value',
    	        valueField: 'key',
    	        allowBlank:false,
    	        blankText:'El motivo es un dato requerido',
    	        editable:false,
    	        labelWidth : 150,
    	        width: 600,
    	        emptyText:'Seleccione un Motivo ...'
    	    });
    
    panelClausula= Ext.create('Ext.form.Panel', {
        id: 'panelClausula',
        width: 650,
        //url: _URL_INSERTA_CLAUSU,
        bodyPadding: 5,
        renderTo: Ext.getBody(),
        items: [
            	motivoRechazo
    	        ,{
    	            xtype: 'textarea'
	            	,fieldLabel: 'Descripci&oacute;n'
            		,labelWidth: 150
            		,width: 600
            		,name:'params.descripcion'
        			,height: 250
        			,allowBlank: false
        			,blankText:'La descripci&oacute;n es un dato requerido'
    	        }],
	        buttonAlign:'center',
	        buttons: [{
    		text: 'Guardar'
    		,icon:_CONTEXT+'/resources/fam3icons/icons/accept.png'
    		,buttonAlign : 'center',
    		handler: function() {
    	    	if (panelClausula.form.isValid()) {
    	    		panelClausula.form.submit({
    		        	waitMsg:'Procesando...',			        	
    		        	failure: function(form, action) {
    		        		Ext.Msg.show({
    	   	                    title: 'ERROR',
    	   	                    msg: action.result.errorMessage,
    	   	                    buttons: Ext.Msg.OK,
    	   	                    icon: Ext.Msg.ERROR
    	   	                });
    					},
    					success: function(form, action) {
    						Ext.Msg.show({
    	   	                    title: '&Eacute;XITO',
    	   	                    msg: "La cl&aacute;usula se guardo correctamente",
    	   	                    buttons: Ext.Msg.OK
    	   	                });
    						panelClausula.form.reset();
    						
    					}
    				});
    			} else {
    				Ext.Msg.show({
    	                   title: 'Aviso',
    	                   msg: 'Complete la informaci&oacute;n requerida',
    	                   buttons: Ext.Msg.OK,
    	                   icon: Ext.Msg.WARNING
    	               });
    			}
    		}
    	},{
    	    text: 'Cancelar',
    	    icon:_CONTEXT+'/resources/fam3icons/icons/cancel.png',
    	    buttonAlign : 'center',
    	    handler: function() {
    	        modificacionClausula.close();
    	    }
    	}
    	]
    });
    
    
    
	modificacionClausula = Ext.create('Ext.window.Window',
	        {
	            title        : 'Rechazar Reclamaci&oacute;n'
	            ,modal       : true
	            ,buttonAlign : 'center'
	            ,width		 : 650
	            ,height      : 375
	            ,items       :
	            [
	             	panelClausula
	        ]
	        }).show();
});*/