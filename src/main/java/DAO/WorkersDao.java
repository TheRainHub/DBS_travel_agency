package DAO;

import entities.Worker;
import entities.User;
import entities.Position;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkersDao extends BaseDao<Worker> {

    private final UserDao userDao;
    private final PositionDao positionDao;

    public WorkersDao(Connection connection, UserDao userDao, PositionDao positionDao) {
        super(connection);
        this.userDao = userDao;
        this.positionDao = positionDao;
    }

    @Override
    public void insert(Worker worker) throws SQLException {
        String sql = "INSERT INTO \"Workers\" (worker_id, user_id, position_id, experience, salary) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, worker.getId());
            stmt.setInt(2, worker.getUser().getId());
            stmt.setInt(3, worker.getPosition().getId());
            stmt.setLong(4, worker.getExperience());
            stmt.setLong(5, worker.getSalary());
            stmt.executeUpdate();
        }
    }

    @Override
    public Worker findById(int id) throws SQLException {
        String sql = "SELECT * FROM \"Workers\" WHERE worker_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapWorker(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Worker> findAll() throws SQLException {
        String sql = "SELECT * FROM \"Workers\"";
        List<Worker> workers = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                workers.add(mapWorker(rs));
            }
        }
        return workers;
    }

    @Override
    public void update(Worker worker) throws SQLException {
        String sql = "UPDATE \"Workers\" SET experience = ?, salary = ?, position_id = ? WHERE worker_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, worker.getExperience());
            stmt.setLong(2, worker.getSalary());
            stmt.setInt(3, worker.getPosition().getId());
            stmt.setInt(4, worker.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM \"Workers\" WHERE worker_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public List<Worker> findByMinSalary(long minSalary) throws SQLException {
        String sql = "SELECT * FROM \"Workers\" WHERE salary >= ?";
        List<Worker> workers = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, minSalary);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    workers.add(mapWorker(rs));
                }
            }
        }
        return workers;
    }

    private Worker mapWorker(ResultSet rs) throws SQLException {
        Worker worker = new Worker();
        worker.setId(rs.getInt("worker_id"));
        worker.setExperience(rs.getLong("experience"));
        worker.setSalary(rs.getLong("salary"));

        // Если требуется полная загрузка User и Position:
        worker.setUser(userDao.findById(rs.getInt("user_id")));
        worker.setPosition(positionDao.findById(rs.getInt("position_id")));

        return worker;
    }
}
