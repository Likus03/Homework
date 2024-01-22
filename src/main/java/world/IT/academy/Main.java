package world.IT.academy;

import world.IT.academy.entities.Specialization;
import world.IT.academy.entities.Student;
import world.IT.academy.utils.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.*;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        /*1 query*/
        String specializationName = "IT";
        List<Student> studentBySpecializationName = findStudentBySpecializationName(specializationName);
        studentBySpecializationName.forEach(System.out::println);

        /*2 query*/
        String newSpecializationName = "testing";
        updateSpecializationName(newSpecializationName);

        /*3 query*/
        List<Specialization> studentsWithTwoFields = selectTwoFields();
        studentsWithTwoFields.forEach(System.out::println);

        //:)
    }

    private static List<Specialization> selectTwoFields() {
        EntityManager entityManager = JPAUtil.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Specialization> criteriaQuery = criteriaBuilder.createQuery(Specialization.class);

        Root<Specialization> root = criteriaQuery.from(Specialization.class);

        criteriaQuery.multiselect(root.get("name"), root.get("specializationCount"));

        List<Specialization> specializations = entityManager.createQuery(criteriaQuery).getResultList();

        transaction.commit();

        return specializations;
    }

    private static void updateSpecializationName(String newSpecializationName) {
        EntityManager entityManager = JPAUtil.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Specialization> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Specialization.class);

        Root<Specialization> root = criteriaUpdate.from(Specialization.class);

        criteriaUpdate.set(root.get("name"), newSpecializationName);
        criteriaUpdate
                .where(criteriaBuilder.equal(root.get("id"), 2L));

        entityManager.createQuery(criteriaUpdate).executeUpdate();
        transaction.commit();
    }

    private static List<Student> findStudentBySpecializationName(String specializationName) {
        EntityManager entityManager = JPAUtil.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Student> criteriaQuery = criteriaBuilder.createQuery(Student.class);

        Root<Student> studentRoot = criteriaQuery.from(Student.class);
        Join<Student, Specialization> joinSpecializationRoot = studentRoot.join("specialization", JoinType.INNER);

        Predicate condition = criteriaBuilder.equal(joinSpecializationRoot.get("name"), specializationName);

        criteriaQuery.select(studentRoot)
                .where(condition);

        List<Student> students = entityManager.createQuery(criteriaQuery).getResultList();

        transaction.commit();

        return students;
    }
}