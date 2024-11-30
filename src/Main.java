import java.math.BigInteger; // For handling very large numbers
import java.util.*; // For Scanner, Map, List, and other utility classes

public class Main {

    // Class to represent an edge in a graph
    static class Edge {
        int target; // Index of the destination city
        int cost;   // Cost of traveling to the destination

        // Constructor for initializing edge properties
        public Edge(int target, int cost) {
            this.target = target;
            this.cost = cost;
        }
    }

    // Class to represent a node in the priority queue
    static class Node {
        int city; // Index of the city
        int distance; // Distance from the start city

        // Constructor for initializing node properties
        public Node(int city, int distance) {
            this.city = city;
            this.distance = distance;
        }
    }

    // Dijkstra's algorithm for finding the shortest path in a weighted graph
    public static int dijkstra(Map<Integer, List<Edge>> graph, int start, int end, int n) {
        int[] distances = new int[n + 1]; // Array to store minimum distances
        Arrays.fill(distances, Integer.MAX_VALUE); // Initialize all distances to infinity
        distances[start] = 0; // Distance to the starting city is 0

        // Priority queue for processing nodes by minimum distance
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(nr -> nr.distance));
        priorityQueue.add(new Node(start, 0)); // Start with the starting city

        while (!priorityQueue.isEmpty()) {
            Node currentNode = priorityQueue.poll(); // Get the node with the smallest distance
            int currentCity = currentNode.city;

            // If the destination city is reached, return the minimum cost
            if (currentCity == end) {
                return distances[end];
            }

            // Explore neighbors of the current city
            for (Edge edge : graph.getOrDefault(currentCity, new ArrayList<>())) {
                int newDist = distances[currentCity] + edge.cost; // Calculate the new distance
                if (newDist < distances[edge.target]) { // If a shorter path is found
                    distances[edge.target] = newDist; // Update the distance
                    priorityQueue.add(new Node(edge.target, newDist)); // Add the updated node to the queue
                }
            }
        }

        return -1; // If the destination is unreachable
    }

    // Recursive method to compute the nth Catalan number
    public static int catalanNumber(int n) {
        if (n == 0) return 1; // Base case
        int result = 0;
        for (int i = 0; i < n; i++) {
            result += catalanNumber(i) * catalanNumber(n - 1 - i); // Use the recurrence relation
        }
        return result;
    }

    // Method to compute the factorial of a number using BigInteger
    public static BigInteger calculateFactorial(int n) {
        BigInteger result = BigInteger.ONE; // Start with 1
        for (int i = 2; i <= n; i++) {
            result = result.multiply(BigInteger.valueOf(i)); // Multiply by each number up to n
        }
        return result;
    }

    // Method to compute the sum of digits in a large number
    public static int calculateSumOfDigits(BigInteger number) {
        String numberString = number.toString(); // Convert the number to a string
        int sum = 0;
        for (char digit : numberString.toCharArray()) { // Iterate through each digit
            sum += Character.getNumericValue(digit); // Add the numeric value of the digit to the sum
        }
        return sum;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Compute the Catalan number
        System.out.print("Enter the number of bracket pairs (N): ");
        int n = scanner.nextInt();
        System.out.println("The Catalan number for N = " + n + " is " + catalanNumber(n));

        // Compute the sum of digits in 100!
        BigInteger factorial100 = calculateFactorial(100);
        int sumOfDigits = calculateSumOfDigits(factorial100);
        System.out.println("The sum of digits in 100! is: " + sumOfDigits);

        // Shortest path calculation using Dijkstra's algorithm
        System.out.print("Enter the number of tests: ");
        int s = scanner.nextInt(); // Number of test cases
        scanner.nextLine(); // Skip the newline character

        for (int t = 0; t < s; t++) {
            int citiesCount = scanner.nextInt(); // Number of cities
            scanner.nextLine();

            Map<String, Integer> cityToIndex = new HashMap<>(); // Map for city name to index
            Map<Integer, List<Edge>> graph = new HashMap<>(); // Graph represented as an adjacency list

            for (int i = 1; i <= citiesCount; i++) {
                String cityName = scanner.nextLine();
                cityToIndex.put(cityName, i);

                int neighbors = scanner.nextInt(); // Number of neighboring cities
                for (int j = 0; j < neighbors; j++) {
                    int neighbor = scanner.nextInt();
                    int cost = scanner.nextInt();

                    // Add the edge to the graph
                    graph.computeIfAbsent(i, k -> new ArrayList<>()).add(new Edge(neighbor, cost));
                }
                if (scanner.hasNextLine()) scanner.nextLine(); // Skip the newline character
            }

            int queries = scanner.nextInt(); // Number of queries
            scanner.nextLine();

            for (int i = 0; i < queries; i++) {
                String startCity = scanner.next();
                String endCity = scanner.next();

                int startIndex = cityToIndex.get(startCity);
                int endIndex = cityToIndex.get(endCity);

                int cost = dijkstra(graph, startIndex, endIndex, citiesCount);
                System.out.println("The minimum cost from " + startCity + " to " + endCity + " is: " + cost);
            }

            if (scanner.hasNextLine()) scanner.nextLine(); // Skip the blank line between test cases
        }

        scanner.close(); // Close the Scanner to release resources
    }
}
