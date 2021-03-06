package exercises.module8.daoNpool;


import exercises.module8.daoNpool.dao.impl.DaoFactoryImpl;
import exercises.module8.daoNpool.dao.interfaces.DaoFactory;
import exercises.module8.daoNpool.dao.interfaces.GenericDao;
import exercises.module8.daoNpool.dao.models.Group;
import exercises.module8.daoNpool.dao.models.Student;
import exercises.module8.daoNpool.pool.ConnectionPool;
import exercises.module8.daoNpool.pool.MyConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static exercises.module8.daoNpool.dao.utils.DbSampleData.ADD_SAMPLE_DATA;
import static exercises.module8.daoNpool.dao.utils.DbSampleData.CREATE_TABLE;

/**
 * @author Dmitrii
 *         Date: 22.11.2017
 *         Time: 15:57
 */
public class Main {

    public static void main(String[] args) {
        prepareData();
        try(Connection connection = ConnectionPool.getConnectionFromPool()) {
            DaoFactory df = new DaoFactoryImpl(connection);
            GenericDao<Group> groupDao = df.getDao(Group.class);
            groupDao.insert(new Group(234, "dfsdfasd"));
            List<Group> groups = groupDao.getAll();
            groups.forEach(a -> System.out.println(a.getDepartment()));
            System.out.println(groupDao.get(1));
            System.out.println(" ======================== ");
            GenericDao<Student> studentDao = df.getDao(Student.class);
            List<Student> students = studentDao.getAll();
            students.forEach(a -> System.out.println(a.getName() + " " + a.getSurname()));

        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    private static void prepareData() {
            try(Connection connection = ConnectionPool.getConnectionFromPool()) {
                Statement st = connection.createStatement();
                st.addBatch(CREATE_TABLE);
                st.addBatch(ADD_SAMPLE_DATA);
                int[] countWithoutException = st.executeBatch();
                System.out.println("Inserted = " + countWithoutException.length);
                st.close();
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
    }
}
