package cn.crabime.practice.collections;

import java.util.ArrayList;
import java.util.List;

public class Skiplist {

    private List<Node> heads;
    // 使用 ArrayList 保存每层链表的表头，表头不存储元素，值为 -i ， i为层数，从 1 开始。

    private class Node{
        int val;
        // 使用双链表方便操作
        Node left,right,down;

        public Node(int val) {
            this.val = val;
            this.left = null;
            this.down = null;
            this.right = null;
        }
    }

    public Skiplist() {
        Node head0 = new Node(-1);
        heads = new ArrayList<>();
        heads.add(head0);
    }

    public boolean search(int target){
        int level = heads.size()-1;
        Node searchingStart = heads.get(level);
        while(level != -1){
            boolean jump = false;
            while(searchingStart.right!=null){
                searchingStart = searchingStart.right;
                if(searchingStart.val==target)
                    return true;
                else if(searchingStart.val>target){
                    if(level == 0)
                        return false;
                    else {
                        level--;
                        searchingStart = searchingStart.left.down;
                        jump = true;
                        break;
                    }
                }
            }
            if(jump)
                continue;
            level--;
            searchingStart = searchingStart.down;
        }
        return false;
    }

    public void add(int num){
        // 在底层添加元素
        Node n0 = new Node( num);
        Node[] jumpingNodes = searchNodes(num);
        Node lefter = jumpingNodes[0];
        n0.right = lefter.right;
        n0.left = lefter;
        if(lefter.right!=null)
            lefter.right.left=n0;
        lefter.right = n0;

        // 添加索引
        int levelToAddIndex = 1;
        while(throwCoin()){
            Node hi;
            if(jumpingNodes.length <= levelToAddIndex){
                hi= new Node(-levelToAddIndex-1);
                heads.add(hi);
                hi.down = heads.get(levelToAddIndex-1);
            }
            else
                hi = jumpingNodes[levelToAddIndex];
            Node ni = new Node(num);
            // 连接上下层相同的元素，用于跳
            ni.down = n0;
            ni.right = hi.right;
            ni.left = hi;
            if(hi.right!=null)
                hi.right.left = ni;
            hi.right = ni;
            n0 = ni;
            levelToAddIndex++;
        }
    }

    public boolean erase(int num) {
        Node[] jmpt = searchNodes(num);
        boolean exist = false;
        for (Node n:jmpt)
            if(n.val==num){
                exist = true;
                del(n);
            }
        return exist;
    }

    private Node[] searchNodes(int target){
        // 本函数也能实现 public boolean search(int target)的功能，但是由于是直到底层才停止的，效果不如在中间层找到就直接返回 true 的好。
        Node[] jumps = new Node[heads.size()];
        int level = heads.size()-1;
        Node searchingStart = heads.get(level);
        while(level!=-1){
            boolean jump = false;
            while(searchingStart.right!=null){
                searchingStart = searchingStart.right;
                if(searchingStart.val==target) {
                    while(level!=-1){
                        jumps[level] = searchingStart;
                        searchingStart=searchingStart.down;
                        level --;
                    }
                    return jumps;
                }
                else if(searchingStart.val>target){
                    jumps[level] = searchingStart.left;
                    level --;
                    searchingStart = searchingStart.left.down;
                    jump = true;
                    break;
                }
            }
            if(jump)
                continue;
            jumps[level] = searchingStart;
            level --;
            searchingStart = searchingStart.down;
        }
        return jumps;
    }

    private void del(Node n){
        n.left.right = n.right;
        if(n.right != null ) {
            n.right.left = n.left;
        }
    }

    private boolean throwCoin(){
        // 抛硬币， 50% 概率
        return Math.random()<0.5f;
    }

}

