package DAO;

import entities.Position;
import entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PositionDao extends BaseDao<Position> {
    public PositionDao(Connection conn) {
        super(conn);
    }

    public Position findById(int id) throws SQLException {
        String sql = "SELECT * FROM \"Positions\" WHERE position_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Position position = new Position();
                    position.setId(rs.getLong("position_id"));
                    position.setName(rs.getString("name"));
                    // Дополнительные поля...
                    return position;
                }
            }
        }
        return null;
    }
}
