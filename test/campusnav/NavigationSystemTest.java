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
}
