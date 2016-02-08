package mx.com.gseguros.portal.documentos.service.impl;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.portal.consultas.dao.ConsultasDAO;
import mx.com.gseguros.portal.cotizacion.dao.CotizacionDAO;
import mx.com.gseguros.portal.cotizacion.model.ParametroGeneral;
import mx.com.gseguros.portal.documentos.model.Documento;
import mx.com.gseguros.portal.documentos.service.DocumentosManager;
import mx.com.gseguros.portal.mesacontrol.dao.MesaControlDAO;
import mx.com.gseguros.utils.Utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DocumentosManagerImpl implements DocumentosManager
{
	private final static Logger logger = LoggerFactory.getLogger(DocumentosManagerImpl.class);
	
	@Value("${ruta.documentos.poliza}")
	private String rutaDocumentosPoliza;
	
	@Autowired
	private CotizacionDAO cotizacionDAO;
	
	@Autowired
	private ConsultasDAO consultasDAO;
	
	@Autowired
	private MesaControlDAO mesaControlDAO;
	
	@Override
	public Map<String,String> generarDocumentosParametrizados(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String nmsuplem
			,int proceso
			,String ntramite
			,String nmsolici
			)throws Exception
	{
		StringBuilder sb = new StringBuilder(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ generarDocumentosParametrizados @@@@@@"
				,"\n@@@@@@ cdunieco=" , cdunieco
				,"\n@@@@@@ cdramo="   , cdramo
				,"\n@@@@@@ estado="   , estado
				,"\n@@@@@@ nmpoliza=" , nmpoliza
				,"\n@@@@@@ nmsituac=" , nmsituac
				,"\n@@@@@@ nmsuplem=" , nmsuplem
				,"\n@@@@@@ proceso="  , proceso
				,"\n@@@@@@ ntramite=" , ntramite
				,"\n@@@@@@ nmsolici=" , nmsolici
				));
		
		Map<String,String> result = new HashMap<String,String>();
		String             paso   = "Iniciando generaci\u00F3n de documentos parametrizados";
		
		try
		{
			if(StringUtils.isBlank(ntramite)
					||StringUtils.isBlank(nmsolici))
			{
				paso = "Recuperando datos de p\u00F3liza para documentos";
				sb.append("\n").append(paso);
				
				Map<String,String> datosPoliza = consultasDAO.recuperarDatosPolizaParaDocumentos(cdunieco,cdramo,estado,nmpoliza);
				ntramite = datosPoliza.get("ntramite");
				nmsolici = datosPoliza.get("nmsolici");
				
				sb.append(Utils.log("\ndatos recuperados ntramite=",ntramite,",nmsolici=",nmsolici));
			}
			
			result.put("ntramite" , ntramite);
			result.put("nmsolici" , nmsolici);
					
			paso = "Generando carpeta";
			sb.append("\n").append(paso);
			
			String rutaCarpeta = Utils.join(rutaDocumentosPoliza,"/",ntramite);
			File   carpeta     = new File(rutaCarpeta);
			if(!carpeta.exists())
			{
				sb.append(Utils.log("\nNo existe la carpeta ",rutaCarpeta,", se va a crear"));
				carpeta.mkdir();
				if(carpeta.exists())
				{
					sb.append(Utils.log("\nCarpeta creada ",rutaCarpeta));
				}
				else
				{
					throw new ApplicationException("Error al crear la carpeta");
				}
			}
			
			paso = "Verificando proceso recibido";
			sb.append("\n").append(paso);
			
			if(proceso!=DocumentosManager.PROCESO_EMISION
					&&proceso!=DocumentosManager.PROCESO_ENDOSO)
			{
				throw new ApplicationException("Tipo de proceso no soportado");
			}
			
			paso = "Generando parametrizaci\u00F3n de documentos";
			sb.append("\n").append(paso);
			
			String cdorddoc = cotizacionDAO.insercionDocumentosParametrizados(
					cdunieco
					,cdramo
					,estado
					,nmpoliza
					,nmsituac
					,nmsuplem
					,proceso==DocumentosManager.PROCESO_EMISION ? "EMISION" : "ENDOSOS"
					);
			
			sb.append(Utils.log("\ncdorddoc generado=",cdorddoc));
			
			paso = "Generando y registrando documentos temporales";
			sb.append("\n").append(paso);
			
			List<Map<String,String>> docsATransferir = cotizacionDAO.generarDocumentosBaseDatos(
            		cdorddoc
            		,nmsolici
            		,ntramite
            		);
			
			sb.append(Utils.log("\nlista documentos a transferir=",docsATransferir));
			
			paso = "Recuperando ruta temporal de documentos";
			sb.append("\n").append(paso);
			
			String rutaDocsBaseDatos = consultasDAO.recuperarTparagen(ParametroGeneral.DIRECTORIO_REPORTES);
			sb.append(Utils.log("\nruta temporal documentos=",rutaDocsBaseDatos));
			
			paso = "Transfiriendo documentos";
			sb.append("\n").append(paso);
			
			for(Map<String,String>doc:docsATransferir)
            {
				String origen  = Utils.join(rutaDocsBaseDatos,doc.get("CDDOCUME"));
				String destino = Utils.join(rutaDocumentosPoliza,"/",ntramite,"/",doc.get("CDDOCUME"));
            	try
            	{
            		sb.append(Utils.log("\nIntentando mover desde:",origen,",hacia:",destino));
            		FileUtils.moveFile(
            				new File(origen)
            				,new File(destino)
            				);
            	}
            	catch(Exception ex)
            	{
            		long timestamp = System.currentTimeMillis();
            		sb.append(Utils.log("\nError (#",timestamp,") al mover desde:",origen,",hacia:",destino));
            		logger.error(Utils.join("Error (#",timestamp,") al transferir archivo "),ex);
            	}
            }
			
			paso = "Se finaliza la generaci\u00F3n de documentos parametrizados";
			sb.append("\n").append(paso);
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso, sb.toString());
		}
		
		sb.append(Utils.log(
				 "\n@@@@@@ result=",result
				,"\n@@@@@@ generarDocumentosParametrizados @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		logger.debug(sb.toString());
		return result;
	}
	
	@Override
	@Deprecated
	public void guardarDocumento(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,Date feinici
			,String cddocume
			,String dsdocume
			,String nmsolici
			,String ntramite
			,String tipmov
			,String swvisible
			,String codidocu
			,String cdtiptra
			,String cdorddoc
			,Documento documento
			)throws Exception
	{
		mesaControlDAO.guardarDocumento(
				cdunieco
				,cdramo
				,estado
				,nmpoliza
				,nmsuplem
				,feinici
				,cddocume
				,dsdocume
				,nmsolici
				,ntramite
				,tipmov
				,swvisible
				,codidocu
				,cdtiptra
				,cdorddoc
				,documento
				);
	}
}