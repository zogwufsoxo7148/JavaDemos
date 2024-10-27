package com.example.javademos.demos.InterviewQuestion;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonTreeBuilder {

    public static void main(String[] args) throws Exception {
        // 输入的JSON字符串，表示一个扁平的节点结构
        String input = "[{\"key\":1,\"value\":\"alibaba\"},{\"key\":11,\"value\":\"aliyun\"},{\"key\":111,\"value\":\"gts\"},{\"key\":112,\"value\":\"cse\"},{\"key\":12,\"value\":\"taotian\"},{\"key\":121,\"value\":\"tmall\"}]";

        // 使用Jackson库将输入的JSON字符串解析为List<Map<String, Object>>结构
        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, Object>> inputList = objectMapper.readValue(input, List.class);

        // 调用 buildTree 函数，将扁平结构转换为树形结构
        Map<String, Object> tree = buildTree(inputList);

        // 将树形结构转换为格式化的JSON字符串并输出
        String outputJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(tree);
        System.out.println(outputJson);
    }

    /**
     * 将扁平的节点列表构建为树形结构
     * @param inputList 输入的节点列表，列表中的每个节点都包含 "key" 和 "value"
     * @return 构建完成的树形结构的根节点
     */
    public static Map<String, Object> buildTree(List<Map<String, Object>> inputList) {
        // 存储每个节点的 Map，以 key 为索引快速查找父子节点关系
        Map<Integer, Map<String, Object>> nodeMap = new HashMap<>();

        // 保存根节点引用
        Map<String, Object> root = null;

        // 遍历输入列表，初始化每个节点，并为每个节点设置 "key"、"value" 和 "children"（初始为空）
        for (Map<String, Object> item : inputList) {
            Map<String, Object> node = new HashMap<>();
            node.put("key", item.get("key"));
            node.put("value", item.get("value"));
            // 初始化子节点列表为空
            node.put("children", new ArrayList<>());
            // 将节点放入 nodeMap 中，key 为节点的 "key" 值
            nodeMap.put((Integer) item.get("key"), node);
        }

        // 第二次遍历，建立父子关系，构建树
        for (Map<String, Object> item : inputList) {
            Integer key = (Integer) item.get("key");
            // 获取当前节点
            Map<String, Object> node = nodeMap.get(key);
            // 通过整除10的方式，确定父节点的key
            Integer parentKey = key / 10;

            // 如果 parentKey 为0，说明是根节点
            if (parentKey == 0) {
                root = node;  // 设置根节点
            } else {
                // 获取父节点，并将当前节点加入父节点的 "children" 列表
                Map<String, Object> parentNode = nodeMap.get(parentKey);
                if (parentNode != null) {
                    List<Map<String, Object>> children = (List<Map<String, Object>>) parentNode.get("children");
                    children.add(node);  // 将当前节点添加到父节点的子节点列表中
                }
            }
        }

        // 返回构建好的树形结构的根节点
        return root;
    }
}

