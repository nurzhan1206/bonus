import java.util.*;

public class MSTHandler {

    private final int V; // Количество вершин
    private final List<Edge> allEdges; // Все ребра исходного графа
    private List<Edge> mstEdges; // Ребра, составляющие MST

    public MSTHandler(int V, List<Edge> allEdges) {
        this.V = V;
        this.allEdges = allEdges;
        this.mstEdges = new ArrayList<>();
    }

    /**
     * 1. Строит MST с использованием алгоритма Краскала.
     */
    public void buildMST() {
        // Сортируем все ребра по весу
        Collections.sort(allEdges);

        DisjointSet ds = new DisjointSet(V);
        mstEdges.clear();

        for (Edge edge : allEdges) {
            int rootU = ds.find(edge.u);
            int rootV = ds.find(edge.v);

            // Если ребро не образует цикл, добавляем его в MST
            if (rootU != rootV) {
                mstEdges.add(edge);
                ds.union(rootU, rootV);
            }
        }
    }

    /**
     * 2. Отображает текущие ребра MST.
     */
    public void displayMST() {
        int totalWeight = 0;
        System.out.println("Текущие ребра в MST:");
        for (Edge edge : mstEdges) {
            System.out.println("  " + edge);
            totalWeight += edge.weight;
        }
        System.out.println("Общий вес MST: " + totalWeight);
    }

    /**
     * 3. Удаляет ребро и находит заменяющее.
     */
    public void removeAndReconnect(Edge edgeToRemove) {
        if (!mstEdges.remove(edgeToRemove)) {
            System.out.println("Ошибка: Ребро " + edgeToRemove + " не найдено в MST.");
            return;
        }

        System.out.println("\n--- Удаление ребра: " + edgeToRemove + " ---");

        // 4. Показываем компоненты после удаления
        // После удаления ребра (u, v) дерево распадается на 2 компоненты.
        // Найдем все вершины в одной компоненте (например, достижимые из u).
        Set<Integer> component1 = findComponent(edgeToRemove.u);
        System.out.println("Компонента 1 (вершины): " + component1);

        Set<Integer> component2 = new HashSet<>();
        for(int i = 0; i < V; i++) {
            if(!component1.contains(i)) {
                component2.add(i);
            }
        }
        System.out.println("Компонента 2 (вершины): " + component2);


        // 5. Находим новое ребро для соединения
        Edge bestReplacement = findReplacementEdge(component1);

        if (bestReplacement != null) {
            mstEdges.add(bestReplacement);
            System.out.println("\nНайдено заменяющее ребро: " + bestReplacement);

            // 6. Отображаем новый MST
            System.out.println("--- Новый MST после пересоединения ---");
            displayMST();
        } else {
            System.out.println("\nНе удалось найти заменяющее ребро (граф стал несвязным).");
        }
    }

    // Вспомогательный метод для поиска компоненты связности с помощью BFS
    private Set<Integer> findComponent(int startNode) {
        Set<Integer> component = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();

        queue.add(startNode);
        component.add(startNode);

        // Создаем список смежности из ОСТАВШИХСЯ ребер MST
        Map<Integer, List<Integer>> adj = new HashMap<>();
        for (Edge e : mstEdges) {
            adj.computeIfAbsent(e.u, k -> new ArrayList<>()).add(e.v);
            adj.computeIfAbsent(e.v, k -> new ArrayList<>()).add(e.u);
        }

        while (!queue.isEmpty()) {
            int u = queue.poll();
            if (adj.containsKey(u)) {
                for (int v : adj.get(u)) {
                    if (!component.contains(v)) {
                        component.add(v);
                        queue.add(v);
                    }
                }
            }
        }
        return component;
    }

    // Вспомогательный метод для поиска самого дешевого ребра,
    // соединяющего две компоненты.
    private Edge findReplacementEdge(Set<Integer> component1) {
        Edge bestEdge = null;

        // Ищем среди ВСЕХ ребер исходного графа
        for (Edge edge : allEdges) {
            boolean uInComp1 = component1.contains(edge.u);
            boolean vInComp1 = component1.contains(edge.v);

            // Если ребро соединяет две разные компоненты
            // (одна вершина в component1, другая - нет)
            if (uInComp1 != vInComp1) {
                // Мы ищем ребро с минимальным весом
                if (bestEdge == null || edge.weight < bestEdge.weight) {
                    bestEdge = edge;
                }
            }
        }
        return bestEdge;
    }

    // Геттер для получения ребер MST (нужен для Main)
    public List<Edge> getMstEdges() {
        return new ArrayList<>(mstEdges);
    }
}