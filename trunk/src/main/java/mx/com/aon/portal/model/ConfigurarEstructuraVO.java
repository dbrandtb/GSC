/**
 * 
 */
package mx.com.aon.portal.model;

/**
 * 
 * Clase VO usada para obtener un modelo de agrupacion de polizas.
 * 
 * @param codigoEstructura
 * @param codigoElemento
 * @param nombre
 * @param vinculoPadre
 * @param dsPadre
 * @param codigoTipoNivel
 * @param tipoNivel
 * @param posicion
 * @param nomina
 * @param codigoPersona
 * @param dsNombre
 *
 */

public class ConfigurarEstructuraVO {
		private String codigoEstructura;
		private String codigoElemento;
		private String nombre;
		private String vinculoPadre;
		private String dsPadre;
        private String codigoTipoNivel;
        private String tipoNivel;
		private String posicion;
		private String nomina;
		private String codigoPersona;
		private String dsNombre;
		
		public String getCodigoEstructura() {
			return codigoEstructura;
		}
		
		public void setCodigoEstructura(String codigoEstructura) {
			this.codigoEstructura = codigoEstructura;
		}
		
		public String getCodigoElemento() {
			return codigoElemento;
		}
		
		public void setCodigoElemento(String codigoElemento) {
			this.codigoElemento = codigoElemento;
		}
		
		public String getNombre() {
			return nombre;
		}
		
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
		
		public String getVinculoPadre() {
			return vinculoPadre;
		}
		
		public void setVinculoPadre(String vinculoPadre) {
			this.vinculoPadre = vinculoPadre;
		}
		
		public String getDsPadre() {
			return dsPadre;
		}
		
		public void setDsPadre(String dsPadre) {
			this.dsPadre = dsPadre;
		}
		
		public String getCodigoTipoNivel() {
			return codigoTipoNivel;
		}
		
		public void setCodigoTipoNivel(String codigoTipoNivel) {
			this.codigoTipoNivel = codigoTipoNivel;
		}
		
		public String getTipoNivel() {
			return tipoNivel;
		}
		
		public void setTipoNivel(String tipoNivel) {
			this.tipoNivel = tipoNivel;
		}
		
		public String getPosicion() {
			return posicion;
		}
		
		public void setPosicion(String posicion) {
			this.posicion = posicion;
		}
		
		public String getNomina() {
			return nomina;
		}
		
		public void setNomina(String nomina) {
			this.nomina = nomina;
		}
		
		public String getCodigoPersona() {
			return codigoPersona;
		}
		
		public void setCodigoPersona(String codigoPersona) {
			this.codigoPersona = codigoPersona;
		}
		
		public String getDsNombre() {
			return dsNombre;
		}
		
		public void setDsNombre(String dsNombre) {
			this.dsNombre = dsNombre;
		}
}