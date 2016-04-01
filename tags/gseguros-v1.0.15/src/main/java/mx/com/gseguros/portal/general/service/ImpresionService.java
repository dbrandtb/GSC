package mx.com.gseguros.portal.general.service;


public interface ImpresionService {
	
	/**
	 * Imprime un documento
	 * 
	 * @param documento Ruta completa del documento a imprimir
	 * @param nombreImpresora Nombre de la impresora a utilizar
	 * @param numCopias Numero de copias requeridas
	 * @param bandeja   Nombre de la bandeja de impresion, si es null se utilizara la bandeja por defecto
	 * @throws Exception
	 */
	public void imprimeDocumento(String documento, String nombreImpresora, int numCopias, String bandeja) throws Exception;
	
}