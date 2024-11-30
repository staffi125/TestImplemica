import java.math.BigInteger;
import java.util.*;

public class Main {

    // Клас для опису ребра
    static class Edge {
        int target; // Індекс міста
        int cost;

        public Edge(int target, int cost) {
            this.target = target;
            this.cost = cost;
        }
    }

    // Клас для вузла в пріоритетній черзі
    static class Node {
        int city; // Індекс міста
        int distance;

        public Node(int city, int distance) {
            this.city = city;
            this.distance = distance;
        }
    }

    // Метод для знаходження мінімальних відстаней
    public static int dijkstra(Map<Integer, List<Edge>> graph, int start, int end, int n) {
        int[] distances = new int[n + 1]; // Масив для зберігання відстаней
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[start] = 0;

        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(nr -> nr.distance));
        priorityQueue.add(new Node(start, 0));

        while (!priorityQueue.isEmpty()) {
            Node currentNode = priorityQueue.poll();
            int currentCity = currentNode.city;

            // Якщо досягли кінцевого вузла, повертаємо мінімальну вартість
            if (currentCity == end) {
                return distances[end];
            }

            for (Edge edge : graph.getOrDefault(currentCity, new ArrayList<>())) {
                int newDist = distances[currentCity] + edge.cost;
                if (newDist < distances[edge.target]) {
                    distances[edge.target] = newDist;
                    priorityQueue.add(new Node(edge.target, newDist));
                }
            }
        }

        return -1; // Якщо шлях недосяжний
    }

    // Метод для обчислення числа Каталана
    public static int catalanNumber(int n) {
        if (n == 0) return 1; // Базовий випадок
        int result = 0;
        for (int i = 0; i < n; i++) {
            result += catalanNumber(i) * catalanNumber(n - 1 - i);
        }
        return result;
    }

    // Метод для обчислення факторіала
    public static BigInteger calculateFactorial(int n) {
        BigInteger result = BigInteger.ONE;
        for (int i = 2; i <= n; i++) {
            result = result.multiply(BigInteger.valueOf(i));
        }
        return result;
    }

    // Метод для обчислення суми цифр у великому числі
    public static int calculateSumOfDigits(BigInteger number) {
        String numberString = number.toString();
        int sum = 0;
        for (char digit : numberString.toCharArray()) {
            sum += Character.getNumericValue(digit);
        }
        return sum;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Обчислення числа Каталана
        System.out.print("Введіть число пар дужок (N): ");
        int n = scanner.nextInt();
        System.out.println("Число Каталана для N = " + n + " дорівнює: " + catalanNumber(n));

        // Обчислення суми цифр факторіала 100
        BigInteger factorial100 = calculateFactorial(100);
        int sumOfDigits = calculateSumOfDigits(factorial100);
        System.out.println("Сума цифр у числі 100! дорівнює: " + sumOfDigits);

        // Робота з графом для задачі з мінімальними відстанями
        System.out.print("Введіть кількість тестів: ");
        int s = scanner.nextInt(); // Кількість тестів
        scanner.nextLine(); // Пропустити символ нового рядка

        for (int t = 0; t < s; t++) {
            int citiesCount = scanner.nextInt(); // Кількість міст
            scanner.nextLine();

            Map<String, Integer> cityToIndex = new HashMap<>(); // Відображення імені міста у його індекс
            Map<Integer, List<Edge>> graph = new HashMap<>(); // Граф

            for (int i = 1; i <= citiesCount; i++) {
                String cityName = scanner.nextLine();
                cityToIndex.put(cityName, i);

                int neighbors = scanner.nextInt(); // Кількість сусідів
                for (int j = 0; j < neighbors; j++) {
                    int neighbor = scanner.nextInt();
                    int cost = scanner.nextInt();

                    graph.computeIfAbsent(i, k -> new ArrayList<>()).add(new Edge(neighbor, cost));
                }
                if (scanner.hasNextLine()) scanner.nextLine(); // Пропустити символ нового рядка
            }

            int queries = scanner.nextInt(); // Кількість запитів
            scanner.nextLine();

            for (int i = 0; i < queries; i++) {
                String startCity = scanner.next();
                String endCity = scanner.next();

                int startIndex = cityToIndex.get(startCity);
                int endIndex = cityToIndex.get(endCity);

                int cost = dijkstra(graph, startIndex, endIndex, citiesCount);
                System.out.println("Мінімальна вартість з " + startCity + " до " + endCity + ": " + cost);
            }

            if (scanner.hasNextLine()) scanner.nextLine(); // Пропустити порожній рядок між тестами
        }
    }
}
