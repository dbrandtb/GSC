Ext.require([ 'Ext.form.*', 'Ext.data.*', 'Ext.chart.*', 'Ext.grid.Panel','Ext.layout.container.Column', 'Ext.selection.CheckboxModel' ]);
var datosgrid;
var storeIncisos;

Ext.define('modelClau',
{
    extend:'Ext.data.Model',
    fields:['noFactura','fechaFactura','tipoServicio','proveedor','importe']
});

storeIncisos=new Ext.data.Store(
{
    autoDestroy: true,
    model: 'modelClau'
});

Ext.onReady(function() {

    Ext.selection.CheckboxModel.override( {
        mode: 'SINGLE',
        allowDeselect: true
    });
    
    ///// NUEVOS
	Ext.define('DatosFacturaxTramite',{
		extend: 'Ext.data.Model',
		fields: [
				{type:'string',    name:'reclamacion'		},				{type:'string',    name:'ntramite'			},
				{type:'string',    name:'factura'			},				{type:'date',      name:'fechaFactura',	dateFormat : 'd/m/Y'},
				{type:'string',    name:'cdtipser'			},				{type:'string',    name:'descServicio'		},
				{type:'string',    name:'cdpresta'			},				{type:'string',    name:'nomProveedor'		},
				{type:'string',    name:'ptimport'			},				{type:'string',    name:'cdgarant'			},
				{type:'string',    name:'descCdgarant'		},				{type:'string',    name:'desctoPorc'		},
				{type:'string',    name:'desctoNum'			},				{type:'string',    name:'cdconval'			},
				{type:'string',    name:'dssubgar'			},				{type:'string',    name:'cdmoneda'			},
				{type:'string',    name:'descMoneda'		},				{type:'string',    name:'tasaCambio'		},
				{type:'string',    name:'ptimporta'			},				{type:'string',    name:'dctoNuex'			}
		]
    });

	var storeDatosFacturaxTramite = Ext.create('Ext.data.Store', {
	storeId: 'storeDatosFacturaxTramite',
	model: 'DatosFacturaxTramite',
		data : recordsStoreFactura
	});

	var gridDatosFacturaxTramite = Ext.create('Ext.grid.Panel', {
		width   : 980,
		height: 300,
		title   : 'Facturas',
		store   : storeDatosFacturaxTramite,
		autoScroll:true,
		id      : 'gridDatosFacturaxTramite',
		features:[{
			ftype:'summary'
		}],
		columns: _11_columnas_Factura,
		tbar: [{
			icon:_CONTEXT+'/resources/extjs4/resources/ext-theme-classic/images/icons/fam/add.png',
			text: 'Agregar Factura',
			scope: this,
			handler: this.onAddClick
		}]
	});
	
	gridDatosFacturaxTramite.store.sort([{ 
		property    : 'factura',
		direction   : 'ASC'
	}]);
	
	_11_itemsForm.push({
		colspan:2,
		border    : 0,
		items    :
		[
			gridDatosFacturaxTramite
		]
	});
	

				
				
	_11_form=Ext.create('Ext.form.Panel',{
		border    : 0
		,title: 'Afiliados Afectados'
		,renderTo : 'div_clau'
		,bodyPadding: 5
		,width: 1000
		,layout     :
		{
			type     : 'table'
			,columns : 2
		}
		,defaults   :
		{
			style : 'margin:5px;'
		}
		,listeners : { afterrender : heredarPanel }
		,items    : _11_itemsForm
		,buttonAlign:'center'
		,buttons:[
			{
				text     : 'Regresar'
				,icon    : _CONTEXT+'/resources/fam3icons/icons/cancel.png'
				,handler : _11_regresarMC
			},
			{
				id:'botonCotizar',
				icon:_CONTEXT+'/resources/fam3icons/icons/disk.png',
				text: 'Guardar',
				handler: function()
				{
					alert("GUARDADO TOTAL");
				}
			}
		]
	}); 
	_fieldByName('OTVALOR11').forceSelection=true;
	_fieldByName('OTVALOR11').setEditable(true);
	});