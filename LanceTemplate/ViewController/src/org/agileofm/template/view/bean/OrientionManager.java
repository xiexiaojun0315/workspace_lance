//Copyright (c) 2014 Oracle and/or its affiliates. All rights reserved.
package org.agileofm.template.view;

import com.zngh.platform.front.core.view.BaseManagedBean;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.layout.RichPanelBox;
import oracle.adf.view.rich.component.rich.layout.RichPanelDashboard;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.nav.RichButton;

/**
 * Session scope bean which remembers the current orientation on the device and provides
 * a bunch of convenience functions to help simplify the pages
 */
public class OrientionManager extends BaseManagedBean implements Serializable {
    @SuppressWarnings("compatibility:-9161622147801318700")
    private static final long serialVersionUID = -63859150684533036L;
    private RichPanelDashboard dashBoard;
    private RichPanelBox endBox;
    private RichPanelGroupLayout endGrp;

    public void changeCurrentLoc(ActionEvent actionEvent) {
        // Add event code here...
        RichButton btn = (RichButton) actionEvent.getComponent();
        setOrientation(btn.getText());
        System.out.println("当前布局输出：" + btn.getText() + " " + btn.getAttributes().get("defLoc"));
        changeLayout(btn.getText());
    }

    public void setDashBoard(RichPanelDashboard dashBoard) {
        this.dashBoard = dashBoard;
    }

    public RichPanelDashboard getDashBoard() {
        return dashBoard;
    }

    public void setEndBox(RichPanelBox endBox) {
        this.endBox = endBox;
    }

    public RichPanelBox getEndBox() {
        return endBox;
    }

    public void setEndGrp(RichPanelGroupLayout endGrp) {
        this.endGrp = endGrp;
    }

    public RichPanelGroupLayout getEndGrp() {
        return endGrp;
    }

    private enum Orientation {
        PHONE,
        TABLET,
        SCREEN
    };
    private Orientation _orientation = Orientation.SCREEN;

    public void setOrientation(String orientation) {
        _orientation = Orientation.valueOf(orientation.toUpperCase());
    }

    public String getOrientation() {
        return _orientation.toString().toLowerCase();
    }

    public String getInfoTileGroupLayout() {
        if (this._orientation == Orientation.SCREEN || this._orientation == Orientation.TABLET) {
            return "horizontal";
        } else {
            return "vertical";
        }
    }
    //
    //    public String getInfoTileLayout(){
    //        return (isPortraitMode())?"vertical":"horizontal";
    //    }
    //
    //    public String getOrgInfoTileWidth(){
    //        return (isPortraitMode())?"160px":"320px";
    //    }
    //
    //    public String getPersonInfoTileWidth(){
    //        return (isPortraitMode())?"158px":"188px";
    //    }
    //
    //    public String getOrgInfoTileHeight(){
    //        return (isPortraitMode())?"193px":"140px";
    //    }
    //
    //    public String getPersonInfoTileHeight(){
    //        return (isPortraitMode())?"100px":"140px";
    //    }
    //
    //
    //    public String getInfoTileDeckForwardTransition(){
    //        return (isPortraitMode())?"slideDown":"slideStart";
    //    }
    //
    //    public String getInfoTileDeckBackTransition(){
    //        return (isPortraitMode())?"slideUp":"slideEnd";
    //    }

    public void changeLayout(String layout) {
//        if(layout.equals("phone")){
//            System.out.println(1);
//            this.getEndGrp().getChildren().remove(this.getEndBox());
//            this.getDashBoard().getParent().getChildren().add(this.getEndBox());
//            this.refreshComponents(this.getEndGrp(),this.getDashBoard());
//        }else if((layout.equals("phone") || layout.equals("phone")) && this.getEndGrp()!=null && this.getEndGrp().getParent().getChildren().size()==0){
//            System.out.println(2);
//            this.getEndGrp().getParent().getChildren().add(this.getEndBox().getParent());
//        }
    }

}
