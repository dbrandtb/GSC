/**
 * 
 */
package mx.com.aon.configurador.pantallas.model.components;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author eflores
 * @date 01/08/2008
 *
 */
public class StoreNamesCombos implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<String> listaStore;
    private List<Map<Integer, String>> listaName;
    /**
     * @return the listaName
     */
    public List<Map<Integer, String>> getListaName() {
        return listaName;
    }
    /**
     * @param listaName the listaName to set
     */
    public void setListaName(List<Map<Integer, String>> listaName) {
        this.listaName = listaName;
    }
    /**
     * @return the listaStore
     */
    public List<String> getListaStore() {
        return listaStore;
    }
    /**
     * @param listaStore the listaStore to set
     */
    public void setListaStore(List<String> listaStore) {
        this.listaStore = listaStore;
    }
}
