import org.sql2o.Connection;

import java.util.List;

public class Community {
    private String name;
    private String description;
    private int id;

    public Community(String name, String description){
        this.name = name;
        this.description = description;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object otherCommunity){
        if (!(otherCommunity instanceof Community)) {
            return false;
        } else {
            Community newCommunity = (Community) otherCommunity;
            return this.getName().equals(newCommunity.getName()) &&
                    this.getDescription().equals(newCommunity.getDescription());
        }
    }
    public void save() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "INSERT INTO communities (name, description) VALUES (:name, :description)";
            this.id = (int) con.createQuery(sql, true)
                    .addParameter("name", this.name)
                    .addParameter("description", this.description)
                    .executeUpdate()
                    .getKey();
        }
    }
    public static List<Community> all() {
        String sql = "SELECT * FROM communities";
        try(Connection con = DB.sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Community.class);
        }
    }

}
