package campusnav;

/**
 * 混合导航：短距离步行、长距离骑行 + 步行接驳。
 * 规则：全程 ≤ 1 公里只步行；否则先骑行大部分路段，最后一段步行。
 */
public class MixedNavigator extends AbstractNavigator {

    private static final double WALK_ONLY_LIMIT_KM = 1.0;
    private static final double WALK_LAST_SEGMENT_KM = 0.3;

    public MixedNavigator(CampusMap map) {
        super(map);
    }

    @Override
    public String getModeName() {
        return "混合";
    }

    @Override
    protected double getSpeedKmh() {
        // 混合模式的综合速度：按比例估算
        return 10.0;
    }

    @Override
    protected String getTip() {
        return "提示: 混合导航 —— 近距离步行，远距离骑行为主、末端步行接驳。";
    }

    @Override
    public String navigate(String from, String to) {
        validate(from, to);
        double distance = map.getDistance(from, to);
        if (distance < 0) {
            throw new IllegalStateException("两点之间暂无直达路径: " + from + " -> " + to);
        }
        if (distance == 0) {
            return "起点与终点相同(" + from + ")，无需移动。";
        }
        if (distance <= WALK_ONLY_LIMIT_KM) {
            double minutes = distance / 4.8 * 60.0;
            return "【混合】" + from + " → " + to
                    + "\n  方案: 全程步行（距离较近）"
                    + "\n  距离: " + formatDouble(distance) + " 公里"
                    + "\n  预计耗时: " + formatDouble(minutes) + " 分钟"
                    + "\n  " + getTip();
        }
        double walkKm = Math.min(WALK_LAST_SEGMENT_KM, distance / 3);
        double bikeKm = distance - walkKm;
        double minutes = bikeKm / 15.0 * 60.0 + walkKm / 4.8 * 60.0;
        return "【混合】" + from + " → " + to
                + "\n  方案: 骑行 " + formatDouble(bikeKm) + " 公里 + 步行 "
                + formatDouble(walkKm) + " 公里"
                + "\n  距离: " + formatDouble(distance) + " 公里"
                + "\n  预计耗时: " + formatDouble(minutes) + " 分钟"
                + "\n  " + getTip();
    }
}
