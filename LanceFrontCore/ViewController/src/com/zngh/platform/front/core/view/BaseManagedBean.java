package com.zngh.platform.front.core.view;

import java.util.Map;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import oracle.adf.controller.ControllerContext;
import oracle.adf.controller.TaskFlowId;
import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCDataControl;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.as.jmx.framework.annotations.Deprecated;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;
import oracle.binding.ControlBinding;
import oracle.binding.OperationBinding;

import oracle.jbo.ViewObject;
import oracle.jbo.server.ApplicationModuleImpl;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;


/**
 * BaseManagedBean
 *
 * @author MuHongdi
 * @date 2012年9月26日
 */
public class BaseManagedBean {
    protected static final ADFLogger logger = ADFLogger.createADFLogger(BaseManagedBean.class.getName());

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public Map findUrlParams() {
        Map<String, String> paras = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        return paras;
    }

    /**
     * 刷新UI指定组件
     * @param components
     */
    public static void refreshComponents(UIComponent... components) {
        AdfFacesContext context = AdfFacesContext.getCurrentInstance();
        for (int i = 0; i < components.length; i++) {
            if (null != components[i]) {
                context.addPartialTarget(components[i]);
            }
        }
    }

    public void setELExpressionValue(String elExpression, Object value) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELContext elContext = facesContext.getELContext();
        ExpressionFactory expressionFactory = facesContext.getApplication().getExpressionFactory();
        ValueExpression ValueExpression =
            expressionFactory.createValueExpression(elContext, elExpression, Object.class);
        ValueExpression.setValue(elContext, value);
    }

    public Object resolveELExpressionValue(String elExpression) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Application application = facesContext.getApplication();
        return application.evaluateExpressionGet(facesContext, elExpression, Object.class);
    }

    /**
     * Programmatic invocation of a method that an EL evaluates
     * to. The method must not take any parameters.
     *
     * @param methodExpression EL of the method to invoke
     * @return Object that the method returns
     */
    public Object invokeELMethod(String methodExpression) {
        return invokeELMethod(methodExpression, new Class[0], new Object[0]);
    }

    /**
     * Programmatic invocation of a method that an EL evaluates to.
     *
     * @param methodExpression EL of the method to invoke
     * @param paramTypes Array of Class defining the types of the
     * parameters
     * @param params Array of Object defining the values of the
     * parametrs
     * @return Object that the method returns
     */
    public Object invokeELMethod(String methodExpression, Class[] paramTypes, Object[] params) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELContext elContext = facesContext.getELContext();
        ExpressionFactory expressionFactory = facesContext.getApplication().getExpressionFactory();
        MethodExpression exp =
            expressionFactory.createMethodExpression(elContext, methodExpression, Object.class, paramTypes);
        return exp.invoke(elContext, params);
    }

    public ViewObject findViewObjectFromBindingIterator(String voIterator) {
        DCBindingContainer bc = (DCBindingContainer) getBindings();
        DCIteratorBinding iter = bc.findIteratorBinding(voIterator);
        return iter.getViewObject();
    }

    public DCBindingContainer getDCBindingContainer() {
        return ((DCBindingContainer) getBindings());
    }

    /**
     * 获取bindings中的method action
     * @param name
     * @return
     */
    public OperationBinding findOperation(String name) {
        OperationBinding operationBinding = getDCBindingContainer().getOperationBinding(name);

        return operationBinding;
    }

    /**
     * 获取bindings中的iterator
     * @param iteratorName
     * @return
     */
    public DCIteratorBinding findIterator(String iteratorName) {
        DCIteratorBinding iter = getDCBindingContainer().findIteratorBinding(iteratorName);
        return iter;
    }

    public Object getBoundAttributeValue(String attributeName) {
        return findControlBinding(attributeName).getInputValue();
    }

    public void setBoundAttributeValue(String attributeName, Object value) {
        findControlBinding(attributeName).setInputValue(value);
    }

    private AttributeBinding findControlBinding(String attributeName) {
        return findControlBinding(getBindings(), attributeName);
    }

    private AttributeBinding findControlBinding(BindingContainer bindingContainer, String attributeName) {
        if ((attributeName != null) && (bindingContainer != null)) {
            ControlBinding ctrlBinding = bindingContainer.getControlBinding(attributeName);

            if (ctrlBinding instanceof AttributeBinding)
                return ((AttributeBinding) ctrlBinding);

        }

        return null;
    }

    public void refreshPage() {
        FacesContext fc = FacesContext.getCurrentInstance();
        String refreshPage = fc.getViewRoot().getViewId();
        ViewHandler vh = fc.getApplication().getViewHandler();
        UIViewRoot uIV = vh.createView(fc, refreshPage);
        uIV.setViewId(refreshPage);
        fc.setViewRoot(uIV);
    }

    public void activeUIComponent(UIComponent cmp) {
        this.appendScript("document.getElementById('" + cmp.getClientId() + "').click();");
    }

    public void appendScript(String script) {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExtendedRenderKitService erks = Service.getRenderKitService(fc, ExtendedRenderKitService.class);
        erks.addScript(fc, script);
    }

    /**
     * 以浏览器新窗口的形式打开taskflow
     * @param taskflowId       taskflow id, 例如view-appraisal-taskflow
     * @param taskflowDocument taskflow文档的路径, 例如/WEB-INF/performance/appraisal/view-appraisal-taskflow.xml
     * @param paramMap         taskflow参数map
     */
    public void launchTaskflow(String taskflowId, String taskflowDocument, Map paramMap) {
        String taskflowURL =
            ControllerContext.getInstance().getTaskFlowURL(false, new TaskFlowId(taskflowDocument, taskflowId),
                                                           paramMap);
        StringBuilder script = new StringBuilder();
        script.append("window.open(\"" + taskflowURL + "\"").append(",\"\",\"location=0,scrollbars=1,resizable=1\");");
        this.appendScript(script.toString());
    }

    /**
     * 关闭当前窗口
     * @return
     */
    public String closeWindow() {
        StringBuffer script = new StringBuffer();
        script.append("window.close()");
        logger.info("script: " + script.toString());
        appendScript(script.toString());
        return null;
    }

