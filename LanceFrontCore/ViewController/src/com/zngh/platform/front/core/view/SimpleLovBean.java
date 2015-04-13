package com.zngh.platform.front.core.view;


import java.util.Iterator;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import oracle.adf.view.rich.component.rich.RichDialog;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.RichQuery;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelHeader;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.LaunchPopupEvent;
import oracle.adf.view.rich.render.ClientEvent;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;


/**
 * 简化Lov显示
 * @author muhongdi@qq.com
 */
public class SimpleLovBean {
    //for form
    private RichPanelFormLayout serverListenerForm;
    //for table
    private RichPanelGroupLayout serverListenerGP;
    private RichPanelGroupLayout serverListenerGP1;
    private RichPanelGroupLayout serverListenerGP2;
    private RichPanelGroupLayout serverListenerGP3;
    private RichPanelGroupLayout serverListenerGP4;
    private RichPanelGroupLayout serverListenerGP5;
    private RichPanelGroupLayout serverListenerGP6;
    private RichPanelGroupLayout serverListenerGP7;
    private RichPanelGroupLayout serverListenerGP8;
    private RichPanelGroupLayout serverListenerGP9;

    public void lovLaunch4Form(LaunchPopupEvent launchPopupEvent) {
        this.lovLaunch(launchPopupEvent, serverListenerForm.getClientId());
    }

    public void lovLaunch4Table(LaunchPopupEvent launchPopupEvent) {
        this.lovLaunch(launchPopupEvent, serverListenerGP.getClientId());
    }

    public void lovLaunch4Table1(LaunchPopupEvent launchPopupEvent) {
        this.lovLaunch(launchPopupEvent, serverListenerGP1.getClientId());
    }

    public void lovLaunch4Table2(LaunchPopupEvent launchPopupEvent) {
        this.lovLaunch(launchPopupEvent, serverListenerGP2.getClientId());
    }

    public void lovLaunch4Table3(LaunchPopupEvent launchPopupEvent) {
        this.lovLaunch(launchPopupEvent, serverListenerGP3.getClientId());
    }

    public void lovLaunch4Table4(LaunchPopupEvent launchPopupEvent) {
        this.lovLaunch(launchPopupEvent, serverListenerGP4.getClientId());
    }

    public void lovLaunch4Table5(LaunchPopupEvent launchPopupEvent) {
        this.lovLaunch(launchPopupEvent, serverListenerGP5.getClientId());
    }


    public void lovLaunch4Table6(LaunchPopupEvent launchPopupEvent) {
        this.lovLaunch(launchPopupEvent, serverListenerGP6.getClientId());
    }


    public void lovLaunch4Table7(LaunchPopupEvent launchPopupEvent) {
        this.lovLaunch(launchPopupEvent, serverListenerGP7.getClientId());
    }


    public void lovLaunch4Table8(LaunchPopupEvent launchPopupEvent) {
        this.lovLaunch(launchPopupEvent, serverListenerGP8.getClientId());
    }


    public void lovLaunch4Table9(LaunchPopupEvent launchPopupEvent) {
        this.lovLaunch(launchPopupEvent, serverListenerGP9.getClientId());
    }


    public void setServerListenerForm(RichPanelFormLayout serverListenerContainer) {
        this.serverListenerForm = serverListenerContainer;
    }

    public RichPanelFormLayout getServerListenerForm() {
        return serverListenerForm;
    }

    public void setServerListenerGP(RichPanelGroupLayout tableLovGp) {
        this.serverListenerGP = tableLovGp;
    }

    public RichPanelGroupLayout getServerListenerGP() {
        return serverListenerGP;
    }


    public void setServerListenerGP1(RichPanelGroupLayout serverListenerGP1) {
        this.serverListenerGP1 = serverListenerGP1;
    }

    public RichPanelGroupLayout getServerListenerGP1() {
        return serverListenerGP1;
    }

    public void setServerListenerGP2(RichPanelGroupLayout serverListenerGP2) {
        this.serverListenerGP2 = serverListenerGP2;
    }

    public RichPanelGroupLayout getServerListenerGP2() {
        return serverListenerGP2;
    }

    public void setServerListenerGP3(RichPanelGroupLayout serverListenerGP3) {
        this.serverListenerGP3 = serverListenerGP3;
    }

    public RichPanelGroupLayout getServerListenerGP3() {
        return serverListenerGP3;
    }

    public void setServerListenerGP4(RichPanelGroupLayout serverListenerGP4) {
        this.serverListenerGP4 = serverListenerGP4;
    }

    public RichPanelGroupLayout getServerListenerGP5() {
        return serverListenerGP5;
    }

    public RichPanelGroupLayout getServerListenerGP6() {
        return serverListenerGP6;
    }

