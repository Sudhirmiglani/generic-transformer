package com.rubix.transformer.util;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * Created by sudhir.m on 17/11/16.
 */
public class BinarySearchTree {

    private Node root;
    private HashMap<String, String> leaves;

    @Data
    private static class Node {
        private String data;
        private Map<String, Node> children;

        Node(String string) {
            this.data = string;
            children = new HashMap<>();
        }
    }

    public BinarySearchTree() {
        root = new Node("" + (char) 0);
        leaves = new HashMap<>();
    }

    public void insert(final String source, final String destination) {

        String arr[] = source.split("\\.");
        Node crawl = root;

        for (int i = 0; i < arr.length; i++) {
            final String key = arr[i];
            Map<String, Node> children = crawl.getChildren();
            if (children.containsKey(key)) {
                if (leaves.containsKey(key)) {
                    leaves.remove(key);
                }
                crawl = children.get(key);
            } else {
                Node node = new Node(key);
                children.put(key, node);
                leaves.put(source, destination);
            }
        }
    }

    public Map<String, String> getAllLeaveNodes() {
        return leaves;
    }

}
