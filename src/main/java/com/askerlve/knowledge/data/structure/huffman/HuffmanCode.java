package com.askerlve.knowledge.data.structure.huffman;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Askerlve
 * @Description: 霍夫漫对字符串的压缩与解压综的实现
 * @date 2019/3/11上午10:03
 */
public class HuffmanCode {

    /** 根节点信息，根据编码后设置根节点信息 */
    public CodeNode root;

    /** 节点信息 */
    public class CodeNode {
        /** 编码存储的数据信息 */
        public char data;

        /** 字符出现的频率 */
        public int frequence;

        /** 霍夫漫编码左节点 */
        public CodeNode left;

        /** 霍夫漫编码右节点 */
        public CodeNode right;

        /** 父节点信息 */
        public CodeNode parent;

        /** 标识是否为计算添加节点 */
        public boolean addNode;

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("CodeNode{");
            sb.append("data=").append(data);
            sb.append(", frequence=").append(frequence);
            sb.append('}');
            return sb.toString();
        }
    }

    /**
     * 编码，形成每个字符的霍夫漫编码
     *
     * @param map 统计信息
     * @return 结果编码后的信息
     */
    public Map<Character, String> getHuffManCode(Map<Character, Integer> map) {
        if (null != map && !map.isEmpty()) {
            // 使用小顶堆来进行数据的存储
            PriorityQueue<CodeNode> nodePriQueue =
                    new PriorityQueue<>(
                            map.size(),
                            (o1, o2) -> {
                                if (o1.frequence > o2.frequence) {
                                    return 1;
                                } else if (o1.frequence < o2.frequence) {
                                    return -1;
                                }
                                return 0;
                            });

            CodeNode nodeTmp = null;

            // 1,将数据放入小顶堆中
            for (Map.Entry<Character, Integer> item : map.entrySet()) {

                nodeTmp = new CodeNode();
                nodeTmp.data = item.getKey();
                nodeTmp.frequence = item.getValue();

                nodePriQueue.offer(nodeTmp);
            }

            int queueSize = nodePriQueue.size();

            // 将统计数据编译成霍夫漫编码树信息
            for (int i = 1; i < queueSize; i++) {
                CodeNode left = nodePriQueue.poll();
                CodeNode right = nodePriQueue.poll();

                CodeNode sumNode = new CodeNode();
                sumNode.frequence = left.frequence + right.frequence;
                sumNode.data = (char) ((int) left.data + (int) right.data);
                sumNode.addNode = true;

                sumNode.left = left;
                sumNode.right = right;

                left.parent = sumNode;
                right.parent = sumNode;

                nodePriQueue.offer(sumNode);
            }

            // 构建完成
            root = nodePriQueue.poll();

            // 构建霍夫漫编码
            Map<Character, String> result = this.builderCode(root);

            return result;
        }

        return null;
    }

    public Map<Character, String> builderCode(CodeNode root) {

        Map<Character, String> result = new HashMap<>();

        StringBuilder code = new StringBuilder();

        this.buildCode(code, root, result);

        return result;
    }

    /**
     * 进行霍夫温编码的操作，此处使用递归来实现
     *
     * @param code 主串信息
     * @param node 霍夫漫编码树信息
     * @param result 存储的结果节点信息
     */
    private void buildCode(StringBuilder code, CodeNode node, Map<Character, String> result) {
        if (null == node) {
            return;
        }

        if (!node.addNode) {
            result.put(node.data, code.toString());
        }

        if (node.left != null) {
            code.append("0");
            this.buildCode(code, node.left, result);
            code.deleteCharAt(code.length() - 1);
        }

        if (node.right != null) {
            code.append("1");
            this.buildCode(code, node.right, result);
            code.deleteCharAt(code.length() - 1);
        }
    }

    public String parseHuffman2(String src, Map<Character, String> huffman) {
        StringBuilder out = new StringBuilder();

        char[] hufSrcs = src.toCharArray();

        for (char hufs : hufSrcs) {
            out.append(huffman.get(hufs));
        }

        return out.toString();
    }

    /**
     * 进行霍夫漫的解码操作
     *
     * @param hufStr
     * @param root
     * @return
     */
    public String decodeHuffman(String hufStr, CodeNode root) {
        char[] hubs = hufStr.toCharArray();

        int index = 0;

        StringBuilder resultMsg = new StringBuilder();

        while (index < hubs.length) {
            CodeNode node = root;

            do {
                if (hubs[index] == '0') {
                    node = node.left;
                } else if (hubs[index] == '1') {
                    node = node.right;
                }
                index++;
            } while (index < hubs.length && (node.left != null || node.right != null));

            resultMsg.append(node.data);
        }

        return resultMsg.toString();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            int valueRand = ThreadLocalRandom.current().nextInt(1, 50);

            StringBuilder msg = new StringBuilder();

            for (int j = 0; j < valueRand; j++) {
                msg.append((char) ThreadLocalRandom.current().nextInt(65, 122));
            }

            String src = "我我我我我我我我我我我我是是是是是是小小小小小小thisis" + msg.toString();

            Map<Character, Integer> conMap = StrProc.countCharset(src);

            System.out.println(conMap);

            HuffmanCode instance = new HuffmanCode();
            Map<Character, String> huffCode = instance.getHuffManCode(conMap);
            System.out.println(huffCode);

            Integer value = Integer.parseInt("1010", 2);
            System.out.println(value);

            // Map<Character, Byte> parseTwo = instance.encodeHuf(huffCode);

            // String hufOutValue = instance.parseHuffman(src, parseTwo);
            String hufOutValue = instance.parseHuffman2(src, huffCode);

            // DataOutputStreamHuffman.OUTPUT.outtoFile(src.getBytes(StandardCharsets.UTF_8));
            // DataOutputStreamHuffman.OUTPUT.outHuffmantoFile(hufOutValue.getBytes());

            String deValue = instance.decodeHuffman(hufOutValue, instance.root);
            System.out.println("原始" + src);
            System.out.println("结果" + deValue);
            System.out.println("equals=" + src.equals(deValue));

            System.out.println(
                    "--------------------------------------------------------------------------------");
        }
    }

}
