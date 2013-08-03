Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";


/*********************************          combo Pais         *************************/

 var elJson_CmbPais = new Ext.data.JsonReader(
    { root: 'comboObtenerPais',
      id: 'country_code'
    },
        [{name: 'country_code', type: 'string',mapping:'country_code'},
         {name: 'num_code', type: 'string',mapping:'num_code'},
         {name: 'country_name', type: 'string',mapping:'country_name'},
         {name: 'region_id', type: 'string',mapping:'region_id'}]
     );


var descPais = new Ext.data.Store({
    proxy: new Ext.data.HttpProxy({
        url: _ACTION_OBTENER_PAIS            
    }),
	reader: elJson_CmbPais
});

var comboPais = new Ext.form.ComboBox({
	    tpl: '<tpl for="."><div ext:qtip="{country_code}.{country_name}" class="x-combo-list-item">{country_name}</div></tpl>',
	    id:'PaisId',
	    store: descPais,          
	    name: 'PaisId',
	    width:260,
	    displayField:'country_name',
	    valueField:'country_code',
	    hiddenName: 'idh',
	    typeAhead: true,
	    anchor:'%',
	    allowBlank : false,
	    mode: 'local',
	    triggerAction: 'all',
	    fieldLabel: getLabelFromMap('PaisId',helpMap,'Pa&iacute;s'),
	    tooltip:getToolTipFromMap('PaisId',helpMap,'Pa&iacute;s'),
        hasHelpIcon:getHelpIconFromMap('PaisId',helpMap),
        Ayuda: getHelpTextFromMap('PaisId',helpMap),
	    labelAlign:'right',
	    selectOnFocus:true,
	    forceSelection:true,
	    emptyText:'...'
	    
	    /*onSelect: function (record){
 			this.setValue(record.get("country_code"));

 			          //   CodSist.setRawvalue('');
 			             CodSist.removeAll();
						 CodSist.load({
						          	    params:{country_code: record.get("country_code")}	
						          	  });
 			             //CodSist.load();
						this.collapse();	
	            }*/
       });	


 /*****************************************           combo codigo sistema       ******************************/
       
 var elJson_CmbCodSist = new Ext.data.JsonReader(
 { root: 'comboSistemaExterno', 
   id: 'codigo'
 },
    [
     {name: 'country_codeSis', type: 'string', mapping: 'codigo'},
	 {name: 'cdsistema', type: 'string', mapping: 'descripcion'}
     ]
 );
 
 var CodSist = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
            url: _ACTION_COMBO_COD_SIST             
            }),
	        reader: elJson_CmbCodSist
});


var comboCatdeSist = new Ext.form.ComboBox({
	    tpl: '<tpl for="."><div ext:qtip="{country_codeSis}.{cdsistema}" class="x-combo-list-item">{cdsistema}</div></tpl>',
	    store: CodSist,                       
	    id:'CodSistId',
	    name: 'CodSistId',
	    width:260,
	    displayField:'cdsistema',
	    valueField: 'country_codeSis',                       
	    hiddenName: 'country_codeSish',
	    typeAhead: true,
	    anchor:'%',
	    allowBlank : false,
	    mode: 'local',
	    triggerAction: 'all',
	    fieldLabel: getLabelFromMap('CodSistId',helpMap,'C&oacute;digo de Sistema'),
	    tooltip:getToolTipFromMap('CodSistId',helpMap,'C&oacute;digo de Sistema'),
        hasHelpIcon:getHelpIconFromMap('CodSistId',helpMap),
        Ayuda: getHelpTextFromMap('CodSistId',helpMap),
	    labelAlign:'right',
	    selectOnFocus:true,
	    forceSelection:true,
	    emptyText:'...',
	    onSelect: function (record){
			                          this.setValue(record.get("country_codeSis"));
			                          //  CatAon.setRawvalue('');
                                      Ext.getCmp('txtCatExtId').setValue('');
                                      el_storeText.removeAll();
                                      Ext.getCmp('CatAonId').clearValue();
						              CatAon.removeAll();
						              CatAon.load({
						          	                params:{ country_code: Ext.getCmp('dtsUsr').form.findField('idh').getValue(),
                                                                cdsistema: record.get("country_codeSis") 						          	
						          	                       }	
						          	  });
									  this.collapse();	
						          	}       
        });	
    
       CodSist.load();
       
       /**************************       combo catalogo Aon cat Web       ***************************/
       
