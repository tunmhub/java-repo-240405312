package campusnav;

/**
 * 校园导航系统入口。
 * 按作业要求使用 if-else 实现多方案分发，体会传统分支结构的利弊。
 */
public class NavigationSystem {
    private final CampusMap map;
    private final WalkNavigator walkNavigator;
    private final BikeNavigator bikeNavigator;
    private final MixedNavigator mixedNavigator;

    public NavigationSystem(CampusMap map) {
        if (map == null) {
            throw new IllegalArgumentException("地图不能为空");
        }
        this.map = map;
        this.walkNavigator = new WalkNavigator(map);
        this.bikeNavigator = new BikeNavigator(map);
        this.mixedNavigator = new MixedNavigator(map);
    }

    public CampusMap getMap() {
        return map;
    }

    /**
     * 按模式导航。用 if-else 分发，新增模式时必须继续改这里。
     *
     * @param from 起点，如“图书馆”
     * @param to   终点，如“食堂”
     * @param mode 模式：walk / 步行，bike / 骑行，mixed / 混合
     * @return 可读的导航结果文本
     * @throws IllegalArgumentException 参数为空、地点不存在或模式不支持
     */
    public String navigate(String from, String to, String mode) {
        if (mode == null || mode.trim().isEmpty()) {
            throw new IllegalArgumentException("导航模式不能为空，当前可用: walk / bike / mixed");
        }
        String m = mode.trim().toLowerCase();
        if (m.equals("walk") || m.equals("步行")) {
            return walkNavigator.navigate(from, to);
        } else if (m.equals("bike") || m.equals("骑行")) {
            return bikeNavigator.navigate(from, to);
        } else if (m.equals("mixed") || m.equals("混合")) {
            return mixedNavigator.navigate(from, to);
        } else {
            throw new IllegalArgumentException(
                    "不支持的导航模式: " + mode + "（当前可用: walk / bike / mixed）");
        }
    }

    /**
     * 构建默认校园地图（示例数据）。
     */
    public static NavigationSystem createDefault() {
        CampusMap map = new CampusMap();
        map.addLocation(new Location("图书馆", "校园中心图书馆"));
        map.addLocation(new Location("教学楼A", "一号教学楼"));
        map.addLocation(new Location("食堂", "第一食堂"));
        map.addLocation(new Location("宿舍区", "学生宿舍"));
        map.addLocation(new Location("体育馆", "综合体育馆"));
        map.addPath("图书馆", "教学楼A", 0.4);
        map.addPath("图书馆", "食堂", 0.6);
        map.addPath("教学楼A", "食堂", 0.5);
        map.addPath("食堂", "宿舍区", 0.8);
        map.addPath("宿舍区", "体育馆", 1.2);
        map.addPath("图书馆", "宿舍区", 1.0);
        map.addPath("图书馆", "体育馆", 1.5);
        map.addPath("教学楼A", "体育馆", 1.3);
        return new NavigationSystem(map);
    }
}
