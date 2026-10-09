package campusnav;

/**
 * 演示入口。
 */
public class Main {
    public static void main(String[] args) {
        NavigationSystem system = NavigationSystem.createDefault();
        System.out.println("======== 校园导航系统 ========");
        System.out.println("已加载地点数: " + system.getMap().getLocationCount());
        System.out.println();
        try {
            System.out.println(system.navigate("图书馆", "食堂", "walk"));
        } catch (Exception e) {
            System.out.println("[错误] " + e.getMessage());
        }
    }
}
