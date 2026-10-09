package campusnav;

/**
 * 演示入口。
 */
public class Main {
    public static void main(String[] args) {
        NavigationSystem system = NavigationSystem.createDefault();
        System.out.println("=== 校园导航系统演示 ===");
        try {
            String result = system.navigate("图书馆", "食堂", "walk");
            System.out.println(result);
        } catch (Exception e) {
            System.out.println("导航失败: " + e.getMessage());
        }
    }
}
