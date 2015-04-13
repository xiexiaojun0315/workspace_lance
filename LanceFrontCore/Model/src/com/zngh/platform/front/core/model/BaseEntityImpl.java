package com.zngh.platform.front.core.model;

import oracle.jbo.AttributeList;
import oracle.jbo.server.EntityImpl;

public class BaseEntityImpl extends EntityImpl {
    public BaseEntityImpl() {
        super();
    }

    @Override
    protected void create(AttributeList attributeList) {
        String[] attrs = this.getAttributeNames();
        for (String attr : attrs) {
            if ("Uuid".equals(attr)) {
                this.setAttribute("Uuid", java.util.UUID.randomUUID().toString().replace("-", ""));
                break;
            }
        }
        super.create(attributeList);
    }
    
}
