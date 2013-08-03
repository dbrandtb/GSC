	/* ********************************** JSONREADERS *************************** */
	var readerGetDtosGrles = new Ext.data.JsonReader(
				{
	            root : 'MListPolizaEncabezado',
	            totalProperty: 'totalCount',
	            successProperty : '@success'
		        },
		        [
		         {name : 'cdunieco', /*mapping : '',*/ type : 'string'},
				 {name : 'dsunieco', /*mapping : '',*/ type : 'string'},
				 {name : 'cdramo', /*mapping : '',*/ type : 'string'},
				 {name : 'dsramo', /*mapping : '',*/ type : 'string'},
				 {name : 'nmpolant', /*mapping : '',*/ type : 'string'},
				 {name : 'nmpoliza', /*mapping : '',*/ type : 'string'},
				 {name : 'nmsuplem', /*mapping : '',*/ type : 'string'},
				 {name : 'estado', /*mapping : '',*/ type : 'string'},
				 {name : 'feefecto', /*mapping : '',*/ type : 'string'},
				 {name : 'fevencim', /*mapping : '',*/ type : 'string'},
				 {name : 'cdperson', /*mapping : '',*/ type : 'string'},
				 {name : 'asegurado', /*mapping : '',*/ type : 'string'},
				 {name : 'cdperpag', /*mapping : '',*/ type : 'string'},
				 {name : 'dsperpag', /*mapping : '',*/ type : 'string'},
				 {name : 'cdforpag', /*mapping : '',*/ type : 'string'},
				 {name : 'dsforpag', /*mapping : '',*/ type : 'string'},
				 {name : 'status', /*mapping : '',*/ type : 'string'},
				 {name : 'swestado', /*mapping : '',*/ type : 'string'},
				 {name : 'nmsolici', /*mapping : '',*/ type : 'string'},
				 {name : 'feautori', /*mapping : '',*/ type : 'string'},
				 {name : 'cdmotanu', /*mapping : '',*/ type : 'string'},
				 {name : 'feanulac', /*mapping : '',*/ type : 'string'},
				 {name : 'swautori', /*mapping : '',*/ type : 'string'},
				 {name : 'cdmoneda', /*mapping : '',*/ type : 'string'},
				 {name : 'feinisus', /*mapping : '',*/ type : 'string'},
				 {name : 'fefinsus', /*mapping : '',*/ type : 'string'},
				 {name : 'ottempot', /*mapping : '',*/ type : 'string'},
				 {name : 'hhefecto', /*mapping : '',*/ type : 'string'},
				 {name : 'feproren', /*mapping : '',*/ type : 'string'},
				 {name : 'nmrenova', /*mapping : '',*/ type : 'string'},
				 {name : 'ferecibo', /*mapping : '',*/ type : 'string'},
				 {name : 'feultsin', /*mapping : '',*/ type : 'string'},
				 {name : 'nmnumsin', /*mapping : '',*/ type : 'string'},
				 {name : 'cdtipcoa', /*mapping : '',*/ type : 'string'},
				 {name : 'swtarifi', /*mapping : '',*/ type : 'string'},
				 {name : 'swabrido', /*mapping : '',*/ type : 'string'},
				 {name : 'feemisio', /*mapping : '',*/ type : 'string'},
				 {name : 'nmpoliex', /*mapping : '',*/ type : 'string'},
				 {name : 'nmcuadro', /*mapping : '',*/ type : 'string'},
				 {name : 'porredau', /*mapping : '',*/ type : 'string'},
				 {name : 'swconsol', /*mapping : '',*/ type : 'string'},
				 {name : 'nmpolnva', /*mapping : '',*/ type : 'string'},
				 {name : 'fesolici', /*mapping : '',*/ type : 'string'},
				 {name : 'cdramant', /*mapping : '',*/ type : 'string'},
				 {name : 'cdmejred', /*mapping : '',*/ type : 'string'},
				 {name : 'nmpoldoc', /*mapping : '',*/ type : 'string'},
				 {name : 'nmpoliza2', /*mapping : '',*/ type : 'string'},
				 {name : 'nmrenove', /*mapping : '',*/ type : 'string'},
				 {name : 'nmsuplee', /*mapping : '',*/ type : 'string'},
				 {name : 'ttipcamc', /*mapping : '',*/ type : 'string'},
				 {name : 'ttipcamv', /*mapping : '',*/ type : 'string'},
				 {name : 'swpatent', /*mapping : '',*/ type : 'string'},
				 {name : 'prima_total', /*mapping : '',*/ type : 'string'}
		         ]
		    );
	
	var readerComboFormaPago = new Ext.data.JsonReader(
	 		{
            root : 'comboFormaPago',
            totalProperty: 'totalCount',
            successProperty : '@success'
	        },
	        [
	         {name : 'id', type : 'string'},
	         {name : 'texto', type : 'string'}
	         ]
	    );
		
	var readerComboInstrumentoPago = new Ext.data.JsonReader(
	 		{
            root : 'comboInstrumentoPago',
            totalProperty: 'totalCount',
            successProperty : '@success'
	        },
	        [
	         {name : 'cdForPag', type : 'string'},
	         {name : 'dsForPag', type : 'string'},
	         {name : 'cdMuestra', type : 'string'}
	         ]
	    );
	
	/* ********************************** STORES *************************** */ 
	var storeGetDtosGrles = new Ext.data.Store({
	    proxy: new Ext.data.HttpProxy({url: _ACTION_GET_ENCABEZADO_POLIZA}),
	    reader: readerGetDtosGrles
	});
	
    var dataStoreComboFormaPago = new Ext.data.Store({
         proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_FORMA_PAGO}),
         reader: readerComboFormaPago
     });

     var dataStoreComboInstrumentoPago = new Ext.data.Store({
          proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_INSTRUMENTO_PAGO}),
          reader: readerComboInstrumentoPago
      });