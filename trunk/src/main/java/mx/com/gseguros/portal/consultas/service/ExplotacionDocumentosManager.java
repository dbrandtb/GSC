package mx.com.gseguros.portal.consultas.service;

import java.util.List;
import java.util.Map;

import mx.com.gseguros.portal.cotizacion.model.Item;

public interface ExplotacionDocumentosManager
{
	
	public Map<String,Item> pantallaExplotacionDocumentos(String cdusuari, String cdsisrol) throws Exception;
	
	public String generarLote(
			String cdusuari
			,String cdsisrol
			,String cdtipram
			,String cdtipimp
			,String tipolote
			,List<Map<String, String>> movs
			,String rutaDocumentosPoliza
			,String rutaServidorReports
			,String passServidorReports
			,String nombreReporteRemesa
			)throws Exception;
	
	public void imprimirLote(
			String lote
			,String hoja
			,String peso
			,String cdtipram
			,String cdtipimp
			,String tipolote
			,String dsimpres
			,String charola1
			,String charola2
			,String cdusuari
			,String cdsisrol
			,boolean test
			)throws Exception;

	public Map<String,Item> pantallaExplotacionRecibos(String cdusuari, String cdsisrol) throws Exception;
	
	public Map<String,Item> pantallaPermisosImpresion(String cdusuari, String cdsisrol) throws Exception;
	
	public void movPermisoImpresion(
			String tipo
			,String cdusuari
			,String cdunieco
			,String cdtipram
			,String clave
			,String funcion
			,String accion
			)throws Exception;
	
	public void actualizarStatusRemesa(
			String ntramite
			,String status
			,String cdusuari
			,String cdsisrol
			)throws Exception;
}