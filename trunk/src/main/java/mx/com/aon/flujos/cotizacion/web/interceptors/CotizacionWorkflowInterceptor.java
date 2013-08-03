/**
 * 
 */
package mx.com.aon.flujos.cotizacion.web.interceptors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

import mx.com.aon.flujos.cotizacion.web.EntradaCotizacionAction;
import mx.com.aon.flujos.cotizacion.web.PrincipalCotizacionAction;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.opensymphony.workflow.InvalidActionException;
import com.opensymphony.workflow.InvalidEntryStateException;
import com.opensymphony.workflow.InvalidInputException;
import com.opensymphony.workflow.InvalidRoleException;
import com.opensymphony.workflow.Workflow;
import com.opensymphony.workflow.WorkflowException;
import com.opensymphony.workflow.basic.BasicWorkflow;
import com.opensymphony.workflow.loader.ActionDescriptor;
import com.opensymphony.workflow.loader.WorkflowDescriptor;
import com.opensymphony.workflow.spi.Step;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * @author eflores
 * @date 15/07/2008
 *
 */
public class CotizacionWorkflowInterceptor implements Interceptor {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private final transient Logger log = Logger.getLogger(CotizacionWorkflowInterceptor.class);
    
    EntradaCotizacionAction entradaCotizacionAction;

    /* (non-Javadoc)
     * @see com.opensymphony.xwork2.interceptor.Interceptor#destroy()
     */
    public void destroy() {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see com.opensymphony.xwork2.interceptor.Interceptor#init()
     */
    public void init() {
        if (log.isDebugEnabled()) {
            log.debug("-> CotizacionWorkflowInterceptor.init");
        }
    }

    /* (non-Javadoc)
     * @see com.opensymphony.xwork2.interceptor.Interceptor#intercept(com.opensymphony.xwork2.ActionInvocation)
     */
    public String intercept(ActionInvocation invocation) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("-> CotizacionWorkflowInterceptor.intercept");
            log.debug("Intercept...");
        }
        
        Action action = (Action) invocation.getAction();
        if (!(action instanceof PrincipalCotizacionAction)) {
            return invocation.invoke();
        } else {
            if (action instanceof EntradaCotizacionAction) {
                entradaCotizacionAction = (EntradaCotizacionAction) action;
            }
        }
        
        if (log.isDebugEnabled()) {
            log.debug("::: entradaCotizacionAction : " + entradaCotizacionAction);    
        }
        
        Map session = invocation.getInvocationContext().getSession();
        createWorkflow(session);
        Result result = invocation.getResult();
        if (log.isDebugEnabled()) {
            log.debug("::: Result : " + result);
            log.debug("::: Action : " + invocation.getAction());
            log.debug("::: ActionInvocation : " + invocation.getInvocationContext().getActionInvocation());
            log.debug("::: Name : " + invocation.getInvocationContext().getName());
            log.debug("::: Parameters : " + invocation.getInvocationContext().getParameters());
            log.debug("::: ValueStack : " + invocation.getInvocationContext().getValueStack());
            log.debug("::: Proxy : " + invocation.getProxy());
            log.debug("::: ResultCode : " + invocation.getResultCode());
            log.debug("::: ContextMap : " + invocation.getInvocationContext().getContextMap());
        }
                
        try {
            return invocation.invoke();
        }

        catch (Exception e) {
            log.error("Exception in execute()", e);
            throw e;
        }

        finally {
            if (log.isDebugEnabled()) {
                log.debug("Finally...");
            }
        }
    }
    
    /**
     * @param String
     * @throws WorkflowException 
     * @throws InvalidEntryStateException 
     * @throws InvalidInputException 
     * @throws InvalidRoleException 
     * @throws InvalidActionException 
     */
    private void createWorkflow(Map session) throws InvalidActionException, InvalidRoleException, 
            InvalidInputException, InvalidEntryStateException, WorkflowException {
        
        String user = (String) session.get("CONTENIDO_USER");
        String worflowId = (String) session.get("WORKFLOW_ID");
        if (log.isDebugEnabled()) {
            log.debug("#### session user : " + user);
            log.debug("#### session worflowId : " + worflowId);
        }
        
        Workflow wf = new BasicWorkflow(user);
        if (log.isDebugEnabled()) {
            log.debug("#### wf : " + wf);
        }
        
        long id = 0;
        
        if (StringUtils.isBlank(worflowId)) {
            boolean canInit = wf.canInitialize("flujo", 100, null);
            
            if (log.isDebugEnabled()) {
                log.debug(".... canInit : " + canInit);
            }
            
            if (canInit) {
                id = wf.initialize("flujo", 100, null);
                session.put("WORKFLOW_ID", String.valueOf(id));
            }    
        } else {
            id = Long.parseLong(worflowId);
        }
        
        if (log.isDebugEnabled()) {
            log.debug("-> Workflow id : " + id);
        }
                
        String doAction = (String) session.get("ACTION_ID");
        if (log.isDebugEnabled()) {
            log.debug(".... doAction : " + doAction);
        }
        
        if (StringUtils.isNotBlank(doAction)) {
            int action = Integer.parseInt(doAction);
            wf.doAction(id, action, Collections.EMPTY_MAP);
        }
        
        int[] actions = wf.getAvailableActions(id, null);
        WorkflowDescriptor wd = wf.getWorkflowDescriptor(wf.getWorkflowName(id));
        
        for (int i = 0; i < actions.length; i++) {
            long idActionAvailable = wd.getAction(actions[i]).getId();
            String nameActionAvailable = wd.getAction(actions[i]).getName();
            if (log.isDebugEnabled()) {
                log.debug(".... action : " + actions[i]);
                log.debug(".... id Action : " + idActionAvailable);
                log.debug(".... name Action : " + nameActionAvailable);
            }
        }
        
        ArrayList<Step> steps = new ArrayList<Step>();
        steps.addAll(wf.getCurrentSteps(id));
        steps.addAll(wf.getHistorySteps(id));
        for (Iterator iterator = steps.iterator(); iterator.hasNext();) {
            Step step = (Step) iterator.next();
            String owner = step.getOwner();
            ActionDescriptor action = wd.getAction(step.getActionId());
            if (log.isDebugEnabled()) {
                log.debug(wd.getStep(step.getStepId()).getName() + " / " + step.getId());
                log.debug(action == null ? "NONE" : action.getName());
                log.debug(step.getStatus());
                log.debug(owner);
                log.debug(step.getStartDate());
                log.debug(step.getFinishDate());
            }
            long[] prevIds = step.getPreviousStepIds();
            
            if (prevIds != null) {
                for (int i = 0; i < prevIds.length; i++) {
                    long prevId = prevIds[i];
                    if (log.isDebugEnabled()) {
                        log.debug(prevId + ", ");
                    }
                }
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("none");
                }
            }
        }
    }

    /**
     * @return the entradaCotizacionAction
     */
    public EntradaCotizacionAction getEntradaCotizacionAction() {
        return entradaCotizacionAction;
    }

    /**
     * @param entradaCotizacionAction the entradaCotizacionAction to set
     */
    public void setEntradaCotizacionAction(EntradaCotizacionAction entradaCotizacionAction) {
        this.entradaCotizacionAction = entradaCotizacionAction;
    }
}
