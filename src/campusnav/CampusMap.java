package campusnav;

/**
 * 校园地图：用数组保存地点与邻接距离矩阵。
 * 按作业要求不使用集合框架（List/Map）。
 */
public class CampusMap {
    private static final int MAX_LOCATIONS = 20;

    private final Location[] locations;
    private final double[][] distances;
    private int count;

    public CampusMap() {
        locations = new Location[MAX_LOCATIONS];
        distances = new double[MAX_LOCATIONS][MAX_LOCATIONS];
        count = 0;
        for (int i = 0; i < MAX_LOCATIONS; i++) {
            for (int j = 0; j < MAX_LOCATIONS; j++) {
                distances[i][j] = (i == j) ? 0.0 : -1.0;
            }
        }
    }

    public void addLocation(Location location) {
        if (location == null) {
            throw new IllegalArgumentException("地点不能为空");
        }
        if (count >= MAX_LOCATIONS) {
            throw new IllegalStateException("地点数量已达上限 " + MAX_LOCATIONS);
        }
        if (indexOf(location.getName()) >= 0) {
            throw new IllegalArgumentException("地点已存在: " + location.getName());
        }
        locations[count] = location;
        count++;
    }

    public void addPath(String from, String to, double distanceKm) {
        int i = indexOf(from);
        int j = indexOf(to);
        if (i < 0 || j < 0) {
            throw new IllegalArgumentException("路径端点不存在: " + from + " -> " + to);
        }
        if (distanceKm <= 0) {
            throw new IllegalArgumentException("距离必须大于 0");
        }
        distances[i][j] = distanceKm;
        distances[j][i] = distanceKm;
    }

    public boolean hasLocation(String name) {
        return indexOf(name) >= 0;
    }

    /**
     * @return 两点间距离（千米）；若不可直达返回 -1
     */
    public double getDistance(String from, String to) {
        int i = indexOf(from);
        int j = indexOf(to);
        if (i < 0 || j < 0) {
            return -1;
        }
        return distances[i][j];
    }

    public int getLocationCount() {
        return count;
    }

    public Location getLocationAt(int index) {
        if (index < 0 || index >= count) {
            throw new IndexOutOfBoundsException("下标越界: " + index);
        }
        return locations[index];
    }

    private int indexOf(String name) {
        if (name == null) {
            return -1;
        }
        String key = name.trim();
        for (int i = 0; i < count; i++) {
            if (locations[i].getName().equals(key)) {
                return i;
            }
        }
        return -1;
    }
}
