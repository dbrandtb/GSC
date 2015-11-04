package mx.com.gseguros.portal.general.service;


public interface ImpresionService {
	
	public void imprimeDocumento(String documento, int iPrinter, int numCopias, Integer mediaId) throws Exception;
	
	public void imprimeDocumento(String documento, String nombreImpresora, int numCopias, Integer mediaId) throws Exception;
	
}