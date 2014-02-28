Ext.require([ 'Ext.form.*', 'Ext.data.*', 'Ext.chart.*', 'Ext.grid.Panel','Ext.layout.container.Column', 'Ext.selection.CheckboxModel' ]);

Ext.onReady(function() {

	Ext.create('Ext.tab.Panel', {
    	    //width: 800,
    	    //height: 400,
    	    renderTo: document.body,
    	    border : false,
    	    items: [{
    	            title: 'Informaci&oacute;n General',
    	            loader: {
	    	        	url: _URL_LOADER_INFO_GRAL_RECLAMACION,
	    	        	scripts: true,
	    	        	autoLoad: false,
	    	        	ajaxOptions: {
	    	        		method: 'POST'
	    	        	}
	    	        },
	                listeners : {
	                    activate : function(tab) {
	                        tab.loader.load();
	                    }
	                }
	    	    }, {
	    	        title: 'Revisi&oacute;n Administrativa',
	    	        loader: {
	    	        	url: _URL_LOADER_REV_ADMIN,
	    	        	scripts: true,
	    	        	autoLoad: false,
	    	        	params: {
	    	        		'params.ntramite': 1396,
	    	        		'params.nsinies': 1396
	    	        	},
	    	        	ajaxOptions: {
	    	        		method: 'POST'
	    	        	}
	    	        },
	                listeners : {
	                    activate : function(tab) {
	                        tab.loader.load();
	                    }
	                }
	    	    }, {
	    	        title: 'C&aacute;lculos',
	    	        loader: {
	    	        	url: _URL_LOADER_CALCULOS,
	    	        	scripts: true,
	    	        	autoLoad: false,
	    	        	ajaxOptions: {
	    	        		method: 'POST'
	    	        	}
	    	        },
	                listeners : {
	                    activate : function(tab) {
	                        tab.loader.load();
	                    }
	                }
	    	    }]
    	});

});