package cholog;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;

@Repository
public class UpdatingDAO {
    private JdbcTemplate jdbcTemplate;

    public UpdatingDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    private final RowMapper<Customer> actorRowMapper = (resultSet, rowNum) -> {
        Customer customer = new Customer(
                resultSet.getLong("id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name")
        );
        return customer;
    };

    /**
     * public int update(String sql, @Nullable Object... args)
     */
    public void insert(Customer customer) {
        //todo: customer를 디비에 저장하기
        String sql = "insert into customers (first_name, last_name) values(?, ?)";
        jdbcTemplate.update(sql, customer.getFirstName(), customer.getLastName());
    }
    /**
     * public int update(String sql, @Nullable Object... args)
     */
    public int delete(Long id) {
        //todo: id에 해당하는 customer를 지우고, 해당 쿼리에 영향받는 row 수 반환하기
        String sql = "delete from customers where id = ?";
        return jdbcTemplate.update(sql, id); // 메소드는 삭제된 행의 수를 반환. 존재하는 ID의 경우 1, 없는 ID의 경우 0
    }

    /**
     * public int update(final PreparedStatementCreator psc, final KeyHolder generatedKeyHolder)
     */

    /**
     * Spring Framework의 JdbcTemplate와 KeyHolder를 사용하여 customers 테이블에 새로운 고객 정보를 삽입하고, 삽입된 데이터의 자동 생성된 키(예: id)를 반환하는 과정을 구현
     * @param customer
     * @return
     */
    //todo : keyHolder에 대해 학습하고, Customer를 저장후 저장된 Customer의 id를 반환하기

    public Long insertWithKeyHolder(Customer customer) {
        String sql = "insert into customers (first_name, last_name) values (?, ?)"; // 테이블에 새로운 고객 정보를 삽입
        KeyHolder keyHolder = new GeneratedKeyHolder(); // 생성된 키(여기서는 id)를 보관할 객체

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into customers (first_name, last_name) values (?, ?) ",
                    new String[]{"id"}); // 쿼리를 실행한 후 생성된 키를 반환
            ps.setString(1, customer.getFirstName()); //'1'은 placeholder의 위치
            ps.setString(2, customer.getLastName());
            return ps;
        }
        , keyHolder);

        Long id = keyHolder.getKey().longValue();

        return keyHolder.getKey().longValue(); // 삽입된 고객의 id 값을 반환합니다
    }

    // JdbcTemplate의 execute 메서드를 사용하여 ConnectionCallback을 통해 Statement를 실행하고, 자동 생성된 키를 검색
    public Long insertWithStatement(Customer customer) {
        // SQL 문에서 직접적인 값 삽입
        String sql = "INSERT INTO customers (first_name, last_name) VALUES ('" + customer.getFirstName() + "', '" + customer.getLastName() + "')";
        return jdbcTemplate.execute(new ConnectionCallback<Long>() {
            @Override
            public Long doInConnection(Connection conn) throws SQLException, DataAccessException {
                Statement stmt = conn.createStatement();
                int affectedRows = stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            return generatedKeys.getLong(1); // 삽입된 고객의 ID 값을 반환
                        }
                    }
                }
                return null;
            }
        });
    }


}
