package com.askerlve.knowledge.data.structure.shortest_path;

import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * @author Askerlve
 * @Description: 地图最短路径
 * @date 2019/4/4上午9:15
 */
public class Graph {

    private LinkedList<Edge> adj[]; // 邻接表
    private int v; // 顶点个数

    public Graph(int v) {
        this.v = v;
        this.adj = new LinkedList[v];
        for (int i = 0; i < v; ++i) {
            this.adj[i] = new LinkedList<>();
        }
    }

    public void addEdge(int s, int t, int w) { // 添加一条边
        this.adj[s].add(new Edge(s, t, w));
    }

    private class Edge {
        public int sid; // 边的起始顶点编号
        public int tid; // 边的终止顶点编号
        public int w; // 权重
        public Edge(int sid, int tid, int w) {
            this.sid = sid;
            this.tid = tid;
            this.w = w;
        }
    }
    /** 为实现dijkstra算法 */
    private class Vertex implements Comparable<Vertex> {

        /** 顶点编号的id */
        public int id;

        /** 从起始顶点到这个顶点的距离 */
        public int dist;

        public Vertex(int id, int dist) {
            this.id = id;
            this.dist = dist;
        }

        @Override
        public int compareTo(Vertex o) {

            if (o.dist > this.dist) {
                return -1;
            }

            return 1;
        }
    }

    /**
     * 顶点s到顶点t的最短路径
     *
     * @param s
     * @param t
     */
    public void dijkstra(int s, int t) {

        // 用来还原最短路径
        int[] predecessor = new int[this.v];
        // 记录到这个顶点的距离
        Vertex[] vertexes = new Vertex[this.v];
        // 初始化dist值为无穷大
        for (int i = 0; i < v; i++) {
            vertexes[i] = new Vertex(i, Integer.MAX_VALUE);
        }

        // 声明小顶堆
        PriorityQueue<Vertex> queue = new PriorityQueue<>();
        // 标记是否进入队列过
        boolean[] inqueue = new boolean[this.v];

        // 先把起始顶点放入队列中
        queue.add(vertexes[s]);

        // 初始顶点距离为0
        vertexes[s].dist = 0;
        inqueue[s] = true;

        while (!queue.isEmpty()) {
            // 取dist最小的顶点
            Vertex minVertext = queue.poll();

            // 如果找到当前结束顶点t,则返回
            if (minVertext.id == t) {
                break;
            }

            for (int i = 0; i < adj[minVertext.id].size(); i++) {
                // 取出现minVertext相领的边
                Edge e = adj[minVertext.id].get(i);
                // 取出下一条边
                Vertex nextVertext = vertexes[e.tid];
                // 找到next最短的路径
                if (minVertext.dist + e.w < nextVertext.dist) {
                    // 更新dist
                    nextVertext.dist = minVertext.dist + e.w;
                    // 更新前驱节点
                    predecessor[nextVertext.id] = minVertext.id;
                    // 如果没有在队列中，则加入队列中
                    if (inqueue[nextVertext.id] == false) {
                        queue.add(nextVertext);
                        inqueue[nextVertext.id] = true;
                    }else{
                        //更新nextVertext在队列重的位置
                        queue.remove(nextVertext);
                        queue.add(nextVertext);
                    }
                }
            }
        }

        System.out.println("最后输出");
        System.out.print(s);
        print(s, t, predecessor);
    }

    private void print(int s, int t, int[] predecessor) {
        if (s == t) {
            return;
        }
        print(s, predecessor[t], predecessor);

        System.out.print("-->" + t);
    }

    public static void main(String[] args) {
        Graph instance = new Graph(17);
        instance.addEdge(1, 5, 5);
        instance.addEdge(2, 5, 10);
        instance.addEdge(3, 7, 7);
        instance.addEdge(3, 6, 6);
        instance.addEdge(4, 6, 8);
        instance.addEdge(4, 7, 6);
        instance.addEdge(5, 8, 3);
        instance.addEdge(6, 8, 2);
        instance.addEdge(6, 9, 3);
        instance.addEdge(7, 9, 2);
        instance.addEdge(8, 10, 1);
        instance.addEdge(8, 11, 1);
        instance.addEdge(9, 11, 2);
        instance.addEdge(9, 12, 10);
        instance.addEdge(10, 13, 3);
        instance.addEdge(11, 13, 2);
        instance.addEdge(11, 14, 20);
        instance.addEdge(12, 14, 2);
        instance.addEdge(7, 15, 1);
        instance.addEdge(15, 14, 3);
        instance.dijkstra(3, 14);
    }

}

