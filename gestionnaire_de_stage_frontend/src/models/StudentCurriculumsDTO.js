export default class StudentCurriculumsDTO{
    principal
    curriculumList

    constructor($principal, curriculumList) {
        this.principal = $principal;
        this.curriculumList = curriculumList;
    }
}