var elJson_CmbCatAon = new Ext.data.JsonReader(
{  root: 'comboObtenerCatalogoAon',
   id: 'cdtablaacw'
},
[{name: 'country_codeTable', type: 'string',mapping:'cdtablaacw'},
 {name: 'cdsistemaAon', type: 'string',mapping:'cdsistema'}]
 );
 

var CatAon = new Ext.data.Store({
    proxy: new Ext.data.HttpProxy({
        url: _ACTION_COMBO_CATA_AON                  
    }),
	reader: elJson_CmbCatAon
});

       
var comboCatAonCatWeb = new Ext.form.ComboBox({
	    tpl: '<tpl for="."><div ext:qtip="{country_codeTable}.{country_codeTable}" class="x-combo-list-item">{country_codeTable}</div></tpl>',
	    store: CatAon,                
	    id:'CatAonId',
	    name: 'CatAonId',
	    width:260,
	    displayField:'country_codeTable',
	    valueField:'country_codeTable',
	    hiddenName: 'country_codeTableh',
	    typeAhead: true,
	    anchor:'%',
	    allowBlank : false,
	    mode: 'local',
	    triggerAction: 'all',
	    fieldLabel: getLabelFromMap('CatAonId',helpMap,'Cat&aacute;logo Aon Cat Web'),
	    tooltip:getToolTipFromMap('CatAonId',helpMap,'Cat&aacute;logo Aon Cat Web'),
        hasHelpIcon:getHelpIconFromMap('CatAonId',helpMap),
        Ayuda: getHelpTextFromMap('CatAonId',helpMap),
	    labelAlign:'right',
	    selectOnFocus:true,
	    forceSelection:true,
	    emptyText:'...',
	  
		onSelect: function (record){
			                        this.setValue(record.get("country_codeTable"));
                                    Ext.getCmp('txtCatExtId').setValue('');
		                            el_storeDerecha.proxy = new Ext.data.HttpProxy({ url : _ACTION_OBTIENE_CATALOGO });
			                        el_storeText.load({
						          	                   params:{  country_code:Ext.getCmp('dtsUsr').form.findField('idh').getValue(),
                                                                    cdsistema:Ext.getCmp('dtsUsr').form.findField('country_codeSish').getValue(),
                                                                   cdtablaacw:record.get("country_codeTable")
						          	                          },
	          	                                        callback:function(){
                                                          var _TextEqui = el_storeText.reader.jsonData.equivalenciaTextList[0].cdTablaExt;
                                                          var _nmTabla = el_storeText.reader.jsonData.equivalenciaTextList[0].nmtabla;
                                                          var _nmColumna = el_storeText.reader.jsonData.equivalenciaTextList[0].nmcolumna;
 
                                                          	var idABuscar;
                                                          	//cada vez que selecciono el combo, pongo todas las celdas editables de nuevo y luego deshabilito según lo que seleccion
                                                          	for(i=1;i<6;i++)
															{
															idABuscar="otclave"+i+"acw";
															gridExterno.getColumnModel().setEditable(gridExterno.getColumnModel().getIndexById(idABuscar),true);
                                                          	//Deshabilitamos columna editables de la mitad derecha de la grilla según la cantidad de colummnas que se pueden editar															
															}
                                                          	for(i=5;i > _nmColumna;i--)
															{
															idABuscar="otclave"+i+"acw";
															gridExterno.getColumnModel().setEditable(gridExterno.getColumnModel().getIndexById(idABuscar),false);
															
															}
                                                          Ext.getCmp('txtCatExtId').setValue(_TextEqui);
                                                          Ext.getCmp('nmtablaId').setValue(_nmTabla);
                                                          Ext.getCmp('txtCatExtId').setDisabled(true);
                                                         }
						          	                  });
			                        this.collapse();
			                        reloadGrid();
			                  //  gridCaso.getColumnModel().setHidden(1, true);       OCULTA LA COLUMNA DE LA GRILLA
       	 }   
		 });	
	    
