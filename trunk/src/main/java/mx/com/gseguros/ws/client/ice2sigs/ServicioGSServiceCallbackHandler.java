
/**
 * ServicioGSServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package mx.com.gseguros.ws.client.ice2sigs;

    /**
     *  ServicioGSServiceCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class ServicioGSServiceCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public ServicioGSServiceCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public ServicioGSServiceCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for agenteGS method
            * override this method for handling normal response from agenteGS operation
            */
           public void receiveResultagenteGS(
                    mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.AgenteGSResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from agenteGS operation
           */
            public void receiveErroragenteGS(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for movimientoGS method
            * override this method for handling normal response from movimientoGS operation
            */
           public void receiveResultmovimientoGS(
                    mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.MovimientoGSResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from movimientoGS operation
           */
            public void receiveErrormovimientoGS(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for movimientoAseguradoCoberturaGS method
            * override this method for handling normal response from movimientoAseguradoCoberturaGS operation
            */
           public void receiveResultmovimientoAseguradoCoberturaGS(
                    mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.MovimientoAseguradoCoberturaGSResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from movimientoAseguradoCoberturaGS operation
           */
            public void receiveErrormovimientoAseguradoCoberturaGS(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for polizaGS method
            * override this method for handling normal response from polizaGS operation
            */
           public void receiveResultpolizaGS(
                    mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.PolizaGSResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from polizaGS operation
           */
            public void receiveErrorpolizaGS(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for movimientoAseguradoGS method
            * override this method for handling normal response from movimientoAseguradoGS operation
            */
           public void receiveResultmovimientoAseguradoGS(
                    mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.MovimientoAseguradoGSResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from movimientoAseguradoGS operation
           */
            public void receiveErrormovimientoAseguradoGS(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for clienteSaludGS method
            * override this method for handling normal response from clienteSaludGS operation
            */
           public void receiveResultclienteSaludGS(
                    mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.ClienteSaludGSResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from clienteSaludGS operation
           */
            public void receiveErrorclienteSaludGS(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for clienteGS method
            * override this method for handling normal response from clienteGS operation
            */
           public void receiveResultclienteGS(
                    mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.ClienteGSResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from clienteGS operation
           */
            public void receiveErrorclienteGS(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for movimientoAgenteGS method
            * override this method for handling normal response from movimientoAgenteGS operation
            */
           public void receiveResultmovimientoAgenteGS(
                    mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.MovimientoAgenteGSResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from movimientoAgenteGS operation
           */
            public void receiveErrormovimientoAgenteGS(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for reciboGS method
            * override this method for handling normal response from reciboGS operation
            */
           public void receiveResultreciboGS(
                    mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.ReciboGSResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from reciboGS operation
           */
            public void receiveErrorreciboGS(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for agenteSaludGS method
            * override this method for handling normal response from agenteSaludGS operation
            */
           public void receiveResultagenteSaludGS(
                    mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.AgenteSaludGSResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from agenteSaludGS operation
           */
            public void receiveErroragenteSaludGS(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for comisionReciboAgenteGS method
            * override this method for handling normal response from comisionReciboAgenteGS operation
            */
           public void receiveResultcomisionReciboAgenteGS(
                    mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.ComisionReciboAgenteGSResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from comisionReciboAgenteGS operation
           */
            public void receiveErrorcomisionReciboAgenteGS(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for movimientoAseguradoEndosoGS method
            * override this method for handling normal response from movimientoAseguradoEndosoGS operation
            */
           public void receiveResultmovimientoAseguradoEndosoGS(
                    mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.MovimientoAseguradoEndosoGSResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from movimientoAseguradoEndosoGS operation
           */
            public void receiveErrormovimientoAseguradoEndosoGS(java.lang.Exception e) {
            }
                


    }
    