Ext.onReady(function() {
	
	function actuzalizarMCSigs (values , ntramite)
	{	
	 Ext.Ajax.request(
                                        {
                                              url      : _p54_urlactulizaNumFolioMcSigs
                                             ,extraParams : {
                                                'params.cdunieco' : values.CDUNIEXT,
                                                'params.cdramo'   : values.RAMO,
                                                'params.nmpoliza' : values.NMPOLIEX,
                                                'params.numtra'   : json.params.ntramite
                                            }
                                         });
	}

});