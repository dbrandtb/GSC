/*var storeComboEmpresas = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_EMPRESAS}),
            reader: new Ext.data.JsonReader(
            		{root: 'comboClientesCorpBO'},
            		[
            			{name: 'cdElemento', type: 'string'},            			
            			{name: 'dsElemen', type: 'string'},
            			{name: 'cdPerson', type: 'string'}
            		]
            )
        });
        
*/
/*var storeComboGrupos = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_GRUPOS}),
            reader: new Ext.data.JsonReader(
            		{root: 'comboGrupos'},
            		[
            			{name: 'descripcion', type: 'string'},            			
            			{name: 'cdmatriz', type: 'string'},
            			{name: 'cdformatoorden', type: 'string'},
            			{name: 'cdunieco', type: 'string'},
            			{name: 'cdramo', type: 'string'},
            			{name: 'cdproceso', type: 'string'}
            		]
            )
        });  */      


var storeGridGuion = new Ext.data.Store({
	proxy: new Ext.data.HttpProxy({url: _ACTION_BUSCAR_GUION_LLAMADAS}),
	reader: new Ext.data.JsonReader({
					root:'listaGuionLlamadas',
					totalProperty: 'totalCount',
					successProperty : '@success'
				},
				[
				{name: 'cdUnieco', type: 'string', mapping:'cdUnieco'},
				{name: 'dsUnieco', type: 'string', mapping:'dsUnieco'},
				{name: 'cdRamo', type: 'string', mapping:'cdRamo'},
				{name: 'dsRamo', type: 'string', mapping:'dsRamo'},
				{name: 'cdElemento', type: 'string', mapping: 'cdElemento'},
				{name: 'dsElemen', type: 'string', mapping: 'dsElemen'},
				{name: 'cdProceso', type: 'string', mapping: 'cdProceso'},
				{name: 'dsProceso', type: 'string', mapping: 'dsProceso'},
				{name: 'cdGuion', type: 'string', mapping: 'cdGuion'},
				{name: 'dsGuion', type: 'string', mapping: 'dsGuion'},
				{name: 'cdTipGui', type: 'string', mapping: 'cdTipGuion'},
				{name: 'dsTipGui', type: 'string', mapping: 'dsTipGuion'},
				{name: 'estado', type: 'string', mapping: 'estado'}
				
				
				]
			)
});