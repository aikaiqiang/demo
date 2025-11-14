package kaywall.top.example;

import java.util.Map;
import java.util.TreeMap;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {

        Map<String, Integer> map = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

        map.put("APPLE", 100);
        map.put("Banana", 200);
        System.out.println(map.get("apple ".trim()));

//        int a =  0 % 1;
//        int b =  1 % 1;
//        int c =  2 % 1;
//        System.out.println(a);
//        System.out.println(b);
//        System.out.println(c);
//        Map<String, Integer> map2 = Map.of("stageflow1", 1, "stageflow2", 2, "stageflow3", 3, "stageflow4", 4, "stageflow5", 5);
//
//        System.out.println(map2);
//        System.out.println( "Hello World!" );
//        long A = 1000L; // 修改: 使用超过 Long 缓存范围的值
//        Long B = 1000L; // 修改: 使用超过 Long 缓存范围的值
//        System.out.println("A == B: " + (A == B));
    }
}

