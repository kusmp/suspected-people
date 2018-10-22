import com.sun.deploy.perf.PerfRollup;

import java.util.*;
import java.util.stream.Collectors;

public class main {


    public static void main(String[] args) {
        long peopleQuantity;
        double probability;
        long hotelsQuantity;
        long daysQuantity;
        int counter = 0;
        int pairCount = 0;
        long tempVar = 0;
        long pairDayCount = 0;
        List<String> filledPairs = new ArrayList<>();
        List<Integer> onlyPairs = new ArrayList<>();
        Set<String> hash_Set = new HashSet<>();
        List<Hotel> hotels = new ArrayList<>();
        Map<Integer, List<Integer[]>> pairsPerDay = new HashMap<>();

        Scanner odczyt = new Scanner(System.in);
        System.out.println("Liczba osób: ");
        peopleQuantity = odczyt.nextLong();
        odczyt = new Scanner(System.in).useLocale(Locale.US);
        System.out.println("Prawdopodobieństwo: ");
        probability = odczyt.nextDouble();
        odczyt = new Scanner(System.in);
        System.out.println("Liczba hoteli: ");
        hotelsQuantity = odczyt.nextLong();
        odczyt = new Scanner(System.in);
        System.out.println("Liczba dni: ");
        daysQuantity = odczyt.nextLong();
        odczyt = new Scanner(System.in);

        //generate hotels
        for (int i = 0; i < hotelsQuantity; i++) {
            hotels.add(new Hotel(i));
        }

        //generate people
        for (int j = 0; j < daysQuantity; j++) {

            counter = 0;
            List<Person> people = new ArrayList<>();
            Map<Integer, Integer> mapOfPeople = new HashMap<>();
            for (int i = 0; i < peopleQuantity; i++) {
                people.add(new Person(ifGo(probability), i));
            }
            for (Person person : people) {
                if (person.ifGo == true) {
                    counter++;
                    Random generator = new Random();
                    int hotelId = generator.nextInt(((int) hotelsQuantity)) + 1;
                    person.setHotelId(hotelId);
                    mapOfPeople.put(person.getId(), person.getHotelId());
                }
            }


            Map<Integer, List<Integer>> reverseMapOfPeople = mapOfPeople.entrySet()
                    .stream()
                    .collect(Collectors.groupingBy(Map.Entry::getValue,
                            Collectors.mapping(
                                    Map.Entry::getKey,
                                    Collectors.toList())));

            System.out.println("Day: " + (j + 1));
            System.out.println("Counter: " + counter);
            System.out.println(reverseMapOfPeople);

            //wyodrębnianie par
            for (Map.Entry<Integer, List<Integer>> entry : reverseMapOfPeople.entrySet()) {
                List<Integer> list = new ArrayList<Integer>(entry.getValue());
                for (int i = 0; i < list.size(); i++)
                    for (int p = i + 1; p < list.size(); p++) {
                        Collections.sort(list);
                        filledPairs.add(list.get(i).toString() + " " +list.get(p).toString());
                        System.out.println("pairs: " + list.get(i) + " " + list.get(p));
                    }

            }


        }
        //tworzenie may z parami i liczbą występowania
        Map<String, Integer> countPairs = new HashMap<>();
        for(String key : filledPairs){
            Integer tempString = countPairs.get(key);
            if(tempString == null){
                countPairs.put(key, 1);
            }
            else {
                countPairs.put(key, countPairs.get(key) + 1);
                if(tempString==1) {
                    pairCount++;
                }
            }
        }

        //obliczanie unikalnych osób
        for (Map.Entry<String, Integer> entry : countPairs.entrySet()) {
            String key = entry.getKey();
            String[] separatedNumbers = key.split(" ");
            if(entry.getValue() > 1){
            hash_Set.add(separatedNumbers[0]);
            hash_Set.add(separatedNumbers[1]);
            }

        }


//        for (int tempcount : countPairs.values()){
//           if(tempcount > 1)
//            tempVar += tempcount;
//        }

        //wyliczanie par-dni
        for (Map.Entry<String, Integer> entry : countPairs.entrySet()) {
            Integer value = entry.getValue();
            if(value>1) {
                value = (value * (value - 1) / 2);
                pairDayCount+=value;
                onlyPairs.add(value);
            }

        }

//        Map<Integer, Integer> histogram = new HashMap<>();
//        for (Map.Entry<String, Integer> entry : countPairs.entrySet()) {
//            Integer value = entry.getValue();
//            String key = entry.getKey();
//
//
//        }


        System.out.println("\n\n\n");
       // System.out.println("Size: " + filledPairs.size());
        System.out.println("Liczba podejrzanych par: " + pairCount);
        System.out.println("Liczba podejrzanych \"par-dni\": " + pairDayCount);
        System.out.println("Liczba podejrzanych osób: " + hash_Set.size());

        System.out.println("\nHistogram:");
        int maxMeetCount = countPairs.values().stream().max(Comparator.comparing(Integer::valueOf)).orElse(0);
        for (int i = 1; i <= maxMeetCount; i++) {
            final int meetCount = i;
            long sum = countPairs.values().stream().filter(value -> value == meetCount).count();
            System.out.println(i + "=" + sum);
        }
    }

    public static boolean ifGo(double probability) {
        Random generator = new Random();

        if (generator.nextDouble() < probability) {
            return true;
        } else return false;
    }


}