/*var comboCatSistExt = new Ext.form.ComboBox({
	    tpl: '<tpl for="."><div ext:qtip="{country_code}.{cdsistema}" class="x-combo-list-item">{cdsistema}</div></tpl>',
	    store: CatSistExt,             //obtenerStoreParaCombo(),
	    id:'CatExtId',
	    name: 'CatExtId',
	    width:260,
	    displayField:'cdsistema',
	    valueField:'country_code',
	    hiddenName: 'cdsistemah',
	    typeAhead: true,
	    anchor:'%',
	    allowBlank : false,
	    mode: 'local',
	    triggerAction: 'all',
	    fieldLabel: getLabelFromMap('adminisEquivCatExt',helpMap,'Cat&aacute;logo sistema Externo'),
	    tooltip:getToolTipFromMap('adminisEquivCatExt',helpMap,'Cat&aacute;logo sistema Externo'),
	    labelAlign:'right',
	    selectOnFocus:true,
	    forceSelection:true,
	    emptyText:'...'
       });	
    */
		 
var dtsUsr = new Ext.FormPanel({
        id: 'dtsUsr',
        renderTo: 'formBusqueda',
        title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('dtsUsr',helpMap,'Administraci&oacute;n de Equivalencia')+'</span>',
        
        iconCls:'logo',
        bodyStyle:'background: white',
        buttonAlign: "center",
        store:el_storeText,
        labelAlign: 'right',
     // url:                              
        frame: true,   
        width: 725,
        height: 210,
        items: [
                
        	    {
                layout:'form',
                border: false,
                items:[
                		{
		                labelWidth: 100,
		                layout: 'form',
		             //   title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;"> </span><br>',
		                frame:true,
		                baseCls: '',
		                buttonAlign: "center",
		                      items:[
		                            {
		                             layout:'column',
		                             border:false,
		                             columnWidth: 1,
		                             items:[
			                                {
					                         columnWidth: .10,
					                         layout: 'form',
					                         html: '<span style="">&nbsp;</span>'
			                                 },
			                                 {
			                                  columnWidth:.6,
			                                  layout: 'form',
			                                  border: false,
			                                  items:[                                     
                                                      comboPais,	
                                                      comboCatdeSist,
                                                      comboCatAonCatWeb,
	           				                         {
	           					                      xtype:'textfield',
			            		                      id: 'txtCatExtId',
						                              fieldLabel: getLabelFromMap('txtCatExtId',helpMap,'Cat&aacute;logo Sistema Externo'),
						                              tooltip:getToolTipFromMap('txtCatExtId',helpMap,'Cat&aacute;logo Sistema Externo'),
						                              hasHelpIcon:getHelpIconFromMap('txtCatExtId',helpMap),
		                                              Ayuda: getHelpTextFromMap('txtCatExtId',helpMap),
						                              name:'cdTablaExt',            	        
						                              width: 220
						                             },
						                             {id:'nmtablaId',
									                 xtype:'hidden',
									                 //dataIndex: 'nmtabla',
									                 name:'nmtabla'
									                }
						                             
	                                                ]
			                                   },
			                                   {
			                                    columnWidth:.2,
			                                    layout: 'form',
					                            html: '<span style="">&nbsp;</span>'                                        
			                                   }
		                                	  ]
		                            	 }
		                            	]
                          }
                        ]
                }
               ]  

});   


 /****************          Definicion de las columnas de la grilla Ext y AON         *************/

