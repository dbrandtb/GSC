package mx.com.aon.portal.service;

import java.util.Map;

import mx.com.aon.core.ApplicationException;

public interface InstrumentoPagoManager {
	
	/**
	 * Metodo que obiente una lista paginada de las claves para asignar atributos
	 * @param cdElemento
	 * @param cdForPag
	 * @param cdUnieco
	 * @param cdRamo
	 * @param start
	 * @param limit
	 * @return PageList del grid de intrumentos por cliente
	 * @throws ApplicationException
	 */
	public PagedList getInstrumetosPagoCliente(String cdElemento, String cdForPag, String cdUnieco, String cdRamo, int start , int limit)throws ApplicationException;
	
	/**
	 * Metodo para agregar un instrumento de pago por Cliente
	 * @param cdElemento
	 * @param cdForPag
	 * @param cdUnieco
	 * @param cdRamo
	 * @return String del mensaje de resultado para esta operacion 
	 */
	public String agregarInstrumetoPagoCliente(String cdElemento, String cdForPag, String cdUnieco, String cdRamo)throws ApplicationException;
	
	/**
	 * Metodo para borrar un instrumento de pago por Cliente
	 * @param cdInsCte
	 * @return String del mensaje de resultado para esta operacion
	 * @throws ApplicationException
	 */
	public String borrarInstrumetoPagoCliente(String cdInsCte)throws ApplicationException;
	
	
	/**
	 * Metodo para obtener los atributos variables de un instrumento de pago
	 * @param cdInsCte
	 * @param dsAtribu
	 * @param start
	 * @param limit
	 * @return Lista de los atributos para un instrumento de pago
	 * @throws ApplicationException
	 */
	public PagedList getAtributosInstrumetoPago(String cdInsCte, String dsAtribu, int start , int limit)throws ApplicationException;
	
	/**
	 * Metodo para guardar un atributo variable a un instrumento de pago
	 * @param params
	 * @return String del mensaje de resultado para esta operacion
	 * @throws ApplicationException
	 */
	public String guardarAtributoInstrumetoPagoCliente(Map<String, Object> params)throws ApplicationException;
	
	/**
	 * Metodo para borrar un atributo variable a un instrumento de pago
	 * @param cdInsCte
	 * @param cdAtribu
	 * @return SString del mensaje de resultado para esta operacion
	 * @throws ApplicationException
	 */
	public String borrarAtributoInstrumetoPago(String cdInsCte, String cdAtribu)throws ApplicationException;
	
	
	/**
	 * Metodo que guarda lo atributos de un instumento de pago a la poliza
	 * @param params hashmap con los parametros pv_cdunieco,pv_cdramo,pv_estado,pv_nmpoliza,pv_cdunica,pv_nmsuplem,pv_status,pv_cdforpag,pv_otvalor01,pv_otvalor02,pv_otvalor03...pv_otvalor50
	 * @return
	 * @throws ApplicationException
	 */
	public String guardaAtributosInstPagoPoliza(Map<String, String> params)throws ApplicationException;
}
