package com.lance.view.bean;

import com.lance.model.vo.JobSubCategoryVOImpl;
import com.lance.model.vo.JobSubCategoryVORowImpl;
import com.lance.model.vo.UserJobCategoryVOImpl;
import com.lance.model.vo.UserJobCategoryVORowImpl;

import com.zngh.platform.front.core.view.BaseManagedBean;

import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.data.RichListView;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.nav.RichButton;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import org.apache.myfaces.trinidad.event.RowDisclosureEvent;
import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.RowKeySet;

public class CategoryBean extends BaseManagedBean {
    private RichSelectBooleanCheckbox agreementChk1;
    private RichSelectBooleanCheckbox agreementChk2;
    private RichSelectBooleanCheckbox agreementChk3;
    private RichSelectBooleanCheckbox agreementChk4;
    private RichButton selectMyCategoryNextBtn;
    private RichSelectBooleanCheckbox confirmMyCategory_InDutyChk;
    private RichSelectBooleanCheckbox confirmMyCategory_SubscribeChk;
    private RichListView selectMyCategoryList;
    private RichButton toNextPageBtn;

    public CategoryBean() {
        super();
    }

    public void listGrpDiscListener(RowDisclosureEvent rowDisclosureEvent) {
        // Add event code here...
        System.out.println("listGrpDiscListener");
        System.out.println(rowDisclosureEvent.getSource());
        System.out.println(rowDisclosureEvent.getSource().getClass().getName());
    }

    public void listSelectListener(SelectionEvent selectionEvent) {
        // Add event code here...
        System.out.println("listSelectListener");
        System.out.println(selectionEvent.getSource());
        System.out.println(selectionEvent.getSource().getClass().getName());
    }

    public String b1_action() {
        System.out.println("ok");
        return null;
    }

    public String submitMyTalent_action() {
        //用户从事该领域工作
        Boolean b1 = (Boolean) this.confirmMyCategory_InDutyChk.getValue();
        //用户订阅该领域的信息
        Boolean b2 = (Boolean) this.confirmMyCategory_InDutyChk.getValue();
        System.out.println(b1);
        System.out.println(b2);
        if (b1 != null || b2 != null) {
            if (b1 || b2) {
                UserJobCategoryVOImpl vo =
                    (UserJobCategoryVOImpl) this.findViewObjectFromBindingIterator("UserJobCategory1Iterator");
                UserJobCategoryVORowImpl row = (UserJobCategoryVORowImpl) vo.createRow();
                row.setCategoryId((String) this.resolveELExpressionValue("#{bindings.CategoryId.inputValue}"));
                row.setSubCategoryId((String) this.resolveELExpressionValue("#{bindings.Uuid.inputValue}"));
                row.setUserName(this.findCurrentUserId());
                if (b1) {
                    row.setInDuty("Y");
                }
                if (b2) {
                    row.setSubscribe("Y");
                }
                vo.insertRow(row);
            }
        }
        commit_action();
        System.out.println("commited");
        String script =
            "setTimeout(function(){document.getElementById('" + this.toNextPageBtn.getClientId() + "').click();},2000)";
        System.out.println(script);
        this.appendScript(script);
        return "next";
    }

    public void confirmMyCategory(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().equals(DialogEvent.Outcome.ok)) {
            submitMyTalent_action();
        }
    }

    public void setAgreementChk1(RichSelectBooleanCheckbox agreementChk1) {
        this.agreementChk1 = agreementChk1;
    }

    public RichSelectBooleanCheckbox getAgreementChk1() {
        return agreementChk1;
    }

    public void setAgreementChk2(RichSelectBooleanCheckbox agreementChk2) {
        this.agreementChk2 = agreementChk2;
    }

    public RichSelectBooleanCheckbox getAgreementChk2() {
        return agreementChk2;
    }

    public void setAgreementChk3(RichSelectBooleanCheckbox agreementChk3) {
        this.agreementChk3 = agreementChk3;
    }

    public RichSelectBooleanCheckbox getAgreementChk3() {
        return agreementChk3;
    }

    public void setAgreementChk4(RichSelectBooleanCheckbox agreementChk4) {
        this.agreementChk4 = agreementChk4;
    }

    public RichSelectBooleanCheckbox getAgreementChk4() {
        return agreementChk4;
    }

    public void onTechCategorySelect(SelectionEvent selectionEvent) {
        this.selectMyCategoryNextBtn.setDisabled(false);
        RichListView rlv = (RichListView) selectionEvent.getSource();
        RowKeySet rks = rlv.getSelectedRowKeys();
        Iterator rksIterator = rks.iterator();
        while (rksIterator.hasNext()) {
            List list = (List) rksIterator.next();
            for (Object o : list) {
                System.out.println(((oracle.jbo.Key) o).getKeyValues());
                //                Key key=(Key)o;
                //                System.out.println(key.getAttribute("Uuid"));
            }
        }
        this.refreshComponents(this.selectMyCategoryNextBtn);
    }

    public String agreeAgreement_action() {
        if (agreementChk1.getValue().equals(true) && agreementChk2.getValue().equals(true) &&
            agreementChk3.getValue().equals(true) && agreementChk4.getValue().equals(true)) {
            System.out.println("agreeAgreement_action");
            return "next";
        } else {
            this.showFacesMessage(FacesMessage.SEVERITY_WARN, null, "请勾选所有须知事项", "消息");
            return null;
        }
    }

    public void setSelectMyCategoryNextBtn(RichButton selectMyCategoryNextBtn) {
        this.selectMyCategoryNextBtn = selectMyCategoryNextBtn;
    }

    public RichButton getSelectMyCategoryNextBtn() {
        return selectMyCategoryNextBtn;
    }

    public void setConfirmMyCategory_InDutyChk(RichSelectBooleanCheckbox confirmMyCategory_InDutyChk) {
        this.confirmMyCategory_InDutyChk = confirmMyCategory_InDutyChk;
    }

    public RichSelectBooleanCheckbox getConfirmMyCategory_InDutyChk() {
        return confirmMyCategory_InDutyChk;
    }

    public void setConfirmMyCategory_SubscribeChk(RichSelectBooleanCheckbox confirmMyCategory_SubscribeChk) {
        this.confirmMyCategory_SubscribeChk = confirmMyCategory_SubscribeChk;
    }

    public RichSelectBooleanCheckbox getConfirmMyCategory_SubscribeChk() {
        return confirmMyCategory_SubscribeChk;
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public String commit_action() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Commit");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        return null;
    }

    public String selectMyCategory_action() {
        if (selectMyCategoryList.getSelectedRowKeys().getSize() == 0) {
            this.showFacesMessage(FacesMessage.SEVERITY_WARN, null, "请先选择一个分类", "");
            return null;
        }

        JobSubCategoryVOImpl vo =
            (JobSubCategoryVOImpl) this.findViewObjectFromBindingIterator("JobSubCategory1Iterator");
        JobSubCategoryVORowImpl row = (JobSubCategoryVORowImpl) vo.createRow();
        vo.insertRow(row);
        vo.setCurrentRow(row);
        return "next";
    }

    public void setSelectMyCategoryList(RichListView selectMyCategoryList) {
        this.selectMyCategoryList = selectMyCategoryList;
    }

    public RichListView getSelectMyCategoryList() {
        return selectMyCategoryList;
    }

    public void setToNextPageBtn(RichButton toNextPageBtn) {
        this.toNextPageBtn = toNextPageBtn;
    }

    public RichButton getToNextPageBtn() {
        return toNextPageBtn;
    }


}
