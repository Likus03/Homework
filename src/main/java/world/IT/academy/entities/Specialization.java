package world.IT.academy.entities;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Entity
@Table(name = "Specialization")
public class Specialization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "specializationCount")
    private Integer specializationCount;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "specialization", cascade = CascadeType.ALL)
    private List<Student> student;

    public Specialization(Integer specializationCount, String name, List<Student> student) {
        this.specializationCount = specializationCount;
        this.name = name;
        this.student = student;
    }

    public Specialization(String name, Integer specializationCount) {
        this.specializationCount = specializationCount;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSpecializationCount() {
        return specializationCount;
    }

    public void setSpecializationCount(Integer specializationCount) {
        this.specializationCount = specializationCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getStudent() {
        return student;
    }

    public void setStudent(List<Student> student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "Specialization{" +
                "id=" + id +
                ", specializationCount=" + specializationCount +
                ", name='" + name + '\'' +
                ", student=" + student +
                '}';
    }
}
