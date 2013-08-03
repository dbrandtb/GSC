<%@ include file="/taglibs.jsp"%>

    // **************************************************************************************************   
    // Elementos Layout ....
    // **************************************************************************************************


ScreenLayoutComponents = {
    
    getComponents: function(){
        var data = [];       
        
        //FORM PANEL
        data.push(
			[MENU_LAYOUTS_PANELS, MENU_LAYOUTS_CONTROL_FORMPANEL,
				MENU_LAYOUTS_CONTROL_FORMPANEL_TOOLTIP,
				{xtype:'form', title:MENU_LAYOUTS_CONTROL_FORMPANEL_TITLE, autoScroll:'true'}]
		);
		
		
		// Border layout
        data.push([MENU_LAYOUTS_LAYOUTS, 'Border Layout', 'Layout with regions', function(add,parent) {
                var w = new Ext.Window({
                    title:"Border Layout",
                    width:550,
                    height:400,
                    layout:'fit',
                    items:[{
                        autoScroll:true,
                        xtype:"form",
                        frame:true,
                        id: "borderLayoutFormId", 
                        defaults:{
                            style:"margin:10px"
                        },
                        items:[{
                                xtype:"fieldset",
                                title:"Center",
                                autoHeight:true,
                                items:[{
                                        xtype:"textfield",
                                        fieldLabel:"Title",
                                        name:"title_center",
                                        width:299
                                   		},
                                    	{
                                        layout:"form",
                                        items:[{
                                                xtype:"numberfield",
                                                fieldLabel:"Alto (px)",
                                                name:"height_center",
                                                allowBlank: false,
                                                allowDecimals:false,
                                                allowNegative:false,
                                                width:66
                                               }]
                                        }
                                      ]
                            },{
                                xtype:"fieldset",
                                title:"Add north region",
                                autoHeight:true,
                                checkboxToggle:true,
                                collapsed:true,
                                checkboxName:"active_north",
                                items:[{
                                        xtype:"textfield",
                                        fieldLabel:"Title",
                                        name:"title_north",
                                        width:299
                                    },{
                                        layout:"table",
                                        items:[{
                                                layout:"form",
                                                items:[{
                                                        xtype:"numberfield",
                                                        fieldLabel:"Height (px)",
                                                        name:"height_north",
                                                        allowDecimals:false,
                                                        allowNegative:false,
                                                        width:66
                                                    }]
                                            },{
                                                layout:"form",
                                                hideLabels:true,
                                                style:"margin-left:10px",
                                                items:[{
                                                        xtype:"checkbox",
                                                        name:"split_north",
                                                        boxLabel:"Split"
                                                    }]
                                            },{
                                                layout:"form",
                                                hideLabels:true,
                                                style:"margin-left:10px",
                                                items:[{
                                                        xtype:"checkbox",
                                                        name:"collapsible_north",
                                                        boxLabel:"Collapsible"
                                                    }]
                                            },{
                                                layout:"form",
                                                hideLabels:true,
                                                style:"margin-left:10px",
                                                items:[{
                                                        xtype:"checkbox",
                                                        name:"titleCollapse_north",
                                                        boxLabel:"TitleCollapse"
                                                    }]
                                            }]
                                    }]
                            },{
                                xtype:"fieldset",
                                title:"Add south region",
                                autoHeight:true,
                                checkboxToggle:true,
                                collapsed:true,
                                checkboxName:"active_south",
                                items:[{
                                        xtype:"textfield",
                                        fieldLabel:"Title",
                                        name:"title_south",
                                        width:299
                                    },{
                                        layout:"table",
                                        items:[{
                                                layout:"form",
                                                items:[{
                                                        xtype:"numberfield",
                                                        fieldLabel:"Height (px)",
                                                        name:"height_south",
                                                        allowDecimals:false,
                                                        allowNegative:false,
                                                        width:66
                                                    }]
                                            },{
                                                layout:"form",
                                                hideLabels:true,
                                                style:"margin-left:10px",
                                                items:[{
                                                        xtype:"checkbox",
                                                        name:"split_south",
                                                        boxLabel:"Split"
                                                    }]
                                            },{
                                                layout:"form",
                                                hideLabels:true,
                                                style:"margin-left:10px",
                                                items:[{
                                                        xtype:"checkbox",
                                                        name:"collapsible_south",
                                                        boxLabel:"Collapsible"
                                                    }]
                                            },{
                                                layout:"form",
                                                hideLabels:true,
                                                style:"margin-left:10px",
                                                items:[{
                                                        xtype:"checkbox",
                                                        name:"titleCollapse_south",
                                                        boxLabel:"TitleCollapse"
                                                    }]
                                            }]
                                    }]
                            },{
                                xtype:"fieldset",
                                title:"Add west region",
                                autoHeight:true,
                                checkboxToggle:true,
                                collapsed:true,
                                checkboxName:"active_west",
                                items:[{
                                        xtype:"textfield",
                                        fieldLabel:"Title",
                                        name:"title_west",
                                        width:299
                                    },{
                                        layout:"table",
                                        items:[{
                                                layout:"form",
                                                items:[{
                                                        xtype:"numberfield",
                                                        fieldLabel:"Width (px)",
                                                        name:"width_west",
                                                        allowDecimals:false,
                                                        allowNegative:false,
                                                        width:66
                                                    }]
                                            },{
                                                layout:"form",
                                                hideLabels:true,
                                                style:"margin-left:10px",
                                                items:[{
                                                        xtype:"checkbox",
                                                        name:"split_west",
                                                        boxLabel:"Split"
                                                    }]
                                            },{
                                                layout:"form",
                                                hideLabels:true,
                                                style:"margin-left:10px",
                                                items:[{
                                                        xtype:"checkbox",
                                                        name:"collapsible_west",
                                                        boxLabel:"Collapsible"
                                                    }]
                                            },{
                                                layout:"form",
                                                hideLabels:true,
                                                style:"margin-left:10px",
                                                items:[{
                                                        xtype:"checkbox",
                                                        name:"titleCollapse_west",
                                                        boxLabel:"TitleCollapse"
                                                    }]
                                            }]
                                    }]
                            },{
                                xtype:"fieldset",
                                title:"Add east region",
                                autoHeight:true,
                                checkboxToggle:true,
                                collapsed:true,
                                checkboxName:"active_east",
                                items:[{
                                        xtype:"textfield",
                                        fieldLabel:"Title",
                                        name:"title_east",
                                        width:299
                                    },{
                                        layout:"table",
                                        items:[{
                                                layout:"form",
                                                items:[{
                                                        xtype:"numberfield",
                                                        fieldLabel:"Width (px)",
                                                        name:"width_east",
                                                        allowDecimals:false,
                                                        allowNegative:false,
                                                        width:66
                                                    }]
                                            },{
                                                layout:"form",
                                                hideLabels:true,
                                                style:"margin-left:10px",
                                                items:[{
                                                        xtype:"checkbox",
                                                        name:"split_east",
                                                        boxLabel:"Split"
                                                    }]
                                            },{
                                                layout:"form",
                                                hideLabels:true,
                                                style:"margin-left:10px",
                                                items:[{
                                                        xtype:"checkbox",
                                                        name:"collapsible_east",
                                                        boxLabel:"Collapsible"
                                                    }]
                                            },{
                                                layout:"form",
                                                hideLabels:true,
                                                style:"margin-left:10px",
                                                items:[{
                                                        xtype:"checkbox",
                                                        name:"titleCollapse_east",
                                                        boxLabel:"TitleCollapse"
                                                    }]
                                            }]
                                    }]
                            }],
                        buttons:[{
                            text:'Ok',
                            scope:this,
                            handler:function() {
                            
                            if(Ext.getCmp('borderLayoutFormId').form.isValid())
                            {
                                var values = w.items.first().form.getValues();
                                w.close();
                                
                                var heightPanel = values.height_center;
                                var config = {layout:'border', height: parseInt(heightPanel, 10), items:[]};
                                
                                config.items.push({region:'center',title:values.title_center||null, layout:'form'});
                                Ext.each(['north','south','west','east'], function(r) {
                                    if (values['active_'+r]) {
                                        
                                    	if(!isNaN(parseInt(values['width_'+r], 10))){
                                    		config.items.push({
                                            	region        : r,
                                            	title         : values['title_'+r]||null,
                                            	width         : parseInt(values['width_'+r], 10),
                                            	split         :   true, //(values['split_'+r]?true:null),
                                            	collapsible   :   true,   //(values['collapsible_'+r]?true:null),
                                            	titleCollapse :   true, //(values['titleCollapse_'+r]?true:null),
                                            	layout        : 'form'  
                                        	});
                                    	}else if(!isNaN(parseInt(values['height_'+r], 10))){
	                                    	config.items.push({
                                            	region        : r,
                                            	title         : values['title_'+r]||null,
                                            	height        : parseInt(values['height_'+r], 10),
                                            	split         :   true, //(values['split_'+r]?true:null),
                                            	collapsible   :   true,   //(values['collapsible_'+r]?true:null),
                                            	titleCollapse :   true, //(values['titleCollapse_'+r]?true:null),
                                            	layout        : 'form'  
                                        	});
                                    	}else {
	                                    	config.items.push({
                                            	region        : r,
                                            	title         : values['title_'+r]||null,
                                            	split         :   true, //(values['split_'+r]?true:null),
                                            	collapsible   :   true,   //(values['collapsible_'+r]?true:null),
                                            	titleCollapse :   true, //(values['titleCollapse_'+r]?true:null),
                                            	layout        : 'form'  
                                        	});
	                                    
                                    	}	
                                    
                                    }
                                });
                                if (parent) { parent.layout = 'fit'; }
                                
                                var configTotal = {layout:'form', buttonAlign: 'center', items:[]};
                                configTotal.items.push(config);
                                add.call(this, configTotal);
                            }else{ 
                            	Ext.MessageBox.alert('Aviso', 'Complete la informaci&oacute;n requerida');
                            }
                            
                            }
                        },{
                            text:'Cancel',
                            handler:function() {w.close();}
                        }]
                    }]
                });
                w.show();
            }]);
		
        
        
        // Column Layout
        data.push([MENU_LAYOUTS_LAYOUTS, 'Column Layout', 'Layout of columns', function(add,parent) {
            var w = new Ext.Window({
        width:425,
        height:349,
        layout:"fit",
        title:"Nuevo Column Layout",
        items:[{
            xtype:"form",
            frame:true,
            items:[{
                        layout:"form",
                        items:[{
                        xtype:"numberfield",
                        fieldLabel:"Altura (px)",
                        name:"height_component",
                        allowDecimals:false,
                        allowNegative:false,
                        width:66
                       }]
                },{
                columns:"3",
                layout:"table",
                layoutConfig:{
                  columns:3
                },
                defaults:{
                  style:"margin:2px"
                },
                items:[{
                    html:"Columna"
                  },{
                    html:"Tamaño *"
                  },{
                    html:"Titulo **"
                  },{
                    xtype:"checkbox",
                                        name:'active_1'
                  },{
                    xtype:"textfield",
                    maskRe:/[0-9%]/,
                    width:53,
                                        name:'size_1'
                  },{
                    xtype:"textfield",
                                        name:'title_1'
                  },{
                    xtype:"checkbox",
                                        name:'active_2'
                  },{
                    xtype:"textfield",
                    maskRe:/[0-9%]/,
                    width:53,
                                        name:'size_2'
                  },{
                    xtype:"textfield",
                                        name:'title_2'
                  },{
                    xtype:"checkbox",
                                        name:'active_3'
                  },{
                    xtype:"textfield",
                    maskRe:/[0-9%]/,
                    width:53,
                                        name:'size_3'
                  },{
                    xtype:"textfield",
                                        name:'title_3'
                  },{
                    xtype:"checkbox",
                                        name:'active_4'
                  },{
                    xtype:"textfield",
                    maskRe:/[0-9%]/,
                    width:53,
                                        name:'size_4'
                  },{
                    xtype:"textfield",
                                        name:'title_4'
                  },{
                    xtype:"checkbox",
                                        name:'active_5'
                  },{
                    xtype:"textfield",
                    maskRe:/[0-9%]/,
                    width:53,
                                        name:'size_5'
                  },{
                    xtype:"textfield",
                                        name:'title_5'
                  },{
                    xtype:"checkbox",
                                        name:'active_6'
                  },{
                    xtype:"textfield",
                    maskRe:/[0-9%]/,
                    width:53,
                                        name:'size_6'
                  },{
                    xtype:"textfield",
                                        name:'title_6'
                  }]
              },{
                                html:"* Tamaño: Puede manejarse como el porcentaje en base al ancho de la pantalla (i.e. 33%),"+
                                    "en pixeles (i.e. 120), o vacío (para un tamaño auto)<br/>"+
                                    "** Titulo : No se fija en caso de dejar el campo vacío"
                            }]
          }],
                    buttons:[{
                        text:'Ok',
                        scope:this,
                        handler:function() {
                            var values = w.items.first().form.getValues();
                            w.close();
                            
                            var heightPanel = values.height_component;
                            var config;
                            
                            if(Ext.isEmpty(heightPanel)) config = {layout:'column', items:[]};
                            	else config = {layout:'column', height: parseInt(heightPanel, 10), items:[]};
                            
                            Ext.each([1,2,3,4,5,6], function(r) {
                                if (values['active_'+r]) {
                                    var item = {layout:"form",title:values['title_'+r]||null};
                                    var widthVal = values['size_'+r];
                                    var width = parseInt(widthVal,10);
                                    
                                    if (!isNaN(width)) {
                                        if (widthVal.charAt(widthVal.length-1) == '%') {
                                            item.columnWidth = width/100;
                                        } else {
                                            item.width = width;
                                        }
                                    }
                                    config.items.push(item);
                                }
                            });
                            
                            var configTotal = {layout:'form', buttonAlign: 'center', items:[]};
                            configTotal.items.push(config);
                            add.call(this, configTotal);
                            
                        }
                    },{
                        text:'Cancel',
                        handler:function() {w.close();}
                    }]
            });
            w.show();

        }]);

                    
		
		
		
		

		// Tab Panel
		data.push([MENU_LAYOUTS_PANELS, MENU_LAYOUTS_CONTROL_TABPANEL, MENU_LAYOUTS_CONTROL_TABPANEL_TOOLTIP, function(add,parent) {
			var w = new Ext.Window({
        width:586,
        height:339,
        title: MENU_LAYOUTS_CONTROL_TABPANEL_WINDOW_TITLE,
        items:[{
            xtype:"form",
            frame:true,
            items:[{
                layout:"table",
                layoutConfig:{
                  columns:2
                },
                defaults:{
                  style:"margin:1px;",
                  border:true
                },
                xtype:"fieldset",
                title: MENU_LAYOUTS_CONTROL_TABPANEL_WINDOW_FIELDSET_TITLE,
                autoHeight:true,
                items:[{
                    title: MENU_LAYOUTS_CONTROL_TABPANEL_WINDOW_FIELDSET_TITLE_1
                  },{
                    title: MENU_LAYOUTS_CONTROL_TABPANEL_WINDOW_FIELDSET_TITLE_2
                  },{
                    xtype:"textfield",
                    name:"title_1",
                    width:200
                  },{
                    xtype:"radio",
                    fieldLabel:"Label",
                    boxLabel: MENU_LAYOUTS_CONTROL_TABPANEL_WINDOW_RADIO_TITLE,
                    name:"active",
										inputValue:0
                  },{
                    xtype:"textfield",
                    name:"title_2",
                    width:200
                  },{
                    xtype:"radio",
                    fieldLabel:"Label",
                    boxLabel: MENU_LAYOUTS_CONTROL_TABPANEL_WINDOW_RADIO_TITLE,
                    name:"active",
										inputValue:1
                  },{
                    xtype:"textfield",
                    name:"title_3",
                    width:200
                  },{
                    xtype:"radio",
                    fieldLabel:"Label",
                    boxLabel: MENU_LAYOUTS_CONTROL_TABPANEL_WINDOW_RADIO_TITLE,
                    name:"active",
										inputValue:2
                  },{
                    xtype:"textfield",
                    name:"title_4",
                    width:200
                  },{
                    xtype:"radio",
                    fieldLabel:"Label",
                    boxLabel: MENU_LAYOUTS_CONTROL_TABPANEL_WINDOW_RADIO_TITLE,
                    name:"active",
										inputValue:3
                  },{
                    xtype:"textfield",
                    name:"title_5",
                    width:200
                  },{
                    xtype:"radio",
                    fieldLabel:"Label",
                    boxLabel: MENU_LAYOUTS_CONTROL_TABPANEL_WINDOW_RADIO_TITLE,
                    name:"active",
										inputValue:4
                  },{
                    xtype:"textfield",
                    name:"title_6",
                    width:200
                  },{
                    xtype:"radio",
                    fieldLabel:"Label",
                    boxLabel: MENU_LAYOUTS_CONTROL_TABPANEL_WINDOW_RADIO_TITLE,
                    name:"active",
										inputValue:5
                  }]
              }]
          }],
					buttons:[{
						text:'Ok',
						scope:this,
						handler:function() {
							var values = w.items.first().form.getValues();
							w.close();
							var config = {xtype:'tabpanel',deferredRender:false,plain:true,bodyStyle:'padding:10px',height:235, autoWidth:true ,items:[]};
							var activeTab = 0;
							Ext.each([1,2,3,4,5,6], function(i) {
								if (values['title_'+i]) {
									config.items.push({layout:'form',autoScroll:'true',title:values['title_'+i]});
									if (values.active == i) { activeTab = i; }
								}
							});
							config.activeTab = activeTab;
							add.call(this, config);
						}
					},{
						text:'Cancel',
						handler:function() {w.close();}
					}]
			});
			w.show();

		}]);
		
		
		//FIELD SET
		data.push(   	['Forms','Field Set',	'A Fieldset, containing other form elements',
			{xtype:'fieldset' ,title:'Legend',autoHeight:true, collapsible:false  }] );
    
		
    
        return data;
    }
    
    
    
};


