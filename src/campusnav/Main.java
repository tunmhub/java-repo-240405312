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
        String[][] demos = {
                {"图书馆", "食堂", "walk"},
                {"图书馆", "体育馆", "bike"},
                {"宿舍区", "体育馆", "mixed"},
        };
        for (String[] d : demos) {
            try {
                System.out.println(system.navigate(d[0], d[1], d[2]));
                System.out.println();
            } catch (Exception e) {
                System.out.println("[错误] " + e.getMessage());
            }
        }
    }
}
