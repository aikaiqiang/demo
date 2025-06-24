package kaywall.top.example;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        long A = 1000L; // 修改: 使用超过 Long 缓存范围的值
        Long B = 1000L; // 修改: 使用超过 Long 缓存范围的值
        System.out.println("A == B: " + (A == B));
    }
}

