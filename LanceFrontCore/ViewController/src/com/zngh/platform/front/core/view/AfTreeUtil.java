package com.zngh.platform.front.core.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import oracle.adf.view.rich.component.rich.data.RichTree;

import oracle.jbo.Key;

import org.apache.myfaces.trinidad.model.RowKeySet;

public class AfTreeUtil {

    /**
     * 获取被选中的树节点
     * @param tree
     * @return
     */
    public static List<Key> getSelectedTreeKeys(RichTree tree) {
        RowKeySet rks = tree.getSelectedRowKeys();
        Iterator rksIterator = rks.iterator();
        List selected = new ArrayList();
        while (rksIterator.hasNext()) {
            List keys = (List) rksIterator.next();
            Key key = (Key) keys.get(keys.size() - 1);
            selected.add(key.getKeyValues()[0]);
        }
        return selected;
    }

}
