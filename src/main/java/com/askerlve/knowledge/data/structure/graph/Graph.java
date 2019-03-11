package com.askerlve.knowledge.data.structure.graph;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @author Askerlve
 * @Description: 使用邻接表实现的无向图
 * @date 2019/1/28上午9:51
 */
public class Graph {
    private int v; // 顶点的个数
    private LinkedList<Integer> adj[]; // 邻接表

    public Graph(int v) {
        this.v = v;
        adj = new LinkedList[v];
        for (int i=0; i<v; ++i) {
            adj[i] = new LinkedList<>();
        }
    }

    public void addEdge(int s, int t) { // 无向图一条边存两次
        adj[s].add(t);
        adj[t].add(s);
    }

    //广度优先搜索
    public void bfs(int s, int t) {
        if (s == t) return;
        //标记已被访问的顶点
        boolean[] visited = new boolean[v];
        visited[s]=true;
        //用来存储已经被访问，但是相连的顶点还没背访问的顶点
        Queue<Integer> queue = new LinkedList<>();
        queue.add(s);
        //搜索路径
        int[] prev = new int[v];
        for (int i = 0; i < v; ++i) {
            prev[i] = -1;
        }
        while (queue.size() != 0) {
            int w = queue.poll();
            for (int i = 0; i < adj[w].size(); ++i) {
                int q = adj[w].get(i);
                if (!visited[q]) {
                    prev[q] = w;
                    if (q == t) {
                        print(prev, s, t);
                        return;
                    }
                    visited[q] = true;
                    queue.add(q);
                }
            }
        }
    }
    private void print(int[] prev, int s, int t) { // 递归打印 s->t 的路径
        if (prev[t] != -1 && t != s) {
            print(prev, s, prev[t]);
        }
        System.out.print(t + " ");
    }


    //深度优先搜索
    boolean found = false; // 全局变量或者类成员变量
    public void dfs(int s, int t) {
        found = false;
        //标记已被访问的顶点
        boolean[] visited = new boolean[v];
        //搜索路径
        int[] prev = new int[v];
        for (int i = 0; i < v; ++i) {
            prev[i] = -1;
        }
        recurDfs(s, t, visited, prev);
        print(prev, s, t);
    }

    private void recurDfs(int w, int t, boolean[] visited, int[] prev) {
        if (found == true) return;
        visited[w] = true;
        if (w == t) {
            found = true;
            return;
        }
        for (int i = 0; i < adj[w].size(); ++i) {
            int q = adj[w].get(i);
            if (!visited[q]) {
                prev[q] = w;
                recurDfs(q, t, visited, prev);
            }
        }
    }


}

class MyGraph {

    /** 顶点的个数 */
    private int v;

    private LinkedList<Edge> adj[];

    public MyGraph(int v) {
        this.v = v;
        this.adj = new LinkedList[v];

        for (int i = 0; i < v; i++) {
            this.adj[i] = new LinkedList<>();
        }
    }

    /**
     * 添加一条区，
     *
     * @param s 起始顶点
     * @param t 结束顶点
     * @param w 权重，现在权重为距离
     */
    public void addEdge(int s, int t, int w) {
        this.adj[s].add(new Edge(s, t, w));
    }

    /** */
    private class Edge {

        /** 起始顶点 */
        public int sid;

        /** 结束顶点 */
        public int tid;

        /** 权重 */
        public int w;

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
     * 顶点s到顶点t的最短路径,dijkstra算法
     *
     * @param s 开始顶点
     * @param t 结束顶点
     */
    public void dijkstra(int s, int t) {
        // 1,声明一个数组用来记录下已经访问过的下标
        int[] process = new int[this.v];

        // 2,用来记录从开始顶点到每个顶点的权重，当前权重为距离
        Vertex[] vertextNext = new Vertex[this.v];

        // 3,用于标识当前对象是否被访问过
        boolean[] visted = new boolean[this.v];

        // 初始化vertextNext数组
        for (int i = 0; i < this.v; i++) {
            vertextNext[i] = new Vertex(i, Integer.MAX_VALUE);
        }

        // 小顶堆，用来计算最短距离
        PriorityQueue<Vertex> queue = new PriorityQueue<>();

        // 放入第一个顶点
        queue.add(vertextNext[s]);
        // 首个顶点的权重更新，即距离更新为1
        vertextNext[s].dist = 0;
        // 标识当前第一个顶点被访问
        visted[s] = true;

        Vertex minVertext = null;

        while (!queue.isEmpty()) {
            minVertext = queue.poll();
            // 检查当前顶点是否为目标顶点，如果为目标顶点，则退出
            if (minVertext.id == t) {
                break;
            }

            for (int i = 0; i < adj[minVertext.id].size(); i++) {
                Edge runItem = adj[minVertext.id].get(i);

                // 得到下一个顶点
                Vertex verNext = vertextNext[runItem.tid];

                if (minVertext.dist + runItem.w < verNext.dist) {

                    verNext.dist = minVertext.dist + runItem.w;

                    // 记录下当前的顶点的前一个顶点
                    process[verNext.id] = minVertext.id;

                    // 如果当前顶点未访问过，则加入队列继续访问
                    if (visted[verNext.id] == false) {
                        queue.add(verNext);
                        visted[verNext.id] = true;
                    }
                }
            }
        }

        System.out.println("打印路径");
        System.out.print(s);
        print(s, t, process);
    }

    private void print(int s, int t, int[] process) {
        if (s == t) {
            return;
        }

        print(s, process[t], process);

        System.out.print("-->" + t);
    }

    public static void main(String[] args) {
        MyGraph instance = new MyGraph(15);
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
        instance.addEdge(9, 12, 1);
        instance.addEdge(10, 13, 3);
        instance.addEdge(11, 13, 2);
        instance.addEdge(11, 14, 2);
        instance.addEdge(11, 14, 1);
        instance.addEdge(12, 14, 2);
        instance.dijkstra(3, 14);
    }

}
