
function actuzalizarMCSigs (callback)
{	
	var mask, ck = 'Actuzalizando Mesa de Control';
    try 
    {
    	mask = _maskLocal(ck);
        Ext.Ajax.request(
        {
              url      : _p54_urlactulizaNumFolioMcSigs
             ,params : 
             {
                'params.cdunieco' : values.CDUNIEXT,
                'params.cdramo'   : values.RAMO,
                'params.nmpoliza' : values.NMPOLIEX,
                'params.numtra'   : json.params.ntramite
             },
            success : function (response) 
            {
            	var json = Ext.decode(response.responseText);
            	if (json.success === true) 
            	{
                    mask.close();
                    callback();
            	}
            	else
            	{
            	   mensajeError('Error cambiando el estatus de tramite de la poliza');
            	   callback();
            	}
            }
            ,failure : function()
            {
                mask.close();
                errorComunicacion(null,'Error cambiando el estatus de tramite de la poliza');
            }
         });
    } 
    catch (e) 
    {
        manejaException(e, ck);
    }
}
