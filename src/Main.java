import java.util.ArrayList;
import java.util.List;



public class Main {
    public static void main(String[] args) {
        // 1. Задаем граф
        // Вершины нумеруются с 0
        int V = 6; // 6 вершин (0, 1, 2, 3, 4, 5)
        List<Edge> allEdges = new ArrayList<>();

        allEdges.add(new Edge(0, 1, 4));
        allEdges.add(new Edge(0, 2, 3));
        allEdges.add(new Edge(1, 2, 1));
        allEdges.add(new Edge(1, 3, 2));
        allEdges.add(new Edge(2, 3, 4));
        allEdges.add(new Edge(3, 4, 2));
        allEdges.add(new Edge(4, 5, 6));
        allEdges.add(new Edge(2, 5, 5)); // Добавим еще ребер
        allEdges.add(new Edge(2, 4, 7));

        System.out.println("--- Исходный граф (все ребра) ---");
        for (Edge e : allEdges) System.out.println(e);
        System.out.println("=========================================\n");

        // 2. Создаем обработчик и строим MST
        MSTHandler handler = new MSTHandler(V, allEdges);
        handler.buildMST();

        // 3. Отображаем исходный MST
        System.out.println("--- Построен исходный MST ---");
        handler.displayMST();
        System.out.println("=========================================\n");

        // 4. Удаляем ребро из MST
        // Возьмем, к примеру, второе ребро из построенного MST
        List<Edge> currentMST = handler.getMstEdges();
        if (currentMST.size() > 1) {
            Edge edgeToRemove = currentMST.get(1); // Пример: удаляем ребро (1, 3) w: 2

            // 5. Запускаем процесс удаления и пересоединения
            handler.removeAndReconnect(edgeToRemove);
        } else {
            System.out.println("В MST недостаточно ребер для удаления.");
        }
    }
}