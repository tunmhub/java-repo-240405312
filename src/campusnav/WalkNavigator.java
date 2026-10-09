package campusnav;

/**
 * 步行导航。
 */
public class WalkNavigator extends AbstractNavigator {

    public WalkNavigator(CampusMap map) {
        super(map);
    }

    @Override
    public String getModeName() {
        return "步行";
    }

    @Override
    protected double getSpeedKmh() {
        return 4.8;
    }

    @Override
    protected String getTip() {
        return "提示: 步行模式建议走人行道，注意来往车辆，约 5 分钟/400 米。";
    }
}
