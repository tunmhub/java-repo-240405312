package campusnav;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

/**
 * 步行导航单元测试：覆盖正常、异常、边界场景。
 */
public class NavigationSystemTest {
    private NavigationSystem system;

    @Before
    public void setUp() {
        system = NavigationSystem.createDefault();
    }

    // ---------- 正常场景 ----------

    @Test
    public void testWalkModeNormal() {
        String result = system.navigate("图书馆", "食堂", "walk");
        assertNotNull("walk 模式不应返回 null", result);
        assertTrue("walk 模式应包含'步行'", result.contains("步行"));
        assertTrue("结果应包含起点", result.contains("图书馆"));
        assertTrue("结果应包含终点", result.contains("食堂"));
    }

    @Test
    public void testWalkModeChineseAlias() {
        String result = system.navigate("图书馆", "教学楼A", "步行");
        assertNotNull(result);
        assertTrue(result.contains("步行"));
    }

    @Test
    public void testWalkModeDistanceShown() {
        String result = system.navigate("宿舍区", "体育馆", "walk");
        assertTrue("应显示距离", result.contains("距离"));
        assertTrue("应显示耗时", result.contains("耗时"));
    }

    // ---------- 异常场景 ----------

    @Test
    public void testNullFrom() {
        try {
            system.navigate(null, "食堂", "walk");
            fail("起点为 null 应抛出异常");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("起点"));
        }
    }

    @Test
    public void testEmptyMode() {
        try {
            system.navigate("图书馆", "食堂", "");
            fail("模式为空应抛出异常");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("模式"));
        }
    }

    @Test
    public void testUnknownLocation() {
        try {
            system.navigate("火星站", "食堂", "walk");
            fail("不存在的起点应抛出异常");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("不存在"));
        }
    }

    @Test
    public void testUnsupportedMode() {
        try {
            system.navigate("图书馆", "食堂", "rocket");
            fail("不支持的模式应抛出异常");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("不支持"));
        }
    }

    // ---------- 边界场景 ----------

    @Test
    public void testSameStartAndEnd() {
        String result = system.navigate("图书馆", "图书馆", "walk");
        assertNotNull(result);
        assertTrue("起终点相同时应有提示", result.contains("无需移动") || result.contains("相同"));
    }

    @Test
    public void testBlankFrom() {
        try {
            system.navigate("   ", "食堂", "walk");
            fail("空白起点应抛出异常");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("起点"));
        }
    }

    @Test
    public void testModeCaseInsensitive() {
        String result = system.navigate("图书馆", "食堂", "WALK");
        assertNotNull(result);
        assertTrue(result.contains("步行"));
    }

    // ---------- 新增：骑行 / 混合 ----------

    @Test
    public void testBikeModeNormal() {
        String result = system.navigate("图书馆", "体育馆", "bike");
        assertNotNull("bike 模式不应返回 null", result);
        assertTrue("bike 模式应包含'骑行'", result.contains("骑行"));
    }

    @Test
    public void testBikeModeChineseAlias() {
        String result = system.navigate("宿舍区", "图书馆", "骑行");
        assertNotNull(result);
        assertTrue(result.contains("骑行"));
    }

    @Test
    public void testBikeFasterThanWalk() {
        String walk = system.navigate("图书馆", "体育馆", "walk");
        String bike = system.navigate("图书馆", "体育馆", "bike");
        // 从输出中粗略比较：骑行耗时应更短（分钟数更小）
        double walkMin = extractMinutes(walk);
        double bikeMin = extractMinutes(bike);
        assertTrue("骑行应快于步行", bikeMin < walkMin);
    }

    @Test
    public void testMixedModeShortDistanceWalkOnly() {
        // 图书馆-教学楼A 0.4km <= 1km，混合模式应全程步行
        String result = system.navigate("图书馆", "教学楼A", "mixed");
        assertNotNull(result);
        assertTrue(result.contains("混合"));
        assertTrue("近距离混合应步行", result.contains("步行"));
    }

    @Test
    public void testMixedModeLongDistance() {
        // 宿舍区-体育馆 1.2km，应骑行+步行
        String result = system.navigate("宿舍区", "体育馆", "mixed");
        assertNotNull(result);
        assertTrue(result.contains("混合"));
        assertTrue("远距离混合应含骑行", result.contains("骑行"));
    }

    @Test
    public void testUnreachablePath() {
        CampusMap map = new CampusMap();
        map.addLocation(new Location("A点", "孤立点A"));
        map.addLocation(new Location("B点", "孤立点B"));
        NavigationSystem sys = new NavigationSystem(map);
        try {
            sys.navigate("A点", "B点", "walk");
            fail("无路径时应抛出异常");
        } catch (IllegalStateException e) {
            assertTrue(e.getMessage().contains("路径") || e.getMessage().contains("直达"));
        }
    }

    @Test
    public void testUnknownModeStillRejected() {
        try {
            system.navigate("图书馆", "食堂", "subway");
            fail("不支持的模式应抛出异常");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("不支持"));
        }
    }

    private double extractMinutes(String navResult) {
        int idx = navResult.indexOf("预计耗时:");
        if (idx < 0) {
            return Double.MAX_VALUE;
        }
        String sub = navResult.substring(idx + "预计耗时:".length()).trim();
        int end = sub.indexOf(' ');
        if (end < 0) {
            end = sub.indexOf('分');
        }
        return Double.parseDouble(sub.substring(0, Math.max(end, 1)).trim());
    }
}