var cmExterno = new Ext.grid.ColumnModel([


        {
        header:getLabelFromMap('clavExt01',helpMap,'Clave01 Ext'),
        tooltip:getToolTipFromMap('clavExt01',helpMap,'Clave 1'),
        dataIndex: 'otclave01ext',
        sortable: true,
        resizable: true,
        width: 70
        },
        {
        header:getLabelFromMap('clavExt02',helpMap,'Clave02 Ext'),
        tooltip:getToolTipFromMap('clavExt02',helpMap,'Clave 2'),
        dataIndex: 'otclave02ext',
        sortable: true,
        resizable: true,
        width: 70
        },
        {
        header:getLabelFromMap('clavExt03',helpMap,'Clave03 Ext'),
        tooltip:getToolTipFromMap('clavExt03',helpMap,'Clave 3'),
        dataIndex: 'otclave03ext',
        sortable: true,
        resizable: true,
        width: 70
        },
        {
        header:getLabelFromMap('clavExt04',helpMap,'Clave04 Ext'),
        tooltip:getToolTipFromMap('clavExt04',helpMap,'Clave 4'),
        dataIndex: 'otclave04ext',
        sortable: true,
        resizable: true,
        width: 70
        },
        {
        header:getLabelFromMap('clavExt05',helpMap,'Clave05 Ext'),
        tooltip:getToolTipFromMap('clavExt05',helpMap,'Clave 5'),
        dataIndex: 'otclave05ext',
        sortable: true,
        resizable: true,
        width: 70
        },
        {
        header:getLabelFromMap('cmBlank',helpMap,''),
        id:'ColumvaciaID',
        width: 25,
        resizable: true,
        //            *****    esta funcion llama a la clase 'x-grid3-header-columvaciaId para pintar la celda cabecera' *******
        renderer:function extGrid_renderer(cell, hd, record, row, col, store) {

        	hd+='x-grid3-header-ColumvaciaID';
             // return val;
             }        
        },
        {
        header:'OTVALOREXT',
        tooltip:'valor OTVALOREXT',
        dataIndex: 'otvalorExt',
        sortable: true,
        resizable: true,
        width: 75
        },
        {
        header:getLabelFromMap('clavAon01',helpMap,'Clave01 Aon'),
        tooltip:getToolTipFromMap('clavAon01',helpMap,'Clave 1'),
        dataIndex: 'otclave01acw',
        id: 'otclave1acw',
        sortable: true,
        resizable: true,
        editor: new Ext.form.TextField({name: 'otclave01acw' }),
        width: 70
        },
        {
        header:getLabelFromMap('clavAon02',helpMap,'Clave02 Aon'),
        tooltip:getToolTipFromMap('clavAon02',helpMap,'Clave 2'),
        dataIndex: 'otclave02acw',
        id: 'otclave2acw',        
        sortable: true,
        resizable: true,
        editor: new Ext.form.TextField({name:'otclave02acw'}),
        width: 70
        },
        {
        header:getLabelFromMap('clavAon03',helpMap,'Clave03 Aon'),
        tooltip:getToolTipFromMap('clavAon03',helpMap,'Clave 3'),
        dataIndex: 'otclave03acw',
        id: 'otclave3acw',
        sortable: true,
        resizable: true,
        editor: new Ext.form.TextField({name:'otclave03acw'}),
        width: 70
        },
        {
        header:getLabelFromMap('clavAon04',helpMap,'Clave04 Aon'),
        tooltip:getToolTipFromMap('clavAon04',helpMap,'Clave 4'),
        dataIndex: 'otclave04acw',
        id: 'otclave4acw',
        sortable: true,
        resizable: true,
        editor: new Ext.form.TextField({name:'otclave04acw'}),
        width: 70
        },
        {
        header:getLabelFromMap('clavAon05',helpMap,'Clave05 Aon'),
        tooltip:getToolTipFromMap('clavAon05',helpMap,'Clave 5'),
        dataIndex: 'otclave05acw',
        id: 'otclave5acw',
        sortable: true,
        resizable: true,
        editor: new Ext.form.TextField({name:'otclave05acw'}), 
        width: 70
        }
]);
	
/************                  TABLA                          *********************/
   
var elJsonTextFiel = new Ext.data.JsonReader(
{   id:'cdEquivalencia',
    root : 'equivalenciaTextList',
    totalProperty: 'totalCount',
    successProperty : 'success'
},
[   {name: 'cdTablaExt',  type: 'string', mapping: 'cdTablaExt'}, 
    {name: 'nmcolumna',  type: 'string', mapping: 'nmcolumna'},
    {name: 'nmtabla',  type: 'string', mapping: 'nmtabla'} 
    
] 
);


var el_storeText = new Ext.data.Store({
	proxy: new Ext.data.HttpProxy({
        url: _ACTION_OBTIENE_TABLA,   
        waitTitle: getLabelFromMap('400017',helpMap,'Espere ...')
        }),
	reader: elJsonTextFiel
});
	

/************     grilla derecha correspondiente a Catalogo *********************/

