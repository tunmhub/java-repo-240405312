package campusnav;

/**
 * 导航器抽象类：统一校验入口与结果格式，子类只需关心本模式的速度与提示语。
 * 使用抽象类集中公共流程，避免各模式复制粘贴同一套校验逻辑。
 */
public abstract class AbstractNavigator {
    protected final CampusMap map;

    protected AbstractNavigator(CampusMap map) {
        if (map == null) {
            throw new IllegalArgumentException("地图不能为空");
        }
        this.map = map;
    }

    /** 模式名称，如“步行” */
    public abstract String getModeName();

    /** 平均速度（千米/小时） */
    protected abstract double getSpeedKmh();

    /** 模式专属提示语 */
    protected abstract String getTip();

    /**
     * 执行导航。公共校验与结果拼接放在这里，子类不重复实现。
     */
    public String navigate(String from, String to) {
        validate(from, to);
        double distance = map.getDistance(from, to);
        if (distance < 0) {
            throw new IllegalStateException("两点之间暂无直达路径: " + from + " -> " + to);
        }
        if (distance == 0) {
            return "起点与终点相同(" + from + ")，无需移动。";
        }
        double minutes = distance / getSpeedKmh() * 60.0;
        return "【" + getModeName() + "】" + from + " → " + to
                + "\n  距离: " + formatDouble(distance) + " 公里"
                + "\n  预计耗时: " + formatDouble(minutes) + " 分钟"
                + "\n  " + getTip();
    }

    protected void validate(String from, String to) {
        if (from == null || from.trim().isEmpty()) {
            throw new IllegalArgumentException("起点不能为空");
        }
        if (to == null || to.trim().isEmpty()) {
            throw new IllegalArgumentException("终点不能为空");
        }
        if (!map.hasLocation(from)) {
            throw new IllegalArgumentException("起点不存在: " + from);
        }
        if (!map.hasLocation(to)) {
            throw new IllegalArgumentException("终点不存在: " + to);
        }
    }

    protected String formatDouble(double value) {
        return String.format("%.1f", value);
    }
}
