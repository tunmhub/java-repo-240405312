package campusnav;

/**
 * 骑行导航。
 */
public class BikeNavigator extends AbstractNavigator {

    public BikeNavigator(CampusMap map) {
        super(map);
    }

    @Override
    public String getModeName() {
        return "骑行";
    }

    @Override
    protected double getSpeedKmh() {
        return 15.0;
    }

    @Override
    protected String getTip() {
        return "提示: 骑行请走非机动车道，校园内请减速慢行，注意行人。";
    }
}