var elJsonGrillaDerecha = new Ext.data.JsonReader(
{   id:'cdEquivalencia',
    root : 'equivalenciaList',
    totalProperty: 'totalCount',
    successProperty : 'success'
},
[   {name: 'country_code',  type: 'string', mapping: 'country_code'},
    {name: 'cdsistema',  type: 'string', mapping: 'cdsistema'},
    
    {name: 'otclave01acw',  type: 'string', mapping: 'otclave01acw'},
    {name: 'otclave01ext',  type: 'string', mapping: 'otclave01ext'},
    {name: 'otclave02acw',  type: 'string', mapping: 'otclave02acw'},
    {name: 'otclave02ext',  type: 'string', mapping: 'otclave02ext'},
    
    {name: 'otclave03acw',  type: 'string', mapping: 'otclave03acw'}, 
    {name: 'otclave03ext',  type: 'string', mapping: 'otclave03ext'},
    {name: 'otclave04acw',  type: 'string', mapping: 'otclave04acw'}, 
    {name: 'otclave04ext',  type: 'string', mapping: 'otclave04ext'},
    {name: 'otclave05acw',  type: 'string', mapping: 'otclave05acw'}, 
    {name: 'otclave05ext',  type: 'string', mapping: 'otclave05ext'}, 
    {name: 'otvalorExt',  	type: 'string', mapping: 'otvalorExt'}
] 
);
var el_storeDerecha = new Ext.data.Store({
	proxy: new Ext.data.HttpProxy({
        url: _ACTION_OBTIENE_CATALOGO,                   
        waitTitle: getLabelFromMap('400017',helpMap,'Espere ...')
        }),
	reader: elJsonGrillaDerecha
});


var  gridExterno;      

function createGridExterno(){
       gridExterno= new Ext.grid.EditorGridPanel({

            id: 'gridExterno',
            el: 'gridDer',                  
            store: el_storeDerecha,
            
            title:'<table width="100%" border="0"> <tr> <td width="55%" align="center">Cat&aacute;logo Sistema Externo</td> <td width="45%" align="center">Cat&aacute;logo Aon Cat Web</td> </tr> </table>',
            reader: elJsonGrillaDerecha,
            border: true,
            cm: cmExterno,
            clicksToEdit: 1,
            successProperty: 'success',
            width:725,          //340,
            frame:true,
            height:430,
            sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
            stripeRows: true,
            collapsible: true,
            bbar: new Ext.PagingToolbar({
                    pageSize:20,
                    store: el_storeDerecha,
                    displayInfo: true,
                    displayMsg: getLabelFromMap('400009', helpMap,'{0} - {1} of {2}'),
                    emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
                })          
        });
         gridExterno.render()
}

createGridExterno();

