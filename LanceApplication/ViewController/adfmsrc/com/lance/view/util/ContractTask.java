package com.lance.view.util;

import oracle.jbo.Row;

public class ContractTask {

    //vo应该包含：
    //当前状态,谁可以产生这个状态,下一个可以跳到的状态
    public static final String[][] FLOW = {
        { "draft", "client","waiting" }, { "waiting", "lancer", "process" }, { "process", "sys" },
        { "done", "client" }
    };

    public ContractTask() {
        super();
    }

    public String audit(Row row, String currentUser) {
        String currentStatus = (String) row.getAttribute("ProcessStatus");
        String[] curNode = findFlowNodeByStatus(currentStatus);
        String[] nextNode = findNextNode(currentStatus);
        String optUser = findStatusOptUser(curNode);
        if (!optUser.equals(currentUser)) {
            return "用户"+currentUser+"不能执行此操作";
        }
        row.setAttribute("ProcessStatus", nextNode);
        return null;
    }

    public String[] findFlowNodeByStatus(String status) {
        for (String[] node : FLOW) {
            if (node[0].equals(status)) {
                return node;
            }
        }
        return null;
    }

    public String findStatusOptUser(String[] node) {
        return node[1];
    }

    public String[] findNextNode(String status) {
        for (int i = 0; i < FLOW.length; i++) {
            String[] node = FLOW[i];
            if (node[0].equals(status)) {
                i++;
                return FLOW[i];
            }
        }
        return null;
    }

}
