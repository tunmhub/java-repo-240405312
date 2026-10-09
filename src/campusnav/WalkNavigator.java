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
        return "步行模式：请注意车辆，走人行道。";
    }
}