//    public static ApplicationModuleImpl findAmFromBinding(String dataControl) {
//        try {
//            @SuppressWarnings("oracle.jdeveloper.java.method-deprecated")
//            DCDataControl dc = BindingContext.getCurrent().findDataControl(dataControl);
//            ApplicationModuleImpl am = (ApplicationModuleImpl) dc.getDataProvider();
//            logger.log(am.toString());
//            return am;
//        } catch (Exception e) {
//            logger.log(logger.ERROR, "BindingContext:" + BindingContext.getCurrent());
//            logger.log(logger.ERROR, "ADFContext:" + ADFContext.getCurrent());
//            logger.log(logger.ERROR, "FacesContext:" + FacesContext.getCurrentInstance());
//            showFacesMessage(FacesMessage.SEVERITY_ERROR, "服务器维护中，请退出当前页面重试", "", null);
//            e.printStackTrace();
//        }
//        logger.log(logger.ERROR, "findAmFromBinding can't find am:" + dataControl);
//        return null;
//    }
    
    public static ApplicationModuleImpl findAmFromBinding(String dataControl) {
       return RestUtil.findAmFromBinding(dataControl);
    }

    /**
     *
     * @param detail
     * @param client
     */
    public static void showFacesMessageError(String detail, String client) {
        showFacesMessage(FacesMessage.SEVERITY_ERROR, null, detail, client);
    }

    public static void showFacesMessage(FacesMessage.Severity severity, String summary, String detail, String client) {
        FacesMessage fm = new FacesMessage();
        fm.setDetail(detail);
        fm.setSummary(summary);
        fm.setSeverity(severity);
        FacesContext.getCurrentInstance().addMessage(client, fm);
    }

    //    public String findCurrentUserId(){
    //
    //        }
    //
    //    public String[] findCurrentUserRoles()
    //    public boolean isUserInRole()
    //

    /**
     * 已过期，使用findCurrentUserName()
     * @return
     */
    @Deprecated
    public String findCurrentUserId() {
        return (String) this.resolveELExpressionValue("#{securityContext.userName}");
    }
    
    public String findCurrentUserName() {
        return (String) this.resolveELExpressionValue("#{securityContext.userName}");
    }

    public String[] findCurrentUserRoles() {
        return ADFContext.getCurrent().getSecurityContext().getUserRoles();
    }

    public boolean isUserInRole(String role) {
        return (Boolean) this.resolveELExpressionValue("#{securityContext.userInRole['" + role + "']}");
    }


    public boolean isAuthenticated() {
        ExternalContext ectx = FacesContext.getCurrentInstance().getExternalContext();
        return ectx.getUserPrincipal() != null;
    }

}
