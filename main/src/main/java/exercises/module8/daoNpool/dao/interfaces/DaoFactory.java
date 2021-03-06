package exercises.module8.daoNpool.dao.interfaces;

import exercises.module8.daoNpool.dao.exception.PersistException;
import exercises.module8.daoNpool.dao.models.Group;
import exercises.module8.daoNpool.dao.models.Student;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Dmitrii
 * Date: 22.11.2017
 * Time: 18:59
 */

/**
 * Фабрика объектов для работы с базой данных
 */
public interface DaoFactory {

    /**
     * Возвращает объект для управления персистентным состоянием объекта
     */
    GenericDao getDao(Class clazz);

}