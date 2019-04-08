package com.askerlve.knowledge.data.structure.top_sort;

import java.util.LinkedList;

/**
 * @author Askerlve
 * @Description: 拓扑排序
 * @date 2019/4/2上午9:24
 */
public class TopSort {

    /**
     * 有向无环图
     */
    private int v; // 顶点的个数
    private LinkedList<Integer> adj[]; // 邻接表

    public TopSort(int v) {
        this.v = v;
        adj = new LinkedList[v];
        for (int i = 0; i < v; ++i) {
            adj[i] = new LinkedList<>();
        }
    }

    /**
     * s 先于 t，边 s->t
     * @param s
     * @param t
     */
    public void addEdge(int s, int t) {
        adj[s].add(t);
    }

    /**
     * Kahn实现排序
     */
    public void topoSortByKahn() {

        /**
         * 统计每个顶点的入度
         */
        int[] inDegree = new int[v];
        for (int i = 0; i < v; ++i) {
            for (int j = 0; j < adj[i].size(); ++j) {
                int w = adj[i].get(j); // i->w
                inDegree[w]++;
            }
        }

        LinkedList<Integer> queue = new LinkedList<>();
        for (int i = 0; i < v; ++i) {
            if (inDegree[i] == 0) queue.add(i);
        }

        while (!queue.isEmpty()) {
            int i = queue.remove();
            System.out.print("->" + i);
            for (int j = 0; j < adj[i].size(); ++j) {
                int k = adj[i].get(j);
                inDegree[k]--;
                if (inDegree[k] == 0) queue.add(k);
            }
        }

        System.out.println();

        boolean cycleFlag = false;

        // 当完成之后，检查表的表中的入度，如果发现还有不为0，说明存在环
        for (int i = 0; i < inDegree.length; i++) {
            if (inDegree[i] != 0) {

                cycleFlag = true;

                break;
            }
        }

        if (cycleFlag) {
            System.out.println("当前存在环");
        } else {
            System.out.println("当前不存在环");
        }
    }

    /**
     * 深度优先实现
     */
    public void topoSortByDFS() {

        // 先构建逆邻接表，边 s->t 表示，s 依赖于 t，t 先于 s
        LinkedList<Integer> inverseAdj[] = new LinkedList[v];

        for (int i = 0; i < v; ++i) { // 申请空间
            inverseAdj[i] = new LinkedList<>();
        }

        for (int i = 0; i < v; ++i) { // 通过邻接表生成逆邻接表
            for (int j = 0; j < adj[i].size(); ++j) {
                int w = adj[i].get(j); // i->w
                inverseAdj[w].add(i); // w->i
            }
        }

        boolean[] visited = new boolean[v];
        for (int i = 0; i < v; ++i) { // 深度优先遍历图
            if (visited[i] == false) {
                visited[i] = true;
                dfs(i, inverseAdj, visited);
            }
        }

    }

    private void dfs(int vertex, LinkedList<Integer> inverseAdj[], boolean[] visited) {

        for (int i = 0; i < inverseAdj[vertex].size(); ++i) {
            int w = inverseAdj[vertex].get(i);
            if (visited[w] == true) continue;
            visited[w] = true;
            // 先把 vertex 这个顶点可达的所有顶点都打印出来之后，再打印它自己
            dfs(w, inverseAdj, visited);
        }

        System.out.print("->" + vertex);
    }


    public static void main(String[] args) {
        TopSort instance = new TopSort(10);
        instance.addEdge(6, 7);
        instance.addEdge(5, 8);
        instance.addEdge(7, 8);
        instance.addEdge(8, 9);
        instance.addEdge(4, 5);
        instance.addEdge(4, 6);
        instance.addEdge(1, 4);
        instance.addEdge(2, 4);
        instance.addEdge(3, 4);
        instance.topoSortByKahn();
        System.out.println();
        instance.topoSortByDFS();
    }

}
