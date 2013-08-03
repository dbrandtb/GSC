<%@ include file="/taglibs.jsp"%>



    // **************************************************************************************************   
    // Elementos comunes de pantalla ....
    // **************************************************************************************************


    //  message area...


ScreenCommonsComponents = {

        getComponents : function(){
        var data = [];
		
        /*
        data.push(
            ['Elementos Comunes','Text-Editor',
            'Editor de Texto',
            {xtype:'htmleditor', fieldLabel:'Area de Texto', height:200, anchor:'98%' } 
            ]
        );


        // file upload
        data.push(
           ['Elementos Comunes','File-upload',
            'Componente para subir archivos',
            {xtype:'textfield',fieldLabel:'Archivo...',name:'archivo',inputType:'file',width:200}
           ] 
        );


        // Imagen de Archivo
        data.push(
            ['Elementos Comunes','Imagen de Archivo',
            'Agrega una imagen de archivo al area de trabajo',
            function(add,parent){
                var w = new Ext.Window({
                width:300,
                height:300,
                title:"Agregar Imagen",
                items:[{
                    xtype:"form",
                    items:[{
                    xtype:"textfield",
                    fieldLabel:"Archivo",
                    id:  "archivoImagen",
                    name:"archivoImagen",
                    // inputType:"file",
                    width:100
                    }]

                }] , 
                  buttons:[{
                    text:'Ok',
                    scope:this,
                    handler:function() {
                    // 
                    var values = w.items.first().form.getValues();
                    w.close();
                    var config  = new Ext.Template(
                        '<div>','<img src="'+ values.archivoImagen +'"/>','</div>'     
                    );

                    add.call(this, config);

                    }
                },{
                    text:'Cancel',
                    handler:function() {
                    w.close();
                    }
                }]
               });
               w.show();                   

            }
            ]
        );



        //Etiqueta Personalizada ...
          data.push(['Elementos Comunes', 'Etiqueta Personalizada', 
                'Etiqueta creada con editor', 
                function(add,parent) {
                var w = new Ext.Window({
                    width:800,
                    height:400,
                    layout:"fit",
                    title:"Editor de texto",
                    items:[{
                    xtype:"form",
                    frame:true,
                    items:[{
                        xtype:'htmleditor', 
                        fieldLabel:'Contenido',
                        height:200,
                        name:"myeditor",
                        anchor:'98%' 
                    }]
                    }],
                buttons:[{
                text:'Ok',
                scope:this,
                handler:function() {
                    var values = w.items.first().form.getValues();
                    w.close();
                    var config = new Ext.Template(
                        '<div>', ''+  values.myeditor + '' , '</div>'
                    );                                

                    add.call(this, config);
                }
                },{
                text:'Cancel',
                handler:function() {w.close();}
                }]
            });
            w.show();

        } 
        ] );        

		
		
		//Agregar imagen del servidor
		data.push(
            ['Editores','Agregar imagen',
                'Agregar imagen al area de trabajo',
                function(add,parent){
                    var w = new Ext.Window({
                        width:400,
                        height:400,
                        title:"Agregar imagen",
                        items:[{
                            xtype:"form",
                            items:[
                            	{
                            		xtype:'filetreepanel'
                            		,height:300
									,autoWidth:true
									,id:'ftp'
									,title:'FileTreePanel'
									,rootPath:'/'
									,url:'/AON/builder/fileTree.action'
									,topMenu:true
									,autoScroll:true
									,enableProgress:false
                            	}
                            ]
                        
                        }], 
                          buttons:[{
                            text:'Ok',
                            scope:this,
                            handler:function() {
                            	
                            	var sm = Ext.getCmp("ftp").getSelectionModel();
                            	var node = sm.getSelectedNode();
                            	//alert("Nodo editado=" + node.text );
                            	//alert("Ruta del nodo=" + Ext.getCmp("ftp").getPath(node));
                            	//alert("profundidad=" + node.getDepth() );
                            	
                            	if(node != null && Ext.getCmp("ftp").getPath(node)!= ""){
                                	var ruta = Ext.getCmp("ftp").getPath(node);
                                		var config = new Ext.Template(    
                                		'<div>','<img src="','/AON/Filetree/',ruta,'"/>','</div>');
                                		add.call(this,config);
			                    		w.close();
                                }else{
                                	Ext.Msg.alert('Status', 'Debes elegir una imagen.');
                                }
                                
                                
                                
                            }
                        },{
                            text:'Cancel',
                            handler:function() {
                                w.close();
                            }
                        }]
                   });
                   w.show();

                }            
            ]    
        );
		*/
        
        ///Etiqueta
        data.push(
            ['Elementos Comunes','Etiqueta Personalizada',
                'Personalizar una etiqueta de usuario',
                function(add,parent){
                    var w = new Ext.Window({
                        width:740,
                        height:500,
                        title:"Agregar Imagen",
                        items:[{
                            xtype:"form",
                            items:[{
                                xtype:"htmleditor",
                                fieldLabel:"Etiqueta Personalizada",
                                id:  "etiqueta",
                                name:"etiqueta",                            
                                height:200, 
                                anchor:'98%'
                            }]
                        
                        }] , 
                          buttons:[{
                            text:'Ok',
                            scope:this,
                            handler:function() {
                                // 
                                var values = w.items.first().form.getValues();
                                w.close();
                                
                                var config  = new Ext.Template(
                                    '<div>',''+ values.etiqueta +'','</div>'     
                                );
                                /*var config ={
									xtype:  "label", 
									html: values.etiqueta
								};*/
                                add.call(this, config);
                                                          
                            }
                        },{
                            text:'Cancel',
                            handler:function() {
                                w.close();
                            }
                        }]
                   });
                   w.show();                   
                   
                }
            ]
        );        
		

		//Subir imagenes al servidor y agregarla al editor
		data.push(
            ['Elementos Comunes','Agregar Imagen',
                'Agregar imagen al area de trabajo',
                function(add,parent){
                    var w = new Ext.Window({
                        width:400,
                        height:400,
                        title:"Agregar imagen",
                        items:[{
                            xtype:"form",
                            items:[
                            	{
                            		xtype:'filetreepanel'
                            		,height:300
									,autoWidth:true
									,id:'ftp'
									,title:'FileTreePanel'
									,rootPath:'/'
									,url:'/AON/confalfa/fileTree.action'
									//,topMenu:true
									,autoScroll:true
									,enableProgress:false
                            	}
                            ]
                        
                        }], 
                          buttons:[{
                            text:'Ok',
                            scope:this,
                            handler:function() {
                            	var sm = Ext.getCmp("ftp").getSelectionModel();
                            	var node = sm.getSelectedNode();
                            	
                            	if(node != null && Ext.getCmp("ftp").getPath(node)!= ""){
                                	var nombre = Ext.getCmp("ftp").getPath(node);
                                	var config = new Ext.Template(    
                                		'<div>','<img src="',rutaImagenes,nombre,'"/>','</div>');
                                	add.call(this,config);
                                }else{
                                	Ext.Msg.alert('Status', 'Debes elegir una imagen.');
                                }
                                w.close();
                            }
                        },{
                            text:'Cancel',
                            handler:function() {
                                w.close();
                            }
                        }]
                   });
                   w.show();
                }            
            ]    
        );

        return data;
        }
    };