var rsgncCsGrdBttn = new Ext.Panel({
  layout:'form',
  renderTo: 'formBtnGrd', 
  borderStyle:true, 
  bodyStyle:'background: white',
  width: 725,
  height: 50, 
  border: false,  
  items:[ 
          {
		  buttonAlign: "center",       
		  border: false,			
          buttons:[
                   {
					text:getLabelFromMap('admCmbBsqBttnGuardar', helpMap,'Guardar'),
					tooltip:getToolTipFromMap('admCmbBsqBttnGuardar', helpMap,'Guardar'),			        			
                    handler: function(){
                 			if (gridExterno.store.getModifiedRecords()!=0){
                 				if (!validaExistenciaDeClavesIguales())
                 					guardarEquivalencias();
                 				else  {Ext.MessageBox.alert(getLabelFromMap('400010', helpMap,'Error'),getLabelFromMap('400116', helpMap,'Existen claves duplicadas;n'));}	
                 			}else{
					  	  		Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe ingresar un registro para realizar esta operaci&oacute;n'));
                 			}
                     }
                   },
                   {
					text:getLabelFromMap('admCmbBsqBttnReporte', helpMap,'Reporte'),
					tooltip:getToolTipFromMap('admCmbBsqBttnReporte', helpMap,'Reporte'),			        			
                    handler: function(){
                    	var _url = _ACTION_REPORTE_TABLA;
                    	var width, height;
							width = screen.width;
							height = screen.height;
                        	window.open(_url, 'exportaReporteTablasNoConciliadas', config='height=' + height + ', width=' + width + ', toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, directories=no, status=no')
                     }
                    }
                    /*{
					text:getLabelFromMap('admCmbBsqBttnGuardar', helpMap,'Regresar'),
					tooltip:getToolTipFromMap('admCmbBsqBttnRegresar', helpMap,'Regresar a la Pantalla Anterior'),			        			
                    handler:function(){
//se tiene que modificar	window.location=_ACTION_REGRESAR_A_CONFIGURAR_MATRIZ_TAREA+'?cdmatriz='+ CODMATRIZ; 
                      }
                    }*/
                  ]
           }
        ]
});
 rsgncCsGrdBttn.render();
 descPais.load();
 
 function  validaExistenciaDeClavesIguales (){
 
 	//var recs = gridExterno.store.getModifiedRecords();
 	var existeIgual=false;
 	var j;
 	var recTmp;
 	var recTmp2;
 	
 	for (var i=0; i<gridExterno.store.getTotalCount(); i++) {
 		recTmp = gridExterno.store.getAt(i);
    	var concatenacion=recTmp.get('otclave01ext')+recTmp.get('otclave01acw')+recTmp.get('otclave02acw')+recTmp.get('otclave03acw')
    						+recTmp.get('otclave04acw')+recTmp.get('otclave05acw');
    	  j=i+1;
    	 while ((!existeIgual) && j<gridExterno.store.getTotalCount())
    		{ recTmp2 = gridExterno.store.getAt(j);
    		var concatenacion2=recTmp2.get('otclave01ext')+recTmp2.get('otclave01acw')+recTmp2.get('otclave02acw')+recTmp2.get('otclave03acw')
    						+recTmp2.get('otclave04acw')+recTmp2.get('otclave05acw');
  			if (concatenacion == concatenacion2)
  				existeIgual=true;
  			j++;	
    		}
 	  }
 	return existeIgual;
 }
 
 function guardarEquivalencias () {
 
   var _params = "";
  
   var recs = gridExterno.store.getModifiedRecords();
   gridExterno.stopEditing();
  
  //alert(dtsUsr.findById("nmtablaId").getValue());

   for (var i=0; i<recs.length; i++) {
    	
   		_params +=  "&equivalenciaList["+i+"].country_code="+dtsUsr.findById("PaisId").getValue()+"&";
   		_params +=  "&equivalenciaList["+i+"].cdsistema="+dtsUsr.findById("CodSistId").getValue()+"&";
   		_params +=  "&equivalenciaList["+i+"].cdtablaacw="+dtsUsr.findById("CatAonId").getValue()+"&";
   		_params +=  "&equivalenciaList["+i+"].nmtabla="+dtsUsr.findById("nmtablaId").getValue()+"&";
	   //Ext.getCmp('nmtablaId').getValue();
	   //Ext.getCmp('dtsUsr').form.findField('tab').getValue(),equivalenciaTextList
	   //recs[i].get('nmtabla')+"&";
   
	   _params +=  "&equivalenciaList["+i+"].otclave01acw="+ recs[i].get('otclave01acw')+"&";
	   _params +=  "equivalenciaList["+i+"].otclave01ext="+recs[i].get('otclave01ext')+"&";
	   
	   _params +=  "&equivalenciaList["+i+"].otclave02acw="+ recs[i].get('otclave02acw')+"&";
	   _params +=  "&equivalenciaList["+i+"].otclave02ext="+recs[i].get('otclave02ext')+"&";
	
	   _params +=  "&equivalenciaList["+i+"].otclave03acw="+ recs[i].get('otclave03acw')+"&";
	   _params +=  "&equivalenciaList["+i+"].otclave03ext="+recs[i].get('otclave03ext')+"&";
	
	   _params +=  "&equivalenciaList["+i+"].otclave04acw="+ recs[i].get('otclave04acw')+"&";
	   _params +=  "&equivalenciaList["+i+"].otclave04ext="+recs[i].get('otclave04ext')+"&";
	
	   _params +=  "&equivalenciaList["+i+"].otclave05acw="+ recs[i].get('otclave05acw')+"&";
	   _params +=  "&equivalenciaList["+i+"].otclave05ext="+recs[i].get('otclave05ext')+"&";
    		
  }
  
  if (recs.length > 0) {

   var conn = new Ext.data.Connection ();
   conn.request({
     url: _ACTION_GUARDAR_TABLA,
     params: _params,
     method: 'POST',
     callback: function (options, success, response) {
         if (Ext.util.JSON.decode(response.responseText).success == false) {
          Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), Ext.util.JSON.decode(response.responseText).errorMessages[0]);
         } else {
          Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), Ext.util.JSON.decode(response.responseText).actionMessages[0],
                         function(){
	          	                    gridExterno.store.commitChanges();
	          	                    reloadGrid(gridExterno)
                        });
                }
       }
   });
  }
 } 	
 
});


function reloadGrid(){
	var _params = {
       		          country_code: Ext.getCmp('PaisId').getValue(),
       		          cdsistema: Ext.getCmp('CodSistId').getValue(),
       		          cdtablaacw: Ext.getCmp('CatAonId').getValue()

       		         };
	reloadComponentStore(Ext.getCmp('gridExterno'), _params, cbkReloadDer);
}


function cbkReloadDer(_r, _options, _success, _store) {
	if (!_success) {
		_store.removeAll();
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _store.reader.jsonData.actionErrors[0]);
	}
}