    public RichPanelGroupLayout getServerListenerGP7() {
        return serverListenerGP7;
    }

    public RichPanelGroupLayout getServerListenerGP8() {
        return serverListenerGP8;
    }

    public RichPanelGroupLayout getServerListenerGP9() {
        return serverListenerGP9;
    }


    public static void lovLaunch(LaunchPopupEvent launchPopupEvent, String serverListenerId) {
        RichInputListOfValues inputListOfValues = (RichInputListOfValues)launchPopupEvent.getComponent();

        if (inputListOfValues != null) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            String clientId = inputListOfValues.getClientId(facesContext);
            StringBuffer scriptBuffer = new StringBuffer();
            //pfl1:serverListener上级id.可以通过bingdingserverListener的上级容器获得
            scriptBuffer.append(" var docComp = AdfPage.PAGE.findComponent(\"" + serverListenerId +
                                "\"); var comp = AdfPage.PAGE.findComponent(\"");
            scriptBuffer.append(clientId).append("\");  ");
            scriptBuffer.append(" if (comp != null && docComp != null) { var CompId = comp.getId(); ");
            scriptBuffer.append(" AdfCustomEvent.queue(docComp,\"lovafterlaunch\",{compId:CompId},true);}");
            String script = scriptBuffer.toString();
            FacesContext fc = FacesContext.getCurrentInstance();
            ExtendedRenderKitService erks = Service.getRenderKitService(fc, ExtendedRenderKitService.class);
            erks.addScript(fc, script);
        }
    }

    public static void lovAfterLaunch(ClientEvent clientEvent) {
        if (clientEvent.getParameters().size() > 0 && clientEvent.getParameters().get("compId") != null) {
            String compId = clientEvent.getParameters().get("compId").toString();
            RichInputListOfValues inputListOfValues = (RichInputListOfValues) JSFUtils.findComponentInRoot(compId);
            if (inputListOfValues != null) {
                Iterator facetNames = inputListOfValues.getFacetNames();
                RichPopup richPopup = null;
                String facetName = null;
                UIComponent componentFacet = null;
                UIComponent componentdialog = null;
                while (facetNames.hasNext()) {
                    facetName = (String)facetNames.next();
                    if (facetName != null) {
                        componentFacet = inputListOfValues.getFacet(facetName);
                        if (componentFacet != null && componentFacet instanceof RichPopup) {
                            richPopup = (RichPopup)componentFacet;
                            break;
                        }
                    }
                }
                if (richPopup != null) {
                    List uiCompList = richPopup.getChildren();
                    if (uiCompList.size() > 0) {
                        componentdialog = (UIComponent)uiCompList.get(0);
                        if (componentdialog != null && componentdialog instanceof RichDialog) {
                            RichDialog richDialog = (RichDialog)componentdialog;
                            if (richDialog != null) {
                                //                                richDialog.setStyleClass(richDialog.getStyleClass()+" custom_lov_popup");
                                richDialog.setAffirmativeTextAndAccessKey("确定");
                                richDialog.setCancelTextAndAccessKey("取消");
                                AdfFacesContext.getCurrentInstance().addPartialTarget(richDialog);
                                if (richDialog.getChildCount() == 1 && richDialog.getChildren().get(0) != null &&
                                    richDialog.getChildren().get(0) instanceof RichPanelGroupLayout) {
                                    RichPanelGroupLayout panelGroupLayout =
                                        (RichPanelGroupLayout)richDialog.getChildren().get(0);
                                    uiCompList = panelGroupLayout.getChildren();
                                    panelGroupLayout.setStyleClass("custom_lov_popup");
                                    if (uiCompList.size() > 0 && uiCompList.get(0) != null &&
                                        uiCompList.get(0) instanceof RichPanelHeader) {
                                        RichPanelHeader richPanelHeader = (RichPanelHeader)uiCompList.get(0);
                                        if (richPanelHeader.getChildCount() == 1 &&
                                            richPanelHeader.getChildren().get(0) != null &&
                                            richPanelHeader.getChildren().get(0) instanceof RichQuery) {
                                            RichQuery richQuery = (RichQuery)richPanelHeader.getChildren().get(0);
                                            if (richQuery != null) {
                                                richQuery.setModeChangeVisible(false);
                                                richQuery.setHeaderText("");
                                                richQuery.setStyleClass(richQuery.getStyleClass() +
                                                                        " hideAFDisclosed");

                                                richPanelHeader.setStyleClass("custom_lov_popup");

                                                //p_AFDisclosed
                                                AdfFacesContext.getCurrentInstance().addPartialTarget(richQuery);
                                                AdfFacesContext.getCurrentInstance().addPartialTarget(richPanelHeader);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
