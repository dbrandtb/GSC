   
 
/* ********************************** JSONREADERS *************************** */
var jsonconsultaFax= new Ext.data.JsonReader({
					root:'MListConsultaFax',
					totalProperty: 'totalCount',
					successProperty : '@success'
				},
				[
				{name: 'cdtipoar', mapping:'cdtipoar', type: 'string'},
				{name: 'dsarchivo', mapping:'dsarchivo', type: 'string'},
				{name: 'nmfax',  mapping:'nmfax',type: 'string'},
				{name: 'nmpoliex',  mapping:'nmpoliex',type: 'string'},
				{name: 'nmcaso',  mapping:'nmcaso',type: 'string'},
				{name: 'cdusuario',  mapping:'cdusuario',type: 'string'},
				{name: 'dsusuari',  mapping:'dsusuari',type: 'string'},
				{name: 'ferecepcion',  mapping:'ferecepcion',type: 'string'},
				{name: 'feingreso',  mapping:'feingreso',type: 'string'},
				{name: 'blarchivo',  mapping:'blarchivo',type: 'string'},
				{name: 'otvalor',  mapping:'otvalor',type: 'string'},
				{name: 'cdatribu',  mapping:'cdatribu',type: 'string'},
				{name: 'dstipoar',  mapping:'dstipoar',type: 'string'}
				]
			);

/* ********************************** STORES *************************** */ 
var storeGridFax = new Ext.data.Store({
proxy: new Ext.data.HttpProxy({
url: _ACTION_BUSCAR_FAX,
        waitMsg : getLabelFromMap('400017', helpMap, 'Espere por favor....')
        }),
reader: jsonconsultaFax
});
 
 
