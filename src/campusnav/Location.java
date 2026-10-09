package campusnav;

/**
 * 校园地点。封装名称与简介，防止外部随意篡改。
 */
public class Location {
    private final String name;
    private final String description;

    public Location(String name, String description) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("地点名称不能为空");
        }
        this.name = name.trim();
        this.description = description == null ? "" : description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return name + "(" + description + ")";
    }
}
