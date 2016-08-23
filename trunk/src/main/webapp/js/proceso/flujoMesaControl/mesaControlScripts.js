
function actualizarMCSigs (CDUNIEXT,RAMO,NMPOLIEX,ntramite,callback)
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
                'params.cdunieco' : CDUNIEXT,
                'params.cdramo'   : RAMO,
                'params.nmpoliza' : NMPOLIEX,
                'params.numtra'   : ntramite
             },
            success : function (response) 
            {
            	var json = Ext.decode(response.responseText);
            	if (json.success === true) 
            	{
                    mask.close();
                    if(!Ext.isEmpty(callback))
                    {
                        callback();
                    }
            	}
            	else
            	{
            	   mask.close()
            	   mensajeError('Error cambiando el estatus de tramite de la poliza');
            	   if(!Ext.isEmpty(callback))
            	   {
            	       callback();
            	   }
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
