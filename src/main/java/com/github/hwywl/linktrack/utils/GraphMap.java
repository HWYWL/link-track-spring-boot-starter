package com.github.hwywl.linktrack.utils;

import com.github.hwywl.linktrack.model.LinkTrackNode;
import com.github.hwywl.linktrack.system.MethodType;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 构造前端数据
 *
 * @author huangwenyi
 */
public class GraphMap {

    /**
     * 只需保证可见性，无需保证线程安全
     */
    private volatile static Map<String, LinkTrackNode> runTimeNodeMap;

    static {
        runTimeNodeMap = new HashMap<>();
    }

    public static LinkTrackNode get(String key) {
        return runTimeNodeMap.get(key);
    }

    public static LinkTrackNode put(String key, LinkTrackNode linkTrackNode) {
        return runTimeNodeMap.put(key, linkTrackNode);
    }

    public static boolean containsKey(String key) {
        return GraphMap.runTimeNodeMap.containsKey(key);
    }

    public static List<LinkTrackNode> get(MethodType methodType) {
        return runTimeNodeMap.values().stream()
                .filter(runTimeNode -> runTimeNode.getMethodType() == methodType &&
                        !runTimeNode.getMethodName().contains("lambda$"))
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
    }

    /**
     * 获取树形结构
     * @param key 主节点的方法名/子节点的类名+方法名
     * @return
     */
    public static LinkTrackNode getTree(String key) {
        LinkTrackNode root = runTimeNodeMap.get(key);
        if (root == null) {
            return root;
        }
        root.setValue(root.getAvgRunTime());
        List<LinkTrackNode> children = root.getChildren();
        if (children != null && children.size() > 0) {
            children.forEach(child -> {
                String childKey = child.getClassName() + "." + child.getMethodName();
                // 递归调用，组成树的子节点
                LinkTrackNode newChild = getTree(childKey);
                if (newChild != null) {
                    child.setChildren(newChild.getChildren());
                    child.setValue(child.getAvgRunTime());
                }
            });
        }

        return root;
    }
}
