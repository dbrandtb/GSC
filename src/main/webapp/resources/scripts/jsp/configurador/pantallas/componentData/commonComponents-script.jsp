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
            [MENU_COMUNES_ELEMCOMUNES,'Text-Editor',
            'Editor de Texto',
            {xtype:'htmleditor', fieldLabel:'Area de Texto', height:200, anchor:'98%' } 
            ]
        );


        // file upload
        data.push(
           [MENU_COMUNES_ELEMCOMUNES,'File-upload',
            'Componente para subir archivos',
            {xtype:'textfield',fieldLabel:'Archivo...',name:'archivo',inputType:'file',width:200}
           ] 
        );
        


        // Imagen de Archivo
        data.push(
            [MENU_COMUNES_ELEMCOMUNES,'Imagen de Archivo',
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
          data.push([MENU_COMUNES_ELEMCOMUNES, 'Etiqueta Personalizada', 
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

		
		*/
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
									,url:'fileTree.action'
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
                                	var _CONTEXT = "${ctx}";

									var _ruta  = _CONTEXT + '/resources/images/'; 

                                		var config = new Ext.Template(    
                                		'<div>','<img src="', _ruta,ruta,'"/>','</div>');
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
		
        
        ///Etiqueta
                data.push(
		//	['Forms','Combo Box Marca',
			//'A combo box marca',
			//{"displayField":"label","editable":true,"emptyText":"Seleccione","fieldLabel":"Marca","forceSelection":true,"hiddenName":"hmarca","id":"marca|APDIT008","labelSeparator":"","listWidth":200,"mode":"local","name":"marca", "selectOnFocus":false,"triggerAction":"","typeAhead":false,"valueField":"","width":200,"xtype":"combo"}]
		
            ['Test','Sexo test','test de combo sexo',
            {"displayField":"label","editable":true,"emptyText":"Seleccione...","fieldLabel":"SEXO","forceSelection":true,"id":"sexo|SEX","labelSeparator":"","listWidth":200,"mode":"local","name":"parameters.sexo","selectOnFocus":true,"triggerAction":"all","typeAhead":true,"valueField":"value","width":200,"xtype":"combo"} ],
            
            ['Test','Parentesco test','test de combo parentesco',
            {"displayField":"label","editable":true,"emptyText":"Seleccione...","fieldLabel":"PARENTESCO","forceSelection":true,"id":"parentesco|115PAREN","labelSeparator":"","listWidth":200,"mode":"local","name":"parameters.parentesco","selectOnFocus":true,"triggerAction":"all","typeAhead":true,"valueField":"value","width":200,"xtype":"combo"}],
            
            ['Forms','Text Field',
				'A Text Field',
				{xtype:'textfield',fieldLabel:'Text',name:'parameters.textvalue'}],
			['Forms','Number Field',
				'A Text Field where you can only enter numbers',
				{xtype:'numberfield',fieldLabel:'Number',name:'parameters.numbervalue'}],
			['Forms','Time Field',
				'A Text Field where you can only enter a time',
				{xtype:'timefield',fieldLabel:'Time',name:'parameters.timevalue'}],
			['Forms','Date Field',
				'A Text Field where you can only enter a date',
				{xtype:'datefield',fieldLabel:'Date',name:'parameters.datevalue'}]
            
		
		);
		
		data.push(
			['Custom Links','link Yahoo','Links',
			  {"border":false,"html":"<a href=\"http://mail.yahoo.com\">Correo Yahoo<\/a>","id":"linkMailYahoo","width":300} ]
		)
        
        
        data.push(
            [MENU_COMUNES_ELEMCOMUNES, MENU_COMUNES_CONTROL_ETIQUETA, 
                MENU_COMUNES_CONTROL_ETIQUETA_TOOLTIP,
                function(add,parent){
                    var w = new Ext.Window({
                        width:700,
                        height:520,
                        title: MENU_COMUNES_CONTROL_ETIQUETA_WINDOW_TITLE,
                        items:[{
                            xtype:"form",
                            items:[{
                                xtype:"htmleditor",
                                hideLabel:true,
                                id:  "etiqueta",
                                name:"etiqueta",                            
                                height:200, 
                                anchor:'98%'
                            }]
                        
                        }] , 
                          buttons:[{
                            text: MENU_COMUNES_CONTROL_ETIQUETA_WINDOW_BOTON_OK,
                            scope:this,
                            handler:function() {
                                // 
                                var values = w.items.first().form.getValues();
                                w.close();
                                
                                //var config  = new Ext.Template(
                                  //  '<div>',''+ values.etiqueta +'','</div>'     
                                //);
                                
                                var config = { "html": values.etiqueta, "border": false, "width":200 };
                                
                                
                                /*var config ={
									xtype:  "label", 
									html: values.etiqueta
								};*/
                                add.call(this, config);
                                                          
                            }
                        },{
                            text: MENU_COMUNES_CONTROL_ETIQUETA_WINDOW_BOTON_CANCEL,
                            handler:function() {
                                w.close();
                            }
                        }]
                   });
                   w.show();                   
                   
                }
            ]
        );        
		
/*
		data.push(
			['Canvas','Canvas','Canvas',
			  //{"border":false,"html":"<canvas id='100' width='100'>","id":"canvasPrueba","width":300}
			function (add, parent) {
				var w = new Ext.Window({
					width: 200,
					height: 200,
					title: 'Configuración del canvas',
					modal: true,
					items: [
						{
							xtype: 'form',
							autoWidth: true,
							autoHeight: true,
							items: [
								{
									xtype: 'numberfield',
									labelWidth: 30,
									width: 30,
									fieldLabel: 'Ancho',
									value: 100
								},
								{
									xtype: 'numberfield',
									labelWidth: 30,
									width: 30,
									fieldLabel: 'Alto',
									value: 10
								}
							]
						}
					],
					buttons: [
						{
							text: 'Ok',
							scope: this,
							handler: function () {
									var alto = w.items.first().form.items.items[1].getValue();
									var ancho = w.items.first().form.items.items[0].getValue();
									w.close();

									var _id_canvas = Ext.id();
                               		var config = new Ext.Template(    
                               		'<div style="background: black; position: relative; width:', ancho, '%; height:', alto, 'px">', '</div>');
//"<canvas id='",_id_canvas, "' width='", ancho, "' height='", alto, "'></canvas>",
                               		//'<img src="', _ruta,ruta,'"/>','</div>');

									//var _html = "<canvas id='" + _id_canvas + "'></canvas>"; 
									//var lienzo = {"html": _html, "width": ancho, "height": alto};

									console.log(config);
									add.call(this, config);
									
									*var canvas = document.getElementById(_id_canvas);
									var _context = canvas.getContext('2d');
									_context.fillStyle = 'red';*

									*_context.beginPath();
									_context.lineWidth = alto;
									_context.moveTo(0, 0);
									_context.lineTo(ancho, alto);

									*_context.moveTo(ancho, ancho);
									_context.lineTo(ancho, alto);*
									_context.closePath();
									_context.stroke();
									//_context.fill();*

									*_context.fillRect(0, 0, ancho, alto);
									_context.strokeStyle = 'red';
									_context.strokeRect(0, 0, ancho, alto);* 
							}
						}
					]
				});
				w.show();
				w.items.first().form.items.items[0].focus();
			}
		 ]
		)
        
*/
		//Subir imagenes al servidor y agregarla al editor
        /*
		data.push(
            [MENU_COMUNES_ELEMCOMUNES, MENU_COMUNES_CONTROL_IMAGEN, 
                MENU_COMUNES_CONTROL_IMAGEN,
                function(add,parent){
                    var w = new Ext.Window({
                        width:400,
                        height:400,
                        title: MENU_COMUNES_CONTROL_IMAGEN_WINDOW_TITLE,
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
									,url:'/catweb-configuracion/configuradorpantallas/fileTree.action'
									//,topMenu:true
									,autoScroll:true
									,enableProgress:false
                            	}
                            ]
                        
                        }], 
                          buttons:[{
                            text: MENU_COMUNES_CONTROL_IMAGEN_WINDOW_BOTON_OK,
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
                                	Ext.Msg.alert(MENU_COMUNES_CONTROL_IMAGEN_WINDOW_BOTON_OK_ALERT, MENU_COMUNES_CONTROL_IMAGEN_WINDOW_BOTON_OK_ALERT_DESC);
                                }
                                w.close();
                            }
                        },{
                            text: MENU_COMUNES_CONTROL_IMAGEN_WINDOW_BOTON_CANCEL,
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

        return data;
        }
        
        
        
    };


