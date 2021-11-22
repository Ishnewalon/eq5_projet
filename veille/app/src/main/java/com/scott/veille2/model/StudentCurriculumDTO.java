package com.scott.veille2.model;

import java.util.List;

public class StudentCurriculumDTO {
    Curriculum principal;

    List<Curriculum> curriculumList;

    public StudentCurriculumDTO(Curriculum principal, List<Curriculum> curriculumList) {
        this.principal = principal;
        this.curriculumList = curriculumList;
    }

    public Curriculum getPrincipal() {
        return principal;
    }

    public void setPrincipal(Curriculum principal) {
        this.principal = principal;
    }

    public List<Curriculum> getCurriculumList() {
        return curriculumList;
    }

    public void setCurriculumList(List<Curriculum> curriculumList) {
        this.curriculumList = curriculumList;
    }
}
