package core.ref;

import next.model.Question;
import next.model.User;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    public void showClass() {
        Class<Question> clazz = Question.class;

        // 패키지 + 클래스 이름을 반환한다.
        logger.debug(clazz.getName());

        // 모든 필드 목록을 반환한다.
        for(int i = 0 ; i < clazz.getDeclaredFields().length ; i++) {
            logger.debug(String.valueOf(clazz.getDeclaredFields()[i]));
        }

        // 모든 메서드 목록을 반환한다.
        for(int i = 0 ; i < clazz.getDeclaredMethods().length ; i++) {
            logger.debug(String.valueOf(clazz.getDeclaredMethods()[i]));
        }

    }
    
    @Test
    public void newInstanceWithConstructorArgs() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<User> clazz = User.class;
        logger.debug(clazz.getName());

        Constructor[] constructors = clazz.getDeclaredConstructors();
        for (Constructor constructor : constructors) {
            logger.debug(constructor.toString());
            logger.debug(String.valueOf(constructor.getParameterCount()));

            User user = (User) constructor.newInstance("sykim", "1234", "김승영", "sykim@woowahan.com");
            logger.debug("user : {}", user);
        }

    }
    
    @Test
    public void privateFieldAccess() throws Exception {
        Class<Student> clazz = Student.class;
        logger.debug(clazz.getName());

        logger.debug(String.valueOf(clazz.getDeclaredField("name")));

        Field field = clazz.getDeclaredField("name");

        field.setAccessible(true);

        Student student = new Student();

        field.set(student, "김승영");

        logger.debug("name : {}", student.getName());
    }
}
